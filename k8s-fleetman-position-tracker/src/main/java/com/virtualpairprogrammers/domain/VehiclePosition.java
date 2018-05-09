package com.virtualpairprogrammers.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonFormat;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class VehiclePosition implements Comparable<VehiclePosition>
{
	private String name;
	private BigDecimal lat;
	private BigDecimal longitude;
	
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss.SSSZ", timezone="UTC")
	private Date timestamp;
	private BigDecimal speed;
	
	VehiclePosition() {}
	
	VehiclePosition(String name, BigDecimal lat, BigDecimal lng, Date timestamp) {
		this.name = name;
		this.lat = lat;
		this.longitude = lng;
		this.timestamp = timestamp;
	}

	@Override
	public int compareTo(VehiclePosition o) 
	{
		return o.timestamp.compareTo(this.timestamp);
	}

	public String getName() {
		return this.name;
	}

	public BigDecimal getLat() {
		return this.lat;
	}

	public BigDecimal getLongitude() {
		return this.longitude;
	}

	public Date getTimestamp() {
		return this.timestamp;
	}

	public BigDecimal getSpeed() {
		return this.speed;
	}

}
