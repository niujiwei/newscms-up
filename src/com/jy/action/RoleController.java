package com.jy.action;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jy.common.ExportExcel;
import com.jy.common.SessionInfo;
import com.jy.excel.bean.RoleExcel;
import com.jy.model.AppMenu;
import com.jy.model.Function;
import com.jy.model.Role;
import com.jy.service.RoleService;
import com.jy.service.UserInfoService;

@Controller
@RequestMapping(value = "/role.do")
public class RoleController {
	@Resource
	private RoleService roleService;
	@Resource
	private UserInfoService uis;

	/**
	 * 角色管理 跳转角色管理页面
	 * 
	 * @param menu_id
	 * @param session
	 * @return
	 */
	@RequestMapping(params = "method=setRole")
	public String getSetRole(String menu_id, HttpSession session) {
		Integer[] rid = (Integer[]) session
				.getAttribute(SessionInfo.SessionRote);
		Integer id = Integer.parseInt(menu_id);
		List<String> list = uis.getFunctions(id, rid);
		StringBuffer bf = new StringBuffer();
		if (list.size() > 0) {
			for (String ss : list) {
				bf.append(ss);
				bf.append(",");

			}
			bf.deleteCharAt(bf.length() - 1);
			session.setAttribute("function", bf.toString());
		} else {
			session.setAttribute("function", "");

		}
		return "menu/setRole";
	}

	/**
	 * 角色管理 设置权限
	 * 
	 * @param menu_id
	 * @param session
	 * @return
	 */
	@RequestMapping(params = "method=role")
	public String getRole(String menu_id, HttpSession session) {
		Integer[] rid = (Integer[]) session
				.getAttribute(SessionInfo.SessionRote);
		Integer id = Integer.parseInt(menu_id);
		List<String> list = uis.getFunctions(id, rid);
		StringBuffer bf = new StringBuffer();
		if (list.size() > 0) {
			for (String ss : list) {
				bf.append(ss);
				bf.append(",");

			}
			bf.deleteCharAt(bf.length() - 1);
			session.setAttribute("function", bf.toString());
		} else {
			session.setAttribute("function", "");

		}
		return "menu/roles";
	}

	/**
	 * 角色管理 获取所有角色
	 * 
	 * @param page
	 * @param rows
	 * @param role_name
	 * @return
	 */
	@RequestMapping(params = "method=getRoles")
	public @ResponseBody
	Map<String, Object> getRoles(String page, String rows, String role_name) {
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
		List<Role> list = roleService.getRoles((page2 - 1) * rows1, rows1,
				role_name);
		int total = roleService.getCount(role_name);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", total);
		map.put("rows", list);
		return map;
	}

	/**
	 * 角色管理 获取功能
	 */
	@RequestMapping(params = "method=getFunctions")
	public @ResponseBody
	Map<String, Object> getFunctions(Integer mid) {
		List<Function> list = roleService.getFunctions(mid);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", list);
		return map;
	}

	/**
	 * 获取指定角色
	 */
	@RequestMapping(params = "method=getRole1")
	public @ResponseBody
	Role getRole1(int id) {
		Role role = roleService.getRole(id);
		return role;
	}

	/**
	 * 角色管理 获取指定角色数量
	 */
	@RequestMapping(params = "method=userRoleCount")
	public @ResponseBody
	Integer getUserRoleCount(Integer id) {
		Integer i = 0;
		if (id == null) {
		} else {
			i = roleService.getUserRoleCount(id);
		}
		return i;
	}

	/**
	 * 角色管理 修改角色
	 */
	@RequestMapping(params = "method=modifyRole")
	public String modifyRole(Integer id, String name, String remarks) {
		Role role = new Role();
		role.setId(id);
		role.setName(name);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		role.setLtime(df.format(new Date()));
		role.setRemarks(remarks);
		roleService.modifyRole(role);
		return "menu/roles";
	}

	/**
	 * 角色管理 删除一个角色
	 */
	@RequestMapping(params = "method=deleteRole")
	public @ResponseBody
	boolean deleteRole(int id) {
		boolean b = roleService.delRole(id);
		return b;
	}

	/**
	 * 角色管理 检查角色名是否重复
	 */
	@RequestMapping(params = "method=checkRole")
	public @ResponseBody
	boolean checkRole(String name) {
		boolean b = roleService.checkRole(name);
		return b;
	}

