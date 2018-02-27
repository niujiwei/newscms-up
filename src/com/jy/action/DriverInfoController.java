package com.jy.action;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.jy.common.DriverExcelInfo;
import com.jy.common.ExportUtils;
import com.jy.common.SessionInfo;
import com.jy.common.UUIDUtils;
import com.jy.dao.Json;
import com.jy.excel.bean.DriverExcel;
import com.jy.model.City_info;
import com.jy.model.Driver;
import com.jy.model.DriverToOrder;
import com.jy.model.JySuppliers;
import com.jy.model.PositionInfo;
import com.jy.model.User;
import com.jy.service.DriverInfoService;
import com.jy.service.ShippingOrderInfoService;
import com.jy.thread.FenPeiSaveHistoryThread;

/**
 * 司机管理
 * 
 * @author 90
 * 
 */
@Controller
@RequestMapping(value = "/driver.do")
public class DriverInfoController {
	@Resource
	private DriverInfoService dsi;
	@Resource
	private ShippingOrderInfoService csi;

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
		File newfile = filez.createTempFile("driverOrder", prefix, filez);
		String filesname = newfile.toString();
		filesname = filesname.substring(filesname.lastIndexOf("\\") + 1,
				filesname.length());
		request.setAttribute("filesname", filesname);
		myfile.transferTo(newfile);
		return "driver/driveriImplinplan";
	}

	/**
	 * 导入客户信息页面跳转
	 */
	@RequestMapping(params = "method=imp")
	public String goimp() {
		return "driver/driveriImplinplan";
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
		DriverExcelInfo oef = new DriverExcelInfo();
		User user = (User) session.getAttribute(SessionInfo.SessionName);
		String flag = "";
		try {
			flag = oef.impExcel(filepath, user.getUsername(), pid);
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
	 * 跳转到司机管理页面
	 * 
	 * @return
	 */
	@RequestMapping(params = "method=getDriverInfo")
	public String getDriverMan() {
		return "driver/DriverInfo";
	}

	/**
	 * 新增跳转到运单分配的司机管理页面
	 * 
	 * @return
	 */
	@RequestMapping(params = "method=getDriverInfoNews")
	public String getDriverMans() {
		return "driver/DriverInfoNews";
	}

	/**
	 * 司机管理查询、统计司机数量时,多加了两个条件查询(货运编号和出货订单号),多加了两个表的联合查询jy_drivertoorder、
	 * jy_shiping_order,且进行去除重复值
	 * 
	 * @param rows
	 * @param page
	 * @param driver_name
	 * @param driver_suppliers
	 * @param driver_phone
	 * @param driver_cardnumber
	 * @param driver_address
	 * @param id
	 * @param request
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(params = "method=getDriver")
	public @ResponseBody
	Map getDriverInfo(String rows, String page, String driver_name,
			String driver_suppliers, String driver_phone,
			String driver_cardnumber, String driver_address,
			String shiping_order_num, String shiping_order_goid, String id,
			HttpServletRequest request) {
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
		Map<String, Object> map = new HashMap<String, Object>();
		List<Driver> list = dsi.getDriverInfo((page1 - 1) * rows1, rows1,
				driver_name, driver_suppliers, driver_phone, driver_cardnumber,
				driver_address, shiping_order_num, shiping_order_goid,
				user.getId(), user);
		int count = dsi.getDriver(driver_name, driver_suppliers, driver_phone,
				driver_cardnumber, driver_address, shiping_order_num,
				shiping_order_goid, user.getId(), user);
		map.put("total", count);
		map.put("rows", list);
		return map;
	}

	/**
	 * 司机管理select2查询供应商信息
	 * 
	 * @param num
	 * @return
	 */
	@RequestMapping(params = "method=getSuppliers")
	public @ResponseBody
	String getSuppliers(String num) {
		List<JySuppliers> list = dsi.getSuppliers(num);
		JSONObject jb = null;
		JSONArray jsons = new JSONArray();
		for (JySuppliers d : list) {
			jb = new JSONObject();
			jb.put("id", d.getSuppliersId());
			jb.put("name", d.getSuppliersName());
			jsons.add(jb);
		}
		return jsons.toString();

	}

	/**
	 * 司机管理删除司机信息
	 * 
	 * @param del
	 * @return
	 */
	@RequestMapping(params = "method=deleteDriver")
	public @ResponseBody
	Json delTruck(String[] del) {
		Json json = new Json();
		int truck = dsi.deleteDriver(del);
		if (truck > 0) {
			dsi.deleteDriverCityInfo(del);
			json.setFlag(true);
			return json;
		}
		json.setFlag(false);
		return json;
	}

	/**
	 * 司机管理修改跳转页面
	 * 
	 * @param pid
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "method=EditDriverPage")
	public String EditDriver(String pid, HttpServletRequest request) {
		request.setAttribute("flg", pid);
		return "driver/EditDriver";
	}

	/**
	 * 司机管理添加跳转页面
	 * 
	 * @return
	 */
	@RequestMapping(params = "method=addDriver")
	public String addDriver() {
		return "driver/AddDriver";
	}

	/**
	 * 司机管理司机信息添加
	 * 
	 * @param d
	 * @return
	 */
	@RequestMapping(params = "method=saveDriver")
	public @ResponseBody
	Json saveDriver(Driver d) {
		String type = d.getDriver_cartype();
		d.setDriver_cartype(type);
		Json json = new Json();
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy:MM:dd hh:mm:ss");
		d.setDriver_createtime(dateFormat.format(new Date()));
		d.setDriver_id(UUIDUtils.getUUID());
		int truck = dsi.saveDriver(d);
		saveDriverCityInfo(d);
		if (truck > 0) {
			json.setFlag(true);
			return json;
		} else {
			json.setFlag(false);
			return json;
		}
	}

	/**
	 * 司机管理分配运单信息
	 * 
	 * @param pid
	 * @param orders
	 * @return
	 */
	@RequestMapping(params = "method=saveDriverOrders")
	public @ResponseBody
	Json saveDriverOrders(String pid,String equipmentNum,
			String userName ,String[] orders) {
		Json json = new Json();
		if (orders != null) {
			List<DriverToOrder> orderlist = new ArrayList<DriverToOrder>();
			for (int i = 0; i < orders.length; i++) {
				DriverToOrder order = new DriverToOrder();
				order.setId(UUIDUtils.getUUID());
				order.setDriver_id(pid);
				order.setOrder_id(orders[i]);
				order.setEquipmentNum(equipmentNum);
				order.setUserName(userName);
				orderlist.add(order);
				
			}
			int i = dsi.saveDrivertoOrder(orderlist);
			if (i > 0) {
				int ii = dsi.updateShipperorderState(orders);
				if (ii > 0) {
					new Thread(new FenPeiSaveHistoryThread(orders, csi))
							.start();
				}
				json.setFlag(true);
			}

			return json;

		} else {
			json.setFlag(false);
			return json;

		}
	}

	/**
	 * 司机管理获取要修改的司机信息
	 * 
	 * @param driver_id
	 * @return
	 */
	@RequestMapping(params = "method=getUpdateDriver")
	public @ResponseBody
	Driver getUpdateDriver(String driver_id) {
		Driver der = dsi.getUpdateDriver(driver_id);
		List<City_info> listDrivers = dsi.getDriverCityInfo(driver_id);
		int listCount = listDrivers.size();
		if (listCount == 0) {
			der.setDriver_province(null);
			der.setDriver_city(null);
			der.setDriver_countys(new Integer[] {});
			return der;
		} else {
			Integer[] county = new Integer[listCount];
			for (int i = 0; i < listCount; i++) {
				county[i] = listDrivers.get(i).getCounty();
			}
			der.setDriver_province(listDrivers.get(0).getProvince());
			der.setDriver_city(listDrivers.get(0).getCity());
			der.setDriver_countys(county);

		}
		return der;
	}

	/**
	 * 司机管理修改司机信息
	 * 
	 * @param d
	 * @param session
	 * @return
	 */
	@RequestMapping(params = "method=updateDriver")
	public @ResponseBody
	Json updateDriver(Driver d, HttpSession session) {
		String type = d.getDriver_cartype();
		User user = (User) session.getAttribute(SessionInfo.SessionName);
		Json json = new Json();
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy:MM:dd hh:mm:ss");
		d.setDriver_updatedate(dateFormat.format(new Date()));
		d.setDriver_updatepeople(user.getUsername());
		int truck = 0;
		String[] did = new String[1];
		did[0] = d.getDriver_id();
		try {
			truck = dsi.updateDriver(d);
			dsi.deleteDriverCityInfo(did);
			saveDriverCityInfo(d);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (truck > 0) {
			json.setFlag(true);
			return json;
		}
		json.setFlag(false);
		return json;
	}

	/**
	 * 司机终到位置省----已过时
	 * 
	 * @return
	 */
	@RequestMapping(params = "method=getFinalposition")
	public @ResponseBody
	List<Map<String, Object>> getFinalposition() {
		List<PositionInfo> list = dsi.getFinalposition();
		List<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		for (PositionInfo tc : list) {
			map = new HashMap<String, Object>();
			map.put("id", tc.getId());
			map.put("text", tc.getAreaname());
			map.put("oneid", tc.getOneid());
			map.put("twoid", tc.getTwoid());
			map.put("threeid", tc.getThreeid());
			lists.add(map);
		}
		return lists;
	}

	/**
	 * 司机终到位置市--已经过时
	 * 
	 * @param oneid
	 * @return
	 */
	@RequestMapping(params = "method=getFinalpositionCity")
	public @ResponseBody
	List<Map<String, Object>> getFinalpositionCity(Integer oneid) {
		List<PositionInfo> list = dsi.getFinalpositionCity(oneid);
		List<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		if (list != null) {
			for (PositionInfo tc : list) {
				map = new HashMap<String, Object>();
				// System.out.println(tc.getTwoid());
				map.put("id", tc.getId());
				map.put("text", tc.getAreaname());
				map.put("oneid", tc.getOneid());
				map.put("twoid", tc.getTwoid());
				map.put("threeid", tc.getThreeid());
				lists.add(map);
			}
			System.out.println("city:" + oneid + "\t" + list.size());
		}
		return lists;
	}

	/**
	 * 司机终到位置区--已过时
	 * 
	 * @param oneid
	 * @param twoid
	 * @return
	 */
	@RequestMapping(params = "method=getFinalpositionCounty")
	public @ResponseBody
	List<Map<String, Object>> getFinalpositionCounty(Integer oneid,
			Integer twoid) {
		List<PositionInfo> list = dsi.getFinalpositionCounty(oneid, twoid);
		List<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		for (PositionInfo tc : list) {
			map = new HashMap<String, Object>();
			map.put("i" + "d", tc.getId());
			map.put("text", tc.getAreaname());
			map.put("oneid", tc.getOneid());
			map.put("twoid", tc.getTwoid());
			map.put("threeid", tc.getThreeid());
			lists.add(map);
		}
		return lists;
	}

	// 车辆信息检测
	@RequestMapping(params = "method=ptype")
	public @ResponseBody
	Driver ptype(String car) {
		System.out.println(car);
		Driver der = dsi.ptype(car);
		return der;
	}

	/**
	 * 旧的获取省市县
	 * 
	 * @param province
	 * @param city
	 * @param count
	 * @return
	 */
	@RequestMapping(params = "method=getNum")
	public @ResponseBody
	PositionInfo getNum(String province, String city, String count) {
		PositionInfo position = null;
		List<PositionInfo> posi = dsi.getNum(province, city, count);
		boolean bool1 = false;
		boolean bool2 = false;
		@SuppressWarnings("unused")
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

	/**
	 * 省市县tree
	 * 
	 * @param oneid
	 * @return
	 */
	@RequestMapping(params = "method=getNewFinalPositionAddress")
	public @ResponseBody
	List<Map<String, Object>> getNewFinalPositionAddress(String id) {
		
		if(id==null){
			id="1";
			
		}
		List<City_info> list = dsi.getNewFinalPositionAddress(id);
		List<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		for (City_info tc : list) {
			map = new HashMap<String, Object>();
			map.put("id", tc.getCitycode());
			map.put("text", tc.getCityname());
			map.put("citye_parent_id", tc.getCitye_parent_id());
			map.put("state", "closed");
			map.put("checked", false);
			map.put("children", "");
			map.put("attributes", "");
			map.put("city_type", tc.getCity_type());
			lists.add(map);
		}
		return lists;
	}
	
	
	/**
	 * 改终到位置省
	 * 
	 * @param oneid
	 * @return
	 */
	@RequestMapping(params = "method=getNewFinalPositionCounty")
	public @ResponseBody
	List<Map<String, Object>> getNewFinalPositionCounty() {
		List<City_info> list = dsi.getNewFinalPositionCounty();
		List<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		for (City_info tc : list) {
			map = new HashMap<String, Object>();
			map.put("id", tc.getCitycode());
			map.put("text", tc.getCityname());
			map.put("citye_parent_id", tc.getCitycode());
			map.put("city_type", tc.getCity_type());
			lists.add(map);
		}
		return lists;
	}

	/**
	 * 省市县改终到位置市
	 */
	@RequestMapping(params = "method=getNewFinalpositionCity")
	public @ResponseBody
	List<Map<String, Object>> getNewFinalpositionCity(Integer citye_parent_id) {
		List<City_info> list = dsi.getNewFinalpositionCity(citye_parent_id);
		List<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		for (City_info tc : list) {
			map = new HashMap<String, Object>();
			map.put("id", tc.getCitycode());
			map.put("text", tc.getCityname());
			map.put("citye_parent_id", tc.getCitycode());
			map.put("city_type", tc.getCity_type());
			lists.add(map);
		}
		return lists;
	}

	/**
	 * 终到位置获取区
	 */
	@RequestMapping(params = "method=getNewFinalpositionCounty")
	public @ResponseBody
	List<Map<String, Object>> getNewFinalpositionCounty(Integer citye_parent_id) {
		List<City_info> list = dsi.getNewFinalpositionCounty(citye_parent_id);
		List<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		for (City_info tc : list) {
			map = new HashMap<String, Object>();
			map.put("id", tc.getCitycode());
			map.put("text", tc.getCityname());
			map.put("citye_parent_id", tc.getCitycode());
			map.put("city_type", tc.getCity_type());
			lists.add(map);
		}
		return lists;

	}

	/**
	 * 司机信息修改保存新的省市县
	 * 
	 * @param driver
	 * @return
	 */
	public int saveDriverCityInfo(Driver driver) {
		List<Driver> listDriver = new ArrayList<Driver>();
		if (driver.getDriver_countys() == null) {
			Driver diDriver = new Driver();
			diDriver.setId(UUIDUtils.getUUID());
			diDriver.setDriver_id(driver.getDriver_id());
			diDriver.setDriver_province(driver.getDriver_province());
			diDriver.setDriver_city(driver.getDriver_city());
			listDriver.add(diDriver);
		} else {
			for (int i = 0; i < driver.getDriver_countys().length; i++) {
				Driver diDriver = new Driver();
				diDriver.setId(UUIDUtils.getUUID());
				diDriver.setDriver_id(driver.getDriver_id());
				diDriver.setDriver_province(driver.getDriver_province());
				diDriver.setDriver_city(driver.getDriver_city());
				diDriver.setDriver_county(driver.getDriver_countys()[i]);
				listDriver.add(diDriver);
			}
		}
		int j = dsi.saveDriverCityInfo(listDriver);
		return j;

	}

	/**
	 * 司机管理司机信息导出
	 * 
	 * @param headtitle
	 * @param fieldName
	 * @param response
	 * @param session
	 * @param driver_name
	 * @param driver_suppliers
	 * @param driver_phone
	 * @param driver_cardnumber
	 * @param driver_address
	 * @return
	 */
	@RequestMapping(params = "method=outDriverExcel")
	public @ResponseBody
	String allMessageOutPut(String[] headtitle1, String[] fieldName1,
			String[] dataIds, String driver_name, String driver_suppliers,
			String driver_phone, String driver_cardnumber,
			String driver_address, String shiping_order_num,
			String shiping_order_goid, String id, HttpServletRequest request,
			HttpServletResponse response) {
		String filename = "司机信息导出";
		User user = (User) request.getSession().getAttribute(
				SessionInfo.SessionName);
		List<Driver> list = dsi.getDriverInfoExcel(driver_name,
				driver_suppliers, driver_phone, driver_cardnumber,
				driver_address, shiping_order_num, shiping_order_goid, user,
				user.getId(), dataIds);
		InputStream in = AbnormalReportController.class
				.getResourceAsStream("/../exportData/driverData.properties");
		Map<String, List<String>> map = ExportUtils.getHeadTitle(in);//通用获取headtitle fieldName
		
		List<String> headTitle =map.get("headTitle");
		List<String> fieldName =map.get("fieldName");
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
			ExportUtils.outputHeaders((String[]) headTitle.toArray(new String[headTitle.size()]), workbook, "司机信息导出");
			// 调用工具类生成内容
			ExportUtils.outputColums((String[]) fieldName.toArray(new String[fieldName.size()]), list, workbook, 1, "司机信息导出");
			workbook.write(ouputStream);
			ouputStream.flush();
			ouputStream.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(params = "method=driverISHaveGps")
	public @ResponseBody JSONObject driverISHaveGps(String driverId){
		JSONObject json = new JSONObject();
		Map<String, String> map = dsi.driverISHaveGps(driverId);
		json.put("map", map);
		return json;
		
		
	}

}
