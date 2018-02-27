package com.jy.action;

import java.io.OutputStream;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jy.common.ExportUtils;
import com.jy.common.SessionInfo;
import com.jy.common.UUIDUtils;
import com.jy.dao.Json;
import com.jy.model.ChangeOrder;
import com.jy.model.CreateAgreement;
import com.jy.model.OrderHistory;
import com.jy.model.ShippingOrder;
import com.jy.model.User;
import com.jy.service.ChangeOrderInfoService;
import com.jy.service.ShippingOrderInfoService;

//* 货运订单管理
@Controller
@RequestMapping(value = "/ChangeO.do")
public class ChangeOrderInfoController {
	@Resource
	private ChangeOrderInfoService csi;
	@Resource
	private ShippingOrderInfoService css;

	// 暂时没有用
	@RequestMapping(params = "method=ChangeOrderInfo")
	public String getShipOrderInfoMan() {
		return "change_order/changeOrderInfo";
	}

	// 暂时没有用
	@RequestMapping(params = "method=AddchangeOrder")
	public String AddchangeOrder(String pid, HttpServletRequest request) {
		request.setAttribute("flg", pid);
		return "change_order/AddchangeOrder";
	}

	// 暂时没有用
	@RequestMapping(params = "method=EditchangeOrder")
	public String EditchangeOrder(String pid, HttpServletRequest request) {
		request.setAttribute("flg", pid);
		return "change_order/EditchangeOrder";
	}

	//// 暂时没有用
	@SuppressWarnings("rawtypes")
	@RequestMapping(params = "method=getChangeOrder")
	public @ResponseBody
	Map getChangeOrderInfo(String rows, String page, String name,
			String phone_number, String type, String start_date, String end_date) {
		Integer rows1 = 0;// 当前有几行
		Integer page1 = 1;// 当前有几页
		if (rows != null && !"".equals(rows)) {
			rows1 = Integer.parseInt(rows);
		}
		if (page != null && !"".equals(page)) {
			page1 = Integer.parseInt(page);
		}
		List<ShippingOrder> list = csi.getChangeOrderInfo((page1 - 1) * rows1,
				rows1, name, phone_number, type, start_date, end_date);
		int count = csi.getChangeOrder(name, phone_number, type, start_date,
				end_date);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", count);
		map.put("rows", list);
		return map;
	}

	// 订单群发页面
	@RequestMapping(params = "method=OrderSendMsg")
	public String OrderSendMsg() {
		return "ordermessage/orderSendMsg";
	}
	
	// 暂时没有用
	@SuppressWarnings("rawtypes")
	@RequestMapping(params = "method=getOrderInfo")
	public @ResponseBody
	Map getOrderInfo(String rows, String page, String num, String receipt,
			String phone, String send_type) {
		Integer rows1 = 0;// 当前有几行
		Integer page1 = 1;// 当前有几页
		if (rows != null && !"".equals(rows)) {
			rows1 = Integer.parseInt(rows);
		}
		if (page != null && !"".equals(page)) {
			page1 = Integer.parseInt(page);
		}
		List<ShippingOrder> list = csi.getOrderInfo((page1 - 1) * rows1, rows1,
				num, receipt, phone, send_type);
		int count = csi.getOrderInfoCount(num, receipt, phone, send_type);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", count);
		map.put("rows", list);
		return map;
	}

	// * 批量删除

	@RequestMapping(params = "method=deleteChangeOrder")
	public @ResponseBody
	Json delShipOrder(String[] del) {
		Json json = new Json();
		int truck = csi.deleteChangeOrder(del);
		if (truck > 0) {
			json.setFlag(true);
			return json;
		}
		json.setFlag(false);
		return json;
	}

	// 保存
	@RequestMapping(params = "method=saveChangeOrder")
	public @ResponseBody
	Json saveChangeOrder(ChangeOrder d) {
		Json json = new Json();
		String id = d.getShiping_order_id();
		int check = csi.searchOrder(id);
		if (check > 0) {
			d.setUpdate_time(DateFormat.getDateTimeInstance()
					.format(new Date()));
			int up = csi.updateChangeOrder(d);
			if (up > 0) {
				json.setFlag(true);
				return json;
			} else {
				json.setFlag(false);
				return json;
			}
		} else {
			d.setChange_fee_id(UUIDUtils.getUUID());
			d.setUpdate_time(DateFormat.getDateTimeInstance()
					.format(new Date()));
			int truck = csi.saveChangeOrder(d);
			int order = csi.updateOrder(d.getShiping_order_id());
			if (truck > 0 && order > 0) {
				json.setFlag(true);
				return json;
			} else {
				json.setFlag(false);
				return json;
			}
		}
	}

