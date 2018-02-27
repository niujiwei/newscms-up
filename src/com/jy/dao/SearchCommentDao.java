package com.jy.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jy.model.Comment;

public interface SearchCommentDao {
	// 管理员
	List<Comment> getCommentList(int row, int page, String shiping_order_num,
			String customer_name, String end_address, String driver_name,
			String receipt_name, String id);

	int getComment_count(String shiping_order_num, String customer_name,
			String end_address, String driver_name, String receipt_name,
			String id);

	// 供应商
	List<Comment> getCommentList2(int row, int page, String shiping_order_num,
			String customer_name, String end_address, String driver_name,
			String receipt_name, String id);

	int getComment_count2(String shiping_order_num, String customer_name,
			String end_address, String driver_name, String receipt_name,
			String id);

	// 司机
	List<Comment> getCommentList1(int row, int page, String shiping_order_num,
			String customer_name, String end_address, String driver_name,
			String receipt_name, String id);

	int getComment_count1(String shiping_order_num, String customer_name,
			String end_address, String driver_name, String receipt_name,
			String id);

	// 信息员
	List<Comment> getCommentList3(int row, int page, String shiping_order_num,
			String customer_name, String end_address, String driver_name,
			String receipt_name, String id);

	int getComment_count3(String shiping_order_num, String customer_name,
			String end_address, String driver_name, String receipt_name,
			String id);

	// 根据id获取评价信息
	List<Comment> getCommentListByIds(@Param("array") String[] dataIds);

	// 删除评论
	int deleteComment(@Param("array") String[] comment_id);
}
