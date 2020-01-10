package maldiniPaone.utilities.beans.users;

import maldiniPaone.utilities.UserType;
/**
 * Extends {@link maldiniPaone.utilities.beans.users.User}.
 * Represents an authority
 **/
public class Authority extends User 
{
	
	private Integer LastLocationIndex=-1;

	//================================================================================
    // userType
    //================================================================================
	@Override
	public UserType getUserType()
	{
		return UserType.Authority;
	}

	/**
	 * @return the lastLocationIndex
	 */
	public Integer getLastLocationIndex() {
		return LastLocationIndex;
	}

	/**
	 * @param lastLocationIndex the lastLocationIndex to set
	 */
	public void setLastLocationIndex(Integer lastLocationIndex) {
		LastLocationIndex = lastLocationIndex;
	}
	
}
