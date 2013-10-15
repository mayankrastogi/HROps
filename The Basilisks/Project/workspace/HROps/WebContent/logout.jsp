<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%
		session.removeAttribute("emp_detail"); //remove the session variable
		session.invalidate(); // invalidate the session
		response.sendRedirect("index.jsp"); //redirect it to loin page
	%>