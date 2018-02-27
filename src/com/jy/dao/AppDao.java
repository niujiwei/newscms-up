package com.jy.dao;

import java.util.List;
import java.util.Map;

import com.baidu.yun.core.annotation.R;
import com.jy.model.*;
import org.apache.ibatis.annotations.Param;

/**
 * app 的dao
 * 
 * @author 90
 * 
 */
public interface AppDao {
	/**
	 * APP 查询部门信息
	 * 
	 * @param departmentId
	 * @param departmentName
	 * @return
	 */
	public List<Department> appGetDepartmentInfo(
			@Param("departmentId") String departmentId,
			@Param("departmentName") String departmentName);

	/**
	 * APP 管理员查询司机信息
	 * 
	 * @param user
	 * @param driverName
	 * @return
	 */
	public List<Driver> appGetDriverInfo(
			@Param("driverName") String driverName, @Param("type") String type);

	/**
	 * APP 司机查询司机信息
	 * 
	 * @param driverName
	 * @return
	 */
	public List<Driver> appDriverGetDriverInfo(
			@Param("driverId") String driverid,
			@Param("driverName") String driverName, @Param("type") String type);

	/**
	 * APP 供应商查询司机信息
	 * 
	 * @param driverName
	 * @return
	 */
	public List<Driver> appSupplersGetDriverInfo(
			@Param("supplersId") String supplersId,
			@Param("driverName") String driverName, @Param("type") String type);

	/**
	 * APP 项目部询司机信息
	 * 
	 * @param driverName
	 * @return
	 */
	public List<Driver> appOtherGetDriverInfo(
			@Param("customerId") String customerId,
			@Param("driverName") String driverName, @Param("type") String type);

	/**
	 * APP 管理员查询
	 * 
	 * @param supplersName
	 * @return
	 */
	public List<JySuppliers> appGetSuppliersInfo(
			@Param("supplersName") String supplersName,
			@Param("type") String type);

	/**
	 * APP 项目部查询
	 * 
	 * @param supplersName
	 * @return
	 */
	public List<JySuppliers> appOtherGetSuppliersInfo(
			@Param("supplersName") String supplersName,
			@Param("customerId") String customerId, @Param("type") String type);

	/**
	 * APP 获取项目信息
	 * 
	 * @param user
	 * @param customersName
	 * @return
	 */
	public List<Customer> appGetCustomersInfo(
			@Param("customersName") String customersName);

	/**
	 * APP--添加用户检查用户名是否存在
	 * 
	 * @param name
	 * @return
	 */
	public int appCheckUser(@Param("name") String name);

	/**
	 * APP 添加用户信息
	 * 
	 * @param user
	 * @return
	 */
	public int appSaveUserInfo(User user);

	/**
	 * APP 更新用户信息
	 * 
	 * @param user
	 * @return
	 */
	public int appUpdataUserInfo(User user);

	/**
	 * APP 异常反馈 添加信息
	 * 
	 * @param abnormalReport
	 * @return
	 */
	public int appSaveAbnormalReport(AbnormalReport abnormalReport);

	/**
	 * APP 异常反馈 保存图片信息
	 * 
	 * @param abnormalImages
	 * @return
	 */
	public int appSaveAbnormalImage(
			@Param("list") List<AbnormalImages> abnormalImages);

	/**
	 * APP 异常反馈更新异常状态
	 * 
	 * @param shippingOrderId
	 * @return
	 */
	public int appUpdateIsAbnormal(
			@Param("shippingOrderId") String shippingOrderId,
			@Param("flag") String flag);

	/**
	 * APP 异常处理更新异常表中信息
	 * 
	 * @param id
	 * @param abnormal_resultremark
	 * @param userName
	 * @return
	 */
	public int appUpdateAbnormalState(@Param("id") String id,
			@Param("abnormal_resultremark") String abnormal_resultremark,
			@Param("userName") String userName);

	/**
	 * APP 异常处理查询运单是否有没有处理的
	 * 
	 * @param orderId
	 * @return
	 */
	public int appGetAbnormalOrderNum(@Param("orderId") String orderId);

	/**
	 * APP更新运单是否异常上报
	 * 
	 * @param orderId
	 * @return
	 */
	public int appUpdateOrederAbnormal(@Param("orderId") String orderId);

