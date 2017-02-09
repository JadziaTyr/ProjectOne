package com.revature.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;
import java.util.List;

import com.revature.models.Role;

/*******************************
 * Programmer: Jadzia Kephart
 * Date Created: 27 January 2017
 * Date Last-Updated: 27 January 2017
 *
 * Class Name: RoleDAO
 * Purpose: To interact with the database user role table
 ******************************/
public class RoleDAO extends DAO
{
	//no-arg constructor
	public RoleDAO()
	{
		super();
	}

	//overloaded constructor
	//Purpose: to transfer savepoints from DAO to DAO
	public RoleDAO(Savepoint savepoint)
	{
		super(savepoint);
	}

	//Purpose: to provide a list of user roles
	//Tested and Works
	public boolean retrieveRoles(List<Role> list, Connection conn)
	{
		try
		{
			String select = "SELECT * FROM ers_user_roles";
			
			Statement statement = conn.createStatement();
			
			ResultSet rs = statement.executeQuery(select);
			
			while(rs.next())
			{
				list.add(new Role(rs.getInt("ur_id"), rs.getString("ur_role")));
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

