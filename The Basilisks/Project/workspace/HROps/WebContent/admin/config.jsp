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
	<jsp:param name="title" value="Admin - Configure Server" />
	<jsp:param name="prefix" value="../" />
</jsp:include>
<%@page import="com.thebasilisks.Employee"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.thebasilisks.DBConnection"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="com.thebasilisks.Department"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Properties"%><html>
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
  	<div class="bold-label" style="padding: 20px;">
  	<form name="db_config" id="db_config">

<%
Properties props = new Properties();
props.load(getServletContext().getResourceAsStream("/WEB-INF/config/DBConnection.properties"));
System.out.print(props.getProperty("username"));
%>
<h3>Database Configuration</h3>
<label for="db_username">Username</label><br><input type="text" name="db_username" id="db_username" value="<%out.print(props.getProperty("username")); %>"><br>
<label for="db_password">Password</label><br><input type="text" name="db_password" id="db_password" value="<% out.print(props.getProperty("password")); %>"><br>
<label for="db_host">Host</label><br><input type="text" name="db_host" id="db_host" value="<% out.print(props.getProperty("host")); %>"><br>
<label for="db_port">Port Address</label><br><input type="text" name="db_port" id="db_port" value="<% out.print(props.getProperty("port")); %>"><br>
<label for="db_dbname">Database Name</label><br><input type="text" name="db_dbname" id="db_dbname" value="<% out.print(props.getProperty("dbname")); %>"><br>
<input type="button" value="UPDATE" onclick="updateDB()">
</form>
<br><br>
<form name="imap_config" id="imap_config">
<%
props.load(getServletContext().getResourceAsStream("/WEB-INF/config/MailProcessor.properties"));
%>
<h3>IMAP Configuration</h3>
<label for="imap_host">Host</label><br><input type="text" name="imap_host" id="imap_host" value="<% out.print(props.getProperty("MailProcessor.host")); %>"><br>
<label for="imap_port">Port</label><br><input type="text" name="imap_port" id="imap_port" value= "<% out.print(props.getProperty("MailProcessor.port")); %>"><br>
<label for="imap_username">Username</label><br><input type="text" name="imap_username" id="imap_username" value= "<% out.print(props.getProperty("MailProcessor.username")); %>"><br>
<label for="imap_password">Password</label><br><input type="text" name="imap_password" id="imap_password" value= "<% out.print(props.getProperty("MailProcessor.password")); %>"><br>
<label for="imap_freq">Polling Frequency</label><br><input type="text" name="imap_freq" id="imap_freq" value= "<% out.print(props.getProperty("MailProcessor.pollingFrequency")); %>"><br>
<input type="button" value="UPDATE" onclick="updateimap()">
</form>
<br><br>
<form name="smtp_config" id="smtp_config">
<h3>SMTP Configuration</h3>
<%props.load(getServletContext().getResourceAsStream("/WEB-INF/config/SendMail.properties")); %><br>
<label for="smtp_host">SMTP Host</label><br><input type="text" name="smtp_host" id="smtp_host" value= "<% out.print(props.getProperty("mail.smtp.host")); %>"><br>
<label for="smtp_port">SMTP Port</label><br><input type="text" name="smtp_port" id="smtp_port" value= "<% out.print(props.getProperty("mail.smtp.port")); %>"><br>
<label for="smtp_username">Username</label><br><input type="text" name="smtp_username" id="smtp_username" value= "<% out.print(props.getProperty("SendMail.username")); %>"><br>
<label for="smtp_password">Password</label><br><input type="text" name="smtp_password" id="smtp_password" value= "<% out.print(props.getProperty("SendMail.password")); %>"><br>
<label for="smtp_from">From E-mail</label><br><input type="text" name="smtp_from" id="smtp_from" value= "<% out.print(props.getProperty("SendMail.from")); %>"><br>
<label for="smtp_hostname">Hostname</label><br><input type="text" name="smtp_hostname" id="smtp_hostname" value= "<% out.print(props.getProperty("SendMail.server.hostname")); %>"><br>
<label for="smtp_server_port">Server Port</label><br><input type="text" name="smtp_server_port" id="smtp_server_port" value= "<% out.print(props.getProperty("SendMail.server.port")); %>"><br>
<input type="button" value="UPDATE" onclick="updatesmtp()"><br>
</form>
<br><br>
<form name="message_config" id="message_config">
<h3>Automated E-mail  Configuration</h3><br>
<% props.load(getServletContext().getResourceAsStream("/WEB-INF/config/SendMail.properties")); %>
<label for="offer_subject">Subject For Offer Letter</label><br><input type="text" name="offer_subject" id="offer_subject"  value="<%out.print(props.getProperty("letter.offer.subject")); %>"><br><br>
<div id="body_offerletter"><b>{position}</b> : Insert the position for which the applicant is selected<br>
<b>{offer_accept_link}</b> : Inserts auto-generated link to accept the offer.<br>
<b>{offer_reject_link} </b>: Inserts auto-generated link to reject the offer.<br>
</div>
<label for="offer_body">Body For Offer Letter</label><br><textarea name="offer_body" id="offer_body" rows="5" cols="40"><%out.print(props.getProperty("letter.offer.body")); %></textarea><br><br>
<div id="body_interviewletter">
<b>{opportunity_id}</b> : Inserts opportunity id for which the applicant has to attend the interview<br>
<b>{interview_schedule}</b> : Inserts the Interview Schedule for the applicant.<br>
</div>
<label for="interview_subject">Subject For Interview</label><br><input type="text" name="interview_subject" id="interview_subject" value="<%out.print(props.getProperty("letter.interview.subject")); %>"><br><br>
<label for="interview_body">Body For Interview</label><br><textarea name="interview_body" id="interview_body" rows="5" cols="40" ><%out.print(props.getProperty("letter.interview.body")); %></textarea><br><br>
<label for="rejection_subject">Subject For Rejection</label><br><input type="text" name="rejection_subject" id="rejection_subject" value="<%out.print(props.getProperty("letter.rejection.subject")); %>"><br><br>
<div id="body_rejectionletter">
<b>{reject_reason}</b> : Inserts the reason for rejection.<br> 
</div>
<label for="rejection_body">Body For Rejection letter</label><br><textarea  name="rejection_body" id="rejection_body" rows="5" cols="40" ><%out.print(props.getProperty("letter.rejection.body")); %></textarea><br><br>
<label for="password_subject">Subject While Sending Password</label><br><input type="text" name="password_subject" id="password_subject"  value="<%out.print(props.getProperty("letter.password.subject")); %>"><br><br>
<div id="body_passwordletter">
<b>{employee_id} </b>: Inserts newly generated Employee Id of the Applicant.<br>
<b>{password}</b> :  Inserts new auto generated password of the Applicant.<br>
</div>
<label for="password_body" >Body while Sending Password</label><br><textarea  name="password_body" id="password_body" rows="5"cols="40" ><%out.print(props.getProperty("letter.password.body")); %></textarea><br><br>
<label for="mail_rejection_subject">Subject for automated Rejection Letter</label><br><input type="text" name="mail_rejection_subject" id="mail_rejection_subject" value="<%out.print(props.getProperty("MailProcessor.rejection.subject")); %>"><br><br>
<div id="body_mailrejectletter">
<b>{mailProcessor_reject_reason}</b> : Used in Rejection mail or feedback mail that the mail Processor sent if there is some problem with Job Application<br><br>
</div>
<label for="mail_rejection_body" >Body of automated Rejection Letter</label><br><textarea  name="mail_rejection_body" id="mail_rejection_body" rows="5" cols="40" ><%out.print(props.getProperty("MailProcessor.rejection.body")); %></textarea><br>
<input type="button" value="UPDATE" onclick="updatemsg()"><br>
</form>
  </div>   
</div>
    <!-- end .content --></div>
  <!-- end .container --></div>
</body>

<script type="text/javascript" src="../js/jquery.js"></script>
<script type="text/javascript" src="../js/admin/config.js"></script>

</html>