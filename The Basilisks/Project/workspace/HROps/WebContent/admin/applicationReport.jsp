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
	<jsp:param name="title" value="Admin - View Application Report" />
	<jsp:param name="prefix" value="../" />
</jsp:include>
<%@page import="com.thebasilisks.Employee"%>
<%@page import="net.sf.jasperreports.engine.JasperPrint"%>
<%@page import="net.sf.jasperreports.j2ee.servlets.BaseHttpServlet"%>
<%@page import="net.sf.jasperreports.j2ee.servlets.ImageServlet"%>
<%@page import="net.sf.jasperreports.engine.export.JRHtmlExporter"%>
<%@page import="net.sf.jasperreports.engine.JRExporterParameter"%>
<%@page import="net.sf.jasperreports.engine.export.JRHtmlExporterParameter"%>
<%@page import="net.sf.jasperreports.engine.JRException"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.thebasilisks.DBConnection"%>
<%@page import="java.io.File"%>
<%@page import="net.sf.jasperreports.engine.JRRuntimeException"%>
<%@page import="net.sf.jasperreports.engine.JasperFillManager"%>
<%@page import="java.util.HashMap"%><html>

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
  			<input id="pdf" type="Button" value="Download as PDF">
			<input id="xls" type="Button" value="Download as XLS">
		</div>
		<div id="report">
		<%
			try {
				Connection connection=DBConnection.getConnection();
				String reportFileName = getServletContext().getRealPath("/WEB-INF/reports/ApplicationReport.jasper");
				File reportFile = new File(reportFileName);
				if (!reportFile.exists())
					throw new JRRuntimeException("File ApplicationReport.jasper not found. The report design must be compiled first.");
						
				JasperPrint jasperPrint = JasperFillManager.fillReport(reportFileName,new HashMap(),connection);
				
				session.setAttribute(BaseHttpServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE, jasperPrint);
				session.setAttribute(ImageServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE, jasperPrint);
				
				JRHtmlExporter exporter = new JRHtmlExporter();
				
				exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
				exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, out);
				exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI, "image?image=");
				
				response.setContentType("text/html");
				exporter.exportReport();
			} catch (JRException e) {
				// TODO Auto-generated catch block
				out.println("An error occured while printing the report:<br>"+e);
				e.printStackTrace();
			}
			%>
	</div>
  </div>   
</div>
    <!-- end .content --></div>
  <!-- end .container --></div>
</body>

<script type="text/javascript" src="../js/jquery.js"></script>
<script type="text/javascript" src="../js/admin/applicationReport.js"></script>

</html>