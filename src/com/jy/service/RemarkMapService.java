package com.jy.service;

import java.util.List;

import com.jy.model.Aging;
import com.jy.model.Customer;
import com.jy.model.DeliveryCustomer;
import com.jy.model.Remark;
import com.jy.model.RemarkRange;
import com.jy.model.RemarkType;
import com.jy.model.ShippingOrder;
import com.jy.model.User;

public interface RemarkMapService {
	/**
	 *  收货客户导出
	 * @param remark_name
	 * @param remark_tel
	 * @param remark_address
	 * @param User_address
	 * @param delivery_cus_name
	 * @param user
	 * @param delivery_id
	 * @return
	 */
	public List<DeliveryCustomer> outPutDeliveryCustomers(String remark_name,
			String remark_tel, String remark_address, String User_address,
			String delivery_cus_name, User user, String[] delivery_id);

	/**
	 * 发货客户导出信息
	 */
	public List<Customer> getOutPutCustomers(String customer_name,
			String customer_tel, String customer_address, User user,
			String[] dataIds);

	/**
	 * 时效提醒
	 */
	public List<ShippingOrder> getShippingAging();

	public User getChannelId(String id);

	/**
	 * 查询发货客户信息记录
	 * 
	 * @param page
	 * @param rows
	 * @param remark_name
	 * @param remark_tel
	 * @param remark_address
	 * @return
	 */
	public List<Customer> getRemarks(Integer page, Integer rows,
			String remark_name, String remark_tel, String remark_address,
			User user);

	/**
	 * 查询发货客户信息总条数
	 * 
	 * @param remark_name
	 * @param remark_tel
	 * @param remark_address
	 * @return
	 */
	public int getCount(String remark_name, String remark_tel,
			String remark_address, User user);

	/**
	 * 保存客户信息
	 * 
	 * @param customer
	 * @return
	 */
	public int saveCustomer(Customer customer);

	/**
	 * 检查编码是否重复
	 * 
	 * @param id
	 * @return
	 */
	public int getcustomer_num(String id);

	/**
	 * 获取到要更新的客户信息
	 * 
	 * @param cid
	 * @return
	 */
	public Customer getCustomerbyid(String cid);

	/**
	 * 更新发货客户信息
	 * 
	 * @param customer
	 * @return
	 */
	public int updateCustomer(Customer customer);

	/**
	 * 时效管理查询信息总条数
	 * 
	 * @param customer_name
	 * @param customer_address
	 * @param user_address
	 * @param user
	 * @return
	 */
	public int getAgingCount(String customer_name, String customer_address,
			String user_address, User user);

	/**
	 * 时效管理查询信息总记录
	 * 
	 * @param page
	 * @param rows
	 * @param remark_name
	 * @param remark_address
	 * @param user_address
	 * @param user
	 * @return
	 */
	public List<Aging> getAgings(Integer page, Integer rows,
			String remark_name, String remark_address, String user_address,
			User user);

	/**
	 * 检查时效是否存在
	 * 
	 * @param aging
	 * @return
	 */
	public boolean saveCheckAging(Aging aging);

	/**
	 * 保存时效信息
	 * 
	 * @param aging
	 * @return
	 */
	public int saveAging(Aging aging);

	/**
	 * 删除时效信息
	 * 
	 * @param id
	 * @return
	 */
	public int deleteAging(String[] id);

	/**
	 * 获取修改的时效信息
	 * 
	 * @param aid
	 * @return
	 */
	public Aging getsetAging(String aid);

	/**
	 * 修改时效
	 * 
	 * @param aging
	 * @return
	 */
	public int updateAging(Aging aging);

	/**
	 * 获取发货客户信息
	 * 
	 * @param name
	 * @return
	 */
	public List<Customer> getCustomersByName(String name);

