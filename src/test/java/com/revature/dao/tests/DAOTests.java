package com.revature.dao.tests;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.revature.dao.DAO;
import com.revature.dao.ReimbursementDAO;
import com.revature.dao.RoleDAO;
import com.revature.models.Reimbursement;
import com.revature.models.ReimbursementType;
import com.revature.models.Role;
import com.revature.models.User;

public class DAOTests
{

	@Before
	public void setUp() throws Exception
	{
	}

	@After
	public void tearDown() throws Exception
	{
	}


	@Ignore
	public void insertReimbursement()
	{
		try(Connection conn = DriverManager.getConnection(DAO.URL, DAO.USERNAME, DAO.PASSWORD);)
		{
			Reimbursement reimbursement = new Reimbursement(109.0, "desc", new User(1), new ReimbursementType(1));		
			ReimbursementDAO dao = new ReimbursementDAO();
			assertTrue(dao.insertReimbursement(reimbursement, conn));
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
			fail("thing");
		}
	}
	
	@Ignore
	public void updateReimbursement()
	{
		try(Connection conn = DriverManager.getConnection(DAO.URL, DAO.USERNAME, DAO.PASSWORD);)
		{
			List<Role> list = new ArrayList<>();
			RoleDAO dao = new RoleDAO();
			assertTrue(dao.retrieveRoles(list,conn));
			assertTrue(list.size() > 0);
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
		}
	}
	
	@Test
	public void insertPerson()
	{
		try(Connection conn = DriverManager.getConnection(DAO.URL, DAO.USERNAME, DAO.PASSWORD);)
		{

		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
			fail("thing");
		}
	}
}
