package com.jy.model;

public class ShippingTimes {
	private String shiping_order_id;// 订单id
	private String shiping_order_num;// 货运编号
	private String shiping_order_goid;// 出货订单号
	private String end_address;// 终到地址
	private String auto_sign_time;// 围栏签收时间
	private String sign_time;// 订单签收时间
	private String aging_time;// 时效
	private String aging_day;
	private String result;// 结果
	private String custom_name;// 发货客户名称
	private String send_mechanism;// 受理机构
	private String receivetime;// 接单时间
	private String updatetime;// 导入时间
	private String receipt_name;// 到货联系人
	private String sign_remarks;// 签收备注
	private String abnormal_name;//异常上报内容
	

	public String getAbnormal_name() {
		return abnormal_name;
	}

	public void setAbnormal_name(String abnormal_name) {
		this.abnormal_name = abnormal_name;
	}

	public String getSign_remarks() {
		return sign_remarks;
	}

	public void setSign_remarks(String sign_remarks) {
		this.sign_remarks = sign_remarks;
	}

	public String getReceipt_name() {
		return receipt_name;
	}

	public void setReceipt_name(String receipt_name) {
		this.receipt_name = receipt_name;
	}

	public String getReceivetime() {
		return receivetime;
	}

	public String getShiping_order_goid() {
		return shiping_order_goid;
	}

	public void setShiping_order_goid(String shiping_order_goid) {
		this.shiping_order_goid = shiping_order_goid;
	}

	public void setReceivetime(String receivetime) {
		this.receivetime = receivetime;
	}

	public String getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}

	public String getSend_mechanism() {
		return send_mechanism;
	}

	public void setSend_mechanism(String send_mechanism) {
		this.send_mechanism = send_mechanism;
	}

	public String getCustom_name() {
		return custom_name;
	}

	public void setCustom_name(String custom_name) {
		this.custom_name = custom_name;
	}

	public String getAging_day() {
		return aging_day;
	}

	public void setAging_day(String aging_day) {
		this.aging_day = aging_day;
	}

	public String getEnd_address() {
		return end_address;
	}

	public void setEnd_address(String end_address) {
		this.end_address = end_address;
	}

	public String getShiping_order_id() {
		return shiping_order_id;
	}

	public void setShiping_order_id(String shiping_order_id) {
		this.shiping_order_id = shiping_order_id;
	}

	public String getShiping_order_num() {
		return shiping_order_num;
	}

	public void setShiping_order_num(String shiping_order_num) {
		this.shiping_order_num = shiping_order_num;
	}

	public String getAuto_sign_time() {
		return auto_sign_time;
	}

	public void setAuto_sign_time(String auto_sign_time) {
		this.auto_sign_time = auto_sign_time;
	}

	public String getSign_time() {
		return sign_time;
	}

	public void setSign_time(String sign_time) {
		this.sign_time = sign_time;
	}

	public String getAging_time() {
		return aging_time;
	}

	public void setAging_time(String aging_time) {
		this.aging_time = aging_time;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
}
