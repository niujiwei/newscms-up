package com.jy.service;
import com.jy.model.Comment;

public interface SearchDriverService {
	//通过查询收货人、联系方式,展示司机信息到app
		public Comment getSearchDriver(String receipt_name,String receipt_tel,String shiping_order_num);
		int saveComment(Comment comment);
}
