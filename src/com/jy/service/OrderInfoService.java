package com.jy.service;

import java.util.List;

import com.jy.model.*;

public interface OrderInfoService {

	int DeliveryExcelInfo(List<DeliveryCustomer> tlist);

	public List<PositionInfo> getAddres(String province, String city,
			String count);

	// 所有人员的查询
	public List<ShippingOrder> getOrderCustomInfo(int rows, int page,
			String name, String start_date1, String end_date2,
			String start_date, String end_date, String car_number, String type);

	// public List<Driver> getDriver(String driver_name);
	public int getOrderCustom(String name, String start_date1,
			String end_date2, String start_date, String end_date,
			String car_number, String type);

	// 删除
	public int deleteOrderCustom(String[] del);

	/**
	 * 根据客户车辆信息id删除相关订单信息
	 * 
	 * @param pid
	 * @return
	 */
	public int deleteOrderMsg(String[] pid);

	// xingjia
	int customerExcel(List<Customer> tlist);

	/**
	 * 查询是否有关联的订单
	 * 
	 * @param pid
	 * @return
	 */
	public int OrderMsgNum(String[] pid);

	// 保存
	public int saveOrderCustom(OrderCustom d);

	// 修改查询
	public OrderCustom getUpdateOrderCustom(String id);

	// 用户修改
	public int updateOrderCustom(OrderCustom d);

	/*
	 * select 查询
	 */
	List<OrderCustom> getPlateNumber(String number);

	/**
	 * 客户名称查询
	 * 
	 * @param name
	 * @return
	 */
	public List<OrderCustom> getCustomer(String name);

	public List<OrderCustom> getOrderCustomAll(String name,
			String phone_number, String type, String start_date, String end_date);// 导出查询

	/**
	 * 查询重复订单号
	 * 
	 * @param id
	 * @return
	 */
	public List<ShippingOrder> getAorder();

	/**
	 * 查询订单所以信息页面展示
	 * 
	 * @param pid
	 * @return
	 */
	public ShippingOrder getShipOrderMsg(String pid);

	/**
	 * 修改订单备注
	 * 
	 * @param pid
	 * @param notes
	 * @return
	 */
	public int getRemakesOrder(String pid, String notes, String remarks_orde,
			String remarks_date);

	/**
	 * 首页订单页面显示
	 * 
	 * @param name
	 * @param phone_number
	 * @param type
	 * @param start_date
	 * @param end_date
	 * @param pid
	 * @param phone
	 * @param order_state
	 * @param pay_type
	 * @param send_address
	 * @return
	 */
	int getShipOrder(String name, String phone_number, String type,
			String start_date, String end_date, String pid, String phone,
			String order_state, String pay_type, String send_address,
			String goods_name);// 查询总数

	List<ShippingOrder> getShipOrderInfo(int rows, int page, String name,
			String phone_number, String type, String start_date,
			String end_date, String pid, String phone, String order_state,
			String pay_type, String send_address, String goods_name); // 查询司机

	/**
	 * 订单修改记录查询
	 * 
	 * @param pid
	 * @return
	 */
	int getShipOrder1(String pid);// 查询总数

	/**
	 * 运单录入 导入运单保存数据
	 * 
	 * @param tlist
	 * @return
	 */
	public int saveimp(List<ShippingOrder> tlist);

	/**
	 * 运单导入获取时效
	 * 
	 * @param id
	 * @param address
	 * @param inputTime
	 * @return
	 */
	public Aging getNewAging(String id, String address, String inputTime);

	List<ShippingOrder> getShipOrderInfo1(int rows, int page, String pid); // 查询司机

	public Aging getAging(String id, String address);// 获取时效

	public int updateAging(String id, String aging, String agingDay);// 更新时效

	ShippingOrder getShipOrderInfos(String order_id);

	/**
	 * 到达订单历史表存储
	 * 
	 * @param order
	 * @return
	 */
	public int saveOrderHistory(List<OrderHistory> order);
	
	/**
	 * 通过订单号和出货订单号判断运单是否存在
	 * @param orderNum
	 * @param shiping_order_goid
	 * @return
	 */
	public int bGetOrderCount(String orderNum,String shiping_order_goid);

	/**
	 * 根据终到位置获取省市县
	 * @param endAddress
	 * @return
	 */
	List<City_info> getCityInfoByEndAddress(String endAddress);

	/**
	 * 更新时效和终到位置
	 * @param id
	 * @param aging
	 * @param agingDay
	 * @param province
	 * @param city
	 * @param county
	 * @return
	 */
	int updateAgingAndCityInfo(String id, String aging, String agingDay,Integer province,
									  Integer city,Integer county);// 更新时效

}
