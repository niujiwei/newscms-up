package com.jy.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.jy.dao.CollectDAO;
import com.jy.model.Confluence;
import com.jy.model.Mingxi;
import com.jy.model.OrderCustom;
import com.jy.model.Settlement;
import com.jy.model.ShippingOrder;
import com.jy.model.User;
import com.jy.service.CollectService;
@Component
public class CollectServiceImpl implements CollectService {
	@Resource
	private CollectDAO collectDAO;
	
	
	public List<OrderCustom> getCollect(int rows, int page,
			String custom_name, String car_time,String kuhu_date,String aa,String write_off3,String send_time,String bb,String receipt_time,String cc,String car_number,String driver_name) {
	List<OrderCustom> list =collectDAO.getCollect(rows, page, custom_name, car_time, kuhu_date,aa, write_off3, send_time,bb, receipt_time,cc, car_number, driver_name);
		return list;
	}

	public int getCollectInfo(String custom_name, String car_time,String kuhu_date,String aa,String write_off3,String send_time, String bb,String receipt_time,String cc,String car_number,String driver_name) {
		int i = collectDAO.getCollectInfo(custom_name, car_time, kuhu_date,aa, write_off3, send_time,bb, receipt_time,cc, car_number, driver_name);
		return i;
	}

	public int updateShippingOrder(OrderCustom d) {
		int i =collectDAO.updateShippingOrder(d);
		return i;
	}

	public OrderCustom getUpdateShippingOrder(String id) {
		OrderCustom os = collectDAO.getUpdateShippingOrder(id);
		return os;
	}



	public OrderCustom getupdate3(String id) {
		OrderCustom oc = collectDAO.getupdate3(id);
		return oc;
	}

	public List<ShippingOrder> getAdorn_fee(int rows, int page,
			String shiping_order_num, String send_station,String others_date,String aa,String write_off5,String custom_name,String receipt,String goods_name,String goods_num,String pay_type,String paid) {
		List<ShippingOrder> list =collectDAO.getAdorn_fee(rows, page, shiping_order_num, send_station,others_date, aa, write_off5, custom_name, receipt, goods_name, goods_num, pay_type, paid);
		return list;
	}

	public int getAdorn_feeInfo(String shiping_order_num, String send_station,String others_date,String aa,String write_off5,String custom_name,String receipt,String goods_name,String goods_num,String pay_type,String paid) {
		 int i = collectDAO.getAdorn_feeInfo(shiping_order_num, send_station,others_date, aa, write_off5, custom_name, receipt, goods_name, goods_num, pay_type, paid);
		return i;
	}

	public int update2(@Param("array") String   d ) {
		 int i = collectDAO.update2(d);
		return i;
	}

	public ShippingOrder getUpdate2(String id) {
		ShippingOrder so = collectDAO.getUpdate2(id);
		return so;
	}


	

	public int update3(String custom_id, String write_off3, String remarks_fee1,String kuhu_date,String People_kehu) {
		int i = collectDAO.update3(custom_id, write_off3, remarks_fee1,kuhu_date, People_kehu);
		return i;
	}

	public int update9(String shiping_order_id, String write_off5
			,String others_date,
			String people_others,String Write_remarks) {
		int i = collectDAO.update9(shiping_order_id, write_off5,others_date,people_others,Write_remarks);
		return i;
	}

	public List<ShippingOrder> selectinfo(String[] s) {
	    List<ShippingOrder>  list = collectDAO.selectinfo(s);
		return list;
	}

	public int insertinfo(List<Confluence> list) {
		int i = collectDAO.insertinfo(list);
		return i;
	}

	public List<OrderCustom> select(String[] s) {
		List<OrderCustom> list = collectDAO.select(s);
		return list;
	}

	public int add(List<Mingxi> list) {
		int i = collectDAO.add(list);
		return i;
	}

	public int otherUpdate(ShippingOrder d) {
		int i = collectDAO.otherUpdate(d);
		return i;
	}

	
	

	



	public List<ShippingOrder> selectIn(String id) {
		
		return null;
	}

	public int editOther(String shiping_order_id, String write_off5,String others_date,String people_others) {
		int i = collectDAO.editOther(shiping_order_id, write_off5, others_date, people_others);
		return i;
	}

	public int delMingxi(String[] del) {
		int i = collectDAO.delMingxi(del);
		return i;
	}

	public int delConf(String[] del) {
		int i = collectDAO.delConf(del);
		return i;
	}


	public int editKuhu(String custom_id, String write_off3,String kuhu_date,String people_kehu) {
		int i = collectDAO.editKuhu(custom_id, write_off3, kuhu_date, people_kehu);
		return i;
	}

	public List<User> selectfo(User u) {
		List<User> list = collectDAO.selectfo(u);
		return list;
	}

	public int addSettlement(List<Settlement> list) {
		int i = collectDAO.addSettlement(list);
		return i;
	}

	public List<Settlement> selectSettement(
			String shiping_order_num) {
		List<Settlement> list = collectDAO.selectSettement( shiping_order_num);
		return list;
	}

	public int selectSettementInfo(String shiping_order_num) {
		int i = collectDAO.selectSettementInfo(shiping_order_num);
		return i;
	}

	public List<ShippingOrder> selectIn(String[] c) {
		// TODO Auto-generated method stub
		return null;
	}

	public int sss(String[] del) {
		// TODO Auto-generated method stub
		return 0;
	}

	

	public int editWrite(String shiping_order_id,String write_id) {
		int i = collectDAO.editWrite(shiping_order_id,write_id);
		return i;
	}

	public int Del(String[] del) {
		int i = collectDAO.Del(del);
		return i;
	}

	public int sp(String[] del) {
		int i = collectDAO.sp(del);
		return i;
	}



	
	
	

	

	

}
