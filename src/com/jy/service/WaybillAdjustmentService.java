package com.jy.service;

import com.jy.model.*;

import java.util.List;

/**
 * Created by Administrator on 2017/2/22/022.
 */
public interface WaybillAdjustmentService {

    /**
     * 运单调整获取列表总数
     * @param start_time
     * @param end_time
     * @param shiping_order_num
     * @param shiping_order_goid
     * @param applicant_state
     * @param applicant_type
     * @param send_mechanism
     * @param end_address
     * @param receipt_name
     * @return
     */
    int getCountWaybillAdjustment(User user, String start_time, String end_time, String shiping_order_num, String shiping_order_goid, String applicant_state,
                                  String applicant_type, String send_mechanism, String end_address, String receipt_name);

    /**
     * 获取列表中信息列表
     * @param rows
     * @param page
     * @param start_time
     * @param end_time
     * @param shiping_order_num
     * @param shiping_order_goid
     * @param applicant_state
     * @param applicant_type
     * @param send_mechanism
     * @param end_address
     * @param receipt_name
     * @return
     */
    List<WaybillAdjustment> getWaybillAdjustmentListInfo(User user,int rows, int page, String start_time, String end_time,
                                                              String shiping_order_num, String shiping_order_goid, String applicant_state,
                                                              String applicant_type, String send_mechanism, String end_address, String receipt_name);

    /**
     * 根据运单id查询运单信息
     * @param orderId
     * @return
     */
    ShippingOrder getShppingOrderMessage(String orderId);

    /**
     * 保存申请主表信息
     * @param waybillAdjustment
     * @return
     */
    int saveWaybillAdjustmentInfo(WaybillAdjustment waybillAdjustment);

    /**
     * 保存运单调整新运单
     * @param waybillAdjustmentOrder
     * @return
     */
    int saveWaybillAdjustment(WaybillAdjustmentOrder waybillAdjustmentOrder);

    /**
     * 保存运单调整修改记录
     * @param list
     * @return
     */
    int saveWaybillAdjustmentHistoryList(String waybillAdjustmentId,List<WaybillAdjustmentHistory> list);

    /**
     * 更新运单状态
     * @param orderId
     * @param state
     * @return
     */
    int updateOrderState(String orderId,String state);

    /**
     * 根据id 获取运单调整信息
     * @param id
     * @return
     */
    WaybillAdjustment getWaybillAuditInfo(String id);

    /**
     * 根据id获取运单调整的调整记录
     * @param id
     * @return
     */
    List<WaybillAdjustmentHistory> getWaybillAuditHistoryListInfo(String id);

    /**
     * 运单调整审核处理
     * @param waybillAdjustment
     * @return
     */
    int doWaybillAuditInfo(WaybillAdjustment waybillAdjustment);

    /**
     * 删除运单签收和时效信息
     * @param orderId
     * @return
     */
    int deleteSignOrderMessage(String orderId);

    /**
     * 更新审核状态运单状态
     * @param orderId
     * @param orderState
     * @param waybillState
     * @return
     */
    int updateOrderWaybillAudit(String orderId,String orderState,String waybillState);

    /**
     *获取运单调整后保存的信息
     * @param waybill_adjustment_id
     * @return
     */
    WaybillAdjustmentOrder getWaybillAdjustmentOrderInfo(String waybill_adjustment_id);


}
