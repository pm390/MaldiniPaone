package maldiniPaone.servlets.managers.interfaces;

import java.util.List;

import maldiniPaone.databaseConnection.databaseExceptions.IllegalParameterException;
import maldiniPaone.databaseConnection.databaseExceptions.ServerSideDatabaseException;
import maldiniPaone.utilities.beans.Location;
import maldiniPaone.utilities.beans.users.Municipality;
//TODO javadocs
public interface ManageSuggestions {

	public boolean addSuggestion(String suggestion,Location location) throws ServerSideDatabaseException, IllegalParameterException;
	
	public List<String> getSuggestions(Municipality municipality)
			throws ServerSideDatabaseException, IllegalParameterException;
}
