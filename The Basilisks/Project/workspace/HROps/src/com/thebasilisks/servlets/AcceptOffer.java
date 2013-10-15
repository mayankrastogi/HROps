package com.thebasilisks.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thebasilisks.Application;

/**
 * Servlet implementation class AcceptOffer
 */
public class AcceptOffer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AcceptOffer() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String offerId = request.getParameter("offerId");
		String task = request.getParameter("task");
		
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		
		if(offerId != null && task != null)
		{
			out.println("<br><br><br>");
			if(task.equals("accept"))
			{
				if(Application.acceptOrRejectOffer(offerId, Application.OFFER_ACCEPTED))
				{
					out.println("<center><h3>You have chosen to accept our offer letter.<br>Your employee details have been mailed to you</h3></center>");
				}
				else
					out.println("<center><h3>An error occured while processing your request. Please try again.<br>Contact the administrator if problem persists.</h3></center>");
			}
			else if(task.equals("reject"))
			{
				if(Application.acceptOrRejectOffer(offerId, Application.OFFER_REJECTED))
				{
					out.println("<center><h3>You have chosen to reject our offer letter. We would have loved to have you as our employee.</h3></center>");
				}
				else
					out.println("<center><h3>An error occured while processing your request. Please try again.<br>Contact the administrator if problem persists.</h3></center>");
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
