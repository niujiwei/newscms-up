package com.jy.service;

import java.util.List;

import com.jy.model.DeliveryCustomer;
import com.jy.model.JITMonitor;
import com.jy.model.ShippingOrder;

public interface JITMonitorService {
	// 所有合同的查询
	public int getJITMonitor(String start_date, String end_date,
			Integer monitorType, String carId);

	public List<JITMonitor> getJITMonitorInfo(int rows, int page,
			String start_date, String end_date, Integer monitorType,
			String carId); // 查询

	/*
	 * select 查询
	 */
	public List<JITMonitor> getCar_idLength(String car_id, int type);

	public List<JITMonitor> getJITMonitorAll(String start_date,
			String end_date, Integer monitorType, String carId);// 导出

	public JITMonitor searchJITMonitor(String del);// 处理查询

	public int dealJITMonitor(JITMonitor m);// 处理
	// 根据carid查询报警信息

	public List<JITMonitor> getCarIdJITMonitor(String travelCardId);

	public String getDriverid(String carid);

	public String[] getDriverOrder(String driver_id);

	public List<DeliveryCustomer> getCustomer(String order_id);

	public int signOrder(ShippingOrder order);

	public ShippingOrder getOrder(String order_id);

	public int signOrderyesorno(String order_id);

	public List<ShippingOrder> getSignOrders();// 取出所有电子签收的订单

	public int updateOrder_state(String order_id);// 修改订单签收状态为4

	public List<DeliveryCustomer> getNewCustomer(String delivery_name,
			String delivery_people, String delivery_cus_name);
}
