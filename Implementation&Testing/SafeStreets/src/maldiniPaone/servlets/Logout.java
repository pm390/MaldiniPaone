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

/**
 * Servlet implementation class Logout
 */
@WebServlet("/Logout")
public class Logout extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Logout() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	/**
	 * Logout from the service
	 **/
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getSession(true).removeAttribute("user");
		// set output to json format
		PrintWriter outputWriter = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		GenericResponse message = new GenericResponse();
		outputWriter.println(new Gson().toJson(message));
		return;
	}

}
