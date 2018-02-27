package com.jy.service;

import java.util.List;

import com.jy.model.Department;
import com.jy.model.User;

/**
 * 部门接口
 * 
 * @author Administrator
 * 
 */
public interface DepartmentService {

	/**
	 * 获取指定部门
	 * 
	 * @param id
	 * @return
	 */
	public Department getDepartment(String id);

	/**
	 * 修改部门
	 * 
	 * @param depn
	 * @return
	 */
	public boolean modifyDepartment(Department depn);

	/**
	 * 删除部门
	 * 
	 * @param id
	 * @return
	 */
	public boolean delDepartment(String id);

	/**
	 * 添加部门
	 * 
	 * @param depn
	 * @return
	 */
	public boolean addDepartment(Department depn);

	/**
	 * 获取部门总数
	 * 
	 * @return
	 */
	public int getCount();

	/**
	 * 部门管理 获取部门树
	 * 
	 * @param id
	 * @return
	 */
	public String getTree(String id);

	/**
	 * 获取部门树s
	 * 
	 * @param id
	 * @return
	 */
	public String getTree2(String id);

	/**
	 * 检查部门名是否重复
	 * 
	 * @param name
	 * @param fid
	 * @return
	 */
	public boolean checkDepn(String name, String fid);

	/**
	 * 获取子部门
	 * 
	 * @param id
	 * @return
	 */
	public List<Department> getDepnkids(String id);

	/**
	 * 用户查询自己下面的部门信息
	 * 
	 * @param id
	 * @param user
	 * @return
	 */
	public String getDepartmentTree(String id, User user);
}
