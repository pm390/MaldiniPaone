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
import maldiniPaone.utilities.UserType;
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
    	PrintWriter outputWriter = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		User user= (User) request.getSession(true).getAttribute("user");
		if(user==null||//short circuit
				user.getPassword()!=request.getParameter("oldPassword"))//check if the 
			//user inserted the correct old password
		{
			//TODO send json object to indicate illegal request(not allowed)
			//outputWriter.println(new Gson().toJson(message));
			return;
		}
		try
		{
			String newPass=request.getParameter("newPassword");
			String newUser=request.getParameter("newUsername");
			String newEmail=request.getParameter("newEmail");
			String oldEmail=user.getEmail();
			if(oldEmail==null)
			{
				oldEmail=UserManager.getIstance().findEmailByUsername(user.getUsername());
			}
			UserManager.getIstance().modifyUserCredentials(user.getUsername(), user.getPassword(),
					newUser, newPass, newEmail, user.getUserType());
			int attempt=0;
			if(newEmail!=null)
			{
				while(!MailManager.getInstance().sendAccountModificationMail(oldEmail, newEmail)
						&&attempt<Constants.MAX_ATTEMPTS)
				{
					++attempt;
				}
				user.setEmail(newEmail);
			}
			else
			{
				while(!MailManager.getInstance().sendAccountModificationMail(oldEmail)
						&&attempt<Constants.MAX_ATTEMPTS)
				{
					++attempt;
				}
				user.setEmail(oldEmail);
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
		//TODO send json object to indicate a successful registration
		//outputWriter.println(new Gson().toJson(message));
		return;
	}
    
    
    
    
    
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException 
    {
    	PrintWriter outputWriter = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		User user= (User) request.getSession(true).getAttribute("user");
		if(user==null)
		{
			//TODO send json object to indicate illegal request(not allowed)
			//outputWriter.println(new Gson().toJson(message));
			return;
		}
		try
		{
			UserManager.getIstance().removeUser(user.getUsername(), user.getPassword(), user.getUserType());	
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
		//TODO send json object to indicate a successful registration
		//outputWriter.println(new Gson().toJson(message));
		return;
	}
}