	// 修改查询
	@RequestMapping(params = "method=getUpdateChangeOrder")
	public @ResponseBody
	ChangeOrder getUpdateShipOrder(String id) {
		ChangeOrder der = csi.getUpdateChangeOrder(id);
		return der;
	}

	// 修改
	@RequestMapping(params = "method=updateChangeOrder")
	public @ResponseBody
	Json updateChangeOrder(ChangeOrder d) {
		d.setUpdate_time(DateFormat.getDateTimeInstance().format(new Date()));
		Json json = new Json();
		int i = csi.updateChangeOrder(d);
		if (i > 0) {
			json.setFlag(true);
			return json;
		}
		json.setFlag(false);
		return json;
	}

	// * 订单查询协议类型

	@RequestMapping(params = "method=getAddOrderFee")
	public @ResponseBody
	CreateAgreement getAddOrderFee(String id) {
		CreateAgreement der = csi.getAddOrderFee(id);
		return der;
	}

	// 车牌检索
	/*
	 * @RequestMapping(params = "method=getPlateNumberLength") public
	 * 
	 * @ResponseBody String getPlateNumberLength(String number) {
	 * List<ShippingOrder> list = csi.getPlateNumberLength(number); JSONObject
	 * jb = null; JSONArray jsons = new JSONArray(); for (ShippingOrder d :
	 * list) { jb = new JSONObject(); jb.put("id", d.getTravel_card_id());
	 * jb.put("name", d.getPlate_number()); jsons.add(jb); } return
	 * jsons.toString(); }
	 */

