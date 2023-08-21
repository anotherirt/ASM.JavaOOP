package vn.funix.fx19878.java.asm3.models;

import vn.funix.fx19878.java.asm2.models.Account;
import vn.funix.fx19878.java.asm2.models.Customer;

import java.util.List;
import java.util.Objects;

public class DigitalCustomer extends Customer {

    public DigitalCustomer(String name, String customerId, List<Account> account) {
        super(name, customerId, account);
    }

    public void withdraw(String number, Double amount){
        Account account;
        for(int i = 0; i < getAccounts().size(); i++){
            if(Objects.equals(getAccounts().get(i).getAccountNumber(), number)){
                account = getAccounts().get(i);
                if(Objects.equals(account.getAccountType(), "SAVINGS")){
                    SavingsAccount savingsAccount = (SavingsAccount) account;
                    boolean isPremium = savingsAccount.isPremium();
                    if(savingsAccount.withdraw(amount)){
                        savingsAccount.log(amount, isPremium);
                    } else {
                        System.out.println("So tien rut khong kha dung.");
                    }
                    break;
                }
                if(Objects.equals(account.getAccountType(), "LOAN")){
                    LoanAccount loanAccount = (LoanAccount) account;
                    boolean isPremium = loanAccount.isPremium();
                    if(loanAccount.withdraw(amount)){
                        loanAccount.log(amount, isPremium);
                    } else {
                        System.out.println("So tien rut khong kha dung.");
                    }
                    break;
                }
            }
        }
    }
}
