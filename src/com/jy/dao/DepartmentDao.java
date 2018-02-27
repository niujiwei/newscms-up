package com.jy.dao;

import java.util.List;

import com.jy.model.Department;

/**
 * 部门dao
 * 
 * @author Administrator
 * 
 */
public interface DepartmentDao {
	// 部门管理 获取部门树
	Department getDepartmentf();

	// 获取部门子节点信息
	List<Department> getDepartments(String id);

	// 获取指定部门
	Department getDepartment(String id);

	// 修改部门
	int modifyDepartment(Department depn);

	// 删除部门
	int delDepartment(String id);

	// 删除部门
	int delDepartments(String id);

	// 添加部门
	int addDepartment(Department depn);

	// 获取部门总数
	int getCount();

	// 检查部门名是否重复
	int checkDepn(String name, String fid);

	// 获取部门树
	Department getDepartmentDisanfang();

}
