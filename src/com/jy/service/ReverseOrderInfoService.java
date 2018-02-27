package com.jy.service;

import java.util.List;
import java.util.Map;

import com.jy.model.*;
import org.apache.ibatis.annotations.Param;

public interface ReverseOrderInfoService {

    Map<String, Object> getReverseOrderInfo(User user, Integer start, Integer length, String send_time, String end_time, String shiping_order_num, String end_address,
                                            String end_mechanism, String custom_contact, String order_type, String receipt_contact, String send_mechanism);


    /**
     * 添加逆向退货信息
     *
     * @param user
     * @param reverserOrderModel
     * @return
     */
    boolean addReverseOrderInfo(User user, ReverserOrderModel reverserOrderModel);

    /**
     * 保存历史信息
     *
     * @param orderHistory
     * @return
     */
    boolean saveReverseOrderHistory(OrderHistory orderHistory);

    /**
     * 分配运单信息
     *
     * @param orderIds
     * @param driverId
     * @return
     */
    boolean distributionReverseOrder(List<String> orderIds, String driverId);

    /**
     * 分配运单信息
     *
     * @param driverId
     * @param orderId
     * @return
     */
    boolean saveDistributionReverseOrderInfo(String driverId, String[] orderId);

    /**
     * 保存分配信息
     *
     * @param list
     * @return
     */
    boolean saveDistributionReverseOrder(List<DriverToOrder> list);


    /**
     * 接单运单信息
     *
     * @return
     */
    boolean takingReverserOrder(String orderId, String driverId, String remark, Integer state);


    /**
     * 保存接单图片
     *
     * @param shipperOrderImg
     * @return
     */
    boolean updateSaveReverseImage(ShipperOrderImg shipperOrderImg);

    /**
     * 保存多张签收图片
     *
     * @param shipperOrderImgs
     * @return
     */
    int batchSaveTakingReverseImage(List<ShipperOrderImg> shipperOrderImgs);

    /**
     * 更新对应的信息
     *
     * @param reverserOrderModel
     * @return
     */
    boolean updateReverserOrder(ReverserOrderModel reverserOrderModel);


    /**
     * 保存签收信息
     *
     * @param sign_order
     * @return
     */
    boolean saveSignReverserOrderInfo(Sign_order sign_order);


    /**
     * 保存签收图片信息
     *
     * @param shipperOrderImg
     * @return
     */
    boolean saveSignReverserOrderImage(ShipperOrderImg shipperOrderImg);

    /**
     * 查询分配列表信息
     *
     * @param user
     * @param start
     * @param length
     * @return
     */
    Map<String, Object> getDistributionReverseOrderInfo(User user, Integer start, Integer length, String send_time, String end_time,
                                                        String end_address, String send_mechanism, String custom_contact, String order_type);

    /**
     * 保存历史
     *
     * @param list
     * @return
     */
    int saveReverserOrderHistoryList(List<OrderHistory> list);

    /**
     * 更新状态
     *
     * @param ids
     * @param state
     * @return
     */
    int updateReverserOrderState(List<String> ids, String driverId, Integer state);

    /**
     * 获取分配和接单的信息
     *
     * @param user
     * @param start
     * @param length
     * @return
     */
    Map<String, Object> getTakingReverseOrderInfo(User user, Integer start, Integer length, String driverId, String send_time, String end_time,
                                                  String end_address, String send_mechanism, String custom_contact, String order_type);

    /**
     * 删除分配
     *
     * @param driverId
     * @param orderIds
     * @return
     */
    boolean cancelDistributionReverseOrder(String driverId, List<String> orderIds);

    /**
     * 根据id 获取逆向信息
     *
     * @param id
     * @return
     */
    ReverserOrderModel getReverseOrderInfoByIds(String id);


    /**
     * 获取签收列表信息
     *
     * @param user
     * @param start
     * @param length
     * @return
     */
    Map<String, Object> getSignReverseOrderInfo(User user, Integer start, Integer length, String send_time, String end_time, String shiping_order_num, String shipping_order_state, String order_type, String send_mechanism, String end_address, String end_mechanism);

    /**
     * 保存
     *
     * @param reverserOrderModel
     * @return
     */
    int saveReverseOrderHistory(ReverserOrderModel reverserOrderModel);

    /**
     * 保存
     *
     * @param reverserOrderModel
     * @return
     */
    int saveReverseOrderHistory(ReverserOrderModel reverserOrderModel,String message,User user);

    /**
     * 保存城市信息
     *
     * @param reverserOrderModel
     * @return
     */
    int saveReverserOrderCityInfo(ReverserOrderModel reverserOrderModel);


    /**
     * 更新逆向物流运单城市信息
     *
     * @param reverserOrderModel
     * @return
     */
    int updateReverseOrderCityInfo(ReverserOrderModel reverserOrderModel);

    /**
     * 删除
     *
     * @param list
     * @return
     */
    boolean deleteReverserOrderInfo(List<String> list);

    /**
     * 判断签收表中是否有签收信息
     *
     * @param orderId 订单id
     * @return false
     */
    boolean checkReverseOrderInfoById(String orderId);

    /**
     * 更新逆向签收信息
     *
     * @param sign_order
     * @return
     */
    boolean updateSignReverseOrderInfo(Sign_order sign_order);

    /**
     * 更新运单状态
     *
     * @param orderId
     * @param state
     * @return
     */
    boolean updateReverseOrderState(String orderId,String state);

    /**
     * 更新
     * @param list
     * @return
     */
    boolean saveSignReverseOrderImage(List<ShipperOrderImg> list);

    /**
     * 获取签收信息通过id
     * @param orderId
     * @return
     */
    ReverserOrderModel getSignReverseOrderById(String orderId);

    /**
     * 获取签收图片信息
     * @param orderId
     * @return
     */
    List<ShipperOrderImg> getSingReverseOrderImg(String orderId);

    /**
     * 获取接单图片信息
     * @param orderId
     * @return
     */
    List<ShipperOrderImg> getTakingReverseOrderImg(String orderId);

    /**
     * 获取逆向物流历史信息
     * @param orderId
     * @return
     */
    List<OrderHistory> getReverseOrderHistory(String orderId);

    /**
     * 获逆向物流运单信息
     * 通过id
     */
    List<ReverserOrderModel> getReverseOrderByIds(List<String> ids);

    /**
     * 通过id获取到签收信息
     * @param ids
     * @return
     */
    List<ReverserOrderModel> getSignReverseOrderByIds(List<String> ids);

    /**
     * 批量保存订单信息
     * @param list
     * @return
     */
    int batchInsertReverseOrderInfo(List<ReverserOrderModel> list);

    /**
     * 获取运单号是否存在
     * @param shiping_order_num
     * @param shiping_order_goid
     * @return
     */
    boolean getReverseOrderCountByNum(String shiping_order_num,String shiping_order_goid);

}
