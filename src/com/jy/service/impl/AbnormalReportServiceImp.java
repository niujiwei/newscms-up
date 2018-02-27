package com.jy.service.impl;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.jy.dao.AbnormalReportDao;
import com.jy.model.AbnormalImages;
import com.jy.model.AbnormalReport;
import com.jy.model.AbnormalReportFile;
import com.jy.model.OrderHistory;
import com.jy.model.ShippingOrder;
import com.jy.model.User;
import com.jy.service.AbnormalReportService;

@Service("AbnormalReportService")
public class AbnormalReportServiceImp implements AbnormalReportService {
	@Resource
	private AbnormalReportDao ar_dao;

	// 运单录入查询运单信息
	public List<ShippingOrder> getShipOrderInfo(int rows, Integer page,
			String send_time, String shiping_order_num, String send_mechanism,
			String end_address, String custom_name, String receipt_name,
			String receipt_tel, String topordernumber, String downordernumber,
			String shipperorder_id, String end_time, User user) {
		List<ShippingOrder> list = null;
		if (user != null) {
			if (user.getFlag().equals("0")) {// 管理员
				list = ar_dao.getShipOrderInfo(rows, page, send_time,
						shiping_order_num, send_mechanism, end_address,
						custom_name, receipt_name, receipt_tel, topordernumber,
						downordernumber, shipperorder_id, end_time);
			} else if (user.getFlag().equals("1")) {// 司机
				list = ar_dao.driverGetShipperOrderInfo(rows, page, send_time,
						shiping_order_num, send_mechanism, end_address,
						custom_name, receipt_name, receipt_tel, topordernumber,
						downordernumber, shipperorder_id, end_time,
						user.getDriver_id());
			} else if (user.getFlag().equals("2")) {// 供应商
				List<String> address = ar_dao.getAddressSupperlies(user
						.getSuppliers_id());
				list = ar_dao.suppliersGetShipperOrderInfo(rows, page,
						send_time, shiping_order_num, send_mechanism,
						end_address, custom_name, receipt_name, receipt_tel,
						topordernumber, downordernumber, shipperorder_id,
						end_time, user.getSuppliers_id(), address);

			} else if (user.getFlag().equals("3") || user.getFlag().equals("4")) {// 信息员
				list = ar_dao.otherGetShipOrderInfo(rows, page, send_time,
						shiping_order_num, send_mechanism, end_address,
						custom_name, receipt_name, receipt_tel, topordernumber,
						downordernumber, shipperorder_id, end_time,
						user.getId());
			}

		}

		return list;
	}

	// 运单录入运单信息总条数查询
	public int getShipOrder(String send_time, String shiping_order_num,
			String send_mechanism, String end_address, String custom_name,
			String receipt_name, String receipt_tel, String topordernumber,
			String downordernumber, String shipperorder_id, String end_time,
			User user) {
		int count = 0;
		if (user != null) {
			if (user.getFlag().equals("0")) {// 管理员
				count = ar_dao.getShipOrder(send_time, shiping_order_num,
						send_mechanism, end_address, custom_name, receipt_name,
						receipt_tel, topordernumber, downordernumber,
						shipperorder_id, end_time);
			} else if (user.getFlag().equals("1")) {// 司机
				count = ar_dao.driverGetShipOrder(send_time, shiping_order_num,
						send_mechanism, end_address, custom_name, receipt_name,
						receipt_tel, topordernumber, downordernumber,
						user.getDriver_id(), shipperorder_id, end_time);

			} else if (user.getFlag().equals("2")) {// 供应商
				List<String> address = ar_dao.getAddressSupperlies(user
						.getSuppliers_id());
				count = ar_dao.suppliersGetShipOrder(send_time,
						shiping_order_num, send_mechanism, end_address,
						custom_name, receipt_name, receipt_tel, topordernumber,
						downordernumber, shipperorder_id, end_time,
						user.getSuppliers_id(), address);

			} else if (user.getFlag().equals("3")) {// 信息员
				count = ar_dao.otherGetShipOrder(send_time, shiping_order_num,
						send_mechanism, end_address, custom_name, receipt_name,
						receipt_tel, topordernumber, downordernumber,
						shipperorder_id, end_time, user.getId());

			}
		}

		return count;
	}

