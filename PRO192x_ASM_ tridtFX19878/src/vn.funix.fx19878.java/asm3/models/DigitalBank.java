package vn.funix.fx19878.java.asm3.models;

import vn.funix.fx19878.java.asm2.models.Account;
import vn.funix.fx19878.java.asm2.models.Bank;
import vn.funix.fx19878.java.asm2.models.Customer;
import vn.funix.fx19878.java.asm4.dao.CustomerDAO;

import java.util.Objects;
import java.util.Scanner;

public class DigitalBank extends Bank {

    private static final Scanner sc = new Scanner(System.in);

    public DigitalBank() {
    }


    public Customer getCustomerById(String customerId) {
        for (int i = 0; i < getCustomers().size(); i++) {
            if (Objects.equals(getCustomers().get(i).getCustomerId(), customerId)) {
                return getCustomers().get(i);
            }
        }
        return null;
    }


    public void addCustomer(String customerId, String name) {
        boolean isNewCustomer = true;
        for (int i = 0; i < getCustomers().size(); i++) {
            if (Objects.equals(getCustomers().get(i).getCustomerId(), customerId)) {
                isNewCustomer = false;
                break;
            }
        }
        if (isNewCustomer) {
            DigitalCustomer newCustomer = new DigitalCustomer(name, customerId, null);
            this.addCustomer(newCustomer);
        } else {
            System.out.println("Khach hang da dang ky");
        }
    }

    // THÊM ACCOUNT VỚI ĐỐI TƯỢNG CUSTOMER TƯƠNG ỨNG THEO CUSTOMER ID
    public void addAccount(String customerId, Account account) {
        for (Customer customer : getCustomers()) {
            if (Objects.equals(customerId, customer.getCustomerId())) {
                customer.addAccount(account);
                return;
            }
        }
    }

    public void withdraw(String customerId, String number, Double amount) {
        Customer customer = null;
        for (int i = 0; i <= getCustomers().size() - 1; i++) {
            if(Objects.equals(getCustomers().get(i).getCustomerId(), customerId)) {
                customer = getCustomers().get(i);
                break;
            } else {
                System.out.println("Khach hang khong ton tai!!");
            }
        }
        DigitalCustomer digitalCustomer = (DigitalCustomer) customer;
        assert digitalCustomer != null;
        digitalCustomer.withdraw(number, amount); // GỌI HÀM WITHDRAW VỚI ĐỐI TƯỢNG CUSTOMER TƯƠNG ỨNG THEO CUSTOMER ID
    }
}
