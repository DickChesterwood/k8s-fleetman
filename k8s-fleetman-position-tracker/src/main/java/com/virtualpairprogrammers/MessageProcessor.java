package com.virtualpairprogrammers;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.virtualpairprogrammers.data.Data;
import com.virtualpairprogrammers.domain.VehicleBuilder;
import com.virtualpairprogrammers.domain.VehiclePosition;

@Component
public class MessageProcessor {
	
	@Autowired
	private Data data;
	
	@JmsListener(destination="${fleetman.position.queue}")
	public void processPositionMessageFromQueue(Map<String, String> incomingMessage ) 
	{
		VehiclePosition newReport = new VehicleBuilder()
				                          .withName(incomingMessage.get("vehicle"))
				                          .withLat(new BigDecimal(incomingMessage.get("lat")))
				                          .withLng(new BigDecimal(incomingMessage.get("long")))
				                          .withTimestamp(incomingMessage.get("time"))
				                          .build();
				                          
		data.updatePosition(newReport);
	}

}
