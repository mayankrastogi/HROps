package com.thebasilisks.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.thebasilisks.Application;
import com.thebasilisks.Employee;
import com.thebasilisks.JobOpportunity;

/**
 * Servlet implementation class HR
 */
public class HR extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final int FILTER_UNREAD = 1;
	private static final int SHORTLIST = 2;
	private static final int REJECT_IN_SHORTLISTING = 3;
	private static final int FILTER_SHORTLISTED = 4;
	private static final int FILTER_REJECTED = 5;
	private static final int REJECTED_REASON = 6;
	private static final int FILTER_SELECTED = 7;
	private static final int NUM_OF_VACANCIES = 8;
	private static final int INTERVIEW_SCORE = 9;
	private static final int SELECT_APPLICATIONS = 10;
	private static final int REJECT_IN_SELECTION = 11;
	
	private PrintWriter out;
	private HttpSession session;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HR() {
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
			if(!employee.getRole().equals("HR"))
				return;
		
		response.setContentType("text/html");
		
		int task = Integer.parseInt(request.getParameter("task"));
		int applicationId;
		String opportunityId, reason;
		
		switch(task)
		{
		case FILTER_UNREAD:
			opportunityId = request.getParameter("oppId");
			printApplications(opportunityId, Application.UNREAD);
			break;
			
		case SHORTLIST:
			applicationId = Integer.parseInt(request.getParameter("appId"));
			out.println(Application.shortlistApplication(applicationId));
			break;
			
		case REJECT_IN_SHORTLISTING:
			applicationId = Integer.parseInt(request.getParameter("appId"));
			reason = request.getParameter("reason");
			out.println(Application.rejectApplication(applicationId, Application.REJECTED_IN_SHORTLISTING, reason));
			break;
			
		case FILTER_SHORTLISTED:
			opportunityId = request.getParameter("oppId");
			printApplications(opportunityId, Application.SHORTLISTED);
			break;
			
		case FILTER_REJECTED:
			opportunityId = request.getParameter("oppId");
			printApplications(opportunityId, Application.REJECTED_IN_SHORTLISTING);
			break;
			
		case REJECTED_REASON:
			applicationId = Integer.parseInt(request.getParameter("appId"));
			reason = Application.getApplication(applicationId).getRejectionReason();
			out.println(reason);
			break;
			
		case FILTER_SELECTED:
			opportunityId = request.getParameter("oppId");
			printApplicationsAndCheckbox(opportunityId, Application.SELECTED);
			break;
		
		case NUM_OF_VACANCIES:
			opportunityId = request.getParameter("oppId");
			out.println(JobOpportunity.getJobOpportunity(opportunityId).getNumOfVacancies());
			break;
		
		case INTERVIEW_SCORE:
			applicationId = Integer.parseInt(request.getParameter("appId"));
			out.println(Application.getApplication(applicationId).getInterviewResult());
			break;
			
		case SELECT_APPLICATIONS:
			opportunityId = request.getParameter("oppId");
			String applications[] = request.getParameterValues("appId");
			selectApplications(applications, opportunityId);
			break;
		
		case REJECT_IN_SELECTION:
			applicationId = Integer.parseInt(request.getParameter("appId"));
			reason = request.getParameter("reason");
			out.println(Application.rejectApplication(applicationId, Application.REJECTED_IN_SELECTION, reason));
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
	
	private void printApplicationsAndCheckbox(String opportunityId, int status)
	{
		Employee employee = (Employee)session.getAttribute("emp_detail");
		ArrayList<Application> list = Application.getApplications(employee.getDeptId(), opportunityId, status);
		Iterator<Application> it = list.iterator();
		Application app = null;
		
		while(it.hasNext())
		{
			app = it.next();
		    out.println("<li data-appid=\""+app.getApplicationID()+"\"><input type=\"checkbox\" name=\"appId\" value=\""+app.getApplicationID()+"\"><a href=\"#\">&lt;" +app.getEmail()+"&gt;<br>\n" +
		    		"<small>["+app.getOpportunityID()+"] "+app.getApplicationDate()+"</small></a>\n"+
		      "</li>");
		}
	}
	
	private void selectApplications(String[] applications, String opportunityId)
	{
		if(applications.length <= JobOpportunity.getJobOpportunity(opportunityId).getNumOfVacancies())
		{
			for(String applicationId : applications)
				Application.selectForHire(Integer.parseInt(applicationId));
			out.println(true);
		}
		else
			out.println(false);
	}

}
