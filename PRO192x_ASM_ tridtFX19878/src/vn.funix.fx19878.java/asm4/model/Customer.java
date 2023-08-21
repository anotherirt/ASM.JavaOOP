package vn.funix.fx19878.java.asm4.model;


import vn.funix.fx19878.java.asm4.dao.AccountDAO;
import vn.funix.fx19878.java.asm4.dao.CustomerDAO;

import java.io.IOException;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.*;

public class Customer extends User {
    private static final long serialVersionUID = 5462223600L;
    private static final Scanner sc = new Scanner(System.in);
    private final List<Account> accounts = new ArrayList<>();

    public Customer(String customerID, String name) {
        super(customerID, name);
    }

    public Customer(List<String> values) {
        this(values.get(0), values.get(1));
    }

    public List<Account> getAccounts() {
        return AccountDAO.list();
    }

    public boolean isPremium() {
        for (Account account : AccountDAO.list()) {
            if (Objects.equals(account.getCustomerId(), getCustomerId())) {
                if (account.isPremium()) {
                    return true;
                }
            }
        }
        return false;
    }

    /* HÀM THÊM TÀI KHOẢN VÀO FILE ACCOUNT.DAT */
    public void addAccount(Account newAccount){
        List<Account> accountList = AccountDAO.list();
        accountList.add(newAccount);
        AccountDAO.save(accountList);
    }

//    public boolean isAccountExisted(String accountNumber) {
//        for (Account account : AccountDAO.list()) {
//            if (Objects.equals(accountNumber, account.getAccountNumber())) {
//                return true;
//            }
//        }
//        return false;
//    }

    public boolean isAccountExisted(String accountNumbers) {
        return AccountDAO.list()
                .stream()
                .anyMatch(account -> Objects.equals(accountNumbers, account.getAccountNumber()));
    }

    public double getBalance() {
        double total = 0;
        for (Account account : AccountDAO.list()) {
            if (Objects.equals(account.getCustomerId(), getCustomerId())) {
                total += account.getBalance();
            }
        }
        return total;
    }

    public Account getAccountByAccountNumber(List<Account> accounts, String accountNumber) {
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        return null;
    }

    /* HÀM HIỆN THỊ THÔNG TIN KHÁCH HÀNG VÀ TÀI KHOẢN CỦA KHÁCH HÀNG */
    public void displayInformation() {
        DecimalFormat format = new DecimalFormat("#,###vnd");
        String isPre = isPremium() ? "Premium" : "Normal";
        int count = 1;
        System.out.println(getCustomerId() + " |       " + getName() + " |   " + isPre + " |   " + format.format(getBalance()));

        for (Account account : AccountDAO.list()) {
            if (account.getCustomerId().equals(getCustomerId())) {
                System.out.println(count++ + "     " + account.toString());
            }
        }
    }
    /* HÀM HIỂN THỊ TẤT CẢ GIAO DỊCH CỦA KHÁCH HÀNG THEO MÃ SỐ KHÁCH HÀNG */
    public void displayTransactionInformation(){
        DecimalFormat format = new DecimalFormat("#,###vnd");
        String isPre = isPremium() ? "Premium" : "Normal";
        int count = 1;
        System.out.println(getCustomerId() + " |       " + getName() + " |   " + isPre + " |   " + format.format(getBalance())); // Thông tin KH

        for(Account account : AccountDAO.list()){
            if(Objects.equals(account.getCustomerId(), getCustomerId())){
                System.out.println(count++ + "     " + account.toString()); // Thông tin tài khoản của KH
            }
        }

        for (Account account : AccountDAO.list()) {
            if (account.getCustomerId().equals(getCustomerId())) {
                account.displayTransactionsList(); // Lịch sử giao dịch tất cả account của KH tương ứng
                return;
            }
        }
    }
    /* HÀM THỰC HIỆN CHUYỂN TIỀN VÀ CẬP NHẬT SỐ DƯ TK TRONG FILE ACCOUNTS.DAT */
    public void transfer(Scanner scanner) {
        DecimalFormat format = new DecimalFormat("#,###vnd");
        List<Account> accountList = AccountDAO.list();
        if (!accountList.isEmpty()) {
            Account account1;
            Account account2;
            double amount;

            do {
                System.out.println("Nhap so tai khoan: ");
                account1 = getAccountByAccountNumber(accountList, scanner.nextLine()); // Nhập vào số TK gửi tiền
            } while (account1 == null);

            do {
                System.out.println("Nhap so tai khoan nhan: ");
                account2 = getAccountByAccountNumber(accountList, scanner.nextLine()); // Nhập vào số TK nhận tiền
            } while (account2 == null);
            for(Customer customer : CustomerDAO.list()){
                if(Objects.equals(customer.getCustomerId(), account2.getCustomerId())){
                    System.out.println("Gui tien den tai khoan: " + account2.getAccountNumber() + " | " + customer.getName()); // Hiện thị tên chủ TK
                }
            }

            do {
                System.out.println("Nhap so tien chuyen: ");
                amount = Double.parseDouble(scanner.nextLine()); // Nhập vào số tiền chuyển
            } while (amount <= 0);

            System.out.println("Xac nhan thuc hien chuyen " + format.format(amount) + "đ tu tai khoan [" + account1.getAccountNumber() + "] den tai khoan [" + account2.getAccountNumber() + "] (Y/N): ");
            String xacThuc = sc.next(); // Xác nhận lại số tiền chuyển đi Y/N
            if (xacThuc.equals("N") || xacThuc.equals("n")) {
                System.out.println("Cam on!! Hen gap lai.");
                System.exit(0);
            } else if (xacThuc.equals("Y") || xacThuc.equals("y")) {
                    SavingsAccount savingsAccount = (SavingsAccount) account1;
                    savingsAccount.transfers(account2, amount); // nhập Y/y thực hiện giao dịch chuyển tiền trên đối tượng SavingsAccount
                    AccountDAO.update(savingsAccount); // Cập nhật lại số dư TK trong file sau khi đã chuyển tiền (bị trừ tiền)
            } else {
                System.out.println("Thao tac khong thanh cong");
            }
        }
    }

    /* HÀM THỰC HIỆN RÚT TIỀN VÀ CẬP NHẬT SỐ DƯ TK TRONG FILE ACCOUNTS.DAT */
    public void withdraw(Scanner scanner) {
        List<Account> accounts = AccountDAO.list();
        if (!accounts.isEmpty()) {
            Account account;
            double amount;

            do {
                System.out.println("Nhap so tai khoan: ");
                account = getAccountByAccountNumber(accounts, scanner.nextLine()); // Nhập vào số TK muốn rút tiền
            } while (account == null);

            do {
                System.out.println("Nhap so tien rut: ");
                amount = Double.parseDouble(scanner.nextLine()); // Nhập số tiền muốn rút
            } while (amount <= 0);

            if (account instanceof SavingsAccount) {
                SavingsAccount savingsAccount = (SavingsAccount) account;
                savingsAccount.withdraw(amount); // Thực hiện rút tiền từ account với amount vừa nhập
                AccountDAO.update(savingsAccount); // Cập nhật số dư TK trong file
            }
        } else {
            System.out.println("Thao tac khong thanh cong");
        }
    }
}
