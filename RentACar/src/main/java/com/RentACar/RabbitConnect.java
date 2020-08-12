package com.RentACar;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class RabbitConnect {
    private ConnectionFactory factory = new ConnectionFactory();
    private Connection connection;
    private Channel channel;

    public Connection estConn() {
        factory.setHost("192.168.1.13");
        factory.setUsername("dhio");
        factory.setPassword("admin");
        try {
            connection = factory.newConnection();
//            System.out.println("Connection Success!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    public Channel estChannel(Connection connection) {

        try {
            channel = connection.createChannel();
//            System.out.println("Channel Created!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return channel;
    }


}
