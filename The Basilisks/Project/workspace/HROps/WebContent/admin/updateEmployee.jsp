<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	Employee employee = (Employee) session.getAttribute("emp_detail");
	if (employee == null) //if employee hasn't logged in then redirect him to index page
		response.sendRedirect("../login.jsp");
	else if(!employee.getRole().equals("ADMIN"))
		response.sendRedirect("../empHome.jsp");
%>
<jsp:include page="../WEB-INF/header.jsp" flush="true">
	<jsp:param name="title" value="Admin - Update Employee" />
	<jsp:param name="prefix" value="../" />
</jsp:include>
<%@page import="com.thebasilisks.Employee"%>
<%@page import="com.thebasilisks.JobOpportunity"%>
<%@page import="com.thebasilisks.Application"%><html>
  <div id="sidebar1">
  <div id="filter">
  </div>
  <div id="app_list">
  	<ul class="nav">
  	
    </ul>
  </div>
<!-- end .sidebar1 --></div>
  <div class="content">
  	<div id="rt_content">
  	<div style="padding: 20px;">
	<h2>Update Employee</h2><center>
	<div id="srch_area">
	<label for="empId">Search Employee : </label>
	<input type="text" name="empId" id="empId" width="80%">
	<input type="button" name="btn_srchemp" id="btn_srchemp" value="Go" onclick="search_emp()">
	</div></center>
	<div id="result_area">

	</div>
	</div>
  	</div>
    
    <!-- end .content --></div>
  <!-- end .container --></div>
</body>

<script type="text/javascript" src="../js/jquery.js"></script>
<script type="text/javascript" src="../js/admin/admin.js"></script>

</html>