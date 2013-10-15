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
 * Servlet implementation class Manager
 */
public class Manager extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final int FILTER_SHORTLISTED = 1;
	private static final int SCHEDULE_INTERVIEW = 2;
	private static final int FILTER_INTERVIEWED = 3;
	private static final int INTERVIEW_SCORE = 4;
	private static final int SELECT = 5;
	private static final int REJECT = 6;
	private static final int FILTER_SELECTED = 7;
	private static final int FILTER_REJECTED = 8;
	private static final int REJECTED_REASON = 9;

	private PrintWriter out;
	private HttpSession session;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Manager() {
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
			if(!employee.getRole().equals("MANAGER"))
				return;
		
		response.setContentType("text/html");
		
		int task = Integer.parseInt(request.getParameter("task"));
		int applicationId;
		String opportunityId, reason, dateString;
		
		switch(task)
		{
		case FILTER_SHORTLISTED:
			opportunityId = request.getParameter("oppId");
			printUnscheduledApplications(opportunityId, Application.SHORTLISTED);
			break;
			
		case SCHEDULE_INTERVIEW:
			applicationId = Integer.parseInt(request.getParameter("appId"));
			dateString = request.getParameter("yyyy")+"-"+request.getParameter("mm")+"-"+request.getParameter("dd");
			scheduleInterview(applicationId, Integer.parseInt(request.getParameter("interviewer")), dateString, request.getParameter("time")+":00:00");
			break;
			
		case FILTER_INTERVIEWED:
			opportunityId = request.getParameter("oppId");
			printInterviewedApplications(opportunityId);
			break;
			
		case INTERVIEW_SCORE:
			applicationId = Integer.parseInt(request.getParameter("appId"));
			out.println(Application.getApplication(applicationId).getInterviewResult());
			break;
			
		case SELECT:
			applicationId = Integer.parseInt(request.getParameter("appId"));
			out.println(Application.selectApplication(applicationId));
			break;
			
		case REJECT:
			applicationId = Integer.parseInt(request.getParameter("appId"));
			reason = request.getParameter("reason");
			out.println(Application.rejectApplication(applicationId, Application.REJECTED_IN_SELECTION, reason));
			break;
			
		case FILTER_SELECTED:
			opportunityId = request.getParameter("oppId");
			printApplications(opportunityId, Application.SELECTED);
			break;
			
		case FILTER_REJECTED:
			opportunityId = request.getParameter("oppId");
			printApplications(opportunityId, Application.REJECTED_IN_SELECTION);
			break;
			
		case REJECTED_REASON:
			applicationId = Integer.parseInt(request.getParameter("appId"));
			reason = Application.getApplication(applicationId).getRejectionReason();
			out.println(reason);
			break;
		}
	}

	private void printApplications(String opportunityId, int status)
	{
		Employee employee = (Employee)session.getAttribute("emp_detail");
		ArrayList<Application> list = Application.getApplications(employee.getDeptId(), opportunityId, status);
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
	
	private void printUnscheduledApplications(String opportunityId, int status)
	{
		Employee employee = (Employee)session.getAttribute("emp_detail");
		ArrayList<Application> list = Application.getApplications(employee.getDeptId(), opportunityId, status);
		Iterator<Application> it = list.iterator();
		Application app = null;
		
		while(it.hasNext())
		{
			app = it.next();
			if(app.getInterviewer() != 0)
				continue;
		    out.println("<li data-appid=\""+app.getApplicationID()+"\"><a href=\"#\">&lt;" +app.getEmail()+"&gt;<br>\n" +
		    		"<small>["+app.getOpportunityID()+"] "+app.getApplicationDate()+"</small></a>\n"+
		      "</li>");
		}
	}
	
	private void printInterviewedApplications(String opportunityId)
	{
		Employee employee = (Employee)session.getAttribute("emp_detail");
		ArrayList<Application> list = Application.getApplications(employee.getDeptId(), opportunityId, Application.SHORTLISTED);
		Iterator<Application> it = list.iterator();
		Application app = null;
		
		while(it.hasNext())
		{
			app = it.next();
			//if(app.getInterviewer() == 0 || app.getInterviewResult() == 0)
			if(app.getInterviewer() == 0)
				continue;
		    out.println("<li data-appid=\""+app.getApplicationID()+"\"><a href=\"#\">&lt;" +app.getEmail()+"&gt;<br>\n" +
		    		"<small>["+app.getOpportunityID()+"] "+app.getApplicationDate()+"</small></a>\n"+
		      "</li>");
		}
	}
	
	private void scheduleInterview(int applicationId, int interviewer, String dateString, String timeString)
	{
		Date interviewDate = null;
		Time interviewTime = null;
		try {
			interviewDate = Date.valueOf(dateString);
			interviewTime = Time.valueOf(timeString);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			out.println(false);
			e.printStackTrace();
			return;
		}
		
		out.println(Application.scheduleInterview(applicationId, interviewer, interviewDate, interviewTime));
	}
}
