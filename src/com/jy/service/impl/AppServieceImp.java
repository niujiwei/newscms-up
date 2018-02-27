package com.jy.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.baidu.yun.core.annotation.R;
import com.baidu.yun.core.filter.IFieldFilter;
import com.jy.dao.ReverseOrderDao;
import com.jy.model.*;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Component;

import com.jy.common.UUIDUtils;
import com.jy.dao.AppDao;
import com.jy.dao.RemarkMapDao;
import com.jy.dao.ShippingOrderDao;
import com.jy.service.AppService;

@Component
public class AppServieceImp implements AppService {
	@Resource
	private AppDao app_dao;
	@Resource
	private RemarkMapDao rms_dao;
	@Resource
	private ShippingOrderDao so_dao;

	@Resource
	private ReverseOrderDao reverseOrderDao;

	// APP 获取部门信息
	public List<Department> appGetDepartmentInfo(String departmentId,
			String departmentName) {

		return app_dao.appGetDepartmentInfo(departmentId, departmentName);
	}

	// APP 获取司机信息
	public List<Driver> appGetDriverInfo(User user, String driverName,
			String type) {
		List<Driver> list = null;
		if (user != null) {
			if (user.getFlag().equals("0")) {// 管理员
				list = app_dao.appGetDriverInfo(driverName, type);
			} else if (user.getFlag().equals("1")) {// 司机
				list = app_dao.appDriverGetDriverInfo(user.getDriver_id(),
						driverName, type);
			} else if (user.getFlag().equals("2")) {// 供应商
				list = app_dao.appSupplersGetDriverInfo(user.getSuppliers_id(),
						driverName, type);

			} else if (user.getFlag().equals("3") || user.getFlag().equals("4")) {// 信息员
				list = app_dao.appOtherGetDriverInfo(user.getCustomer_id(),
						driverName, type);
			}
		}
		return list;
	}

	// APP 查询供应商
	public List<JySuppliers> appGetSupplersInfo(User user, String supplersName,
			String type) {
		List<JySuppliers> list = null;
		if (user != null) {
			if (user.getFlag().equals("0")) {// 管理员
				list = app_dao.appGetSuppliersInfo(supplersName, type);
			} else if (user.getFlag().equals("1")) {// 司机

			} else if (user.getFlag().equals("2")) {// 供应商

			} else if (user.getFlag().equals("3") || user.getFlag().equals("4")) {// 信息员
				list = app_dao.appOtherGetSuppliersInfo(user.getCustomer_id(),
						supplersName, type);
			}
		}
		return list;
	}

	// APP 查询项目部
	public List<Customer> appGetCustomersInfo(User user, String customersName) {
		List<Customer> list = null;
		if (user != null) {
			if (user.getFlag().equals("0")) {// 管理员
				list = app_dao.appGetCustomersInfo(customersName);
			} else if (user.getFlag().equals("1")) {// 司机

			} else if (user.getFlag().equals("2")) {// 供应商

			} else if (user.getFlag().equals("3") || user.getFlag().equals("4")) {// 信息员

			}
		}
		return list;
	}

	// APP 用户添加检查用户名是否存在
	public boolean appCheckUser(String name) {
		boolean b = false;
		int i = app_dao.appCheckUser(name);
		if (i > 0) {
			b = true;
		}
		return b;
	}

	// APP 添加用户信息
	public boolean appSaveUserInfo(User user) {
		boolean b = false;
		int i = app_dao.appSaveUserInfo(user);
		if (i > 0) {
			b = true;
		}

		return b;
	}

	// APP 修改用户信息
	public boolean appUpdataUserInfo(User user) {
		boolean b = false;
		int i = app_dao.appUpdataUserInfo(user);
		if (i > 0) {
			b = true;
		}
		return b;
	}

