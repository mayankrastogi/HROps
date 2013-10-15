<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	Employee employee = (Employee) session.getAttribute("emp_detail");
	if (employee == null) //if employee hasn't logged in then redirect him to index page
		response.sendRedirect("../login.jsp");
%>
<jsp:include page="../WEB-INF/header.jsp" flush="true">
	<jsp:param name="title" value="View Attendance" />
	<jsp:param name="prefix" value="../" />
</jsp:include>
<%@page import="java.sql.*" %>
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
   if(session.isNew()==false){
   Employee sess_emp=(Employee)session.getAttribute("emp_detail");
   int empid=sess_emp.getEmployeeID();
   Connection con=DBConnection.getConnection();
   Statement st=con.createStatement();
   ResultSet resultset=st.executeQuery("select * from HROPS_SCHEMA.ATTENDANCE where EMPLOYEE_ID="+empid);		
   out.println("<table cellpadding=\"10\" border=\"2\"><tr><th colspan=\"2\">IN</th><th colspan=\"2\">OUT</th></tr><tr><th>Date</th><th>Time</th><th>Date</th><th>Time</th></tr>");
   while(resultset.next())
   {
		String in_dt[],out_dt[];
	   	in_dt=resultset.getString(3).split(" ");
	  	out.print("<tr><td>"+in_dt[0]+"</td><td>"+in_dt[1].substring(0, 5)+"</td>");
	  	if(resultset.getString(4) != null)
	  	{
	  		out_dt=resultset.getString(4).split(" ");
	  		out.print("<td>"+out_dt[0]+"</td><td>"+out_dt[1].substring(0, 5)+"</td>");
	  	}
	  	else
	  		out.print("<td></td><td></td>");
	  	out.println("</tr>");
   }
   out.println("</table>");
   }
   else
   {
	   response.sendRedirect("../index.jsp");
   }
   
%>
		
  	</div>
    </div>
    <!-- end .content --></div>
  <!-- end .container --></div>
</body>

<script type="text/javascript" src="../js/jquery.js"></script>
<script type="text/javascript" src="../js/empHome.js"></script>

</html>