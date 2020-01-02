package maldiniPaone.servlets.managers;

import maldiniPaone.servlets.managers.interfaces.ManageNotification;
import maldiniPaone.utilities.beans.Location;

public class NotificationManager implements ManageNotification {

	private NotificationManager() 
	{
		
	}
	
	private static NotificationManager instance;
	
	@Override
	public void notify(Integer id,Location location, String message) 
	{
		
	}



	public static NotificationManager getInstance()
	{
		return (instance==null)? instance=new NotificationManager():instance;
	}

}
