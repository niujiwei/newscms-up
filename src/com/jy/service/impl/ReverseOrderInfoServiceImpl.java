package com.jy.service.impl;

import java.text.DateFormat;
import java.util.*;

import javax.annotation.Resource;

import com.jy.action.ReverseOrderInfoController;
import com.jy.common.DateUtils;
import com.jy.common.SpringContextHolder;
import com.jy.common.ValidateUntil;
import com.jy.dao.RemarkMapDao;
import com.jy.model.*;
import com.jy.service.DriverInfoService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.jy.common.UUIDUtils;
import com.jy.dao.ReverseOrderDao;
import com.jy.service.ReverseOrderInfoService;

@Service("ReverseOrderInfoService")
public class ReverseOrderInfoServiceImpl implements ReverseOrderInfoService {
    @Resource
    private ReverseOrderDao so_dao;
    @Resource
    private RemarkMapDao rms_dao;

    @Override
    public Map<String, Object> getReverseOrderInfo(User user, Integer start, Integer length, String send_time, String end_time, String shiping_order_num, String end_address,
                                                   String end_mechanism, String custom_contact, String order_type, String receipt_contact, String send_mechanism) {
        Map<String, Object> map = new HashMap<String, Object>();
        int count = 0;
        List<ReverserOrderModel> list = new ArrayList<ReverserOrderModel>();
        if (user != null) {
            if (user.getFlag().equals("0")) {// 管理员
                count = so_dao.getReverserOrderModelCount(send_time, end_time, shiping_order_num, end_address, end_mechanism, custom_contact, order_type, receipt_contact, send_mechanism);
                list = so_dao.getReverserOrderModelInfo(start, length, send_time, end_time, shiping_order_num, end_address, end_mechanism, custom_contact, order_type, receipt_contact, send_mechanism);
            } else if (user.getFlag().equals("1")) {// 司机
                count = so_dao.driverGetReverserOrderModelCount(send_time, end_time, shiping_order_num, end_address, end_mechanism, custom_contact, order_type, receipt_contact, send_mechanism, user.getDriver_id());
                list = so_dao.driverGetReverserOrderModelInfo(start, length, send_time, end_time, shiping_order_num, end_address, end_mechanism, custom_contact, order_type, receipt_contact, send_mechanism, user.getDriver_id());
            } else if (user.getFlag().equals("2")) {// 供应商
                count = so_dao.supplierGetReverserOrderModelCount(send_time, end_time, shiping_order_num, end_address, end_mechanism, custom_contact, order_type, receipt_contact, send_mechanism, user.getSuppliers_id());
                list = so_dao.supplierGetReverserOrderModelInfo(start, length, send_time, end_time, shiping_order_num, end_address, end_mechanism, custom_contact, order_type, receipt_contact, send_mechanism, user.getSuppliers_id());
            } else if (user.getFlag().equals("3") || user.getFlag().equals("4")) {// 信息员
                count = so_dao.customerGetReverserOrderModelCount(send_time, end_time, shiping_order_num, end_address, end_mechanism, custom_contact, order_type, receipt_contact, send_mechanism, user.getCustomer_id());
                list = so_dao.customerGetReverserOrderModelInfo(start, length, send_time, end_time, shiping_order_num, end_address, end_mechanism, custom_contact, order_type, receipt_contact, send_mechanism, user.getCustomer_id());

            }
        }


        map.put("total", count);
        map.put("rows", list);
        return map;
    }

