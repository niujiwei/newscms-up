package com.jy.service;

import java.util.List;
import java.util.Map;

import com.jy.model.AbnormalReport;
import com.jy.model.Menu;
import com.jy.model.ShippingOrder;
import com.jy.model.ShippingTimes;
import com.jy.model.User;

public interface LoginUserService {
	// 判断用户是否存在
	public boolean checkUser(User u);

	// 注册用户信息
	// public int addUser(User user);
	// 获取用户详细信息
	public User getUserInfo(User user);

	// 获取用户权限
	public Integer[] getUserRote(String id);

	// 查询密码是否正确
	public int checkPas(String id, String password);

	// 更新密码
	public int updatePas(int id, String password);

	// 更新登录时间
	public void updateUserTime(String id);

	public List<String> getRoles(String id);

	public String getCarCount(String id);

	/**
	 * 更新app标识
	 * 
	 * @param id
	 * @param string
	 * @return
	 */
	public boolean updatechannelId(String cannelId, String device_type,
			String id);

	// scms 思唯新增
	// 待配订单页面
	public String[] getOrderid(String driver_id, int flag, String order_id,
			String rownum, String rows, String shiping_order_goid,
			String custom_name, String receipt_name);

	// 展示订单所有基本信息
	public List<ShippingOrder> getOrders(String[] orderids);

	public int receiveOrder(String order_id, String driver_id);

	public String getDriverName(String driver_id);

	// 配送页面和签收查询页面订单id
	public String[] getOrderidss(String driver_id, int flag, int x,
			String order_id, String rownum, String rows, String startTime,
			String endTime, String state, String shiping_order_goid,
			String custom_name, String receipt_name);

	public int updateOrderState(int flag, String order_id);
	
	/**
	 * 获取首页运单信息
	 * @return
	 */
	public List<Map<String, Integer>> getOrderCountMessage(String result,String abnormal_result,User user,String startDate);
	
	/**
	 * 获取问题件
	 * @return
	 */
	public List<AbnormalReport> getAbnormalReportInfo(int row,int page,User user);
	
	/**
	 * 获取问题件总个数
	 * @return
	 */
	public int getAbnormalReportCount();
	
	/**
	 * 获取异常信息
	 * @return
	 */
	public List<ShippingTimes> getShippingTimes(String result,int row,int page);
	
	/**
	 * 中间center 查询点击的页面信息
	 * @param user
	 * @param tittle
	 * @return
	 */
	public List<Menu> centerAddTabInfo(User user,String tittle);
}
