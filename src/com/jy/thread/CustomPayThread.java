package com.jy.thread;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.jy.common.UUIDUtils;
import com.jy.model.OrderHistory;
import com.jy.model.ShippingOrder;
import com.jy.model.User;
import com.jy.service.ShippingOrderInfoService;

public class CustomPayThread implements Runnable  {
   private List<ShippingOrder> list;
    private ShippingOrderInfoService csi;
    private User user;
	
    
    public CustomPayThread() {
		super();
	}


	public CustomPayThread(List<ShippingOrder> list,
			ShippingOrderInfoService csi) {
		super();
		this.list = list;
		this.csi = csi;
	}
	
	public CustomPayThread(List<ShippingOrder> list,
			ShippingOrderInfoService csi,User user ) {
		super();
		this.list = list;
		this.csi = csi;
		this.user = user;
	}
	public void run() {
		//List<ShippingOrder> list =  csi.selectShippinOrderId(code, min_time, max_time);
		for (ShippingOrder shiOrder : list) {
		    saveHistory(shiOrder.getShiping_order_id(), shiOrder.getShiping_order_num(), "运单客户已结算,结算人："+user.getRealName());
		}
	}
	/**记录客户结算
	 * 
	 * @param ids
	 * @param num
	 * @param message
	 */
	public void saveHistory(String ids,String num, String message){
		List<OrderHistory> order=new ArrayList<OrderHistory>();
		OrderHistory h=new OrderHistory();
		h.setOrder_history_id(UUIDUtils.getUUID());
		h.setOrders_id(ids);
		h.setOrder_arrive_time(DateFormat.getDateTimeInstance().format(new Date()));
		h.setGoods_arrive_remakes(h.getOrder_arrive_time()+"---货运编号为"+num+"\t"+message);
		order.add(h);
		csi.saveOrderHistory(order);
	}

}
