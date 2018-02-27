package com.jy.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jy.model.Aging;
import com.jy.model.Customer;
import com.jy.model.DeliveryCustomer;
import com.jy.model.Driver;
import com.jy.model.Remark;
import com.jy.model.RemarkRange;
import com.jy.model.RemarkType;
import com.jy.model.ShippingOrder;
import com.jy.model.User;

public interface RemarkMapDao {
	List<DeliveryCustomer> otherGetDeliveryPutOut(String remark_name,
			String remark_tel, String remark_address, String delivery_cus_name,
			@Param("array") String[] delivery_id, @Param("userId")String userID);

	List<DeliveryCustomer> supplersGetDelivery(String customer_name,
			String customer_tel, String customer_address,
			String delivery_cus_name, @Param("array") String[] delivery_id,
			@Param("list") List<String> address);

	List<DeliveryCustomer> getAdminDeliveryC(String remark_name,
			String remark_tel, String remark_address, String delivery_cus_name,
			@Param("array") String[] delivery_id, String User_address);

	List<DeliveryCustomer> getDriverDeliveryC(String remark_name,
			String remark_tel, String remark_address, String delivery_cus_name,
			@Param("array") String[] delivery_id,
			@Param("list") List<String> address);

	// 获取信息导出
	List<Customer> getOutPutAll(String customer_name, String customer_tel,
			String customer_address, @Param("array") String[] dataIds);

	// 项目部发货客户管理导出
	List<Customer> getOutpPutOther(String customer_name, String customer_tel,
			String customer_address, @Param("array") String[] dataIds,
			@Param("id") String id);

	// 时效提醒
	List<ShippingOrder> getShinpingAging_time();

	// 查询司机
	List<Driver> getDriver_id(String id);

	// 获取设备号id
	User getChannelId(String id);

	// 管理员--发货客户查询信息
	List<Customer> getRemarks(Integer page, Integer rows, String remark_name,
			String remark_tel, String remark_address);

	// 管理员--发货客户总条数
	int getCount(String remark_name, String remark_tel, String remark_address);

	// 管理员--发货客户查询信息
	List<Customer> supplierGetRemarks(Integer page, Integer rows, String remark_name,
							  String remark_tel, String remark_address,@Param("driverId") String driverId);
	// 管理员--发货客户总条数
	int supplierGetCount(String remark_name, String remark_tel, String remark_address,@Param("driverId") String driverId);

	// 信息员--发货客户查询信息
	List<Customer> otherGetCustomer(Integer page, Integer rows,
			String remark_name, String remark_tel, String remark_address,
			String didName);

	// 信息员--发货客户查询总条数
	int otherGetCustomerCount(String remark_name, String remark_tel,
			String remark_address, String didName);

	// 所有人--保存客户信息
	int saveCustomer(Customer customer);

	// 所有人--删除客户信息
	int deleteRemark(@Param("array") String[] id);

	// 检查编码是否重复
	int getcustomer_num(String id);

	// 获取到要更新的客户的详细信息
	Customer getCustomerbyid(String cid);

	// 更新发货客户信息
	int updateCustomer(Customer customer);

	// 管理员--时效管理查询信息
	List<Aging> getAgings(Integer page, Integer rows, String remark_name,
			String remark_address, String user_address);

	// 司机--时效管理查询信息
	List<Aging> getAgingsUser(Integer page, Integer rows, String remark_name,
			String remark_address, String user_address);

	// 供应商--时效管理查询信息
	List<Aging> suppliersGetAgingsUser(Integer page, Integer rows,
			String remark_name, String remark_address, String supplierID);

	// 信息员----时效管理查询信息
	List<Aging> otherGetAgings(Integer page, Integer rows, String remark_name,
			String remark_address, String user_address);

	// 管理员--时效管理查询总条数
	int getAgingCount(String customer_name, String customer_address,
			String user_address);

	// 司机--时效管理查询总条数
	int getAgingCountUser(String customer_name, String customer_address,
			String user_address);

	// 供应商--时效管理查询总条数
	int suppliersGetAgingsUserCount(String customer_name,
			String customer_address, String supplierID);

	// 信息员--时效管理查询总条数
	int otherGetAgingCount(String customer_name, String customer_address,
			String user_address);

	// 检查时效是否存在
	int saveCheckAging(Aging aging);

	// 保存时效信息
	int saveAging(Aging aging);

