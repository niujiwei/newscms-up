package com.jy.dao;


import com.jy.model.ShippingOrder;
import com.jy.model.SubsidyHistoryModel;
import com.jy.model.SubsidyInfoModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SubsidyInfoDao {

    /**
     * 查询补助信息
     *
     * @param start
     * @param length
     * @return
     */
    List<SubsidyInfoModel> getSubsidyInfoList(@Param("start") int start, @Param("length") int length,
                                              @Param("start_time") String start_time, @Param("end_time") String end_time,
                                              @Param("shiping_order_num") String shiping_order_num,
                                              @Param("shiping_order_goid") String shiping_order_goid,
                                              @Param("send_mechanism") String send_mechanism,
                                              @Param("end_address") String end_address,
                                              @Param("receipt_name") String receipt_name,
                                              @Param("end_mechanism") String end_mechanism,
                                              @Param("result") String result, @Param("state") String state);

    /**
     * 查询补助信息条数
     *
     * @return
     */
    int getSubsidyInfoCount(@Param("start_time") String start_time, @Param("end_time") String end_time, @Param("shiping_order_num") String shiping_order_num,
                            @Param("shiping_order_goid") String shiping_order_goid,
                            @Param("send_mechanism") String send_mechanism,
                            @Param("end_address") String end_address,
                            @Param("receipt_name") String receipt_name,
                            @Param("end_mechanism") String end_mechanism,
                            @Param("result") String result, @Param("state") String state);

    /**
     * 供应商查询补助信息
     *
     * @param start
     * @param length
     * @return
     */
    List<SubsidyInfoModel> supplierGetSubsidyInfoList(@Param("start") int start, @Param("length") int length,
                                                      @Param("start_time") String start_time, @Param("end_time") String end_time,
                                                      @Param("shiping_order_num") String shiping_order_num,
                                                      @Param("shiping_order_goid") String shiping_order_goid,
                                                      @Param("send_mechanism") String send_mechanism,
                                                      @Param("end_address") String end_address,
                                                      @Param("receipt_name") String receipt_name,
                                                      @Param("end_mechanism") String end_mechanism,
                                                      @Param("result") String result, @Param("state") String state, @Param("list") List<String> userAddress,
                                                      @Param("suppliersId") String suppliersId);

    /**
     * 供应商查询补助信息条数
     *
     * @return
     */
    int supplierGetSubsidyInfoCount(@Param("start_time") String start_time, @Param("end_time") String end_time, @Param("shiping_order_num") String shiping_order_num,
                                    @Param("shiping_order_goid") String shiping_order_goid,
                                    @Param("send_mechanism") String send_mechanism,
                                    @Param("end_address") String end_address,
                                    @Param("receipt_name") String receipt_name,
                                    @Param("end_mechanism") String end_mechanism,
                                    @Param("result") String result, @Param("state") String state, @Param("list") List<String> userAddress,
                                    @Param("suppliersId") String suppliersId);

    /**
     * 供应商查询补助信息
     *
     * @param start
     * @param length
     * @return
     */
    List<SubsidyInfoModel> otherGetSubsidyInfoList(@Param("start") int start, @Param("length") int length,
                                                   @Param("start_time") String start_time, @Param("end_time") String end_time,
                                                   @Param("shiping_order_num") String shiping_order_num,
                                                   @Param("shiping_order_goid") String shiping_order_goid,
                                                   @Param("send_mechanism") String send_mechanism,
                                                   @Param("end_address") String end_address,
                                                   @Param("receipt_name") String receipt_name,
                                                   @Param("end_mechanism") String end_mechanism,
                                                   @Param("result") String result, @Param("state") String state,
                                                   @Param("customer_id") String customer_id);

    /**
     * 供应商查询补助信息条数
     *
     * @return
     */
    int otherGetSubsidyInfoCount(@Param("start_time") String start_time, @Param("end_time") String end_time, @Param("shiping_order_num") String shiping_order_num,
                                 @Param("shiping_order_goid") String shiping_order_goid,
                                 @Param("send_mechanism") String send_mechanism,
                                 @Param("end_address") String end_address,
                                 @Param("receipt_name") String receipt_name,
                                 @Param("end_mechanism") String end_mechanism,
                                 @Param("result") String result, @Param("state") String state,
                                 @Param("customer_id") String customer_id);

    /**
     * 插入补助信息
     *
     * @param subsidyInfoModels sy
     * @return int
     */
    int batchInsertSubsidyInfo(@Param("list") List<SubsidyInfoModel> subsidyInfoModels);


    /**
     * 更新补助信息
     *
     * @param subsidyInfoModels sy
     * @return int
     */
    int bathUpdateSubsidyInfo(@Param("list") List<SubsidyInfoModel> subsidyInfoModels);

    /**
     * 删除信息
     *
     * @param ids ids
     * @return id
     */
    int deleteSubsidyInfo(@Param("list") List<String> ids);


    /**
     * 保存节点信息
     *
     * @param subsidyHistoryModelList list
     * @return int
     */
    int saveSubsidyHistory(@Param("list") List<SubsidyHistoryModel> subsidyHistoryModelList);

    /**
     * 根据出货订单号和货运编号查询运单信息
     *
     * @param shipping_order_num
     * @param shipping_order_goid
     * @return
     */
    List<ShippingOrder> getShippingOrderInfo(@Param("shiping_order_num") String shipping_order_num, @Param("shiping_order_goid") String shipping_order_goid);

    /**
     * 查询补助申请信息根据出货订单号和运单号排除未通过的
     *
     * @param shipping_order_num
     * @param shipping_order_goid
     * @return
     */
    List<SubsidyInfoModel> getSubsidyInfoModelList(@Param("shiping_order_num") String shipping_order_num, @Param("shiping_order_goid") String shipping_order_goid);

    /**
     * 根据id查询补助信息
     *
     * @param ids
     * @return
     */
    List<SubsidyInfoModel> getSubsidyInfoModelByIds(@Param("list") List<String> ids);

    /**
     * 根据一个id获取补助信息
     *
     * @param id
     * @return
     */
    SubsidyInfoModel getSubsidyInfoModelById(@Param("id") String id);

    /**获取补助信息添加运单信息
     * @return
     */
    List<ShippingOrder> getAddOrderInfo(@Param("start") Integer start,@Param("length") Integer length, @Param("startTime") String startTime, @Param("endTime") String endTime, @Param("shiping_order_num") String shiping_order_num, @Param("shiping_order_goid") String shipperorder_id, @Param("send_mechanism") String send_mechanism,@Param("end_address") String end_address);

    /**
     * 获取补助信息添加运单个数
     * @param startTime
     * @param endTime
     * @param shiping_order_num
     * @param shipperorder_id
     * @param send_mechanism
     * @param end_address
     * @return
     */
    int getAddOrderCount(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("shiping_order_num") String shiping_order_num, @Param("shiping_order_goid") String shipperorder_id, @Param("send_mechanism") String send_mechanism,@Param("end_address") String end_address);


    /**
     * 供应商获取补助运单信息
     * @param start
     * @param length
     * @param startTime
     * @param endTime
     * @param shiping_order_num
     * @param shipperorder_id
     * @param send_mechanism
     * @param end_address
     * @param suppliersId
     * @param endAddress
     * @return
     */
    List<ShippingOrder> supplierAddOrderInfo(@Param("start") Integer start, @Param("length") Integer length, @Param("startTime") String startTime, @Param("endTime") String endTime, @Param("shiping_order_num") String shiping_order_num, @Param("shiping_order_goid") String shipperorder_id, @Param("send_mechanism") String send_mechanism, @Param("end_address") String end_address,
                                             @Param("suppliersId") String suppliersId, @Param("list") List<String> endAddress);

    /**
     * 供应商
     * @param startTime
     * @param endTime
     * @param shiping_order_num
     * @param shipperorder_id
     * @param send_mechanism
     * @param end_address
     * @param suppliersId
     * @param endAddress
     * @return
     */
    int supplierGetAddOrderCount(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("shiping_order_num") String shiping_order_num, @Param("shiping_order_goid") String shipperorder_id, @Param("send_mechanism") String send_mechanism,@Param("end_address") String end_address,
                                 @Param("suppliersId") String suppliersId, @Param("list") List<String> endAddress);


    /**
     *
     * @param start
     * @param length
     * @param startTime
     * @param endTime
     * @param shiping_order_num
     * @param shipperorder_id
     * @param send_mechanism
     * @param end_address
     * @param customer_id
     * @return
     */
    List<ShippingOrder> otherGetAddOrderInfo(@Param("start") Integer start, @Param("length") Integer length, @Param("startTime") String startTime, @Param("endTime") String endTime, @Param("shiping_order_num") String shiping_order_num, @Param("shiping_order_goid") String shipperorder_id, @Param("send_mechanism") String send_mechanism, @Param("end_address") String end_address,
                                             @Param("customer_id") String customer_id);

    /**
     * @param startTime
     * @param endTime
     * @param shiping_order_num
     * @param shipperorder_id
     * @param send_mechanism
     * @param end_address
     * @param customer_id
     * @return
     */
    int otherGetAddOrderInfoCount(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("shiping_order_num") String shiping_order_num, @Param("shiping_order_goid") String shipperorder_id, @Param("send_mechanism") String send_mechanism, @Param("end_address") String end_address,
                                  @Param("customer_id") String customer_id);

    /**
     *
     * @param start
     * @param length
     * @param startTime
     * @param endTime
     * @param shiping_order_num
     * @param shipperorder_id
     * @param send_mechanism
     * @param end_address
     * @param driverId
     * @return
     */
    List<ShippingOrder> driverGetAddOrderInfo(@Param("start") Integer start, @Param("length") Integer length, @Param("startTime") String startTime, @Param("endTime") String endTime, @Param("shiping_order_num") String shiping_order_num, @Param("shiping_order_goid") String shipperorder_id, @Param("send_mechanism") String send_mechanism, @Param("end_address") String end_address,
                                             @Param("driverId") String driverId);

    /**
     *
     * @param startTime
     * @param endTime
     * @param shiping_order_num
     * @param shipperorder_id
     * @param send_mechanism
     * @param end_address
     * @param driverId
     * @return
     */
    int driverGetAddOrderCount( @Param("startTime") String startTime, @Param("endTime") String endTime, @Param("shiping_order_num") String shiping_order_num, @Param("shiping_order_goid") String shipperorder_id, @Param("send_mechanism") String send_mechanism, @Param("end_address") String end_address,
                                   @Param("driverId") String driverId);

    /**
     * 获取运单信息
     * @param id
     * @return
     */
    ShippingOrder getShppingOrderMessage(@Param("id") String id);


    int adminCancelAudit(@Param("list") List<String> ids);

    int customerCancelAudit(@Param("list")List<String> ids);

}
