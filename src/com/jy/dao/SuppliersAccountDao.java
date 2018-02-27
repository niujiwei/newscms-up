package com.jy.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jy.model.City_info;
import com.jy.model.Customer;
import com.jy.model.JySuppliers;
import com.jy.model.OrderHistory;
import com.jy.model.ShippingOrder;

public interface SuppliersAccountDao {

	// 管理员--供应商信息结算查询
	public List<JySuppliers> getSuppliers(int rows, int page,
			String suppliersName, String suppliersSendTime,
			String suppliersEndTime, String endAddress);

	// 供应商--供应商信息结算查询
	public List<JySuppliers> supplierGetSuppliers(int rows, int page,
			String suppliersName, String suppliersSendTime,
			String suppliersEndTime, String endAddress,
			@Param("supplierId") String supplierId);

	// 录单员--供应商结算信息查询
	public List<JySuppliers> otherGetSupplierss(int rows, int page,
			String suppliersName, String suppliersSendTime,
			String suppliersEndTime, String endAddress,
			@Param("user_id") String user_id);

	// 管理员--供应商结算信息查询
	public int getSuppliersCount(String suppliersName,
			String suppliersSendTime, String suppliersEndTime, String endAddress);

	// 管理员--供应商结算信息查询
	public int suppliersGetSuppliersCount(String suppliersName,
			String suppliersSendTime, String suppliersEndTime, String endAddress,@Param("supplierId") String supplierId);

	// 供应商结算查询订单
	public List<ShippingOrder> getShiOreder(int rows, int page, String pid,
			String shiping_order_num, String end_address, String custom_name,
			String receipt_name, String receipt_tel, String send_mechanism,
			String send_time, String time, String timeId,
			@Param("array") String[] driverId, String shipperorder_id,
			String clearing_state);

	// 供应商结算查询查询总条数
	public int getShiOrederCount(String pid, String shiping_order_num,
			String end_address, String custom_name, String receipt_name,
			String receipt_tel, String send_mechanism, String send_time,
			String time, String timeId, @Param("array") String[] driverId,
			String shipperorder_id, String clearing_state);

	// 录单员--供应商结算查询总条数
	public int otherGetSupplierCount(String suppliersName,
			String suppliersSendTime, String suppliersEndTime,
			String endAddress, @Param("user_id") String user_id);

	// 供应商信息导出合计
	public JySuppliers getOutClass(String suppliersName,
			String suppliersSendTime, String suSendTime);

	// 供应商结算导出订单信息
	public List<ShippingOrder> getOutSuppOrder(String suppliersName,
			String suppliersSendTime, String suSendTime, String send_mechanism,
			String supplierId);

	// 供应商信息导出合计
	public ShippingOrder getOutHeji(String suppliersName,
			String suppliersSendTime, String suSendTime, String send_mechanism,
			String supplierId);

	// 供应商结算 更新结算状态
	public int settleSupp(@Param("array") String[] driverId,
			String suppliersSendTime, String suSendTime);

	// 保存历史记录供应商结算信息
	public int saveOrderSuppli(List<OrderHistory> order);

	// 获取项目部的信息
	public Customer getCustomer(@Param("customerId") String customerId);

	// 供应商结算获取结算订单
	public List<ShippingOrder> getShi(@Param("array") String[] driverId,
			String suppliersSendTime, String suSendTime);

	// 没有用了
	public JySuppliers getClearing(String suppliersName,
			String suppliersSendTime, String suppliersEndAddress,
			String suppliersSendMechanism);

	// 没有用了没有用了
	public ShippingOrder getClearingState(String clearing_state);

}
