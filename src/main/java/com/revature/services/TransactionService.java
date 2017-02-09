/*******************************
 * Programmer: Jadzia Kephart
 * Date Created: 27 January 2017
 * Date Last-Updated: 27 January 2017
 *
 * Class Name: TransactionService
 * Purpose: To store transactions and assist in undoing them
 ******************************/

package com.revature.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import com.revature.dao.DAO;
import com.revature.dao.ReimbursementDAO;
import com.revature.dao.ReimbursementStatusDAO;
import com.revature.dao.ReimbursementTypeDAO;
import com.revature.dao.RoleDAO;
import com.revature.dao.UserDAO;
import com.revature.models.Reimbursement;
import com.revature.models.ReimbursementStatus;
import com.revature.models.ReimbursementType;
import com.revature.models.Role;
import com.revature.models.User;

public class TransactionService
{
	private DAO dao; // a DAO object to be utilized all around

	static
	{
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}

	/*
	 * Purpose: Return if login was a success
	 */
	public boolean initialReadTransaction(List<ReimbursementType> typeList, List<ReimbursementStatus> statusList,
			List<Role> roleList)
	{
		try (Connection conn = DriverManager.getConnection(DAO.URL, DAO.USERNAME, DAO.PASSWORD);)
		{
			dao = new ReimbursementTypeDAO();
			((ReimbursementTypeDAO) dao).retrieveReimbursementTypes(typeList, conn);

			dao = new ReimbursementStatusDAO();
			((ReimbursementStatusDAO) dao).retrieveReimbursementStatuses(statusList, conn);

			dao = new RoleDAO();
			((RoleDAO) dao).retrieveRoles(roleList, conn);
		}
		catch (SQLException ex)
		{
			System.out.println("INITIAL READ TRANSACTION");
			ex.printStackTrace();
		}
		return false;
	}

	/*
	 * Purpose: Return if login was a success
	 */
	public boolean loginTransaction(User person)
	{
		try (Connection conn = DriverManager.getConnection(DAO.URL, DAO.USERNAME, DAO.PASSWORD);)
		{
			dao = new UserDAO();

			return ((UserDAO) dao).retrievePersonByUsername(person, conn);
		}
		catch (SQLException ex)
		{
			System.out.println("LOG IN TRANSACTION");
			ex.printStackTrace();
		}
		return false;
	}

	/*
	 * Purpose: Return if updating a person succeeded
	 */
	public boolean updatePersonTransaction(User person)
	{
		try (Connection conn = DriverManager.getConnection(DAO.URL, DAO.USERNAME, DAO.PASSWORD);)
		{
			conn.setAutoCommit(false);
			dao = new UserDAO();

			dao.createSavePoint(conn);

			if (((UserDAO) dao).updatePersonById(person, conn))
			{
				DAO.commit(conn);
				return true;
			}
			dao.rollback(conn);
		}
		catch (SQLException ex)
		{
			System.out.println("UPDATE PERSON TRANSACTION");
			ex.printStackTrace();
		}
		return false;
	}

	/*
	 * Purpose: Return if inputting a transaction worked
	 */
	public boolean newReimbursmentTransaction(User person, Reimbursement reimbursement)
	{
		try (Connection conn = DriverManager.getConnection(DAO.URL, DAO.USERNAME, DAO.PASSWORD);)
		{
			conn.setAutoCommit(false);
			dao = new ReimbursementDAO();

			dao.createSavePoint(conn);

			if (((ReimbursementDAO) dao).insertReimbursement(reimbursement, conn))
			{
				DAO.commit(conn);
				return true;
			}
			dao.rollback(conn);
		}
		catch (SQLException ex)
		{
			System.out.println("INPUT NEW REIMBURSEMENT REQUEST");
			ex.printStackTrace();
		}
		return false;
	}

	public boolean newUser(User user)
	{
		try (Connection conn = DriverManager.getConnection(DAO.URL, DAO.USERNAME, DAO.PASSWORD);)
		{
			conn.setAutoCommit(false);
			dao = new UserDAO();

			dao.createSavePoint(conn);

			if (((UserDAO)dao).insertPerson(user, conn))
			{
				DAO.commit(conn);
				return true;
			}
			dao.rollback(conn);
		}
		catch (SQLException ex)
		{
			System.out.println("INPUT NEW USER ERROR");
			ex.printStackTrace();
		}
		return false;
	}

	/*
	 * Purpose: Return if updating a reimbursement transaction worked
	 */
	public boolean updateReimbursmentTransaction(Reimbursement reimbursement)
	{
		try (Connection conn = DriverManager.getConnection(DAO.URL, DAO.USERNAME, DAO.PASSWORD);)
		{
			conn.setAutoCommit(false);
			dao = new ReimbursementDAO();

			dao.createSavePoint(conn);

			if (((ReimbursementDAO) dao).updateReimbursementWithResponseById(reimbursement, conn))
			{
				DAO.commit(conn);
				return true;
			}
			dao.rollback(conn);
		}
		catch (SQLException ex)
		{
			System.out.println("UPDATE REIMBURSEMENT REQUEST");
			ex.printStackTrace();
		}
		return false;
	}

