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

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jy.common.ExportUtils;
import com.jy.common.MD5;
import com.jy.common.SessionInfo;
import com.jy.common.UUIDUtils;
import com.jy.dao.Json;
import com.jy.model.Department;
import com.jy.model.Driver;
import com.jy.model.JySuppliers;
import com.jy.model.Role;
import com.jy.model.TemporaryCar;
import com.jy.model.TravelCard;
import com.jy.model.User;
import com.jy.model.UserPage;
import com.jy.service.TemporaryCarService;
import com.jy.service.TravelCardService;
import com.jy.service.UserInfoService;

@Controller
@RequestMapping(value = "/user.do")
public class UserInfoController {
	@Resource
	private UserInfoService uis;
	@Resource
	private TemporaryCarService cs;
	@Resource
	private TravelCardService ts;

	/**
	 * 用户管理--跳转用户管理信息页面
	 * 
	 * @param menu_id
	 * @param session
	 * @return
	 */
	@RequestMapping(params = "method=userInfo")
	public String userinfo(String menu_id, HttpSession session) {
	/*	Integer[] rid = (Integer[]) session
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

		}*/
		return "admin/userInfo";
	}

	/**
	 * 用户管理查询页面权限信息
	 * 
	 * @param url
	 * @param session
	 * @return
	 */
	@RequestMapping(params = "method=getFunctions")
	public @ResponseBody
	String getFunctions(String url, HttpSession session) {
		Integer[] rid = (Integer[]) session
				.getAttribute(SessionInfo.SessionRote);
		String[] sss = url.split("menu_id=");
		Integer id = Integer.parseInt(sss[1]);
		List<String> list = uis.getFunctions(id, rid);
		StringBuffer bf = new StringBuffer();
		if (list.size() > 0) {
			for (String ss : list) {
				bf.append(ss);
				bf.append(",");
			}
			bf.deleteCharAt(bf.length() - 1);
		} else {

		}
		return bf.toString();
	}

	/**
	 * 用户管理--用户信息查询
	 * 
	 * @param page
	 * @param session
	 * @return
	 */
	@RequestMapping(params = "method=getUser")
	public @ResponseBody
	Map<String, Object> getUser(UserPage page, HttpSession session) {
		User user = (User) session.getAttribute(SessionInfo.SessionName);// 获取登录的用户信息
		// 当前第几页
		Integer page2 = 1;
		// 当前页一共几条
		Integer rows1 = 0;
		if (page.getPage() != null && !"".equals(page.getPage())) {
			page2 = Integer.parseInt(page.getPage());
		}
		if (page.getRows() != null && !"".equals(page.getRows())) {
			rows1 = Integer.parseInt(page.getRows());
		}
		if ("".equals(page.getDepartment_id())) {
			page.setDepartment_id(null);
		}
		List<User> list = uis.getUserInfo((page2 - 1) * rows1, rows1,
				page.getUser_name(), page.getUser_realName(),
				page.getDepartment_id(), page.getFlag(), user);
		int total = uis.getCount(page.getUser_name(), page.getUser_realName(),
				page.getDepartment_id(), page.getFlag(), user);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", total);
		map.put("rows", list);
		return map;
	}

	/**
	 * 用户管理--跳转修改页面
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "method=editUserInfo")
	public String editUserInfo(String id, HttpServletRequest request) {
		request.setAttribute("userId", id);
		return "admin/editUserInfo";
	}

	/**
	 * 用户管理修改用户信息
	 * 
	 * @param id
	 * @param realName
	 * @param did
	 * @param flag
	 * @param car
	 * @param suppliers_id
	 * @param user_province
	 * @param user_city
	 * @param user_county
	 * @param user_address
	 * @param customerid
	 * @return
	 */
	@RequestMapping(params = "method=modifyUser")
	public String modifyUser(String id, String realName, String did,
			String flag, String car, String suppliers_id,
			Integer user_province, Integer user_city, Integer user_county,
			String user_address, String customerid) {
		User user = new User();
		user.setId(id);
		user.setRealName(realName);
		user.setDid(did);
		user.setFlag(flag);
		user.setUser_city(user_city);
		user.setUser_county(user_county);
		user.setUser_province(user_province);
		user.setUser_address(user_address);
		if ("1".equals(flag)) {
			user.setDriver_id(car);
			user.setCustomer_id("");
			user.setSuppliers_id("");
		} else if ("2".equals(flag)) {
			user.setSuppliers_id(suppliers_id);
			user.setDriver_id(car);
			user.setCustomer_id("");
		} else if ("3".equals(flag) || "4".equals(flag)) {
			user.setCustomer_id(customerid);
			user.setSuppliers_id("");
			user.setDriver_id("");
		}
		boolean b = uis.modifyUser(user);
		if(b){
			uis.updateUser_Role_Info(user.getId(), user.getFlag());
		}
		return "admin/userInfo";
	}

	/**
	 * 用户管理--删除用户信息
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "method=deleteUser")
	public @ResponseBody
	boolean deleteUser(String id) {
		boolean b = uis.delUser(id);
		if (b) {
			uis.delUserRole(id);
		}
		return b;
	}

	/**
	 * 用户管理--添加用户跳转页面
	 * 
	 * @return
	 */
	@RequestMapping(params = "method=addUserInfo")
	public String addUserInfo() {
		return "admin/addUserInfo";
	}

