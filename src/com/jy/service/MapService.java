package com.jy.service;

/***
 * @author  黄清华

 * @param  全图监控模块
 * @since 1.6
 * @version 1.0
 * @throws null
 * @createtime 2015-6-3
 */
import java.util.List;

import com.jy.model.Maps;
import com.jy.model.OldMointorData;
import com.jy.model.Remark;
import com.jy.model.User;

//画框取车返回的结果集
public interface MapService {

	/**
	 * 地图上的车辆信息
	 * 
	 * @param carno
	 * @param user
	 * @return
	 */
	List<Maps> loadmapinfo(String carno);

	List<Maps> loadmapinfo(String carno, User user);

	List<Maps> returncarinfo(String[] car);

	/**
	 * 车辆全图监控查询
	 * 
	 * @param rows
	 * @param page
	 * @param driverName
	 * @param dept
	 * @param driverPhone
	 * @param user
	 * @return
	 */
	List<Maps> loadallcarinfo(int rows, int page, String driverName,
			String dept, String driverPhone, String shiping_order_num,
			String shiping_order_goid, User user,String user_name,String user_address);

	/**
	 * 车辆全图监控查询总条数
	 * 
	 * @param driverName
	 * @param dept
	 * @param driverPhone
	 * @param user
	 * @return
	 */
	int loadallcarinfocount(String driverName, String dept, String driverPhone,
			String shiping_order_num, String shiping_order_goid, User user,String user_name,String user_address);// 总数量查询

	List<Remark> getallremark();// 加载所有的reamk信息

	List<Remark> getallremarkbydname(String dname);// 通过连锁店名字获取信息
	// List<Maps>getcarnotoselect(String carno);//获取所有的车牌号放进select2

	// 回放-----------跟踪
	List<OldMointorData> gethfinfo(String carno);

	List<Maps> gethfmess(String carno, String starttime, String endtime);

	List<Maps> gethfmesspage(String carno, String starttime, String endtime,
			int rows, int page);// 加载页面分页查询

	int loadhfpage(String carno, String starttime, String endtime);// 总数量查询

	public int updateDepartment(String creatingUser, String institutionId);

	/**
	 * 保存轨迹 jy_monitor_data
	 * @param maps
	 * @return
	 */
	public int saveMaps(Maps maps);
	
	/**
	 * 保存轨迹日期表
	 * @param maps
	 * @param db
	 * @return
	 */
	public int saveMapsweek(Maps maps, String db);
	
	/**
	 * 删除jy_monitor_data 数据
	 * @param carid
	 * @return
	 */
	public int deletecarbyid(String carid);

	int gethfpage(String tablename, String carno, String starttime,
			String endtime);

	public List<Maps> gethfmessnozero(String tablename, String carno,
			String starttime, String endtime, String dbname, String page);// 查询非0绘画路线
	/**
	 * 根据供应商用户id 获取司机信息
	 * @param userId
	 * @return
	 */
	public List<Maps> getSupplerDriver(String userId);
}
