package com.RentACar.database.queue;

import com.RentACar.RabbitConnect;

import com.RentACar.database.controller.DBRecvController;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.GetResponse;
import org.springframework.beans.factory.annotation.Autowired;


import java.io.IOException;

public class RabbitReceiveDB {
    private static final String TASK_QUEUE_NAME = "rental-queue-task";
    private static final String EXCHANGE_NAME = "rental-exchange-task";

    public void ReceiveTask() throws IOException {
        RabbitConnect rabbitConnect = new RabbitConnect();
        Connection conn = rabbitConnect.estConn();
        Channel channel = rabbitConnect.estChannel(conn);
        boolean durable = true; // change to durable
        boolean ack = false;

        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        channel.queueDeclare(TASK_QUEUE_NAME, durable, false, false, null);
        channel.queueBind(TASK_QUEUE_NAME, EXCHANGE_NAME,"" );
        channel.basicQos(1);
        System.out.println("[x] DB receiving Task from Client...");

        GetResponse response = channel.basicGet(TASK_QUEUE_NAME, ack);
        while (response == null) {
            response = channel.basicGet(TASK_QUEUE_NAME, ack);
        }
        channel.basicAck(response.getEnvelope().getDeliveryTag(),false);
        System.out.println("[x] Success receiving Task!");
        String type = response.getProps().getType();
        String data = new String(response.getBody(), "UTF-8");
        System.out.println(data);
        new DBRecvController(type, data);

    }
}
