package com.jy.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jy.common.UUIDUtils;
import com.jy.dao.Json;
import com.jy.model.Comment;
import com.jy.service.SearchDriverService;
import com.jy.service.impl.ShippingOrderInfoServiceImpl;

@Controller
@RequestMapping(value="/searchDriver.do")
public class SearchDriverController {
	@Resource
	private SearchDriverService dce;
	@Resource
	private ShippingOrderInfoServiceImpl soi;
	@RequestMapping(params = "method=getSearchDriver")
	public @ResponseBody Comment getSearchDriver(String receipt_name,String receipt_tel,String shiping_order_num,HttpServletRequest request){
		Comment comment =dce.getSearchDriver(receipt_name, receipt_tel,shiping_order_num);		
		return comment;
	}
	@RequestMapping(params = "method=getComment")
	public @ResponseBody Json getComment(String xing,String remark,HttpServletRequest request,String receipt_name,String receipt_tel,String shiping_order_num){
		Json json=new Json();
		Comment comment =dce.getSearchDriver(receipt_name, receipt_tel,shiping_order_num);		
		String uuid =UUIDUtils.getUUID();
		comment.setComment_id(uuid);		
		comment.setXing(xing);
		comment.setRemark(remark);
		
		String shiping_order_id=comment.getShiping_order_id();
		int j=soi.updateComment_state(shiping_order_id);
		int i=dce.saveComment(comment);
		if(i>0){
			json.setFlag(true);			
		}else{
			json.setFlag(false);
		}
		return json;
	}
}