	/**
	 * APP 运单分配查询是否已分配
	 * 
	 * @param orderId
	 * @return
	 */
	public int appShiFouFenPei(@Param("orderId") String orderId);

	/**
	 * APP 司机管理分配运单保存
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
	public int appUpdateShipperorderState(@Param("array") String[] did);

	/**
	 * APP 司机接单
	 * 
	 * @param order_id
	 * @param driver_id
	 * @return
	 */
	public int appReceiveOrder(String order_id, String driver_id);

	/**
	 * APP 接单更新运单状态
	 * 
	 * @param flag
	 * @param order_id
	 * @return
	 */
	public int appUpdateOrderState(@Param("flag") int flag,
			@Param("order_id") String order_id, @Param("allTime") String allTime);

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
	public String appGetAgingAllTime(@Param("orderId") String orderId);

	/***
	 * APP 签收是否有签收信息
	 * 
	 * @param order_id
	 * @return
	 */
	public int appIsnotSignOrder(String order_id);

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
	int appSaveSignImages(@Param("list") List<ShipperOrderImg> shiOrdImg);

	/**
	 * APP 运单签收 更新运单状态
	 * 
	 * @param shiping_order_id
	 * @return
	 */
	public int appUpdatestate(@Param("shiping_order_id") String shiping_order_id);

	/**
	 * APP 运单签收 保存运单时效信息
	 * 
	 * @param shiping_order_id
	 * @return
	 */
	public int appInsertShippingTime(
			@Param("shiping_order_id") String shiping_order_id);

	/**
	 * APP获取供应商下面的司机
	 * 
	 * @param driverInfoSupp
	 * @return
	 */
	List<Driver> appGetDriverInfoSupp(@Param("supplerId") String driverInfoSupp);

	/**
	 * APP 管理员获取要签收的信息
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
	 * @return
	 */
	List<ShippingOrder> appGetAllSignOrderInfo(int row, int page,
			String shiping_order_num, String send_time, String end_time,
			String send_mechanism, String receipt_name, String end_address,
			String shiping_order_goid);

	/**
	 * APP 获取要签收的信息
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
	 * @return
	 */
	List<ShippingOrder> appGetSignOrderInfo(int row, int page,
			String shiping_order_num, String send_time, String end_time,
			String send_mechanism, String receipt_name, String end_address,
			String shiping_order_goid, @Param("driverId") String driverId);

	/**
	 * APP 获取要签收的信息
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
	 * @return
	 */
	List<ShippingOrder> appAllGetReceiveOrderInfo(int row, int page,
			String shiping_order_num, String send_time, String end_time,
			String send_mechanism, String receipt_name, String end_address,
			String shiping_order_goid);

	/**
	 * APP 获取要签收的信息
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
	 * @return
	 */
	List<ShippingOrder> appGetReceiveOrderInfo(int row, int page,
			String shiping_order_num, String send_time, String end_time,
			String send_mechanism, String receipt_name, String end_address,
			String shiping_order_goid, @Param("driverId") String driverId);

	/**
	 * APP 查询看消息信息
	 * 
	 * @param userId
	 * @param state
	 * @return
	 */
	List<MsgModel> appGetMsgInfo(@Param("userId") String userId,
			@Param("state") String state);

	/**
	 * APP 查询未读消息个数
	 * 
	 * @param userId
	 * @param state
	 * @return
	 */
	int appGetMsgCount(@Param("userId") String userId,
			@Param("state") String state);

	/**
	 * APP 消息已读
	 * 
	 * @param userId
	 * @param state
	 * @return
	 */
	int appUpdateMsgState(@Param("messageId") String messageId,
			@Param("state") String state,@Param("userId")String userId);

	/**
	 * APP 供应商查询逆向物流信息
	 * 
	 * @param row
	 * @param page
	 * @param send_time
	 * @param end_time
	 * @param shiping_order_num
	 * @param end_mechanism
	 * @param custom_name
	 * @param supplerIds
	 * @return
	 */
	List<ShippingOrder> appGetSupplerReverseOrderInfo(int row, int page,
			String send_time, String end_time, String shiping_order_num,
			String end_mechanism, String custom_name,
			String shiping_order_state, @Param("supplerIds") String supplerIds);

