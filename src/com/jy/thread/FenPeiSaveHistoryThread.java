package com.jy.thread;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.jy.common.HistoryUtils;
import com.jy.common.UUIDUtils;
import com.jy.model.OrderHistory;
import com.jy.model.ShippingOrder;
import com.jy.service.ShippingOrderInfoService;

public class FenPeiSaveHistoryThread implements Runnable  {
	private String[] orders;
    private ShippingOrderInfoService csi;
    private String message;
    private String state;
    
	public void run() {
		for (int i = 0; i < orders.length; i++) {
			String order_num="";
			ShippingOrder order = csi.getUpdateSignShipOrder(orders[i]);
			if(order!=null) order_num =order.getShiping_order_num();
			HistoryUtils.saveHistory(orders[i],order_num,message,state);
		}
		
	}
	public FenPeiSaveHistoryThread() {
		super();
	}

	public FenPeiSaveHistoryThread(String[] orders, ShippingOrderInfoService csi) {
		super();
		this.orders = orders;
		this.csi = csi;
		this.state="1";
		this.message="运单已分配";
	}

	public FenPeiSaveHistoryThread(String[] orders, ShippingOrderInfoService csi, String message, String state) {
		this.orders = orders;
		this.csi = csi;
		this.message = message;
		this.state = state;
	}

	public void saveHistory(String ids, String ordernum, String message){
		List<OrderHistory> order=new ArrayList<OrderHistory>();
		OrderHistory h=new OrderHistory();
		h.setOrder_history_id(UUIDUtils.getUUID());
		h.setOrders_id(ids);
		h.setOrder_arrive_time(DateFormat.getDateTimeInstance().format(new Date()));
		h.setGoods_arrive_remakes(h.getOrder_arrive_time()+"---货运编号为:"+ordernum+"\t"+message);
		order.add(h);
		int count=csi.saveOrderHistory(order);
	}
}
