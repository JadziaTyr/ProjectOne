package com.revature.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class MasterServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		System.out.println("Implement GET - MASTER");
		
		String page = new ServletAssistant().correctInsecureNavigation(req, resp);
				
		if(page != null)
		{
			req.getRequestDispatcher(page).forward(req, resp);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		System.out.println("Implement POST - MASTER");
				
		String page = new ServletAssistant().correctHomepage(req, resp);
				
		HttpSession session = req.getSession();
				
		if(!page.equals("Login.html"))
		{	
			// populating the session value
			session.setAttribute("flag", req.getAttribute("user"));
			session.setAttribute("user", req.getAttribute("user"));
			session.setAttribute("username", req.getParameter("username"));
			session.setAttribute("password", req.getParameter("password"));
		
			req.getRequestDispatcher("Homepage.jsp").forward(req, resp);
		}
		else
		{
			page = new ServletAssistant().correctSecureNavigation(req, resp);
						
			if(page != null)
			{
				resp.sendRedirect(page);
			}
		}
	}
}
