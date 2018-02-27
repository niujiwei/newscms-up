package com.jy.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jy.model.Function;
import com.jy.model.Menu;

/**
 * 
 * @author Zzp Menu菜单管理Dao接口类
 */
public interface MenuDao {

	// 根据角色获取菜单
	List<Menu> getMenu(int id, int roelid);

	// 获取菜单信息
	List<Menu> getnoteId(int id);

	// 获取菜单信息
	List<Menu> getMenus();

	// 菜单管理 获取所有菜单
	List<Menu> getFMenuids(@Param("array") Integer[] ids);

	// 菜单管理 获取所有菜单
	List<Menu> getRoleMenus(@Param("array") Integer[] ids);

	// 获取页面菜单路径图标等信息
	List<Menu> getcMenus(Integer id, @Param("array") Integer[] ids);

	// 获取所有父节点
	List<Menu> getnoteAll();

	// 获取所有子节点
	List<Menu> getMenuAll(int fuid);

	// 获取菜单更新信息
	Menu getupdateMenuInfo(int id);

	// 菜单管理 更新菜单信息
	int updateMenu(Menu menu);

	// 菜单管理 删除菜单
	int deleteMenu(int mid);

	// 菜单管理 添加菜单
	int addMenu(Menu menu);

	// 获取用角色菜单id
	Integer[] getIdMenu(int id);

	// 获取按钮
	List<Function> getMenuFunction(Integer id);
}
