package com.jy.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.omg.PortableInterceptor.USER_EXCEPTION;
import org.springframework.stereotype.Component;

import com.jy.common.UUIDUtils;
import com.jy.dao.RemarkMapDao;
import com.jy.model.Aging;
import com.jy.model.Customer;
import com.jy.model.DeliveryCustomer;
import com.jy.model.Driver;
import com.jy.model.Remark;
import com.jy.model.RemarkRange;
import com.jy.model.RemarkType;
import com.jy.model.ShippingOrder;
import com.jy.model.User;
import com.jy.service.RemarkMapService;

@Component
public class RemarkMapServiceImpl implements RemarkMapService {
	@Resource
	private RemarkMapDao rmdao;

	// 收货客户导出
	public List<DeliveryCustomer> outPutDeliveryCustomers(String remark_name,
			String remark_tel, String remark_address, String User_address,
			String delivery_cus_name, User user, String[] dataIds) {
		List<DeliveryCustomer> list = null;
		if (user != null) {
			if (user.getFlag().equals("0")) {// 管理员
				list = rmdao.getAdminDeliveryC(remark_name, remark_tel,
						remark_address, delivery_cus_name, dataIds,
						user.getUser_address());
			} else if (user.getFlag().equals("1")) {// 司机
				List<String> addresslist = rmdao.getAddressDriver(user
						.getDriver_id());
				list = rmdao
						.getDriverDeliveryC(remark_name, remark_tel,
								remark_address, delivery_cus_name, dataIds,
								addresslist);

			} else if (user.getFlag().equals("2")) {// 供应商
				List<String> addresslist = rmdao.getAddressSupperlies(user
						.getSuppliers_id());
				list = rmdao
						.supplersGetDelivery(remark_name, remark_tel,
								remark_address, delivery_cus_name, dataIds,
								addresslist);
			} else if (user.getFlag().equals("3") || user.getFlag().equals("4")) {// 信息员
				list = rmdao.otherGetDeliveryPutOut(remark_name, remark_tel,
						remark_address, delivery_cus_name, dataIds,
						user.getId());

			}

		}

		return list;

	};

	//发货客户导出
	public List<Customer> getOutPutCustomers(String customer_name,
			String customer_tel, String customer_address, User user,
			String[] dataIds) {
		List<Customer> list = null;
		if (user != null) {
			if (user.getFlag().equals("0")) {// 管理员
				list = rmdao.getOutPutAll(customer_name, customer_tel,
						customer_address, dataIds);
			} else if (user.getFlag().equals("1")) {// 司机
				list = rmdao.getOutPutAll(customer_name, customer_tel,
						customer_address, dataIds);
			} else if (user.getFlag().equals("2")) {// 供应商
				list = rmdao.getOutPutAll(customer_name, customer_tel,
						customer_address, dataIds);
			} else if (user.getFlag().equals("3") || user.getFlag().equals("4")) {// 信息员
				list = rmdao.getOutpPutOther(customer_name, customer_tel,
						customer_address, dataIds, user.getId());
			}
		}
		return list;
	};

	// 时效提醒
	public List<ShippingOrder> getShippingAging() {
		return rmdao.getShinpingAging_time();
	};

	public User getChannelId(String id) {
		List<Driver> Driver_id = rmdao.getDriver_id(id);

		return rmdao.getChannelId(Driver_id.get(0).getDriver_id());
	};

	// 查询发货客户信息
	public List<Customer> getRemarks(Integer page, Integer rows,
			String remark_name, String remark_tel, String remark_address,
			User user) {
		List<Customer> list = null;
		if (user != null) {
			if (user.getFlag().equals("0")) {// 管理员
				list = rmdao.getRemarks(page, rows, remark_name, remark_tel,
						remark_address);
			} else if (user.getFlag().equals("1")) {// 司机
				list = rmdao.supplierGetRemarks(page, rows, remark_name, remark_tel,
						remark_address,user.getDriver_id());
			} else if (user.getFlag().equals("2")) {// 供应商
				list = rmdao.supplierGetRemarks(page, rows, remark_name, remark_tel,
						remark_address,user.getDriver_id());
			} else if (user.getFlag().equals("3") || user.getFlag().equals("4")) {// 信息员
				list = rmdao.otherGetCustomer(page, rows, remark_name,
						remark_tel, remark_address, user.getId());
			}
		}

		return list;
	}

