package com.thebasilisks.servlets;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.thebasilisks.Application;
import com.thebasilisks.Employee;
import com.thebasilisks.Initializer;

/**
 * Servlet implementation class ResumeServer
 */
public class ResumeServer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ResumeServer() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		if(session.getAttribute("emp_detail") != null && request.getParameter("appId") != null)
		{
			String role = ((Employee)session.getAttribute("emp_detail")).getRole();
			if(role.equals("HR") || role.equals("MANAGER") || role.equals("INTERVIEWER"))
			{
				try {
					int applicationId = Integer.parseInt(request.getParameter("appId"));
					Application application = Application.getApplication(applicationId);
					
					File pdfFile = new File(Initializer.getServletContext().getRealPath("/WEB-INF/resume/"+application.getResumeLocation()));
					String fileName = application.getOpportunityID()+"_"+application.getEmail()+".pdf";
					
					response.setContentType("application/pdf");
					response.addHeader("Content-Disposition", "inline; filename="+fileName);
					response.setContentLength((int)pdfFile.length());
					
					FileInputStream fileInputStream = new FileInputStream(pdfFile);
					ServletOutputStream outputStream = response.getOutputStream();
					
					//byte bytes[] = new byte[2048];
					int bytes;
					while((bytes = fileInputStream.read()) != -1)
						outputStream.write(bytes);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
