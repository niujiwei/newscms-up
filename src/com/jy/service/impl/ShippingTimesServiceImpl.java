package com.jy.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.jy.dao.RemarkMapDao;
import com.jy.dao.ShippingTimesDao;
import com.jy.model.ShippingTimes;
import com.jy.model.User;
import com.jy.service.ShippingTimesService;

@Component
public class ShippingTimesServiceImpl implements ShippingTimesService {
	@Resource
	private ShippingTimesDao shippingTimesDao;
	@Resource
	private RemarkMapDao rms_dao;

	// 时效管理 时效信息查询
	public List<ShippingTimes> getShippingTimes(int rows, int page,
			String orderId, String orderAddress, String startDate,
			String endDate, String sendMession, String shiping_order_goid,
			String type, User user) {
		List<ShippingTimes> list = null;
		if (user.getFlag().equals("0")) {// 管理员
			list = shippingTimesDao.getShippingTimes(rows, page, orderId,
					orderAddress, startDate, endDate, sendMession,
					shiping_order_goid, type);
		} else if (user.getFlag().equals("1")) {// 司机
			list = shippingTimesDao.driverGetShippingTimes(rows, page, orderId,
					orderAddress, startDate, endDate, sendMession,
					shiping_order_goid, type, user.getDriver_id());
		} else if (user.getFlag().equals("2")) {// 供应商
			List<String> userAddress = rms_dao.getAddressSupperlies(user
					.getSuppliers_id());
			list = shippingTimesDao.supperGetShippingTimes(rows, page, orderId,
					orderAddress, startDate, endDate, sendMession,
					shiping_order_goid, type, user.getSuppliers_id(),
					userAddress);
		} else if (user.getFlag().equals("3") || user.getFlag().equals("4")) {// 信息员
			list = shippingTimesDao.otherGetShippingTimes(rows, page, orderId,
					orderAddress, startDate, endDate, sendMession,
					shiping_order_goid, type, user.getId());

		}

		return list;
	}

	// 时效管理 查询时效信息总条数
	public int getShippingTimesCount(String orderId, String orderAddress,
			String startDate, String endDate, String sendMession,
			String shiping_order_goid, String type, User user) {
		int count = 0;
		if (user.getFlag().equals("0")) {// 管理员
			count = shippingTimesDao.getShippingTimesCount(orderId,
					orderAddress, startDate, endDate, sendMession,
					shiping_order_goid, type);
		} else if (user.getFlag().equals("1")) {// 司机
			count = shippingTimesDao.driverGetShippingTimesCount(orderId,
					orderAddress, startDate, endDate, sendMession,
					shiping_order_goid, type, user.getDriver_id());
		} else if (user.getFlag().equals("2")) {// 供应商
			List<String> userAddress = rms_dao.getAddressSupperlies(user
					.getSuppliers_id());
			count = shippingTimesDao.supperGetShippingTimesCount(orderId,
					orderAddress, startDate, endDate, sendMession,
					shiping_order_goid, type, user.getSuppliers_id(),
					userAddress);
		} else if (user.getFlag().equals("3") || user.getFlag().equals("4")) {// 信息员
			count = shippingTimesDao.otherGetShippingTimesCount(orderId,
					orderAddress, startDate, endDate, sendMession,
					shiping_order_goid, type, user.getId());

		}

		return count;
	}

	public List<ShippingTimes> outTimesFile(String[] dataId, String orderId,
			String orderAddress, String startDate, String endDate,
			String sendMession, String shiping_order_goid, String type,
			User user) {
		List<ShippingTimes> list = null;
		if (user.getFlag().equals("0")) {// 管理员
			list = shippingTimesDao.outTimesFile(dataId, orderId, orderAddress,
					startDate, endDate, sendMession, shiping_order_goid, type);

		} else if (user.getFlag().equals("1")) {// 司机

		} else if (user.getFlag().equals("2")) {// 供应商

		} else if (user.getFlag().equals("3") || user.getFlag().equals("4")) {// 信息员
			list = shippingTimesDao.otherOutTimesFile(dataId, orderId,
					orderAddress, startDate, endDate, sendMession,
					shiping_order_goid, type, user.getId());
		}

		return list;
	}

}
