package com.jy.quartz;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baidu.map.BaiDuMap;
import com.baidu.map.GeoPoint;
import com.baidu.map.GeoUtility;
import com.jy.common.UUIDUtils;
import com.jy.model.DeliveryCustomer;
import com.jy.model.Maps;
import com.jy.model.OrderHistory;
import com.jy.model.ShippingOrder;
import com.jy.model.Sign_order;
import com.jy.model.SiteLine;
import com.jy.service.AlarmService;
import com.jy.service.JITMonitorService;
import com.jy.service.ShippingOrderInfoService;
import common.Logger;

/*
 * 报警管理
 * */
@Controller
@RequestMapping(value = "/alarm2.do")
public class AlarmMonitor {
	@Resource
	private AlarmService as;
	@Resource
	private ShippingOrderInfoService csi;
	@Resource
	private JITMonitorService jitService;
	private Logger log = Logger.getLogger(MyAging.class);
	
	public void monitor() {

		List<Maps> list = as.getMaps();// 获取每个车辆最新的坐标等信息
		System.out.println("list"+list.size());
		// 开始循环九通返回的定位数据
		for (Maps m : list) {
			//if(m.getCarid().equals("rzjxps")){
				//System.out.println("定时器里面的car_id"+m.getCarid());
				String driver_id = jitService.getDriverid(m.getCarid());
				 //System.out.println("定时器里面的driver_id"+driver_id);
				String[] orders = jitService.getDriverOrder(driver_id);
				if(orders.length==0) continue;
				try {
					checksite(orders, m);
					//checksignOrder();
				} catch (Exception e) {
					log.error("MyAging"+m.getCarid()+e.getMessage());
				}
				
			//}
		
			// System.out.println("orders.length"+orders.length);
			/*
			 * for(int i=0;i<orders.length;i++){
			 * System.out.println("i"+i+"====order_id"+orders[i]);
			 * 
			 * }
			 */
		
		}
	}

