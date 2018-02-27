package com.jy.action;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.jy.common.DeliveryExcelInfo;
import com.jy.common.ExportUtils;
import com.jy.common.OrderExcelForPOI;
import com.jy.common.UUIDUtils;
import com.jy.dao.Json;
import com.jy.model.OrderCustom;
import com.jy.model.PositionInfo;
import com.jy.model.ShipperOrderImg;
import com.jy.model.ShippingOrder;
import com.jy.service.OrderInfoService;
import com.jy.service.ShippingOrderInfoService;

/*
 * 货运订单管理
 * */
@Controller
@RequestMapping(value = "/orderC.do")
public class OrderInfoController {
	@Resource
	private ShippingOrderInfoService soi;
	@Resource
	private OrderInfoService csi;
	private String customid;
	@Resource
	private ShippingOrderInfoService coi;

	@RequestMapping(params = "method=orderCustomInfo")
	public String getorderCustomInfoMan() {
		return "order/orderCustomInfo";
	}

	// 查看订单
	@RequestMapping(params = "method=getShipOrderInfo")
	public String getShipOrderInfoMan(String pid,String order, HttpServletRequest request) {
		request.setAttribute("flg", pid);
		request.setAttribute("order", order);
		return "shipping_order/shippingOrderInfo";
	}

	// 查看订单
	@RequestMapping(params = "method=getShipOrderInfoPage")
	public String getShipOrderInfoPage(String pid, HttpServletRequest request) {
		request.setAttribute("flg", pid);
		return "shipping_order/shippingOrderInfoPage";
	}

