package vn.funix.fx19878.java.asm4.model;

import vn.funix.fx19878.java.asm3.models.IReportService;
import vn.funix.fx19878.java.asm3.models.IWithdraw;
import vn.funix.fx19878.java.asm4.dao.AccountDAO;
import vn.funix.fx19878.java.asm4.service.ITransfer;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SavingsAccount extends Account implements IReportService, IWithdraw, ITransfer {
    public static final double SAVINGS_ACCOUNT_MAX_WITHDRAW = 5000000.0;
    private static final double SAVINGS_ACCOUNT_WITHDRAW_FEE = 0.0;
    private static final double SAVINGS_ACCOUNT_WITHDRAW_PREMIUM_FEE = 0.0;

    public SavingsAccount(String customerId, String accountNumber, double balance, String accountType) {
        super(customerId, accountNumber, balance, "SAVINGS");
    }

    @Override
    public void log(double amount, boolean isPremium) {
        DecimalFormat format = new DecimalFormat("#,###vnd");
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        System.out.println("+----------+----------------------+-------+");
        System.out.println("|        BIÊN LAI GIAO DỊCH SAVINGS       |");
        System.out.println("|   NGÀY G/D:  " + String.format("%24s", dateFormat.format(date)) + "   |");
        System.out.println("|   ATM ID:    " + String.format("%24s", "DIGITAL-BANK-ATM-2023") + "   |");
        System.out.println("|   SO TK:     " + String.format("%24s", getAccountNumber()) + "   |");
        System.out.println("|   SO TIEN:   " + String.format("%24s", String.format("%s", format.format(amount))) + "   |");
        System.out.println("|   PHI+VAT:   " + String.format("%24s", String.format("%s", format.format(getFee(amount)))) + "   |");
        System.out.println("|   SO DU:     " + String.format("%24s", String.format("%s", format.format(getBalance()))) + "   |");
        System.out.println("+----------+----------------------+-------+");
    }

    /* HÀM THỰC HIỆN RÚT TIỀN VÀ TẠO GIAO DỊCH VÀO FILE TRANSACTION.DAT */
    @Override
    public boolean withdraw(double amount) {
        if(isAccepted(amount)){
            double newBalance = getBalance() - amount - getFee(amount);
            setBalance(newBalance);
            System.out.println("Giao dich thanh cong!!");
            log(amount, isPremium());
            Transaction withdrawTransaction = new Transaction(getCustomerId(),getAccountNumber(),-amount,true,TransactionType.WITHDRAW);
            createTransaction(withdrawTransaction); // tạo lịch sư gioa dịch rút tiền (trừ tiền)
            return true;
        }
        System.out.println("Giao dich khong thanh cong!!");
        return false;
    }

    @Override
    public boolean isAccepted(double amount) {
        double minAmount = 50000;
        if (minBalance((getBalance()-amount)) && amount%10000==0 && amount>=minAmount){
            if(!isPremium()){
                if(amount <= SAVINGS_ACCOUNT_MAX_WITHDRAW){
                    return true;
                }else {
                    System.out.println("Khong the rut tren 5.000.000vnd tren mot lan giao dich. Vui long thu lai.");
                }
            }else {
                return true;
            }
        }else {
            if(!minBalance(getBalance() - amount)){
                System.out.println("So du sau khi rut duoi 50,000. Vui long thu lai sau. ");
            }
            else if(amount%10000!=0){
                System.out.println("So tien rut phai la boi cua 10,000. Vui long thu lai sau. ");
            }
            else if (amount<minAmount){
                System.out.println("So tien rut toi thieu 50,000. Vui long thu lai sau.");
            }
        }
        return false;
    }

    public double getFee(double amount) {
        if (isPremium()) {
            return SAVINGS_ACCOUNT_WITHDRAW_PREMIUM_FEE * amount;
        } else {
            return SAVINGS_ACCOUNT_WITHDRAW_FEE * amount;
        }
    }

    /* HÀM THỰC HIỆN CHUYỂN TIỀN VÀ TẠO GIAO DỊCH VÀO FILE TRANSACTION.DAT */
    @Override
    public boolean transfers(Account receivedAccount, double amount) {
        if (isAccepted(amount)) {
            double newBalance1 = getBalance() - amount - getFee(amount);
            this.setBalance(newBalance1); // Cập nhật số dư sau khi chuyển tiền của TK gửi
            double newBalance2 = receivedAccount.getBalance() + amount;
            receivedAccount.setBalance(newBalance2); // Cập nhật số dư TK nhận tiền
            AccountDAO.update(receivedAccount); // Cập nhật số dư TK trong file
            System.out.println("Giao dich thanh cong!!");
            Transaction transaction1 = new Transaction(getCustomerId(), getAccountNumber(), -amount, true, TransactionType.TRANSFERS);
            createTransaction(transaction1); // Tạo lịch sử giao dịch chuyển tiền cho TK gửi (trừ tiền)
            Transaction transaction2 = new Transaction(receivedAccount.getCustomerId(), receivedAccount.getAccountNumber(), amount, true, TransactionType.DEPOSIT);
            createTransaction(transaction2); // Tạo lịch sử giao dịch chuyển tiền cho TK nhận (cộng tiền)
            logTranfer(receivedAccount, amount); // In ra console biên lai giao dịch chuyển tiền
            return true;
        }
        System.out.println("Giao dich khong thanh cong");
        return false;
    }

    public void logTranfer(Account receivedAccount, double amount) {
        DecimalFormat format = new DecimalFormat("#,###vnd");
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        System.out.println("+----------+----------------------+-------+");
        System.out.println("|        BIÊN LAI GIAO DỊCH SAVINGS       |");
        System.out.println("|   NGÀY G/D:  " + String.format("%24s", dateFormat.format(date)) + "   |");
        System.out.println("|   ATM ID:    " + String.format("%24s", "DIGITAL-BANK-ATM-2023") + "   |");
        System.out.println("|   SO TK:     " + String.format("%24s", getAccountNumber()) + "   |");
        System.out.println("|   SO TK NHAN:    " + String.format("%20s", receivedAccount.getAccountNumber()) + "   |");
        System.out.println("|   SO TIEN CHUYEN:" + String.format("%20s", String.format("%s", format.format(amount))) + "   |");
        System.out.println("|   SO DU:     " + String.format("%24s", String.format("%s", format.format(getBalance()))) + "   |");
        System.out.println("|   PHI+VAT:   " + String.format("%24s", String.format("%s", format.format(getFee(amount)))) + "   |");
        System.out.println("+----------+----------------------+-------+");
    }
}
