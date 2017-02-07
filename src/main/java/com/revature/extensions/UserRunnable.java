package com.revature.extensions;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.revature.models.User;

public class UserRunnable implements Runnable
{
	User user;
	public UserRunnable(User user) throws MyException
	{
		super();
		this.user = user;
	}

	@Override
	public void run()
	{
		try
		{
			sendEmail(user);
		}
		catch(MyException ex)
		{
			System.out.println(ex.getMessage());
		}
	}

	private boolean sendEmail(User user) throws MyException
	{
		// Recipient's email ID needs to be mentioned.
		String to = user.getEmail();

		// Sender's email ID needs to be mentioned
		String from = "revatureprojectone";
		String password = "p4ssw0rd1";

		System.out.println("TO: " + to + "\n" + "FROM: " + from);

		// Assuming you are sending email from localhost
		String host = "smtp.gmail.com";

		// Get system properties
		Properties props = System.getProperties();

		String emailPort = "587";// gmail's smtp port
		props = System.getProperties();
		props.put("mail.smtp.port", emailPort);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");

		// Setup mail server
		props.put("mail.smtp.host", host);

		// Get the default Session object
		Session session = Session.getDefaultInstance(props);

		try
		{
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

			// Set Subject: header field
			message.setSubject("Reimbursement Request");

			// Now set the actual message
			message.setText("Your account has been created. "
					      + "Your user name is " + user.getUserName() 
					      + "Your password is " + user.getPassword());

			// Send message
			Transport transport = session.getTransport("smtp");
			transport.connect(host, from, password);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();

			return true;
		}
		catch (MessagingException mex)
		{
			mex.printStackTrace();
		}
		throw new MyException();
	}
}
