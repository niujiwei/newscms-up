package com.jy.csaction;

import java.io.OutputStream;
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
import com.jy.common.ExportUtils;
import com.jy.common.SessionInfo;
import com.jy.common.UUIDUtils;
import com.jy.dao.Json;
import com.jy.model.DeliveryCustomer;
import com.jy.model.ShippingOrder;
import com.jy.model.User;
import com.jy.service.RemarkMapService;
import com.jy.service.ShippingOrderInfoService;
import com.jy.thread.CustomPayThread;

@Controller
@RequestMapping(value = "/settlement.do")
public class SettlementController {
	@Resource
	private ShippingOrderInfoService csi;
	@Resource
	private RemarkMapService rms;

	/**
	 * 客户结算跳转页面
	 * 
	 * @param menu_id
	 * @return
	 */
	@RequestMapping(params = "method=getCustomerbilling")
	public String getCustomerbilling(String menu_id) {
		return "settlement/Customerbilling";
	}

	/**
	 * 客户分页
	 */
	@RequestMapping(params = "method=getCustomerbillingList")
	public @ResponseBody
	Map<String, Object> getRemarks(String page, String rows,
			String customer_name, String customer_tel, String customer_address,
			String User_address) {
		// 当前第几页
		Integer page2 = 1;
		// 当前页一共几条
		Integer rows1 = 0;
		if (page != null && !"".equals(page)) {
			page2 = Integer.parseInt(page);
		}
		if (rows != null && !"".equals(rows)) {
			rows1 = Integer.parseInt(rows);
		}
		int total = rms.getDeliveryCount(customer_name, customer_tel,
				customer_address, User_address);
		List<DeliveryCustomer> list = rms.getDeliveryCustomers((page2 - 1)
				* rows1, rows1, customer_name, customer_tel, customer_address,
				User_address);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", total);
		map.put("rows", list);
		return map;
	}

	@RequestMapping(params = "method=getDeliveryCustomer")
	public String addremarkMap() {
		return "customer/addDeliveryCustomer";
	}

	// 保存的客户信息
	@RequestMapping(params = "method=saveDeliveryCustomer")
	public @ResponseBody
	Json saveDeliveryCustomer(DeliveryCustomer customer) {
		customer.setDelivery_id(UUIDUtils.getUUID());
		int i = rms.saveDeliveryCustomer(customer);
		Json json = new Json();
		if (i > 0) {
			json.setFlag(true);
		} else {
			json.setFlag(false);
		}
		return json;
	}

	// 修改用户
	@RequestMapping(params = "method=UpdateDeliverycustomer")
	public String UpdateDeliverycustomer(String cid, HttpServletRequest request) {
		request.setAttribute("cid", cid);
		return "customer/UpdateDeliverycustomer";
	}

	@RequestMapping(params = "method=getDeliveryCustomerbyid")
	public @ResponseBody
	DeliveryCustomer getDeliveryCustomerbyid(String cid) {
		return rms.getDeliveryCustomerbyid(cid);
	}

	@RequestMapping(params = "method=savefroupdateDeliveryCustomer")
	public @ResponseBody
	Json savefroupdateDeliveryCustomer(DeliveryCustomer customer,
			HttpSession session) {
		User user = (User) session.getAttribute(SessionInfo.SessionName);
		customer.setDelivery_operation(user.getRealName());
		int i = rms.updateDeliveryCustomer(customer);
		Json json = new Json();
		if (i > 0) {
			json.setFlag(true);
		} else {
			json.setFlag(false);
		}
		return json;
	}

	/**
	 * 删除指定客户
	 */
	@RequestMapping(params = "method=deleteDeliveryCustomer")
	public @ResponseBody
	boolean deleteDeliveryCustomer(String id) {
		// rms.deleteDeliveryCustomer(id);
		return false;
	}

	@RequestMapping(params = "method=DeliveryCustomerMapmodify")
	public String DeliveryCustomerMapmodify(String id,
			HttpServletRequest request) {
		request.setAttribute("id", id);
		return "customer/locationDeliveryCustomerMap";
	}

	/**
	 * 客户区域定位
	 */
	@RequestMapping(params = "method=updateLocationRemarkCustomer")
	public String updateLocationRemarkCustomer(DeliveryCustomer customer,
			String kkk) {
		rms.updateLocationRemarkCustomer(customer);
		if (kkk.equals("save")) {
			return "customer/deliveryCustomer";
		} else {
			return "map/addremarkMap";

		}

	}

	// 新的导出方案
	@RequestMapping(params = "method=outShipOrder")
	public @ResponseBody
	String outShipOrder(String datas, String max_time, String min_time,
			String custom_name1, HttpServletResponse response) {
		String filename = "客户结算信息导出";
		String[] headtitle1 = { "货运编号", "出货订单号", "受理机构", "终到位置", "总件数", "配送费",
				"上楼费", "附加费", "代收款", "合计", "备注", "起运时间", "时效结果", "到货联系人",
				"货物名称", "总重量", "总体积" };
		String[] fieldName1 = { "shiping_order_num", "shiping_order_goid",
				"send_mechanism", "end_address", "goods_num", "deliver_fee",
				"upstairs_fee", "added_fee", "trade_agency", "heji", "remarks",
				"send_time", "result", "receipt_name", "goods_name",
				"goods_weight", "goods_vol" };
		try {
			// 声明一个工作薄
			HSSFWorkbook workbook = new HSSFWorkbook();
			// 生成一个表格
			response.setContentType("application/vnd.ms-excel;");
			response.setHeader("Content-Disposition", "attachment;filename=\""
					+ new String(filename.getBytes("gbk"), "iso-8859-1")
					+ ".xls\"");
			OutputStream ouputStream = response.getOutputStream();
			Gson gson = new Gson();
			if (custom_name1 != null) {
				List<ShippingOrder> lists = gson.fromJson(custom_name1,
						new TypeToken<List<ShippingOrder>>() {
						}.getType());
				for (ShippingOrder shippingOrder : lists) {

					ExportUtils.outputHeaders(headtitle1, workbook,
							shippingOrder.getSend_mechanism());
					List<ShippingOrder> lOrders = csi.getAllMessage(
							shippingOrder.getSend_mechanism(),
							shippingOrder.getMin_time(),
							shippingOrder.getMax_time());
					ShippingOrder ship = csi.getSumMessage(
							shippingOrder.getSend_mechanism(),
							shippingOrder.getMin_time(),
							shippingOrder.getMax_time());
					ship.setShiping_order_num("合计");
					lOrders.add(ship);
					ExportUtils.outputColums(fieldName1, lOrders, workbook, 1,
							"客户结算信息导出");
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
	 * 客户结算 结算选中的信息
	 */
	@RequestMapping(params = "method=custom_pay")
	public @ResponseBody
	boolean custom_pay(String custom_code, String min_time, String max_time,HttpSession session) {
		List<ShippingOrder> list = csi.selectShippinOrderId(custom_code,
				min_time, max_time);
		boolean b = csi.custom_pay(custom_code, min_time, max_time);
		User user = (User) session.getAttribute(SessionInfo.SessionName);
		if (b) {
			new Thread(new CustomPayThread(list, csi,user)).start();// 保存结算信息
		}
		return b;
	}

}
