package com.jy.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.jy.common.UUIDUtils;
import com.jy.dao.DriverDao;
import com.jy.dao.SuppliersAccountDao;
import com.jy.dao.SuppliersDao;
import com.jy.model.City_info;
import com.jy.model.Customer;
import com.jy.model.Driver;
import com.jy.model.JySuppliers;
import com.jy.model.OrderHistory;
import com.jy.model.ShippingOrder;
import com.jy.model.User;
import com.jy.service.SuppliersService;

@Component
public class SuppliersServiceImpl implements SuppliersService {
	@Resource
	private SuppliersDao suppliersDao;
	@Resource
	private SuppliersAccountDao sad;
	@Resource
	private DriverDao dd;

	// 供应商的导出
	public List<JySuppliers> getOutPutCustomers(String name, String phone,
			String address, String id, String suppliers_customer, User user,
			String[] dataIds) {
		List<JySuppliers> list = null;
		if (user != null) {
			if (user.getFlag().equals("0")) {// 管理员
				list = suppliersDao.getSuppUserOutPut(name, phone, address, id,
						suppliers_customer, dataIds);
			} else if (user.getFlag().equals("1")) {// 司机
				list = suppliersDao.getSupplierOutPut(name, phone, address,
						user.getDriver_id(), suppliers_customer, dataIds);
			} else if (user.getFlag().equals("2")) {// 供应商
				list = suppliersDao.getSuppliersOutPut(name, phone, address,
						user.getId(), suppliers_customer, dataIds);
			} else if (user.getFlag().equals("3") || user.getFlag().equals("4")) {// 信息员
				list = suppliersDao.otherGetSuppUserOutPut(name, phone,
						address, user.getId(), suppliers_customer, dataIds);
			}

		}
		return list;
	}

	// 添加车辆类型
	public List<String> addCarType() {
		return suppliersDao.getCartype();
	};

	// 供应商或司机的信息改变
	public void supupdateDriver(JySuppliers j) {
		// System.out.println(dd.selectID(j.getSuppliersId()));
		if (dd.selectID(j.getSuppliersId()) != null) {
			Driver d = new Driver();
			d.setDriver_id(j.getSuppliersId());
			d.setDriver_name(j.getSuppliersName());
			d.setDriver_phone(j.getSuppliersPhone());
			d.setDriver_cardnumber(j.getSuppliers_carnumber());
			d.setDriver_updatepeople(j.getSuppliersOperator());
			d.setDriver_updatedate(j.getSuppliersOperatorDate());
			d.setDriver_suppliers(j.getSuppliersId());
			d.setDriver_province(j.getDriver_province());
			d.setDriver_city(j.getDriver_city());
			d.setDriver_county(j.getDriver_county());
			d.setDriver_address(j.getSuppliersAddress());
			d.setDriver_carnumber(j.getSuppliers_carnumber());
			d.setDriver_cartype(j.getSuppliers_cartype());
			d.setDriver_phonebrand(j.getPhone_brand());
			d.setDriver_phonemodel(j.getPhone_model());
			d.setListCity_infos(j.getListCity_infos());
			dd.updateDriver(d);
			saveDriverCityInfoNew(d);
		}
		;

	};

	/**
	 * 司机信息修改保存新的省市县
	 * 
	 * @param driver
	 * @return
	 */
	public int saveDriverCityInfo(Driver driver) {
		List<Driver> listDriver = new ArrayList<Driver>();
		if (driver.getDriver_countys() == null) {
			Driver diDriver = new Driver();
			diDriver.setId(UUIDUtils.getUUID());
			diDriver.setDriver_id(driver.getDriver_id());
			diDriver.setDriver_province(driver.getDriver_province());
			diDriver.setDriver_city(driver.getDriver_city());
			listDriver.add(diDriver);
		} else {
			for (int i = 0; i < driver.getDriver_countys().length; i++) {
				Driver diDriver = new Driver();
				diDriver.setId(UUIDUtils.getUUID());
				diDriver.setDriver_id(driver.getDriver_id());
				diDriver.setDriver_province(driver.getDriver_province());
				diDriver.setDriver_city(driver.getDriver_city());
				diDriver.setDriver_county(driver.getDriver_countys()[i]);
				listDriver.add(diDriver);
			}
		}
		int j = dd.saveDriverCityInfo(listDriver);
		return j;

	}

	/**
	 * 司机信息修改保存新的省市县
	 * 
	 * @param driver
	 * @return
	 */
	public int saveDriverCityInfoNew(Driver driver) {
		List<Driver> listDriver = new ArrayList<Driver>();
		List<City_info> citys = driver.getListCity_infos();
		if(citys==null) return 0;
		for (City_info city_info : citys) {
			Driver diDriver = new Driver();
			diDriver.setId(UUIDUtils.getUUID());
			diDriver.setDriver_id(driver.getDriver_id());
			diDriver.setDriver_province(city_info.getProvince());
			diDriver.setDriver_city(city_info.getCity());
			diDriver.setDriver_county(city_info.getCounty());
			listDriver.add(diDriver);
		}
		if (listDriver.size()==0)return 0;
		int j = dd.saveDriverCityInfo(listDriver);
		return j;

	}

