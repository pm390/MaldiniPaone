package maldiniPaone.servlets.managers.interfaces;


import maldiniPaone.utilities.beans.Location;
import maldiniPaone.utilities.beans.users.Authority;

/**
 * This interface defines the signatures of the methods to save and update an
 * authority position
 **/
public interface ManageAuthorityPosition {

	/**
	 * Adds the position of an authority to the system
	 * 
	 * @param authority the authority whose position must be saved
	 * @param location  : the coordinates added to the system
	 * @return true if the insertion is successful false otherwise
	 **/
	public boolean addPosition(Authority authority, Location location);

	/**
	 * Update the position of an authority saved on the system
	 * 
	 * @param authority the authority whose position must be updated
	 * @param location  : the coordinates to be set after the update
	 * @return true if the update is successful false otherwise
	 **/
	public boolean updatePosition(Authority authority, Location location);
	/**
	 * Deletes the position of an authority saved on the system
	 * 
	 * @param authority the authority whose position must be deleted
	 **/
	public void removeAuthority(Authority authority);

}
