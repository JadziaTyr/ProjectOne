package com.revature.database;

public class HelloDAO implements Dao {
	//SQL
	public void create(Object o){
		//jdbc
		System.err.println("[LOG] DAO - Object is saved to database");
	}
	public void getbyid(int id){}
	public void getall(int start, int end){}
	public void update(Object o){}
	public Object getReimbursemntsByStatus(String status){
		return null;
	}
}
