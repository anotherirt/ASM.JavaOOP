package vn.funix.fx19878.java.asm4.service;

import vn.funix.fx19878.java.asm4.model.Account;

public interface ITransfer  {
    boolean transfers(Account receivedAccount, double amount);

}