    @Override
    public boolean addReverseOrderInfo(User user, ReverserOrderModel reverserOrderModel) {
        if (user == null) return false;
        reverserOrderModel.setGoods_num( ValidateUntil.isNumber(reverserOrderModel.getGoods_num())?reverserOrderModel.getGoods_num():"0");
        reverserOrderModel.setReal_num(ValidateUntil.isNumber(reverserOrderModel.getReal_num())?reverserOrderModel.getReal_num():"0");

        reverserOrderModel.setShiping_order_id(UUIDUtils.getUUID());
        reverserOrderModel.setShipping_order(user.getUsername());//制单人
        reverserOrderModel.setCreate_type(user.getFlag());//创建类型
        reverserOrderModel.setSend_time(reverserOrderModel.getSend_time()==null||reverserOrderModel.getSend_time().equals("")?DateUtils.format(new Date(), "yyyy-MM-dd"):reverserOrderModel.getSend_time());
        if (user.getFlag().equals("1") || user.getFlag().equals("2")) {//司机信息

            Driver driver = so_dao.getDriverSupplierId(user.getDriver_id());
            if (driver == null) return false;
            user.setSuppliers_id(driver.getDriver_suppliers());
            reverserOrderModel.setEnd_address((driver.getDriver_address()==null)?"":driver.getDriver_address().split(",")[0]);
            reverserOrderModel.setDriver_id(user.getDriver_id());
            reverserOrderModel.setSuppliers_id(user.getSuppliers_id());
            reverserOrderModel.setShipping_order_state(2);//运单状态
            reverserOrderModel.setSend_station(reverserOrderModel.getEnd_address());
            int type = reverserOrderModel.getOrder_type();
            if(type==1){
                ReverseOrderInfoController reverseOrderInfoController =SpringContextHolder.getBean("reverseOrderInfoController");
                reverseOrderInfoController.saveShippingOrder(reverserOrderModel);
            }
        } else if (user.getFlag().equals("3") || user.getFlag().equals("4")) {
            Customer customer = so_dao.getCustomer(user.getCustomer_id());
            reverserOrderModel.setSend_mechanism(customer.getCustomer_name());
            reverserOrderModel.setShipping_order_state(0);//运单状态
            reverserOrderModel.setRemarks("");//备注
            reverserOrderModel.setSend_station(customer.getCustomer_address());//起运地
            reverserOrderModel.setRemarks_order(reverserOrderModel.getRemarks());//项目部备注
            reverserOrderModel.setUpdatetime(DateUtils.format(new Date(), "yyyy-MM-dd hh:mm:ss"));
        }

        int i = so_dao.insertReverseOrderInfo(reverserOrderModel);
        return i > 0;
    }

    public boolean saveReverseOrderHistory(OrderHistory orderHistory) {
        int i = so_dao.saveReverserOrderHistory(orderHistory);
        return i > 0;
    }

    @Override
    public boolean saveDistributionReverseOrderInfo(String driverId, String[] orders) {
        int r = 0;
        if (orders != null) {
            List<DriverToOrder> orderList = new ArrayList<DriverToOrder>();
            List<OrderHistory> historyList = new ArrayList<OrderHistory>();
            List<String> orderIds = Arrays.asList(orders);
            DriverToOrder order;
            OrderHistory orderHistory;
            DriverInfoService driverInfoService = SpringContextHolder.getBean("DriverInfoService");
            Driver driver = driverInfoService.getUpdateDriver(driverId);
            String driverName =driver==null?"":driver.getDriver_name();
            String supplierName=driver==null?"":driver.getDriver_suppliersname();
            for (int i = 0; i < orders.length; i++) {
                order = new DriverToOrder();
                order.setId(UUIDUtils.getUUID());
                order.setDriver_id(driverId);
                order.setOrder_id(orders[i]);
                orderList.add(order);
                ReverserOrderModel reverserOrderModel= getReverseOrderInfoByIds(orders[i]);
                String orderNum= reverserOrderModel==null?"":reverserOrderModel.getShiping_order_num();
                orderHistory = createOrderHistory(orders[i],orderNum,"供应商:"+supplierName+"已分配给司机"+driverName+"提货");
                historyList.add(orderHistory);
            }
            boolean b = this.saveDistributionReverseOrder(orderList);

            if (b) {
                this.saveReverserOrderHistoryList(historyList);
            }
            r = this.updateReverserOrderState(orderIds, driverId, 1);
        }
        return r > 0;
    }

    @Override
    public boolean distributionReverseOrder(List<String> orderIds, String driverId) {
        if (orderIds == null || orderIds.size() == 0) return false;
        List<DriverToOrder> list = new ArrayList<DriverToOrder>();
        DriverToOrder driverToOrder;
        for (String order : orderIds
                ) {
            driverToOrder = new DriverToOrder();
            driverToOrder.setId(UUIDUtils.getUUID());
            driverToOrder.setDriver_id(driverId);
            driverToOrder.setOrder_id(order);
            list.add(driverToOrder);
        }
        int i = so_dao.saveDistributionReverserOrderInfo(list);
        return i > 0;
    }

