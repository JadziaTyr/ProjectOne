/*******************************
 * Programmer: Jadzia Kephart
 * Date Created: 27 January 2017
 * Date Last-Updated: 27 January 2017
 *
 * Class Name: UserDAO
 * Purpose: To interact with the database user table
 ******************************/

package com.revature.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;
import java.util.List;

import com.revature.models.Role;
import com.revature.models.User;

public class UserDAO extends DAO
{
	//no-arg constructor
	public UserDAO()
	{
	}
	
	//overloaded constructor
	//Purpose: to transfer savepoints from DAO to DAO
	public UserDAO(Savepoint savepoint)
	{
		super(savepoint);
	}

	//Purpose: to return a list of all the people
	//Tested and Works
	public boolean retrieveAllPeople(List<User> list, Connection conn)
	{
		try
		{
			String select = "SELECT * FROM ers_users";
			
			Statement statement = conn.createStatement();
			
			ResultSet rs = statement.executeQuery(select);
			
			while(rs.next())
			{
				list.add(new User(rs.getInt("u_id"), 
						          rs.getString("u_firstname"), 
						          rs.getString("u_lastname"),
						          rs.getString("u_username"),
						          rs.getString("u_password"),
						          rs.getString("u_email"),
						          new Role(rs.getInt("ur_id"), "")));
			}
			
			return true;
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
		}
		return false;
	}
	
	//Purpose: to retrieve the person with the provided username
	//Tested and Works
	public boolean retrievePersonByUsernameAndPassword(User person, Connection conn)
	{
		try
		{
			//PreparedStatement
			String selectStatement = "SELECT * FROM ers_users WHERE u_username = ? AND u_password = ?";
		
	        PreparedStatement statement = conn.prepareStatement(selectStatement); //create a statement for running 
	        statement.setString(1, person.getUserName());
	        statement.setString(2, person.getPassword());
	        
			ResultSet rs =  statement.executeQuery();
			
			if(rs.next())
			{
				person.setId(rs.getInt("u_id"));
				person.setFirstName(rs.getString("u_firstname"));
				person.setLastName(rs.getString("u_lastname"));
				person.setPassword(rs.getString("u_password"));
				person.setEmail(rs.getString("u_email"));
				person.setPersonRole(new Role(rs.getInt("ur_id"), ""));
				return true;
			}
			return false;
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
		}
		return false;
	}
	
	//Purpose: to retrieve the person with the provided username
	//Tested and Works
	public boolean retrievePersonById(User person, Connection conn)
	{
		try
		{
			//PreparedStatement
			String selectStatement = "SELECT * FROM ers_users WHERE u_id = ?";
		
	        PreparedStatement statement = conn.prepareStatement(selectStatement); //create a statement for running 
	        statement.setInt(1, person.getId());
			ResultSet rs =  statement.executeQuery();
			
			if(rs.next())
			{
				person.setFirstName(rs.getString("u_firstname"));
				person.setLastName(rs.getString("u_lastname"));
				person.setUserName(rs.getString("u_username"));
				person.setPassword(rs.getString("u_password"));
				person.setEmail(rs.getString("u_email"));
				person.setPersonRole(new Role(rs.getInt("ur_id"), ""));
			}
			return true;
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
		}
		return false;
	}
	
	public boolean retrievePersonByEmail(User person, Connection conn)
	{
		try
		{
			//PreparedStatement
			String selectStatement = "SELECT * FROM ers_users WHERE u_email = ?";
		
	        PreparedStatement statement = conn.prepareStatement(selectStatement); //create a statement for running 
	        statement.setString(1, person.getEmail());
			ResultSet rs =  statement.executeQuery();
			
			if(rs.next())
			{
				person.setId(rs.getInt("u_id"));
				person.setFirstName(rs.getString("u_firstname"));
				person.setLastName(rs.getString("u_lastname"));
				person.setUserName(rs.getString("u_username"));
				person.setPassword(rs.getString("u_password"));
				person.setEmail(rs.getString("u_email"));
				person.setPersonRole(new Role(rs.getInt("ur_id"), ""));
			}
			return true;
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
		}
		return false;
	}
	
	//Purpose: to insert a new user in the database
	//Tested and Works
	public boolean insertPerson(User person, Connection conn)
	{
		try
		{
			String sqlStoredProcedure = "{call insert_user(?,?,?,?,?,?,?)}";
			CallableStatement callableStatement = conn.prepareCall(sqlStoredProcedure);
			
			callableStatement.setString("username", person.getUserName());
			callableStatement.setString("pass_word", person.getPassword());
			callableStatement.setString("firstN", person.getFirstName());
			callableStatement.setString("lastN", person.getLastName());
			callableStatement.setString("email", person.getEmail());
			callableStatement.setInt("role_id", person.getPersonRole().getId());

			callableStatement.registerOutParameter("ident", person.getId());
			
			callableStatement.executeUpdate();	
			
			person.setId(callableStatement.getInt("ident"));
			return true;
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
		}
		return false;
	}
	
	//Purpose: to update the info in the database for this user
	//Tested and Works
	public boolean updatePersonById(User person, Connection conn)
	{
		try
		{
			String sqlStoredProcedure = "{call update_user(?,?,?,?,?,?,?)}";
			CallableStatement callableStatement = conn.prepareCall(sqlStoredProcedure);
			
			callableStatement.setInt("ident", person.getId());
			callableStatement.setString("username", person.getUserName());
			callableStatement.setString("pass_word", person.getPassword());
			callableStatement.setString("firstN", person.getFirstName());
			callableStatement.setString("lastN", person.getLastName());
			callableStatement.setString("email", person.getEmail());
			callableStatement.setInt("role_id", person.getPersonRole().getId());
			
			
			callableStatement.executeUpdate();	
			return true;
		}
		catch(SQLException ex)
		{
			
		}
		return false;
	}
}
