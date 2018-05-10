package com.virtualpairprogrammers.tracker.data;

import java.util.Collection;

import com.virtualpairprogrammers.tracker.domain.VehicleNotFoundException;
import com.virtualpairprogrammers.tracker.domain.VehiclePosition;

public interface Data {

	void updatePosition(VehiclePosition position);

	VehiclePosition getLatestPositionFor(String vehicleName) throws VehicleNotFoundException;

	void addAllReports(VehiclePosition[] allReports);

	Collection<VehiclePosition> getAllReportsForVehicleSince(String vehicleName, String timestamp) throws VehicleNotFoundException;

}