package com.jy.weChart.action;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jy.common.BaiduWeather;
import com.jy.common.SessionInfo;
import com.jy.common.UUIDUtils;
import com.jy.model.Comment;
import com.jy.model.DeliveryCustomer;
import com.jy.model.Driver;
import com.jy.model.Maps;
import com.jy.model.OrderHistory;
import com.jy.model.ShipperOrderImg;
import com.jy.model.ShippingOrder;
import com.jy.model.User;
import com.jy.model.UserInfo;
import com.jy.service.BaiDuMapService;
import com.jy.service.ShippingOrderInfoService;
import com.jy.weChart.service.WeChartService;

/**
 * 微信公众号
 * 
 * @author 90
 * 
 */
@Controller
@RequestMapping(value = "/weChart.do")
public class WeChartAction {
	private static Logger log = Logger.getLogger(WeChartAction.class);
	@Resource
	private ShippingOrderInfoService shiService;
	private final String appID = "wx9834a157619c2111";
	private final String secret = "f2bc98370a749628f05fd2ef12ce9101";
	@Resource
	private WeChartService chartService;

	@Resource
	private BaiDuMapService baiDuMapService;

	private static final int cookieTime = 60 * 60 * 24 * 7;

	@RequestMapping(params = "method=getCode")
	public String getCode(String code, String state, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {
		Gson gson = new Gson();
		Cookie[] cookie2 = request.getCookies();
		String path = "weChart/weChart_LoginInfo";
		if (cookie2 == null) {
			return path;
		}
		try {
			for (Cookie cookie3 : cookie2) {
				if (cookie3.getName().equals("userInfo")) {
					String userInfo = URLDecoder.decode(cookie3.getValue(),
							"UTF-8");
					UserInfo user = gson.fromJson(userInfo, UserInfo.class);
					session.setAttribute(SessionInfo.weChartUserInfo, user);
					path = "weChart/weChart_MenuInfo";
				}
			}
		} catch (Exception e) {
			log.error("WeChartAction.class:" + e.getMessage());
			request.setAttribute("type", "error");
			request.setAttribute("message", e.getMessage());
			path = "weChart/weChart_Message";

		}

		return path;
	}

	// 微信公众号获取用户信息

	/*
	 * @RequestMapping(params = "method=getCode") public String getCode(String
	 * code, String state, HttpSession session, HttpServletRequest request) {
	 * 
	 * String path = ""; String urlCode =
	 * "https://api.weixin.qq.com/sns/oauth2/access_token"; String parpamCode =
	 * "appid=" + appID + "&secret=" + secret + "&code=" + code +
	 * "&grant_type=authorization_code"; try { String str =
	 * HttpRequesterUtils.doGet(urlCode, parpamCode, "utf-8"); JSONObject json =
	 * JSONObject.fromObject(str); String refresh_token =
	 * json.getString("refresh_token"); String urlRefreshToken =
	 * "https://api.weixin.qq.com/sns/oauth2/refresh_token"; String
	 * parpamRefreshToken = "appid=" + appID +
	 * "&grant_type=refresh_token&refresh_token=" + refresh_token; String str2 =
	 * HttpRequesterUtils.doGet(urlRefreshToken, parpamRefreshToken, "utf-8");
	 * JSONObject json2 = JSONObject.fromObject(str2); String access_token2 =
	 * json2.getString("access_token"); String openid =
	 * json2.getString("openid");
	 * 
	 * String url2 = " https://api.weixin.qq.com/sns/userinfo"; String parpam3 =
	 * "access_token=" + access_token2 + "&openid=" + openid + "&lang=zh_CN";
	 * String userInfo = HttpRequesterUtils.doGet(url2, parpam3, "utf-8");
	 * JSONObject userInfojsonJsonObject = JSONObject.fromObject(userInfo);
	 * UserInfo user = (UserInfo) JSONObject.toBean( userInfojsonJsonObject,
	 * UserInfo.class); int i = chartService.weChartGetUserCount(user);
	 * 
	 * if (i > 0) { UserInfo info = chartService.weChartGetUserInfo(user
	 * .getOpenid()); session.setAttribute(SessionInfo.weChartUserInfo, info);
	 * path = "weChart/weChart_Menu"; // 已注册 } else {// 未注册
	 * session.setAttribute(SessionInfo.weChartUserInfo, user);
	 * chartService.weChartSaveUserInfo(user); path = "weChart/weChart_Login"; }
	 * 
	 * } catch (Exception e) { log.error("WeChartAction.class:" +
	 * e.getMessage()); request.setAttribute("type", "error");
	 * request.setAttribute("message", e.getMessage()); path =
	 * "weChart/weChart_Message";
	 * 
	 * }
	 * 
	 * return path; }
	 */

	// 微信公众号更新用户信息
	@RequestMapping(params = "method=weChartUpdateUserInfo")
	public String weChartUpdateUserInfo(String openid, String username,
			String phone, HttpSession session) {
		int i = chartService.weChartUpdateUserInfo(openid, username, phone);
		if (i > 0) {
			UserInfo user = chartService.weChartGetUserInfo(openid);
			session.setAttribute(SessionInfo.weChartUserInfo, user);
		}
		return "weChart/weChart_Menu";
	}

	// 微信公众号查询传递用户id
	@RequestMapping(params = "method=weChartToOrder")
	public String weChartToOrder(String openid, String type, String textOrderNum,String textOrderGoid,HttpServletRequest request) {
		request.setAttribute("openid", openid);
		request.setAttribute("type", type);
		request.setAttribute("textOrderNum", textOrderNum);
		request.setAttribute("textOrderGoid", textOrderGoid);
		return "weChart/weChart_Order";
	}

	// 微信公众号查询对应的运单
	@RequestMapping(params = "method=weChartOrderInfo")
	public @ResponseBody
	JSONObject weChartOrderInfo(String openid, String page, String size,
			HttpServletRequest request, HttpSession session) {
		int pageInt = 0;

		if (page != null && !page.equals("")) {
			pageInt = Integer.parseInt(page);

		}
		int sizeInt = 0;
		if (size != null && !size.equals("")) {
			sizeInt = Integer.parseInt(size);

		}
		JSONObject json = new JSONObject();
		UserInfo userinfo = (UserInfo) session
				.getAttribute(SessionInfo.weChartUserInfo);
		List<ShippingOrder> list = new ArrayList<ShippingOrder>();
		int count = 0;
		if (userinfo == null) {
			Gson gson = new Gson();
			Cookie[] cookie2 = request.getCookies();

			if (cookie2 == null)
				return json;
			try {
				for (Cookie cookie3 : cookie2) {
					if (cookie3.getName().equals("userInfo")) {
						String userInfo = URLDecoder.decode(cookie3.getValue(),
								"UTF-8");
						userinfo = gson.fromJson(userInfo, UserInfo.class);
						session.setAttribute(SessionInfo.weChartUserInfo,
								userinfo);

					}
				}
			} catch (Exception e) {
				log.error("WeChartAction.class:" + e.getMessage());

			}

		}

		list = chartService.weChartOrderInfoByPhoneAndName(
				userinfo.getUsername(), userinfo.getPhone(), (pageInt - 1)
						* sizeInt, sizeInt);
		count = chartService.weChartOrderCountByPhoneAndName(
				userinfo.getUsername(), userinfo.getPhone());
		/*
		 * List<ShippingOrder> list = chartService.weChartOrderInfo(openid,
		 * (pageInt - 1) * sizeInt, sizeInt);
		 */
		// count = chartService.weChartOrderCount(openid);

		json.put("list", list);
		json.put("count", count);

		return json;
	}

	// 跳转详细信息列表
	@RequestMapping(params = "method=weChartGetOrderInfo")
	public String weChartGetOrderInfo(String orderid,
			HttpServletRequest request, HttpSession session) {
		request.setAttribute("orderid", orderid);
		return "weChart/weChart_OrderInfo";
	}

	// 跳转详细信息列表
	@RequestMapping(params = "method=weChartGetOrderMessage")
	public @ResponseBody
	JSONObject weChartGetOrderMessage(String orderid,
			HttpServletRequest request, HttpSession session) {
		JSONObject json = new JSONObject();
		ShippingOrder order = chartService.weChartGetOrderMessage(orderid);
		json.put("order", order);
		return json;
	}

	// 跳转详细信息列表
	@RequestMapping(params = "method=weChartGetOrderHistoryList")
	public @ResponseBody
	JSONObject weChartGetOrderHistoryList(String orderid,
			HttpServletRequest request, HttpSession session) {
		JSONObject json = new JSONObject();
		List<OrderHistory> historyList = chartService
				.weChartGetOrderHistoryList(orderid);
		json.put("historyList", historyList);
		return json;
	}

	// 跳转详细信息列表
	@RequestMapping(params = "method=weChartDriverEvaluation")
	public String weChartDriverEvaluation(String orderid,
			HttpServletRequest request, HttpSession session) {
		request.setAttribute("orderid", orderid);
		return "weChart/weChart_driverEvaluation";
	}

	// 保存评价信息
	@RequestMapping(params = "method=weChartsSaveEvaluationMssage")
	public @ResponseBody
	JSONObject getOrderMessage(String message, String num, String datas,
			HttpSession session) {
		JSONObject jsonArray = new JSONObject();
		Gson gson = new Gson();
		UserInfo user = (UserInfo) session
				.getAttribute(SessionInfo.weChartUserInfo);
		if (datas == null || datas.equals("")) {
			jsonArray.put("state", false);
			jsonArray.put("message", "没有运单信息");
			return jsonArray;
		}
		if (user == null) {
			jsonArray.put("state", false);
			jsonArray.put("message", "用户信息没有获取到");
			return jsonArray;
		}
		ShippingOrder shippingOrders = gson.fromJson(datas,
				new TypeToken<ShippingOrder>() {
				}.getType());
		Comment comment = new Comment();
		comment.setDriver_id(shippingOrders.getDriver_id());
		comment.setDriver_name(shippingOrders.getDriver_name());
		comment.setDriver_phone(shippingOrders.getDriver_phone());
		comment.setShiping_order_id(shippingOrders.getShiping_order_id());
		comment.setShiping_order_num(shippingOrders.getShiping_order_num());
		comment.setSend_mechanism(shippingOrders.getSend_mechanism());
		comment.setSuppliers_id(shippingOrders.getDriver_suppliers());
		comment.setComment_id(UUIDUtils.getUUID());
		comment.setXing(num);
		comment.setRemark(message);
		comment.setComment_name(user.getUsername());
		comment.setNickname(user.getNickname());
		comment.setOpenid(user.getOpenid());
		comment.setComment_phone(user.getPhone());
		shiService.updateComment_state(shippingOrders.getShiping_order_id());
		int i = chartService.weChartGetCommentCount(shippingOrders
				.getShiping_order_id());
		if (i == 0) {
			int j = chartService.weChartSaveComment(comment);
			if (j > 0) {
				jsonArray.put("state", true);
				jsonArray.put("message", "感谢您的评价");
			} else {
				jsonArray.put("state", false);
				jsonArray.put("message", "评价信息未保存成功");
			}
		}

		return jsonArray;
	}

	// 跳转详细信息列表
	@RequestMapping(params = "method=weChartMessage")
	public String weChartMessage(String message, String type,
			HttpServletRequest request) {
		request.setAttribute("type", type);
		request.setAttribute("message", message);
		return "weChart/weChart_Message";
	}

	// 跳转地图信息页面
	@RequestMapping(params = "method=weChartOrderInfoMap")
	public String weChartOrderInfoMap(String orderid, HttpServletRequest request) {
		request.setAttribute("orderid", orderid);
		return "weChart/weChart_OrderInfoMap";
	}

	/**
	 * 获取订单节点信息
	 * 
	 * @param shiping_order_id
	 * @return
	 */
	@RequestMapping(params = "method=getShippingOrderHistoryInfo")
	public @ResponseBody
	JSONObject getShippingOrderHistoryInfo(String shiping_order_id) {
		JSONObject json = new JSONObject();
		List<OrderHistory> history = baiDuMapService
				.getShippingOrderHistoyInfo(shiping_order_id);
		Driver driver = baiDuMapService.getDriverInfo(shiping_order_id);
		String driverid = driver == null ? "" : driver.getDriver_id();
		User user = baiDuMapService.getUserInfo(driverid);
		Map<String, String> map = baiDuMapService
				.getRecSinTime(shiping_order_id);
		ShippingOrder order = baiDuMapService
				.getShippingOrderInfo(shiping_order_id);
		json.put("order", order);
		json.put("driver", driver);
		json.put("user", user);
		json.put("history", history);
		json.put("time", map);
		return json;
	}

	/**
	 * 获取收货客户定位信息
	 * 
	 * @param delivery_cus_name
	 * @param delivery_people
	 * @return
	 */
	@RequestMapping(params = "method=getDeliveryCustomerInfo")
	public @ResponseBody
	JSONObject getDeliveryCustomerInfo(String delivery_cus_name,
			String delivery_people) {
		JSONObject json = new JSONObject();
		try {
			DeliveryCustomer customer = baiDuMapService
					.getDeliveryCustomerInfo(delivery_cus_name, delivery_people);
			json.put("customer", customer);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return json;
	}

	/**
	 * 获取一些时间坐标点
	 * 
	 * @param updatetime
	 * @param receivetime
	 * @param sign_time
	 * @param userName
	 * @param equipmentNum
	 * @return
	 */
	@RequestMapping(params = "method=getSomeTimePoint")
	public @ResponseBody
	JSONObject getSomeTimePoint(String updatetime, String receivetime,
			String sign_time, String auto_sign_time, String userName,
			String equipmentNum) {
		JSONObject json = new JSONObject();

		/*
		 * if (updatetime != null && !updatetime.equals(""))
		 * putSometimePoits(json,"updatetime", updatetime, userName,
		 * equipmentNum);
		 */
		if (receivetime != null && !receivetime.equals(""))
			putSometimePoits(json, "receivetime", receivetime, userName,
					equipmentNum);
		if (sign_time != null && !sign_time.equals(""))
			putSometimePoits(json, "sign_time", sign_time, userName,
					equipmentNum);
		if (auto_sign_time != null && !auto_sign_time.equals(""))
			putSometimePoits(json, "auto_sign_time", auto_sign_time, userName,
					equipmentNum);
		return json;

	}

	/**
	 * 共同的坐标点私有方法
	 * 
	 * @param maps
	 * @param mapKey
	 * @param time
	 * @param userName
	 * @param equipmentNum
	 * @return
	 */
	private JSONObject putSometimePoits(JSONObject json, String mapKey,
			String time, String userName, String equipmentNum) {
		String tableName = BaiduWeather.getTableName(time);
		Maps updMap = baiDuMapService.getSomeTimePoint(tableName, time,
				userName, equipmentNum, 0);
		if (updMap == null)
			updMap = baiDuMapService.getSomeTimePoint(tableName, time,
					userName, equipmentNum, 1);

		json.put(mapKey, updMap);
		return json;
	}

	/**
	 * 获取司机信息
	 * 
	 * @param userName
	 * @return
	 */
	@RequestMapping(params = "method=loadMapDriverInfo")
	public @ResponseBody
	JSONObject loadMapDriverInfo(String userName, String equipmentNum) {
		JSONObject json = new JSONObject();
		List<Maps> map = baiDuMapService.loadMapDriverInfo(userName,
				equipmentNum);
		json.put("map", map);
		return json;
	}

	/**
	 * 获取坐标点个数
	 * 
	 * @param userName
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@RequestMapping(params = "method=loadMapPointCount")
	public @ResponseBody
	JSONObject loadMapPointCount(String userName, String startTime,
			String endTime, String equipmentNum) {
		Map<String, String> dbMap = BaiduWeather.dateFen(startTime, endTime);
		int count = 0;
		JSONObject json = new JSONObject();
		for (String key : dbMap.keySet()) {
			count += baiDuMapService.loadMapPointCount(key, userName,
					startTime, endTime, equipmentNum);
		}
		json.put("count", count);
		return json;
	}

	/**
	 * 获取坐标点
	 * 
	 * @param page
	 * @param size
	 * @param userName
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@RequestMapping(params = "method=loadMapPointInfo")
	public @ResponseBody
	JSONObject loadMapPointInfo(int page, int size, String userName,
			String startTime, String endTime, String equipmentNum) {
		Map<String, String> dbMap = BaiduWeather.dateFen(startTime, endTime);
		int n = 0;
		JSONObject json = new JSONObject();
		String[] db = new String[2];
		for (String key : dbMap.keySet()) {
			db[n++] = key;
		}
		if ("".equals(db[1]) || db[1] == null) {
			db[1] = db[0];
		}
		List<Maps> list = baiDuMapService.loadMapPointInfo(db[0], userName,
				startTime, endTime, db[1], page * size, size, equipmentNum);
		json.put("list", list);
		return json;
	}

	// 跳转地图信息页面
	@RequestMapping(params = "method=weChartSignOrderImage")
	public String weChartSignOrderImage(String orderid,
			HttpServletRequest request) {
		request.setAttribute("orderid", orderid);
		return "weChart/weChart_OrderImage";
	}

	// 微信查看图片获取信息
	@RequestMapping(params = "method=weChartGetSignOrderImageInfo")
	public @ResponseBody
	JSONObject weChartGetSignOrderImageInfo(String orderId) {
		JSONObject json = new JSONObject();
		List<ShipperOrderImg> order = chartService
				.weChartGetSignOrderImages(orderId);
		json.put("successMsg", order);
		return json;
	}

	// 微信通过运单号和出货订单号查询运单信息
	@RequestMapping(params = "method=weChartSearchOrderInfo")
	public @ResponseBody
	JSONObject weChartSearchOrderInfo(String orderNum, String orderGoid) {
		JSONObject json = new JSONObject();
		List<ShippingOrder> list = chartService.weChartSearchOrderInfo(
				orderNum, orderGoid);
		json.put("list", list);
		return json;
	}

	// 微信公众号更新用户信息
	@RequestMapping(params = "method=weChartSaveUserInfo")
	public String weChartSaveUserInfo(String openid, String username,
			String phone, HttpSession session, HttpServletResponse response,
			HttpServletRequest request) {

		try {
			UserInfo user = (UserInfo) session
					.getAttribute(SessionInfo.weChartUserInfo);
			Gson gson = new Gson();
			if (user != null)
				return "weChart/weChart_MenuInfo";
			user = new UserInfo();
			Cookie[] cookie2 = request.getCookies();
			if (cookie2 == null)
				return "weChart/weChart_LoginInfo";
			for (Cookie cookie3 : cookie2) {
				if (cookie3.getName().equals("userInfo")) {
					String userInfo = URLDecoder.decode(cookie3.getValue(),
							"UTF-8");
					user = gson.fromJson(userInfo, UserInfo.class);
					if(user==null||"".equals(user.getPhone())||"".equals(user.getUsername())) return "weChart/weChart_MenuInfo";
					session.setAttribute(SessionInfo.weChartUserInfo, user);
					return "weChart/weChart_MenuInfo";
				}
			}
			if(phone.equals("")||username.equals("")) return "weChart/weChart_MenuInfo";
			user.setPhone(phone);
			user.setUsername(username);

			String userInfo = URLEncoder.encode(gson.toJson(user), "utf-8");
			Cookie cookie = new Cookie("userInfo", userInfo);
			cookie.setMaxAge(cookieTime);
			cookie.setPath(request.getContextPath());
			response.addHeader("Set-Cookie", "uid=112; Path=/; HttpOnly");
			response.addCookie(cookie);
			session.setAttribute(SessionInfo.weChartUserInfo, user);
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		}

		return "weChart/weChart_MenuInfo";
	}

	// 微信公众号更新用户信息
	@RequestMapping(params = "method=weChartUpdateUserInfoByCookie")
	public String weChartUpdateUserInfoByCookie(String openid, String username,
			String phone, HttpSession session, HttpServletResponse response,
			HttpServletRequest request) {
		String path = "weChart/weChart_LoginInfo";
		Gson gson = new Gson();
		Cookie[] cookie2 = request.getCookies();
		if (cookie2 == null)
			return path;
		try {
			for (Cookie cookie3 : cookie2) {
				if (cookie3.getName().equals("userInfo")) {
					String userInfo = URLDecoder.decode(cookie3.getValue(),
							"UTF-8");
					UserInfo user = gson.fromJson(userInfo, UserInfo.class);
					user.setPhone(phone);
					user.setUsername(username);
					session.setAttribute(SessionInfo.weChartUserInfo, user);
					cookie3.setValue(URLEncoder.encode(gson.toJson(user), "utf-8"));
					cookie3.setMaxAge(cookieTime);
					cookie3.setPath(request.getContextPath());
					response.addHeader("Set-Cookie", "uid=112; Path=/; HttpOnly");
					response.addCookie(cookie3);
					path = "weChart/weChart_MenuInfo";
				}
			}
		} catch (Exception e) {
			log.error("WeChartAction.class:" + e.getMessage());

		}
		return path;
	}
}
