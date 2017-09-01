package com.virtualpairprogrammers.services;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.virtualpairprogrammers.controllers.Position;

@FeignClient(url="fleetman-position-tracker:8080", name="fleetman-position-tracker")
public interface RemotePositionMicroserviceCalls 
{
	// REST Call
	// /vehicles/{name}
	@RequestMapping(method=RequestMethod.GET, value="/vehicles/{name}")
	public Position getLatestPositionForVehicle(@PathVariable("name") String name);
}
