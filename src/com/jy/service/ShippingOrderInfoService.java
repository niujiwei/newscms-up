package com.jy.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jy.model.Customer;
import com.jy.model.Driver;
import com.jy.model.MsgModel;
import com.jy.model.OrderHistory;
import com.jy.model.ShipperOrderImg;
import com.jy.model.ShippingOrder;
import com.jy.model.Sign_order;
import com.jy.model.User;

public interface ShippingOrderInfoService {
	public List<ShippingOrder> getCustomerReport(String name,
			String phone_number, String type, String start_date,
			String end_date, String pid, String phone);

	public ShippingOrder getCustomerReportPage(String name,
			String phone_number, String type, String start_date,
			String end_date, String pid, String phone);

	/**
	 * 到达
	 * 
	 * @param del
	 * @return
	 */
	public int isArrive(@Param("array") String[] del);

	/**
	 * 取消到达
	 * 
	 * @param del
	 * @return
	 */
	public int isNotArrive(@Param("array") String[] del);

	/*
	 * select 查询
	 */
	List<ShippingOrder> getPlateNumberLength(String number);

	int getAorder(String number);// 重复订单号查询

	List<ShippingOrder> getShipOrderAll(String name, String phone_number,
			String type, String start_date, String end_date);// 导出查询

	/**
	 * 删除与需求相关的当单信息
	 * 
	 * @param del
	 * @return
	 */
	int deleteShipToDemand(String[] del);

	public List<ShippingOrder> getSignShipOrder1(int rows, int page,
			String name, String phone_number, String type, String start_date,
			String end_date, String did, String shipping_order_state);

	// public List<Driver> getDriver(String driver_name);
	public int getSignShipOrdercount1(String name, String phone_number,
			String type, String start_date, String end_date, String did,
			String shipping_order_state);

	/**
	 * 运单签收 保存签收信息
	 * 
	 * @param sign_order
	 * @return
	 */
	public int saveSignShipOrder(Sign_order sign_order);

	/**
	 * 运单签收 更新运单状态
	 * 
	 * @param shiping_order_id
	 * @return
	 */
	public int updatestate(String shiping_order_id);

	// 查询签收的订单是否已被签收
	public int getSignShipOrder(String shipingordernum);

	// 签收是订单存在时更新数据
	public int updateSignShipOrderNum(Sign_order sign_order);

	int updateSignShipOrder(Sign_order sign_order);

	/**
	 * 运单签收获取要签收运单信息
	 * 
	 * @param id
	 * @return
	 */
	public ShippingOrder getUpdateSignShipOrder(String id);

	/**
	 * 协议制作订单查询方法---没有用
	 * 
	 * @param id
	 * @param startDate
	 * @param end_date
	 * @param ddId
	 * @param order_state
	 * @param pay_type
	 * @param perpole
	 * @param end_address
	 * @param send_address
	 * @param phone_number
	 * @return
	 */
	int getShipOrderCA(String id, String startDate, String end_date,
			String ddId, String order_state, String pay_type, String perpole,
			String end_address, String send_address, String phone_number);// 查询总数

	/**
	 * 协议制作订单查询方法---没有用
	 * 
	 * @param rows
	 * @param page
	 * @param id
	 * @param startDate
	 * @param end_date
	 * @param ddId
	 * @param order_state
	 * @param pay_type
	 * @param perpole
	 * @param end_address
	 * @param send_address
	 * @param phone_number
	 * @return
	 */
	List<ShippingOrder> getShipOrderInfoCA(int rows, int page, String id,
			String startDate, String end_date, String ddId, String order_state,
			String pay_type, String perpole, String end_address,
			String send_address, String phone_number); // 查询

	/**
	 * 查询确定订单信息--没有用
	 * 
	 * @param rows
	 * @param page
	 * @param pid
	 * @return
	 */
	List<ShippingOrder> getShipOrderCAM(int rows, int page, String[] pid);

	/**
	 * 订单图片路径
	 * 
	 * @param sign_id
	 * @param paths
	 * @param paths2
	 * @return
	 */
	public int saveSignImages(String uuid, String order_id, String paths);

