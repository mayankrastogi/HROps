package com.thebasilisks;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;


public class Department {
	Connection con;
	
	private int departmentId;
	private String departmentName;
	private int departmentHead;
	
	public Department()
	{
		
	}
	
	public Department(int departmentHead, int departmentId,
			String departmentName) {
		this.departmentHead = departmentHead;
		this.departmentId = departmentId;
		this.departmentName = departmentName;
	}
	
	

	/**
	 * @return the departmentId
	 */
	public int getDepartmentId() {
		return departmentId;
	}

	/**
	 * @param departmentId the departmentId to set
	 */
	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}

	/**
	 * @return the departmentName
	 */
	public String getDepartmentName() {
		return departmentName;
	}

	/**
	 * @param departmentName the departmentName to set
	 */
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	/**
	 * @return the departmentHead
	 */
	public int getDepartmentHead() {
		return departmentHead;
	}

	/**
	 * @param departmentHead the departmentHead to set
	 */
	public void setDepartmentHead(int departmentHead) {
		this.departmentHead = departmentHead;
	}
	
	public static Department getDepartment(int departmentId) {
		Department department = null;
		Connection connection = DBConnection.getConnection();

		String sql = "SELECT * FROM HROPS_SCHEMA.DEPARTMENT WHERE DEPT_ID=?";
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, departmentId);
			ResultSet result = statement.executeQuery();

			if (result.next()) {
				department = new Department();
				department.departmentId = result.getInt("DEPT_ID");
				department.departmentName = result.getString("DEPT_NAME");
				department.departmentHead = result.getInt("DEPT_HEAD");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			department = null;
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return department;
	}

	public HashMap getDept(){
	con=DBConnection.getConnection();
	HashMap<Integer,String> map=new HashMap<Integer, String>();
	try{
		
		Statement st=con.createStatement();
		ResultSet resultset = st.executeQuery("select * from HROPS_SCHEMA.DEPARTMENT");
		while(resultset.next()){
			map.put(resultset.getInt(1), resultset.getString(2));
		}
			
						
	}
	catch(Exception e){
		e.printStackTrace();
	}
	finally {
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
return map;
}

}
