package vn.funix.fx19878.java.asm4.model;

import vn.funix.fx19878.java.asm4.dao.AccountDAO;
import vn.funix.fx19878.java.asm4.dao.CustomerDAO;
import vn.funix.fx19878.java.asm4.service.TextFileService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class DigitalBank extends Bank {

    public DigitalBank() {
        super();
    }

    /* HÀM HIỆN THỊ TẤT CẢ KHÁCH HÀNG TRONG FILE CUSTOMERS.DAT */
    public void showCustomers() {
        if (CustomerDAO.list().isEmpty()) {
            System.out.println("Chua co khach hang nao trong danh sach!");
        } else {
            for (Customer customer : CustomerDAO.list()) {
                customer.displayInformation();
            }
        }
    }

    public Customer getCustomerById(List<Customer> customers, String customerId) {
        for (Customer customer : customers) {
            if (Objects.equals(customer.getCustomerId(), customerId)) {
                return customer;
            }
        }
        return null;
    }

    /* HÀM THÊM DANH SÁCH KHÁCH HÀNG VÀO FILE CUSTOMERS.DAT */
    public void addCustomer(String fileName) {
        List<List<String>> customerListFromFile = TextFileService.ReadFile(fileName); // Lấy ra danh sách KH từ file customers.txt
        List<Customer> customerListFromDataBase = CustomerDAO.list();
        if (customerListFromFile.isEmpty()) {
            System.out.println("Khong the them khach hang");
        }
        for (List<String> customerFromFile : customerListFromFile) {
            Customer newCustomer = new Customer(customerFromFile);
            try {
                if (Customer.validateCustomerId(customerFromFile.get(0))) {
                    if (isCustomerExisted(customerFromFile.get(0))) {
                        System.out.println("Khach hang " + customerFromFile.get(0) + " da ton tai trong danh sach.");
                    } else {
                        customerListFromDataBase.add(newCustomer);
                        System.out.println("Da them khach hang " + customerFromFile.get(0) + " thanh cong");
                    }
                } // Kiểm tra tính hợp lệ và KH đã tồn tại trong file hay chưa
            } catch (CustomerIdNotValidException e) {
                System.out.println(customerFromFile.get(0) + " la " + e.getMessage() + ". Khong the them vao danh sach");
                // Bắt exception mã số KH không hợp lệ với định nghĩa CustomerIdNotValidException
            }
        }
        CustomerDAO.save(customerListFromDataBase); // Thêm List<> KH vào file Customers.dat
    }

    /* HÀM KIỂM TRA MÃ SỐ KH VÀ THÊM TÀI KHOẢN VÀO ĐÚNG MÃ SỐ KH */
    public void addSavingAccount(String customerId, Account account) {
        for (Customer customer : CustomerDAO.list()) {
            if (customer.getCustomerId().equals(customerId)) {
                customer.addAccount(account);
            }
        }
    }


    /* Hàm kiểm tra mã số KH, hiện thị thông tin KH và thực hiện chuyển tiền qua đối tượng customer */
    public void transfers(Scanner scanner, String customerId) {
        for (Customer customer : CustomerDAO.list()) {
            if (Objects.equals(customer.getCustomerId(), customerId)) {
                customer.displayInformation(); // hiện thị thông tin KH và tài khoản của KH
                customer.transfer(scanner); // thực hiện giao dịch chuyển tiền
            }
        }
    }

    /* Hàm kiểm tra mã số KH, hiện thị thông tin KH và thực hiện rút tiền qua đối tượng customer */
    public void withdraw(Scanner scanner, String customerId) {
        for (Customer customer : CustomerDAO.list()) {
            if (Objects.equals(customer.getCustomerId(), customerId)) {
                customer.displayInformation(); // hiện thị thông tin KH và tài khoản của KH
                customer.withdraw(scanner); // thực hiện giao dịch rút tiền
            }
        }
    }
}