	// APP异常反馈 添加信息
	public boolean appSaveAbnormalReport(AbnormalReport abnormalReport) {
		boolean b = false;
		int i = app_dao.appSaveAbnormalReport(abnormalReport);
		if (i > 0) {
			b = true;
		}
		return b;
	}

	// APP 异常反馈 保存图片信息
	public boolean appSaveAbnormalImage(List<AbnormalImages> abnormalImages) {
		boolean b = false;
		int i = app_dao.appSaveAbnormalImage(abnormalImages);
		if (i > 0) {
			b = true;
		}
		return b;
	}

	// APP 异常反馈更新运单状态
	public boolean appUpdateIsAbnormal(String shippingOrderId, String flag) {
		boolean b = false;
		int i = app_dao.appUpdateIsAbnormal(shippingOrderId, flag);
		if (i > 0) {
			b = true;
		}
		return b;
	}

	// APP 异常处理更新异常表中信息
	public boolean appUpdateAbnormalState(String id,
			String abnormal_resultremark, String userName) {
		boolean b = false;
		int i = app_dao.appUpdateAbnormalState(id, abnormal_resultremark,
				userName);
		if (i > 0) {
			b = true;
		}
		return b;
	}

	// APP 异常处理查询运单是否有没有处理的
	public boolean appGetAbnormalOrderNum(String orderId) {
		boolean b = true;
		int i = app_dao.appGetAbnormalOrderNum(orderId);
		if (i > 0) {
			b = false;
		}
		return b;
	}

	// APP 更新运单是否异常上报
	public boolean appUpdateOrederAbnormal(String orderId) {
		boolean b = false;
		int i = app_dao.appUpdateOrederAbnormal(orderId);
		if (i > 0) {
			b = true;
		}
		return b;
	}

	// APP 查看订单是否分配
	public boolean appShiFouFenPei(String orderId) {
		boolean b = true;
		int i = app_dao.appShiFouFenPei(orderId);
		if (i > 0) {
			b = false;
		}
		return b;
	}

	// APP 司机管理分配运单保存
	public int appSaveDrivertoOrder(List<DriverToOrder> orders) {

		return app_dao.appSaveDrivertoOrder(orders);
	}

	// APP 分配订单修改状态
	public int appUpdateShipperorderState(String[] did) {
		return app_dao.appUpdateShipperorderState(did);
	}

	// APP 司机接单
	public int appReceiveOrder(String order_id, String driver_id) {

		return app_dao.appReceiveOrder(order_id, driver_id);
	}

	// APP 司机接单更新状态
	public int appUpdateOrderState(int flag, String order_id, String allTime) {

		return app_dao.appUpdateOrderState(flag, order_id, allTime);
	}

	// APP 获取司机姓名
	public String appGetDriverName(String driver_id) {

		return app_dao.appGetDriverName(driver_id);
	}

	// APP 接单将标准时效更新到订单中
	public String appGetAgingAllTime(String orderId) {

		return app_dao.appGetAgingAllTime(orderId);
	}

	// APP 签收是否有签收信息
	public boolean appIsnotSignOrder(String order_id) {
		boolean b = false;
		int i = app_dao.appIsnotSignOrder(order_id);
		if (i > 0) {
			b = true;
		}
		return b;
	}

	// APP 更新签收信息
	public int appUpdateSignShipOrder(Sign_order sign_order) {

		return app_dao.appUpdateSignShipOrder(sign_order);
	}

	// APP 保存签收信息
	public int appSaveSignShipOrder(Sign_order sign_order) {

		return app_dao.appSaveSignShipOrder(sign_order);
	}

	// APP 运单签收保存图片路径
	public int appSaveSignImages(List<ShipperOrderImg> shiOrdImg) {

		return app_dao.appSaveSignImages(shiOrdImg);
	}

	// APP 运单签收 更新运单状态
	public int appUpdatestate(String shiping_order_id) {

		return app_dao.appUpdatestate(shiping_order_id);
	}

