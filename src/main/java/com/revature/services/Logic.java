package com.revature.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.revature.extensions.EmailReimbursementResolvedRunnable;
import com.revature.extensions.MyException;
import com.revature.extensions.UserRunnable;
import com.revature.models.Reimbursement;
import com.revature.models.ReimbursementStatus;
import com.revature.models.ReimbursementType;
import com.revature.models.Role;
import com.revature.models.User;

public class Logic
{
	public final Random RANDOM;
	public final static int MANAGER_ID = 2;
	public final static int EMPLOYEE_ID = 1;

	private static Logic logic; // the only logical instance

	private List<ReimbursementType> typeList;
	private List<ReimbursementStatus> statusList;
	private List<Role> roleList;

	private Logic()
	{
		typeList = new ArrayList<ReimbursementType>();
		statusList = new ArrayList<ReimbursementStatus>();
		roleList = new ArrayList<Role>();
		RANDOM = new Random();
		readLookUpTables(typeList, statusList, roleList);
	}

	private void readLookUpTables(List<ReimbursementType> typeList, List<ReimbursementStatus> statusList,
			List<Role> roleList)
	{
		new TransactionService().initialReadTransaction(typeList, statusList, roleList);
	}

	public boolean login(User person)
	{
		return new TransactionService().loginTransaction(person);
	}

	public boolean editEmployeeProfile(User person)
	{
		return new TransactionService().updatePersonTransaction(person);
	}

	public boolean newRequestSubmitted(User person, Reimbursement re)
	{
		if (new TransactionService().newReimbursmentTransaction(person, re))
		{
			person.getReimbursementList().clear();
			return this.getUserReimbursementRequestList(person);
		}
		return false;
	}

	public boolean getAllEmployees(List<User> list)
	{
		if (new TransactionService().fetchUserList(list))
		{
			for (User user : list)
			{
				user.setPersonRole(this.getRoleById(user.getId()));
				user.setPendingCount(this.getCountOfPending(user));
				user.setResolvedCount(this.getCountOfResolved(user));
			}
			return true;
		}
		return false;
	}

	public boolean retrieveAllReimbursements(List<Reimbursement> list)
	{
		if (new TransactionService().fetchReimbursementList(list))
		{
			int lcv = 0;
			while (lcv < list.size())
			{
				Reimbursement re = list.get(lcv);
				ReimbursementType type = re.getReimbursementType();
				ReimbursementStatus status = re.getReimbursementStatus();

				re.setReimbursementStatus(this.getReimbursementStatusById(status.getId()));
				re.setReimbursementType(this.getReimbursementTypeById(type.getId()));
				lcv++;
			}
			return true;
		}
		return false;
	}

	public boolean getUserReimbursementRequestList(User person)
	{
		person.getReimbursementList().clear();
		if (new TransactionService().fetchUserReimbursementList(person))
		{
			int lcv = 0;
			while (lcv < person.getReimbursementList().size())
			{
				Reimbursement re = person.getReimbursementList().get(lcv);
				ReimbursementType type = re.getReimbursementType();
				ReimbursementStatus status = re.getReimbursementStatus();

				re.setReimbursementStatus(this.getReimbursementStatusById(status.getId()));
				re.setReimbursementType(this.getReimbursementTypeById(type.getId()));
				lcv++;
			}
			return true;
		}
		return false;
	}

	public boolean saveBatchOfReimbursements(List<Reimbursement> list) throws MyException
	{
		for (Reimbursement re : list)
		{
			re.setReimbursementStatus(this.getReimbursementStatusByStatus(re.getReimbursementStatus().getStatus()));
		}

		if (new TransactionService().updateReimbursementList(list))
		{
			(new Thread(new EmailReimbursementResolvedRunnable(list))).start();
			return true;
		}
		return false;
	}

	public boolean createUser(User user)
	{
		//change to randomized
		user.setUserName(this.getRandomString(10));
		user.setPassword(this.getRandomString(8));
		user.setPersonRole(this.getRoleById(user.getPersonRole().getId()));
		
		if(new TransactionService().newUser(user))
		{
			(new Thread(new UserRunnable(user))).start();
			return true;
		}
		
		return false;
	}
	
	private String getRandomString(int length)
	{
		//numbers 48-57
		//U characters 65-90
		//L characters 97-122
		String choices = "";
		int lcv = 48;
		
		while(lcv<=122)
		{
			choices += (char) lcv;
			
			if(lcv == 57)
			{
				lcv = 65;
			}
			else if(lcv == 90)
			{
				lcv = 97;
			}
			else
			{
				lcv++;
			}
		}
		
		String use = "";
		lcv = 0;
		while(lcv < length)
		{
			use += choices.charAt(RANDOM.nextInt(choices.length()));
			lcv++;
		}
		
		return use;
	}

