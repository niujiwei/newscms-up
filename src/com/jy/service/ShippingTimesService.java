package com.jy.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jy.model.ShippingTimes;
import com.jy.model.User;

public interface ShippingTimesService {
	
	/**
	 * 时效管理 查询时效信息
	 * @param rows
	 * @param page
	 * @param orderId
	 * @param orderAddress
	 * @param startDate
	 * @param endDate
	 * @param sendMession
	 * @param shiping_order_goid
	 * @param type
	 * @return
	 */
	public List<ShippingTimes> getShippingTimes(int rows, int page,
			String orderId, String orderAddress, String startDate,
			String endDate, String sendMession, String shiping_order_goid,
			String type,User user);

	/**
	 * 时效管理 时效信息查询总条数
	 * @param orderId
	 * @param orderAddress
	 * @param startDate
	 * @param endDate
	 * @param sendMession
	 * @param shiping_order_goid
	 * @param type
	 * @param user
	 * @return
	 */
	public int getShippingTimesCount(String orderId, String orderAddress,
			String startDate, String endDate, String sendMession,
			String shiping_order_goid, String type,User user);

	/**
	 * 时效管理信息导出
	 * @param dataId
	 * @param orderId
	 * @param orderAddress
	 * @param startDate
	 * @param endDate
	 * @param sendMession
	 * @param shiping_order_goid
	 * @param type
	 * @param user
	 * @return
	 */
	public List<ShippingTimes> outTimesFile(@Param("array") String[] dataId,
			String orderId, String orderAddress, String startDate,
			String endDate, String sendMession, String shiping_order_goid,
			String type,User user);

	
}
