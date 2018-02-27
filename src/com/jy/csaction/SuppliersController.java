package com.jy.csaction;

import java.io.File;
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

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.jy.action.AbnormalReportController;
import com.jy.common.ExportUtils;
import com.jy.common.SessionInfo;
import com.jy.common.UUIDUtils;
import com.jy.dao.Json;
import com.jy.model.City_info;
import com.jy.model.JySuppliers;
import com.jy.model.User;
import com.jy.service.SuppliersService;
import com.jy.service.UserInfoService;

/**
 * 供应商管理
 * 
 * @author 90
 * 
 */
@Controller
@RequestMapping(value = "/suppliers.do")
public class SuppliersController extends BaseAction {
	Logger log = Logger.getLogger(SuppliersController.class);
	@Resource
	private SuppliersService suppliersService;
	@Resource
	private UserInfoService uis;

	/**
	 * 导出客户信息
	 */
	@RequestMapping(params = "method=outPutSendSuppliers")
	public @ResponseBody
	String outPutSendCustomers(String[] headtitle, String[] fieldName1,
			String[] dataIds, HttpServletResponse response,
			HttpSession session, String name, String phone, String address,
			String id, String suppliers_customer, String suppliers_cartype) {
		String filename = "供应商信息导出";
		if (dataIds.length == 0) {
			dataIds = null;
		}
		User user = (User) session.getAttribute(SessionInfo.SessionName);
		List<JySuppliers> list = suppliersService.getOutPutCustomers(name,
				phone, address, user.getDriver_id(), suppliers_customer, user,
				dataIds);
		
		InputStream in = AbnormalReportController.class
				.getResourceAsStream("/../exportData/suppliersData.properties");
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
			ExportUtils.outputHeaders((String[]) headTitle.toArray(new String[headTitle.size()]), workbook, "供应商信息导出");
			// 调用工具类生成内容
			ExportUtils.outputColums((String[]) fieldName.toArray(new String[fieldName.size()]), list, workbook, 1, "供应商信息导出");
			workbook.write(ouputStream);
			ouputStream.flush();
			ouputStream.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	};

	/**
	 * 跳转到供应商管理页面
	 * 
	 * @param menu_id
	 * @param session
	 * @return
	 */

	@RequestMapping(params = "method=getSuppliers")
	public String getSupp(String menu_id, HttpSession session) {
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
		return "suppliers/suppliers";
	}

	/**
	 * 供应商信息
	 * 
	 * @param rows
	 * @param page
	 * @param name
	 * @param phone
	 * @param address
	 * @param id
	 * @param request
	 * @param suppliers_customer
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(params = "method=getSuppliersList")
	public @ResponseBody
	Map getSuppliers(String rows, String page, String name, String phone,
			String address, String id, HttpServletRequest request,
			String suppliers_customer, String suppliers_cartype) {
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
		List<JySuppliers> list = suppliersService.getSuppUser((page1 - 1)
				* rows1, rows1, name, phone, address, user.getDriver_id(),
				suppliers_customer, user);
		int count = suppliersService.getSuppCounUser(name, phone, address,
				user.getDriver_id(), suppliers_customer, user);

		map.put("total", count);
		map.put("rows", list);

		return map;

	}

	/**
	 * 添加车辆类型
	 */
	@RequestMapping(params = "method=carType")
	@ResponseBody
	public List<Map<String, Object>> addType() {
		List<String> li = suppliersService.addCarType();
		List<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		for (int i = 0; i < li.size(); i++) {
			map = new HashMap<String, Object>();
			map.put("id", i);
			map.put("text", li.get(i));
			lists.add(map);
		}
		return lists;
	};

