package com.jy.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.jy.dao.DriverDao;
import com.jy.model.City_info;
import com.jy.model.Driver;
import com.jy.model.DriverToOrder;
import com.jy.model.JySuppliers;
import com.jy.model.PositionInfo;
import com.jy.model.User;
import com.jy.service.DriverInfoService;

@Component
@Service("DriverInfoService")
public class DriverInfoServiceImpl implements DriverInfoService {
	@Resource
	private DriverDao driverdao;

	// 司机表的导入
	public int driverExcel(List<Driver> tlist) {
		int a = 0;
		for (int i = 0; i < tlist.size(); i++) {
			System.out.println(tlist.get(i).getDriver_suppliersname());
			String driver_suppliers = driverdao.driver_suppliers(tlist.get(i)
					.getDriver_suppliersname(), tlist.get(i)
					.getDriver_address());
			System.out.println(driver_suppliers);
			tlist.get(i).setDriver_suppliers(driver_suppliers);
			String driver_address = tlist.get(i).getDriver_address();
			int d = driver_address.indexOf("省");
			int e = driver_address.indexOf("市");
			String str = driver_address.substring(0, d);
			City_info ci = driverdao.Driver_province(str);
			tlist.get(i).setDriver_province(ci.getCitycode());
			if (e != 0) {
				String str2 = driver_address.substring(d + 1, e + 1);
				String str3 = driver_address.substring(e + 1,
						driver_address.length());
				City_info b = driverdao.Driver_city(str2);
				tlist.get(i).setDriver_city(b.getCitycode());
				City_info f = driverdao.Driver_city(str3);
				if (f != null) {
					tlist.get(i).setDriver_county(f.getCitycode());
				}
			}
			a = driverdao.saveDriver(tlist.get(i));
		}
		return a;
	}

	// 司机管理查询信息,新添加了两个查询条件货运编号shiping_order_num、出货订单号shiping_order_goid
	public List<Driver> getDriverInfo(int rows, int page, String driver_name,
			String driver_suppliers, String driver_phone,
			String driver_cardnumber, String driver_address,
			String shiping_order_num, String shiping_order_goid, String id,
			User user) {
		List<Driver> list = null;
		if (user != null) {
			if (user.getFlag().equals("0")) {// 管理员
				list = driverdao.getDriverInfoSuppUser(rows, page, driver_name,
						driver_suppliers, driver_phone, driver_cardnumber,
						driver_address, shiping_order_num, shiping_order_goid,
						user.getSuppliers_id());
			} else if (user.getFlag().equals("1")) {// 司机
				list = driverdao.getDriverInfo(rows, page, driver_name,
						driver_suppliers, driver_phone, driver_cardnumber,
						driver_address, shiping_order_num, shiping_order_goid,
						user.getId());

			} else if (user.getFlag().equals("2")) {// 供应商
				list = driverdao.getDriverInfoSupp(rows, page, driver_name,
						driver_suppliers, driver_phone, driver_cardnumber,
						driver_address, shiping_order_num, shiping_order_goid,
						user.getSuppliers_id());

			} else if (user.getFlag().equals("3") || user.getFlag().equals("4")) {// 信息员
				list = driverdao.othergetDriverInfo(rows, page, driver_name,
						driver_suppliers, driver_phone, driver_cardnumber,
						driver_address, shiping_order_num, shiping_order_goid,
						id);
			}
		}
		return list;
	}

	// 司机管理查询信息总条数,新添加了两个查询条件货运编号shiping_order_num、出货订单号shiping_order_goid
	public int getDriver(String driver_name, String driver_suppliers,
			String driver_phone, String driver_cardnumber,
			String driver_address, String shiping_order_num,
			String shiping_order_goid, String id, User user) {
		int count = 0;
		if (user != null) {
			if (user.getFlag().equals("0")) {// 管理员
				count = driverdao.getDriverSuppUser(driver_name,
						driver_suppliers, driver_phone, driver_cardnumber,
						driver_address, shiping_order_num, shiping_order_goid,
						"");
			} else if (user.getFlag().equals("1")) {// 司机
				count = driverdao.getDriver(driver_name, driver_suppliers,
						driver_phone, driver_cardnumber, driver_address,
						shiping_order_num, shiping_order_goid, user.getId());
			} else if (user.getFlag().equals("2")) {// 供应商
				count = driverdao.getDriverSupp(driver_name, driver_suppliers,
						driver_phone, driver_cardnumber, driver_address,
						shiping_order_num, shiping_order_goid,
						user.getSuppliers_id());
			} else if (user.getFlag().equals("3") || user.getFlag().equals("4")) {// 信息员
				count = driverdao.otherDriverInfoCount(driver_name,
						driver_suppliers, driver_phone, driver_cardnumber,
						driver_address, shiping_order_num, shiping_order_goid,
						id);
			}

		}

		return count;
	}

	// selet2查询供应商信息
	public List<JySuppliers> getSuppliers(String num) {
		List<JySuppliers> list = driverdao.getSuppliers(num);
		return list;
	}

	// 司机管理分配运单保存
	public int saveDrivertoOrder(List<DriverToOrder> orders) {

		return driverdao.saveDrivertoOrder(orders);
	}

	// 司机管理删除司机信息
	public int deleteDriver(String[] del) {
		int flag = driverdao.deleteDriver(del);
		return flag;
	}

