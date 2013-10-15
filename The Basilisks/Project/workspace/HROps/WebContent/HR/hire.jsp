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
	<jsp:param name="title" value="HR - Hire Applicants" />
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
  	<form id="applications">
  	<input type="hidden" name="oppId" value="<%=(!jobList.isEmpty())? jobList.get(0).getOpportunityId() : "" %>">
  	<ul class="nav">
  	<%
  	ArrayList<Application> appList;
  	if(jobList.isEmpty())
  		appList = new ArrayList<Application>();
  	else
  		appList = Application.getApplications(employee.getDeptId(),jobList.get(0).getOpportunityId(),Application.SELECTED);
  	Iterator<Application> appIt = appList.iterator();
  	Application app = null;
  	while(appIt.hasNext())
  	{
  		app = appIt.next();
  	%>
      <li data-appid="<%=app.getApplicationID() %>">
      <input type="checkbox" name="appId" value="<%=app.getApplicationID() %>">
      <a href="#">&lt;<%=app.getEmail() %>&gt;<br>
      <small>[<%=app.getOpportunityID() %>] <%=app.getApplicationDate() %></small></a>
      </li>
    <%
    }
    %>
    </ul>
    </form>
  </div>
  <div id="hire-button"><a href="#">Hire Selected Applicants</a></div>
<!-- end .sidebar1 --></div>
  <div class="content">
  	<div id="rt_menubar">
  		<ul>
  			<li id="vacancy" class="no-bttn"><%=(!jobList.isEmpty())? "No. of vacancies : "+jobList.get(0).getNumOfVacancies() : "" %></li>
  			<li id="score" class="no-bttn"></li>
  			<li id="reject-button">Reject Application</li>
  		</ul>
  	</div>
  	<div id="rt_content">
  		<iframe id="resume-frame" src=""></iframe>
  	</div>
    
    <!-- end .content --></div>
  <!-- end .container --></div>
</body>

<script type="text/javascript" src="../js/jquery.js"></script>
<script type="text/javascript" src="../js/HR/hire.js"></script>

</html>