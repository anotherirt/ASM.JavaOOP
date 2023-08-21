package vn.funix.fx19878.java.asm2;

import vn.funix.fx19878.java.asm2.models.Account;
import vn.funix.fx19878.java.asm2.models.Bank;
import vn.funix.fx19878.java.asm2.models.Customer;
import vn.funix.fx19878.java.asm2.models.User;

import java.util.*;
import java.util.regex.Pattern;

public class ASM2 {
    public static final String AUTHOR = "FX19878";
    public static final String VERSION = "V2.0.0";

    private static final Bank bank = new Bank();

    public static void main(String[] args) {
        handleFeature();
    }

    /* HÀM XỬ LÝ TÍNH NĂNG THEO OPTION NGƯỜI DÙNG NHẬP VÀO */
    public static void handleFeature() {
        Scanner sc = new Scanner(System.in);
        int option;
        try {
            while (true) {
                subMenu();
                option = sc.nextInt();
                switch (option) {
                    case 1 -> {
                        addCustomers();
                    }
                    case 2 -> {
                        addAccountForCus();
                    }
                    case 3 -> {
                        showCustomersList();
                    }
                    case 4 -> {
                        findCustomer();
                    }
                    case 5 -> {
                        findCusByCusName();
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
        } catch (Exception InputMismatchException) {
            System.out.println("----------------");
            System.out.println("Sai cu phap, vui long nhap lai!");
            handleFeature();
        }

    }

    /* CHỨC NĂNG 1: HÀM THÊM KHÁCH HÀNG VÀO DANH SÁCH KHÁCH HÀNG TRONG CLASS BANK. */
    public static void addCustomers() {
        Scanner sc = new Scanner(System.in);
        String name, cusId;
        System.out.println("Nhap ten khach hang: ");
        name = sc.next();

        System.out.println("Nhap so CCCD: ");
        cusId = sc.next();
        // KIỂM TRA SỐ CCCD NGƯỜI DÙNG NHẬP VÀO CÓ ĐÚNG ĐỊNH DẠNG THEO HÀM VALIDATE TRONG CLASS USER
        while (!User.validateCustomerId(cusId)) {
            System.out.println("So CCCD khong hop le, \nVui long nhap lai hoac nhap 'No' de thoat: ");
            System.out.println("Nhap so CCCD: ");
            cusId = sc.next();
            if (cusId.equals("No") || cusId.equals("no")) {
                System.exit(0);
            }
        }
        // KIỂM TRA SỐ CCCD ĐÃ TỒN TẠI HAY CHƯA
        boolean isExisted = bank.isCustomerExisted(cusId);
        while (isExisted) {
            System.out.println("So CCCD da bi trung");
            System.out.println("Vui long nhap lai so CCCD: ");
            cusId = sc.next();
            isExisted = bank.isCustomerExisted(cusId);
        }

        Customer customer = new Customer(name, cusId, null); //Tạo new Customer với dữ liệu vừa nhập
        bank.addCustomer(customer); //Thêm new customer vào bank
        System.out.println("Them khach hang thanh cong!");
    }

    /* CHỨC NĂNG 2: HÀM THÊM ACCOUNT CHO KHÁCH HÀNG TRONG DANH SÁCH KHÁCH HÀNG TRONG BANK */
    public static void addAccountForCus() {
        Scanner sc = new Scanner(System.in);
        String cusId;
        String accNumbers;
        double balance;
        // YÊU CẦU NGƯỜI DÙNG NHẬP CCCD, KIỂM TRA SỐ CCCD ĐÃ CÓ TRONG BANK HAY CHƯA
        System.out.println("Nhap so CCCD cua khach hang: ");
        cusId = sc.next();
        boolean isExisted = bank.isCustomerExisted(cusId);
        while (!isExisted) {
            System.out.println("So CCCD khong ton tai \nVui long nhap lai: ");
            cusId = sc.next();
            isExisted = bank.isCustomerExisted(cusId);
        }
        // YÊU CẦU NGƯỜI DÙNG NHẬP SỐ TK CÓ 6 CHỮ SỐ, KIỂM TRA NGƯỜI DÙNG NHẬP ĐÚNG HAY CHƯA
        System.out.println("Nhap so TK gom 6 chu so: ");
        accNumbers = sc.next();
        Pattern pattern = Pattern.compile("^\\d{6}$");
        while (!(pattern.matcher(accNumbers).find())) {
            System.out.println("So TK khong dung \nVui long nhap lai: ");
            accNumbers = sc.next();
        }
        // KIỂM TRA SỐ TK NGƯỜI DÙNG NHẬP VÀO VỚI DANH SÁCH TRONG BANK, ĐÃ TỒN TẠI HAY CHƯA
        for (int i = 0; i <= bank.getCustomers().size() - 1; i++) {
            boolean isExistedAccount = bank.getCustomers().get(i).isAccountExisted(accNumbers);
            while (isExistedAccount) {
                System.out.println("So TK da bi trung");
                System.out.println("Vui long nhap lai so TK gom 6 so: ");
                accNumbers = sc.next();
                isExistedAccount = bank.getCustomers().get(i).isAccountExisted(accNumbers);
            }
        }
        // YÊU CẦU NGƯỜI DÙNG NHẬP SỐ DƯ TÀI KHOẢN
        System.out.println("Nhap so du: ");
        balance = sc.nextDouble();
        while (balance < 50000.0) {
            System.out.println("So du toi thieu 50.000đ: ");
            System.out.println("Vui long nhap lai: ");
            balance = sc.nextDouble();
        }

        Account account = new Account(accNumbers, balance, null); // Tạo new account theo dữ liệu vừa nhập
        bank.addAccount(cusId, account); // Thêm new account vào bank
        System.out.println("Them tai khoan thanh cong!");
    }

    /* CHỨC NĂNG 3: HIỆN THỊ CẢ DANH SÁCH KHÁCH HÀNG VỚI HÀM DISPLAYINFORMATION TRONG CLASS CUSTOMER */
    public static void showCustomersList() {
        for (int i = 0; i <= bank.getCustomers().size() - 1; i++) {
            bank.getCustomers().get(i).displayInformation();
        }
    }

    /* CHỨC NĂNG 4: TÌM KHÁCH HÀNG TRONG DANH SÁCH KHÁCH HÀNG THEO SỐ CCCD */
    public static void findCustomer() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Nhap so CCCD khach hang: ");
        String cusId = sc.next();
        while (true) {
            for (int i = 0; i <= bank.getCustomers().size() - 1; i++) {
                if (Objects.equals(cusId, bank.getCustomers().get(i).getCustomerId())) {
                    bank.getCustomers().get(i).displayInformation();
                    return;
                }
            }
            System.out.println("So CCCD khong ton tai \nVui long nhap lai: ");
            cusId = sc.next();
        }
    }

    /* CHỨC NĂNG 5: TÌM KHÁCH HÀNG TRONG DANH SÁCH KHÁCH HÀNG THEO TÊN KHÁCH HÀNG */
    public static void findCusByCusName() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Nhap ten khach hang: ");
        String name = sc.next();
        while (true) {
            for (int i = 0; i <= bank.getCustomers().size() - 1; i++) {
                if (bank.getCustomers().get(i).getName().contains(name) || bank.getCustomers().get(i).getName().equalsIgnoreCase(name)) {
                    bank.getCustomers().get(i).displayInformation();
                    return;
                }
            }
            System.out.println("Khach hang khong ton tai \nVui long nhap lai: ");
            name = sc.next();
        }
    }

    private static void subMenu() {
        System.out.println("+----------+----------------------+--------+");
        System.out.println("| NGAN HANG SO | " + AUTHOR + "@" + VERSION + "            |");
        System.out.println("+----------+----------------------+--------+");
        System.out.println(" 1. Them khach hang");
        System.out.println(" 2. Them tai khoan cho khach hang");
        System.out.println(" 3. Hien thi danh sach khach hang");
        System.out.println(" 4. Tim theo CCCD");
        System.out.println(" 5. Tim theo ten khach hang");
        System.out.println(" 0. Thoat");
        System.out.println("+----------+----------------------+--------+");
        System.out.println("Chuc nang: ");
    }
}