	/**
	 * 获取司机的订单
	 * 
	 * @param string
	 * @return
	 */
	public List<ShippingOrder> getCarShipOrder(String car_id);

	/**
	 * 输出一维码
	 * 
	 * @param checkarray
	 * @return
	 */
	public List<ShippingOrder> getUpdateShipOrderArray(String[] checkarray);

	/**
	 * 查询扫描信息
	 * 
	 * @param string
	 * @return
	 */
	public ShippingOrder getAppShipOrder(String string);

	/**
	 * zzp添加发送短信模板
	 * 
	 * @param msgTitle
	 * @param msgContent
	 * @return
	 */
	public int sendMsgModelInfo(String msgTitle, String msgContent);

	/**
	 * zzp select查询模板
	 * 
	 * @return
	 */
	public List<MsgModel> getMsgModel();

	public String sendMsgContent(String msgid);

	/**
	 * 运单签收 查看运签收历史记录
	 * 
	 * @return
	 */
	public List<OrderHistory> getShowOrder(String pid);

	/**
	 * 订单历史修改记录
	 * 
	 * @param d
	 * @return
	 */
	public int saveOrderEHistory(ShippingOrder d);

	/**
	 * 删除订单修改信息
	 */
	public int deleteOrderEH(String[] del);

	/**
	 * 查询司机分配订单页面
	 * 
	 * @param i
	 * @param rows1
	 * @param name
	 * @param phone_number
	 * @param type
	 * @param start_date
	 * @param end_date
	 * @param pid2
	 * @param phone
	 * @param pid
	 * @param shiping_order_goid
	 * @return
	 */
	public List<ShippingOrder> getShipOrderInfoOnePage(int i, Integer rows1,
			String name, String phone_number, String send_mechanism,
			String start_date, String end_date, String pid2, String phone,
			String pid, String shiping_order_goid, String type,
			String end_mechanism,String endTime);

	/**
	 * 查询分配订单页面总数
	 * 
	 * @param name
	 * @param phone_number
	 * @param type
	 * @param start_date
	 * @param end_date
	 * @param pid2
	 * @param phone
	 * @param pid
	 * @param shiping_order_goid
	 * @return
	 */
	public int getShipOrderOnePage(String send_time, String shiping_order_num,
			String send_mechanism, String end_address, String custom_name,
			String receipt_name, String

			receipt_tel, String driver_id, String shiping_order_goid,
			String type, String end_mechanism,String end_time);

	/**
	 * 分配运单删除分配信息
	 * 
	 * @param pid
	 * @param del
	 * @return
	 */
	public int deleteShipOrderPage(String pid, String[] del);

	/**
	 * 客户结算页面根据客户名称为主查询运单
	 * 
	 * @param i
	 * @param rows1
	 * @param name
	 * @param phone_number
	 * @param type
	 * @param start_date
	 * @param end_date
	 * @param pid
	 * @param phone
	 * @return
	 */
	public List<ShippingOrder> getShipOrderBySendMechanism(int i,
			Integer rows1, String start_time, String end_time,
			String custom_name, User user);

	/**
	 * 客户结算页面根据客户名称为主查询总页数
	 * 
	 * @param start_time
	 * @param end_time
	 * @param custom_name
	 * @return
	 */
	public int getShipOrderBySendMechanismTotal(String start_time,
			String end_time, String custom_name, User user);

	/**
	 * 客户结算总的状态
	 * 
	 * @param start_time
	 * @param end_time
	 * @param custom_name
	 * @return
	 */
	public int selectCustomerSettlementState(String start_time,
			String end_time, String custom_name);

	/**
	 * 客户结算 详细信息页面数据
	 * 
	 * @param i
	 * @param rows1
	 * @param start_time
	 * @param end_time
	 * @param custom_name
	 * @return
	 */
	public List<ShippingOrder> getDtailCustomerbilling(int i, Integer rows1,
			String send_time, String shiping_order_num, String send_mechanism,
			String end_address, String custom_name, String receipt_name,
			String receipt_tel, String startTime, String endTime,
			String customCode, String shipperorder_id,
			String customer_settlement_state);

