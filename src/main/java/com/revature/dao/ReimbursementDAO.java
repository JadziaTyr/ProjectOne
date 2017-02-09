/*******************************
 * Programmer: Jadzia Kephart
 * Date Created: 27 January 2017
 * Date Last-Updated: 27 January 2017
 *
 * Class Name: ReimbursementDAO
 * Purpose: To interact with the database reimbursement table
 ******************************/

package com.revature.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.Calendar;
import java.util.List;

import com.revature.models.Reimbursement;
import com.revature.models.ReimbursementStatus;
import com.revature.models.ReimbursementType;
import com.revature.models.User;

public class ReimbursementDAO extends DAO
{
	//no-arg constructor
	public ReimbursementDAO()
	{
		super();
	}
	
	//overloaded constructor
	//Purpose: to transfer savepoints from DAO to DAO
	public ReimbursementDAO(Savepoint savepoint)
	{
		super(savepoint);
	}

	//Purpose: to retrieve a list of all reimbursement requests by user
	//Tested and Works
	public boolean retrieveReimbursementsByUserId(Connection conn, User person)
	{
		try
		{
			//PreparedStatement
			String selectStatement = "SELECT * FROM ers_reimbursements WHERE u_id_author = ? ORDER BY r_submitted DESC";
		
	        PreparedStatement statement = conn.prepareStatement(selectStatement); //create a statement for running 
	        statement.setInt(1, person.getId());
			ResultSet rs =  statement.executeQuery();

			while(rs.next())
			{
				
				
				person.getReimbursementList().add(new Reimbursement(rs.getInt("r_id"), 
						                   	      rs.getDouble("r_amount"), 
						                          rs.getString("r_description"),
						                          rs.getBlob("r_recepit") != null?rs.getBlob("r_recepit").getBytes(1, (int)rs.getBlob("r_recepit").length()) : new byte[0],
						                          rs.getTimestamp("r_submitted"),
						                          rs.getTimestamp("r_resolved"),
						                          person,
						                          new User(rs.getInt("u_id_resolver")),
						                          new ReimbursementType(rs.getInt("rt_type")),
						                          new ReimbursementStatus(rs.getInt("rs_status"))));
			}
			return true;
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
		}
		return false;
	}

	public int retrieveNumberOfReimbursementsRequestsOfStatusProvidedForUser(Connection conn, User person, ReimbursementStatus status)
	{
		try
		{
			//PreparedStatement
			String selectStatement = "SELECT * FROM ers_reimbursements WHERE u_id_author = ? AND rs_status = ?";
		
	        PreparedStatement statement = conn.prepareStatement(selectStatement); //create a statement for running 
	        statement.setInt(1, person.getId());
	        statement.setInt(2, status.getId());
			ResultSet rs =  statement.executeQuery();

			int count = 0;
			while(rs.next())
			{
				count++;
			}
			return count;
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
		}
		return 0;
	}
	
	//Purpose: to retrieve a list of all reimbursement requests by user
	//Tested and Works
	public boolean retrieveReimbursementsByReimbursementId(Connection conn, Reimbursement re)
	{
		try
		{
			//PreparedStatement
			String selectStatement = "SELECT * FROM ers_reimbursements WHERE r_id = ?";
		
	        PreparedStatement statement = conn.prepareStatement(selectStatement); //create a statement for running 
	        statement.setInt(1, re.getId());
			ResultSet rs =  statement.executeQuery();

			while(rs.next())
			{
				re.setId(rs.getInt("r_id"));
				re.setAmount(rs.getDouble("r_amount"));
				re.setDescription(rs.getString("r_description"));
				re.setImage(rs.getBlob("r_recepit") != null?rs.getBlob("r_recepit").getBytes(1, (int)rs.getBlob("r_recepit").length()) : new byte[0]);
				re.setSubmittedTimestamp(rs.getTimestamp("r_submitted"));
				re.setResolvedTimestamp(rs.getTimestamp("r_resolved"));
				re.setRequester(new User(rs.getInt("u_id_author")));
				re.setApprover(new User(rs.getInt("u_id_resolver")));
				re.setReimbursementType(new ReimbursementType(rs.getInt("rt_type")));
				re.setReimbursementStatus(new ReimbursementStatus(rs.getInt("rs_status")));
			}
			return true;
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
		}
		return false;
	}
	