	// 查询发货客户信息总条数
	public int getCount(String remark_name, String remark_tel,
			String remark_address, User user) {
		int total = 0;
		if (user != null) {
			if (user.getFlag().equals("0")) {// 管理员
				total = rmdao.getCount(remark_name, remark_tel, remark_address);

			} else if (user.getFlag().equals("1")) {// 司机
				total = rmdao.supplierGetCount(remark_name, remark_tel, remark_address,user.getDriver_id());
			} else if (user.getFlag().equals("2")) {// 供应商
				total = rmdao.supplierGetCount(remark_name, remark_tel, remark_address,user.getDriver_id());
			} else if (user.getFlag().equals("3") || user.getFlag().equals("4")) {// 信息员
				total = rmdao.otherGetCustomerCount(remark_name, remark_tel,
						remark_address, user.getId());
			}
		}

		return total;
	}

	// 保存客户信息
	public int saveCustomer(Customer customer) {
		customer.setCustomer_id(UUIDUtils.getUUID());
		return rmdao.saveCustomer(customer);
	}

	// 检查编码是否重复
	public int getcustomer_num(String id) {
		return rmdao.getcustomer_num(id);
	}

	// 获取到要更新的客户的详细信息
	public Customer getCustomerbyid(String cid) {
		return rmdao.getCustomerbyid(cid);
	}

	// 更新发货客户信息
	public int updateCustomer(Customer customer) {
		return rmdao.updateCustomer(customer);
	}

	// 时效管理查询总记录
	public List<Aging> getAgings(Integer page, Integer rows,
			String remark_name, String remark_address, String user_address,
			User user) {
		List<Aging> list = null;
		if (user != null) {
			if (user.getFlag().equals("0")) {// 管理员
				list = rmdao.getAgings(page, rows, remark_name, remark_address,
						user_address);

			} else if (user.getFlag().equals("1")) {// 司机
				list = rmdao.getAgingsUser(page, rows, remark_name,
						remark_address, user.getDriver_id());

			} else if (user.getFlag().equals("2")) {// 供应商
				list = rmdao.suppliersGetAgingsUser(page, rows, remark_name,
						remark_address, user.getSuppliers_id());

			} else if (user.getFlag().equals("3") || user.getFlag().equals("4")) {// 信息员
				list = rmdao.otherGetAgings(page, rows, remark_name,
						remark_address, user.getId());
			}

		}

		return list;
	}

	// 时效管理查询总条数
	public int getAgingCount(String customer_name, String customer_address,
			String user_address, User user) {
		int total = 0;
		if (user != null) {
			if (user.getFlag().equals("0")) {// 管理员
				total = rmdao.getAgingCount(customer_name, customer_address,
						user_address);
			} else if (user.getFlag().equals("1")) {// 司机
				total = rmdao.getAgingCountUser(customer_name,
						customer_address, user.getDriver_id());

			} else if (user.getFlag().equals("2")) {// 供应商
				total = rmdao.suppliersGetAgingsUserCount(customer_name,
						customer_address, user.getSuppliers_id());

			} else if (user.getFlag().equals("3") || user.getFlag().equals("4")) {// 信息员
				total = rmdao.otherGetAgingCount(customer_name,
						customer_address, user.getId());
			}
		}

		return total;
	}

	// 检查时效是否存在
	public boolean saveCheckAging(Aging aging) {
		int i = rmdao.saveCheckAging(aging);
		if (i > 0) {
			return false;
		}
		return true;
	}

