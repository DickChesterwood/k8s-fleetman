package com.virtualpairprogrammers.api;

import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCircuitBreaker
@EnableFeignClients
public class CloudConfiguration {
	
}
