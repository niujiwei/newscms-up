package com.jy.service.impl;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import com.jy.dao.RoleDao;
import com.jy.dao.UserDao;
import com.jy.model.AppMessage;
import com.jy.model.AppVersion;
import com.jy.model.Department;
import com.jy.model.Driver;
import com.jy.model.JySuppliers;
import com.jy.model.Role;
import com.jy.model.User;
import com.jy.service.UserInfoService;

@Component
public class UserInfoServiceImpl implements UserInfoService {
	@Resource
	private UserDao userdao;
	@Resource
	private RoleDao roledao;

	// 根据用户角色查询页面按钮权限
	public List<String> getFunctions(Integer mid, Integer[] rids) {
		List<String> list = userdao.getFunctions(mid, rids);
		return list;
	}

	// 用户管理--查询用户信息总条数
	public int getCount(String user_name, String user_realName,
			String user_did, String flag, User user) {
		int count = 0;
		if (user != null) {
			if ("0".equals(user.getFlag())) {// 管理员
				count = userdao.getCount(user_name, user_realName, user_did,
						flag);
			} else if ("1".equals(user.getFlag())) {// 司机
				count = userdao.driverGetUserCount(user_name, user_realName,
						user_did, flag, user.getId());
			} else if ("2".equals(user.getFlag())) {// 供应商
				count = userdao.supplierGetUserCount(user_name, user_realName,
						user_did, flag, user.getSuppliers_id());
			} else if ("3".equals(user.getFlag())) {// 省内项目部
				count = userdao.otherGetUserCount(user_name, user_realName,
						user_did, flag, user.getDid());
			} else if ("4".equals(user.getFlag())) {// 省外项目部
				count = userdao.otherGetUserCount(user_name, user_realName,
						user_did, flag, user.getDid());

			}
		}

		return count;
	}

	// 用户管理--查询用户信息
	public List<User> getUserInfo(int page, int rows, String user_name,
			String user_realName, String user_did, String flag, User user) {
		List<User> list = new ArrayList<User>();
		if (user != null) {
			if ("0".equals(user.getFlag())) {// 管理员
				list = userdao.getUser(page, rows, user_name, user_realName,
						user_did, flag);
			} else if ("1".equals(user.getFlag())) {// 司机
				list = userdao.driverGetUser(page, rows, user_name,
						user_realName, user_did, flag, user.getId());
			} else if ("2".equals(user.getFlag())) {// 供应商
				list = userdao.supplierGetUser(page, rows, user_name,
						user_realName, user_did, flag, user.getSuppliers_id());

			} else if ("3".equals(user.getFlag())) {// 省内项目部
				list = userdao.otherGetUser(page, rows, user_name,
						user_realName, user_did, flag, user.getDid());

			} else if ("4".equals(user.getFlag())) {// 省外项目部
				list = userdao.otherGetUser(page, rows, user_name,
						user_realName, user_did, flag, user.getDid());
			}
		}

		return list;
	}

	// 用户管理--修改用户
	public boolean modifyUser(User user) {
		int b = userdao.modifyUser(user);
		if (b > 0)
			return true;
		else
			return false;
	}

	// 用户管理--删除用户
	public boolean delUser(String id) {
		int i = userdao.delUser(id);
		if (i > 0)
			return true;
		else
			return false;
	}

	// 用户管理--删除用户信息删除角色信息
	public boolean delUserRole(String id) {
		int i = userdao.delUserRole(id);
		if (i > 0)
			return true;
		else
			return false;
	}

	// 用户管理--添加用户信息
	public boolean addUser(User user) {
		int i = userdao.addUser(user);
		if (i > 0)
			return true;
		else
			return false;
	}

	// 用户管理 --添加用户时保存角色信息
	public boolean saveRole(String id, Integer rid) {
		int i = userdao.saveRole(id, rid);
		if (i > 0)
			return true;
		else
			return false;
	}

