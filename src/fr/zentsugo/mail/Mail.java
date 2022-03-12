package fr.zentsugo.mail;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import fr.zentsugo.pcvcregister.Register;

public class Mail {
	
	
	
	    public static void main(String[] args) {
	    	
	    	System.out.println("Started");
	    	
	    	SendMail("zentsugo@gmail.com", "hacrimogene88", "lol", "lol");
	    	
	    }
	    
	    public static void SendMail(String mail, String password, String subject, String messagetext) {
	    	
	    	final String usernamefinal = mail;
	        final String passwordfinal = password;

	        Properties props = new Properties();
	        props.put("mail.smtp.starttls.enable", "true");
	        props.put("mail.smtp.auth", "true");
	        props.put("mail.smtp.host", "smtp.gmail.com");
	        props.put("mail.smtp.port", "465");

	        Session session = Session.getInstance(props,
	          new javax.mail.Authenticator() {
	            protected PasswordAuthentication getPasswordAuthentication() {
	                return new PasswordAuthentication(usernamefinal, passwordfinal);
	            }
	          });

	        try {

	            Message message = new MimeMessage(session);
	            message.setFrom(new InternetAddress(usernamefinal));
	            message.setRecipients(Message.RecipientType.TO,
	                InternetAddress.parse("zentsugo@gmail.com"));
	            message.setSubject(subject);
	            message.setText(messagetext);

	            Transport.send(message);

	            System.out.println("Done");

	        } catch (MessagingException e) {
	            throw new RuntimeException(e);
	        }
	    	
	    }
	}
