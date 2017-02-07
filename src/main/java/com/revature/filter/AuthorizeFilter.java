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

import com.revature.models.User;
import com.revature.services.Logic;

public class AuthorizeFilter implements Filter
{
	@Override
	public void init(FilterConfig arg0) throws ServletException {}
	
	@Override
	public void destroy() {}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException
	{
		System.out.println("Authorize filter");
		HttpServletRequest request = (HttpServletRequest) req; // must cast to get session
		HttpServletResponse response = (HttpServletResponse) resp; //must cast to get chain forward
		
		boolean noUser = true;
		if(request.getSession() != null)
		{
			if(request.getSession().getAttribute("user") != null)
			{
				User user = (User)request.getSession().getAttribute("user");
				if(request.getRequestURI().contains("ViewRequests") 
						&& !(user.getPersonRole().getId() == Logic.MANAGER_ID))
				{
					request.getRequestDispatcher("error.html").forward(request, response);
				}
				else if(request.getRequestURI().contains("ViewAllEmployees") && !(user.getPersonRole().getId() == Logic.MANAGER_ID))
				{
					request.getRequestDispatcher("error.html").forward(request, response);
				}
				else
				{
					chain.doFilter(req, resp);
				}
				noUser = false;
			}
		}
		
		if(noUser)
		{			
			request.getRequestDispatcher("Login.html").forward(request, response);
		}
	}
}
