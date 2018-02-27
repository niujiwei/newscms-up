package com.jy.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jy.model.Maps;
import com.jy.model.OldMointorData;
import com.jy.model.Remark;

/***
 * 
 * @author 黄清华
 * @param 全图监控模块
 * @since 1.6
 * @version 1.0
 * @throws null
 * @createtime 2015-6-3
 */
public interface MapDao {
	List<Maps> returncarinfo(String[] car);

	/**
	 * 管理员--地图中的车辆信息
	 * 
	 * @param carno
	 * @return
	 */
	List<Maps> loadmapinfo(String carno);

	List<Maps> loadmapinfo2(String carno);

	/**
	 * 司机--地图中的车辆信息
	 * 
	 * @param carno
	 * @param driver_id
	 * @return
	 */
	List<Maps> driverLoadmapinfo(String carno, String driver_id);

	/**
	 * 司供应商--地图中的车辆信息
	 * 
	 * @param carno
	 * @param suppliersId
	 * @return
	 */
	List<Maps> suppliersLoadmapinfo(String carno, String suppliersId);

	/**
	 * 信息员--地图中的车辆信息
	 * 
	 * @param carno
	 * @param user_id
	 * @return
	 */
	List<Maps> otherLoadmapinfo(String carno, String user_id);

	/**
	 * 管理员--车辆全图监控查询总条数
	 * 
	 * @param driverName
	 * @param dept
	 * @param driverPhone
	 * @param shiping_order_num
	 * @param shiping_order_goid
	 * @return
	 */
	int loadallcarinfocount(String driverName, String dept, String driverPhone,
			String shiping_order_num, String shiping_order_goid,String user_name,String user_address);

	/**
	 * 司机--车辆全图监控查询总条数
	 * 
	 * @param driverName
	 * @param dept
	 * @param driverPhone
	 * @param shiping_order_num
	 * @param shiping_order_goid
	 * @param driver_id
	 * @return
	 */
	int driverLoadallcarinfoCount(String driverName, String dept,
			String driverPhone, String shiping_order_num,
			String shiping_order_goid,String user_name,String user_address,String driver_id);

	/**
	 * 供应商--车辆全图监控查询总条数
	 * 
	 * @param driverName
	 * @param dept
	 * @param driverPhone
	 * @param shiping_order_num
	 * @param shiping_order_goid
	 * @param suppliersId
	 * @return
	 */
	int suppliersLoadallcarinfoCount(String driverName, String dept,
			String driverPhone, String shiping_order_num,
			String shiping_order_goid,String user_name,String user_address, String suppliersId);

	/**
	 * 信息员--车辆全图监控查询总条数
	 * 
	 * @param driverName
	 * @param dept
	 * @param driverPhone
	 * @param shiping_order_num
	 * @param shiping_order_goid
	 * @param userId
	 * @return
	 */
	int otherLoadallcarinfoCount(String driverName, String dept,
			String driverPhone, String shiping_order_num,
			String shiping_order_goid,String user_name,String user_address, String userId);

	/**
	 * 管理员--车辆全图监控查询
	 * 
	 * @param rows
	 * @param page
	 * @param driverName
	 * @param dept
	 * @param driverPhone
	 * @param shiping_order_num
	 * @param shiping_order_goid
	 * @return
	 */
	List<Maps> loadallcarinfo(int rows, int page, String driverName,
			String dept, String driverPhone, String shiping_order_num,
			String shiping_order_goid,String user_name,String user_address);

	/**
	 * 司机--车辆全图监控查询
	 * 
	 * @param rows
	 * @param page
	 * @param driverName
	 * @param dept
	 * @param driverPhone
	 * @param shiping_order_num
	 * @param shiping_order_goid
	 * @param driverId
	 * @return
	 */
	List<Maps> driverLoadallcarinfo(int rows, int page, String driverName,
			String dept, String driverPhone, String shiping_order_num,
			String shiping_order_goid,String user_name,String user_address,String driverId);

	/**
	 * 供应商--车辆全图监控查询
	 * 
	 * @param rows
	 * @param page
	 * @param driverName
	 * @param dept
	 * @param driverPhone
	 * @param shiping_order_num
	 * @param shiping_order_goid
	 * @param suppliersId
	 * @return
	 */
	List<Maps> suppliersLoadallcarinfo(int rows, int page, String driverName,
			String dept, String driverPhone, String shiping_order_num,
			String shiping_order_goid,String user_name,String user_address,String suppliersId);

	/**
	 * 项目部--车辆全图监控查询
	 * 
	 * @param rows
	 * @param page
	 * @param driverName
	 * @param dept
	 * @param driverPhone
	 * @param shiping_order_num
	 * @param shiping_order_goid
	 * @param userId
	 * @return
	 */
	List<Maps> otherLoadallcarinfo(int rows, int page, String driverName,
			String dept, String driverPhone, String shiping_order_num,
			String shiping_order_goid,String user_name,String user_address,String userId);

	/**
	 * 获取接单签收时间
	 * 
	 * @param shiping_order_num
	 * @param shiping_order_goid
	 * @param driverId
	 * @return
	 */
	Map<String, String> getOrderTime(String shiping_order_num,
			String shiping_order_goid, String driverId);

	/**
	 * 保存轨迹 jy_monitor_data
	 * 
	 * @param maps
	 * @return
	 */
	int saveMaps(Maps maps);

	/**
	 * 保存轨迹日期表
	 * 
	 * @param maps
	 * @param db
	 * @return
	 */
	int saveMapsweek(Maps maps, String db);

	/**
	 * 删除jy_monitor_data 数据
	 * 
	 * @param carid
	 * @return
	 */
	int deletecarbyid(String carid);

	int gethfpage(String tablename, String carno, String starttime,
			String endtime);

	List<Maps> gethfmessnozero(String tablename, String carno,
			String starttime, String endtime, String dbname, String page);// 查询非0绘画路线

	List<Remark> getallremark();// 加载所有的reamk信息

	List<Remark> getallremarkbydname(String dname);// 通过dname获取rmark信息

	List<OldMointorData> gethfinfo(String carno);

	List<Maps> gethfmess(String carno, String starttime, String endtime);

	// public List<Maps>getcarnotoselect(String carno);//获取所有的车牌号放进select2

	List<Maps> gethfmesspage(String carno, String starttime, String endtime,
			int rows, int page);// 加载页面分页查询

	int loadhfpage(String carno, String starttime, String endtime);// 总数量查询

	void l();

	void ul();
	
	//根据供应商用户id 获取司机信息
	List<Maps> getSupplerDriver(@Param("userId")String userId);

}
