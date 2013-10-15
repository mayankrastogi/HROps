package com.thebasilisks;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Application Lifecycle Listener implementation class Initializer
 *
 */
public class Initializer implements ServletContextListener {

	private Properties props;
	private InputStream in;
	private MailProcessor mailProcessor;
	private static ServletContext servletContext;
	Thread thread;

	private static final Logger logger = Logger.getLogger(Initializer.class
			.toString());

    /**
     * Default constructor. 
     */
    public Initializer() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent sce) {
    	logger.info("Stopping MailProcessor . . .");
    	thread.interrupt();
    	mailProcessor.terminate();
    	logger.info("Service stopped");
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent sce) {
    	
    	// Initialize DBConnection for Connection Pooling
    	servletContext = sce.getServletContext();
    	props = new Properties();
    	in = sce.getServletContext().getResourceAsStream("/WEB-INF/config/DBConnection.properties");
		try {
			props.load(in);
			logger.info("----Properties Loaded!!----");
		} catch (IOException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}
		DBConnection.getInstance(props);
		
		// Initialize and start MailProcessor service
    	props = new Properties();
		in = sce.getServletContext().getResourceAsStream("/WEB-INF/config/MailProcessor.properties");
		try {
			props.load(in);
			logger.info("----Properties Loaded!!----");
		} catch (IOException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		mailProcessor = new MailProcessor(props, sce.getServletContext());
		thread = new Thread(mailProcessor);
		thread.start();
		logger.info("Service started");
    }

	/**
	 * @return the servletContext
	 */
	public static ServletContext getServletContext() {
		return servletContext;
	}
};