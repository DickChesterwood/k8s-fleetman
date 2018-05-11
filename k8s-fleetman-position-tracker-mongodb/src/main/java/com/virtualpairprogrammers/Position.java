package com.virtualpairprogrammers;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonFormat;

@XmlRootElement
public class Position implements Comparable<Position>
{
	private BigDecimal lat;
	private BigDecimal longitude;
	
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss.SSSZ", timezone="UTC")
	private Date timestamp;
	private BigDecimal speed;
	
	public Position() {}
	
	
	
	public BigDecimal getLat() {
		return lat;
	}



	public void setLat(BigDecimal lat) {
		this.lat = lat;
	}



	public BigDecimal getLongitude() {
		return longitude;
	}



	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}



	public Date getTimestamp() {
		return timestamp;
	}



	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}



	public Position(String lat,String longitude, String timestamp)
	{
		this.lat = new BigDecimal(lat);
		this.longitude = new BigDecimal(longitude);
		DateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
		try {
			this.timestamp = format.parse(timestamp);
		} 
		catch (ParseException e) 
		{
			// code error
			throw new RuntimeException(e);
		} 
	}

	@Override
	public int compareTo(Position o) 
	{
		return o.timestamp.compareTo(this.timestamp);
	}

	public void setSpeed(BigDecimal speed) {
		this.speed = speed;
	}
	
	public BigDecimal getSpeed()
	{
		return this.speed;
	}
}
