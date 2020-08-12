package com.RentACar.database.adapter;

import com.RentACar.database.model.Car;
import com.RentACar.database.model.Owner;
import com.RentACar.database.model.Reservation;
import com.RentACar.database.model.User;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JsonParser {
    private User dataUser;
    private Owner dataOwner;

    public User parseUserData(String dataRcv) throws JSONException {

        JSONObject jsonObject = new JSONObject(dataRcv);
        /* ====================GET User Parameter dari JSON=================== */

        String name = jsonObject.isNull("name") ? null :  jsonObject.getString("name");
        String username = jsonObject.isNull("username") ? null : jsonObject.getString("username");
        String password = jsonObject.isNull("password") ? null : jsonObject.getString("password");
        int login = jsonObject.isNull("login") ? 0 : jsonObject.getInt("login");
        String status = jsonObject.isNull("status") ? null : jsonObject.getString("status");
        String task = jsonObject.isNull("task") ? null : jsonObject.getString("task");
        String phone = jsonObject.isNull("phone") ? null : jsonObject.getString("phone");
        this.dataUser = new User(name, username, password, login, status, task, phone);

        /* ================GET DATA FOR CAR FOR RESERVATION================== */
        JSONObject carsObj = jsonObject.isNull("cars") ? null :  jsonObject.getJSONObject("cars");
        System.out.println(carsObj);
        Car car;
        Reservation reservation;
        if (!jsonObject.isNull("cars")) {
            /* ==========================DATA CAR FOR USER==================================== */
            String carBrand = carsObj.isNull("carBrand") ? null : carsObj.getString("carBrand");
            String carModel = carsObj.isNull("carModel") ? null : carsObj.getString("carModel");
            String location = carsObj.isNull("location") ? null : carsObj.getString("location");
            double carPrice = carsObj.isNull("carPrice") ? 0 : carsObj.getDouble("carPrice");
            int carStatus = carsObj.isNull("status") ? 0 : carsObj.getInt("status");
            int carIdUser = carsObj.isNull("id_user") ? 0 : carsObj.getInt("id_user");
            int carIdOwner = carsObj.isNull("id_owner") ? 0 : carsObj.getInt("id_owner");
            int carId = carsObj.isNull("id_car") ? 0 : carsObj.getInt("id_car");
            car = new Car(carId, carIdOwner, username, carBrand, carModel, location, carPrice, carStatus);

            /* =====================ADDITIONAL DATA FOR RESERVATION=================================== */
            int carIdTrans = carsObj.isNull("id_trans") ? 0 : carsObj.getInt("id_trans");
            int duration = carsObj.isNull("duration") ? 0 : carsObj.getInt("duration");
            String payment = carsObj.isNull("payment") ? null : carsObj.getString("payment");
            String sStart = carsObj.isNull("start_date") ? null : carsObj.getString("start_date");
            String sEnd = carsObj.isNull("end_date") ? null : carsObj.getString("end_date");
            reservation = new Reservation(carId, carBrand, carModel, location, payment, duration, carPrice, sStart, sEnd);
            reservation.setStatus("unpaid"); // BY DEFAULT PAYMENT STATUS WILL BE "UNPAID"
            reservation.setName_user(name);
            reservation.setId_user(carIdUser);
            reservation.setId_trans(carIdTrans);
            reservation.setTotalPrice();

            this.dataUser.setCar(car);
            this.dataUser.setReservation(reservation);
            System.out.println(this.dataUser.getCar());
            System.out.println(this.dataUser.getReservation());
        }
        return this.dataUser;
    }

    public Owner parseOwnerData(String dataRcv) throws JSONException {

        JSONObject jsonObject = new JSONObject(dataRcv);
        /* =====================GET Owner Parameter FROM JSON============================ */

        String name = jsonObject.isNull("name") ? null :  jsonObject.getString("name");
        String username = jsonObject.isNull("username") ? null : jsonObject.getString("username");
        String password = jsonObject.isNull("password") ? null : jsonObject.getString("password");
        int login = jsonObject.isNull("login") ? 0 : jsonObject.getInt("login");
        String status = jsonObject.isNull("status") ? null : jsonObject.getString("status");
        String task = jsonObject.isNull("task") ? null : jsonObject.getString("task");
        this.dataOwner = new Owner(name, username, password, status, task, login);
        System.out.println(this.dataOwner.getTask());

        /* =============================GET DATA FOR CAR FOR RESERVATION============================ */
        JSONObject carsObj = jsonObject.isNull("cars") ? null :  jsonObject.getJSONObject("cars");
        System.out.println(carsObj);
        Car car;
        if (!jsonObject.isNull("cars")) {
            /* =================================DATA CAR FOR OWNER================================== */
            String carBrand = carsObj.isNull("carBrand") ? null : carsObj.getString("carBrand");
            String carModel = carsObj.isNull("carModel") ? null : carsObj.getString("carModel");
            String location = carsObj.isNull("location") ? null : carsObj.getString("location");
            double carPrice = carsObj.isNull("carPrice") ? 0 : carsObj.getDouble("carPrice");
            int carStatus = carsObj.isNull("status") ? 0 : carsObj.getInt("status");
            int carIdOwner = carsObj.isNull("id_owner") ? 0 : carsObj.getInt("id_owner");
            int carId = carsObj.isNull("id_car") ? 0 : carsObj.getInt("id_car");
            car = new Car(carId, carIdOwner, username, carBrand, carModel, location, carPrice, carStatus);

            this.dataOwner.setCar(car);
            System.out.println(this.dataOwner);
//            System.out.println(this.dataUser.getCar());
        }
        return this.dataOwner;
    }
}

//            /* format date */
//            SimpleDateFormat format=new SimpleDateFormat("dd-MM-yyyy");
//            Date start = null, end = null;
//            try {
//                start = format.parse(sStart);
//                end = format.parse(sEnd);
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }

//                System.out.println(this.dataOwner.getName());
//                System.out.println(this.dataOwner.getUsername());
//                System.out.println(this.dataOwner.getPassword());
//                System.out.println(this.dataOwner.getLogin());
//                System.out.println(this.dataOwner.getStatus());
//                System.out.println(this.dataOwner.getTask());
//
//                        System.out.println(this.dataOwner.getCar().getCar());
//                        System.out.println(this.dataOwner.getCar().getLocation());
//                        System.out.println(this.dataOwner.getCar().getCarPrice());
//                        System.out.println(this.dataOwner.getCar().getStatus());