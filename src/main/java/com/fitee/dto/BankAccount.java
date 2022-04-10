package com.fitee.dto;

import javax.persistence.Embeddable;

@Embeddable
public class BankAccount {

    private String accountName;
    private String accountNumber;

    public BankAccount(String accountName, String accountNumber) {
        this.accountName = accountName;
        this.accountNumber = accountNumber;
    }

    public BankAccount() {

    }
}
