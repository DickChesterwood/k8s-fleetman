package com.virtualpairprogrammers.api.services;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.virtualpairprogrammers.api.domain.VehiclePosition;

@Service 
public class PositionTrackingExternalService 
{
	@Autowired
	private RemotePositionMicroserviceCalls remoteService;
	
    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
	
	//@HystrixCommand(fallbackMethod="handleExternalServiceDown")
	public Collection<VehiclePosition> getAllUpdatedPositionsSince(Date since)
	{
		String date = formatter.format(since);
		Collection<VehiclePosition> results = remoteService.getAllLatestPositionsSince(date);
		return results;
	}
	
	public Collection<VehiclePosition> handleExternalServiceDown(Date since)
	{
		System.out.println("hystrix triggered.");
		// as the external service is down, simply return "no updates"
		return new HashSet<>();
	}

	@HystrixCommand(fallbackMethod="getHistoryForDown")
	public Collection<VehiclePosition> getHistoryFor(String vehicleName) {
		return remoteService.getHistoryFor(vehicleName);
	}
	
	public Collection<VehiclePosition> getHistoryForDown(String vehicleName) {
		return new HashSet<>();
	}
	
}
