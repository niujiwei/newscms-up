package com.jy.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jy.model.AbnormalReport;
import com.jy.model.Menu;
import com.jy.model.ShippingOrder;
import com.jy.model.ShippingTimes;
import com.jy.model.User;

public interface LoginDao {
	int checkUser(User u);

	// 注册用户
	int addUser(User user);

	// 查询用户所有信息
	User getUserInfo(User user);

	// 获取用户权限
	Integer[] getUserRote(String id);

	// 查询用户密码是否正确
	int checkPas(String id, String password);

	int updatePas(int id, String password);

	// 更新登录时间
	void updateUserTime(String id);

	List<String> getRoles(String id);

	String getCarCount(String id);

	/**
	 * 更新app标识
	 * 
	 * @param id
	 * @param id2
	 * @return
	 */
	int updatechannelId(String cannelId, String device_type, String id);

	// scms 思唯新增
	// 待配订单页面
	public String[] getOrderid(String driver_id, int flag, String order_id,
			String rownum, String rows, String shiping_order_goid,
			String custom_name, String receipt_name);

	List<ShippingOrder> getOrders(@Param("array") String[] orderids);// 展示订单所有基本信息

	int receiveOrder(String order_id, String driver_id);

	String getDriverName(String driver_id);

	// 配送页面和签收查询页面
	public String[] getOrderidss(String driver_id, int flag, int x,
			String order_id, String rownum, String rows, String startTime,
			String endTime, String state, String shiping_order_goid,
			String custom_name, String receipt_name);

	int updateOrderState(int flag, String order_id);

	/**
	 * 首页查询获取运单数量信息
	 * 
	 * @return
	 */
	public List<Map<String, Integer>> getOrderCountMessage(
			@Param("result") String result,
			@Param("abnormal_result") String abnormal_result,@Param("startDate")String startDate);

	/**
	 * 司机---首页查询获取运单数量信息
	 * 
	 * @return
	 */
	public List<Map<String, Integer>> getDriverOrderCountMessage(
			@Param("result") String result,
			@Param("abnormal_result") String abnormal_result,
			@Param("driverId") String driverId,@Param("startDate")String startDate);

	/**
	 * 供应商---首页查询获取运单数量信息
	 * 
	 * @return
	 */
	public List<Map<String, Integer>> getSupperOrderCountMessage(
			@Param("result") String result,
			@Param("abnormal_result") String abnormal_result,
			@Param("suppliersId") String suppliersId,
			@Param("list") List<String> address,@Param("startDate")String startDate);

	/**
	 * 司机---首页查询获取运单数量信息
	 * 
	 * @return
	 */
	public List<Map<String, Integer>> getOtherOrderCountMessage(
			@Param("result") String result,
			@Param("abnormal_result") String abnormal_result,
			@Param("userId") String userId,@Param("startDate")String startDate);

	/**
	 * 获取问题件
	 * 
	 * @return
	 */
	public List<AbnormalReport> getAbnormalReportInfo(@Param("row") int row,
			@Param("page") int page);

	/**
	 * 获取问题件总个数
	 * 
	 * @return
	 */
	public int getAbnormalReportCount();

	/**
	 * 获取异常信息
	 * 
	 * @return
	 */
	public List<ShippingTimes> getShippingTimes(@Param("result") String result,
			@Param("row") int row, @Param("page") int page);
	
	/**
	 * 中间center 查询点击的页面信息
	 * @param user
	 * @param tittle
	 * @return
	 */
	public List<Menu> centerAddTabInfo(@Param("id")String id,@Param("tittle")String tittle);

}
