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
	<jsp:param name="title" value="Admin - Create Department" />
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
<%@page import="java.util.Map"%><html>
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
  		<h2>Create Department</h2><br>
  		<div id="Dept_frm_div"><center>
<form method="post" name="Createdept" id="Createdept" onsubmit="return false">

Department Name : <input type="text" id="deptname" name="deptname"><br>

<br>
<input type="submit" value="Submit" onclick="submitForm1()">
</form>
</center>
</div>
  	
<div id="update_dept">
<h1>Update Department</h1>
<form name="frm_update_dept" id="frm_update_dept">
Department :
<select name="select_dept_id" id="select_dept_id" onchange="getdept()">
<%
Department depart = new Department();
HashMap map=depart.getDept();
map=depart.getDept();
Iterator iter=map.entrySet().iterator();
while (iter.hasNext()) {
Map.Entry pairs = (Map.Entry)iter.next();
   	   out.println("<option value="+pairs.getKey()+">"+pairs.getValue()+"</option>");
}			
 %>
</select><br>
<label for="depthead" id="label-depthead">Department Head : </label>
<select name="depthead" id="depthead">

</select>
<input type="button" value="Update" onclick="updatedept()">
</form>
</div>
</div>   
</div>
    <!-- end .content --></div>
  <!-- end .container --></div>
</body>

<script type="text/javascript" src="../js/jquery.js"></script>
<script type="text/javascript" src="../js/admin/admin.js"></script>

</html>