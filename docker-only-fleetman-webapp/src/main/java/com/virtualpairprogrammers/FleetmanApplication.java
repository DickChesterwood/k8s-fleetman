package com.virtualpairprogrammers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FleetmanApplication {
	public static void main(String[] args) {
		SpringApplication.run(FleetmanApplication.class, args);		
	}	
}

