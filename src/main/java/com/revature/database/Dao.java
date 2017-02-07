package com.revature.database;

public interface Dao {

	public void create(Object o);

	public void getbyid(int id);

	public void getall(int start, int end);

	public void update(Object o);

	public Object getReimbursemntsByStatus(String status);

}
