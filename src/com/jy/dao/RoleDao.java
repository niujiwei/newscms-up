package com.jy.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jy.model.AppMenu;
import com.jy.model.Function;
import com.jy.model.Role;

public interface RoleDao {
	// 角色管理 查询角色信息
	List<Role> getRoles(Integer page, Integer rows, String name);

	// 角色管理 修改角色
	int modifyRole(Role role);

	// 删除角色信息
	int delRoles(int id);

	// 角色管理 删除角色
	int delRole(int id);

	// 角色管理 保存角色
	int addRole(Role role);

	// 角色管理 获取指定角色
	Role getRole(int id);

	// 获取角色数量
	int getUserRole(String id);

	// 角色管理 查询获取角色总数
	int getCount(String name);

	// 获取角色信息
	List<Role> getRoleList();

	// 删除角色对应菜单
	int delMenu(Integer rid);

	// 添加角色对应菜单
	int addMenu(Integer mid, Integer rid);

	// 角色管理 检查角色名是否重复
	int checkRole(String name);

	// 获取最大角色
	int getRoleId();

	// 角色管理 获取指定角色数量
	int getUserRoleCount(int id);

	// 角色管理 获取角色树
	Integer[] getIdRoles(String id);

	// 保存用户对应的角色
	int addUserRole(String uid, Integer rid);

	// 删除用户对应的角色
	int delUserRoles(String id);

	// 角色管理 获取功能
	List<Function> getFunctions(Integer mid);

	// 角色管理 获取已拥有菜单功能
	Integer[] getFids(Integer mid, Integer rid);

	// 角色管理 删除角色按钮信息
	int delMenuFunction(Integer rid, Integer mid);

	// 角色管理 添加角色按钮信息
	int addFunction(Integer fid, Integer rid);

	// 获取APP 菜单
	List<AppMenu> getAppMenu();

	// 获取APP 角色菜单
	Integer[] getRoleAppMenu(@Param("roleId") String roleId);

	// 插入APP的权限角色关联
	int insertAPPRole(@Param("list") List<AppMenu> list);

	// 删除App菜单权限
	int deleteAppRole(@Param("roleId") int roleId);

}
