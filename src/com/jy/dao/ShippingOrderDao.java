package com.jy.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jy.model.Customer;
import com.jy.model.Driver;
import com.jy.model.MsgModel;
import com.jy.model.OrderHistory;
import com.jy.model.ShipperOrderImg;
import com.jy.model.ShippingOrder;
import com.jy.model.Sign_order;

public interface ShippingOrderDao {

	// 制作协议订单的查询
	List<ShippingOrder> getShipOrderCAM(int rows, int page,
			@Param("array") String[] pid);

	List<ShippingOrder> getCustomerReport(String name, String phone_number,
			String type, String start_date, String end_date, String pid,
			String phone);

	ShippingOrder getCustomerReportPage(String name, String phone_number,
			String type, String start_date, String end_date, String pid,
			String phone);

	// 取消到达
	int isNotArrive(@Param("array") String[] del);

	// 获取数据下拉表的查询
	List<ShippingOrder> getPlateNumberLength(String number);

	// 重复订单号查询
	int getAorder(String number);

	// 导出
	List<ShippingOrder> getShipOrderAll(String name, String phone_number,
			String type, String start_date, String end_date);

	// 协议制作订单查询
	int getShipOrderCA(String id, String startDate, String end_date,
			String ddId, String order_state, String pay_type, String perpole,
			String end_address, String send_address, String phone_number);

	// 协议制作订单查询
	List<ShippingOrder> getShipOrderInfoCA(int rows, int page, String id,
			String startDate, String end_date, String ddId, String order_state,
			String pay_type, String perpole, String end_address,
			String send_address, String phone_number);

	// 删除有需求关联的订单信息
	int deleteShipToDemand(@Param("array") String[] del);

	// 删除订单修改信息
	int deleteOrderEH(@Param("array") String[] del);

	List<ShippingOrder> getSignShipOrder1(int rows, int page, String name,
			String phone_number, String type, String start_date,
			String end_date, String did, String shipping_order_state);

	int getSignShipOrdercount1(String name, String phone_number, String type,
			String start_date, String end_date, String did,
			String shipping_order_state);

	/**
	 * 签收更新表中状态
	 * 
	 * @param shiping_order_id
	 * @return
	 */
	int updatestate(String shiping_order_id);

	/**
	 * 查询pc签收时，是否已经签收
	 * 
	 * @param shipingordernum
	 * @return
	 */
	int getSignShipOrderNum(String shipingordernum);

	int updateSignShipOrderNum(Sign_order sign_order);

	int updateSignShipOrder(Sign_order sign_order);

	/**
	 * 批量导入
	 * 
	 * @param tlist
	 * @return
	 */
	int saveShipOrders(List<ShippingOrder> tlist);

	/**
	 * 图片路径
	 * 
	 * @param sign_id
	 * @param paths
	 * @param paths2
	 * @return
	 */
	int saveSignImages(String uuid, String order_id, String paths);

	/**
	 * 获取司机的运单
	 * 
	 * @param car_id
	 * @return
	 */
	List<ShippingOrder> getCarShipOrder(String car_id);

	// 输出一维码
	List<ShippingOrder> getUpdateShipOrderArray(String[] checkarray);

	/**
	 * 获取扫描信息
	 * 
	 * @param string
	 * @return
	 */
	ShippingOrder getAppShipOrder(String string);

	/**
	 * zzp添加发送短信模板
	 * 
	 * @param msgTitle
	 * @param msgContent
	 * @return
	 */
	int sendMsgModelInfo(MsgModel mm);

	/**
	 * zzp添加select查询短息模板
	 * 
	 * @return
	 */
	List<MsgModel> getMsgModel();

	String sendMsgContent(String msgid);

	// 运单签收 查看运签收历史记录
	List<OrderHistory> getShowOrder(String pid);

	// 运单签收 修改运单状态
	int appupdatestate(String shiping_order_id);

	/**
	 * 订单历史修改记录
	 * 
	 * @param d
	 * @return
	 */
	int saveOrderEHistory(ShippingOrder d);

	// 查询分配总数
	int getShipOrderOnePage(String send_time, String shiping_order_num, String send_mechanism,
			String end_address, String custom_name, String receipt_name, String
			receipt_tel, String driver_id, String shiping_order_goid,String type,String end_mechanism,String end_time);

	// 查询司机分配订单
	List<ShippingOrder> getShipOrderInfoOnePage(int rows, int page,
			String name, String phone_number, String send_mechanism, String start_date,
			String end_date, String pid2, String phone, String pid,
			String shiping_order_goid,String type,String end_mechanism,String endTime);

