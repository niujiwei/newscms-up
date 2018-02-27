package com.jy.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jy.model.Comment;
import com.jy.model.User;

public interface SearchCommentService {
	// 查询评论列表
	List<Comment> getCommentList(int row, int page, String shiping_order_num,
			String customer_name, String end_address, String driver_name,
			String receipt_name, String id, User user);

	// 统计评论数据
	int getComment_count(String shiping_order_num, String customer_name,
			String end_address, String driver_name, String receipt_name,
			String id, User user);

	// 删除评论
	int deleteComment(String[] comment_id);

	// 根据id获取评价信息
	List<Comment> getCommentListByIds(@Param("array") String[] dataIds);
}