	// APP 运单签收 保存运单时效信息
	public int appInsertShippingTime(String shiping_order_id) {

		return app_dao.appInsertShippingTime(shiping_order_id);
	}

	// APP获取供应商下面的司机
	public List<Driver> appGetDriverInfoSupp(String driverInfoSupp) {

		return app_dao.appGetDriverInfoSupp(driverInfoSupp);
	}

	// APP 获取签收列表信息
	public List<ShippingOrder> appGetSignOrderInfo(int row, int page,
			String send_time, String end_time, String shiping_order_num,
			String send_mechanism, String receipt_name, String end_address,
			String shiping_order_goid, User user) {
		List<ShippingOrder> list = new ArrayList<ShippingOrder>();
		if (user != null) {
			if (user.getFlag().equals("0")) {// 管理员
				list = app_dao.appGetAllSignOrderInfo(row, page,
						shiping_order_num, send_time, end_time, send_mechanism,
						receipt_name, end_address, shiping_order_goid);
			} else if (user.getFlag().equals("1")) {// 司机
				list = app_dao.appGetSignOrderInfo(row, page,
						shiping_order_num, send_time, end_time, send_mechanism,
						receipt_name, end_address, shiping_order_goid,
						user.getDriver_id());

			} else if (user.getFlag().equals("2")) {// 供应商
				list = app_dao.appGetSignOrderInfo(row, page,
						shiping_order_num, send_time, end_time, send_mechanism,
						receipt_name, end_address, shiping_order_goid,
						user.getDriver_id());

			} else if (user.getFlag().equals("3") || user.getFlag().equals("4")) {// 信息员

			}
		}

		return list;
	}

	// APP 获取接单列表
	public List<ShippingOrder> appGetReceiveOrderInfo(int row, int page,
			String send_time, String end_time, String shiping_order_num,
			String send_mechanism, String receipt_name, String end_address,
			String shiping_order_goid, User user) {
		List<ShippingOrder> list = new ArrayList<ShippingOrder>();
		if (user != null) {
			if (user.getFlag().equals("0")) {// 管理员
				list = app_dao.appAllGetReceiveOrderInfo(row, page,
						shiping_order_num, send_time, end_time, send_mechanism,
						receipt_name, end_address, shiping_order_goid);

			} else if (user.getFlag().equals("1")) {// 司机
				list = app_dao.appGetReceiveOrderInfo(row, page,
						shiping_order_num, send_time, end_time, send_mechanism,
						receipt_name, end_address, shiping_order_goid,
						user.getDriver_id());

			} else if (user.getFlag().equals("2")) {// 供应商
				list = app_dao.appGetReceiveOrderInfo(row, page,
						shiping_order_num, send_time, end_time, send_mechanism,
						receipt_name, end_address, shiping_order_goid,
						user.getDriver_id());

			} else if (user.getFlag().equals("3") || user.getFlag().equals("4")) {// 信息员

			}
		}

		return list;
	}

	// APP 查询看消息信息
	public List<MsgModel> appGetMsgInfo(User user, String state) {

		return app_dao.appGetMsgInfo(user.getId(), state);
	}

	// APP 查询未读消息个数
	public int appGetMsgCount(String userId, String state) {

		return app_dao.appGetMsgCount(userId, state);
	}

	// APP 消息已读
	public int appUpdateMsgState(String messageId, String state,String userId) {

		return app_dao.appUpdateMsgState(messageId, state, userId);
	}

