package com.jy.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jy.dao.SearchWaybillDao;
import com.jy.model.Comment;
import com.jy.model.OrderHistory;
import com.jy.model.ShippingOrder;
import com.jy.service.SearchWaybillService;

@Service("SearchWaybillService")
public class SearchWaybillServiceImpl implements SearchWaybillService {
	@Resource
	private SearchWaybillDao searchWaybillDao;

	// 新增app端查看运单历史记录，展示运单信息节点
	public List<OrderHistory> getSearchWaybill(String shiping_order_num) {

		return searchWaybillDao.getSearchWaybill(shiping_order_num);
	}

	// 查询运单信息
	public List<ShippingOrder> getShippingOrders(String shiping_order_num,
			String shiping_order_goid) {

		return searchWaybillDao.getShippingOrders(shiping_order_num,
				shiping_order_goid);
	}

	//获取要评价的详细信息
	public List<ShippingOrder> getOrderMessage(String id) {
		return searchWaybillDao.getOrderMessage(id);
	}
	
	

	//保存评价信息
	public int saveComment(Comment comment) {
		
		return searchWaybillDao.saveComment(comment);
	}

	//看是否有评论
	public int getCommentCount(String id) {
	
		return searchWaybillDao.getCommentCount(id);
	}

}