    @Override
    public boolean takingReverserOrder(String orderId, String driverId, String remark, Integer state) {
        int i = so_dao.updateReverserOrderTaking(orderId, driverId, remark, state);
        return i > 0;
    }

    @Override
    public boolean updateSaveReverseImage(ShipperOrderImg shipperOrderImg) {
        shipperOrderImg = shipperOrderImg == null ? new ShipperOrderImg() : shipperOrderImg;
        int i = so_dao.saveReverserOrderTakingImage(shipperOrderImg);
        return i > 0;
    }

    @Override
    public boolean updateReverserOrder(ReverserOrderModel reverserOrderModel) {
        int i = so_dao.updateReverserOrderInfo(reverserOrderModel);
        return i > 0;
    }

    @Override
    public boolean saveSignReverserOrderInfo(Sign_order sign_order) {
        int i = so_dao.saveSignReverserOrderInfo(sign_order);
        return i > 0;
    }

    @Override
    public boolean saveSignReverserOrderImage(ShipperOrderImg shipperOrderImg) {
        int i = so_dao.saveSignReverserOrderImage(shipperOrderImg);
        return i > 0;
    }

    @Override
    public Map<String, Object> getDistributionReverseOrderInfo(User user, Integer start, Integer length, String send_time, String end_time,
                                                               String end_address, String send_mechanism, String custom_contact, String order_type) {
        Map<String, Object> map = new HashMap<String, Object>();
        int count=0;
        List<ReverserOrderModel> list=new ArrayList<ReverserOrderModel>();
        if (user != null) {
            if (user.getFlag().equals("0")) {// 管理员
                count = so_dao.getDistributionReverserOrderModelCount(send_time, end_time, end_address, send_mechanism, custom_contact, order_type);
                list = so_dao.getDistributionReverserOrderModel(start, length, send_time, end_time, end_address, send_mechanism, custom_contact, order_type);
            } else if (user.getFlag().equals("1")) {// 司机

            } else if (user.getFlag().equals("2")) {// 供应商
                List<String> userAddress = rms_dao.getAddressSupperlies(user
                        .getSuppliers_id());
                count = so_dao.supplierGetDistributionReverserOrderCount(send_time, end_time, end_address, send_mechanism, custom_contact, order_type, user.getSuppliers_id(),userAddress);

                list = so_dao.supplierGetDistributionReverserOrderModel(start, length, send_time, end_time, end_address, send_mechanism, custom_contact, order_type,user.getSuppliers_id(),userAddress);
            } else if (user.getFlag().equals("3") || user.getFlag().equals("4")) {// 信息员

            }
        }


        map.put("total", count);
        map.put("rows", list);
        return map;
    }

    @Override
    public boolean saveDistributionReverseOrder(List<DriverToOrder> list) {
        int i = so_dao.saveDistributionReverserOrderInfo(list);
        return i > 0;
    }

    @Override
    public int saveReverserOrderHistoryList(List<OrderHistory> list) {
        if (list.size()==0) return 0;
        return so_dao.saveReverserOrderHistoryList(list);
    }

    @Override
    public int updateReverserOrderState(List<String> ids, String driverId, Integer state) {
        Driver driver = so_dao.getDriverSupplierId(driverId);
        String supplierId = driver == null ? "" : driver.getDriver_suppliers();
        return so_dao.updateReverserOrderState(ids, state, supplierId, driverId);
    }

    @Override
    public Map<String, Object> getTakingReverseOrderInfo(User user, Integer start, Integer length, String driverId, String send_time, String end_time,
                                                         String end_address, String send_mechanism, String custom_contact, String order_type) {
        Map<String, Object> map = new HashMap<String, Object>();
        int count;
        List<ReverserOrderModel> list;
        count = so_dao.getTakingReverserOrderModelCount(driverId, send_time, end_time, end_address, send_mechanism, custom_contact, order_type);
        list = so_dao.getTakingReverserOrderModel(start, length, driverId, send_time, end_time, end_address, send_mechanism, custom_contact, order_type);
        map.put("total", count);
        map.put("rows", list);
        return map;
    }

