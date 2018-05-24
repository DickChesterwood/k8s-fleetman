package com.virtualpairprogrammers.tracker;

import java.text.ParseException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.virtualpairprogrammers.tracker.domain.VehicleNotFoundException;

@SpringBootApplication
@EnableScheduling
public class PositionTrackerApplication {
	public static void main(String[] args) throws VehicleNotFoundException, ParseException {
		SpringApplication.run(PositionTrackerApplication.class, args);
	}
}
