package com.thebasilisks;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.HashMap;

public class Qualification {

	private int qualificationID;
	private String qualificationDescription;
	
	public Qualification(){
	
}
	
	/**
	 * @return the qualificationID
	 */
	public int getQualificationID() {
		return qualificationID;
	}

	/**
	 * @param qualificationID the qualificationID to set
	 */
	public void setQualificationID(int qualificationID) {
		this.qualificationID = qualificationID;
	}

	/**
	 * @return the qualificationDescription
	 */
	public String getQualificationDescription() {
		return qualificationDescription;
	}

	/**
	 * @param qualificationDescription the qualificationDescription to set
	 */
	public void setQualificationDescription(String qualificationDescription) {
		this.qualificationDescription = qualificationDescription;
	}

	public HashMap<Integer,String> getQual(){
		Connection con=DBConnection.getConnection();
		HashMap<Integer,String> map=new HashMap<Integer, String>();
		try{
			
			Statement st=con.createStatement();
			ResultSet resultset = st.executeQuery("select * from HROPS_SCHEMA.Qualification");
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
	
	public static Qualification getQualification(int qualificationID)
	{
		Qualification qualification = null;

		Connection connection = DBConnection.getConnection();
		String sql = "SELECT * FROM HROPS_SCHEMA.QUALIFICATION WHERE QUAL_ID=?";

		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, qualificationID);
			ResultSet result = statement.executeQuery();

			if (result.next()) {
				qualification = new Qualification();
				qualification.qualificationID = result.getInt(1);
				qualification.qualificationDescription = result.getString(2);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			qualification = null;
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return qualification;
	}
}
