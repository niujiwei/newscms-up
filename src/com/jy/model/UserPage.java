package com.jy.model;

public class UserPage {
	private String page;//页数
	private String rows;//行数
	private String user_name;//用户名
	private String user_realName;//真实姓名
	private String department_id;//部门id
	private String flag;//标志
	
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	public String getRows() {
		return rows;
	}
	public void setRows(String rows) {
		this.rows = rows;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getUser_realName() {
		return user_realName;
	}
	public void setUser_realName(String user_realName) {
		this.user_realName = user_realName;
	}
	public String getDepartment_id() {
		return department_id;
	}
	public void setDepartment_id(String department_id) {
		this.department_id = department_id;
	}

}
