package com.jy.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jy.model.City_info;
import com.jy.model.Driver;
import com.jy.model.DriverToOrder;
import com.jy.model.JySuppliers;
import com.jy.model.PositionInfo;
import com.jy.model.User;

public interface DriverInfoService {
	/**
	 * 司机管理查询信息,新添加了两个查询条件货运编号shiping_order_num、出货订单号shiping_order_goid
	 * 
	 * @param rows
	 * @param page
	 * @param driver_name
	 * @param driver_suppliers
	 * @param driver_phone
	 * @param driver_cardnumber
	 * @param driver_address
	 * @param id
	 * @param user
	 * @return
	 */
	public List<Driver> getDriverInfo(int rows, int page, String driver_name,
			String driver_suppliers, String driver_phone,
			String driver_cardnumber, String driver_address,
			String shiping_order_num, String shiping_order_goid, String id,
			User user);

	/**
	 * 司机管理查询总条数,新添加了两个查询条件货运编号shiping_order_num、出货订单号shiping_order_goid
	 * 
	 * @param driver_name
	 * @param driver_suppliers
	 * @param driver_phone
	 * @param driver_cardnumber
	 * @param driver_address
	 * @param id
	 * @param user
	 * @return
	 */
	public int getDriver(String driver_name, String driver_suppliers,
			String driver_phone, String driver_cardnumber,
			String driver_address, String shiping_order_num,
			String shiping_order_goid, String id, User user);

	/**
	 * select2供应商信息查询
	 * 
	 * @param num
	 * @return
	 */
	public List<JySuppliers> getSuppliers(String num);

	/**
	 * 司机管理分配运单保存到管理表中
	 * 
	 * @param orders
	 * @return
	 */
	public int saveDrivertoOrder(List<DriverToOrder> orders);

	/**
	 * 分配运单更新运单状态
	 * 
	 * @param did
	 * @return
	 */
	public int updateShipperorderState(@Param("array") String[] did);

	/**
	 * 司机管理删除司机信息
	 * 
	 * @param del
	 * @return
	 */
	public int deleteDriver(String[] del);

	/**
	 * 司机管理删除司机对应的省市县
	 * 
	 * @param dId
	 * @return
	 */
	public int deleteDriverCityInfo(String[] dId);

	/**
	 * 司机管理司机信息添加
	 * 
	 * @param d
	 * @return
	 */
	public int saveDriver(Driver d);

	/**
	 * 司机管理获取要修改的司机信息
	 * 
	 * @param driver_id
	 * @return
	 */
	public Driver getUpdateDriver(String id);

	/**
	 * 司机管理修改司机信息
	 * 
	 * @param d
	 * @return
	 */
	public int updateDriver(Driver d);

	/**
	 * 获取省----已过时
	 * 
	 * @return
	 */
	public List<PositionInfo> getFinalposition();

	/**
	 * 司机终到位置市--已经过时
	 * 
	 * @param oneid
	 * @return
	 */
	public List<PositionInfo> getFinalpositionCity(Integer oneid);

	/**
	 * 司机终到位置区--已过时
	 * 
	 * @param oneid
	 * @param twoid
	 * @return
	 */
	public List<PositionInfo> getFinalpositionCounty(Integer oneid,
			Integer twoid);

	/**
	 * 旧的省市县
	 * 
	 * @param province
	 * @param city
	 * @param count
	 * @return
	 */
	public List<PositionInfo> getNum(String province, String city, String count);

	/**
	 * 终到位置获取省
	 * 
	 * @return
	 */
	public List<City_info> getNewFinalPositionCounty();

	/**
	 * 终到位置获取市
	 * 
	 * @param citye_parent_id
	 * @return
	 */
	public List<City_info> getNewFinalpositionCity(Integer citye_parent_id);

	/**
	 * 终到位置获取区
	 * 
	 * @param citye_parent_id
	 * @return
	 */
	public List<City_info> getNewFinalpositionCounty(Integer citye_parent_id);

	/**
	 * 保存省市县信息
	 * 
	 * @param drivers
	 * @return
	 */
	public int saveDriverCityInfo(List<Driver> drivers);

	/**
	 * 司机管理修改查询获取省市县
	 * 
	 * @param driverId
	 * @return
	 */
	public List<City_info> getDriverCityInfo(String driverId);

	/**
	 * 司机信息导出
	 * 
	 * @param driver_name
	 * @param driver_suppliers
	 * @param driver_phone
	 * @param driver_cardnumber
	 * @param driver_address
	 * @return
	 */
	public List<Driver> getDriverInfoExcel(String driver_name,
			String driver_suppliers, String driver_phone,
			String driver_cardnumber, String driver_address,
			String shiping_order_num, String shiping_order_goid, User user,
			String id, String[] dataIds);

	/**
	 * app 获取司机信息
	 * 
	 * @param string
	 * @return
	 */
	public List<Driver> getDriverInfoSupp(String string);

	/*
	 * select 查询
	 */
	public List<Driver> getDriver_length(String search);

	public List<Driver> getPhone_length(String phones);

	public List<Driver> getDateLength(String date);

	public int searchDriver(String driver_name, String plate_number);

	public List<Driver> driver(String num);// 查询是司机的车牌

	public Driver ptype(String car);// 信息查询

	/**
	 * 电话
	 * 
	 * @param id
	 * @return
	 */
	public Driver getPhone(String id);

	/**
	 * 省市县tree
	 * 
	 * @param id
	 * @return
	 */
	public List<City_info> getNewFinalPositionAddress(@Param("id") String id);

	/**
	 * 判断用户是否存在gps
	 */
	public Map<String, String> driverISHaveGps(String driverId);

}
