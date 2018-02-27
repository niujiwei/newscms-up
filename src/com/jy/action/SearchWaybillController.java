package com.jy.action;

import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jy.common.UUIDUtils;
import com.jy.model.Comment;
import com.jy.model.OrderHistory;
import com.jy.model.ShippingOrder;
import com.jy.service.SearchWaybillService;
import com.jy.service.ShippingOrderInfoService;

@Controller
@RequestMapping(value = "WeChart/searchWaybill.do")
public class SearchWaybillController {
	@Resource
	private SearchWaybillService sce;
	@Resource
	private ShippingOrderInfoService shiService;

	/**
	 * 新增app端查看运单历史记录，展示运单信息节点
	 */
	@RequestMapping(params = "method=getSearchWaybill")
	public @ResponseBody
	List<OrderHistory> getSearchWaybill(String shiping_order_num) {
		List<OrderHistory> historys = sce.getSearchWaybill(shiping_order_num);
		return historys;
	}

	/**
	 * app 查询运单
	 */
	@RequestMapping(params = "method=quickSearch")
	public @ResponseBody
	String quickSearch(String shiping_order_num, String shiping_order_goid) {
		JSONObject jb = null;
		JSONArray jsons = new JSONArray();
		List<ShippingOrder> list = sce.getShippingOrders(shiping_order_num,
				shiping_order_goid);
		for (ShippingOrder d : list) {
			jb = new JSONObject();
			jb.put("id", d.getShiping_order_id());
			jb.put("shiping_order_goid", d.getShiping_order_goid());
			jb.put("shiping_order_num", d.getShiping_order_num());
			jb.put("comment_state", d.getComment_state());
			jb.put("shipping_order_state", d.getShipping_order_state());
			jsons.add(jb);
		}
		return jsons.toString();
	}

	/**
	 * 获取运单信息
	 */
	@RequestMapping(params = "method=getOrderMessage")
	public @ResponseBody
	List<ShippingOrder> getOrderMessage(String id) {

		return sce.getOrderMessage(id);

	}

	/**
	 * 保存评价信息
	 */
	@RequestMapping(params = "method=saveMssage")
	public @ResponseBody
	String getOrderMessage(String message, String num, String datas) {
		JSONObject jsonArray = new JSONObject();
		Gson gson = new Gson();
		if(datas!=null&&!datas.equals("")){
			List<ShippingOrder> shippingOrders = gson.fromJson(datas,
					new TypeToken<List<ShippingOrder>>() {
					}.getType());

		
		
		if (shippingOrders.size() > 0) {
			ShippingOrder order = shippingOrders.get(0);
			Comment comment = new Comment();
			comment.setDriver_id(order.getDriver_id());
			comment.setDriver_name(order.getDriver_name());
			comment.setDriver_phone(order.getDriver_phone());
			comment.setShiping_order_id(order.getShiping_order_id());
			comment.setShiping_order_num(order.getShiping_order_num());
			comment.setSend_mechanism(order.getSend_mechanism());
			comment.setSuppliers_id(order.getDriver_suppliers());
			comment.setComment_id(UUIDUtils.getUUID());
			comment.setXing(num);
			comment.setRemark(message);
			
            shiService.updateComment_state(order.getShiping_order_id());
            int i =sce.getCommentCount(order.getShiping_order_id());
            if(i==0){
            	int j = sce.saveComment(comment);
            	if(j>0){
            		jsonArray.put("message",true);
            	}else{
            		jsonArray.put("message",false);
            	}
            }
            jsonArray.put("message",true);
            
		}
		}
		return jsonArray.toString();

	}
}
