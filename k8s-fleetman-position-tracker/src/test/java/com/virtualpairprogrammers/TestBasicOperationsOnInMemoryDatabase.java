package com.virtualpairprogrammers;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import com.virtualpairprogrammers.data.Data;
import com.virtualpairprogrammers.data.DataBasicInMemoryImpl;
import com.virtualpairprogrammers.domain.VehicleBuilder;
import com.virtualpairprogrammers.domain.VehicleNotFoundException;
import com.virtualpairprogrammers.domain.VehiclePosition;

public class TestBasicOperationsOnInMemoryDatabase {
	
	private Data testData = new DataBasicInMemoryImpl();
	private VehiclePosition firstReport;
	private VehiclePosition secondReport;
	private VehiclePosition thirdReport;
	private VehiclePosition fourthReport;
	private VehiclePosition fifthReport;
	private VehiclePosition sixthReport;
	private VehiclePosition seventhReport;
	private VehiclePosition[] allReports;
	
	public TestBasicOperationsOnInMemoryDatabase() {
		firstReport = new VehicleBuilder()
				.withName("who cares")
				.withLat("1.0")
				.withLng("1.0")
				.withTimestamp("Wed Feb 01 10:26:12 BST 2017")
				.build();
		
		secondReport = new VehicleBuilder()
				.withName("who cares")
				.withLat("1.0")
				.withLng("1.0")
				.withTimestamp("Mon May 01 10:26:12 BST 2017")
				.build();

		thirdReport = new VehicleBuilder()
				.withName("who cares")
				.withLat("1.0")
				.withLng("1.0")
				.withTimestamp("Wed Jul 05 10:26:12 BST 2017")
				.build();

		fourthReport = new VehicleBuilder()
						.withName("who cares")
						.withLat("1.0")
						.withLng("1.0")
						.withTimestamp("Wed Jul 05 10:26:24 BST 2017")
						.build();
		
		fifthReport = new VehicleBuilder()
				.withName("who cares")
				.withLat("4.0")
				.withLng("2.0")
				.withTimestamp("Wed Jul 05 10:26:30 BST 2017")
				.build();
		
		sixthReport = new VehicleBuilder()
				.withName("who cares")
				.withLat("1.0")
				.withLng("1.0")
				.withTimestamp("Thu Jul 06 10:26:12 BST 2017")
				.build();
		
		seventhReport = new VehicleBuilder()
				.withName("who cares")
				.withLat("1.0")
				.withLng("1.0")
				.withTimestamp("Wed May 09 19:55:12 BST 2018")
				.build();		
		
		allReports = new VehiclePosition[] {firstReport, secondReport, thirdReport, fourthReport, fifthReport, sixthReport, seventhReport};
	}
	
	@Test
	public void testGettingAllReportsWorks() {
		testData.updatePosition(thirdReport);
		try 
		{
			VehiclePosition foundPosition = testData.getLatestPositionFor("who cares");
			assertEquals(thirdReport, foundPosition);
		} 
		catch (VehicleNotFoundException e) 
		{
			fail("Vehicle was not found - it should have been!!");
		}
	}
	
	@Test(expected = VehicleNotFoundException.class)
	public void testFindingANonExistentVehicleRaisesException() throws VehicleNotFoundException {
		testData.updatePosition(thirdReport);
		testData.getLatestPositionFor("wrong name");
	}
	
	@Test
	public void testGettingLastReportWorks() throws VehicleNotFoundException
	{
		testData.updatePosition(firstReport);
		testData.updatePosition(secondReport);
		testData.updatePosition(thirdReport);
		
		VehiclePosition foundPosition = testData.getLatestPositionFor("who cares");
		assertEquals(thirdReport, foundPosition);
	}

	@Test
	public void testGettingLastReportWorksBasedOnTimestampEvenIfReportsArriveInTheWrongOrder() throws VehicleNotFoundException
	{
		testData.updatePosition(sixthReport);
		testData.updatePosition(secondReport);
	    testData.updatePosition(seventhReport);
	    testData.updatePosition(firstReport);
	    testData.updatePosition(thirdReport);
		
		VehiclePosition foundPosition = testData.getLatestPositionFor("who cares");
		assertEquals(seventhReport, foundPosition);		
	}
	
	@Test
	public void testGettingAllVehiclesSinceAParticularTime() throws VehicleNotFoundException
	{
		testData.addAllReports(allReports);
		
		// This is the exact timestamp of report 4.
		Collection<VehiclePosition> reports = testData.getAllReportsForVehicleSince("who cares", "Wed Jul 05 10:26:24 BST 2017");
		
		// should contain 4, 5, 6, 7
		Set<VehiclePosition> expectedReports = new HashSet<>();
		expectedReports.add(fourthReport);
		expectedReports.add(fifthReport);
		expectedReports.add(sixthReport);
		expectedReports.add(seventhReport);
		
		assertEquals(4, reports.size());
		assertTrue(reports.containsAll(expectedReports));
	}
	
	@Test
	public void testGettingAllReportsSinceAVeryLateTimeResultsInNoReports() throws VehicleNotFoundException
	{
		testData.addAllReports(allReports);
		Collection <VehiclePosition> results = testData.getAllReportsForVehicleSince("who cares", "Thu May 10 12:00:00 BST 2018");
		assertTrue(results.isEmpty());
	}
	
	@Test
	public void testGettingAllReportsSinceAVeryLongTimeAgoReturnsTheLot() throws VehicleNotFoundException
	{
		testData.addAllReports(allReports);
		Collection <VehiclePosition> results = testData.getAllReportsForVehicleSince("who cares", "Sat Jun 09 12:00:00 BST 1973");
		assertEquals(7, results.size());
		assertTrue(results.containsAll(Arrays.asList(allReports)));
	}
	
	@Test(expected=VehicleNotFoundException.class)
	public void testGettingAllReportsForNonExistentVehicleThrowsException() throws VehicleNotFoundException
	{
		testData.addAllReports(allReports);
		Collection <VehiclePosition> results = testData.getAllReportsForVehicleSince("unknown", "Sat Jun 09 12:00:00 BST 1973");
		results.size(); // just to silence the warning!
	}
}
