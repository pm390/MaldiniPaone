package maldiniPaone.databaseConnection;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import maldiniPaone.utilities.UserType;
import maldiniPaone.utilities.beans.Location;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserDatabaseTests {

	// may be loaded from a file for bigger tests
	static String[] citizenNames = { "angelo", "pietro", "luca", "matteo", "antonio", "calogero", "carciofo" };
	static String[] authorityNames = { "aldo", "annibale", "distruttore", "calzolaio", "alberigo", "ermenegildo",
			"caldo" };
	static String[] managerNames = { "a", "b", "c", "d", "e", "f", "h" };
	static String[] municipalityNames = { "i", "l", "m", "n", "o", "p", "q" };
	static String[] retriedNames = citizenNames;// reuse citizen names and try to add new user with those usernames
	static String[] passwords = { "a", "p", "l", "m", "a", "c", "c" };
	static String[] emailsCitizen = { "a@n", "p@i", "l@u", "m@a", "a@nt", "c@a", "c@ar" };
	static String[] emailsMunicipality = { "a", "b", "c", "d", "e", "f", "g" };
	static String[] emailsManager = { "h", "i", "l", "m", "n", "o", "p" };
	static String[] emailsAuthoryties = { "q", "r", "s", "t", "u", "v", "w" };
	static String[] venues = { "lombaria" };
	static String[] cityHallsNames = { "alserio", "erba", "anzano" };
	static String[] cityHallsProvinces = { "como", "lecco", "sondrio" };
	static Float[] latitudes = { 10f, 20f, 30f, 40f, 50f, 60f, 180f, 190f };
	static Float[] longitudes = { 10f, 20f, 30f, 40f, 50f, 60f, 120f, 200f, 300f };

	@BeforeClass
	public static void beforeTests() {
		System.out.println("to do with empty db");
	}

	//test user registration
	@Test
	public void testAddUsers() {
		// should give correct results
		Random rn = new Random();
		List<Location> locations = new ArrayList<Location>();
		//test location creation
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

		//test cityhall creation
		try {
			for (int j = 0; j < cityHallsNames.length; ++j) {
				int locationIndex = rn.nextInt(locations.size());

				if (!UserDatabaseConnector.addCityhall(cityHallsNames[j], cityHallsProvinces[j], "lombardia",
						locations.get(locationIndex))) {
					fail("all cityhall should be created");
				}

			}
			//test cityhall recreation
			for (int j = 0; j < cityHallsNames.length; ++j) {
				int locationIndex = rn.nextInt(locations.size());

				if (UserDatabaseConnector.addCityhall(cityHallsNames[j], cityHallsProvinces[j], "lombardia",
						locations.get(locationIndex))) {
					fail("all cityhall are duplicates");
				}

			}
			
			//distrcit creation test
			List<Integer> distretti = new ArrayList<Integer>();
			distretti.add(UserDatabaseConnector.addDistrict(cityHallsNames[0], cityHallsProvinces[0], locations.get(0),
					locations.get(0)));
			for (int i = 0; i < 15; ++i) {
				int locationTopLeft = rn.nextInt(locations.size());
				int locationBottomRight = rn.nextInt(locations.size());
				distretti.add(UserDatabaseConnector.addDistrict(cityHallsNames[0], cityHallsProvinces[0],
						locations.get(locationTopLeft), locations.get(locationBottomRight)));
			}
			//citizen creation
			for (int i = 0; i < citizenNames.length; ++i)// add system managers
			{
				//create citizen
				assertEquals(true, UserDatabaseConnector.addCitizen(citizenNames[i], passwords[i], emailsCitizen[i]));
				//retry
				assertEquals(false, // trying to re-add user should result in failure
						UserDatabaseConnector.addCitizen(citizenNames[i], passwords[i], emailsCitizen[i]));
				//check if available
				assertEquals("created user should be municipality", UserType.Citizen,
						UserDatabaseConnector.checkUserCredentials(citizenNames[i], passwords[i]));
			}
			
			
			for (int i = 0; i < managerNames.length; ++i)// add system managers
			{
				//create manager
				assertEquals(true, UserDatabaseConnector.addManager(managerNames[i], passwords[i], emailsManager[i],
						venues[i % venues.length]));
				//recreate manager
				assertEquals(false, // trying to re-add user should result in failure
						UserDatabaseConnector.addManager(managerNames[i], passwords[i], emailsManager[i],
								venues[i % venues.length]));
				//add manager with already used name
				assertEquals(false, // trying to re-add user should result in failure
						UserDatabaseConnector.addManager(retriedNames[i], passwords[i], emailsManager[i],
								venues[i % venues.length]));
				//check if manager is created
				assertEquals("created user should be managers", UserType.Manager,
						UserDatabaseConnector.checkUserCredentials(managerNames[i], passwords[i]));
			}
			for (int i = 0; i < municipalityNames.length; ++i)// add system managers
			{
				//add municipality
				assertEquals(true,
						UserDatabaseConnector.addMunicipalityByManager(municipalityNames[i], passwords[i],
								emailsMunicipality[i], cityHallsNames[i % cityHallsNames.length],
								cityHallsProvinces[i % cityHallsProvinces.length]));
				//re add
				assertEquals(false, // trying to re-add user should result in failure
						UserDatabaseConnector.addMunicipalityByManager(municipalityNames[i], passwords[i],
								emailsMunicipality[i], cityHallsNames[i % cityHallsNames.length],
									cityHallsProvinces[i % cityHallsProvinces.length]));
				//reuse name
				assertEquals(false, // trying to re-add user should result in failure
						UserDatabaseConnector.addMunicipalityByManager(retriedNames[i], passwords[i],
								emailsMunicipality[i], cityHallsNames[i % cityHallsNames.length],
								cityHallsProvinces[i % cityHallsProvinces.length]));
				//check if municipality is created
				assertEquals("created user should be municipality", UserType.Municipality,
						UserDatabaseConnector.checkUserCredentials(municipalityNames[i], passwords[i]));
			}
			for (int i = 0; i < authorityNames.length; ++i)// add system managers
			{
				int randIndex = rn.nextInt(distretti.size());
				System.out.println(distretti.size());
				//create authority
				assertEquals(true, UserDatabaseConnector.addAuthority(authorityNames[i], passwords[i],
						emailsAuthoryties[i], municipalityNames[i], distretti.get(randIndex)));
				//re create
				assertEquals(false, // trying to re-add user should result in failure
						UserDatabaseConnector.addAuthority(authorityNames[i], passwords[i], emailsAuthoryties[i],
				municipalityNames[i], distretti.get(randIndex)));
				//add user with already used username
				assertEquals(false, // trying to re-add user should result in failure
						UserDatabaseConnector.addAuthority(retriedNames[i], passwords[i], emailsAuthoryties[i],
								municipalityNames[i], distretti.get(randIndex)));
				//check if authority is created
				assertEquals("created user should be municipality", UserType.Authority,
						UserDatabaseConnector.checkUserCredentials(authorityNames[i], passwords[i]));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			fail("unexpected failure");
		}

	}

}
