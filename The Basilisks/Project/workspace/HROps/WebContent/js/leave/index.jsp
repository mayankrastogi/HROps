<%@page import="com.thebasilisks.Employee"%>
<%
	Employee employee = (Employee) session.getAttribute("emp_detail");
	if (employee != null) //if employee hasn't logged in then redirect him to index page
		response.sendRedirect("../empHome.jsp");
	else
		response.sendRedirect("../login.jsp");
%>