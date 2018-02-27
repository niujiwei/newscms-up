package com.jy.action;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

import com.jy.common.ExportUtils;
import com.jy.common.OrderExcelForPOI;
import com.jy.common.QRCodeEncoderHandler;
import com.jy.common.SessionInfo;
import com.jy.common.UUIDUtils;
import com.jy.dao.Json;
import com.jy.model.OrderCustom;

import com.jy.model.Delivery;
import com.jy.model.ShippingManual;
import com.jy.model.Sign_order;
import com.jy.model.User;
import com.jy.service.OrderInfoService;
import com.jy.service.ShippingManualInfoService;


/*
 * 货运订单管理
 * */
@Controller
@RequestMapping(value = "/shipManual.do")
public class ShippingManualInfoController {
	@Resource
	private ShippingManualInfoService csi;
	@Resource
	private OrderInfoService ocs;

	@RequestMapping(params = "method=getShipManualInfo")
	public String getShipManualInfoMan() {
		return "shipping_manual/shippingManualInfo";
	}

	// 跳转编辑页面并且获取合同的id 返回到页面
	@RequestMapping(params = "method=getEditShipManualPage")
	public String getEditShipManualPage(String pid, HttpServletRequest request) {
		request.setAttribute("flg", pid);
		return "shipping_manual/EditShipManual";
	}

	@RequestMapping(params = "method=getShipManualPage")
	public String getShipManualPage(String pid, HttpServletRequest request) {
		request.setAttribute("flg", pid);
		return "shipping_manual/SearchShipManual";
	}
	//打印
	@RequestMapping(params = "method=getShipManualPrint")
	public String getShipManualPrint(String pid, HttpServletRequest request) {
		ShippingManual d = getUpdateShipManual(pid);
		if (d.getSend_type() == 0) {
			d.setSendtype("√");
		} else if (d.getSend_type() == 1) {
			d.setSendtype1("√");
		}
	
		if (d.getPay_type() == 0) {
			d.setPaytype("回付");
		} else if (d.getPay_type() == 1) {
			d.setPaytype("提付");
		} else if (d.getPay_type() == 2) {
			d.setPaytype("现付");
		} else if (d.getPay_type() == 3) {
			d.setPaytype("货到前付");
		}

		request.setAttribute("sp", d);
		return "shipping_manual/printer";
	}
	

	//打印一维码
	@RequestMapping(params = "method=getShipManualPrintYwm")
	public String getShipManualPrintYWM(String[] checkarray, HttpServletRequest request) {
		StringBuffer s=new StringBuffer();
		for(int i=0;i<checkarray.length;i++){
			s.append(checkarray[i]+",");
		}
		String num=s.substring(0,s.lastIndexOf(","));
		request.setAttribute("sp",num);
		return "shipping_manual/printYWM";
	}
	//输出一维码
		@RequestMapping(params = "method=getShipYwm")
		public @ResponseBody String getShipYWM(String order_num, HttpServletResponse response) {
			String[] s=order_num.split(",");
			List<ShippingManual> d = csi.getUpdateShipManualArray(s);
			JSONObject obje=new JSONObject();
			obje.accumulate("Detail", d);
			return obje.toString();
		}
	// 导入
	@RequestMapping(params = "method=imp")
	public String goimp() {
		return "shipping_manual/maplinimp";
	}

	// 做导入前的上传处理
	@RequestMapping(params = "method=startimplinplan")
	public String startimplinplan(HttpServletRequest request,
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
		File newfile = filez.createTempFile("shipManual", prefix, filez);
		// System.out.println(newfile);
		String filesname = newfile.toString();
		filesname = filesname.substring(filesname.lastIndexOf("\\") + 1,
				filesname.length());
		request.setAttribute("filesname", filesname);
		myfile.transferTo(newfile);
		return "shipping_manual/maplinimp";
	}

