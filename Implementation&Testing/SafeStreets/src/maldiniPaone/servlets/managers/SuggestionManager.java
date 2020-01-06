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
import maldiniPaone.utilities.beans.Violation;
import maldiniPaone.utilities.beans.users.Municipality;

/**
 * Implements {@link ManageSuggestions} Singleton design pattern
 **/
public class SuggestionManager implements ManageSuggestions {
	// ================================================================================
	// instance
	// ================================================================================
	private static SuggestionManager instance;

	private SuggestionManager() {
	}
	// ================================================================================
	// Instantiator
	// ================================================================================

	/**
	 * Gets the instance of the Suggestion manager. Singleton design pattern
	 * 
	 * @return the instance
	 **/
	public static SuggestionManager getIstance() {
		return (instance == null) ? instance = new SuggestionManager() : instance;
	}

	@Override
	public boolean addSuggestion(String suggestion, Location location)
			throws ServerSideDatabaseException, IllegalParameterException {
		CityHall cityHall = DataAccessFacade.getInstance().getClosestCityHall(location);
		return DataAccessFacade.getInstance().addSuggestion(suggestion, cityHall);
	}

	@Override
	public List<String> getSuggestions(Municipality municipality)
			throws ServerSideDatabaseException, IllegalParameterException {
		List<String> suggestions = null;
		CityHall cityHall = municipality.getCityhall();
		suggestions = DataAccessFacade.getInstance().getStaticSuggestions(cityHall);
		if (suggestions != null && suggestions.size() >= Constants.SUGGESTION_MAX_SIZE) {
			return suggestions;
		} else if (suggestions == null) {
			suggestions = new ArrayList<String>();
		}
		try {
			suggestions.addAll(GenerateSuggestions(cityHall));
		} catch (Exception e) {
			if (Constants.VERBOSE) {
				e.printStackTrace();
			}
		}
		// add here code to get suggestion from accident data
		return suggestions;
	}

	private static List<String> GenerateSuggestions(CityHall cityHall)
			throws ServerSideDatabaseException, IllegalParameterException {
		List<String> result = new ArrayList<String>();
		List<Violation> violation = DataAccessFacade.getInstance().getViolations(cityHall);
		for (Violation v : violation) {
			//TODO check v.getCount() if value is high enough
			switch (v.getViolationType()) {
			case DoubleParking:
				/*
				 * TODO something like
				 * result.add("should add more parking area to reduce double parking")
				 */
				break;
			case ParkingWithForbiddingSignals:
				/*
				 * TODO something like result.
				 * add("should add more authorities to check area where parking is forbidden")
				 */
				break;
			case ReservedParking:
				/*
				 * TODO something like result.
				 * add("should add more authorities to check ReservedParking and areas around them"
				 * )
				 */
			default:
				break;
			}
		}
		return result;
	}

}