	// 导出信息
	@RequestMapping(params = "method=outShipOrder")
	public @ResponseBody
	String outShipOrder(String datas, String[] headtitle, String[] fieldName,
			HttpServletResponse response, String name, String plate_number,
			String type, String start_date, String end_date) {
		String filename = "运单信息导出";
		List<ShippingOrder> list = csi.getShipOrderAll(name, plate_number,
				type, start_date, end_date);
		// ExportExcel<Agreement> esx= new ExportExcel<Agreement>();
		try {
			// 声明一个工作薄
			HSSFWorkbook workbook = new HSSFWorkbook();
			// 生成一个表格
			response.setContentType("application/vnd.ms-excel;");
			response.setHeader("Content-Disposition", "attachment;filename=\""
					+ new String(filename.getBytes("gbk"), "iso-8859-1")
					+ ".xls\"");
			OutputStream ouputStream = response.getOutputStream();
			// 调用工具类创建表头
			ExportUtils.outputHeaders(headtitle, workbook, "运单信息导出");
			// 调用工具类生成内容
			ExportUtils.outputColums(fieldName, list, workbook, 1, "运单信息导出");
			workbook.write(ouputStream);
			ouputStream.flush();
			ouputStream.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 客户回单 跳转回单页面
	 * 
	 * @return
	 */
	@RequestMapping(params = "method=backOrderInfo")
	public String backOrderInfo() {
		return "back_oder/backOrderInfo";
	}

	/**
	 * 回单管理 回单信息查看
	 * 
	 * @param rows
	 * @param page
	 * @param name
	 * @param phone_number
	 * @param type
	 * @param start_date
	 * @param end_date
	 * @param start_date1
	 * @param end_date1
	 * @param start_date2
	 * @param end_date2
	 * @param custom_name
	 * @param receipt
	 * @param address
	 * @param shipping_order_state
	 * @param shipperorder_id
	 * @param session
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(params = "method=getBackOrderInfo")
	public @ResponseBody
	Map getBackOrderInfo(String rows, String page, String name,
			String phone_number, String type, String start_date,
			String end_date, String start_date1, String end_date1,
			String start_date2, String end_date2, String custom_name,
			String receipt, String address, String shipping_order_state,
			String shipperorder_id, HttpSession session) {
		Integer rows1 = 0;// 当前有几行
		Integer page1 = 1;// 当前有几页
		if (rows != null && !"".equals(rows)) {
			rows1 = Integer.parseInt(rows);
		}
		if (page != null && !"".equals(page)) {
			page1 = Integer.parseInt(page);
		}
		User user = (User) session.getAttribute(SessionInfo.SessionName);
		Map<String, Object> map = new HashMap<String, Object>();
		List<ShippingOrder> list = csi.getBackOrderInfo((page1 - 1) * rows1,
				rows1, name, phone_number, type, start_date, end_date,
				start_date1, end_date1, start_date2, end_date2, custom_name,
				receipt, address, shipping_order_state, shipperorder_id, user);
		int count = csi.getBackOrder(name, phone_number, type, start_date,
				end_date, start_date1, end_date1, start_date2, end_date2,
				custom_name, receipt, address, shipping_order_state,
				shipperorder_id, user);
		map.put("total", count);
		map.put("rows", list);
		return map;
	}

	/**
	 * 客户回单 回单返回
	 * 
	 * @param deal
	 * @param dealnum
	 * @param session
	 * @return
	 */
	@RequestMapping(params = "method=dealBackOrder")
	public @ResponseBody
	Json dealBackOrder(String[] deal, String[] dealnum, HttpSession session) {
		Json json = new Json();
		if (deal.length > 0) {
			User user = (User) session.getAttribute(SessionInfo.SessionName);
			if (user.getFlag().equals("0")||user.getFlag().equals("3")||user.getFlag().equals("4")) {
				int truck = csi.otherDealBackOrder(deal);
				if (truck > 0) {
					json.setFlag(true);
					for (int i = 0; i < dealnum.length; i++) {
						saveHistory(deal[i], dealnum[i], "信息员回单已收到");
					}
					return json;
				}

			} else {
				int truck = csi.dealBackOrder(deal);
				if (truck > 0) {
					json.setFlag(true);
					for (int i = 0; i < dealnum.length; i++) {
						saveHistory(deal[i], dealnum[i], "供应商回单已返回");
					}
					return json;
				}

			}
		}
		json.setFlag(false);
		return json;
	}

	/**
	 * 保存历史历史记录
	 * 
	 * @param ids
	 * @param num
	 * @param message
	 */
	public void saveHistory(String ids, String num, String message) {
		List<OrderHistory> order = new ArrayList<OrderHistory>();
		OrderHistory h = new OrderHistory();
		h.setOrder_history_id(UUIDUtils.getUUID());
		h.setOrders_id(ids);
		h.setOrder_arrive_time(DateFormat.getDateTimeInstance().format(
				new Date()));
		h.setGoods_arrive_remakes(h.getOrder_arrive_time() + "---货运编号为" + num
				+ "\t" + message);
		order.add(h);
		css.saveOrderHistory(order);

	}

	// * 批量未接收处理
	@RequestMapping(params = "method=dealNotBackOrder")
	public @ResponseBody
	Json dealNotBackOrder(String[] deal) {
		Json json = new Json();
		int truck = csi.dealNotBackOrder(deal);
		if (truck > 0) {
			json.setFlag(true);
			return json;
		}
		json.setFlag(false);
		return json;
	}

	/**
	 * 客户回单 回单信息导出导出
	 * @param headtitle
	 * @param fieldName
	 * @param dataIds
	 * @param response
	 * @param session
	 * @param name
	 * @param type
	 * @param start_date
	 * @param end_date
	 * @param start_date1
	 * @param end_date1
	 * @param custom_name
	 * @param receipt
	 * @param address
	 * @param shipping_order_state
	 * @param shipperorder_id
	 * @return
	 */
	@RequestMapping(params = "method=outBackShipOrderFile")
	public @ResponseBody
	String outBackShipOrderFile(String[] headtitle, String[] fieldName,
			String[] dataIds, HttpServletResponse response,
			HttpSession session, String name, String type, String start_date,
			String end_date, String start_date1, String end_date1,
			String custom_name, String receipt, String address,
			String shipping_order_state, String shipperorder_id) {
		if (dataIds.length == 0) {
			dataIds = null;
		}
		String filename = "客户回单信息导出";
		User user = (User) session.getAttribute(SessionInfo.SessionName);
		List<ShippingOrder> list = csi.outBackShipOrderFile(user, name,
				type, start_date, end_date, start_date1, end_date1,
				custom_name, receipt, address, shipping_order_state,
				shipperorder_id, dataIds);
		// ExportExcel<Agreement> esx= new ExportExcel<Agreement>();
		try {
			// 声明一个工作薄
			HSSFWorkbook workbook = new HSSFWorkbook();
			// 生成一个表格
			response.setContentType("application/vnd.ms-excel;");
			response.setHeader("Content-Disposition", "attachment;filename=\""
					+ new String(filename.getBytes("gbk"), "iso-8859-1")
					+ ".xls\"");
			OutputStream ouputStream = response.getOutputStream();
			// 调用工具类创建表头
			ExportUtils.outputHeaders(headtitle, workbook, "客户回单信息导出");
			// 调用工具类生成内容
			ExportUtils.outputColums(fieldName, list, workbook, 1, "客户回单信息导出");
			workbook.write(ouputStream);
			ouputStream.flush();
			ouputStream.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
