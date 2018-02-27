package com.jy.common;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.jy.csaction.ExeclRead;
import com.jy.model.DeliveryCustomer;
import com.jy.service.RemarkMapService;

/*
 * 接收货客户  导入
 * **/

public class DeliveryExcelInfo {
	

	private RemarkMapService rmsMapService;
	private JSONObject jsonObject = new JSONObject();//存放信息
	private JSONArray jsonArray = new JSONArray();//存放list 集合

	
	public DeliveryExcelInfo() {
		super();
	}


	public DeliveryExcelInfo(RemarkMapService rmsMapService) {
		super();
		this.rmsMapService = rmsMapService;
	}


	public String impExcel(String execelFile, String usersname, String pid)
			throws Exception {
		DeliveryCustomer tli;
		
		List<DeliveryCustomer> tlist = new ArrayList<DeliveryCustomer>();
		File file = new File(execelFile);
		String[][] result = ExeclRead.getData(file, 1);
		int rowLength = result.length;
		for (int i = 0; i < rowLength; i++) {
			tli = new DeliveryCustomer();
			// ----------------------循环插入
			for (int j = 0; j < result[i].length; j++) {
				// id
				/*
				 * if (j == 0) { tli.setDelivery_id(UUIDUtils.getUUID());
				 * //发货客户名称 }
				 */if (j == 0) {
					tli.setDelivery_cus_name(result[i][j]);
				} else if (j == 1) {
					// 收客户编号
					tli.setDelivery_num(result[i][j]);

				} else if (j == 2) {
					// 收货客户姓名
					tli.setDelivery_name(result[i][j]);
				} else if (j == 3) {
					// 收获联系人
					tli.setDelivery_people(result[i][j]);
				} else if (j == 4) {
					// 客户电话
					tli.setDelivery_tel(result[i][j]);
				} else if (j == 5) {
					// 客户地址
					tli.setDelivery_address(result[i][j]);
				}/*
				 * else if (j == 7) { // 主要业务
				 * tli.setDelivery_business(result[i][j]); }
				 */else if (j == 6) {
					// 简介
					tli.setDelivery_profile(result[i][j]);
				} /*
				 * else if (j == 9) { //
				 * tli.setDelivery_experience(result[i][j]); }
				 */

			}

			/*
			 * if (tli.getShiping_order_num() != "") {
			 * tli.setShipping_order(usersname); tli.setCreat_type(1);
			 * tli.setShipping_order_state(0);
			 * tli.setShiping_order_id(UUIDUtils.getUUID());
			 * tli.setUpdatetime(DateFormat.getDateTimeInstance().format( new
			 * Date()));
			 * tli.setOrder_time(DateFormat.getDateTimeInstance().format( new
			 * Date()));// 制单时间
			 * 
			 * //System.out.println(tli.getShiping_yueid()+"???????????????????????"
			 * );
			 * 
			 * }
			 */
			tli.setDelivery_id(UUIDUtils.getUUID());
			tlist.add(tli);
		}
		// 筛选订单
		// tlist=removeDuplicate(tlist);
		// List<ShippingOrder> listdata=OrderInfoServiceImpl.getAorder();
		// tlist=removeDataDuplicate(tlist,listdata);
		int js = tlist.size();// 插入成功条数
		int zs = rowLength;// 总数

		//String rb = zs + "#" + js + "#" + order_num.toString();
		/*
		 * for (int i = 0; i < tlist.size(); i++) { System.out.println("订单号：" +
		 * tlist.get(i).getShiping_order_num()); }
		 */
		int num = 0;
		/*
		 * for (ShippingOrder shippingOrder : tlist) {
		 * System.out.println(shippingOrder.getShiping_order_id()); }
		 */
		if (tlist.size() > 0) {
			//System.out.println(tlist.size() + "/////////////////////////////");

			num = rmsMapService.deliveryExcelInfo(tlist);
		}
		jsonObject.put("js", js);
		jsonObject.put("zs", zs);
		jsonObject.put("num", num);
		jsonObject.put("list", jsonArray);
		return jsonObject.toString();

	}

	
}
