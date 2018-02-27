package com.jy.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import com.jy.dao.BaiDuMapDao;
import com.jy.model.DeliveryCustomer;
import com.jy.model.Driver;
import com.jy.model.Maps;
import com.jy.model.OrderHistory;
import com.jy.model.ShippingOrder;
import com.jy.model.User;
import com.jy.service.BaiDuMapService;

@Controller
public class BaiDuMapSericeImpl implements BaiDuMapService {
	@Resource
	private BaiDuMapDao baiDuMapDao;

	// 查询订单节点信息
	public List<OrderHistory> getShippingOrderHistoyInfo(String orderId) {
		return baiDuMapDao.getShippingOrderHistoyInfo(orderId);
	}

	// 查询用户信息
	public User getUserInfo(String driverId) {

		return baiDuMapDao.getUserInfo(driverId);
	}

	// 查询接单时间和签收时间
	public Map<String, String> getRecSinTime(String orderId) {
		return baiDuMapDao.getRecSinTime(orderId);
	}

	// 获取司机信息
	public Driver getDriverInfo(String orderId) {

		return baiDuMapDao.getDriverInfo(orderId);
	}

	// 获取订单信息
	public ShippingOrder getShippingOrderInfo(String orderId) {

		return baiDuMapDao.getShippingOrderInfo(orderId);
	}

	// 获取收货客户定位信息
	public DeliveryCustomer getDeliveryCustomerInfo(String delivery_cus_name,String delivery_people) {
     
		return baiDuMapDao.getDeliveryCustomerInfo(delivery_cus_name,delivery_people);
	}
	
	//根据用户名去加载定位信息
	public List<Maps> loadMapDriverInfo(String carno,String equipmentNum) {
		
		
		return baiDuMapDao.loadMapDriverInfo(carno, equipmentNum);
	}
	
	// 加载坐标点定数量
	public int loadMapPointCount(String tablename, String carno,
			String starttime, String endtime,String equipmentNum) {
		
		return baiDuMapDao.loadMapPointCount(tablename, carno, starttime, endtime,equipmentNum);
	}
	
	//加载坐标点信息
	public List<Maps> loadMapPointInfo(String tablename, String carno,
			String starttime, String endtime, String dbname, int page,
			int size,String equipmentNum) {
		return baiDuMapDao.loadMapPointInfo(tablename, carno, starttime, endtime, dbname, page, size,equipmentNum);
	}

	// 根据时间点查询map坐标
	public Maps getSomeTimePoint(String tableName,String updatetime, String userName,
			String equipmentNum,int mintime) {
		return baiDuMapDao.getSomeTimePoint(tableName,updatetime, userName, equipmentNum,mintime);
	}

}
