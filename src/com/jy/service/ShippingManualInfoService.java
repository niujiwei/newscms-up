package com.jy.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jy.model.ShippingManual;
import com.jy.model.Sign_order;

public interface ShippingManualInfoService {
			//所有人员的查询
			public List<ShippingManual> getShipManualInfo(int rows,int page,String start_time,String end_time,String send_station,String end_address,String num,String receipt,String custom_name,String order_state,String pay_type ,String send_type,String goods_name);
			//public  List<Driver> getDriver(String driver_name);
			public int getShipManual(String start_time,String end_time,String send_station,String end_address,String num,String receipt,String custom_name,String order_state,String pay_type ,String send_type,String goods_name);
			//删除
			public int deleteShipManual(String[] del);
			//保存
			public int saveShipManual(ShippingManual d);
			//修改查询
			public ShippingManual getUpdateShipManual(String id);
			//用户修改
			public int updateShipManual(ShippingManual d);
			
			public  int isArrive(@Param("array") String[] del);//到达
			public  int isNotArrive(@Param("array") String[] del);//取消到达			 
			/*
			 * select 查询
			 * */
			List<ShippingManual> getPlateNumberLength(String number);
			ShippingManual getNumber(String number);//重复订单号查询
			int getAorder(String number);//重复订单号查询
			List<ShippingManual> getShipManualAll(String name,String phone_number, String type,String start_date,String end_date) ;//导出查询
			/**
			 * 删除与需求相关的当单信息
			 * @param del
			 * @return
			 */
			int deleteShipToDemand(String []del);
		
			/**
			 * hqhadd
			 */
			//所有人员的查询
			public List<ShippingManual> getSignShipManual(int rows,int page,String name ,String phone_number, String type,String start_date,String end_date,String shipping_order_state);
			//public  List<Driver> getDriver(String driver_name);
			public int getSignShipManualcount(String name ,String phone_number, String type,String start_date,String end_date,String shipping_order_state);
			
			public List<ShippingManual> getSignShipManual1(int rows,int page,String name ,String phone_number, String type,String start_date,String end_date,String did,String shipping_order_state);
			//public  List<Driver> getDriver(String driver_name);
			public int getSignShipManualcount1(String name ,String phone_number, String type,String start_date,String end_date,String did,String shipping_order_state);
			
			public int saveSignShipManual(Sign_order sign_order);
			public int updatestate(String shiping_order_id);
			//修改查询
			public ShippingManual getUpdateSignShipManual(String id);
			//协议制作
			int getShipManualCA(String id ,String name,String phone_number, String type, String start_date, String end_date,String send_station,String end_address,String car_number);//查询总数
			List<ShippingManual> getShipManualInfoCA(int rows,int page,String id ,String name,String phone_number, String type, String start_date, String end_date,String send_station,String end_address,String car_number);	//查询
			List<ShippingManual> getShipManualCAM(int rows, int page, String[] pid);//查询确定订单信息
			/**
			 * 订单图片路径
			 * @param sign_id
			 * @param paths
			 * @param paths2 
			 * @return
			 */
			public int saveSignImages(String uuid, String order_id, String paths);
			/**
			 * 获取司机的订单
			 * @param string
			 * @return
			 */
			public List<ShippingManual> getCarShipManual(String car_id);
			public List<ShippingManual> getUpdateShipManualArray(
					String[] checkarray);
			/**
			 * 查询扫描信息
			 * @param string
			 * @return
			 */
			public ShippingManual getAppShipManual(String string);
			/**
			 * 修改历史订单表
			 * @param d
			 * @return
			 */
			public int updateShip(ShippingManual d);
			/**
			 * 删除订单及相关历史订单表
			 * @param del
			 * @return
			 */
			 public int deleteShip(@Param("array")String []del);

}

