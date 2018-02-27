package com.jy.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jy.model.City_info;
import com.jy.model.Department;
import com.jy.model.JySuppliers;

public interface SuppliersDao {
	// 管理员--供应商信息导出
	List<JySuppliers> getSuppUserOutPut(String name, String phone,
			String address, String id, String suppliers_customer,
			@Param("array") String[] dataIds);

	// 司机--供应商信息导出
	List<JySuppliers> getSupplierOutPut(String name, String phone,
			String address, String id, String suppliers_customer,
			@Param("array") String[] dataIds);

	// 供应商--供应商信息导出
	List<JySuppliers> getSuppliersOutPut(String name, String phone,
			String address, String id, String suppliers_customer,
			@Param("array") String[] dataIds);

	// 录单员--供应商信息导出
	List<JySuppliers> otherGetSuppUserOutPut(String name, String phone,
			String address, String id, String suppliers_customer,
			@Param("array") String[] dataIds);

	// 管理员--供应商信息查询
	List<JySuppliers> getSuppUser(int rows, int page, String name,
			String phone, String address, String id, String suppliers_customer);

	// 司机--供应商信息查询
	List<JySuppliers> getSupplier(int rows, int page, String name,
			String phone, String address, String id, String suppliers_customer);

	// 供应商--供应商信息查询
	List<JySuppliers> getSuppliers(int rows, int page, String name,
			String phone, String address, String id, String suppliers_customer);

	// 录单员--供应商信息查询
	List<JySuppliers> otherGetSuppUser(int rows, int page, String name,
			String phone, String address, String id, String suppliers_customer);

	// 管理员--供应商信息查询总条数商
	int getSuppCounUser(String name, String phone, String address, String id,
			String suppliers_customer);

	// 司机--供应商信息查询总条数
	int getSuppliersCoun(String name, String phone, String address, String id,
			String suppliers_customer);

	// 供应商--供应商信息查询总条数
	int getSuppliersCount(String name, String phone, String address, String id,
			String suppliers_customer);

	// 录单员--供应商信息查询总条数
	int otherGetSuppCounUser(String name, String phone, String address,
			String id, String suppliers_customer);

	// 车辆类型
	List<String> getCartype();

	// 添加供应商信息
	int saveSuppliers(JySuppliers suppliers);

	// 供应商信息添加添加 省市县
	int saveSupplersCityInfo(List<JySuppliers> jyslist);

	// 添加供应商信息保存图片
	int updateSupplierImage(String images, String suppId);

	// 删除供应商省市县
	int deleteSuppliersCityInfo(@Param("array") String[] pid);

	// 获取要删除的供应商信息
	JySuppliers getUpdateSuppliers(String cid);

	// 删除供应商信息
	int deletesuppliers(@Param("array") String[] pid);

	// 更新供应商信息
	int updateSuppliers(JySuppliers jyuppliers);

	// select2 查询供应商信息
	List<JySuppliers> getSpriadd();

	// 获取供应商的省市县
	List<City_info> getCityInfo(String jysId);
	
	//获取省市县多选
	List<City_info> getCity_Info(@Param("province")List<Integer> province,
			@Param("city")	List<Integer> city,@Param("county") List<Integer> county);
	//获取省市名称
	List<String> getCity_Info_name(@Param("province")List<Integer> province,
			@Param("city")	List<Integer> city,@Param("county") List<Integer> county);

}
