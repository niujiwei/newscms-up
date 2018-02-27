package com.jy.model;

import java.util.Date;
import java.util.List;

public class Driver {
	private String driver_id;// 司机ID
	private String driver_name;// 司机姓名
	private String driver_phone;// 手机号
	private String driver_cardnumber;// 身份证号
	private String driver_updatedate;// 更新日期
	private String driver_updatepeople;// 更新人
	private String driver_createtime;// 创建时间
	private String driver_suppliers;// 供应商id
	private String driver_suppliersname;// 供应商名字
	private Integer driver_province;// 终到位置(省)id
	private String driver_provincename;// 终到位置(省)
	private Integer driver_city;// 终到位置(城市)id
	private String driver_cityname;// 终到位置(城市)
	private Integer driver_county;// 终到位置(县区)id
	private String driver_countyname;// 终到位置(县区)
	private String driver_address;// 终到位置
	private String driver_remarks;// 司机备注
	private String id;
	private String driver_phonebrand;// 司机手机品牌
	private String driver_phonemodel;// 司机手机型号
	private String driver_carnumber;// 司机车牌号
	private String driver_cartype;// 司机车辆种类
	private String supplie_company;// 供应商公司名称
	private String is_supperlis;// 是否为供应商（1是，0否）
	private String userName;// 用户名
	private List<City_info> listCity_infos;// 省市县集合
	private int driver_sex;//0是女1是男
	private int driver_age;//司机年龄
	private String start_time;//开始配送时间

	public int getDriver_sex() {
		return driver_sex;
	}

	public void setDriver_sex(int driver_sex) {
		this.driver_sex = driver_sex;
	}

	public int getDriver_age() {
		return driver_age;
	}

	public void setDriver_age(int driver_age) {
		this.driver_age = driver_age;
	}

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getIs_supperlis() {
		return is_supperlis;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setIs_supperlis(String is_supperlis) {
		this.is_supperlis = is_supperlis;
	}

	public List<City_info> getListCity_infos() {
		return listCity_infos;
	}

	public void setListCity_infos(List<City_info> listCity_infos) {
		this.listCity_infos = listCity_infos;
	}

	public String getSupplie_company() {
		return supplie_company;
	}

	public void setSupplie_company(String supplie_company) {
		this.supplie_company = supplie_company;
	}

	private Integer[] driver_countys;

	public String getDriver_phonebrand() {
		return driver_phonebrand;
	}

	public void setDriver_phonebrand(String driver_phonebrand) {
		this.driver_phonebrand = driver_phonebrand;
	}

	public String getDriver_phonemodel() {
		return driver_phonemodel;
	}

	public void setDriver_phonemodel(String driver_phonemodel) {
		this.driver_phonemodel = driver_phonemodel;
	}

	public String getDriver_carnumber() {
		return driver_carnumber;
	}

	public void setDriver_carnumber(String driver_carnumber) {
		this.driver_carnumber = driver_carnumber;
	}

	public String getDriver_cartype() {
		return driver_cartype;
	}

	public void setDriver_cartype(String driver_cartype) {
		this.driver_cartype = driver_cartype;
	}

	public Integer[] getDriver_countys() {
		return driver_countys;
	}

	public void setDriver_countys(Integer[] driver_countys) {
		this.driver_countys = driver_countys;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDriver_remarks() {
		return driver_remarks;
	}

	public void setDriver_remarks(String driver_remarks) {
		this.driver_remarks = driver_remarks;
	}

	public String getDriver_address() {
		return driver_address;
	}

	public void setDriver_address(String driver_address) {
		this.driver_address = driver_address;
	}

	public String getDriver_id() {
		return driver_id;
	}

	public void setDriver_id(String driver_id) {
		this.driver_id = driver_id;
	}

	public String getDriver_name() {
		return driver_name;
	}

	public void setDriver_name(String driver_name) {
		this.driver_name = driver_name;
	}

	public String getDriver_phone() {
		return driver_phone;
	}

	public void setDriver_phone(String driver_phone) {
		this.driver_phone = driver_phone;
	}

	public String getDriver_cardnumber() {
		return driver_cardnumber;
	}

	public void setDriver_cardnumber(String driver_cardnumber) {
		this.driver_cardnumber = driver_cardnumber;
	}

	public String getDriver_updatedate() {
		return driver_updatedate;
	}

	public void setDriver_updatedate(String driver_updatedate) {
		this.driver_updatedate = driver_updatedate;
	}

	public String getDriver_updatepeople() {
		return driver_updatepeople;
	}

	public void setDriver_updatepeople(String driver_updatepeople) {
		this.driver_updatepeople = driver_updatepeople;
	}

	public String getDriver_createtime() {
		return driver_createtime;
	}

	public void setDriver_createtime(String driver_createtime) {
		this.driver_createtime = driver_createtime;
	}

	public String getDriver_suppliers() {
		return driver_suppliers;
	}

	public void setDriver_suppliers(String driver_suppliers) {
		this.driver_suppliers = driver_suppliers;
	}

	public String getDriver_suppliersname() {
		return driver_suppliersname;
	}

	public void setDriver_suppliersname(String driver_suppliersname) {
		this.driver_suppliersname = driver_suppliersname;
	}

	public Integer getDriver_province() {
		return driver_province;
	}

	public void setDriver_province(Integer driver_province) {
		this.driver_province = driver_province;
	}

	public String getDriver_provincename() {
		return driver_provincename;
	}

	public void setDriver_provincename(String driver_provincename) {
		this.driver_provincename = driver_provincename;
	}

	public Integer getDriver_city() {
		return driver_city;
	}

	public void setDriver_city(Integer driver_city) {
		this.driver_city = driver_city;
	}

	public String getDriver_cityname() {
		return driver_cityname;
	}

	public void setDriver_cityname(String driver_cityname) {
		this.driver_cityname = driver_cityname;
	}

	public Integer getDriver_county() {
		return driver_county;
	}

	public void setDriver_county(Integer driver_county) {
		this.driver_county = driver_county;
	}

	public String getDriver_countyname() {
		return driver_countyname;
	}

	public void setDriver_countyname(String driver_countyname) {
		this.driver_countyname = driver_countyname;
	}

}
