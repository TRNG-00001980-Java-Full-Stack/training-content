package configs;

import java.util.Properties;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.*;

public class EmailConfig {
	private static final String USER_NAME = "princelokiodin@gmail.com";
	private static final String PASSWORD = "ijqy sqfo hxqt mfvk";
	private static final String HOST = "smtp.gmail.com";
	
	
	public static void sendEmail(String toAddress, String subject, String messageBody) {
		Properties properties = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable","true");
		properties.put("mail.smtp.host", HOST);
		properties.put("mail.smtp.port", "587");//465
		
		
		Session session = Session.getInstance(properties, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(USER_NAME, PASSWORD);
			}
		});
		
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(USER_NAME));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toAddress));
			message.setSubject(subject);
			message.setText(messageBody);
			
			
			Transport.send(message);
			System.out.println("Email sent successfully");
		}catch(MessagingException ex) {
			ex.printStackTrace();
		}
	}

}
