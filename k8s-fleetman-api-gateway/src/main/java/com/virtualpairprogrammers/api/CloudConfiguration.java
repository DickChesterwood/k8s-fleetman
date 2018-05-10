package com.virtualpairprogrammers.api;

import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile({"production", "development"})
@EnableCircuitBreaker
@EnableFeignClients
public class CloudConfiguration {
	
}