	// 供应商--司机管理分配订单
	List<ShippingOrder> getShipOrderInfoFenpei(int i, Integer rows1,
			String send_time, String shiping_order_num, String send_mechanism,
			String end_address, String custom_name, String receipt_name,
			String receipt_tel, String shipperorder_id,String end_mechanism,
			@Param("list") List<String> address,
			@Param("supplersId") String userId);

	// 供应商--司机管理分配订单总数
	int getShipOrderFenpei(String send_time, String shiping_order_num,
			String send_mechanism, String end_address, String custom_name,
			String receipt_name, String receipt_tel, String shipperorder_id,String end_mechanism,
			@Param("list") List<String> end_addres,
			@Param("suppliers") String userId);

	// 录单员--司机管理分配订单
	List<ShippingOrder> otherShipOrderInfoFenpei(int i, Integer rows1,
			String send_time, String shiping_order_num, String send_mechanism,
			String end_address, String custom_name, String receipt_name,
			String receipt_tel, String shipperorder_id,String end_mechanism , String end_addres,
			@Param("userId") String userId);

	// 录单员--司机管理分配订单总条数
	int otherShipOrderFenpei(String send_time, String shiping_order_num,
			String send_mechanism, String end_address, String custom_name,
			String receipt_name, String receipt_tel, String shipperorder_id,String end_mechanism,
			String end_addres, @Param("userId") String userId);

	// 分配运单删除分配信息
	int deleteShipOrderPage(String pid, @Param("array") String[] del);

	// 管理员 --客户结算页面以客户为主查询运单
	List<ShippingOrder> getShipOrderBySendMechanism(int rows, int page,
			String start_time, String end_time, String custom_name);

	// 录单员--客户结算 其他录单员查询客户结算
	List<ShippingOrder> otherGetShipOrderBySendMechanism(int i, Integer rows1,
			String start_time, String end_time, String custom_name,
			@Param("user_id") String user_id);

	// 管理员 --客户结算页面以客户为主查询运单总条数
	int getShipOrderBySendMechanismTotal(String start_time, String end_time,
			String custom_name);

	// 录单员--客户结算查询总条数
	int otherGetShipOrderBySendMechanismTotal(String start_time,
			String end_time, String custom_name,
			@Param("user_id") String user_id);

	// 客户结算 详细信息页面数据
	List<ShippingOrder> getDtailCustomerbilling(int i, Integer rows1,
			String send_time, String shiping_order_num, String send_mechanism,
			String end_address, String custom_name, String receipt_name,
			String receipt_tel, String startTime, String endTime,
			String customCode, String shipperorder_id,
			String customer_settlement_state);

	// 查询客户结算是否全部以结算 没有用
	int selectCustomerSettlementState(String start_time, String end_time,
			String custom_name);

	// 客户结算页面总数量 没有用
	int getDtailCustomerbillingTotal(String send_time,
			String shiping_order_num, String send_mechanism,
			String end_address, String custom_name, String receipt_name,
			String receipt_tel, String startTime, String endTime,
			String customCode, String shipperorder_id,
			String customer_settlement_state);

	// 获取所有客户的用户名
	List<String> getAllCustom();

	// 导出获取所有客户信息
	List<ShippingOrder> getAllMessage(String custom_name, String minTime,
			String maxTime);

	// 客户结算导出合计行
	ShippingOrder getSumMessage(String custom_name, String minTime,
			String maxTime);

	// 客户结算 更新结算状态
	int custom_pay(String customCode, String minTime, String maxTime);

	// 客户结算返回结算的运单号
	List<ShippingOrder> selectShippinOrderId(String customCode, String minTime,
			String maxTime);

	// 运单签收 保存图片
	int saveFilename(@Param("list") List<ShipperOrderImg> shiOrdImg);

	// 是否有签收信息
	public Sign_order isnotSignOrder(String order_id);

	// 管理员--司机管理分配订单
	public List<ShippingOrder> getAllShipOrderInfoFenPei(int i, Integer rows1,
			String send_time, String shiping_order_num, String send_mechanism,
			String end_address, String custom_name, String receipt_name,
			String receipt_tel, String shipperorder_id,String end_mechanism);

	// 管理员--司机管理分配订单总条数
	public int getAllShipOrderInfoFenPeiCount(String send_time,
			String shiping_order_num, String send_mechanism,
			String end_address, String custom_name, String receipt_name,
			String receipt_tel, String shipperorder_id,String end_mechanism);

