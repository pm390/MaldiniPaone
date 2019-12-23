package maldiniPaone.servlets.managers;

import java.util.ArrayList;
import java.util.List;

import maldiniPaone.constants.Constants;
import maldiniPaone.databaseConnection.DataAccessFacade;
import maldiniPaone.databaseConnection.databaseExceptions.IllegalParameterException;
import maldiniPaone.databaseConnection.databaseExceptions.ServerSideDatabaseException;
import maldiniPaone.servlets.managers.interfaces.ManageSuggestions;
import maldiniPaone.utilities.beans.CityHall;
import maldiniPaone.utilities.beans.Location;
import maldiniPaone.utilities.beans.users.Municipality;

public class SuggestionManager implements ManageSuggestions
{

	@Override
	public boolean addSuggestion(String suggestion, Location location) throws ServerSideDatabaseException, IllegalParameterException 
	{
		CityHall cityHall=DataAccessFacade.getInstance().getClosestCityHall(location);
		return DataAccessFacade.getInstance().addSuggestion(suggestion,cityHall);
	}

	@Override
	public List<String> getSuggestions(Municipality municipality) 
			throws ServerSideDatabaseException, IllegalParameterException 
	{
		List<String> suggestions=null;
		suggestions=DataAccessFacade.getInstance().getStaticSuggestions(municipality.getCityhall());
		if(suggestions!=null&&suggestions.size()>=Constants.SUGGESTION_MAX_SIZE)
		{
			return suggestions;
		}
		else if(suggestions==null)
		{
			suggestions=new ArrayList<String>();
		}
		suggestions.addAll(GenerateSuggestions(municipality.getLocation()));
		return suggestions;
	}
	
	
	private static List<String> GenerateSuggestions(Location location)
	{
		List<String> result=new ArrayList<String>();
		//TODO think how to use results
		return result;
	}

}
