package com.jy.dao;

import com.jy.model.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by njw on 2017/7/11.
 */
public interface SCMDao {

    /**
     * 获取要传输的
     * @param time
     * @param customerId
     * @return
     */
    List<ShippingOrder> getShippingOrder(@Param("time") String time, String customerId);

    /**
     * 插入要传输的
     * @param list
     * @return
     */
    int insertShippingOrder(@Param("list") List<ShippingOrder> list);

    /**
     * 判断是否存在
     * @param orderId
     * @return
     */
    int countShippingOrder(@Param("orderId") String orderId);

    /**
     *
     * @param startState
     * @param endState
     * @return
     */
    List<ShippingOrder> getScmShippingOrder(@Param("startState") String startState,@Param("endState") String endState);

    /**
     * 获取历史信息
     * @param orderId
     * @return
     */
    List<OrderHistory> getOrderHistory(@Param("orderId") String orderId);

    /**
     * 查询要传输的
     * @param historyId
     * @return
     */
    SCMBusinessRemarkData getSCMBusinessRemarkDataOrder(@Param("historyId") String historyId);

    /**
     * 判断是否与存在
     * @param dmsId
     * @return
     */
    int getSCMBusinessCount(@Param("dmsId") String dmsId);

    /**
     * 判断是否存在
     * @param bus_id
     * @param state
     * @return
     */
    int getSCMBusinessCountSate(@Param("bus_id") String bus_id,@Param("state") String state);


    /**
     * 插入要传输的数据信息
     * @param list
     * @return
     */
    int insertSCMBusinessRemarkData(List<SCMBusinessRemarkData> list);

    /**
     * 查询要传输的信息
     * @param state
     * @param time
     * @return
     */
    List<SCMBusinessRemarkData> getSCMBusinessRemarkData(@Param("state") String state,@Param("time") String time,@Param("max_size") Integer max_size);


    /**
     * 更新传输后的状态
     * @param state
     * @param dmsIds
     * @return
     */
    int updateSCMBusinessRemarkData(@Param("state") Integer state,@Param("list") List<String> dmsIds);

    /**
     * 更新传输状态
     * @param dmsId
     * @return
     */
    int updateScmShippingOrder(@Param("state") Integer state,@Param("list") List<String> dmsId,@Param("minCount") Integer minCount,@Param("maxCount") Integer maxCount);


    int updateScmShippingOrderA(@Param("state") Integer state,@Param("list") List<SCMBusinessRemarkData> dmsId);


    /**
     * 更新时间
     * @param time
     * @return
     */
    int updateScmUpdateTime(String time);

    /**
     * 获取要插入的
     * @param size
     * @return
     */
    List<SCMSupplierUser> getInsertSCMSupplierUser(@Param("size") Integer size);

    /**
     * 获取要插入的
     * @param size
     * @return
     */
    List<SCMSupplierUser> getInsertSCMSupplierUserDay(@Param("size") Integer size);

    /**
     * 插入要查询的
     * @param list
     * @return
     */
    int insertSCMSupplierUser(List<SCMSupplierUser> list);


    /**
     * 查询要传输的
     * @param size
     * @return
     */
    List<SCMSupplierUser> getSCMSupplierUser(@Param("page")Integer page,@Param("size") Integer size,@Param("state") String state);


    int getSCMSupplierUserCount(@Param("size") Integer size,@Param("state") String state);

    /**
     * 判断是否存在
     * @param supUserId
     * @return
     */
    int selectSCMSupplierUserCount(@Param("supUserId") String supUserId);

    /**
     * 更新结果
     * @param state
     * @param ids
     * @return
     */
    int updateSCMSupplierUser(@Param("state") Integer state,@Param("list") List<String> ids);

    /**
     * 查询图片信息
     * @param size
     * @param state
     * @return
     */
    List<String> getSCMSupplierUserHead(@Param("page") Integer page,@Param("size") Integer size,@Param("state") String state);


    List<String> getSCMSupplierUserHeadImage(@Param("page") Integer page, @Param("size") Integer size, @Param("state") String state, @Param("list") List<String> ids);

    /**
     * 查询头像信息条数
     * @param state
     * @return
     */
    int getSCMSupplierUserHeadCount(@Param("state") String state);

    /**
     * 删除重复的用户信息
     * @param supplierId
     * @return
     */
    int deleteSCMSupplierUserCount(@Param("supplierId") String supplierId);
}
