package com.thebasilisks.servlets;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class admin_config
 */
public class AdminConfig extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdminConfig() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doProcess(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doProcess(request, response);
	}

	protected void doProcess(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		int taskid = Integer.parseInt(request.getParameter("taskid"));
		PrintWriter out = response.getWriter();

		if (taskid == 1) {

			String db_username = request.getParameter("db_username");
			String db_password = request.getParameter("db_password");
			String db_host=request.getParameter("db_host");
			String db_port=request.getParameter("db_port");
			String db_dbname=request.getParameter("db_dbname");
			if (db_username != null && db_password != null && db_host!=null && db_port!=null && db_dbname!=null) {
				Properties props = new Properties();
				props.load(getServletContext().getResourceAsStream(
						"/WEB-INF/config/DBConnection.properties"));
				props.setProperty("username", db_username);
				props.setProperty("password", db_password);
				props.setProperty("port", db_port);
				props.setProperty("host", db_host);
				props.setProperty("dbname", db_dbname);
				props.store(
						new FileOutputStream(getServletContext().getRealPath(
								"/WEB-INF/config/DBConnection.properties")),
						null);
				out.print(true);

			} else {
				out.print(false);
			}
		} else if (taskid == 2) {

			String imap_host = request.getParameter("imap_host");
			String imap_port = request.getParameter("imap_port");
			String imap_username = request.getParameter("imap_username");
			String imap_password = request.getParameter("imap_password");
			String imap_freq = request.getParameter("imap_freq");
			try {
				int num_port = Integer.parseInt(imap_port);
				int num_freq = Integer.parseInt(imap_freq);
				if (imap_host != null && imap_port != null
						&& imap_username != null && imap_password != null
						&& imap_freq != null) {
					Properties props = new Properties();

					props.load(getServletContext().getResourceAsStream(
							"/WEB-INF/config/MailProcessor.properties"));
					props.setProperty("MailProcessor.host", imap_host);
					props.setProperty("MailProcessor.port", imap_port);
					props.setProperty("MailProcessor.username", imap_username);
					props.setProperty("MailProcessor.password", imap_password);
					props.setProperty("MailProcessor.pollingFrequency",
							imap_freq);
					props
							.store(
									new FileOutputStream(
											getServletContext()
													.getRealPath(
															"/WEB-INF/config/MailProcessor.properties")),
									null);

					out.print(true);
				} else {
					out.print(false);
				}
			} catch (Exception e) {
				e.printStackTrace();
				out.print(false);
			}
		} else if (taskid == 3) {
			String smtp_host = request.getParameter("smtp_host");
			String smtp_port = request.getParameter("smtp_port");
			String smtp_username = request.getParameter("smtp_username");
			String smtp_password = request.getParameter("smtp_password");
			String smtp_from = request.getParameter("smtp_from");
			String smtp_hostname = request.getParameter("smtp_hostname");
			String smtp_server_port = request.getParameter("smtp_server_port");
			try {
				int num_port = Integer.parseInt(smtp_port);
				int num_server_port = Integer.parseInt(smtp_server_port);

				if (smtp_host != null && smtp_port != null
						&& smtp_username != null && smtp_password != null
						&& smtp_hostname != null && smtp_server_port != null) {

					Properties props = new Properties();

					props.load(getServletContext().getResourceAsStream(
							"/WEB-INF/config/SendMail.properties"));
					// System.out.print(props.getProperty("SendMail.from"));
					props.setProperty("mail.smtp.host", smtp_host);
					props.setProperty("mail.smtp.port", smtp_port);
					props.setProperty("SendMail.username", smtp_username);
					props.setProperty("SendMail.password", smtp_password);
					props.setProperty("SendMail.from", smtp_from);
					props
							.setProperty("SendMail.server.hostname",
									smtp_hostname);
					props.setProperty("SendMail.server.port", smtp_server_port);

					props
							.store(
									new FileOutputStream(
											getServletContext()
													.getRealPath(
															"/WEB-INF/config/SendMail.properties")),
									null);
					System.out.print(props.getProperty("SendMail.from"));

					out.print(true);
				} else {
					out.print(false);
				}
			} catch (Exception e) {
				e.printStackTrace();
				out.print(false);
			}
		} else if (taskid == 4) {
			String offer_subject = request.getParameter("offer_subject");
			String offer_body = request.getParameter("offer_body");
			String interview_subject = request
					.getParameter("interview_subject");
			String interview_body = request.getParameter("interview_body");
			String rejection_subject = request
					.getParameter("rejection_subject");
			String rejection_body = request.getParameter("rejection_body");
			String password_subject = request.getParameter("password_subject");
			String password_body = request.getParameter("password_body");
			String mail_rejection_subject = request
					.getParameter("mail_rejection_subject");
			String mail_rejection_body = request
					.getParameter("mail_rejection_body");

			if (offer_subject != null && offer_body != null
					&& interview_subject != null && interview_body != null
					&& rejection_subject != null && rejection_body != null
					&& password_subject != null && password_body != null
					&& mail_rejection_subject != null
					&& mail_rejection_body != null) {
				Properties props = new Properties();

				props.load(getServletContext().getResourceAsStream(
						"/WEB-INF/config/SendMail.properties"));

				props.setProperty("letter.offer.subject", offer_subject);
				props.setProperty("letter.offer.body", offer_body);
				props
						.setProperty("letter.interview.subject",
								interview_subject);
				props.setProperty("letter.interview.body", interview_body);
				props
						.setProperty("letter.rejection.subject",
								rejection_subject);
				props.setProperty("letter.rejection.body", rejection_body);
				props.setProperty("letter.password.subject", password_subject);
				props.setProperty("letter.password.body", password_body);
				props.setProperty("MailProcessor.rejection.subject",
						mail_rejection_subject);
				props.setProperty("MailProcessor.rejection.body",
						mail_rejection_body);

				props.store(new FileOutputStream(getServletContext()
						.getRealPath("/WEB-INF/config/SendMail.properties")),
						null);

				out.print(true);
			} else
				out.print(false);

		}

	}
}
