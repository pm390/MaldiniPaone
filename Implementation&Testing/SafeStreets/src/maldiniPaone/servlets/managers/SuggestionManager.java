package maldiniPaone.servlets.managers;

import java.util.ArrayList;
import java.util.List;

import maldiniPaone.databaseConnection.DataAccessFacade;
import maldiniPaone.databaseConnection.databaseExceptions.IllegalParameterException;
import maldiniPaone.databaseConnection.databaseExceptions.ServerSideDatabaseException;
import maldiniPaone.servlets.managers.interfaces.ManageSuggestions;
import maldiniPaone.utilities.beans.CityHall;
import maldiniPaone.utilities.beans.Location;
import maldiniPaone.utilities.beans.Violation;
import maldiniPaone.utilities.beans.users.Municipality;
import maldiniPaone.utilities.constants.Constants;

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
			if (v.getCount() < Constants.VIOLATION_SEVERITY_LIMIT)
				continue;
			switch (v.getViolationType()) {
			case DoubleParking:
				result.add("Aggiungere parcheggi : i parcheggi in doppia fila sono frequenti");
				break;
			case ParkingWithForbiddingSignals:
				result.add("Controllare le zone con divieto di parcheggio : "
						+ "i parcheggi in zona vietata sono frequenti");
				break;
			case ReservedParking:
				result.add("Controllare le zone riservate : i parcheggi in zona riservata sono frequenti");
			default:
				break;
			}
		}
		return result;
	}

}
