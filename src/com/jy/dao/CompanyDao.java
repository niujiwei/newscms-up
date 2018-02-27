package com.jy.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jy.model.Company;
import com.jy.model.Income;
import com.jy.model.ShippingOrder;
import com.jy.model.Sign_order;

public  interface CompanyDao {
	/**
	 * 中转公司dao
	 * 费家震
	 */ 
	// 全部查询
	List<Company> getCompany(int rows,int page,String company_name,String start_people,String end_phone, String phone);	
	//分页查询总数
	int getCompanyInfo( String company_name,String start_people,String end_phone, String phone);
	// 添加
	 int addCompany(Company d);//添加
	//删除
	 int deleteCompany(@Param("array") String[] del);//删除
	 // 
	 Company getUpdateCompany(String id);//修改查询
	 int updateCompany(Company d);//用户修改
}
