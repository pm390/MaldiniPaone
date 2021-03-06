package maldiniPaone.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.google.gson.Gson;

import maldiniPaone.databaseConnection.databaseExceptions.IllegalParameterException;
import maldiniPaone.databaseConnection.databaseExceptions.ServerSideDatabaseException;
import maldiniPaone.servlets.managers.ReportManager;
import maldiniPaone.utilities.ResponseObjects.GenericResponse;
import maldiniPaone.utilities.beans.Location;
import maldiniPaone.utilities.beans.Photo;
import maldiniPaone.utilities.beans.users.User;
import maldiniPaone.utilities.constants.Constants;

@MultipartConfig
@WebServlet("/ReportCreation")
public class ReportCreation extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ReportCreation() {
		super();
		//
	}

	@Override
	/**
	 * Create a Report
	 **/
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// set output to json format
		PrintWriter outputWriter = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		if (request.getSession() == null || // shortcircuit
				request.getSession().getAttribute("user") == null || !ServletFileUpload.isMultipartContent(request)) {
			GenericResponse message = new GenericResponse(400, "invalid access");
			outputWriter.println(new Gson().toJson(message));
			outputWriter.close();
			return;
		}

		try {
			// get needed data
			User user = (User) request.getSession().getAttribute("user");
			String username = user.getUsername();
			Location location = new Location();
			Float latitude = Float.parseFloat(request.getParameter("latitude"));
			Float longitude = Float.parseFloat(request.getParameter("longitude"));
			location.setLatitude(latitude);
			location.setLongitude(longitude);
			String note = request.getParameter("note");
			String licensePlate = request.getParameter("car");

			// get all photos
			int index = 0;
			List<Photo> photos = new ArrayList<Photo>();
			Collection<Part> parts = request.getParts();
			for (Part x : parts) {
				System.out.println(x.getName());
				if (x.getName().equals("photo"))// for each image
				{
					String image = x.getSubmittedFileName();// get filename
					
					Integer i = image.lastIndexOf(".");// get last dot
					if(Constants.VERBOSE)
						{
						System.out.println(image);
						System.out.println(i);
						}
					if(image=="")
					{
						continue;
					}
					String fileExtension = image.substring(i);// get file extension
					InputStream input = x.getInputStream();// get input stream
					// save data to a bean
					Photo temp = new Photo();
					temp.setFileExtension(fileExtension);
					temp.setPhoto(input);
					temp.setPhotoNumber(index++);
					photos.add(temp);
				}
			}

			ReportManager.getInstance().addReport(username, location, photos, licensePlate, note);
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
		}
		GenericResponse message = new GenericResponse();
		outputWriter.println(new Gson().toJson(message));
		outputWriter.close();
		return;
	}

}
