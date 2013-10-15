package com.thebasilisks.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.thebasilisks.DBConnection;
import com.thebasilisks.Employee;

/**
 * Servlet implementation class Authenticator
 */
public class Authenticator extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Authenticator() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	public static String convtoMD5(String userpass){
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(userpass.getBytes());

			byte byteData[] = md.digest();

			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < byteData.length; i++)
				sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16)
						.substring(1));

			String md5pass = sb.toString(); // convert password to md5 form so
			// it can be checked
			return md5pass;
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return null;
		}
	}
	protected void doProcess(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String username = request.getParameter("hr_user");
		String userpass = request.getParameter("hr_pass");
		HttpSession session;
		Employee emp = new Employee();
	
		
		
		
		userpass=convtoMD5(userpass);
		
		
		Connection con = DBConnection.getConnection(); // getting connected
		// to database
		int matches = 0;

		try {
			Statement st = con.createStatement();
			ResultSet resultset = st
					.executeQuery("select * from HROPS_SCHEMA.EMPLOYEE where EMPLOYEE_ID="
							+ username + " and PASSWORD='" + userpass + "'");

			while (resultset.next()) // connect to a database and check username
			// and password
			{
				matches++;
				emp.setEmployeeID(resultset.getInt(1)); // Authentication
				// Successful
				emp.setPassword(resultset.getString(2));
				emp.setPositionId(resultset.getInt(3)); // write all the fetched
				// values
				emp.setSupervisor(resultset.getInt(4)); // in an Object and
				// then set it as
				emp.setHiredate(resultset.getDate(5)); // session variable
				emp.setSalary(resultset.getInt(6));
				emp.setDeptId(resultset.getInt(7));
				emp.setQualId(resultset.getInt(8));
				emp.setGender(resultset.getString(9));
				emp.setName(resultset.getString(10));
				emp.setRole(resultset.getString(11));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		session = request.getSession();
		if (matches == 1) {
			// public HttpSession session;

			session.setAttribute("emp_detail", emp);
			response.sendRedirect("empHome.jsp");
		} else {
			session.setAttribute("errorlogin", "0");
			response.sendRedirect("login.jsp");
		}
		// if login succedds create a session and set employee object in session
		// variable and redirect to empHome.jsp
		// else redirect the page to login.jsp

	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doProcess(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		if(session.getAttribute("emp_detail") != null && request.getParameter("action").equals("changePass"))
		{
			String oldPass = request.getParameter("oldPass");
			String newPass = request.getParameter("newPass");
			String confirmPass = request.getParameter("confirmPass");
			
			Employee employee = (Employee)session.getAttribute("emp_detail");
			PrintWriter out = response.getWriter();
			oldPass=convtoMD5(oldPass);
			
			if(oldPass.equals(employee.getPassword()) && newPass.equals(confirmPass))
			{
				Connection connection = DBConnection.getConnection();
				newPass=convtoMD5(newPass);
				try {
					PreparedStatement statement = connection.prepareStatement("UPDATE HROPS_SCHEMA.EMPLOYEE SET PASSWORD=? WHERE EMPLOYEE_ID=?");
					statement.setString(1, newPass);
					statement.setInt(2, employee.getEmployeeID());
					statement.executeUpdate();
					employee.setPassword(newPass);
					session.setAttribute("emp_detail", employee);
					out.println("Password changed successfully.");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					out.println("An Error occured while changing the password.");
					e.printStackTrace();
				}
			}
			else
				out.println("Passwords do not match.");
		}
		else
			doProcess(request, response);
	}

}