	// APP 获取逆向物流信息
	public List<ShippingOrder> appGetReverseOrderInfo(int row, int page,
			String send_time, String end_time, String shiping_order_num,
			String end_mechanism, String custom_name,
			String shiping_order_state, User user) {
		List<ShippingOrder> list = null;
		if (user != null) {
			if (user.getFlag().equals("0")) {// 管理员
				list = app_dao.appGetReverseOrderInfo(row, page, send_time,
						end_time, shiping_order_num, end_mechanism,
						custom_name, shiping_order_state);
			} else if (user.getFlag().equals("1")) {// 司机
				list = app_dao.appGetDriverReverseOrderInfo(row, page,
						send_time, end_time, shiping_order_num, end_mechanism,
						custom_name, shiping_order_state, user.getDriver_id());

			} else if (user.getFlag().equals("2")) {// 供应商
				list = app_dao.appGetSupplerReverseOrderInfo(row, page,
						send_time, end_time, shiping_order_num, end_mechanism,
						custom_name, shiping_order_state,
						user.getSuppliers_id());

			} else if (user.getFlag().equals("3") || user.getFlag().equals("4")) {// 信息员
				list = app_dao
						.appGetOtherReverseOrderInfo(row, page, send_time,
								end_time, shiping_order_num, end_mechanism,
								custom_name, shiping_order_state,
								user.getCustomer_id());
			}
		}
		return list;
	}

	// APP 查询逆向物流运单号是否存在
	public boolean appCheckReverseOrderNum(String orderNum) {
		int i = app_dao.appCheckReverseOrderNum(orderNum);
		boolean b = true;
		if (i > 0) {
			b = false;
		}
		return b;
	}

	// APP 逆向物流录入
	public int appSaveReverseOrder(ShippingOrder shippingOrder) {

		return app_dao.appSaveReverseOrder(shippingOrder);
	}

	// APP 逆向物流保存签收信息
	public boolean appSaveSignReverseOrder(Sign_order singOrder) {
		boolean b = false;
		int i = app_dao.appSaveSignReverseOrder(singOrder);
		if (i > 0) {
			b = true;
		}
		return b;
	}

	// APP 逆向签收查询是否签收
	public boolean appCkeckReverseOrderCount(String orderId) {
		boolean b = true;
		int i = app_dao.appCkeckReverseOrderCount(orderId);
		if (i > 0) {
			b = false;
		}
		return b;
	}

	// APP 逆向物流保存签收图片
	public boolean appSaveSignReverseOrderImage(List<ShipperOrderImg> shiOrdImg) {
		boolean b = false;
		int i = app_dao.appSaveSignReverseOrderImage(shiOrdImg);
		if (i > 0) {
			b = true;
		}
		return b;
	}

	// APP 逆向物流更新运单状态
	public boolean appUpdateReverseOrderState(String shiping_order_id) {
		boolean b = false;
		int i = app_dao.appUpdateReverseOrderState(shiping_order_id);
		if (i > 0) {
			b = true;
		}
		return b;
	}

	// APP 逆向物流更新签收信息
	public boolean appUpdateSignReverseOrder(Sign_order singOrder) {
		boolean b = false;
		int i = app_dao.appUpdateSignReverseOrder(singOrder);
		if (i > 0) {
			b = true;
		}
		return b;
	}

	// APP 获取城市信息
	public List<City_info> appGetCityInfo(String citye_parent_id) {

		return app_dao.appGetCityInfo(citye_parent_id);
	}

	// APP 获取历史节点信息
	public List<OrderHistory> appGetOrderHistory(String orderId, String type) {
		List<OrderHistory> list = null;
		if (type != null) {
			if (type.equals("1")) {// 正向物流查询运单信息
				list = app_dao.appGetShipperOrderHistory(orderId);

			} else if (type.equals("2")) {// 逆向物流查询运单信息
				list = app_dao.appGetReverseOrderHistory(orderId);
			}
		}
		return list;
	}

	// APP 查询订单签收图片信息
	public List<ShipperOrderImg> appGetOrderImages(String orderId, String type) {
		List<ShipperOrderImg> list = null;
		if (type != null) {
			if (type.equals("1")) {// 正向物流查询运单信息
				list = app_dao.appGetShipperOrderImages(orderId);

			} else if (type.equals("2")) {// 逆向物流查询运单信息
				list = app_dao.appGetReverseOrderImages(orderId);
			} else if (type.equals("3")) {// 异常上报
				list = app_dao.appGetAbnormalImages(orderId);
			}
		}
		return list;

	}