	/**
	 * APP 司机查询逆向物流
	 * 
	 * @param row
	 * @param page
	 * @param send_time
	 * @param end_time
	 * @param shiping_order_num
	 * @param end_mechanism
	 * @param custom_name
	 * @param driverIds
	 * @return
	 */
	List<ShippingOrder> appGetDriverReverseOrderInfo(int row, int page,
			String send_time, String end_time, String shiping_order_num,
			String end_mechanism, String custom_name,
			String shiping_order_state, @Param("driverIds") String driverIds);

	/**
	 * APP 项目部查询逆向物流信息
	 * 
	 * @param row
	 * @param page
	 * @param send_time
	 * @param end_time
	 * @param shiping_order_num
	 * @param end_mechanism
	 * @param custom_name
	 * @param customerId
	 * @return
	 */

	List<ShippingOrder> appGetOtherReverseOrderInfo(int row, int page,
			String send_time, String end_time, String shiping_order_num,
			String end_mechanism, String custom_name,
			String shiping_order_state, @Param("customerId") String customerId);

	/**
	 * APP 获取所有的逆向物流信息
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
			String end_mechanism, String custom_name, String shiping_order_state);

	/**
	 * APP 查询逆向物流运单号是否存在
	 * 
	 * @param orderNum
	 * @return
	 */
	int appCheckReverseOrderNum(@Param("orderNum") String orderNum);

	/**
	 * APP 逆向物流保存订单
	 * 
	 * @param shippingOrder
	 * @return
	 */
	int appSaveReverseOrder(ShippingOrder shippingOrder);

	/**
	 * APP 逆向物流签收保存查询是否有签收信息
	 * 
	 * @param singOrder
	 * @return
	 */
	int appCkeckReverseOrderCount(@Param("orderId") String orderId);

	/**
	 * APP 逆向物流签收保存签收信息
	 * 
	 * @param singOrder
	 * @return
	 */
	int appSaveSignReverseOrder(Sign_order singOrder);

	/**
	 * APP 逆向物流更新签收信息
	 * 
	 * @param singOrder
	 * @return
	 */
	int appUpdateSignReverseOrder(Sign_order singOrder);

	/**
	 * APP 逆向物流保存签收图片
	 * 
	 * @param shiOrdImg
	 * @return
	 */
	int appSaveSignReverseOrderImage(
			@Param("list") List<ShipperOrderImg> shiOrdImg);

	/**
	 * APP 逆向物流签收更新运单状态
	 * 
	 * @param singOrder
	 * @return
	 */
	int appUpdateReverseOrderState(
			@Param("shiping_order_id") String shiping_order_id);

	/**
	 * APP 获取省市县
	 * 
	 * @param citye_parent_id
	 * @return
	 */
	List<City_info> appGetCityInfo(
			@Param("citye_parent_id") String citye_parent_id);

	/**
	 * APP 正向物流查询订单信息
	 * 
	 * @param orderId
	 * @return
	 */
	List<OrderHistory> appGetShipperOrderHistory(
			@Param("orderId") String orderId);

	/**
	 * APP 逆向物流查询订单信息
	 * 
	 * @param orderId
	 * @return
	 */
	List<OrderHistory> appGetReverseOrderHistory(
			@Param("orderId") String orderId);

	/**
	 * APP 正向物流 订单签收图片信息
	 * 
	 * @param orderId
	 * @return
	 */
	List<ShipperOrderImg> appGetShipperOrderImages(
			@Param("orderId") String orderId);

	/**
	 * APP 逆向物流 查询订单签收图片信息
	 * 
	 * @param orderId
	 * @return
	 */
	List<ShipperOrderImg> appGetReverseOrderImages(
			@Param("orderId") String orderId);

	/**
	 * APP 逆向物流 查询订单签收图片信息
	 * 
	 * @param orderId
	 * @return
	 */
	List<ShipperOrderImg> appGetAbnormalImages(
			@Param("abnormalt_id") String abnormalt_id);

	/**
	 * APP 添加供应商信息
	 * 
	 * @param suppliers
	 * @return
	 */
	int appSaveSuppliers(JySuppliers suppliers);

