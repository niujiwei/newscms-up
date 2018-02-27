package com.jy.csaction;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jy.common.ExportUtils;
import com.jy.common.SessionInfo;
import com.jy.model.ShippingTimes;
import com.jy.model.User;
import com.jy.service.ShippingOrderInfoService;
import com.jy.service.ShippingTimesService;
import com.jy.service.UserInfoService;

/**
 * 时效管理
 * 
 * @author 90
 * 
 */
@Controller
@RequestMapping(value = "/shippingTimes.do")
public class ShippingTimesController extends BaseAction {
	Logger log = Logger.getLogger(ShippingTimesController.class);
	@Resource
	private ShippingTimesService shippingTimesService;
	@Resource
	private UserInfoService uis;
	@Resource
	private ShippingOrderInfoService csi;

	/**
	 * 时效管理 跳转时效管理页面
	 * 
	 * @param menu_id
	 * @param session
	 * @return
	 */
	@RequestMapping(params = "method=shippingTimes")
	public String shippingTimes(String menu_id,String order, HttpServletRequest request) {
		request.setAttribute("order", order);
		
		return "shippingTimes/shippingTimes";
	}

	/**
	 * 时效管理 查询时效信息
	 * 
	 * @param rows
	 * @param page
	 * @param orderId
	 * @param orderAddress
	 * @param startDate
	 * @param endDate
	 * @param sendMession
	 * @param shiping_order_goid
	 * @param ship_state
	 * @param session
	 * @return
	 */
	@RequestMapping(params = "method=getshippingTimes")
	public @ResponseBody
	Map getShippingTimes(String rows, String page, String orderId,
			String orderAddress, String startDate, String endDate,
			String sendMession, String shiping_order_goid, String ship_state,
			HttpSession session) {
		Integer rows1 = 0;// 当前有几行
		Integer page1 = 1;// 当前有几页
		if (rows != null && !"".equals(rows)) {
			rows1 = Integer.parseInt(rows);
		}
		if (page != null && !"".equals(page)) {
			page1 = Integer.parseInt(page);
		}
		User user = (User) session.getAttribute(SessionInfo.SessionName);
		List<ShippingTimes> list = shippingTimesService.getShippingTimes(
				(page1 - 1) * rows1, rows1, orderId, orderAddress, startDate,
				endDate, sendMession, shiping_order_goid, ship_state, user);
		int count = shippingTimesService.getShippingTimesCount(orderId,
				orderAddress, startDate, endDate, sendMession,
				shiping_order_goid, ship_state, user);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", count);
		map.put("rows", list);
		return map;
	}

	/**
	 * 时效管理 时效信息 导出
	 * 
	 * @param dataId
	 * @param headtitle
	 * @param fieldName
	 * @param response
	 * @param orderId
	 * @param orderAddress
	 * @param startDate
	 * @param endDate
	 * @param sendMession
	 * @param shiping_order_goid
	 * @param ship_state
	 * @param session
	 * @return
	 */
	@RequestMapping(params = "method=outTimesFile")
	public @ResponseBody
	String outTimesFile(String[] dataId, String[] headtitle,
			String[] fieldName, HttpServletResponse response, String orderId,
			String orderAddress, String startDate, String endDate,
			String sendMession, String shiping_order_goid, String ship_state,
			HttpSession session) {
		String filename = "配送时效";
		if (dataId.length == 0) {
			dataId = null;
		}
		User user = (User) session.getAttribute(SessionInfo.SessionName);
		List<ShippingTimes> list = shippingTimesService.outTimesFile(dataId,
				orderId, orderAddress, startDate, endDate, sendMession,
				shiping_order_goid, ship_state, user);
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
			ExportUtils.outputHeaders(headtitle, workbook, "配送时效");
			// 调用工具类生成内容
			ExportUtils.outputColums(fieldName, list, workbook, 1, "配送时效");
			workbook.write(ouputStream);
			ouputStream.flush();
			ouputStream.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将毫秒数换算成x天x时x分x秒x毫秒
	 * 
	 * @param ms
	 * @return
	 */
	public static String format(long ms) {
		int ss = 1000;
		int mi = ss * 60;
		int hh = mi * 60;
		int dd = hh * 24;

		long day = ms / dd;
		long hour = (ms - day * dd) / hh;
		long minute = (ms - day * dd - hour * hh) / mi;
		long second = (ms - day * dd - hour * hh - minute * mi) / ss;
		long milliSecond = ms - day * dd - hour * hh - minute * mi - second
				* ss;

		String strDay = day < 10 ? "0" + day : "" + day;
		String strHour = hour < 10 ? "0" + hour : "" + hour;
		String strMinute = minute < 10 ? "0" + minute : "" + minute;
		String strSecond = second < 10 ? "0" + second : "" + second;
		String strMilliSecond = milliSecond < 10 ? "0" + milliSecond : ""
				+ milliSecond;
		strMilliSecond = milliSecond < 100 ? "0" + strMilliSecond : ""
				+ strMilliSecond;
		return strDay + "天" + strHour + "小时" + strMinute + "分钟";
		// + strSecond + "秒 "+ strMilliSecond;
	}

}