    @Override
    public boolean cancelDistributionReverseOrder(String driverId, List<String> orderIds) {

        int i = so_dao.deleteDistributionReverseOrder(orderIds, driverId);

        return i > 0;
    }

    @Override
    public ReverserOrderModel getReverseOrderInfoByIds(String id) {

        return so_dao.getReverserOrderModel(id);
    }

    @Override
    public int batchSaveTakingReverseImage(List<ShipperOrderImg> shipperOrderImages) {
        return so_dao.batchSaveTakingReverserOrderImage(shipperOrderImages);
    }

    @Override
    public Map<String, Object> getSignReverseOrderInfo(User user, Integer start, Integer length, String send_time, String end_time, String shiping_order_num, String shipping_order_state, String order_type, String send_mechanism, String end_address, String end_mechanism) {
        Map<String, Object> map = new HashMap<String, Object>();
        int count=0;
        List<ReverserOrderModel> list=new ArrayList<ReverserOrderModel>();
        if (user != null) {
            if (user.getFlag().equals("0")) {// 管理员
                count = so_dao.getSignReverserOrderCount(send_time, end_time, shiping_order_num, shipping_order_state, order_type, send_mechanism, end_address, end_mechanism);
                list = so_dao.getSignReverserOrderModel(start, length, send_time, end_time, shiping_order_num, shipping_order_state, order_type, send_mechanism, end_address, end_mechanism);
            } else if (user.getFlag().equals("1")) {// 司机
                count = so_dao.driverGetSignReverserOrderCount(send_time, end_time, shiping_order_num, shipping_order_state, order_type, send_mechanism, end_address, end_mechanism,user.getDriver_id());
                list = so_dao.driverGetSignReverserOrderModel(start, length, send_time, end_time, shiping_order_num, shipping_order_state, order_type, send_mechanism, end_address, end_mechanism,user.getDriver_id());
            } else if (user.getFlag().equals("2")) {// 供应商
                count = so_dao.supplierGetSignReverserOrderCount(send_time, end_time, shiping_order_num, shipping_order_state, order_type, send_mechanism, end_address, end_mechanism, user.getSuppliers_id());
                list = so_dao.supplierGetSignReverserOrderModel(start, length, send_time, end_time, shiping_order_num, shipping_order_state, order_type, send_mechanism, end_address, end_mechanism,user.getSuppliers_id());
            } else if (user.getFlag().equals("3") || user.getFlag().equals("4")) {// 信息员
                count = so_dao.customerGetSignReverserOrderCount(send_time, end_time, shiping_order_num, shipping_order_state, order_type, send_mechanism, end_address, end_mechanism, user.getCustomer_id());
                list = so_dao.customerGetSignReverserOrderModel(start, length, send_time, end_time, shiping_order_num, shipping_order_state, order_type, send_mechanism, end_address, end_mechanism, user.getCustomer_id());
            }
        }
        map.put("total", count);
        map.put("rows", list);
        return map;
    }

    @Override
    public int saveReverseOrderHistory(ReverserOrderModel reverserOrderModel) {
        OrderHistory orderHistory = new OrderHistory();
        orderHistory.setOrder_history_id(UUIDUtils.getUUID());
        orderHistory.setOrders_id(reverserOrderModel.getShiping_order_id());
        orderHistory.setOrder_arrive_time(DateFormat.getDateTimeInstance().format(new Date()));
        orderHistory.setGoods_arrive_remakes(orderHistory.getOrder_arrive_time()+"---"+reverserOrderModel.getShiping_order_num() + "运单录入");
        return so_dao.saveReverserOrderHistory(orderHistory);
    }

    private OrderHistory createOrderHistory(String orderId,String orderNum,String message){
        OrderHistory orderHistory = new OrderHistory();
        orderHistory.setOrder_history_id(UUIDUtils.getUUID());
        orderHistory.setOrders_id(orderId);
        orderHistory.setOrder_arrive_time(DateFormat.getDateTimeInstance().format(new Date()));
        orderHistory.setGoods_arrive_remakes(orderHistory.getOrder_arrive_time()+"---"+"货运编号:"+orderNum +" "+ message);
        return orderHistory;
    }

