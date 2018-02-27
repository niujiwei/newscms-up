package com.jy.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.jy.dao.ChangeOrderDao;
import com.jy.dao.RemarkMapDao;
import com.jy.model.ChangeOrder;
import com.jy.model.CreateAgreement;
import com.jy.model.ShippingOrder;
import com.jy.model.User;
import com.jy.service.ChangeOrderInfoService;

@Service("ChangeOrderInfoService")
public class ChangeOrderInfoServiceImpl implements ChangeOrderInfoService {
	@Resource
	private ChangeOrderDao so_dao;
	@Resource
	private RemarkMapDao rms_dao;

	// 客户回单回单信息查询
	public List<ShippingOrder> getBackOrderInfo(int rows, int page,
			String name, String phone_number, String type, String start_date,
			String end_date, String start_date1, String end_date1,
			String start_date2, String end_date2, String custom_name,
			String receipt, String address, String shipping_order_state,
			String shipperorder_id, User user) {
		List<ShippingOrder> list = null;
		if (user != null) {
			if (user.getFlag().equals("0")) {// 管理员
				list = so_dao.getBackOrderInfo(rows, page, name, phone_number,
						type, start_date, end_date, start_date1, end_date1,
						start_date2, end_date2, custom_name, receipt, address,
						shipping_order_state, shipperorder_id);
			} else if (user.getFlag().equals("1")) {// 司机
				list = so_dao.driverChangeOrderInfo(rows, page, name,
						phone_number, type, start_date, end_date, start_date1,
						end_date1, start_date2, end_date2, custom_name,
						receipt, address, shipping_order_state,
						shipperorder_id, user.getDriver_id());
			} else if (user.getFlag().equals("2")) {// 供应商
				List<String> user_address = rms_dao.getAddressSupperlies(user
						.getSuppliers_id());
				list = so_dao.suppliersChangeOrderInfo(rows, page, name,
						phone_number, type, start_date, end_date, start_date1,
						end_date1, start_date2, end_date2, custom_name,
						receipt, address, shipping_order_state,
						shipperorder_id, user.getSuppliers_id(), user_address);

			} else if (user.getFlag().equals("3") || user.getFlag().equals("4")) {// 信息员
				list = so_dao.otherChangeOrderInfo(rows, page, name,
						phone_number, type, start_date, end_date, start_date1,
						end_date1, start_date2, end_date2, custom_name,
						receipt, address, shipping_order_state,
						shipperorder_id, user.getId());
			}
		}

		return list;
	}

	// 客户回单 回单信息查询总条数
	public int getBackOrder(String name, String phone_number, String type,
			String start_date, String end_date, String start_date1,
			String end_date1, String start_date2, String end_date2,
			String custom_name, String receipt, String address,
			String shipping_order_state, String shipperorder_id, User user) {
		int count = 0;
		if (user != null) {
			if (user.getFlag().equals("0")) {// 管理员
				count = so_dao.getBackOrder(name, phone_number, type,
						start_date, end_date, start_date1, end_date1,
						start_date2, end_date2, custom_name, receipt, address,
						shipping_order_state, shipperorder_id);
			} else if (user.getFlag().equals("1")) {// 司机
				count = so_dao.driverChangeOrderInfoCount(name, phone_number,
						type, start_date, end_date, start_date1, end_date1,
						start_date2, end_date2, custom_name, receipt, address,
						shipping_order_state, shipperorder_id,
						user.getDriver_id());

			} else if (user.getFlag().equals("2")) {// 供应商
				List<String> user_address = rms_dao.getAddressSupperlies(user
						.getSuppliers_id());
				count = so_dao.suppliersChangeOrderInfoCount(name,
						phone_number, type, start_date, end_date, start_date1,
						end_date1, start_date2, end_date2, custom_name,
						receipt, address, shipping_order_state,
						shipperorder_id, user.getSuppliers_id(), user_address);

			} else if (user.getFlag().equals("3") || user.getFlag().equals("4")) {// 信息员
				count = so_dao.otherChangeOrderInfoCount(name, phone_number,
						type, start_date, end_date, start_date1, end_date1,
						start_date2, end_date2, custom_name, receipt, address,
						shipping_order_state, shipperorder_id, user.getId());
			}
		}

		return count;
	}

	// 查询分页

	public List<ShippingOrder> getChangeOrderInfo(int rows, int page,
			String name, String phone_number, String type, String start_date,
			String end_date) {
		List<ShippingOrder> list = so_dao.getChangeOrderInfo(rows, page, name,
				phone_number, type, start_date, end_date);
		return list;
	}

