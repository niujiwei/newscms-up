package com.jy.action;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.jy.common.CustomerExcelInfo;
import com.jy.common.ExportUtils;
import com.jy.common.SessionInfo;
import com.jy.common.UUIDUtils;
import com.jy.dao.Json;
import com.jy.model.Aging;
import com.jy.model.Customer;
import com.jy.model.Remark;
import com.jy.model.RemarkRange;
import com.jy.model.ShippingOrder;
import com.jy.model.User;
import com.jy.service.RemarkMapService;
import com.jy.service.UserInfoService;

/**
 * 发货客户和时效管理
 * 
 * @author 90
 * 
 */
@Controller
@RequestMapping(value = "/remarkMap.do")
public class RemarkMapController {

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
		File newfile = filez.createTempFile("customerOrder", prefix, filez);
		String filesname = newfile.toString();
		filesname = filesname.substring(filesname.lastIndexOf("\\") + 1,
				filesname.length());
		request.setAttribute("filesname", filesname);
		myfile.transferTo(newfile);
		return "customer/customerMap";
	}

	/**
	 * 导入客户信息页面跳转
	 */
	@RequestMapping(params = "method=imp")
	public String goimp() {
		return "customer/customerMap";
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
			HttpSession session, String pid) {
		String filepath = request.getSession().getServletContext()
				.getRealPath("WebRoot/orderFiles/" + filename);
		CustomerExcelInfo oef = new CustomerExcelInfo(rms);
		User user = (User) session.getAttribute(SessionInfo.SessionName);
		String flag = "";
		try {
			flag = oef.impExcel(filepath, user.getRealName(), pid);
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
	 * 导出客户信息
	 */
	@RequestMapping(params = "method=outPutSendCustomers")
	public @ResponseBody
	String outPutSendCustomers(String customer_num, String customer_name,
			String customer_people, String customer_tel, String[] headtitle,
			String[] fieldName, String[] dataIds, HttpServletResponse response,
			HttpSession session, String customer_address,
			String customer_business, String customer_profile,
			String customer_experience, String customer_operation,
			String customer_operationtime) {
		String filename = "发货客户信息导出";
		if (dataIds.length == 0) {
			dataIds = null;
		}
		User user = (User) session.getAttribute(SessionInfo.SessionName);
		List<Customer> list = rms.getOutPutCustomers(customer_name,
				customer_tel, customer_address, user, dataIds);
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
			ExportUtils.outputHeaders(headtitle, workbook, "发货客户信息导出");
			// 调用工具类生成内容
			ExportUtils.outputColums(fieldName, list, workbook, 1, "发货客户信息导出");
			workbook.write(ouputStream);
			ouputStream.flush();
			ouputStream.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	};

	/**
	 * 跳转到客户管理页面
	 * 
	 * @param menu_id
	 * @param session
	 * @return
	 */
	@RequestMapping(params = "method=getaddRemarkMap")
	public String remarkMap(String menu_id, HttpSession session) {
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
		return "customer/customers";
	}

	/**
	 * 时效提醒功能打开
	 * 
	 */

	public List<ShippingOrder> oAging() {
		return rms.getShippingAging();

	}

	/**
	 * 新客户信息跳转页面
	 * 
	 * @return
	 */
	@RequestMapping(params = "method=getRemarkMap")
	public String addremarkMap() {
		return "customer/addcustomers";
	}

	/**
	 * 删除节点信息
	 */
	@RequestMapping(params = "method=deleteRemark")
	public @ResponseBody
	boolean deleteRemark(String[] id) {
		return rms.deleteRemark(id);
	}

	/**
	 * 保存客户信息
	 * 
	 * @param customer
	 * @return
	 */
	@RequestMapping(params = "method=saveCustomer")
	public @ResponseBody
	Json saveCustomer(Customer customer) {
		int i = rms.saveCustomer(customer);
		Json json = new Json();
		if (i > 0) {
			json.setFlag(true);
		} else {
			json.setFlag(false);
		}
		return json;
	}

	/**
	 * 查询发货客户信息
	 * 
	 * @param session
	 * @param page
	 * @param rows
	 * @param customer_name
	 * @param customer_tel
	 * @param customer_address
	 * @return
	 */
	@RequestMapping(params = "method=getRemarks")
	public @ResponseBody
	Map<String, Object> getRemarks(HttpSession session, String page,
			String rows, String customer_name, String customer_tel,
			String customer_address) {
		User user = (User) session.getAttribute(SessionInfo.SessionName);
		// String didFaterId = csi.getDepartmentFatherId(user.getId());
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
		// 查询信息
		int total = rms.getCount(customer_name, customer_tel, customer_address,
				user);
		List<Customer> list = rms.getRemarks((page2 - 1) * rows1, rows1,
				customer_name, customer_tel, customer_address, user);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", total);
		map.put("rows", list);
		return map;
	}

	@RequestMapping(params = "method=remarkMapmodify")
	public String remarkMapmodify(String id, HttpServletRequest request) {
		request.setAttribute("id", id);
		return "map/addremarkMap";
	}

	/**
	 * 获取到要修改的客户信息跳转修改页面
	 * 
	 * @param cid
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "method=updateCustomer")
	public String updateCustomer(String cid, HttpServletRequest request) {
		request.setAttribute("cid", cid);
		return "customer/Updatecustomers";
	}

	/**
	 * 检查发货客户编码是否重复
	 * 
	 * @param customer_num
	 * @return
	 */
	@RequestMapping(params = "method=getcustomer_num")
	public @ResponseBody
	Json getcustomer_num(String customer_num) {
		int i = rms.getcustomer_num(customer_num);
		Json json = new Json();
		if (i > 0) {
			json.setFlag(false);
		} else {
			json.setFlag(true);
		}
		return json;
	}

	/**
	 * 获取到要更新客户信息详细
	 * 
	 * @param cid
	 * @return
	 */
	@RequestMapping(params = "method=getCustomerbyid")
	public @ResponseBody
	Customer getCustomerbyid(String cid) {
		return rms.getCustomerbyid(cid);
	}

	/**
	 * 更新发货客户信息
	 * 
	 * @param customer
	 * @param session
	 * @return
	 */
	@RequestMapping(params = "method=savefroupdateCustomer")
	public @ResponseBody
	Json savefroupdateCustomer(Customer customer, HttpSession session) {
		User user = (User) session.getAttribute(SessionInfo.SessionName);
		customer.setCustomer_operation(user.getRealName());
		int i = rms.updateCustomer(customer);
		Json json = new Json();
		if (i > 0) {
			json.setFlag(true);
		} else {
			json.setFlag(false);
		}
		return json;
	}

	/**
	 * 检查客户姓名重复
	 * 
	 * @param customer_name
	 * @return
	 */
	@RequestMapping(params = "method=getcustomer_name")
	public @ResponseBody
	Json getcustomer_name(String customer_name) {
		int i = rms.getcustomer_name(customer_name);
		Json json = new Json();
		if (i > 0) {
			json.setFlag(false);
		} else {
			json.setFlag(true);
		}
		return json;
	}

	/**
	 * 跳转时效管理页面
	 * 
	 * @param menu_id
	 * @param session
	 * @return
	 */
	@RequestMapping(params = "method=toAging")
	public String toAging(String menu_id, HttpSession session) {
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
		return "customer/Aging";
	}

	/**
	 * 查询时效管理信息
	 * 
	 * @param page
	 * @param rows
	 * @param customer_name
	 * @param customer_address
	 * @param user_addres
	 * @param requelst
	 * @return
	 */
	@RequestMapping(params = "method=getAging")
	public @ResponseBody
	Map<String, Object> getAging(String page, String rows,
			String customer_name, String customer_address, String user_addres,
			HttpServletRequest requelst) {
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
		User user = (User) requelst.getSession().getAttribute(
				SessionInfo.SessionName);
		// String didFaterId = csi.getDepartmentFatherId(user.getId());
		int total = rms.getAgingCount(customer_name, customer_address,
				user.getUser_address(), user);
		List<Aging> list = rms.getAgings((page2 - 1) * rows1, rows1,
				customer_name, customer_address, user.getUser_address(), user);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", total);
		map.put("rows", list);
		return map;
	}

	/**
	 * 跳转到添加时效的页面
	 * 
	 * @return
	 */
	@RequestMapping(params = "method=tosaveAging")
	public String tosaveTime() {
		return "customer/saveAging";
	}

	/**
	 * 检查时效是否存在
	 * 
	 * @param aging
	 * @return
	 */
	@RequestMapping(params = "method=saveCheckAging")
	public @ResponseBody
	boolean saveCheckAging(Aging aging) {
		return rms.saveCheckAging(aging);

	}

	/**
	 * 保存时效信息
	 * 
	 * @param aging
	 * @param session
	 * @return
	 */
	@RequestMapping(params = "method=saveAging")
	public @ResponseBody
	Json saveTime(Aging aging, HttpSession session) {
		Json json = new Json();
		User user = (User) session.getAttribute(SessionInfo.SessionName);
		aging.setAging_operator(user.getUsername());
		aging.setAging_id(UUIDUtils.getUUID());
		int i = rms.saveAging(aging);
		if (i >= 1) {
			json.setFlag(true);
			return json;
		}
		json.setFlag(false);
		return json;
	}

	/**
	 * 修改时效跳转页面
	 * 
	 * @param cid
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "method=setAging")
	public String setTime(String cid, HttpServletRequest request) {
		request.setAttribute("flg", cid);
		return "customer/EditAging";
	}

	/**
	 * 获取修改的时效信息
	 * 
	 * @param aid
	 * @return
	 */
	@RequestMapping(params = "method=getsetAging")
	public @ResponseBody
	Aging getsetAging(String aid) {
		return rms.getsetAging(aid);
	}

	/**
	 * 更新时效
	 * 
	 * @param aging
	 * @param session
	 * @return
	 */
	@RequestMapping(params = "method=updateAging")
	public @ResponseBody
	Json updateAging(Aging aging, HttpSession session) {
		User user = (User) session.getAttribute(SessionInfo.SessionName);
		aging.setAging_operator(user.getUsername());
		int i = rms.updateAging(aging);
		Json json = new Json();
		if (i >= 1) {
			json.setFlag(true);
			return json;
		}
		json.setFlag(false);
		return json;
	}

	/**
	 * 删除时效
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "method=deleteAging")
	public @ResponseBody
	Json deleteAging(String[] id) {
		Json json = new Json();
		int i = rms.deleteAging(id);
		if (i >= 1) {
			json.setFlag(true);
			return json;
		}
		json.setFlag(false);
		return json;
	}

	/**
	 * 获取发货客户信息
	 * 
	 * @param name
	 * @return
	 */
	@RequestMapping(params = "method=getCustomers")
	public @ResponseBody
	String getCustomers(String name) {
		System.out.println(name);
		List<Customer> list = rms.getCustomersByName(name);
		JSONObject jb = null;
		JSONArray jsons = new JSONArray();
		for (Customer d : list) {
			jb = new JSONObject();
			jb.put("id", d.getCustomer_id());
			jb.put("name", d.getCustomer_name());
			jsons.add(jb);
		}
		return jsons.toString();
	}

	/**
	 * 检查 节点信息
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "method=checkRemark")
	public @ResponseBody
	boolean checkRemark(String id) {
		boolean b = rms.checkRemark(id);
		return b;
	}

	/**
	 * 获取指定标注
	 */
	@RequestMapping(params = "method=getRemark")
	public @ResponseBody
	Customer getRemark(String id) {
		return rms.getRemark(id);
	}

	/**
	 * 检查标注名
	 * 
	 * @param name
	 * @return
	 */
	@RequestMapping(params = "method=checkName")
	public @ResponseBody
	boolean checkName(String name) {
		boolean b = rms.checkName(name);
		return b;
	}

	/*
	 * // 获取标注类型信息
	 * 
	 * @RequestMapping(params = "method=getTypes") public @ResponseBody
	 * List<Map<String, Object>> getTypes() { List<RemarkType> type =
	 * rms.getTypes(); List<Map<String, Object>> list = new
	 * ArrayList<Map<String, Object>>(); Map<String, Object> map = null; for
	 * (RemarkType rt : type) { map = new HashMap<String, Object>();
	 * map.put("id",rt.getRemark_type_id()); map.put("text",
	 * rt.getRemark_type_name()); if(rt.getRemark_type_name().equals("无")){
	 * map.put("selected", true);
	 * 
	 * } list.add(map); } return list; }
	 */
	// 获取标注范围信息
	@RequestMapping(params = "method=getRange")
	public @ResponseBody
	List<Map<String, Object>> getRange() {
		List<RemarkRange> range = rms.getRange();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		for (RemarkRange rr : range) {
			map = new HashMap<String, Object>();
			map.put("id", rr.getRemark_range_id());
			map.put("text", rr.getRemark_range_name());
			/*
			 * if(rr.getRemark_range_name().equals("100米")){ map.put("selected",
			 * true); }
			 */
			list.add(map);
		}
		return list;
	}

	@RequestMapping(params = "method=getOneRange")
	public @ResponseBody
	int getOneRange(String id) {
		String name = rms.getOneRange(id);
		return Integer.parseInt(name.substring(0, name.length() - 1));
	}

	@RequestMapping(params = "method=getModifyPeople")
	public @ResponseBody
	String getModifyPeople(Integer people) {
		return rms.getModifyPeople(people);
	}

	/**
	 * 查询标记名称
	 */
	@RequestMapping(params = "method=getRemarkName")
	public @ResponseBody
	String getRemarkName(String name, String state) {
		List<Remark> list = rms.getRemarkName(name, state);
		JSONObject jb = null;
		JSONArray jsons = new JSONArray();
		for (Remark r : list) {
			jb = new JSONObject();
			jb.put("id", r.getRemark_id());
			jb.put("name", r.getRemark_name());
			jsons.add(jb);
		}
		return jsons.toString();
	}

	/**
	 * 查询标记联系方式
	 */
	@RequestMapping(params = "method=getRemarktel")
	public @ResponseBody
	String getRemarktel(String name, String state) {
		List<Remark> list = rms.getRemarkName(name, state);
		JSONObject jb = null;
		JSONArray jsons = new JSONArray();
		for (Remark r : list) {
			jb = new JSONObject();
			jb.put("id", r.getRemark_id());
			jb.put("name", r.getRemark_tel());
			jsons.add(jb);
		}
		return jsons.toString();
	}

	/**
	 * 查询标记名称
	 */
	@RequestMapping(params = "method=getRemarkaddress")
	public @ResponseBody
	String getRemarkaddress(String name, String state) {
		List<Remark> list = rms.getRemarkName(name, state);

		JSONObject jb = null;
		JSONArray jsons = new JSONArray();
		for (Remark r : list) {
			jb = new JSONObject();
			jb.put("id", r.getRemark_id());
			jb.put("name", r.getRemark_address());
			jsons.add(jb);
		}
		return jsons.toString();
	}

	/**
	 * 添加一个标注
	 */
	@RequestMapping(params = "method=saveRemark")
	public String saveremark(Customer customer, String kkk) {
		customer.setCustomer_id(UUIDUtils.getUUID());
		rms.addRemark(customer);
		if (kkk.equals("save")) {
			return "map/remarkMap";
		} else {
			return "map/addremarkMap";

		}

	}

	/**
	 * 更新一个标注
	 */
	@RequestMapping(params = "method=updateRemark")
	public String updateremark(Customer customer, String kkk) {
		rms.updateRemark(customer);
		if (kkk.equals("save")) {
			return "map/remarkMap";
		} else {
			return "map/addremarkMap";

		}
	}

	public static boolean isNumeric0(String str) {
		for (int i = str.length(); --i >= 0;) {
			int chr = str.charAt(i);
			if (chr < 48 || chr > 57)
				return false;
		}
		return true;
	}

	/**
	 * 新的添加
	 * 
	 * @return
	 */
	@RequestMapping(params = "method=newAddAging")
	public String newAddAging() {
		return "customer/newSaveAging";
	}

	/**
	 * 新的添加时效
	 * 
	 * @param aging
	 * @param session
	 * @return
	 */
	@RequestMapping(params = "method=newSaveAgingInfo")
	public @ResponseBody
	String newSaveAgingInfo(Aging aging, HttpSession session) {
		int inum = 0;// 应插入的条数
		int snum = 0;// 实际条数
		int ii = 0;// 是否插入成功
		StringBuffer message = new StringBuffer();
		User user = (User) session.getAttribute(SessionInfo.SessionName);
		if (aging != null) {
			String customerids = aging.getAging_cutomer_id();
			String countynames = aging.getAging_countyname();
			if (customerids != null) {
				String[] aCustomer = customerids.split(",");
				if (countynames != null) {// 选择啦一个活多个区

					String[] aCountyname = countynames.split(",");
					for (int i = 0; i < aCustomer.length; i++) {
						for (int j = 0; j < aCountyname.length; j++) {
							if (!aCustomer[i].equals("1")) {

								inum = aCustomer.length * aCountyname.length;
								String address = rms.getAgingCityInfo(
										aging.getAging_province(),
										aging.getAging_city(),
										Integer.parseInt(aCountyname[j]));// 获取省市县
								Aging oAging = new Aging();
								oAging.setAging_city(aging.getAging_city());// 市
								oAging.setAging_province(aging
										.getAging_province());// 省
								oAging.setAging_county(Integer
										.parseInt(aCountyname[j]));// 县
								oAging.setAging_address(address);// 地区
								oAging.setAging_time(aging.getAging_time());// 时效
								oAging.setAging_day(aging.getAging_day());// 隔日次日当日
								oAging.setAging_cutomer_id(aCustomer[i]);// 发货客户
								oAging.setStar_time(aging.getStar_time());// 触发开始时间
								oAging.setEnd_time(aging.getEnd_time());// 触发结束时间
								oAging.setAging_operator(user.getUsername());
								oAging.setAging_id(UUIDUtils.getUUID());
								boolean b = rms.saveCheckAging(oAging);
								if (b) {
									// 要进行判断
									ii = rms.saveAging(oAging);// 插入
									if (ii > 0) {
										snum++;
									}
								} else {
									Customer customer = rms
											.getRemark(aCustomer[i]);
									if (customer != null) {
										String str = customer
												.getCustomer_name()
												+ "终到位置："
												+ address
												+ "触发时间："
												+ aging.getStar_time()
												+ "-"
												+ aging.getEnd_time() + "#";
										message.append(str);
									}
								}
							}
						}
					}
				} else {// 没有选择区
					for (int i = 0; i < aCustomer.length; i++) {
						inum = aCustomer.length;
						Aging oAging = new Aging();
						oAging.setAging_city(aging.getAging_city());// 市
						oAging.setAging_province(aging.getAging_province());// 省
						// oAging.setAging_county(Integer.parseInt(aCountyname[j]));//县
						oAging.setAging_address(aging.getAging_address());// 终到位置
						oAging.setAging_time(aging.getAging_time());// 时效
						oAging.setAging_day(aging.getAging_day());// 隔日次日当日
						oAging.setStar_time(aging.getStar_time());// 触发开始时间
						oAging.setEnd_time(aging.getEnd_time());// 触发结束时间
						oAging.setAging_operator(user.getUsername());
						oAging.setAging_id(UUIDUtils.getUUID());
						oAging.setAging_cutomer_id(aCustomer[i]);// 发货客户
						boolean b = rms.saveCheckAging(oAging);
						if (b) {
							// 要进行判断
							ii = rms.saveAging(oAging);// 插入
							if (ii > 0) {
								snum++;
							}
						} else {
							Customer customer = rms.getRemark(aCustomer[i]);
							if (customer != null) {
								String str = customer.getCustomer_name()
										+ "--终到位置：" + aging.getAging_address()
										+ "触发时间：" + aging.getStar_time() + "-"
										+ aging.getEnd_time() + "#";
								message.append(str);
							}

						}
					}
				}
			}
		}
		JSONObject jb = new JSONObject();
		JSONArray jsons = new JSONArray();
		jb.put("inum", inum);
		jb.put("snum", snum);
		jb.put("message", message.toString());
		jsons.add(jb);
		return jsons.toString();
	}

}