	// 上传开始保存到数据库
	@RequestMapping(params = "method=implinplan")
	public @ResponseBody
	String implinplan(HttpServletRequest request, String filename,String usersname,String pid) {
		String filepath = request.getSession().getServletContext()
				.getRealPath("WebRoot/orderFiles/" + filename);
		OrderExcelForPOI oef = new OrderExcelForPOI();
		String flag = "";
		try {
			//flag = oef.impExcel(filepath,usersname,pid);
			deleteFile(filepath); // 执行上传文件删除操作
			return flag;
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(flag
					+ "===========================================");
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
	@RequestMapping(params = "method=getShipManual")
	public @ResponseBody
	Map getShipManualInfo(String rows, String page, String start_time,String end_time,String send_station,String end_address,String num,String receipt,String custom_name,String order_state,String pay_type ,String send_type,String goods_name) {
		Integer rows1 = 0;// 当前有几行
		Integer page1 = 1;// 当前有几页
		if (rows != null && !"".equals(rows)) {
			rows1 = Integer.parseInt(rows);
		}
		if (page != null && !"".equals(page)) {
			page1 = Integer.parseInt(page);
		}
		List<ShippingManual> list = csi.getShipManualInfo((page1 - 1) * rows1,
				rows1, start_time, end_time, send_station, end_address, num, receipt, custom_name, order_state, pay_type , send_type, goods_name);
		
		int count = csi.getShipManual(start_time, end_time, send_station, end_address, num, receipt, custom_name, order_state, pay_type , send_type, goods_name);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", count);
		map.put("rows", list);
		return map;
	}

	/*
	 * 批量删除
	 */
	@RequestMapping(params = "method=deleteShipManual")
	public @ResponseBody
	Json delShipManual(String[] del) {
		Json json = new Json();
	
		int n=csi.deleteShip(del);
		int truck = csi.deleteShipManual(del);
		if (truck > 0) {
			json.setFlag(true);
			return json;
		}
		json.setFlag(false);
		return json;
	}
	
	/*
	 * 批量取消到达
	 */
	@RequestMapping(params = "method=isNotArrive")
	public @ResponseBody
	Json isNotArrive(String[] del) {
		Json json = new Json();
		int truck = csi.isNotArrive(del);
		if (truck > 0) {
			json.setFlag(true);
			return json;
		}
		json.setFlag(false);
		return json;
	}
	
	/*
	 * 批量达到
	 */
	@RequestMapping(params = "method=isArrive")
	public @ResponseBody
	Json isArrive(String[] del) {
		Json json = new Json();
		int truck = csi.isArrive(del);
		if (truck > 0) {
			json.setFlag(true);
			return json;
		}
		json.setFlag(false);
		return json;
	}
	// 跳转到添加页面
	@RequestMapping(params = "method=addShipManual")
	public String addShipManual(String pid,HttpServletRequest request) {
		request.setAttribute("flg", pid);
		return "shipping_manual/AddShipManual";
	}

	// 保存
	@RequestMapping(params = "method=saveShipManual")
	public @ResponseBody
	Json saveShipManual(ShippingManual d) {
		Json json = new Json();
		d.setShiping_order_id(UUIDUtils.getUUID());		
		d.setPractiacl_num(d.getGoods_num());	
		int truck = csi.saveShipManual(d);
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
	@RequestMapping(params = "method=getUpdateShipManual")
	public @ResponseBody
	ShippingManual getUpdateShipManual(String id) {
		ShippingManual der = csi.getUpdateShipManual(id);
		return der;
	}

	// 导入页面
	@RequestMapping(params = "method=inputShipManual")
	public String inputShipManual() {
		return "driver/InputDriver";
	}

	// 修改
	@RequestMapping(params = "method=updateShipManual")
	public @ResponseBody
	Json updateShipManual(ShippingManual d,HttpServletRequest request) {	
		User user=(User)request.getSession().getAttribute(SessionInfo.SessionName);
		d.setEhistory_id(UUIDUtils.getUUID());
		d.setEdit_order(user.getUsername());
		d.setUpdatetime(DateFormat.getDateTimeInstance().format(new Date()));
		d.setPractiacl_num(d.getGoods_num());
		Json json = new Json();
		int n=csi.updateShip(d);
		int i = csi.updateShipManual(d);
		if (i > 0&&n>0) {
			json.setFlag(true);
			return json;
		}
		json.setFlag(false);
		return json;
	}

	/*
	 * 订单重复查询
	 */
	@RequestMapping(params = "method=getNumber")
	public @ResponseBody
	ShippingManual getShipManualLength(String number) {
		ShippingManual der = csi.getNumber(number);
		return der;
	}
	//重复订单号个数查询
	@RequestMapping(params = "method=getAorder")
	public @ResponseBody String getAorder(String number){
		int i=csi.getAorder(number);
		Json json=new Json();
		if(i>0){
			json.setFlag(false);
		}else{
			json.setFlag(true);
		}
		return json.toString();
	}

	// 车牌检索
	@RequestMapping(params = "method=getPlateNumberLength")
	public @ResponseBody
	String getPlateNumberLength(String number) {
		List<ShippingManual> list = csi.getPlateNumberLength(number);
		JSONObject jb = null;
		JSONArray jsons = new JSONArray();
		for (ShippingManual d : list) {
			jb = new JSONObject();
			jb.put("id", d.getTravel_card_id());
			jb.put("name", d.getPlate_number());
			jsons.add(jb);
		}
		return jsons.toString();
	}

	// 导出信息
	@RequestMapping(params = "method=outShipManual")
	public @ResponseBody
	String outShipManual(String datas, String[] headtitle, String[] fieldName,
			HttpServletResponse response, String name, String plate_number,
			String type, String start_date, String end_date) {
		String filename = "运单信息导出";
		List<ShippingManual> list = csi.getShipManualAll(name, plate_number,
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
	 * 协议制作订单查询方法
	 * @param rows
	 * @param page
	 * @param id
	 * @param type
	 * @param start_date
	 * @param end_date
	 * @return
	 */
		@RequestMapping(params = "method=getShipManualCA")
		public @ResponseBody
		Map getShipManualCA(String rows, String page, 
				String id,String name,
				String phone_number, String type, String start_date, String end_date) {
			Integer rows1 = 0;// 当前有几行
			Integer page1 = 1;// 当前有几页
			if (rows != null && !"".equals(rows)) {
				rows1 = Integer.parseInt(rows);
			}
			if (page != null && !"".equals(page)) {
				page1 = Integer.parseInt(page);
			}
			List<ShippingManual> list = csi.getShipManualInfoCA((page1 - 1) * rows1,
					rows1, id, name, phone_number, type, start_date, end_date,null,null,null);

			int count = csi.getShipManualCA(id, name, phone_number, type, start_date, end_date,null,null,null);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("total", count);
			map.put("rows", list);
			return map;
		}
		
		//协议制作订单查询
		@RequestMapping(params="method=getShipManualCAM")
		public @ResponseBody Map getShipManualCAM(String rows, String page, String[] pid){
			Integer rows1 = 0;// 当前有几行
			Integer page1 = 1;// 当前有几页
			if (rows != null && !"".equals(rows)) {
				rows1 = Integer.parseInt(rows);
			}
			if (page != null && !"".equals(page)) {
				page1 = Integer.parseInt(page);
			}
			List<ShippingManual> list=csi.getShipManualCAM((page1 - 1) * rows1,rows1,pid.length==0?null:pid);//当前页
			int count=pid.length;
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("total", count);
			map.put("rows", list);
			return map;
		}

	/**
	 * 签收订单
	 * 
	 * @author H
	 * @serial v1.0
	 * @time 2015.8.7
	 */

	@RequestMapping(params = "method=getSignShipManualInfo")
	public String getSignShipManualInfo() {
		return "shipping_order/signShipManual";
	}

	// 全部查询
	@RequestMapping(params = "method=getSignShipManual")
	public @ResponseBody
	Map getSignShipManual(String rows, String page, String name,
			String phone_number, String type, String start_date, String end_date,HttpSession session,String shipping_order_state) {
		Integer rows1 = 0;// 当前有几行
		Integer page1 = 1;// 当前有几页
		if (rows != null && !"".equals(rows)) {
			rows1 = Integer.parseInt(rows);
		}
		if (page != null && !"".equals(page)) {
			page1 = Integer.parseInt(page);
		}
		User user=(User)session.getAttribute(SessionInfo.SessionName);
		List<ShippingManual> list = csi.getSignShipManual((page1 - 1) * rows1,
				rows1, name, phone_number, type, start_date, end_date,shipping_order_state);

		int count = csi.getSignShipManualcount(name, phone_number, type,
				start_date, end_date,shipping_order_state);
		
		
		List<ShippingManual> list1 = csi.getSignShipManual1((page1 - 1) * rows1,
				rows1, name, phone_number, type, start_date, end_date,user.getDid(),shipping_order_state);

		int count1 = csi.getSignShipManualcount1(name, phone_number, type,
				start_date, end_date,user.getDid(),shipping_order_state);
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		list.addAll(list1);
		System.out.println(list.size());
		map.put("total", (count+count1));
		map.put("rows", list);
		return map;
	}
	
	// 全部查询
	/**
	 * 协议制作订单查询方法
	 * @param rows
	 * @param page
	 * @param id
	 * @param type
	 * @param start_date
	 * @param end_date
	 * @return
	 */
		@RequestMapping(params = "method=getSignShipManualCA")
		public @ResponseBody
		Map getSignShipManualCA(String rows, String page, 
				String id,String name,String phone_number, String type, String start_date, String end_date,String send_station,String end_address,String car_number) {
			Integer rows1 = 0;// 当前有几行
			Integer page1 = 1;// 当前有几页
			if (rows != null && !"".equals(rows)) {
				rows1 = Integer.parseInt(rows);
			}
			if (page != null && !"".equals(page)) {
				page1 = Integer.parseInt(page);
			}
			List<ShippingManual> list = csi.getShipManualInfoCA((page1 - 1) * rows1,
					rows1, id,  name, phone_number, type, start_date, end_date,send_station,end_address,car_number);

			int count = csi.getShipManualCA(id, name, phone_number, type, start_date, end_date,send_station,end_address,car_number);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("total", count);
			map.put("rows", list);
			return map;
		}

	@RequestMapping(params = "method=getUpdateSignShipManual")
	public @ResponseBody
	ShippingManual getUpdateSignShipManual(String id) {
		ShippingManual der = csi.getUpdateSignShipManual(id);
		return der;
	}
		
	// 跳转编辑页面并且获取id 返回到页面
	@RequestMapping(params = "method=getEditSignShipManualPage")
	public String getEditSignShipManualPage(String pid,String isok,
			HttpServletRequest request) {
		request.setAttribute("flg", pid);
		request.setAttribute("isok", isok);
		return "shipping_manual/EditSignShipManual";
	}
	@RequestMapping(params = "method=getimgurl")
	public String getimgurl(String iurl,
			HttpServletRequest request) {
		request.setAttribute("iurl", iurl);
		return "shipping_manual/orderImg";
	}
	
	// 跳转编辑页面并且获取id 返回到页面
	@RequestMapping(params = "method=saveSignShipManual")
	public @ResponseBody
	Json saveSignShipManual(Sign_order sign_order) {
		sign_order.setSign_id(UUIDUtils.getUUID());
		int i = 0;
		csi.updatestate(sign_order.getOrder_id());
		Json json = new Json();
		i = csi.saveSignShipManual(sign_order);
		if (i > 0) {
			json.setFlag(true);
			return json;
		}
		json.setFlag(false);
		return json;
	}
	@RequestMapping(params = "method=getQrcode")
	public void getQrcode(HttpServletResponse response,String orderId) {
		String content =orderId;
       QRCodeEncoderHandler handler = new QRCodeEncoderHandler(); 
       BufferedImage bi=handler.encoderQRCode(content,3);
       try {
		ImageIO.write(bi, "png", response.getOutputStream());
	} catch (IOException e) {
		e.printStackTrace();
	}
       System.out.println("encoder QRcode success");
	}
		@RequestMapping(params = "method=getdbrcode")
		public void getdbrcode(HttpServletResponse response,String orderId) {
			String format = MimeTypes.MIME_JPEG;// MimeTypes.MIME_JPEG  
			  
	        String text = orderId;  
	  
	        ByteArrayOutputStream bout = null;  
	        try  
	        {  
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
	  
	        }  
	        catch (Exception e)  
	        {  
	            //log.error("Error while generating barcode", e);  
	        }  
	        finally  
	        {  
	            if (bout != null)  
	            {  
	                try  
	                {  
	                    bout.close();  
	                }
	                catch (IOException e)  
	                {  
	                    // TODO Auto-generated catch block  
	                    e.printStackTrace();  
	                }  
	            }  
	  
	        }  
	       }
		public static Configuration buildCfg()  
	    {  
	        DefaultConfiguration cfg = new DefaultConfiguration("barcode");  
	        // Get type  
	  
	        String type = "code128";  
	        DefaultConfiguration child = new DefaultConfiguration(type);  
	        cfg.addChild(child);  
	        return cfg;  
	    }
		private String createXML(List<ShippingManual> list) {  
	        Document document = DocumentHelper.createDocument();  
	        Element root = document.addElement("xml");  
	        Iterator<ShippingManual> it = list.iterator();  
	        while (it.hasNext()) {  
	        	ShippingManual so = it.next();  
	            Element edept = root.addElement("Detail");  
	            edept.addElement("shiping_order_num").addText(so.getShiping_order_num()+"");  
	            edept.addElement("receipt").addText("123");  
	            edept.addElement("receipt_tel").addText("123");  
	            edept.addElement("receipt_address").addText("123");

	        }
	        String xmlString = document.asXML();  
	        return xmlString;  
	    }
}
