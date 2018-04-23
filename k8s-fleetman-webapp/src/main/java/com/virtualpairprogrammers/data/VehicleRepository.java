package com.virtualpairprogrammers.data;

import org.springframework.data.jpa.repository.JpaRepository;

import com.virtualpairprogrammers.domain.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle, Long>
{
	public Vehicle findByName(String name);
} 
