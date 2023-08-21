package vn.funix.fx19878.java.asm4;

import vn.funix.fx19878.java.asm4.dao.AccountDAO;
import vn.funix.fx19878.java.asm4.dao.CustomerDAO;
import vn.funix.fx19878.java.asm4.model.Customer;
import vn.funix.fx19878.java.asm4.model.DigitalBank;
import vn.funix.fx19878.java.asm4.model.SavingsAccount;
import vn.funix.fx19878.java.asm4.model.Transaction;
import vn.funix.fx19878.java.asm4.model.TransactionType;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;
import java.util.regex.Pattern;

public class ASM4 {
    public static final String AUTHOR = "FX19878";
    public static final String VERSION = "V4.0.0";
    private static final DigitalBank activeBank = new DigitalBank();
    public static final Scanner sc = new Scanner(System.in);
    public static final Scanner scanner = new Scanner(System.in);

    /* HÀM MAIN */
    public static void main(String[] args) {
        handleFeature();
    }

    /* HÀM HIỆN THỊ MENU ĐỂ NGƯỜI DỤNG CHỌN CHỨC NĂNG */
    public static void menu() {
        System.out.println("+--------+-----------------+----------+");
        System.out.println("|Digital Bank | " + AUTHOR + "@" + VERSION + "        |");
        System.out.println("+--------+-----------------+----------+");
        System.out.println("| 1. Xem danh sach khach hang         |");
        System.out.println("| 2. Nhap danh sach khach hang        |");
        System.out.println("| 3. Them tai khoan ATM               |");
        System.out.println("| 4. Chuyen tien                      |");
        System.out.println("| 5. Rut tien                         |");
        System.out.println("| 6. Tra cuu lich su giao dich        |");
        System.out.println("| 0. Thoat                            |");
        System.out.println("+--------+-----------------+----------+");
        System.out.println("Chuc nang: ");
    }

    /* HÀM ĐIỀU KHIỂN XỬ LÝ TÍNH NĂNG CHƯƠNG TRÌNH */
    public static void handleFeature() {
        Scanner scanner1 = new Scanner(System.in);
        int option;
        try {
            while (true) {
                menu();
                option = scanner1.nextInt();
                switch (option) {
                    case 1 -> {
                        showCustomersList();
                    }
                    case 2 -> {
                        addCustomersList();
                    }
                    case 3 -> {
                        addSavingsAccount();
                    }
                    case 4 -> {
                        transfer();
                    }
                    case 5 -> {
                        withdraw();
                    }
                    case 6 -> {
                        showTransactionsList();
                    }
                    case 0 -> {
                        System.out.println("Cam on!! Hen gap lai.");
                        System.exit(0);
                    }
                    default -> {
                        System.out.println("So ban nhap khong dung, vui long chon lai!");
                        System.out.println("+----------+----------------------+");
                    }
                }
            }
        } catch (Exception InputMismatchException) {
            System.out.println("Sai cu phap, vui long nhap lai!");
            System.out.println("+----------+----------------------+");
            handleFeature();
        }
    }

    /* HÀM HIỂN THỊ DANH SÁCH KHÁCH HÀNG */
    public static void showCustomersList() {
        activeBank.showCustomers();
    }

    /* HÀM THÊM DANH SÁCH KHÁCH HÀNG CHO TRƯỚC VÀO DANH SÁCH DỮ LIỆU */
    public static void addCustomersList() {
        System.out.println("Nhap duong dan den file: ");
        String filePath = scanner.nextLine();
        activeBank.addCustomer(filePath); // CHỈ ĐỌC Đ/C: "store/customers.txt"
    }

