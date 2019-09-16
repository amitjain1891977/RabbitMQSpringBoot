package com.demo;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

@ComponentScan
@SpringBootApplication
public class RabbitMQDemo {

	public static void main(String[] args) {
		System.setProperty("server.servlet.context-path", "/rabbitmqdemo");
		SpringApplication.run(RabbitMQDemo.class, args);
	}

}
