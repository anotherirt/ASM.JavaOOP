package vn.funix.fx19878.java.asm4.dao;


import vn.funix.fx19878.java.asm4.model.Customer;
import vn.funix.fx19878.java.asm4.service.BinaryFileService;

import java.io.IOException;
import java.util.List;

public class CustomerDAO {
    private final static String FILE_PATH = "store\\customers.dat";

    public static void save(List<Customer> customers){
        BinaryFileService.writeFile(FILE_PATH, customers);
    }

    public static List<Customer> list() {
        return BinaryFileService.readFile(FILE_PATH);
    }
}