	// APP 查询带分配运单
	List<ShippingOrder> suppliersGetShipperOrderInfoapp(String rownum,
			String row, String supplierid, String shippingOrderId,
			String shiping_order_goid, String custom_name, String receipt_name,@Param("list") List<String> userAddress);

	// app 配送订单 司机登陆
	List<ShippingOrder> psDriverPageInfoShipperOrder(String rownum, String row,
			String driver_id, String shipperOrderId, String startTime,
			String endTime, String state, String shiping_order_goid,
			String custom_name, String receipt_name);

	// app 配送订单 供应商登陆
	List<ShippingOrder> psSuppPageInfoShipperOrder(String rownum, String row,
			String supplielisId, String shipperOrderId, String startTime,
			String endTime, String state, String shiping_order_goid,
			String custom_name, String receipt_name);

	// 删除已分配的订单更新状态
	int updateShipperOrderDelete(@Param("array") String[] orderid);

	// 系统删除已分配的订单
	int deleteShipOrders(String orderid);

	// 运单录入 删除所有订单信息
	int deleteAllMessage(String send_time, String shiping_order_num,
			String send_mechanism, String end_address, String custom_name,
			String receipt_name, String receipt_tel, String topordernumber,
			String downordernumber, String end_time);

	// 查询订单签收图片
	List<ShipperOrderImg> getSingShiperOrderImg(String orderId);

	// 运单签收信息导出
	List<ShippingOrder> outSignShipOrderFile(String userId, String name,
			String start_date, String end_date, String shipping_order_state,
			String custom_name, String receipt, String address,
			String shipperorder_id, @Param("array") String[] dataIds);

	// 运单签收信息导出存储过程--没有用
	String callSignShipperOrderProcedure(String userId);

	// 快速查询
	List<ShippingOrder> getQuickSearchShiping(String shiping_order_goid,
			String shiping_order_num, String userId);

	// 管理员--运单录入运单信息查询
	List<ShippingOrder> getShipOrderInfo(int rows, Integer page,
			String send_time, String shiping_order_num, String send_mechanism,
			String end_address, String custom_name, String receipt_name,
			String receipt_tel, String topordernumber, String downordernumber,
			String shipperorder_id, String end_time);

	// 录单员--运单录入运单信息查询
	List<ShippingOrder> otherGetShipOrderInfo(int i, Integer rows1,
			String send_time, String shiping_order_num, String send_mechanism,
			String end_address, String custom_name, String receipt_name,
			String receipt_tel, String topordernumber, String downordernumber,
			String shipperorder_id, String end_time,
			@Param("userId") String userId);

	// 供应商--运单录入运单信息查询
	List<ShippingOrder> suppliersGetShipperOrderInfo(int i, Integer rows1,
			String send_time, String shiping_order_num, String send_mechanism,
			String end_address, String custom_name, String receipt_name,
			String receipt_tel, String topordernumber, String downordernumber,
			String shipperorder_id, String end_time,
			@Param("list") List<String> userAddress,
			@Param("suppliersId") String suppliersId);

	// 司机--运单录入运单信息查询
	List<ShippingOrder> driverGetShipperOrderInfo(int i, Integer rows1,
			String send_time, String shiping_order_num, String send_mechanism,
			String end_address, String custom_name, String receipt_name,
			String receipt_tel, String topordernumber, String downordernumber,
			String shipperorder_id, String end_time,
			@Param("driverId") String driverId);

	// 管理员--运单录入运单信息查询总条数
	int getShipOrder(String send_time, String shiping_order_num,
			String send_mechanism, String end_address, String custom_name,
			String receipt_name, String receipt_tel, String topordernumber,
			String downordernumber, String shipperorder_id, String end_time);

	// 录单员--运单录入运单信息查询总条数
	int otherGetShipOrder(String send_time, String shiping_order_num,
			String send_mechanism, String end_address, String custom_name,
			String receipt_name, String receipt_tel, String topordernumber,
			String downordernumber, String shipperorder_id, String end_time,
			@Param("user_id") String user_id);

	// 供应商--运单录入运单信息查询总条数
	int suppliersGetShipOrder(String send_time, String shiping_order_num,
			String send_mechanism, String end_address, String custom_name,
			String receipt_name, String receipt_tel, String topordernumber,
			String downordernumber, String shipperorder_id, String end_time,
			@Param("suppliersId") String suppliersId,
			@Param("list") List<String> userAddress);

