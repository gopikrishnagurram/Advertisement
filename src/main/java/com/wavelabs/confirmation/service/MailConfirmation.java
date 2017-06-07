package com.wavelabs.confirmation.service;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

import com.wavelabs.model.Advertisement;
import com.wavelabs.model.User;

public class MailConfirmation {

	private static Properties props = null;
	private static MailConfirmation mail = null;
	private static final String HTMLTMPLATESTART = "<html></head> " + "<title>Confirmation from wavelabs</title>"
			+ "<style> body { background-image : url(\"wavelabs.jpg\"); } </style> </head>" + "<body>";
	private static final String HTMLTEMPLATEEND = "</body></html>";
	private static final Logger LOGGER = Logger.getLogger(MailConfirmation.class);

	private MailConfirmation() throws IOException {
		props = new Properties();
		props.load(new FileReader("E:/RestFul/Search/src/main/resources/mail.properties"));
	}

	public static MailConfirmation getInstance() throws IOException {

		if (mail == null) {
			mail = new MailConfirmation();
		}
		return mail;
	}

	public static void sendMail(final String from, final String password, String to, String sub, String msg)
			throws IOException {
		getInstance();
		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(from, password);
			}
		});
		try {
			MimeMessage message = new MimeMessage(session);
			message.setContent(msg, "text/html");
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.setSubject(sub);
			Transport.send(message);
		} catch (MessagingException e) {
			LOGGER.error(e);
		}
	}

	public static void sendMail(Advertisement advert, User user, String type) throws IOException {
		String msg = "<h1 style=\"color:green\"> Hello,  " + advert.getUser().getFirstName() + " </h1>";
		String message = "<h1 style=\"color:red\"> Advertisement posted Successfully </h1>";
		if ("POST".equals(type)) {
			message = msg + message + advertisementMessage(advert);
		} else if ("APPLY".equals(type)) {

			message = msg + "<h2 style=\"color:red\">" + user.getFirstName() + " Intrested in your advertisement" + "</h2>";
			message = message + userMessage(user);
			message = message + " For Add \n" + advertisementMessage(advert);
		}
		sendMail("gopikrishnagurram279", "AxisAmma@nemanarmy", advert.getUser().getEmail(), "Wavelabs Update",
				HTMLTMPLATESTART + message + HTMLTEMPLATEEND);
	}

	public static String advertisementMessage(Advertisement advert) {

		String message = "";
		message = message + "<table style=\"color:blue\"><tr> Name : " + advert.getName() + "</tr> <tr> Type : "
				+ advert.getType() + " </tr> <tr> Descrption : " + advert.getDescription() + "</tr> <tr> Location :"
				+ advert.getLocation() + " </tr> </table>";
		return message;
	}

	public static String userMessage(User user) {
		return "<table style=\"color:green\"> <tr> Email : " + user.getFirstName() + " </tr> <tr> Phone :" + user.getPhone()
				+ " </tr> </table>";
	}
}
