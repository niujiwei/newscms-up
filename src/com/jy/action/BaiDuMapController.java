package com.jy.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jy.common.BaiduWeather;
import com.jy.model.DeliveryCustomer;
import com.jy.model.Driver;
import com.jy.model.Maps;
import com.jy.model.OrderHistory;
import com.jy.model.ShippingOrder;
import com.jy.model.User;
import com.jy.service.BaiDuMapService;

@Controller
@RequestMapping(value = "/baiduMap.do")
public class BaiDuMapController {

	@Resource
	private BaiDuMapService baiDuMapService;

	/**
	 * 跳转到地图展示页面
	 * 
	 * @return
	 */
	@RequestMapping(params = "method=getMap")
	public String getMap() {
		return "baiduMap/baiduMap";
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
			DeliveryCustomer customer = baiDuMapService.getDeliveryCustomerInfo(
					delivery_cus_name, delivery_people);
			json.put("customer", customer);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
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
	JSONObject loadMapDriverInfo(String userName,String equipmentNum) {
		JSONObject json = new JSONObject();
		List<Maps> map = baiDuMapService.loadMapDriverInfo(userName,equipmentNum);
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
			String endTime,String equipmentNum) {
		Map<String, String> dbMap = BaiduWeather.dateFen(startTime, endTime);
		int count = 0;
		JSONObject json = new JSONObject();
		for (String key : dbMap.keySet()) {
			count += baiDuMapService.loadMapPointCount(key, userName,
					startTime, endTime,equipmentNum);
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
			String startTime, String endTime,String equipmentNum) {
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
				startTime, endTime, db[1], page * size, size,equipmentNum);
		json.put("list", list);
		return json;
	}

	// 地图展示页面
	@RequestMapping(params = "method=getDriverMessage")
	public String getDriverMessage(String shiping_order_num) {

		return "baiduMap/baiduMap";
	}

	/**
	 * 获取一些时间坐标点
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
			String sign_time, String auto_sign_time,String userName, String equipmentNum) {
		JSONObject json = new JSONObject();
		
		/*if (updatetime != null && !updatetime.equals(""))
			putSometimePoits(json,"updatetime", updatetime, userName, equipmentNum);*/
		if (receivetime != null && !receivetime.equals(""))
			putSometimePoits(json,"receivetime", receivetime, userName, equipmentNum);
		if (sign_time != null && !sign_time.equals(""))
			putSometimePoits(json, "sign_time",sign_time, userName, equipmentNum);
		if(auto_sign_time!=null && !auto_sign_time.equals(""))
			putSometimePoits(json, "auto_sign_time",auto_sign_time, userName, equipmentNum);
		return json;

	}
	
	/**
	 * 共同的坐标点私有方法
	 * @param maps
	 * @param mapKey
	 * @param time
	 * @param userName
	 * @param equipmentNum
	 * @return
	 */
	private JSONObject  putSometimePoits(
			JSONObject json, String mapKey, String time,
			String userName, String equipmentNum) {
		int mintime=0;
		String tableName = BaiduWeather.getTableName(time);
		Maps updMap = baiDuMapService.getSomeTimePoint(tableName, time,
				userName, equipmentNum,mintime);
		if (updMap == null)
			 updMap = baiDuMapService.getSomeTimePoint(tableName, time,
					userName, equipmentNum,mintime++);
		json.put(mapKey, updMap);
		return json;
	}
}
