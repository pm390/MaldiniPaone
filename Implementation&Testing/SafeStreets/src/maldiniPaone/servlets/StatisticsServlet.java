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
import maldiniPaone.utilities.ResponseObjects.GenericResponse;
import maldiniPaone.utilities.ResponseObjects.StatisticsResponse;
import maldiniPaone.utilities.beans.Location;
import maldiniPaone.utilities.beans.Statistic;
import maldiniPaone.utilities.constants.Constants;

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
		//
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	/**
	 * Get Statistics from server
	 **/
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// set output to json format
		PrintWriter outputWriter = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		List<Statistic> statistics = null;
		try {
			Location location = new Location();
			// get needed data
			Float latitude = Float.parseFloat(request.getParameter("latitude"));
			Float longitude = Float.parseFloat(request.getParameter("longitude"));

			Float width = Float.parseFloat(request.getParameter("width"));
			Float height = Float.parseFloat(request.getParameter("height"));

			location.setLatitude(latitude-width/2);
			location.setLongitude(longitude-height/2);
			// get statistics
			statistics = StatisticManager.getInstance().getStatistics(location, Math.max(width, height));
		}  catch (ServerSideDatabaseException e) {
			if (Constants.VERBOSE) {
				e.printStackTrace();
			}
			GenericResponse message = new GenericResponse(500,"Server side error");
			outputWriter.println(new Gson().toJson(message));
			outputWriter.close();
			return;
		} catch (IllegalParameterException e) {
			if (Constants.VERBOSE) {
				e.printStackTrace();
			}
			GenericResponse message = new GenericResponse(400,"invalid parameters");
			outputWriter.println(new Gson().toJson(message));
			outputWriter.close();
			return;
		}
		StatisticsResponse message = new StatisticsResponse(statistics);
		outputWriter.println(new Gson().toJson(message));
		return;
	}

}