    /* HÀM THÊM ACCOUNT CHO KHÁCH HÀNG TRONG DANH SÁCH DỮ LIỆU */
    public static void addSavingsAccount() {
        String customerId;
        String accNumbers;
        double balance;
        System.out.println("Nhap ma so khach hang: ");
        customerId = sc.next(); // Nhập vào số cccd của khách hàng

        boolean isExisted = activeBank.isCustomerExisted(customerId); // Kiểm tra cccd có hợp lệ
        while (!isExisted) {
            System.out.println("Khong tim thay khach hang " + customerId + ", tac vu khong thanh cong");
            System.out.println("Vui long nhap lai hoac nhap 'No' de thoat: ");
            customerId = sc.next();
            if (customerId.equals("No") || customerId.equals("no")) {
                System.out.println("Cam on!! Hen gap lai.");
                System.exit(0);
            }
            isExisted = activeBank.isCustomerExisted(customerId);
        }

        System.out.println("Nhap so TK gom 6 so: ");
        accNumbers = sc.next(); // Nhập vào số tài khoản

        Pattern pattern = Pattern.compile("^\\d{6}$");
        while (!(pattern.matcher(accNumbers).find())) {
            System.out.println("So TK khong dung \nVui long nhap lai: ");
            accNumbers = sc.next(); // Kiểm tra số Tk có đúng 6 số
        }
        for (Customer customer : CustomerDAO.list()) {
            boolean isExistedAccount = customer.isAccountExisted(accNumbers); // Kiểm tra số tài khoản có bị trùng không
            while (isExistedAccount) {
                System.out.println("So TK da bi trung");
                System.out.println("Vui long nhap lai so TK gom 6 so: ");
                accNumbers = sc.next();
                isExistedAccount = customer.isAccountExisted(accNumbers);
            }
        }

        System.out.println("Nhap so du tai khoan >= 50,000vnd: ");
        balance = sc.nextDouble(); // Nhập vào số dư ban đầu cho TK

        while (balance < 50000.0) {
            System.out.println("So du toi thieu 50.000vnd: ");
            System.out.println("Vui long nhap lai: ");
            balance = sc.nextDouble(); // Kiểm tra số dư tối thiểu là 50.000
        }

        SavingsAccount newSavingsAccount = new SavingsAccount(customerId, accNumbers, balance, "SAVINGS"); // Tạo new Account với số CCCD, số TK và số dư đã nhậpp
        activeBank.addSavingAccount(customerId, newSavingsAccount); // Thêm TK vừa tạo vào file thông qua đối tượng Bank
        System.out.println("Tao tai khoan thanh cong");
        Transaction transaction = new Transaction(customerId, accNumbers, balance, true, TransactionType.DEPOSIT);
        newSavingsAccount.createTransaction(transaction); // Tạo giao dịch thêm mới TK
    }

    /* HÀM THỰC HIỆN CHUYỂN TIỀN GIỮA CÁC TÀI KHOẢN */
    public static void transfer() {
        String customerId;
        System.out.println("Nhap ma so khach hang: ");
        customerId = sc.next(); // Nhập số CCCD

        boolean isExisted = activeBank.isCustomerExisted(customerId); // Kiểm tra CCCD này có trong file hay không
        while (!isExisted) {
            System.out.println("Khong tim thay khach hang " + customerId + ", tac vu khong thanh cong");
            System.out.println("Vui long nhap lai hoac nhap 'No' de thoat: ");
            customerId = sc.next();
            if (customerId.equals("No") || customerId.equals("no")) {
                System.out.println("Cam on!! Hen gap lai.");
                System.exit(0);
            }
            isExisted = activeBank.isCustomerExisted(customerId);
        }
        activeBank.transfers(new Scanner(System.in), customerId); // thực hiện giao dịch chuyển tiền thông qua đối tượng Bank theo mã số khách hàng đã nhập
    }

    /* HÀM THỰC HIỆN RÚT TIỀN TỪ TÀI KHOẢN  */
    public static void withdraw() {
        String customerId;
        System.out.println("Nhap ma so khach hang: ");
        customerId = sc.next(); // Nhập vào CCCD khách hàng muốn rút tiền

        boolean isExisted = activeBank.isCustomerExisted(customerId); // Kiểm tra số CCCD có tồn tại trong file
        while (!isExisted) {
            System.out.println("Khong tim thay khach hang " + customerId + ", tac vu khong thanh cong");
            System.out.println("Vui long nhap lai hoac nhap 'No' de thoat: ");
            customerId = sc.next();
            if (customerId.equals("No") || customerId.equals("no")) {
                System.out.println("Cam on!! Hen gap lai.");
                System.exit(0);
            }
            isExisted = activeBank.isCustomerExisted(customerId);
        }
        activeBank.withdraw(new Scanner(System.in), customerId);// thực hiện giao dịch rút tiền thông qua đối tượng Bank theo mã số khách hàng
    }

    /* HÀM HIỆN THỊ LỊCH SỬ GIAO DỊCH THEO MÃ SỐ KHÁCH HÀNG */
    public static void showTransactionsList() {
        List<Customer> customersList = CustomerDAO.list();
        String customerId;
        System.out.println("Nhap ma so khach hang: ");
        customerId = sc.next(); // Nhập vào số CCCD

        boolean isExisted = activeBank.isCustomerExisted(customerId); // Kiểm tra số CCCD có tồn tại trong file
        while (!isExisted) {
            System.out.println("Khong tim thay khach hang " + customerId + ", tac vu khong thanh cong");
            System.out.println("Vui long nhap lai hoac nhap 'No' de thoat: ");
            customerId = sc.next();
            if (customerId.equals("No") || customerId.equals("no")) {
                System.out.println("Cam on!! Hen gap lai.");
                System.exit(0);
            }
            isExisted = activeBank.isCustomerExisted(customerId);
        }
        Customer customer = activeBank.getCustomerById(customersList, customerId);
        customer.displayTransactionInformation(); // Hiện thị lịch sử giao dịch theo mã số KH vừa nhập vào
    }
}
