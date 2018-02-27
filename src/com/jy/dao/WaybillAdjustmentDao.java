package com.jy.dao;

import com.jy.model.ShippingOrder;
import com.jy.model.WaybillAdjustment;
import com.jy.model.WaybillAdjustmentHistory;
import com.jy.model.WaybillAdjustmentOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Administrator on 2017/2/22/022.
 */
public interface WaybillAdjustmentDao {
    /**
     * 管理员--运单调整获取列表总数
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
    int getCountWaybillAdjustment(@Param("start_time") String start_time, @Param("end_time")String end_time,
                                  @Param("shiping_order_num") String shiping_order_num, @Param("shiping_order_goid") String shiping_order_goid,@Param("applicant_state") String applicant_state,
                                  @Param("applicant_type") String applicant_type, @Param("send_mechanism") String send_mechanism,@Param("end_address") String end_address, @Param("receipt_name") String receipt_name);

    /**
     * 管理员--获取列表中信息列表
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
    List<WaybillAdjustment> getWaybillAdjustmentListInfo(@Param("rows") int rows, @Param("page") int page, @Param("start_time") String start_time, @Param("end_time")String end_time,
                                                              @Param("shiping_order_num") String shiping_order_num, @Param("shiping_order_goid") String shiping_order_goid,@Param("applicant_state") String applicant_state,
                                                              @Param("applicant_type") String applicant_type, @Param("send_mechanism") String send_mechanism,@Param("end_address") String end_address, @Param("receipt_name") String receipt_name);

    /**
     * 司机--获取运单调整信息列表
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
     * @param userId
     * @return
     */
    List<WaybillAdjustment> driverGetWaybillAdjustmentListInfo(@Param("rows") int rows, @Param("page") int page, @Param("start_time") String start_time, @Param("end_time")String end_time,
                                                         @Param("shiping_order_num") String shiping_order_num, @Param("shiping_order_goid") String shiping_order_goid,@Param("applicant_state") String applicant_state,
                                                         @Param("applicant_type") String applicant_type, @Param("send_mechanism") String send_mechanism,@Param("end_address") String end_address, @Param("receipt_name") String receipt_name,@Param("userId") String userId);

    /**
     * 司机--获取运单调整列表总数
     * @param start_time
     * @param end_time
     * @param shiping_order_num
     * @param shiping_order_goid
     * @param applicant_state
     * @param applicant_type
     * @param send_mechanism
     * @param end_address
     * @param receipt_name
     * @param userId
     * @return
     */
    int driverGetCountWaybillAdjustment(@Param("start_time") String start_time, @Param("end_time")String end_time,
                                  @Param("shiping_order_num") String shiping_order_num, @Param("shiping_order_goid") String shiping_order_goid,@Param("applicant_state") String applicant_state,
                                  @Param("applicant_type") String applicant_type, @Param("send_mechanism") String send_mechanism,@Param("end_address") String end_address, @Param("receipt_name") String receipt_name,@Param("userId") String userId);


    /**
     * 司机--获取运单调整信息列表
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
     * @param suppliersId
     * @return
     */
    List<WaybillAdjustment> suppliersGetWaybillAdjustmentListInfo(@Param("rows") int rows, @Param("page") int page, @Param("start_time") String start_time, @Param("end_time")String end_time,
                                                               @Param("shiping_order_num") String shiping_order_num, @Param("shiping_order_goid") String shiping_order_goid,@Param("applicant_state") String applicant_state,
                                                               @Param("applicant_type") String applicant_type, @Param("send_mechanism") String send_mechanism,@Param("end_address") String end_address, @Param("receipt_name") String receipt_name,@Param("suppliersId") String suppliersId);

    /**
     * 供应商--运单调整列表总数
     *
     * @param start_time
     * @param end_time
     * @param shiping_order_num
     * @param shiping_order_goid
     * @param applicant_state
     * @param applicant_type
     * @param send_mechanism
     * @param end_address
     * @param receipt_name
     * @param suppliersId
     * @return
     */
    int suppliersGetCountWaybillAdjustment(@Param("start_time") String start_time, @Param("end_time") String end_time,
                                           @Param("shiping_order_num") String shiping_order_num, @Param("shiping_order_goid") String shiping_order_goid, @Param("applicant_state") String applicant_state,
                                           @Param("applicant_type") String applicant_type, @Param("send_mechanism") String send_mechanism, @Param("end_address") String end_address, @Param("receipt_name") String receipt_name, @Param("suppliersId") String suppliersId);

    /**
     * 根据运单id查询运单信息
     * @param orderId
     * @return
     */
    ShippingOrder getShppingOrderMessage(@Param("orderId") String orderId);

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
    int saveWaybillAdjustmentHistoryList(@Param("waybillAdjustmentId") String waybillAdjustmentId,@Param("list") List<WaybillAdjustmentHistory> list);

    /**
     * 更新运单状态
     * @param orderId
     * @param state
     * @return
     */
    int updateOrderState(@Param("orderId") String orderId,@Param("state") String state);

    /**
     * 根据id 获取运单调整信息
     * @param id
     * @return
     */
    WaybillAdjustment getWaybillAuditInfo(@Param("id") String id);

    /**
     * 根据id获取运单调整的调整记录
     * @param id
     * @return
     */
    List<WaybillAdjustmentHistory> getWaybillAuditHistoryListInfo(@Param("id") String id);

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
    int deleteSignOrderMessage(@Param("orderId") String orderId);

    /**
     * 更新运单状态和审核状态
     *
     * @param orderId
     * @param orderState
     * @param waybillState
     * @return
     */
    int updateOrderWaybillAudit(@Param("orderId") String orderId, @Param("orderState") String orderState, @Param("waybillState") String waybillState);

    /**
     *获取运单调整后保存的信息
     * @param waybill_adjustment_id
     * @return
     */
    WaybillAdjustmentOrder getWaybillAdjustmentOrderInfo(@Param("waybill_adjustment_id") String waybill_adjustment_id);

}
