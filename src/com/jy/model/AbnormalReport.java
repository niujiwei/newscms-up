package com.jy.model;

import java.util.List;

/**
 * 异常反馈
 * 
 * @author 90
 * 
 */
public class AbnormalReport {
	private String id;// 异常反馈id
	private String shiping_order_id;// 异常订单id
	private String shiping_order_num;// 订单编号
	private String abnormal_type;// 异常反馈类型（1.货物异常，2客户异常）
	private String abnormal_type_name;//异常类型名字
	private String abnormal_message;// 异常反馈信息
	private String abnormal_time;// 上报时间
	private String abnormal_num;// 上报数量
	private String abnormal_name;// 上报人姓名
	private String abnormal_supperid;// 异常反馈供应商id
	private String abnormal_driverid;// 异常反馈司机id
	private String abnormal_remark;// 上报异常备注
	private String abnormal_result;// 上报处理标志(0，未处理，1已处理)
	private String abnormal_resultremark;// 异常处理结果
	private String abnormal_resultuser;// 异常处理人姓名
	private String abnormal_resulttime;// 异常处理时间
	private String abnormal_userid;//异常上报用户id
	private List<AbnormalImages> abnormalImages;// 异常反馈图片信息

	private ShippingOrder shippingOrder;// 异常反馈对应的订单信息

	
	public String getAbnormal_userid() {
		return abnormal_userid;
	}

	public void setAbnormal_userid(String abnormal_userid) {
		this.abnormal_userid = abnormal_userid;
	}

	public String getAbnormal_type_name() {
		return abnormal_type_name;
	}

	public void setAbnormal_type_name(String abnormal_type_name) {
		this.abnormal_type_name = abnormal_type_name;
	}

	public String getAbnormal_resulttime() {
		return abnormal_resulttime;
	}

	public void setAbnormal_resulttime(String abnormal_resulttime) {
		this.abnormal_resulttime = abnormal_resulttime;
	}

	public String getAbnormal_resultuser() {
		return abnormal_resultuser;
	}

	public void setAbnormal_resultuser(String abnormal_resultuser) {
		this.abnormal_resultuser = abnormal_resultuser;
	}

	public String getAbnormal_resultremark() {
		return abnormal_resultremark;
	}

	public void setAbnormal_resultremark(String abnormal_resultremark) {
		this.abnormal_resultremark = abnormal_resultremark;
	}

	public ShippingOrder getShippingOrder() {
		return shippingOrder;
	}

	public void setShippingOrder(ShippingOrder shippingOrder) {
		this.shippingOrder = shippingOrder;
	}

	public String getShiping_order_num() {
		return shiping_order_num;
	}

	public void setShiping_order_num(String shiping_order_num) {
		this.shiping_order_num = shiping_order_num;
	}

	public List<AbnormalImages> getAbnormalImages() {
		return abnormalImages;
	}

	public void setAbnormalImages(List<AbnormalImages> abnormalImages) {
		this.abnormalImages = abnormalImages;
	}

	public String getAbnormal_remark() {
		return abnormal_remark;
	}

	public void setAbnormal_remark(String abnormal_remark) {
		this.abnormal_remark = abnormal_remark;
	}

	public String getAbnormal_name() {
		return abnormal_name;
	}

	public void setAbnormal_name(String abnormal_name) {
		this.abnormal_name = abnormal_name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getShiping_order_id() {
		return shiping_order_id;
	}

	public void setShiping_order_id(String shiping_order_id) {
		this.shiping_order_id = shiping_order_id;
	}

	public String getAbnormal_type() {
		return abnormal_type;
	}

	public void setAbnormal_type(String abnormal_type) {
		this.abnormal_type = abnormal_type;
	}

	public String getAbnormal_message() {
		return abnormal_message;
	}

	public void setAbnormal_message(String abnormal_message) {
		this.abnormal_message = abnormal_message;
	}

	public String getAbnormal_time() {
		return abnormal_time;
	}

	public void setAbnormal_time(String abnormal_time) {
		this.abnormal_time = abnormal_time;
	}

	public String getAbnormal_num() {
		return abnormal_num;
	}

	public void setAbnormal_num(String abnormal_num) {
		this.abnormal_num = abnormal_num;
	}

	public String getAbnormal_supperid() {
		return abnormal_supperid;
	}

	public void setAbnormal_supperid(String abnormal_supperid) {
		this.abnormal_supperid = abnormal_supperid;
	}

	public String getAbnormal_driverid() {
		return abnormal_driverid;
	}

	public void setAbnormal_driverid(String abnormal_driverid) {
		this.abnormal_driverid = abnormal_driverid;
	}

	public String getAbnormal_result() {
		return abnormal_result;
	}

	public void setAbnormal_result(String abnormal_result) {
		this.abnormal_result = abnormal_result;
	}

}
