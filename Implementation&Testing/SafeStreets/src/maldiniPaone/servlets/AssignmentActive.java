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
import maldiniPaone.servlets.managers.ReportManager;
import maldiniPaone.utilities.UserType;
import maldiniPaone.utilities.ResponseObjects.AssignmentStateResponse;
import maldiniPaone.utilities.ResponseObjects.GenericResponse;
import maldiniPaone.utilities.beans.Assignment;
import maldiniPaone.utilities.beans.users.User;
import maldiniPaone.utilities.constants.Constants;

/**
 * Servlet implementation class AssignmentActive
 */
@WebServlet("/AssignmentActive")
public class AssignmentActive extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AssignmentActive() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// set output to json format
		PrintWriter outputWriter = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		User user = (User) request.getSession(true).getAttribute("user");
		if (user == null || // short circuit
				user.getUserType() != UserType.Authority) {
			GenericResponse message = new GenericResponse(400, "invalid access");
			outputWriter.println(new Gson().toJson(message));
			outputWriter.close();
			return;
		}
		Assignment active=null;
		try {
			active=ReportManager.getInstance().checkActive(user.getUsername());
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
		AssignmentStateResponse message = new AssignmentStateResponse(active);
		outputWriter.println(new Gson().toJson(message));
		outputWriter.close();
		return;
	}

}