	// 保存时效信息
	public int saveAging(Aging aging) {
		return rmdao.saveAging(aging);
	}

	// 删除时效信息
	public int deleteAging(String[] id) {
		return rmdao.deleteAging(id);
	}

	// 获取修改的时效信息
	public Aging getsetAging(String aid) {
		return rmdao.getsetAging(aid);
	}

	// 修改时效
	public int updateAging(Aging aging) {
		return rmdao.updateAging(aging);
	}

	// 获取发货客户信息
	public List<Customer> getCustomersByName(String name) {
		return rmdao.getCustomersByName(name);
	}

	// 获取收货客户信息总条数
	public int getDeliveryCon(String customer_name, String customer_tel,
			String customer_address, String User_address,
			String delivery_cus_name, User user) {
		int total = 0;
		if (user != null) {
			if (user.getFlag().equals("0")) {// 管理员
				total = rmdao.getDeliveryCon(customer_name, customer_tel,
						customer_address, user.getUser_address(),
						delivery_cus_name);

			} else if (user.getFlag().equals("1")) {// 司机
				List<String> addresslist = rmdao.getAddressDriver(user
						.getDriver_id());
				total = rmdao.getDeliveryCountdd(customer_name, customer_tel,
						customer_address, addresslist, delivery_cus_name,user.getDriver_id());

			} else if (user.getFlag().equals("2")) {// 供应商
				List<String> addresslist = rmdao.getAddressSupperlies(user
						.getSuppliers_id());
				total = rmdao.getDeliveryCountdd(customer_name, customer_tel,
						customer_address, addresslist, delivery_cus_name,user.getDriver_id());
			} else if (user.getFlag().equals("3") || user.getFlag().equals("4")) {// 信息员
				total = rmdao.otherGetDeliveryCon(customer_name, customer_tel,
						customer_address, user.getId(), delivery_cus_name);
			}

		}

		return total;
	}

	// 获取收货客户信息
	public List<DeliveryCustomer> getDeliveryC(Integer page, Integer rows,
			String remark_name, String remark_tel, String remark_address,
			String User_address, String delivery_cus_name, User user) {
		List<DeliveryCustomer> list = null;
		if (user != null) {
			if (user.getFlag().equals("0")) {// 管理员
				list = rmdao.getDeliveryC(page, rows, remark_name, remark_tel,
						remark_address, user.getUser_address(),
						delivery_cus_name);
			} else if (user.getFlag().equals("1")) {// 司机
				List<String> addresslist = rmdao.getAddressDriver(user
						.getDriver_id());
				list = rmdao.getDeliveryCustomersdd(page, rows, remark_name,
						remark_tel, remark_address, addresslist,
						delivery_cus_name,user.getDriver_id());

			} else if (user.getFlag().equals("2")) {// 供应商
				List<String> addresslist = rmdao.getAddressSupperlies(user
						.getSuppliers_id());
				list = rmdao.getDeliveryCustomersdd(page, rows, remark_name,
						remark_tel, remark_address, addresslist,
						delivery_cus_name,user.getDriver_id());

			} else if (user.getFlag().equals("3") || user.getFlag().equals("4")) {// 信息员
				list = rmdao.otherGetDeliveryC(page, rows, remark_name,
						remark_tel, remark_address, user.getId(),
						delivery_cus_name);
			}

		}

		return list;
	}

	// 检查客户名重复
	public int getcustomer_name(String customer_name) {
		return rmdao.getcustomer_name(customer_name);
	}

	// 添加收货客户信息
	public int saveDeliveryCustomer(DeliveryCustomer customer) {

		return rmdao.saveDeliveryCustomer(customer);
	}

	// 检查收货客户标注节点信息
	public boolean checkRemark(String id) {
		int i = rmdao.checkRemark(id);
		if (i > 0)
			return true;
		else
			return false;
	}

	// 删除节点信息错误的
	public boolean deleteRemark(String[] id) {
		int i = rmdao.deleteRemark(id);
		if (i > 0)
			return true;
		else
			return false;
	}

