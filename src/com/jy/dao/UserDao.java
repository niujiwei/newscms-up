package com.jy.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.jy.model.AppMessage;
import com.jy.model.AppVersion;
import com.jy.model.Department;
import com.jy.model.Driver;
import com.jy.model.JySuppliers;
import com.jy.model.Role;
import com.jy.model.User;

public interface UserDao {

	// 获取页面按钮信息
	List<String> getFunctions(Integer mid, @Param("array") Integer[] rids);

	// 用户管理--管理员查询用户信息
	List<User> getUser(Integer page, Integer rows, String user_name,
			String user_realName, String user_did, String flag);

	// 用户管理--管理员用户信息总条数
	int getCount(String user_name, String user_realName, String user_did,
			String flag);

	// 用户管理--司机查询用户信息
	List<User> driverGetUser(Integer page, Integer rows, String user_name,
			String user_realName, String user_did, String flag, String userId);

	// 用户管理--司机用户信息总条数
	int driverGetUserCount(String user_name, String user_realName,
			String user_did, String flag, String userId);

	// 用户管理--司机查询用户信息
	List<User> supplierGetUser(Integer page, Integer rows, String user_name,
			String user_realName, String user_did, String flag, String userId);

	// 用户管理--司机用户信息总条数
	int supplierGetUserCount(String user_name, String user_realName,
			String user_did, String flag, String did);

	// 用户管理--司机查询用户信息
	List<User> otherGetUser(Integer page, Integer rows, String user_name,
			String user_realName, String user_did, String flag, String did);

	// 用户管理--司机用户信息总条数
	int otherGetUserCount(String user_name, String user_realName,
			String user_did, String flag, String did);

	// 用户管理--修改用户信息
	int modifyUser(User user);

	// 用户管理--删除用户信息
	int delUser(String id);

	// 用户管理--删除用户信息删除角色信息
	int delUserRole(String uid);

	// 用户管理--添加用户信息
	int addUser(User user);

	// 用户管理--添加用户时报错角色信息
	int saveRole(String user_role_uid, Integer user_role_rid);

	// 用户管理--添加用户检查用户名是否存在
	int checkUser(String name);

	// 用户管理--重置用户密码
	int resetpassword(String id);

	// 用户管理--管理员导出用户信息
	List<User> getAllUser(@Param("array") String[] ids, String user_name,
			String user_realName, String department_id, String flag);

	// 用户管理--司机导出用户信息
	List<User> driverOutPutUser(@Param("array") String[] ids, String user_name,
			String user_realName, String department_id, String flag, String id);

	// 用户管理--供应商导出用户信息
	List<User> suppliersOutPutUser(@Param("array") String[] ids,
			String user_name, String user_realName, String department_id,
			String flag, String supplersId);

	// 用户管理--项目部导出用户信息
	List<User> otherOutPutUser(@Param("array") String[] ids, String user_name,
			String user_realName, String department_id, String flag, String did);

	// 修改密码
	int modifyPassword(String id, String password);

	// 根据id查询用户信息
	User getUser1(String id);

	User getTemUser(String id);

	User getTraUser(String id);

	// 查询部门信息
	String getDepartment(String did);

	// 获取部门列表
	List<Department> getDepartments();

	// 获取角色列表
	List<Role> getRoles();

	// 修改角色与用户的关联表
	int saveRoles(Integer user_role_uid, Integer user_role_rid);

	// 取出最大用户id
	int getUserId();

	// 获取部门名字
	String namegetDepartment(String id);

	// 获取用户个人信息
	User getAppUser(String id);

	// 查询部门
	List<Department> getDepartmentName(String department_name);

	// 获取app版本号
	AppVersion getAppVersion();

	// 获取用户意见信息
	int saveAppMessage(AppMessage appmessage);

	// 更新部门
	int updateDepartment(String creatingUser, String institutionId);

	// 用户管理--查询司机信息
	List<Driver> getDrivers(String search);

	// 用户管理 --查询供应商
	List<JySuppliers> getJySuppliers(String search);

	// 用户管理--获取部门信息
	List<Department> getDepartmentTree(String id);
	
	// 用户修改--更新对应的角色
	int updateUser_Role_Info(@Param("userId")String userId,@Param("roleId")String roleId);
}
