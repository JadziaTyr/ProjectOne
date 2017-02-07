package com.revature.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * Front Controller
 *
 */
public class MasterServlet extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		System.err.println("[LOG] Request sent to Front Controller");
		
		String nextPage = new RequestHelper().process(req, resp);
							//nextpage e.g. hello.jsp
		req.getRequestDispatcher(nextPage).forward(req, resp);
	}
}
