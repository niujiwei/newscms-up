package com.jy.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jy.model.DeliveryCustomer;
import com.jy.model.Driver;
import com.jy.model.Maps;
import com.jy.model.OrderHistory;
import com.jy.model.ShippingOrder;
import com.jy.model.User;

public interface BaiDuMapService {

	/**
	 * 查询订单节点信息
	 * 
	 * @param orderId
	 * @return
	 */
	public List<OrderHistory> getShippingOrderHistoyInfo(String orderId);

	/**
	 * 查询用户信息
	 * 
	 * @param orderId
	 * @return
	 */
	public User getUserInfo(String driverId);

	/**
	 * 查询接单时间和签收时间
	 * 
	 * @param orderId
	 * @return
	 */
	public Map<String, String> getRecSinTime(String orderId);

	/**
	 * 获取司机信息
	 * 
	 * @param orderId
	 * @return
	 */
	public Driver getDriverInfo(String orderId);

	/**
	 * 获取运单信息
	 * 
	 * @param orderId
	 * @return
	 */
	public ShippingOrder getShippingOrderInfo(String orderId);

	/**
	 * 获取收货客户定位信息
	 * 
	 * @param orderId
	 * @return
	 */
	public DeliveryCustomer getDeliveryCustomerInfo(String delivery_cus_name,
			String delivery_people);

	/**
	 * 根据用户名去加载定位信息
	 * 
	 * @param carno
	 * @return
	 */
	public List<Maps> loadMapDriverInfo(String carno,String equipmentNum);

	/**
	 * 加载坐标点定数量
	 * 
	 * @param tablename
	 * @param carno
	 * @param starttime
	 * @param endtime
	 * @return
	 */
	public int loadMapPointCount(String tablename, String carno,
			String starttime, String endtime,String equipmentNum);

	/**
	 * 加载坐标点信息
	 * 
	 * @param tablename
	 * @param carno
	 * @param starttime
	 * @param endtime
	 * @param dbname
	 * @param page
	 * @param size
	 * @return
	 */
	List<Maps> loadMapPointInfo(String tablename, String carno,
			String starttime, String endtime, String dbname, int page, int size,String equipmentNum);

	/**
	 * 根据时间点查询map坐标
	 * 
	 * @param updatetime
	 * @param userName
	 * @param equipmentNum
	 * @return
	 */
	Maps getSomeTimePoint(String tableName,String updatetime, String userName,
			String equipmentNum,int mintime);
}
