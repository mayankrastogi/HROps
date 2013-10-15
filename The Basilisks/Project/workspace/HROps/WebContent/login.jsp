<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login - The Basilisks HRM</title>
	<link type="image/x-icon" href="favicon.ico" rel="shortcut icon">
	<!-- Load stylesheets -->
	<link type="text/css" rel="stylesheet" href="css/style.css" media="screen" />
	<!-- // Load stylesheets -->
</head>

<%@page import="java.security.*"%>
<%@page import="java.sql.*"%>
<%@page import="com.thebasilisks.*"%>
<%
	Employee sess_emp = (Employee) session.getAttribute("emp_detail");
	if (sess_emp == null) { //if user hasn't logged in then redirect him to login page
		if (request.getParameter("hr_sub") == null) {
%>
<body>

	<div id="wrapper">
		<div id="heading">
			<h1>THE BASILISKS</h1>
			<h3>HR Operations Manager</h3>
		</div><br>
		<div id="wrappertop"></div>

		<div id="wrappermiddle">

			<h2>Login</h2>
			
			<div id="error">
			<%
			if (session.getAttribute("errorlogin") == "0") //checking if the user entered wrong username or passwd in index page
			{
				out.println("Invalid Username or Password");//if yes, then notify him about this
				session.removeAttribute("errorlogin");
			}//else continue normally
			%>
			</div>

			<div id="username_input">

				<div id="username_inputleft"></div>

				<div id="username_inputmiddle">
				<form name="frm_login" method="POST" action="login.do">
					<input type="text" name="hr_user" id="eid" placeholder="Employee-Id" >
					<img id="url_user" src="img/mailicon.png" alt="">
				
				</div>

				<div id="username_inputright"></div>

			</div>

			<div id="password_input">

				<div id="password_inputleft"></div>

				<div id="password_inputmiddle">
				
					<input type="password" name="hr_pass" id="passwd" placeholder="Password">
					<img id="url_password" src="img/passicon.png" alt="">
				
				</div>

				<div id="password_inputright"></div>

			</div>

			<div id="submit">
				
				<input type="submit" id="sbmt" name="hr_sub" value="">
				
				</form>
			</div>


			<div id="links_left">
			</div>
		</div>

		

</body>
	<!-- Load Javascript -->
	<script type="text/javascript" src="js/jquery.js"></script>
	<script type="text/javascript" src="js/jquery.query-2.1.7.js"></script>
	<script type="text/javascript" src="js/rainbows.js"></script>
	<!-- // Load Javascipt -->
	<script>


	$(document).ready(function(){
 
	$("#submit1").hover(
	function() {
	$(this).animate({"opacity": "0"}, "fast");
	},
	function() {
	$(this).animate({"opacity": "1"}, "fast");
	});
 	});


</script>
</html>
<%
	}
	} else {
		response.sendRedirect("empHome.jsp");
	}
%>