	/**
	 * 用户管理--添加用户信息
	 * 
	 * @param username
	 * @param password
	 * @param realName
	 * @param did
	 * @param flag
	 * @param car
	 * @param suppliers_id
	 * @param user_province
	 * @param user_city
	 * @param user_county
	 * @param user_address
	 * @param customerid
	 * @return
	 */
	@RequestMapping(params = "method=saveUser")
	public String saveUser(String username, String password, String realName,
			String did, String flag, String car, String suppliers_id,
			Integer user_province, Integer user_city, Integer user_county,
			String user_address, String customerid) {
		User user = new User();

		String id = UUIDUtils.getUUID();
		user.setId(id);
		user.setUsername(username);
		user.setPassword(MD5.toMD5(password));
		user.setRealName(realName);
		user.setDid(did);
		user.setFlag(flag);
		if ("1".equals(flag)) {
			user.setDriver_id(car);
		} else if ("2".equals(flag)) {
			user.setSuppliers_id(suppliers_id);
			user.setDriver_id(car);
		} else if ("3".equals(flag) || "4".equals(flag)) {
			user.setCustomer_id(customerid);
		}
		user.setUser_city(user_city);
		user.setUser_county(user_county);
		user.setUser_province(user_province);
		user.setUser_address(user_address);
		uis.addUser(user);
		if (flag != null && !"".equals(flag)) {
			int i = Integer.parseInt(flag);
			uis.saveRole(id, i);
		}
		return "admin/userInfo";
	}

	/**
	 * 用户管理--添加用户检查用户名是否存在
	 * 
	 * @param name
	 * @return
	 */
	@RequestMapping(params = "method=checkUser")
	public @ResponseBody
	boolean checkUser(String name) {
		boolean b = uis.checkUser(name);
		return b;
	}