	/**
	 * 供应商管理添加跳转页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "method=addSuppliers")
	public String addSupp(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.removeAttribute("filename");
		return "suppliers/addSuppliers";
	}

	/**
	 * 供应商管理添加信息
	 * 
	 * @param suppliers
	 * @param session
	 * @return
	 */
	@RequestMapping(params = "method=addProve")
	public @ResponseBody
	Json addProve(JySuppliers suppliers, HttpSession session) {
		String type = suppliers.getSuppliers_cartype();
		suppliers.setSuppliers_cartype(type);
		Json json = new Json();
		if (session.getAttribute("filename") != null) {
			suppliers.setSuppliersProve(session.getAttribute("filename")
					.toString());
		}
		suppliers.setSuppliersId(UUIDUtils.getUUID());
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy:MM:dd hh:mm:ss");
		suppliers.setSuppliersOperatorDate(dateFormat.format(new Date()));
		User user = (User) session.getAttribute(SessionInfo.SessionName);
		suppliers.setSuppliersOperator(user.getUsername());
		int i = suppliersService.saveSuppliers(suppliers);
		
		if (i >= 1) {
			suppliers =saveNewSupplersCityInfo(suppliers);
			suppliersService.supToDriver(suppliers);
			session.removeAttribute("filename");
			json.setFlag(true);
			return json;
		}
		json.setFlag(false);
		return json;
	}

	/**
	 * 供应商管理跳转修改页面
	 * 
	 * @param cid
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "method=toUpdateSuppliers")
	public String toUpdateSuppliers(String cid, HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.removeAttribute("filename");
		request.setAttribute("flg", cid);
		return "suppliers/EditSuppliers";
	}

	@RequestMapping(params = "method=up")
	public void upload(@RequestParam("file") MultipartFile file,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			super.upload(file, "suppliersImages", request);
			/*
			 * response.getWriter().print(super.getFileName());
			 * filename+=super.getFileName();
			 */
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 更新供应商信息
	 * 
	 * @param cid
	 * @return
	 */
	@RequestMapping(params = "method=updateSuppliers")
	public @ResponseBody
	JySuppliers updateSuppliers(String cid) {
		JySuppliers jysp = suppliersService.getUpdateSuppliers(cid);
		List<City_info> cityInfo = suppliersService.getCityInfo(cid);
		jysp.setListCity_infos(cityInfo);
		/*int iCityInfo = cityInfo.size();
		if (iCityInfo == 0) {
			jysp.setDriver_province(null);
			jysp.setDriver_city(null);
			jysp.setSuppers_county(new Integer[] {});
			return jysp;
		} else {
			Integer[] county = new Integer[iCityInfo];
			for (int i = 0; i < iCityInfo; i++) {
				county[i] = cityInfo.get(i).getCounty();
			}
			jysp.setDriver_province(cityInfo.get(0).getProvince());
			jysp.setDriver_city(cityInfo.get(0).getCity());
			jysp.setSuppers_county(county);
		}*/
		return jysp;
	}

	/**
	 * 供应商管理修改
	 * 
	 * @param impath
	 * @param jysuppliers
	 * @param session
	 * @return
	 */
	@RequestMapping(params = "method=update")
	public @ResponseBody
	Json update(String impath, JySuppliers jysuppliers, HttpSession session) {
		Json json = new Json();
		int i = suppliersService.updateSuppliers(jysuppliers);
		

		if (i >= 1) {

			jysuppliers = saveNewSupplersCityInfo(jysuppliers);
			suppliersService.supupdateDriver(jysuppliers);// 修改供应商基本信息时，司机也在变化
			json.setFlag(true);
			return json;
		}
		json.setFlag(false);
		return json;
	}

