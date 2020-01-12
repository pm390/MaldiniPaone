package maldiniPaone.databaseConnection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import maldiniPaone.utilities.State;
import maldiniPaone.utilities.ViolationType;
import maldiniPaone.utilities.beans.Location;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ReportAndAssignmentTests {

	public static Float[] latitudes = { 10f, 20f, 30f, 40f, 50f, 60f, 180f, 190f };
	public static Float[] longitudes = { 10f, 20f, 30f, 40f, 50f, 60f, 120f, 200f, 300f };
	public static String[] plates = { "abc", "cde", "fge", "hdf", "ciao", "random", "other" };

	@Test
	public void test() {
		List<Integer[]> listIndexes=new ArrayList<Integer[]>();
		Random rn = new Random();
		List<Location> locations = new ArrayList<Location>();
		// test location creation
		try
		{
		for (int i = 0; i < latitudes.length; ++i) {
			for (int j = 0; j < longitudes.length; ++j) {
				try {
					Location temp = new Location();
					temp.setLatitude(latitudes[i]);
					temp.setLongitude(longitudes[j]);
					locations.add(temp);
				} catch (Exception e) {
					if (Math.abs(latitudes[i]) <= 180 && Math.abs(longitudes[j]) <= 90) {
						fail("unexpected failure");
					}
				}
			}
		}
		for (int i = 0; i < UserDatabaseTests.citizenNames.length; ++i) {
			int locationIndex = rn.nextInt(locations.size());
			//add twice a report with the same license plate
			Integer[] ids=ReportAndAssignmentUpdater.addReport(UserDatabaseTests.citizenNames[i],
					new Timestamp(System.currentTimeMillis()), locations.get(locationIndex), "some note", plates[i]);
			listIndexes.add(ids);
			Integer[] idsRetry=ReportAndAssignmentUpdater.addReport(UserDatabaseTests.citizenNames[i],
					new Timestamp(System.currentTimeMillis()), locations.get(locationIndex), "some note", plates[i]);
			//should be associated with the next row in assignment bridge
			assertEquals((Integer)(ids[ids.length-1]+1),(Integer)idsRetry[idsRetry.length-1]);
		}
		
		for(int i=0;i<listIndexes.size();++i)
		{
			Integer[] ids=listIndexes.get(i);
			//should be able to accept the assignment
			assertEquals(true,ReportAndAssignmentUpdater.updateAssignment(ids[ids.length-1], 
					UserDatabaseTests.authorityNames[i], null, State.Accepted));
		
			//can't reaccept the same assignment
			assertEquals(false,ReportAndAssignmentUpdater.updateAssignment(ids[ids.length-1], 
					UserDatabaseTests.authorityNames[i], null, State.Accepted));
		}
		for(int i=0;i<listIndexes.size();++i)
		{
			Integer[] ids=listIndexes.get(i);
			//should be able to terminate
			assertEquals(true,ReportAndAssignmentUpdater.updateAssignment(ids[ids.length-1], 
					UserDatabaseTests.authorityNames[i], ViolationType.DoubleParking, State.CorrectlyFinished));
			//can't reterminate the assignment
			assertEquals(false,ReportAndAssignmentUpdater.updateAssignment(ids[ids.length-1], 
					UserDatabaseTests.authorityNames[i], ViolationType.ReservedParking, State.AlreadySolved));
		}
		for(int i=0;i<listIndexes.size();++i)
		{
			Integer[] ids=listIndexes.get(i);
			//can't accept finished assignments
			assertEquals(false,ReportAndAssignmentUpdater.updateAssignment(ids[ids.length-1], 
					UserDatabaseTests.authorityNames[i], null, State.Accepted));
		}
		}catch(Exception e)
		{
			e.printStackTrace();
			fail("unexpected failure in Report and assignments");
		}
		
		
		
	}

}