	/**
	 * APP 司机管理司机信息添加
	 * 
	 * @param d
	 * @return
	 */
	int appSaveDriver(Driver d);

	/**
	 * APP 添加供应商信息添加添加 省市县
	 * 
	 * @param jyslist
	 * @return
	 */
	int appSaveSupplersCityInfo(List<JySuppliers> jyslist);

	/**
	 * APP 保存司机信息省市县
	 * 
	 * @param drivers
	 * @return
	 */
	int appSaveDriverCityInfo(List<Driver> drivers);

	/**
	 * APP 司机管理修改司机信息
	 * 
	 * @param d
	 * @return
	 */
	int appUpdateDriver(Driver d);

	/**
	 * APP 更新供应商信息
	 * 
	 * @param jyuppliers
	 * @return
	 */
	int appUpdateSuppliers(JySuppliers jyuppliers);

	/**
	 * APP 逆向物流获取项目信息
	 * 
	 * @param user
	 * @param customersName
	 * @return
	 */
	public List<Customer> appReverseGetCustomersInfo(
			@Param("customersName") String customersName);

	/**
	 * APP查询用户所有信息
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
	 * 获取正逆向物流权限信息
	 * @param father
	 * @return
	 */
	List<String> appGetMenuAndRole(@Param("userId") String userId,@Param("fatherId") String father);

	/**
	 * APP 获取消息
	 * 
	 * @param id
	 * @return
	 */
	Map<String, Integer> getOrderMsgCount(@Param("id") String id);

	/**
	 * 管理员查询数量信息
	 * 
	 * @return
	 */
	List<Map<String, Integer>> getNumberMessage(@Param("userId") String userId);

	/**
	 * 司机查询数量信息
	 * 
	 * @param userId
	 * @param driverId
	 * @return
	 */
	List<Map<String, Integer>> driverGetNumberMessage(
			@Param("userId") String userId, @Param("driverId") String driverId);

	/**
	 * 供应商查询数量
	 * 
	 * @param userId
	 * @param driverId
	 * @param suppliers
	 * @return
	 */
	List<Map<String, Integer>> supplierGetNumberMessage(
			@Param("userId") String userId, @Param("driverId") String driverId,
			@Param("suppliers") String suppliers,
			@Param("list") List<String> address);

	/**
	 * 项目查询数据
	 * 
	 * @param userId
	 * @return
	 */
	List<Map<String, Integer>> otherGetNumberMessage(
			@Param("userId") String userId,
			@Param("customerId") String customerId);

	/**
	 * APP项目更新手机号
	 * 
	 * @param customerId
	 * @param customerTel
	 * @return
	 */
	int appUpdateCustomerTel(@Param("customerId") String customerId,
			@Param("customerTel") String customerTel);

	/**
	 * APP供应商更新手机号
	 * 
	 * @param supplerId
	 * @param supplerPhone
	 * @return
	 */
	int appUpdateSupplersTel(@Param("supplerId") String supplerId,
			@Param("supplerPhone") String supplerPhone);

	/**
	 * APP司机更新手机号
	 * 
	 * @param driverId
	 * @param driverPhone
	 * @return
	 */
	int appUpdateDriverTel(@Param("driverId") String driverId,
			@Param("driverPhone") String driverPhone);

	/**
	 * app 发送消息信息
	 * 
	 * @param lModels
	 * @return
	 */
	int appSaveMessageInfo(@Param("list") List<MsgModel> lModels);

	/**
	 * 查询gps 设备表是否存用户姓名
	 * 
	 * @param username
	 * @return
	 */
	int getGpsInfoCount(@Param("username") String username);

	/**
	 * app 获取定位时间
	 * 
	 * @return
	 */
	AppVersion appGetLocationTime();

	/**
	 *  判断用户是否存在GPS
	 * @param driverId
	 * @return
	 */
	int  appDriverISHaveGps(
			@Param("driverId") String driverId);
	
	/**
	 * 根据司机id 获取用户名
	 * @param driverId
	 * @return
	 */
	String driverGetUserName(@Param("driverId")String driverId);

