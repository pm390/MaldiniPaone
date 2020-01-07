package maldiniPaone.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import maldiniPaone.ResponseObjects.LoginResponse;
import maldiniPaone.constants.Constants;
import maldiniPaone.databaseConnection.databaseExceptions.IllegalParameterException;
import maldiniPaone.databaseConnection.databaseExceptions.ServerSideDatabaseException;
import maldiniPaone.servlets.managers.UserManager;
import maldiniPaone.utilities.UserType;
import maldiniPaone.utilities.beans.users.User;
import maldiniPaone.utilities.beans.users.UserFactory;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Login() {
		super();
		// TODO Auto-generated constructor stub
	}

	/*
	 * @Override protected void doGet(HttpServletRequest request,
	 * HttpServletResponse response) throws ServletException, IOException {
	 * if(Constants.VERBOSE)System.out.
	 * println("unexpected request login is post operation");
	 * response.sendError(400,
	 * "Bad Request"+((Constants.VERBOSE)?"method forbidden in login servlet":""));
	 * }
	 */

	@Override
	/**
	 * Login to the service
	 **/
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// set output to json format
		PrintWriter outputWriter = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		// get needed information
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		UserType userType;
		try {
			userType = UserManager.getIstance().login(username, password);
			if (userType != null) {
				// build user base informations into an object when additional information are
				// needed
				// they will be retrieved and added to the object created for reusing them
				User user = UserFactory.buildUserBase(username, password, userType);
				// save the user object in the session
				request.getSession().setAttribute("user", user);
			} else {
				LoginResponse message = new LoginResponse(404,"user not found");
				outputWriter.println(new Gson().toJson(message));
				outputWriter.close();
				return;
			}
		} catch (ServerSideDatabaseException e) {
			if (Constants.VERBOSE) {
				e.printStackTrace();
			}
			LoginResponse message = new LoginResponse(500,"Database not accessible");
			outputWriter.println(new Gson().toJson(message));
			outputWriter.close();
			return;
		} catch (IllegalParameterException e) {
			if (Constants.VERBOSE) {
				e.printStackTrace();
			}
			LoginResponse message = new LoginResponse(500,"Database not accessible");
			outputWriter.println(new Gson().toJson(message));
			outputWriter.close();
			return;
		}
		LoginResponse message = new LoginResponse(userType.toString());
		outputWriter.println(new Gson().toJson(message));
		outputWriter.close();
		return;
}

}
