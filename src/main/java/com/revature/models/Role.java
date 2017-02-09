/*******************************
 * Programmer: Jadzia Kephart
 * Date Created: 27 January 2017
 * Date Last-Updated: 27 January 2017
 *
 * Class Name: Role
 * Purpose: To store a user role from the database
 ******************************/

package com.revature.models;

public class Role
{
	private int id; //the id of the role
	private String title; //the title of the role
	
	public Role(int id)
	{
		super();
		this.id = id;
		this.title = "No Role";
	}
	
	public Role(int id, String title)
	{
		super();
		this.id = id;
		this.title = title;
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

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}
}