	// 2小时自动签收
	public void checksignOrder() {
		List<ShippingOrder> orders = jitService.getSignOrders();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		if (orders != null) {
			for (ShippingOrder order : orders) {
				String time0 = order.getSign_time();
				String time2 = sdf.format(now);
				// System.out.println("电子签收时间"+time0);
				 //System.out.println("当前系统时间"+time2);
				long result = 0;
				try {
					result = (sdf.parse(time2).getTime() - sdf.parse(time0)
							.getTime()) / 60000;
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (result > 120) {
					jitService.updateOrder_state(order.getShiping_order_id());
					saveHistory(order.getShiping_order_id(),
							order.getShiping_order_num(), "app电子围栏2小时到自动签收成功");
				}

			}
		}

	}

	/**
	 * 监控
	 * 
	 * @param l
	 *            线路信息
	 * @param m
	 *            车辆信息以及车辆坐标信息
	 */
	public void checksite(String[] orders, Maps m) {
		Boolean b = new Boolean(null);
		// System.out.println(list.size());
		for (int i2 = 0; i2 < orders.length; i2++) {
			System.out.println("ordersId" + orders[i2]);
			DeliveryCustomer customer = new DeliveryCustomer();
			List<DeliveryCustomer> cus =null;
			ShippingOrder order = jitService.getOrder(orders[i2]);
			try {
				// cus = jitService.getCustomer(orders[i2]);
				 cus = jitService.getNewCustomer(order.getReceipt_name(),order.getReceipt_name(),order.getSend_mechanism());
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (cus!= null) {
				if(cus.size()>0){
				customer=cus.get(0);
			
				if ("1".equals(customer.getDelivery_areaType())) {// 区域
					String[] points = customer.getDelivery_point().split("\\|");// 取出区域的每个点
					GeoPoint[] polygon = new GeoPoint[points.length - 1];
					for (int i = 1; i < points.length; i++) {// 遍历所有点拆分为经纬度放入polygon中
						String lng = points[i].split(",")[0];
						String lat = points[i].split(",")[1];
						GeoPoint g1 = new GeoPoint(Double.parseDouble(lng),
								Double.parseDouble(lat));
						polygon[i - 1] = g1;
					}
					GeoPoint g2 = new GeoPoint(Double.parseDouble(m.getLng()),
							Double.parseDouble(m.getLat()));
					// 判断当前车辆的坐标点是否在该坐标区域内
					b = GeoUtility.isInPolygon(g2, polygon);
				} else if ("0".equals(customer.getDelivery_areaType())) {// 点
					String[] points = customer.getDelivery_point().split("\\|");// 圆心坐标
					String lng = m.getLng();
					String lat = m.getLat();// 车定位的坐标
					String radius = customer.getDelivery_radius();
					// System.out.println(Double.parseDouble(points[0].split(",")[0]));
					// System.out.println(Double.parseDouble(points[0].split(",")[1]));
					double distance = BaiDuMap.GetShortDistance(
							Double.parseDouble(points[0].split(",")[0]),
							Double.parseDouble(points[0].split(",")[1]),
							Double.parseDouble(lng), Double.parseDouble(lat));
					double radius2 = Double.parseDouble(radius);
					// 判断是否在圆内
					double result = distance - radius2;
					if (result < 0) {
						b = true;
					} else {
						b = false;
					}
				}
				}
				
				if (b) {
					System.out.println("电子围栏签收结果"+b);
					signOrder(customer, orders[i2]);
				} else {

				}
			}
		}
	}

	// 电子签收
	public void signOrder(DeliveryCustomer customer, String order_id) {
		int i = jitService.signOrderyesorno(order_id);
		if (i != 3 && i != 4) {
			ShippingOrder order = new ShippingOrder();
			order = jitService.getOrder(order_id);
			jitService.signOrder(order);
			saveHistory(order_id, order.getShiping_order_num(), "app电子围栏签收成功");
		}
	}

	public void saveHistory(String ids, String num, String message) {
		List<OrderHistory> order = new ArrayList<OrderHistory>();
		OrderHistory h = new OrderHistory();
		h.setOrder_history_id(UUIDUtils.getUUID());
		h.setOrders_id(ids);
		h.setOrder_arrive_time(DateFormat.getDateTimeInstance().format(
				new Date()));
		h.setGoods_arrive_remakes(h.getOrder_arrive_time() + "---货运编号为" + num
				+ "\t" + message);
		order.add(h);
		int count = csi.saveOrderHistory(order);
	}

	// 获取最新定位数据
	@RequestMapping(params = "method=getMaps")
	public @ResponseBody
	List<Maps> getMaps() {
		System.out.println("第六站");
		List<Maps> list = as.getMaps();
		/*
		 * for(Maps m:list){ System.out.println(m.getCarid());
		 * 
		 * }
		 */
		return list;

	}

	// 获取数据库的线路信息状态,好确定是添加或更新
	@RequestMapping(params = "method=getSiteState")
	public @ResponseBody
	Boolean getSiteState(String line_id, String car_id) {
		System.out.println("第十站");
		int i = as.getSiteState(car_id, line_id);
		if (i > 0) {
			return true;
		} else {
			return false;
		}
	}

	// 获取该线路站点信息
	@RequestMapping(params = "method=getSite")
	public @ResponseBody
	List<SiteLine> getSite(String id) {
		List<SiteLine> list = as.getSite(id);
		return list;
	}

	public static String change(int second) {
		int h = 0;
		int d = 0;
		int s = 0;
		int temp = second % 3600;
		if (second > 3600) {
			h = second / 3600;
			if (temp != 0) {
				if (temp > 60) {
					d = temp / 60;
					if (temp % 60 != 0) {
						s = temp % 60;
					}
				} else {
					s = temp;
				}
			}
		} else {
			d = second / 60;
			if (second % 60 != 0) {
				s = second % 60;
			}
		}
		String xx = "";
		if (h != 0) {
			xx += h + "时";
		}
		if (d != 0) {
			xx += d + "分";
		}
		if (s != 0) {
			xx += s + "秒";
		}
		return xx;
	}

	public String jisuanTime(String xx, String yy) {
		int hour = 0;
		int hour2 = 0;
		int minute = 0;
		int minute2 = 0;
		int second = 0;
		int second2 = 0;
		if (xx != null || !"".equals(xx)) {
			int i = xx.indexOf('时');
			if (i != -1) {
				String yy1 = xx.substring(0, i);
				hour = Integer.parseInt(yy1);
				xx = xx.substring(i + 1);
				i = xx.indexOf('分');
				if (i != -1) {
					yy1 = xx.substring(0, i);
					minute = Integer.parseInt(yy1);
					xx = xx.substring(i + 1);
					i = xx.indexOf('秒');
					if (i != -1) {
						yy1 = xx.substring(0, i);
						second = Integer.parseInt(yy1);
					} else {
						second = 0;
					}
				} else {
					minute = 0;
					int i3 = xx.indexOf('秒');
					if (i3 != -1) {
						String yy3 = xx.substring(0, i3);
						second = Integer.parseInt(yy3);
					} else {
						second = 0;
					}
				}
			} else {
				hour = 0;
				int i2 = xx.indexOf('分');
				if (i2 != -1) {
					String yy2 = xx.substring(0, i2);
					minute = Integer.parseInt(yy2);
					xx = xx.substring(i2 + 1);
					i2 = xx.indexOf('秒');
					if (i2 != -1) {
						yy2 = xx.substring(0, i2);
						second = Integer.parseInt(yy2);
					} else {
						second = 0;
					}
				} else {
					minute = 0;
					int i3 = xx.indexOf('秒');
					if (i3 != -1) {
						String yy3 = xx.substring(0, i3);
						second = Integer.parseInt(yy3);
					} else {
						second = 0;
					}
				}
			}
		}
		if (yy != null || !"".equals(yy)) {
			int i = yy.indexOf('时');
			if (i != -1) {
				String yy1 = yy.substring(0, i);
				hour2 = Integer.parseInt(yy1);
				yy = yy.substring(i + 1);
				i = yy.indexOf('分');
				if (i != -1) {
					yy1 = yy.substring(0, i);
					minute2 = Integer.parseInt(yy1);
					yy = yy.substring(i + 1);
					i = yy.indexOf('秒');
					if (i != -1) {
						yy1 = yy.substring(0, i);
						second2 = Integer.parseInt(yy1);
					} else {
						second2 = 0;
					}
				} else {
					minute2 = 0;
					int i3 = yy.indexOf('秒');
					if (i3 != -1) {
						String yy3 = yy.substring(0, i3);
						second2 = Integer.parseInt(yy3);
					} else {
						second2 = 0;
					}
				}
			} else {
				hour2 = 0;
				int i2 = yy.indexOf('分');
				if (i2 != -1) {
					String yy2 = yy.substring(0, i2);
					minute2 = Integer.parseInt(yy2);
					yy = yy.substring(i2 + 1);
					i2 = yy.indexOf('秒');
					if (i2 != -1) {
						yy2 = yy.substring(0, i2);
						second2 = Integer.parseInt(yy2);
					} else {
						second2 = 0;
					}
				} else {
					minute2 = 0;
					int i3 = yy.indexOf('秒');
					if (i3 != -1) {
						String yy3 = yy.substring(0, i3);
						second2 = Integer.parseInt(yy3);
					} else {
						second2 = 0;
					}
				}
			}
		}
		String finalTime = "";
		int millisecond = hour * 60 * 60 + minute * 60 + second;
		int millisecond2 = hour2 * 60 * 60 + minute2 * 60 + second;
		int result = millisecond2 - millisecond;
		if (result > 0) {
			finalTime = change(result);
		} else {
			int b = Math.abs(result);
			finalTime = "-" + change(b);
		}
		return finalTime;
	}
}
