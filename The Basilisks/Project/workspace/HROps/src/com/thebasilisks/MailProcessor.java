package com.thebasilisks;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

import javax.mail.BodyPart;
import javax.mail.FetchProfile;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.event.MessageCountAdapter;
import javax.mail.event.MessageCountEvent;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.search.FlagTerm;
import javax.servlet.ServletContext;

public class MailProcessor implements Runnable {

	private String host;
	private int port;
	private String username;
	private String password;
	private String protocol;
	private int pollingFrequency;
	private boolean debug;
	private Properties props;
	private boolean running;
	private ServletContext context;

	private static final Logger logger = Logger.getLogger(MailProcessor.class
			.toString());

	public MailProcessor(Properties props, ServletContext context) {
		this.props = props;

		host = props.getProperty("MailProcessor.host");
		port = Integer.parseInt(props.getProperty("MailProcessor.port"));
		username = props.getProperty("MailProcessor.username");
		password = props.getProperty("MailProcessor.password");
		protocol = props.getProperty("MailProcessor.protocol");
		pollingFrequency = Integer.parseInt(props
				.getProperty("MailProcessor.pollingFrequency"));
		debug = Boolean.parseBoolean(props.getProperty("debug"));
		this.context = context;

		props.put("mail." + protocol + ".auth.plain.disable", "true");
		props.put("mail." + protocol + ".auth.ntlm.disable", "true");

		running = true;
	}

	public void terminate() {
		running = false;
	}