	// 供应商添加到司机中
	public void supToDriver(JySuppliers j) {
		Driver d = new Driver();
		d.setDriver_id(j.getSuppliersId());
		d.setDriver_name(j.getSuppliersName());
		d.setDriver_phone(j.getSuppliersPhone());
		d.setDriver_cardnumber(j.getSuppliers_carnumber());
		d.setDriver_updatepeople(j.getSuppliersOperator());
		d.setDriver_updatedate(j.getSuppliersOperatorDate());
		d.setDriver_suppliers(j.getSuppliersId());
		d.setDriver_province(j.getDriver_province());
		d.setDriver_city(j.getDriver_city());
		d.setDriver_county(j.getDriver_county());
		d.setDriver_address(j.getSuppliersAddress());
		d.setDriver_carnumber(j.getSuppliers_carnumber());
		d.setDriver_cartype(j.getSuppliers_cartype());
		d.setDriver_phonebrand(j.getPhone_brand());
		d.setDriver_phonemodel(j.getPhone_model());
		d.setDriver_county(j.getDriver_province());
		d.setDriver_city(j.getDriver_city());
		d.setDriver_countys(j.getSuppers_county());
		d.setListCity_infos(j.getListCity_infos());
		d.setIs_supperlis("1");
		dd.saveDriver(d);
		saveDriverCityInfoNew(d);
	};

	// 供应商信息查询
	public List<JySuppliers> getSuppUser(int rows, int page, String name,
			String phone, String address, String id, String suppliers_customer,
			User user) {
		List<JySuppliers> list = null;
		if (user != null) {
			if (user.getFlag().equals("0")) {// 管理员
				list = suppliersDao.getSuppUser(rows, page, name, phone,
						address, id, suppliers_customer);
			} else if (user.getFlag().equals("1")) {// 司机
				list = suppliersDao.getSupplier(rows, page, name, phone,
						address, user.getDriver_id(), suppliers_customer);
			} else if (user.getFlag().equals("2")) {// 供应商
				list = suppliersDao.getSuppliers(rows, page, name, phone,
						address, user.getId(), suppliers_customer);
			} else if (user.getFlag().equals("3") || user.getFlag().equals("4")) {// 信息员
				list = suppliersDao.otherGetSuppUser(rows, page, name, phone,
						address, user.getId(), suppliers_customer);
			}

		}

		return list;
	}

	// 供应商信息查询总条数
	public int getSuppCounUser(String name, String phone, String address,
			String id, String suppliers_customer, User user) {
		int count = 0;
		if (user != null) {
			if (user.getFlag().equals("0")) {// 管理员
				count = suppliersDao.getSuppCounUser(name, phone, address, id,
						suppliers_customer);
			} else if (user.getFlag().equals("1")) {// 司机
				count = suppliersDao.getSuppliersCoun(name, phone, address,
						user.getDriver_id(), suppliers_customer);
			} else if (user.getFlag().equals("2")) {// 供应商
				count = suppliersDao.getSuppliersCount(name, phone, address,
						user.getId(), suppliers_customer);
			} else if (user.getFlag().equals("3") || user.getFlag().equals("4")) {// 信息员
				count = suppliersDao.otherGetSuppCounUser(name, phone, address,
						user.getId(), suppliers_customer);
			}
		}

		return count;
	}

	// 添加供应商信息
	public int saveSuppliers(JySuppliers suppliers) {
		return suppliersDao.saveSuppliers(suppliers);
	}

	// 添加供应商信息保存省市县
	public int saveSupplersCityInfo(List<JySuppliers> jyslist) {
		return suppliersDao.saveSupplersCityInfo(jyslist);
	}

	// 添加供应商信息保存图片
	public int updateSuppliersImage(String images, String suppId) {
		return suppliersDao.updateSupplierImage(images, suppId);
	}

	// 删除供应商省市县信息
	public int deleteSuppliersCityInfo(String[] pid) {
		return suppliersDao.deleteSuppliersCityInfo(pid);
	}

	// 删除供应之前查询供应商信息
	public JySuppliers getUpdateSuppliers(String cid) {
		return suppliersDao.getUpdateSuppliers(cid);
	}

	// 删除供应商信息
	public int deletesuppliers(String[] pid) {
		return suppliersDao.deletesuppliers(pid);
	}

	// 删除供应商相应的司机信息
	public int deleteDriver(String[] pid) {

		for (int i = 0; i < pid.length; i++) {
			List<Driver> driverid = dd.getSupDriverId(pid[i]);
			String[] driverids = new String[20];
			for (int j = 0; j < driverid.size(); j++) {
				driverids[j] = driverid.get(j).getDriver_id();
			}
			dd.deleteDriverCityInfo(driverids);
			dd.deleteDriver(driverids);
		}

		return 0;
	};

