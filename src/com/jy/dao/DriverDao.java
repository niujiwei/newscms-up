package com.jy.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jy.model.City_info;
import com.jy.model.Driver;
import com.jy.model.DriverToOrder;
import com.jy.model.JySuppliers;
import com.jy.model.PositionInfo;

public interface DriverDao {
	// 供应商获取相应的司机id
	List<Driver> getSupDriverId(String pid);

	// 获取省相应的型号
	City_info Driver_province(String address);

	// 获取市相应的型号
	City_info Driver_city(String address);

	// 导入时，获取driver_suppliers
	String driver_suppliers(String driver_suppliersname, String driver_address);

	// 管理员--司机管理查询司机信息,新添加了两个查询条件货运编号shiping_order_num、出货订单号shiping_order_goid
	List<Driver> getDriverInfoSuppUser(int rows, int page, String driver_name,
			String driver_suppliers, String driver_phone,
			String driver_cardnumber, String driver_address,
			String shiping_order_num, String shiping_order_goid, String id);

	// 司机--司机管理查询司机信息,新添加了两个查询条件货运编号shiping_order_num、出货订单号shiping_order_goid
	List<Driver> getDriverInfo(int rows, int page, String driver_name,
			String driver_suppliers, String driver_phone,
			String driver_cardnumber, String driver_address,
			String shiping_order_num, String shiping_order_goid, String id);

	// 供应商--司机管理查询司机信息,新添加了两个查询条件货运编号shiping_order_num、出货订单号shiping_order_goid
	List<Driver> getDriverInfoSupp(int rows, int page, String driver_name,
			String driver_suppliers, String driver_phone,
			String driver_cardnumber, String driver_address,
			String shiping_order_num, String shiping_order_goid, String id);

	// 录单员--司机管理查询司机信息,新添加了两个查询条件货运编号shiping_order_num、出货订单号shiping_order_goid
	List<Driver> othergetDriverInfo(int rows, int page, String driver_name,
			String driver_suppliers, String driver_phone,
			String driver_cardnumber, String driver_address,
			String shiping_order_num, String shiping_order_goid, String id);

	// 管理员--司机管理查询司机信息总条数,新添加了两个查询条件货运编号shiping_order_num、出货订单号shiping_order_goid
	int getDriverSuppUser(String driver_name, String driver_suppliers,
			String driver_phone, String driver_cardnumber,
			String driver_address, String shiping_order_num,
			String shiping_order_goid, String id);

	// 司机--司机管理查询司机信息总条数,新添加了两个查询条件货运编号shiping_order_num、出货订单号shiping_order_goid
	int getDriver(String driver_name, String driver_suppliers,
			String driver_phone, String driver_cardnumber,
			String driver_address, String shiping_order_num,
			String shiping_order_goid, String id);

	// 供应商--司机管理查询司机信息总条数,新添加了两个查询条件货运编号shiping_order_num、出货订单号shiping_order_goid
	int getDriverSupp(String driver_name, String driver_suppliers,
			String driver_phone, String driver_cardnumber,
			String driver_address, String shiping_order_num,
			String shiping_order_goid, String id);// 司机查询总数

	// 录单员--司机管理查询司机信息总条数,新添加了两个查询条件货运编号shiping_order_num、出货订单号shiping_order_goid
	int otherDriverInfoCount(String driver_name, String driver_suppliers,
			String driver_phone, String driver_cardnumber,
			String driver_address, String shiping_order_num,
			String shiping_order_goid, String id);

	// select2查询供应商信息
	public List<JySuppliers> getSuppliers(String num);

	// 删除供应商相关得信息
	public int deleteSupDriver(@Param("array") String[] pid);

	// 司机管理删除司机信息
	public int deleteDriver(@Param("array") String[] del);

	// 司机管理分配运单保存 导入excel表
	int saveDrivertoOrder(List<DriverToOrder> orders);

	// 司机管理司机信息添加
	public int saveDriver(Driver d);

	// 司机管理获取要修改的司机信息
	public Driver getUpdateDriver(String id);

	// 司机管理修改司机信息
	public int updateDriver(Driver d);

	// 获取省--已过时
	List<PositionInfo> getFinalposition();

	// 司机终到位置市--已经过时
	List<PositionInfo> getFinalpositionCity(Integer oneid);

	// 司机终到位置区--已过时
	List<PositionInfo> getFinalpositionCounty(Integer oneid, Integer twoid);

	// 旧的省市县
	public List<PositionInfo> getNum(String province, String city, String count);

	// 终到位置获取省
	public List<City_info> getNewFinalPositionCounty();

	// 终到位置获取市
	public List<City_info> getNewFinalpositionCity(Integer citye_parent_id);

	// 终到位置获取区
	public List<City_info> getNewFinalpositionCounty(Integer citye_parent_id);

	// 保存省市县信息
	public int saveDriverCityInfo(List<Driver> drivers);

	// 司机管理修改查询获取省市县
	public List<City_info> getDriverCityInfo(String driverId);

	// 司机管理修改信息删除对应的省市县
	public int deleteDriverCityInfo(@Param("array") String[] dId);

	// APP获取供应商下面的司机
	List<Driver> getDriverInfoSuppapp(String driverInfoSupp);

	// 管理员获取司机信息导出
	List<Driver> getDriverInfoExcelSuppUser(String driver_name,
			String driver_suppliers, String driver_phone,
			String driver_cardnumber, String driver_address,
			String shiping_order_num, String shiping_order_goid, String id,
			@Param("array") String[] dataIds);

	// 司机导出司机信息表
	List<Driver> getDriverInfoExcel(String driver_name,
			String driver_suppliers, String driver_phone,
			String driver_cardnumber, String driver_address,
			String shiping_order_num, String shiping_order_goid, String id,
			@Param("array") String[] dataIds);

	// 供应商导出信息表
	List<Driver> getDriverInfoExcelSupp(String driver_name,
			String driver_suppliers, String driver_phone,
			String driver_cardnumber, String driver_address,
			String shiping_order_num, String shiping_order_goid, String id,
			@Param("array") String[] dataIds);

	// 信息员导出信息表
	List<Driver> othergetDriverExcelInfo(String driver_name,
			String driver_suppliers, String driver_phone,
			String driver_cardnumber, String driver_address,
			String shiping_order_num, String shiping_order_goid, String id,
			@Param("array") String[] dataIds);

	// 分配订单修改状态
	public int updateShipperorderState(@Param("array") String[] did);

	// 省市县tree
	public List<City_info> getNewFinalPositionAddress(@Param("id") String id);

	/**
	 * 司机人名查询
	 * 
	 * @param search
	 * @return
	 */
	public List<Driver> getDriver_length(String search);

	/**
	 * 司机模块的车牌查询
	 * 
	 * @param phones
	 * @return
	 */
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

	// 检测供应商的司机身份是否存在
	public List<Driver> selectID(String id);
	
	// 判断用户是否存在
	public Map<String, String> driverISHaveGps(@Param("driverId")String driverId);

}
