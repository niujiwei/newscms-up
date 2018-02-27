package com.jy.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jy.common.UUIDUtils;
import com.jy.dao.RemarkMapDao;
import com.jy.dao.ShippingOrderDao;
import com.jy.model.Customer;
import com.jy.model.Driver;
import com.jy.model.MsgModel;
import com.jy.model.OrderHistory;
import com.jy.model.ShipperOrderImg;
import com.jy.model.ShippingOrder;
import com.jy.model.Sign_order;
import com.jy.model.User;
import com.jy.service.ShippingOrderInfoService;

@Service("ShippingOrderInfoService")
public class ShippingOrderInfoServiceImpl implements ShippingOrderInfoService {
    @Resource
    private ShippingOrderDao so_dao;
    @Resource
    private RemarkMapDao rms_dao;

    public List<ShippingOrder> getCustomerReport(String send_time,
                                                 String shiping_order_num, String send_mechanism,
                                                 String end_address, String custom_name, String receipt_name,
                                                 String receipt_tel) {
        List<ShippingOrder> list = so_dao.getCustomerReport(send_time,
                shiping_order_num, send_mechanism, end_address, custom_name,
                receipt_name, receipt_tel);
        return list;
    }

    public ShippingOrder getCustomerReportPage(String send_time,
                                               String shiping_order_num, String send_mechanism,
                                               String end_address, String custom_name, String receipt_name,
                                               String receipt_tel) {
        ShippingOrder list = so_dao.getCustomerReportPage(send_time,
                shiping_order_num, send_mechanism, end_address, custom_name,
                receipt_name, receipt_tel);
        return list;
    }

    public List<ShippingOrder> getPlateNumberLength(String number) {
        List<ShippingOrder> list = so_dao.getPlateNumberLength(number);
        return list;
    }

    public List<ShippingOrder> getShipOrderAll(String name,
                                               String phone_number, String type, String start_date, String end_date) {
        List<ShippingOrder> list = so_dao.getShipOrderAll(name, phone_number,
                type, start_date, end_date);
        return list;
    }

    // 重复订单号
    public ShippingOrder getNumber(String number, String shipordernum) {
        ShippingOrder der = so_dao.getNumber(number, shipordernum);
        return der;
    }

    public List<ShippingOrder> getSignShipOrder1(int rows, int page,
                                                 String name, String phone_number, String type, String start_date,
                                                 String end_date, String did, String shipping_order_state) {
        List<ShippingOrder> list = so_dao.getSignShipOrder1(rows, page, name,
                phone_number, type, start_date, end_date, did,
                shipping_order_state);
        return list;
    }

    public int getSignShipOrdercount1(String name, String phone_number,
                                      String type, String start_date, String end_date, String did,
                                      String shipping_order_state) {
        int count = so_dao.getSignShipOrdercount1(name, phone_number, type,
                start_date, end_date, did, shipping_order_state);
        return count;
    }