	// 用户管理--添加用户检查用户名是否存在
	public boolean checkUser(String name) {
		int i = userdao.checkUser(name);
		if (i > 0)
			return true;
		else
			return false;
	}

	// 用户管理--重置用户密码
	public boolean resetpassword(String id) {
		int i = userdao.resetpassword(id);
		if (i > 0)
			return true;
		else
			return false;
	}

	// 用户管理--导出用户信息
	public List<User> getAllUser(String[] ids, User user, String user_name,
			String user_realName, String department_id, String flag) {
		List<User> list = new ArrayList<User>();
		if (user != null) {
			if ("0".equals(user.getFlag())) {// 管理员
				list = userdao.getAllUser(ids, user_name, user_realName,
						department_id, flag);

			} else if ("1".equals(user.getFlag())) {// 司机

				list = userdao.driverOutPutUser(ids, user_name, user_realName,
						department_id, flag, user.getId());

			} else if ("2".equals(user.getFlag())) {// 供应商
				list = userdao.suppliersOutPutUser(ids, user_name,
						user_realName, department_id, flag,
						user.getSuppliers_id());

			} else if ("3".equals(user.getFlag())) {// 省内项目部
				list = userdao.otherOutPutUser(ids, user_name, user_realName,
						department_id, flag, user.getDid());

			} else if ("4".equals(user.getFlag())) {// 省外项目部
				list = userdao.otherOutPutUser(ids, user_name, user_realName,
						department_id, flag, user.getDid());

			}
		}
		return list;
	}

	// 根据id查询用户信息
	public User getUser1(String id) {
		User user = userdao.getUser1(id);
		return user;
	}

	// 根据部门编号 拿部门名称
	public String getDepartment(String did) {
		return userdao.getDepartment(did);
	}

	// 获取部门列表
	public List<Department> getDepartments() {
		return userdao.getDepartments();
	}

	// 获取角色列表
	public List<Role> getRoles() {

		return userdao.getRoles();
	}

	// 修改角色与用户的关联表
	public boolean saveRoles(Integer id, Integer rid) {
		int i = userdao.saveRoles(id, rid);
		if (i > 0)
			return true;
		else
			return false;
	}

	// 取出最大用户id
	public int getUserId() {
		return userdao.getUserId();
	}

	// 根据用户id拿角色id
	public int getRole(String id) {
		int role = roledao.getUserRole(id);

		return role;
	}

	// 修改密码
	public boolean modifyPassword(String id, String password) {
		int b = userdao.modifyPassword(id, password);
		if (b > 0)
			return true;
		else
			return false;
	}

	// 获取部门名字
	public String namegetDepartment(String id) {
		return userdao.namegetDepartment(id);
	}

	// 根据id查询用户信息
	public User getOneUser(String id) {
		User user = userdao.getUser1(id);
		return user;
	}

	// 获取用户个人信息
	public User getAppUser(String id) {
		User user = userdao.getAppUser(id);
		return user;
	}

	// 获取指定部门
	public List<Department> getDepartmentName(String department_name) {
		List<Department> list = userdao.getDepartmentName(department_name);
		return list;
	}

	// 获取app版本号
	public AppVersion getAppVersion() {
		AppVersion app = userdao.getAppVersion();
		return app;
	}

	// 用户意见信息
	public int saveAppMessage(AppMessage appmessage) {
		int flag = userdao.saveAppMessage(appmessage);
		return flag;
	}

	// 更新部门
	public int updateDepartment(String creatingUser, String institutionId) {
		int flag = userdao.updateDepartment(creatingUser, institutionId);
		return flag;
	}

	// 用户管理--查询司机信息
	public List<Driver> getDrivers(String search) {

		return userdao.getDrivers(search);
	}

	// 用户管理--查询供应商
	public List<JySuppliers> getJySuppliers(String search) {

		return userdao.getJySuppliers(search);
	}

	// 用户修改--更新对应的角色
	public int updateUser_Role_Info(String userId, String roleId) {

		return userdao.updateUser_Role_Info(userId, roleId);
	}
}
