package com.jy.weChart.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jy.model.Comment;
import com.jy.model.OrderHistory;
import com.jy.model.ShipperOrderImg;
import com.jy.model.ShippingOrder;
import com.jy.model.UserInfo;

public interface WeChartService {
	/**
	 * 微信--检测用户信息
	 * 
	 * @param user
	 * @return
	 */
	public int weChartGetUserCount(UserInfo user);

	/**
	 * 微信--更新用户信息
	 * 
	 * @param user
	 * @return
	 */
	int weChartSaveUserInfo(UserInfo user);

	/**
	 * 微信--保存用户信息
	 * 
	 * @param openid
	 * @param username
	 * @param phone
	 * @return
	 */
	int weChartUpdateUserInfo(String openid, String username, String phone);

	/**
	 * 微信--获取用户信息
	 * 
	 * @param openid
	 * @return
	 */
	UserInfo weChartGetUserInfo(String openid);

	/**
	 * 微信--获取用户订单
	 * 
	 * @param openid
	 * @return
	 */
	List<ShippingOrder> weChartOrderInfo(String openid, int page, int size);

	/**
	 * 微信--获取运单数量
	 * 
	 * @param openid
	 * @return
	 */
	int weChartOrderCount(String openid);

	/**
	 * 获取运单信息
	 * 
	 * @param orderId
	 * @return
	 */
	ShippingOrder weChartGetOrderMessage(String orderId);

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
	int weChartGetCommentCount(String id);

	/**
	 * 保存评价信息
	 * 
	 * @param comment
	 * @return
	 */
	int weChartSaveComment(Comment comment);
	
	/**
	 * 微信 正向物流 订单签收图片信息
	 * 
	 * @param orderId
	 * @return
	 */
	List<ShipperOrderImg> weChartGetSignOrderImages(
			String orderId);
	
	/**
	 * 微信--通过运单号和出货订单号获取用户订单
	 * @param openid
	 * @return
	 */
	List<ShippingOrder> weChartSearchOrderInfo(String orderNum ,
			String orderGoid);
	
	/**
	 * 微信--获取用户订单通过用户名和手机号
	 * 
	 * @param openid
	 * @return
	 */
	List<ShippingOrder> weChartOrderInfoByPhoneAndName(
			 String username, String phone,
			 int page, int size);

	/**
	 * 微信--获取用户订单数量通过用户名和手机号
	 * 
	 * @param openid
	 * @return
	 */
	int weChartOrderCountByPhoneAndName(
			 String username, String phone);
}
