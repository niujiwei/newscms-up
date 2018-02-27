package com.jy.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.jy.common.UUIDUtils;
import com.jy.dao.JITMonitorDao;
import com.jy.model.DeliveryCustomer;
import com.jy.model.JITMonitor;
import com.jy.model.MonitorRuning;
import com.jy.model.ShippingOrder;
import com.jy.service.JITMonitorService;
@Component
public class JITMonitorServiceImpl implements JITMonitorService{
	@Resource
	private JITMonitorDao jmonitdao;
	/*
	 * 查询分页
	 * */
	public List<JITMonitor>  getJITMonitorInfo(int rows,int page,String start_date,String end_date,Integer monitorType,String carId) {
		List<JITMonitor> list=jmonitdao.getJITMonitorInfo(rows, page, start_date, end_date, monitorType, carId);
		return list;
	}
	public int getJITMonitor(String start_date,String end_date,Integer monitorType,String carId) {
		int count =jmonitdao.getJITMonitor(start_date, end_date, monitorType, carId);
		return count;
	}
	/*
	 * 选择框的查询
	 * */
	public List<JITMonitor> getCar_idLength(String car_id,int type){
		List<JITMonitor>list=jmonitdao.getCar_idLength(car_id,type);
		return list;
	}
	//导出
	public List<JITMonitor> getJITMonitorAll(String start_date,String end_date,Integer monitorType,String carId){
		List<JITMonitor>list=jmonitdao.getJITMonitorAll(start_date, end_date, monitorType, carId);
		return list;
	}
	//处理
	public JITMonitor searchJITMonitor(String del) {
		JITMonitor m=jmonitdao.searchJITMonitor(del);
		return m;
	}
	public int dealJITMonitor(JITMonitor m) {
		int i=jmonitdao.dealJITMonitor(m);
		return i;
	}
	public List<JITMonitor> getCarIdJITMonitor(String travelCardId) {
		// TODO Auto-generated method stub
		List<JITMonitor> jit=jmonitdao.getCarIdJITMonitor(travelCardId);
		return jit;
	}
	public String getDriverid(String carid) {
		// TODO Auto-generated method stub
		return jmonitdao.getDriverid(carid);
	}
	public String[] getDriverOrder(String driver_id) {
		// TODO Auto-generated method stub
		return jmonitdao.getDriverOrder(driver_id);
	}
	public List<DeliveryCustomer> getCustomer(String order_id) {
		// TODO Auto-generated method stub
		return jmonitdao.getCustomer(order_id);
	}
	public int signOrder(ShippingOrder order) {
		String order_id = order.getShiping_order_id();
			int i = jmonitdao.checkOrder(order_id);
			if(i>0){
				jmonitdao.signOrder(order_id);
			}else{
				order.setSign_id(UUIDUtils.getUUID());
				int i2 = jmonitdao.addSignOrder(order);	
			}
			try {
				jmonitdao.updateSignTime(order_id);
			} catch (Exception e) {
				e.printStackTrace();
			}
		return i;
	}
	public ShippingOrder getOrder(String order_id) {
		// TODO Auto-generated method stub
		return jmonitdao.getOrder(order_id);
	}
	public int signOrderyesorno(String order_id) {
		// TODO Auto-generated method stub
		return jmonitdao.signOrderyesorno(order_id);
	}
	public List<ShippingOrder> getSignOrders() {
		// TODO Auto-generated method stub
		return jmonitdao.getSignOrders();
	}
	public int updateOrder_state(String order_id) {
		// TODO Auto-generated method stub
		return jmonitdao.updateOrder_state(order_id);
	}
	public List<DeliveryCustomer> getNewCustomer(String delivery_name,
			String delivery_people, String delivery_cus_name) {
		
		return jmonitdao.getNewCustomer(delivery_name, delivery_people, delivery_cus_name);
	}
}
