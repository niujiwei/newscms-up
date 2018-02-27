package com.jy.dao;

import java.util.List;

import com.jy.model.*;
import org.apache.ibatis.annotations.Param;

public interface OrderDao {
    public List<PositionInfo> getAddres(String province, String city,
                                        String count);

    int getOrderCustom(String name, String start_date1, String end_date2,
                       String start_date, String end_date, String car_number, String type);// 查询总数

    List<ShippingOrder> getOrderCustomInfo(int rows, int page, String name,
                                           String start_date1, String end_date2, String start_date,
                                           String end_date, String car_number, String type); // 查询司机

    int deleteOrderCustom(@Param("array") String[] del);// 删除

    /**
     * 根据客户车辆信息id删除相关订单信息
     *
     * @param pid
     * @return
     */
    int deleteOrderMsg(@Param("array") String[] pid);

    /**
     * 查询是否有关联的订单
     *
     * @param pid
     * @return
     */
    int OrderMsgNum(@Param("array") String[] pid);

    int saveOrderCustom(OrderCustom d);// 保存

    OrderCustom getUpdateOrderCustom(String id);// 修改查询

    int updateOrderCustom(OrderCustom d);// 用户修改

    /**
     * 修改订单备注
     *
     * @param pid
     * @param notes
     * @return
     */
    int getRemakesOrder(String pid, String notes, String remarks_orde,
                        String remarks_date);

    /*
     * 获取数据下拉表的查询
     */
    List<OrderCustom> getPlateNumber(String number);

    List<OrderCustom> getOrderCustomAll(String name, String phone_number,
                                        String type, String start_date, String end_date);

    /**
     * 批量导入
     *
     * @param tlist
     * @return
     */
    int saveShipOrders(List<ShippingOrder> tlist);

    /**
     * 发货客户导入
     *
     * @param tlist
     * @return
     */
    int customerExcel(List<Customer> tlist);

    /**
     * 收货客户导入
     *
     * @param tlist
     * @return
     */
    int DeliveryExcelInfo(List<DeliveryCustomer> tlist);

    /**
     * 客户名称查询
     *
     * @param name
     * @return
     */
    List<OrderCustom> getCustomer(String name);

    /**
     * 查询重复订单号
     *
     * @return
     */
    List<ShippingOrder> getAorder();

    /**
     * 查询订单所以信息页面展示
     *
     * @param pid
     * @return
     */
    ShippingOrder getShipOrderMsg(String pid);

    /**
     * 首页订单页面显示
     *
     * @param name
     * @param phone_number
     * @param type
     * @param start_date
     * @param end_date
     * @param pid
     * @param phone
     * @param order_state
     * @param pay_type
     * @param send_address
     * @return
     */
    int getShipOrder(String name, String phone_number, String type,
                     String start_date, String end_date, String pid, String phone,
                     String order_state, String pay_type, String send_address,
                     String goods_name);// 查询总数

    List<ShippingOrder> getShipOrderInfo(int rows, int page, String name,
                                         String phone_number, String type, String start_date,
                                         String end_date, String pid, String phone, String order_state,
                                         String pay_type, String send_address, String goods_name); // 查询司机

    /**
     * 订单修改记录查询
     *
     * @param pid
     * @return
     */
    int getShipOrder1(String pid);// 查询总数

    List<ShippingOrder> getShipOrderInfo1(int rows, int page, String pid); // 查询司机

    List<Driver> getDriverInfo();// 获取所有司机

    int saveDriverToOrder(List<DriverToOrder> dtolist);// 添加司机订单关联表

    int upfenpeiOrder(List<DriverToOrder> dtolist);// 更新订单分配状态

    // 运单导入获取时效
    Aging getAging(String id, String address);

    // 运单导入更新时效
    int updateAging(String id, String aging, String agingDay);

    int impExcelSaveimp(JySuppliers list);

    int impExcelDriverSaveimp(DriverNew list);

    int impAgingExcel(Aging aging);

    int impUserExcel(User user);

    // 运单导入--新的更新时效信息
    Aging getNewAging(String id, String address, String inputTime);

    public ShippingOrder getShipOrderInfos(String order_id);

    // 到达订单历史表存储
    int saveOrderHistory(List<OrderHistory> order);

    //通过订单号和出货订单号判断运单是否存在
    public int bGetOrderCount(@Param("orderNum") String orderNum, @Param("shiping_order_goid") String shiping_order_goid);

    /**
     * 根据终到位置获取省市县
     * @param endAddress
     * @return
     */
    List<City_info> getCityInfoByEndAddress(@Param("endAddress") String endAddress);

    /**
     * 更新时效和终到位置
     * @param id
     * @param aging
     * @param agingDay
     * @param province
     * @param city
     * @param county
     * @return
     */
    int updateAgingAndCityInfo(String id, String aging, String agingDay,Integer province,
                                      Integer city,Integer county);// 更新时效
}
