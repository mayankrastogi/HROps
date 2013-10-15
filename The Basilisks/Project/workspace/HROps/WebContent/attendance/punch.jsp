<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%!String punchHead;%>
<%!String btn_punch;%>

<%
	Employee employee = (Employee) session.getAttribute("emp_detail");
	if (employee == null) //if employee hasn't logged in then redirect him to index page
		response.sendRedirect("../login.jsp");
%>
<jsp:include page="../WEB-INF/header.jsp" flush="true">
	<jsp:param name="title" value="Give Attendance" />
	<jsp:param name="prefix" value="../" />
</jsp:include>
<%@page import="com.thebasilisks.Employee"%>
<%@page import="com.thebasilisks.JobOpportunity"%>
<%@page import="com.thebasilisks.Application"%>
<%@page import="com.thebasilisks.Attendance"%><html>
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
		///////////JSP FILE HANDLES THE PUNCHING IN AND OUT OF ATTENDANCE///////////
		Employee emp = (Employee) session.getAttribute("emp_detail");
		if (emp != null) { //if the employee has not logged in redirect him to login page
			Attendance empAttendance = new Attendance(emp.getEmployeeID());
			if (empAttendance.lastStateCheck() == 1) //1 for out   0 for in
			{
				punchHead = "Punch Out";
				btn_punch = "out";
			} else {
				punchHead = "Punch In";
				btn_punch = "in";
			}
		} else {
			response.sendRedirect("../index.jsp");
		}
	%>

	<div id="punch_head">
		<%
			out.println(punchHead);
		%>
	</div>
	<div id="dt_show"></div>
	<div id="time_show"></div>
	<div id="button_area">
		<%
			out.println("<input type=button name=" + btn_punch + " id= "
					+ btn_punch + " value= '" + punchHead
					+ "' onclick=punch()>");
		%>
	</div>
	<div id="msg"></div>

	</div>
    </div>
    <!-- end .content --></div>
  <!-- end .container --></div>
</body>

<script type="text/javascript" src="../js/jquery.js"></script>
<script type="text/javascript" src="../js/empHome.js"></script>
<script type="text/javascript" src="../js/attendance/bg_code.js"></script>
</html>