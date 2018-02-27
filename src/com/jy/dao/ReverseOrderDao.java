package com.jy.dao;

import java.util.List;

import com.baidu.yun.core.annotation.R;
import com.jy.model.*;
import org.apache.ibatis.annotations.Param;

public interface ReverseOrderDao {

    /**
     * 管理员获取逆向物流列表信息
     *
     * @param start
     * @param length
     * @param send_time
     * @param end_time
     * @param shiping_order_num
     * @param end_address
     * @param end_mechanism
     * @param custom_contact
     * @param order_type
     * @param receipt_contact
     * @param send_mechanism
     * @return
     */
    List<ReverserOrderModel> getReverserOrderModelInfo(@Param("start") Integer start, @Param("length") Integer length, @Param("send_time") String send_time, @Param("end_time") String end_time, @Param("shiping_order_num") String shiping_order_num, @Param("end_address") String end_address,
                                                       @Param("end_mechanism") String end_mechanism, @Param("custom_contact") String custom_contact, @Param("order_type") String order_type, @Param("receipt_contact") String receipt_contact, @Param("send_mechanism") String send_mechanism);

    //司机获取逆向物流信心
    List<ReverserOrderModel> driverGetReverserOrderModelInfo(@Param("start") Integer start, @Param("length") Integer length, @Param("send_time") String send_time, @Param("end_time") String end_time, @Param("shiping_order_num") String shiping_order_num, @Param("end_address") String end_address,
                                                             @Param("end_mechanism") String end_mechanism, @Param("custom_contact") String custom_contact, @Param("order_type") String order_type, @Param("receipt_contact") String receipt_contact, @Param("send_mechanism") String send_mechanism, @Param("driverId") String driverId);

    //供应商获取逆向物流信息
    List<ReverserOrderModel> supplierGetReverserOrderModelInfo(@Param("start") Integer start, @Param("length") Integer length, @Param("send_time") String send_time, @Param("end_time") String end_time, @Param("shiping_order_num") String shiping_order_num, @Param("end_address") String end_address,
                                                               @Param("end_mechanism") String end_mechanism, @Param("custom_contact") String custom_contact, @Param("order_type") String order_type, @Param("receipt_contact") String receipt_contact, @Param("send_mechanism") String send_mechanism, @Param("supplierId") String supplierId);

    //项目部获取对应的逆向信息
    List<ReverserOrderModel> customerGetReverserOrderModelInfo(@Param("start") Integer start, @Param("length") Integer length, @Param("send_time") String send_time, @Param("end_time") String end_time, @Param("shiping_order_num") String shiping_order_num, @Param("end_address") String end_address,
                                                               @Param("end_mechanism") String end_mechanism, @Param("custom_contact") String custom_contact, @Param("order_type") String order_type, @Param("receipt_contact") String receipt_contact, @Param("send_mechanism") String send_mechanism, @Param("customerId") String customerId);

    /**
     * 管理员获取逆向物流信息总条数
     *
     * @param send_time
     * @param end_time
     * @param shiping_order_num
     * @param end_address
     * @param end_mechanism
     * @param custom_contact
     * @param order_type
     * @param receipt_contact
     * @param send_mechanism
     * @return
     */
    int getReverserOrderModelCount(@Param("send_time") String send_time, @Param("end_time") String end_time, @Param("shiping_order_num") String shiping_order_num, @Param("end_address") String end_address,
                                   @Param("end_mechanism") String end_mechanism, @Param("custom_contact") String custom_contact, @Param("order_type") String order_type, @Param("receipt_contact") String receipt_contact, @Param("send_mechanism") String send_mechanism);

    //司机获取总条数
    int driverGetReverserOrderModelCount(@Param("send_time") String send_time, @Param("end_time") String end_time, @Param("shiping_order_num") String shiping_order_num, @Param("end_address") String end_address,
                                         @Param("end_mechanism") String end_mechanism, @Param("custom_contact") String custom_contact, @Param("order_type") String order_type, @Param("receipt_contact") String receipt_contact, @Param("send_mechanism") String send_mechanism, @Param("driverId") String driverId);

    //供应商获取总条数
    int supplierGetReverserOrderModelCount(@Param("send_time") String send_time, @Param("end_time") String end_time, @Param("shiping_order_num") String shiping_order_num, @Param("end_address") String end_address,
                                           @Param("end_mechanism") String end_mechanism, @Param("custom_contact") String custom_contact, @Param("order_type") String order_type, @Param("receipt_contact") String receipt_contact, @Param("send_mechanism") String send_mechanism, @Param("supplierId") String supplierId);

