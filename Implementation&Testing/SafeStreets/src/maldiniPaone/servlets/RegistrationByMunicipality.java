package maldiniPaone.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.text.RandomStringGenerator;

import maldiniPaone.constants.Constants;
import maldiniPaone.databaseConnection.databaseExceptions.IllegalParameterException;
import maldiniPaone.databaseConnection.databaseExceptions.ServerSideDatabaseException;
import maldiniPaone.servlets.managers.MailManager;
import maldiniPaone.servlets.managers.UserManager;
import maldiniPaone.utilities.PasswordBuilder;
import maldiniPaone.utilities.UserType;
import maldiniPaone.utilities.beans.CityHall;
import maldiniPaone.utilities.beans.District;
import maldiniPaone.utilities.beans.Location;
import maldiniPaone.utilities.beans.users.Municipality;
import maldiniPaone.utilities.beans.users.User;

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
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		if(Constants.VERBOSE)System.out.println("unexpected request : registration is post operation");
		response.sendError(400, "Bad Request"+((Constants.VERBOSE)?"method forbidden in registration servlet":""));
	}
*/
	
    
    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		PrintWriter outputWriter = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		User user= (User) request.getSession(true).getAttribute("user");
		if(user==null||//short circuit
				user.getUserType()!=UserType.Municipality)
		{
			//TODO send json object to indicate illegal request(not allowed)
			//outputWriter.println(new Gson().toJson(message));
			return;
		}
		//here we have user != null and for sure a municipality
		CityHall cityHall=((Municipality)user).getCityhall();
		
		//now cityhall is available
		String username=(String)request.getParameter("username");
		String password=PasswordBuilder.GetRandomPassword();
		String email=(String)request.getParameter("email");
		String targetUserType=(String)request.getParameter("userType");
		try
		{
			if(cityHall==null)
			//if the session doesn't contain already the cityhall of the municipality it must be retrieved
			{
				cityHall=UserManager.getIstance().getCityHall(user.getUsername());
				((Municipality)user).setCityhall(cityHall);
			}
			if(targetUserType==UserType.Municipality.toString())
			{
				
				if(UserManager.getIstance().registerMunicipalityByMunicipality(username, password, email, 
						user.getUsername(), cityHall.getName(), cityHall.getProvince()))
				{
					MailManager.getInstance().sendConfirmationMail(username, password, email);
				}
				else
				{
					//TODO duplicate username or password JSON message
					return;
				}
			}
			else if(targetUserType==UserType.Authority.toString())
			{
				if(password!=null)
				{
					//TODO json. password should be not specified for authorities
					return;
				}
				//get a password
				password=PasswordBuilder.GetRandomPassword();

				//TODO parse additional data for authority
				//TODO add district
				if(registerAuthority(username,password,email,(Municipality) user,request))
				{
					MailManager.getInstance().sendConfirmationMail(username, password, email);
				}
				else
				{
					//TODO duplicate username or password JSON message
					return ;
				}
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
    
    
    
    //================================================================================
    // Utility functions
    //================================================================================
	//TODO javadoc here
    private static boolean registerAuthority(String username,String password,String email,
    		Municipality creator,HttpServletRequest request) throws ServerSideDatabaseException, IllegalParameterException
    {
    	CityHall cityhall=creator.getCityhall();
    	
		Float topLeftLatitude=Float.parseFloat(request.getParameter("tLLatitude"));
		Float topLeftLongitude=Float.parseFloat(request.getParameter("tLLongitude"));
		Location topLeftLocation=new Location();
		topLeftLocation.setLatitude(topLeftLatitude);
		topLeftLocation.setLongitude(topLeftLongitude);
    	
		Float bottomRightLatitude=Float.parseFloat(request.getParameter("bRLatitude"));
		Float bottomRightLongitude=Float.parseFloat(request.getParameter("bRLongitude"));
		Location bottomRightLocation=new Location();
		topLeftLocation.setLatitude(bottomRightLatitude);
		topLeftLocation.setLongitude(bottomRightLongitude);
    	return UserManager.getIstance().registerAuthority(username, password, email, 
    			creator.getUsername(),cityhall.getName(), cityhall.getProvince(), 
    			topLeftLocation, bottomRightLocation);
    }
	
}