	// 删除时效信息
	int deleteAging(@Param("array") String[] id);

	// 获取要修改的时效信息
	Aging getsetAging(String aid);

	// 修改时效
	int updateAging(Aging aging);

	// 获取发货客户信息
	List<Customer> getCustomersByName(String name);

	// 管理员--收货客户信息查询
	List<DeliveryCustomer> getDeliveryC(Integer page, Integer rows,
			String remark_name, String remark_tel, String remark_address,
			String User_address, String delivery_cus_name);

	// 管理员--收货客户信息总条数
	int getDeliveryCon(String remark_name, String remark_tel,
			String remark_address, String User_address, String delivery_cus_name);

	// 司机--收货客户信息查询
	List<DeliveryCustomer> getDeliveryCustomersdd(Integer page, Integer rows,
			String remark_name, String remark_tel, String remark_address,
			@Param("list") List<String> address, String delivery_cus_name,@Param("driverId") String driverId);

	// 司机--收货客户信息查询总条数
	int getDeliveryCountdd(String remark_name, String remark_tel,
			String remark_address, @Param("list") List<String> address,
			String delivery_cus_name,@Param("driverId") String driverId);

	// 供应商--收货客户信息查询
	List<DeliveryCustomer> supplersGetDeliveryInfoddd(Integer i, Integer rows1,
			String customer_name, String customer_tel, String customer_address,
			@Param("list") List<String> address, String delivery_cus_name);

	// 供应商--收货客户信息查询总条数
	int supplersGetDeliveryCountddd(String customer_name, String customer_tel,
			String customer_address, @Param("list") List<String> address,
			String delivery_cus_name);

	// 信息员--收货客户信息查询
	List<DeliveryCustomer> otherGetDeliveryC(Integer page, Integer rows,
			String remark_name, String remark_tel, String remark_address,
			String userID, String delivery_cus_name);

	// 信息员--收货客户信息查询总条数
	int otherGetDeliveryCon(String remark_name, String remark_tel,
			String remark_address, String userID, String delivery_cus_name);

	// 获取司机对应的终到位置
	List<String> getAddressDriver(String driverId);

	// 获取供应商对应的终到位置
	List<String> getAddressSupperlies(String SupperliesId);

	// 检查客户名重复
	int getcustomer_name(String customer_name);

	// 添加收货客户信息
	int saveDeliveryCustomer(DeliveryCustomer customer);

	// 检查发货客户节点标注信息
	int checkRemark(String id);

	// 删除收货客户信息
	int deleteDeliveryCustomer(@Param("array") String[] id);

	// 更新要修改的收货客户信息
	int updateDeliveryCustomer(DeliveryCustomer customer);

	// 更新收货客户定位信息
	int updateLocationRemarkCustomer(DeliveryCustomer customer);

	// 更新一下标注
	int updateRemark(Customer remark);

	// 检查标注名
	int checkName(String name);

	// 添加一个标记
	int addRemark(Customer remark);

	// 导入发货客户
	int customerExcelImport(List<Customer> tlist);

	// 获取所有的发货客户信息
	List<Customer> getAllCustomers();

	// 收货客户导入
	int deliveryExcelInfo(List<DeliveryCustomer> tlist);

	List<RemarkType> getTypes();

	List<RemarkRange> getRange();

	Customer getRemark(String id);

	List<Remark> getRemarkName(String name, String state);

	List<DeliveryCustomer> getDeliveryCustomers(Integer page, Integer rows,
			String remark_name, String remark_tel, String remark_address,
			String driverId);

	int getDeliveryCount(String remark_name, String remark_tel,
			String remark_address, String driverId);

	String getModifyPeople(Integer people);

	String getOneRange(String id);

	DeliveryCustomer getDeliveryCustomerbyid(String cid);

	List<DeliveryCustomer> getDeliveryCustomerbyname(String cid,
			String send_mechanism);

	// ----------------收货客户
	List<DeliveryCustomer> supplersGetDeliveryInfo(Integer page, Integer rows,
			String remark_name, String remark_tel, String remark_address,
			String supplersId);

	int supplersGetDeliveryCount(String remark_name, String remark_tel,
			String remark_address, String supplersId);

	// 时效管理--新增时效时获取省市县全名
	String getAgingCityInfo(int aging_province, int aging_city, int aging_county);

	List<DeliveryCustomer> getUpdateLocationRemarkCustomer();

}
