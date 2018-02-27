package com.jy.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jy.model.DeliveryCustomer;
import com.jy.model.JITMonitor;
import com.jy.model.MonitorRuning;
import com.jy.model.ShippingOrder;

public interface JITMonitorDao {
	// 查询总数
	int getJITMonitor(String start_date, String end_date, Integer monitorType,
			String carId);

	List<JITMonitor> getJITMonitorInfo(int rows, int page, String start_date,
			String end_date, Integer monitorType, String carId); // 查询

	public List<JITMonitor> getJITMonitorAll(String start_date,
			String end_date, Integer monitorType, String carId);

	/*
	 * 获取数据下拉表的查询
	 */
	public List<JITMonitor> getCar_idLength(String car_id, int type);

	public JITMonitor searchJITMonitor(String del);// 处理查询

	public int dealJITMonitor(JITMonitor m);// 处理
	// 根据carid查询报警信息

	List<JITMonitor> getCarIdJITMonitor(String travelCardId);

	String getDriverid(String carid);

	String[] getDriverOrder(String driver_id);

	List<DeliveryCustomer> getCustomer(String order_id);

	int signOrder(String order_id);

	int checkOrder(String order_id);

	ShippingOrder getOrder(String order_id);

	int addSignOrder(ShippingOrder order);

	int signOrderyesorno(String order_id);

	int updateSignTime(String order_id);

	List<ShippingOrder> getSignOrders();// 取出所有电子签收的订单

	int updateOrder_state(String order_id);// 修改订单签收状态为4

	/**
	 * 新电子围栏判断收货客户
	 * 
	 * @param delivery_name
	 * @param delivery_people
	 * @param delivery_cus_name
	 * @return
	 */
	public List<DeliveryCustomer> getNewCustomer(
			@Param("delivery_name") String delivery_name,
			@Param("delivery_people") String delivery_people,
			@Param("delivery_cus_name") String delivery_cus_name);
}
