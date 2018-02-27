package com.jy.model;

public class Comment {
	private String comment_id;//编号
	private String shiping_order_id;//订单编号
	private String send_mechanism;//受理机构
	private String driver_id;//司机编号
	private String driver_name;//司机姓名
	private String comment_name;//评论人
	private String openid;//评论人微信id
	private String nickname;//微信用户名
	private String comment_phone;//评论人电话
	private String remark;//评论备注
	private String xing;//星星
	//**********************************************
	private String driver_phone;//司机电话
	private String shipping_order_state;//订单状态
	private String comment_state;//评论状态
	private String comment_data;//评价时间
	private String shiping_order_num;//货运编号 
	private String end_address;//终到位置
	private String receipt_name;//到货联系人（收货联系人）
	private String receipt_tel;//到货联系人电话（收货联系人电话）
	private String goods_name;//货物名称
	private String goods_num;//货物总件数
	private String suppliers_id;//供应商
	
	
	
	
	public String getComment_phone() {
		return comment_phone;
	}
	public void setComment_phone(String comment_phone) {
		this.comment_phone = comment_phone;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getComment_id() {
		return comment_id;
	}
	public void setComment_id(String comment_id) {
		this.comment_id = comment_id;
	}
	public String getShiping_order_id() {
		return shiping_order_id;
	}
	public void setShiping_order_id(String shiping_order_id) {
		this.shiping_order_id = shiping_order_id;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getXing() {
		return xing;
	}
	public void setXing(String xing) {
		this.xing = xing;
	}
	public String getSend_mechanism() {
		return send_mechanism;
	}
	public void setSend_mechanism(String send_mechanism) {
		this.send_mechanism = send_mechanism;
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
	public String getComment_name() {
		return comment_name;
	}
	public void setComment_name(String comment_name) {
		this.comment_name = comment_name;
	}
	public String getDriver_phone() {
		return driver_phone;
	}
	public void setDriver_phone(String driver_phone) {
		this.driver_phone = driver_phone;
	}
	public String getShipping_order_state() {
		return shipping_order_state;
	}
	public void setShipping_order_state(String shipping_order_state) {
		this.shipping_order_state = shipping_order_state;
	}
	public String getComment_state() {
		return comment_state;
	}
	public void setComment_state(String comment_state) {
		this.comment_state = comment_state;
	}
	public String getComment_data() {
		return comment_data;
	}
	public void setComment_data(String comment_data) {
		this.comment_data = comment_data;
	}
	public String getShiping_order_num() {
		return shiping_order_num;
	}
	public void setShiping_order_num(String shiping_order_num) {
		this.shiping_order_num = shiping_order_num;
	}
	public String getEnd_address() {
		return end_address;
	}
	public void setEnd_address(String end_address) {
		this.end_address = end_address;
	}
	public String getReceipt_name() {
		return receipt_name;
	}
	public void setReceipt_name(String receipt_name) {
		this.receipt_name = receipt_name;
	}
	public String getReceipt_tel() {
		return receipt_tel;
	}
	public void setReceipt_tel(String receipt_tel) {
		this.receipt_tel = receipt_tel;
	}
	public String getGoods_name() {
		return goods_name;
	}
	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}
	public String getGoods_num() {
		return goods_num;
	}
	public void setGoods_num(String goods_num) {
		this.goods_num = goods_num;
	}
	public String getSuppliers_id() {
		return suppliers_id;
	}
	public void setSuppliers_id(String suppliers_id) {
		this.suppliers_id = suppliers_id;
	}
	
}
