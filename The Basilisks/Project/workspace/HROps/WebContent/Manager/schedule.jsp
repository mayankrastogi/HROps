<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	Employee employee = (Employee) session.getAttribute("emp_detail");
	if (employee == null) //if employee hasn't logged in then redirect him to index page
		response.sendRedirect("../login.jsp");
	else if(!employee.getRole().equals("MANAGER"))
		response.sendRedirect("../empHome.jsp");
%>
<jsp:include page="../WEB-INF/header.jsp" flush="true">
	<jsp:param name="title" value="Manager - Schedule Interviews" />
	<jsp:param name="prefix" value="../" />
</jsp:include>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.thebasilisks.Employee"%>
<%@page import="com.thebasilisks.JobOpportunity"%>
<%@page import="com.thebasilisks.Application"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Calendar"%><html>
  <div id="sidebar1">
  <div id="filter">
  <label for="filter">Job Opportunity </label>
  <select id="filter" name="filter">
  	<option value="" selected="selected">All</option>
  <%
  ArrayList<JobOpportunity> jobList = JobOpportunity.getJobOpportunities(employee.getDeptId());
  Iterator<JobOpportunity> jobIt = jobList.iterator();
  JobOpportunity jobOpportunity = null;
  while(jobIt.hasNext())
  {
  	jobOpportunity = jobIt.next();
  %>
  	<option value="<%=jobOpportunity.getOpportunityId() %>"><%=jobOpportunity.getOpportunityId() %></option>
  <%
  }
  %>
  </select>
  </div>
  <div id="app_list">
  	<ul class="nav">
  	<%
  	ArrayList<Application> appList = Application.getApplications(employee.getDeptId(), Application.SHORTLISTED);
  	Iterator<Application> appIt = appList.iterator();
  	Application app = null;
  	while(appIt.hasNext())
  	{
  		app = appIt.next();
  		if(app.getInterviewer() != 0)
				continue;
  	%>
      <li data-appid="<%=app.getApplicationID() %>"><a href="#">&lt;<%=app.getEmail() %>&gt;<br>
      <small>[<%=app.getOpportunityID() %>] <%=app.getApplicationDate() %></small></a>
      </li>
    <%
    }
    %>
    </ul>
  </div>
<!-- end .sidebar1 --></div>
  <div class="content">
  	<div id="rt_content">
  	<div style="padding: 20px;">
  		<form id="scheduleForm">
  		<%Calendar calendar = Calendar.getInstance(); %>
  			<ul>
  				<li>
  					<label for="dd">Select Date (dd/mm/yyyy) : </label>
  					<input type="text" name="dd" value="<%=calendar.get(Calendar.DAY_OF_MONTH) %>">/
  					<input type="text" name="mm" value="<%=calendar.get(Calendar.MONTH)+1 %>">/
  					<input type="text" name="yyyy" value="<%=calendar.get(Calendar.YEAR) %>">
  				</li>
  				<li>
  					<label for="time">Select Time Slot : </label>
  					<select name="time">
  						<%
  						for(int i=9;i<=17;i++)
  						{
  						%>
  						<option value="<%=i %>"><%=(i>12)?i%12:i %>:00 <%=(i<12)?"AM":"PM" %></option>
  						<%
  						}
  						%>
  					</select>
  				</li>
  				<li>
  					<label for="interviewer">Select Interviewer : </label>
  					<select name="interviewer">
  					<%
  					ArrayList<Employee> list = Employee.getInterviewers(employee.getEmployeeID());
  					Iterator<Employee> it = list.iterator();
  					Employee interviewer = null;
  					while(it.hasNext())
  					{
	  					interviewer = it.next();
  					%>
  						<option value="<%=interviewer.getEmployeeID() %>"><%=interviewer.getName() %>(<%=interviewer.getEmployeeID() %>)</option>
  					<%
  					}
  					%>
  					</select>
  				</li>
  				<li>
  					<input type="hidden" name="appId" value="">
  					<input type="submit" value="Schedule">
  				</li>
  			</ul>
  		</form>
  	</div>
    </div>
    <!-- end .content --></div>
  <!-- end .container --></div>
</body>

<script type="text/javascript" src="../js/jquery.js"></script>
<script type="text/javascript" src="../js/Manager/schedule.js"></script>

</html>