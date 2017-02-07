package com.revature.services;

import com.revature.beans.HelloBean;
import com.revature.database.HelloDAO;
/*
 * Services are good for transactions
 */
public class DataService {

	public void saveHelloBean(HelloBean model) {
		
		System.err.println("[LOG] Calling Business logic component");
		//Create Connection
		//Turn off AutoCommit
		//perform any log needed before saving to the database
		//call dao methods for transaction
		new HelloDAO().create(model);
		//commit()
		
	}

}
