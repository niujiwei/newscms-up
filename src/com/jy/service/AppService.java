package com.jy.service;

import java.util.List;
import java.util.Map;

import com.jy.model.*;
import net.sf.json.JSONObject;

import org.apache.ibatis.annotations.Param;

public interface AppService {

	/**
	 * APP 查询部门信息
	 * 
	 * @param departmentId
	 * @param departmentName
	 * @return
	 */
	public List<Department> appGetDepartmentInfo(String departmentId,
			String departmentName);

	/**
	 * APP 获取司机信息
	 * 
	 * @param user
	 * @param driverName
	 * @return
	 */
	public List<Driver> appGetDriverInfo(User user, String driverName,
			String type);

	/**
	 * APP 获取供应商信息
	 * 
	 * @param user
	 * @param supplersName
	 * @return
	 */
	public List<JySuppliers> appGetSupplersInfo(User user, String supplersName,
			String type);

	/**
	 * APP 获取项目部信息
	 * 
	 * @param user
	 * @param customersName
	 * @return
	 */
	public List<Customer> appGetCustomersInfo(User user, String customersName);

	/**
	 * APP--添加用户检查用户名是否存在
	 * 
	 * @param name
	 * @return
	 */
	public boolean appCheckUser(String name);

	/**
	 * APP 添加用户信息
	 * 
	 * @param user
	 * @return
	 */
	public boolean appSaveUserInfo(User user);

	/**
	 * APP 更新用户信息
	 * 
	 * @param user
	 * @return
	 */
	public boolean appUpdataUserInfo(User user);

	/**
	 * APP异常反馈 添加信息
	 * 
	 * @param abnormalReport
	 * @return
	 */
	public boolean appSaveAbnormalReport(AbnormalReport abnormalReport);

	/**
	 * APP异常反馈 保存图片信息
	 * 
	 * @param abnormalImages
	 * @return
	 */
	public boolean appSaveAbnormalImage(
			@Param("list") List<AbnormalImages> abnormalImages);

	/**
	 * 异常反馈更新异常状态
	 * 
	 * @param shippingOrderId
	 * @return
	 */
	public boolean appUpdateIsAbnormal(String shippingOrderId, String flag);

	/**
	 * APP 异常处理更新异常表中信息
	 * 
	 * @param id
	 * @param abnormal_resultremark
	 * @param userName
	 * @return
	 */
	boolean appUpdateAbnormalState(String id, String abnormal_resultremark,
			String userName);

	/**
	 * APP 异常处理查询运单是否有没有处理的
	 * 
	 * @param orderId
	 * @return
	 */
	boolean appGetAbnormalOrderNum(String orderId);

	/**
	 * APP 更新运单是否异常上报
	 * 
	 * @param orderId
	 * @return
	 */
	boolean appUpdateOrederAbnormal(String orderId);

	/**
	 * APP 运单分配查询是否已分配
	 * 
	 * @param orderId
	 * @return
	 */
	public boolean appShiFouFenPei(String orderId);

	/**
	 * APP 司机管理分配运单保存到管理表中
	 * 
	 * @param orders
	 * @return
	 */
	public int appSaveDrivertoOrder(List<DriverToOrder> orders);

	/**
	 * APP 分配订单修改状态
	 * 
	 * @param did
	 * @return
	 */
	public int appUpdateShipperorderState(String[] did);

	/**
	 * APP 司机接单
	 * 
	 * @param order_id
	 * @param driver_id
	 * @return
	 */
	public int appReceiveOrder(String order_id, String driver_id);

	/**
	 * APP 司机接单更新状态
	 * 
	 * @param flag
	 * @param order_id
	 * @return
	 */
	public int appUpdateOrderState(int flag, String order_id, String allTime);

	/**
	 * APP 查询司机姓名
	 * 
	 * @param driver_id
	 * @return
	 */
	public String appGetDriverName(String driver_id);

	/**
	 * APP 接单时获取表准时效要求
	 * 
	 * @param orderId
	 * @return
	 */
	public String appGetAgingAllTime(String orderId);

	/***
	 * APP 签收是否有签收信息
	 * 
	 * @param order_id
	 * @return
	 */
	public boolean appIsnotSignOrder(String order_id);

	/**
	 * APP 更新签收信息
	 * 
	 * @param sign_order
	 * @return
	 */
	public int appUpdateSignShipOrder(Sign_order sign_order);

	/**
	 * APP 运单签收保存签收信息
	 * 
	 * @param sign_order
	 * @return
	 */
	public int appSaveSignShipOrder(Sign_order sign_order);

	/**
	 * APP 运单签收保存图片路径
	 * 
	 * @param sign_id
	 * @param paths
	 * @param paths2
	 * @return
	 */
	int appSaveSignImages(List<ShipperOrderImg> shiOrdImg);

	/**
	 * APP 运单签收 更新运单状态
	 * 
	 * @param shiping_order_id
	 * @return
	 */
	public int appUpdatestate(String shiping_order_id);

