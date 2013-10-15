package com.thebasilisks;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class Employee {

	private int employeeID;
	private String password;
	private String name;
	private int positionId;
	private int supervisor;
	private Date hiredate;
	private int salary;
	private int deptId;
	private int qualId;
	private String gender;
	private String role;

	public Employee() {

	}

	public Employee(int employeeID, String name, String gender, int positionId,
			int deptId, int supervisor, int qualId, Date hiredate, int salary,
			String role) {
		this.employeeID = employeeID;
		this.name = name;
		this.gender = gender;
		this.positionId = positionId;
		this.deptId = deptId;
		this.supervisor = supervisor;
		this.qualId = qualId;
		this.hiredate = hiredate;
		this.salary = salary;
		this.role = role;
	}

	public int getEmployeeID() {
		return employeeID;
	}

	public void setEmployeeID(int employeeID) {
		this.employeeID = employeeID;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPositionId() {
		return positionId;
	}

	public void setPositionId(int positionId) {
		this.positionId = positionId;
	}

	public int getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(int supervisor) {
		this.supervisor = supervisor;
	}

	public Date getHiredate() {
		return hiredate;
	}

	public void setHiredate(Date hiredate) {
		this.hiredate = hiredate;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	public int getDeptId() {
		return deptId;
	}

	public void setDeptId(int deptId) {
		this.deptId = deptId;
	}

	public int getQualId() {
		return qualId;
	}

	public void setQualId(int qualId) {
		this.qualId = qualId;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}

	/**
	 * @param role
	 *            the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}

	public void createEmployee() {

	}

	public static Employee getEmployee(int employeeId) {
		Employee employee = null;

		Connection connection = DBConnection.getConnection();
		String sql = "SELECT * FROM HROPS_SCHEMA.EMPLOYEE WHERE EMPLOYEE_ID=?";
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, employeeId);
			ResultSet result = statement.executeQuery();

			if (result.next()) {
				employee = new Employee();
				employee.employeeID = result.getInt(1);
				employee.password = result.getString(2);
				employee.positionId = result.getInt(3);
				employee.supervisor = result.getInt(4);
				employee.hiredate = result.getDate(5);
				employee.salary = result.getInt(6);
				employee.deptId = result.getInt(7);
				employee.qualId = result.getInt(8);
				employee.gender = result.getString(9);
				employee.name = result.getString(10);
				employee.role = result.getString(11);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			employee = null;
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return employee;
	}

	public static boolean isSupervisor(int employeeId) {

		Connection connection = DBConnection.getConnection();
		String sql = "SELECT COUNT(*) FROM HROPS_SCHEMA.EMPLOYEE WHERE SUPERVISOR=?";
		int count = 0;
		
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, employeeId);
			ResultSet result = statement.executeQuery();

			if (result.next()) {
				count = result.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			count = 0;
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(count > 0)
			return true;
		else
			return false;
	}
	
	public static ArrayList<Employee> getInterviewers(int employeeId) {
		ArrayList<Employee> list = new ArrayList<Employee>();
		Employee employee = null;

		Connection connection = DBConnection.getConnection();
		String sql = "SELECT * FROM HROPS_SCHEMA.EMPLOYEE WHERE SUPERVISOR=? AND ROLE='INTERVIEWER'";
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, employeeId);
			ResultSet result = statement.executeQuery();

			while (result.next()) {
				employee = new Employee();
				employee.employeeID = result.getInt(1);
				employee.password = result.getString(2);
				employee.positionId = result.getInt(3);
				employee.supervisor = result.getInt(4);
				employee.hiredate = result.getDate(5);
				employee.salary = result.getInt(6);
				employee.deptId = result.getInt(7);
				employee.qualId = result.getInt(8);
				employee.gender = result.getString(9);
				employee.name = result.getString(10);
				employee.role = result.getString(11);

				list.add(employee);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return list;
	}
	
	@SuppressWarnings("unchecked")
	public HashMap getAllEmp(){
		HashMap<Integer,String> map=new HashMap<Integer, String>();
		Connection con = null;
		con=DBConnection.getConnection();
		try{
			Statement st=con.createStatement();
			ResultSet resultset = st.executeQuery("select EMPLOYEE_ID,NAME from HROPS_SCHEMA.EMPLOYEE");
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
