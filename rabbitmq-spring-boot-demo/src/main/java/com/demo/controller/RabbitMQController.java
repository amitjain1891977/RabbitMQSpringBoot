package com.demo.controller;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

@RestController
public class RabbitMQController {

	private final static String QUEUE_NAME = "RabbitMQ_Message_Queue";

	@RequestMapping("/sendMessage")
	public String sendMessage() {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");

		try (Connection connection = factory.newConnection(); Channel channel = connection.createChannel()) {
			channel.queueDeclare(QUEUE_NAME, false, false, false, null);
			String message = "Hello World!";
			channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
			return "Message sent :: " + message;
		} catch (Exception exe) {
			exe.printStackTrace();
		}
		return null;
	}

	@RequestMapping("/receiveMessage")
	public String recieveMessage() {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection;
		try {
			connection = factory.newConnection();

			Channel channel = connection.createChannel();

			channel.queueDeclare(QUEUE_NAME, false, false, false, null);

			DeliverCallback deliverCallback = (consumerTag, delivery) -> {
				String message = new String(delivery.getBody(), "UTF-8");
				System.out.println(" [x] Received '" + message + "'");
			};
			
			channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {
			});
		} catch (IOException | TimeoutException e) {
			e.printStackTrace();
		}
		return "1 Message Recieved";
	}

}
