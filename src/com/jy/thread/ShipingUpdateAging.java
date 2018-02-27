package com.jy.thread;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.jy.common.HistoryUtils;
import com.jy.common.UUIDUtils;
import com.jy.model.Aging;
import com.jy.model.OrderHistory;
import com.jy.model.ShippingOrder;
import com.jy.service.OrderInfoService;

public class ShipingUpdateAging implements Runnable {
	private List<ShippingOrder> tlist;
	

	private OrderInfoService orderInfoService;
	

	public ShipingUpdateAging() {
		super();
	
	}
	
	
	public ShipingUpdateAging(List<ShippingOrder> tlist,
			OrderInfoService orderInfoService) {
		super();
		this.tlist = tlist;
		this.orderInfoService = orderInfoService;
	}


	public void run() {
		String message = "运单已导入";	
		for (ShippingOrder shipping : tlist) {
//			Aging aging = OrderInfoServiceImpl.getAging(
//					shipping.getSend_mechanism(), shipping.getEnd_address());
			Aging aging = orderInfoService.getNewAging(
					shipping.getSend_mechanism(), shipping.getEnd_address(),shipping.getUpdatetime());
			if (aging != null) {
				orderInfoService.updateAging(
						shipping.getShiping_order_id(), aging.getAging_time(),
						aging.getAging_day());
			}
			HistoryUtils.saveHistory(shipping.getShiping_order_id(),
					shipping.getShiping_order_num(), message,"0");
		}
	}

	public void saveHistory(String ids, String num, String message) {
		List<OrderHistory> order = new ArrayList<OrderHistory>();
		/*
		 * for(int i=0;i<num.length;i++){
		 * 
		 * }
		 */
		OrderHistory h = new OrderHistory();
		h.setOrder_history_id(UUIDUtils.getUUID());
		h.setOrders_id(ids);
		h.setOrder_arrive_time(DateFormat.getDateTimeInstance().format(
				new Date()));
		h.setGoods_arrive_remakes(h.getOrder_arrive_time() + "---货运编号为" + num
				+ "\t" + message);
		order.add(h);
		orderInfoService.saveOrderHistory(order);
	}
}
