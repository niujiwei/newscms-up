package com.jy.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jy.model.ChangeOrder;
import com.jy.model.CreateAgreement;
import com.jy.model.ShippingOrder;
import com.jy.model.User;

public interface ChangeOrderInfoService {

	/**
	 * 回单管理 客户回单查询订单
	 * 
	 * @param rows
	 * @param page
	 * @param name
	 * @param phone_number
	 * @param type
	 * @param start_date
	 * @param end_date
	 * @return
	 */
	public List<ShippingOrder> getBackOrderInfo(int rows, int page,
			String name, String phone_number, String type, String start_date,
			String end_date, String start_date1, String end_date1,
			String start_date2, String end_date2, String custom_name,
			String receipt, String address, String shipping_order_state,
			String shipperorder_id, User user);

	/**
	 * 客户回单信息查询总条数
	 * 
	 * @param name
	 * @param phone_number
	 * @param type
	 * @param start_date
	 * @param end_date
	 * @param start_date1
	 * @param end_date1
	 * @param start_date2
	 * @param end_date2
	 * @param custom_name
	 * @param receipt
	 * @param address
	 * @param shipping_order_state
	 * @param shipperorder_id
	 * @return
	 */
	public int getBackOrder(String name, String phone_number, String type,
			String start_date, String end_date, String start_date1,
			String end_date1, String start_date2, String end_date2,
			String custom_name, String receipt, String address,
			String shipping_order_state, String shipperorder_id, User user);

	/**
	 * 客户回单导出
	 * 
	 * @param user_id
	 * @param name
	 * @param type
	 * @param start_date
	 * @param end_date
	 * @param start_date1
	 * @param end_date1
	 * @param custom_name
	 * @param receipt
	 * @param address
	 * @param shipping_order_state
	 * @param shipperorder_id
	 * @param shiping_id
	 * @return
	 */
	public List<ShippingOrder> outBackShipOrderFile(User user,
			String name, String type, String start_date, String end_date,
			String start_date1, String end_date1, String custom_name,
			String receipt, String address, String shipping_order_state,
			String shipperorder_id, String[] shiping_id);

	/**
	 * 所有人员的查询 没有用到
	 * 
	 * @param rows
	 * @param page
	 * @param name
	 * @param phone_number
	 * @param type
	 * @param start_date
	 * @param end_date
	 * @return
	 */
	public List<ShippingOrder> getChangeOrderInfo(int rows, int page,
			String name, String phone_number, String type, String start_date,
			String end_date);

	/**
	 * 没有用到
	 * 
	 * @param name
	 * @param phone_number
	 * @param type
	 * @param start_date
	 * @param end_date
	 * @return
	 */
	public int getChangeOrder(String name, String phone_number, String type,
			String start_date, String end_date);

	/**
	 * 删除
	 * 
	 * @param del
	 * @return
	 */
	public int deleteChangeOrder(String[] del);

	/**
	 * 没有用到保存
	 * 
	 * @param d
	 * @return
	 */
	public int saveChangeOrder(ChangeOrder d);

	/**
	 * 没有用到修改查询
	 * 
	 * @param id
	 * @return
	 */
	public ChangeOrder getUpdateChangeOrder(String id);

	/**
	 * 没有用到 用户修改
	 * 
	 * @param d
	 * @return
	 */
	public int updateChangeOrder(ChangeOrder d);

	/**
	 * 
	 * select 查询
	 */
	List<ShippingOrder> getPlateNumberLength(String number);

	/**
	 * 重复订单号查询
	 * 
	 * @param id
	 * @return
	 */
	CreateAgreement getAddOrderFee(String id);

	List<ShippingOrder> getShipOrderAll(String name, String phone_number,
			String type, String start_date, String end_date);// 导出查询

	/**
	 * 添加中转费时，修改订单状态是否录入 is_update
	 * 
	 * @param id
	 * @return
	 */
	public int updateOrder(String id);

	/**
	 * 添加费用判断该订单是否已经被编辑费用：费用表是否有该订单信息
	 * 
	 * @param id
	 * @return
	 */
	public int searchOrder(String id);

	/**
	 * 客户回单 供应商回单返回信息
	 * 
	 * @param deal
	 * @return
	 */
	public int dealBackOrder(@Param("array") String[] deal);// 处理成接收

	/**
	 * 客户回单 录单员订单处理
	 * 
	 * @param deal
	 * @return
	 */
	public int otherDealBackOrder(@Param("array") String[] deal);

	public int dealNotBackOrder(@Param("array") String[] deal);// 处理成为未收

	/**
	 * 订单发送消息页面订单查询
	 * 
	 * @param rows
	 * @param page
	 * @param num
	 * @param receipt
	 * @param phone
	 * @param send_type
	 * @return
	 */
	public List<ShippingOrder> getOrderInfo(int rows, int page, String num,
			String receipt, String phone, String send_type);

	/**
	 * 订单发送信息页面订单查询
	 * 
	 * @param num
	 * @param receipt
	 * @param phone
	 * @param send_type
	 * @return
	 */
	public int getOrderInfoCount(String num, String receipt, String phone,
			String send_type);

}