	//Purpose: to retrieve a list of all reimbursement requests by user
	//Tested and Works
	public boolean retrieveAllReimbursements(Connection conn, List<Reimbursement> list)
	{
		try
		{
			//PreparedStatement
			String selectStatement = "SELECT * FROM ers_reimbursements ORDER BY r_submitted DESC";
		
	        PreparedStatement statement = conn.prepareStatement(selectStatement); //create a statement for running 

			ResultSet rs =  statement.executeQuery();

			while(rs.next())
			{
				list.add(new Reimbursement(rs.getInt("r_id"), 
						                   rs.getDouble("r_amount"), 
						                   rs.getString("r_description"),
						                   rs.getBlob("r_recepit") != null?rs.getBlob("r_recepit").getBytes(1, (int)rs.getBlob("r_recepit").length()) : new byte[0],
						                   rs.getTimestamp("r_submitted"),
						                   rs.getTimestamp("r_resolved"),
						                   new User(rs.getInt("u_id_author")),
						                   new User(rs.getInt("u_id_resolver")),
						                   new ReimbursementType(rs.getInt("rt_type")),
						                   new ReimbursementStatus(rs.getInt("rs_status"))));
			}
			return true;
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
		}
		return false;
	}
	
	//Purpose: to insert this reimbursement into the database 
	//Tested and Works
	public boolean insertReimbursement(Reimbursement reimbursement, Connection conn)
	{
		try
		{
			String sqlStoredProcedure = "{call insert_reimbursement(?,?,?,?,?,?,?)}";
			CallableStatement callableStatement = conn.prepareCall(sqlStoredProcedure);
			
			System.out.println(reimbursement.toString());
			
			callableStatement.setDouble("amt", reimbursement.getAmount());
			callableStatement.setString("descrip", reimbursement.getDescription());
			callableStatement.setBytes("rec", reimbursement.getImage());
			callableStatement.setTimestamp("sub", reimbursement.getSubmittedTimestamp());
			callableStatement.setInt("author_id", reimbursement.getRequester().getId());
			callableStatement.setInt("type_id", reimbursement.getReimbursementType().getId());
			callableStatement.registerOutParameter("ident", reimbursement.getId()); //account number
			
			callableStatement.executeUpdate();	
			
			reimbursement.setId(callableStatement.getInt("ident"));
			return true;
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
		}
		return false;
	}
	
	//Purpose: to update the info in the database for this reimbursement
	//Tested and Works
	public boolean updateReimbursementWithResponseById(Reimbursement reimbursement, Connection conn)
	{
		try
		{
			String sqlStoredProcedure = "{call update_reimbursement(?,?,?,?,?,?,?,?,?,?)}";
			CallableStatement callableStatement = conn.prepareCall(sqlStoredProcedure);

			callableStatement.setDouble("amt", reimbursement.getAmount());
			callableStatement.setString("descrip", reimbursement.getDescription());
			callableStatement.setBytes("rec", reimbursement.getImage());
			callableStatement.setTimestamp("sub", reimbursement.getSubmittedTimestamp());
			callableStatement.setInt("author_id", reimbursement.getRequester().getId());
			callableStatement.setInt("type_id", reimbursement.getReimbursementType().getId());
			callableStatement.setInt("reviewer_id", reimbursement.getApprover().getId());
			callableStatement.setInt("status_id", reimbursement.getReimbursementStatus().getId());
			callableStatement.setTimestamp("resultTime", new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));
			callableStatement.setInt("ident", reimbursement.getId());
			
			callableStatement.executeUpdate();	
			return true;
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
		}
		return false;
	}
}
