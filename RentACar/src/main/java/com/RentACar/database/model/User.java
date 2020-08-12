package com.RentACar.database.model;

public class User {

    private long id_user;
    private String name, username, password, status, task, phone;
    private int login;
    private Car car;
    private Reservation reservation;

    public User(String name, String username, String password, int login, String status, String task, String phone) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.status = status;
        this.login = login;
        this.task = task;
        this.phone = phone;
    }

    public long getId_user() {
        return id_user;
    }
    public void setId_user(long id_user) {
        this.id_user = id_user;
    }

    public String getName() {
        return name;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public int getLogin() {
        return login;
    }
    public String getStatus() {
        return status;
    }
    public String getTask() {
        return task;
    }
    public String getPhone() {
        return phone;
    }

    public Car getCar() {
        return car;
    }
    public void setCar(Car car) {
        this.car = car;
    }

    public Reservation getReservation() {
        return reservation;
    }
    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

}
