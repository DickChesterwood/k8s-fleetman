package com.virtualpairprogrammers;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

/**
 * These tests are based on rough measurements on a real map. Therefore, we only expect
 * results to be correct within 0.1mph. This is good enough for this system anyway.
 */
public class TestSpeedCalculations {

	@Test
	public void testSpeedAsMeasuredBetweenTwoPointsCoveredInFiveSeconds() throws VehicleNotFoundException {
		Data data = new Data();
		
		// These data points measured on a map
		// 1: 53.33507, -1.53766
 
		Map<String, String> report1 = new HashMap<>();
		report1.put("vehicle", "city_truck");		
		report1.put("lat", "53.33507");
		report1.put("long", "-1.53766");
		report1.put("time", "Wed Jul 05 10:26:24 BST 2017");
		
		data.updatePosition(report1);
		
		Position pos = data.getLatestPositionFor("city_truck");
		assertNull("Expected speed of vehicle with one report is null", pos.getSpeed());
		
		// Point 2 is measured at 153m apart. 53.33635, -1.53682
		Map<String, String> report2 = new HashMap<>();
		report2.put("vehicle", "city_truck");		
		report2.put("lat", "53.33635");
		report2.put("long", "-1.53682");
		report2.put("time", "Wed Jul 05 10:26:29 BST 2017");
		
		data.updatePosition(report2);
		
		pos = data.getLatestPositionFor("city_truck");
		
		// The two points are 153m apart, covered in 5s which gives 30.6m/s
		// This equates to 68.45025 mph. We're not expecting any of this to be dead accurate!
		// We'll go for within 0.1 mph...
		
		assertEquals(68.45025, pos.getSpeed().doubleValue(), 0.1);
	}

	@Test
	public void testSpeedWhenTravellingExactlyOneKilometerInOneMinute() throws VehicleNotFoundException {
		Data data = new Data();
		
		// These two points are on OS grid lines 1km apart, as measured by Memory Map.
		Map<String, String> report1 = new HashMap<>();
		report1.put("vehicle", "city_truck");		
		report1.put("lat", "53.33393");
		report1.put("long", "-1.52097");
		report1.put("time", "Wed Jul 05 10:26:00 BST 2017");
		
		data.updatePosition(report1);
		
		Position pos = data.getLatestPositionFor("city_truck");
		assertNull("Expected speed of vehicle with one report is null", pos.getSpeed());
		
		Map<String, String> report2 = new HashMap<>();
		report2.put("vehicle", "city_truck");		
		report2.put("lat", "53.34292");
		report2.put("long", "-1.52083");
		report2.put("time", "Wed Jul 05 10:27:00 BST 2017");
		
		data.updatePosition(report2);
		
		pos = data.getLatestPositionFor("city_truck");
		
		// 1km apart, gives a speed of 16.67m/s ie 37.28mph
		assertEquals(37.28, pos.getSpeed().doubleValue(), 0.1);
	}
	
	@Test
	public void testStationaryVehicle() throws VehicleNotFoundException {
		Data data = new Data();
		
		Map<String, String> report1 = new HashMap<>();
		report1.put("vehicle", "city_truck");		
		report1.put("lat", "53.33393");
		report1.put("long", "-1.52097");
		report1.put("time", "Wed Jul 05 10:26:00 BST 2017");
		
		data.updatePosition(report1);
		
		Position pos = data.getLatestPositionFor("city_truck");
		assertNull("Expected speed of vehicle with one report is null", pos.getSpeed());
		
		Map<String, String> report2 = new HashMap<>();
		report2.put("vehicle", "city_truck");		
		report2.put("lat", "53.33393");
		report2.put("long", "-1.52097");
		report2.put("time", "Wed Jul 05 10:26:00 BST 2017");
		
		data.updatePosition(report2);
		
		pos = data.getLatestPositionFor("city_truck");
		
		assertEquals(0, pos.getSpeed().doubleValue(), 0);
	}
	
	@Test
	public void testSpeedIsBasedOnlyOnLastReport() throws VehicleNotFoundException {
		Data data = new Data();
		
		// These two points are on OS grid lines 1km apart, as measured by Memory Map.
		Map<String, String> report1 = new HashMap<>();
		report1.put("vehicle", "city_truck");		
		report1.put("lat", "53.33393");
		report1.put("long", "-1.52097");
		report1.put("time", "Wed Jul 05 10:26:00 BST 2017");
		
		data.updatePosition(report1);
				
		Map<String, String> report2 = new HashMap<>();
		report2.put("vehicle", "city_truck");		
		report2.put("lat", "53.34292");
		report2.put("long", "-1.52083");
		report2.put("time", "Wed Jul 05 10:27:00 BST 2017");
		
		data.updatePosition(report2);
		
		Map<String, String> report3 = new HashMap<>();
		report3.put("vehicle", "city_truck");		
		report3.put("lat", "53.33635");
		report3.put("long", "-1.53682");
		report3.put("time", "Wed Jul 05 10:28:24 BST 2017");
		
		data.updatePosition(report3);
		
		Position pos = data.getLatestPositionFor("city_truck");
		
		// This last leg is 1.29km, and it took 84 seconds. 34.35mph 
		assertEquals(34.35, pos.getSpeed().doubleValue(), 0.1);
	}	
}
