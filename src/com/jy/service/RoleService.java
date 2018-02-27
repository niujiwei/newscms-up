package com.jy.service;

import java.util.List;

import com.jy.model.AppMenu;
import com.jy.model.Function;
import com.jy.model.Role;

public interface RoleService {
	/**
	 * 角色管理 查询角色信息
	 * 
	 * @param page
	 * @param rows
	 * @param name
	 * @return
	 */
	public List<Role> getRoles(Integer page, Integer rows, String name);

	/**
	 * 角色管理 查询角色信息数量
	 * 
	 * @param name
	 * @return
	 */
	public int getCount(String name);

	/**
	 * 角色管理 修改角色
	 * 
	 * @param role
	 * @return
	 */
	public boolean modifyRole(Role role);

	/**
	 * 角色管理 删除角色
	 * 
	 * @param id
	 * @return
	 */
	public boolean delRole(int id);

	/**
	 * 角色管理 保存角色
	 * 
	 * @param role
	 * @return
	 */
	public boolean addRole(Role role);

	/**
	 * 角色管理 获取指定角色
	 * 
	 * @param id
	 * @return
	 */
	public Role getRole(int id);

	/**
	 * 角色管理 获取权限树
	 * 
	 * @param id
	 * @return
	 */
	public String getTree(Integer id);

	/**
	 * 角色管理 保存权限菜单
	 */
	public int delMenu(Integer id);

	/**
	 * 保存角权限信息
	 * 
	 * @param mid
	 * @param rid
	 * @return
	 */
	public boolean addMenu(Integer mid, Integer rid);

	/**
	 * 角色管理 检查角色名是否重复
	 * 
	 * @param name
	 * @return
	 */
	public boolean checkRole(String name);

	/**
	 * 获取最大角色
	 * 
	 * @return
	 */
	public int getId();

	/**
	 * 角色管理 获取指定角色数量
	 * 
	 * @param id
	 * @return
	 */
	public Integer getUserRoleCount(Integer id);

	/**
	 * 角色管理 获取角色树
	 * 
	 * @param id
	 * @return
	 */
	public String getRoleTree(String id);

	/**
	 * 删除用户对应的角色
	 * 
	 * @param id
	 * @return
	 */
	public boolean delUserRoles(String id);

	/**
	 * 保存用户对应的角色
	 * 
	 * @param uid
	 * @param rid
	 * @return
	 */
	public boolean addUserRole(String uid, Integer rid);

	/**
	 * 角色管理 获取功能
	 * 
	 * @param mid
	 * @return
	 */
	public List<Function> getFunctions(Integer mid);

	/**
	 * 角色管理 获取已拥有菜单功能
	 * 
	 * @param mid
	 * @param rid
	 * @return
	 */
	public Integer[] getFids(Integer mid, Integer rid);

	/**
	 * 角色管理 删除角色按钮信息
	 * 
	 * @param rid
	 * @param mid
	 * @return
	 */
	public boolean delMenuFunction(Integer rid, Integer mid);

	/**
	 * 角色管理 添加角色按钮信息
	 * 
	 * @param fid
	 * @param rid
	 * @return
	 */
	public boolean addFunction(Integer fid, Integer rid);

	/**
	 * 获取APP 菜单
	 * 
	 * @return
	 */
	String getAppMenu(String roleId);

	/**
	 */
	int insertAPPRole(List<AppMenu> list);

	/**
	 * 删除APP菜单权限
	 * 
	 * @param roleId
	 * @return
	 */
	int deleteAppRole( int roleId);

}