	// 删除发货客户信息
	public boolean deleteDeliveryCustomer(String[] id) {
		int i = rmdao.deleteDeliveryCustomer(id);
		if (i > 0)
			return true;
		else
			return false;
	}

	// 更新要修改的收货客户信息
	public int updateDeliveryCustomer(DeliveryCustomer customer) {
		return rmdao.updateDeliveryCustomer(customer);
	}

	// 更新收货客户定位信息
	public int updateLocationRemarkCustomer(DeliveryCustomer customer) {
		return rmdao.updateLocationRemarkCustomer(customer);
	}

	// 更新一下标注
	public boolean updateRemark(Customer remark) {
		int i = rmdao.updateRemark(remark);
		if (i > 0)
			return true;
		else
			return false;
	}

	// 检查标注名
	public boolean checkName(String name) {
		int i = rmdao.checkName(name);
		if (i > 0)
			return true;
		else
			return false;
	}

	// 添加标记
	public boolean addRemark(Customer remark) {
		int i = rmdao.addRemark(remark);
		if (i > 0)
			return true;
		else
			return false;
	}

	public List<RemarkType> getTypes() {
		return rmdao.getTypes();
	}

	public List<RemarkRange> getRange() {
		return rmdao.getRange();
	}

	public List<Remark> getRemarkName(String name, String state) {

		return rmdao.getRemarkName(name, state);
	}

	public String getModifyPeople(Integer people) {
		return rmdao.getModifyPeople(people);
	}

	public Customer getRemark(String id) {
		return rmdao.getRemark(id);
	}

	public String getOneRange(String id) {
		return rmdao.getOneRange(id);
	}

	public List<DeliveryCustomer> getDeliveryCustomers(Integer page,
			Integer rows, String remark_name, String remark_tel,
			String remark_address, String driverId) {

		return rmdao.getDeliveryCustomers(page, rows, remark_name, remark_tel,
				remark_address, driverId);
	}

	public int getDeliveryCount(String remark_name, String remark_tel,
			String remark_address, String User_address) {

		return rmdao.getDeliveryCount(remark_name, remark_tel, remark_address,
				User_address);
	}

	public DeliveryCustomer getDeliveryCustomerbyid(String cid) {

		return rmdao.getDeliveryCustomerbyid(cid);
	}

	public List<DeliveryCustomer> getDeliveryCustomerbyname(String cid,
			String send_mechanism) {

		return rmdao.getDeliveryCustomerbyname(cid, send_mechanism);
	}

	public List<DeliveryCustomer> supplersGetDeliveryInfo(Integer page,
			Integer rows, String remark_name, String remark_tel,
			String remark_address, String supplersId) {

		return rmdao.supplersGetDeliveryInfo(page, rows, remark_name,
				remark_tel, remark_address, supplersId);
	}

	public int supplersGetDeliveryCount(String remark_name, String remark_tel,
			String remark_address, String supplersId) {

		return rmdao.supplersGetDeliveryCount(remark_name, remark_tel,
				remark_address, supplersId);
	}

	public String getAgingCityInfo(int aging_province, int aging_city,
			int aging_county) {

		return rmdao.getAgingCityInfo(aging_province, aging_city, aging_county);
	}

	// 导入发货客户信息
	public int customerExcelImport(List<Customer> tlist) {

		return rmdao.customerExcelImport(tlist);
	}

	// 获取所有的发货客户信息
	public List<Customer> getAllCustomers() {

		return rmdao.getAllCustomers();
	}

	// 收货客户导入
	public int deliveryExcelInfo(List<DeliveryCustomer> tlist) {
	
		return rmdao.deliveryExcelInfo(tlist);
	}

	@Override
	public List<DeliveryCustomer> getUpdateLocationRemarkCustomer() {
		return rmdao.getUpdateLocationRemarkCustomer();
	}
}
