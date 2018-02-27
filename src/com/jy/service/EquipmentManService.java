package com.jy.service;

import java.util.List;

import com.jy.model.Equipment_info;
import com.jy.model.Truck;
import com.jy.model.User;

/**
 * 2015年5月5日 11:54:51
 * 
 * @author zkj 设备管理service
 */
public interface EquipmentManService {
	// 设备查询
	public List<Equipment_info> getEquipmentManInfo(int page, int rows,
			String equipment_number, String carid, String department_id,String car_number,User user);

	public List<Equipment_info> getEquipmentManInfoOne();

	int InfoManAdd(Equipment_info equipment_info);

	// 获取总条数
	public int getCount(String equipment_number, String carid,
			String department_id,String car_number,User user);

	// 获取设备列表下拉框
	public List<Equipment_info> getEquipment_number(String search);

	// 获取车牌号列表下拉框
	public List<Equipment_info> getCar_id(String carid);

	// 获取车牌号列表下拉框
	public List<Equipment_info> getCar_idAdd(String carid);

	// 删除设备
	public int deleteEquipment(String[] del);

	// 查询修改的信息
	public Equipment_info getUpdateEquipment(String pid);

	// 修改设备信息
	public int updateEquipment(Equipment_info equipment_info);

	// 是否重复
	public int checkEquipment(String equipment_number);

	public int checkEquipmentSinger(String equipment_number, String equipment_id);

	public int createEquTable(String string);

	// 获取用户信息
	public List<User> getUserName(String username);

	// 绑定操作
	public int bundlingUserInfo(String[] id);

	// 解除绑定操作
	public int unbundlingUserInfo(String[] id);
	
	// 获取用户车牌号 是否存在
	public int getCarNumberCount(String carNumber);
}
