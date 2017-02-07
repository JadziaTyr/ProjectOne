/*******************************
 * Programmer: Jadzia Kephart
 * Date Created: 27 January 2017
 * Date Last-Updated: 27 January 2017
 *
 * Class Name: User
 * Purpose: To store a user from the database
 ******************************/

package com.revature.models;

import java.util.ArrayList;
import java.util.List;

public class User
{
	private int id; //the unique id of the user
	private String firstName; //the first name of the user
	private String lastName; //the last name of the user
	private String userName; //the unique username of the user
	private String password; //the password of the user
	private String email; //the email of the user
	private Role personRole; //the role of the user

	private List<Reimbursement> reimbursementList;
	private int pendingCount;
	private int resolvedCount;
	
	public User()
	{
		super();
		this.id = -1;
		this.firstName = "firstName";
		this.lastName = "lastName";
		this.userName = "userName";
		this.password = "password";
		this.email = "email";
		this.personRole = null;
		this.reimbursementList = new ArrayList<Reimbursement>();	
		this.pendingCount = 0;
		this.resolvedCount = 0;
	}
	
	public User(int id)
	{
		super();
		this.id = id;
	}
	
	public User(String username, String password)
	{
		this();
		this.userName = username;
		this.password = password;
	}
	
	public User(String firstName, String lastName, String userName, String password, String email, Role personRole)
	{
		this();
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.personRole = personRole;
	}
	
	public User(int id, String firstName, String lastName, String userName, String password, String email, Role personRole)
	{
		this();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.personRole = personRole;
	}

	@Override
	public String toString()
	{
		return this.firstName + " " + this.lastName + " " + this.userName + " " + this.password + " " + this.email;
	}

	public String getFullName()
	{
		return firstName + " " + lastName;
	}
	
	
	
	//ACCESS and MUTATE
	public int getId()
	{
		return id;
	}
	
	public void setId(int id)
	{
		this.id = id;
	}
	
	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public Role getPersonRole()
	{
		return personRole;
	}

	public void setPersonRole(Role personRole)
	{
		this.personRole = personRole;
	}

	public List<Reimbursement> getReimbursementList()
	{
		return reimbursementList;
	}

	public void setReimbursementList(List<Reimbursement> reimbursementList)
	{
		this.reimbursementList = reimbursementList;
	}
	
	public int getPendingCount()
	{
		return pendingCount;
	}

	public void setPendingCount(int pendingCount)
	{
		this.pendingCount = pendingCount;
	}

	public int getResolvedCount()
	{
		return resolvedCount;
	}

	public void setResolvedCount(int resolvedCount)
	{
		this.resolvedCount = resolvedCount;
	}
}
