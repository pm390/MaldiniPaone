package maldiniPaone.servlets.managers.interfaces;

import java.util.List;

import maldiniPaone.databaseConnection.databaseExceptions.IllegalParameterException;
import maldiniPaone.databaseConnection.databaseExceptions.ServerSideDatabaseException;
import maldiniPaone.utilities.beans.Location;
import maldiniPaone.utilities.beans.users.Municipality;

/**
 * This interface defines the signatures of the methods to add and retrieve
 * suggestions
 **/
public interface ManageSuggestions {

	/**
	 * Adds a suggestion to the closest CityHall in a given location
	 * 
	 * @param suggestion the content of the suggestion
	 * @param location   the location where the suggestion is made
	 * @return true if it is successful , false otherwise
	 * @throws ServerSideDatabaseException if the database can't be found
	 * @throws IllegalParameterException   if the provided parameters aren't valid
	 **/
	public boolean addSuggestion(String suggestion, Location location)
			throws ServerSideDatabaseException, IllegalParameterException;

	/**
	 * Gets the suggestions given to a certain municipality
	 * 
	 * @param municipality the municipality who is searching the suggestions
	 * @return list of String containing the suggestions
	 * @throws ServerSideDatabaseException if the database can't be found
	 * @throws IllegalParameterException   if the provided parameters aren't valid
	 **/
	public List<String> getSuggestions(Municipality municipality)
			throws ServerSideDatabaseException, IllegalParameterException;
}
