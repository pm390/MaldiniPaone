package maldiniPaone.servlets.managers;

import maldiniPaone.constants.Constants;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Manages the email notification of the users. Singleton design pattern
 **/
public class MailManager {
	// ================================================================================
	// static fields
	// ================================================================================

	private static MailManager instance;

	private String username;
	private String password;
	private Session session;
	// ================================================================================
	// private constructor
	// ================================================================================

	private MailManager() {
		initialize();
	}

	// ================================================================================
	// Instantiator
	// ================================================================================
	/**
	 * Gets the instance of the MailManager. Design pattern Singleton.
	 * 
	 * @return the instance
	 **/
	public static MailManager getInstance() {
		return (instance == null) ? instance = new MailManager() : instance;
	}

	/**
	 * initializes the MailManager and creates a session with the mail service
	 **/
	private void initialize() {
		synchronized (instance) {
			username = Constants.MAIL_USERNAME;
			password = Constants.MAIL_PASSWORD;
			Properties prop = new Properties();
			prop.put("mail.smtp.host", "smtp.gmail.com");
			prop.put("mail.smtp.port", "587");
			prop.put("mail.smtp.auth", "true");
			prop.put("mail.smtp.starttls.enable", "true");
			session = Session.getInstance(prop, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);
				}
			});
		}
	}

	/**
	 * Sends Account creation confirmation email to a user who registered
	 * 
	 * @param user       the username of the user who is registered
	 * @param mailAddress the mail address to be notified
	 * @return true
	 **/
	public boolean sendConfirmationMail(String user, String mailAddress) {
		return sendMail("Account Confirmation",

				// message visualized with how it will be formatted
				("We are really happy you registered to SafeStreets."

						+ "\n\nYour username is :" + user
						+ "\n\nIf you haven't registered to the service please visit this page:"
						+ Constants.SERVER_ADDRESS + "/DeleteUser?user=" + user + user.length()) + mailAddress.length()
				// end message
				, mailAddress);
	}

	/**
	 * Sends Account creation confirmation email to a user who registered
	 * 
	 * @param user        the username of the user who is registered
	 * @param password    the password of the created account
	 * @param mailAddress the mail address to be notified
	 * @return true
	 **/
	public boolean sendConfirmationMail(String user, String password, String mailAddress) {
		return sendMail("Account Confirmation",
				// message visualized with how it will be formatted
				("We are really happy you registered to SafeStreets."

						+ "\n\nYour username is :" + user + "\nYour temporary password is: " + password
						+ "\nIf you haven't registered to the service please visit this page:"
						+ Constants.SERVER_ADDRESS + "/DeleteUser?user=" + user + user.length()) + mailAddress.length()
				// end message
				, mailAddress);
	}

	/**
	 * Sends Account Modification email to a user who modified his/her account
	 * 
	 * @param mailAddress the mail address to be notified
	 * @return true
	 **/
	public boolean sendAccountModificationMail(String mailAddress) {
		return sendMail("Account modification", ("Your account credentials has been modified."
				+ "\nIf you didn't modify it please contact us at " + Constants.MAIL_USERNAME)
		// end message
				, mailAddress);
	}

	/**
	 * Sends Account Modification email to a user who modified his/her account
	 * 
	 * @param oldAddress the mail address to be notified
	 * @param newAddress the new mail address of the user which must be notified too
	 * @return true
	 **/
	public boolean sendAccountModificationMail(String oldAddress, String newAddress) {
		return sendMail("Account modification",
				("Your account credentials has been modified." + "\nYour new email is really :" + newAddress
						+ "\nIf you didn't modify it please contact us at " + Constants.MAIL_USERNAME)
				// end message
				, oldAddress) && sendMail("Account modification", ("Your account credentials has been modified."
						+ "\nIf you didn't modify it please contact us at " + Constants.MAIL_USERNAME)
		// end message
						, newAddress);
	}

	/**
	 * Sends an email in a different thread
	 * 
	 * @param subject     the subject of the mail
	 * @param content     the actual body of the mail
	 * @param mailAddress the address which is notified
	 * @return true
	 **/
	private boolean sendMail(String subject, String content, String mailAddress) {
		if (mailAddress == null || mailAddress.equals("")) {
			if (Constants.VERBOSE)
				System.out.println("email not specified");
			return false;
		}
		Runnable thread = new Runnable() {
			public void run() {

				if (Constants.VERBOSE)
					System.out.println("sending email to" + mailAddress);
				try {
					Message message = new MimeMessage(session);
					message.setFrom(new InternetAddress(Constants.MAIL_USERNAME));
					message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mailAddress));
					message.setSubject(subject);
					message.setText(content);
					synchronized (instance) {// avoid conflict of reinitialization and sending at the same time
						Transport.send(message);
					}
				} catch (MessagingException e) {
					initialize();// reinitialize the mail Manager
					if (Constants.VERBOSE)
						e.printStackTrace();

				}
			}
		};
		thread.run();
		return true;
	}

	public static void main(String[] args) {
		MailManager.getInstance().sendConfirmationMail("pm390", "pietrohideki@gmail.com");
	}

}
