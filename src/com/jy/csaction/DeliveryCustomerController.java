package com.jy.csaction;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.jy.common.DeliveryExcelInfo;
import com.jy.common.ExportUtils;
import com.jy.common.SessionInfo;
import com.jy.common.UUIDUtils;
import com.jy.dao.Json;
import com.jy.model.DeliveryCustomer;
import com.jy.model.User;
import com.jy.service.RemarkMapService;
import com.jy.service.UserInfoService;

@Controller
@RequestMapping(value = "/deliveryCustomer.do")
public class DeliveryCustomerController {
	/*@Resource
	private ShippingOrderInfoService csi;*/
	@Resource
	private RemarkMapService rms;
	@Resource
	private UserInfoService uis;

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
		File newfile = File.createTempFile("deliveryCustomerOrder", prefix,
				filez);
		String filesname = newfile.toString();
		filesname = filesname.substring(filesname.lastIndexOf("\\") + 1,
				filesname.length());
		request.setAttribute("filesname", filesname);
		myfile.transferTo(newfile);
		return "customer/deliveryMaplinimp";
	}

	/**
	 * 客户导入上传开始保存到数据库
	 * 
	 * @param request
	 * @param filename
	 * @param usersname
	 * @param pid
	 * @return
	 */
	@RequestMapping(params = "method=implinplan")
	public @ResponseBody
	String implinplan2(HttpServletRequest request, String filename,
			HttpSession session, String usersname, String pid) {
		String filepath = request.getSession().getServletContext()
				.getRealPath("WebRoot/orderFiles/" + filename);
		DeliveryExcelInfo oef = new DeliveryExcelInfo(rms);

		String flag = "";
		try {
			flag = oef.impExcel(filepath, usersname, pid);
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
	 * 导入客户信息页面跳转
	 */
	@RequestMapping(params = "method=imp")
	public String goimp() {
		return "customer/deliveryMaplinimp";
	}

	/**
	 * 收货客户的导出的
	 * @param customer_name
	 * @param customer_tel
	 * @param customer_address
	 * @param User_address
	 * @param request
	 * @param delivery_cus_name
	 * @param headtitle
	 * @param fieldName
	 * @param dataIds
	 * @param response
	 * @param session
	 * @param delivery_id
	 * @return
	 */
	@RequestMapping(params = "method=outPutDeliveryCustomers")
	public @ResponseBody
	String outPutDeliveryCustomers(String customer_name, String customer_tel,
			String customer_address, String User_address,
			HttpServletRequest request, String delivery_cus_name,
			String[] headtitle, String[] fieldName, String[] dataIds,
			HttpServletResponse response, HttpSession session,
			String delivery_id) {
		String filename = "收货客户信息导出";
		if (dataIds.length == 0) {
			dataIds = null;
		}
		User user = (User) session.getAttribute(SessionInfo.SessionName);
		List<DeliveryCustomer> list = rms.outPutDeliveryCustomers(
				customer_name, customer_tel, customer_address,
				user.getUser_address(), delivery_cus_name, user, dataIds);
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
			ExportUtils.outputHeaders(headtitle, workbook, "收货客户信息导出");
			// 调用工具类生成内容
			ExportUtils.outputColums(fieldName, list, workbook, 1, "收货客户信息导出");
			workbook.write(ouputStream);
			ouputStream.flush();
			ouputStream.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 收货客户信息跳转页面
	 * 
	 * @param menu_id
	 * @param session
	 * @return
	 */
	@RequestMapping(params = "method=getDelivery")
	public String getDelivery(String menu_id, HttpSession session) {
		Integer[] rid = (Integer[]) session
				.getAttribute(SessionInfo.SessionRote);
		Integer id = Integer.parseInt(menu_id);
		List<String> list = uis.getFunctions(id, rid);
		if (list.size() > 0) {
			StringBuffer bf = new StringBuffer();
			for (String ss : list) {
				bf.append(ss);
				bf.append(",");

			}
			bf.deleteCharAt(bf.length() - 1);
			session.setAttribute("function", bf.toString());
		} else {
			session.setAttribute("function", "");

		}
		return "customer/deliveryCustomer";
	}

	/**
	 * 收货客户信息查询
	 * 
	 * @param page
	 * @param rows
	 * @param customer_name
	 * @param customer_tel
	 * @param customer_address
	 * @param User_address
	 * @param request
	 * @param delivery_cus_name
	 * @return
	 */
	@RequestMapping(params = "method=getDeliveryCustomers")
	public @ResponseBody
	Map<String, Object> getRemarks(String page, String rows,
			String customer_name, String customer_tel, String customer_address,
			String User_address, HttpServletRequest request,
			String delivery_cus_name) {
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
		User user = (User) request.getSession().getAttribute(
				SessionInfo.SessionName);

		int total = rms.getDeliveryCon(customer_name, customer_tel,
				customer_address, user.getUser_address(), delivery_cus_name,
				user);
		List<DeliveryCustomer> list = rms.getDeliveryC((page2 - 1) * rows1,
				rows1, customer_name, customer_tel, customer_address,
				user.getUser_address(), delivery_cus_name, user);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", total);
		map.put("rows", list);
		return map;
	}

	/**
	 * 跳转修改页面
	 * 
	 * @param cid
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "method=UpdateDeliverycustomer")
	public String UpdateDeliverycustomer(String cid, HttpServletRequest request) {
		request.setAttribute("cid", cid);
		return "customer/UpdateDeliverycustomer";
	}

	/**
	 * 跳转收货客户添加页面
	 * 
	 * @return
	 */
	@RequestMapping(params = "method=getDeliveryCustomer")
	public String addremarkMap() {
		return "customer/addDeliveryCustomer";
	}

	@RequestMapping(params = "method=getDelivery2")
	public String getDelivery2() {
		return "customer/deliveryCustomer";
	}

	/**
	 * 添加客户信息
	 * 
	 * @param customer
	 * @return
	 */
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

	/**
	 * 获取要修改的收货客户信息
	 * 
	 * @param cid
	 * @return
	 */
	@RequestMapping(params = "method=getDeliveryCustomerbyid")
	public @ResponseBody
	DeliveryCustomer getDeliveryCustomerbyid(String cid) {
		return rms.getDeliveryCustomerbyid(cid);
	}

	/**
	 * 更新要修改的信息
	 * 
	 * @param customer
	 * @param session
	 * @return
	 */
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
	 * 删除发货客户客户
	 */
	@RequestMapping(params = "method=deleteDeliveryCustomer")
	public @ResponseBody
	boolean deleteDeliveryCustomer(String[] id) {
		return rms.deleteDeliveryCustomer(id);
	}

	/**
	 * 不知道什么用
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
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

	@RequestMapping(params = "method=getUpdateLocationCustomer")
	public @ResponseBody List<DeliveryCustomer> getUpdateLocationCustomer() {

		return rms.getUpdateLocationRemarkCustomer();

	}

	@RequestMapping(params = "method=updateLocationCustomer")
	public @ResponseBody JSONObject updateLocationCustomer(DeliveryCustomer customer,
											 String kkk) {
		int i = rms.updateLocationRemarkCustomer(customer);
		JSONObject jsonObject  = new JSONObject();
		jsonObject.put("success",i);
		return jsonObject;

	}


}
