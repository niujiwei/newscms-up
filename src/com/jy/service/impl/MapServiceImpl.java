package com.jy.service.impl;

/***
 * @author  黄清华

 * @param  全图监控模块
 * @since 1.6
 * @version 1.0
 * @throws null
 * @createtime 2015-6-3
 */
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.jy.common.UUIDUtils;
import com.jy.dao.MapDao;
import com.jy.dao.ShippingOrderDao;
import com.jy.model.Maps;
import com.jy.model.OldMointorData;
import com.jy.model.Remark;
import com.jy.model.User;
import com.jy.service.MapService;

@Component
public class MapServiceImpl implements MapService {

	@Resource
	private MapDao mapDao;
	@Resource
	private ShippingOrderDao sod;

	public List<Maps> returncarinfo(String[] car) {
		return mapDao.returncarinfo(car);
	}

	public List<Maps> loadmapinfo(String carno) {
		return mapDao.loadmapinfo2(carno);
	}

	// 地图上的车辆信息
	public List<Maps> loadmapinfo(String carno, User user) {
		List<Maps> mapjglist = null;
		// try {
		if (user.getFlag().equals("0")) {// 管理员

			mapjglist = mapDao.loadmapinfo(carno);

		} else if (user.getFlag().equals("1")) {// 司机
			mapjglist = mapDao.driverLoadmapinfo(carno, user.getDriver_id());

		} else if (user.getFlag().equals("2")) {// 供应商
			mapjglist = mapDao.suppliersLoadmapinfo(carno,
					user.getSuppliers_id());

		} else if (user.getFlag().equals("3") || user.getFlag().equals("4")) {// 信息员
			mapjglist = mapDao.otherLoadmapinfo(carno, user.getId());
		}
		// mapDao.l();

		// mapDao.ul();
		/*
		 * } catch (Exception e) { e.printStackTrace(); // mapDao.ul(); }
		 */
		return mapjglist;
	}

	// 车辆全图监控查询
	public List<Maps> loadallcarinfo(int rows, int page, String driverName,
			String dept, String driverPhone, String shiping_order_num,
			String shiping_order_goid, User user,String user_name,String user_address) {
		List<Maps> list = null;
		if (user.getFlag().equals("0")) {// 管理员
			list = mapDao.loadallcarinfo(rows, page, driverName, dept,
					driverPhone, shiping_order_num, shiping_order_goid,user_name,user_address);
		} else if (user.getFlag().equals("1")) {// 司机
			list = mapDao.driverLoadallcarinfo(rows, page, driverName, dept,
					driverPhone, shiping_order_num, shiping_order_goid,
					user_name,user_address,user.getDriver_id());
		} else if (user.getFlag().equals("2")) {// 供应商
			list = mapDao.suppliersLoadallcarinfo(rows, page, driverName, dept,
					driverPhone, shiping_order_num, shiping_order_goid,user_name,user_address,user.getSuppliers_id());
		} else if (user.getFlag().equals("3") || user.getFlag().equals("4")) {// 信息员
			list = mapDao.otherLoadallcarinfo(rows, page, driverName, dept,
					driverPhone, shiping_order_num, shiping_order_goid,
					user_name,user_address,user.getId());

		}
		if (shiping_order_num == null && shiping_order_goid == null)
			return list;

		return getOrderTime(list, shiping_order_num, shiping_order_goid);
	}

	// 获取接单签收时间
	List<Maps> getOrderTime(List<Maps> list, String shiping_order_num,
			String shiping_order_goid) {
		if (list != null) {
			if ((shiping_order_num != null && !shiping_order_num.equals(""))
					|| (shiping_order_goid != null && !shiping_order_goid
							.equals(""))) {
				for (Maps maps : list) {
					Map<String, String> map = mapDao.getOrderTime(
							shiping_order_num, shiping_order_goid,
							maps.getDriverId());
					if (map != null) {
						maps.setReceivetime(map.get("receivetime"));
						maps.setSigntime(map.get("signtime"));
					}
				}
			}
		}

		return list;

	}

