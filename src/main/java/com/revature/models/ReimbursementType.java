/*******************************
 * Programmer: Jadzia Kephart
 * Date Created: 27 January 2017
 * Date Last-Updated: 27 January 2017
 *
 * Class Name: ReimbursementType
 * Purpose: To store a reimbursement type from the database
 ******************************/

package com.revature.models;

public class ReimbursementType
{
	private int id; //the id of the type
	private String type; //the type
	
	public ReimbursementType()
	{
		super();
		this.id = -1;
		this.type = "No Type";
	}
	
	public ReimbursementType(int id)
	{
		this();
		this.id = id;
	}
	
	public ReimbursementType(int id, String type)
	{
		super();
		this.id = id;
		this.type = type;
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

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}
}
