<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	Employee employee = (Employee) session.getAttribute("emp_detail");
	if (employee == null) //if employee hasn't logged in then redirect him to index page
		response.sendRedirect("../login.jsp");
	else if(!employee.getRole().equals("HR"))
		response.sendRedirect("../empHome.jsp");
%>
<jsp:include page="../WEB-INF/header.jsp" flush="true">
	<jsp:param name="title" value="HR - View Shortlisted Applications" />
	<jsp:param name="prefix" value="../" />
</jsp:include>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.thebasilisks.Employee"%>
<%@page import="com.thebasilisks.JobOpportunity"%>
<%@page import="com.thebasilisks.Application"%><html>
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
  		<iframe id="resume-frame" src=""></iframe>
  	</div>
    
    <!-- end .content --></div>
  <!-- end .container --></div>
</body>

<script type="text/javascript" src="../js/jquery.js"></script>
<script type="text/javascript" src="../js/HR/shortlisted.js"></script>

</html>