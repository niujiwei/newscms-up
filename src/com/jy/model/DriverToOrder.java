package com.jy.model;

/**
 * 订单司机关联表
 * 
 * @author zzp
 * 
 */
public class DriverToOrder {
	private String id;// id
	private String driver_id;// 司机id
	private String order_id; // 订单id
	private String equipmentNum;// 设备号
	private String userName;// 用户名

	public String getEquipmentNum() {
		return equipmentNum;
	}

	public void setEquipmentNum(String equipmentNum) {
		this.equipmentNum = equipmentNum;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDriver_id() {
		return driver_id;
	}

	public void setDriver_id(String driver_id) {
		this.driver_id = driver_id;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

}
