package vn.funix.fx19878.java.asm3;

import vn.funix.fx19878.java.asm2.models.Account;
import vn.funix.fx19878.java.asm2.models.Customer;
import vn.funix.fx19878.java.asm3.models.DigitalBank;
import vn.funix.fx19878.java.asm3.models.DigitalCustomer;
import vn.funix.fx19878.java.asm3.models.LoanAccount;
import vn.funix.fx19878.java.asm3.models.SavingsAccount;

import java.util.Scanner;
import java.util.regex.Pattern;

public class ASM3 {
    public static final String AUTHOR = "FX19878";
    public static final String VERSION = "V3.0.0";
    public static final String CUSTOMER_ID = "091097122214";
    public static final String CUSTOMER_NAME = "TRI";
    public static final DigitalBank activeBank = new DigitalBank();
    public static final Scanner sc = new Scanner(System.in);


    public static void main(String[] args) {
        activeBank.addCustomer(CUSTOMER_ID, CUSTOMER_NAME);
        handleFeature();
    }

    // CHỨC NĂNG 1: HIỆN THỊ DANH SÁCH KHÁCH HÀNG
    public static void showCustomer(){
        Customer customer = activeBank.getCustomerById(CUSTOMER_ID);
        if(customer != null)
            customer.displayInformation();
    }

    // CHỨC NĂNG 2: THÊM TÀI KHOẢN SAVINGS CHO KHÁCH HÀNG
    public static void addSavingsAccount(){
        String accNumbers;
        double balance;

        System.out.println("Nhap so TK gom 6 chu so: ");
        accNumbers = sc.next();
        Pattern pattern = Pattern.compile("^\\d{6}$"); // ĐỊNH DẠNG SỐ TK GỒM 6 SỐ
        while (!(pattern.matcher(accNumbers).find())) {
            System.out.println("So TK khong dung \nVui long nhap lai: ");
            accNumbers = sc.next();
        }
        // KIỂM TRA SỐ TK NGƯỜI DÙNG NHẬP VÀO VỚI DANH SÁCH TRONG BANK, ĐÃ TỒN TẠI HAY CHƯA
        for (int i = 0; i <= activeBank.getCustomers().size() - 1; i++) {
            boolean isExistedAccount = activeBank.getCustomers().get(i).isAccountExisted(accNumbers);
            while (isExistedAccount) {
                System.out.println("So TK da bi trung");
                System.out.println("Vui long nhap lai so TK gom 6 so: ");
                accNumbers = sc.next();
                isExistedAccount = activeBank.getCustomers().get(i).isAccountExisted(accNumbers);
            }
        }
        System.out.println("Nhap so du: ");
        balance = sc.nextDouble(); // NHẬP SỐ DƯ CHO TÀI KHOẢN SAVINGS
        while (balance < 50000.0) {
            System.out.println("So du toi thieu 50.000đ: ");
            System.out.println("Vui long nhap lai: ");
            balance = sc.nextDouble();
        }

        SavingsAccount savingsAccount = new SavingsAccount(accNumbers, balance, "SAVINGS"); // TẠO NEW SAVINGS ACCONUT
        activeBank.addAccount(CUSTOMER_ID, savingsAccount); // THÊM SAVINGS ACCOUNT VÀO LIST ACCOUNT
        System.out.println("Da them tai khoan thanh cong!!");
    }

    // CHỨC NĂNG 3: THÊM TÀI KHOẢN LOAN CHO KHÁCH HÀNG
    public static void addLoanAccount(){
        String accNumbers;
        double limit;

        System.out.println("Nhap so TK gom 6 chu so: ");
        accNumbers = sc.next();
        Pattern pattern = Pattern.compile("^\\d{6}$");
        while (!(pattern.matcher(accNumbers).find())) {
            System.out.println("So TK khong dung \nVui long nhap lai: ");
            accNumbers = sc.next();
        }
        // KIỂM TRA SỐ TK NGƯỜI DÙNG NHẬP VÀO VỚI DANH SÁCH TRONG BANK, ĐÃ TỒN TẠI HAY CHƯA
        for (int i = 0; i <= activeBank.getCustomers().size() - 1; i++) {
            boolean isExistedAccount = activeBank.getCustomers().get(i).isAccountExisted(accNumbers);
            while (isExistedAccount) {
                System.out.println("So TK da bi trung");
                System.out.println("Vui long nhap lai so TK gom 6 so: ");
                accNumbers = sc.next();
                isExistedAccount = activeBank.getCustomers().get(i).isAccountExisted(accNumbers);
            }
        }
        System.out.println("Nhap han muc TK: ");
        limit = sc.nextDouble();
        while (limit > 100000000.0) {
            System.out.println("Han muc toi da la 100.000.000đ: ");
            System.out.println("Vui long nhap lai: ");
            limit = sc.nextDouble(); // THÊM HẠN MỨC CHO TK LOAN, TỐI ĐA 100.000.000
        }

        LoanAccount loanAccount = new LoanAccount(accNumbers, limit, "LOAN"); // TẠO NEW LOAN ACCOUNT
        activeBank.addAccount(CUSTOMER_ID, loanAccount); // THÊM LOAN ACCOUNT CHO LIST ACCOUNT
        System.out.println("Da them tai khoan thanh cong!!");
    }

