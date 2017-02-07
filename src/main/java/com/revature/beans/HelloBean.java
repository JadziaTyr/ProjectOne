package com.revature.beans;

/**
 * Model
 */
public class HelloBean {
	
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public HelloBean(String message) {
		super();
		this.message = message;
		System.err.println("[LOG] Creating Model..");
	}

	public HelloBean() {
		super();
	}

	@Override
	public String toString() {
		return  message;
	}
	
	

}