	// APP 添加供应商信息
	public boolean appSaveSuppliers(JySuppliers suppliers) {
		boolean b = false;
		int i = app_dao.appSaveSuppliers(suppliers);
		if (i > 0) {
			b = true;
		}
		return b;
	}

	// APP 保存供应商的省市县信息
	public boolean appSaveSuppliersCityInfo(JySuppliers suppliers) {
		boolean b = false;
		List<JySuppliers> jyslist = new ArrayList<JySuppliers>();
		if (suppliers.getDriver_countyname() != null
				&& !suppliers.getDriver_countyname().equals("")) {
			String[] str = suppliers.getDriver_countyname().split(",");
			for (int i = 0; i < str.length; i++) {
				JySuppliers jsu = new JySuppliers();
				jsu.setId(UUIDUtils.getUUID());
				jsu.setSuppliersId(suppliers.getSuppliersId());
				jsu.setDriver_province(suppliers.getDriver_province());
				jsu.setDriver_city(suppliers.getDriver_city());
				if (str[i] != null && !str[i].equals("")) {
					Integer countyid = Integer.parseInt(str[i].trim());
					jsu.setDriver_county(countyid);
				}

				jyslist.add(jsu);
			}
		} else {
			JySuppliers jsu = new JySuppliers();
			jsu.setId(UUIDUtils.getUUID());
			jsu.setSuppliersId(suppliers.getSuppliersId());
			jsu.setDriver_province(suppliers.getDriver_province());
			jsu.setDriver_city(suppliers.getDriver_city());
			jyslist.add(jsu);

		}
		int i = app_dao.appSaveSupplersCityInfo(jyslist);
		if (i > 0) {
			b = true;
		}
		return b;
	}

	// APP 司机管理司机信息添加
	public boolean appSaveDriver(Driver d) {
		boolean b = false;
		int i = app_dao.appSaveDriver(d);
		if (i > 0) {
			b = true;
		}
		return b;
	}

	// APP 保存司机终到位置
	public boolean appsaveDriverCityInfo(Driver driver) {
		boolean b = false;

		List<Driver> listDriver = new ArrayList<Driver>();

		if (driver.getDriver_countyname() != null
				&& !driver.getDriver_countyname().equals("")) {
			String[] str = driver.getDriver_countyname().split(",");
			for (int i = 0; i < str.length; i++) {
				Driver diDriver = new Driver();
				diDriver.setId(UUIDUtils.getUUID());
				diDriver.setDriver_id(driver.getDriver_id());
				diDriver.setDriver_province(driver.getDriver_province());
				diDriver.setDriver_city(driver.getDriver_city());
				if (str[i] != null && !str[i].equals("")) {
					Integer countyid = Integer.parseInt(str[i].trim());
					diDriver.setDriver_county(countyid);
				}
				listDriver.add(diDriver);
			}
		} else {
			Driver diDriver = new Driver();
			diDriver.setId(UUIDUtils.getUUID());
			diDriver.setDriver_id(driver.getDriver_id());
			diDriver.setDriver_province(driver.getDriver_province());
			diDriver.setDriver_city(driver.getDriver_city());
			listDriver.add(diDriver);

		}
		int j = app_dao.appSaveDriverCityInfo(listDriver);
		if (j > 0) {
			b = true;
		}
		return b;
	}

	// APP 司机管理修改司机信息
	public boolean appUpdateDriver(Driver d) {
		boolean b = false;
		int i = app_dao.appUpdateDriver(d);
		if (i > 0) {
			b = true;
		}
		return b;
	}

	// APP 供应商管理更新信息
	public boolean appUpdateSuppliers(JySuppliers jyuppliers) {
		boolean b = false;
		int i = app_dao.appUpdateSuppliers(jyuppliers);
		if (i > 0) {
			b = true;
		}
		return b;
	}

