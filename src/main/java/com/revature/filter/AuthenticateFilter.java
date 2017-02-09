package com.revature.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.models.User;
import com.revature.services.Logic;
import com.revature.servlets.ServletAssistant;

public class AuthenticateFilter implements Filter
{
	public void destroy()
	{

	}

	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException
	{
		// make sure I get here
		System.out.println("Authenticating");

		HttpServletRequest request = (HttpServletRequest) req; // must cast to get session
		HttpServletResponse response = (HttpServletResponse) resp; //must cast to get chain forward
		HttpSession session = request.getSession();
		boolean logged_in = false;
		
		if (session != null) 
		{
			Object flag = session.getAttribute("flag");
			if (flag != null) //authState is a flag
			{
				request.setAttribute("user", flag);
				logged_in = true;
			}
		}
		if(request.getRequestURI().contains("login_master.do"))
		{
			String page = new ServletAssistant().correctHomepage(request, response);
			
			if(page != "Login.html")
			{
				String username = (String) request.getParameter("username");
				String password = (String) request.getParameter("password");
								
				User user = new User(username, password);
				
				logged_in = Logic.getLogic().login(user);
								
				if(logged_in)
				{
					req.setAttribute("user", user);
				}
			}
		}
		
		if (null != request.getHeader("x-requested-with"))
		{
			logged_in = true;
		}
		
		if (logged_in)
		{
			request.getRequestDispatcher("master.do").forward(request, response);
		}
		else
		{
			response.sendRedirect("Login.html");
		}
	}

	public void init(FilterConfig arg0) throws ServletException
	{

	}
}
