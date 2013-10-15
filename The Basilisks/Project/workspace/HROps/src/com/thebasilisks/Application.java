package com.thebasilisks;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

import com.thebasilisks.servlets.Authenticator;

public class Application {

	private int applicationID;
	private String email;
	private String opportunityID;
	private String resumeLocation;
	private Date applicationDate;
	private int status;
	private String offerID;
	private int interviewer;
	private Date interviewDate;
	private Time interviewTime;
	private int interviewResult;
	private String rejectionReason;

	public static final int UNREAD = 0;
	public static final int SHORTLISTED = 1;
	public static final int REJECTED_IN_SHORTLISTING = 2;
	public static final int SELECTED = 3;
	public static final int REJECTED_IN_SELECTION = 4;
	public static final int OFFER_SENT = 5;
	public static final int OFFER_ACCEPTED = 6;
	public static final int OFFER_REJECTED = 7;

	public Application() {
		email = null;
		offerID = null;
		opportunityID = null;
		resumeLocation = null;
		status = UNREAD;
	}

	public Application(String email, String opportunityID,
			java.util.Date applicationDate, String resumeLocation, int status) {
		this.email = email;
		this.opportunityID = opportunityID;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(applicationDate);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		this.applicationDate = new Date(calendar.getTimeInMillis());
		this.resumeLocation = resumeLocation;
		this.status = status;
	}

	/**
	 * @return the applicationID
	 */
	public int getApplicationID() {
		return applicationID;
	}

