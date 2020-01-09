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
import maldiniPaone.utilities.beans.users.User;

/**
 * Servlet implementation class AccountModification
 */
@WebServlet("/AccountModification")
public class AccountModification extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AccountModification() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	/**
	 * Modify User account's credentials.
	 **/
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//set output to json format
		PrintWriter outputWriter = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		User user = (User) request.getSession(true).getAttribute("user");
		if (user == null || // short circuit
				!user.getPassword().equals(request.getParameter("oldPassword")))
		// check if the user inserted the correct old password
		{
			if(Constants.VERBOSE)
			{
				System.out.println(user.getPassword()+"new:"+request.getParameter("oldPassword"));
			}
			GenericResponse message = new GenericResponse(400,"illegalRequest");
			outputWriter.println(new Gson().toJson(message));
			outputWriter.close();
			return;
		}
		try {
			if (Constants.VERBOSE)
				System.out.println("parsing parameters");// debug
			//parse parameters
			String newPass = request.getParameter("newPassword");
			String newUser = request.getParameter("newUsername");
			String newEmail = request.getParameter("newEmail");
			String oldEmail = user.getEmail();
			if (oldEmail == null) {
				if (Constants.VERBOSE)
					System.out.println("finding email");// debug

				oldEmail = UserManager.getIstance().findEmailByUsername(user.getUsername());
				if (Constants.VERBOSE)
					System.out.println(oldEmail);// debug
			}
			if (UserManager.getIstance().modifyUserCredentials(user.getUsername(), user.getPassword(), newUser, newPass,
					newEmail, user.getUserType())) {
				//update username and password in the session if the modification is successful
				user.setUsername(newUser);
				user.setPassword(newPass);

				if (newEmail != null) {
					//new email
					user.setEmail(newEmail);
					MailManager.getInstance().sendAccountModificationMail(oldEmail, newEmail);
				} else {
					//no new email
					user.setEmail(oldEmail);
					MailManager.getInstance().sendAccountModificationMail(oldEmail);
				}
				if (Constants.VERBOSE)
					System.out.println("modified");// debug
			} else {
				GenericResponse message = new GenericResponse(500,"modification failed");
				outputWriter.println(new Gson().toJson(message));
				outputWriter.close();
				return;
			}

		} catch (ServerSideDatabaseException e) {
			if (Constants.VERBOSE) {
				e.printStackTrace();
			}
			GenericResponse message = new GenericResponse(500,"server side error");
			outputWriter.println(new Gson().toJson(message));
			outputWriter.close();
			return;
		} catch (IllegalParameterException e) {
			if (Constants.VERBOSE) {
				e.printStackTrace();
			}
			GenericResponse message = new GenericResponse(400,"invalid request");
			outputWriter.println(new Gson().toJson(message));
			outputWriter.close();
			return;
		}
		GenericResponse message = new GenericResponse();
		outputWriter.println(new Gson().toJson(message));
		outputWriter.close();
		return;
	}

}
