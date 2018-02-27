package com.jy.model;

/**
 * 异常反馈导出信息
 * 
 * @author 90
 * 
 */
public class AbnormalReportFile {
	private String rowno;// 序号
	private String shiping_order_num;// 订单编号
	private String shiping_order_goid;// 出货订单号
	private String send_mechanism;// 受理机构
	private String end_address;// 终到位置-----用来分配司机配送地址，把运单分配给司机
	private String receipt_tel;// 到货联系人电话
	private String receipt_name;// 到货联系人
	private String abnormal_type;// 异常反馈类型（1.货物异常，2客户异常）
	private String abnormal_message;// 异常反馈信息
	private String abnormal_resultremark;// 异常处理结果
	private String abnormal_remark;// 上报异常备注
	private String abnormal_name;// 上报人姓名

	public String getRowno() {
		return rowno;
	}

	public void setRowno(String rowno) {
		this.rowno = rowno;
	}

	public String getShiping_order_num() {
		return shiping_order_num;
	}

	public void setShiping_order_num(String shiping_order_num) {
		this.shiping_order_num = shiping_order_num;
	}

	public String getShiping_order_goid() {
		return shiping_order_goid;
	}

	public void setShiping_order_goid(String shiping_order_goid) {
		this.shiping_order_goid = shiping_order_goid;
	}

	public String getSend_mechanism() {
		return send_mechanism;
	}

	public void setSend_mechanism(String send_mechanism) {
		this.send_mechanism = send_mechanism;
	}

	public String getEnd_address() {
		return end_address;
	}

	public void setEnd_address(String end_address) {
		this.end_address = end_address;
	}

	public String getReceipt_tel() {
		return receipt_tel;
	}

	public void setReceipt_tel(String receipt_tel) {
		this.receipt_tel = receipt_tel;
	}

	public String getReceipt_name() {
		return receipt_name;
	}

	public void setReceipt_name(String receipt_name) {
		this.receipt_name = receipt_name;
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

	public String getAbnormal_resultremark() {
		return abnormal_resultremark;
	}

	public void setAbnormal_resultremark(String abnormal_resultremark) {
		this.abnormal_resultremark = abnormal_resultremark;
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

}
