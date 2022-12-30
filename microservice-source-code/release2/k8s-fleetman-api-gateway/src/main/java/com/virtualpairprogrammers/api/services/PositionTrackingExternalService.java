package com.virtualpairprogrammers.api.services;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import org.slf4j.*;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.virtualpairprogrammers.api.domain.VehiclePosition;

@Service 
public class PositionTrackingExternalService 
{
	@Autowired
	private RemotePositionMicroserviceCalls remoteService;
	
        private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        Logger logger = LoggerFactory.getLogger(PositionTrackingExternalService.class);
	
	public Collection<VehiclePosition> getAllUpdatedPositionsSince(Date since)
	{
		String date = formatter.format(since);
		try {
		   return remoteService.getAllLatestPositionsSince(date);
                }
		catch (feign.RetryableException e)
		{ 
                   logger.info("Unable to contact the position tracker. Most likely this is because that microservice hasn't yet started. This is only an error if the position tracker is running and healthy. Wait a few minutes before panicking!");
                   return new ArrayList<VehiclePosition>();
                }
	}
	
	public Collection<VehiclePosition> getHistoryFor(String vehicleName) {
                try {
		   return remoteService.getHistoryFor(vehicleName);
                }
                catch (feign.RetryableException e)
                { 
                   logger.info("Unable to contact the position tracker. Most likely this is because that microservice hasn't yet started. This is only an error if the position tracker is running and healthy. Wait a few minutes before panicking!");
                   return new ArrayList<VehiclePosition>();
                }
	}
}
