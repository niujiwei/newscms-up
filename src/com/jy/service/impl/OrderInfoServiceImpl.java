package com.jy.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.jy.model.*;
import org.springframework.stereotype.Service;

import com.jy.dao.OrderDao;
import com.jy.service.OrderInfoService;

@Service("OrderInfoService")
public class OrderInfoServiceImpl implements OrderInfoService {
	@Resource
	private OrderDao so_dao;

	// 查询分页
	public List<ShippingOrder> getOrderCustomInfo(int rows, int page,
			String send_time, String shiping_order_num, String send_mechanism,
			String end_address, String custom_name, String receipt_name,
			String receipt_tel) {

		List<ShippingOrder> list = so_dao.getOrderCustomInfo(rows, page,
				send_time, shiping_order_num, send_mechanism, end_address,
				custom_name, receipt_name, receipt_tel);
		return list;
	}

	public int getOrderCustom(String send_time, String shiping_order_num,
			String send_mechanism, String end_address, String custom_name,
			String receipt_name, String receipt_tel) {

		int count = so_dao.getOrderCustom(send_time, shiping_order_num,
				send_mechanism, end_address, custom_name, receipt_name,
				receipt_tel);
		return count;
	}

	public int deleteOrderCustom(String[] del) {
		int i = so_dao.deleteOrderCustom(del);
		return i;
	}

	/**
	 * 根据客户车辆信息id删除相关订单信息
	 * 
	 * @param pid
	 * @return
	 */
	public int deleteOrderMsg(String[] pid) {
		int i = so_dao.deleteOrderMsg(pid);
		return i;
	}

	/**
	 * 查询是否有关联的订单
	 * 
	 * @param pid
	 * @return
	 */
	public int OrderMsgNum(String[] pid) {
		int i = so_dao.OrderMsgNum(pid);
		return i;
	}

	public int saveOrderCustom(OrderCustom d) {
		int i = so_dao.saveOrderCustom(d);
		return i;
	}

	public OrderCustom getUpdateOrderCustom(String id) {
		OrderCustom sp = so_dao.getUpdateOrderCustom(id);
		return sp;
	}

	public int updateOrderCustom(OrderCustom d) {
		int i = so_dao.updateOrderCustom(d);
		return i;
	}

	public List<OrderCustom> getPlateNumber(String number) {
		List<OrderCustom> list = so_dao.getPlateNumber(number);
		return list;
	}

	public List<OrderCustom> getOrderCustomAll(String name,
			String phone_number, String type, String start_date, String end_date) {
		List<OrderCustom> list = so_dao.getOrderCustomAll(name, phone_number,
				type, start_date, end_date);
		return list;
	}

	// 运单录入 导入运单保存数据
	public int saveimp(List<ShippingOrder> tlist) {
		int i = so_dao.saveShipOrders(tlist);
		return i;
	}

	public int customerExcel(List<Customer> tlist) {

		int i = so_dao.customerExcel(tlist);
		return i;
	}

	public List<OrderCustom> getCustomer(String name) {
		List<OrderCustom> list = so_dao.getCustomer(name);
		return list;
	}

	/**
	 * 运单导入查询重复订单号
	 * 
	 * @param id
	 * @return
	 */
	public List<ShippingOrder> getAorder() {
		List<ShippingOrder> list = so_dao.getAorder();
		return list;
	}

	// 查询订单所有的信息
	public ShippingOrder getShipOrderMsg(String pid) {
		ShippingOrder s = so_dao.getShipOrderMsg(pid);
		return s;
	}

	/**
	 * 修改订单备注
	 * 
	 * @param pid
	 * @param notes
	 * @return
	 */
	public int getRemakesOrder(String pid, String notes, String remarks_orde,
			String remarks_date) {
		int i = so_dao.getRemakesOrder(pid, notes, remarks_orde, remarks_date);
		return i;
	}

	/**
	 * 首页订单显示
	 */
	public List<ShippingOrder> getShipOrderInfo(int rows, int page,
			String name, String phone_number, String type, String start_date,
			String end_date, String pid, String phone, String order_state,
			String pay_type, String send_address, String goods_name) {
		List<ShippingOrder> list = so_dao.getShipOrderInfo(rows, page, name,
				phone_number, type, start_date, end_date, pid, phone,
				order_state, pay_type, send_address, goods_name);
		return list;
	}

	public int getShipOrder(String name, String phone_number, String type,
			String start_date, String end_date, String pid, String phone,
			String order_state, String pay_type, String send_address,
			String goods_name) {
		int count = so_dao.getShipOrder(name, phone_number, type, start_date,
				end_date, pid, phone, order_state, pay_type, send_address,
				goods_name);
		return count;
	}

	/**
	 * 订单修改记录查询
	 * 
	 * @param pid
	 * @return
	 */
	public int getShipOrder1(String pid) {
		return so_dao.getShipOrder1(pid);
	}

	public List<ShippingOrder> getShipOrderInfo1(int rows, int page, String pid) {
		return so_dao.getShipOrderInfo1(rows, page, pid);
	}

	/**
	 * 获取司机信息
	 * 
	 * @return
	 */
	public List<Driver> getDriverInfo() {

		List<Driver> list = so_dao.getDriverInfo();
		return list;
	}

	/**
	 * 添加司机订单关联
	 * 
	 * @param dtolist
	 * @return
	 */
	public int saveDriverToOrder(List<DriverToOrder> dtolist) {

		int fla = so_dao.saveDriverToOrder(dtolist);
		return fla;
	}

	public void upfenpeiOrder(List<DriverToOrder> dtolist) {

		 so_dao.upfenpeiOrder(dtolist);
	}

	// 运单导入获取时效
	public Aging getAging(String id, String address) {
		return so_dao.getAging(id, address);
	}

	// 运单导入更新时效
	public int updateAging(String id, String aging, String agingDay) {
		return so_dao.updateAging(id, aging, agingDay);
	}

	public List<PositionInfo> getAddres(String province, String city,
			String count) {

		return so_dao.getAddres(province, city, count);
	}

	public int DeliveryExcelInfo(List<DeliveryCustomer> tlist) {

		return so_dao.DeliveryExcelInfo(tlist);
	}

	public int impExcelSaveimp(JySuppliers list) {
		return so_dao.impExcelSaveimp(list);
	}

	public int impExcelDriverSaveimp(DriverNew list) {
		return so_dao.impExcelDriverSaveimp(list);
	}

	public int impAgingExcel(Aging aging) {
		return so_dao.impAgingExcel(aging);
	}

	public int impUserExcel(User user) {
		return so_dao.impUserExcel(user);
	}

	// 运单录入--新的更新时效
	public Aging getNewAging(String id, String address, String inputTime) {
		return so_dao.getNewAging(id, address, inputTime);

	}

	public ShippingOrder getShipOrderInfos(String order_id) {

		return so_dao.getShipOrderInfos(order_id);
	}

	//保存节点信息
	public int saveOrderHistory(List<OrderHistory> order) {
		
		return so_dao.saveOrderHistory(order);
	}

	//通过订单号和出货订单号判断运单是否存在
	public int bGetOrderCount(String orderNum, String shiping_order_goid) {
	
		return so_dao.bGetOrderCount(orderNum, shiping_order_goid);
	}

	@Override
	public List<City_info> getCityInfoByEndAddress(String endAddress) {
		return so_dao.getCityInfoByEndAddress(endAddress);
	}

	@Override
	public int updateAgingAndCityInfo(String id, String aging, String agingDay, Integer province, Integer city, Integer county) {
		return so_dao.updateAgingAndCityInfo(id,aging,agingDay,province,city,county);
	}
}
