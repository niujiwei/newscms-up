package com.jy.dao;

import java.util.List;

import com.jy.model.Comment;


public interface SearchDriverDao {
	//通过查询收货人、联系方式,展示司机信息到app
	Comment getSearchDriver(String receipt_name,String receipt_tel,String shiping_order_num);
	int saveComment(Comment comment);
}
