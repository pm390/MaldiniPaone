package maldiniPaone.databaseConnection;


import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import maldiniPaone.utilities.UserType;
import maldiniPaone.utilities.beans.Location;
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserDatabaseTests {

	@BeforeClass
	public static void beforeTests()
	{
		System.out.println("to do with empty db");
	}
	
	
	@Test
	public void testAddUsers()
	{
		//should give correct results
		String[] usernames= {"angelo","pietro","luca","matteo","antonio","calogero","carciofo"};
		String[] passwords= {"a","p","l","m","a","c","c"};
		String[] emails={"a@n","p@i","l@u","m@a","a@nt","c@a","c@ar"};
		String[] venues= {"lombaria","como","somewhere"};
		String[] cityHallsNames= {"alserio","erba","anzano"};
		String[] cityHallsProvinces= {"como","lecco","sondrio"};
		Float[] latitudes= {10f,20f,30f,40f,50f,60f};
		Float[] longitudes= {10f,20f,30f,40f,50f,60f};
		//TODO add values for creating districts
 		try
		{
 			for(int j=0;j<cityHallsNames.length;++j)
 			{
 				Location temp=new Location();
 				temp.setLatitude(latitudes[j]);
 				temp.setLongitude(longitudes[j]);
 				UserDatabaseConnector.addCityhall(cityHallsNames[j],
 						cityHallsProvinces[j], "lombardia",temp);
 			}
 			//TODO create districts
 			int i;
			for(i=0;i<usernames.length/4;++i)//add system managers
			{
				assertEquals(true,
				UserDatabaseConnector.addManager(usernames[i], passwords[i], emails[i],venues[i%venues.length]));
				assertEquals(
						false,//trying to re-add user should result in failure
				UserDatabaseConnector.addManager(usernames[i], passwords[i], emails[i],venues[i%venues.length]));
				assertEquals("created user should be managers",
						UserType.Manager,
						UserDatabaseConnector.checkUserCredentials(usernames[i], passwords[i]));
			}
			int firstMunicipalityIndex=i;
			for(;i<usernames.length/2;++i)
			{
				assertEquals(
						true,
				UserDatabaseConnector.addMunicipalityByManager(usernames[i], passwords[i],
						emails[i], cityHallsNames[i%cityHallsNames.length],cityHallsProvinces[i%cityHallsProvinces.length])
				);
				assertEquals(//trying to re-add the user should result in failure
						false,
				UserDatabaseConnector.addMunicipalityByManager(usernames[i], passwords[i],
						emails[i], cityHallsNames[i%cityHallsNames.length],cityHallsProvinces[i%cityHallsProvinces.length])
				);
				assertEquals("created user should  be Municipality",
							UserType.Municipality,
							UserDatabaseConnector.checkUserCredentials(usernames[i], passwords[i]));
				
			}
			int municipalityCount=i-firstMunicipalityIndex;
			for(;i<usernames.length*3/4;++i)
			{
				//add authorities using username[firstMunicipalityIndex+i%municipalityCount]
				// as creator
			}
			for(;i<usernames.length;++i)
			{
				//add citizen
			}
			
		}
		catch(Exception e)
		{
			fail("unexpected failure");
		}
 		
	}


	

	
}