	/**
	 * 客户结算页面中记录数
	 * 
	 * @param i
	 * @param rows1
	 * @param start_time
	 * @param end_time
	 * @param custom_name
	 * @return
	 */
	public int getDtailCustomerbillingTotal(String send_time,
			String shiping_order_num, String send_mechanism,
			String end_address, String custom_name, String receipt_name,
			String receipt_tel, String startTime, String endTime,
			String customCode, String shipperorder_id,
			String customer_settlement_state);

	/**
	 * 导出获取所有用户
	 * 
	 * @return
	 */
	public List<String> getAllCustom();

	/**
	 * 导出获取所有用户信息
	 * 
	 * @param custom_name
	 * @return
	 */
	public List<ShippingOrder> getAllMessage(String custom_name,
			String minTime, String maxTime);

	/**
	 * 导出获取所有用户的合计
	 * 
	 * @param custom_name
	 * @return
	 */
	public ShippingOrder getSumMessage(String custom_name, String minTime,
			String maxTime);

	/**
	 * 客户结算
	 * 
	 * @param custom_code
	 * @param min_time
	 * @param max_time
	 * @return
	 */
	public boolean custom_pay(String custom_code, String min_time,
			String max_time);

	/**
	 * 客户结算 返回要结算运单信息
	 * 
	 * @param custom_code
	 * @param min_time
	 * @param max_time
	 * @return
	 */
	public List<ShippingOrder> selectShippinOrderId(String custom_code,
			String min_time, String max_time);

	/**
	 * 运单签收 保存图片
	 */
	public boolean saveFilename(List<ShipperOrderImg> shiOrdImg);

	/**
	 * 是否有签收信息
	 */
	public Sign_order isnotSignOrder(String order_id);

	public int getAllShipOrderInfoFenPeiCount(String name, String phone_number,
			String type, String start_date, String end_date, String pid,
			String phone, String shipperorder_id, String end_mechanism,
			User user);

	// app 配送订单 司机登陆
	List<ShippingOrder> psDriverPageInfoShipperOrder(String rownum, String row,
			String driver_id, String shipperOrderId, String startTime,
			String endTime, String state, String shiping_order_goid,
			String custom_name, String receipt_name);

	// app 配送订单 供应商登陆
	List<ShippingOrder> psSuppPageInfoShipperOrder(String rownum, String row,
			String supperlis_id, String shipperOrderId, String startTime,
			String endTime, String state, String shiping_order_goid,
			String custom_name, String receipt_name);

	// 删除已分配的订单更新状态
	int updateShipperOrderDelete(@Param("array") String[] orderid);

	// 系统删除已分配的订单
	int deleteShipOrders(String orderid);

	/**
	 * 运单信息导出
	 * 
	 * @param userId
	 * @param name
	 * @param start_date
	 * @param end_date
	 * @param shipping_order_state
	 * @param custom_name
	 * @param receipt
	 * @param address
	 * @param shipperorder_id
	 * @param dataIds
	 * @return
	 */
	List<ShippingOrder> outSignShipOrderFile(User user, String name,
			String start_date, String end_date, String shipping_order_state,
			String custom_name, String receipt, String address,
			String shipperorder_id, String[] dataIds);

	/**
	 * 运单签收信息导出存储过程--没有用
	 * 
	 * @param userId
	 */
	void callSignShipperOrderProcedure(String userId);

	/**
	 * 快速查询
	 * 
	 * @param shiping_order_goid
	 * @param shiping_order_num
	 * @param userId
	 * @return
	 */
	List<ShippingOrder> getQuickSearchShiping(String shiping_order_goid,
			String shiping_order_num, String userId);

	/**
	 * 运单录入 删除所有订单信息
	 * 
	 * @param send_time
	 * @param shiping_order_num
	 * @param send_mechanism
	 * @param end_address
	 * @param custom_name
	 * @param receipt_name
	 * @param receipt_tel
	 * @param topordernumber
	 * @param downordernumber
	 * @param end_time
	 * @return
	 */
	int deleteAllMessage(String send_time, String shiping_order_num,
			String send_mechanism, String end_address, String custom_name,
			String receipt_name, String receipt_tel, String topordernumber,
			String downordernumber, String end_time);

