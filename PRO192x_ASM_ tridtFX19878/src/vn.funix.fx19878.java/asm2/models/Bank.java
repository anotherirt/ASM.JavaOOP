package vn.funix.fx19878.java.asm2.models;
import java.util.*;
import java.util.ArrayList;

public class Bank {
    private final String id ;
    private final List<Customer> customers;

    public Bank() {
        customers = new ArrayList<Customer>();

        this.id=String.valueOf(UUID.randomUUID());
    }

    public String getId() {
        return id;
    }

    public List<Customer> getCustomers(){
        return customers;
    }

    public void addCustomer(Customer newCustomer) {
        customers.add(newCustomer);
    }

    public void addAccount(String customerId, Account account){
        for (Customer customer : customers) {
            if (Objects.equals(customerId, customer.getCustomerId())) {
                customer.addAccount(account);
                return;
            }
        }
    }

    public boolean isCustomerExisted(String customerId) {
        for (Customer customer : customers) {
            if (Objects.equals(customerId, customer.getCustomerId())) {
                return true;
            }
        }
        return false;
    }


}
