package com.revature.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.beans.HelloBean;
import com.revature.services.BusinessDelegate;

/**
 * 
 * """""Controller"""""
 *
 */
public class RequestHelper
{

	public String process(HttpServletRequest req, HttpServletResponse resp)
	{
		System.err.println("[LOG] Processing request with helper : " + req.getRequestURI());

		switch (req.getRequestURI())
		{
		case "/FrontController/hello.do":
		{
			// one thing
			String message = req.getParameter("hello");
			// 1. validate the input
			if (validateHelloForm(req, message) == false)
			{
				// STOP right there.. quit haxing.
				return "index.jsp";
			}
			// 2. Beanify the input
			HelloBean model = new HelloBean(message);
			// 3. Call business delegate
			new BusinessDelegate().saveHelloBean(model);
			// 4. persist info (req, session, application variables)
			req.getSession().setAttribute("hello", model);
			System.err.println("[LOG] Processing complete.. redirecting");
			return "hello.jsp";
		}
		case "/FrontController/goodbye.do":
		{
			// another thing
			String message = req.getParameter("goodbye");
			HelloBean model = new HelloBean(message);
			new BusinessDelegate().saveHelloBean(model);
			req.getSession().setAttribute("goodbye", model);
			return "goodbye.jsp";
		}
		default:
		{
			throw new IllegalArgumentException("Not a valid URI");
		}
		}
	}

	private boolean validateHelloForm(HttpServletRequest req, String field1)
	{
		System.err.println("[LOG] Validating input..");
		if (field1.length() > 100)
		{
			req.setAttribute("error", "Hello message too long!!!");
			return false;
		}
		return true;
	}

}
