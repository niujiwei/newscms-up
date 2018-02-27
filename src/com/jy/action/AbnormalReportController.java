package com.jy.action;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.http.HttpRequest;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.baidu.push.msg.AndroidPushBatchUniMsg;
import com.baidu.push.msg.AndroidPushBatchUniMsgNew;
import com.baidu.yun.push.exception.PushClientException;
import com.baidu.yun.push.exception.PushServerException;
import com.jy.common.ExportUtils;
import com.jy.common.OrderedProperties;
import com.jy.common.SessionInfo;
import com.jy.common.UUIDUtils;
import com.jy.csaction.BaseAction;
import com.jy.model.AbnormalImages;
import com.jy.model.AbnormalReport;
import com.jy.model.AbnormalReportFile;
import com.jy.model.OrderHistory;
import com.jy.model.ShippingOrder;
import com.jy.model.User;
import com.jy.service.AbnormalReportService;

/**
 * 异常反馈
 * 
 * @author 90
 * 
 */
@Controller
@RequestMapping(value = "/abnormalreport.do")
public class AbnormalReportController {
	@Resource
	private AbnormalReportService ar_service;

	/**
	 * 异常反馈--跳转异常反馈页面
	 * 
	 * @param menu_id
	 * @param session
	 * @return
	 */
	@RequestMapping(params = "method=abnormalReportInfo")
	public String abnormalReportInfo(String order,HttpServletRequest request) {
		
		request.setAttribute("order", order);
		return "abnormalreport/abnormalReportInfo";
	}

	/**
	 * 异常反馈--跳转异常反馈查询运单页面
	 * 
	 * @param menu_id
	 * @param session
	 * @return
	 */
	@RequestMapping(params = "method=serachOrder")
	public String serachOrder() {
		return "abnormalreport/serachOrder";
	}

	/**
	 * 异常反馈运单信息
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
		List<ShippingOrder> list = ar_service.getShipOrderInfo((page1 - 1)
				* rows1, rows1, send_time, shiping_order_num, send_mechanism,
				end_address, custom_name, receipt_name, receipt_tel,
				topordernumber, downordernumber, shipperorder_id, end_time,
				user);
		int count = ar_service.getShipOrder(send_time, shiping_order_num,
				send_mechanism, end_address, custom_name, receipt_name,
				receipt_tel, topordernumber, downordernumber, shipperorder_id,
				end_time, user);
		map.put("total", count);
		map.put("rows", list);
		return map;
	}

	/**
	 * 异常反馈--跳转异常反馈添加页面
	 * 
	 * @param menu_id
	 * @param session
	 * @return
	 */
	@RequestMapping(params = "method=addAbnormalReport")
	public String addAbnormalReport(String shiping_order_id,
			HttpServletRequest request) {
		request.setAttribute("shiping_order_id", shiping_order_id);
		return "abnormalreport/addAbnormalReport";
	}

	/**
	 * 异常反馈--获取异常运单信息
	 * 
	 * @param menu_id
	 * @param session
	 * @return
	 */
	@RequestMapping(params = "method=getShipOrderMsg")
	public @ResponseBody
	ShippingOrder getShipOrderMsg(String shiping_order_id) {
		ShippingOrder shippingOrder = ar_service
				.getShipOrderMsg(shiping_order_id);
		return shippingOrder;
	}

