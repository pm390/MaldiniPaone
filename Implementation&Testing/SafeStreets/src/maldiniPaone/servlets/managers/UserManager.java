package maldiniPaone.servlets.managers;

import maldiniPaone.databaseConnection.DataAccessFacade;
import maldiniPaone.databaseConnection.databaseExceptions.IllegalParameterException;
import maldiniPaone.databaseConnection.databaseExceptions.ServerSideDatabaseException;
import maldiniPaone.servlets.managers.interfaces.ManageAccountModification;
import maldiniPaone.servlets.managers.interfaces.ManageAuthorityPosition;
import maldiniPaone.servlets.managers.interfaces.ManageLogin;
import maldiniPaone.servlets.managers.interfaces.ManageRegistration;
import maldiniPaone.servlets.managers.interfaces.ManageRegistrationByManager;
import maldiniPaone.servlets.managers.interfaces.ManageRegistrationByMunicipalities;
import maldiniPaone.servlets.managers.interfaces.ManageUserDataRetrieve;
import maldiniPaone.utilities.UserType;
import maldiniPaone.utilities.beans.CityHall;
import maldiniPaone.utilities.beans.District;
import maldiniPaone.utilities.beans.Location;
import maldiniPaone.utilities.beans.users.Authority;

/**
 * Implements {@link ManageAccountModification} ,
 * {@link ManageAuthorityPosition} , {@link ManageLogin} ,
 * {@link ManageRegistration}, {@link ManageRegistrationByMunicipalities}
 * ,{@link ManageRegistrationByManager} and {@link ManageUserDataRetrieve}.
 * Singleton design pattern
 **/
public class UserManager implements ManageAccountModification, ManageAuthorityPosition, ManageLogin, ManageRegistration,
		ManageRegistrationByMunicipalities, ManageUserDataRetrieve, ManageRegistrationByManager {
	// ================================================================================
	// instance
	// ================================================================================

	private static UserManager instance;

	private UserManager() {
	}
	// ================================================================================
	// Instantiator
	// ================================================================================

	/**
	 * Gets the instance of the UserManager. Singleton design pattern
	 * 
	 * @return the instance
	 **/
	public static UserManager getIstance() {
		return (instance == null) ? instance = new UserManager() : instance;
	}

	// ================================================================================
	// Manage Login
	// ================================================================================

	@Override
	public UserType login(String username, String password)
			throws ServerSideDatabaseException, IllegalParameterException {
		return DataAccessFacade.getInstance().checkUserCredentials(username, password);
	}

	// ================================================================================
	// Manage Citizen Registration
	// ================================================================================

	@Override
	public boolean registerCitizen(String username, String password, String email)
			throws ServerSideDatabaseException, IllegalParameterException {
		return DataAccessFacade.getInstance().addCitizen(username, password, email);
	}

	// ================================================================================
	// Manage Municipality Registration
	// ================================================================================

	@Override
	public boolean registerMunicipalityByManager(String username, String password, String email, String cityHallName,
			String cityHallProvince, String region, Location location)
			throws ServerSideDatabaseException, IllegalParameterException {
		CityHall cityHall = new CityHall();
		cityHall.setName(cityHallName);
		cityHall.setLocation(location);
		cityHall.setProvince(cityHallProvince);
		cityHall.setRegion(region);
		return DataAccessFacade.getInstance().addMunicipalityAndCityHall(username, password, email, null, cityHall);
	}

	@Override
	public boolean registerMunicipalityByMunicipality(String username, String password, String email, String creator,
			String cityHallName, String cityHallProvince)
			throws ServerSideDatabaseException, IllegalParameterException {
		CityHall cityHall = new CityHall();
		cityHall.setName(cityHallName);
		cityHall.setProvince(cityHallProvince);
		return DataAccessFacade.getInstance().addMunicipality(username, password, email, creator, cityHall);
	}

	// ================================================================================
	// Manage Authority Registration
	// ================================================================================
	@Override
	public boolean registerAuthority(String username, String password, String email, String creator,
			String cityHallName, String cityHallProvince, Location topLeftDistrictBound,
			Location bottomRightDistrictBound) throws ServerSideDatabaseException, IllegalParameterException {

		District district = new District();
		System.out.println(district);
		district.setLocationTopLeft(topLeftDistrictBound);
		district.setLocationBottomRight(bottomRightDistrictBound);
		district.setName(cityHallName);
		district.setProvince(cityHallProvince);

		return DataAccessFacade.getInstance().addAuthority(username, password, email, creator, district);
	}

	// ================================================================================
	// Manage Manager Registration
	// ================================================================================
	@Override
	public boolean registerManager(String username, String password, String email, String venue)
			throws ServerSideDatabaseException, IllegalParameterException {
		return DataAccessFacade.getInstance().addManager(username, password, email, venue);
	}
	// ================================================================================
	// Manage account modification
	// ================================================================================

	@Override
	public boolean modifyUserCredentials(String oldUsername, String oldPassword, String newUsername, String newPassword,
			String newEmail, UserType user) throws ServerSideDatabaseException, IllegalParameterException {
		return DataAccessFacade.getInstance().modifyUser(oldUsername, oldPassword, user, newEmail, newUsername,
				newPassword);
	}

	@Override
	public boolean removeUser(String username, String password, UserType user)
			throws ServerSideDatabaseException, IllegalParameterException {
		return DataAccessFacade.getInstance().removeUser(username, password, user);
	}

	@Override
	public boolean forgotPassword(String oldUsername, UserType user, String newPassword)
			throws ServerSideDatabaseException, IllegalParameterException {
		return DataAccessFacade.getInstance().forgotPassword(oldUsername, user, newPassword);
	}

	// ================================================================================
	// Manage authority position
	// ================================================================================
	@Override
	public boolean addPosition(Authority authority, Location location) {
		boolean res=AuthorityLocation.getInstance().addAuthorities(authority, location);
		if(res)
		{
			authority.setLastLocationIndex(AuthorityLocation.getInstance().getPositionIndex(location));
		}
		return res;
	}

	@Override
	public boolean updatePosition(Authority authority, Location location)  {
		if(authority.getLastLocationIndex().equals(AuthorityLocation.getInstance().getPositionIndex(location)))
		{
			return true;
		}
		AuthorityLocation.getInstance().removeAuthority(authority,authority.getLastLocationIndex());
		return AuthorityLocation.getInstance().addAuthorities(authority, location);
	}
	@Override
	public void removeAuthority(Authority authority)
	{
		AuthorityLocation.getInstance().removeAuthority(authority,authority.getLastLocationIndex());
		authority.setLastLocationIndex(-1);
	}
	
	// ================================================================================
	// Manage User Data Retrieve
	// ================================================================================

	@Override
	public String findEmailByUsername(String username) throws ServerSideDatabaseException, IllegalParameterException {
		return DataAccessFacade.getInstance().findEmailByUsername(username);
	}

	@Override
	public String findUsernameByEmail(String email) throws ServerSideDatabaseException, IllegalParameterException {
		return DataAccessFacade.getInstance().findUsernameByEmail(email);
	}

	@Override
	public CityHall getCityHall(String username) throws ServerSideDatabaseException, IllegalParameterException {
		return DataAccessFacade.getInstance().getCityHall(username);
	}

}
