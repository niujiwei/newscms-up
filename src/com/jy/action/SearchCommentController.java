package com.jy.action;

import java.io.OutputStream;
import java.util.ArrayList;
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
import com.jy.dao.Json;
import com.jy.model.Comment;
import com.jy.model.ShippingOrder;
import com.jy.model.User;
import com.jy.service.SearchCommentService;
import com.jy.service.impl.SearchCommentServiceImpl;

@Controller
@RequestMapping(value = "/searchComment.do")
public class SearchCommentController {
	@Resource
	private SearchCommentService scsi;

	// 跳转司机评价页
	@RequestMapping(params = "method=getComment")
	public String getComment() {
		return "comment/commentInfo";
	}

	// 列表信息
	@RequestMapping(params = "method=getCommentList")
	public @ResponseBody
	Map getCommentList(String rows, String page, String shiping_order_num,
			String send_mechanism, String end_address, String driver_name,
			String receipt_name, HttpServletRequest request) {
		Integer rows1 = 0;// 当前有几行
		Integer page1 = 1;// 当前有几页
		if (rows != null && !"".equals(rows)) {
			rows1 = Integer.parseInt(rows);
		}
		if (page != null && !"".equals(page)) {
			page1 = Integer.parseInt(page);
		}
		User user = (User) request.getSession().getAttribute(
				SessionInfo.SessionName);
		List<Comment> list = scsi.getCommentList((page1 - 1) * rows1, rows1,
				shiping_order_num, send_mechanism, end_address, driver_name,
				receipt_name, user.getId(), user);
		int count = scsi.getComment_count(shiping_order_num, send_mechanism,
				end_address, driver_name, receipt_name, user.getId(), user);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", count);
		map.put("rows", list);
		return map;
	}

	@RequestMapping(params = "method=deleteComment")
	public @ResponseBody
	Json deleteComment(String[] comment_id) {
		Json json = new Json();
		int i = scsi.deleteComment(comment_id);
		if (i > 0) {
			json.setFlag(true);
		} else {
			json.setFlag(false);
		}
		return json;
	}

	@RequestMapping(params = "method=allMessageOutPut")
	public @ResponseBody
	String allMessageOutPut(String[] dataIds, String[] headtitle,
			String[] fieldName, HttpServletResponse response,
			HttpSession session, String shiping_order_num,
			String send_mechanism, String end_address, String driver_name,
			String receipt_name) {
		String filename = "评价信息导出";
		List<Comment> list = new ArrayList<Comment>();
		if (dataIds.length > 0) {
			list=scsi.getCommentListByIds(dataIds);
		} else {
			User user = (User) session.getAttribute(SessionInfo.SessionName);
			int count = scsi.getComment_count(shiping_order_num,
					send_mechanism, end_address, driver_name, receipt_name,
					user.getId(), user);
			list = scsi.getCommentList(0, count, shiping_order_num,
					send_mechanism, end_address, driver_name, receipt_name,
					user.getId(), user);
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
			ExportUtils.outputHeaders(headtitle, workbook, "评价信息导出");
			// 调用工具类生成内容
			ExportUtils.outputColums(fieldName, list, workbook, 1, "评价信息导出");
			workbook.write(ouputStream);
			ouputStream.flush();
			ouputStream.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
