package com.revature.dao;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;
import java.util.List;

import com.revature.models.ReimbursementStatus;

/*******************************
 * Programmer: Jadzia Kephart
 * Date Created: 27 January 2017
 * Date Last-Updated: 27 January 2017
 *
 * Class Name: ReimbursementStatusDAO
 * Purpose: To interact with the database reimbursement status table
 ******************************/
public class ReimbursementStatusDAO extends DAO
{
	//no-arg constructor
	public ReimbursementStatusDAO()
	{
	}
	
	//overloaded constructor
	//Purpose: to transfer savepoints from DAO to DAO
	public ReimbursementStatusDAO(Savepoint savepoint)
	{
		super(savepoint);
	}

	//Purpose: to provide a list of reimbursement statuses
	//Tested and Works
	public boolean retrieveReimbursementStatuses(List<ReimbursementStatus> list, Connection conn)
	{
		try
		{
			String select = "SELECT * FROM ers_reimbursement_status";
			
			Statement statement = conn.createStatement();
			
			ResultSet rs = statement.executeQuery(select);
			
			while(rs.next())
			{
				list.add(new ReimbursementStatus(rs.getInt("rs_id"), rs.getString("rs_status")));
			}
			
			return true;
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
		}
		return false;
	}
}