	// 异常反馈 获取运单信息
	public ShippingOrder getShipOrderMsg(String shippingorderId) {

		return ar_dao.getShipOrderMsg(shippingorderId);
	}

	// 异常反馈 添加信息
	public boolean saveAbnormalReport(AbnormalReport abnormalReport) {
		boolean b = false;
		int i = ar_dao.saveAbnormalReport(abnormalReport);
		if (i > 0) {
			b = true;
		}
		return b;
	}

	// 异常反馈 保存图片信息
	public boolean saveAbnormalImage(List<AbnormalImages> abnormalImages) {
		boolean b = false;
		int i = ar_dao.saveAbnormalImage(abnormalImages);
		if (i > 0) {
			b = true;
		}
		return b;
	}

	// 异常反馈 更新运单状态
	public boolean updateIsAbnormal(String shippingOrderId, String flag) {
		boolean b = false;
		int i = ar_dao.updateIsAbnormal(shippingOrderId, flag);
		if (i > 0) {
			b = true;
		}
		return b;
	}

	// 保存节点信息
	public int saveOrderHistory(List<OrderHistory> order) {

		return ar_dao.saveOrderHistory(order);
	}

	// 司机获取供应商id
	public String getSupplersId(String driverId) {

		return ar_dao.getSupplersId(driverId);
	}

	// 异常反馈查询异常信息
	public List<AbnormalReport> getAbnormalReport(int rows, Integer page,
			String send_time, String end_time, String shiping_order_num,
			String shiping_order_goid, String abnormal_result,
			String abnormal_type, String end_address, String receipt_name,
			String send_mechanism, User user) {
		List<AbnormalReport> list = null;
		if (user != null) {
			if (user.getFlag().equals("0")) {// 管理员
				list = ar_dao.getAbnormalReport(rows, page, send_time,
						end_time, shiping_order_num, shiping_order_goid,
						abnormal_result, abnormal_type, end_address,
						receipt_name, send_mechanism);
			} else if (user.getFlag().equals("1")) {// 司机
				list = ar_dao.deriverGetAbnormalReport(rows, page, send_time,
						end_time, shiping_order_num, shiping_order_goid,
						abnormal_result, abnormal_type, end_address,
						receipt_name, send_mechanism, user.getDriver_id());

			} else if (user.getFlag().equals("2")) {// 供应商
				list = ar_dao.suppliersGetAbnormalReport(rows, page, send_time,
						end_time, shiping_order_num, shiping_order_goid,
						abnormal_result, abnormal_type, end_address,
						receipt_name, send_mechanism, user.getSuppliers_id());

			} else if (user.getFlag().equals("3") || user.getFlag().equals("4")) {// 信息员
				list = ar_dao.otherGetAbnormalReport(rows, page, send_time,
						end_time, shiping_order_num, shiping_order_goid,
						abnormal_result, abnormal_type, end_address,
						receipt_name, send_mechanism, user.getCustomer_id());
			}

		}
		return list;
	}

