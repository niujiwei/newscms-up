package com.jy.model;

import java.util.List;

public class User {
	private String id;// id
	private String username;// 姓名
	private String password;// 密码
	private String realName;// 真是姓名
	private String did;// 部门id
	private String lastTime;// 最后登陆时间
	private String state;// 0是失效1是有效
	private String flag;// 1是司机0是其他,2供应商
	private String temporary_car_id;// 临时车辆id
	private String plate_number;// 车牌号
	private String travel_card_id;// 固定车辆id
	private String channelId;// 设备id
	private String device_type;// 设备类型
	private String driver_id;// 司机id
	private String driver_name;//司机姓名
	private String department_name;// 部门名字
	private String suppliers_id;// 供应商id
	private String suppliers_name;// 商名称
	private Integer user_province;// 所属大区省
	private Integer user_city;// 所属大区市
	private Integer user_county;// 所属大区区
	private String user_province_name;// 所属大区省
	private String user_city_name;// 所属大区市
	private String user_county_name;// 所属大区区
	private String user_address;// 所属大区位置
	private String createtime;
	private String customer_id;// 发客户id
	private String customer_name;// 发货客户姓名
	private String userPhone;// 用户电话
	private String suppliers_phone;// 供应商电话
	private String driver_phone;// 司机电话
	private String tel;// 项目部电话
	private String customer_tel;// 客户电话
	private String drivername;
	private List<String> userAppRole;// APP登陆权限

	
	public String getDriver_name() {
		return driver_name;
	}

	public void setDriver_name(String driver_name) {
		this.driver_name = driver_name;
	}

	public List<String> getUserAppRole() {
		return userAppRole;
	}

	public void setUserAppRole(List<String> userAppRole) {
		this.userAppRole = userAppRole;
	}

	public String getDrivername() {
		return drivername;
	}

	public void setDrivername(String drivername) {
		this.drivername = drivername;
	}

	public String getCustomer_tel() {
		return customer_tel;
	}

	public void setCustomer_tel(String customer_tel) {
		this.customer_tel = customer_tel;
	}

	public String getDriver_phone() {
		return driver_phone;
	}

	public void setDriver_phone(String driver_phone) {
		this.driver_phone = driver_phone;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getSuppliers_phone() {
		return suppliers_phone;
	}

	public void setSuppliers_phone(String suppliers_phone) {
		this.suppliers_phone = suppliers_phone;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getCustomer_name() {
		return customer_name;
	}

	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}

	public String getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(String customer_id) {
		this.customer_id = customer_id;
	}

	public String getUser_province_name() {
		return user_province_name;
	}

	public void setUser_province_name(String user_province_name) {
		this.user_province_name = user_province_name;
	}

	public String getUser_city_name() {
		return user_city_name;
	}

	public void setUser_city_name(String user_city_name) {
		this.user_city_name = user_city_name;
	}

	public String getUser_county_name() {
		return user_county_name;
	}

	public void setUser_county_name(String user_county_name) {
		this.user_county_name = user_county_name;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getSuppliers_name() {
		return suppliers_name;
	}

	public void setSuppliers_name(String suppliers_name) {
		this.suppliers_name = suppliers_name;
	}

	public String getSuppliers_id() {
		return suppliers_id;
	}

	public void setSuppliers_id(String suppliers_id) {
		this.suppliers_id = suppliers_id;
	}

	public Integer getUser_province() {
		return user_province;
	}

	public void setUser_province(Integer user_province) {
		this.user_province = user_province;
	}

	public Integer getUser_city() {
		return user_city;
	}

	public void setUser_city(Integer user_city) {
		this.user_city = user_city;
	}

	public Integer getUser_county() {
		return user_county;
	}

	public void setUser_county(Integer user_county) {
		this.user_county = user_county;
	}

	public String getUser_address() {
		return user_address;
	}

	public void setUser_address(String user_address) {
		this.user_address = user_address;
	}

	public String getDepartment_name() {
		return department_name;
	}

	public void setDepartment_name(String department_name) {
		this.department_name = department_name;
	}

	public String getDriver_id() {
		return driver_id;
	}

	public void setDriver_id(String driver_id) {
		this.driver_id = driver_id;
	}

	public String getDevice_type() {
		return device_type;
	}

	public void setDevice_type(String device_type) {
		this.device_type = device_type;
	}

	// private String department_name;
	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getTemporary_car_id() {
		return temporary_car_id;
	}

	public void setTemporary_car_id(String temporary_car_id) {
		this.temporary_car_id = temporary_car_id;
	}

	public String getPlate_number() {
		return plate_number;
	}

	public void setPlate_number(String plate_number) {
		this.plate_number = plate_number;
	}

	public String getTravel_card_id() {
		return travel_card_id;
	}

	public void setTravel_card_id(String travel_card_id) {
		this.travel_card_id = travel_card_id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getDid() {
		return did;
	}

	public void setDid(String did) {
		this.did = did;
	}

	public String getLastTime() {
		if (lastTime != null) {
			lastTime = lastTime.substring(0, 19);
		}
		return lastTime;
	}

	public void setLastTime(String lastTime) {
		this.lastTime = lastTime;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

}
