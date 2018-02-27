package com.jy.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jy.model.City_info;
import com.jy.model.Customer;
import com.jy.model.JySuppliers;
import com.jy.model.OrderHistory;
import com.jy.model.ShippingOrder;
import com.jy.model.User;

public interface SuppliersService {
	/**
	 * 供应商的导出
	 */
	public List<JySuppliers> getOutPutCustomers(String name, String phone,
			String address, String id, String suppliers_customer, User user,
			String[] dataIds);

	/**
	 * 供应商管理查询信息
	 * 
	 * @param rows
	 * @param page
	 * @param name
	 * @param phone
	 * @param address
	 * @param id
	 * @param suppliers_customer
	 * @return
	 */
	public List<JySuppliers> getSuppUser(int rows, int page, String name,
			String phone, String address, String id, String suppliers_customer,
			User user);

	/**
	 * 添加车辆类型
	 * 
	 * @param jysuppliers
	 */
	public List<String> addCarType();

	/**
	 * 供应商添加到司机中
	 */
	public void supToDriver(JySuppliers jysuppliers);

	/**
	 * 修改供应商信息时，绑定司机的改变
	 */
	public void supupdateDriver(JySuppliers j);

	/**
	 * 供应商管理查询总条数
	 * 
	 * @param name
	 * @param phone
	 * @param address
	 * @param id
	 * @param suppliers_customer
	 * @return
	 */
	public int getSuppCounUser(String name, String phone, String address,
			String id, String suppliers_customer, User user);

	/**
	 * 添加供应商信息
	 * 
	 * @param suppliers
	 * @return
	 */
	public int saveSuppliers(JySuppliers suppliers);

	/**
	 * 添加供应商信息保存省市县
	 * 
	 * @param jyslist
	 * @return
	 */
	public int saveSupplersCityInfo(List<JySuppliers> jyslist);

	/**
	 * 添加供应商信息保存图片
	 * 
	 * @param images
	 * @param suppId
	 * @return
	 */
	public int updateSuppliersImage(String images, String suppId);

	/**
	 * 删除供应商的省市县
	 * 
	 * @param pid
	 * @return
	 */
	public int deleteSuppliersCityInfo(String[] pid);

	/**
	 * 查询要删除的供应商信息
	 * 
	 * @param cid
	 * @return
	 */
	public JySuppliers getUpdateSuppliers(String cid);

	/**
	 * 删除供应商信息
	 * 
	 * @param pid
	 * @return
	 */
	public int deletesuppliers(String[] pid);

	/**
	 * 删除相应的司机
	 */
	public int deleteDriver(String[] pid);

	/**
	 * 更新供应商信息
	 * 
	 * @param jyuppliers
	 * @return
	 */
	public int updateSuppliers(JySuppliers jyuppliers);

	/**
	 * select2查询供应商信息
	 * 
	 * @return
	 */
	public List<JySuppliers> getSpriadd();

	/**
	 * 获取供应商的省市县
	 * 
	 * @param jysId
	 * @return
	 */
	public List<City_info> getCityInfo(String jysId);

	/**
	 * 供应商结算 查询信息
	 * 
	 * @param rows
	 * @param page
	 * @param suppliersName
	 * @param suppliersSendTime
	 * @param suppliersEndTime
	 * @param endAddress
	 * @return
	 */
	public List<JySuppliers> getSupplierss(int rows, int page,
			String suppliersName, String suppliersSendTime,
			String suppliersEndTime, String endAddress, User user);

	/**
	 * 供应商结算 查询总条数
	 * 
	 * @param suppliersName
	 * @param suppliersSendTime
	 * @param suppliersEndTime
	 * @param endAddress
	 * @return
	 */
	public int getSupplierCount(String suppliersName, String suppliersSendTime,
			String suppliersEndTime, String endAddress, User user);

	/**
	 * 供应商结算查询订单
	 * 
	 * @param rows
	 * @param page
	 * @param pid
	 * @param shiping_order_num
	 * @param end_address
	 * @param custom_name
	 * @param receipt_name
	 * @param receipt_tel
	 * @param send_mechanism
	 * @param send_time
	 * @param time
	 * @param timeId
	 * @param driverId
	 * @param shipperorder_id
	 * @param clearing_state
	 * @return
	 */
	public List<ShippingOrder> getShiOreder(int rows, int page, String pid,
			String shiping_order_num, String end_address, String custom_name,
			String receipt_name, String receipt_tel, String send_mechanism,
			String send_time, String time, String timeId,
			@Param("array") String[] driverId, String shipperorder_id,
			String clearing_state);

	/**
	 * 供应商结算查询订单总条数
	 * 
	 * @param pid
	 * @param shiping_order_num
	 * @param end_address
	 * @param custom_name
	 * @param receipt_name
	 * @param receipt_tel
	 * @param send_mechanism
	 * @param send_time
	 * @param time
	 * @param timeId
	 * @param driverId
	 * @param shipperorder_id
	 * @param clearing_state
	 * @return
	 */
	public int getShiOrederCount(String pid, String shiping_order_num,
			String end_address, String custom_name, String receipt_name,
			String receipt_tel, String send_mechanism, String send_time,
			String time, String timeId, @Param("array") String[] driverId,
			String shipperorder_id, String clearing_state);

	/**
	 * 供应商结算 更新结算状态
	 * 
	 * @param driverId
	 * @param suppliersSendTime
	 * @param suSendTime
	 * @return
	 */
	public int settleSupp(@Param("array") String[] driverId,
			String suppliersSendTime, String suSendTime);

	/**
	 * 保存历史记录供应商结算信息
	 * 
	 * @param order
	 * @return
	 */
	public int saveOrderSupp(List<OrderHistory> order);

	/**
	 * 供应商结算获取结算订单
	 * 
	 * @param driverId
	 * @param suppliersSendTime
	 * @param suSendTime
	 * @return
	 */
	public List<ShippingOrder> getShi(String[] driverId,
			String suppliersSendTime, String suSendTime);

	/**
	 * 没有用了
	 * 
	 * @param suppliersName
	 * @param suppliersSendTime
	 * @param suppliersEndAddress
	 * @param suppliersSendMechanism
	 * @return
	 */
	public JySuppliers getClearing(String suppliersName,
			String suppliersSendTime, String suppliersEndAddress,
			String suppliersSendMechanism);

	/**
	 * 获取项目部的信息
	 * 
	 * @param customerId
	 * @return
	 */
	public Customer getCustomer(@Param("customerId") String customerId);

	/**
	 * 获取省市县多选
	 * 
	 * @param province
	 * @param city
	 * @param county
	 * @return
	 */
	public List<City_info> getCity_Info(List<Integer> province,
			List<Integer> city, List<Integer> county);

	// 获取省市名称
	public List<String> getCity_Info_name(@Param("province") List<Integer> province,
			@Param("city") List<Integer> city,
			@Param("county") List<Integer> county);

}
