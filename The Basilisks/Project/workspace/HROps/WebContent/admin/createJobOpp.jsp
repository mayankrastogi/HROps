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
	<jsp:param name="title" value="Admin - Create Job Opportunity" />
	<jsp:param name="prefix" value="../" />
</jsp:include>
<%@page import="com.thebasilisks.Employee"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.thebasilisks.DBConnection"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.util.Calendar"%><html>
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
  		<h2>Create Job Opportunity</h2><br>
  		<form method="post" name="Createjob" id="Createjob" onsubmit="return false">
Opportunity Id : <input type="text" id="oppid" name="oppid"><br>
Department Id :
<select name="dept" id="dept">
<% 
			String deptdesc;
			int deptid;
			PreparedStatement st;
			Connection con = DBConnection.getConnection();
			st = con.prepareStatement("SELECT DEPT_ID,DEPT_NAME from HROPS_SCHEMA.DEPARTMENT");
			ResultSet resultset=st.executeQuery();
			while(resultset.next())
			{
				deptid=resultset.getInt(1);
				deptdesc=resultset.getString(2);
				out.println("<option value="+deptid+">"+deptid+"  "+deptdesc+"</option>");
		    }
%>
</select>
<br>
Position Id :
<select name="pos" id="pos">
<%
			String posdesc;
			int posid;
			PreparedStatement st1;
			Connection con1=DBConnection.getConnection();
			st1 = con1.prepareStatement("SELECT POSITION_ID,POS_DESC from HROPS_SCHEMA.POSITION");
			ResultSet resultset1=st1.executeQuery();
			while(resultset1.next())
			{
				posid=resultset1.getInt(1);
				posdesc=resultset1.getString(2);
				out.println("<option value="+posid+">"+posid+"  "+posdesc+"</option>");
		    }
%>

</select>

<br> 
No of Vacancies : <input type="text" id="vacancy" name="vacancy"><br>
<label for="dd">Last Date Of Application(dd/mm/yyyy) : </label>

<% Calendar calendar = Calendar.getInstance();%>
  					<input type="text" name="dd" value="<%=calendar.get(Calendar.DAY_OF_MONTH) %>">/
  					<input type="text" name="mm" value="<%=calendar.get(Calendar.MONTH)+1 %>">/
  					<input type="text" name="yyyy" value="<%=calendar.get(Calendar.YEAR) %>"><br>
<input type="submit" value="submit" onclick="submitForm4()">
</form>
  	</div>
    </div>
    <!-- end .content --></div>
  <!-- end .container --></div>
</body>

<script type="text/javascript" src="../js/jquery.js"></script>
<script type="text/javascript" src="../js/admin/admin.js"></script>

</html>