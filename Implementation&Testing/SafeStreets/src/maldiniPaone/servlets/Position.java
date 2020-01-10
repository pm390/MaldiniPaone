package maldiniPaone.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import maldiniPaone.servlets.managers.UserManager;
import maldiniPaone.utilities.UserType;
import maldiniPaone.utilities.ResponseObjects.GenericResponse;
import maldiniPaone.utilities.beans.Location;
import maldiniPaone.utilities.beans.users.Authority;
import maldiniPaone.utilities.beans.users.User;
import maldiniPaone.utilities.constants.Constants;

/**
 * Servlet implementation class Position
 */
@WebServlet("/Position")
public class Position extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Position() {
        super();
        // 
       
    }



	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
				Float latitude = Float.parseFloat(request.getParameter("latitude"));
				Float longitude = Float.parseFloat(request.getParameter("longitude"));
				Location loc=new Location();
				try
				{
					loc.setLatitude(latitude);
					loc.setLongitude(longitude);
				}
				catch(Exception e)
				{
					if (Constants.VERBOSE) {
						e.printStackTrace();
					}
					GenericResponse message = new GenericResponse(400, "invalid parameters");
					outputWriter.println(new Gson().toJson(message));
					outputWriter.close();
					return;
				}
				Boolean isNew = ((Authority)user).getLastLocationIndex().equals(-1);
				if(isNew)
				{
					UserManager.getIstance().addPosition((Authority)user,loc );
				}
				else
				{
					UserManager.getIstance().updatePosition((Authority)user, loc);
				}
				GenericResponse message = new GenericResponse();
				outputWriter.println(new Gson().toJson(message));
				outputWriter.close();
				return;
	}

}
