package com.thebasilisks.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thebasilisks.DBConnection;

/**
 * Servlet implementation class Leave
 */
public class Leave extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Leave() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		int taskid = Integer.parseInt(request.getParameter("taskid"));
		Connection con = DBConnection.getConnection();

		if (taskid == 1) {
			try {
				int empid = Integer.parseInt(request.getParameter("empid"));
				int dd = Integer.parseInt(request.getParameter("dd"));
				int mm = Integer.parseInt(request.getParameter("mm"));
				int yyyy = Integer.parseInt(request.getParameter("yyyy"));
				String fromdate = yyyy + "-" + mm + "-" + dd;
				Date fromDate = Date.valueOf(fromdate);
				int dd1 = Integer.parseInt(request.getParameter("dd1"));
				int mm1 = Integer.parseInt(request.getParameter("mm1"));
				int yyyy1 = Integer.parseInt(request.getParameter("yyyy1"));
				String todate = yyyy1 + "-" + mm1 + "-" + dd1;
				Date toDate = Date.valueOf(todate);
				String reason = request.getParameter("reason");
				String status = request.getParameter("status");
				String sql = "INSERT INTO HROPS_SCHEMA.\"LEAVE\"(EMPLOYEE_ID,FROM_DATE,TO_DATE,REASON,STATUS) VALUES(?,?,?,?,?)";
				try {
					PreparedStatement statement = con.prepareStatement(sql);
					statement.setInt(1, empid);
					statement.setDate(2, fromDate);
					statement.setDate(3, toDate);
					statement.setString(4, reason);
					statement.setString(5, status);
					statement.executeUpdate();
					out.print("SUCCESS");
					System.out.print("Applied for leave");
					return;
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			out.print("An error occured.");
			return;
		}

		else if (taskid == 2) {
			try {
				String status = request.getParameter("approve");
				int leaveId = Integer.parseInt(request.getParameter("leaveId"));
				String sql = "UPDATE  HROPS_SCHEMA.\"LEAVE\" SET STATUS=? WHERE LEAVE_ID=?";
				try {
					PreparedStatement statement = con.prepareStatement(sql);
					statement.setString(1, status);
					statement.setInt(2, leaveId);
					statement.executeUpdate();
					out.print("SUCCESS");
					System.out.print("Request Evaluated");
					return;
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			out.print("An error occured.");
			return;
		}

	}

}
