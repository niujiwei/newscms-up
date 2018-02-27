package com.jy.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jy.model.Comment;
import com.jy.model.OrderHistory;
import com.jy.model.ShippingOrder;

public interface SearchWaybillDao {
	/**
	 * 新增app端查看运单历史记录，展示运单信息节点
	 * 
	 * @param shiping_order_num
	 * @return
	 */
	List<OrderHistory> getSearchWaybill(String shiping_order_num);

	/**
	 * 查询运单信息
	 * 
	 * @param shiping_order_num
	 * @param shiping_order_goid
	 * @return
	 */
	public List<ShippingOrder> getShippingOrders(
			@Param("shiping_order_num") String shiping_order_num,
			@Param("shiping_order_goid") String shiping_order_goid);

	/**
	 * 获取要评价的信息
	 * 
	 * @param id
	 * @return
	 */
	public List<ShippingOrder> getOrderMessage(@Param("id") String id);

	/**
	 * 看是否已经评价
	 * @param id
	 * @return
	 */
	public int getCommentCount(@Param("id") String id);

	/**
	 * 保存评价信息
	 * 
	 * @param comment
	 * @return
	 */
	int saveComment(Comment comment);

}
