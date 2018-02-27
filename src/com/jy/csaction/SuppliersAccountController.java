package com.jy.csaction;

import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jy.action.AbnormalReportController;
import com.jy.common.ExportUtils;
import com.jy.common.SessionInfo;
import com.jy.common.UUIDUtils;
import com.jy.dao.SuppliersAccountDao;
import com.jy.model.Customer;
import com.jy.model.JySuppliers;
import com.jy.model.ShippingOrder;
import com.jy.model.User;
import com.jy.service.SuppliersService;
import com.jy.thread.SupliersRunnable;

/**
 * 供应商结算
 * 
 * @author 90
 * 
 */
@Controller
@RequestMapping(value = "/Suppliers.do")
public class SuppliersAccountController {
	@Resource
	private SuppliersService ss;
	@Resource
	private SuppliersAccountDao sa;

	/**
	 * 跳转供应商结算页面
	 * 
	 * @return
	 */
	@RequestMapping(params = "method=getSuppliers")
	public String getSuppliers() {

		return "supplier/suppliersAccount";

	}

	/**
	 * 供应商结算 查询供应商信息
	 * 
	 * @param rows
	 * @param page
	 * @param suppliersName
	 * @param suppliersSendTime
	 * @param suppliersEndTime
	 * @param suppliersEndAddress
	 * @param suppliers_id
	 * @param session
	 * @return
	 */
	@RequestMapping(params = "method=getSupplierss")
	public @ResponseBody
	Map getSupplierss(String rows, String page, String suppliersName,
			String suppliersSendTime, String suppliersEndTime,
			String suppliersEndAddress, String suppliers_id, HttpSession session) {
		// System.out.println(suppliers_id+"5555555");
		Integer rows1 = 0;// 当前有几行
		Integer page1 = 1;// 当前有几页
		if (rows != null && !"".equals(rows)) {
			rows1 = Integer.parseInt(rows);
		}
		if (page != null && !"".equals(page)) {
			page1 = Integer.parseInt(page);
		}
		User user = (User) session.getAttribute(SessionInfo.SessionName);
		List<JySuppliers> list = ss.getSupplierss((page1 - 1) * rows1, rows1,
				suppliersName, suppliersSendTime, suppliersEndTime,
				suppliersEndAddress, user);
		int count = ss.getSupplierCount(suppliersName, suppliersSendTime,
				suppliersEndTime, suppliersEndAddress, user);
		for (JySuppliers jySuppliers : list) {

			if (jySuppliers.getClearing_state().equals("0")) {
				jySuppliers.setClearing_state("1");
			} else {
				jySuppliers.setClearing_state("0");
			}

		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", count);
		map.put("rows", list);

		return map;
	}

	/**
	 * 供应商结算跳转详细页面
	 * 
	 * @param driverId
	 * @param pid
	 * @param time
	 * @param timeId
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "method=getSupplierOrder")
	public String getSupplierOrder(String driverId, String pid, String time,
			String timeId, HttpServletRequest request) {
		request.setAttribute("flg", pid);
		request.setAttribute("tim", time);
		request.setAttribute("tmi", timeId);
		request.setAttribute("driverId", driverId);
		return "supplier/suppliersOrder";
	}

	/**
	 * 供应商结算详细信息
	 * 
	 * @param rows
	 * @param page
	 * @param pid
	 * @param shiping_order_num
	 * @param end_address
	 * @param custom_name
	 * @param receipt_name
	 * @param receipt_tel
	 * @param send_mechanism
	 * @param send_time
	 * @param time
	 * @param timeId
	 * @param driverId
	 * @param shipperorder_id
	 * @param clearing_state
	 * @return
	 */
	@RequestMapping(params = "method=getShiOrederCon")
	public @ResponseBody
	Map getShiOreder(String rows, String page, String pid,
			String shiping_order_num, String end_address, String custom_name,
			String receipt_name, String receipt_tel, String send_mechanism,
			String send_time, String time, String timeId, String[] driverId,
			String shipperorder_id, String clearing_state, HttpSession session) {
		Integer rows1 = 0;// 当前有几行
		Integer page1 = 1;// 当前有几页

		if (rows != null && !"".equals(rows)) {
			rows1 = Integer.parseInt(rows);
		}
		if (page != null && !"".equals(page)) {
			page1 = Integer.parseInt(page);
		}
		User user = (User) session.getAttribute(SessionInfo.SessionName);
		if (user.getFlag().equals("1")) {

		} else if (user.getFlag().equals("2")) {

		} else if (user.getFlag().equals("0")) {

		} else if (user.getFlag().equals("3") || user.getFlag().equals("4")) {
			Customer customer = ss.getCustomer(user.getCustomer_id());
			send_mechanism = customer.getCustomer_name();
		}

		int count = ss.getShiOrederCount(pid, shiping_order_num, end_address,
				custom_name, receipt_name, receipt_tel, send_mechanism,
				send_time, time, timeId, driverId, shipperorder_id,
				clearing_state);
		List<ShippingOrder> list = ss.getShiOreder((page1 - 1) * rows1, rows1,
				pid, shiping_order_num, end_address, custom_name, receipt_name,
				receipt_tel, send_mechanism, send_time, time, timeId, driverId,
				shipperorder_id, clearing_state);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", count);
		map.put("rows", list);
		return map;
	}

	/**
	 * 供应商结算运单信息导出
	 * 
	 * @param datas
	 * @param suppliersName
	 * @param suppliersSendTime
	 * @param suSendTime
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "method=outSuppOrder")
	public @ResponseBody
	String outSuppOrder(String[] dataIds, String suppliersName,
			String suppliersSendTime, String suSendTime, String suppliersId,
			String suppliersAddress, HttpServletResponse response,
			HttpSession session) {
		// System.out.println(suppliersName+suppliersSendTime+suSendTime);
		
		User user = (User) session.getAttribute(SessionInfo.SessionName);
		String send_mechanism = "";
		String propertiesPath ="suppliersAccountData.properties";


		if (user.getFlag().equals("1")) {
			propertiesPath="suppSuppliersAccountData.properties";


		} else if (user.getFlag().equals("2")) {
			propertiesPath="suppSuppliersAccountData.properties";
		} else if (user.getFlag().equals("0")) {

		} else if (user.getFlag().equals("3") || user.getFlag().equals("4")) {
			Customer customer = ss.getCustomer(user.getCustomer_id());
			send_mechanism = customer.getCustomer_name();
		}
		String filename = "供应商结算信息导出";
		// List<String> list = sa.getOutClass(suppliersName);
		InputStream in = AbnormalReportController.class
				.getResourceAsStream("/../exportData/"+propertiesPath);
		Map<String, List<String>> map = ExportUtils.getHeadTitle(in);//通用获取headtitle fieldName
		List<String> headTitle =map.get("headTitle");
		List<String> fieldName =map.get("fieldName");


		// ExportExcel<Agreement> esx= new ExportExcel<Agreement>();
		try {
			// 声明一个工作薄
			HSSFWorkbook workbook = new HSSFWorkbook();
			// 生成一个表格
			response.setContentType("application/vnd.ms-excel;");
			response.setHeader("Content-Disposition", "attachment;filename=\""
					+ new String(filename.getBytes("UTF-8"), "iso-8859-1")
					+ ".xls\"");
			OutputStream ouputStream = response.getOutputStream();
			Gson gson = new Gson();
			if (suppliersId != null) {
				List<JySuppliers> jySuppliers = gson.fromJson(suppliersId,
						new TypeToken<List<JySuppliers>>() {
						}.getType());
				for (JySuppliers jySuppliers2 : jySuppliers) {
					if (jySuppliers2 != null) {
						ExportUtils.outputHeaders((String[]) headTitle.toArray(new String[headTitle.size()]), workbook,
								jySuppliers2.getSuppliersName());
						List<ShippingOrder> list = sa.getOutSuppOrder(
								jySuppliers2.getSuppliersName(),
								jySuppliers2.getSuppliersSendTime(),
								jySuppliers2.getSuSendTime(), send_mechanism,jySuppliers2.getSuppliersId());
						/*for (ShippingOrder sod : list) {
							if ("0".equals(sod.getClearing_state())) {
								sod.setClearing_state("订单未结算");
							} else if ("1".equals(sod.getClearing_state())) {
								sod.setClearing_state("订单已结算");
							}
						}*/
						ShippingOrder lists = sa.getOutHeji(
								jySuppliers2.getSuppliersName(),
								jySuppliers2.getSuppliersSendTime(),
								jySuppliers2.getSuSendTime(), send_mechanism,jySuppliers2.getSuppliersId());
						lists.setShiping_order_num("合计");
						list.add(lists);
						ExportUtils.outputColums((String[]) fieldName.toArray(new String[fieldName.size()]), list, workbook, 1,
								"供应商结算信息导出");
					}