	/**
	 * 跳转司机分配运单信息
	 * @param pid
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "method=getShipOrderInfoPageTwo")
	public String getShipOrderInfoPageTwo(String pid,String equipmentNum,String userName, HttpServletRequest request) {
	
		try {
			equipmentNum = URLDecoder.decode(equipmentNum,"utf-8");
			userName =URLDecoder.decode(userName,"utf-8");
			request.setAttribute("flg", pid);
			request.setAttribute("equipmentNum", equipmentNum);
			request.setAttribute("userName", userName);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return "shipping_order/shippingOrderInfoPageTwo";
	}

	// 跳转编辑页面并且获取合同的id 返回到页面
	@RequestMapping(params = "method=getEditorderCustom")
	public String getEditShipOrderPage(String pid, HttpServletRequest request) {
		request.setAttribute("flg", pid);
		return "order/EditorderCustom";
	}

	// 查询是否有该订单数量
	// @RequestMapping(params = "method=orderCustomMsg")
	/*
	 * public @ResponseBody Json getShipOrder(String pid, HttpServletRequest
	 * request) { int i=csi.getAorder(pid); Json json=new Json(); if (i == 1) {
	 * json.setFlag(true); }else{ json.setFlag(false); } return json; }
	 */
	// 查询页面订单信息展示,加了查询到的图片内容
	@RequestMapping(params = "method=getShipOrderPage")
	public String getShipOrderPage(String pid, String shiping_order_num,
			 HttpServletRequest request) {

		ShippingOrder d = csi.getShipOrderMsg(pid);
		if (d.getShiping_order_id() != null || d.getShiping_order_id() != "") {
			if(d.getShipping_order_state()==0){
				d.setOrder_state("已发运");
			}else if(d.getShipping_order_state()==1){
				d.setOrder_state("已分配");
			}else if(d.getShipping_order_state()==2){
				d.setOrder_state("已接单");
			}else if(d.getShipping_order_state()==3){
				d.setOrder_state("电子围栏签收");
			}else if(d.getShipping_order_state()==4){
				d.setOrder_state("已签收");
		    }else if(d.getShipping_order_state()==5){
		    	d.setOrder_state("已返回");
		    }else if(d.getShipping_order_state()==6){
		    	d.setOrder_state("已收到");
		    }	
		}
		/*
		 * if(d.getSend_type()==0){ d.setSendtype("自提"); }else
		 * if(d.getSend_type()==1){ d.setSendtype("送货"); }
		 * 
		 * if(d.getPay_type()==0){ d.setPaytype("回付");
		 * d.setHf_pay(d.getTransport_pay()); }else if(d.getPay_type()==1){
		 * d.setPaytype("提付"); }else if(d.getPay_type()==2){ d.setPaytype("现付");
		 * d.setXf_pay(d.getTransport_pay()); }else if(d.getPay_type()==3){
		 * d.setPaytype("货到前付"); d.setHdqf_pay(d.getTransport_pay()); }
		 * d.setOverGoodsNum(d.getGoods_num()-d.getPractiacl_num());
		 * //实收总运费计算getPaid_chayi getChayi_daifukuan1 paid1 代收 paid 提货款
		 * paid_chayi
		 * if(d.getPaid_chayi()!=""&&d.getPaid_chayi()!=null&&d.getChayi_daifukuan1
		 * ()!=""&&d.getChayi_daifukuan1()!=null){ d.setTransport_pay1(String
		 * .valueOf(Double.parseDouble(d.getPaid_chayi()))+"0");
		 * d.setSum_pay(String
		 * .valueOf(Double.parseDouble(d.getPaid_chayi())+Double
		 * .parseDouble(d.getChayi_daifukuan1()))+"0"); }else
		 * if(d.getPaid_chayi(
		 * )!=""&&d.getPaid_chayi()!=null&&(d.getChayi_daifukuan1
		 * ()==""||d.getChayi_daifukuan1()==null)){
		 * d.setSum_pay(String.valueOf(Double
		 * .parseDouble(d.getPaid_chayi()))+"0");
		 * d.setTransport_pay1(String.valueOf
		 * (Double.parseDouble(d.getPaid_chayi()))+"0"); }else
		 * if(d.getChayi_daifukuan1
		 * ()!=""&&d.getChayi_daifukuan1()!=null&&(d.getPaid_chayi
		 * ()==""||d.getPaid_chayi()==null)){ d.setTransport_pay1("0.00");
		 * d.setSum_pay
		 * (String.valueOf(Double.parseDouble(d.getChayi_daifukuan1()))+"0");
		 * }else{
		 * if(d.getPaid()!=""&&d.getPaid()!=null&&d.getPaid1()!=""&&d.getPaid1
		 * ()!=null){ d.setTransport_pay1(d.getPaid());
		 * d.setSum_pay(String.valueOf
		 * (Integer.parseInt(d.getPaid().substring(0,d
		 * .getPaid().length()-3))+Integer.parseInt(d.getPaid1().substring(0,
		 * d.getPaid1().length()-3)))+".00"); }else
		 * if(d.getPaid()!=""&&d.getPaid
		 * ()!=null&&(d.getPaid1()==""||d.getPaid1()==null)){
		 * d.setTransport_pay1(d.getPaid());
		 * d.setSum_pay(String.valueOf(Integer.parseInt(d.getPaid().substring(0,
		 * d.getPaid().length()-3)))+".00"); }else
		 * if(d.getPaid1()!=""&&d.getPaid1
		 * ()!=null&&(d.getPaid()==""||d.getPaid()==null)){
		 * d.setTransport_pay1("0.00");
		 * d.setSum_pay(String.valueOf(Integer.parseInt
		 * (d.getPaid1().substring(0, d.getPaid1().length()-3)))+".00"); } }
		 * 
		 * System.out.println(d.getPaid()+"paid");
		 * System.out.println(d.getPaid1()+"paid1"); //运费核销 write_off5
		 * if(d.getWrite_off5().equals("0")){ d.setWrite_off_t("打卡"); }else
		 * if(d.getWrite_off5().equals("1")){ d.setWrite_off_t("现金"); }else
		 * {//状态为3 是未核销 d.setWrite_off_t("未核销"); } //车辆装卸费核销 write_off4
		 * write_off_z if(d.getAdorn_fee2()!=null&&d.getAdorn_fee2()!=""){
		 * if(d.getWrite_off4()!=null&&d.getWrite_off4()!=""){
		 * if(d.getWrite_off4().equals("0")){ d.setWrite_off_z("打卡"); }else
		 * if(d.getWrite_off4().equals("1")){ d.setWrite_off_z("现金"); }else
		 * {//状态为3 是未核销 d.setWrite_off_z("未核销"); } }
		 * 
		 * }
		 * 
		 * //中转费核销 write_off write_off_c 中转费费核销文字
		 * if(d.getChange_pay()!=null&&d.getChange_pay()!=""){
		 * System.out.println(d.getChange_pay());
		 * System.out.println(d.getWrite_off());
		 * if(d.getWrite_off().equals("0")){ d.setWrite_off_c("打卡"); }else
		 * if(d.getWrite_off().equals("1")){ d.setWrite_off_c("现金"); }else
		 * if(d.getWrite_off().equals("3")){ System.out.println("22weihexioa");
		 * d.setWrite_off_c("未核销"); } }else { System.out.println("3333333");
		 * d.setWrite_off_c(""); }
		 * 
		 * //落地费核销 write_off3 write_off3_l
		 * if(d.getBorn_fee()!=null&&d.getBorn_fee()!=""){
		 * if(d.getWrite_off3().equals("0")){ d.setWrite_off3_l("打卡"); }else
		 * if(d.getWrite_off3().equals("1")){ d.setWrite_off3_l("现金"); }else
		 * {//状态为3 是未核销 d.setWrite_off3_l("未核销"); } }
		 */
		List<ShipperOrderImg> listOrderImg = soi.getSingShiperOrderImg(pid);
		if (listOrderImg.size() == 0) {
			request.setAttribute("iurl", "");
		}
		request.setAttribute("pid", listOrderImg);
		request.setAttribute("sp", d);
		request.setAttribute("numb", shiping_order_num);
		return "layout/shipOrderMsg";
	}

