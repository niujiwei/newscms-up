package com.jy.model;


public class Role {
	private int id;
	private String name;
	private String ctime;
	private String ltime;
	private String remarks;
	
	public Role() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public Role(String name, String ctime, String ltime, String remarks) {
		super();
		this.name = name;
		this.ctime = ctime;
		this.ltime = ltime;
		this.remarks = remarks;
	}


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCtime() {
		if(ctime!=null){
			ctime=ctime.substring(0,19);
		}
		return ctime;
	}
	public void setCtime(String ctime) {
		this.ctime = ctime;
	}
	public String getLtime() {
		if(ltime!=null){
			ltime=ltime.substring(0,19);
		}
		return ltime;
	}
	public void setLtime(String ltime) {
		this.ltime = ltime;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
}
