
<%@page import="com.thebasilisks.Attendance"%>
<%@page import="com.thebasilisks.Employee"%>
<%
	Employee employee = (Employee) session.getAttribute("emp_detail");
	if (employee != null) //if employee hasn't logged in then redirect him to index page
	{
		response.setContentType("text/plain");
		try {
			Employee emp = (Employee) session.getAttribute("emp_detail");
			Attendance empAttendance = new Attendance(emp.getEmployeeID());
			if (request.getParameter("punch").equals("1")) {
				String timeStamp = request.getParameter("timestamp");
				if (empAttendance.lastStateCheck() == 1) {
					empAttendance.punchInOut(timeStamp, 1);
					out.println("100"); //show punchin option on webpage by returning 100
				} else {
					empAttendance.punchInOut(timeStamp, 0);
					out.println("200"); //show punchout option on webpage by returning 200
				}

			} else {
				out.println("1"); // if somehow punch value is not set to 1  an error is displayed
			}

		} catch (Exception e) {
			e.printStackTrace(); //if any error occurs in system
		}
	}
%>