	@RequestMapping(params = "method=getExcel")
	public String getExcel(String pid, HttpServletRequest request) {
		return "order/excel";
	}

	// 导入
	@RequestMapping(params = "method=imp")
	public String goimp(String pid, HttpServletRequest request) {
		return "order/maplinimp";
	}

	// 做导入前的上传处理//////////////
	@RequestMapping(params = "method=startimplinplanexcle")
	public String startimplinplanexcle(HttpServletRequest request,
			@RequestParam(value = "files") MultipartFile files)
			throws IllegalStateException, IOException {
		String path = request.getSession().getServletContext()
				.getRealPath("WebRoot/orderFiles/");// 文件路径
		// System.out.println(path);
		File filez = new File(path);
		if (!filez.exists()) {
			filez.mkdirs();
		}
		MultipartFile myfile = files;
		String filename = myfile.getOriginalFilename();
		String prefix = filename.substring(filename.lastIndexOf("."));
		File newfile = filez.createTempFile("shipOrder", prefix, filez);
		// System.out.println(newfile);
		String filesname = newfile.toString();
		filesname = filesname.substring(filesname.lastIndexOf("\\") + 1,
				filesname.length());
		request.setAttribute("filesname", filesname);
		myfile.transferTo(newfile);
		return "order/excel";
	}

	// 上传开始保存到数据库
	@RequestMapping(params = "method=implinplanexcle")
	public @ResponseBody
	String implinplanexcle(HttpServletRequest request, String filename,
			String usersname) {
		String filepath = request.getSession().getServletContext()
				.getRealPath("WebRoot/orderFiles/" + filename);
		DeliveryExcelInfo oef = new DeliveryExcelInfo();
		String flag = "";
		String pid = customid;
		try {
			flag = oef.impExcel(filepath, usersname, pid);
			deleteFile(filepath);// 执行上传文件删除操作
			customid = "";
			return flag;
		} catch (Exception e) {
			e.printStackTrace();
			return flag;
		}
	}