	/**
	 * APP 运单签收 保存运单时效信息
	 * 
	 * @param shiping_order_id
	 * @return
	 */
	public int appInsertShippingTime(String shiping_order_id);

	/**
	 * APP获取供应商下面的司机
	 * 
	 * @param driverInfoSupp
	 * @return
	 */
	List<Driver> appGetDriverInfoSupp(String driverInfoSupp);

	/**
	 * APP 获取签收列表
	 * 
	 * @param row
	 * @param page
	 * @param send_time
	 * @param end_time
	 * @param shiping_order_num
	 * @param send_mechanism
	 * @param receipt_name
	 * @param end_address
	 * @param shiping_order_goid
	 * @param user
	 * @return
	 */
	List<ShippingOrder> appGetSignOrderInfo(int row, int page,
			String send_time, String end_time, String shiping_order_num,
			String send_mechanism, String receipt_name, String end_address,
			String shiping_order_goid, User user);

	/**
	 * APP 获取接单列表
	 * 
	 * @param row
	 * @param page
	 * @param send_time
	 * @param end_time
	 * @param shiping_order_num
	 * @param send_mechanism
	 * @param receipt_name
	 * @param end_address
	 * @param shiping_order_goid
	 * @param user
	 * @return
	 */
	List<ShippingOrder> appGetReceiveOrderInfo(int row, int page,
			String send_time, String end_time, String shiping_order_num,
			String send_mechanism, String receipt_name, String end_address,
			String shiping_order_goid, User user);

	/**
	 * APP 查询看消息信息
	 * 
	 * @param userId
	 * @param state
	 * @return
	 */
	List<MsgModel> appGetMsgInfo(User user, String state);

	/**
	 * APP 查询未读消息个数
	 * 
	 * @param userId
	 * @param state
	 * @return
	 */
	int appGetMsgCount(String userId, String state);

	/**
	 * APP 消息已读
	 * 
	 * @param userId
	 * @param state
	 * @return
	 */
	int appUpdateMsgState(String messageId, String state,String userId);

	/**
	 * APP 获取逆向物流未签收
	 * 
	 * @param row
	 * @param page
	 * @param send_time
	 * @param end_time
	 * @param shiping_order_num
	 * @param end_mechanism
	 * @param custom_name
	 * @return
	 */
	List<ShippingOrder> appGetReverseOrderInfo(int row, int page,
			String send_time, String end_time, String shiping_order_num,
			String end_mechanism, String custom_name,
			String shiping_order_state, User user);

	/**
	 * APP 查询逆向物流运单号是否存在
	 * 
	 * @param orderNum
	 * @return
	 */
	boolean appCheckReverseOrderNum(String orderNum);

	/**
	 * APP 逆向物流保存订单
	 * 
	 * @param shippingOrder
	 * @return
	 */
	int appSaveReverseOrder(ShippingOrder shippingOrder);

	/**
	 * APP 逆向物流签收保存签收信息
	 * 
	 * @param singOrder
	 * @return
	 */
	boolean appSaveSignReverseOrder(Sign_order singOrder);

	/**
	 * APP 逆向物流签收查看是否签收
	 * 
	 * @param singOrder
	 * @return
	 */
	boolean appCkeckReverseOrderCount(String orderId);

	/**
	 * APP 逆向物流保存签收图片
	 * 
	 * @param shiOrdImg
	 * @return
	 */
	boolean appSaveSignReverseOrderImage(
			@Param("list") List<ShipperOrderImg> shiOrdImg);

	/**
	 * APP 逆向物流更新签收信息
	 * 
	 * @param singOrder
	 * @return
	 */
	boolean appUpdateSignReverseOrder(Sign_order singOrder);

	/**
	 * APP 逆向物流签收更新运单状态
	 * 
	 * @param singOrder
	 * @return
	 */
	boolean appUpdateReverseOrderState(String shiping_order_id);

	/**
	 * APP 获取省市县
	 * 
	 * @param citye_parent_id
	 * @return
	 */
	List<City_info> appGetCityInfo(String citye_parent_id);

	/**
	 * APP 查询运单获取历史记录
	 * 
	 * @param orderId
	 * @param type
	 * @return
	 */
	List<OrderHistory> appGetOrderHistory(String orderId, String type);

	/**
	 * APP 查询订单签收图片信息
	 * 
	 * @param orderId
	 * @param type
	 * @return
	 */
	List<ShipperOrderImg> appGetOrderImages(String orderId, String type);

	/**
	 * APP 添加供应商信息
	 * 
	 * @param suppliers
	 * @return
	 */
	boolean appSaveSuppliers(JySuppliers suppliers);

	/**
	 * APP 保存供应商的省市县信息
	 * 
	 * @param suppliers
	 * @return
	 */
	boolean appSaveSuppliersCityInfo(JySuppliers suppliers);

	/**
	 * APP 司机管理司机信息添加
	 * 
	 * @param d
	 * @return
	 */
	boolean appSaveDriver(Driver d);

