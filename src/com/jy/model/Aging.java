package com.jy.model;

public class Aging {
	private String aging_id;
	private String aging_cutomer_id;// 客户id
	private String cutomer_name;// 客户名称
	private Integer aging_province;// 终到位置(省)id
	private String aging_provincename;// 终到位置(省)
	private Integer aging_city;// 终到位置(城市)id
	private String aging_cityname;// 终到位置(城市)
	private Integer aging_county;// 终到位置(县区)id
	private String aging_countyname;// 终到位置(县区)
	private String aging_address;// 终到位置
	private String aging_time;// 标准时效
	private String aging_day;// 标准时效日期 0:今日；1:次日
	private String aging_operator;// 操作人
	private String aging_operator_date;// 操作日期
	private String star_time;// 触发开始时间
	private String end_time;// 触发截止时间

	public String getStar_time() {
		return star_time;
	}

	public void setStar_time(String star_time) {
		this.star_time = star_time;
	}

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

	public String getAging_day() {
		return aging_day;
	}

	public void setAging_day(String aging_day) {
		this.aging_day = aging_day;
	}

	public String getCutomer_name() {
		return cutomer_name;
	}

	public void setCutomer_name(String cutomer_name) {
		this.cutomer_name = cutomer_name;
	}

	public String getAging_operator() {
		return aging_operator;
	}

	public void setAging_operator(String aging_operator) {
		this.aging_operator = aging_operator;
	}

	public String getAging_operator_date() {
		return aging_operator_date;
	}

	public void setAging_operator_date(String aging_operator_date) {
		this.aging_operator_date = aging_operator_date;
	}

	public String getAging_id() {
		return aging_id;
	}

	public void setAging_id(String aging_id) {
		this.aging_id = aging_id;
	}

	public String getAging_cutomer_id() {
		return aging_cutomer_id;
	}

	public void setAging_cutomer_id(String aging_cutomer_id) {
		this.aging_cutomer_id = aging_cutomer_id;
	}

	public Integer getAging_province() {
		return aging_province;
	}

	public void setAging_province(Integer aging_province) {
		this.aging_province = aging_province;
	}

	public String getAging_provincename() {
		return aging_provincename;
	}

	public void setAging_provincename(String aging_provincename) {
		this.aging_provincename = aging_provincename;
	}

	public Integer getAging_city() {
		return aging_city;
	}

	public void setAging_city(Integer aging_city) {
		this.aging_city = aging_city;
	}

	public String getAging_cityname() {
		return aging_cityname;
	}

	public void setAging_cityname(String aging_cityname) {
		this.aging_cityname = aging_cityname;
	}

	public Integer getAging_county() {
		return aging_county;
	}

	public void setAging_county(Integer aging_county) {
		this.aging_county = aging_county;
	}

	public String getAging_countyname() {
		return aging_countyname;
	}

	public void setAging_countyname(String aging_countyname) {
		this.aging_countyname = aging_countyname;
	}

	public String getAging_address() {
		return aging_address;
	}

	public void setAging_address(String aging_address) {
		this.aging_address = aging_address;
	}

	public String getAging_time() {
		return aging_time;
	}

	public void setAging_time(String aging_time) {
		this.aging_time = aging_time;
	}

}