	/**
	 * 管理员获取逆向物流接单信息
	 * @param send_time
	 * @param end_time
	 * @param end_address
	 * @param send_mechanism
	 * @param custom_contact
	 * @param order_type
	 * @return
	 */
	List<ReverserOrderModel> appGetTakingOrderListInfo(@Param("start") Integer start, @Param("length") Integer length,@Param("send_time") String send_time, @Param("end_time") String end_time, @Param("end_address") String end_address, @Param("send_mechanism") String send_mechanism, @Param("custom_contact") String custom_contact, @Param("order_type") String order_type);

	/**
	 * 司机获取逆向物流接单信息
	 *
	 * @param driverId
	 * @param send_time
	 * @param end_time
	 * @param end_address
	 * @param send_mechanism
	 * @param custom_contact
	 * @param order_type
	 * @return
	 */
	List<ReverserOrderModel> appDriverGetTakingOrderListInfo(@Param("start") Integer start, @Param("length") Integer length, @Param("driverId") String driverId, @Param("send_time") String send_time, @Param("end_time") String end_time, @Param("end_address") String end_address, @Param("send_mechanism") String send_mechanism, @Param("custom_contact") String custom_contact, @Param("order_type") String order_type);

	/**
	 * 管理员获取签收列表
	 *
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
	List<ReverserOrderModel> appGetSignReverseOrderInfo(@Param("start") Integer start, @Param("length") Integer length, @Param("send_time") String send_time, @Param("end_time") String end_time, @Param("shiping_order_num") String shiping_order_num, @Param("shipping_order_state") String shipping_order_state, @Param("order_type") String order_type, @Param("send_mechanism") String send_mechanism, @Param("end_address") String end_address, @Param("end_mechanism") String end_mechanism);

	/**
	 * 项目部获取逆向物流签收列表
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
	 * @param customerId
	 * @return
	 */
	List<ReverserOrderModel> customerAppGetSignReverseOrderInfo(@Param("start") Integer start, @Param("length") Integer length, @Param("send_time") String send_time, @Param("end_time") String end_time, @Param("shiping_order_num") String shiping_order_num, @Param("shipping_order_state") String shipping_order_state, @Param("order_type") String order_type, @Param("send_mechanism") String send_mechanism, @Param("end_address") String end_address, @Param("end_mechanism") String end_mechanism,@Param("customerId") String customerId);

	/**
	 * 获取版本更新信息
	 * @return
	 */
	List<String> appGetUpdateMessage();


	/**
	 * 管理员获取逆向物流信息
	 * @param userId
	 * @return
	 */
	List<Map<String, Integer>> getReverseNumberMessage(@Param("userId") String userId);

	/**
	 * 司机获取逆向物流信息
	 * @param userId
	 * @param driverId
	 * @return
	 */
	List<Map<String, Integer>> driverGetReverseNumberMessage(
			@Param("userId") String userId, @Param("driverId") String driverId);

	/**
	 * 供应商获取逆向物流信息
	 * @param userId
	 * @param driverId
	 * @param suppliers
	 * @param address
	 * @return
	 */
	List<Map<String, Integer>> supplierGetReverseNumberMessage(
			@Param("userId") String userId, @Param("driverId") String driverId,
			@Param("suppliers") String suppliers,
			@Param("list") List<String> address);

	/**
	 * 项目部获取逆向物流信息
	 * @param userId
	 * @param customerId
	 * @return
	 */
	List<Map<String, Integer>> otherGetReverseNumberMessage(
			@Param("userId") String userId,
			@Param("customerId") String customerId);



	/**
	 * 管理员获取逆向物流信息
	 * @param userId
	 * @return
	 */
	List<Map<String, Integer>> getMessageCount(@Param("userId") String userId);

	/**
	 * 项目部
	 * @param userId
	 * @return
	 */
	List<Map<String, Integer>> customerGetMessageCount(@Param("userId") String userId,@Param("customerId") String customerId);


	List<Map<String, Integer>> supplierGetMessageCount(
			@Param("userId") String userId, @Param("driverId") String driverId,
			@Param("suppliers") String suppliers,
			@Param("list") List<String> address);

	List<Map<String, Integer>> driverGetMessageCount(
			@Param("userId") String userId, @Param("driverId") String driverId);

	/**
	 * 获取是否异常上报
	 * @param orderId
	 * @return
	 */
	int getIsAbnormalReportCount(@Param("orderId") String orderId);

}


