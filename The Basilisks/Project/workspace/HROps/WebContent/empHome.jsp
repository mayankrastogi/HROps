<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	Employee employee = (Employee) session.getAttribute("emp_detail");
	if (employee == null) //if employee hasn't logged in then redirect him to index page
		response.sendRedirect("login.jsp");
%>

<%@page import="com.thebasilisks.Employee"%>
<%@page import="com.thebasilisks.JobOpportunity"%>
<%@page import="com.thebasilisks.Application"%>
<%@page import="com.thebasilisks.Position"%>
<%@page import="com.thebasilisks.Qualification"%>
<%@page import="com.thebasilisks.Department"%><html>

<jsp:include page="WEB-INF/header.jsp" flush="true">
	<jsp:param name="title" value="Employee Home" />
	<jsp:param name="prefix" value="" />
</jsp:include>
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
  	<br>
  	<h2>Employee Details</h2>
  	<p class="personal-details">
  	Employee ID : <%=employee.getEmployeeID() %><br>
  	<%
  	if(employee.getName() != null)
  	{
  	%>
  	Name : <%=employee.getName() %><br>
  	<%
  	}
  	if(employee.getGender() != null)
  	{
  	%>
  	Gender : <%=employee.getGender().equals("M") ? "Male" : "Female" %><br>
  	<%
  	}
  	if(employee.getQualId() != 0)
  	{
  	%>
  	Qualification : <%=Qualification.getQualification(employee.getQualId()).getQualificationDescription() %><br>
  	</p>
  	<%
  	}
  	%>
  	<p class="job-details">
  	Department : <%=Department.getDepartment(employee.getDeptId()).getDepartmentName() %><br>
  	Position : <%=Position.getPosition(employee.getPositionId()).getPositionDescription() %><br>
  	<%
  	if(employee.getSupervisor() != 0)
  	{
  	%>
  	Supervisor : <%=Employee.getEmployee(employee.getSupervisor()).getName() %><br>
  	<%
  	}
  	%>
  	Date of Hiring : <%=employee.getHiredate().toString() %><br>
  	<%
  	if(employee.getSalary() != 0)
  	{
  	%>
  	Salary : <%=employee.getSalary() %><br>
  	<%
  	}
  	if(employee.getRole() != null)
  	{
  	%>
  	Special Role : <%=employee.getRole() %><br>
  	<%
  	}
  	%>
  	</p>
  	</div>
    
    <!-- end .content --></div>
  <!-- end .container --></div>
</body>

<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/empHome.js"></script>

</html>