	/**
	 * 司机管理分配运单
	 * 
	 * @param i
	 * @param rows1
	 * @param name
	 * @param phone_number
	 * @param type
	 * @param start_date
	 * @param end_date
	 * @param pid
	 * @param phone
	 * @param shipperorder_id
	 * @param user
	 * @return
	 */
	public List<ShippingOrder> getAllShipOrderInfoFenPei(int i, Integer rows1,
			String name, String phone_number, String type, String start_date,
			String end_date, String pid, String phone, String shipperorder_id,
			String end_mechanism, User user);

	/**
	 * 运单录入运单查询
	 * 
	 * @param rows
	 * @param page
	 * @param send_time
	 * @param shiping_order_num
	 * @param send_mechanism
	 * @param end_address
	 * @param custom_name
	 * @param receipt_name
	 * @param receipt_tel
	 * @param topordernumber
	 * @param downordernumber
	 * @param shipperorder_id
	 * @param end_time
	 * @return
	 */
	public List<ShippingOrder> getShipOrderInfo(int rows, Integer page,
			String send_time, String shiping_order_num, String send_mechanism,
			String end_address, String custom_name, String receipt_name,
			String receipt_tel, String topordernumber, String downordernumber,
			String shipperorder_id, String end_time, User user);

	/**
	 * 运单录入运单信息查询总条数
	 * 
	 * @param send_time
	 * @param shiping_order_num
	 * @param send_mechanism
	 * @param end_address
	 * @param custom_name
	 * @param receipt_name
	 * @param receipt_tel
	 * @param topordernumber
	 * @param downordernumber
	 * @param shipperorder_id
	 * @param end_time
	 * @return
	 */
	public int getShipOrder(String send_time, String shiping_order_num,
			String send_mechanism, String end_address, String custom_name,
			String receipt_name, String receipt_tel, String topordernumber,
			String downordernumber, String shipperorder_id, String end_time,
			User user);

	/**
	 * 获取部门id
	 * 
	 * @param userId
	 * @return
	 */
	public String getDepartmentFatherId(String userId);

	/**
	 * 获取发货客户名称
	 * 
	 * @param number
	 * @return
	 */
	public List<Customer> getCustomName(String number);

	/**
	 * 到达订单历史表存储
	 * 
	 * @param order
	 * @return
	 */
	public int saveOrderHistory(List<OrderHistory> order);

	/**
	 * 确定到达时候修改客户车到时间
	 * 
	 * @param custom_id
	 * @return
	 */
	public int isArriveCoustom(String custom_id);

	/**
	 * 取消到达删除数据
	 * 
	 * @param order
	 * @return
	 */
	public int deleteOrderHistory(String[] order);

	/**
	 * 运单管理删除运单信息
	 * 
	 * @param del
	 * @return
	 */
	public int deleteShipOrder(String[] del);

	/**
	 * 删除关联司机订单
	 * 
	 * @param del
	 * @return
	 */
	public int deleteDriverToOrder(String[] del);

	/**
	 * 运单管理修改运单信息
	 * 
	 * @param d
	 * @return
	 */
	public int updateShipOrder(ShippingOrder d);

	/**
	 * 运单管理修改运单信息，多修改了个订单状态
	 * 
	 * @param d
	 * @return
	 */
	public int updateShipOrders(ShippingOrder d);

	/**
	 * 运单查看获取运单信息
	 * 
	 * @param id
	 * @return
	 */
	public ShippingOrder getUpdateShipOrder(String id);

	/**
	 * 运单录入 部分信息导出
	 * 
	 * @param dataIds
	 * @return
	 */
	public List<ShippingOrder> someMessageOutPut(String[] dataIds);

	/**
	 * 运单录入 检查是否订单号存在
	 * 
	 * @param number
	 * @param shipordernum
	 * @return
	 */
	public ShippingOrder getNumber(String number, String shipordernum);

	/**
	 * 运单录入 保存运单信息
	 * 
	 * @param d
	 * @return
	 */
	public int saveShipOrder(ShippingOrder d);

