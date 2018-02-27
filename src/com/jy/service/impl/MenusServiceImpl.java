package com.jy.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Component;

import com.jy.common.SessionInfo;
import com.jy.dao.MenuDao;
import com.jy.dao.RoleDao;
import com.jy.model.Menu;
import com.jy.model.User;
import com.jy.service.MenusService;

/**
 * 
 * @author Zzp Menu菜单service实现类
 */
@Component
public class MenusServiceImpl implements MenusService {
	@Resource
	private MenuDao menudao;
	@Resource
	private RoleDao roledao;
	private List<Menu> list;

	// 菜单管理 获取所有菜单
	public JSONArray getMenu(Integer[] rid, User user) {
		list = menudao.getRoleMenus(rid);
		JSONArray array = new JSONArray();
		for (Menu menu : list) {
			if (menu.getFid() == 0) {
				JSONObject jsonObject = JSONObject.fromObject(menu);
				jsonObject.put("children", build(menu.getId(), user));
				array.add(jsonObject);

			}
		}
		return array;
	}

	// 菜单管理 截取菜单
	private JSONArray build(Integer id, User user) {
		JSONArray buildarray = new JSONArray();
		for (Menu menu2 : list) {
			if (menu2.getFid() == id) {
				if ((user.getFlag().equals("1")||user.getFlag().equals("2"))
						&& menu2.getText().equals("运单录入")) {
					menu2.setText("运单查看");
				}
				if ((user.getFlag().equals("0"))
						&& menu2.getText().equals("异常反馈")) {
					menu2.setText("异常处理");
				}
				JSONObject jsonObject = JSONObject.fromObject(menu2);
				jsonObject.put("children", build(menu2.getId(), user));
				buildarray.add(jsonObject);
			}
		}
		return buildarray;

	}

	// 菜单管理treegrid，获取菜单数据
	public String getMenus() {
		List<Menu> notelist = menudao.getnoteAll();
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		if (notelist.size() > 0) {
			for (Menu menu : notelist) {
				sb.append("{\"id\":\"" + menu.getId()
						+ "\",\"pageurl\":\"\",\"iconCls\":\""
						+ menu.getIconCls() + "\",\"fid\":\"" + menu.getFid()
						+ "\",\"text\":\"" + menu.getText()
						+ "\",\"children\":[");
				List<Menu> menulist = menudao.getMenuAll(menu.getId());
				if (menulist.size() > 0) {
					for (Menu menu2 : menulist) {
						sb.append("{\"id\":\"" + menu2.getId()
								+ "\",\"pageurl\":\"" + menu2.getPageurl()
								+ "\",\"fid\":\"" + menu2.getFid()
								+ "\",\"iconCls\":\"" + menu2.getIconCls()
								+ "\",\"menuDescribe\":\""
								+ menu2.getMenuDescribe() + "\",\"text\":\""
								+ menu2.getText() + "\"");
						List<Menu> menulist2 = menudao
								.getMenuAll(menu2.getId());
						if (menulist2.size() > 0) {
							sb.append(",\"children\":[");
							for (Menu menu3 : menulist2) {
								sb.append("{\"id\":\"" + menu3.getId()
										+ "\",\"pageurl\":\""
										+ menu3.getPageurl() + "\",\"fid\":\""
										+ menu3.getFid() + "\",\"iconCls\":\""
										+ menu3.getIconCls()
										+ "\",\"menuDescribe\":\""
										+ menu3.getMenuDescribe()
										+ "\",\"text\":\"" + menu3.getText()
										+ "\"},");
							}
							sb.deleteCharAt(sb.length() - 1);
							sb.append("]},");
						} else {
							sb.append("},");
						}
					}
					sb.deleteCharAt(sb.length() - 1);
					sb.append("]},");
				} else {
					sb.append("],\"leaf\":\"" + true + "\"},");
				}

			}

			sb.deleteCharAt(sb.length() - 1);
			sb.append("]");
		} else {
			sb.append("{[]}");
		}
		return sb.toString();
	}

	// 菜单管理 更新菜单信息查询
	public Menu getupdateMenuInfo(int id) {
		Menu list = menudao.getupdateMenuInfo(id);
		return list;
	}

	// 菜单管理 更新菜单信息
	public int updateMenu(Menu meun) {
		int flag = menudao.updateMenu(meun);
		return flag;
	}

	// 删除菜单
	public int deleteMenu(int mid) {
		int flag = menudao.deleteMenu(mid);
		return flag;
	}

	// 菜单管理 获取菜单Combotree
	public String getmenucombo(HttpSession session) {
		List<Menu> notelist = menudao.getnoteAll();
		User user = (User) session.getAttribute(SessionInfo.SessionName);
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		if (notelist.size() > 0) {
			for (Menu menu : notelist) {
				sb.append("{\"id\":\"" + menu.getId() + "\",\"fid\":\""
						+ menu.getFid() + "\",\"iconCls\":\""
						+ menu.getIconCls() + "\",\"menuDescribe\":\""
						+ menu.getMenuDescribe() + "\",\"text\":\""
						+ menu.getText() + "\",\"children\":[");
				List<Menu> menulist = menudao.getMenuAll(menu.getId());
				if (menulist.size() > 0) {
					for (Menu menu2 : menulist) {
						if (!user.getFlag().equals("0")||!user.getFlag().equals("3")||!user.getFlag().equals("4")) {
							if (menu2.getText().equals("运单录入")) {
								menu2.setText("运单查询");
							}

						}
						if ((user.getFlag().equals("0")) && menu2.getText().equals("异常反馈")) {
							menu2.setText("异常处理");
						}
						sb.append("{\"id\":\"" + menu2.getId()
								+ "\",\"pageurl\":\"" + menu2.getPageurl()
								+ "\",\"fid\":\"" + menu2.getFid()
								+ "\",\"iconCls\":\"" + menu2.getIconCls()
								+ "\",\"menuDescribe\":\""
								+ menu2.getMenuDescribe() + "\",\"text\":\""
								+ menu2.getText() + "\"},");
					}
					sb.deleteCharAt(sb.length() - 1);
					sb.append("]},");
				} else {
					sb.append("]},");

				}
			}
			sb.deleteCharAt(sb.length() - 1);
			sb.append("]");
		} else {
			sb.append("[{}]");
		}
		return sb.toString();
	}

	// 菜单管理 添加菜单
	public int addMenu(Menu menu) {
		int flag = menudao.addMenu(menu);
		return flag;
	}

}
