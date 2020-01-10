package maldiniPaone.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import maldiniPaone.databaseConnection.databaseExceptions.IllegalParameterException;
import maldiniPaone.databaseConnection.databaseExceptions.ServerSideDatabaseException;
import maldiniPaone.servlets.managers.MailManager;
import maldiniPaone.servlets.managers.UserManager;
import maldiniPaone.utilities.PasswordBuilder;
import maldiniPaone.utilities.UserType;
import maldiniPaone.utilities.ResponseObjects.GenericResponse;
import maldiniPaone.utilities.beans.CityHall;
import maldiniPaone.utilities.beans.Location;
import maldiniPaone.utilities.beans.users.Municipality;
import maldiniPaone.utilities.beans.users.User;
import maldiniPaone.utilities.constants.Constants;

/**
 * Servlet implementation class RegistrationByMunicipality
 */
@WebServlet("/RegistrationByMunicipality")
public class RegistrationByMunicipality extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegistrationByMunicipality() {
		super();
		// TODO Auto-generated constructor stub
	}
	/*
	 * @Override protected void doGet(HttpServletRequest request,
	 * HttpServletResponse response) throws ServletException, IOException {
	 * if(Constants.VERBOSE)System.out.
	 * println("unexpected request : registration is post operation");
	 * response.sendError(400,
	 * "Bad Request"+((Constants.VERBOSE)?"method forbidden in registration servlet"
	 * :"")); }
	 */

	@Override
	/**
	 * Register Authority or Municipality done by a System Manager
	 **/
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// set output to json format
		PrintWriter outputWriter = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		User user = (User) request.getSession(true).getAttribute("user");
		if (user == null || // short circuit
				user.getUserType() != UserType.Municipality) {
			GenericResponse message = new GenericResponse(400,"invalid access");
			outputWriter.println(new Gson().toJson(message));
			outputWriter.close();
			return;
		}
		// here we have user != null and for sure a municipality
		CityHall cityHall = ((Municipality) user).getCityhall();

		// get needed data
		String username = (String) request.getParameter("username");
		String password = PasswordBuilder.GetRandomPassword();
		String email = (String) request.getParameter("email");
		String targetUserType = (String) request.getParameter("userType");
		try {
			email=email.toLowerCase();
			if (cityHall == null)
			// if the session doesn't contain already the cityhall of the municipality it
			// must be retrieved
			{
				cityHall = UserManager.getIstance().getCityHall(user.getUsername());
				((Municipality) user).setCityhall(cityHall);

				if (Constants.VERBOSE)
					System.out.println(cityHall.toString());// debug
			}
			if (targetUserType.equals(UserType.Municipality.toString())) {

				if (UserManager.getIstance().registerMunicipalityByMunicipality(username, password, email,
						user.getUsername(), cityHall.getName(), cityHall.getProvince())) {
					MailManager.getInstance().sendConfirmationMail(username, password, email);
				} else {
					GenericResponse message = new GenericResponse(400,"already exist");
					outputWriter.println(new Gson().toJson(message));
					outputWriter.close();
					return;
				}
			} else if (targetUserType.equals(UserType.Authority.toString())) {
				if (Constants.VERBOSE)
					System.out.println("register authority");

				
				if (registerAuthority(username, password, email, (Municipality) user, request)) {
					MailManager.getInstance().sendConfirmationMail(username, password, email);
				} else {
					GenericResponse message = new GenericResponse(400,"already exist");
					outputWriter.println(new Gson().toJson(message));
					outputWriter.close();
					return;
				}
			}
		} catch (ServerSideDatabaseException e) {
			if (Constants.VERBOSE) {
				e.printStackTrace();
			}
			GenericResponse message = new GenericResponse(500,"Server side error");
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
	private static boolean registerAuthority(String username, String password, String email, Municipality creator,
			HttpServletRequest request) throws ServerSideDatabaseException, IllegalParameterException {
		CityHall cityhall = creator.getCityhall();
		try
		{
			Float topLeftLatitude = Float.parseFloat(request.getParameter("tLLatitude"));
			Float topLeftLongitude = Float.parseFloat(request.getParameter("tLLongitude"));
			Location topLeftLocation = new Location();
			topLeftLocation.setLatitude(topLeftLatitude);
			topLeftLocation.setLongitude(topLeftLongitude);
	
			Float bottomRightLatitude = Float.parseFloat(request.getParameter("bRLatitude"));
			Float bottomRightLongitude = Float.parseFloat(request.getParameter("bRLongitude"));
			Location bottomRightLocation = new Location();
			bottomRightLocation.setLatitude(bottomRightLatitude);
			bottomRightLocation.setLongitude(bottomRightLongitude);
			return UserManager.getIstance().registerAuthority(username, password, email, creator.getUsername(),
					cityhall.getName(), cityhall.getProvince(), topLeftLocation, bottomRightLocation);
		}
		catch(NumberFormatException e)//value which should have float inside aren't valid
		{
			throw new IllegalParameterException();
		}		
	}

}