	/**
	 * APP供应商获取查询待分配运单
	 * 
	 * @param rownum
	 * @param row
	 * @param supplierid
	 * @param shippingOrderId
	 * @param shiping_order_goid
	 * @param custom_name
	 * @param receipt_name
	 * @return
	 */
	public List<ShippingOrder> suppliersGetShipperOrderInfo(String rownum,
			String row, String supplierid, String shippingOrderId,
			String shiping_order_goid, String custom_name, String receipt_name,
			List<String> userAddress);

	/**
	 * 运单签收 查询运单信息
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
	 * @return
	 */
	public List<ShippingOrder> getSignShipOrder(int rows, int page,
			String name, String start_date, String end_date,
			String shipping_order_state, String custom_name, String receipt,
			String address, String shipperorder_id, String startSignTime,
			String endSignTime, User user);

	/**
	 * 新增已签收的信息查询
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
	public List<ShippingOrder> getShipOrderOld(int rows, int page, String name,
			String start_date, String end_date, String shipping_order_state,
			String custom_name, String receipt, String address,
			String shipperorder_id, User user);

	/**
	 * 新增已签收的信息统计
	 * 
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
	public int getShipOrderOldcount(String name, String start_date,
			String end_date, String shipping_order_state, String custom_name,
			String receipt, String address, String shipperorder_id, User user);

	/**
	 * 运单签收 查询运单签收信息总条数
	 * 
	 * @param name
	 * @param start_date
	 * @param end_date
	 * @param shipping_order_state
	 * @param custom_name
	 * @param receipt
	 * @param address
	 * @param shipperorder_id
	 * @return
	 */
	public int getSignShipOrdercount(String shipperorder_num,
			String start_date, String end_date, String shipping_order_state,
			String custom_name, String receipt, String address,
			String shipperorder_id, String startSignTime, String endSignTime,
			User user);

	/**
	 * 查询订单签收图片
	 * 
	 * @param orderId
	 * @return
	 */
	List<ShipperOrderImg> getSingShiperOrderImg(String orderId);

	/**
	 * 司机 已评论
	 * 
	 * @param shiping_order_id
	 * @return
	 */
	int updateComment_state(String shiping_order_id);

	/**
	 * APP 运单签收 保存运单时效信息
	 * 
	 * @param shiping_order_id
	 * @return
	 */
	int insertShippingTime(String shiping_order_id);

	/**
	 * 56 link项目对接
	 * 
	 * @param goid
	 * @param mechanism
	 * @return
	 */
	int get56LinkProject(String goid, String mechanism);

	/**
	 * 根据接单id查询司机信息
	 * 
	 * @param orderId
	 * @return
	 */
	Driver getDriverInfo(String orderId);

	/**
	 * 用户导入查询发货客户信息
	 * 
	 * @param customerId
	 * @return
	 */
	Customer userGetCusetomerInfo(String customerId);

	/**
	 * 运单签收 补充签收备注
	 * @param orderId
	 * @param remarks
	 * @return
	 */
	int updateOrderSignRemarks(String orderId,String remarks);

	/**
	 * 运单签收 修改签收时效的备注
	 * @param orderId
	 * @param remarks
	 * @return
	 */
	int updateOrderShipperTimeRemarks(String orderId,String remarks);



	/**
	 * 获取发货客户名称
	 *
	 * @param number
	 * @return
	 */
	List<Customer> getCustomName(String number,User user);

	/**
	 * 根据运单号查询运单信息
	 *
	 * @param shiping_order_num
	 * @param shiping_order_goid
	 * @return
	 */
	List<ShippingOrder> getShippingOrderId(String shiping_order_num, String shiping_order_goid);

	/**
	 * 获取业务id
	 * @param shiping_order_num
	 * @param shiping_order_goid
	 * @return
	 */
	String getShippingOrderBusinessType(String shiping_order_num,String shiping_order_goid,int businessType);

	/**
	 * 更新业务状态状态
	 * @return
	 */
	int updateShippingOrderBusinessType(List<String> orderId, int businessType);
}
