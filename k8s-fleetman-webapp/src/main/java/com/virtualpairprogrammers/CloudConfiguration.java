package com.virtualpairprogrammers;

import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

// TODO review this; I think it will be needed for k8s.
@Configuration
@Profile({"production","docker"})
@EnableCircuitBreaker
@EnableFeignClients
public class CloudConfiguration {
	
}
