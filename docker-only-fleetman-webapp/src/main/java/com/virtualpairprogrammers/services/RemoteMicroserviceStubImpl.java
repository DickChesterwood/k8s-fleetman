package com.virtualpairprogrammers.services;

import java.math.BigDecimal;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.virtualpairprogrammers.controllers.Position;

@Profile({"development","docker-demo"})
@Service
public class RemoteMicroserviceStubImpl implements RemotePositionMicroserviceCalls 
{	
	// This stub implementation simply returns a random position in a small area.
	// Something in the area of 53.38207,-1.48423
	public Position getLatestPositionForVehicle(String name) 
	{	
        BigDecimal lat = generateRandomBigDecimalFromRange("53.38653","53.37687" );		
		BigDecimal longitude = generateRandomBigDecimalFromRange("-1.49850","-1.46517");
		return new Position(lat, longitude,new java.util.Date(), true, name);
	}
	
	public static BigDecimal generateRandomBigDecimalFromRange(BigDecimal min, BigDecimal max) {
	    BigDecimal randomBigDecimal = min.add(new BigDecimal(Math.random()).multiply(max.subtract(min)));
	    return randomBigDecimal.setScale(6,BigDecimal.ROUND_HALF_UP);
	}

	public static BigDecimal generateRandomBigDecimalFromRange(String min, String max) {
	    return generateRandomBigDecimalFromRange(new BigDecimal(min), new BigDecimal(max));
	}

}
