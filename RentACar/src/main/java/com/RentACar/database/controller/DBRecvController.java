package com.RentACar.database.controller;

import com.RentACar.Message;
import com.RentACar.database.adapter.JsonParser;
import com.RentACar.database.model.Car;
import com.RentACar.database.model.Owner;
import com.RentACar.database.model.Reservation;
import com.RentACar.database.model.User;
import com.RentACar.database.queue.RabbitSendDB;

import com.RentACar.database.services.OwnerDBService;
import com.RentACar.database.services.ReservationDBService;
import com.RentACar.database.services.UserDBService;
import org.json.JSONException;
import org.json.JSONObject;
import com.google.gson.Gson;



import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Objects;

public class DBRecvController {
    private User userData;
    private Owner ownerData;

    RabbitSendDB sendToRabbit = new RabbitSendDB();
    JsonParser parseData = new JsonParser();
    Gson json = new Gson();

    UserDBService userService = new UserDBService();
    OwnerDBService ownerService = new OwnerDBService();
    ReservationDBService reservationService = new ReservationDBService();

    public DBRecvController(String status, String dataRcv) {

        if(status.equals("UserAccess")) {
            try {
                this.userData = parseData.parseUserData(dataRcv);
            } catch (JSONException e) {
                System.out.println("JSON parsing error : " +e);
            } finally {
                switch (Objects.requireNonNull(this.userData).getTask().toLowerCase()) {
                    case "regis":
                        userRegisAct();
                        break;
                    case "login":
                        userLoginAct();
                        break;
                    case "logout":
                        userLogoutAct();
                        break;
                     case "search_car":
                        userSearchAct();
                        break;
                }
            }

        } else if (status.equals("OwnerAccess")) {
            try {
                this.ownerData = parseData.parseOwnerData(dataRcv);
                System.out.println(this.ownerData);
            } catch (JSONException e) {
                System.out.println("JSON parsing error : " +e);
            } finally {
                switch (Objects.requireNonNull(this.ownerData).getTask().toLowerCase()) {
                    case "regis":
                        ownerRegisAct();
                        break;
                    case "login":
                        ownerLoginAct();
                        break;
                    case "logout":
                        ownerLogoutAct();
                        break;
                     case "add_car":
                        ownerAddCar();
                        break;
                     case "update_car":
                        ownerUpdateCar();
                        break;
                     case "remove_car":
                        ownerRemoveCar();
                        break;
                }
            }
        } else if(status.equals("UserReservation")) {
            try {
                this.userData = parseData.parseUserData(dataRcv);
            } catch (JSONException e) {
                System.out.println("JSON parsing error : " +e);
            } finally {
                switch (Objects.requireNonNull(this.userData).getTask().toLowerCase()) {
                    case "reservation":
                        userReservation();
                        break;
                    case "check_reservation":
                        checkReservation();
                        break;
                }
            }
        }
    }
    //===================================================================//
    /* FEEDBACK REGISTRATION DATA */
    private void userRegisAct() {
        String posResponse = "Registration Success! Hi "+this.userData.getName().toUpperCase()+ ", welcome to Rent-A-Car Rental Service. You have been registered as a Customer";
        String negResponse = "Sorry you have failed to register, please check your data \n" +this.userData;

        try {
            if (userService.userRegis(this.userData) > 0) {
                System.out.println("Registration Account Success!");
                sendFeedback(json.toJson(new Message(posResponse, "200")));
            } else {
                System.out.println("Someone failed to register!");
                sendFeedback(json.toJson(new Message(negResponse, "200")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void ownerRegisAct() {
        String posResponse = "Registration Success! Hi "+this.ownerData.getName().toUpperCase()+ ", welcome to Rent-A-Car Rental Service. You have been registered as a Lessor";
        String negResponse = "Sorry you have failed to register, please check your data \n" +this.ownerData;

        try {
            if (ownerService.ownerRegisServ(this.ownerData) > 0) {
                System.out.println("Registration Account Success!");
                sendFeedback(json.toJson(new Message(posResponse, "200")));
            } else {
                System.out.println("Failed to login!");
                sendFeedback(json.toJson(new Message(negResponse, "200")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //===================================================================//
    /* FEEDBACK LOGIN DATA */
    private void userLoginAct() {
        String result = userService.userLogin(this.userData);
        String negResponse = "Sorry you are not a user";
        try {
            if (!result.equals("")) {
                System.out.println("Account " +this.userData.getUsername().toUpperCase()+ " is trying to log in");
                sendFeedback(json.toJson(new Message(result, "200")));
            } else {
                System.out.println("Account " +this.userData.getUsername().toUpperCase()+" is trying to log in");
                sendFeedback(json.toJson(new Message(negResponse, "200")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void ownerLoginAct() {
        String result = ownerService.ownerLoginServ(this.ownerData);
        String negResponse = "Sorry you are not a lessor";
        try {
            if (!result.equals("")) {
                System.out.println("Account " +this.ownerData.getUsername().toUpperCase()+ " is trying to log in");
                sendFeedback(json.toJson(new Message(result, "200")));
            } else {
                System.out.println("Account " +this.ownerData.getUsername().toUpperCase()+" is trying to log in");
                sendFeedback(json.toJson(new Message(negResponse, "200")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //===================================================================//
    /* FEEDBACK LOGOUT */
    private void userLogoutAct() {
        String posResponse = "You have been logged out";
        String negResponse = "[ERROR] You are not logged in";

        try {
            if (userService.userLogout(this.userData) > 0) {
                System.out.println("Account " +this.userData.getUsername().toUpperCase()+" has been Logged out");
                sendFeedback(json.toJson(new Message(posResponse, "200")));
            } else {
                System.out.println("Can't validate logout!");
                sendFeedback(json.toJson(new Message(negResponse, "200")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void ownerLogoutAct() {
        String posResponse = "You have been logged out";
        String negResponse = "[ERROR] You are not logged in";

        try {
            if (ownerService.ownerLogoutServ(this.ownerData) > 0) {
                System.out.println("Account " +this.ownerData.getUsername().toUpperCase()+" has been Logged out");
                sendFeedback(json.toJson(new Message(posResponse, "200")));
            } else {
                System.out.println("Can't validate Logout!");
                sendFeedback(json.toJson(new Message(negResponse, "200")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //===================================================================//
    /* FEEDBACK SEARCH CAR  */
    private void userSearchAct() {
        List<Car> searchResult = userService.userSearchByLocation(this.userData);
        String noCarResponse = "No available car in your designated area";
        String notLogin =  "[ERROR] You are not logged in nor a user";

        try {
            if (searchResult != null && !searchResult.isEmpty()){
                System.out.println("Get available car list success");
                sendFeedback(json.toJson(searchResult));
            } else if(searchResult.isEmpty()) {
                System.out.println("Get available car list success");
                sendFeedback(json.toJson(new Message(noCarResponse, "200")));
            } else {
                System.out.println("Failed getting available car");
                sendFeedback(json.toJson(new Message(notLogin, "200")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //===================================================================//
    /* FEEDBACK RESERVATION */
    private void userReservation() {
        String result = reservationService.userCarReserve(this.userData);
        String negResponse = "Sorry, you're not either logged in nor a user";
        try {
            if (!result.equals("") && this.userData.getReservation().getPayment().equals("ewallet")){
                System.out.println("User " +this.userData.getUsername().toUpperCase()+ " is trying to make reservation");
                sendFeedback(result + "\n" + json.toJson(new Message("\n Reservation Success!", "200")));
            } else if(!result.equals("") && this.userData.getReservation().getPayment().equals("bank")){
                System.out.println("User " +this.userData.getUsername().toUpperCase()+ " is trying to make reservation");
                sendFeedback(result + "\n " + json.toJson(new Message("Reservation Success! Please make a payment for VA: 008" +this.userData.getPhone(), "200")));
            } else {
                System.out.println("User " +this.userData.getUsername().toUpperCase()+ " is trying to make reservation");
                sendFeedback(json.toJson(new Message(negResponse, "200")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //===================================================================//
    /* FEEDBACK RESERVATION STATUS */
    private void checkReservation() {
        String result = reservationService.getReservation(this.userData);
        try {
            if (!result.equals("")){
                System.out.println("Fetch reservation status success");
                sendFeedback(result);
            } else {
                System.out.println("Failed fetch reservation status");
                sendFeedback("No reservation data or you are not logged in");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //===================================================================//
    /* FEEDBACK ADD CAR FOR OWNER TO DB */
    private void ownerAddCar() {
        try {
            if (ownerService.addCarServ(this.ownerData) > 0) {
                System.out.println("Car Database Updated");
                sendFeedback(json.toJson(new Message("Your car ads successfully posted!", "200")));
            } else {
                System.out.println("Failed to update");
                sendFeedback(json.toJson(new Message("Failed to proceed, either you are not logged in or you are not a user", "200")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //===================================================================//
    /* FEEDBACK UPDATE CAR IN DB */
    private void ownerUpdateCar() {
        try {
            if (ownerService.updateCarServ(this.ownerData) > 0) {
                System.out.println("Car Database Updated");
                sendFeedback(json.toJson(new Message("Your car ads successfully updated!", "200")));
            } else {
                System.out.println("Failed to update");
                sendFeedback(json.toJson(new Message("Failed to proceed, either you are not logged in or you are not an owner", "200")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //===================================================================//
    /* FEEDBACK REMOVE CAR FROM DB */
    private void ownerRemoveCar() {
        try {
            if (ownerService.deleteCarServ(this.ownerData) > 0) {
                System.out.println("Car Database Updated");
                sendFeedback(json.toJson(new Message("Your car ads successfully removed!", "200")));
            } else {
                System.out.println("Failed to update");
                sendFeedback(json.toJson(new Message("Failed to proceed, either you are not logged in or you are not a user", "200")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendFeedback(String message) {
        sendToRabbit.send(message);
    }
}