    //项目部获取总条数
    int customerGetReverserOrderModelCount(@Param("send_time") String send_time, @Param("end_time") String end_time, @Param("shiping_order_num") String shiping_order_num, @Param("end_address") String end_address,
                                           @Param("end_mechanism") String end_mechanism, @Param("custom_contact") String custom_contact, @Param("order_type") String order_type, @Param("receipt_contact") String receipt_contact, @Param("send_mechanism") String send_mechanism, @Param("customerId") String customerId);

    /**
     * 插入逆向运单信息
     *
     * @param reverserOrderModel
     * @return
     */
    int insertReverseOrderInfo(@Param("reverserOrder") ReverserOrderModel reverserOrderModel);

    /**
     * 批量插入逆向信息
     *
     * @param list
     * @return
     */
    int batchInsertReverseOrderInfo(@Param("list") List<ReverserOrderModel> list);

    /**
     * 修改逆向信息
     *
     * @param reverserOrderModel
     * @return
     */
    int updateReverserOrderInfo(@Param("reverserOrder") ReverserOrderModel reverserOrderModel);

    /**
     * 删除逆向物流
     *
     * @param list
     * @return
     */
    int deleteReverserOrderInfo(@Param("list") List<String> list);


    /**
     * 分配逆向物流
     *
     * @param list
     * @return
     */
    int saveDistributionReverserOrderInfo(@Param("list") List<DriverToOrder> list);


    /**
     * 接单逆向物流
     *
     * @param orderId
     * @param remark
     * @param state
     * @return
     */
    int updateReverserOrderTaking(@Param("orderId") String orderId, @Param("driverId") String driverId, @Param("remark") String remark, @Param("state") Integer state);


    /**
     * 保存接货图片
     *
     * @return
     */
    int saveReverserOrderTakingImage(ShipperOrderImg shipperOrderImg);


    /**
     * 存历史
     *
     * @param orderHistory
     * @return
     */
    int saveReverserOrderHistory(@Param("history") OrderHistory orderHistory);

    /**
     * 存历史信息
     *
     * @param list
     * @return
     */
    int saveReverserOrderHistoryList(@Param("list") List<OrderHistory> list);

    /**
     * 保存签收历史
     *
     * @param sign_order
     * @return
     */
    int saveSignReverserOrderInfo(Sign_order sign_order);


    /**
     * 保存签收图片
     *
     * @param shipperOrderImg
     * @return
     */
    int saveSignReverserOrderImage(ShipperOrderImg shipperOrderImg);

    /**
     * 查询司机对应的供应商
     *
     * @param driverId
     * @return
     */
    Driver getDriverSupplierId(@Param("diverId") String driverId);

    /**
     * 查询对应的客户信息
     *
     * @param customerId
     * @return
     */
    Customer getCustomer(String customerId);

    /**
     * 查询分配的退货列表
     *
     * @param start
     * @param length
     * @return
     */
    List<ReverserOrderModel> getDistributionReverserOrderModel(@Param("start") Integer start, @Param("length") Integer length, @Param("send_time") String send_time, @Param("end_time") String end_time, @Param("end_address") String end_address, @Param("send_mechanism") String send_mechanism, @Param("custom_contact") String custom_contact, @Param("order_type") String order_type);


    // 供应商查询分配的退货列表
    List<ReverserOrderModel> supplierGetDistributionReverserOrderModel(@Param("start") Integer start, @Param("length") Integer length, @Param("send_time") String send_time, @Param("end_time") String end_time, @Param("end_address") String end_address, @Param("send_mechanism") String send_mechanism, @Param("custom_contact") String custom_contact, @Param("order_type") String order_type, @Param("supplierId") String supplierId, @Param("list") List<String> list);


    /**
     * 查询分配的退货列表条数
     *
     * @return
     */
    int getDistributionReverserOrderModelCount(@Param("send_time") String send_time, @Param("end_time") String end_time, @Param("end_address") String end_address, @Param("send_mechanism") String send_mechanism, @Param("custom_contact") String custom_contact, @Param("order_type") String order_type);


