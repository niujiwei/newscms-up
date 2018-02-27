package com.jy.model;

import java.util.List;

/**
 * JySuppliers entity. @author MyEclipse Persistence Tools
 */

public class JySuppliers {
	// Fields

	private String suppliersId;
	private String suppliersName;// 供应商名称
	private String suppliersAddress;
	private String suppliersPeople;// 公司法人
	private String suppliersPerson;// 联系人
	private String suppliersPhone;// 电话
	private String suppliersService;// 服务范围
	private String suppliersDeadline;// 合同期限
	private String suppliersStartDate;
	private String suppliersEndDate;
	private String suppliersProve;// 资质
	private String suppliersOperator;// 操作人
	private String suppliersOperatorDate;// 操作日期
	private String suppliersOrderNum; // 运单编号
	private String suppliersSendTime; // 日期
	private String suppliersGoodsName;// 货物名称
	private String suppliersGoodsNum;// 总件数
	private String suppliersGoodsVol;// 体积
	private String suppliersTradeAgency;// 代收货款
	private String suppliersUpstairsFee;// 上楼费
	private String suppliersDeliverFee;// 配送费
	private String suppliersAddedFee;// 附加费
	private String suppliersOtherFee;// 其他费用
	private String suppliersTransportPay;// 总运费
	private String suppliersSum;// 合计
	private String suppliersGoodsWeight;// 重量
	private Integer driver_province;// 终到位置(省)id
	private String driver_provincename;// 终到位置(省)
	private Integer driver_city;// 终到位置(城市)id
	private String driver_cityname;// 终到位置(城市)
	private Integer driver_county;// 终到位置(县区)id
	private String driver_countyname;// 终到位置(县区)
	private String suppliersEndAddress; // 终到位置
	private String suppliersSendMechanism;// 受理机构
	private String suppliersCount; // 总件数
	private String suSendTime;//结算中截止日期
	private String driverId;// 司机ID
	private String clearing_state;
	private String id;
	private Integer[] suppers_county;// 城市
	private String suppliers_customer;// 发货客户信息
	private String supplier_customer_name;//发货客户姓名
	private String department_name;// 供应商所在的项目部
	private List<Customer> customer;//发货客户信息
	private List<City_info> listCity_infos;//省市县信息;

	private String phone_brand;// 供应商手机品牌
	private String phone_model;// 供应商手机型号
	private String suppliers_carnumber;// 供应商车牌号
	private String suppliers_cartype;// 供应商车辆种类
	private String supplie_company;// 供应商公司名称

	// Constructors

	
	public List<Customer> getCustomer() {
		return customer;
	}

	public List<City_info> getListCity_infos() {
		return listCity_infos;
	}

	public void setListCity_infos(List<City_info> listCity_infos) {
		this.listCity_infos = listCity_infos;
	}

	public String getSupplier_customer_name() {
		return supplier_customer_name;
	}

	public void setSupplier_customer_name(String supplier_customer_name) {
		this.supplier_customer_name = supplier_customer_name;
	}

	public String getDepartment_name() {
		return department_name;
	}

	public void setDepartment_name(String department_name) {
		this.department_name = department_name;
	}

	public String getSupplie_company() {
		return supplie_company;
	}

	public void setSupplie_company(String supplie_company) {
		this.supplie_company = supplie_company;
	}

	public String getPhone_brand() {
		return phone_brand;
	}

	public void setPhone_brand(String phone_brand) {
		this.phone_brand = phone_brand;
	}

	public String getPhone_model() {
		return phone_model;
	}

	public void setPhone_model(String phone_model) {
		this.phone_model = phone_model;
	}

	public String getSuppliers_carnumber() {
		return suppliers_carnumber;
	}

	public void setSuppliers_carnumber(String suppliers_carnumber) {
		this.suppliers_carnumber = suppliers_carnumber;
	}

	public String getSuppliers_cartype() {
		return suppliers_cartype;
	}

	public void setSuppliers_cartype(String suppliers_cartype) {
		this.suppliers_cartype = suppliers_cartype;
	}

	public void setCustomer(List<Customer> customer) {
		this.customer = customer;
	}

	public String getSuppliers_customer() {
		return suppliers_customer;
	}

	public void setSuppliers_customer(String suppliers_customer) {
		this.suppliers_customer = suppliers_customer;
	}

	public Integer[] getSuppers_county() {
		return suppers_county;
	}

	public void setSuppers_county(Integer[] suppers_county) {
		this.suppers_county = suppers_county;
	}

