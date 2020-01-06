package maldiniPaone.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (Constants.VERBOSE)
			System.out.println("unexpected request :registration is post operation");
		response.sendError(400,
				"Bad Request" + ((Constants.VERBOSE) ? "method forbidden in registration servlet" : ""));
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
			if (Constants.VERBOSE) {
				System.out.println("registering");// debug
			}
			if (user == null && UserManager.getIstance().registerCitizen(username, password, email)) {
				if (Constants.VERBOSE) {
					System.out.println("registrering citizen");//debug
				}
				MailManager.getInstance().sendConfirmationMail(username, email);
			} else if (user == null) {
				// TODO send json object to indicate an a duplicate username or email
				// outputWriter.println(new Gson().toJson(message));
				return;
			} else if (user.getUserType() == UserType.Manager) {
				password = PasswordBuilder.GetRandomPassword();
				if (registerMunicipality(username, password, // utility static function
						email, request)) // parses request and registers municipality
				{
					MailManager.getInstance().sendConfirmationMail(username, password, email);
				} else {
					// TODO send json object to indicate an a duplicate username or email
					// outputWriter.println(new Gson().toJson(message));
					return;
				}
			} else {
				// TODO send json object to indicate an invalid request
				// outputWriter.println(new Gson().toJson(message));
				return;
			}
		} catch (ServerSideDatabaseException e) {
			if (Constants.VERBOSE) {
				e.printStackTrace();
			}
			// TODO send json object to indicate an error server side(5xx)
			// outputWriter.println(new Gson().toJson(message));
			return;
		} catch (IllegalParameterException e) {
			if (Constants.VERBOSE) {
				e.printStackTrace();
			}
			// TODO send json object to indicate an error in the parameters (4xx)
			// outputWriter.println(new Gson().toJson(message));
			return;
		}
		// TODO send json object to indicate a successful registration
		// outputWriter.println(new Gson().toJson(message));
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
