package com.jy.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import com.jy.dao.ConfluenceDAO;
import com.jy.model.Confluence;
import com.jy.model.Mingxi;
import com.jy.model.ShippingOrder;
import com.jy.service.ConfluenceService;
@Controller
public class ConfluenceServiceImpl implements ConfluenceService {
	@Resource
	private ConfluenceDAO cdao ;
	public List<Confluence> getConfluence(int rows, int page,
			String direction,String fee_date,String bb) {
		List<Confluence> list = cdao.getConfluence(rows, page, direction,fee_date, bb );
		return list;
	}

	public int getConfluenceInfo(String direction,String fee_date,String bb) {
		int i = cdao.getConfluenceInfo(direction, fee_date, bb);
		return i;
	}

	// 全部查询
	//分页查询总数
	public List<Mingxi> getMingxi(int rows, int page, String subject) {
		List<Mingxi>list=cdao.getMingxi(rows, page, subject);
		return list;
	}

	public int getMingxiInfo(String subject) {
		int i=cdao.getMingxiInfo(subject);
		return i;
	}

	public Mingxi gets(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
