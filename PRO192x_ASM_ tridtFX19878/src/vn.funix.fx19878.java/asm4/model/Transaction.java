package vn.funix.fx19878.java.asm4.model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class Transaction implements Serializable {
    private static final long serialVersionUID = 5462223600L;
    private String customerId;
    private String accountNumber;
    private double amount;
    private String time;
    private boolean status;
    TransactionType type;

    public Transaction(String customerId, String accountNumber, double amount, boolean status, TransactionType type) {
        this.customerId = customerId;
        this.accountNumber = accountNumber;
        this.amount = amount;
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        this.time = dateFormat.format(date);
        this.status = status;
        this.type = type;
    }

    public String getcustomerId() {
        return customerId;
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

    public TransactionType getType() {
        return type;
    }

    public boolean isStatus() {
        return status;
    }

    public String toString(){
        DecimalFormat format = new DecimalFormat("#,###vnd");
        return "[GD]" + String.format("%10s", getAccountNumber()) + " | " + String.format("%15s", getType()) + " |" +
                String.format("%10s", format.format(getAmount())) + " |"  + String.format("%10s", getTime());
    }
}
