package vn.funix.fx19878.java.asm3.models;

import vn.funix.fx19878.java.asm2.models.Account;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class LoanAccount extends Account implements IReportService,IWithdraw {
    public static final double LOAN_ACCOUNT_WITHDRAW_FEE = 0.05;
    public static final double LOAN_ACCOUNT_WITHDRAW_PREMIUM_FEE = 0.01;
    public static final double LOAN_ACCOUNT_MAX_BALANCE = 100000000.0;

    public LoanAccount(String accountNumber, double balance, String accountType) {
        super(accountNumber, balance, "LOAN");
    }

    // KIỂM TRA SỐ TIỀN RÚT CÓ ĐÚNG ĐIỀU KIỆN:
    // Hạn mức không được quá giới hạn 100.000.000đ
    // Hạn mức còn lại sau khi rút tiền không được nhỏ hơn 50.000đ
    @Override
    public boolean isAccepted(double amount) {
        return amount < LOAN_ACCOUNT_MAX_BALANCE && (getBalance() - amount >= 50000);
    }


    // NẾU SỐ TIỀN RÚT ĐÚNG ĐIỀU KIỆN, TRỪ SỐ DƯ TRONG TÀI KHOẢN
    // VỚI Phí cho mỗi lần giao dịch là 0.05 trên số tiền giao dịch đối với tài khoản thường và 0.01 trên số tiền giao dịch đối với tài khoản Premium.
    @Override
    public boolean withdraw(double amount) {
        double newBalance = 0.0;
        if(isAccepted(amount)){
                newBalance = getBalance() - amount - getFee(amount, isPremium());
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
        System.out.println("|   PHI+VAT:  " + String.format("%24s", String.format("%,d", (long) getFee(amount, isPremium))) + "đ   |");
        System.out.println("|   SO DU:    " + String.format("%24s", String.format("%,d", (long) getBalance())) + "đ   |");
        System.out.println("+----------+----------------------+-------+");
    }
    public double getFee(double amount, boolean is_premium) {
            if (is_premium) {
                return LOAN_ACCOUNT_WITHDRAW_PREMIUM_FEE * amount;
            } else {
                return LOAN_ACCOUNT_WITHDRAW_FEE * amount;
            }
    }
}
