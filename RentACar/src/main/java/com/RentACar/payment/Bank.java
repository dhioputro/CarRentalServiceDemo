package com.RentACar.payment;

import lombok.NoArgsConstructor;

public class Bank {
    private long ID, id_trans;
    private String va, payStatus = "unpaid";

    public Bank(){
    }

    public Bank(String va, long id_trans) {
        this.va = va;
        this.id_trans = id_trans;
    }

    public long getId() {
        return ID;
    }
    public void setId(long id) {
        this.ID = id;
    }

    public long getId_trans() {
        return id_trans;
    }
    public void setId_trans(long id_trans) {
        this.id_trans = id_trans;
    }

    public String getVa() {
        return va;
    }
    public void setVa(String va) {
        this.va = va;
    }

    public String getPayStatus() {
        return payStatus;
    }
    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }
}
