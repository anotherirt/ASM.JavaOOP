package vn.funix.fx19878.java.asm2.models;

import org.w3c.dom.ls.LSOutput;
import vn.funix.fx19878.java.asm3.models.Transaction;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Account {
    private String accountNumber;
    private double balance;
    private String accountType;

    private List<Transaction> transaction = new ArrayList<>();


    public Account(String accountNumber, double balance, String accountType) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.accountType = accountType;

    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public boolean isPremium() {
        return getBalance() >= 10000000.0;
    }

    public String toString() {
        DecimalFormat format = new DecimalFormat("#,###đ");
        return accountNumber + " |   " + accountType + " |               " + format.format(balance);
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public List<Transaction> getTransaction() {
        return transaction;
    }

    public void addTransaction(Transaction transaction){
        this.transaction.add(transaction);
//        System.out.println(this.transaction);
    }

    public void displayTransactions(){
        if(getTransaction() != null){
            ArrayList<Transaction> transactions = new ArrayList<>(getTransaction());
            System.out.println("+----------+----------------------+-------+");
            for(Transaction transaction : transactions){
                System.out.println(transaction.toString());
            }
        }
    }
}