	/** default constructor */
	public JySuppliers() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getSuppliersCount() {
		return suppliersCount;
	}

	public void setSuppliersCount(String suppliersCount) {
		this.suppliersCount = suppliersCount;
	}

	public String getSuSendTime() {
		return suSendTime;
	}

	public void setSuSendTime(String suSendTime) {
		this.suSendTime = suSendTime;
	}

	public String getDriverId() {
		return driverId;
	}

	public void setDriverId(String driverId) {
		this.driverId = driverId;
	}

	public String getClearing_state() {
		return clearing_state;
	}

	public void setClearing_state(String clearingState) {
		clearing_state = clearingState;
	}

	/** minimal constructor */
	public JySuppliers(String suppliersId) {
		this.suppliersId = suppliersId;
	}

	/** full constructor */

	// Property accessors

	public String getSuppliersId() {
		return this.suppliersId;
	}

	public JySuppliers(String suppliersId, String suppliersName,
			String suppliersAddress, String suppliersPeople,
			String suppliersPerson, String suppliersPhone,
			String suppliersService, String suppliersDeadline,
			String suppliersStartDate, String suppliersEndDate,
			String suppliersProve, String suppliersOperator,
			String suppliersOperatorDate, String suppliersOrderNum,
			String suppliersSendTime, String suppliersGoodsName,
			String suppliersGoodsNum, String suppliersGoodsVol,
			String suppliersTradeAgency, String suppliersUpstairsFee,
			String suppliersDeliverFee, String suppliersAddedFee,
			String suppliersOtherFee, String suppliersTransportPay,
			String suppliersSum, String suppliersGoodsWeight,
			String suppliersEndAddress, String suppliersSendMechanism) {
		super();
		this.suppliersId = suppliersId;
		this.suppliersName = suppliersName;
		this.suppliersAddress = suppliersAddress;
		this.suppliersPeople = suppliersPeople;
		this.suppliersPerson = suppliersPerson;
		this.suppliersPhone = suppliersPhone;
		this.suppliersService = suppliersService;
		this.suppliersDeadline = suppliersDeadline;
		this.suppliersStartDate = suppliersStartDate;
		this.suppliersEndDate = suppliersEndDate;
		this.suppliersProve = suppliersProve;
		this.suppliersOperator = suppliersOperator;
		this.suppliersOperatorDate = suppliersOperatorDate;
		this.suppliersOrderNum = suppliersOrderNum;
		this.suppliersSendTime = suppliersSendTime;
		this.suppliersGoodsName = suppliersGoodsName;
		this.suppliersGoodsNum = suppliersGoodsNum;
		this.suppliersGoodsVol = suppliersGoodsVol;
		this.suppliersTradeAgency = suppliersTradeAgency;
		this.suppliersUpstairsFee = suppliersUpstairsFee;
		this.suppliersDeliverFee = suppliersDeliverFee;
		this.suppliersAddedFee = suppliersAddedFee;
		this.suppliersOtherFee = suppliersOtherFee;
		this.suppliersTransportPay = suppliersTransportPay;
		this.suppliersSum = suppliersSum;
		this.suppliersGoodsWeight = suppliersGoodsWeight;
		this.suppliersEndAddress = suppliersEndAddress;
		this.suppliersSendMechanism = suppliersSendMechanism;
	}

	public String getSuppliersGoodsWeight() {
		return suppliersGoodsWeight;
	}

	public void setSuppliersGoodsWeight(String suppliersGoodsWeight) {
		this.suppliersGoodsWeight = suppliersGoodsWeight;
	}

	public void setSuppliersId(String suppliersId) {
		this.suppliersId = suppliersId;
	}

	public String getSuppliersName() {
		return this.suppliersName;
	}

	public void setSuppliersName(String suppliersName) {
		this.suppliersName = suppliersName;
	}

	public String getSuppliersAddress() {
		return this.suppliersAddress;
	}

	public void setSuppliersAddress(String suppliersAddress) {
		this.suppliersAddress = suppliersAddress;
	}

	public String getSuppliersPeople() {
		return this.suppliersPeople;
	}

	public void setSuppliersPeople(String suppliersPeople) {
		this.suppliersPeople = suppliersPeople;
	}

	public String getSuppliersPerson() {
		return this.suppliersPerson;
	}

	public void setSuppliersPerson(String suppliersPerson) {
		this.suppliersPerson = suppliersPerson;
	}

	public String getSuppliersPhone() {
		return this.suppliersPhone;
	}

	public void setSuppliersPhone(String suppliersPhone) {
		this.suppliersPhone = suppliersPhone;
	}

