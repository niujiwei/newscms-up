package com.jy.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jy.model.Company;
import com.jy.model.CreateAgreement;
import com.jy.model.GuanlianCA;
import com.jy.model.ShippingOrder;

public interface CreatAgreementDao {
	int getCreateAgreement(String id, String start_date, String end_date,
			String car_name,String car_number1,String agreement_type,String end_address,String company_id,String phone_number,String order_state,String departid);// 查询总数

	List<CreateAgreement> getCreateAgreementInfo(int rows, int page, String id,
			String start_date, String end_date,String car_name,String car_number1,String agreement_type,String end_address,String company_id,String phone_number,String order_state,String departid); // 查询

	int saveCreateAgreement(CreateAgreement d);// 保存

	CreateAgreement getUpdateCreateAgreement(String id);// 修改查询

	int updateCreateAgreement(CreateAgreement d);// 用户修改

	int searchOrder(String ids);

	/*
	 * 获取数据下拉表的查询
	 */
	List<CreateAgreement> getPlateNumberLength(String number);

	CreateAgreement getNumber(String number);// 重复订单号查询

	List<ShippingOrder> getCreateAgreementAll(@Param("array") String[]getCreateAgreementAll);

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
	int updateState(Integer state,@Param("array") String[] pid);

	/**
	 * 详情信息订单号关联查询
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
	int deleteCreateAgreement(@Param("array") String[] del);// 删除

	/**
	 * 关联表订单号查询
	 * 
	 * @param del
	 * @return
	 */
	List<GuanlianCA> orderNumber(@Param("array") String[] del);

	/**
	 * 删除关联表的关联信息
	 * 
	 * @param del
	 * @return
	 */
	int deleToOder(@Param("array") String[] del);

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
	int updataZong(String cid,String money,Integer count);
	/**
	 * 移除协议中的运单
	 * 
	 * @param name
	 * @return
	 */
	int removeShip(String cid,@Param("array") String []pid);
}
