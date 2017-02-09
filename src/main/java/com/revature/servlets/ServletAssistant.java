package com.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.extensions.MyException;
import com.revature.models.Reimbursement;
import com.revature.models.ReimbursementStatus;
import com.revature.models.ReimbursementType;
import com.revature.models.Role;
import com.revature.models.User;
import com.revature.services.Logic;

public class ServletAssistant
{
	public String correctHomepage(HttpServletRequest req, HttpServletResponse resp)
	{
		if (req.getParameter("loginButton") != null)
		{
			return "login";
		}

		return "Login.html";
	}

	public String correctInsecureNavigation(HttpServletRequest req, HttpServletResponse resp)
	{
		if (null != req.getHeader("x-requested-with"))
		{
			// AJAX REQUEST
			if (req.getParameter("pictureId") != null)
			{
				Reimbursement re = new Reimbursement(Integer.parseInt(req.getParameter("pictureId")));
				Logic.getLogic().popUpImage(re);
				String receiptString = new String(Base64.encodeBase64(re.getImage()));

				PrintWriter writer;
				try
				{
					writer = resp.getWriter();
					writer.write(receiptString);
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
			if (req.getParameter("newUser") != null)
			{
				PrintWriter writer;
				try
				{
					writer = resp.getWriter();

					if (this.newUserCreation(req))
					{
						writer.write("Creation Successful");
					}
					else
					{
						writer.write("Creation Failed");
					}
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
			return null;
		}
		else // not ajax request
		{
			System.out.println("NOT AJAX");

			if (req.getParameter("logout") != null)
			{
				return logoutUser(req);
			}
			else if (req.getParameter("edit") != null)
			{
				return fetchProfile(req);
			}
			else if (req.getParameter("viewMyRequests") != null)
			{
				req.getSession().setAttribute("list",
						Logic.getLogic().getUserReimbursementRequestList((User) req.getSession().getAttribute("user")));
				return "ViewMyRequests.jsp";
			}
			else if (req.getParameter("submitRequest") != null)
			{
				HttpSession session = req.getSession();

				// put in list of reimbursement types
				if (session != null)
				{
					session.setAttribute("reimbursementTypes", Logic.getLogic().getTypeList());
					return "SubmitRequest.jsp";
				}
			}
			else if (req.getParameter("viewEmployees") != null)
			{
				return setUpEmployeeListView(req);
			}
			else if (req.getParameter("viewAllRequests") != null)
			{
				return prepRequestsFromAllUsers(req);
			}
			else if (req.getParameter("homepage") != null)
			{
				return "Homepage.jsp";
			}
			else if (req.getParameter("createNewUser") != null)
			{
				req.getSession().setAttribute("userRoles", Logic.getLogic().getRoleList());
				return "NewUser.jsp";
			}
		}
		return "error.html";
	}

	public String correctSecureNavigation(HttpServletRequest req, HttpServletResponse resp)
	{
		if (null != req.getHeader("x-requested-with"))
		{
			if (req.getParameter("editUser") != null)
			{
				PrintWriter writer;
				try
				{
					writer = resp.getWriter();

					if (this.setUpEditProfileSave(req))
					{
						writer.write("Edit Successful");
					}
					else
					{
						writer.write("Edit Failed");
					}
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
			else if(req.getParameter("resetEmail") != null)
			{
				PrintWriter writer;
				try
				{
					writer = resp.getWriter();

					if (this.userReset(req))
					{
						writer.write("Email Successful");
					}
					else
					{
						writer.write("Email Failed");
					}
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}			}
			return null;
		}
		else
		{
			if (req.getParameter("submitRequestButton") != null || ServletFileUpload.isMultipartContent(req))
			{
				return setUpSingleUserRequestView(req);
			}
			if (req.getParameter("submitResolvedRequests") != null)
			{
				return saveUpdatedRequests(req);
			}
		}
		return "Login.html";
	}

	private boolean userReset(HttpServletRequest req)
	{
		//if this user exists
		if(Logic.getLogic().emailIsValid(req.getParameter("resetEmail")))
		{
			return true;
		}
		return false;
	}

	private boolean newUserCreation(HttpServletRequest req)
	{
		ObjectMapper mapper = new ObjectMapper();

		try
		{
			User user = mapper.readValue(req.getParameter("newUser"), User.class);
			user.setPersonRole(new Role(Integer.parseInt(req.getParameter("role"))));

			if (Logic.getLogic().createUser(user))
			{
				return true;
			}

		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	private String saveUpdatedRequests(HttpServletRequest req)
	{

		List<Reimbursement> list = (List<Reimbursement>) req.getSession().getAttribute("reimbursementList");

		List<Reimbursement> updateList = new ArrayList<Reimbursement>();

		for (int i = 0; i < list.size(); i++)
		{
			if (req.getParameter("" + list.get(i).getId()) != null)
			{
				// if changed
				if (Logic.getLogic().reimbursementStatusChanged(list.get(i).getId(),
						req.getParameter("" + list.get(i).getId())))
				{
					System.out.println("changed " + list.get(i).getDescription());
					// save changes

					list.get(i).setReimbursementStatus(
							new ReimbursementStatus(req.getParameter("" + list.get(i).getId())));
					list.get(i).getApprover().setId(((User) req.getSession().getAttribute("user")).getId());
					// put in list to be changed
					updateList.add(list.get(i));
				}
			}
		}

		try
		{
			if (Logic.getLogic().saveBatchOfReimbursements(updateList))
			{
				return this.prepRequestsFromAllUsers(req);
			}
		}
		catch (MyException e)
		{
			e.printStackTrace();
		}

		return "error.html";
	}

	public String prepRequestsFromAllUsers(HttpServletRequest req)
	{
		System.out.println("in prep requests");
		List<Reimbursement> list = new ArrayList<Reimbursement>();

		if (Logic.getLogic().retrieveAllReimbursements(list))
		{
			System.out.println("after retrieval");
			req.getSession().setAttribute("reimbursementList", list);
			return "ViewAllRequests.jsp"; // link to servlet instead
		}
		return "error.html";
	}

	private String fetchProfile(HttpServletRequest req)
	{
		if (req.getSession() != null)
		{
			if (req.getSession().getAttribute("user") != null)
			{
				return "EditProfile.jsp";
			}
		}

		return "Login.html";
	}

	public String logoutUser(HttpServletRequest req)
	{
		System.out.println("Logout");

		HttpSession session = req.getSession();
		session.invalidate();

		return "Login.html";
	}

	public String setUpEmployeeListView(HttpServletRequest req)
	{
		List<User> list = new ArrayList<User>();
		if (Logic.getLogic().getAllEmployees(list))
		{
			req.getSession().setAttribute("employeeList", null);
			req.getSession().setAttribute("employeeList", list);
			return "ViewAllEmployees.jsp"; // link to servlet instead
		}
		return "error.html";
	}

	private boolean setUpEditProfileSave(HttpServletRequest req)
	{
		User person = (User) req.getSession().getAttribute("user");

		ObjectMapper mapper = new ObjectMapper();

		try
		{
			System.out.println(req.getParameter("editUser"));
			User user = mapper.readValue(req.getParameter("editUser"), User.class);
			user.setId(person.getId());
			user.setPersonRole(person.getPersonRole());
			
			System.out.println(person.toString());
			System.out.println(user.toString());
			
			// connect to database and save
			if (Logic.getLogic().editEmployeeProfile(user))
			{
				//update the user to the newly saved version
				req.getSession().setAttribute("user", user);
				return true;
			}

			return false;			
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public String setUpSingleUserRequestView(HttpServletRequest req)
	{
		if (ServletFileUpload.isMultipartContent(req)) // if submitting a
														// request
		{
			return readInNewRequest(req);
		}
		else // if not submitting a request just go to the logged in person's
				// page
		{
			if (!Logic.getLogic().getUserReimbursementRequestList(((User) req.getSession().getAttribute("user"))))
			{
				return "error.html";
			}
		}
		return "ViewAllRequests.jsp";
	}

	public String readInNewRequest(HttpServletRequest req)
	{
		System.out.println("New Request");
		Reimbursement re = new Reimbursement();
		try
		{
			List<FileItem> items = new ServletFileUpload((new DiskFileItemFactory())).parseRequest(req);

			int lcv = 0;

			while (lcv < items.size())
			{
				FileItem item = items.get(lcv);
				if (item.isFormField())
				{
					if (item.getFieldName().equals("amount"))
					{
						re.setAmount(Double.parseDouble(item.getString()));
					}
					else if (item.getFieldName().equals("description"))
					{
						re.setDescription(item.getString());
					}
					else if (item.getFieldName().equals("types"))
					{
						re.setReimbursementType(
								(ReimbursementType) Logic.getLogic().getReimbursementTypeByType(item.getString()));
					}
				}
				else
				{
					re.setImage(item.get());
				}
				lcv++;
			}

			System.out.println("Type " + re.getReimbursementType().getType());
			re.setRequester((User) req.getSession().getAttribute("user"));
			if (!Logic.getLogic().newRequestSubmitted(((User) req.getSession().getAttribute("user")), re))
			{
				throw new Exception();
			}
			System.out.println(re.toString());
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			return "error.html";
		}
		return "ViewMyRequests.jsp";
	}
}