					/*
					 * JySuppliers ship = sa.getOutClass(suppliersName,
					 * suppliersSendTime, suSendTime);
					 */

				}

			}

			workbook.write(ouputStream);
			ouputStream.flush();
			ouputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 供应商结算结算信息
	 * 
	 * @param driverId
	 * @param suppliersSendTime
	 * @param suSendTime
	 * @param sign_order
	 * @return
	 */
	@RequestMapping(params = "method=settleSupp")
	public @ResponseBody
	int settleSupp(String[] driverId, String suppliersSendTime,
			String suSendTime, ShippingOrder sign_order,HttpSession session) {
		// System.out.println(suppliersSendTime+suSendTime+"$$$$$$$$$$"+driverId.length);
		sign_order.setSign_id(UUIDUtils.getUUID());
		if (sign_order.getSign_time() == null
				|| "".equals(sign_order.getSign_time())) {
			SimpleDateFormat dateFor = new SimpleDateFormat(
					"yyyy-MM-dd hh:mm:ss");
			sign_order.setSign_time(dateFor.format(new Date()));
		}

		// saveOrderSuppliers(sign_order.getSign_name(),sign_order.getOrder_number(),"已签收,签收人:"+sign_order.getSign_user());
		List<ShippingOrder> list = ss.getShi(driverId, suppliersSendTime,
				suSendTime);
		User user = (User) session.getAttribute(SessionInfo.SessionName);
		int i = ss.settleSupp(driverId, suppliersSendTime, suSendTime);

		if (i > 0) {
			new Thread(new SupliersRunnable(list, ss,user)).start();
		}
		return i;

	}

}
