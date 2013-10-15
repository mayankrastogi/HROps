package com.thebasilisks.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.thebasilisks.Application;
import com.thebasilisks.Employee;

/**
 * Servlet implementation class Interviewer
 */
public class Interviewer extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private static final int PRINT_SCHEDULE = 1;
	private static final int FILTER_TIME = 2;
	private static final int FILTER_APPLICATIONS = 3;
	private static final int PRINT_SCORE = 4;
	private static final int UPDATE_SCORE = 5;
	
	private PrintWriter out;
	private HttpSession session;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Interviewer() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		out = response.getWriter();
		session = request.getSession();
		
		Employee employee = (Employee)session.getAttribute("emp_detail");
		if(employee == null)
			return;
		else
			if(!employee.getRole().equals("INTERVIEWER"))
				return;
		
		response.setContentType("text/html");
		
		int task = Integer.parseInt(request.getParameter("task"));
		int applicationId, interviewResult;
		Date date;
		Time time;
		
		switch(task)
		{
		case PRINT_SCHEDULE:
			date = Date.valueOf(request.getParameter("date"));
			printSchedule(employee.getEmployeeID(), date);
			break;
			
		case FILTER_TIME:
			date = Date.valueOf(request.getParameter("date"));
			printTime(employee.getEmployeeID(), date);
			break;
			
		case FILTER_APPLICATIONS:
			date = Date.valueOf(request.getParameter("date"));
			time = Time.valueOf(request.getParameter("time"));
			printApplications(date, time);
			break;
			
		case PRINT_SCORE:
			applicationId = Integer.parseInt(request.getParameter("appId"));
			out.println(Application.getApplication(applicationId).getInterviewResult());
			break;
			
		case UPDATE_SCORE:
			applicationId = Integer.parseInt(request.getParameter("appId"));
			interviewResult = Integer.parseInt(request.getParameter("score"));
			out.println(Application.updateScore(applicationId, interviewResult));
		}
	}
	
	private void printTime(int interviewer, Date interviewDate) {
		ArrayList<Time> list = Application.getInterviewTimes(interviewer, interviewDate);
		Iterator<Time> it = list.iterator();
		Time time = null;
		
		out.println("<option value=\"\"></option>");
		while(it.hasNext())
		{
			time = it.next();
			out.println("<option value=\""+time.toString()+"\">"+time.toString()+"</option>");
		}
	}

	private void printApplications(Date interviewDate, Time interviewTime)
	{
		Employee employee = (Employee)session.getAttribute("emp_detail");
		ArrayList<Application> list = Application.getApplications(employee.getEmployeeID(), interviewDate, interviewTime);
		Iterator<Application> it = list.iterator();
		Application app = null;
		
		while(it.hasNext())
		{
			app = it.next();
		    out.println("<li data-appid=\""+app.getApplicationID()+"\"><a href=\"#\">&lt;" +app.getEmail()+"&gt;<br>\n" +
		    		"<small>["+app.getOpportunityID()+"] "+app.getApplicationDate()+"</small></a>\n"+
		      "</li>");
		}
	}

	private void printSchedule(int interviewer, Date interviewDate) {
		ArrayList<Time> list = Application.getInterviewTimes(interviewer, interviewDate);
		Iterator<Time> it = list.iterator();
		Time time = null;
		int num;
		
		if(list.isEmpty())
			return;
		
		out.println("<table cellpadding=\"5px\" border=\"2\">");
		out.println("<tr>");
		out.println("<th>Interview Time</td>");
		out.println("<th>No. of Applicants</td>");
		out.println("</tr>");
		while(it.hasNext())
		{
			time = it.next();
			num = Application.getNumberOfApplications(interviewer, interviewDate, time);
			out.println("<tr>");
			out.println("<td>"+time.toString()+"</td>");
			out.println("<td>"+num+"</td>");
			out.println("</tr>");
		}
		out.println("</table>");
	}

}