	// 做导入前的上传处理
	@RequestMapping(params = "method=startimplinplan")
	public String startimplinplan(HttpServletRequest request,
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
		File newfile = filez.createTempFile("shipOrder", prefix, filez);
		String filesname = newfile.toString();
		filesname = filesname.substring(filesname.lastIndexOf("\\") + 1,
				filesname.length());
		request.setAttribute("filesname", filesname);
		myfile.transferTo(newfile);
		return "order/maplinimp";
	}

	// 上传开始保存到数据库
	@RequestMapping(params = "method=implinplan")
	public @ResponseBody
	String implinplan(HttpServletRequest request, String filename,
			String usersname) {
		String filepath = request.getSession().getServletContext()
				.getRealPath("WebRoot/orderFiles/" + filename);
		OrderExcelForPOI oef = new OrderExcelForPOI();
		String flag = "";
		String pid = customid;
		try {
			//flag = oef.impExcel(filepath, usersname, pid);
			deleteFile(filepath);// 执行上传文件删除操作
			customid = "";
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

	// 全部查询
	/*
	 * @RequestMapping(params = "method=getOrderCustom") public @ResponseBody
	 * Map getShipOrderInfo(String rows, String page, String send_time ,String
	 * shiping_order_num, String send_mechanism,String end_address, String
	 * custom_name,String receipt_name, String receipt_tel) { Integer rows1 =
	 * 0;// 当前有几行 Integer page1 = 1;// 当前有几页 if (rows != null &&
	 * !"".equals(rows)) { rows1 = Integer.parseInt(rows); } if (page != null &&
	 * !"".equals(page)) { page1 = Integer.parseInt(page); } List<OrderCustom>
	 * list = csi.getOrderCustomInfo((page1 - 1) * rows1,rows1, send_time ,
	 * shiping_order_num,send_mechanism, end_address,custom_name,
	 * receipt_name,receipt_tel); int count = csi.getOrderCustom(send_time ,
	 * shiping_order_num,send_mechanism, end_address,custom_name,
	 * receipt_name,receipt_tel); Map<String, Object> map = new HashMap<String,
	 * Object>(); map.put("total", count); map.put("rows", list); return map; }
	 */
	/**
	 * 查询是否有关联的订单
	 * 
	 * @param pid
	 * @return
	 */
	@RequestMapping(params = "method=OrderMsgNum")
	public @ResponseBody
	Json OrderMsgNum(String[] del) {
		int i = csi.OrderMsgNum(del);
		Json json = new Json();
		if (i > 0) {
			json.setFlag(true);
		} else {
			json.setFlag(false);
		}
		return json;
	}

	/*
	 * 批量删除
	 */
	@RequestMapping(params = "method=deleteOrderCustom")
	public @ResponseBody
	Json delShipOrder(String[] del) {
		Json json = new Json();
		int num = csi.deleteOrderMsg(del);
		int truck = csi.deleteOrderCustom(del);
		if (truck > 0) {
			json.setFlag(true);
			return json;
		}
		json.setFlag(false);
		return json;
	}

	// 跳转到添加页面
	@RequestMapping(params = "method=addOrderCustom")
	public String addShipOrder() {
		return "order/AddorderCustom";
	}

	// 保存
	@RequestMapping(params = "method=saveOrderCustom")
	public @ResponseBody
	Json saveOrderCustom(OrderCustom d) {
		Json json = new Json();
		d.setCustom_id(UUIDUtils.getUUID());
		d.setUpdatetime(DateFormat.getDateTimeInstance().format(new Date()));
		int truck = csi.saveOrderCustom(d);
		if (truck > 0) {
			json.setFlag(true);
			return json;
		} else {
			json.setFlag(false);
			return json;
		}

	}

	// 修改查询
	/*
	 * 订单重复查询
	 */
	@RequestMapping(params = "method=getUpdateOrderCustom")
	public @ResponseBody
	OrderCustom getUpdateOrderCustom(String id) {
		OrderCustom der = csi.getUpdateOrderCustom(id);
		return der;
	}

	// 修改
	@RequestMapping(params = "method=updateOrderCustom")
	public @ResponseBody
	Json updateShipOrder(OrderCustom d) {
		d.setUpdatetime(DateFormat.getDateTimeInstance().format(new Date()));
		Json json = new Json();
		int i = csi.updateOrderCustom(d);
		if (i > 0) {
			json.setFlag(true);
			return json;
		}
		json.setFlag(false);
		return json;
	}

	/**
	 * 修改订单备注
	 * 
	 * @param pid
	 * @param notes
	 * @return
	 */
	@RequestMapping(params = "method=getRemakesOrder")
	public @ResponseBody
	Json getRemakesOrder(String pid, String notes, String remarks_orde,
			String remarks_date) {
		int i = csi.getRemakesOrder(pid, notes, remarks_orde, remarks_date);
		Json json = new Json();
		if (i > 0) {
			json.setFlag(true);
		} else {
			json.setFlag(false);
		}
		return json;
	}

	// 客户检索 getCustomer
	@RequestMapping(params = "method=getCustomer")
	public @ResponseBody
	String getCustomer(String name) {
		List<OrderCustom> list = csi.getCustomer(name);
		JSONObject jb = null;
		JSONArray jsons = new JSONArray();
		for (OrderCustom d : list) {
			jb = new JSONObject();
			jb.put("id", d.getCustomer_id());
			jb.put("name", d.getCustomer_name());
			jsons.add(jb);
		}
		return jsons.toString();
	}

	/*
	 * // 车牌检索
	 * 
	 * @RequestMapping(params = "method=getPlateNumberLength") public
	 * 
	 * @ResponseBody String getPlateNumber(String number) { List<OrderCustom>
	 * list = csi.getPlateNumber(number); JSONObject jb = null; JSONArray jsons
	 * = new JSONArray(); for (OrderCustom d : list) { jb = new JSONObject();
	 * jb.put("id", d.getTravel_card_id()); jb.put("name", d.getPlate_number());
	 * jsons.add(jb); } return jsons.toString(); }
	 */

	// 导出信息
	@RequestMapping(params = "method=outShipOrder")
	public @ResponseBody
	String outShipOrder(String datas, String[] headtitle, String[] fieldName,
			HttpServletResponse response, String name, String plate_number,
			String type, String start_date, String end_date) {
		String filename = "运单信息导出";
		List<OrderCustom> list = csi.getOrderCustomAll(name, plate_number,
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
	 * 订单修改记录查询
	 * 
	 * @param rows
	 * @param page
	 * @param pid
	 * @return
	 */
	@RequestMapping(params = "method=getShipOrder1")
	public @ResponseBody
	Map getShipOrderInfo1(String rows, String page, String pid) {
		Integer rows1 = 0;// 当前有几行
		Integer page1 = 1;// 当前有几页
		if (rows != null && !"".equals(rows)) {
			rows1 = Integer.parseInt(rows);
		}
		if (page != null && !"".equals(page)) {
			page1 = Integer.parseInt(page);
		}
		System.out.println(pid);
		List<ShippingOrder> list = csi.getShipOrderInfo1((page1 - 1) * rows1,
				rows1, pid);
		int count = csi.getShipOrder1(pid);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", count);
		map.put("rows", list);
		return map;
	}

	// 首页订单页面转
	@RequestMapping(params = "method=getOrderMsgSY")
	public String getOrderMsgSY() {
		return "layout/getOrderMsgSY";
	}

	@RequestMapping(params = "method=getAddres")
	public @ResponseBody
	PositionInfo getAddres(String province, String city, String count) {
		PositionInfo position = null;
		List<PositionInfo> posi = csi.getAddres(province, city, count);
		boolean bool1 = false;
		boolean bool2 = false;
		boolean bool3 = false;
		for (PositionInfo tc : posi) {
			if (tc.getThreeid() != 0) {
				bool1 = true;
				position = tc;
				continue;
			}
			if (tc.getTwoid() != 0 && bool1 != true) {
				bool2 = true;
				position = tc;
				continue;
			}
			if (tc.getOneid() != 0 && bool2 != true) {
				bool3 = true;
				position = tc;
				continue;
			}
		}
		return position;
	}	

}