	/**
	 * 收货客户查询信息
	 * 
	 * @param page
	 * @param rows
	 * @param remark_name
	 * @param remark_tel
	 * @param remark_address
	 * @param User_address
	 * @param delivery_cus_name
	 * @param user
	 * @return
	 */
	public List<DeliveryCustomer> getDeliveryC(Integer page, Integer rows,
			String remark_name, String remark_tel, String remark_address,
			String User_address, String delivery_cus_name, User user);

	/**
	 * 收货客户查询总条数
	 * 
	 * @param remark_name
	 * @param remark_tel
	 * @param remark_address
	 * @param User_address
	 * @param delivery_cus_name
	 * @param user
	 * @return
	 */
	public int getDeliveryCon(String customer_name, String customer_tel,
			String customer_address, String User_address,
			String delivery_cus_name, User user);

	/**
	 * 检查客户名重复
	 * 
	 * @param customer_name
	 * @return
	 */
	public int getcustomer_name(String customer_name);

	/**
	 * 添加收货客户信息
	 * 
	 * @param customer
	 * @return
	 */
	public int saveDeliveryCustomer(DeliveryCustomer customer);

	/**
	 * 检查发货客户标注节点信息
	 * 
	 * @param id
	 * @return
	 */
	public boolean checkRemark(String id);

	/**
	 * 删除收货客节点信息
	 * 
	 * @param id
	 * @return
	 */
	public boolean deleteRemark(String[] id);

	/**
	 * 删除发货客户信息
	 * 
	 * @param id
	 * @return
	 */
	public boolean deleteDeliveryCustomer(String[] id);

	/**
	 * 更新要修改的收货客户信息
	 * 
	 * @param customer
	 * @return
	 */
	public int updateDeliveryCustomer(DeliveryCustomer customer);

	/**
	 * 收货客户定位
	 * 
	 * @param customer
	 * @return
	 */
	public int updateLocationRemarkCustomer(DeliveryCustomer customer);

	/**
	 * 更新一个标注
	 * 
	 * @param remark
	 * @return
	 */
	public boolean updateRemark(Customer remark);

	/**
	 * 检查一个标注名
	 * 
	 * @param name
	 * @return
	 */
	public boolean checkName(String name);

	/**
	 * 添加一个标记
	 * 
	 * @param remark
	 * @return
	 */
	public boolean addRemark(Customer remark);

	/**
	 * 导入发货客户
	 * 
	 * @param tlist
	 * @return
	 */
	int customerExcelImport(List<Customer> tlist);

	/**
	 * 获取所有的发货客户信息
	 * 
	 * @return
	 */
	List<Customer> getAllCustomers();
	
	/**
	 * 收货客户导入
	 * @param tlist
	 * @return
	 */
	int deliveryExcelInfo(List<DeliveryCustomer> tlist);

	public List<RemarkType> getTypes();

	public List<RemarkRange> getRange();

	public List<Remark> getRemarkName(String name, String state);

	public Customer getRemark(String id);

	public List<DeliveryCustomer> getDeliveryCustomers(Integer page,
			Integer rows, String remark_name, String remark_tel,
			String remark_address, String User_address);

	public int getDeliveryCount(String remark_name, String remark_tel,
			String remark_address, String User_address);

	public String getModifyPeople(Integer people);

	public String getOneRange(String id);

	public DeliveryCustomer getDeliveryCustomerbyid(String cid);

	public List<DeliveryCustomer> getDeliveryCustomerbyname(String cid,
			String send_mechanism);

	// ------------接货客户
	public int supplersGetDeliveryCount(String customer_name,
			String customer_tel, String customer_address, String suppliers_id);

	public List<DeliveryCustomer> supplersGetDeliveryInfo(Integer i,
			Integer rows1, String customer_name, String customer_tel,
			String customer_address, String suppliers_id);

	/**
	 * 时效管理--新增时效时获取省市县全名
	 *
	 * @param aging_province
	 * @param aging_city
	 * @param aging_county
	 * @return
	 */
	String getAgingCityInfo(int aging_province, int aging_city, int aging_county);

	List<DeliveryCustomer> getUpdateLocationRemarkCustomer();

}
