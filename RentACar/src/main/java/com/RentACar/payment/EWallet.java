package com.RentACar.payment;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

public class EWallet {
    private long id;
    private String account;
    private String name;
    private Double balance;

    public EWallet(String account) {
        this.account = account;
    }

    public String getAccount() {
        return account;
    }

    public Double getBalance() {
        return balance;
    }
    public void setBalance(Double balance) {
        this.balance = balance;
    }
}