	public String getSuppliersDeadline() {
		return this.suppliersDeadline;
	}

	public void setSuppliersDeadline(String suppliersDeadline) {
		this.suppliersDeadline = suppliersDeadline;
	}

	public String getSuppliersStartDate() {
		return this.suppliersStartDate;
	}

	public void setSuppliersStartDate(String suppliersStartDate) {
		this.suppliersStartDate = suppliersStartDate;
	}

	public String getSuppliersEndDate() {
		return this.suppliersEndDate;
	}

	public void setSuppliersEndDate(String suppliersEndDate) {
		this.suppliersEndDate = suppliersEndDate;
	}

	public String getSuppliersProve() {
		return this.suppliersProve;
	}

	public void setSuppliersProve(String suppliersProve) {
		this.suppliersProve = suppliersProve;
	}

	public String getSuppliersOperator() {
		return this.suppliersOperator;
	}

	public void setSuppliersOperator(String suppliersOperator) {
		this.suppliersOperator = suppliersOperator;
	}

	public String getSuppliersOperatorDate() {
		return this.suppliersOperatorDate;
	}

	public void setSuppliersOperatorDate(String suppliersOperatorDate) {
		this.suppliersOperatorDate = suppliersOperatorDate;
	}

	public String getSuppliersService() {
		return suppliersService;
	}

	public void setSuppliersService(String suppliersService) {
		this.suppliersService = suppliersService;
	}

	public String getSuppliersOrderNum() {
		return suppliersOrderNum;
	}

	public void setSuppliersOrderNum(String suppliersOrderNum) {
		this.suppliersOrderNum = suppliersOrderNum;
	}

	public String getSuppliersSendTime() {
		return suppliersSendTime;
	}

	public void setSuppliersSendTime(String suppliersSendTime) {
		this.suppliersSendTime = suppliersSendTime;
	}

	public String getSuppliersGoodsName() {
		return suppliersGoodsName;
	}

	public void setSuppliersGoodsName(String suppliersGoodsName) {
		this.suppliersGoodsName = suppliersGoodsName;
	}

	public String getSuppliersGoodsNum() {
		return suppliersGoodsNum;
	}

	public void setSuppliersGoodsNum(String suppliersGoodsNum) {
		this.suppliersGoodsNum = suppliersGoodsNum;
	}

	public String getSuppliersGoodsVol() {
		return suppliersGoodsVol;
	}

	public void setSuppliersGoodsVol(String suppliersGoodsVol) {
		this.suppliersGoodsVol = suppliersGoodsVol;
	}

	public String getSuppliersTradeAgency() {
		return suppliersTradeAgency;
	}

	public void setSuppliersTradeAgency(String suppliersTradeAgency) {
		this.suppliersTradeAgency = suppliersTradeAgency;
	}

	public String getSuppliersUpstairsFee() {
		return suppliersUpstairsFee;
	}

	public void setSuppliersUpstairsFee(String suppliersUpstairsFee) {
		this.suppliersUpstairsFee = suppliersUpstairsFee;
	}

	public String getSuppliersDeliverFee() {
		return suppliersDeliverFee;
	}

	public void setSuppliersDeliverFee(String suppliersDeliverFee) {
		this.suppliersDeliverFee = suppliersDeliverFee;
	}

	public String getSuppliersAddedFee() {
		return suppliersAddedFee;
	}

	public void setSuppliersAddedFee(String suppliersAddedFee) {
		this.suppliersAddedFee = suppliersAddedFee;
	}

	public String getSuppliersOtherFee() {
		return suppliersOtherFee;
	}

	public void setSuppliersOtherFee(String suppliersOtherFee) {
		this.suppliersOtherFee = suppliersOtherFee;
	}

	public String getSuppliersTransportPay() {
		return suppliersTransportPay;
	}

	public void setSuppliersTransportPay(String suppliersTransportPay) {
		this.suppliersTransportPay = suppliersTransportPay;
	}

	public String getSuppliersSum() {
		return suppliersSum;
	}

	public void setSuppliersSum(String suppliersSum) {
		this.suppliersSum = suppliersSum;
	}

	public String getSuppliersEndAddress() {
		return suppliersEndAddress;
	}

	public void setSuppliersEndAddress(String suppliersEndAddress) {
		this.suppliersEndAddress = suppliersEndAddress;
	}

	public String getSuppliersSendMechanism() {
		return suppliersSendMechanism;
	}

	public void setSuppliersSendMechanism(String suppliersSendMechanism) {
		this.suppliersSendMechanism = suppliersSendMechanism;
	}

}