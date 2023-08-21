package vn.funix.fx19878.java.asm4.dao;

import vn.funix.fx19878.java.asm4.model.Transaction;
import vn.funix.fx19878.java.asm4.service.BinaryFileService;

import java.io.IOException;
import java.util.List;

public class TransactionDAO {
    private final static String FILE_PATH = "store\\transactions.dat";

    public static void save(List<Transaction> transactions){
        BinaryFileService.writeFile(FILE_PATH, transactions);
    }

    public static List<Transaction> list() {
        return BinaryFileService.readFile(FILE_PATH);
    }
}