	public boolean fetchUserReimbursementList(User person)
	{
		try (Connection conn = DriverManager.getConnection(DAO.URL, DAO.USERNAME, DAO.PASSWORD);)
		{
			dao = new ReimbursementDAO();

			conn.setAutoCommit(false);
			dao.createSavePoint(conn);

			if (((ReimbursementDAO) dao).retrieveReimbursementsByUserId(conn, person))
			{
				DAO.commit(conn);
				return true;
			}

			dao.rollback(conn);
		}
		catch (SQLException ex)
		{
			System.out.println("FETCH REIMBURSEMENT REQUESTS FOR SPECIFIC USER");
			ex.printStackTrace();
		}
		return false;
	}

	public boolean fetchReimbursementList(List<Reimbursement> list)
	{
		try (Connection conn = DriverManager.getConnection(DAO.URL, DAO.USERNAME, DAO.PASSWORD);)
		{
			dao = new ReimbursementDAO();

			conn.setAutoCommit(false);
			dao.createSavePoint(conn);

			if (!((ReimbursementDAO) dao).retrieveAllReimbursements(conn, list))
			{
				return false;
			}

			System.out.println("After retrieve reimbursements");
			
			dao = new UserDAO();
			for (int i = 0; i < list.size(); i++)
			{
				if (!(((UserDAO) dao).retrievePersonById(list.get(i).getRequester(), conn) && ((UserDAO) dao).retrievePersonById(list.get(i).getApprover(), conn)))
				{
					return false;
				}
			}

			System.out.println("After retrieve users");

			return true;
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
		}
		return false;
	}

	public boolean fetchUserById(User person)
	{
		try (Connection conn = DriverManager.getConnection(DAO.URL, DAO.USERNAME, DAO.PASSWORD);)
		{
			dao = new UserDAO();

			conn.setAutoCommit(false);
			dao.createSavePoint(conn);

			if (((UserDAO) dao).retrievePersonById(person, conn))
			{
				DAO.commit(conn);
				return true;
			}

			dao.rollback(conn);
			return false;
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
		}
		return false;
	}
	
	public boolean fetchUserByEmail(User person)
	{
		try (Connection conn = DriverManager.getConnection(DAO.URL, DAO.USERNAME, DAO.PASSWORD);)
		{
			dao = new UserDAO();

			if (((UserDAO) dao).retrievePersonByEmail(person, conn))
			{
				return true;
			}

			return false;
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
		}
		return false;
	}

	public boolean fetchUserList(List<User> list)
	{
		try (Connection conn = DriverManager.getConnection(DAO.URL, DAO.USERNAME, DAO.PASSWORD);)
		{
			conn.setAutoCommit(false);

			dao = new UserDAO();

			if (((UserDAO) dao).retrieveAllPeople(list, conn))
			{
				DAO.commit(conn);
				return true;
			}
			dao.rollback(conn);
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
		}
		return false;
	}

	public boolean updateReimbursementList(List<Reimbursement> list)
	{
		try (Connection conn = DriverManager.getConnection(DAO.URL, DAO.USERNAME, DAO.PASSWORD);)
		{
			conn.setAutoCommit(false);

			dao = new ReimbursementDAO();
			dao.createSavePoint(conn);

			int lcv = 0;
			while (lcv < list.size())
			{
				if (!((ReimbursementDAO) dao).updateReimbursementWithResponseById(list.get(lcv), conn))
				{
					dao.rollback(conn);
					return false;
				}
				lcv++;
			}
			DAO.commit(conn);
			return true;
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
		}
		return false;
	}

	public boolean fetchReimbursementById(Reimbursement re)
	{
		try (Connection conn = DriverManager.getConnection(DAO.URL, DAO.USERNAME, DAO.PASSWORD);)
		{
			dao = new ReimbursementDAO();

			if (((ReimbursementDAO) dao).retrieveReimbursementsByReimbursementId(conn, re))
			{
				return true;
			}
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
		}
		return false;
	}

	public int countStatus(User person, ReimbursementStatus status)
	{
		try (Connection conn = DriverManager.getConnection(DAO.URL, DAO.USERNAME, DAO.PASSWORD);)
		{
			dao = new ReimbursementDAO();
			return ((ReimbursementDAO) dao).retrieveNumberOfReimbursementsRequestsOfStatusProvidedForUser(conn, person,
					status);
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
		}
		return 0;
	}
}