	/**
	 * @param applicationID
	 *            the applicationID to set
	 */
	public void setApplicationID(int applicationID) {
		this.applicationID = applicationID;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the opportunityID
	 */
	public String getOpportunityID() {
		return opportunityID;
	}

	/**
	 * @param opportunityID
	 *            the opportunityID to set
	 */
	public void setOpportunityID(String opportunityID) {
		this.opportunityID = opportunityID;
	}

	/**
	 * @return the resumeLocation
	 */
	public String getResumeLocation() {
		return resumeLocation;
	}

	/**
	 * @param resumeLocation
	 *            the resumeLocation to set
	 */
	public void setResumeLocation(String resumeLocation) {
		this.resumeLocation = resumeLocation;
	}

	/**
	 * @return the applicationDate
	 */
	public Date getApplicationDate() {
		return applicationDate;
	}

	/**
	 * @param applicationDate
	 *            the applicationDate to set
	 */
	public void setApplicationDate(java.util.Date applicationDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(applicationDate);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		this.applicationDate = new Date(calendar.getTimeInMillis());
	}

	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * @return the offerID
	 */
	public String getOfferID() {
		return offerID;
	}

	/**
	 * @param offerID
	 *            the offerID to set
	 */
	public void setOfferID(String offerID) {
		this.offerID = offerID;
	}

	/**
	 * @return the interviewer
	 */
	public int getInterviewer() {
		return interviewer;
	}

	/**
	 * @param interviewer
	 *            the interviewer to set
	 */
	public void setInterviewer(int interviewer) {
		this.interviewer = interviewer;
	}

	/**
	 * @return the interviewDate
	 */
	public Date getInterviewDate() {
		return interviewDate;
	}

	/**
	 * @param interviewDate
	 *            the interviewDate to set
	 */
	public void setInterviewDate(Date interviewDate) {
		this.interviewDate = interviewDate;
	}

	/**
	 * @return the interviewTime
	 */
	public Time getInterviewTime() {
		return interviewTime;
	}

	/**
	 * @param interviewTime
	 *            the interviewTime to set
	 */
	public void setInterviewTime(Time interviewTime) {
		this.interviewTime = interviewTime;
	}

	/**
	 * @return the interviewResult
	 */
	public int getInterviewResult() {
		return interviewResult;
	}

	/**
	 * @param interviewResult
	 *            the interviewResult to set
	 */
	public void setInterviewResult(int interviewResult) {
		this.interviewResult = interviewResult;
	}

	/**
	 * @return the rejectionReason
	 */
	public String getRejectionReason() {
		return rejectionReason;
	}

	/**
	 * @param rejectionReason
	 *            the rejectionReason to set
	 */
	public void setRejectionReason(String rejectionReason) {
		this.rejectionReason = rejectionReason;
	}

	public static Application getApplication(int applicationId) {
		Application application = null;

		Connection connection = DBConnection.getConnection();
		String sql = "SELECT * FROM HROPS_SCHEMA.\"APPLICATION\" WHERE APPLICATION_ID=?";

		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, applicationId);
			ResultSet result = statement.executeQuery();

			if (result.next()) {
				application = new Application();
				application.applicationID = result.getInt(1);
				application.email = result.getString(2);
				application.opportunityID = result.getString(3);
				application.applicationDate = result.getDate(4);
				application.resumeLocation = result.getString(5);
				application.status = result.getInt(6);
				application.offerID = result.getString(7);
				application.interviewer = result.getInt(8);
				application.interviewDate = result.getDate(9);
				application.interviewTime = result.getTime(10);
				application.interviewResult = result.getInt(11);
				application.rejectionReason = result.getString(12);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			application = null;
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return application;
	}

	public static ArrayList<Application> getApplications(int departmentId) {
		ArrayList<Application> list = new ArrayList<Application>();
		Application application = null;

		Connection connection = DBConnection.getConnection();
		String sql = "SELECT * FROM HROPS_SCHEMA.\"APPLICATION\" WHERE OPPORTUNITY_ID IN(SELECT OPPORTUNITY_ID FROM HROPS_SCHEMA.JOB_OPPORTUNITY WHERE DEPARTMENT_ID=?) ORDER BY APPLICATION_DATE DESC";

		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, departmentId);
			ResultSet result = statement.executeQuery();

			while (result.next()) {
				application = new Application();

				application.applicationID = result.getInt(1);
				application.email = result.getString(2);
				application.opportunityID = result.getString(3);
				application.applicationDate = result.getDate(4);
				application.resumeLocation = result.getString(5);
				application.status = result.getInt(6);
				application.offerID = result.getString(7);
				application.interviewer = result.getInt(8);
				application.interviewDate = result.getDate(9);
				application.interviewTime = result.getTime(10);
				application.interviewResult = result.getInt(11);
				application.rejectionReason = result.getString(12);

				list.add(application);
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

	public static ArrayList<Application> getApplications(int departmentId,
			int status) {
		ArrayList<Application> list = new ArrayList<Application>();
		Application application = null;

		Connection connection = DBConnection.getConnection();
		String sql = "SELECT * FROM HROPS_SCHEMA.\"APPLICATION\" WHERE OPPORTUNITY_ID IN(SELECT OPPORTUNITY_ID FROM HROPS_SCHEMA.JOB_OPPORTUNITY WHERE DEPARTMENT_ID=?) AND STATUS=? ORDER BY APPLICATION_DATE DESC";

		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, departmentId);
			statement.setInt(2, status);
			ResultSet result = statement.executeQuery();

			while (result.next()) {
				application = new Application();

				application.applicationID = result.getInt(1);
				application.email = result.getString(2);
				application.opportunityID = result.getString(3);
				application.applicationDate = result.getDate(4);
				application.resumeLocation = result.getString(5);
				application.status = result.getInt(6);
				application.offerID = result.getString(7);
				application.interviewer = result.getInt(8);
				application.interviewDate = result.getDate(9);
				application.interviewTime = result.getTime(10);
				application.interviewResult = result.getInt(11);
				application.rejectionReason = result.getString(12);

				list.add(application);
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

	public static ArrayList<Application> getApplications(int departmentId,
			String opportunityId) {
		ArrayList<Application> list = new ArrayList<Application>();
		Application application = null;

		Connection connection = DBConnection.getConnection();
		String sql = "SELECT * FROM HROPS_SCHEMA.\"APPLICATION\" WHERE OPPORTUNITY_ID IN(SELECT OPPORTUNITY_ID FROM HROPS_SCHEMA.JOB_OPPORTUNITY WHERE DEPARTMENT_ID=?) ORDER BY APPLICATION_DATE DESC";

		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, departmentId);
			ResultSet result = statement.executeQuery();

			while (result.next()) {
				if (!opportunityId.equals("")
						&& !result.getString(3).equals(opportunityId))
					continue;

				application = new Application();

				application.applicationID = result.getInt(1);
				application.email = result.getString(2);
				application.opportunityID = result.getString(3);
				application.applicationDate = result.getDate(4);
				application.resumeLocation = result.getString(5);
				application.status = result.getInt(6);
				application.offerID = result.getString(7);
				application.interviewer = result.getInt(8);
				application.interviewDate = result.getDate(9);
				application.interviewTime = result.getTime(10);
				application.interviewResult = result.getInt(11);
				application.rejectionReason = result.getString(12);

				list.add(application);
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

	public static ArrayList<Application> getApplications(int departmentId,
			String opportunityId, int status) {
		ArrayList<Application> list = new ArrayList<Application>();
		Application application = null;

		Connection connection = DBConnection.getConnection();
		String sql = "SELECT * FROM HROPS_SCHEMA.\"APPLICATION\" WHERE OPPORTUNITY_ID IN(SELECT OPPORTUNITY_ID FROM HROPS_SCHEMA.JOB_OPPORTUNITY WHERE DEPARTMENT_ID=?) AND STATUS=? ORDER BY APPLICATION_DATE DESC";

		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, departmentId);
			statement.setInt(2, status);
			ResultSet result = statement.executeQuery();

			while (result.next()) {
				if (!opportunityId.equals("")
						&& !result.getString(3).equals(opportunityId))
					continue;

				application = new Application();

				application.applicationID = result.getInt(1);
				application.email = result.getString(2);
				application.opportunityID = result.getString(3);
				application.applicationDate = result.getDate(4);
				application.resumeLocation = result.getString(5);
				application.status = result.getInt(6);
				application.offerID = result.getString(7);
				application.interviewer = result.getInt(8);
				application.interviewDate = result.getDate(9);
				application.interviewTime = result.getTime(10);
				application.interviewResult = result.getInt(11);
				application.rejectionReason = result.getString(12);

				list.add(application);
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

	public static ArrayList<Application> getApplications(int interviewer,
			Date interviewDate, Time interviewTime) {
		ArrayList<Application> list = new ArrayList<Application>();
		Application application = null;

		Connection connection = DBConnection.getConnection();
		String sql = "SELECT * FROM HROPS_SCHEMA.\"APPLICATION\" WHERE INTERVIEWER=? AND INTERVIEW_DATE=? AND INTERVIEW_TIME=? ORDER BY INTERVIEW_DATE ASC";

		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, interviewer);
			statement.setDate(2, interviewDate);
			statement.setTime(3, interviewTime);
			ResultSet result = statement.executeQuery();

			while (result.next()) {
				application = new Application();

				application.applicationID = result.getInt(1);
				application.email = result.getString(2);
				application.opportunityID = result.getString(3);
				application.applicationDate = result.getDate(4);
				application.resumeLocation = result.getString(5);
				application.status = result.getInt(6);
				application.offerID = result.getString(7);
				application.interviewer = result.getInt(8);
				application.interviewDate = result.getDate(9);
				application.interviewTime = result.getTime(10);
				application.interviewResult = result.getInt(11);
				application.rejectionReason = result.getString(12);

				list.add(application);
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
	
	public static ArrayList<Application> getApplicationsByStatus(int status) {
		ArrayList<Application> list = new ArrayList<Application>();
		Application application = null;

		Connection connection = DBConnection.getConnection();
		String sql = "SELECT * FROM HROPS_SCHEMA.\"APPLICATION\" WHERE STATUS=? ORDER BY APPLICATION_DATE DESC";

		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, status);
			ResultSet result = statement.executeQuery();

			while (result.next()) {
				application = new Application();

				application.applicationID = result.getInt(1);
				application.email = result.getString(2);
				application.opportunityID = result.getString(3);
				application.applicationDate = result.getDate(4);
				application.resumeLocation = result.getString(5);
				application.status = result.getInt(6);
				application.offerID = result.getString(7);
				application.interviewer = result.getInt(8);
				application.interviewDate = result.getDate(9);
				application.interviewTime = result.getTime(10);
				application.interviewResult = result.getInt(11);
				application.rejectionReason = result.getString(12);

				list.add(application);
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

	public static ArrayList<Date> getInterviewDates(int interviewer) {
		ArrayList<Date> list = new ArrayList<Date>();

		Connection connection = DBConnection.getConnection();
		String sql = "SELECT DISTINCT INTERVIEW_DATE FROM HROPS_SCHEMA.\"APPLICATION\" WHERE INTERVIEWER=? ORDER BY INTERVIEW_DATE ASC";

		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, interviewer);
			ResultSet result = statement.executeQuery();

			while (result.next()) {
				list.add(result.getDate(1));
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

	public static ArrayList<Time> getInterviewTimes(int interviewer,
			Date interviewDate) {
		ArrayList<Time> list = new ArrayList<Time>();

		Connection connection = DBConnection.getConnection();
		String sql = "SELECT DISTINCT INTERVIEW_TIME FROM HROPS_SCHEMA.\"APPLICATION\" WHERE INTERVIEWER=? AND INTERVIEW_DATE=? ORDER BY INTERVIEW_TIME ASC";

		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, interviewer);
			statement.setDate(2, interviewDate);
			ResultSet result = statement.executeQuery();

			while (result.next()) {
				list.add(result.getTime(1));
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

	public static int getNumberOfApplications(int interviewer,
			Date interviewDate, Time interviewTime) {
		int count = 0;

		Connection connection = DBConnection.getConnection();
		String sql = "SELECT COUNT(*) FROM HROPS_SCHEMA.\"APPLICATION\" WHERE INTERVIEWER=? AND INTERVIEW_DATE=? AND INTERVIEW_TIME=?";

		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, interviewer);
			statement.setDate(2, interviewDate);
			statement.setTime(3, interviewTime);
			ResultSet result = statement.executeQuery();

			if (result.next()) {
				count = result.getInt(1);
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
		return count;
	}

	public boolean storeApplication() {
		boolean result = true;

		String sql = "INSERT INTO HROPS_SCHEMA.\"APPLICATION\" VALUES(DEFAULT,?,?,?,?,?,NULL,NULL,NULL,NULL,NULL,NULL)";
		Connection connection = DBConnection.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, email);
			statement.setString(2, opportunityID);
			statement.setDate(3, applicationDate);
			statement.setString(4, resumeLocation);
			statement.setInt(5, status);
			statement.executeUpdate();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			result = false;
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return result;
	}

	public static boolean exists(String email, String opportunityId) {
		Connection connection = DBConnection.getConnection();

		String sql = "SELECT COUNT(*) FROM HROPS_SCHEMA.\"APPLICATION\" WHERE EMAIL=? AND OPPORTUNITY_ID=?";
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, email);
			statement.setString(2, opportunityId);
			ResultSet result = statement.executeQuery();
			result.next();
			if (result.getInt(1) == 0)
				return false;
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
		return true;
	}

	public static boolean shortlistApplication(int applicationId) {
		boolean result = true;

		Connection connection = DBConnection.getConnection();
		String sql = "UPDATE HROPS_SCHEMA.\"APPLICATION\" SET STATUS=? WHERE APPLICATION_ID=?";

		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, SHORTLISTED);
			statement.setInt(2, applicationId);
			statement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			result = false;
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public static boolean selectApplication(int applicationId) {
		boolean result = true;

		Connection connection = DBConnection.getConnection();
		String sql = "UPDATE HROPS_SCHEMA.\"APPLICATION\" SET STATUS=? WHERE APPLICATION_ID=?";

		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, SELECTED);
			statement.setInt(2, applicationId);
			statement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			result = false;
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public static boolean selectForHire(int applicationId) {
		boolean result = true;
		String offerId = generateOfferId();

		Connection connection = DBConnection.getConnection();
		String sql = "UPDATE HROPS_SCHEMA.\"APPLICATION\" SET STATUS=?, OFFER_ID=? WHERE APPLICATION_ID=?";

		try {
			connection.setAutoCommit(false);
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, OFFER_SENT);
			statement.setString(2, offerId);
			statement.setInt(3, applicationId);
			statement.executeUpdate();
			
			sql = "UPDATE HROPS_SCHEMA.JOB_OPPORTUNITY SET NO_OF_VACANCIES=NO_OF_VACANCIES-1 WHERE OPPORTUNITY_ID=(SELECT OPPORTUNITY_ID FROM HROPS_SCHEMA.\"APPLICATION\" WHERE APPLICATION_ID=?)";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, applicationId);
			statement.executeUpdate();
			
			connection.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			result = false;
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (result) {
			// Send Offer Mail
			JobOpportunity opportunity = JobOpportunity.getJobOpportunity(getApplication(applicationId).opportunityID);
			Position position = Position.getPosition(opportunity.getPosition());
			SendMail.sendOfferMail(getApplication(applicationId).email, position.getPositionDescription(), offerId);
		}
		return result;
	}

	public static boolean rejectApplication(int applicationId, int status, String rejectionReason) {
		if(status != REJECTED_IN_SELECTION && status != REJECTED_IN_SHORTLISTING && status != OFFER_REJECTED)
			return false;
		
		boolean result = true;

		Connection connection = DBConnection.getConnection();
		String sql = "UPDATE HROPS_SCHEMA.\"APPLICATION\" SET STATUS=?, REASON=? WHERE APPLICATION_ID=?";

		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, status);
			statement.setString(2, rejectionReason);
			statement.setInt(3, applicationId);
			statement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			result = false;
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (result) {
			// Send Rejection Mail
			SendMail.sendRejectionMail(Application.getApplication(applicationId).email, rejectionReason);
		}
		return result;
	}
	
	public static boolean scheduleInterview(int applicationId, int interviewer, Date interviewDate, Time interviewTime) {
		boolean result = true;

		Connection connection = DBConnection.getConnection();
		String sql = "UPDATE HROPS_SCHEMA.\"APPLICATION\" SET INTERVIEWER=?, INTERVIEW_DATE=?, INTERVIEW_TIME=? WHERE APPLICATION_ID=?";

		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, interviewer);
			statement.setDate(2, interviewDate);
			statement.setTime(3, interviewTime);
			statement.setInt(4, applicationId);
			statement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			result = false;
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(result)
		{
			Application application = getApplication(applicationId);
			SendMail.sendInterviewMail(application.email, application.opportunityID, interviewDate, interviewTime);
		}
		return result;
	}
	
	public static boolean updateScore(int applicationId, int interviewResult) {
		boolean result = true;

		Connection connection = DBConnection.getConnection();
		String sql = "UPDATE HROPS_SCHEMA.\"APPLICATION\" SET \"RESULT\"=? WHERE APPLICATION_ID=?";

		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, interviewResult);
			statement.setInt(2, applicationId);
			statement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			result = false;
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public static boolean acceptOrRejectOffer(String offerID, int status) {
		if(status != OFFER_ACCEPTED && status != OFFER_REJECTED)
			return false;
		
		boolean result = false;

		Connection connection = DBConnection.getConnection();
		String sql = "UPDATE HROPS_SCHEMA.\"APPLICATION\" SET STATUS=? WHERE OFFER_ID=?";

		try {
			connection.setAutoCommit(false);
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, status);
			statement.setString(2, offerID);
			statement.executeUpdate();
			
			if(status == OFFER_ACCEPTED)
			{
				sql = "SELECT DEPARTMENT_ID, POSITION_ID FROM HROPS_SCHEMA.JOB_OPPORTUNITY WHERE OPPORTUNITY_ID=(SELECT OPPORTUNITY_ID FROM HROPS_SCHEMA.\"APPLICATION\" WHERE OFFER_ID=?)";
				statement = connection.prepareStatement(sql);
				statement.setString(1, offerID);
				ResultSet resultSet = statement.executeQuery();
				
				int employeeId, departmentId, positionId;
				String email;
				
				if(resultSet.next())
				{
					departmentId = resultSet.getInt(1);
					positionId = resultSet.getInt(2);
				}
				else
				{
					System.out.println("rollback 1");
					connection.rollback();
					return false;
				}
				
				sql = "SELECT EMAIL FROM HROPS_SCHEMA.\"APPLICATION\" WHERE OFFER_ID=?";
				statement = connection.prepareStatement(sql);
				statement.setString(1, offerID);
				resultSet = statement.executeQuery();
				
				if(resultSet.next())
					email = resultSet.getString(1);
				else
				{
					System.out.println("rollback 2");
					connection.rollback();
					return false;
				}
				
				String symbols = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
				Random random = new Random();
				
				StringBuffer password = new StringBuffer(10);
				for(int i=0; i<10;i++)
					password.append(symbols.charAt(random.nextInt(symbols.length())));
				
				sql = "INSERT INTO HROPS_SCHEMA.EMPLOYEE VALUES(DEFAULT,?,?,NULL,CURRENT_DATE,NULL,?,NULL,NULL,NULL,NULL)";
				statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				statement.setString(1, Authenticator.convtoMD5(password.toString()));
				statement.setInt(2, positionId);
				statement.setInt(3, departmentId);
				statement.executeUpdate();
				
				resultSet = statement.getGeneratedKeys();
				if(resultSet.next())
					employeeId = resultSet.getInt(1);
				else
				{
					System.out.println("rollback 3");
					connection.rollback();
					return false;
				}
				SendMail.sendUsernameAndPassword(email, Integer.toString(employeeId), password.toString());
			}
			
			sql = "UPDATE HROPS_SCHEMA.\"APPLICATION\" SET OFFER_ID=NULL WHERE OFFER_ID=?";
			statement = connection.prepareStatement(sql);
			statement.setString(1, offerID);
			statement.executeUpdate();
			
			connection.commit();
			
			result = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			result = false;
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}
	
	private static String generateOfferId()
	{
		String symbols = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		Random random = new Random();
		
		StringBuffer offerId = new StringBuffer(10);
		for(int i=0; i<10;i++)
			offerId.append(symbols.charAt(random.nextInt(symbols.length())));
		
		int count = 0;
		Connection connection = DBConnection.getConnection();
		String sql = "SELECT COUNT(*) FROM HROPS_SCHEMA.\"APPLICATION\" WHERE OFFER_ID=?";
		
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, offerId.toString());
			ResultSet result = statement.executeQuery();
			
			if(result.next())
				count = result.getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(count == 0)
			return offerId.toString();
		else
			return generateOfferId();
	}
}