	// APP逆向物流 获取供应商信息
	public List<Customer> appReverseGetCustomersInfo(String customersName) {

		return app_dao.appReverseGetCustomersInfo(customersName);
	}

	// APP 获取用户信息
	public User appGetUserInfo(User user) {

		return app_dao.appGetUserInfo(user);
	}

	// APP 获取用户对应的权限
	public List<String> getAppMenuRole(User user) {

		return app_dao.getAppMenuRole(user);
	}



	@Override
	public List<String> appGetMenuAndRole(User user,String fatherId) {

		return app_dao.appGetMenuAndRole(user.getId(),fatherId);
	}

	// APP 获取消息
	public Map<String, Integer> getOrderMsgCount(String id) {

		return app_dao.getOrderMsgCount(id);
	}

	public List<Map<String, Integer>> getNumberMessage(User user) {
		// int count = 0;
			List<Map<String, Integer>> list = new ArrayList<Map<String, Integer>>();
			if (user != null) {
				if (user.getFlag().equals("0")) {// 管理员
					list = app_dao.getNumberMessage(user.getId());

				} else if (user.getFlag().equals("1")) {// 司机
					list = app_dao.driverGetNumberMessage(user.getId(),
							user.getDriver_id());

				} else if (user.getFlag().equals("2")) {// 供应商
					List<String> userAddress = rms_dao.getAddressSupperlies(user
							.getSuppliers_id());
					list = app_dao.supplierGetNumberMessage(user.getId(),
							user.getDriver_id(), user.getSuppliers_id(),
							userAddress);

				} else if (user.getFlag().equals("3") || user.getFlag().equals("4")) {// 信息员
					list = app_dao.otherGetNumberMessage(user.getId(),
							user.getCustomer_id());
				}
		}
		return list;
	}

	@Override
	public List<Map<String, Integer>> getReverseNumberMessage(User user) {
		List<Map<String, Integer>> list = new ArrayList<Map<String, Integer>>();
		if (user != null) {
			if (user.getFlag().equals("0")) {// 管理员
				list = app_dao.getReverseNumberMessage(user.getId());

			} else if (user.getFlag().equals("1")) {// 司机
				list = app_dao.driverGetReverseNumberMessage(user.getId(),
						user.getDriver_id());

			} else if (user.getFlag().equals("2")) {// 供应商
				List<String> userAddress = rms_dao.getAddressSupperlies(user
						.getSuppliers_id());
				list = app_dao.supplierGetReverseNumberMessage(user.getId(),
						user.getDriver_id(), user.getSuppliers_id(),
						userAddress);

			} else if (user.getFlag().equals("3") || user.getFlag().equals("4")) {// 信息员
				list = app_dao.otherGetReverseNumberMessage(user.getId(),
						user.getCustomer_id());
			}
		}
		return list;
	}


	@Override
	public List<Map<String, Integer>> appGetMessageCount(User user) {
		List<Map<String, Integer>> list = new ArrayList<Map<String, Integer>>();
		if (user != null) {
			if (user.getFlag().equals("0")) {// 管理员
				list = app_dao.getMessageCount(user.getId());

			} else if (user.getFlag().equals("1")) {// 司机
				list = app_dao.driverGetMessageCount(user.getId(),
						user.getDriver_id());

			} else if (user.getFlag().equals("2")) {// 供应商
				List<String> userAddress = rms_dao.getAddressSupperlies(user
						.getSuppliers_id());
				list = app_dao.supplierGetMessageCount(user.getId(),
						user.getDriver_id(), user.getSuppliers_id(),
						userAddress);

			} else if (user.getFlag().equals("3") || user.getFlag().equals("4")) {// 信息员
				list = app_dao.customerGetMessageCount(user.getId(),
						user.getCustomer_id());
			}


		}
		return list;
	}

