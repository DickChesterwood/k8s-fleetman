package com.virtualpairprogrammers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * This config class is necessary if running a WAR file under tomcat.
 * Added just so we demo deploying a WAR to a Tomcat image under Docker.
 */
@SpringBootApplication
@EnableScheduling
public class TomcatWARApplication extends SpringBootServletInitializer 
{
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(TomcatWARApplication.class);
    }
    
	public static void main(String[] args) {
		SpringApplication.run(FleetmanApplication.class, args);		
	}    

}