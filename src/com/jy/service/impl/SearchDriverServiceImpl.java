package com.jy.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jy.dao.SearchDriverDao;
import com.jy.model.Comment;

import com.jy.service.SearchDriverService;

@Service("SearchDriverService")
public class SearchDriverServiceImpl implements SearchDriverService{
	@Resource
	private SearchDriverDao sdo;
	//通过查询收货人、联系方式,展示司机信息到app
	public Comment getSearchDriver(String receipt_name, String receipt_tel,String shiping_order_num) {
		// TODO Auto-generated method stub
		return sdo.getSearchDriver(receipt_name, receipt_tel,shiping_order_num);
	}
	public int saveComment(Comment comment) {
		// TODO Auto-generated method stub
		return sdo.saveComment(comment);
	}

}
