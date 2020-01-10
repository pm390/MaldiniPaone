package maldiniPaone.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import maldiniPaone.databaseConnection.databaseExceptions.IllegalParameterException;
import maldiniPaone.databaseConnection.databaseExceptions.ServerSideDatabaseException;
import maldiniPaone.servlets.managers.SuggestionManager;
import maldiniPaone.servlets.managers.UserManager;
import maldiniPaone.utilities.UserType;
import maldiniPaone.utilities.ResponseObjects.GenericResponse;
import maldiniPaone.utilities.ResponseObjects.SuggestionResponse;
import maldiniPaone.utilities.beans.CityHall;
import maldiniPaone.utilities.beans.Location;
import maldiniPaone.utilities.beans.users.Municipality;
import maldiniPaone.utilities.beans.users.User;
import maldiniPaone.utilities.constants.Constants;

/**
 * Servlet implementation class SuggestionServlet
 */
@WebServlet("/SuggestionServlet")
public class SuggestionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SuggestionServlet() {
		super();
		// 
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter outputWriter = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		User user = (User) request.getSession(true).getAttribute("user");
		if (user == null || // short circuit
				user.getUserType() != UserType.Municipality) {
			GenericResponse message = new GenericResponse(400, "invalid access");
			outputWriter.println(new Gson().toJson(message));
			outputWriter.close();
			return;
		}
		List<String> suggestions=null;
		// here we have user != null and for sure a municipality
		CityHall cityHall = ((Municipality) user).getCityhall();
		try {
			if (cityHall == null)
			// if the session doesn't contain already the cityhall of the municipality it
			// must be retrieved
			{
				cityHall = UserManager.getIstance().getCityHall(user.getUsername());
				((Municipality) user).setCityhall(cityHall);
				if (Constants.VERBOSE)
					System.out.println(cityHall.toString());// debug
			}
			
			suggestions=SuggestionManager.getIstance().getSuggestions((Municipality)user);
			
		} catch (ServerSideDatabaseException e) {
			if (Constants.VERBOSE) {
				e.printStackTrace();
			}
			GenericResponse message = new GenericResponse(500, "Server side error");
			outputWriter.println(new Gson().toJson(message));
			outputWriter.close();
			return;
		} catch (IllegalParameterException e) {
			if (Constants.VERBOSE) {
				e.printStackTrace();
			}
			GenericResponse message = new GenericResponse(400, "invalid parameters");
			outputWriter.println(new Gson().toJson(message));
			outputWriter.close();
			return;
		} catch (Exception e) {
			if (Constants.VERBOSE) {
				e.printStackTrace();
			}
			// debug purpouse
			return;
		}
		SuggestionResponse message = new SuggestionResponse(suggestions);
		outputWriter.println(new Gson().toJson(message));
		outputWriter.close();
		return;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter outputWriter = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		User user = (User) request.getSession(true).getAttribute("user");
		if (user == null) {
			GenericResponse message = new GenericResponse(400, "invalid access");
			outputWriter.println(new Gson().toJson(message));
			outputWriter.close();
			return;
		}
		Float latitude = Float.parseFloat(request.getParameter("latitude"));
		Float longitude = Float.parseFloat(request.getParameter("longitude"));
		String suggestion= request.getParameter("suggestion");
		try {
			Location location=new Location();
			location.setLatitude(latitude);
			location.setLongitude(longitude);
			SuggestionManager.getIstance().addSuggestion(suggestion, location);
		} catch (ServerSideDatabaseException e) {
			if (Constants.VERBOSE) {
				e.printStackTrace();
			}
			GenericResponse message = new GenericResponse(500, "Server side error");
			outputWriter.println(new Gson().toJson(message));
			outputWriter.close();
			return;
		} catch (IllegalParameterException e) {
			if (Constants.VERBOSE) {
				e.printStackTrace();
			}
			GenericResponse message = new GenericResponse(400, "invalid parameters");
			outputWriter.println(new Gson().toJson(message));
			outputWriter.close();
			return;
		} catch (Exception e) {
			if (Constants.VERBOSE) {
				e.printStackTrace();
			}
			// debug purpouse
			return;
		}
		GenericResponse message = new GenericResponse();
		outputWriter.println(new Gson().toJson(message));
		outputWriter.close();
		return;
	}

}
