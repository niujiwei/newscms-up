package com.jy.action;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jy.common.*;
import com.jy.thread.FenPeiSaveHistoryThread;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.avalon.framework.configuration.Configuration;
import org.apache.avalon.framework.configuration.DefaultConfiguration;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.krysalis.barcode4j.BarcodeGenerator;
import org.krysalis.barcode4j.BarcodeUtil;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.MimeTypes;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.jy.csaction.BaseAction;
import com.jy.dao.Json;
import com.jy.model.Aging;
import com.jy.model.ComboTreeModel;
import com.jy.model.Customer;
import com.jy.model.MsgModel;
import com.jy.model.OrderHistory;
import com.jy.model.ShipperOrderImg;
import com.jy.model.ShippingOrder;
import com.jy.model.Sign_order;
import com.jy.model.User;
import com.jy.service.OrderInfoService;
import com.jy.service.ShippingOrderInfoService;
import com.jy.service.SuppliersService;
import com.jy.service.impl.OrderInfoServiceImpl;

/**
 * 订单管理
 * 
 * @author 90
 * 
 */
@Controller
@RequestMapping(value = "/shipOrder.do")
public class ShippingOrderInfoController extends BaseAction {
	@Resource
	private ShippingOrderInfoService csi;
	@Resource
	private OrderInfoService ordersercice;
	@Resource
	private OrderInfoServiceImpl OrderInfoServiceImpl;
	@Resource
	private SuppliersService suppliersService;

	/**
	 * 运单录入跳转页面
	 * 
	 * @return
	 */
	@RequestMapping(params = "method=getShipOrderInfo")
	public String getShipOrderInfoMan() {
		return "shipping_order/shippingOrderInfo";
	}

	/**
	 * 打印一维码
	 * 
	 * @param checkarray
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "method=getShipOrderPrintYwm")
	public String getShipOrderPrintYWM(String[] checkarray,
			HttpServletRequest request) {
		StringBuffer s = new StringBuffer();
		for (int i = 0; i < checkarray.length; i++) {
			s.append(checkarray[i] + ",");
		}
		String num = s.substring(0, s.lastIndexOf(","));
		request.setAttribute("sp", num);
		return "shipping_order/printYWM";
	}

	/**
	 * 输出一维码
	 * 
	 * @param order_num
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "method=getShipYwm")
	public @ResponseBody
	String getShipYWM(String order_num, HttpServletResponse response) {
		String[] s = order_num.split(",");
		List<ShippingOrder> d = csi.getUpdateShipOrderArray(s);
		JSONObject obje = new JSONObject();
		obje.accumulate("Detail", d);
		return obje.toString();
	}

	/**
	 * 运单管理运单导入跳转页面
	 * 
	 * @return
	 */
	@RequestMapping(params = "method=imp")
	public String goimp() {
		return "shipping_order/maplinimp";
	}

	/**
	 * 做导入前的上传处理
	 * 
	 * @param request
	 * @param files
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@RequestMapping(params = "method=startimplinplan")
	public @ResponseBody
	JSONObject startimplinplan(HttpServletRequest request,
			@RequestParam(value = "files") MultipartFile files)
			throws IllegalStateException, IOException {
		String path = request.getSession().getServletContext()
				.getRealPath("WebRoot/orderFiles/");// 文件路径
		File filez = new File(path);
		if (!filez.exists()) {
			filez.mkdirs();
		}
		MultipartFile myfile = files;
		String filename = myfile.getOriginalFilename();
		String prefix = filename.substring(filename.lastIndexOf("."));
		@SuppressWarnings("static-access")
		File newfile = filez.createTempFile("shipOrder", prefix, filez);
		String filesname = newfile.toString();
		filesname = filesname.substring(filesname.lastIndexOf("\\") + 1,
				filesname.length());
		// request.setAttribute("filesname", filesname);
		myfile.transferTo(newfile);
		JSONObject json = new JSONObject();
		json.put("filesname", filesname);
		return json;
	}

	/**
	 * 运单录入 导入上传开始保存到数据库
	 * 
	 * @param request
	 * @param filename
	 * @param usersname
	 * @param pid
	 * @return
	 */
	@RequestMapping(params = "method=implinplan")
	public @ResponseBody
	String implinplan(HttpServletRequest request, String filename,
			String usersname, String pid) {
		String filepath = request.getSession().getServletContext()
				.getRealPath("WebRoot/orderFiles/" + filename);
		OrderExcelForPOI oef = new OrderExcelForPOI(ordersercice);
		String flag = "";

		User user = (User) request.getSession().getAttribute(
				SessionInfo.SessionName);
		if (user != null) {
			Customer customer = csi.userGetCusetomerInfo(user.getCustomer_id());
			if (customer != null) {
				user.setCustomer_name(customer.getCustomer_name());
			}
		}
		try {
			flag = oef.impExcel(filepath, user, pid);
			deleteFile(filepath); // 执行上传文件删除操作
			return flag;

		} catch (Exception e) {
			e.printStackTrace();

			return flag;
		}

	}

	/**
	 * 删除单个文件
	 * 
	 * @param sPath
	 *            被删除文件的文件名
	 * @return 单个文件删除成功返回true，否则返回false
	 */
	public boolean deleteFile(String sPath) {
		boolean flag;
		flag = false;
		File file = new File(sPath);
		// 路径为文件且不为空则进行删除
		if (file.isFile() && file.exists()) {
			file.delete();
			flag = true;
		}
		return flag;
	}