    @Override
    public int saveReverseOrderHistory(ReverserOrderModel reverserOrderModel,String message, User user) {
        OrderHistory orderHistory =createOrderHistory(reverserOrderModel.getShiping_order_id(),reverserOrderModel.getShiping_order_num(),message);
        return so_dao.saveReverserOrderHistory(orderHistory);
    }

    @Override
    public int saveReverserOrderCityInfo(ReverserOrderModel reverserOrderModel) {
        return so_dao.saveReverserOrderCityInfo(reverserOrderModel);
    }

    @Override
    public int updateReverseOrderCityInfo(ReverserOrderModel reverserOrderModel) {
        return so_dao.updateReverseOrderCityInfo(reverserOrderModel);
    }

    @Override
    public boolean deleteReverserOrderInfo(List<String> list) {
        if (list == null || list.size() == 0) return false;
        int i = so_dao.deleteReverserOrderInfo(list);
        if (i > 0) so_dao.deleteReverserOrderCityInfo(list);
        return i > 0;
    }

    @Override
    public boolean checkReverseOrderInfoById(String orderId) {
        int i = so_dao.checkReverseOrderInfoById(orderId);
        return i == 0;
    }

    @Override
    public boolean updateSignReverseOrderInfo(Sign_order sign_order) {
        int i = so_dao.updateSignReverseOrder(sign_order);
        return i > 0;
    }

    @Override
    public boolean updateReverseOrderState(@Param("orderId") String orderId, @Param("state") String state) {
        int i = so_dao.updateReverseOrderState(orderId, state);
        return i > 0;
    }

    @Override
    public boolean saveSignReverseOrderImage(List<ShipperOrderImg> list) {
        int i = so_dao.saveSignReverseOrderImage(list);
        return i > 0;
    }

    @Override
    public ReverserOrderModel getSignReverseOrderById(String orderId) {

        return so_dao.getSignReverseOrderInfo(orderId);
    }

    @Override
    public List<ShipperOrderImg> getSingReverseOrderImg(String orderId) {
        return so_dao.getSingReverseOrderImg(orderId);
    }

    @Override
    public List<ShipperOrderImg> getTakingReverseOrderImg(String orderId) {
        return so_dao.getTakingReverseOrderImg(orderId);
    }

    @Override
    public List<OrderHistory> getReverseOrderHistory(String orderId) {

        return so_dao.getReverseOrderHistory(orderId);
    }

    @Override
    public List<ReverserOrderModel> getReverseOrderByIds(List<String> ids) {
        if (ids==null||ids.size()==0) return new ArrayList<ReverserOrderModel>();
        return so_dao.getReverseOrderByIds(ids);
    }


    @Override
    public List<ReverserOrderModel> getSignReverseOrderByIds(List<String> ids) {

        return so_dao.getSignReverseOrderByIds(ids);
    }

    @Override
    public int batchInsertReverseOrderInfo(List<ReverserOrderModel> list) {
        if (list.size()==0) return 0;
        return so_dao.batchInsertReverseOrderInfo(list);
    }

    @Override
    public boolean getReverseOrderCountByNum(String shiping_order_num, String shiping_order_goid) {
        int i = so_dao.getReverseOrderCountByNum(shiping_order_num,shiping_order_goid);
        return i==0;
    }

    public static String insertMessage(User user,ReverserOrderModel reverserOrderModel){

        String message = "";
        String orderType=reverserOrderModel.getOrder_type()==1?"调拨":"退货";
        if (user.getFlag().equals("0")) {
            message="管理员-"+user.getRealName()+"逆向"+orderType+"指令信息录入";
        }
        if (user.getFlag().equals("1")) {
            message="司机-"+user.getRealName()+"逆向"+orderType+"运单信息录入";
        }
        if (user.getFlag().equals("2")) {
            message="供应商-"+user.getRealName()+"逆向"+orderType+"运单信息录入";
        }
        if (user.getFlag().equals("3")||user.getFlag().equals("4")) {
            message="项目部-"+user.getRealName()+"逆向"+orderType+"指令信息录入";
        }
        return message;
    }
}