	/**
	 * 供应商信息填加保存省市县
	 * 
	 * @param suppliers
	 * @return
	 */
	public int saveSupplersCityInfo(JySuppliers suppliers) {
		List<JySuppliers> jyslist = new ArrayList<JySuppliers>();
		
		if (suppliers.getSuppers_county() != null
				&& suppliers.getSuppers_county().length > 0) {
			for (int j = 0; j < suppliers.getSuppers_county().length; j++) {
				JySuppliers jsu = new JySuppliers();
				jsu.setId(UUIDUtils.getUUID());
				jsu.setSuppliersId(suppliers.getSuppliersId());
				jsu.setDriver_province(suppliers.getDriver_province());
				jsu.setDriver_city(suppliers.getDriver_city());
				jsu.setDriver_county(suppliers.getSuppers_county()[j]);
				jyslist.add(jsu);
			}
		} else {
			JySuppliers jsu = new JySuppliers();
			jsu.setId(UUIDUtils.getUUID());
			jsu.setSuppliersId(suppliers.getSuppliersId());
			jsu.setDriver_province(suppliers.getDriver_province());
			jsu.setDriver_city(suppliers.getDriver_city());
			jyslist.add(jsu);

		}
		int ii = suppliersService.saveSupplersCityInfo(jyslist);
		return ii;
	}
	
	/**
	 * 供应商信息填加保存省市县
	 * 
	 * @param suppliers
	 * @return
	 */
	public JySuppliers saveNewSupplersCityInfo(JySuppliers suppliers) {
		List<JySuppliers> jyslist = new ArrayList<JySuppliers>();
		String address = suppliers.getDriver_provincename();
		Map<String, Object> map =getCity_Info(address);
		
		List<City_info> listCity_infos =(List<City_info>) map.get("listCityCode");
		
		for (City_info city_info : listCity_infos) {
			JySuppliers jsu = new JySuppliers();
			jsu.setId(UUIDUtils.getUUID());
			jsu.setSuppliersId(suppliers.getSuppliersId());
			jsu.setDriver_province(city_info.getProvince());
			jsu.setDriver_city(city_info.getCity());
			jsu.setDriver_county(city_info.getCounty());
			jyslist.add(jsu);
		}
		if(jyslist.size()==0) return suppliers;
		String[] sId = new String[1];
		sId[0] = suppliers.getSuppliersId();
		suppliersService.deleteSuppliersCityInfo(sId);
		suppliersService.saveSupplersCityInfo(jyslist);
		List<String> listCityName =(List<String>) map.get("listCityName");
		String name="";
		for (String str : listCityName) {
			name+=str+";";
		}
		suppliers.setSuppliersAddress(name);
		suppliers.setListCity_infos(listCity_infos);
		suppliersService.updateSuppliers(suppliers);
		return suppliers;
	}

	/**
	 * 添加供应商保存图片信息
	 * 
	 * @param images
	 * @param suppId
	 * @param session
	 * @return
	 */
	@RequestMapping(params = "method=updateImage")
	public @ResponseBody
	Json updateImage(String images, String suppId, HttpSession session) {
		Json json = new Json();
		if (!"".equals(session.getAttribute("filename"))
				&& session.getAttribute("filename") != null) {
			if (!"".equals(images) && images != null) {
				images += "|" + session.getAttribute("filename").toString();
			} else {
				images += session.getAttribute("filename").toString();
			}
		}
		int i = suppliersService.updateSuppliersImage(images, suppId);
		if (i >= 1) {
			json.setFlag(true);
			session.removeAttribute("filename");
			return json;
		}
		json.setFlag(false);
		return json;
	}

