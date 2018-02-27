package com.jy.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import com.jy.dao.CreatAgreementDao;
import com.jy.model.Company;
import com.jy.model.CreateAgreement;
import com.jy.model.GuanlianCA;
import com.jy.model.ShippingOrder;
import com.jy.service.CreateAgreementInfoService;
@Controller
public class CreateAgreementInfoServiceImpl  implements CreateAgreementInfoService{
	@Resource
	private CreatAgreementDao ca;
	//查询分页

	public List<CreateAgreement> getCreateAgreementInfo(int rows, int page,
			String id, String start_date, String end_date,String car_name,String car_number1,String agreement_type,String end_address,String company_id,String phone_number,String order_state,String departid) {
		List<CreateAgreement> list=ca.getCreateAgreementInfo(rows, page, id, start_date, end_date,car_name,car_number1,agreement_type,end_address,company_id,phone_number,order_state,departid);
		return list;
	}

	public int getCreateAgreement(String id, String start_date, String end_date,String car_name,String car_number1,String agreement_type,String end_address,String company_id,String phone_number,String order_state,String departid) {
		int i=ca.getCreateAgreement(id, start_date, end_date,car_name,car_number1,agreement_type,end_address,company_id,phone_number,order_state,departid);
		return i;
	}

	public int saveCreateAgreement(CreateAgreement d) {
		int i=ca.saveCreateAgreement(d);
		return i;
	}

	public CreateAgreement getUpdateCreateAgreement(String id) {
		CreateAgreement c=ca.getUpdateCreateAgreement(id);
		return c;
	}

	public int updateCreateAgreement(CreateAgreement d) {
		int i=ca.updateCreateAgreement(d);
		return i;
	}

	public List<CreateAgreement> getPlateNumberLength(String number) {
		List<CreateAgreement> list=ca.getPlateNumberLength(number);
		return list;
	}

	public CreateAgreement getNumber(String number) {
		CreateAgreement c=ca.getNumber(number);
		return c;
	}

	public List<ShippingOrder> getCreateAgreementAll(String[]getCreateAgreementAll) {
		 List<ShippingOrder> list=ca.getCreateAgreementAll(getCreateAgreementAll);
		return list;
	}

	public int  saveAgreementToorder(List<GuanlianCA> list) {
		int i=ca.saveAgreementToorder(list);
		return i;
	}

	/**
	 * 制作协议修改订单好
	 */
	public int updateState(String[] pid,Integer state) {
		int i=ca.updateState(state,pid);
		return i;
	}
	/**
	 * 详情信息订单号查询
	 * @param id
	 * @return
	 */
	public List<GuanlianCA> getXXCreateAgreement(String id) {
		List<GuanlianCA> list=ca.getXXCreateAgreement(id);
		return list;
	}

/**
 * 	获取订单id
 */
	public List<GuanlianCA> orderNumber(String[] del) {
		List<GuanlianCA> list=ca.orderNumber(del);
		return list;
	}
/**
 * 删除关联表表
 */
	public int deleToOder(String[] del) {
		int i=ca.deleToOder(del);
		return i;
	}
/**
 * 协议删除时候，修改订单状态
 */
	public int deleState(List<GuanlianCA> list) {
		int i=ca.deleState(list);
		return i;
	}
	/**
	 * 删除协议表
	 */
	public int deleteCreateAgreement(String[] del) {
		int i=ca.deleteCreateAgreement(del);
		return i;
	}
	/**
	 * 中转公司查询
	 */
	public List<Company> getCompany(String name) {
		List<Company> list=ca.getCompany(name);
		return list;
	}

	public int searchOrder(String ids) {
		int i  = ca.searchOrder(ids);
		return i;
	}
	/**
	 * 修改金额
	 * */
	public int updataMoney(String id, String practiacl_num, String send_fee,
			String send_remarks, String change_pay, String change_remarks,String chanage_pay_type,String change_other) {
		// TODO Auto-generated method stub
		return ca.updataMoney(id, practiacl_num, send_fee, send_remarks, change_pay, change_remarks,chanage_pay_type,change_other);
	}
	/**
	 * 修改总金额
	 * 
	 * @param name
	 * @return
	 */
	public int updataZong(String id,String money,Integer count){
		return ca.updataZong(id,money,count);
	}
	/**
	 * 修改总金额
	 * 
	 * @param name
	 * @return
	 */
	public int removeShip(String cid,String []pid){
		return ca.removeShip(cid,pid);
	}
}
