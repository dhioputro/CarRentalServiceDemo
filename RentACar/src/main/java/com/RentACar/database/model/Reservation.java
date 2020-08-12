package com.RentACar.database.model;

import com.RentACar.payment.Bank;
import com.RentACar.payment.EWallet;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor

public class Reservation {
    private long id_trans, id_user, id_car;
    private String name_user, carBrand, carModel, location, payment, status;
    private double duration, carPrice, totalPrice;
    private String start_date, end_date;
    private EWallet eWallet;
    private Bank bank;

    public Reservation(long id_car, String carBrand, String carModel, String location, String payment,
                       double duration, double carPrice, String start_date, String end_date) {
        this.id_car = id_car;
        this.carBrand = carBrand;
        this.carModel = carModel;
        this.location = location;
        this.payment = payment;
        this.duration = duration;
        this.carPrice = carPrice;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public long getId_trans() {
        return id_trans;
    }
    public void setId_trans(long id_trans) {
        this.id_trans = id_trans;
    }

    public long getId_user() {
        return id_user;
    }
    public void setId_user(long id_user) {
        this.id_user = id_user;
    }

    public String getName_user() {
        return name_user;
    }
    public void setName_user(String name_user) {
        this.name_user = name_user;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public String getStatus() {
        return status;
    }

    public long getId_car() {
        return id_car;
    }
    public String getCarBrand() {
        return carBrand;
    }
    public String getCarModel() {
        return carModel;
    }
    public String getLocation() {
        return location;
    }
    public String getPayment() {
        return payment;
    }
    public double getDuration() {
        return duration;
    }
    public double getCarPrice() {
        return carPrice;
    }
    public String getStart_date() {
        return start_date;
    }
    public String getEnd_date() {
        return end_date;
    }

    public double getTotalPrice() {
        return totalPrice;
    }
    public void setTotalPrice() {
        this.totalPrice = this.duration*this.carPrice;
    }

    public EWallet geteWallet() {
        return eWallet;
    }
    public void seteWallet(EWallet eWallet) {
        this.eWallet = eWallet;
    }

    public Bank getBank() {
        return bank;
    }
    public void setBank(Bank bank) {
        this.bank = bank;
    }
}