    //供应商获取分配列表
    int supplierGetDistributionReverserOrderCount(@Param("send_time") String send_time, @Param("end_time") String end_time, @Param("end_address") String end_address, @Param("send_mechanism") String send_mechanism, @Param("custom_contact") String custom_contact, @Param("order_type") String order_type, @Param("supplierId") String supplierId, @Param("list") List<String> list);


    /**
     * 更新对应的状态
     *
     * @param ids
     * @param state
     * @return
     */
    int updateReverserOrderState(@Param("list") List<String> ids, @Param("state") Integer state, @Param("supplierId") String supplierId, @Param("driverId") String driverId);


    /**
     * 查询分配的退货列表
     *
     * @param start
     * @param length
     * @return
     */
    List<ReverserOrderModel> getTakingReverserOrderModel(@Param("start") Integer start, @Param("length") Integer length, @Param("driverId") String driverId, @Param("send_time") String send_time, @Param("end_time") String end_time, @Param("end_address") String end_address, @Param("send_mechanism") String send_mechanism, @Param("custom_contact") String custom_contact, @Param("order_type") String order_type);

    /**
     * 查询分配的退货列表条数
     *
     * @return
     */
    int getTakingReverserOrderModelCount(@Param("driverId") String driverId, @Param("send_time") String send_time, @Param("end_time") String end_time, @Param("end_address") String end_address, @Param("send_mechanism") String send_mechanism, @Param("custom_contact") String custom_contact, @Param("order_type") String order_type);

    /**
     * 删除分配信息
     *
     * @param ids
     * @param driverId
     * @return
     */
    int deleteDistributionReverseOrder(@Param("list") List<String> ids, @Param("driverId") String driverId);

    /**
     * 根据id获取对象信息
     *
     * @param id
     * @return
     */
    ReverserOrderModel getReverserOrderModel(@Param("id") String id);

    /**
     * 批量接单图片保存信息
     *
     * @param list
     * @return
     */
    int batchSaveTakingReverserOrderImage(@Param("list") List<ShipperOrderImg> list);

    /**
     * 获取签收信息列表
     *
     * @param start
     * @param length
     * @return
     */
    List<ReverserOrderModel> getSignReverserOrderModel(@Param("start") Integer start, @Param("length") Integer length, @Param("send_time") String send_time, @Param("end_time") String end_time, @Param("shiping_order_num") String shiping_order_num, @Param("shipping_order_state") String shipping_order_state, @Param("order_type") String order_type, @Param("send_mechanism") String send_mechanism, @Param("end_address") String end_address, @Param("end_mechanism") String end_mechanism);

    //司机获取签收列表信息
    List<ReverserOrderModel> driverGetSignReverserOrderModel(@Param("start") Integer start, @Param("length") Integer length, @Param("send_time") String send_time, @Param("end_time") String end_time, @Param("shiping_order_num") String shiping_order_num, @Param("shipping_order_state") String shipping_order_state, @Param("order_type") String order_type, @Param("send_mechanism") String send_mechanism, @Param("end_address") String end_address, @Param("end_mechanism") String end_mechanism,@Param("driverId") String driverId);

    //供应商获取签收信息
    List<ReverserOrderModel> supplierGetSignReverserOrderModel(@Param("start") Integer start, @Param("length") Integer length, @Param("send_time") String send_time, @Param("end_time") String end_time, @Param("shiping_order_num") String shiping_order_num, @Param("shipping_order_state") String shipping_order_state, @Param("order_type") String order_type, @Param("send_mechanism") String send_mechanism, @Param("end_address") String end_address, @Param("end_mechanism") String end_mechanism,@Param("suppliersId") String suppliersId);

    //项目部获取签收信息
    List<ReverserOrderModel> customerGetSignReverserOrderModel(@Param("start") Integer start, @Param("length") Integer length, @Param("send_time") String send_time, @Param("end_time") String end_time, @Param("shiping_order_num") String shiping_order_num, @Param("shipping_order_state") String shipping_order_state, @Param("order_type") String order_type, @Param("send_mechanism") String send_mechanism, @Param("end_address") String end_address, @Param("end_mechanism") String end_mechanism,@Param("customerId") String customerId);


    /**
     * 获取签收信息列表总条数
     *
     * @return
     */
    int getSignReverserOrderCount(@Param("send_time") String send_time, @Param("end_time") String end_time, @Param("shiping_order_num") String shiping_order_num, @Param("shipping_order_state") String shipping_order_state, @Param("order_type") String order_type, @Param("send_mechanism") String send_mechanism, @Param("end_address") String end_address, @Param("end_mechanism") String end_mechanism);

