package maldiniPaone.servlets.managers.interfaces;

import maldiniPaone.utilities.beans.Location;

public interface ManageNotification {

	public void notify(Integer id, Location location, String message);
}
