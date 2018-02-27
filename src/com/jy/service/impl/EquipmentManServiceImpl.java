package com.jy.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.jy.common.UUIDUtils;
import com.jy.dao.EquipmentManDao;
import com.jy.model.Equipment_info;
import com.jy.model.ShippingOrder;
import com.jy.model.User;
import com.jy.service.EquipmentManService;

@Component
public class EquipmentManServiceImpl implements EquipmentManService {
	@Resource
	public EquipmentManDao equipmentManDao;

	// 查询
	public List<Equipment_info> getEquipmentManInfo(int page, int rows,
			String equipment_number, String carid, String department_id,String car_number,User user) {
		List<Equipment_info> list = null;
		if (user != null) {
			if (user.getFlag().equals("0")) {// 管理员
				list = equipmentManDao.getEquipmentMan(page, rows,
						equipment_number, carid, department_id,car_number);
			} else if (user.getFlag().equals("1")) {// 司机
				
			} else if (user.getFlag().equals("2")) {// 供应商
				list = equipmentManDao.supplerGetEquipmentMan(page, rows, equipment_number, carid, department_id,car_number, user.getSuppliers_id());

			} else if (user.getFlag().equals("3") || user.getFlag().equals("4")) {// 信息员
				list = equipmentManDao.customerGetEquipmentMan(page, rows, equipment_number, carid, department_id,car_number, user.getCustomer_id());
			}
		}
		return list;
	}

	public List<Equipment_info> getEquipmentManInfoOne() {
		List<Equipment_info> equipment_infos = equipmentManDao
				.getEquipmentManInfoOne();
		return equipment_infos;
	}

	/**
	 * 新增
	 */
	public int InfoManAdd(Equipment_info equipment_info) {
		equipment_info.setEquipment_id(UUIDUtils.getUUID());
		int i = equipmentManDao.InfoManAdd(equipment_info);
		return i;
	}

	// 获取总条数
	public int getCount(String equipment_number, String car_id,
			String is_binding,String car_number,User user) {
		int count = 0;
		if (user != null) {
			if (user.getFlag().equals("0")) {// 管理员
				count =equipmentManDao.getCount(equipment_number, car_id, is_binding,car_number);
			} else if (user.getFlag().equals("1")) {// 司机
				
			} else if (user.getFlag().equals("2")) {// 供应商
				count = equipmentManDao.supplerGetCount(equipment_number, car_id, is_binding, car_number,user.getSuppliers_id());

			} else if (user.getFlag().equals("3") || user.getFlag().equals("4")) {// 信息员
				count = equipmentManDao.customerGetCount(equipment_number, car_id, is_binding,car_number, user.getCustomer_id());
				
			}
		}

		return count;
		
		
		
		
	}

	// 获取设备号下拉的数据
	public List<Equipment_info> getEquipment_number(String search) {
		equipmentManDao.getEquipment_number(search);
		return equipmentManDao.getEquipment_number(search);
	}

	// 获取车牌号列表下拉框
	public List<Equipment_info> getCar_id(String carid) {
		equipmentManDao.getCar_id(carid);
		return equipmentManDao.getCar_id(carid);
	}

	public int deleteEquipment(String[] del) {
		int flag = equipmentManDao.deleteEquipment(del);
		return flag;
	}

	public Equipment_info getUpdateEquipment(String pid) {
	
		Equipment_info equipment_info = equipmentManDao.getUpdateEquipment(pid);
		return equipment_info;
	}

	public int updateEquipment(Equipment_info equipment_info) {
	
		int flag = equipmentManDao.updateEquipment(equipment_info);
		return flag;
	}

	public int checkEquipment(String equipment_number) {
	
		int flag = equipmentManDao.checkEquipment(equipment_number);
		return flag;
	}

	public int checkEquipmentSinger(String equipment_number, String equipment_id) {
	
		int flag = equipmentManDao.checkEquipmentSinger(equipment_number,
				equipment_id);
		return flag;
	}

	// 获取车牌号列表下拉框
	public List<Equipment_info> getCar_idAdd(String carid) {
		List<Equipment_info> list = equipmentManDao.getCar_idAdd(carid);
		return list;
	}
	//建表日期表
	public int createEquTable(String tablename) {
		int falg = equipmentManDao.createEquTable(tablename);
		return falg;
	}
	//获取用户信息
	public List<User> getUserName(String username) {
		List<User> list = equipmentManDao.getUserName(username);

		return list;
	}
	//绑定设备
	public int bundlingUserInfo(String[] id) {
		
		return equipmentManDao.bundlingUserInfo(id);
	}

	public int unbundlingUserInfo(String[] id) {
		
		return equipmentManDao.unbundlingUserInfo(id);
	}
	
	// 获取用户车牌号 是否存在
	public int getCarNumberCount(String carNumber) {
	    
		return equipmentManDao.getCarNumberCount(carNumber);
	}
}