	// 异常反馈查询异常信息总条数
	public int getAbnormalReportCount(String send_time, String end_time,
			String shiping_order_num, String shiping_order_goid,
			String abnormal_result, String abnormal_type, String end_address,
			String receipt_name, String send_mechanism, User user) {
		int count = 0;
		if (user != null) {
			if (user.getFlag().equals("0")) {// 管理员
				count = ar_dao.getAbnormalReportCount(send_time, end_time,
						shiping_order_num, shiping_order_goid, abnormal_result,
						abnormal_type, end_address, receipt_name,
						send_mechanism);

			} else if (user.getFlag().equals("1")) {// 司机
				count = ar_dao.driverGetAbnormalReportCount(send_time,
						end_time, shiping_order_num, shiping_order_goid,
						abnormal_result, abnormal_type, end_address,
						receipt_name, send_mechanism, user.getDriver_id());

			} else if (user.getFlag().equals("2")) {// 供应商
				count = ar_dao.suppliersGetAbnormalReportCount(send_time,
						end_time, shiping_order_num, shiping_order_goid,
						abnormal_result, abnormal_type, end_address,
						receipt_name, send_mechanism, user.getSuppliers_id());

			} else if (user.getFlag().equals("3") || user.getFlag().equals("4")) {// 信息员
				count = ar_dao.otherGetAbnormalReportCount(send_time, end_time,
						shiping_order_num, shiping_order_goid, abnormal_result,
						abnormal_type, end_address, receipt_name,
						send_mechanism, user.getCustomer_id());

			}
		}
		return count;
	}

	// 异常反馈查询图片信息
	public List<AbnormalImages> showAbnormalImg(String id) {

		return ar_dao.showAbnormalImg(id);
	}

	// 异常反馈获取异常信息
	public AbnormalReport exceptionHandling(String id) {

		return ar_dao.exceptionHandling(id);
	}

	// 异常处理更新异常状态
	public boolean updateAbnormalState(String id, String abnormal_resultremark,
			String userName) {
		boolean b = false;
		int i = ar_dao.updateAbnormalState(id, abnormal_resultremark, userName);
		if (i > 0) {
			b = true;
		}
		return b;
	}

	// 异常处理查询运单是否有没有处理的
	public boolean getAbnormalOrderNum(String orderId) {
		boolean b = true;
		int i = ar_dao.getAbnormalOrderNum(orderId);
		if (i > 0) {
			b = false;
		}
		return b;
	}

	// 更新运单是否异常上报
	public boolean updateOrederAbnormal(String orderId) {
		boolean b = true;
		int i = ar_dao.updateOrederAbnormal(orderId);
		if (i > 0) {
			b = false;
		}
		return b;
	}

	// 获取要推送用户的设备号
	public List<String> getChannelIds(String abnormal_supperid,
			String abnormal_driverid,String abnormalreportId) {

		//return ar_dao.getChannelIds(abnormal_supperid, abnormal_driverid);
		return ar_dao.getChannelIdsTwo(abnormalreportId);
	}

	public List<AbnormalReportFile> outputAbnormalReportFile(String send_time,
			String end_time, String shiping_order_num,
			String shiping_order_goid, String abnormal_result,
			String abnormal_type, String end_address, String receipt_name,
			String send_mechanism, String[] abnormal_ids, User user) {
		List<AbnormalReportFile> list = null;
		if (user != null) {
			if (user.getFlag().equals("0")) {// 管理员
				list = ar_dao.outputAbnormalReport(send_time, end_time,
						shiping_order_num, shiping_order_goid, abnormal_result,
						abnormal_type, end_address, receipt_name,
						send_mechanism, abnormal_ids);

			} else if (user.getFlag().equals("1")) {// 司机
				list = ar_dao.driverOutputAbnormalReport(send_time, end_time,
						shiping_order_num, shiping_order_goid, abnormal_result,
						abnormal_type, end_address, receipt_name,
						send_mechanism, user.getDriver_id(), abnormal_ids);

			} else if (user.getFlag().equals("2")) {// 供应商
				list = ar_dao.suppliersOutputAbnormalReport(send_time,
						end_time, shiping_order_num, shiping_order_goid,
						abnormal_result, abnormal_type, end_address,
						receipt_name, send_mechanism, user.getSuppliers_id(),
						abnormal_ids);

			} else if (user.getFlag().equals("3") || user.getFlag().equals("4")) {// 信息员
				list = ar_dao.otherOutputAbnormalReport(send_time, end_time,
						shiping_order_num, shiping_order_goid, abnormal_result,
						abnormal_type, end_address, receipt_name,
						send_mechanism, user.getCustomer_id(), abnormal_ids);
			}
		}

		return list;
	}
}