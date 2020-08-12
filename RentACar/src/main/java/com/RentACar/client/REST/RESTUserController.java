package com.RentACar.client.REST;


import com.RentACar.client.queue.RabbitReceiveClient;
import com.RentACar.client.queue.RabbitSendClient;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/RentACar/user")
public class RESTUserController {

    RabbitSendClient sendToRabbit;
    RabbitReceiveClient receiveFromRabbit = new RabbitReceiveClient();

    /*=============================REGISTRATION=========================================*/
    @PostMapping("/registration")
    public String userRegis(@RequestBody String user) throws IOException {
        sendToRabbit = new RabbitSendClient();
        sendToRabbit.send(user, "UserAccess");
        return receiveFromRabbit.receiveFeedback();
    }

    /*=============================LOGIN===============================================*/
    @PostMapping("/login")
    public String userLogin(@RequestBody String user) throws IOException {
        sendToRabbit = new RabbitSendClient();
        sendToRabbit.send(user, "UserAccess");
        return receiveFromRabbit.receiveFeedback();
    }

    /*=============================LOGOUT=============================================*/
    @PostMapping("/logout")
    public String userLogout(@RequestBody String user) throws IOException {
        sendToRabbit = new RabbitSendClient();
        sendToRabbit.send(user, "UserAccess");
        return receiveFromRabbit.receiveFeedback();
    }

    /*=============================SEARCH CAR=========================================*/
    @GetMapping("/search_car")
    public String userSearch(@RequestBody String user) throws IOException {
        sendToRabbit = new RabbitSendClient();
        sendToRabbit.send(user, "UserAccess");
        return receiveFromRabbit.receiveFeedback();
    }

    /*=============================RESERVE CAR=========================================*/
    @PostMapping("/reserve_car")
    public String userReserve(@RequestBody String user) throws IOException {
        sendToRabbit = new RabbitSendClient();
        sendToRabbit.send(user, "UserReservation");
        return receiveFromRabbit.receiveFeedback();
    }

    /*=============================CHECK RESERVATION=========================================*/
    @GetMapping("/check")
    public String viewUserReservation(@RequestBody String user) throws IOException {
        sendToRabbit = new RabbitSendClient();
        sendToRabbit.send(user, "UserReservation");
        return receiveFromRabbit.receiveFeedback();
    }

}
