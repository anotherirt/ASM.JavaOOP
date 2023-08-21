package vn.funix.fx19878.java.asm3.models;

import vn.funix.fx19878.java.asm2.models.Account;
import vn.funix.fx19878.java.asm4.service.ITransfer;

import java.sql.SQLOutput;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SavingsAccount extends Account implements IReportService,IWithdraw {
    public static final double SAVINGS_ACCOUNT_MAX_WITHDRAW = 5000000.0;
    private static final double SAVINGS_ACCOUNT_WITHDRAW_FEE = 0.0;
    private static final double SAVINGS_ACCOUNT_WITHDRAW_PREMIUM_FEE = 0.0;

    public SavingsAccount(String accountNumber, double balance, String accountType) {
        super(accountNumber, balance, "SAVINGS");
    }


    // KIỂM TRA SỐ TIỀN RÚT CÓ ĐÚNG ĐIỀU KIỆN:
    // Số tiền rút phải lớn hơn hoặc bằng 50.000đ
    // Số tiền 1 lần rút không được quá 5.000.000đ đối với tài khoản thường, và không giới hạn đối với tài khoản Premium.
    // Số dư còn lại sau khi rút phải lớn hơn hoặc bằng 50.000đ
    // Số tiền rút phải là bội số của 10.000đ
    @Override
    public boolean isAccepted(double amount) {
        if((amount >= 50000.0) && (amount % 10000.0 == 0) && (getBalance() - amount >= 50000.0)){
            if(isPremium()){
                return true;
            }
            else return amount < SAVINGS_ACCOUNT_MAX_WITHDRAW;
        }
        return false;
    }

    // NẾU SỐ TIỀN RÚT ĐÚNG ĐIỀU KIỆN, TRỪ SỐ DƯ TRONG TÀI KHOẢN
    @Override
    public boolean withdraw(double amount) {
        if(isAccepted(amount)){
            double newBalance = getBalance() - amount - getFee(amount);
            Transaction transaction = new Transaction(this.getAccountNumber(), amount, true);
            addTransaction(transaction);
            setBalance(newBalance);
            System.out.println("Giao dich thanh cong!!");
            return true;
        }
            System.out.println("Giao dich khong thanh cong!!");
            return false;
    }

    // IN BIÊN LAI GIAO DỊCH SAU KHI RÚT TIỀN
    @Override
    public void log(double amount, boolean isPremium) {
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        System.out.println("+----------+----------------------+-------+");
        System.out.println("|        BIÊN LAI GIAO DỊCH SAVINGS       |");
        System.out.println("|   NGÀY G/D:  " + String.format("%24s", dateFormat.format(date)) + "   |");
        System.out.println("|   ATM ID:    " + String.format("%24s", "DIGITAL-BANK-ATM-2023") + "   |");
        System.out.println("|   SO TK:     " + String.format("%24s", getAccountNumber()) + "   |");
        System.out.println("|   SO TIEN:  " + String.format("%24s", String.format("%,d", (long) amount)) + "đ   |");
        System.out.println("|   PHI+VAT:  " + String.format("%24s", String.format("%,d", (long) getFee(amount))) + "đ   |");
        System.out.println("|   SO DU:    " + String.format("%24s", String.format("%,d", (long) getBalance())) + "đ   |");
        System.out.println("+----------+----------------------+-------+");
    }
    public double getFee(double amount) {
        if (isPremium()) {
            return SAVINGS_ACCOUNT_WITHDRAW_PREMIUM_FEE * amount;
        } else {
            return SAVINGS_ACCOUNT_WITHDRAW_FEE * amount;
        }
    }

}
