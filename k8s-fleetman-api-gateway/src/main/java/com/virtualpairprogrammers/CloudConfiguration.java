package com.virtualpairprogrammers;

import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile({"k8s"})
@EnableCircuitBreaker
@EnableFeignClients
public class CloudConfiguration {
	
}
