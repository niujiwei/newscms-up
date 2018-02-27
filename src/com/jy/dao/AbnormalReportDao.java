package com.jy.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jy.model.AbnormalImages;
import com.jy.model.AbnormalReport;
import com.jy.model.AbnormalReportFile;
import com.jy.model.OrderHistory;
import com.jy.model.ShippingOrder;

public interface AbnormalReportDao {

	// 管理员--异常上报运单信息查询
	List<ShippingOrder> getShipOrderInfo(int rows, Integer page,
			String send_time, String shiping_order_num, String send_mechanism,
			String end_address, String custom_name, String receipt_name,
			String receipt_tel, String topordernumber, String downordernumber,
			String shipperorder_id, String end_time);

	// 录单员--异常上报运单信息查询
	List<ShippingOrder> otherGetShipOrderInfo(int i, Integer rows1,
			String send_time, String shiping_order_num, String send_mechanism,
			String end_address, String custom_name, String receipt_name,
			String receipt_tel, String topordernumber, String downordernumber,
			String shipperorder_id, String end_time, String userId);

	// 供应商--异常上报运单信息查询
	List<ShippingOrder> suppliersGetShipperOrderInfo(int i, Integer rows1,
			String send_time, String shiping_order_num, String send_mechanism,
			String end_address, String custom_name, String receipt_name,
			String receipt_tel, String topordernumber, String downordernumber,
			String shipperorder_id, String end_time, String suppliersId,
			@Param("list") List<String> userAddress);

	// 司机--异常上报运单信息查询
	List<ShippingOrder> driverGetShipperOrderInfo(int i, Integer rows1,
			String send_time, String shiping_order_num, String send_mechanism,
			String end_address, String custom_name, String receipt_name,
			String receipt_tel, String topordernumber, String downordernumber,
			String shipperorder_id, String end_time, String driverId);

	// 管理员--异常上报运单信息查询总条数
	int getShipOrder(String send_time, String shiping_order_num,
			String send_mechanism, String end_address, String custom_name,
			String receipt_name, String receipt_tel, String topordernumber,
			String downordernumber, String shipperorder_id, String end_time);

	// 录单员--异常上报运单信息查询总条数
	int otherGetShipOrder(String send_time, String shiping_order_num,
			String send_mechanism, String end_address, String custom_name,
			String receipt_name, String receipt_tel, String topordernumber,
			String downordernumber, String shipperorder_id, String end_time,
			String userId);

	// 供应商--异常上报运单信息查询总条数
	int suppliersGetShipOrder(String send_time, String shiping_order_num,
			String send_mechanism, String end_address, String custom_name,
			String receipt_name, String receipt_tel, String topordernumber,
			String downordernumber, String shipperorder_id, String end_time,
			String suppliersId, @Param("list") List<String> userAddress);

	// 司机--异常上报运单信息查询总条数
	int driverGetShipOrder(String send_time, String shiping_order_num,
			String send_mechanism, String end_address, String custom_name,
			String receipt_name, String receipt_tel, String topordernumber,
			String downordernumber, String shipperorder_id, String end_time,
			String driverId);

	// 获取供应商的终到位置（市+区）
	List<String> getAddressSupperlies(String supplierId);

	// 异常反馈 获取运单信息
	ShippingOrder getShipOrderMsg(
			@Param("shiping_order_id") String shippingorderId);

	// 司机获取供应商id
	String getSupplersId(@Param("drverId") String driverId);

	// 异常反馈 添加信息
	int saveAbnormalReport(AbnormalReport abnormalReport);

	// 异常反馈 保存图片信息
	int saveAbnormalImage(@Param("list") List<AbnormalImages> abnormalImages);

	// 异常反馈更新异常状态
	int updateIsAbnormal(@Param("shippingOrderId") String shippingOrderId,
			@Param("flag") String flag);

	// 保存节点信息
	int saveOrderHistory(List<OrderHistory> order);

	// 异常反馈 管理员查询信息
	List<AbnormalReport> getAbnormalReport(int rows, Integer page,
			String send_time, String end_time, String shiping_order_num,
			String shiping_order_goid, String abnormal_result,
			String abnormal_type, String end_address, String receipt_name,
			String send_mechanism);

	// 异常反馈 管理员查询总条数
	int getAbnormalReportCount(String send_time, String end_time,
			String shiping_order_num, String shiping_order_goid,
			String abnormal_result, String abnormal_type, String end_address,
			String receipt_name, String send_mechanism);

