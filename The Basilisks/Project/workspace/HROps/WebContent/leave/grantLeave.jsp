<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.beans.Statement"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.util.*" %>
<%
	Employee employee = (Employee) session.getAttribute("emp_detail");
	if (employee == null) //if employee hasn't logged in then redirect him to index page
		response.sendRedirect("../login.jsp");
%>
<jsp:include page="../WEB-INF/header.jsp" flush="true">
	<jsp:param name="title" value="Grant Leave" />
	<jsp:param name="prefix" value="../" />
</jsp:include>
<%@page import="com.thebasilisks.Employee"%>
<%@page import="com.thebasilisks.JobOpportunity"%>
<%@page import="com.thebasilisks.Application"%>
<%@page import="com.thebasilisks.DBConnection"%><html>
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
	<%
	Employee emp = (Employee)session.getAttribute("emp_detail");
	int sup=emp.getEmployeeID();
	String sql="select * from HROPS_SCHEMA.LEAVE where EMPLOYEE_ID IN (select EMPLOYEE_ID from HROPS_SCHEMA.EMPLOYEE where SUPERVISOR = ?) and STATUS = ?";
	Connection con = DBConnection.getConnection(); //getting connected to database
	PreparedStatement st = con.prepareStatement(sql);
	st.setInt(1, sup);
	st.setString(2, "w");
	ResultSet resultset = st.executeQuery();
	%>
		<div style="padding: 20px">
		<table border=1 width ="90%">
		<tr>
		<td>Employee ID</td>
		<td>From Date</td>
		<td>To Date</td>
		<td>Reason</td>
		<td>Approval</td>
		</tr>
	<%
	while (resultset.next())
	{
	%>
		<tr>
		<td><%out.println(resultset.getInt(2)); %></td>
		<td><%out.println(resultset.getDate(3)); %></td>
		<td><%out.println(resultset.getDate(4)); %></td>
		<td><%out.println(resultset.getString(5)); %></td>
		<td>
		<form method="POST" name="grant" id="grant" onsubmit="return false">
		<input type="radio" name="approve" value="a">Accept
		<input type="radio" name="approve" value="r">Reject
		<input type="hidden" name="leaveId" value="<%=resultset.getInt(1) %>">
		<input type="submit" value="Submit" onclick="submitForm6()">
		</form>
		</td>
		</tr>
	<%
	}
	
	%>	
	</table>
	</div>
  	</div>
  	</div>
    
    <!-- end .content --></div>
  <!-- end .container --></div>
</body>

<script type="text/javascript" src="../js/jquery.js"></script>
<script type="text/javascript" src="../js/leave/leave.js"></script>

</html>