	/**
	 * 供应商管理删除图片
	 * 
	 * @param impath
	 * @param images
	 * @param suppId
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "method=deleteimage")
	public @ResponseBody
	Json deleteimage(String impath, String images, String suppId,
			HttpServletRequest request) {
		Json json = new Json();
		String sess = request.getSession().getServletContext()
				.getRealPath("/suppliersImages")
				+ "\\" + impath.substring(impath.lastIndexOf("/") + 1);
		int i = 0;
		if (deleteFile(sess)) {
			i = suppliersService.updateSuppliersImage(images, suppId);
		}
		if (i >= 1) {
			json.setFlag(true);
		}
		return json;
	}

	/**
	 * 删除供应商信息
	 * 
	 * @param pid
	 * @param session
	 * @return
	 */
	@RequestMapping(params = "method=deletesuppliers")
	public @ResponseBody
	Json deletesuppliers(String[] pid, HttpSession session) {
		Json json = new Json();
		for (int i = 0; i < pid.length; i++) {
			JySuppliers suppliers = suppliersService.getUpdateSuppliers(pid[i]);
			if (!"".equals(suppliers.getSuppliersProve())
					&& suppliers.getSuppliersProve() != null) {
				String[] images = suppliers.getSuppliersProve().split("\\|");
				for (int p = 0; p < images.length; p++) {
					String sess = session.getServletContext().getRealPath(
							"/suppliersImages")
							+ "\\"
							+ images[p]
									.substring(images[p].lastIndexOf("/") + 1);
					deleteFile(sess);
				}
			}
		}
		int i = suppliersService.deletesuppliers(pid);
		suppliersService.deleteDriver(pid);// 删除相应的司机
		if (i >= 1) {
			suppliersService.deleteSuppliersCityInfo(pid);// 删除对应的省市县
			json.setFlag(true);
			return json;
		}
		json.setFlag(false);
		return json;
	}

	/**
	 * 删除文件
	 * 
	 * @param sPath
	 *            被删除文件的文件名
	 * @return 单个文件删除成功返回true，否则返回false
	 */
	public boolean deleteFile(String sPath) {
		boolean flag = false;
		File file = new File(sPath);
		// 路径为文件且不为空则进行删除
		if (file.isFile() && file.exists()) {
			file.delete();
			flag = true;
		}
		return flag;
	}

	/**
	 * select2查询供应商信息
	 * 
	 * @return
	 */
	@RequestMapping(params = "method=getSuppiadd")
	@ResponseBody
	public List<Map<String, Object>> getSuppiadd() {
		List<JySuppliers> list = suppliersService.getSpriadd();
		List<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		for (JySuppliers jySuppliers : list) {
			map = new HashMap<String, Object>();
			map.put("id", jySuppliers.getSuppliersId());
			map.put("text", jySuppliers.getSuppliersAddress());
			lists.add(map);
		}
		return lists;
	}

	/**
	 * 拆分字符串
	 * 
	 * @param str
	 * @return
	 */
	public static String SpiltArray(String str) {
		StringBuffer sb = new StringBuffer();
		String[] temp = str.split(",");
		for (int i = 0; i < temp.length; i++) {
			if (!"".equals(temp[i]) && temp[i] != null)
				sb.append("'" + temp[i] + "',");
		}
		String result = sb.toString();
		String tp = result.substring(result.length() - 1, result.length());
		if (",".equals(tp))
			return result.substring(0, result.length() - 1);
		else
			return result;
	}
    public  void main(String[] args) {
    	
    }
    //获取省市县
    public Map<String, Object>getCity_Info(String citymessage){
    	List<Integer> city =new ArrayList<Integer>();//市
    	city.add(0);
    	List<Integer> province = new ArrayList<Integer>();//省
    	province.add(0);
    	List<Integer> county= new ArrayList<Integer>();//县
    	county.add(0);
    	JSONArray json = JSONArray.fromObject(citymessage);
		for (int i = 0; i < json.size(); i++) {
			JSONObject jsonObject=json.getJSONObject(i);
			//String text = (String)jsonObject.get("text");
			Integer id = (Integer) jsonObject.get("id");
			Integer city_type = (Integer)jsonObject.get("city_type");
			if(city_type.equals(1)){
				province.add(id);
			}else if(city_type.equals(2)){
				city.add(id);
			}else if(city_type.equals(3)){
				county.add(id);
			}
		}
		 List<City_info>  listCityCode = suppliersService.getCity_Info(province, city, county);
		 List<String> listCityName =suppliersService.getCity_Info_name(province, city, county);
		
		 Map<String, Object> map = new HashMap<String, Object>();
		 map.put("listCityCode", listCityCode);
		 map.put("listCityName", listCityName);
		return map;
    	
    }
    	
}