	/**
	 * APP 保存供应商的省市县信息
	 * 
	 * @param suppliers
	 * @return
	 */
	boolean appsaveDriverCityInfo(Driver driver);

	/**
	 * APP 司机管理修改司机信息
	 * 
	 * @param d
	 * @return
	 */
	boolean appUpdateDriver(Driver d);

	/**
	 * APP 更新供应商信息
	 * 
	 * @param jyuppliers
	 * @return
	 */
	boolean appUpdateSuppliers(JySuppliers jyuppliers);

	/**
	 * APP 逆向物流获取项目信息
	 * 
	 * @param user
	 * @param customersName
	 * @return
	 */
	public List<Customer> appReverseGetCustomersInfo(String customersName);

	/**
	 * APP 查询用户所有信息
	 * 
	 * @param user
	 * @return
	 */
	User appGetUserInfo(User user);

	/**
	 * 获取APP登录菜单权限
	 * 
	 * @param user
	 * @return
	 */
	List<String> getAppMenuRole(User user);





	/**
	 * 获取正向逆向物流的菜单权限
	 * @param fatherId
	 * @return
	 */
	List<String> appGetMenuAndRole(User user,String fatherId);

	/**
	 * APP 获取消息
	 * 
	 * @param id
	 * @return
	 */
	Map<String, Integer> getOrderMsgCount(@Param("id") String id);

	/**
	 * app 获取数量
	 */
	List<Map<String, Integer>> getNumberMessage(User user);


	/**
	 * app 获取逆向数量
	 */
	List<Map<String, Integer>> getReverseNumberMessage(User user);


	/**
	 * APP 获取首页消息信息
	 * @param user
	 * @return
	 */
	List<Map<String, Integer>> appGetMessageCount(User user);


	/**
	 * app 更新个人信息
	 * 
	 * @param user
	 * @param phone
	 * @return
	 */
	int appUpdatePersonInfo(User user, String phone);

	/**
	 * app 发送消息信息
	 * 
	 * @param lModels
	 * @return
	 */
	int appSaveMessageInfo(List<MsgModel> lModels);

	/**
	 * 查询gps 设备表是否存用户姓名
	 * 
	 * @param username
	 * @return
	 */
	int getGpsInfoCount(String username);

	/**
	 * app 获取定位时间
	 * 
	 * @return
	 */
	AppVersion appGetLocationTime();

	/**
	 * 判断用户是否存在GPS
	 * 
	 * @param driverId
	 * @return
	 */
	int appDriverISHaveGps(String driverId);

	/**
	 * 根据司机id 获取用户名
	 * 
	 * @param driverId
	 * @return
	 */
	String driverGetUserName(String driverId);

	/**
	 * 
	 * @param rows
	 * @param page
	 * @param name
	 * @param start_date
	 * @param end_date
	 * @param shipping_order_state
	 * @param custom_name
	 * @param receipt
	 * @param address
	 * @param shipperorder_id
	 * @param user
	 * @return
	 */
	JSONObject appSearchShipperOrderInfoGroupByRole(int rows, int page,
			String name, String start_date, String end_date,
			String shipping_order_state, String custom_name, String receipt,
			String address, String shipperorder_id, User user);


	/**
	 * 逆向物流接单信息
	 *
	 * @param user
	 * @param start
	 * @param length
	 * @param send_time
	 * @param end_time
	 * @param end_address
	 * @param send_mechanism
	 * @param custom_contact
	 * @param order_type
	 * @return
	 */
	List<ReverserOrderModel> appGetTakingReverseOrderInfo(User user, Integer start, Integer length, String send_time, String end_time,
														  String end_address, String send_mechanism, String custom_contact, String order_type);

	/**
	 * app 获取签收列表信息
	 *
	 * @param user
	 * @param start
	 * @param length
	 * @param send_time
	 * @param end_time
	 * @param shiping_order_num
	 * @param shipping_order_state
	 * @param order_type
	 * @param send_mechanism
	 * @param end_address
	 * @param end_mechanism
	 * @return
	 */
	List<ReverserOrderModel> appGetSignReverseOrderInfo(User user, Integer start, Integer length, String send_time, String end_time, String shiping_order_num, String shipping_order_state, String order_type, String send_mechanism, String end_address, String end_mechanism);

	/**
	 * app 获取更新信息
	 * @return
	 */
	List<String> appGetUpdateMessage();


	/**
	 * 逆向物流查询
	 * @param user
	 * @param start
	 * @param length
	 * @param send_time
	 * @param end_time
	 * @param shiping_order_num
	 * @param shipping_order_state
	 * @param order_type
	 * @param send_mechanism
	 * @param end_address
	 * @param end_mechanism
	 * @return
	 */
	Map<String, Object> getSignReverseOrderInfo(User user, Integer start, Integer length, String send_time, String end_time, String shiping_order_num, String shipping_order_state, String order_type, String send_mechanism, String end_address, String end_mechanism);


	boolean getIsAbnormalReportCount(@Param("orderId") String orderId);
}
