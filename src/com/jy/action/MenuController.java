package com.jy.action;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jy.common.SessionInfo;
import com.jy.dao.Json;
import com.jy.model.Menu;
import com.jy.model.User;
import com.jy.service.MenusService;
import com.jy.service.UserInfoService;

/**
 * 
 * @author Zzp Menu菜单管理类
 */
@Controller
@RequestMapping(value = "/menu.do")
public class MenuController {

	@Resource
	private MenusService menuService;
	@Resource
	private UserInfoService uis;

	/**
	 * 菜单管理跳转添加页面
	 * 
	 * @param menu_id
	 * @param session
	 * @return
	 */
	@RequestMapping(params = "method=menu")
	public String menu(String menu_id, HttpSession session) {
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
		return "menu/menus";
	}

	/**
	 * 菜单管理treegrid，获取菜单数据
	 * 
	 * @return
	 */
	@RequestMapping(params = "method=menus")
	public @ResponseBody
	String getMenuPage() {
		String menus = menuService.getMenus();
		return menus;
	}

	/**
	 * 菜单管理 更新菜单信息查询
	 * 
	 * @param menuid
	 * @return
	 */
	@RequestMapping(params = "method=getUpdatemenuInfo")
	public @ResponseBody
	Menu getupdateMenuInfo(int menuid) {
		Menu menu = menuService.getupdateMenuInfo(menuid);
		return menu;
	}

	/**
	 * 菜单管理 更新菜单信息
	 * 
	 * @param menu
	 * @return
	 */
	@RequestMapping(params = "method=updatemenuInfo")
	public @ResponseBody
	Json updateMenu(Menu menu) {
		Json json = new Json();
		int flag = menuService.updateMenu(menu);
		if (flag > 0) {
			json.setFlag(true);
			return json;
		}
		json.setFlag(false);
		return json;
	}

	/**
	 * 菜单管理 删除菜单
	 * 
	 * @param mid
	 * @return
	 */
	@RequestMapping(params = "method=deletemenuInfo")
	public @ResponseBody
	Json deletemenuInfo(int mid) {
		Json json = new Json();
		int flag = menuService.deleteMenu(mid);
		if (flag > 0) {
			json.setFlag(true);
			return json;
		}
		json.setFlag(false);
		return json;
	}

	/**
	 * 菜单管理 添加菜单
	 * 
	 * @param menu
	 * @return
	 */
	@RequestMapping(params = "method=savemenuInfo")
	public @ResponseBody
	Json savemenuInfo(Menu menu) {
		Json json = new Json();
		int flag = menuService.addMenu(menu);
		if (flag > 0) {
			json.setFlag(true);
			return json;
		}
		json.setFlag(false);
		return json;
	}

	/**
	 * 菜单管理 获取菜单Combotree
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping(params = "method=getmenucombo")
	public @ResponseBody
	String getmenucombo(HttpSession session) {
		String menucombo = menuService.getmenucombo(session);
		return menucombo;
	}

	/**
	 * 菜单管理 获取所有菜单
	 * 
	 * @param user
	 * @param session
	 * @return
	 */
	@RequestMapping(params = "method=getmenu")
	public @ResponseBody
	JSONArray getMenu(User user, HttpSession session) {
		Integer[] rid = (Integer[]) session
				.getAttribute(SessionInfo.SessionRote);
		User user1 = (User) session.getAttribute(SessionInfo.SessionName);
		return menuService.getMenu(rid, user1);
	}
}