    // CHỨC NĂNG 4: RÚT TIỀN
    public static void withDraw() throws InterruptedException {
        String accNumbers;
        double amount;

        System.out.println("Nhap so TK: ");
        accNumbers = sc.next();
        // NHẬP VÀO SỐ TK CẦN RÚT, KIỂM TRA SỐ TK ĐÃ CÓ HAY CHƯA
        for (int i = 0; i <= activeBank.getCustomers().size() - 1; i++) {
            boolean isExistedAccount = activeBank.getCustomers().get(i).isAccountExisted(accNumbers);
            while (!isExistedAccount) {
                System.out.println("So TK khong ton tai");
                System.out.println("Vui long nhap lai so TK: ");
                accNumbers = sc.next();
                isExistedAccount = activeBank.getCustomers().get(i).isAccountExisted(accNumbers);
            }
        }

        System.out.println("Nhap so tien can rut: ");
        amount = sc.nextDouble();
        activeBank.withdraw(CUSTOMER_ID, accNumbers, amount); // GỌI HÀM WITHDRAW TRONG DIGITAL BANK
    }

    public static void allTransactions(){
        Customer customer = activeBank.getCustomerById(CUSTOMER_ID);
        System.out.println("+----------+----------------------+--------+");
        customer.displayInformation();

        String accNumbers;
        System.out.println("Nhap so TK muon sao ke: ");
        accNumbers = sc.next();
        Pattern pattern = Pattern.compile("^\\d{6}$"); // ĐỊNH DẠNG SỐ TK GỒM 6 SỐ
        while (!(pattern.matcher(accNumbers).find())) {
            System.out.println("So TK khong dung \nVui long nhap lai: ");
            accNumbers = sc.next();
        }
        for (int i = 0; i <= activeBank.getCustomers().size() - 1; i++) {
            boolean isExistedAccount = activeBank.getCustomers().get(i).isAccountExisted(accNumbers);
            while (!isExistedAccount) {
                System.out.println("So TK khong ton tai");
                System.out.println("Vui long nhap lai so TK: ");
                accNumbers = sc.next();
                isExistedAccount = activeBank.getCustomers().get(i).isAccountExisted(accNumbers);
            }
        }
        Account account = customer.getAccountByAccountNumber(accNumbers);
        account.displayTransactions();
    }

    /* HÀM XỬ LÝ TÍNH NĂNG THEO INPUT NGƯỜI DÙNG NHẬP VÀO */

    public static void handleFeature() {
        int option;
        try {
            while (true) {
                subMenu();
                option = sc.nextInt();
                switch (option) {
                    case 1 -> {
                        showCustomer();
                    }
                    case 2 -> {
                        addSavingsAccount();
                    }
                    case 3 -> {
                        addLoanAccount();
                    }
                    case 4 -> {
                        withDraw();
                    }
                    case 5 -> {
                        allTransactions();
                    }
                    case 0 -> {
                        System.out.println("Cam on!! Hen gap lai.");
                        System.exit(0);
                    }
                    default -> {
                        System.out.println("----------------");
                        System.out.println("So ban nhap khong dung, vui long chon lai!");
                    }
                }
            }
        } catch (InterruptedException e) {
            System.out.println("----------------");
            System.out.println("Sai cu phap, vui long nhap lai!");
            handleFeature();
        }
    }

    private static void subMenu() {
        System.out.println("+----------+----------------------+--------+");
        System.out.println("| NGAN HANG SO | " + AUTHOR + "@" + VERSION + "            |");
        System.out.println("+----------+----------------------+--------+");
        System.out.println(" 1. Thong tin khach hang");
        System.out.println(" 2. Them tai khoan ATM");
        System.out.println(" 3. Them tai khoan tin dung");
        System.out.println(" 4. Rut tien");
        System.out.println(" 5. Lich su giao dich");
        System.out.println(" 0. Thoat");
        System.out.println("+----------+----------------------+--------+");
        System.out.println("Chuc nang: ");
    }
}
