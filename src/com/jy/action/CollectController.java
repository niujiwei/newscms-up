/*package com.jy.action;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jy.common.SessionInfo;
import com.jy.common.UUIDUtils;
import com.jy.dao.Json;
import com.jy.model.Company;
import com.jy.model.Confluence;
import com.jy.model.Mingxi;
import com.jy.model.OrderCustom;
import com.jy.model.Settlement;
import com.jy.model.ShippingOrder;
import com.jy.model.User;
import com.jy.service.CollectService;

@Controller
@RequestMapping(value = "/collect.do")
public class CollectController {
	private static final Date Date = null;
	*//**
	 * 应付客户款项核销（按车核销）
	 *//*
	
	@Resource
	private CollectService cs;

	@RequestMapping(params = "method=get")
	public String getCompanyQuery() {
		return "collect/collect";
		
	}

	// 全部查询
	@RequestMapping(params = "method=getCollect")
	public @ResponseBody
	Map getCompany(String rows, String page, String custom_name,
			String car_time, String kuhu_date,String aa,String write_off3,String send_time,String bb,String receipt_time,String cc,String car_number,String driver_name) {
		Integer rows1 = 0;// 当前有几行
		Integer page1 = 1;// 当前有几页
		if (rows != null && !"".equals(rows)) {
			rows1 = Integer.parseInt(rows);
		}
		if (page != null && !"".equals(page)) {
			page1 = Integer.parseInt(page);
		}

		List<OrderCustom> list = cs.getCollect((page1 - 1) * rows1, rows1,
				custom_name, car_time, kuhu_date,aa,write_off3, send_time,bb, receipt_time,cc, car_number, driver_name);

		int count = cs.getCollectInfo(custom_name, car_time, kuhu_date,aa,write_off3, send_time,bb, receipt_time, cc,car_number, driver_name);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", count);
		map.put("rows", list);
		return map;
	}

	// 修改
	@RequestMapping(params = "method=updateShippingOrder")
	public @ResponseBody
	Json updateShippingOrder(OrderCustom d, HttpServletRequest request) {
		Json json = new Json();
		String remarks_fee1 = request.getParameter("remarks_fee1");
		d.setRemarks_fee1(remarks_fee1);
		String paid_fee = request.getParameter("paid_fee");
		String transport_pay = request.getParameter("transport_pay");
		d.setTransport_pay(transport_pay);
		d.setPaid_fee(paid_fee);
		int i = cs.updateShippingOrder(d);
		if (i > 0) {
			json.setFlag(true);
			return json;
		}
		json.setFlag(false);
		return json;
	}

	// 修改
	@RequestMapping(params = "method=otherUpdate")
	public @ResponseBody
	Json otherUpdate(ShippingOrder d, HttpServletRequest request) {
		Json json = new Json();
		try{
		String paid = request.getParameter("paid");
		d.setPaid(paid);
		String write_remarks = request.getParameter("write_remarks");
		String paid_chayi = request.getParameter("paid_chayi");
		d.setPaid_chayi(paid_chayi);
		String chayi_daifukuan1 = request.getParameter("chayi_daifukuan1");
		d.setChayi_daifukuan1(chayi_daifukuan1);
		d.setWrite_remarks(write_remarks);
		String paid1 = request.getParameter("paid1");
		d.setPaid1(paid1);

		int i = cs.otherUpdate(d);
		}catch (Exception e) {
			// TODO: handle exception
		}
		return json;
	}

	// 修改查询
	@RequestMapping(params = "method=getUpdateCompany")
	public @ResponseBody
	OrderCustom getUpdateCompany(String id) {
		OrderCustom der = cs.getUpdateShippingOrder(id);
		return der;
	}

	// 跳转编辑页面并且获取合同的id 返回到页面
	@RequestMapping(params = "method=getEditCompanyPagef")
	public String getEdittCompanyPagef(String pid, HttpServletRequest request) {
		request.setAttribute("flg", pid);
		return "collect/Editcollect";
	}

	@RequestMapping(params = "method=getUpdate3")
	public @ResponseBody
	OrderCustom getUpdate3(String id) {
		OrderCustom der = cs.getupdate3(id);
		return der;
	}

	@RequestMapping(params = "method=update3")
	public @ResponseBody
	Json update3(OrderCustom d, HttpServletRequest request) {
		Json json = new Json();

		boolean flag = false;
		json.setFlag(flag);
		try {
			String[] s = d.getCustom_id().split(",");
			List<OrderCustom> list = cs.select(s);
			for (OrderCustom ye : list) {
				if ("3".equals(ye.getWrite_off3())) {

					for (int i = 0; i < list.size(); i++) {
						SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
						User user=(User)request.getSession().getAttribute(SessionInfo.SessionName);
						d.setPeople_kehu(user.getUsername());
						d.setKuhu_date(df.format(new Date()));
						int truk = cs.update3(ye.getCustom_id(),
								d.getWrite_off3(), d.getRemarks_fee1(),
								d.getKuhu_date(),d.getPeople_kehu());
					}

					// for (OrderCustom sy : list) {
					List<Confluence> list1 = new ArrayList<Confluence>();
					SimpleDateFormat df = new SimpleDateFormat(
							"yyyy-MM-dd");// 设置日期格式
					Confluence co = new Confluence();
					SimpleDateFormat dfc = new SimpleDateFormat(
							"yyyy-MM-dd ");// 设置日期格式
					System.out.println(dfc.format(new Date()));
					co.setFee_date(d.getKuhu_date());
					co.setDirection("收入");
					co.setSource("核销");
					co.setHappen(ye.getPaid_fee());
					co.setId(ye.getCustom_id());
					co.setProject("应收客户账款核销");
					co.setConf_id(UUIDUtils.getUUID());
					co.setShiping_order_num(ye.getCustom_name());
					co.setHandle("应收客户账款");
					list1.add(co);

					int flat = cs.insertinfo(list1);
					// }
					// List<OrderCustom> st = cs.select(s);
					// for (OrderCustom sq : list) {
					List<Mingxi> ls = new ArrayList<Mingxi>();
					Mingxi mx = new Mingxi();
					SimpleDateFormat df1 = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");// 设置日期格式
					mx.setHappen_date(df1.format(new Date()));

					mx.setSubject("应收客户账款核销");
					mx.setSubject_two("到付运费");
					mx.setMingxi_id(ye.getCustom_id());
					mx.setSubject_three(d.getWrite_off3());
					mx.setMoney(ye.getPaid_fee());
					mx.setRemarks(ye.getRemarks_fee1());
					mx.setShiping_order_num(ye.getCustom_name());
					mx.setCustomer_name(ye.getCustom_name());
					mx.setId(UUIDUtils.getUUID());
					ls.add(mx);
					int add = cs.add(ls);
					List<Settlement> sp = new ArrayList<Settlement>();
					Settlement st = new Settlement();
					st.setWrite_id(UUIDUtils.getUUID());
					st.setShiping_order_num(ye.getCustom_name());
					st.setWrite_project("应收客户账款");
					st.setWrite_data(d.getKuhu_date());
					st.setWrite_people(d.getPeople_kehu());
					st.setWrite_money(ye.getPaid_fee());
					st.setWrite_fangshi(d.getWrite_off3());
					st.setRemarks(ye.getRemarks_fee1());
					sp.add(st);
					int o = cs.addSettlement(sp);
				} else {
					
				}
			}
			return json;
		} catch (Exception e) {
			return json;
		}
	}

	*//**
	 * 应收其他费核销
	 *//*
	@RequestMapping(params = "method=gets")
	public String getCompanyQuerys() {
		return "collect/others";
	}

	// 全部查询
	@RequestMapping(params = "method=getAdorn_fee")
	public @ResponseBody
	Map getAdorn_fee(String rows, String page, String shiping_order_num,
			String send_station, String others_date,String aa,String write_off5,String custom_name,String receipt,String goods_name,String goods_num,String pay_type,String paid) {
		Integer rows1 = 0;// 当前有几行
		Integer page1 = 1;// 当前有几页
		if (rows != null && !"".equals(rows)) {
			rows1 = Integer.parseInt(rows);
		}
		if (page != null && !"".equals(page)) {
			page1 = Integer.parseInt(page);
		}
		List<ShippingOrder> list = cs.getAdorn_fee((page1 - 1) * rows1, rows1,
				shiping_order_num, send_station, others_date, aa,write_off5, custom_name, receipt, goods_name, goods_num, pay_type, paid);

		int count = cs.getAdorn_feeInfo(shiping_order_num, send_station,
				others_date,aa,write_off5, custom_name, receipt, goods_name, goods_num, pay_type, paid);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", count);
		map.put("rows", list);
		return map;
	}

	// 跳转编辑页面并且获取合同的id 返回到页面
	@RequestMapping(params = "method=getEditCompanyPages")
	public String getEdittCompanyPages(String pid, HttpServletRequest request) {

		request.setAttribute("flg", pid);
		return "collect/Editothers";
	}

	@RequestMapping(params = "method=getUpdate2")
	public @ResponseBody
	ShippingOrder getUpdate2(String id) {
		ShippingOrder der = cs.getUpdate2(id);
		return der;
	}

	// 修改
	@RequestMapping(params = "method=update9")
	public @ResponseBody
	Json update9(ShippingOrder d,HttpServletRequest request) {
		Json json = new Json();
		boolean flag = false;
		json.setFlag(flag);
		try {
			String[] s = d.getShiping_order_id().split(",");
			
			List<ShippingOrder> list10 = cs.selectinfo(s);
		
			for (ShippingOrder op : list10) {
				if ("3".equals(op.getWrite_off5())) {
					for (int i = 0; i < list10.size(); i++) {
						SimpleDateFormat df = new SimpleDateFormat(
								"yyyy-MM-dd HH:mm:ss");// 设置日期格式
						d.setOthers_date(df.format(new Date()));
					
						d.setShiping_order_num(op.getShiping_order_num());
						User user=(User)request.getSession().getAttribute(SessionInfo.SessionName);
						d.setPeople_others(user.getUsername());
						int truk = cs.update9(op.getShiping_order_id(),
								d.getWrite_off5(),	d.getOthers_date(), 
								d.getPeople_others(),d.getWrite_remarks());
					}
					// List<ShippingOrder> list = cs.selectinfo(s);
					// for (ShippingOrder sy : list) {
					List<Confluence> list1 = new ArrayList<Confluence>();

					Confluence co = new Confluence();
					SimpleDateFormat df2 = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");// 设置日期格式
					co.setFee_date(	d.getOthers_date());
					co.setDirection("收入");
					co.setSource("核销");
					co.setHappen(op.getPaid1());
					co.setId(op.getShiping_order_id());
					co.setProject("应付其他费， 运费核销 ");
					co.setConf_id(UUIDUtils.getUUID());
					co.setShiping_order_num(op.getShiping_order_num());
					co.setHandle("应收运费其,其它费");
					list1.add(co);

					int flat = cs.insertinfo(list1);
					// }
					// List<ShippingOrder> lt = cs.selectinfo(s);
					// for (ShippingOrder sq : lt) {
					List<Mingxi> ls = new ArrayList<Mingxi>();
					Mingxi mx = new Mingxi();
					SimpleDateFormat df1 = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");// 设置日期格式
					mx.setHappen_date(df1.format(new Date()));
					mx.setSubject("应付其他费， 运费核销");
					mx.setSubject_two("其他费，运费");
					mx.setMingxi_id(op.getShiping_order_id());
					mx.setSubject_three(d.getWrite_off5());
					mx.setMoney(op.getPaid1());
					mx.setRemarks(op.getWrite_remarks());
					mx.setShiping_order_num(op.getShiping_order_num());
					mx.setCustomer_name(op.getCustom_name());
					mx.setConsignee(op.getReceipt());
					mx.setId(UUIDUtils.getUUID());
					ls.add(mx);
					int add = cs.add(ls);
					List<Settlement> list9 =  new ArrayList<Settlement>();
					Settlement sl = new Settlement();
					sl.setWrite_id(UUIDUtils.getUUID());
					sl.setShiping_order_num(d.getShiping_order_num());
					sl.setWrite_project("应收其他费，运费");
					sl.setWrite_data(d.getOthers_date());
					sl.setWrite_people(d.getPeople_others());
					sl.setWrite_money(d.getPaid());
					sl.setWrite_fangshi(d.getWrite_off5());
					sl.setRemarks(op.getWrite_remarks());
					list9.add(sl);
					int p = cs.addSettlement(list9);
				} else {

				}

			}

			return json;
		} catch (Exception e) {
			return json;
		}
	}

	// 其他费运费取消核销
	@RequestMapping(params = "method=editOther")
	public @ResponseBody
	Json updateCompany(ShippingOrder d,String pid[],String ids[]
			) {
		Json json = new Json();
		boolean flag = false;
		json.setFlag(flag);
		List<ShippingOrder> list10 = cs.selectinfo(pid);
	
		for(ShippingOrder op : list10 ){
			d.setWrite_off5("3");
	
			ShippingOrder sp = new ShippingOrder();
			d.setOthers_date(sp.getOthers_date());
			d.setPeople_others(null);
			int truk = cs.editOther(op.getShiping_order_id(), d.getWrite_off5(),d.getOthers_date(),d.getPeople_others());
			int i = cs.delConf(pid);
			int v = cs.delMingxi(pid); 
		
			int qq =cs.sp(ids);
		
		}
		return json;
	}
	// 应收客户取消核销
		@RequestMapping(params = "method=editKuhu")
		public @ResponseBody
		Json updatekehu(OrderCustom d,String pid[]
				) {
			Json json = new Json();
			boolean flag = false;
			json.setFlag(flag);
			List<OrderCustom> list = cs.select(pid);
			for(OrderCustom op : list ){
				
				d.setWrite_off3("3");
				OrderCustom oc = new OrderCustom();
				d.setKuhu_date(oc.getKuhu_date());
				d.setPeople_kehu(null);
				int truk = cs.editKuhu(op.getCustom_id(),d.getWrite_off3(),d.getKuhu_date(),d.getPeople_kehu() );
				int i = cs.delConf(pid);
				int v = cs.delMingxi(pid); 
				
				
				
				
				
			
			
			}
			return json;
		}
		
		// 全部查询
		@RequestMapping(params = "method=selectSettement")
		public @ResponseBody
		Map selectSettement( String shiping_order_num
				) {

			if(shiping_order_num!=null){
				try {
					shiping_order_num=new String(shiping_order_num.getBytes("ISO-8859-1"),"utf-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			
			List<Settlement> list = cs.selectSettement(shiping_order_num);
			int count = cs.selectSettementInfo(shiping_order_num
					);
			System.out.println(shiping_order_num+"订单号");
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("total", count);
			map.put("rows", list);
			return map;
		}
}
*/