	// 目前未知
	public int getChangeOrder(String name, String phone_number, String type,
			String start_date, String end_date) {
		int count = so_dao.getChangeOrder(name, phone_number, type, start_date,
				end_date);
		return count;
	}

	// 删除
	public int deleteChangeOrder(String[] del) {
		int i = so_dao.deleteChangeOrder(del);
		return i;
	}

	// 保存
	public int saveChangeOrder(ChangeOrder d) {
		int i = so_dao.saveChangeOrder(d);
		return i;
	}

	// 修改查询
	public ChangeOrder getUpdateChangeOrder(String id) {
		ChangeOrder sp = so_dao.getUpdateChangeOrder(id);
		return sp;
	}

	// 修改
	public int updateChangeOrder(ChangeOrder d) {
		int i = so_dao.updateChangeOrder(d);
		return i;
	}

	// 修改
	public List<ShippingOrder> getPlateNumberLength(String number) {
		List<ShippingOrder> list = so_dao.getPlateNumberLength(number);
		return list;
	}

	// 暂时没有用
	public List<ShippingOrder> getShipOrderAll(String name,
			String phone_number, String type, String start_date, String end_date) {
		List<ShippingOrder> list = so_dao.getShipOrderAll(name, phone_number,
				type, start_date, end_date);
		return list;
	}

	// 订单号查询协议类型
	public CreateAgreement getAddOrderFee(String id) {
		CreateAgreement der = so_dao.getAddOrderFee(id);
		return der;
	}

	/*
	 * 添加中转费，修改订单是否录入状态
	 */
	public int updateOrder(String id) {
		int i = so_dao.updateOrder(id);
		return i;
	}

	// 添加费用判断该订单是否已经被编辑费用：费用表是否有该订单信息
	public int searchOrder(String id) {
		int i = so_dao.searchOrder(id);
		return i;
	}

	// 客户回单 司机或者供应商返回回单
	public int dealBackOrder(String[] deal) {
		int i = so_dao.dealBackOrder(deal);
		return i;
	}

	// 客户回单 录单员接受回单信息返回
	public int otherDealBackOrder(String[] deal) {
		int i = so_dao.otherDealBackOrder(deal);
		return i;
	}

	// 客户回单 取消接受回单
	public int dealNotBackOrder(String[] deal) {
		int i = so_dao.dealNotBackOrder(deal);
		return i;
	}

	// 订单发送消息页面订单查询
	public List<ShippingOrder> getOrderInfo(int rows, int page, String num,
			String receipt, String phone, String send_type) {
		List<ShippingOrder> list = so_dao.getOrderInfo(rows, page, num,
				receipt, phone, send_type);
		return list;
	}

	public int getOrderInfoCount(String num, String receipt, String phone,
			String send_type) {
		int i = so_dao.getOrderInfoCount(num, receipt, phone, send_type);
		return i;
	}

	// 客户回单回单信息导出
	public List<ShippingOrder> outBackShipOrderFile(User user, String name,
			String type, String start_date, String end_date,
			String start_date1, String end_date1, String custom_name,
			String receipt, String address, String shipping_order_state,
			String shipperorder_id, @Param("array") String[] shiping_id) {
		List<ShippingOrder> list = null;
		if (user != null) {
			if (user.getFlag().equals("0")) {// 管理员
				list = so_dao.outNewBackShipOrderFile(user.getId(), name, type,
						start_date, end_date, start_date1, end_date1,
						custom_name, receipt, address, shipping_order_state,
						shipperorder_id, shiping_id);
			} else if (user.getFlag().equals("1")) {// 司机
				list = so_dao.driverOutBackShipOrderFile(user.getId(), name,
						type, start_date, end_date, start_date1, end_date1,
						custom_name, receipt, address, shipping_order_state,
						shipperorder_id, shiping_id, user.getDriver_id());
			} else if (user.getFlag().equals("2")) {// 供应商
				List<String> user_address = rms_dao.getAddressSupperlies(user
						.getSuppliers_id());
				list = so_dao.suppliersOutBackShipOrderFile(user.getId(), name,
						type, start_date, end_date, start_date1, end_date1,
						custom_name, receipt, address, shipping_order_state,
						shipperorder_id, shiping_id, user.getSuppliers_id(),
						user_address);

			} else if (user.getFlag().equals("3") || user.getFlag().equals("4")) {// 信息员
				list = so_dao.otherOutBackShipOrderFile(user.getId(), name,
						type, start_date, end_date, start_date1, end_date1,
						custom_name, receipt, address, shipping_order_state,
						shipperorder_id, shiping_id, user.getId());
			}
		}

		return list;
	}

}