    // 运单签收保存签收信息
    public int saveSignShipOrder(Sign_order sign_order) {
        int count = 0;
        try {
            // String sid = UUIDUtils.getUUID();
            count = so_dao.saveSignShipOrder(sign_order);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    // 运单签收 保存运单签收状态
    public int updatestate(String shiping_order_id) {
        int count = 0;
        count = so_dao.appupdatestate(shiping_order_id);
        return count;
    }

    // 协议制作订单查询方法---没有用
    public int getShipOrderCA(String id, String startDate, String end_date,
                              String ddId, String order_state, String pay_type, String perpole,
                              String end_address, String send_address, String phone_number) {
        int i = so_dao.getShipOrderCA(id, startDate, end_date, ddId,
                order_state, pay_type, perpole, end_address, send_address,
                phone_number);
        return i;
    }

    // 协议制作订单查询方法---没有用
    public List<ShippingOrder> getShipOrderInfoCA(int rows, int page,
                                                  String id, String startDate, String end_date, String ddId,
                                                  String order_state, String pay_type, String perpole,
                                                  String end_address, String send_address, String phone_number) {
        List<ShippingOrder> list = so_dao.getShipOrderInfoCA(rows, page, id,
                startDate, end_date, ddId, order_state, pay_type, perpole,
                end_address, send_address, phone_number);
        return list;
    }

    // 运单签收获取签收信息
    public ShippingOrder getUpdateSignShipOrder(String id) {
        ShippingOrder sp = so_dao.getUpdateSignShipOrder(id);

        return sp;
    }

    // 制作协议订单的查询
    public List<ShippingOrder> getShipOrderCAM(int rows, int page, String[] pid) {
        List<ShippingOrder> list = so_dao.getShipOrderCAM(rows, page, pid);
        return list;
    }

    /**
     * 删除与需求相关的低订单需求
     */
    public int deleteShipToDemand(String[] del) {
        int i = so_dao.deleteShipToDemand(del);
        return i;
    }

    /**
     * 导入
     *
     * @param tlist
     * @return
     */
    public int saveimp(List<ShippingOrder> tlist) {

        int i = so_dao.saveShipOrders(tlist);
        return i;
    }

    /**
     * 图片路径上传
     */
    public int saveSignImages(String uuid, String order_id, String paths) {

        int i = so_dao.saveSignImages(uuid, order_id, paths);
        return i;
    }

    /**
     * 获取司机的订单
     */
    public List<ShippingOrder> getCarShipOrder(String car_id) {

        List<ShippingOrder> der = so_dao.getCarShipOrder(car_id);
        return der;
    }

    // 输出一维码
    public List<ShippingOrder> getUpdateShipOrderArray(String[] checkarray) {

        List<ShippingOrder> sp = so_dao.getUpdateShipOrderArray(checkarray);
        return sp;
    }

    /**
     * 查询扫描信息
     */
    public ShippingOrder getAppShipOrder(String string) {

        ShippingOrder sp = so_dao.getAppShipOrder(string);
        return sp;
    }

    public int getAorder(String number) {
        int i = so_dao.getAorder(number);
        return i;
    }

    // 到达
    public int isArrive(String[] del) {
        int i = so_dao.isArrive(del);
        return i;
    }

    // 取消到达
    public int isNotArrive(String[] del) {
        int i = so_dao.isNotArrive(del);
        return i;
    }

    public int sendMsgModelInfo(String msgTitle, String msgContent) {

        MsgModel mm = new MsgModel();
        mm.setModel_content(msgContent);
        mm.setModel_id(UUIDUtils.getUUID());
        mm.setModel_title(msgTitle);
        int flag = so_dao.sendMsgModelInfo(mm);
        return flag;
    }

    public List<MsgModel> getMsgModel() {

        List<MsgModel> list = so_dao.getMsgModel();
        return list;
    }

    public String sendMsgContent(String msgid) {

        String senmsg = so_dao.sendMsgContent(msgid);
        return senmsg;
    }

    // 运单签收 查看运签收历史记录
    public List<OrderHistory> getShowOrder(String pid) {

        return so_dao.getShowOrder(pid);
    }

    /**
     * 订单历史修改记录
     *
     * @param d
     * @return
     */
    public int saveOrderEHistory(ShippingOrder d) {
        int i = so_dao.saveOrderEHistory(d);
        return i;
    }

    /**
     * 删除订单修改信息
     */
    public int deleteOrderEH(String[] del) {
        int i = so_dao.deleteOrderEH(del);
        return i;
    }

    // 查询司机分配订单页面
    public List<ShippingOrder> getShipOrderInfoOnePage(int i, Integer rows1,
                                                       String name, String phone_number, String send_mechanism,
                                                       String start_date, String end_date, String pid2, String phone,
                                                       String pid, String shiping_order_goid, String type,
                                                       String end_mechanism, String endTime) {
        return so_dao.getShipOrderInfoOnePage(i, rows1, name, phone_number,
                send_mechanism, start_date, end_date, pid2, phone, pid,
                shiping_order_goid, type, end_mechanism, endTime);
    }

    // 查询司机分配订单页面总页数
    public int getShipOrderOnePage(String send_time, String shiping_order_num,
                                   String send_mechanism, String end_address, String custom_name,
                                   String receipt_name, String

                                           receipt_tel, String driver_id, String shiping_order_goid,
                                   String type, String end_mechanism, String end_time) {

        return so_dao
                .getShipOrderOnePage(send_time, shiping_order_num,
                        send_mechanism, end_address, custom_name, receipt_name,
                        receipt_tel, driver_id, shiping_order_goid, type,
                        end_mechanism, end_time);
    }

    // 分配运单删除分配信息
    public int deleteShipOrderPage(String pid, String[] del) {

        return so_dao.deleteShipOrderPage(pid, del);
    }

    // 客户结算 按照客户分组查询信息
    public List<ShippingOrder> getShipOrderBySendMechanism(int rows,
                                                           Integer page, String start_time, String end_time,
                                                           String custom_name, User user) {
        List<ShippingOrder> list = new ArrayList<ShippingOrder>();
        if (user != null) {
            if (user.getFlag().equals("0")) {// 管理员
                list = so_dao.getShipOrderBySendMechanism(rows, page,
                        start_time, end_time, custom_name);
            } else if (user.getFlag().equals("1")) {// 司机

            } else if (user.getFlag().equals("2")) {// 供应商

            } else if (user.getFlag().equals("3") || user.getFlag().equals("4")) {// 信息员
                list = so_dao.otherGetShipOrderBySendMechanism(rows, page,
                        start_time, end_time, custom_name, user.getId());
            }
        }

        return list;
    }

    // 客户结算 根据客户名称为主查询总数
    public int getShipOrderBySendMechanismTotal(String start_time,
                                                String end_time, String custom_name, User user) {
        int count = 0;
        if (user.getFlag().equals("0")) {// 管理员
            count = so_dao.getShipOrderBySendMechanismTotal(start_time,
                    end_time, custom_name);
        } else if (user.getFlag().equals("1")) {// 司机

        } else if (user.getFlag().equals("2")) {// 供应商

        } else if (user.getFlag().equals("3") || user.getFlag().equals("4")) {// 信息员
            count = so_dao.otherGetShipOrderBySendMechanismTotal(start_time,
                    end_time, custom_name, user.getId());
        }

        return count;
    }

    // 客户结算 详细信息页面数据
    public List<ShippingOrder> getDtailCustomerbilling(int i, Integer rows1,
                                                       String send_time, String shiping_order_num, String send_mechanism,
                                                       String end_address, String custom_name, String receipt_name,
                                                       String receipt_tel, String startTime, String endTime,
                                                       String customCode, String shipperorder_id,
                                                       String customer_settlement_state) {

        return so_dao.getDtailCustomerbilling(i, rows1, send_time,
                shiping_order_num, send_mechanism, end_address, custom_name,
                receipt_name, receipt_tel, startTime, endTime, customCode,
                shipperorder_id, customer_settlement_state);
    }

    public int getDtailCustomerbillingTotal(String send_time,
                                            String shiping_order_num, String send_mechanism,
                                            String end_address, String custom_name, String receipt_name,
                                            String receipt_tel, String startTime, String endTime,
                                            String customCode, String shipperorder_id,
                                            String customer_settlement_state) {
        return so_dao.getDtailCustomerbillingTotal(send_time,
                shiping_order_num, send_mechanism, end_address, custom_name,
                receipt_name, receipt_tel, startTime, endTime, customCode,
                shipperorder_id, customer_settlement_state);
    }

    /**
     * 导出的获取客户
     */
    public List<String> getAllCustom() {

        return so_dao.getAllCustom();
    }

    // 导出获取所有客户信息
    public List<ShippingOrder> getAllMessage(String custom_name,
                                             String minTime, String maxTime) {

        return so_dao.getAllMessage(custom_name, minTime, maxTime);
    }

    // 客户结算 导出获取合计信息
    public ShippingOrder getSumMessage(String custom_name, String minTime,
                                       String maxTime) {

        return so_dao.getSumMessage(custom_name, minTime, maxTime);
    }

    // 客户结算 更新结算状态
    public boolean custom_pay(String customCode, String minTime, String maxTime) {
        int i = so_dao.custom_pay(customCode, minTime, maxTime);
        if (i > 0) {
            return true;
        } else {
            return false;
        }
    }

    // 客户结算返回结算的运单号
    public List<ShippingOrder> selectShippinOrderId(String customCode,
                                                    String minTime, String maxTime) {

        return so_dao.selectShippinOrderId(customCode, minTime, maxTime);
    }

    /**
     * 客户结算
     */
    public int selectCustomerSettlementState(String startTime, String endTime,
                                             String customName) {

        return so_dao.selectCustomerSettlementState(startTime, endTime,
                customName);
    }

    /**
     * 签收时查询是否存在
     */
    public int getSignShipOrder(String shipingordernum) {

        return so_dao.getSignShipOrderNum(shipingordernum);
    }

    /**
     * 签收时存在更新数据
     */
    public int updateSignShipOrderNum(Sign_order signOrder) {

        return so_dao.updateSignShipOrderNum(signOrder);
    }

    // 运单签收 保存图片
    public boolean saveFilename(List<ShipperOrderImg> shiOrdImg) {
        int i = so_dao.saveFilename(shiOrdImg);
        if (i > 0) {
            return true;
        }
        return false;
    }

    // 查询是否有签收信息
    public Sign_order isnotSignOrder(String order_id) {

        return so_dao.isnotSignOrder(order_id);
    }

    public int updateSignShipOrder(Sign_order sign_order) {

        return so_dao.updateSignShipOrder(sign_order);
    }

    public List<ShippingOrder> psDriverPageInfoShipperOrder(String rownum,
                                                            String row, String driver_id, String shipperOrderId,
                                                            String startTime, String endTime, String state,
                                                            String shiping_order_goid, String custom_name, String receipt_name) {

        return so_dao.psDriverPageInfoShipperOrder(rownum, row, driver_id,
                shipperOrderId, startTime, endTime, state, shiping_order_goid,
                custom_name, receipt_name);
    }

    public List<ShippingOrder> psSuppPageInfoShipperOrder(String rownum,
                                                          String row, String supperlis_id, String shipperOrderId,
                                                          String startTime, String endTime, String state,
                                                          String shiping_order_goid, String custom_name, String receipt_name) {

        return so_dao.psSuppPageInfoShipperOrder(rownum, row, supperlis_id,
                shipperOrderId, startTime, endTime, state, shiping_order_goid,
                custom_name, receipt_name);
    }

    public int updateShipperOrderDelete(String[] orderid) {

        return so_dao.updateShipperOrderDelete(orderid);
    }

    // 系统删除已分配订单
    public int deleteShipOrders(String orderid) {

        return so_dao.deleteShipOrders(orderid);
    }

    // 运单录入 删除所有订单信息
    public int deleteAllMessage(String send_time, String shiping_order_num,
                                String send_mechanism, String end_address, String custom_name,
                                String receipt_name, String receipt_tel, String topordernumber,
                                String downordernumber, String end_time) {

        return so_dao.deleteAllMessage(send_time, shiping_order_num,
                send_mechanism, end_address, custom_name, receipt_name,
                receipt_tel, topordernumber, downordernumber, end_time);
    }

    // 运单信息导出
    public List<ShippingOrder> outSignShipOrderFile(User user, String name,
                                                    String start_date, String end_date, String shipping_order_state,
                                                    String custom_name, String receipt, String address,
                                                    String shipperorder_id, String[] dataIds) {
        List<ShippingOrder> list = null;
        if (user != null) {
            if (user.getFlag().equals("0")) {// 管理员
                list = so_dao.outNewSignShipOrderFile(name, start_date,
                        end_date, shipping_order_state, custom_name, receipt,
                        address, shipperorder_id, dataIds);
            } else if (user.getFlag().equals("1")) {// 司机
                list = so_dao.driverOutSignShipOrderFile(name, start_date,
                        end_date, shipping_order_state, custom_name, receipt,
                        address, shipperorder_id, dataIds, user.getDriver_id());

            } else if (user.getFlag().equals("2")) {// 供应商
                List<String> userAddress = rms_dao.getAddressSupperlies(user
                        .getSuppliers_id());
                list = so_dao.supperOutSignShipOrderFile(name, start_date,
                        end_date, shipping_order_state, custom_name, receipt,
                        address, shipperorder_id, dataIds,
                        user.getSuppliers_id(), userAddress);
            } else if (user.getFlag().equals("3") || user.getFlag().equals("4")) {// 信息员
                list = so_dao.otherOutSignShipOrderFile(name, start_date,
                        end_date, shipping_order_state, custom_name, receipt,
                        address, shipperorder_id, dataIds, user.getId());

            }
        }

        return list;
    }

    // 运单签收信息导出存储过程--没有用
    public void callSignShipperOrderProcedure(String userId) {
        so_dao.callSignShipperOrderProcedure(userId);

    }

    // 快速查询
    public List<ShippingOrder> getQuickSearchShiping(String shiping_order_goid,
                                                     String shiping_order_num, String userId) {

        return so_dao.getQuickSearchShiping(shiping_order_goid,
                shiping_order_num, userId);
    }

    // 司机管理分配运单
    public List<ShippingOrder> getAllShipOrderInfoFenPei(int i, Integer rows1,
                                                         String send_time, String shiping_order_num, String send_mechanism,
                                                         String end_address, String custom_name, String receipt_name,
                                                         String receipt_tel, String shipperorder_id, String end_mechanism,
                                                         User user) {
        List<ShippingOrder> list = null;
        if (user != null) {
            if (user.getFlag().equals("0")) {// 管理员
                list = so_dao.getAllShipOrderInfoFenPei(i, rows1, send_time,
                        shiping_order_num, send_mechanism, end_address,
                        custom_name, receipt_name, receipt_tel,
                        shipperorder_id, end_mechanism);
            } else if (user.getFlag().equals("1")) {// 司机

            } else if (user.getFlag().equals("2")) {// 供应商
                List<String> userAddress = rms_dao.getAddressSupperlies(user
                        .getSuppliers_id());
                list = so_dao.getShipOrderInfoFenpei(i, rows1, send_time,
                        shiping_order_num, send_mechanism, end_address,
                        custom_name, receipt_name, receipt_tel,
                        shipperorder_id, end_mechanism, userAddress,
                        user.getSuppliers_id());
            } else if (user.getFlag().equals("3") || user.getFlag().equals("4")) {// 信息员
                list = so_dao.otherShipOrderInfoFenpei(i, rows1, send_time,
                        shiping_order_num, send_mechanism, end_address,
                        custom_name, receipt_name, receipt_tel,
                        shipperorder_id, end_mechanism, "", user.getId());

            }
        }

        return list;
    }

    // 司机管理分配运单总条数
    public int getAllShipOrderInfoFenPeiCount(String send_time,
                                              String shiping_order_num, String send_mechanism,
                                              String end_address, String custom_name, String receipt_name,
                                              String receipt_tel, String shipperorder_id, String end_mechanism,
                                              User user) {
        int count = 0;
        if (user != null) {
            if (user.getFlag().equals("0")) {// 管理员
                count = so_dao.getAllShipOrderInfoFenPeiCount(send_time,
                        shiping_order_num, send_mechanism, end_address,
                        custom_name, receipt_name, receipt_tel,
                        shipperorder_id, end_mechanism);
            } else if (user.getFlag().equals("1")) {// 司机

            } else if (user.getFlag().equals("2")) {// 供应商
                List<String> userAddress = rms_dao.getAddressSupperlies(user
                        .getSuppliers_id());
                count = so_dao.getShipOrderFenpei(send_time, shiping_order_num,
                        send_mechanism, end_address, custom_name, receipt_name,
                        receipt_tel, shipperorder_id, end_mechanism,
                        userAddress, user.getSuppliers_id());

            } else if (user.getFlag().equals("3") || user.getFlag().equals("4")) {// 信息员
                count = so_dao.otherShipOrderFenpei(send_time,
                        shiping_order_num, send_mechanism, end_address,
                        custom_name, receipt_name, receipt_tel,
                        shipperorder_id, end_mechanism, "", user.getId());
            }
        }

        return count;
    }

    // 运单录入查询运单信息
    public List<ShippingOrder> getShipOrderInfo(int rows, Integer page,
                                                String send_time, String shiping_order_num, String send_mechanism,
                                                String end_address, String custom_name, String receipt_name,
                                                String receipt_tel, String topordernumber, String downordernumber,
                                                String shipperorder_id, String end_time, User user) {
        List<ShippingOrder> list = null;
        if (user != null) {
            if (user.getFlag().equals("0")) {// 管理员
                list = so_dao.getShipOrderInfo(rows, page, send_time,
                        shiping_order_num, send_mechanism, end_address,
                        custom_name, receipt_name, receipt_tel, topordernumber,
                        downordernumber, shipperorder_id, end_time);
            } else if (user.getFlag().equals("1")) {// 司机
                list = so_dao.driverGetShipperOrderInfo(rows, page, send_time,
                        shiping_order_num, send_mechanism, end_address,
                        custom_name, receipt_name, receipt_tel, topordernumber,
                        downordernumber, shipperorder_id, end_time,
                        user.getDriver_id());
            } else if (user.getFlag().equals("2")) {// 供应商
                List<String> user_address = rms_dao.getAddressSupperlies(user
                        .getSuppliers_id());
                list = so_dao.suppliersGetShipperOrderInfo(rows, page,
                        send_time, shiping_order_num, send_mechanism,
                        end_address, custom_name, receipt_name, receipt_tel,
                        topordernumber, downordernumber, shipperorder_id,
                        end_time, user_address, user.getSuppliers_id());

            } else if (user.getFlag().equals("3") || user.getFlag().equals("4")) {// 信息员
                list = so_dao.otherGetShipOrderInfo(rows, page, send_time,
                        shiping_order_num, send_mechanism, end_address,
                        custom_name, receipt_name, receipt_tel, topordernumber,
                        downordernumber, shipperorder_id, end_time,
                        user.getId());
            }
        }
        return list;
    }

    // 运单录入运单信息总条数查询
    public int getShipOrder(String send_time, String shiping_order_num,
                            String send_mechanism, String end_address, String custom_name,
                            String receipt_name, String receipt_tel, String topordernumber,
                            String downordernumber, String shipperorder_id, String end_time,
                            User user) {
        int count = 0;
        if (user != null) {
            if (user.getFlag().equals("0")) {// 管理员
                count = so_dao.getShipOrder(send_time, shiping_order_num,
                        send_mechanism, end_address, custom_name, receipt_name,
                        receipt_tel, topordernumber, downordernumber,
                        shipperorder_id, end_time);
            } else if (user.getFlag().equals("1")) {// 司机
                count = so_dao.driverGetShipOrder(send_time, shiping_order_num,
                        send_mechanism, end_address, custom_name, receipt_name,
                        receipt_tel, topordernumber, downordernumber,
                        shipperorder_id, end_time, user.getDriver_id());

            } else if (user.getFlag().equals("2")) {// 供应商
                List<String> user_address = rms_dao.getAddressSupperlies(user
                        .getSuppliers_id());
                count = so_dao.suppliersGetShipOrder(send_time,
                        shiping_order_num, send_mechanism, end_address,
                        custom_name, receipt_name, receipt_tel, topordernumber,
                        downordernumber, shipperorder_id, end_time,
                        user.getSuppliers_id(), user_address);

            } else if (user.getFlag().equals("3") || user.getFlag().equals("4")) {// 信息员
                count = so_dao.otherGetShipOrder(send_time, shiping_order_num,
                        send_mechanism, end_address, custom_name, receipt_name,
                        receipt_tel, topordernumber, downordernumber,
                        shipperorder_id, end_time, user.getId());
            }
        }

        return count;
    }

    // 获取部门id
    public String getDepartmentFatherId(String userId) {
        String user = so_dao.getDepartmentFatherId(userId);
        if (user == null) {
            user = "";
        }
        return user;
    }

    // 获取发货客户信息
    public List<Customer> getCustomName(String number) {
        List<Customer> list = so_dao.getCustomName(number);
        return list;
    }

    // 到达订单历史表存储
    public int saveOrderHistory(List<OrderHistory> order) {
        int i = so_dao.saveOrderHistory(order);
        return i;
    }

    // 确定到达时候修改客户车到时间
    public int isArriveCoustom(String custom_id) {
        return so_dao.isArriveCoustom(custom_id);
    }

    // 取消到达删除数据
    public int deleteOrderHistory(String[] order) {
        int i = so_dao.deleteOrderHistory(order);
        return i;

    }

    // 运单录入删除运单信息
    public int deleteShipOrder(String[] del) {
        int i = so_dao.deleteShipOrder(del);
        return i;
    }

    // 删除司机关联订单
    public int deleteDriverToOrder(String[] del) {
        int i = so_dao.deleteDriverToOrder(del);
        return i;
    }

    // 运单录入修改运单信息
    public int updateShipOrder(ShippingOrder d) {
        int i = so_dao.updateShipOrder(d);
        return i;
    }

    // 运单录入修改运单信息，多修改了个订单状态
    public int updateShipOrders(ShippingOrder d) {
        int i = so_dao.updateShipOrders(d);
        return i;
    }

    // 运单查看获取运单信息
    public ShippingOrder getUpdateShipOrder(String id) {
        ShippingOrder sp = so_dao.getUpdateShipOrder(id);
        return sp;
    }

    // 运单录入 部分信息导出
    public List<ShippingOrder> someMessageOutPut(String[] dataIds) {

        return so_dao.someMessageOutPut(dataIds);
    }

    // 运单录入 保存运单信息
    public int saveShipOrder(ShippingOrder d) {
        int i = so_dao.saveShipOrder(d);
        return i;
    }

    // APP查询待分配运单
    public List<ShippingOrder> suppliersGetShipperOrderInfo(String rownum,
                                                            String row, String supplierid, String shippingOrderId,
                                                            String shiping_order_goid, String custom_name, String receipt_name,
                                                            List<String> userAddress) {

        return so_dao.suppliersGetShipperOrderInfoapp(rownum, row, supplierid,
                shippingOrderId, shiping_order_goid, custom_name, receipt_name,
                userAddress);
    }

    // 运单签收 获取运单信息
    public List<ShippingOrder> getSignShipOrder(int rows, int page,
                                                String name, String start_date, String end_date,
                                                String shipping_order_state, String custom_name, String receipt,
                                                String address, String shipperorder_id, String startSignTime,
                                                String endSignTime, User user) {
        List<ShippingOrder> list = null;
        if (user != null) {
            if (user.getFlag().equals("0")) {// 管理员
                list = so_dao.getSignShipOrder(rows, page, name, start_date,
                        end_date, shipping_order_state, custom_name, receipt,
                        address, shipperorder_id, startSignTime, endSignTime);
            } else if (user.getFlag().equals("1")) {// 司机
                list = so_dao.driverSignShipOrder(rows, page, name, start_date,
                        end_date, shipping_order_state, custom_name, receipt,
                        address, shipperorder_id, startSignTime, endSignTime,
                        user.getDriver_id());
            } else if (user.getFlag().equals("2")) {// 供应商
                List<String> user_address = rms_dao.getAddressSupperlies(user
                        .getSuppliers_id());
                list = so_dao.suppliersSignShipOrder(rows, page, name,
                        start_date, end_date, shipping_order_state,
                        custom_name, receipt, address, shipperorder_id,
                        startSignTime, endSignTime, user.getSuppliers_id(),
                        user_address);
            } else if (user.getFlag().equals("3") || user.getFlag().equals("4")) {// 信息员
                list = so_dao.otherSignShipOrder(rows, page, name, start_date,
                        end_date, shipping_order_state, custom_name, receipt,
                        address, shipperorder_id, startSignTime, endSignTime,
                        user.getId());
            }
        }
        return list;
    }

    // 新增已签收信息查询*******************
    public List<ShippingOrder> getShipOrderOld(int rows, int page, String name,
                                               String start_date, String end_date, String shipping_order_state,
                                               String custom_name, String receipt, String address,
                                               String shipperorder_id, User user) {
        List<ShippingOrder> list = null;
        if (user != null) {
            if (user.getFlag().equals("0")) {// 管理员
                list = so_dao.getShipOrderOld(rows, page, name, start_date,
                        end_date, shipping_order_state, custom_name, receipt,
                        address, shipperorder_id);
            } else if (user.getFlag().equals("1")) {// 司机
                list = so_dao.driverShipOrderOld(rows, page, name, start_date,
                        end_date, shipping_order_state, custom_name, receipt,
                        address, shipperorder_id, user.getDriver_id());
            } else if (user.getFlag().equals("2")) {// 供应商
                List<String> user_address = rms_dao.getAddressSupperlies(user
                        .getSuppliers_id());
                list = so_dao.suppliersShipOrderOld(rows, page, name,
                        start_date, end_date, shipping_order_state,
                        custom_name, receipt, address, shipperorder_id,
                        user.getSuppliers_id(), user_address);
            } else if (user.getFlag().equals("3") || user.getFlag().equals("4")) {// 信息员
                list = so_dao.otherShipOrderOld(rows, page, name, start_date,
                        end_date, shipping_order_state, custom_name, receipt,
                        address, shipperorder_id, user.getId());
            }

        }

        return list;
    }

    // 运单签收 获取运单信息总条数
    public int getSignShipOrdercount(String shipperorder_num,
                                     String start_date, String end_date, String shipping_order_state,
                                     String custom_name, String receipt, String address,
                                     String shipperorder_id, String startSignTime, String endSignTime,
                                     User user) {
        int count = 0;
        if (user != null) {
            if (user.getFlag().equals("0")) {// 管理员
                count = so_dao.getSignShipOrdercount(shipperorder_num,
                        start_date, end_date, shipping_order_state,
                        custom_name, receipt, address, shipperorder_id,
                        startSignTime, endSignTime);
            } else if (user.getFlag().equals("1")) {// 司机
                count = so_dao.driverSignShipOrderCount(shipperorder_num,
                        start_date, end_date, shipping_order_state,
                        custom_name, receipt, address, shipperorder_id,
                        startSignTime, endSignTime, user.getDriver_id());
            } else if (user.getFlag().equals("2")) {// 供应商
                List<String> user_address = rms_dao.getAddressSupperlies(user
                        .getSuppliers_id());
                count = so_dao.suppliersGetShipOrderCount(shipperorder_num,
                        start_date, end_date, shipping_order_state,
                        custom_name, receipt, address, shipperorder_id,
                        startSignTime, endSignTime, user.getSuppliers_id(),
                        user_address);
            } else if (user.getFlag().equals("3") || user.getFlag().equals("4")) {// 信息员
                count = so_dao.otherSignShipOrderCount(shipperorder_num,
                        start_date, end_date, shipping_order_state,
                        custom_name, receipt, address, shipperorder_id,
                        startSignTime, endSignTime, user.getId());
            }
        }

        return count;
    }

    // 新增已签收信息统计******************************************************************************
    public int getShipOrderOldcount(String name, String start_date,
                                    String end_date, String shipping_order_state, String custom_name,
                                    String receipt, String address, String shipperorder_id, User user) {
        int count = 0;
        if (user != null) {
            if (user.getFlag().equals("0")) {// 管理员
                count = so_dao.getShipOrderOldcount(name, start_date, end_date,
                        shipping_order_state, custom_name, receipt, address,
                        shipperorder_id);
            } else if (user.getFlag().equals("1")) {// 司机
                count = so_dao.driverShipOrderOldCount(name, start_date,
                        end_date, shipping_order_state, custom_name, receipt,
                        address, shipperorder_id, user.getDriver_id());
            } else if (user.getFlag().equals("2")) {// 供应商
                List<String> user_address = rms_dao.getAddressSupperlies(user
                        .getSuppliers_id());
                count = so_dao.suppliersShipOrderOldCount(name, start_date,
                        end_date, shipping_order_state, custom_name, receipt,
                        address, shipperorder_id, user.getSuppliers_id(),
                        user_address);
            } else if (user.getFlag().equals("3") || user.getFlag().equals("4")) {// 信息员
                count = so_dao.otherShipOrderOldCount(name, start_date,
                        end_date, shipping_order_state, custom_name, receipt,
                        address, shipperorder_id, user.getId());
            }
        }

        return count;
    }

    // 运单签收查看图片信息
    public List<ShipperOrderImg> getSingShiperOrderImg(String orderId) {
        return so_dao.getSingShiperOrderImg(orderId);
    }

    // 已评论
    public int updateComment_state(String shiping_order_id) {

        return so_dao.updateComment_state(shiping_order_id);
    }

    // APP 运单签收 保存运单时效信息
    public int insertShippingTime(String shiping_order_id) {

        return so_dao.insertShippingTime(shiping_order_id);
    }

    // 56项目对接
    public int get56LinkProject(String goid, String mechanism) {

        return so_dao.get56LinkProject(goid, mechanism);
    }

    // 根据接单id查询司机信息
    public Driver getDriverInfo(String orderId) {

        return so_dao.getDriverInfo(orderId);
    }

    // 用户导入查询发货客户信息
    public Customer userGetCusetomerInfo(String customerId) {

        return so_dao.userGetCusetomerInfo(customerId);
    }

    @Override
    public int updateOrderSignRemarks(String orderId, String remarks) {

        return so_dao.updateOrderSignRemarks(orderId, remarks);
    }

    @Override
    public int updateOrderShipperTimeRemarks(String orderId, String remarks) {

        return so_dao.updateOrderShipperTimeRemarks(orderId, remarks);
    }

    @Override
    public List<Customer> getCustomName(String number, User user) {
        List<Customer> list = new ArrayList<Customer>();
        if (user != null) {
            if (user.getFlag().equals("0")) {// 管理员
                list = so_dao.getCustomName(number);
            } else if (user.getFlag().equals("1")) {// 司机

            } else if (user.getFlag().equals("2")) {// 供应商

            } else if (user.getFlag().equals("3") || user.getFlag().equals("4")) {// 信息员
                list = so_dao.customerGetCustomName(number, user.getCustomer_id());
            }

        }
        return list;
    }

    @Override
    public List<ShippingOrder> getShippingOrderId(String shiping_order_num, String shiping_order_goid) {
        return so_dao.getShippingOrderId(shiping_order_num, shiping_order_goid);
    }

    @Override
    public String getShippingOrderBusinessType(String shiping_order_num, String shiping_order_goid,int businessType) {
        return so_dao.getShippingOrderBusinessType(shiping_order_num, shiping_order_goid,businessType);
    }

    @Override
    public int updateShippingOrderBusinessType(List<String> orderId, int businessType) {
        if (orderId == null || orderId.size() == 0) {
            return 0;
        }
        return so_dao.updateShippingOrderBusinessType(orderId, businessType);
    }
}