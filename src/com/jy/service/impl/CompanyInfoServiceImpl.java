package com.jy.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.jy.dao.CompanyDao;
import com.jy.model.Company;
import com.jy.model.Income;
import com.jy.service.CompanyInfoService;
@Component
public class CompanyInfoServiceImpl implements CompanyInfoService {
	@Resource
	private CompanyDao companydao;
	
	public List<Company> getCompany(int rows, int page, String company_name,
			String start_people, String end_phone, String phone) {
		List<Company> list = companydao.getCompany(rows, page, company_name, start_people, end_phone, phone);
		return list;
	}


	public int getCompanyInfo(String company_name, String start_people,
			String end_phone, String phone) {
		 int i = companydao.getCompanyInfo(company_name, start_people, end_phone, phone);
			return i;
	}


	public int addCompany(Company d) {
		int i = companydao.addCompany(d);
		return i;
	}


	public int deleteCompany(String[] del) {
		 int i  = companydao.deleteCompany(del);
		return i;
	}


	public Company getUpdateCompany(String id) {
			Company sp=companydao.getUpdateCompany(id);
		return sp;
	}


	public int updateCompany(Company d) {
		int i = companydao.updateCompany(d);
		return i;
	}


	






}
