package com.virtualpairprogrammers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class FleetmanGlobalConfigApplication {

	public static void main(String[] args) {
		SpringApplication.run(FleetmanGlobalConfigApplication.class, args);
	}
}
