package com.jy.service;


import com.jy.model.ShippingOrder;
import com.jy.model.SubsidyHistoryModel;
import com.jy.model.SubsidyInfoModel;
import com.jy.model.User;

import java.util.List;
import java.util.Map;

public interface SubsidyInfoService {

    /**
     * 获取补助信息
     *
     * @param user
     * @param rows
     * @param page
     * @param shiping_order_num
     * @param shiping_order_goid
     * @param send_mechanism
     * @param end_address
     * @param receipt_name
     * @param end_mechanism
     * @param result
     * @param state
     * @return
     */
    Map<String, Object> getSubsidyInfoMap(User user, Integer rows, Integer page,String start_time, String end_time, String shiping_order_num, String shiping_order_goid, String send_mechanism, String end_address, String receipt_name, String end_mechanism, String result, String state);



    /**
     * 添加补助信息
     * @param subsidyInfoModel
     * @return
     */
    boolean addSubsidyInfo(SubsidyInfoModel subsidyInfoModel);

    /**
     * 批量保存
     * @param list
     * @return
     */
    int batchInsertSubsidyInfo(List<SubsidyInfoModel> list);

    /**
     * 删除补助信息
     * @param list list
     * @return
     */
    boolean deleteSubsidyInfo(List<String> list);

    /**
     * 更新补助信息
     * @param subsidyInfoModel
     * @return
     */
    boolean updateSubsidyInfo(SubsidyInfoModel subsidyInfoModel);

    /**
     * 批量更新补助信息
     *
     * @param subsidyInfoModels
     * @return
     */
    int batchUpdateSubsidyInfo(List<SubsidyInfoModel> subsidyInfoModels);

    /**
     * 保存历史节点信息
     * @param historyModels
     * @return
     */
    boolean saveSubsidyHistory(List<SubsidyHistoryModel> historyModels);

    /**
     * 根据运单号查询运单信息
     * @param shipping_order_num
     * @param shipping_order_goid
     * @return
     */
    List<ShippingOrder> getShippingOrder(String shipping_order_num, String shipping_order_goid);

    /**
     * 根据出货订单号和运单号查询未审核失败的信息保证一条
     *
     * @param shipping_order_num
     * @param shipping_order_goid
     * @return
     */
    List<SubsidyInfoModel> getSubsidyInfoModel(String shipping_order_num, String shipping_order_goid);

    /**
     * @param user
     * @param shiping_order_num
     * @param shiping_order_goid
     * @param send_mechanism
     * @param end_address
     * @param receipt_name
     * @param end_mechanism
     * @param result
     * @param state
     * @return
     */
    List<SubsidyInfoModel> outPutSubsidyInfoModel(User user, String[] id, String start_time,String end_time,String shiping_order_num, String shiping_order_goid, String send_mechanism, String end_address, String receipt_name, String end_mechanism, String result, String state);

    /**
     * 获取补助信息
     * @param id
     * @return
     */
    SubsidyInfoModel getSubsidyInfoModelById(String id);

    /**
     * 获取填补运单信息
     *
     * @return
     */
    Map<String, Object> getAddSubsidyInfo(Integer rows, Integer page, User user, String startTime, String endTime, String shiping_order_num, String shipperorder_id, String send_mechanism, String end_address, String order_type);

    /**
     *
     * @param id
     * @return
     */
    ShippingOrder getShippingOrderMessage(String id);


    /**
     * 取消审核
     * @param ids
     * @param user
     * @return
     */
    int cancelAudit(List<String> ids,User user);
}
