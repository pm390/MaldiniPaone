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
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException
	{
		if(Constants.VERBOSE)System.out.println("unexpected request login is post operation");
		response.sendError(400, "Bad Request"+((Constants.VERBOSE)?"method forbidden in login servlet":""));
	}
	*/


    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		PrintWriter outputWriter = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		if(request.getSession(true).getAttribute("user")!=null)//if already logged in
		{
			//TODO send a json object to indicate the user is already logged in
			//outputWriter.println(new Gson().toJson(message));
			return;
		}
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		try 
		{
			UserType userType=UserManager.getIstance().login(username, password);
			if(userType!=null)
			{
				//build user base informations into an object when additional information are needed
				//they will be retrieved and added to the object created for reusing them
				User user= UserFactory.buildUserBase(username, password, userType);
				//save the user object in the session 
				request.getSession().setAttribute("user",user);
			}
			else
			{
				//TODO send json object to indicate the credentials are wrong
				//outputWriter.println(new Gson().toJson(message));
				return;
			}
		}
		catch (ServerSideDatabaseException e) 
		{
			if(Constants.VERBOSE) {e.printStackTrace();}
			//TODO send json object to indicate an error server side(5xx)
			//outputWriter.println(new Gson().toJson(message));
			return;
		}
		catch( IllegalParameterException e)
		{
			if(Constants.VERBOSE) {e.printStackTrace();}
			//TODO send json object to indicate an error in the parameters (4xx)
			//outputWriter.println(new Gson().toJson(message));
			return;
		}
		//TODO send json object to indicate successful login
		//outputWriter.println(new Gson().toJson(message));
		return;
	}

}
