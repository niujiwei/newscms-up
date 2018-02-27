package com.jy.service.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import com.jy.dao.SearchCommentDao;
import com.jy.model.Comment;
import com.jy.model.User;
import com.jy.service.SearchCommentService;

@Component
public class SearchCommentServiceImpl implements SearchCommentService {
	@Resource
	private SearchCommentDao scd;
	//评论列表
	public List<Comment> getCommentList(int row, int page,
			String shiping_order_num, String customer_name, String end_address,
			String driver_name, String receipt_name, String id, User user) {
		List<Comment> list = null;
		if (user != null) {
			if (user.getFlag().equals("0")) {// 管理员
				list = scd.getCommentList(row, page, shiping_order_num,
						customer_name, end_address, driver_name, receipt_name,
						user.getSuppliers_id());
			} else if (user.getFlag().equals("1")) {// 司机
				list = scd.getCommentList1(row, page, shiping_order_num,
						customer_name, end_address, driver_name, receipt_name,
						user.getId());
			} else if (user.getFlag().equals("2")) {// 供应商
				list = scd.getCommentList2(row, page, shiping_order_num,
						customer_name, end_address, driver_name, receipt_name,
						user.getSuppliers_id());
			} else if (user.getFlag().equals("3") || user.getFlag().equals("4")) {// 信息员
				list = scd.getCommentList3(row, page, shiping_order_num,
						customer_name, end_address, driver_name, receipt_name,
						id);
			}
		}
		return list;
	}
//评论统计
	public int getComment_count(String shiping_order_num, String customer_name,
			String end_address, String driver_name, String receipt_name,
			String id, User user) {
		int count = 0;
		if (user != null) {
			if (user.getFlag().equals("0")) {// 管理员
				count = scd.getComment_count(shiping_order_num, customer_name,
						end_address, driver_name, receipt_name, "");
			} else if (user.getFlag().equals("1")) {// 司机
				count = scd.getComment_count1(shiping_order_num, customer_name,
						end_address, driver_name, receipt_name, user.getId());
			} else if (user.getFlag().equals("2")) {// 供应商
				count = scd.getComment_count2(shiping_order_num, customer_name,
						end_address, driver_name, receipt_name,
						user.getSuppliers_id());
			} else if (user.getFlag().equals("3") || user.getFlag().equals("4")) {// 信息员
				count = scd.getComment_count3(shiping_order_num, customer_name,
						end_address, driver_name, receipt_name, id);
			}
		}
		return count;
	}
//删除评论
	public int deleteComment(String[] comment_id) {
		return scd.deleteComment(comment_id);
	}
	//更加id 获取信息
	public List<Comment> getCommentListByIds(String[] dataIds) {
		
		return scd.getCommentListByIds(dataIds);
	}
}
