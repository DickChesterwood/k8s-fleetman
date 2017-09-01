package com.virtualpairprogrammers.controllers;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Position {
	private BigDecimal lat;
	private BigDecimal longitude;
	private Date timestamp;
	private boolean upToDate;
	private String vehicleName;
	private String speed;
	

	public Position() {}
	
	public Position(BigDecimal lat, BigDecimal longitude, Date timestamp, boolean upToDate, String vehicleName) {
		super();
		this.lat = lat;
		this.longitude = longitude;
		this.timestamp = timestamp;
		this.upToDate = upToDate;
		this.vehicleName = vehicleName;
	}
	public String getVehicleName() {
		return vehicleName;
	}

	public void setVehicleName(String vehicleName) {
		this.vehicleName = vehicleName;
	}

	public boolean isUpToDate() {
		return upToDate;
	}
	public void setUpToDate(boolean upToDate) {
		this.upToDate = upToDate;
	}
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
	
	public String getPrettyTime()
	{
		if (this.timestamp == null) return "No time given";		
		SimpleDateFormat fmt = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
		return fmt.format(this.timestamp);
	}
	
	public String getSpeed() {
		return speed;
	}
	
	public void setSpeed(String speed) {
		this.speed = speed;
	}
}
