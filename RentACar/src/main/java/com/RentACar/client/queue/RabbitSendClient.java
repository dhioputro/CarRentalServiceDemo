package com.RentACar.client.queue;

import com.RentACar.RabbitConnect;
import com.rabbitmq.client.*;
import org.springframework.beans.factory.annotation.Autowired;

public class RabbitSendClient {
    private static final String TASK_QUEUE_NAME = "rental-queue-task";
    private static final String EXCHANGE_NAME = "rental-exchange-task";

    @Autowired
    RabbitConnect rabbitConnect;

    public String send(String message, String type) {

        try {
            rabbitConnect = new RabbitConnect();
            Connection conn = rabbitConnect.estConn();
            Channel channel = rabbitConnect.estChannel(conn);

            AMQP.BasicProperties.Builder propType = new AMQP.BasicProperties.Builder().type(type);
            boolean durable = true; // change to durable

            channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
            channel.queueDeclare(TASK_QUEUE_NAME, durable, false, false, null);

            channel.basicPublish(EXCHANGE_NAME, TASK_QUEUE_NAME, propType.build(), message.getBytes());
            System.out.println("[x] Client proceed send data to DB : '" +message+ "'");
            Thread.sleep(3000);
            channel.close();
            conn.close();
            return "Data Sent!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Send data Failed to Proceed with error : " +e;
        }

    }
}