	/**
	 * 用户管理--重置用户密码
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "method=resetpassword")
	public String resetpassword(String id) {
		uis.resetpassword(id);
		return "admin/userInfo";
	}

	/**
	 * 用户管理--导出用户信息
	 * 
	 * @param datas
	 * @param headtitle
	 * @param fieldName
	 * @param response
	 * @param user_name
	 * @param user_realName
	 * @param department_id
	 * @return
	 */
	@RequestMapping(params = "method=outputUser")
	public @ResponseBody
	String outputUser(String[] ids, String[] headtitle, String[] fieldName,
			HttpServletResponse response, String user_name,
			String user_realName, String department_id, String flag,
			HttpSession session) {
		String filename = "用户导出";
		User user = (User) session.getAttribute(SessionInfo.SessionName);// 获取登录的用户信息
		List<User> list = uis.getAllUser(ids, user, user_name, user_realName,
				department_id, flag);
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
			ExportUtils.outputHeaders(headtitle, workbook, "用户列表");
			// 调用工具类生成内容
			ExportUtils.outputColums(fieldName, list, workbook, 1, "用户列表");
			workbook.write(ouputStream);
			ouputStream.flush();
			ouputStream.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 用户管理--获取指定用户
	 */
	@RequestMapping(params = "method=getUser1")
	public @ResponseBody
	User getUser1(String id) {
		User user = uis.getUser1(id);
		return user;
	}

	/**
	 * 获取用户信息
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "method=getOneUser")
	public @ResponseBody
	User getOneUser(String id) {
		User user = uis.getOneUser(id);
		String did = uis.getDepartment(user.getDid());
		user.setDepartment_name(did);
		return user;
	}

	/*
	 * private static boolean isNumeric0(String str) { for (int i =
	 * str.length(); --i >= 0;) { int chr = str.charAt(i); if (chr < 48 || chr >
	 * 57) return false; } return true; }
	 */

	/**
	 * 修改密码
	 * 
	 * @param id
	 * @param password1
	 * @param password2
	 * @return
	 */
	@RequestMapping(params = "method=modifypassword")
	public @ResponseBody
	Json modifypassword(String id, String password1, String password2) {
		User u = uis.getUser1(id);
		Json json = new Json();
		boolean pass = u.getPassword().equals(MD5.toMD5(password1));
		if (pass) {
			boolean flag = uis.modifyPassword(id, MD5.toMD5(password2));
			if (flag) {
				json.setAns("1");
				return json;
			} else {
				json.setAns("2");
				return json;
			}
		} else {
			json.setAns("3");
			return json;
		}
	}

	/**
	 * 获取用户角色
	 */
	@RequestMapping(params = "method=getRole")
	public @ResponseBody
	int getRole(String id) {
		int role = uis.getRole(id);
		return role;
	}

	/**
	 * 获取司机信息
	 * 
	 * @param search
	 * @return
	 */
	@RequestMapping(params = "method=getDrivers")
	public @ResponseBody
	String getDrivers(String search) {
		List<Driver> list = uis.getDrivers(search);
		JSONObject jb = null;
		JSONArray jsons = new JSONArray();
		for (Driver t : list) {
			jb = new JSONObject();
			jb.put("id", t.getDriver_id());
			jb.put("name", t.getDriver_name());
			jsons.add(jb);
		}
		return jsons.toString();
	}

	/**
	 * 获取指定部门名称
	 */
	@RequestMapping(params = "method=getDepartment")
	public @ResponseBody
	String getDepartment(String did) {
		return uis.getDepartment(did);

	}

	/**
	 * 获取部门列表
	 */
	@RequestMapping(params = "method=getDepartments")
	public @ResponseBody
	List<Map<String, Object>> getDepartments() {
		List<Department> depn = uis.getDepartments();
		List<Map<String, Object>> returnlist = new ArrayList<Map<String, Object>>();
		Map<String, Object> maps = null;
		for (Department department : depn) {
			maps = new HashMap<String, Object>();
			maps.put("id", department.getDepartment_id());
			maps.put("text", department.getDepartment_name());
			returnlist.add(maps);
		}
		return returnlist;

	}

	/**
	 * 获取角色列表
	 */
	@RequestMapping(params = "method=getRoles")
	public @ResponseBody
	List<Map<String, Object>> getRoles() {
		List<Role> role = uis.getRoles();
		List<Map<String, Object>> returnlist = new ArrayList<Map<String, Object>>();
		Map<String, Object> maps = null;
		for (Role role1 : role) {
			maps = new HashMap<String, Object>();
			maps.put("id", role1.getId());
			maps.put("text", role1.getName());
			returnlist.add(maps);
		}
		return returnlist;

	}

	/**
	 * 保存用户权限
	 */
	@RequestMapping(params = "method=saveRole")
	public String saveRole(Integer uid, Integer rid) {
		uis.saveRoles(uid, rid);
		return "admin/userInfo";
	}

	/**
	 * 暂时没用
	 * 
	 * @return
	 */
	@RequestMapping(params = "method=getLinshiCars")
	public @ResponseBody
	List<Map<String, Object>> getLinshiCars() {
		List<TemporaryCar> list = cs.getCars();
		List<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		for (TemporaryCar tc : list) {
			map = new HashMap<String, Object>();
			map.put("id", tc.getTemporaryCarId());
			map.put("text", tc.getPlateNumber());

			lists.add(map);
		}
		return lists;
	}

	/**
	 * 暂时没用
	 * 
	 * @return
	 */
	@RequestMapping(params = "method=getGudingCars")
	public @ResponseBody
	List<Map<String, Object>> getGudingCars() {
		List<TravelCard> list = ts.getCars();
		List<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		for (TravelCard tc : list) {
			map = new HashMap<String, Object>();
			map.put("id", tc.getTravelCardId());
			map.put("text", tc.getPlateNumber());

			lists.add(map);
		}
		return lists;
	}

	/**
	 * 暂时没用
	 * 
	 * @return
	 */
	@RequestMapping(params = "method=getmodifycar")
	public @ResponseBody
	List<Map<String, Object>> getModifycar(String flag, String id) {
		List<TemporaryCar> list = cs.getCars();
		List<TravelCard> list2 = ts.getCars();
		List<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();

		// TemporaryCar car = new TemporaryCar();
		// TravelCard car2 = new TravelCard();
		if ("1".equals(flag)) {
			TemporaryCar cc = cs.getUserTemCar(id);
			if (cc != null) {
				list.add(0, cc);
				Map<String, Object> map = null;
				for (TemporaryCar tc : list) {
					map = new HashMap<String, Object>();
					map.put("id", tc.getTemporaryCarId());
					map.put("text", tc.getPlateNumber());
					if (tc.getTemporaryCarId().equals(cc.getTemporaryCarId())) {
						map.put("selected", true);

					}
					lists.add(map);
				}
			}

		} else if ("2".equals(flag)) {
			TravelCard ccc = ts.getUserTemCar(id);
			if (ccc != null) {
				list2.add(0, ccc);
				Map<String, Object> map = null;
				for (TravelCard tc : list2) {
					map = new HashMap<String, Object>();
					map.put("id", tc.getTravelCardId());
					map.put("text", tc.getPlateNumber());
					if (tc.getTravelCardId().equals(ccc.getTravelCardId())) {
						map.put("selected", true);

					}
					lists.add(map);
				}
			}
		}

		return lists;
	}

	/**
	 * 去个人信息页面
	 */
	@RequestMapping(params = "method=personInfo")
	public String PersonInfo() {
		return "admin/personInfo";
	}

	/**
	 * 用户管理--获取供应商信息
	 * 
	 * @param search
	 * @return
	 */
	@RequestMapping(params = "method=getJySuppliers")
	public @ResponseBody
	String getJySuppliers(String search) {
		List<JySuppliers> list = uis.getJySuppliers(search);
		JSONObject jb = null;
		JSONArray jsons = new JSONArray();
		for (JySuppliers t : list) {

			jb = new JSONObject();
			jb.put("id", t.getSuppliersId());
			jb.put("name", t.getSuppliersName());
			jsons.add(jb);
		}
		return jsons.toString();
	}

}
