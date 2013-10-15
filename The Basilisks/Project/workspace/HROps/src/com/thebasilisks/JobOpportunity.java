package com.thebasilisks;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class JobOpportunity {
	private String opportunityId;
	private int position;
	private int department;
	private int numOfVacancies;
	private Date lastDate;

	public JobOpportunity() {
		department = -1;
		lastDate = null;
		numOfVacancies = -1;
		opportunityId = null;
		position = -1;
	}

	public JobOpportunity(String opportunityId, int position, int department,
			int numOfVacancies, Date lastDate) {
		this.department = department;
		this.lastDate = lastDate;
		this.numOfVacancies = numOfVacancies;
		this.opportunityId = opportunityId;
		this.position = position;
	}

	/**
	 * @return the opportunityId
	 */
	public String getOpportunityId() {
		return opportunityId;
	}

	/**
	 * @param opportunityId
	 *            the opportunityId to set
	 */
	public void setOpportunityId(String opportunityId) {
		this.opportunityId = opportunityId;
	}

	/**
	 * @return the position
	 */
	public int getPosition() {
		return position;
	}

	/**
	 * @param position
	 *            the position to set
	 */
	public void setPosition(int position) {
		this.position = position;
	}

	/**
	 * @return the department
	 */
	public int getDepartment() {
		return department;
	}

	/**
	 * @param department
	 *            the department to set
	 */
	public void setDepartment(int department) {
		this.department = department;
	}

	/**
	 * @return the numOfVacancies
	 */
	public int getNumOfVacancies() {
		return numOfVacancies;
	}

	/**
	 * @param numOfVacancies
	 *            the numOfVacancies to set
	 */
	public void setNumOfVacancies(int numOfVacancies) {
		this.numOfVacancies = numOfVacancies;
	}

	/**
	 * @return the lastDate
	 */
	public Date getLastDate() {
		return lastDate;
	}

	/**
	 * @param lastDate
	 *            the lastDate to set
	 */
	public void setLastDate(Date lastDate) {
		this.lastDate = lastDate;
	}

	public static JobOpportunity getJobOpportunity(String opportunityId) {
		JobOpportunity jobOpportunity = null;
		Connection connection = DBConnection.getConnection();

		String sql = "SELECT * FROM HROPS_SCHEMA.JOB_OPPORTUNITY WHERE OPPORTUNITY_ID=?";
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, opportunityId);
			ResultSet result = statement.executeQuery();

			if (result.next()) {
				jobOpportunity = new JobOpportunity();
				jobOpportunity.opportunityId = result
						.getString("OPPORTUNITY_ID");
				jobOpportunity.position = result.getInt("POSITION_ID");
				jobOpportunity.department = result.getInt("DEPARTMENT_ID");
				jobOpportunity.numOfVacancies = result
						.getInt("No_OF_VACANCIES");
				jobOpportunity.lastDate = result.getDate("LAST_DATE");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			jobOpportunity = null;
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return jobOpportunity;
	}
	
	public static ArrayList<JobOpportunity> getJobOpportunities(int departmentId) {
		ArrayList<JobOpportunity> list = new ArrayList<JobOpportunity>();
		JobOpportunity jobOpportunity = null;
		
		Connection connection = DBConnection.getConnection();
		String sql = "SELECT * FROM HROPS_SCHEMA.JOB_OPPORTUNITY WHERE DEPARTMENT_ID=?";
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, departmentId);
			ResultSet result = statement.executeQuery();

			while (result.next()) {
				jobOpportunity = new JobOpportunity();
				jobOpportunity.opportunityId = result
						.getString("OPPORTUNITY_ID");
				jobOpportunity.position = result.getInt("POSITION_ID");
				jobOpportunity.department = result.getInt("DEPARTMENT_ID");
				jobOpportunity.numOfVacancies = result
						.getInt("No_OF_VACANCIES");
				jobOpportunity.lastDate = result.getDate("LAST_DATE");
				
				list.add(jobOpportunity);
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

}