	public int appUpdatePersonInfo(User user, String phone) {
		int i = 0;
		if (user != null) {

			if (user.getFlag().equals("0")) {// 管理员

			} else if (user.getFlag().equals("1")) {// 司机
				i = app_dao.appUpdateDriverTel(user.getDriver_id(), phone);

			} else if (user.getFlag().equals("2")) {// 供应商
				i = app_dao.appUpdateSupplersTel(user.getSuppliers_id(), phone);
				app_dao.appUpdateDriverTel(user.getDriver_id(), phone);

			} else if (user.getFlag().equals("3") || user.getFlag().equals("4")) {// 项目部
				i = app_dao.appUpdateCustomerTel(user.getCustomer_id(), phone);
			}

		}
		return i;
	}

	// APP 发送信息保存
	public int appSaveMessageInfo(List<MsgModel> lModels) {

		return app_dao.appSaveMessageInfo(lModels);
	}

	// 查询gps 设备表是否存用户姓名
	public int getGpsInfoCount(String username) {

		return app_dao.getGpsInfoCount(username);
	}

	// app 获取定位时间
	public AppVersion appGetLocationTime() {

		return app_dao.appGetLocationTime();
	}

	//判断用户是否存在GPS
	public int appDriverISHaveGps(String driverId) {
		
		return app_dao.appDriverISHaveGps(driverId);
	}

	//根据司机id 获取用户名
	public String driverGetUserName(String driverId) {
		
		return app_dao.driverGetUserName(driverId);
	}

	public JSONObject appSearchShipperOrderInfoGroupByRole(int rows, int page,
			String name, String start_date, String end_date,
			String shipping_order_state, String custom_name, String receipt,
			String address, String shipperorder_id, User user) {
		JSONObject json = new JSONObject();
		if("0".equals(user.getFlag())){
			
		}else if("1".equals(user.getFlag())){
			
		}else if("2".equals(user.getFlag())){
			
		}else if("3".equals(user.getFlag())||"4".equals(user.getFlag())){
			
		}
	
		
		return json;
	}

	@Override
	public List<ReverserOrderModel> appGetTakingReverseOrderInfo(User user, Integer start, Integer length, String send_time, String end_time, String end_address, String send_mechanism, String custom_contact, String order_type) {
		List<ReverserOrderModel> list=new ArrayList<ReverserOrderModel>();
		if("0".equals(user.getFlag())){
			list = app_dao.appGetTakingOrderListInfo(start, length, send_time, end_time, end_address, send_mechanism, custom_contact, order_type);
		}else if("1".equals(user.getFlag())){
			list = app_dao.appDriverGetTakingOrderListInfo(start, length, user.getDriver_id(),send_time, end_time, end_address, send_mechanism, custom_contact, order_type);

		}else if("2".equals(user.getFlag())){
			list = app_dao.appDriverGetTakingOrderListInfo(start, length, user.getDriver_id(),send_time, end_time, end_address, send_mechanism, custom_contact, order_type);

		}else if("3".equals(user.getFlag())||"4".equals(user.getFlag())){
		}
		return list;
	}

	@Override
	public List<ReverserOrderModel> appGetSignReverseOrderInfo(User user, Integer start, Integer length, String send_time, String end_time, String shiping_order_num, String shipping_order_state, String order_type, String send_mechanism, String end_address, String end_mechanism) {
		List<ReverserOrderModel> list=new ArrayList<ReverserOrderModel>();
		if("0".equals(user.getFlag())){
			list = app_dao.appGetSignReverseOrderInfo(start, length, send_time, end_time, shiping_order_num, shipping_order_state, order_type, send_mechanism, end_address, end_mechanism);

		}else if("1".equals(user.getFlag())){

		}else if("2".equals(user.getFlag())){

		}else if("3".equals(user.getFlag())||"4".equals(user.getFlag())){
			list = app_dao.customerAppGetSignReverseOrderInfo(start, length, send_time, end_time, shiping_order_num, shipping_order_state, order_type, send_mechanism, end_address, end_mechanism,user.getCustomer_id());

		}

		return list;
	}

