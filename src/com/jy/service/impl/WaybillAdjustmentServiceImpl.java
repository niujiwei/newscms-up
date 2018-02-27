package com.jy.service.impl;

import com.jy.dao.WaybillAdjustmentDao;
import com.jy.model.*;
import com.jy.service.WaybillAdjustmentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2017/2/22/022.
 */
@Service("WaybillAdjustmentService")
public class WaybillAdjustmentServiceImpl implements WaybillAdjustmentService {
    @Resource
    private WaybillAdjustmentDao waybillAdjustmentDao;

    @Override
    public int getCountWaybillAdjustment(User user, String start_time, String end_time, String shiping_order_num, String shiping_order_goid, String applicant_state, String applicant_type, String send_mechanism, String end_address, String receipt_name) {
        int count = 0;
        if (user != null) {
            if (user.getFlag().equals("0")) {// 管理员
                count = waybillAdjustmentDao.getCountWaybillAdjustment(start_time,end_time,shiping_order_num,shiping_order_goid,applicant_state,applicant_type,send_mechanism,end_address,receipt_name);
            } else if (user.getFlag().equals("1")) {// 司机
                count = waybillAdjustmentDao.driverGetCountWaybillAdjustment(start_time,end_time,shiping_order_num,shiping_order_goid,applicant_state,applicant_type,send_mechanism,end_address,receipt_name,user.getId());
            } else if (user.getFlag().equals("2")) {// 供应商
                count = waybillAdjustmentDao.suppliersGetCountWaybillAdjustment(start_time,end_time,shiping_order_num,shiping_order_goid,applicant_state,applicant_type,send_mechanism,end_address,receipt_name,user.getSuppliers_id());
            } else if (user.getFlag().equals("3") || user.getFlag().equals("4")) {// 信息员
                count = waybillAdjustmentDao.driverGetCountWaybillAdjustment(start_time,end_time,shiping_order_num,shiping_order_goid,applicant_state,applicant_type,send_mechanism,end_address,receipt_name,user.getId());
            }
        }
        return count;
    }

    @Override
    public List<WaybillAdjustment> getWaybillAdjustmentListInfo(User user,int rows, int page, String start_time, String end_time, String shiping_order_num, String shiping_order_goid, String applicant_state, String applicant_type, String send_mechanism, String end_address, String receipt_name) {
        List<WaybillAdjustment> list = null;
        if (user != null) {
            if (user.getFlag().equals("0")) {// 管理员
                list = waybillAdjustmentDao.getWaybillAdjustmentListInfo(rows,page,start_time,end_time,shiping_order_num,shiping_order_goid,applicant_state,applicant_type,send_mechanism,end_address,receipt_name);
            } else if (user.getFlag().equals("1")) {// 司机
                list = waybillAdjustmentDao.driverGetWaybillAdjustmentListInfo(rows,page,start_time,end_time,shiping_order_num,shiping_order_goid,applicant_state,applicant_type,send_mechanism,end_address,receipt_name,user.getId());
            } else if (user.getFlag().equals("2")) {// 供应商
                list = waybillAdjustmentDao.suppliersGetWaybillAdjustmentListInfo(rows,page,start_time,end_time,shiping_order_num,shiping_order_goid,applicant_state,applicant_type,send_mechanism,end_address,receipt_name,user.getSuppliers_id());
            } else if (user.getFlag().equals("3") || user.getFlag().equals("4")) {// 信息员
                list = waybillAdjustmentDao.driverGetWaybillAdjustmentListInfo(rows,page,start_time,end_time,shiping_order_num,shiping_order_goid,applicant_state,applicant_type,send_mechanism,end_address,receipt_name,user.getId());
            }
        }
        return list;
    }

    @Override
    public ShippingOrder getShppingOrderMessage(String orderId) {
        return waybillAdjustmentDao.getShppingOrderMessage(orderId);
    }

    @Override
    public int saveWaybillAdjustmentInfo(WaybillAdjustment waybillAdjustment) {
        return waybillAdjustmentDao.saveWaybillAdjustmentInfo(waybillAdjustment);
    }

    @Override
    public int saveWaybillAdjustment(WaybillAdjustmentOrder waybillAdjustmentOrder) {
        return waybillAdjustmentDao.saveWaybillAdjustment(waybillAdjustmentOrder);
    }

    @Override
    public int saveWaybillAdjustmentHistoryList(String waybillAdjustmentId, List<WaybillAdjustmentHistory> list) {
        return waybillAdjustmentDao.saveWaybillAdjustmentHistoryList(waybillAdjustmentId,list);
    }

    @Override
    public int updateOrderState(String orderId, String state) {
        return waybillAdjustmentDao.updateOrderState(orderId,state);
    }

    @Override
    public WaybillAdjustment getWaybillAuditInfo(String id) {
        return waybillAdjustmentDao.getWaybillAuditInfo(id);
    }

    @Override
    public List<WaybillAdjustmentHistory> getWaybillAuditHistoryListInfo(String id) {

        return waybillAdjustmentDao.getWaybillAuditHistoryListInfo(id);
    }

    @Override
    public int doWaybillAuditInfo(WaybillAdjustment waybillAdjustment) {
        return waybillAdjustmentDao.doWaybillAuditInfo(waybillAdjustment);
    }

    @Override
    public int deleteSignOrderMessage(String orderId) {
        return waybillAdjustmentDao.deleteSignOrderMessage(orderId);
    }

    @Override
    public int updateOrderWaybillAudit(String orderId, String orderState, String waybillState) {
        return waybillAdjustmentDao.updateOrderWaybillAudit(orderId,orderState,waybillState);
    }

    @Override
    public WaybillAdjustmentOrder getWaybillAdjustmentOrderInfo(String waybill_adjustment_id) {
        return waybillAdjustmentDao.getWaybillAdjustmentOrderInfo(waybill_adjustment_id);
    }

}
