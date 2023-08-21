package vn.funix.fx19878.java.asm3.models;

import vn.funix.fx19878.java.asm2.models.Customer;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class Transaction {
    private String id;
    private String accountNumber;
    private double amount;
    private String time;
    private boolean status;

    public Transaction(String accountNumber, double amount, boolean status) {
        this.id = String.valueOf(UUID.randomUUID());
        this.accountNumber = accountNumber;
        this.amount = amount;
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        this.time = dateFormat.format(date);
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getAmount() {
        return amount;
    }

    public String getTime() {
        return time;
    }

    public boolean isStatus() {
        return status;
    }

    public String toString(){
        DecimalFormat format = new DecimalFormat("#,###đ");

            return "[GD]" + String.format("%10s", getAccountNumber()) + " | " + String.format("%20s", format.format(getAmount()))+
                    " |" + String.format("%12s", isStatus()?"Thành công  | ":"Thất bại  | ") + String.format("%10s", getTime());
    }
}
