package maldiniPaone.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import maldiniPaone.ResponseObjects.GenericResponse;
import maldiniPaone.constants.Constants;
import maldiniPaone.databaseConnection.databaseExceptions.IllegalParameterException;
import maldiniPaone.databaseConnection.databaseExceptions.ServerSideDatabaseException;
import maldiniPaone.servlets.managers.MailManager;
import maldiniPaone.servlets.managers.UserManager;
import maldiniPaone.utilities.PasswordBuilder;
import maldiniPaone.utilities.UserType;
import maldiniPaone.utilities.beans.Location;
import maldiniPaone.utilities.beans.users.User;

/**
 * Servlet implementation class Registration
 */
@WebServlet("/Registration")
public class Registration extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Registration() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	/**
	 * Register Citizen or Municipality done by a System Manager
	 **/
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// set output to json format
		PrintWriter outputWriter = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		// get needed data
		String username = (String) request.getParameter("username");
		String password = (String) request.getParameter("password");
		String email = (String) request.getParameter("email");
		User user = (User) request.getSession(true).getAttribute("user");
		try {
			email=email.toLowerCase();
			if (Constants.VERBOSE) {
				System.out.println("registering");// debug
			}
			if (user == null && UserManager.getIstance().registerCitizen(username, password, email)) {
				if (Constants.VERBOSE) {
					System.out.println("registrering citizen");//debug
				}
				MailManager.getInstance().sendConfirmationMail(username, email);
			} else if (user == null) {
				GenericResponse message = new GenericResponse(400,"already exist");
				outputWriter.println(new Gson().toJson(message));
				outputWriter.close();
				return;
			} else {
				GenericResponse message = new GenericResponse(400,"invalid access");
				outputWriter.println(new Gson().toJson(message));
				outputWriter.close();
				return;
			}
		} catch (ServerSideDatabaseException e) {
			if (Constants.VERBOSE) {
				e.printStackTrace();
			}
			GenericResponse message = new GenericResponse(500,"server error");
			outputWriter.println(new Gson().toJson(message));
			outputWriter.close();
			return;
		} catch (IllegalParameterException e) {
			if (Constants.VERBOSE) {
				e.printStackTrace();
			}
			GenericResponse message = new GenericResponse(400,"invalid parameters");
			outputWriter.println(new Gson().toJson(message));
			outputWriter.close();
			return;
		}
		GenericResponse message = new GenericResponse();
		outputWriter.println(new Gson().toJson(message));
		outputWriter.close();
		return;
	}

	// ================================================================================
	// Utility functions
	// ================================================================================
	// TODO javadoc here
	private static boolean registerMunicipality(String username, String password, String email,
			HttpServletRequest request) throws ServerSideDatabaseException, IllegalParameterException {
		String cityHallName = (String) request.getParameter("cityHallName");
		String cityHallProvince = (String) request.getParameter("cityHallProvince");
		String region = (String) request.getParameter("region");
		Float latitude = Float.parseFloat(request.getParameter("latitude"));
		Float longitude = Float.parseFloat(request.getParameter("longitude"));
		Location location = new Location();
		location.setLatitude(latitude);
		location.setLongitude(longitude);
		return UserManager.getIstance().registerMunicipalityByManager(username, password, email, cityHallName,
				cityHallProvince, region, location);
	}

}
