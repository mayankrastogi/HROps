<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	Employee employee = (Employee) session.getAttribute("emp_detail");
	if (employee == null) //if employee hasn't logged in then redirect him to index page
		response.sendRedirect("login.jsp");
%>
<jsp:include page="WEB-INF/header.jsp" flush="true">
	<jsp:param name="title" value="Change Password" />
	<jsp:param name="prefix" value="" />
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
  	<br>
  	<h2>Change Password</h2>
  	<form id="changePassForm">
  		<label for="oldPass">Old Password</label>
  		<input type="password" name="oldPass"><br>
  		<label for="newPass">New Password</label>
  		<input type="password" name="newPass"><br>
  		<label for="confirmPass">Confirm Password</label>
  		<input type="password" name="confirmPass"><span id="tick"></span><br>
  		<input type="submit" value="Change Password">
  	</form>
  	</div>
    </div>
    <!-- end .content --></div>
  <!-- end .container --></div>
</body>

<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/changePass.js"></script>

</html>