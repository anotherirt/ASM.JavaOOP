package vn.funix.fx19878.java.asm4.dao;

import vn.funix.fx19878.java.asm4.model.Account;
import vn.funix.fx19878.java.asm2.models.Customer;
import vn.funix.fx19878.java.asm4.service.BinaryFileService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {
    private final static String FILE_PATH = "store\\accounts.dat";

    public static void save(List<Account> accounts){
        BinaryFileService.writeFile(FILE_PATH, accounts);
    }

    public static List<Account> list() {
        return BinaryFileService.readFile(FILE_PATH);
    }

    public static void update(Account editAccount){

        List<Account> accounts = list();
        boolean hasExist = accounts.stream().anyMatch(account -> account.getAccountNumber().equals(editAccount.getAccountNumber()));

        List<Account> updateAccounts;
        if (!hasExist) {
            updateAccounts = new ArrayList<>();
            updateAccounts.add(editAccount);
        } else {
            updateAccounts = new ArrayList<>();
            for (Account acc : accounts) {
                if (acc.getAccountNumber().equals(editAccount.getAccountNumber())) {
                    updateAccounts.add(editAccount);
                } else {
                    updateAccounts.add(acc);
                }
            }
        }
        save(updateAccounts);
    }
}
