package com.jy.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jy.model.Comment;
import com.jy.model.OrderHistory;
import com.jy.model.ShipperOrderImg;
import com.jy.model.ShippingOrder;
import com.jy.model.UserInfo;

public interface WeChartDao {
	/**
	 * 微信--检测用户信息
	 * 
	 * @param user
	 * @return
	 */
	int weChartGetUserCount(@Param("user") UserInfo user);

	/**
	 * 微信--更新用户信息
	 * 
	 * @param user
	 * @return
	 */
	int weChartSaveUserInfo(@Param("user") UserInfo user);

	/**
	 * 微信--保存用户信息
	 * 
	 * @param openid
	 * @param username
	 * @param phone
	 * @return
	 */
	int weChartUpdateUserInfo(@Param("openid") String openid,
			@Param("username") String username, @Param("phone") String phone);

	/**
	 * 微信--获取用户信息
	 * 
	 * @param openid
	 * @return
	 */
	UserInfo weChartGetUserInfo(@Param("openid") String openid);

	/**
	 * 微信--获取用户订单
	 * 
	 * @param openid
	 * @return
	 */
	List<ShippingOrder> weChartOrderInfo(@Param("openid") String openid,
			@Param("page") int page, @Param("size") int size);

	/**
	 * 微信--通过运单号和出货订单号获取用户订单
	 * 
	 * @param openid
	 * @return
	 */
	List<ShippingOrder> weChartSearchOrderInfo(
			@Param("orderNum") String orderNum,
			@Param("orderGoid") String orderGoid);

	/**
	 * 微信--获取运单数量
	 * 
	 * @param openid
	 * @return
	 */
	int weChartOrderCount(@Param("openid") String openid);

	/**
	 * 微信--获取运单信息
	 * 
	 * @param orderId
	 * @return
	 */
	ShippingOrder weChartGetOrderMessage(@Param("orderId") String orderId);

	/**
	 * 微信--获取运单历史记录
	 * 
	 * @param orderId
	 * @return
	 */
	List<OrderHistory> weChartGetOrderHistoryList(
			@Param("orderId") String orderId);

	/**
	 * 微信 --看是否已经评价
	 * 
	 * @param id
	 * @return
	 */
	int weChartGetCommentCount(@Param("id") String id);

	/**
	 * 保存评价信息
	 * 
	 * @param comment
	 * @return
	 */
	int weChartSaveComment(Comment comment);

	/**
	 * 微信 订单签收图片信息
	 * 
	 * @param orderId
	 * @return
	 */
	List<ShipperOrderImg> weChartGetSignOrderImages(
			@Param("orderId") String orderId);

	/**
	 * 微信--获取用户订单通过用户名和手机号
	 * 
	 * @param openid
	 * @return
	 */
	List<ShippingOrder> weChartOrderInfoByPhoneAndName(
			@Param("username") String username, @Param("phone") String phone,
			@Param("page") int page, @Param("size") int size);

	/**
	 * 微信--获取用户订单数量通过用户名和手机号
	 * 
	 * @param openid
	 * @return
	 */
	int weChartOrderCountByPhoneAndName(
			@Param("username") String username, @Param("phone") String phone);

}
