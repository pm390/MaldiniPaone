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

import maldiniPaone.constants.Constants;
import maldiniPaone.databaseConnection.databaseExceptions.IllegalParameterException;
import maldiniPaone.databaseConnection.databaseExceptions.ServerSideDatabaseException;
import maldiniPaone.servlets.managers.ReportManager;
import maldiniPaone.utilities.State;
import maldiniPaone.utilities.UserType;
import maldiniPaone.utilities.ViolationType;
import maldiniPaone.utilities.beans.Assignment;
import maldiniPaone.utilities.beans.Location;
import maldiniPaone.utilities.beans.users.User;

/**
 * Servlet implementation class AssignmentServlet
 */
@WebServlet("/AssignmentServlet")
public class AssignmentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AssignmentServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	@Override
	/**
	 * Get list of assignments. This servlet should be only called by authorities who are logged in
	 **/
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//set output to json format
		PrintWriter outputWriter = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		List<Assignment> assignment = null;
		try {
			
			User user = (User) request.getSession(true).getAttribute("user");
			//check if user can access this functionality
			if (user == null || // short circuit
					user.getUserType() != UserType.Authority) {
				// TODO invalid access
				return;
			}
			//get needed data
			Location location = new Location();
			Float latitude = Float.parseFloat(request.getParameter("latitude"));
			Float longitude = Float.parseFloat(request.getParameter("longitude"));
			location.setLatitude(latitude);
			location.setLongitude(longitude);

			assignment = ReportManager.getInstance().getAssignment(location);
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
		if (assignment == null || assignment.size() == 0) {
			// TODO send json object to indicate no assignment in the given area
			// outputWriter.println(new Gson().toJson(message));
			return;
		}
		outputWriter.println(new Gson().toJson(assignment));
		return;
	}


	@Override
	/**
	 *	Update the state of an assignment 
	 **/
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//set output to json format
		PrintWriter outputWriter = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		User user = (User) request.getSession(true).getAttribute("user");
		//check if user can access this functionality
		if (user == null || // short circuit
				user.getUserType() != UserType.Authority) {
			// TODO invalid access
			return;
		}

		String username = user.getUsername();
		//get needed data
		State finishState = State.fromString(request.getParameter("newState"));
		Integer id = Integer.parseInt(request.getParameter("assignmentId"));
		try {
			switch (finishState) {
			case Pending:
				ReportManager.getInstance().refuseAssignment(id, username);
				break;
			case Accepted:
				ReportManager.getInstance().acceptAssignment(id, username);
				break;
			case AlreadySolved:
			case CorrectlyFinished:
			case FalseReport:
				//get report type
				ViolationType type = ViolationType.fromString(request.getParameter("type"));
				ReportManager.getInstance().terminateAssignment(id, username, finishState, type);
				break;
			default:
				// TODO send json object to indicate invalid finish state
				// outputWriter.println(new Gson().toJson(message)); ;
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
		// TODO send json object to indicate success
		// outputWriter.println(new Gson().toJson(message));
		return;
	}

}