	public void run() {

		logger.info("Polling started...");
		final Session session = Session.getInstance(props, null);
		session.setDebug(debug);
		Folder inbox = null;
		Store store = null;

		/*
		 * This while loop will make sure the connection is reset if the server
		 * closes the connection. Some MS Exchange Servers may be configured to
		 * close the connection if there is no activity on the inbox folder for
		 * a time period.
		 */
		while (!Thread.currentThread().isInterrupted() && running) {

			try {
				logger
						.info("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
				logger.info("New Connection Established");
				logger.info("Host: " + host);
				logger.info("Port: " + port);
				logger.info("Username: " + username);
				logger.info("Protocol: " + protocol);
				logger.info("Polling Frequency: " + pollingFrequency);
				logger.info("Debug: " + debug);
				logger
						.info("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

				store = session.getStore(protocol);
				store.connect(host, port, username, password);
				inbox = store.getFolder("INBOX");

				if (inbox == null) {
					System.err.println("Cant find INBOX");
					break;
				}

				inbox.open(Folder.READ_ONLY);

				Message msgs[] = inbox.search(new FlagTerm(new Flags(
						Flags.Flag.SEEN), false));
				logger.info("No. of Unread Messages : " + msgs.length);

				/* Use a suitable FetchProfile */
				FetchProfile fp = new FetchProfile();
				fp.add(FetchProfile.Item.ENVELOPE);

				fp.add(FetchProfile.Item.CONTENT_INFO);

				inbox.fetch(msgs, fp);

				printMessage(msgs);
				processMessage(msgs);

				inbox.addMessageCountListener(new MessageCountAdapter() {

					public void messagesAdded(MessageCountEvent ev) {

						Message[] messages = ev.getMessages();
						logger.info("Got " + messages.length + " new messages");

						printMessage(messages);
						processMessage(messages);
					}
				});

				// Check mail once in "pollingFrequency" MILLIseconds
				while (!Thread.currentThread().isInterrupted() && running) {
					try {
						Thread.sleep(pollingFrequency); // sleep for
						// pollingFrequency
						// milliseconds
						// This is to force the IMAP server to send us EXISTS
						// notifications.
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						running = false;
					}
					inbox.getMessageCount();
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (inbox != null) {
						inbox.close(false);
					}
					if (store != null) {
						store.close();
					}
				} catch (MessagingException e) {
					e.printStackTrace();
				}
			}
		}// while
		logger.info("MailProcessor terminated");
	}

	void processMessage(Message[] messages) {
		for (Message message : messages) {
			Application application = new Application();
			try {
				application.setEmail(new InternetAddress(InternetAddress
						.toString(message.getFrom())).getAddress());
				application.setApplicationDate(message.getReceivedDate());
				application.setOpportunityID(message.getSubject());
				application.setStatus(Application.UNREAD);
			} catch (AddressException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (MessagingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			// Check if Opportunity Id is valid
			JobOpportunity opportunity = JobOpportunity
					.getJobOpportunity(application.getOpportunityID());
			if (opportunity != null) {
				// Check if application was received within the last date
				if (!application.getApplicationDate().after(
						opportunity.getLastDate())) {
					// Check if application already exists
					if (!Application.exists(application.getEmail(), application
							.getOpportunityID())) {
						// Download Attachment only if it is in pdf
						// format
						boolean noResume = true;
						try {
							if (message.getContent() instanceof Multipart) {
								Multipart multipart = (Multipart) message
										.getContent();
								// System.out.println(multipart.getCount());

								for (int i = 0; i < multipart.getCount(); i++) {
									BodyPart bodyPart = multipart
											.getBodyPart(i);
									if (!Part.ATTACHMENT
											.equalsIgnoreCase(bodyPart
													.getDisposition())) {
										continue; // dealing with attachments
										// only
									}

									noResume = false;

									String fileName = bodyPart.getFileName();
									if (!fileName.endsWith(".pdf")) {
										logger
												.info("Attachment is not in Portable Document Format(pdf)");
										SendMail
												.sendProcessingErrorMail(
														application.getEmail(),
														"Attachment is not in Portable Document Format(pdf)");
										break;
									}

									InputStream is = bodyPart.getInputStream();
									fileName = application.getOpportunityID()
											+ "_"
											+ application.getEmail()
											+ fileName.substring(fileName
													.lastIndexOf('.'));
									File f = new File(context
											.getRealPath("/WEB-INF/resume/")
											+ fileName);
									FileOutputStream fos = new FileOutputStream(
											f);
									byte[] buf = new byte[4096];
									int bytesRead;
									while ((bytesRead = is.read(buf)) != -1) {
										fos.write(buf, 0, bytesRead);
									}
									fos.close();

									application.setResumeLocation(fileName);
									application.storeApplication();
									logger.info("Application Stored");
									break; // So that only first attachment gets
									// downloaded
								}
							}
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (MessagingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if (noResume) {
							logger
									.info("No resume received with the aplication");
							SendMail.sendProcessingErrorMail(application
									.getEmail(),
									"No resume received with the aplication");
						}
					} else {
						logger.info("Earlier application already exists");
						SendMail.sendProcessingErrorMail(
								application.getEmail(),
								"Earlier application already exists.");
					}
				} else {
					logger.info("Application received after the last date");
					SendMail.sendProcessingErrorMail(application.getEmail(),
							"Application received after the last date.");
				}
			} else {
				logger.info("Opportunity Id does not exist");
				SendMail.sendProcessingErrorMail(application.getEmail(),
						"Opportunity Id does not exist.");
			}
		}
	}

	void printMessage(Message[] messages) {
		for (int i = 0; i < messages.length; i++) {
			logger
					.info("***********************************************************");
			logger.info("------------ Message " + (i + 1) + " ------------");
			try {

				String from = (new InternetAddress(InternetAddress
						.toString(messages[i].getFrom())).getAddress());
				String replyTo = InternetAddress.toString(messages[i]
						.getReplyTo());
				String to = InternetAddress.toString(messages[i]
						.getRecipients(Message.RecipientType.TO));
				String subject = messages[i].getSubject();
				String sentDate = messages[i].getSentDate().toString();
				String body = getText(messages[i]);

				logger.info("From: " + from);
				logger.info("Reply To: " + replyTo);
				logger.info("To: " + to);
				logger.info("Subject: " + subject);
				logger.info("Sent Date: " + sentDate);
				logger.info("Body: " + body);
				logger
						.info("***********************************************************");
			} catch (MessagingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Return the primary text content of the message.
	 * 
	 * @param p
	 * @return
	 * @throws MessagingException
	 * @throws IOException
	 */
	private String getText(Part p) throws MessagingException, IOException {

		if (p.isMimeType("text/*")) {
			logger.info("text/*");
			String s = (String) p.getContent();
			return s;
		}

		if (p.isMimeType("multipart/alternative")) {
			logger.info("multipart/alternative");

			// prefer html text over plain text
			Multipart mp = (Multipart) p.getContent();
			String text = "";

			for (int i = 0; i < mp.getCount(); i++) {
				Part bp = mp.getBodyPart(i);
				if (bp.isMimeType("text/plain")) {
					logger.info("text/plain");
					if (text == null) {
						text = getText(bp);
					}
					continue;
				} else if (bp.isMimeType("text/html")) {
					logger.info("text/html");
					String s = getText(bp);
					if (s != null)
						return s;
				} else {
					return getText(bp);
				}
			}
			return text;
		} else if (p.isMimeType("multipart/*")) {
			logger.info("multipart/*");
			Multipart mp = (Multipart) p.getContent();
			for (int i = 0; i < mp.getCount(); i++) {
				String s = getText(mp.getBodyPart(i));
				if (s != null)
					return s;
			}
		}

		return null;
	}
}