	// 更新供应商信息
	public int updateSuppliers(JySuppliers jyuppliers) {
		return suppliersDao.updateSuppliers(jyuppliers);
	}

	// select2 获取供应商信息
	public List<JySuppliers> getSpriadd() {
		return suppliersDao.getSpriadd();
	}

	// 获取供应商的省市县
	public List<City_info> getCityInfo(String jysId) {
		return suppliersDao.getCityInfo(jysId);
	}

	// 供应商结算 查询信息
	public List<JySuppliers> getSupplierss(int rows, int page,
			String suppliersName, String suppliersSendTime,
			String suppliersEndTime, String endAddress, User user) {
		List<JySuppliers> list = new ArrayList<JySuppliers>();
		if (user != null) {
			if (user.getFlag().equals("0")) {// 管理员
				list = sad.getSuppliers(rows, page, suppliersName,
						suppliersSendTime, suppliersEndTime, endAddress);
			} else if (user.getFlag().equals("1")) {// 司机

			} else if (user.getFlag().equals("2")) {// 供应商
				list = sad.supplierGetSuppliers(rows, page, suppliersName,
						suppliersSendTime, suppliersEndTime, endAddress,
						user.getSuppliers_id());

			} else if (user.getFlag().equals("3") || user.getFlag().equals("4")) {// 信息员
				list = sad.otherGetSupplierss(rows, page, suppliersName,
						suppliersSendTime, suppliersEndTime, endAddress,
						user.getId());

			}

		}

		return list;
	}

	// 供应商结算 查询总条数
	public int getSupplierCount(String suppliersName, String suppliersSendTime,
			String suppliersEndTime, String endAddress, User user) {
		int count = 0;
		if (user != null) {
			if (user.getFlag().equals("0")) {// 管理员
				count = sad.getSuppliersCount(suppliersName, suppliersSendTime,
						suppliersEndTime, endAddress);
			} else if (user.getFlag().equals("1")) {// 司机

			} else if (user.getFlag().equals("2")) {// 供应商
				count = sad.suppliersGetSuppliersCount(suppliersName,
						suppliersSendTime, suppliersEndTime, endAddress,
						user.getSuppliers_id());

			} else if (user.getFlag().equals("3") || user.getFlag().equals("4")) {// 信息员
				count = sad.otherGetSupplierCount(suppliersName,
						suppliersSendTime, suppliersEndTime, endAddress,
						user.getId());
			}

		}

		return count;
	}

	// 供应商结算查询订单信息
	public List<ShippingOrder> getShiOreder(int rows, int page, String pid,
			String shiping_order_num, String end_address, String custom_name,
			String receipt_name, String receipt_tel, String send_mechanism,
			String send_time, String time, String timeId, String[] driverId,
			String shipperorder_id, String clearing_state) {
		return sad.getShiOreder(rows, page, pid, shiping_order_num,
				end_address, custom_name, receipt_name, receipt_tel,
				send_mechanism, send_time, time, timeId, driverId,
				shipperorder_id, clearing_state);
	}

	// 供应商结算查询订单总条数
	public int getShiOrederCount(String pid, String shiping_order_num,
			String end_address, String custom_name, String receipt_name,
			String receipt_tel, String send_mechanism, String send_time,
			String time, String timeId, String[] driverId,
			String shipperorder_id, String clearing_state) {
		return sad.getShiOrederCount(pid, shiping_order_num, end_address,
				custom_name, receipt_name, receipt_tel, send_mechanism,
				send_time, time, timeId, driverId, shipperorder_id,
				clearing_state);
	}

	// 供应商结算 更新结算状态
	public int settleSupp(String[] driverId, String suppliersSendTime,
			String suSendTime) {

		return sad.settleSupp(driverId, suppliersSendTime, suSendTime);
	}

	// 保存历史记录供应商结算信息
	public int saveOrderSupp(List<OrderHistory> order) {

		return sad.saveOrderSuppli(order);

	}

	// 供应商结算获取结算订单
	public List<ShippingOrder> getShi(String[] driverId,
			String suppliersSendTime, String suSendTime) {

		return sad.getShi(driverId, suppliersSendTime, suSendTime);

	}

	// 没有用了没有用了
	public JySuppliers getClearing(String suppliersName,
			String suppliersSendTime, String suppliersEndAddress,
			String suppliersSendMechanism) {

		return sad.getClearing(suppliersName, suppliersSendTime,
				suppliersEndAddress, suppliersSendMechanism);

	}

	// 获取项目部信息
	public Customer getCustomer(String customerId) {

		return sad.getCustomer(customerId);
	}

	// 获取省市县多选
	public List<City_info> getCity_Info(List<Integer> province,
			List<Integer> city, List<Integer> county) {

		return suppliersDao.getCity_Info(province, city, county);
	}

	// 获取省市县城市名称
	public List<String> getCity_Info_name(List<Integer> province,
			List<Integer> city, List<Integer> county) {

		return suppliersDao.getCity_Info_name(province, city, county);
	}

}
