package com.RentACar.database;

import com.RentACar.RabbitConnect;
import com.RentACar.client.RentACarClient;
import com.RentACar.database.queue.RabbitReceiveDB;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class RentACarDB {

	public static RabbitConnect rabbitConnect = new RabbitConnect();
	public static RabbitReceiveDB rabbitReceiveDB = new RabbitReceiveDB();

	public static void main(String[] args) {

		while(rabbitConnect.estConn().isOpen()) {
			try {
				rabbitReceiveDB.ReceiveTask();
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("There is trouble receiving Task from client");
			}
		}

	}

}
