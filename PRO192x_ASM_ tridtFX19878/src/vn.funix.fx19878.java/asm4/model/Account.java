package vn.funix.fx19878.java.asm4.model;

import vn.funix.fx19878.java.asm4.dao.TransactionDAO;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class Account implements Serializable {
    private static final long serialVersionUID = 5462223600L;
    private String customerId;
    private String accountNumber;
    private double balance;
    private String accountType;
    private List<Transaction> transactions = new ArrayList<>();


    public Account(String customerId, String accountNumber, double balance, String accountType) {
        this.customerId = customerId;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.accountType = accountType;

    }

    public String getCustomerId() {
        return customerId;
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
    public static boolean minBalance(double balance){
        return balance >= 50000;
    }

    public String toString() {
        DecimalFormat format = new DecimalFormat("#,###vnd");
        return accountNumber + " |             " + accountType + " |               " + format.format(balance);
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public List<Transaction> getTransaction() {
        return TransactionDAO.list();
    }

    /* HÀM HIỆN THỊ DANH SÁCH GIAO DỊCH TƯƠNG ỨNG VỚI MÃ SỐ KHÁCH HÀNG NHẬP VÀO */
    public void displayTransactionsList(){
        if(getTransaction() != null){
            ArrayList<Transaction> transactions = new ArrayList<>(TransactionDAO.list());
            System.out.println("Lich su giao dich:");
            for(Transaction transaction : transactions){
                if(Objects.equals(getCustomerId(), transaction.getcustomerId())){
                    System.out.println(transaction.toString());
                }
            }
        }
    }
    /* HÀM TẠO GIAO DỊCH MỚI, THÊM VÀO FILE TRANSACITON.DAT */
    public void createTransaction(Transaction transaction){
        List<Transaction> transactionList = TransactionDAO.list();
        transactionList.add(transaction);
        TransactionDAO.save(transactionList);
    }
}
