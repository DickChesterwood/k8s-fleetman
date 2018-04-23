package com.virtualpairprogrammers.services;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.virtualpairprogrammers.controllers.Position;

@Profile({"k8s"})
@FeignClient(url="fleetman-tracker.default.svc.cluster.local:8080", name="fleetman-position-tracker")
public interface RemotePositionMicroserviceCalls 
{
	// REST Call
	// /vehicles/{name}
	@RequestMapping(method=RequestMethod.GET, value="/vehicles/{name}")
	public Position getLatestPositionForVehicle(@PathVariable("name") String name);
}
