<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	Employee employee = (Employee) session.getAttribute("emp_detail");
	String prefix = request.getParameter("prefix");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="com.thebasilisks.Employee"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title><%=request.getParameter("title") %></title>
<link type="image/x-icon" href="<%=prefix %>favicon.ico" rel="shortcut icon">
<link type="text/css" href="<%=prefix %>css/normalize.css" rel="stylesheet">
<link type="text/css" href="<%=prefix %>css/main.css" rel="stylesheet">
<!--[if lte IE 7]>

<style>
.content { margin-right: -1px; } /* this 1px negative margin can be placed on any of the columns in this layout with the same corrective effect. */
ul.nav a { zoom: 1; }  /* the zoom property gives IE the hasLayout trigger it needs to correct extra whiltespace between the links */
</style>
<![endif]--></head>

<body>

<div id="wrapper">
  <div class="header">
  <img id="logo" src="<%=prefix %>img/vipers_logo.JPG" alt="The Basilisks" width="175" height="87">
  <div id="title">The Basilisks<br><span class="HRM">HR Operations Manager</span></div>
    <div id="header_right">
    	Welcome <%=(employee.getName() != null)?employee.getName():employee.getEmployeeID() %><br>
    	<a href="<%=prefix %>changePass.jsp">Change Password</a> |
    	<a href="<%=prefix %>logout.jsp">Logout</a>
    </div>
    <div id="header_topbar" >
      <ul class="nav1">
        <li><a href="<%=prefix %>empHome.jsp">Home</a></li>
        <%
        String role = employee.getRole();
        if(role != null && (role.equals("HR") || role.equals("MANAGER") || role.equals("INTERVIEWER") || role.equals("ADMIN")))
        {
        %>
        <li id=task>
        <a>
           Select Task</a>
           <ul class="dropdown">
           <%
           if(role.equals("HR"))
           {
           %>
           <li><a href="<%=prefix %>HR/shortlist.jsp">View New Applications</a></li>
           <li><a href="<%=prefix %>HR/shortlisted.jsp">View Shortlisted Applications</a></li>
           <li><a href="<%=prefix %>HR/rejected.jsp">View Rejected Applications</a></li>
           <li><a href="<%=prefix %>HR/hire.jsp">Hire Applicants</a></li>
           <%
           } else if(role.equals("MANAGER"))
           {
           %>
           <li><a href="<%=prefix %>Manager/schedule.jsp">Schedule Interviews</a></li>
           <li><a href="<%=prefix %>Manager/viewResults.jsp">View Interview Results</a></li>
           <li><a href="<%=prefix %>Manager/viewSelected.jsp">View Selected Applications</a></li>
           <li><a href="<%=prefix %>Manager/viewRejected.jsp">View Rejected Applications</a></li>
           <%
           }
           else if(role.equals("INTERVIEWER"))
           {
           %>
           <li><a href="<%=prefix %>Interviewer/viewSchedule.jsp">View Interview Schedule</a></li>
           <li><a href="<%=prefix %>Interviewer/updateResults.jsp">Update Interview Results</a></li>
           <%
           }
           else if(role.equals("ADMIN"))
           {
           %>
           <li><a href="<%=prefix %>admin/config.jsp">Configure Server</a></li>
           <li><a href="<%=prefix %>admin/updateEmployee.jsp">Update Employee</a></li>
           <li><a href="<%=prefix %>admin/createJobOpp.jsp">Create Job Opportunity</a></li>
           <li><a href="<%=prefix %>admin/createDepartment.jsp">Create Department</a></li>
           <li><a href="<%=prefix %>admin/createPosition.jsp">Create Position</a></li>
           <li><a href="<%=prefix %>admin/createQualification.jsp">Create Qualification</a></li>
           <li><a href="<%=prefix %>admin/applicationReport.jsp">View Application Report</a></li>
           <%
           }
           %>
           </ul>
       </li>
        <%
        }
        %>
        <li id="attendance"><a>Attendance</a>
        	<ul class="dropdown attendance">
        		<li><a href="<%=prefix %>attendance/punch.jsp">Give Attendance</a></li>
        		<li><a href="<%=prefix %>attendance/viewAttendance.jsp">View Attendance</a></li>
        	</ul>
        </li>
        <li id="leave"><a>Leave</a>
        	<ul class="dropdown leave">
        		<li><a href="<%=prefix %>leave/leaveApplication.jsp">Apply for Leave</a></li>
        		<%
        		if(Employee.isSupervisor(employee.getEmployeeID()))
        		{
        		%>
        		<li><a href="<%=prefix %>leave/grantLeave.jsp">Grant Leave</a></li>
        		<%
        		}
        		%>
        	</ul>
        </li>
        <li><a href="<%=prefix %>help.jsp">Help</a></li>
      </ul>
    </div>
  <!-- end .header --></div>
  