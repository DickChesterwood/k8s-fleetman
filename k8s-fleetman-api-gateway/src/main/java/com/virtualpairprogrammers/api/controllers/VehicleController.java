package com.virtualpairprogrammers.api.controllers;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.virtualpairprogrammers.api.domain.VehiclePosition;
import com.virtualpairprogrammers.api.services.PositionTrackingExternalService;

@Controller
@RequestMapping("/")
public class VehicleController 
{	
	@Autowired
	private SimpMessageSendingOperations messagingTemplate;
	
	@Autowired
	private PositionTrackingExternalService externalService;
	
	private Date lastUpdateTime = null;

	@GetMapping("/")
	@ResponseBody
	/**
	 * This is just a test mapping so we can easily check the API gateway is standing.
	 * When running through the Angular Front end, can visit this URL at /api/
	 */
	public String apiTestUrl()
	{
		return "<p>Fleetman API Gateway at " + new Date() + "</p>";
	}
	
    @Scheduled(fixedRate=100)
    public void updatePositions()
    {
    	Collection<VehiclePosition> results = externalService.getAllUpdatedPositionsSince(lastUpdateTime);
    	this.lastUpdateTime = new Date();
    	for (VehiclePosition next: results)
    	{
			this.messagingTemplate.convertAndSend("/vehiclepositions/messages", next);
    	}
    }
}