	// 车辆全图监控总条数
	public int loadallcarinfocount(String driverName, String dept,
			String driverPhone, String shiping_order_num,
			String shiping_order_goid, User user,String user_name,String user_address) {
		int count = 0;

		if (user.getFlag().equals("0")) {// 管理员
			count = mapDao.loadallcarinfocount(driverName, dept, driverPhone,
					shiping_order_num, shiping_order_goid,user_name,user_address);
		} else if (user.getFlag().equals("1")) {// 司机
			count = mapDao.driverLoadallcarinfoCount(driverName, dept,
					driverPhone, shiping_order_num, shiping_order_goid,
					user_name,user_address,user.getDriver_id());

		} else if (user.getFlag().equals("2")) {// 供应商
			count = mapDao.suppliersLoadallcarinfoCount(driverName, dept,
					driverPhone, shiping_order_num, shiping_order_goid,user_name,user_address,
					user.getSuppliers_id());

		} else if (user.getFlag().equals("3") || user.getFlag().equals("4")) {// 供应商
			count = mapDao.otherLoadallcarinfoCount(driverName, dept,
					driverPhone, shiping_order_num, shiping_order_goid,user_name,user_address,
					user.getId());

		}
		return count;
	}

	public List<Remark> getallremark() {
		List<Remark> allrmklist = null;
		try {
			allrmklist = mapDao.getallremark();
		} catch (Exception e) {
			return null;
		}
		return allrmklist;
	}

	public List<Remark> getallremarkbydname(String dname) {
		List<Remark> onermklist = null;
		try {
			onermklist = mapDao.getallremarkbydname(dname);
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return onermklist;
	}

	public List<OldMointorData> gethfinfo(String carno) {
		List<OldMointorData> omdlist = null;
		try {
			omdlist = mapDao.gethfinfo(carno);
		} catch (Exception e) {
		}
		return omdlist;
	}

	/*
	 * public List<Maps> getcarnotoselect(String carno) {
	 * List<Maps>carnolist=null; try { carnolist=mapDao.getcarnotoselect(carno);
	 * } catch (Exception e) { } return carnolist; }
	 */
	/**
	 * 查询回放使用的坐标点
	 */
	public List<Maps> gethfmess(String carno, String starttime, String endtime) {
		List<Maps> maphflist = null;
		try {
			maphflist = mapDao.gethfmess(carno, starttime, endtime);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return maphflist;
	}

	public List<Maps> gethfmesspage(String carno, String starttime,
			String endtime, int rows, int page) {
		try {
			List<Maps> list = mapDao.gethfmesspage(carno, starttime, endtime,
					rows, page);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public int loadhfpage(String carno, String starttime, String endtime) {
		int count = mapDao.loadhfpage(carno, starttime, endtime);
		return count;
	}

	public int updateDepartment(String creatingUser, String institutionId) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	//保存轨迹 jy_monitor_data
	public int saveMaps(Maps maps) {
		maps.setMonitorid(UUIDUtils.getUUID());
		//maps.setState("1");
		int count = mapDao.saveMaps(maps);
		return count;
	}
	
	// 删除jy_monitor_data 数据
	public int deletecarbyid(String carid) {
		int count = mapDao.deletecarbyid(carid);
		return count;
	}

	public int saveMapsweek(Maps maps, String db) {
		maps.setMonitorid(UUIDUtils.getUUID());
		maps.setState("1");
		int count = mapDao.saveMapsweek(maps, db);
		return count;
	}

	public int gethfpage(String tablename, String carno, String starttime,
			String endtime) {
		int num = 0;
		try {
			num = mapDao.gethfpage(tablename, carno, starttime, endtime);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return num;
	}

	public List<Maps> gethfmessnozero(String tablename, String carno,
			String starttime, String endtime, String dbname, String page) {
		List<Maps> maphflist = null;
		try {
			maphflist = mapDao.gethfmessnozero(tablename, carno, starttime,
					endtime, dbname, page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return maphflist;
	}

	//根据供应商用户id 获取司机信息
	public List<Maps> getSupplerDriver(String userId) {
		
		return mapDao.getSupplerDriver(userId);
	}
}
