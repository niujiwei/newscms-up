package com.jy.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jy.model.Confluence;
import com.jy.model.Mingxi;
import com.jy.model.OrderCustom;
import com.jy.model.Settlement;
import com.jy.model.ShippingOrder;
import com.jy.model.User;

public interface CollectService {
	
	/**
	 *  应收客户账款核销 （按车核销）
	 */
	
	// 全部查询
	int sp(@Param("array") String[] del);
	List<Settlement> selectSettement(String shiping_order_num );
	public int selectSettementInfo( String shiping_order_num  );
	int addSettlement(List<Settlement> list);
	 List<User>  selectfo( User u);
	List<ShippingOrder> selectIn(String[] c);
	int editOther(String shiping_order_id, String write_off5,String others_date,String people_others);
	
	int editKuhu(String custom_id, String write_off3,String kuhu_date,String people_kehu);
	 int delMingxi(@Param("array") String[] del);
	 int delConf(@Param("array") String[] del);
		List<OrderCustom> getCollect(int rows,int page,String custom_name,String car_time,String kuhu_date,String aa,String write_off3,String send_time,String bb,String receipt_time,String cc,String car_number,String driver_name);
		public int getCollectInfo( String custom_name,String car_time,String kuhu_date,String aa,String write_off3,String send_time,String bb,String receipt_time,String cc,String car_number,String driver_name);//鏌ヨ鎬绘暟
	int updateShippingOrder(OrderCustom d);//用户修改
	OrderCustom getUpdateShippingOrder(String id);//修改查询
	 int update3(String custom_id,String write_off3,String remarks_fee1,String kuhu_date,String People_kehu );//用户修改
	 OrderCustom getupdate3(String id);//修改查询
	 int otherUpdate(ShippingOrder d);//用户修改
	 int Del(@Param("array") String[] del);
	 /**
	  * 
	  *   应收其他费备注核销
	  */
		List<ShippingOrder> getAdorn_fee(int rows,int page,String shiping_order_num,String send_station,String others_date,String aa,String write_off5,String custom_name,String receipt,String goods_name,String goods_num,String pay_type,String paid);
		public int getAdorn_feeInfo( String shiping_order_num,String send_station,String others_date,String aa,String write_off5,String custom_name,String receipt,String goods_name,String goods_num,String pay_type,String paid);//查询总数
		 int update2(@Param("array") String  pid );//用户修改
		 ShippingOrder getUpdate2(String  id);//修改查询
		 int sss(String [] del);
		 List<ShippingOrder>  selectinfo( String[] s);
		 List<OrderCustom>  select( String[] s);
		 int insertinfo(List<Confluence> list);
		 int add(List<Mingxi> list);
		 int update9(String shiping_order_id,String write_off5,String others_date ,String people_others,String Write_remarks );//用户修改
		 int editWrite(String shiping_order_id, String write_id);
}
