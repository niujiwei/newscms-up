package com.jy.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jy.model.Confluence;
import com.jy.model.Mingxi;
import com.jy.model.OrderCustom;
import com.jy.model.Settlement;
import com.jy.model.ShippingOrder;
import com.jy.model.User;

public interface CollectDAO {
	
	/**
	 *  搴旀敹瀹㈡埛璐︽鏍搁攢锛堟寜杞︽牳閿�� 鐜伴噾 鎵撳崱锛�
	 *  
	 */
	// 鍏ㄩ儴鏌ヨ
	List<Settlement> selectSettement( String shiping_order_num );
	public int selectSettementInfo( String shiping_order_num  );
	 List<User>  selectfo( User u);
	int editOther(String shiping_order_id, String write_off5,String others_date,String people_others);
	 int delMingxi(@Param("array") String[] del);
	 int Del(@Param("array") String[] del);
	 int delConf(@Param("array") String[] del);
	int sp(@Param("array") String[] del);
	 int editKuhu(String custom_id, String write_off3,String kuhu_date,String people_kehu);
	int otherUpdate(ShippingOrder d);//鐢ㄦ埛淇敼
	List<OrderCustom> getCollect(int rows,int page,String custom_name,String car_time,String kuhu_date,String aa,String write_off3,String send_time,String bb,String receipt_time,String cc,String car_number,String driver_name);
	public int getCollectInfo( String custom_name,String car_time,String kuhu_date,String aa,String write_off3,String send_time,String bb,String receipt_time,String cc,String car_number,String driver_name);//鏌ヨ鎬绘暟
	int updateShippingOrder(OrderCustom d);//鐢ㄦ埛淇敼
	OrderCustom getUpdateShippingOrder(String id);//淇敼鏌ヨ
	
	 int update3(String custom_id,String write_off3,String remarks_fee1,String kuhu_date,String People_kehu );//鐢ㄦ埛淇敼
	 OrderCustom getupdate3(String id);//淇敼鏌ヨ
	 List<OrderCustom>  select( String[] s);
	 /**
	  * 
	  *   搴旀敹鍏朵粬璐瑰娉ㄦ牳閿�
	  */
	 int editWrite(String shiping_order_id, String write_id);
		List<ShippingOrder> getAdorn_fee(int rows,int page,String shiping_order_num,String send_station,String others_date,String aa,String write_off5,String custom_name,String receipt,String goods_name,String goods_num,String pay_type,String paid);
		public int getAdorn_feeInfo( String shiping_order_num,String send_station,String others_date,String aa,String write_off5,String custom_name,String receipt,String goods_name,String goods_num,String pay_type,String paid);//鏌ヨ鎬绘暟
		 int update2(@Param("array") String  d );//鐢ㄦ埛淇敼
		 ShippingOrder getUpdate2(String id);//淇敼鏌ヨ
		 List<ShippingOrder>  selectinfo( String[] s);
		 int insertinfo(List<Confluence> list);
		 int add(List<Mingxi> list);
		 int addSettlement(List<Settlement> list);
		 int update9(String shiping_order_id,String write_off5,String others_date ,String people_others,String Write_remarks );//用户修改
}