	// 异常反馈 信息员查询信息
	List<AbnormalReport> otherGetAbnormalReport(int rows, Integer page,
			String send_time, String end_time, String shiping_order_num,
			String shiping_order_goid, String abnormal_result,
			String abnormal_type, String end_address, String receipt_name,
			String send_mechanism, String customer_id);

	// 异常反馈 信息员查询总条数
	int otherGetAbnormalReportCount(String send_time, String end_time,
			String shiping_order_num, String shiping_order_goid,
			String abnormal_result, String abnormal_type, String end_address,
			String receipt_name, String send_mechanism, String user_id);

	// 异常反馈 司机查询信息
	List<AbnormalReport> deriverGetAbnormalReport(int rows, Integer page,
			String send_time, String end_time, String shiping_order_num,
			String shiping_order_goid, String abnormal_result,
			String abnormal_type, String end_address, String receipt_name,
			String send_mechanism, String driverId);

	// 异常反馈 司机查询总条数
	int driverGetAbnormalReportCount(String send_time, String end_time,
			String shiping_order_num, String shiping_order_goid,
			String abnormal_result, String abnormal_type, String end_address,
			String receipt_name, String send_mechanism, String driverId);

	// 异常反馈 供应商查询信息
	List<AbnormalReport> suppliersGetAbnormalReport(int rows, Integer page,
			String send_time, String end_time, String shiping_order_num,
			String shiping_order_goid, String abnormal_result,
			String abnormal_type, String end_address, String receipt_name,
			String send_mechanism, String suppliersId);

	// 异常反馈 供应商查询总条数
	int suppliersGetAbnormalReportCount(String send_time, String end_time,
			String shiping_order_num, String shiping_order_goid,
			String abnormal_result, String abnormal_type, String end_address,
			String receipt_name, String send_mechanism, String suppliersId);

	// 异常反馈查询异常图片信息
	List<AbnormalImages> showAbnormalImg(@Param("id") String id);

	// 异常处理信息
	AbnormalReport exceptionHandling(@Param("id") String id);

	// 异常处理更新异常表中信息
	int updateAbnormalState(@Param("id") String id,
			@Param("abnormal_resultremark") String abnormal_resultremark,
			@Param("userName") String userName);

	// 异常处理查询运单是否有没有处理的
	int getAbnormalOrderNum(@Param("orderId") String orderId);

	// 更新运单是否异常上报
	int updateOrederAbnormal(@Param("orderId") String orderId);

	// 获取要推送用户的设备号
	List<String> getChannelIds(
			@Param("abnormal_supperid") String abnormal_supperid,
			@Param("abnormal_driverid") String abnormal_driverid);

	// 获取要推送用户的设备号
	List<String> getChannelIdsTwo(
			@Param("abnormalreportId") String abnormalreportId);

	// 异常反馈 管理员导出信息
	List<AbnormalReportFile> outputAbnormalReport(String send_time,
			String end_time, String shiping_order_num,
			String shiping_order_goid, String abnormal_result,
			String abnormal_type, String end_address, String receipt_name,
			String send_mechanism, @Param("array") String[] abnormal_ids);

	// 异常反馈 司机导出信息
	List<AbnormalReportFile> driverOutputAbnormalReport(String send_time,
			String end_time, String shiping_order_num,
			String shiping_order_goid, String abnormal_result,
			String abnormal_type, String end_address, String receipt_name,
			String send_mechanism, @Param("driverId") String driverId,
			@Param("array") String[] abnormal_ids);

	// 异常反馈 供应商导出信息
	List<AbnormalReportFile> suppliersOutputAbnormalReport(String send_time,
			String end_time, String shiping_order_num,
			String shiping_order_goid, String abnormal_result,
			String abnormal_type, String end_address, String receipt_name,
			String send_mechanism, @Param("suppliersId") String suppliersId,
			@Param("array") String[] abnormal_ids);

	// 异常反馈 项目部导出信息
	List<AbnormalReportFile> otherOutputAbnormalReport(String send_time,
			String end_time, String shiping_order_num,
			String shiping_order_goid, String abnormal_result,
			String abnormal_type, String end_address, String receipt_name,
			String send_mechanism, @Param("user_id") String user_id,
			@Param("array") String[] abnormal_ids);

}
