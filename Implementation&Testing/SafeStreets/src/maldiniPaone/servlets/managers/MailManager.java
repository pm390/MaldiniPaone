package maldiniPaone.servlets.managers;

import maldiniPaone.constants.Constants;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailManager {

	private static MailManager instance;
	
	private String username;
	private String password;
	private Session session;
	
	private MailManager()
	{
		initialize();
	}
	
	
	public static MailManager getInstance()
	{
		return (instance==null)? instance=new MailManager(): instance;
	}
	
	
	
	
	private void initialize()
	{
		username=Constants.MAIL_USERNAME;
		password=Constants.MAIL_PASSWORD;
		Properties prop = new Properties();
		prop.put("mail.smtp.host", "smtp.gmail.com");
	    prop.put("mail.smtp.port", "587");
	    prop.put("mail.smtp.auth", "true");
	    prop.put("mail.smtp.starttls.enable", "true");
	    session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
	}
	
	public boolean sendConfirmationMail(String user,String mailAdress)
	{
		return sendMail("Account Confirmation",
				
				//message visualized with how it will be formatted
				("We are really happy you registered to SafeStreets."
						
				+"\n\nYour username is :" +user
				+"\n\nIf you haven't registered to the service please visit this page:"
				+Constants.SERVER_ADDRESS+"/DeleteUser?user="+user+user.length())+mailAdress.length()
				//end message
				,mailAdress);
	}
	
	public boolean sendConfirmationMail(String user,String password,String mailAdress)
	{
		return sendMail(
				"Account Confirmation",
				//message visualized with how it will be formatted
				("We are really happy you registered to SafeStreets."
						
				+"\n\nYour username is :" +user
				+"\nYour temporary password is: " + password
				+"\nIf you haven't registered to the service please visit this page:"
				+Constants.SERVER_ADDRESS+"/DeleteUser?user="+user+user.length())+mailAdress.length()
				//end message
				,mailAdress);
	}
	
	private boolean sendMail(String subject,String content,String mailAdress)
	{
		boolean result=false;
		try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(Constants.MAIL_USERNAME));
            message.setRecipients
            (
                    Message.RecipientType.TO,
                    InternetAddress.parse(mailAdress)
            );
            message.setSubject(subject);
            message.setText(content);
            Transport.send(message);

            result=true;

        } catch (MessagingException e) {
            if(Constants.VERBOSE)e.printStackTrace();
        }

		return result;
	}
	
	public static void main(String[] args) {
		MailManager.getInstance().sendConfirmationMail("pm390","pietrohideki@gmail.com");
	}

}
