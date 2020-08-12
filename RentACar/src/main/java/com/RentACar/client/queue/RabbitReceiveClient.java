package com.RentACar.client.queue;

import com.RentACar.RabbitConnect;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.GetResponse;


import java.io.IOException;

public class RabbitReceiveClient {
    private static final String QUEUE_FEEDBACK = "rental-queue-feedback";
    private static final String EXCHANGE_NAME = "rental-exchange-feedback";

    public String receiveFeedback() throws IOException {
        RabbitConnect rabbitConnect = new RabbitConnect();
        Connection conn = rabbitConnect.estConn();
        Channel channel = rabbitConnect.estChannel(conn);
        boolean durable = true;
        boolean ack = false;

		channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        channel.queueDeclare(QUEUE_FEEDBACK, durable, false, false, null);
        channel.queueBind(QUEUE_FEEDBACK, EXCHANGE_NAME, "");
        System.out.println("[x] Client receiving Feedback from DB...");
        channel.basicQos(1);

        GetResponse response = channel.basicGet(QUEUE_FEEDBACK, ack);
        while (response == null) {
            response = channel.basicGet(QUEUE_FEEDBACK, ack);
        }
        channel.basicAck(response.getEnvelope().getDeliveryTag(),false);
        System.out.println("[x] Success receiving feedback!");
        return new String(response.getBody(), "UTF-8");
    }
}


//        DeliverCallback deliverCallback =(consumerTag, delivery)->{
//            try {
//                String message = new String(delivery.getBody(), "UTF-8");
//                System.out.println(" [x] Receiving feedback... " +message);
//
//            } catch (Exception e) {
//                System.out.println("Feedback failed to received error : " +e);
//            } finally {
//                System.out.println(" [x] Done");
//                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false); // acknowledgement
////                return message;
//            }
//        };
//
//        channel.basicConsume(QUEUE_FEEDBACK, durable, deliverCallback, consumerTag -> { }); // acknowledgement
//        return "Feedback received successfully";
