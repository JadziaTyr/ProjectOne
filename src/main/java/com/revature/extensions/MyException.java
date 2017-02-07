package com.revature.extensions;

public class MyException extends Exception
{
	private static final long serialVersionUID = 1L;
	
	public MyException()
	{
		super("Something is wrong!");
	}

}
