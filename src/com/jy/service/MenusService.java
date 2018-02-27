package com.jy.service;

import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import com.jy.model.Menu;
import com.jy.model.User;

/**
 * 
 * @author Zzp Menu菜单Service管理类
 */
public interface MenusService {

	/**
	 * 菜单管理 获取所有菜单
	 * 
	 * @param rid
	 * @param user
	 * @return
	 */
	public JSONArray getMenu(Integer[] rid, User user);

	/**
	 * 菜单管理treegrid，获取菜单数据
	 * 
	 * @return
	 */
	public String getMenus();

	/**
	 * 菜单管理 更新菜单信息查询
	 * 
	 * @param id
	 * @return
	 */
	public Menu getupdateMenuInfo(int id);

	/**
	 * 菜单管理 更新菜单信息
	 * 
	 * @param meun
	 * @return
	 */
	public int updateMenu(Menu meun);

	/**
	 * 菜单管理 删除菜单
	 * 
	 * @param mid
	 * @return
	 */
	public int deleteMenu(int mid);

	/**
	 * 菜单管理 获取菜单Combotree
	 * 
	 * @param session
	 * @return
	 */
	public String getmenucombo(HttpSession session);

	/**
	 * 菜单管理 添加菜单
	 * 
	 * @param menu
	 * @return
	 */
	public int addMenu(Menu menu);

}