	/**
	 * 运单录入查询
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
	 * @param session
	 * @param end_time
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(params = "method=getShipOrder")
	public @ResponseBody
	Map getShipOrder(String rows, String page, String send_time,
			String shiping_order_num, String send_mechanism,
			String end_address, String custom_name, String receipt_name,
			String receipt_tel, String topordernumber, String downordernumber,
			String shipperorder_id, HttpSession session, String end_time) {
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
		List<ShippingOrder> list = csi.getShipOrderInfo((page1 - 1) * rows1,
				rows1, send_time, shiping_order_num, send_mechanism,
				end_address, custom_name, receipt_name, receipt_tel,
				topordernumber, downordernumber, shipperorder_id, end_time,
				user);
		int count = csi.getShipOrder(send_time, shiping_order_num,
				send_mechanism, end_address, custom_name, receipt_name,
				receipt_tel, topordernumber, downordernumber, shipperorder_id,
				end_time, user);
		map.put("total", count);
		map.put("rows", list);
		return map;
	}

	// 发送短信
	@RequestMapping(params = "method=sendOrderMsg")
	public @ResponseBody
	Json sendOrderMsg(String phone, String orderMsg) {
		HttpSenderTest.pushMassage(phone, orderMsg);
		Json json = new Json();
		int truck = 1;
		if (truck > 0) {
			json.setFlag(true);
			return json;
		}
		json.setFlag(false);
		return json;
	}

	// 保存消息模板zzp添加
	@RequestMapping(params = "method=sendMsgModelInfo")
	public @ResponseBody
	Json sendMsgModelInfo(String msgTitle, String msgContent) {

		int truck = csi.sendMsgModelInfo(msgTitle, msgContent);
		Json json = new Json();
		if (truck > 0) {
			json.setFlag(true);
			return json;
		}
		json.setFlag(false);
		return json;
	}

	// 消息模板selectzzp添加

	@RequestMapping(params = "method=getMsgModel")
	public @ResponseBody
	List<Map<String, Object>> getMsgModel() {
		List<MsgModel> type = csi.getMsgModel();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		for (MsgModel rt : type) {
			map = new HashMap<String, Object>();
			map.put("id", rt.getModel_id());
			map.put("text", rt.getModel_title());
			list.add(map);
		}
		return list;
	}

	// 保存消息模板zzp添加
	@RequestMapping(params = "method=getMsgContent")
	public @ResponseBody
	String getMsgContent(String msgid) {
		String truck = csi.sendMsgContent(msgid);
		return truck;
	}

	/**
	 * 运单录入添加跳转页面
	 * 
	 * @param pid
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "method=addShipOrder")
	public String addShipOrder(String pid, HttpServletRequest request) {
		return "shipping_order/AddShipOrder";
	}

	/**
	 * 运单录入添加保存运单信息
	 * 
	 * @param d
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "method=saveShipOrder")
	public @ResponseBody
	Json saveShipOrder(ShippingOrder d, HttpServletRequest request) {
		Json json = new Json();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String date = dateFormat.format(new Date());
		/*
		 * User user = (User) request.getSession().getAttribute(
		 * SessionInfo.SessionName);
		 */
		d.setShiping_order_id(UUIDUtils.getUUID());
		Aging aging = OrderInfoServiceImpl.getNewAging(d.getSend_mechanism(),d.getEnd_address(),date);
		if (aging != null) {
			d.setAging_time(aging.getAging_time());
			d.setAging_day(aging.getAging_day());
		}
		if (d.getIs_recept().equals("") || d.getIs_recept() == null) {
			d.setIs_recept("0");
		}
		int truck = csi.saveShipOrder(d);
		if (truck > 0) {
			HistoryUtils.saveHistory(d.getShiping_order_id(),d.getShiping_order_num(),"手动录入","0");
		/*	saveHistory(d.getShiping_order_id(), d.getShiping_order_num(),
					"手动录入");*/
			json.setFlag(true);
			return json;
		} else {
			json.setFlag(false);
			return json;
		}

	}

	/**
	 * 跳转司机 导入页面
	 * 
	 * @return
	 */
	@RequestMapping(params = "method=inputShipOrder")
	public String inputShipOrder() {
		return "driver/InputDriver";
	}

	/**
	 * 运单录入 检查运单号是否存在
	 * 
	 * @param number
	 * @param shipordernum
	 * @return
	 */
	@RequestMapping(params = "method=getNumber")
	public @ResponseBody
	ShippingOrder getShipOrderLength(String number, String shipordernum) {
		ShippingOrder der = csi.getNumber(number, shipordernum);
		return der;
	}

	/**
	 * 重复订单号个数查询
	 * 
	 * @param number
	 * @return
	 */
	@RequestMapping(params = "method=getAorder")
	public @ResponseBody
	String getAorder(String number) {
		int i = csi.getAorder(number);
		Json json = new Json();
		if (i > 0) {
			json.setFlag(false);
		} else {
			json.setFlag(true);
		}
		return json.toString();
	}

	/**
	 * 车牌检索
	 * 
	 * @param number
	 * @return
	 */
	@RequestMapping(params = "method=getPlateNumberLength")
	public @ResponseBody
	String getPlateNumberLength(String number) {
		List<ShippingOrder> list = csi.getPlateNumberLength(number);
		JSONObject jb = null;
		JSONArray jsons = new JSONArray();
		for (ShippingOrder d : list) {
			jb = new JSONObject();

			jb.put("id", d.getShiping_order_id());
			jb.put("name", d.getShiping_order_num());

			jsons.add(jb);
		}
		return jsons.toString();
	}

	/***
	 * 运单录入查询客户信息
	 * 
	 * @param name
	 * @return
	 */
	@RequestMapping(params = "method=getCustomName")
	public @ResponseBody
	String getCustomName(String name) {
		List<Customer> list = csi.getCustomName(name);
		JSONObject jb = null;
		JSONArray jsons = new JSONArray();
		for (Customer d : list) {
			jb = new JSONObject();
			jb.put("id", d.getCustomer_num());
			jb.put("name", d.getCustomer_name());
			jb.put("people", d.getCustomer_people());
			jb.put("tel", d.getCustomer_tel());
			jb.put("customerid", d.getCustomer_id());
			jsons.add(jb);
		}
		return jsons.toString();
	}

	/**
	 * 发货客户检索
	 * 
	 * @param name
	 * @return
	 */
	@RequestMapping(params = "method=getNewCustomName")
	public @ResponseBody
	String getNewCustomName(String name,HttpSession session) {
		User user = (User) session.getAttribute(SessionInfo.SessionName);
		List<Customer> list = csi.getCustomName(name,user);
		JSONObject jb = null;
		JSONArray jsons = new JSONArray();
		JSONArray jsona = new JSONArray();
		for (Customer d : list) {
			jb = new JSONObject();
			jb.put("id", d.getCustomer_id());
			jb.put("text", d.getCustomer_name());
			jb.put("iconCls", "");

			/*
			 * jb.put("people", d.getCustomer_people()); jb.put("tel",
			 * d.getCustomer_tel()); jb.put("customerid", d.getCustomer_id());
			 */
			jsons.add(jb);
		}
		jb.put("id", "aaaa");
		jb.put("text", "全选");
		jb.put("children", jsons);
		jsona.add(jb);
		return jsona.toString();
	}

	/**
	 * 导出信息----没有用
	 * 
	 * @param datas
	 * @param headtitle
	 * @param fieldName
	 * @param response
	 * @param name
	 * @param plate_number
	 * @param type
	 * @param start_date
	 * @param end_date
	 * @return
	 */
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
	 * 协议制作订单查询方法---没有用
	 * 
	 * @param rows
	 * @param page
	 * @param id
	 * @param type
	 * @param start_date
	 * @param end_date
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(params = "method=getShipOrderCA")
	public @ResponseBody
	Map getShipOrderCA(String rows, String page, String id, String name,
			String phone_number, String type, String start_date, String end_date) {
		Integer rows1 = 0;// 当前有几行
		Integer page1 = 1;// 当前有几页
		if (rows != null && !"".equals(rows)) {
			rows1 = Integer.parseInt(rows);
		}
		if (page != null && !"".equals(page)) {
			page1 = Integer.parseInt(page);
		}
		List<ShippingOrder> list = csi.getShipOrderInfoCA((page1 - 1) * rows1,
				rows1, id, name, phone_number, type, start_date, end_date,
				null, null, null, null);

		int count = csi.getShipOrderCA(id, name, phone_number, type,
				start_date, end_date, null, null, null, null);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", count);
		map.put("rows", list);
		return map;
	}

	/**
	 * 查询司机已分配订单页面,已签收的订单不在此显示
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
	 * @param pid
	 * @param shipperorder_id
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(params = "method=getShipOrderOnePage")
	public @ResponseBody
	Map getShipOrderOnePage(String rows, String page, String send_time,
			String shiping_order_num, String send_mechanism,
			String end_address, String custom_name, String receipt_name,
			String receipt_tel, String pid, String shipperorder_id,
			String end_mechanism, HttpSession session) {
		// System.out.println("pid"+pid);
		// type 是否显示签收(0 不显示,1显示)
		String type = "0";
		User user = (User) session.getAttribute(SessionInfo.SessionName);
		Map<String, Object> map = new HashMap<String, Object>();
		if (user == null)
			return map;
		Integer rows1 = 0;// 当前有几行
		Integer page1 = 1;// 当前有几页
		if (rows != null && !"".equals(rows)) {
			rows1 = Integer.parseInt(rows);
		}
		if (page != null && !"".equals(page)) {
			page1 = Integer.parseInt(page);
		}
		if (user.getFlag().equals("3") || user.getFlag().equals("4")) {
			Customer customer = suppliersService.getCustomer(user
					.getCustomer_id());
			send_mechanism = customer == null ? "没有绑定发货客户" : customer
					.getCustomer_name();
		}

		List<ShippingOrder> list = csi.getShipOrderInfoOnePage((page1 - 1)
				* rows1, rows1, send_time, shiping_order_num, send_mechanism,
				end_address, custom_name, receipt_name, receipt_tel, pid,
				shipperorder_id, type, end_mechanism,"");
		int count = csi.getShipOrderOnePage(send_time, shiping_order_num,
				send_mechanism, end_address, custom_name, receipt_name,
				receipt_tel, pid, shipperorder_id, type, end_mechanism,"");

		map.put("total", count);
		map.put("rows", list);
		return map;
	}

	/**
	 * 分配运单删除分配信息
	 * 
	 * @param pid
	 * @param del
	 * @return
	 */
	@RequestMapping(params = "method=deleteShipOrderPage")
	public @ResponseBody
	Json delShipOrderPage(String pid, String[] del) {
		Json json = new Json();
		int truck = csi.deleteShipOrderPage(pid, del);
		if (truck > 0) {
			csi.updateShipperOrderDelete(del);
			new Thread(new FenPeiSaveHistoryThread(del,csi,"运单删除分配","7")).start();
			json.setFlag(true);
			return json;
		}
		json.setFlag(false);
		return json;
	}

	// 协议制作订单查询
	@SuppressWarnings("rawtypes")
	@RequestMapping(params = "method=getShipOrderCAM")
	public @ResponseBody
	Map getShipOrderCAM(String rows, String page, String[] pid) {
		Integer rows1 = 0;// 当前有几行
		Integer page1 = 1;// 当前有几页
		if (rows != null && !"".equals(rows)) {
			rows1 = Integer.parseInt(rows);
		}
		if (page != null && !"".equals(page)) {
			page1 = Integer.parseInt(page);
		}
		List<ShippingOrder> list = csi.getShipOrderCAM((page1 - 1) * rows1,
				rows1, pid.length == 0 ? null : pid);// 当前页
		int count = pid.length;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", count);
		map.put("rows", list);
		return map;
	}

	/**
	 * 协议制作订单查询方法
	 * 
	 * @param rows
	 * @param page
	 * @param id
	 * @param type
	 * @param start_date
	 * @param end_date
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(params = "method=getSignShipOrderCA")
	public @ResponseBody
	Map getSignShipOrderCA(String rows, String page, String id,
			String startDate, String end_date, String ddId, String order_state,
			String pay_type, String perpole, String end_address,
			String send_address, String phone_number) {
		Integer rows1 = 0;// 当前有几行
		Integer page1 = 1;// 当前有几页
		if (rows != null && !"".equals(rows)) {
			rows1 = Integer.parseInt(rows);
		}
		if (page != null && !"".equals(page)) {
			page1 = Integer.parseInt(page);
		}
		List<ShippingOrder> list = csi.getShipOrderInfoCA((page1 - 1) * rows1,
				rows1, id, startDate, end_date, ddId, order_state, pay_type,
				perpole, end_address, send_address, phone_number);

		int count = csi.getShipOrderCA(id, startDate, end_date, ddId,
				order_state, pay_type, perpole, end_address, send_address,
				phone_number);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", count);
		map.put("rows", list);
		return map;
	}

	/**
	 * 运单签收 获取要签收的运单信息
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "method=getUpdateSignShipOrder")
	public @ResponseBody
	ShippingOrder getUpdateSignShipOrder(String id) {
		ShippingOrder der = csi.getUpdateSignShipOrder(id);

		return der;
	}

	/**
	 * 运单签收跳转签收页面
	 * 
	 * @param pid
	 * @param isok
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "method=getEditSignShipOrderPage")
	public String getEditSignShipOrderPage(String pid, String isok,
			HttpServletRequest request) {
		request.setAttribute("flg", pid);
		request.setAttribute("isok", isok);

		return "shipping_order/EditSignShipOrder";
	}

	/**
	 * 运单签收 保存签收运单信息
	 * 
	 * @param sign_order
	 * @param session
	 * @return
	 */
	@RequestMapping(params = "method=saveSignShipOrder")
	public @ResponseBody
	Json saveSignShipOrder(Sign_order sign_order, HttpSession session) {

		sign_order.setSign_id(UUIDUtils.getUUID());
		if (sign_order.getSign_time() == null
				|| "".equals(sign_order.getSign_time())) {
			SimpleDateFormat dateFor = new SimpleDateFormat(
					"yyyy-MM-dd hh:mm:ss");
			sign_order.setSign_time(dateFor.format(new Date()));
		}
		int i = 0;
		Json json = new Json();
		ShippingOrder d = ordersercice.getShipOrderInfos(sign_order
				.getOrder_id());
		String str_num = sign_order.getOrder_number();
		if (str_num != null) {
			if (str_num.indexOf(".53") != -1) {
				interface_56LinkProject(sign_order, d);// 56link接口
				// _56linkproject(sign_order, d);//56link 对接
			}
		}
		i = csi.saveSignShipOrder(sign_order);
		if (i > 0) {
			int ii = 0;
			String filenames = (String) session.getAttribute("filename");
			if (filenames != null) {
				String[] filenNamess = filenames.split(",");
				List<ShipperOrderImg> shiOrdImg = new ArrayList<ShipperOrderImg>();
				for (int j = 0; j < filenNamess.length; j++) {
					ShipperOrderImg sOrdImg = new ShipperOrderImg();
					sOrdImg.setImage_id(UUIDUtils.getUUID());
					sOrdImg.setOrder_id(sign_order.getOrder_id());
					sOrdImg.setImageUrl(filenNamess[j]);
					shiOrdImg.add(sOrdImg);
				}
				boolean b = csi.saveFilename(shiOrdImg);
				if (b) {
					ii = csi.updatestate(sign_order.getOrder_id());
				}
			} else {
				ii = csi.updatestate(sign_order.getOrder_id());
			}
			if (ii > 0) {
				saveHistory(sign_order.getOrder_id(),
						sign_order.getOrder_number(),
						"签收,签收人:" + sign_order.getSign_user());
				session.removeAttribute("filename");
				csi.insertShippingTime(sign_order.getOrder_id());
				json.setFlag(true);
				return json;
			}
		}

		json.setFlag(false);
		return json;
	}

	// 56link对接
	public void interface_56LinkProject(Sign_order sign_order, ShippingOrder d) {
		// zzp签收哦
		// zzp添加
		String str = "";
		Properties prop = new Properties();
		InputStream in = TemporaryCarController.class
				.getResourceAsStream("/../56linkedInterface.properties");
		try {
			prop.load(in);
			Iterator<String> it = prop.stringPropertyNames().iterator();
			while (it.hasNext()) {
				String key = it.next();
				if (key.equals("56linkedURL")) {
					str = prop.getProperty(key);
				}

			}
			in.close();

		} catch (Exception e) {
			System.out.println(e.getMessage());
			// returnjson.put("errorMsg", "出错了");
		} // /加载属性列表
			// 运单号、车牌号、
		/*
		 * ShippingOrder d = ordersercice.getShipOrderInfos(sign_order
		 * .getOrder_id());
		 */
		String ddate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(Calendar.getInstance().getTime());

		String agtime = ddate;
		if (!("").equals(d.getReceivetime()) && d.getReceivetime() != null) {
			agtime = d.getReceivetime().substring(0, 10) + " "
					+ d.getAging_time();
			System.out.println(agtime);
		}
		String goid = d.getShiping_order_goid();
		String mechanism = d.getSend_mechanism();
		int i56 = csi.get56LinkProject(goid, mechanism);
		if (i56 == 0) {
			testPost(str, goid, sign_order.getSign_remarks(), ddate,
					sign_order.getSign_user(), agtime);
		}

	}

	public void  saveHistory(String ids, String num, String message) {
		List<OrderHistory> order = new ArrayList<OrderHistory>();
		OrderHistory h = new OrderHistory();
		h.setOrder_history_id(UUIDUtils.getUUID());
		h.setOrders_id(ids);
		h.setOrder_arrive_time(DateFormat.getDateTimeInstance().format(
				new Date()));
		h.setGoods_arrive_remakes(h.getOrder_arrive_time() + "---货运编号为" + num
				+ "\t" + message);
		order.add(h);
		csi.saveOrderHistory(order);
	}

	@RequestMapping(params = "method=getQrcode")
	public void getQrcode(HttpServletResponse response, String orderId) {
		String content = orderId;
		QRCodeEncoderHandler handler = new QRCodeEncoderHandler();
		BufferedImage bi = handler.encoderQRCode(content, 3);
		try {
			ImageIO.write(bi, "png", response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@RequestMapping(params = "method=getdbrcode")
	public void getdbrcode(HttpServletResponse response, String orderId) {
		String format = MimeTypes.MIME_JPEG;// MimeTypes.MIME_JPEG

		String text = orderId;

		ByteArrayOutputStream bout = null;
		try {
			BarcodeUtil util = BarcodeUtil.getInstance();

			Configuration cfg = buildCfg();
			BarcodeGenerator gen = util.createBarcodeGenerator(cfg);

			bout = new ByteArrayOutputStream(4096);

			int dpi = 250; // 分辨率
			int orientation = 0;

			BitmapCanvasProvider bitmap = new BitmapCanvasProvider(bout,
					format, dpi, BufferedImage.TYPE_BYTE_BINARY, false,
					orientation);

			gen.generateBarcode(bitmap, text);
			bitmap.finish();
			response.setContentType(format);
			response.setContentLength(bout.size());
			response.getOutputStream().write(bout.toByteArray());
			response.getOutputStream().flush();

		} catch (Exception e) {
			// log.error("Error while generating barcode", e);
		} finally {
			if (bout != null) {
				try {
					bout.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}

	public static Configuration buildCfg() {
		DefaultConfiguration cfg = new DefaultConfiguration("barcode");
		// Get type

		String type = "code128";
		DefaultConfiguration child = new DefaultConfiguration(type);
		cfg.addChild(child);
		return cfg;
	}

	@SuppressWarnings("unused")
	private String createXML(List<ShippingOrder> list) {
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("xml");
		Iterator<ShippingOrder> it = list.iterator();
		while (it.hasNext()) {
			ShippingOrder so = it.next();
			Element edept = root.addElement("Detail");
			edept.addElement("shiping_order_num").addText(
					so.getShiping_order_num() + "");
			edept.addElement("receipt").addText("123");
			edept.addElement("receipt_tel").addText("123");
			edept.addElement("receipt_address").addText("123");

		}
		String xmlString = document.asXML();
		return xmlString;
	}

	/**
	 * 运单签收查运单历史记录
	 * 
	 * @param orders_id
	 * @param pid
	 * @return
	 */
	@RequestMapping(params = "method=getShowOrder")
	public @ResponseBody
	List<OrderHistory> getShowOrder(String orders_id, String pid) {

		List<OrderHistory> historys = csi.getShowOrder(orders_id);

		return historys;
	}

	/**
	 * 客户结算页面按照客户名称查询订单运单信息
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
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(params = "method=getShipOrderBySendMechanism")
	public @ResponseBody
	Map getShipOrderBySendMechanism(String rows, String page, String send_time,
			String end_time, String custom_name, HttpSession session) {

		Integer rows1 = 0;// 当前有几行
		Integer page1 = 1;// 当前有几页
		if (rows != null && !"".equals(rows)) {
			rows1 = Integer.parseInt(rows);
		}
		if (page != null && !"".equals(page)) {
			page1 = Integer.parseInt(page);
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, 0);
		c.set(Calendar.DAY_OF_MONTH, 1);
		if (send_time == null || "".equals(send_time)) {
			// Date d = new Date();
			send_time = sdf.format(c.getTime());
		}
		Calendar ca = Calendar.getInstance();
		ca.set(Calendar.DAY_OF_MONTH,
				ca.getActualMaximum(Calendar.DAY_OF_MONTH));
		if (end_time == null || "".equals(end_time)) {
			// Date d = new Date();
			end_time = sdf.format(ca.getTime());
		}
		User user = (User) session.getAttribute(SessionInfo.SessionName);
		List<ShippingOrder> list = csi.getShipOrderBySendMechanism((page1 - 1)
				* rows1, rows1, send_time, end_time, custom_name, user);
		int count = csi.getShipOrderBySendMechanismTotal(send_time, end_time,
				custom_name, user);

		for (ShippingOrder shippingOrder : list) {
			if (shippingOrder.getCustomer_settlement_state().equals("0")) {
				shippingOrder.setCustomer_settlement_state("1");
			} else {
				shippingOrder.setCustomer_settlement_state("0");
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", count);
		map.put("rows", list);
		return map;
	}

	/**
	 * 客户结算跳转页面
	 * 
	 * @param send_time
	 * @param end_time
	 * @param custom_code
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "method=ShipOrderBySendMechanism")
	public String ShipOrderBySendMechanism(String send_time, String end_time,
			String custom_code, HttpServletRequest request) {
		request.setAttribute("send_time", send_time);
		request.setAttribute("end_time", end_time);
		try {
			String custom_code1 = new String(
					custom_code.getBytes("iso-8859-1"), "utf-8");
			request.setAttribute("custom_code", custom_code1);

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return "settlement/detailCustomerbilling";
	}

	/**
	 * 客户结算详细页面
	 * 
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(params = "method=getDtailCustomerbilling")
	public @ResponseBody
	Map getDtailCustomerbilling(String rows, String page, String send_time,
			String shiping_order_num, String send_mechanism,
			String end_address, String custom_name, String receipt_name,
			String receipt_tel, String send_time1, String end_time,
			String custom_code, String shipperorder_id,
			String customer_settlement_state) {
		String custom_code1 = "";
		try {
			custom_code1 = new String(custom_code.getBytes("iso-8859-1"),
					"utf-8");
			// request.setAttribute("custom_code", custom_code1);
			// System.out.println(custom_code1);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		Integer rows1 = 0;// 当前有几行
		Integer page1 = 1;// 当前有几页
		if (rows != null && !"".equals(rows)) {
			rows1 = Integer.parseInt(rows);
		}
		if (page != null && !"".equals(page)) {
			page1 = Integer.parseInt(page);
		}
		List<ShippingOrder> list = csi.getDtailCustomerbilling((page1 - 1)
				* rows1, rows1, send_time, shiping_order_num, send_mechanism,
				end_address, custom_name, receipt_name, receipt_tel,
				send_time1, end_time, custom_code1, shipperorder_id,
				customer_settlement_state);
		int count = csi.getDtailCustomerbillingTotal(send_time,
				shiping_order_num, send_mechanism, end_address, custom_name,
				receipt_name, receipt_tel, send_time1, end_time, custom_code1,
				shipperorder_id, customer_settlement_state);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", count);
		map.put("rows", list);
		return map;
	}

	/**
	 * 上传文件
	 * 
	 * @param file
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "method=up")
	public void upload(@RequestParam("file") MultipartFile file,
			HttpServletRequest request, HttpServletResponse response) {

		try {
			super.upload(file, "singshiporder", request);
			/*
			 * response.getWriter().print(super.getFileName());
			 * filename+=super.getFileName();
			 */
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 运单签收跳转显示签收信息页面
	 * 
	 * @param pid
	 * @param isok
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "method=showSignShipOrder")
	public String showSignShipOrder(String pid, String isok,
			HttpServletRequest request) {
		request.setAttribute("flg", pid);
		request.setAttribute("isok", isok);
		return "shipping_order/ShowSignMessage";
	}

	/**
	 * 运单信息导出
	 * 
	 * @param headtitle
	 * @param fieldName
	 * @param dataIds
	 * @param response
	 * @param session
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
	@RequestMapping(params = "method=outSignShipOrderFile")
	public @ResponseBody
	String outSignShipOrderFile(String[] headtitle, String[] fieldName,
			String[] dataIds, HttpServletResponse response,
			HttpSession session, String name, String start_date,
			String end_date, String shipping_order_state, String custom_name,
			String receipt, String address, String shipperorder_id) {
		String filename = "运单签收信息导出";
		if (dataIds.length == 0) {
			dataIds = null;
		}
		User user = (User) session.getAttribute(SessionInfo.SessionName);
		List<ShippingOrder> list = csi.outSignShipOrderFile(user, name,
				start_date, end_date, shipping_order_state, custom_name,
				receipt, address, shipperorder_id, dataIds);
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
			ExportUtils.outputHeaders(headtitle, workbook, "运单签收信息导出");
			// 调用工具类生成内容
			ExportUtils.outputColums(fieldName, list, workbook, 1, "运单签收信息导出");
			workbook.write(ouputStream);
			ouputStream.flush();
			ouputStream.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// 跳转订单信息展示页面并且获取id 返回到页面
	@RequestMapping(params = "method=quickSearch")
	public @ResponseBody
	String quickSearch(String shiping_order_goid, String shiping_order_num,
			HttpSession session) {
		User user = (User) session.getAttribute(SessionInfo.SessionName);
		List<ShippingOrder> list = csi.getShipOrderInfo(0, 5, "",
				shiping_order_num, "", "", "", "", "", "", "",
				shiping_order_goid, "", user);

		JSONObject jb = null;
		JSONArray jsons = new JSONArray();
		for (ShippingOrder d : list) {
			jb = new JSONObject();
			jb.put("id", d.getShiping_order_id());
			jb.put("shiping_order_goid", d.getShiping_order_goid());
			jb.put("shiping_order_num", d.getShiping_order_num());
			jb.put("shiping_order_state", d.getShipping_order_state());
			jb.put("sign_time", d.getSign_time());
			jsons.add(jb);
		}
		return jsons.toString();
	}

	/**
	 * 司机管理获取要分配运单信息
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
	 * @param shipperorder_id
	 * @param request
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(params = "method=getShipOrderFenpei")
	public @ResponseBody
	Map getShipOrderFenpei(String rows, String page, String send_time,
			String shiping_order_num, String send_mechanism,
			String end_address, String custom_name, String receipt_name,
			String receipt_tel, String shipperorder_id, String end_mechanism,
			HttpServletRequest request) {
		Integer rows1 = 0;// 当前有几行
		Integer page1 = 1;// 当前有几页
		if (rows != null && !"".equals(rows)) {
			rows1 = Integer.parseInt(rows);
		}
		if (page != null && !"".equals(page)) {
			page1 = Integer.parseInt(page);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		User user = (User) request.getSession().getAttribute(
				SessionInfo.SessionName);

		List<ShippingOrder> list = csi.getAllShipOrderInfoFenPei((page1 - 1)
				* rows1, rows1, send_time, shiping_order_num, send_mechanism,
				end_address, custom_name, receipt_name, receipt_tel,
				shipperorder_id, end_mechanism, user);
		int count = csi
				.getAllShipOrderInfoFenPeiCount(send_time, shiping_order_num,
						send_mechanism, end_address, custom_name, receipt_name,
						receipt_tel, shipperorder_id, end_mechanism, user);
		map.put("total", count);
		map.put("rows", list);
		return map;
	}

	/**
	 * 运单到达功能--目前没有用
	 * 
	 * @param del
	 * @param num
	 * @param custom_id
	 * @return
	 */
	@RequestMapping(params = "method=isArrive")
	public @ResponseBody
	Json isArrive(String[] del, String[] num, String custom_id) {
		List<OrderHistory> order = new ArrayList<OrderHistory>();
		for (int i = 0; i < num.length; i++) {
			OrderHistory h = new OrderHistory();
			h.setOrder_history_id(UUIDUtils.getUUID());
			h.setOrders_id(del[i]);
			h.setOrder_arrive_time(DateFormat.getDateTimeInstance().format(
					new Date()));
			h.setGoods_arrive_remakes(h.getOrder_arrive_time() + "---货运编号为"
					+ num[i] + "已经确认到达！");
			order.add(h);
		}
		int count = csi.saveOrderHistory(order);
		Json json = new Json();
		int numCutom = csi.isArriveCoustom(custom_id);
		int truck = csi.isArrive(del);
		if (truck > 0 && count > 0 && numCutom > 0) {
			json.setFlag(true);
			return json;
		}
		json.setFlag(false);
		return json;
	}

	/**
	 * 取消到达
	 * 
	 * @param del
	 * @return
	 */
	@RequestMapping(params = "method=isNotArrive")
	public @ResponseBody
	Json isNotArrive(String[] del) {
		Json json = new Json();
		int num = csi.deleteOrderHistory(del);
		int truck = csi.isNotArrive(del);
		if (truck > 0 && num > 0) {
			json.setFlag(true);
			return json;
		}
		json.setFlag(false);
		return json;
	}

	/**
	 * 运单录入订单删除
	 * 
	 * @param del
	 * @return
	 */
	@RequestMapping(params = "method=deleteShipOrder")
	public @ResponseBody
	Json delShipOrder(String[] del) {
		Json json = new Json();
		int truck = csi.deleteShipOrder(del);
		csi.deleteDriverToOrder(del);
		if (truck > 0) {
			json.setFlag(true);
			return json;
		}
		json.setFlag(false);
		return json;
	}

	/**
	 * 运单录入修改运单跳转页面
	 * 
	 * @param pid
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "method=getEditShipOrderPage")
	public String getEditShipOrderPage(String pid, HttpServletRequest request) {
		request.setAttribute("flg", pid);
		return "shipping_order/EditShipOrder";
	}

	/**
	 * 运单录入修改运单信息 调整终到位置时,订单状态改变且自动删除原来分配给供应商的订单 并且生成了新的历史记录
	 * 
	 * @param d
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "method=updateShipOrder")
	public @ResponseBody
	Json updateShipOrder(ShippingOrder d, HttpServletRequest request) {
		Json json = new Json();
		// Integer j = 1;
		Integer k = 0;
		int i = 0;
		ShippingOrder sh = csi.getUpdateShipOrder(d.getShiping_order_id());
		if (!sh.getEnd_address().equals(d.getEnd_address())) {
			d.setShipping_order_state(k);
			Aging aging = ordersercice.getNewAging(sh.getSend_mechanism(),
					d.getEnd_address(), sh.getUpdatetime());
			if (aging != null) {
				d.setAging_day(aging.getAging_day());
				d.setAging_time(aging.getAging_time());
			}
			i = csi.updateShipOrders(d);
			csi.deleteShipOrders(d.getShiping_order_id());
			this.saveHistory(d.getShiping_order_id(), d.getShiping_order_num(),
					"修改终到位置");
			if (i > 0) {
				json.setFlag(true);
				return json;
			}
			json.setFlag(false);
		} else {
			i = csi.updateShipOrder(d);
			if (i > 0) {
				json.setFlag(true);
				return json;
			}

			json.setFlag(false);
		}
		return json;
	}

	/**
	 * 运单录入查看运单详细信息跳转页面
	 * 
	 * @param pid
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "method=getShipOrderPage")
	public String getShipOrderPage(String pid, HttpServletRequest request) {
		request.setAttribute("flg", pid);
		return "shipping_order/SearchShipOrder";
	}

	/**
	 * 运单录入获取运单信息
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "method=getUpdateShipOrder")
	public @ResponseBody
	ShippingOrder getUpdateShipOrder(String id, HttpSession session) {
		ShippingOrder der = csi.getUpdateShipOrder(id);
		return der;
	}

	/**
	 * 运单信息全部导出
	 * 
	 * @param headtitle
	 * @param fieldName
	 * @param response
	 * @param session
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
	@RequestMapping(params = "method=allMessageOutPut")
	public @ResponseBody
	String allMessageOutPut(String[] dataIds, String[] headtitle,
			String[] fieldName, HttpServletResponse response,
			HttpSession session, String send_time, String shiping_order_num,
			String send_mechanism, String end_address, String custom_name,
			String receipt_name, String receipt_tel, String topordernumber,
			String downordernumber, String shipperorder_id, String end_time) {
		String filename = "订单信息导出";
		List<ShippingOrder> list = new ArrayList<ShippingOrder>();
		if (dataIds.length > 0) {
			list = csi.someMessageOutPut(dataIds);
		} else {
			User user = (User) session.getAttribute(SessionInfo.SessionName);
			int count = csi.getShipOrder(send_time, shiping_order_num,
					send_mechanism, end_address, custom_name, receipt_name,
					receipt_tel, topordernumber, downordernumber,
					shipperorder_id, end_time, user);
			list = csi.getShipOrderInfo(0, count, send_time, shiping_order_num,
					send_mechanism, end_address, custom_name, receipt_name,
					receipt_tel, topordernumber, downordernumber,
					shipperorder_id, end_time, user);
		}

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
	 * 运单录入勾选信息导出
	 * 
	 * @param dataIds
	 * @param headtitle
	 * @param fieldName
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "method=someMessageOutPut")
	public @ResponseBody
	String someMessageOutPut(String[] dataIds, String[] headtitle,
			String[] fieldName, HttpServletResponse response) {
		String filename = "订单信息导出";
		List<ShippingOrder> list = csi.someMessageOutPut(dataIds);

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
	 * 运单录入,全部删除
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
	@RequestMapping(params = "method=deleteAllMessage")
	public @ResponseBody
	Json deleteAllMessage(String send_time, String shiping_order_num,
			String send_mechanism, String end_address, String custom_name,
			String receipt_name, String receipt_tel, String topordernumber,
			String downordernumber, String end_time) {
		Json json = new Json();
		int truck = csi.deleteAllMessage(send_time, shiping_order_num,
				send_mechanism, end_address, custom_name, receipt_name,
				receipt_tel, topordernumber, downordernumber, end_time);
		if (truck > 0) {
			json.setFlag(true);
			return json;
		}
		json.setFlag(false);
		return json;

	}

	/**
	 * 运单签收跳转运单签收页面
	 * 
	 * @return
	 */
	@RequestMapping(params = "method=getSignShipOrderInfo")
	public String getSignShipOrderInfo(String menu_id, String order,
			HttpServletRequest request) {
		request.setAttribute("order", order);
		return "shipping_order/signShippingOrder";
	}

	/**
	 * 新增跳已签收的页面
	 */

	@RequestMapping(params = "method=getSignShipOrderOld")
	public String getSignShipOrderOld() {
		return "shipping_order/signShippingOrderOld";
	}

	/**
	 * 运单签收获取签收订单信息
	 * 
	 * @param rows
	 * @param page
	 * @param name
	 * @param start_date
	 * @param end_date
	 * @param session
	 * @param shipping_order_state
	 * @param custom_name
	 * @param receipt
	 * @param address
	 * @param shipperorder_id
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(params = "method=getSignShipOrder")
	public @ResponseBody
	Map getSignShipOrder(String rows, String page, String name,
			String start_date, String end_date, HttpSession session,
			String shipping_order_state, String custom_name, String receipt,
			String address, String shipperorder_id, String startSignTime,
			String endSignTime) {
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
		List<ShippingOrder> list = csi.getSignShipOrder((page1 - 1) * rows1,
				rows1, name, start_date, end_date, shipping_order_state,
				custom_name, receipt, address, shipperorder_id, startSignTime,
				endSignTime, user);
		int count = csi.getSignShipOrdercount(name, start_date, end_date,
				shipping_order_state, custom_name, receipt, address,
				shipperorder_id, startSignTime, endSignTime, user);
		map.put("total", count);
		map.put("rows", list);
		return map;
	}

	/**
	 * 新增运单签收获取已签收的订单信息
	 * 
	 * @param rows
	 * @param page
	 * @param name
	 * @param start_date
	 * @param end_date
	 * @param session
	 * @param shipping_order_state
	 * @param custom_name
	 * @param receipt
	 * @param address
	 * @param shipperorder_id
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(params = "method=getShipOrderOld")
	public @ResponseBody
	Map getShipOrderOld(String rows, String page, String name,
			String start_date, String end_date, HttpSession session,
			String shipping_order_state, String custom_name, String receipt,
			String address, String shipperorder_id) {
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
		List<ShippingOrder> list = csi.getShipOrderOld((page1 - 1) * rows1,
				rows1, name, start_date, end_date, shipping_order_state,
				custom_name, receipt, address, shipperorder_id, user);
		int count = csi.getShipOrderOldcount(name, start_date, end_date,
				shipping_order_state, custom_name, receipt, address,
				shipperorder_id, user);
		map.put("total", count);
		map.put("rows", list);
		return map;
	}

	/**
	 * 运单签收查看图片信息
	 * 
	 * @param iurl
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "method=getimgurl")
	public String getimgurl(String iurl, HttpServletRequest request) {
		List<ShipperOrderImg> listOrderImg = csi.getSingShiperOrderImg(iurl);
		if (listOrderImg.size() == 0) {
			request.setAttribute("iurl", "");
		}
		request.setAttribute("iurl", listOrderImg);

		return "shipping_order/orderImg";
	}

	/**
	 * 发货客户检索
	 * 
	 * @param name
	 * @return
	 */
	@RequestMapping(params = "method=getNoveltCustomName")
	public @ResponseBody
	String getNoveltCustomName(String name) {
		List<Customer> list = csi.getCustomName(name);
		ComboTreeModel treeModel = new ComboTreeModel();
		List<ComboTreeModel> chlidrenlist = new ArrayList<ComboTreeModel>();
		treeModel.setId("1");
		treeModel.setText("全选");
		treeModel.setIconCls("");
		for (Customer d : list) {
			ComboTreeModel children = new ComboTreeModel();
			children.setId(d.getCustomer_id());
			children.setText(d.getCustomer_name());
			children.setIconCls("");
			chlidrenlist.add(children);

		}
		treeModel.setChildren(chlidrenlist);
		String jsons = JSONArray.fromObject(treeModel).toString();
		return jsons;
	}

	/**
	 * 修改签收备注信息
	 * @param orderId
	 * @param remarks
	 * @return
	 */
	@RequestMapping(params = "method=updateOrderSignRemarks")
	public @ResponseBody
	JSONObject updateOrderSignRemarks(String orderId,String remarks,String shiping_order_num) {
		JSONObject json = new JSONObject();
		int i = csi.updateOrderSignRemarks(orderId,remarks);
		int j = csi.updateOrderShipperTimeRemarks(orderId,remarks);
		HistoryUtils.saveHistory(orderId,shiping_order_num,"补充签收备注");
		boolean b = i>0&&j>0;
		json.put("success",b);
		return json;
	}

	/**
	 * 更新 业务状态
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "method=updateBusinessType")
	@ResponseBody
	public JSONObject updateBusinessType(String[] ids){
		JSONObject json = new JSONObject();
		List<String> list;
		if (ids!=null&&ids.length>0){
			list =Arrays.asList(ids);
		}else{
			list =new ArrayList<String>();
		}
		int i  = csi.updateShippingOrderBusinessType(list,2);
		json.put("success",i>0);
		return json;
	}



	// 56lineked推送签收信息
	void testPost(String urlStr, String ordernum, String bz, String singtime,
			String singname, String agtime) {
		try {
			URL url = new URL(urlStr);
			URLConnection con = url.openConnection();
			con.setDoOutput(true);
			con.setRequestProperty("Pragma:", "no-cache");
			con.setRequestProperty("Cache-Control", "no-cache");
			con.setRequestProperty("Content-Type", "text/xml");
			con.setDoOutput(true);
			con.setDoInput(true);
			OutputStreamWriter out = new OutputStreamWriter(
					con.getOutputStream(), "utf-8");
			String xmlInfo = getXmlInfo(ordernum, bz, singtime, singname,
					agtime);
			System.out.println("urlStr=" + urlStr);
			System.out.println("xmlInfo=" + xmlInfo);
			out.write(xmlInfo);
			out.flush();
			out.close();
			BufferedReader br = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String line = "";
			for (line = br.readLine(); line != null; line = br.readLine()) {
				System.out.println(new String(line.getBytes("gbk"), "UTF-8"));
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String getXmlInfo(String ordernum, String bz, String singtime,
			String singname, String agtime) {
		String ddate = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar
				.getInstance().getTime());
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version='1.0' encoding='UTF-8'?>");
		sb.append("<WLLINKED>");
		sb.append("	<HEAD>");
		sb.append("    <TYPE>ORDER_SIGN_SERVICE</TYPE>");
		sb.append("    <SENDER>JY</SENDER>");
		sb.append("    <CODE>61457</CODE>");
		sb.append("    <DATE>" + ddate + "</DATE>");
		sb.append("    <BUSICODE>" + UUIDUtils.getUUID() + "</BUSICODE>");
		sb.append("	</HEAD>");
		sb.append("<XML_DATA>");
		sb.append("		<CECNO>承运商单号（56系统单号）</CECNO>");
		sb.append(" 	<ECNO>" + ddate + "</ECNO>");
		sb.append(" 	<LEGNO>" + ordernum + "</LEGNO>");
		sb.append(" 	<VEHICLENO>1</VEHICLENO>");
		sb.append("  	<STATUS>签收</STATUS>"); // 状态填写签收
		sb.append("  	<DAMAGE_QUANTITY>0</DAMAGE_QUANTITY>");
		sb.append(" 	<POOR_QUANTITY>0</POOR_QUANTITY>");
		sb.append(" 	<REMARK>" + bz + "</REMARK>");
		sb.append("		<PLANARRIVETIME>" + agtime + "</PLANARRIVETIME>"); // 接单时间
		sb.append("		<ARRIVERDTIME>" + singtime + "</ARRIVERDTIME>");
		sb.append("		<SIGNPERSON>" + singname + "</SIGNPERSON>");
		sb.append("		<IMG_URL> </IMG_URL>");
		sb.append("		<SIGNTIME>" + singtime + "</SIGNTIME>");
		sb.append("	</XML_DATA>");
		sb.append("</WLLINKED>");

		return sb.toString();
	}
}
