package maldiniPaone.servlets.managers;

import java.util.ArrayList;
import java.util.List;

import maldiniPaone.utilities.beans.Location;
import maldiniPaone.utilities.beans.users.Authority;
import maldiniPaone.utilities.constants.Constants;

public class AuthorityLocation {

	private static AuthorityLocation instance=new AuthorityLocation();

	private AuthorityLocation() {
		heightEdge = (topLeftLatitude - bottomRightLatitude) / Constants.AUTHORITY_LOCATION_GRANULARITY;
		widthEdge = (topLeftLongitude - bottomRightLongitude) / Constants.AUTHORITY_LOCATION_GRANULARITY;
		for (int i = 0; i < Constants.AUTHORITY_LOCATION_GRANULARITY; ++i) {
			for (int j = 0; j < Constants.AUTHORITY_LOCATION_GRANULARITY; ++j) {
				// for each square in the area create an array that can hold authorities
				List<Authority> temp = new ArrayList<Authority>();
				autorities.add(temp);
			}
		}
		// create one for authorities outside the given area
		List<Authority> temp = new ArrayList<Authority>();
		autorities.add(temp);
	}

	public List<Authority> getAuthorities(Location loc) {
		if (loc.getLongitude() < bottomRightLongitude && loc.getLongitude() > topLeftLongitude && // longitude in the
																									// square
				loc.getLatitude() < topLeftLatitude && loc.getLatitude() > bottomRightLatitude) {
			int leftShift = (int) Math.floor(loc.getLatitude() / widthEdge);
			int downShift = (int) Math.floor(loc.getLongitude() / heightEdge);
			int index = leftShift + downShift * Constants.AUTHORITY_LOCATION_GRANULARITY;
			return autorities.get(index);
		}
		return autorities.get(autorities.size() - 1);
	}

	public Integer getPositionIndex(Location loc) {
		if (loc.getLongitude() < bottomRightLongitude && loc.getLongitude() > topLeftLongitude && // longitude in the
		// square
				loc.getLatitude() < topLeftLatitude && loc.getLatitude() > bottomRightLatitude) {
			int leftShift = (int) Math.floor(loc.getLatitude() / widthEdge);
			int downShift = (int) Math.floor(loc.getLongitude() / heightEdge);
			int index = leftShift + downShift * Constants.AUTHORITY_LOCATION_GRANULARITY;
			return index;
		}
		return autorities.size() - 1;
	}

	public boolean addAuthorities(Authority authority, Location loc) {
		List<Authority> temp;
		if (loc.getLongitude() < bottomRightLongitude && loc.getLongitude() > topLeftLongitude && // longitude in the
																									// square
				loc.getLatitude() < topLeftLatitude && loc.getLatitude() > bottomRightLatitude) {
			int leftShift = (int) Math.floor(loc.getLatitude() / widthEdge);
			int downShift = (int) Math.floor(loc.getLongitude() / heightEdge);
			int index = leftShift + downShift * Constants.AUTHORITY_LOCATION_GRANULARITY;
			temp = autorities.get(index);
			synchronized (temp) {//on the sublist to avoid blocking the entire list
				return temp.add(authority);
			}
		}
		temp = autorities.get(autorities.size() - 1);
		synchronized (temp) {//on the sublist to avoid blocking the entire list
			return temp.add(authority);
		}
	}

	public static AuthorityLocation getInstance() {
		return instance; //use static initialization process to avoid synchronization issues
	}

	private Float heightEdge;

	private Float widthEdge;

	private static List<List<Authority>> autorities = new ArrayList<List<Authority>>();

	private float topLeftLatitude = 46.91f;

	private float topLeftLongitude = 6.10f;

	private float bottomRightLatitude = 36.68f;

	private float bottomRightLongitude = 19.37f;

	public void removeAuthority(Authority authority, Integer lastLocationIndex) {
		if(lastLocationIndex>autorities.size()-1)
		{
			return;
		}
		List<Authority> temp =autorities.get(lastLocationIndex);
		synchronized(temp)//on the sublist to avoid blocking the entire list 
		{
		temp.remove(authority);
		}
	}
}
