package maldiniPaone.servlets;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import maldiniPaone.servlets.managers.PhotoManager;
import maldiniPaone.utilities.UserType;
import maldiniPaone.utilities.ResponseObjects.GenericResponse;
import maldiniPaone.utilities.beans.users.User;

/**
 * Servlet implementation class GetPhoto
 */
@WebServlet("/GetPhoto")
public class GetPhoto extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetPhoto() {
        super();
        
    }

	/**
	 * Retrieve a photo 
	 **/
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user= (User) request.getSession(true).getAttribute("user");
		if(user==null||//short circuit
				user.getUserType()!=UserType.Authority)
		{
			PrintWriter outputWriter = response.getWriter();
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			GenericResponse message = new GenericResponse(400, "invalid access");
			outputWriter.println(new Gson().toJson(message));
			outputWriter.close();
			return;
		}
		String name=request.getParameter("file");
		Integer i=name.lastIndexOf(".");  	
		String ext=name.substring(i+1);
		String type="jpg";
		if(ext.equals("jpg")||ext.equals("jpeg")){
			response.setContentType("image/jpeg");
		}
		else
		{
			response.setContentType("image/"+ext);
			type=ext;
		}
		BufferedImage image=PhotoManager.getInstance().getPhoto(name);
		OutputStream out= response.getOutputStream();
		ImageIO.write(image, type, out);
		out.close();
	}

	
}