	public boolean getEmployeeById(User person)
	{
		if(new TransactionService().fetchUserById(person))
		{
			return true;
		}
		return false;
	}
	
	public boolean reimbursementStatusChanged(int id, String parameter)
	{
		Reimbursement re = new Reimbursement(id);

		// fetch reimbursement by id
		if (new TransactionService().fetchReimbursementById(re))
		{
			re.setReimbursementStatus(this.getReimbursementStatusById(re.getReimbursementStatus().getId()));
			// if the reimbursement status has changed
			if (parameter.equals(re.getReimbursementStatus().getStatus()))
			{
				return false;
			}
		}
		return true;
	}

	public boolean popUpImage(Reimbursement re)
	{
		if (new TransactionService().fetchReimbursementById(re))
		{
			return true;
		}
		return false;

	}

	public boolean emailIsValid(String email)
	{
		User user = new User();
		user.setEmail(email);
		if(new TransactionService().fetchUserByEmail(user))
		{
			user.setUserName(this.getRandomString(10));
			user.setPassword(this.getRandomString(8));
			if(new TransactionService().updatePersonTransaction(user))
			{
				(new Thread(new UserRunnable(user))).start();
				return true;
			}
		}
		
		return false;
	}
	
	private int getCountOfPending(User person)
	{
		System.out.println(person.toString());
		System.out.println(this.getReimbursementStatusByStatus("Pending").toString());
		return new TransactionService().countStatus(person, this.getReimbursementStatusByStatus("Pending"));
	}

	private int getCountOfResolved(User person)
	{
		System.out.println(person.toString());
		System.out.println(this.getReimbursementStatusByStatus("Approved").toString());
		System.out.println(this.getReimbursementStatusByStatus("Denied").toString());

		return new TransactionService().countStatus(person, this.getReimbursementStatusByStatus("Approved"))
				+ new TransactionService().countStatus(person, this.getReimbursementStatusByStatus("Denied"));
	}

	private Role getRoleById(int id)
	{
		boolean found = false;

		Role role = new Role(id);
		int lcv = 0;
		while (!found && lcv < roleList.size())
		{
			role = roleList.get(lcv);
			if (role.getId() == id)
			{
				break;
			}
			lcv++;
		}

		return role;
	}

	public ReimbursementType getReimbursementTypeByType(String type)
	{
		boolean found = false;

		ReimbursementType reType = new ReimbursementType();
		int lcv = 0;
		while (!found && lcv < typeList.size())
		{
			reType = typeList.get(lcv);
			if (reType.getType().equals(type))
			{
				break;
			}
			lcv++;
		}

		return reType;
	}

	public ReimbursementType getReimbursementTypeById(int id)
	{
		boolean found = false;

		ReimbursementType reType = new ReimbursementType();
		int lcv = 0;
		while (!found && lcv < typeList.size())
		{
			reType = typeList.get(lcv);
			if (reType.getId() == id)
			{
				break;
			}
			lcv++;
		}

		return reType;
	}

	private ReimbursementStatus getReimbursementStatusByStatus(String status)
	{
		boolean found = false;

		ReimbursementStatus reStatus = new ReimbursementStatus();
		int lcv = 0;
		while (!found && lcv < statusList.size())
		{
			reStatus = statusList.get(lcv);
			if (reStatus.getStatus().equals(status))
			{
				break;
			}
			lcv++;
		}

		return reStatus;
	}

	private ReimbursementStatus getReimbursementStatusById(int id)
	{
		boolean found = false;

		ReimbursementStatus reStatus = new ReimbursementStatus();
		int lcv = 0;
		while (!found && lcv < statusList.size())
		{
			reStatus = statusList.get(lcv);
			if (reStatus.getId() == id)
			{
				break;
			}
			lcv++;
		}

		return reStatus;
	}

	// ACCESS
	public static Logic getLogic()
	{
		Object lock = new Object();
		synchronized (lock)
		{
			if (logic == null)
				logic = new Logic();
		}
		lock = null;
		return logic;
	}

	public List<ReimbursementType> getTypeList()
	{
		return typeList;
	}

	public List<ReimbursementStatus> getStatusList()
	{
		return statusList;
	}

	public List<Role> getRoleList()
	{
		return roleList;
	}
}