	/**
	 * 异常反馈--上传图片信息
	 * 
	 * @param menu_id
	 * @param session
	 * @return
	 */
	@RequestMapping(params = "method=uploadImage")
	public void uploadImage(@RequestParam("file") MultipartFile file,
			HttpServletRequest request, HttpServletResponse response) {
		BaseAction base = new BaseAction();
		try {
			base.upload(file, "abnormalreport", request);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 异常反馈--获取异常运单信息
	 * 
	 * @param menu_id
	 * @param session
	 * @return
	 */
	@RequestMapping(params = "method=saveAbnormalReport")
	public @ResponseBody
	boolean saveAbnormalReport(AbnormalReport abnormalReport,
			HttpSession session) {
		User user = (User) session.getAttribute(SessionInfo.SessionName);
		if (user != null) {
			if (user.getFlag().equals("1")) {
				String suppliers = ar_service.getSupplersId(user.getDriver_id());
				user.setSuppliers_id(suppliers);
			}
			abnormalReport.setAbnormal_supperid(user.getSuppliers_id());
			abnormalReport.setAbnormal_driverid(user.getDriver_id());
			abnormalReport.setAbnormal_userid(user.getId());
		}
		abnormalReport.setId(UUIDUtils.getUUID());
		boolean b = ar_service.saveAbnormalReport(abnormalReport);
		boolean bb = false;
		if (b) {
			String filenames = (String) session.getAttribute("filename");
			if (filenames != null) {
				String[] file = filenames.split(",");
				List<AbnormalImages> abnormalImagesList = new ArrayList<AbnormalImages>();
				for (int j = 0; j < file.length; j++) {
					AbnormalImages abnormalImages = new AbnormalImages();
					abnormalImages.setImage_id(UUIDUtils.getUUID());
					abnormalImages.setAbnormalt_id(abnormalReport.getId());
					abnormalImages.setOrder_id(abnormalReport
							.getShiping_order_id());
					abnormalImages.setImageUrl(file[j]);
					abnormalImagesList.add(abnormalImages);

				}
				// 保存图片信息
				boolean saveImage = ar_service
						.saveAbnormalImage(abnormalImagesList);
				if (saveImage) {
					// 更新运单信息
					bb = ar_service.updateIsAbnormal(
							abnormalReport.getShiping_order_id(),
							abnormalReport.getAbnormal_type());
				}
			} else {
				// 更新运单状态
				bb = ar_service.updateIsAbnormal(
						abnormalReport.getShiping_order_id(),
						abnormalReport.getAbnormal_type());
			}
			;
			String message = "异常上报，上报原因为"
					+ abnormalReport.getAbnormal_message();
			saveHistory(abnormalReport.getShiping_order_id(),
					abnormalReport.getShiping_order_num(), message);
			session.removeAttribute("filename");
		}

		return bb;
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(params = "method=getAbnromalReportInfo")
	public @ResponseBody
	Map getAbnromalReportInfo(String rows, String page, HttpSession session,
			String send_time, String end_time, String shiping_order_num,
			String shiping_order_goid, String abnormal_result,
			String abnormal_type, String end_address, String receipt_name,
			String send_mechanism) {
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
		List<AbnormalReport> list = ar_service.getAbnormalReport((page1 - 1)
				* rows1, rows1, send_time, end_time, shiping_order_num,
				shiping_order_goid, abnormal_result, abnormal_type,
				end_address, receipt_name, send_mechanism, user);
		int count = ar_service.getAbnormalReportCount(send_time, end_time,
				shiping_order_num, shiping_order_goid, abnormal_result,
				abnormal_type, end_address, receipt_name, send_mechanism, user);

		map.put("total", count);
		map.put("rows", list);
		return map;
	}

	/**
	 * 异常反馈查看图片信息
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "method=showAbnormalImg")
	public String showAbnormalImg(String id, HttpServletRequest request) {
		List<AbnormalImages> list = ar_service.showAbnormalImg(id);
		request.setAttribute("abImglist", list);
		return "abnormalreport/abnormalImg";

	}

	/**
	 * 异常反馈--跳转异常处理页面
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "method=exceptionHandling")
	public String exceptionHandling(String id, HttpServletRequest request) {
		AbnormalReport abnormalReport = ar_service.exceptionHandling(id);
		request.setAttribute("abnormalReport", abnormalReport);
		return "abnormalreport/exceptionHandling";
	}

	/**
	 * 异常反馈--跳转异常处理页面
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "method=doExceptionHandling")
	public @ResponseBody
	boolean doExceptionHandling(String id, String shiping_order_id,
			String abnormal_supperid, String abnormal_driverid,
			String abnormal_resultremark,String order_num, HttpSession session) {
		User user = (User) session.getAttribute(SessionInfo.SessionName);
		boolean b = ar_service.updateAbnormalState(id, abnormal_resultremark,
				user.getUsername());
		if (b) {
			boolean bb = ar_service.getAbnormalOrderNum(shiping_order_id);
			if (bb) {
				ar_service.updateOrederAbnormal(shiping_order_id);
			}
			List<String> channelIds = ar_service.getChannelIds(abnormal_supperid, abnormal_driverid,id);
			String[] chas =null;
			if(channelIds!=null){
				chas =(String[]) channelIds.toArray(new String[channelIds.size()]);
			}
			if(chas!=null){
				String title = "异常处理";
	            String message = "您上报的运单号为："+order_num+"异常已经处理。";
	            JSONObject jsonCustormCont = new JSONObject();
	    		JSONObject json = new JSONObject();
	    		json.put("type", "1");//异常处理
	    		jsonCustormCont.put("successMsg", json);
				// app 开发完成修改
				try {
					AndroidPushBatchUniMsgNew.androidPushBatchMsg(title,message,
							chas,jsonCustormCont);
				} catch (PushClientException e) {
					e.printStackTrace();
				} catch (PushServerException e) {
					e.printStackTrace();
				}
			}
            
			// app 开发完成修改
		}
		return b;
	}

	/**
	 * 异常反馈信息导出
	 * 
	 * @param orderIds
	 * @param id
	 * @param response
	 * @param session
	 * @return
	 * @throws FileNotFoundException
	 */
	@RequestMapping(params = "method=outPutAbnormalFile")
	public @ResponseBody
	String outPutAbnormalFile(String[] abnormal_ids, String id,
			String send_time, String end_time, String shiping_order_num,
			String shiping_order_goid, String abnormal_result,
			String abnormal_type, String end_address, String receipt_name,
			String send_mechanism, HttpServletResponse response,
			HttpSession session) throws FileNotFoundException {
		String filename = "异常反馈信息导出";
		
		InputStream in = AbnormalReportController.class
				.getResourceAsStream("/../exportData/abnormalData.properties");
		Map<String, List<String>> map = ExportUtils.getHeadTitle(in);//通用获取headtitle fieldName
		
		List<String> headTitle =map.get("headTitle");
		List<String> fieldName =map.get("fieldName");
		
		User user = (User) session.getAttribute(SessionInfo.SessionName);

		List<AbnormalReportFile> list = ar_service.outputAbnormalReportFile(
				send_time, end_time, shiping_order_num, shiping_order_goid,
				abnormal_result, abnormal_type, end_address, receipt_name,
				send_mechanism, abnormal_ids, user);

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
			ExportUtils.outputHeaders(
					(String[]) headTitle.toArray(new String[headTitle.size()]),
					workbook, "异常反馈信息导出");
			// 调用工具类生成内容
			ExportUtils.outputColums(
					(String[]) fieldName.toArray(new String[fieldName.size()]),
					list, workbook, 1, "异常反馈信息导出");
			workbook.write(ouputStream);
			ouputStream.flush();
			ouputStream.close();

		} catch (Exception e) {
			System.out.println(e.getMessage());
			
		}
		return null;
	}

	/**
	 * 保存节点记录
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
		ar_service.saveOrderHistory(order);
	}
	
	public static void main(String[] args) {
		
		String[] cha = { "4340312536865689789" };
        String title = "异常处理";
        String message = "您上报的运单号为："+"异常已经处理。";
        JSONObject jsonCustormCont = new JSONObject();
		JSONObject json = new JSONObject();
		json.put("type", "1");//异常处理
		jsonCustormCont.put("successMsg", json);
		
		// app 开发完成修改
		try {
			/*AndroidPushBatchUniMsg.androidPushBatchMsg(title,message,
					cha);*/
			AndroidPushBatchUniMsgNew.androidPushBatchMsg(title,message,
					cha,jsonCustormCont);
		} catch (PushClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PushServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
