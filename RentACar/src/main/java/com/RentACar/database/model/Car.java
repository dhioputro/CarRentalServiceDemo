package com.RentACar.database.model;

import lombok.NoArgsConstructor;

@NoArgsConstructor

public class Car {

    private long id_car, id_owner;
    private String username, carBrand, carModel, location;
    private int status;
    private double carPrice;

    public Car(long id_car, long id_owner, String username, String cB, String cM, String loc, double cP, int stat){
        this.id_car = id_car;
        this.id_owner = id_owner;
        this.username = username;
        this.carBrand = cB;
        this.carModel = cM;
        this.location = loc;
        this.carPrice = cP;
        this.status = stat;
    }
    public Car(long id_car,String carBrand, String carModel, String location, double carPrice,  long id_owner, int status) {
        this.id_car = id_car;
        this.id_owner = id_owner;
        this.carBrand = carBrand;
        this.carModel = carModel;
        this.location = location;
        this.status = status;
        this.carPrice = carPrice;
    }

    public long getId_car() {
        return id_car;
    }
    public long getId_owner() {
        return id_owner;
    }
    public String getUsername() {
        return username;
    }
    public String getCarBrand() {
        return carBrand;
    }
    public String getCarModel() {
        return carModel;
    }
    public String getCar() {
        return getCarBrand() + "-" + getCarModel();
    }
    public String getLocation() {
        return location;
    }
    public double getCarPrice() {
        return carPrice;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
}
