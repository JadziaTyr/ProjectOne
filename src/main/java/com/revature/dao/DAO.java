/*******************************
 * Programmer: Jadzia Kephart
 * Date Created: 27 January 2017
 * Date Last-Updated: 27 January 2017
 *
 * Class Name: DAO
 * Purpose: To store the pieces to connect to a database
 *          To allow every DAO to create a savepoint, rollback and commit(facilitate TLC)
 ******************************/

package com.revature.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;

public abstract class DAO
{
	/*- local DB
	//the pieces to connect to the database
	public static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	public static final String USERNAME = "reembursement_db";
	public static final String PASSWORD = "p4ssw0rd";
	*/
	
	//online
	public static final String URL = "jdbc:oracle:thin:@reimbursement.ckoyfwk648zi.us-east-1.rds.amazonaws.com:1521:ORCL";
	public static final String USERNAME = "JadziaTyr";
	public static final String PASSWORD = "p4ssw0rd";
	
	//a restore point in case a transaction goes awry
	protected Savepoint savepoint;
	
	//no-arg constructor
	public DAO()
	{
		this.savepoint = null;
	}
	
	//overloaded constructor
	//Purpose: to transfer save points from DAO to DAO
	public DAO(Savepoint savepoint)
	{
		this.savepoint = savepoint;
	}
	
	//Purpose: to commit all new data to the database
	public static boolean commit(Connection conn)
	{
		try
		{
			conn.commit();		
			return true;
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return false;
	}
	
	//Purpose: to remove all new data to the database
	public boolean rollback(Connection conn)
	{
		try 
		{
			conn.rollback(savepoint);
			savepoint = null;
			return true;
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return false;
	}

	//Purpose: to create a safe restore point before adding new data to the database
	public void createSavePoint(Connection conn)
	{
		try
		{
			savepoint = conn.setSavepoint();		
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}

	//ACCESS 
	public Savepoint getSavepoint()
	{
		return savepoint;
	}
}
