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
 * Servlet implementation class RegistrationByManager
 */
@WebServlet("/RegistrationByManager")
public class RegistrationByManager extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegistrationByManager() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// set output to json format
		PrintWriter outputWriter = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		User user = (User) request.getSession(true).getAttribute("user");
		if (user == null || // short circuit
				user.getUserType() != UserType.Manager) {
			GenericResponse message = new GenericResponse(400, "invalid access");
			outputWriter.println(new Gson().toJson(message));
			outputWriter.close();
			return;
		}
		// get needed data
		String username = (String) request.getParameter("username");
		String password = PasswordBuilder.GetRandomPassword();
		String email = (String) request.getParameter("email");
		String targetUserType = (String) request.getParameter("userType");
		try {
			if (targetUserType != null) {
				switch (UserType.fromString(targetUserType)) {
				case Municipality:
					if (registerMunicipality(username, password, // utility static function
							email, request)) // parses request and registers municipality
					{
						MailManager.getInstance().sendConfirmationMail(username, password, email);
					} else {
						GenericResponse message = new GenericResponse(400, "already exist");
						outputWriter.println(new Gson().toJson(message));
						outputWriter.close();
						return;
					}
					break;
				case Manager:
					String venueName=request.getParameter("venue");
					UserManager.getIstance().registerManager(username, password, email, venueName);
					break;
				default:
					GenericResponse message = new GenericResponse(400, "invalid parameters");
					outputWriter.println(new Gson().toJson(message));
					outputWriter.close();
					return;
				}
			}
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
