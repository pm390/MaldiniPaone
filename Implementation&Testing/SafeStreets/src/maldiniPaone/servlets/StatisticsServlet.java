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

import maldiniPaone.databaseConnection.databaseExceptions.IllegalParameterException;
import maldiniPaone.databaseConnection.databaseExceptions.ServerSideDatabaseException;
import maldiniPaone.servlets.managers.StatisticManager;
import maldiniPaone.utilities.UserType;
import maldiniPaone.utilities.beans.Location;
import maldiniPaone.utilities.beans.Statistic;
import maldiniPaone.utilities.beans.users.User;

/**
 * Servlet implementation class StatisticsServlet
 */
@WebServlet("/StatisticsServlet")
public class StatisticsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StatisticsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	PrintWriter outputWriter = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		Location location=new Location();
		Float latitude= Float.parseFloat(request.getParameter("latitude"));
		Float longitude= Float.parseFloat(request.getParameter("longitude"));
		location.setLatitude(latitude);
		location.setLongitude(longitude);
		
		Float width=Float.parseFloat(request.getParameter("width"));
		Float height= Float.parseFloat(request.getParameter("height"));
		List<Statistic> statistics=null;
		try 
		{
			statistics=StatisticManager.getInstance().getStatistics(location,Math.max(width, height));
		} 
		catch (ServerSideDatabaseException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (IllegalParameterException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(statistics==null||statistics.size()==0)
		{
			//TODO send json object to indicate no assignment in the given area
			//outputWriter.println(new Gson().toJson(message));
			return;
		}
		outputWriter.println(new Gson().toJson(statistics));
	}

}