	// 司机--运单录入运单信息查询总条数
	int driverGetShipOrder(String send_time, String shiping_order_num,
			String send_mechanism, String end_address, String custom_name,
			String receipt_name, String receipt_tel, String topordernumber,
			String downordernumber, String shipperorder_id, String end_time,
			@Param("driverId") String driverId);

	// 获取父节点部门id
	String getDepartmentFatherId(String userId);

	// 获取发货客户名称
	List<Customer> getCustomName(String number);

	// 到达订单历史表存储
	int saveOrderHistory(List<OrderHistory> order);

	// 确定到达时候修改客户车到时间
	int isArriveCoustom(String custom_id);

	// 取消到达删除数据
	int deleteOrderHistory(@Param("array") String[] order);

	// 运单录入批量删除订单信息
	int deleteShipOrder(@Param("array") String[] del);

	// 删除司机关联订单
	int deleteDriverToOrder(String[] del);

	// 运单录入修改运单信息
	int updateShipOrder(ShippingOrder d);

	// 运单录入修改运单信息，多修改了个订单状态
	int updateShipOrders(ShippingOrder d);

	// 运单查看获取运单信息
	ShippingOrder getUpdateShipOrder(String id);

	// 运单录入订单信息勾选导出
	List<ShippingOrder> someMessageOutPut(String[] dataIds);

	// 运单录入保存运单信息
	int saveShipOrder(ShippingOrder d);

	// 运单录入 到达
	int isArrive(@Param("array") String[] del);

	// 重复订单号查询
	ShippingOrder getNumber(String number, String shipordernum);

	// 管理员--运单签收信息查询
	List<ShippingOrder> getSignShipOrder(int rows, int page, String name,
			String start_date, String end_date, String shipping_order_state,
			String custom_name, String receipt, String address,
			String shipperorder_id,String startSignTime,String endSignTime);

	// 管理员--新*******************
	List<ShippingOrder> getShipOrderOld(int rows, int page, String name,
			String start_date, String end_date, String shipping_order_state,
			String custom_name, String receipt, String address,
			String shipperorder_id);

	// 管理员--运单签收信息查询总条数
	int getSignShipOrdercount(String name, String start_date, String end_date,
			String shipping_order_state, String custom_name, String receipt,
			String address, String shipperorder_id,String startSignTime,String endSignTime);

	// 管理员--新***********************
	int getShipOrderOldcount(String name, String start_date, String end_date,
			String shipping_order_state, String custom_name, String receipt,
			String address, String shipperorder_id);

	// 录单员--运单信息签收查询
	List<ShippingOrder> otherSignShipOrder(int rows, int page, String name,
			String start_date, String end_date, String shipping_order_state,
			String custom_name, String receipt, String address,
			String shipperorder_id, String startSignTime,String endSignTime,@Param("userId") String userId);

	// 录单员--新***************
	List<ShippingOrder> otherShipOrderOld(int rows, int page, String name,
			String start_date, String end_date, String shipping_order_state,
			String custom_name, String receipt, String address,
			String shipperorder_id, @Param("userId") String userId);

	// 供应商--运单信息签收查询
	List<ShippingOrder> suppliersSignShipOrder(int rows, int page, String name,
			String start_date, String end_date, String shipping_order_state,
			String custom_name, String receipt, String address,
			String shipperorder_id, String startSignTime,String endSignTime,@Param("suppliersId") String suppliersId,
			@Param("list") List<String> userAddress);

	// 供应商--新********************************
	List<ShippingOrder> suppliersShipOrderOld(int rows, int page, String name,
			String start_date, String end_date, String shipping_order_state,
			String custom_name, String receipt, String address,
			String shipperorder_id, @Param("suppliersId") String suppliersId,
			@Param("list") List<String> userAddress);

	// 司机--运单签收信息查询
	List<ShippingOrder> driverSignShipOrder(int rows, int page, String name,
			String start_date, String end_date, String shipping_order_state,
			String custom_name, String receipt, String address,
			String shipperorder_id,String startSignTime,String endSignTime, @Param("driverId") String driverId);

	// 司机--新*******************************
	List<ShippingOrder> driverShipOrderOld(int rows, int page, String name,
			String start_date, String end_date, String shipping_order_state,
			String custom_name, String receipt, String address,
			String shipperorder_id, @Param("driverId") String driverId);

	// 信息员--运单签收信息查询总条数
	int otherSignShipOrderCount(String name, String start_date,
			String end_date, String shipping_order_state, String custom_name,
			String receipt, String address, String shipperorder_id,String startSignTime,String endSignTime,
			@Param("userId") String userId);

