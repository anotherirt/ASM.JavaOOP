package vn.funix.fx19878.java.asm4.model;

public class CustomerIdNotValidException extends RuntimeException {
    public CustomerIdNotValidException(String massage) {
        super("Id khong hop le");
    }
}
