/*******************************
 * Programmer: Jadzia Kephart
 * Date Created: 27 January 2017
 * Date Last-Updated: 27 January 2017
 *
 * Class Name: Reimbursement
 * Purpose: To store a reimbursement from the database
 ******************************/

package com.revature.models;

import java.sql.Blob;
import java.sql.Timestamp;
import java.util.Calendar;

import javax.sql.rowset.serial.SerialBlob;

public class Reimbursement
{
	private int id; //the id of the reimbursement request
	private double amount; //the amount of the reimbursement request
	private String description; //the description of the reimbursement request
	private byte[] image; //the image with the reimbursement request
	private Timestamp submittedTimestamp; //the time this request was submitted
	private Timestamp resolvedTimestamp; //the time this request was resloved
	private User requester; //the user who submitted this request
	private User approver; //the user who approved this request
	private ReimbursementType reimbursementType; //the reimbursement type
	private ReimbursementStatus reimbursementStatus; //the status of the request

	public Reimbursement()
	{
		this.id = -1;
		this.amount = -1;
		this.description = "";
		this.image = new byte[0];
		this.submittedTimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
		this.resolvedTimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
		this.requester = new User();
		this.approver = new User();
		this.reimbursementType = new ReimbursementType();
		this.reimbursementStatus = new ReimbursementStatus();
	}

	public Reimbursement(double amount, String description, User requester, ReimbursementType reimbursementType)
	{
		this();
		this.amount = amount;
		this.description = description;
		this.submittedTimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
		this.requester = requester;
		this.reimbursementType = reimbursementType;
	}

	public Reimbursement(int id, double amount, String description, byte[] image, Timestamp submittedTimestamp,
			Timestamp resolvedTimestamp, User requester, User approver, ReimbursementType reimbursementType,
			ReimbursementStatus reimbursementStatus)
	{
		super();
		this.id = id;
		this.amount = amount;
		this.description = description;
		this.image = image;
		this.submittedTimestamp = submittedTimestamp;
		this.resolvedTimestamp = resolvedTimestamp;
		this.requester = requester;
		this.approver = approver;
		this.reimbursementType = reimbursementType;
		this.reimbursementStatus = reimbursementStatus;
	}

	public Reimbursement(int id)
	{
		this();
		this.id = id;
	}

	@Override
	public String toString()
	{
		return id + " " + amount + " " + description + " " + requester.getFullName() + " " + submittedTimestamp;
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

	public double getAmount()
	{
		return amount;
	}

	public void setAmount(double amount)
	{
		this.amount = amount;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public byte[] getImage()
	{
		return image;
	}

	public void setImage(byte[] image)
	{
		this.image = image;
	}

	public Timestamp getSubmittedTimestamp()
	{
		return submittedTimestamp;
	}

	public void setSubmittedTimestamp(Timestamp submittedTimestamp)
	{
		this.submittedTimestamp = submittedTimestamp;
	}

	public Timestamp getResolvedTimestamp()
	{
		return resolvedTimestamp;
	}

	public void setResolvedTimestamp(Timestamp resolvedTimestamp)
	{
		this.resolvedTimestamp = resolvedTimestamp;
	}

	public User getRequester()
	{
		return requester;
	}

	public void setRequester(User requester)
	{
		this.requester = requester;
	}

	public User getApprover()
	{
		return approver;
	}

	public void setApprover(User approver)
	{
		this.approver = approver;
	}

	public ReimbursementType getReimbursementType()
	{
		return reimbursementType;
	}

	public void setReimbursementType(ReimbursementType reimbursementType)
	{
		this.reimbursementType = reimbursementType;
	}

	public ReimbursementStatus getReimbursementStatus()
	{
		return reimbursementStatus;
	}

	public void setReimbursementStatus(ReimbursementStatus reimbursementStatus)
	{
		this.reimbursementStatus = reimbursementStatus;
	}
}
