package com.jy.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.jy.model.ShippingManual;
import com.jy.model.Sign_order;

public  interface ShippingManualDao {
	/**
	 * 制作协议分页
	 * @param del
	 * @return
	 */
	 List<ShippingManual> getShipManualCAM(int rows, int page,@Param("array") String[] pid);
	 
	int getShipManual(String start_time,String end_time,String send_station,String end_address,String num,String receipt,String custom_name,String order_state,String pay_type ,String send_type,String goods_name);//查询总数
	List<ShippingManual> getShipManualInfo(int rows,int page,String start_time,String end_time,String send_station,String end_address,String num,String receipt,String custom_name,String order_state,String pay_type ,String send_type,String goods_name );	//查询司机
	 int deleteShipManual(@Param("array") String[] del);//删除
	 int saveShipManual(ShippingManual d);//保存
	 ShippingManual getUpdateShipManual(String id);//修改查询
	 int updateShipManual(ShippingManual d);//用户修改
	 /**
	  * 历史修改表
	  * @param d
	  * @return
	  */
	 int updateShip(ShippingManual d);
	 int deleteShip(@Param("array")String []del);//删除历史订单表

	 
	/*
	 * 获取数据下拉表的查询
	 * */
	List<ShippingManual> getPlateNumberLength(String number);
	ShippingManual getNumber(String number);//重复订单号查询
	int getAorder(String number);//重复订单号查询
	List<ShippingManual> getShipManualAll(String name,String phone_number, String type,String start_date,String end_date) ;
	//协议制作订单查询
	int getShipManualCA(String id ,String name,String phone_number, String type, String start_date, String end_date,String send_station,String end_address,String car_number);//查询总数
	List<ShippingManual> getShipManualInfoCA(int rows,int page,String id, String name,String phone_number, String type, String start_date, String end_date,String send_station,String end_address,String car_number);	//查询
	/**
	 * 删除有需求关联的订单信息
	 * @param del
	 * @return
	 */
	int deleteShipToDemand(@Param("array")String []del);
	/**
	 * hqhadd
	 */
	//所有人员的查询
	List<ShippingManual> getSignShipManual(int rows,int page,String name ,String phone_number, String type,String start_date,String end_date,String shipping_order_state);
	//public  List<Driver> getDriver(String driver_name);
	int getSignShipManualcount(String name ,String phone_number, String type,String start_date,String end_date,String shipping_order_state);
	ShippingManual getUpdateSignShipManual(String id);//修改查询
	List<ShippingManual> getSignShipManual1(int rows,int page,String name ,String phone_number, String type,String start_date,String end_date,String did,String shipping_order_state);
	int getSignShipManualcount1(String name ,String phone_number, String type,String start_date,String end_date,String did,String shipping_order_state);
	
	int saveSignShipManual(Sign_order sign_order);
	int updatestate(String shiping_order_id);
	/**
	 * 批量导入
	 * @param tlist
	 * @return
	 */
	int saveShipManuals(List<ShippingManual> tlist);
	/**
	 * 图片路径
	 * @param sign_id
	 * @param paths
	 * @param paths2 
	 * @return
	 */
	int saveSignImages(String uuid, String order_id, String paths);
	
	/**
	 * 获取司机的运单
	 * @param car_id
	 * @return
	 */
	List<ShippingManual> getCarShipManual(String car_id);

	List<ShippingManual> getUpdateShipManualArray(String[] checkarray);
	/**
	 * 获取扫描信息
	 * @param string
	 * @return
	 */
	ShippingManual getAppShipManual(String string);
	 int isArrive(@Param("array") String[] del);//到达
	 int isNotArrive(@Param("array") String[] del);//取消到达
}
