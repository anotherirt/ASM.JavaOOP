package vn.funix.fx19878.java.asm4.model;


import vn.funix.fx19878.java.asm4.dao.CustomerDAO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Bank {
    private final String bankId;
    private final String bankName = "AnotherBank";
    private final List<Customer> customers;

    public Bank() {
        customers = new ArrayList<Customer>();

        this.bankId = String.valueOf(UUID.randomUUID());
    }

    public String getBankId() {
        return bankId;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void addCustomer(Customer newCustomer) {
        customers.add(newCustomer);
    }

//    public void addAccount(String customerId, Account account) throws IOException {
//        for (Customer customer : CustomerDAO.list()) {
//            if (Objects.equals(customerId, customer.getCustomerId())) {
//                customer.addAccount(account);
//                return;
//            }
//        }
//    }

//    public boolean isCustomerExisted(String customerId) {
//        for (Customer customer : CustomerDAO.list()) {
//            if (Objects.equals(customerId, customer.getCustomerId())) {
//                return true;
//            }
//        }
//        return false;
//    }

    public boolean isCustomerExisted(String customerId) {
        return CustomerDAO.list()
                .stream()
                .anyMatch(customer -> Objects.equals(customerId, customer.getCustomerId()));
    }

}
