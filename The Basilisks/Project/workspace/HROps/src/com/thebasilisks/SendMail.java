package com.thebasilisks;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;

public class SendMail {
	private Properties props;
	private static Session session;

	private SendMail() {
		ServletContext context = Initializer.getServletContext();
		props = new Properties();
		try {
			props
					.load(context
							.getResourceAsStream("/WEB-INF/config/SendMail.properties"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		final String username = props.getProperty("SendMail.username");
		final String password = props.getProperty("SendMail.password");

		session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
	}

	private void send(String recipient, String mailSubject, String mailBody) {

		String from = props.getProperty("SendMail.from");

		try {

			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.setRecipients(Message.RecipientType.TO, InternetAddress
					.parse(recipient));
			message.setSubject(mailSubject);
			message.setText(mailBody, "utf-8", "html");

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
	
	private String getBaseUrl()
	{
		String serverHostname = props.getProperty("SendMail.server.hostname");
		String serverPort = props.getProperty("SendMail.server.port");
		
		String url = (serverPort.equals("80"))?serverHostname:serverHostname+":"+serverPort;
		url += Initializer.getServletContext().getContextPath();
		return url;
	}

	public static void sendProcessingErrorMail(String recipient, String reason) {
		SendMail sendMail = new SendMail();

		String subject = sendMail.props
				.getProperty("MailProcessor.rejection.subject");
		String body = sendMail.props
				.getProperty("MailProcessor.rejection.body");

		body = body.replace("{mailProcessor_reject_reason}", reason);
		sendMail.send(recipient, subject, body);
	}
	
	public static void sendRejectionMail(String recipient, String reason) {
		SendMail sendMail = new SendMail();

		String subject = sendMail.props
				.getProperty("letter.rejection.subject");
		String body = sendMail.props
				.getProperty("letter.rejection.body");

		body = body.replace("{reject_reason}", reason);
		sendMail.send(recipient, subject, body);
	}
	
	public static void sendInterviewMail(String recipient, String opportunityId, Date date, Time time) {
		SendMail sendMail = new SendMail();

		String subject = sendMail.props
				.getProperty("letter.interview.subject");
		String body = sendMail.props
				.getProperty("letter.interview.body");
		String schedule = "<b>Date : </b>"+date.toString()+"<br><b>Time : </b>"+time.toString();

		body = body.replace("{opportunity_id}", opportunityId);
		body = body.replace("{interview_schedule}", schedule);
		sendMail.send(recipient, subject, body);
	}
	
	public static void sendOfferMail(String recipient, String position, String offerId) {
		SendMail sendMail = new SendMail();

		String subject = sendMail.props
				.getProperty("letter.offer.subject");
		String body = sendMail.props
				.getProperty("letter.offer.body");
		String url = sendMail.getBaseUrl()+ "/offerLetter.do?offerId="+offerId+"&task=";
		
		String acceptLink = "<a href=\""+url+"accept\">"+url+"accept</a>";
		String rejectLink = "<a href=\""+url+"reject\">"+url+"reject</a>";

		body = body.replace("{position}", position);
		body = body.replace("{offer_accept_link}", acceptLink);
		body = body.replace("{offer_reject_link}", rejectLink);
		sendMail.send(recipient, subject, body);
	}
	
	public static void sendUsernameAndPassword(String recipient, String employeeId, String password) {
		SendMail sendMail = new SendMail();

		String subject = sendMail.props
				.getProperty("letter.password.subject");
		String body = sendMail.props
				.getProperty("letter.password.body");

		body = body.replace("{employee_id}", employeeId);
		body = body.replace("{password}", password);
		sendMail.send(recipient, subject, body);
	}
}
