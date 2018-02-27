package com.jy.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jy.model.Equipment_info;
import com.jy.model.Truck;
import com.jy.model.User;

public interface EquipmentManDao {
	// 设备一览
	List<Equipment_info> getEquipmentMan(int page, int rows,
			String equipment_number, String carid, String department_id,String car_number);

	// 供应商获取设备
	List<Equipment_info> supplerGetEquipmentMan(int page, int rows,
			String equipment_number, String carid, String department_id,String car_number,
			String supplerId);

	// 项目部获取设备
	List<Equipment_info> customerGetEquipmentMan(int page, int rows,
			String equipment_number, String carid, String department_id,String car_number,
			String customerId);

	List<Equipment_info> getEquipmentManInfoOne();

	int InfoManAdd(Equipment_info equipment_info);

	// 总条数
	int getCount(String equipment_number, String car_id, String is_binding,String car_number);

	// 总条数
	int supplerGetCount(String equipment_number, String car_id, String is_binding,String car_number,String supplerId);

	// 总条数
	int customerGetCount(String equipment_number, String car_id, String is_binding,String car_number,String customerId);

	// 获取设备列表下拉框
	public List<Equipment_info> getEquipment_number(String search);

	// 获取车牌号列表下拉框
	public List<Equipment_info> getCar_id(String carid);

	// 获取车牌号列表下拉框
	public List<Equipment_info> getCar_idAdd(String carid);

	// 删除设备
	int deleteEquipment(String[] del);

	// 查询要修改的信息
	Equipment_info getUpdateEquipment(String pid);

	// 修改设备
	int updateEquipment(Equipment_info equipment_info);

	// 是否存在
	int checkEquipment(String equipment_number);

	// 是否可修改
	int checkEquipmentSinger(String equipment_number, String equipment_id);

	int createEquTable(String tablename);

	// 获取用户信息
	List<User> getUserName(String username);

	// 绑定操作
	public int bundlingUserInfo(@Param("array") String[] id);

	// 解除绑定操作
	public int unbundlingUserInfo(@Param("array") String[] id);

	// 获取用户车牌号 是否存在
	public int getCarNumberCount(@Param("carNumber") String carNumber);
}