	// 司机管理删除司机对应的省市县
	public int deleteDriverCityInfo(String[] dId) {
		return driverdao.deleteDriverCityInfo(dId);
	}

	// 司机管理司机信息添加
	public int saveDriver(Driver d) {
		int flag = driverdao.saveDriver(d);
		return flag;
	}

	// 司机管理获取要修改的司机信息
	public Driver getUpdateDriver(String id) {
		Driver d = driverdao.getUpdateDriver(id);
		return d;
	}

	// 司机管理修改司机信息
	public int updateDriver(Driver d) {
		int flag = driverdao.updateDriver(d);
		return flag;
	}

	/*
	 * 选择框的查询
	 */
	public List<Driver> getDriver_length(String search) {
		List<Driver> list = driverdao.getDriver_length(search);
		return list;
	}

	public List<Driver> getPhone_length(String phones) {
		List<Driver> list = driverdao.getPhone_length(phones);
		return list;
	}

	public List<Driver> getDateLength(String date) {
		List<Driver> list = driverdao.getDateLength(date);
		return list;
	};

	public int searchDriver(String driver_name, String plate_number) {
		int i = driverdao.searchDriver(driver_name, plate_number);
		return i;
	}

	public List<Driver> driver(String num) {
		List<Driver> list = driverdao.driver(num);
		return list;
	}// 查询是司机的车牌

	public Driver ptype(String car) {
		Driver d = driverdao.ptype(car);
		return d;
	}

	// 司机电话
	public Driver getPhone(String id) {
		Driver list = driverdao.getPhone(id);
		return list;
	}

	// 获取省--已过时
	public List<PositionInfo> getFinalposition() {
		return driverdao.getFinalposition();
	}

	// 司机终到位置市--已经过时
	public List<PositionInfo> getFinalpositionCity(Integer oneid) {

		return driverdao.getFinalpositionCity(oneid);
	}

	// 司机终到位置区--已过时
	public List<PositionInfo> getFinalpositionCounty(Integer oneid,
			Integer twoid) {

		return driverdao.getFinalpositionCounty(oneid, twoid);
	}

	// 旧的省市县
	public List<PositionInfo> getNum(String province, String city, String count) {
		return driverdao.getNum(province, city, count);
	}

	// 终到位置获取省
	public List<City_info> getNewFinalPositionCounty() {

		return driverdao.getNewFinalPositionCounty();
	}

	// 终到位置获取市
	public List<City_info> getNewFinalpositionCity(Integer citye_parent_id) {

		return driverdao.getNewFinalpositionCity(citye_parent_id);
	}

	// 终到位置获取区
	public List<City_info> getNewFinalpositionCounty(Integer citye_parent_id) {

		return driverdao.getNewFinalpositionCounty(citye_parent_id);
	}

	// 保存省市县
	public int saveDriverCityInfo(List<Driver> drivers) {

		return driverdao.saveDriverCityInfo(drivers);
	}

	// 司机管理修改查询获取省市县
	public List<City_info> getDriverCityInfo(String driverId) {
		return driverdao.getDriverCityInfo(driverId);
	}

	// 司机信息导出
	public List<Driver> getDriverInfoExcel(String driver_name,
			String driver_suppliers, String driver_phone,
			String driver_cardnumber, String driver_address,
			String shiping_order_num, String shiping_order_goid, User user,
			String id, String[] dataIds) {

		List<Driver> list = null;
		if (user != null) {
			if (user.getFlag().equals("0")) {// 管理员
				list = driverdao.getDriverInfoExcelSuppUser(driver_name,
						driver_suppliers, driver_phone, driver_cardnumber,
						driver_address, shiping_order_num, shiping_order_goid,
						user.getSuppliers_id(), dataIds);
			} else if (user.getFlag().equals("1")) {// 司机
				list = driverdao.getDriverInfoExcel(driver_name,
						driver_suppliers, driver_phone, driver_cardnumber,
						driver_address, shiping_order_num, shiping_order_goid,
						user.getId(), dataIds);

			} else if (user.getFlag().equals("2")) {// 供应商
				list = driverdao.getDriverInfoExcelSupp(driver_name,
						driver_suppliers, driver_phone, driver_cardnumber,
						driver_address, shiping_order_num, shiping_order_goid,
						user.getSuppliers_id(), dataIds);

			} else if (user.getFlag().equals("3") || user.getFlag().equals("4")) {// 信息员
				list = driverdao.othergetDriverExcelInfo(driver_name,
						driver_suppliers, driver_phone, driver_cardnumber,
						driver_address, shiping_order_num, shiping_order_goid,
						id, dataIds);
			}
		}
		return list;
	}

	// APP获取供应商下面的司机
	public List<Driver> getDriverInfoSupp(String driverInfoSupp) {

		List<Driver> list = driverdao.getDriverInfoSuppapp(driverInfoSupp);
		return list;
	}

	public int updateShipperorderState(String[] did) {

		return driverdao.updateShipperorderState(did);
	}

	public List<City_info> getNewFinalPositionAddress(String id) {
		
		return driverdao.getNewFinalPositionAddress(id);
	}

	//判断用户是否存在gps
	public Map<String, String> driverISHaveGps(String driverId) {
		
		return driverdao.driverISHaveGps(driverId);
	}

}
