package com.jy.weChart.service.imp;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.jy.dao.WeChartDao;
import com.jy.model.Comment;
import com.jy.model.OrderHistory;
import com.jy.model.ShipperOrderImg;
import com.jy.model.ShippingOrder;
import com.jy.model.UserInfo;
import com.jy.weChart.service.WeChartService;

@Component
public class WeChartServiceImp implements WeChartService {
	@Resource
	private WeChartDao chartDao;

	// 微信检测用户
	public int weChartGetUserCount(UserInfo user) {
		return chartDao.weChartGetUserCount(user);
	}

	// 微信保存用户
	public int weChartSaveUserInfo(UserInfo user) {

		return chartDao.weChartSaveUserInfo(user);
	}

	// 微信更新用户
	public int weChartUpdateUserInfo(String openid, String username,
			String phone) {
		return chartDao.weChartUpdateUserInfo(openid, username, phone);
	}

	// 微信--获取用户信息
	public UserInfo weChartGetUserInfo(String openid) {

		return chartDao.weChartGetUserInfo(openid);
	}

	// 微信--获取运单列表
	public List<ShippingOrder> weChartOrderInfo(String openid, int page,
			int size) {
		return chartDao.weChartOrderInfo(openid, page, size);
	}

	// 微信--获取运单数量
	public int weChartOrderCount(String openid) {

		return chartDao.weChartOrderCount(openid);
	}

	// 微信--获取运单信息
	public ShippingOrder weChartGetOrderMessage(String orderId) {

		return chartDao.weChartGetOrderMessage(orderId);
	}

	// 微信--获取运单历史记录
	public List<OrderHistory> weChartGetOrderHistoryList(String orderId) {

		return chartDao.weChartGetOrderHistoryList(orderId);
	}

	// 微信--获取是否评价
	public int weChartGetCommentCount(String id) {

		return chartDao.weChartGetCommentCount(id);
	}

	// 微信--保存评价
	public int weChartSaveComment(Comment comment) {

		return chartDao.weChartSaveComment(comment);
	}

	// 微信--查看签收图片信息
	public List<ShipperOrderImg> weChartGetSignOrderImages(String orderId) {

		return chartDao.weChartGetSignOrderImages(orderId);
	}

	// 微信--查询运单信息
	public List<ShippingOrder> weChartSearchOrderInfo(String orderNum,
			String orderGoid) {
		
		return chartDao.weChartSearchOrderInfo(orderNum, orderGoid);
	}

	//微信--获取用户订单通过用户名和手机号
	public List<ShippingOrder> weChartOrderInfoByPhoneAndName(String username,
			String phone, int page, int size) {
		
		return chartDao.weChartOrderInfoByPhoneAndName(username, phone, page, size);
	}

	//微信--获取用户订单数量通过用户名和手机号
	public int weChartOrderCountByPhoneAndName(String username,
			String phone) {
		
		return chartDao.weChartOrderCountByPhoneAndName(username, phone);
	}

}
