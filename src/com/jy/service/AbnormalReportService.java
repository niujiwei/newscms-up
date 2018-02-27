package com.jy.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jy.model.AbnormalImages;
import com.jy.model.AbnormalReport;
import com.jy.model.AbnormalReportFile;
import com.jy.model.OrderHistory;
import com.jy.model.ShippingOrder;
import com.jy.model.User;

public interface AbnormalReportService {
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
	 * 异常反馈 获取运单信息
	 * 
	 * @param shippingorderId
	 * @return
	 */
	public ShippingOrder getShipOrderMsg(
			@Param("shiping_order_id") String shippingorderId);

	/**
	 * 异常反馈 添加信息
	 * 
	 * @param abnormalReport
	 * @return
	 */
	public boolean saveAbnormalReport(AbnormalReport abnormalReport);

	/**
	 * 司机获取供应商id
	 * 
	 * @param driverId
	 * @return
	 */
	String getSupplersId(@Param("drverId") String driverId);

	/**
	 * 异常反馈 保存图片信息
	 * 
	 * @param abnormalImages
	 * @return
	 */
	public boolean saveAbnormalImage(
			@Param("list") List<AbnormalImages> abnormalImages);

	/**
	 * 异常反馈更新异常状态
	 * 
	 * @param shippingOrderId
	 * @return
	 */
	public boolean updateIsAbnormal(String shippingOrderId, String flag);

	/**
	 * 到达订单历史表存储
	 * 
	 * @param order
	 * @return
	 */
	public int saveOrderHistory(List<OrderHistory> order);

	/**
	 * 异常反馈 管理员查询信息
	 * 
	 * @param rows
	 * @param page
	 * @param send_time
	 * @param end_time
	 * @param shiping_order_num
	 * @param shiping_order_goid
	 * @param abnormal_result
	 * @param abnormal_type
	 * @return
	 */
	List<AbnormalReport> getAbnormalReport(int rows, Integer page,
			String send_time, String end_time, String shiping_order_num,
			String shiping_order_goid, String abnormal_result,
			String abnormal_type, String end_address, String receipt_name,
			String send_mechanism, User user);

	/**
	 * 异常反馈 管理员查询总条数
	 * 
	 * @param send_time
	 * @param end_time
	 * @param shiping_order_num
	 * @param shiping_order_goid
	 * @param abnormal_result
	 * @param abnormal_type
	 * @return
	 */
	int getAbnormalReportCount(String send_time, String end_time,
			String shiping_order_num, String shiping_order_goid,
			String abnormal_result, String abnormal_type, String end_address,
			String receipt_name, String send_mechanism, User user);

	/**
	 * 异常反馈查询异常图片信息
	 * 
	 * @param id
	 * @return
	 */
	List<AbnormalImages> showAbnormalImg(String id);

	/**
	 * 异常处理信息
	 * 
	 * @param id
	 * @return
	 */
	AbnormalReport exceptionHandling(String id);

	/**
	 * 异常处理更新异常表中信息
	 * 
	 * @param id
	 * @param abnormal_resultremark
	 * @param userName
	 * @return
	 */
	boolean updateAbnormalState(String id, String abnormal_resultremark,
			String userName);

	/**
	 * 异常处理查询运单是否有没有处理的
	 * 
	 * @param orderId
	 * @return
	 */
	boolean getAbnormalOrderNum(String orderId);

	/**
	 * 更新运单是否异常上报
	 * 
	 * @param orderId
	 * @return
	 */
	boolean updateOrederAbnormal(@Param("orderId") String orderId);

	/**
	 * 获取要推送用户的设备号
	 * 
	 * @param abnormal_supperid
	 * @param abnormal_driverid
	 * @return
	 */
	List<String> getChannelIds(String abnormal_supperid,
			String abnormal_driverid,String abnormalreportId);

	/**
	 * 异常反馈 信息导出
	 * 
	 * @param send_time
	 * @param end_time
	 * @param shiping_order_num
	 * @param shiping_order_goid
	 * @param abnormal_result
	 * @param abnormal_type
	 * @param end_address
	 * @param receipt_name
	 * @param send_mechanism
	 * @param abnormal_ids
	 * @return
	 */
	List<AbnormalReportFile> outputAbnormalReportFile (String send_time,
			String end_time, String shiping_order_num,
			String shiping_order_goid, String abnormal_result,
			String abnormal_type, String end_address, String receipt_name,
			String send_mechanism, @Param("array") String[] abnormal_ids,User user);
}
