package com.RentACar.database.model;

public class Owner {

    private long id_owner;
    private String name, username, password, status, task;
    private int login;
    private Car car;

    public Owner(String name, String username, String password, String status, String task, int login) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.status = status;
        this.task = task;
        this.login = login;
    }

    public long getId_owner() {
        return id_owner;
    }
    public void setId_owner(int id_owner) {
        this.id_owner = id_owner;
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
    public String getStatus() {
        return status;
    }
    public String getTask() {
        return task;
    }
    public int getLogin() {
        return login;
    }

    public Car getCar() {
        return car;
    }
    public void setCar(Car car) {
        this.car = car;
    }
}
