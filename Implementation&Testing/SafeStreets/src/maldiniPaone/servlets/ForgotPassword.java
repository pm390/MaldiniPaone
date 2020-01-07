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
import maldiniPaone.utilities.PasswordBuilder;
import maldiniPaone.utilities.UserType;

/**
 * Servlet implementation class ForgotPassword
 */
@WebServlet("/ForgotPassword")
public class ForgotPassword extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ForgotPassword() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//set output to json format
		PrintWriter outputWriter = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		String username=request.getParameter("username");
		String email=request.getParameter("email");
		String uType=request.getParameter("type");
		if(((username==null||username.equals(""))&&(email==null||email.equals("")))
				||uType==null||UserType.fromString(uType)==null)
		{
			//TODO invalid request
			return;
		}
		UserType type=UserType.fromString(uType);
		
		try
		{
			if(email==null||email.equals(""))
			{
				email=UserManager.getIstance().findEmailByUsername(username);
			}
			String password=PasswordBuilder.GetRandomPassword();
			UserManager.getIstance().forgotPassword(username, type,password);
			MailManager.getInstance().sendPasswordModificationMail(email, password);
		}
		catch (ServerSideDatabaseException e) {
			if (Constants.VERBOSE) {
				e.printStackTrace();
			}
			// TODO send json object to indicate an error server side(5xx)
			// outputWriter.println(new Gson().toJson(message));
			return;
		} catch (IllegalParameterException e) {
			if (Constants.VERBOSE) {
				e.printStackTrace();
			}
			// TODO send json object to indicate an error in the parameters (4xx)
			// outputWriter.println(new Gson().toJson(message));
			return;
		}
		// TODO send json object to indicate a successful registration
		// outputWriter.println(new Gson().toJson(message));
		return;
	}

	
}
