package com.virtualpairprogrammers.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.virtualpairprogrammers.data.VehicleRepository;
import com.virtualpairprogrammers.domain.Vehicle;
import com.virtualpairprogrammers.services.PositionTrackingExternalService;

@Controller
@Transactional
@RequestMapping("/")
public class VehicleController 
{
	@Autowired
	private VehicleRepository data;
	
	@Autowired
	private SimpMessageSendingOperations messagingTemplate;
	
	@Autowired
	private PositionTrackingExternalService externalService;

	@RequestMapping(value="/newVehicle.html",method=RequestMethod.POST)
	public String newVehicle(Vehicle vehicle)
	{
		data.save(vehicle);
		return "redirect:/website/vehicles/list.html";
	}
	
	@RequestMapping(value="/deleteVehicle.html", method=RequestMethod.POST)
	public String deleteVehicle(@RequestParam Long id)
	{
		data.delete(id);
		return "redirect:/website/vehicles/list.html";		
	}
	
	@RequestMapping(value="/newVehicle.html",method=RequestMethod.GET)
	public ModelAndView renderNewVehicleForm()
	{
		Vehicle newVehicle = new Vehicle();
		return new ModelAndView("newVehicle","form",newVehicle);
	} 
	
	@RequestMapping(value="/", method=RequestMethod.GET)	
	public ModelAndView vehicles()
	{
		List<Vehicle> allVehicles = data.findAll();
		for (Vehicle next: allVehicles)
		{
			Position latest = externalService.getLatestPositionForVehicleFromRemoteMicroservice(next.getName());
			next.setLat(latest.getLat());
			next.setLongitude(latest.getLongitude());
			next.setLastRecordedPosition(latest.getTimestamp());
		}
		return new ModelAndView("liveTracking", "vehicles", allVehicles);
	}
	  
	@RequestMapping(value="/vehicle/{name}")
	public ModelAndView showVehicleByName(@PathVariable("name") String name)
	{
		Vehicle vehicle = data.findByName(name);
		
		// get the current position for this vehicle from the microservice
		Position latestPosition = externalService.getLatestPositionForVehicleFromRemoteMicroservice(name);
		
		// If successful, then update in our database.
		if (latestPosition.isUpToDate())
		{
			vehicle.setLat(latestPosition.getLat());
			vehicle.setLongitude(latestPosition.getLongitude());
			vehicle.setLastRecordedPosition(latestPosition.getTimestamp());
		}
		
		Map<String,Object> model = new HashMap<>();
		model.put("vehicle", vehicle);
		model.put("position", latestPosition);
		return new ModelAndView("vehicleInfo", "model",model);
	}
	
    @Scheduled(fixedRate=100)
    public void updatePositions()
    {
    	// get current position for all vehicles
    	List<Vehicle> allVehicles = data.findAll();
    	for (Vehicle next: allVehicles)
    	{
    		// Only publish 1 in 10 reports - this makes for random updates, each one approx a second apart
    		if (Math.random() < 0.9) continue;
	    	Position latestPosition = externalService.getLatestPositionForVehicleFromRemoteMicroservice(next.getName());
	    	if (latestPosition.isUpToDate())
	    	{
				next.setLat(latestPosition.getLat());
				next.setLongitude(latestPosition.getLongitude());
				next.setLastRecordedPosition(latestPosition.getTimestamp());
				this.messagingTemplate.convertAndSend("/vehiclepositions/messages", latestPosition);
	    	}
    	}
    }
}
