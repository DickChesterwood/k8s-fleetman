package com.virtualpairprogrammers.tracker.data;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.virtualpairprogrammers.tracker.domain.VehicleNotFoundException;
import com.virtualpairprogrammers.tracker.domain.VehiclePosition;

public interface Data {

	void updatePosition(VehiclePosition position);

	VehiclePosition getLatestPositionFor(String vehicleName) throws VehicleNotFoundException;

	void addAllReports(VehiclePosition[] allReports);

	Collection<VehiclePosition> getAllReportsForVehicleSince(String vehicleName, String timestamp) throws VehicleNotFoundException;

	Collection<VehiclePosition> getLatestPositionsOfAllVehiclesUpdatedSince(Date since);

	Collection<VehiclePosition> getLatestPositionsOfAllVehiclesUpdatedSince(String timeStamp);

}