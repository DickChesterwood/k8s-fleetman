package com.virtualpairprogrammers.data;

import java.util.Collection;

import com.virtualpairprogrammers.domain.VehicleNotFoundException;
import com.virtualpairprogrammers.domain.VehiclePosition;

public interface Data {

	void updatePosition(VehiclePosition position);

	VehiclePosition getLatestPositionFor(String vehicleName) throws VehicleNotFoundException;

	void addAllReports(VehiclePosition[] allReports);

	Collection<VehiclePosition> getAllReportsForVehicleSince(String vehicleName, String timestamp) throws VehicleNotFoundException;

}