	/**
	 * 角色管理 保存角色
	 */
	@RequestMapping(params = "method=saveRole")
	public String saveRole(String name, String remarks) {
		Role role = new Role();
		role.setName(name);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		role.setRemarks(remarks);
		role.setCtime(df.format(new Date()));
		roleService.addRole(role);
		return "menu/roles";
	}

	/**
	 * 角色管理 获取权限树
	 */
	@RequestMapping(params = "method=getTree")
	public @ResponseBody
	String getTree(Integer id) {
		return roleService.getTree(id);

	}

	/**
	 * 角色管理 获取已拥有菜单功能
	 */
	@RequestMapping(params = "method=getFids")
	public @ResponseBody
	Integer[] getFids(Integer mid, Integer rid) {
		Integer[] i = roleService.getFids(mid, rid);
		return i;

	}

	/**
	 * 权限管理--获取角色信息
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "method=getRoleTree")
	public @ResponseBody
	String getRoleTree(String id) {
		return roleService.getRoleTree(id);

	}

	/**
	 * 角色管理 保存权限菜单
	 */
	@RequestMapping(params = "method=saveMenu")
	public String saveMenu(String[] nodes, Integer id) {
		if (id != null) {
			roleService.delMenu(id);

		}
		if (id == null) {
			id = roleService.getId();
		}

		for (int i = 0; i < nodes.length; i++) {
			Integer mid = Integer.parseInt(nodes[i]);
			roleService.addMenu(mid, id);
		}
		return "menu/roles";
	}

	/**
	 * 保存菜单功能
	 */
	@RequestMapping(params = "method=saveMenuFunctions")
	public @ResponseBody
	Boolean saveMenuFunctions(String[] fids, Integer rid, Integer menuid) {

		roleService.delMenuFunction(rid, menuid);
		for (int i = 0; i < fids.length; i++) {
			Integer fid = Integer.parseInt(fids[i]);
			roleService.addFunction(fid, rid);
		}

		return true;
	}

	/**
	 * 保存用户角色菜单
	 */
	@RequestMapping(params = "method=saveUserRole")
	public String saveUserRole(String[] nodes, String id) {
		roleService.delUserRoles(id);
		for (int i = 0; i < nodes.length; i++) {
			Integer rid = Integer.parseInt(nodes[i]);

			roleService.addUserRole(id, rid);
		}
		return "menu/setRole";
	}

	/**
	 * 导出角色信息
	 */
	@RequestMapping(params = "method=outputRole")
	public @ResponseBody
	String outputRole(String datas, String[] headtitle,
			HttpServletResponse response) {
		// String address = getadd(add, name);
		String filename = "角色信息导出";
		ExportExcel<RoleExcel> ex = new ExportExcel<RoleExcel>();
		try {
			JSONArray jsonarray = JSONArray.fromObject(datas);
			@SuppressWarnings("unchecked")
			List<RoleExcel> lists = (List<RoleExcel>) JSONArray.toCollection(
					jsonarray, RoleExcel.class);

			response.setContentType("application/vnd.ms-excel;");
			response.setHeader("Content-Disposition", "attachment;filename=\""
					+ new String(filename.getBytes("gbk"), "iso-8859-1")
					+ ".xls\"");
			OutputStream ouputStream = response.getOutputStream();
			ex.exportExcel(headtitle, lists, ouputStream);
			ouputStream.flush();
			ouputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 权限管理--获APP取角色信息
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "method=getAPPRoleTree")
	public @ResponseBody
	String getAPPRoleTree(String roleId) {
		return roleService.getAppMenu(roleId);

	}

	/**
	 * 权限管理--获APP取角色信息
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "method=insertAPPRole")
	public @ResponseBody
	boolean insertAPPRole(String id, Integer[] ckNodes) {
		boolean b = true;

		if (id != null) {
			Integer i = Integer.parseInt(id);
			roleService.deleteAppRole(i);
		}

		List<AppMenu> list = new ArrayList<AppMenu>();
		for (Integer intt : ckNodes) {
			AppMenu appMenu = new AppMenu();
			appMenu.setApp_menu_id(intt);
			appMenu.setApp_role_id(id);
			list.add(appMenu);
		}
		roleService.insertAPPRole(list);

		return b;
	}

}