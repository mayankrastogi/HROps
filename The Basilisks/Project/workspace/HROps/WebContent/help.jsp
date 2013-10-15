<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	Employee employee = (Employee) session.getAttribute("emp_detail");
	if (employee == null) //if employee hasn't logged in then redirect him to index page
		response.sendRedirect("login.jsp");
%>
<jsp:include page="WEB-INF/header.jsp" flush="true">
	<jsp:param name="title" value="Help" />
	<jsp:param name="prefix" value="" />
</jsp:include>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.thebasilisks.Employee"%>
<%@page import="com.thebasilisks.JobOpportunity"%>
<%@page import="com.thebasilisks.Application"%><html>
  <div id="sidebar1">
  <div id="filter">
  </div>
  <div id="app_list">
  </div>
<!-- end .sidebar1 --></div>
  <div class="content">
  	<div id="rt_content">
  		<iframe id="help-frame" src="helpServer.do"></iframe>
  	</div>
    
    <!-- end .content --></div>
  <!-- end .container --></div>
</body>

<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/help.js"></script>

</html>