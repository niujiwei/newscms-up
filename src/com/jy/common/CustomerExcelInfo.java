package com.jy.common;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.jy.csaction.ExeclRead;
import com.jy.model.Customer;
import com.jy.service.RemarkMapService;

/*
 * 发货客户
 * */

public class CustomerExcelInfo {
	// private ApplicationContext ac;
	private RemarkMapService mapService;
	private JSONObject jsonObject = new JSONObject();//存放信息
	private JSONArray jsonArray = new JSONArray();//存放list 集合
	
	
	public CustomerExcelInfo() {
		super();
	}

	public CustomerExcelInfo(RemarkMapService mapService) {
		super();
		this.mapService = mapService;
		
		
	}

	public String impExcel(String execelFile, String realname, String pid)
			throws Exception {
		
		Customer tli;
		List<Customer> tlist = new ArrayList<Customer>();
		File file = new File(execelFile);
		String[][] result = ExeclRead.getData(file, 1);
		int rowLength = result.length;
		for (int i = 0; i < rowLength; i++) {
			tli = new Customer();
			// ----------------------循环插入
			for (int j = 0; j < result[i].length; j++) {
				// id
				if (j == 0) {
					tli.setCustomer_id(UUIDUtils.getUUID());
				} else if (j == 1) {
					// 客户编号
					tli.setCustomer_num(result[i][j - 1]);

				} else if (j == 2) {
					// 客户姓名
					tli.setCustomer_name(result[i][j - 1]);
				} else if (j == 3) {
					// 联系人
					tli.setCustomer_people(result[i][j - 1]);
				} else if (j == 4) {
					// 客户电话
					tli.setCustomer_tel(result[i][j - 1]);
				} else if (j == 5) {
					// 客户地址
					tli.setCustomer_address(result[i][j - 1]);
				} else if (j == 6) {
					// 主要业务
					tli.setCustomer_business(result[i][j - 1]);
				} else if (j == 7) {
					// 简介
					tli.setCustomer_profile(result[i][j - 1]);
				} else if (j == 8) {
					//
					tli.setCustomer_experience(result[i][j - 1]);
				} else if (j == 9) {
					tli.setCustomer_operation(realname);
				}
			}
			tlist.add(tli);
		}
		// 
		tlist = removeDuplicate(tlist);
		// List<ShippingOrder> listdata=OrderInfoServiceImpl.getAorder();
		// tlist=removeDataDuplicate(tlist,listdata);
		List<Customer> listsCustomers =mapService.getAllCustomers();
		tlist=removeDataDuplicate(tlist,listsCustomers);
		int js = tlist.size();//理论插入条数
		int zs = rowLength;// execl的总数
		int num = 0;//实际插入条数
		if (tlist.size() > 0) {
			num = mapService.customerExcelImport(tlist);
		}
		jsonObject.put("js", js);
		jsonObject.put("zs", zs);
		jsonObject.put("num", num);
		jsonObject.put("list", jsonArray);
		return jsonObject.toString();

	}

	//筛选发货客户信息
	public List<Customer> removeDuplicate(List<Customer> list) {
		for (int i = 0; i < list.size() - 1; i++) {
			for (int j = list.size() - 1; j > i; j--) {
				if(list.get(j).getCustomer_name().equals(list.get(i).getCustomer_name())&&list.get(j).getCustomer_address().equals(list.get(i).getCustomer_address())){
				
					JSONObject json = new JSONObject();
					json.put("customer_name", list.get(i).getCustomer_name());
					json.put("customer_address",list.get(i).getCustomer_address());
					json.put("rownum", (i+2));
					json.put("message", "execl中存在"+(j+2));
					jsonArray.add(json);
					list.remove(j);
				}
			}
		}
		return list;
	}

	public List<Customer> removeDataDuplicate(List<Customer> list,
			List<Customer> listdata) {
		for (int i = 0; i < listdata.size(); i++) {
			for (int j = 0; j < list.size(); j++) {
				if(list.get(j).getCustomer_name().equals(listdata.get(i).getCustomer_name())&&list.get(j).getCustomer_address().equals(listdata.get(i).getCustomer_address())){
					
					JSONObject json = new JSONObject();
					json.put("customer_name", list.get(j).getCustomer_name());
					json.put("customer_address",list.get(j).getCustomer_address());
					json.put("rownum", (j+2));
					json.put("message", "数据库中存在相同的信息");
					jsonArray.add(json);
					list.remove(j);
				}
			}
		}
		return list;
	}
}
