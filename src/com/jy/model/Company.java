package com.jy.model;

public class Company {
	
	/**
	 *  中转公司维护bean
	 *  费家震
	 */
	
	private String company_id;   //  id
	
	private  String company_name;  // 中转公司（承运公司）    
	
	private  String company_time; //  创建时间
	
	private  String phone; //  电话
	
	private  String start_people; //  起始站联系人
	
	private  String start_phone; //  起始站电话	
	
	private  String end_people; //  到达站联系人
	
	private  String end_phone; //  到达站联系电话
	
	private String company_area;// 承运地址
	
	private String start_area;// 起始站地址
	
	private String end_area;// 到货点地址
	
	
	public String getCompany_area() {
		return company_area;
	}

	public void setCompany_area(String company_area) {
		this.company_area = company_area;
	}

	public String getStart_area() {
		return start_area;
	}

	public void setStart_area(String start_area) {
		this.start_area = start_area;
	}

	public String getEnd_area() {
		return end_area;
	}

	public void setEnd_area(String end_area) {
		this.end_area = end_area;
	}

	public Company(){}
	
	public Company(String company_id, String company_name, String company_time,
			String phone, String start_people, String start_phone,
			String end_people, String end_phone) {
		super();
		this.company_id = company_id;
		this.company_name = company_name;
		this.company_time = company_time;
		this.phone = phone;
		this.start_people = start_people;
		this.start_phone = start_phone;
		this.end_people = end_people;
		this.end_phone = end_phone;
	}

	public String getCompany_id() {
		return company_id;
	}

	public void setCompany_id(String company_id) {
		this.company_id = company_id;
	}

	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

	public String getCompany_time() {
		return company_time;
	}

	public void setCompany_time(String company_time) {
		this.company_time = company_time;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getStart_people() {
		return start_people;
	}

	public void setStart_people(String start_people) {
		this.start_people = start_people;
	}

	public String getStart_phone() {
		return start_phone;
	}

	public void setStart_phone(String start_phone) {
		this.start_phone = start_phone;
	}

	public String getEnd_people() {
		return end_people;
	}

	public void setEnd_people(String end_people) {
		this.end_people = end_people;
	}

	public String getEnd_phone() {
		return end_phone;
	}

	public void setEnd_phone(String end_phone) {
		this.end_phone = end_phone;
	}
	
	
}
