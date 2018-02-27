package com.jy.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jy.model.ChangeOrder;
import com.jy.model.CreateAgreement;
import com.jy.model.ShippingOrder;

public interface ChangeOrderDao {

	// 管理员--客户回单回单信息查询
	List<ShippingOrder> getBackOrderInfo(int rows, int page, String name,
			String phone_number, String type, String start_date,
			String end_date, String start_date1, String end_date1,
			String start_date2, String end_date2, String custom_name,
			String receipt, String address, String shipping_order_state,
			String shipperorder_id);

	// 信息员--客户回单信息查询
	public List<ShippingOrder> otherChangeOrderInfo(int rows, int page,
			String name, String phone_number, String type, String start_date,
			String end_date, String start_date1, String end_date1,
			String start_date2, String end_date2, String custom_name,
			String receipt, String address, String shipping_order_state,
			String shipperorder_id, @Param("userId") String id);

	// 供应商--客户回单信息查询
	public List<ShippingOrder> suppliersChangeOrderInfo(int rows, int page,
			String name, String phone_number, String type, String start_date,
			String end_date, String start_date1, String end_date1,
			String start_date2, String end_date2, String custom_name,
			String receipt, String address, String shipping_order_state,
			String shipperorder_id, @Param("suppliersId") String suppliersId,
			@Param("list") List<String> userAddress);

	// 司机--客户回单信息查询
	public List<ShippingOrder> driverChangeOrderInfo(int rows, int page,
			String name, String phone_number, String type, String start_date,
			String end_date, String start_date1, String end_date1,
			String start_date2, String end_date2, String custom_name,
			String receipt, String address, String shipping_order_state,
			String shipperorder_id, @Param("driverId") String driverId);

	// 供应商--客户回单信息查询总条数
	int getBackOrder(String name, String phone_number, String type,
			String start_date, String end_date, String start_date1,
			String end_date1, String start_date2, String end_date2,
			String custom_name, String receipt, String address,
			String shipping_order_state, String shipperorder_id);

	// 录单员--回单信息查询总条数
	int otherChangeOrderInfoCount(String name, String phone_number,
			String type, String start_date, String end_date,
			String start_date1, String end_date1, String start_date2,
			String end_date2, String custom_name, String receipt,
			String address, String shipping_order_state,
			String shipperorder_id, @Param("userId") String id);

	// 供应商--回单信息查询总条数
	int suppliersChangeOrderInfoCount(String name, String phone_number,
			String type, String start_date, String end_date,
			String start_date1, String end_date1, String start_date2,
			String end_date2, String custom_name, String receipt,
			String address, String shipping_order_state,
			String shipperorder_id, @Param("suppliersId") String suppliersId,
			@Param("list") List<String> userAddresss);

	// 司机--回单信息查询总条数
	int driverChangeOrderInfoCount(String name, String phone_number,
			String type, String start_date, String end_date,
			String start_date1, String end_date1, String start_date2,
			String end_date2, String custom_name, String receipt,
			String address, String shipping_order_state,
			String shipperorder_id, @Param("driverId") String driverId);

	// 录单员--回单处理已接受
	int otherDealBackOrder(@Param("array") String[] deal);

	// 供应商--回单信息返回
	int dealBackOrder(@Param("array") String[] deal);

	// 客户回单 处理成为未接受收
	int dealNotBackOrder(@Param("array") String[] deal);

	// 查询总数
	int getChangeOrder(String name, String phone_number, String type,
			String start_date, String end_date);

	// 查询司机
	List<ShippingOrder> getChangeOrderInfo(int rows, int page, String name,
			String phone_number, String type, String start_date, String end_date);

	// 删除
	int deleteChangeOrder(@Param("array") String[] del);

	// 保存
	int saveChangeOrder(ChangeOrder d);

	// 修改查询
	ChangeOrder getUpdateChangeOrder(String id);

	// 修改
	int updateChangeOrder(ChangeOrder d);

	// 修改
	List<ShippingOrder> getPlateNumberLength(String number);

	// 订单号查询协议类型
	CreateAgreement getAddOrderFee(String id);

	// 暂时没有用
	List<ShippingOrder> getShipOrderAll(String name, String phone_number,
			String type, String start_date, String end_date);

	// 添加中转费时，修改订单状态是否录入 is_update
	int updateOrder(String id);

	// 添加费用判断该订单是否已经被编辑费用：费用表是否有该订单信息
	int searchOrder(String id);

	// 订单发送消息页面订单查询
	List<ShippingOrder> getOrderInfo(int rows, int page, String num,
			String receipt, String phone, String send_type);

	// 订单发送消息页面订单查询总条数
	int getOrderInfoCount(String num, String receipt, String phone,
			String send_type);

	// 客户回单导出
	public List<ShippingOrder> outBackShipOrderFile(String user_id,
			String name, String type, String start_date, String end_date,
			String start_date1, String end_date1, String custom_name,
			String receipt, String address, String shipping_order_state,
			String shipperorder_id, @Param("array") String[] shiping_id);

	// 管理员--客户回单导出
	public List<ShippingOrder> outNewBackShipOrderFile(String user_id,
			String name, String type, String start_date, String end_date,
			String start_date1, String end_date1, String custom_name,
			String receipt, String address, String shipping_order_state,
			String shipperorder_id, @Param("array") String[] shiping_id);

	// 司机--客户回单导出
	public List<ShippingOrder> driverOutBackShipOrderFile(String user_id,
			String name, String type, String start_date, String end_date,
			String start_date1, String end_date1, String custom_name,
			String receipt, String address, String shipping_order_state,
			String shipperorder_id, @Param("array") String[] shiping_id,
			@Param("driverId") String driverId);

	// 供应商--客户回单导出
	public List<ShippingOrder> suppliersOutBackShipOrderFile(String user_id,
			String name, String type, String start_date, String end_date,
			String start_date1, String end_date1, String custom_name,
			String receipt, String address, String shipping_order_state,
			String shipperorder_id, @Param("array") String[] shiping_id,
			@Param("suppliersId") String suppliersId,
			@Param("listaddress") List<String> user_address);

	// 信息员--客户回单导出
	public List<ShippingOrder> otherOutBackShipOrderFile(String user_id,
			String name, String type, String start_date, String end_date,
			String start_date1, String end_date1, String custom_name,
			String receipt, String address, String shipping_order_state,
			String shipperorder_id, @Param("array") String[] shiping_id,
			@Param("customerId") String customerId);

	// 客户回单存储过程
	void callBackShipperOrderProcedure(String user_id);

}
