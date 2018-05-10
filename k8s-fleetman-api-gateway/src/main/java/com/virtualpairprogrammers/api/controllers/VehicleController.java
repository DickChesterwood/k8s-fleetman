package com.virtualpairprogrammers.api.controllers;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.virtualpairprogrammers.api.domain.VehiclePosition;
import com.virtualpairprogrammers.api.services.PositionTrackingExternalService;

@Controller
@RequestMapping("/api")
public class VehicleController 
{	
	@Autowired
	private SimpMessageSendingOperations messagingTemplate;
	
	@Autowired
	private PositionTrackingExternalService externalService;
	
	private Date lastUpdateTime = null;

    @Scheduled(fixedRate=1000)
    public void updatePositions()
    {
    	Collection<VehiclePosition> results = externalService.getAllUpdatedPositionsSince(lastUpdateTime);
    	lastUpdateTime = new Date();
    	for (VehiclePosition next: results)
    	{
    		System.out.println("updated position for " + next.getName());
			this.messagingTemplate.convertAndSend("/vehiclepositions/messages", next);
    	}
    }
}
