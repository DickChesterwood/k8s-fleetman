package com.virtualpairprogrammers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.collections4.queue.CircularFifoQueue;
import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GeodeticCalculator;
import org.gavaghan.geodesy.GlobalPosition;
import org.springframework.stereotype.Repository;

/**
 * This data repository stores just the last two positional reports for each vehicle. 
 * Of course "in real life" this would be more likely a full data store.
 * 
 * We store two simply so that speed data can also be returned.
 * 
 * Note and disclaimer: this microservice isn't production standard, therefore we haven't given much
 * thought to thread safety. I have wrapped the underlying Queue (a circular queue to 
 * store just the last two reports for each vehicle, which is all we need) in a 
 * SynchronizedCollection and I'm synchronizing when working with the iterator,
 * which may or may not be sufficient for thread safety. 
 * 
 * I can see at least 2 potential race conditions in this code - however it is *good enough*
 * for the purpose of demonstrating a basic microservice.
 * 
 * No guarantees or warranties with this code!
 */
@Repository
public class Data 
{
	private static final BigDecimal MPS_TO_MPH_FACTOR = new BigDecimal("2.236936");
	private GeodeticCalculator geoCalc = new GeodeticCalculator();
	private Map<String,Collection<Position>> positionQueues;
	
	public Data()
	{
		positionQueues = new HashMap<>();
	}
	
	public void updatePosition(Map<String,String> data)
	{
		String vehicleName = data.get("vehicle");
		Collection<Position> positions = positionQueues.get(vehicleName);
		if (positions == null) 
		{
			positions = Collections.synchronizedCollection(new CircularFifoQueue<Position>(2));
			
			// a FIFO queue will delete the older data.
			positionQueues.put(vehicleName, positions);
		}
		Position newPosition = new Position(data.get("lat"), data.get("long"), data.get("time"));
		positions.add(newPosition);
	}
	
	public Position getLatestPositionFor(String vehicleName) throws VehicleNotFoundException
	{
		Collection<Position> queue = positionQueues.get(vehicleName);
		if (queue == null) throw new VehicleNotFoundException();
		
		Position pos = queue.iterator().next();
		if (queue.size() == 2)
		{
			// we have sufficient data to calculate speed
			BigDecimal speed = calculateSpeedInMph(vehicleName);
			pos.setSpeed(speed);
		}
		return pos;
	}
	
	private BigDecimal calculateSpeedInMph(String vehicleName)
	{	
		// we know we have exactly two positions in the queue....
		Collection<Position> queue = positionQueues.get(vehicleName);
		Iterator<Position> it = queue.iterator();
		Position posA;
		Position posB;		
		synchronized(this)
		{
			posA = it.next();
			posB = it.next();
		}
		
		long timeAinMillis = posA.getTimestamp().getTime();
		long timeBinMillis = posB.getTimestamp().getTime();
		long timeInMillis = timeBinMillis - timeAinMillis;
		if (timeInMillis == 0) return new BigDecimal("0");
		
		BigDecimal timeInSeconds = new BigDecimal(timeInMillis / 1000.0);
				
		GlobalPosition pointA = new GlobalPosition(posA.getLat().doubleValue(), posA.getLongitude().doubleValue(), 0.0);
		GlobalPosition pointB = new GlobalPosition(posB.getLat().doubleValue(), posB.getLongitude().doubleValue(), 0.0);
	
		double distance = geoCalc.calculateGeodeticCurve(Ellipsoid.WGS84, pointA, pointB).getEllipsoidalDistance(); // Distance between Point A and Point B
		BigDecimal distanceInMetres = new BigDecimal (""+ distance);
		
		
		BigDecimal speedInMps = distanceInMetres.divide(timeInSeconds, RoundingMode.HALF_UP);
		BigDecimal milesPerHour = speedInMps.multiply(MPS_TO_MPH_FACTOR);
		return milesPerHour;
	}
}
