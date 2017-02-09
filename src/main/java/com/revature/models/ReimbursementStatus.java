/*******************************
 * Programmer: Jadzia Kephart
 * Date Created: 27 January 2017
 * Date Last-Updated: 27 January 2017
 *
 * Class Name: ReimbursementStatus
 * Purpose: To store a reimbursement status from the database
 ******************************/

package com.revature.models;

public class ReimbursementStatus
{
	private int id; //the id of the status
	private String status; //the status
	
	public ReimbursementStatus()
	{
		super();
		this.id = -1;
		this.status = "No Status";
	}
	
	public ReimbursementStatus(int id)
	{
		this();
		this.id = id;
	}
	
	public ReimbursementStatus(String status)
	{
		this();
		this.status = status;
	}
	
	public ReimbursementStatus(int id, String status)
	{
		super();
		this.id = id;
		this.status = status;
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
	
	public String getStatus()
	{
		return status;
	}
	
	public void setStatus(String status)
	{
		this.status = status;
	}
}
