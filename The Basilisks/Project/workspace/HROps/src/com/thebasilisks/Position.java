package com.thebasilisks;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class Position {
	private int positionId;
	private String positionDescription;
	
	public Position(){
	}
/**
	 * @return the positionId
	 */
	public int getPositionId() {
		return positionId;
	}
	/**
	 * @param positionId the positionId to set
	 */
	public void setPositionId(int positionId) {
		this.positionId = positionId;
	}
	/**
	 * @return the positionDescription
	 */
	public String getPositionDescription() {
		return positionDescription;
	}
	/**
	 * @param positionDescription the positionDescription to set
	 */
	public void setPositionDescription(String positionDescription) {
		this.positionDescription = positionDescription;
	}
	public HashMap getAllPosition(){
	HashMap<Integer,String> map=new HashMap<Integer, String>();
	Connection con = DBConnection.getConnection();
	try {
		
		PreparedStatement st = con.prepareStatement("SELECT POSITION_ID,POS_DESC from HROPS_SCHEMA.POSITION");
	int posid;
	String posdesc;
	ResultSet resultset=st.executeQuery();
	while(resultset.next()){
		posid=resultset.getInt(1);
		posdesc=resultset.getString(2);
		map.put(posid, posdesc);
     }
} catch (SQLException e) {
		// TODO Auto-generated catch block
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
	public static Position getPosition(int positionId)
	{
		Position position = null;

		Connection connection = DBConnection.getConnection();
		String sql = "SELECT * FROM HROPS_SCHEMA.\"POSITION\" WHERE POSITION_ID=?";

		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, positionId);
			ResultSet result = statement.executeQuery();

			if (result.next()) {
				position = new Position();
				position.positionId = result.getInt(1);
				position.positionDescription = result.getString(2);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			position = null;
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return position;
	}

}
