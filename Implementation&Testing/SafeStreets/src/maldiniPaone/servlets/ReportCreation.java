package maldiniPaone.servlets;


import java.io.IOException;
import java.io.InputStream;
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

import maldiniPaone.constants.Constants;
import maldiniPaone.databaseConnection.databaseExceptions.IllegalParameterException;
import maldiniPaone.databaseConnection.databaseExceptions.ServerSideDatabaseException;
import maldiniPaone.servlets.managers.ReportManager;
import maldiniPaone.utilities.beans.Location;
import maldiniPaone.utilities.beans.Photo;
import maldiniPaone.utilities.beans.users.User;





	@MultipartConfig
	@WebServlet("/ReportCreation")
public class ReportCreation extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportCreation() {
        super();
        // TODO Auto-generated constructor stub
    }


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getSession() == null || //shortcircuit
					request.getSession().getAttribute("user")==null)
		{
			//invalid access
			return;
		}
		if(!ServletFileUpload.isMultipartContent(request))
		{
			response.sendError(403, "Illegal access use the given form to send photo");
		}
		User user=(User)request.getSession().getAttribute("user");
		String username=user.getUsername();
		Location location=new Location();
		Float latitude= Float.parseFloat(request.getParameter("latitude"));
		Float longitude= Float.parseFloat(request.getParameter("longitude"));
		location.setLatitude(latitude);
		location.setLongitude(longitude);
		String note= request.getParameter("note");
		String licensePlate= request.getParameter("car");
		
		int index=0;
		List<Photo> photos=new ArrayList<Photo>();
		Collection<Part> parts = request.getParts();
		for(Part x:parts)
	    {
	    		 if(x.getName().equals("photo"))//for each image
	    		 {
	    			 String image=x.getSubmittedFileName();
	    			 Integer i=image.lastIndexOf(".");
	    			 String fileExtension=image.substring(i);
	    			 InputStream input = x.getInputStream();
	    			 Photo temp=new Photo();
	    			 temp.setFileExtension(fileExtension);
	    			 temp.setPhoto(input);
	    			 temp.setPhotoNumber(index++);
	    		 }
	    }		
		try 
		{
			ReportManager.getInstance().addReport(username, location, photos, licensePlate, note);
		} 
		catch (ServerSideDatabaseException | IllegalParameterException e) 
		{
			if(Constants.VERBOSE)e.printStackTrace();
		}
		
	}

}
