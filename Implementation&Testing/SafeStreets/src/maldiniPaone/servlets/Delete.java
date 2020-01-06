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
import maldiniPaone.servlets.managers.UserManager;
import maldiniPaone.utilities.beans.users.User;

/**
 * Servlet implementation class Delete
 */
@WebServlet("/Delete")
public class Delete extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Delete() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	/**
	 * Delete the account of a user
	 **/
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// set output to json format
		PrintWriter outputWriter = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		User user = (User) request.getSession(true).getAttribute("user");
		//check if user is logged in
		if (user == null) {
			// TODO check parameter coming from email link and see if it is valid
			// TODO send json object to indicate illegal request(not allowed)
			// outputWriter.println(new Gson().toJson(message));
			return;
		}
		try {
			UserManager.getIstance().removeUser(user.getUsername(), user.getPassword(), user.getUserType());
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

}
