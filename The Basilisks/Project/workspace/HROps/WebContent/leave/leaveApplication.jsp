<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@page import="java.util.*" %>
<%
	Employee employee = (Employee) session.getAttribute("emp_detail");
	if (employee == null) //if employee hasn't logged in then redirect him to index page
		response.sendRedirect("../login.jsp");
%>
<jsp:include page="../WEB-INF/header.jsp" flush="true">
	<jsp:param name="title" value="Apply for Leave" />
	<jsp:param name="prefix" value="../" />
</jsp:include>
<%@page import="com.thebasilisks.Employee"%>
<%@page import="com.thebasilisks.JobOpportunity"%>
<%@page import="com.thebasilisks.Application"%>
<%@page import="com.thebasilisks.DBConnection"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.ResultSet"%><html>
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
	<div style="padding: 20px">
	<h2>Apply for Leave</h2><br>
	<form method="POST" name="leave" id="leaveForm" onsubmit="return false">
	<%
	Employee emp = (Employee)session.getAttribute("emp_detail");
	out.println("<input type=\"hidden\" name=\"empid\" value=\""+emp.getEmployeeID()+"\">");

	%>
	Leave From (dd-mm-yyyy) : 
	<input type="text" id="dd" name="dd">
	<input type="text" id="mm" name="mm">
	<input type="text" id="yyyy" name="yyyy">
	<br>

	Leave Till (dd-mm-yyyy) :
	<input type="text" id="dd1" name="dd1">
	<input type="text" id="mm1" name="mm1">
	<input type="text" id="yyyy1" name="yyyy1">
	<br>

	Reason : 
	<textarea id="reason" name="reason"></textarea>
	<br>
	<input type = "hidden" name = "status" value = "w">
	<input type="submit" value="submit" onclick="submitForm5()">
	</form>
		
  	</div><br>
  	<div style="padding: 20px">
  	<h3>Leave status of previous applications</h3>
  	<table cellpadding="10px" border="3">
  		<tr>
  			<th>Leave From</th>
  			<th>Leave Till</th>
  			<th>Reason</th>
  			<th>Status</th>
  		</tr>
  		<%
  		Connection connection = DBConnection.getConnection();
  		String sql = "SELECT * FROM HROPS_SCHEMA.\"LEAVE\" WHERE EMPLOYEE_ID=?";
  		PreparedStatement statement = connection.prepareStatement(sql);
  		statement.setInt(1, emp.getEmployeeID());
  		ResultSet result = statement.executeQuery();
  		while(result.next())
  		{
  		%>
  		<tr>
  			<td><%=result.getDate(3).toString() %></td>
  			<td><%=result.getDate(4).toString() %></td>
  			<td><%=result.getString(5) %></td>
  			<%
  			String status = result.getString(6);
  			if(status.equals("w"))
  				status = "Pending";
  			else if(status.equals("a"))
  				status = "Approved";
  			else
  				status = "Rejected";
  			%>
  			<td><%=status %></td>
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