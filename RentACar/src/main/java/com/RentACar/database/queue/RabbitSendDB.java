package com.RentACar.database.queue;

import com.RentACar.RabbitConnect;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;


public class RabbitSendDB {
    private static final String QUEUE_FEEDBACK = "rental-queue-feedback";
    private static final String EXCHANGE_NAME = "rental-exchange-feedback";

    public void send(String message) {

        try {
            RabbitConnect rabbitConnect = new RabbitConnect();
            Connection conn = rabbitConnect.estConn();
            Channel channel = rabbitConnect.estChannel(conn);
            boolean durable = true;  // change to durable

            channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
            channel.queueDeclare(QUEUE_FEEDBACK, durable, false, false, null);

            channel.basicPublish(EXCHANGE_NAME, QUEUE_FEEDBACK, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
            System.out.println("Send feedback successfully");
            channel.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed send feedback");
        }
    }
}
