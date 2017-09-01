package com.virtualpairprogrammers;

import java.util.Arrays;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.virtualpairprogrammers.data.VehicleRepository;
import com.virtualpairprogrammers.domain.Vehicle;

/**
 * Used to ensure that the necessary data is pre-populated.
 * 
 * Of course "in real life" (I'm sick of writing that - do I live in a fake parallel universe?) this
 * data would come from the user/administrator/planner/whatever.
 */
@Service
@Transactional
public class Startup {
	Logger log = Logger.getLogger(getClass());
	
	@Autowired
	private VehicleRepository data;
	
	private String[] requiredVehicles = {"city_truck", "village_truck", "small_parcel_service1", "express_delivery1", "dronfield_round",
										 "dronfield_round_b","electronics_pcb_rush_deliveries", "factory_run_a","factory_run_b",
										 "factory_run_c","factory_run_d","factory_run_e","factory_run_f","factory_run_g","factory_run_h",
										 "huddersfield_truck_a","huddersfield_truck_b","local_deliveries_a","local_deliveries_b","london_riverside",
										 "secret_service_a","secret_service_b","secret_service_c","secret_service_d","secret_service_e","secret_service_f",
										 "sheffield_truck_a","sheffield_truck_b","sheffield_truck_c","sheffield_truck_d","top_secret","university_internal_mail_a",
										 "university_internal_mail_b","university_internal_mail_c","university_internal_mail_d","university_internal_mail_e",
										 "university_internal_mail_f","university_internal_mail_g"};
	
	@Configuration
	@Profile({"production", "docker"})
	class ProductionVersion {
		@PostConstruct
		public void populateData()
		{
			Arrays.stream(requiredVehicles).forEach(it -> createVehicleIfDoesntExist(it));
		}		
	}
	@Configuration
	@Profile({"development","docker-demo"})
	class LocalDevelopmentVersion {
		@PostConstruct
		public void populateData()
		{
			createVehicleIfDoesntExist(requiredVehicles[0]);
			createVehicleIfDoesntExist(requiredVehicles[1]);
		}		
	}
	
	private void createVehicleIfDoesntExist(String name) {
		Vehicle exisiting = data.findByName(name);
		if (exisiting == null)	
		{
			log.info("Creating record for " + name);
			this.data.save(new Vehicle(name));
		}
	}
	
	
}
