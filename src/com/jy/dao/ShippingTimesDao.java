package com.jy.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jy.model.ShippingTimes;

public interface ShippingTimesDao {

	// 管理员--获取时效信息
	List<ShippingTimes> getShippingTimes(int rows, int page, String orderId,
			String orderAddress, String startDate, String endDate,
			String sendMession, String shiping_order_goid, String type);

	// 录单员--获取时效信息
	List<ShippingTimes> otherGetShippingTimes(int rows, int page,
			String orderId, String orderAddress, String startDate,
			String endDate, String sendMession, String shiping_order_goid,
			String type, String realname);

	// 司机--获取时效信息
	List<ShippingTimes> driverGetShippingTimes(int rows, int page,
			String orderId, String orderAddress, String startDate,
			String endDate, String sendMession, String shiping_order_goid,
			String type, @Param("driverId") String driverId);

	// 供应商--获取时效信息
	List<ShippingTimes> supperGetShippingTimes(int rows, int page,
			String orderId, String orderAddress, String startDate,
			String endDate, String sendMession, String shiping_order_goid,
			String type, @Param("suppliersId") String suppliersId,
			@Param("list") List<String> listAddress);

	// 管理员--获取时效信息总条数
	int getShippingTimesCount(String orderId, String orderAddress,
			String startDate, String endDate, String sendMession,
			String shiping_order_goid, String type);

	// 录单员--获取时效信息
	int otherGetShippingTimesCount(String orderId, String orderAddress,
			String startDate, String endDate, String sendMession,
			String shiping_order_goid, String type, String user_id);

	// 司机--获取时效信息
	int driverGetShippingTimesCount(String orderId, String orderAddress,
			String startDate, String endDate, String sendMession,
			String shiping_order_goid, String type,
			@Param("driverId") String driverId);

	// 供应商--获取时效信息
	int supperGetShippingTimesCount(String orderId, String orderAddress,
			String startDate, String endDate, String sendMession,
			String shiping_order_goid, String type,
			@Param("suppliersId") String suppliersId,
			@Param("list") List<String> listAddress);

	// 管理员--时效管理导出
	List<ShippingTimes> outTimesFile(@Param("array") String[] dataId,
			String orderId, String orderAddress, String startDate,
			String endDate, String sendMession, String shiping_order_goid,
			String type);

	public List<ShippingTimes> otherOutTimesFile(
			@Param("array") String[] dataId, String orderId,
			String orderAddress, String startDate, String endDate,
			String sendMession, String shiping_order_goid, String type,
			String user_id);
}
