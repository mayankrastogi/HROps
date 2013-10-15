package com.thebasilisks;

import java.sql.*;

public class Attendance {
	String date;
	String time;
	int empID;
	Connection con;

	public Attendance(int empID) {
		this.empID = empID;
		con = DBConnection.getConnection();
	}

	public int lastStateCheck() // checks the state column of the Attendance
								// table and tells whether employee should punch
								// in or punch out
	{
		int stateval = 0;
		try {
			Statement st = con.createStatement();
			ResultSet resultset = st
					.executeQuery("select STATE from HROPS_SCHEMA.ATTENDANCE where ID=(select max(id) from HROPS_SCHEMA.ATTENDANCE where EMPLOYEE_ID='"
							+ empID + "')");
			while (resultset.next()) {
				stateval = resultset.getInt(1);
			}

		} catch (Exception e) {

		}

		return stateval;
	}

	public void punchInOut(String ioTime, int type) throws Exception // Punches the Attendance
	{
		Statement st;
		try {
			if (type == 1) {
				st = con.createStatement();
				st.executeUpdate("update HROPS_SCHEMA.ATTENDANCE set out_time=CURRENT_TIMESTAMP,state=0 where ID=(select max(id) from HROPS_SCHEMA.ATTENDANCE where EMPLOYEE_ID="+empID+")");
			} else {
				st = con.createStatement();
				st.execute("insert into HROPS_SCHEMA.ATTENDANCE(EMPLOYEE_ID,in_time,state) values("+empID+",CURRENT_TIMESTAMP,1)");

			}
		} catch (Exception e) {
			System.out.println(ioTime);
		}
	}

}
