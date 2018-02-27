package com.jy.model;

public class OrderHistory {
	private String order_history_id;//订单历史表ID
	private String orders_id;//订单id
	private  String order_arrive_time;//订单实际到达时间
	private String goods_arrive_remakes;//订单到达实际订单信息备注
	private String state;//状态(0 录入，1分配 2 接单 3,电子围栏 4 签收 5 已返回 6 已接受
	// 7 删除分配)

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getOrder_history_id() {
		return order_history_id;
	}
	public void setOrder_history_id(String order_history_id) {
		this.order_history_id = order_history_id;
	}
	public String getOrders_id() {
		return orders_id;
	}
	public void setOrders_id(String orders_id) {
		this.orders_id = orders_id;
	}
	public String getOrder_arrive_time() {
		return order_arrive_time;
	}
	public void setOrder_arrive_time(String order_arrive_time) {
		this.order_arrive_time = order_arrive_time;
	}
	public String getGoods_arrive_remakes() {
		return goods_arrive_remakes;
	}
	public void setGoods_arrive_remakes(String goods_arrive_remakes) {
		this.goods_arrive_remakes = goods_arrive_remakes;
	}
}
