package com.jy.service;

import java.util.List;

import com.jy.model.AppMessage;
import com.jy.model.AppVersion;
import com.jy.model.Department;
import com.jy.model.Driver;
import com.jy.model.JySuppliers;
import com.jy.model.Role;
import com.jy.model.User;

public interface UserInfoService {

	/**
	 * 根据用户角色查询页面按钮权限
	 * 
	 * @param mid
	 * @param rids
	 * @return
	 */
	public List<String> getFunctions(Integer mid, Integer[] rids);

	/**
	 * 用户管理--查询用户信息
	 * 
	 * @param page
	 * @param rows
	 * @param user_name
	 * @param user_realName
	 * @param user_did
	 * @return
	 */
	public List<User> getUserInfo(int page, int rows, String user_name,
			String user_realName, String user_did, String falg, User user);

	/**
	 * 用户管理--查询用户信息总条数
	 * 
	 * @param user_name
	 * @param user_realName
	 * @param user_did
	 * @return
	 */
	public int getCount(String user_name, String user_realName,
			String user_did, String flag, User user);

	/**
	 * 用户管理修改用户信息
	 * 
	 * @param user
	 * @return
	 */
	public boolean modifyUser(User user);

	/**
	 * 用户管理--删除用户信息
	 * 
	 * @param id
	 * @return
	 */
	public boolean delUser(String id);

	/**
	 * 用户管理--删除用户对应的角色
	 * 
	 * @param id
	 * @return
	 */
	public boolean delUserRole(String id);

	/**
	 * 用户管理--添加用户信息
	 * 
	 * @param user
	 * @return
	 */
	public boolean addUser(User user);

	/**
	 * 用户管理--添加用户时添加角色
	 * 
	 * @param uid
	 * @param rid
	 * @return
	 */
	public boolean saveRole(String uid, Integer rid);

	/**
	 * 用户管理--添加用户检查用户名是否存在
	 * 
	 * @param name
	 * @return
	 */
	public boolean checkUser(String name);

	/**
	 * 用户管理--重置用户密码
	 * 
	 * @param id
	 * @return
	 */
	public boolean resetpassword(String id);

	/**
	 * 用户管理--导出用户信息
	 * 
	 * @param user_name
	 * @param user_realName
	 * @param department_id
	 * @return
	 */
	public List<User> getAllUser(String[] ids, User user, String user_name,
			String user_realName, String department_id, String flag);

	/**
	 * 修改密码
	 * 
	 * @param id
	 * @param password
	 * @return
	 */
	public boolean modifyPassword(String id, String password);

	/**
	 * 根据id查询用户信息
	 * 
	 * @param id
	 * @return
	 */
	public User getUser1(String id);

	/**
	 * 根据id查询用户信息
	 * 
	 * @param id
	 * @return
	 */
	public User getOneUser(String id);

	/**
	 * 根据id查询部门信息
	 * 
	 * @param did
	 * @return
	 */
	public String getDepartment(String did);

	/**
	 * 获取部门列表
	 * 
	 * @return
	 */
	public List<Department> getDepartments();

	/**
	 * 获取角色列表
	 * 
	 * @return
	 */
	public List<Role> getRoles();

	/**
	 * 修改角色与用户的关联表
	 * 
	 * @param uid
	 * @param rid
	 * @return
	 */
	public boolean saveRoles(Integer uid, Integer rid);

	/**
	 * 取出最大用户id
	 * 
	 * @return
	 */
	public int getUserId();

	/**
	 * 根据用户id拿角色id
	 * 
	 * @param id
	 * @return
	 */
	public int getRole(String id);

	/**
	 * 获取部门名字
	 * 
	 * @param id
	 * @return
	 */
	public String namegetDepartment(String id);

	/**
	 * 获取用户个人信息
	 * 
	 * @param string
	 * @return
	 */
	public User getAppUser(String string);

	/**
	 * 获取指定部门
	 * 
	 * @param string
	 * @return
	 */
	public List<Department> getDepartmentName(String string);

	/**
	 * 获取app版本号
	 * 
	 * @return
	 */
	public AppVersion getAppVersion();

	/**
	 * 用户返回意见信息
	 * 
	 * @param appmessage
	 * @return
	 */
	public int saveAppMessage(AppMessage appmessage);

	/**
	 * 更新部门
	 * 
	 * @param creatingUser
	 * @param institutionId
	 * @return
	 */
	public int updateDepartment(String creatingUser, String institutionId);

	/**
	 * 用户管理--查询司机信息
	 * 
	 * @param search
	 * @return
	 */
	public List<Driver> getDrivers(String search);

	/**
	 * 用户管理--查询供应商
	 * 
	 * @param search
	 * @return
	 */
	public List<JySuppliers> getJySuppliers(String search);

	/**
	 * 用户修改--更新对应的角色
	 * 
	 * @param userId
	 * @param roleId
	 * @return
	 */
	int updateUser_Role_Info(String userId, String roleId);

}