    // 司机获取签收信息
    int driverGetSignReverserOrderCount(@Param("send_time") String send_time, @Param("end_time") String end_time, @Param("shiping_order_num") String shiping_order_num, @Param("shipping_order_state") String shipping_order_state, @Param("order_type") String order_type, @Param("send_mechanism") String send_mechanism, @Param("end_address") String end_address, @Param("end_mechanism") String end_mechanism,@Param("driverId") String driverId);

    //供应商获取签收信息
    int supplierGetSignReverserOrderCount(@Param("send_time") String send_time, @Param("end_time") String end_time, @Param("shiping_order_num") String shiping_order_num, @Param("shipping_order_state") String shipping_order_state, @Param("order_type") String order_type, @Param("send_mechanism") String send_mechanism, @Param("end_address") String end_address, @Param("end_mechanism") String end_mechanism,@Param("suppliersId") String suppliersId);

    //项目部获取签收信息
    int customerGetSignReverserOrderCount(@Param("send_time") String send_time, @Param("end_time") String end_time, @Param("shiping_order_num") String shiping_order_num, @Param("shipping_order_state") String shipping_order_state, @Param("order_type") String order_type, @Param("send_mechanism") String send_mechanism, @Param("end_address") String end_address, @Param("end_mechanism") String end_mechanism,@Param("customerId") String customerId);


    //int getReverserOrderCountByOrderNum(@Param("shiping_order_num") String shiping_order_num,@Param("shiping_order_goid") String shiping_order_goid);

    /**
     * 保存城市信息
     *
     * @return
     */
    int saveReverserOrderCityInfo(@Param("reverserOrder") ReverserOrderModel reverserOrderModel);


    /**
     * 更新逆向物流运单城市信息
     *
     * @param reverserOrderModel
     * @return
     */
    int updateReverseOrderCityInfo(@Param("reverserOrder") ReverserOrderModel reverserOrderModel);

    /**
     * 删除城市逆向订单的城市信息
     *
     * @param list
     * @return
     */
    int deleteReverserOrderCityInfo(@Param("list") List<String> list);

    /**
     * 判断id是否存在
     *
     * @param orderId
     * @return
     */
    int checkReverseOrderInfoById(@Param("orderId") String orderId);

    /**
     * 保存签收信息
     *
     * @param sign_order
     * @return
     */
    int saveReverseOrderInfo(Sign_order sign_order);

    /**
     * 更新逆向物流状态
     *
     * @param orderId
     * @param state
     * @return
     */
    int updateReverseOrderState(@Param("orderId") String orderId, @Param("state") String state);

    /**
     * 保存图片信息
     *
     * @param list
     * @return
     */
    int saveSignReverseOrderImage(@Param("list") List<ShipperOrderImg> list);

    /**
     * 更新逆向签收信息
     *
     * @param sign_order
     * @return
     */
    int updateSignReverseOrder(Sign_order sign_order);

    /**
     * 获取签收信息
     *
     * @param orderId
     * @return
     */
    ReverserOrderModel getSignReverseOrderInfo(@Param("orderId") String orderId);

    /**
     * 获取签收图片信息
     *
     * @param orderId
     * @return
     */
    List<ShipperOrderImg> getSingReverseOrderImg(@Param("orderId") String orderId);

    /**
     * 获取接单发运图片信息
     *
     * @param orderId
     * @return
     */
    List<ShipperOrderImg> getTakingReverseOrderImg(@Param("orderId") String orderId);

    /**
     * 获取逆向物流历史信息
     *
     * @param orderId
     * @return
     */
    List<OrderHistory> getReverseOrderHistory(@Param("orderId") String orderId);

    /**
     * 根据id 获取运单信息
     * @return
     */
    List<ReverserOrderModel> getReverseOrderByIds(@Param("list") List<String> ids);

    /**
     * 根据id 获取签收运单信息
     *
     * @return
     */
    List<ReverserOrderModel> getSignReverseOrderByIds(@Param("list") List<String> ids);

    /**
     * 根据运单号获取运单信息是否存在
     * @param shiping_order_num
     * @param shiping_order_goid
     * @return
     */
    int getReverseOrderCountByNum(@Param("shiping_order_num") String shiping_order_num,@Param("shiping_order_goid") String shiping_order_goid);
}
