package com.jy.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.jy.common.MD5;
import com.jy.dao.LoginDao;
import com.jy.dao.RemarkMapDao;
import com.jy.model.AbnormalReport;
import com.jy.model.Menu;
import com.jy.model.ShippingOrder;
import com.jy.model.ShippingTimes;
import com.jy.model.User;
import com.jy.service.LoginUserService;

@Component
public class LoginUserServiceImpl implements LoginUserService {
	@Resource
	private LoginDao loginDao;
	@Resource
	private RemarkMapDao rms_dao;

	public boolean checkUser(User u) {
		String userPassWord = u.getPassword();
		MD5 md5 = new MD5();
		u.setPassword(md5.getMD5ofStr(userPassWord));
		int userflag = loginDao.checkUser(u);

		if (userflag > 0) {
			return true;
		} else {
			return false;
		}
	}

	// 查询所有用户信息
	public User getUserInfo(User user) {
		User userinfo = loginDao.getUserInfo(user);
		return userinfo;
	}

	// 查询用户权限
	public Integer[] getUserRote(String id) {
		Integer[] roteId = loginDao.getUserRote(id);

		return roteId;

	}

	// 查询密码是否正确
	public int checkPas(String id, String password) {
		int roteId = loginDao.checkPas(id, password);
		return roteId;
	}

	// 修改密码
	public int updatePas(int id, String password) {
		int roteId = loginDao.updatePas(id, password);
		return roteId;
	}

	/*
	 * public int addUser(User user) { // MD5加密密码 String userPassWord =
	 * user.getUser_password(); MD5 md5 = new MD5();
	 * user.setUser_password(md5.getMD5ofStr(userPassWord)); int flag =
	 * loginDao.addUser(user); return flag; }
	 */
	// 更新登录时间
	public void updateUserTime(String id) {
		loginDao.updateUserTime(id);
	}

	public List<String> getRoles(String id) {
		List<String> roles = loginDao.getRoles(id);
		return roles;
	}

	public String getCarCount(String id) {

		String carflag = loginDao.getCarCount(id);
		return carflag;
	}

	/**
	 * 更新app标识
	 */
	public boolean updatechannelId(String cannelId, String device_type,
			String id) {

		int flag = loginDao.updatechannelId(cannelId, device_type, id);
		if (flag > 0)
			return true;
		else
			return false;
	}

	public List<ShippingOrder> getOrders(String[] orderids) {

		return loginDao.getOrders(orderids);
	}

	public int receiveOrder(String order_id, String driver_id) {
		return loginDao.receiveOrder(order_id, driver_id);
	}

	public String getDriverName(String driver_id) {

		return loginDao.getDriverName(driver_id);
	}

	public int updateOrderState(int flag, String order_id) {

		return loginDao.updateOrderState(flag, order_id);
	}

	public String[] getOrderid(String driver_id, int flag, String order_id,
			String rownum, String rows, String shiping_order_goid,
			String custom_name, String receipt_name) {

		return loginDao.getOrderid(driver_id, flag, order_id, rownum, rows,
				shiping_order_goid, custom_name, receipt_name);
	}

	public String[] getOrderidss(String driver_id, int flag, int x,
			String order_id, String rownum, String rows, String startTime,
			String endTime, String state, String shiping_order_goid,
			String custom_name, String receipt_name) {

		return loginDao.getOrderidss(driver_id, flag, x, order_id, rownum,
				rows, startTime, endTime, state, shiping_order_goid,
				custom_name, receipt_name);
	}

	// 首页的运单信息条数查询
	public List<Map<String, Integer>> getOrderCountMessage(String result,
			String abnormal_result, User user, String startDate) {
		List<Map<String, Integer>> map = null;

		if (user != null) {
			if (user.getFlag().equals("0")) {// 管理员
				map = loginDao.getOrderCountMessage(result, abnormal_result,
						startDate);
			} else if (user.getFlag().equals("1")) {// 司机
				map = loginDao.getDriverOrderCountMessage(result,
						abnormal_result, user.getDriver_id(), startDate);
			} else if (user.getFlag().equals("2")) {// 供应商
				List<String> user_address = rms_dao.getAddressSupperlies(user
						.getSuppliers_id());
				map = loginDao.getSupperOrderCountMessage(result,
						abnormal_result, user.getSuppliers_id(), user_address,
						startDate);

			} else if (user.getFlag().equals("3") || user.getFlag().equals("4")) {// 信息员
				map = loginDao.getOtherOrderCountMessage(result,
						abnormal_result, user.getId(), startDate);

			}

		}
		return map;
	}

	// 获取异常反馈信息
	public List<AbnormalReport> getAbnormalReportInfo(int row, int page,
			User user) {

		return loginDao.getAbnormalReportInfo(row, page);
	}

	public List<ShippingTimes> getShippingTimes(String result, int row, int page) {

		return loginDao.getShippingTimes(result, row, page);
	}

	// 获取问题件的总个数
	public int getAbnormalReportCount() {

		return loginDao.getAbnormalReportCount();
	}

	// 中间center 查询点击的页面信息
	public List<Menu> centerAddTabInfo(User user, String tittle) {
		List<Menu> list = new ArrayList<Menu>();
		if (user == null)
			return list;
		list = loginDao.centerAddTabInfo(user.getId(), tittle);
		if(user.getFlag().equals("1")||user.getFlag().equals("2")){
			Menu menu = list.get(0);
			if(menu!=null&&menu.getText().equals("运单录入")){
				menu.setText("运单查看");
			}
		}
		return list;
	}

}
