package com.jy.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jy.model.Company;
import com.jy.model.CreateAgreement;
import com.jy.model.GuanlianCA;
import com.jy.model.ShippingOrder;

public interface CreateAgreementInfoService {
	// 所有人员的查询
	public List<CreateAgreement> getCreateAgreementInfo(int rows, int page,
			String id, String start_date, String end_date,String car_name,String car_number1,String agreement_type,String end_address,String company_id,String phone_number,String order_state,String departid);

	public int getCreateAgreement(String id, String start_date,
			String end_date,String car_name,String car_number1,String agreement_type,String end_address,String company_id,String phone_number,String order_state,String departid);

	// 保存
	public int saveCreateAgreement(CreateAgreement d);

	// 修改查询
	public CreateAgreement getUpdateCreateAgreement(String id);

	// 用户修改
	public int updateCreateAgreement(CreateAgreement d);

	/*
	 * select 查询
	 */
	int searchOrder(String ids);

	List<CreateAgreement> getPlateNumberLength(String number);

	CreateAgreement getNumber(String number);// 重复号查询

	List<ShippingOrder> getCreateAgreementAll(String[]agreementIds);// 导出查询

	/**
	 * 关联表的保存
	 * 
	 * @param list
	 * @return
	 */
	int saveAgreementToorder(List<GuanlianCA> list);

	/**
	 * 制作协议修改订单状态
	 * 
	 * @param pid
	 * @return
	 */
	public int updateState(String[] pid,Integer state);

	/**
	 * 详情信息订单号查询
	 * 
	 * @param id
	 * @return
	 */
	List<GuanlianCA> getXXCreateAgreement(String id);

	// 作废时 的步骤
	/**
	 * 协议表的删除
	 * 
	 * @param del
	 * @return
	 */
	int deleteCreateAgreement(String[] del);// 删除

	/**
	 * 关联表订单号查询
	 * 
	 * @param del
	 * @return
	 */
	List<GuanlianCA> orderNumber(String[] del);

	/**
	 * 删除关联表的关联信息
	 * 
	 * @param del
	 * @return
	 */
	int deleToOder(String[] del);

	/**
	 * 将订单修改到初始状态
	 * 
	 * @param list
	 * @return
	 */
	int deleState(List<GuanlianCA> list);

	/**
	 * 中转公司查询
	 * 
	 * @param name
	 * @return
	 */
	List<Company> getCompany(String name);

	/**
	 * 修改金额
	 * 
	 * @param name
	 * @return
	 */
	int updataMoney(String id, String practiacl_num, String send_fee,
			String send_remarks, String change_pay, String change_remarks,String chanage_pay_type,String change_other);
	/**
	 * 修改总金额
	 * 
	 * @param name
	 * @return
	 */
	int updataZong(String id,String money,Integer count);
	/**
	 * 修改总金额
	 * 
	 * @param name
	 * @return
	 */
	int removeShip(String cid,String []pid);
}
