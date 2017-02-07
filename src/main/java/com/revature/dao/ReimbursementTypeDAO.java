package com.revature.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;
import java.util.List;

import com.revature.models.ReimbursementType;

/*******************************
 * Programmer: Jadzia Kephart
 * Date Created: 27 January 2017
 * Date Last-Updated: 27 January 2017
 *
 * Class Name: ReimbursementTypeDAO
 * Purpose: To interact with the database reimbursement type table
 ******************************/
public class ReimbursementTypeDAO extends DAO
{
	//no-arg constructor
	public ReimbursementTypeDAO()
	{
	}
	
	//overloaded constructor
	//Purpose: to transfer savepoints from DAO to DAO
	public ReimbursementTypeDAO(Savepoint savepoint)
	{
		super(savepoint);
	}

	//Purpose: to provide a list of reimbursement types 
	//Tested and Works
	public boolean retrieveReimbursementTypes(List<ReimbursementType> list, Connection conn)
	{
		try
		{
			String select = "SELECT * FROM ers_reimbursement_type";
			
			Statement statement = conn.createStatement();
			
			ResultSet rs = statement.executeQuery(select);
			
			while(rs.next())
			{
				list.add(new ReimbursementType(rs.getInt("rt_id"), rs.getString("rt_type")));
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
