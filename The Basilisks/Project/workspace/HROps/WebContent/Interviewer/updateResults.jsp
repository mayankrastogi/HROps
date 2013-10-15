<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	Employee employee = (Employee) session.getAttribute("emp_detail");
	if (employee == null) //if employee hasn't logged in then redirect him to index page
		response.sendRedirect("../login.jsp");
	else if(!employee.getRole().equals("INTERVIEWER"))
		response.sendRedirect("../empHome.jsp");
%>
<jsp:include page="../WEB-INF/header.jsp" flush="true">
	<jsp:param name="title" value="Interviewer - Update Interview Results" />
	<jsp:param name="prefix" value="../" />
</jsp:include>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.thebasilisks.Employee"%>
<%@page import="com.thebasilisks.JobOpportunity"%>
<%@page import="com.thebasilisks.Application"%>
<%@page import="java.sql.Date"%><html>
  <div id="sidebar1">
  <div id="filter-date">
  <label for="date">Select Date </label>
  <select id="date" name="date">
  	<option value="" selected="selected"></option>
<%
ArrayList<Date> list = Application.getInterviewDates(employee.getEmployeeID());
Iterator<Date> it = list.iterator();
Date date = null;
while(it.hasNext())
{
	date = it.next();
%>
  	<option value="<%=date.toString() %>"><%=date.toString() %></option>
<%
}
%>
  </select>
  </div>
  <div id="filter-time">
  <label for="time">Select Time </label>
  <select id="time" name="time">
  	<option value="" selected="selected"></option>
  </select>
  </div>
  <div id="app_list">
  	<ul class="nav">
    </ul>
  </div>
<!-- end .sidebar1 --></div>
  <div class="content">
  	<div id="rt_menubar">
  		<ul>
  			<li id="score" class="no-bttn">Interview Score : <input type="text" name="score"></li>
  			<li id="update-score-button">Update</li>
  		</ul>
  	</div>
  	<div id="rt_content">
  		<iframe id="resume-frame" src=""></iframe>
  	</div>
    
    <!-- end .content --></div>
  <!-- end .container --></div>
</body>

<script type="text/javascript" src="../js/jquery.js"></script>
<script type="text/javascript" src="../js/Interviewer/updateResults.js"></script>

</html>