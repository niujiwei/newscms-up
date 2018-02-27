package com.jy.thread;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.jy.common.UUIDUtils;
import com.jy.model.OrderHistory;
import com.jy.model.ShippingOrder;
import com.jy.model.User;
import com.jy.service.SuppliersService;


public class SupliersRunnable implements Runnable {
	    private List<ShippingOrder>  list;
		private SuppliersService ss;
		private User user;
		
	public SupliersRunnable() {
			super();
			// TODO Auto-generated constructor stub
		}

	public SupliersRunnable(List<ShippingOrder> list, SuppliersService ss) {
		super();
		this.list = list;
		this.ss = ss;
	}
	public SupliersRunnable(List<ShippingOrder> list, SuppliersService ss,User user) {
		super();
		this.list = list;
		this.ss = ss;
		this.user = user;
	}

	public void run() {
		// TODO Auto-generated method stub
		  for (ShippingOrder shOrder : list) {		    	 				   
		    	saveOrderSuppliers(shOrder.getShiping_order_id(),shOrder.getShiping_order_num(),"供应商已结算,结算人是:"+user.getRealName());
			}
	}
	public void saveOrderSuppliers(String ids,String num, String message){		
		List<OrderHistory> order = new ArrayList<OrderHistory>();
		OrderHistory oh = new OrderHistory();
		oh.setOrder_history_id(UUIDUtils.getUUID());
		oh.setOrders_id(ids);
		oh.setOrder_arrive_time(DateFormat.getDateTimeInstance().format(new Date()));
		oh.setGoods_arrive_remakes(oh.getOrder_arrive_time()+"---货运编号为"+num+"\t"+message);
		order.add(oh);
		int count=ss.saveOrderSupp(order);
	}

}
