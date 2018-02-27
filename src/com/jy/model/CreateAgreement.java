package com.jy.model;

public class CreateAgreement {
	//协议制作基本表
	private String agreement_id;//协议id
	private String agreement_number;//协议编号（自动生成）
	private Integer agreement_type;//协议类型（0、班线 1、配送 2、转运）
	private String ndp_id;//托运机构id
	private String ldp_id;//下一机构id
	private String car_number1;//车牌号
	private String car_id;//车辆id
	private String car_name;//司机姓名
	private String all_money;//总运费(所有订单运费合计)
	private String phone_number;//联系方式
	private String remarks_send;//备注
	private String create_time;//协议制作时间
	private String create_user;//协议制作者
	private String send_type;
	private String end_address;
	private Integer order_state;//订单状态
	private Integer isagain;//是否被接受过
	private String company_id;//中转公司的id
	private String company_name;
	private String end_people;
	private String end_phone;
	private String ship_order_id;//订单id
	private String shiping_order_id;//
	private String company_number;//转运单号
	private Integer chanage_pay_type;//中转付款方式（0.回付，1.现付，2.到付）
	private String company_tel;//中专公司联系方式
	private String send_date;//配送时间
	
	public String getSend_date() {
		return send_date;
	}
	public void setSend_date(String send_date) {
		this.send_date = send_date;
	}
	public String getCompany_tel() {
		return company_tel;
	}
	public void setCompany_tel(String company_tel) {
		this.company_tel = company_tel;
	}
	public Integer getChanage_pay_type() {
		return chanage_pay_type;
	}
	public void setChanage_pay_type(Integer chanage_pay_type) {
		this.chanage_pay_type = chanage_pay_type;
	}
	public String getShiping_order_id() {
		return shiping_order_id;
	}
	public void setShiping_order_id(String shiping_order_id) {
		this.shiping_order_id = shiping_order_id;
	}
	public String getShip_order_id() {
		return ship_order_id;
	}
	public void setShip_order_id(String ship_order_id) {
		this.ship_order_id = ship_order_id;
	}
	public String getCompany_name() {
		return company_name;
	}
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
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
	public String getCompany_id() {
		return company_id;
	}
	public void setCompany_id(String company_id) {
		this.company_id = company_id;
	}
	public Integer getIsagain() {
		return isagain;
	}
	public void setIsagain(Integer isagain) {
		this.isagain = isagain;
	}
	public Integer getOrder_state() {
		return order_state;
	}
	public void setOrder_state(Integer order_state) {
		this.order_state = order_state;
	}
	private Integer number;//订单个数
	
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	
	public String getSend_type() {
		return send_type;
	}
	public void setSend_type(String send_type) {
		this.send_type = send_type;
	}
	public String getEnd_address() {
		return end_address;
	}
	public void setEnd_address(String end_address) {
		this.end_address = end_address;
	}
	public String getAgreement_id() {
		return agreement_id;
	}
	public void setAgreement_id(String agreement_id) {
		this.agreement_id = agreement_id;
	}
	public String getAgreement_number() {
		return agreement_number;
	}
	public void setAgreement_number(String agreement_number) {
		this.agreement_number = agreement_number;
	}
	public Integer getAgreement_type() {
		return agreement_type;
	}
	public void setAgreement_type(Integer agreement_type) {
		this.agreement_type = agreement_type;
	}
	public String getNdp_id() {
		return ndp_id;
	}
	public void setNdp_id(String ndp_id) {
		this.ndp_id = ndp_id;
	}
	public String getLdp_id() {
		return ldp_id;
	}
	public void setLdp_id(String ldp_id) {
		this.ldp_id = ldp_id;
	}

	public String getCar_number1() {
		return car_number1;
	}
	public void setCar_number1(String car_number1) {
		this.car_number1 = car_number1;
	}
	public String getCar_id() {
		return car_id;
	}
	public void setCar_id(String car_id) {
		this.car_id = car_id;
	}
	public String getCar_name() {
		return car_name;
	}
	public void setCar_name(String car_name) {
		this.car_name = car_name;
	}
	public String getAll_money() {
		return all_money;
	}
	public void setAll_money(String all_money) {
		this.all_money = all_money;
	}
	public String getPhone_number() {
		return phone_number;
	}
	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}
	public String getRemarks_send() {
		return remarks_send;
	}
	public void setRemarks_send(String remarks_send) {
		this.remarks_send = remarks_send;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getCreate_user() {
		return create_user;
	}
	public void setCreate_user(String create_user) {
		this.create_user = create_user;
	}
	public String getCompany_number() {
		return company_number;
	}
	public void setCompany_number(String company_number) {
		this.company_number = company_number;
	}
	
}
