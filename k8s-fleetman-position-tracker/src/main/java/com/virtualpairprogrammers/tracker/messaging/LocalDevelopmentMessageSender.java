package com.virtualpairprogrammers.tracker.messaging;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.virtualpairprogrammers.tracker.domain.VehicleBuilder;
import com.virtualpairprogrammers.tracker.domain.VehiclePosition;

/**
 * Whilst running standalone, we send some random chaotic vehicle positions
 * to the embedded queue.
 * @author Richard Chesterwood
 *
 */
@Profile("development")
@Component
public class LocalDevelopmentMessageSender 
{
	private static final String testVehicleName = "truck";
	private static final BigDecimal startLat = new BigDecimal("53.383882");
	private static final BigDecimal startLng = new BigDecimal("-1.483979");
	
	private VehiclePosition lastPosition;
	
	@Autowired
	private JmsMessagingTemplate template;
	
	@Value("${fleetman.position.queue}")
	private String destination;
	
	@PostConstruct
	public void init() {
		lastPosition = new VehicleBuilder().withName(testVehicleName)
		                                   .withLat(startLat)
		                                   .withLng(startLng)
		                                   .withTimestamp(new java.util.Date().toString()).build(); // TODO urgh
		sendMessageToEmbeddedQueue(lastPosition);
	}
	
	@Scheduled(fixedRate=100)
	public void sendPeriodicVehcileUpdates()
	{
		// Random updates about every 1s
		if (Math.random() < 0.9) return;
		
		double randomChangeX = (Math.random() - 0.5) / 1000;
		double randomChangeY = (Math.random() - 0.5) / 1000;
		
		BigDecimal newLat = lastPosition.getLat().add(new BigDecimal("" + randomChangeX));
		BigDecimal newLng = lastPosition.getLongitude().add(new BigDecimal("" + randomChangeY));
		
		VehiclePosition newPostion = new VehicleBuilder().withName(testVehicleName)
				                                         .withLat(newLat)
				                                         .withLng(newLng)
				                                         .withTimestamp(new java.util.Date().toString()).build();
		lastPosition = newPostion;
		sendMessageToEmbeddedQueue(lastPosition);
	}

	private void sendMessageToEmbeddedQueue(VehiclePosition position) {
		Map<String, String> mapMessage = new HashMap<>();
		mapMessage.put("vehicle", position.getName());
		mapMessage.put("lat", position.getLat().toString());
		mapMessage.put("long", position.getLongitude().toString());
		mapMessage.put("time", position.getTimestamp().toString());
		template.convertAndSend(destination, mapMessage);
	}
}
