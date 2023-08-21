package vn.funix.fx19878.java.asm2.models;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Customer extends User {
    private List<Account> accounts;

    public Customer(String name, String customerId, List<Account> account) {
        super(name, customerId);
        accounts = new ArrayList<Account>();
    }

    public Customer(String name, String cccd) {
        super(name, cccd);
    }

    public Customer(List<String> values){
        this(values.get(0), values.get(1));
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public boolean isPremium(){
        for (Account account : this.accounts) {
            if (account.isPremium()) {
                return true;
            }
        }
        return false;
    }

    public void addAccount(Account newAccount) {
        accounts.add(newAccount);
    }

    public boolean isAccountExisted(String accountNumber) {
        for (Account account : accounts) {
            if (Objects.equals(accountNumber, account.getAccountNumber())) {
                return true;
            }
        }
        return false;
    }

    public double getBalance() {
        double total = 0;
        for (Account account : accounts) {
            total += account.getBalance();
        }
        return total;
    }

    public Account getAccountByAccountNumber(String accountNumber) {
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        return null;
    }

    public void displayInformation() {
        DecimalFormat format = new DecimalFormat("#,###đ");
        String isPre;
        if(isPremium())
            isPre="Premium";
        else
            isPre="Normal";

        System.out.println(getCustomerId() + " |       " + getName() + " |   " + isPre + " |   " + format.format(getBalance()));

        for(int i = 0; i <= this.accounts.size() - 1; i++){
            System.out.println(i+1 + "     " + this.accounts.get(i).toString());
        }
    }
}
