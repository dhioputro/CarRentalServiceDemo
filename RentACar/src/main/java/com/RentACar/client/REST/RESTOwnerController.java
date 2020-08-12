package com.RentACar.client.REST;

import com.RentACar.client.queue.RabbitReceiveClient;
import com.RentACar.client.queue.RabbitSendClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/RentACar/owner")
public class RESTOwnerController {

    RabbitSendClient sendToRabbit;
    RabbitReceiveClient receiveFromRabbit = new RabbitReceiveClient();

    /*=============================REGISTRATION===================================*/
    @PostMapping("/registration")
    public String ownerRegis(@RequestBody String owner) throws IOException {
        sendToRabbit = new RabbitSendClient();
        sendToRabbit.send(owner, "OwnerAccess");
        return receiveFromRabbit.receiveFeedback();
    }

    /*=============================LOGIN=========================================*/
    @PostMapping("/login")
    public String ownerLogin(@RequestBody String owner) throws IOException {
        sendToRabbit = new RabbitSendClient();
        sendToRabbit.send(owner, "OwnerAccess");
        return receiveFromRabbit.receiveFeedback();
    }

    /*=============================LOGOUT=========================================*/
    @PostMapping("/logout")
    public String ownerLogout(@RequestBody String owner) throws IOException {
        sendToRabbit = new RabbitSendClient();
        sendToRabbit.send(owner, "OwnerAccess");
        return receiveFromRabbit.receiveFeedback();
    }

    @PostMapping("/addcar")
    public String ownerAddCar(@RequestBody String owner) throws IOException {
        sendToRabbit = new RabbitSendClient();
        sendToRabbit.send(owner, "OwnerAccess");
        return receiveFromRabbit.receiveFeedback();
    }

    /*=============================UPDATE CAR=========================================*/
    @PutMapping("/updatecar")
    public String ownerUpdateCar(@RequestBody String owner) throws IOException {
        sendToRabbit = new RabbitSendClient();
        sendToRabbit.send(owner, "OwnerAccess");
        return receiveFromRabbit.receiveFeedback();
    }

    /*=============================REMOVE CAR=========================================*/
    @PostMapping("/removecar")
    public String ownerRemoveCar(@RequestBody String owner) throws IOException {
        sendToRabbit = new RabbitSendClient();
        sendToRabbit.send(owner, "OwnerAccess");
        return receiveFromRabbit.receiveFeedback();
    }

}