	@Override
	public List<String> appGetUpdateMessage() {

		return app_dao.appGetUpdateMessage();
	}

	@Override
	public Map<String, Object> getSignReverseOrderInfo(User user, Integer start, Integer length, String send_time, String end_time, String shiping_order_num, String shipping_order_state, String order_type, String send_mechanism, String end_address, String end_mechanism) {
		Map<String, Object> map = new HashMap<String, Object>();
		int noSignCount=0;
		int signCount=0;
		List<ReverserOrderModel> list=new ArrayList<ReverserOrderModel>();
		if (user != null) {
			if (user.getFlag().equals("0")) {// 管理员
				noSignCount = reverseOrderDao.getSignReverserOrderCount(send_time, end_time, shiping_order_num, "0", order_type, send_mechanism, end_address, end_mechanism);
				signCount = reverseOrderDao.getSignReverserOrderCount(send_time, end_time, shiping_order_num, "4", order_type, send_mechanism, end_address, end_mechanism);

				list = reverseOrderDao.getSignReverserOrderModel(start, length, send_time, end_time, shiping_order_num, shipping_order_state, order_type, send_mechanism, end_address, end_mechanism);
			} else if (user.getFlag().equals("1")) {// 司机
				noSignCount = reverseOrderDao.driverGetSignReverserOrderCount(send_time, end_time, shiping_order_num, "0", order_type, send_mechanism, end_address, end_mechanism, user.getDriver_id());
				signCount = reverseOrderDao.driverGetSignReverserOrderCount(send_time, end_time, shiping_order_num, "4", order_type, send_mechanism, end_address, end_mechanism,user.getDriver_id());
				list = reverseOrderDao.driverGetSignReverserOrderModel(start, length, send_time, end_time, shiping_order_num, shipping_order_state, order_type, send_mechanism, end_address, end_mechanism,user.getDriver_id());
			} else if (user.getFlag().equals("2")) {// 供应商
				noSignCount = reverseOrderDao.supplierGetSignReverserOrderCount(send_time, end_time, shiping_order_num, "0", order_type, send_mechanism, end_address, end_mechanism, user.getSuppliers_id());
				signCount = reverseOrderDao.supplierGetSignReverserOrderCount(send_time, end_time, shiping_order_num, "4", order_type, send_mechanism, end_address, end_mechanism, user.getSuppliers_id());

				list = reverseOrderDao.supplierGetSignReverserOrderModel(start, length, send_time, end_time, shiping_order_num, shipping_order_state, order_type, send_mechanism, end_address, end_mechanism,user.getSuppliers_id());
			} else if (user.getFlag().equals("3") || user.getFlag().equals("4")) {// 信息员
				noSignCount = reverseOrderDao.customerGetSignReverserOrderCount(send_time, end_time, shiping_order_num, "0", order_type, send_mechanism, end_address, end_mechanism, user.getCustomer_id());
				signCount = reverseOrderDao.customerGetSignReverserOrderCount(send_time, end_time, shiping_order_num, "4", order_type, send_mechanism, end_address, end_mechanism,user.getCustomer_id());
				list = reverseOrderDao.customerGetSignReverserOrderModel(start, length, send_time, end_time, shiping_order_num, shipping_order_state, order_type, send_mechanism, end_address, end_mechanism, user.getCustomer_id());
			}
		}
		map.put("list", list);
		map.put("noSignCount", noSignCount);
		map.put("signCount",signCount);
		return map;
	}

	@Override
	public boolean getIsAbnormalReportCount(String orderId) {
		int i = app_dao.getIsAbnormalReportCount(orderId);
		return i==0;
	}
}