	// 信息员--新***************************************
	int otherShipOrderOldCount(String name, String start_date, String end_date,
			String shipping_order_state, String custom_name, String receipt,
			String address, String shipperorder_id, String userId);

	// 供应商--运单签收信息查询总条数
	int suppliersGetShipOrderCount(String name, String start_date,
			String end_date, String shipping_order_state, String custom_name,
			String receipt, String address, String shipperorder_id,String startSignTime,String endSignTime,
			@Param("suppliersId") String suppliersId,
			@Param("list") List<String> userAddress);

	// 供应商--新***********************************************
	int suppliersShipOrderOldCount(String name, String start_date,
			String end_date, String shipping_order_state, String custom_name,
			String receipt, String address, String shipperorder_id,
			@Param("suppliersId") String suppliersId,
			@Param("list") List<String> userAddress);

	// 司机--运单签收信息查询总条数
	int driverSignShipOrderCount(String name, String start_date,
			String end_date, String shipping_order_state, String custom_name,
			String receipt, String address, String shipperorder_id,String startSignTime,String endSignTime,
			@Param("driverId") String driverId);

	// 司机--新**************************************
	int driverShipOrderOldCount(String name, String start_date,
			String end_date, String shipping_order_state, String custom_name,
			String receipt, String address, String shipperorder_id,
			@Param("driverId") String driverId);

	// 运单签收获取签收信息
	ShippingOrder getUpdateSignShipOrder(String id);

	// 运单签收保存签收信息
	int saveSignShipOrder(Sign_order sign_order);

	// 管理员--运单信息签收导出
	List<ShippingOrder> outNewSignShipOrderFile(String name, String start_date,
			String end_date, String shipping_order_state, String custom_name,
			String receipt, String address, String shipperorder_id,
			@Param("array") String[] dataIds);

	// 供应商--运单信息签收导出
	List<ShippingOrder> supperOutSignShipOrderFile(String name,
			String start_date, String end_date, String shipping_order_state,
			String custom_name, String receipt, String address,
			String shipperorder_id, @Param("array") String[] dataIds,
			@Param("suppliersId") String suppliersId,
			@Param("list") List<String> userAddress);

	// 司机--运单信息签收导出
	List<ShippingOrder> driverOutSignShipOrderFile(String name,
			String start_date, String end_date, String shipping_order_state,
			String custom_name, String receipt, String address,
			String shipperorder_id, @Param("array") String[] dataIds,
			@Param("driverId") String driverId);

	// 项目部--运单信息签收导出
	List<ShippingOrder> otherOutSignShipOrderFile(String name,
			String start_date, String end_date, String shipping_order_state,
			String custom_name, String receipt, String address,
			String shipperorder_id, @Param("array") String[] dataIds,
			@Param("customerId") String customerId);

	// 已评论
	int updateComment_state(String shiping_order_id);

	// APP 运单签收 保存运单时效信息
	public int insertShippingTime(
			@Param("shiping_order_id") String shiping_order_id);

	// 56 link项目对接
	int get56LinkProject(@Param("goid") String goid,
			@Param("mechanism") String mechanism);

	// 根据运单id查询司机信息
	Driver getDriverInfo(@Param("orderId") String orderId);
	
	// 用户导入查询发货客户信息
	Customer userGetCusetomerInfo(@Param("customerId")String customerId);

	/**
	 * 运单签收 补充签收备注
	 * @param orderId
	 * @param remarks
	 * @return
	 */
	int updateOrderSignRemarks(@Param("orderId") String orderId,@Param("remarks") String remarks);

	/**
	 * 运单签收 修改签收时效的备注
	 * @param orderId
	 * @param remarks
	 * @return
	 */
	int updateOrderShipperTimeRemarks(@Param("orderId")String orderId,@Param("remarks")String remarks);

	// 获取发货客户名称
	List<Customer> customerGetCustomName(@Param("number") String number, @Param("customerId") String customerId);


	List<ShippingOrder> getShippingOrderId(@Param("shiping_order_num") String shiping_order_num,@Param("shiping_order_goid") String shiping_order_goid);

	/**
	 * 获取业务id
	 * @param shiping_order_num
	 * @param shiping_order_goid
	 * @return
	 */
	String getShippingOrderBusinessType(@Param("shiping_order_num") String shiping_order_num,@Param("shiping_order_goid") String shiping_order_goid,@Param("businessType") int businessType);

	/**
	 * 更新业务状态状态
	 * @return
	 */
	int updateShippingOrderBusinessType(@Param("list") List<String> orderId, @Param("businessType") int businessType);
}
