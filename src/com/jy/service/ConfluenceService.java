package com.jy.service;

import java.util.List;

import com.jy.model.Confluence;
import com.jy.model.Mingxi;
import com.jy.model.ShippingOrder;

public interface ConfluenceService {

	// 全部查询
			List<Confluence> getConfluence(int rows,int page,String direction,String fee_date,String bb);	
			//分页查询总数
			int getConfluenceInfo( String direction,String fee_date,String bb);
			// 全部查询
			// 全部查询
			List<Mingxi> getMingxi(int rows,int page,String subject);	
			//分页查询总数
			int getMingxiInfo( String subject);
			Mingxi gets(String id);
}
