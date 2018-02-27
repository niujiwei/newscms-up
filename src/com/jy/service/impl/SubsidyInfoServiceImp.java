package com.jy.service.impl;

import com.jy.common.UUIDUtils;
import com.jy.dao.RemarkMapDao;
import com.jy.dao.SubsidyInfoDao;
import com.jy.model.ShippingOrder;
import com.jy.model.SubsidyHistoryModel;
import com.jy.model.SubsidyInfoModel;
import com.jy.model.User;
import com.jy.service.SubsidyInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service("SubsidyInfoServiceImp")
public class SubsidyInfoServiceImp implements SubsidyInfoService {
    @Resource
    private SubsidyInfoDao subsidyInfoDao;
    @Resource
    private RemarkMapDao rms_dao;

    @Override
    public Map<String, Object> getSubsidyInfoMap(User user, Integer rows, Integer page,String start_time, String end_time, String shiping_order_num, String shiping_order_goid, String send_mechanism, String end_address, String receipt_name, String end_mechanism, String result, String state) {
        List<SubsidyInfoModel> list = new ArrayList<SubsidyInfoModel>();
        int count = 0;
        Map<String,Object> map = new HashMap<String,Object>();
        if (user != null) {
            if (user.getFlag().equals("0")) {// 管理员
                list = subsidyInfoDao.getSubsidyInfoList(rows, page,start_time, end_time,shiping_order_num, shiping_order_goid, send_mechanism, end_address, receipt_name, end_mechanism, result, state);
                count = subsidyInfoDao.getSubsidyInfoCount(start_time, end_time,shiping_order_num,shiping_order_goid,send_mechanism,end_address,receipt_name,end_mechanism,result,state);
            } else if (user.getFlag().equals("1")) {// 司机

            } else if (user.getFlag().equals("2")) {// 供应商
                List<String> user_address = rms_dao.getAddressSupperlies(user
                        .getSuppliers_id());
                list = subsidyInfoDao.supplierGetSubsidyInfoList(rows, page, start_time, end_time, shiping_order_num, shiping_order_goid, send_mechanism, end_address, receipt_name, end_mechanism, result, state, user_address, user.getSuppliers_id());
                count = subsidyInfoDao.supplierGetSubsidyInfoCount(start_time, end_time, shiping_order_num, shiping_order_goid, send_mechanism, end_address, receipt_name, end_mechanism, result, state, user_address, user.getSuppliers_id());
            } else if (user.getFlag().equals("3") || user.getFlag().equals("4")) {// 信息员
                list = subsidyInfoDao.otherGetSubsidyInfoList(rows, page, start_time, end_time, shiping_order_num, shiping_order_goid, send_mechanism, end_address, receipt_name, end_mechanism, result, state, user.getCustomer_id());
                count =subsidyInfoDao.otherGetSubsidyInfoCount( start_time, end_time, shiping_order_num, shiping_order_goid, send_mechanism, end_address, receipt_name, end_mechanism, result, state, user.getCustomer_id());
            }
        }
        map.put("total", count);
        map.put("rows", list);
        return map;
    }

    @Override
    public boolean addSubsidyInfo(SubsidyInfoModel subsidyInfoModel) {
        List<SubsidyInfoModel> list = new ArrayList<SubsidyInfoModel>();
        list.add(subsidyInfoModel);
        int i =0;
        if (list.size()>0)
            i=subsidyInfoDao.batchInsertSubsidyInfo(list);
        return i>0;
    }

    @Override
    public boolean deleteSubsidyInfo(List<String> list) {
        int i =0;
        if (list!=null&&list.size()>0)
           i= subsidyInfoDao.deleteSubsidyInfo(list);
        return i>0;
    }

    @Override
    public boolean updateSubsidyInfo(SubsidyInfoModel subsidyInfoModel) {
        int i =0;
        List<SubsidyInfoModel> list = new ArrayList<SubsidyInfoModel>();
        list.add(subsidyInfoModel);
        if (list.size()>0)
            i = subsidyInfoDao.bathUpdateSubsidyInfo(list);
        return i>0;
    }

    @Override
    public int batchUpdateSubsidyInfo(List<SubsidyInfoModel> subsidyInfoModels) {
        int i = 0;
        if (subsidyInfoModels!=null&&subsidyInfoModels.size()>0)
            i=subsidyInfoDao.bathUpdateSubsidyInfo(subsidyInfoModels);
        return i;
    }

    @Override
    public boolean saveSubsidyHistory(List<SubsidyHistoryModel> historyModels) {
        int i =0;
        if(historyModels!=null&&historyModels.size()>0)
            i = subsidyInfoDao.saveSubsidyHistory(historyModels);
        return i>0;
    }

    @Override
    public List<ShippingOrder> getShippingOrder(String shipping_order_num, String shipping_order_goid) {

        return subsidyInfoDao.getShippingOrderInfo(shipping_order_num, shipping_order_goid);
    }

    @Override
    public List<SubsidyInfoModel> getSubsidyInfoModel(String shipping_order_num, String shipping_order_goid) {

        return subsidyInfoDao.getSubsidyInfoModelList(shipping_order_num, shipping_order_goid);
    }

    @Override
    public List<SubsidyInfoModel> outPutSubsidyInfoModel(User user,String[] id, String start_time,String end_time,String shiping_order_num, String shiping_order_goid, String send_mechanism, String end_address, String receipt_name, String end_mechanism, String result, String state) {
        List<String> list = Arrays.asList(id);
        List<SubsidyInfoModel> subsidyInfoModels = new ArrayList<SubsidyInfoModel>();

        if (list.size()>0){
            subsidyInfoModels= subsidyInfoDao.getSubsidyInfoModelByIds(list);
        }else {
            int count;
            if (user != null) {
                if (user.getFlag().equals("0")) {// 管理员
                    count = subsidyInfoDao.getSubsidyInfoCount(start_time, end_time,shiping_order_num,shiping_order_goid,send_mechanism,end_address,receipt_name,end_mechanism,result,state);
                    subsidyInfoModels = subsidyInfoDao.getSubsidyInfoList(0, count, start_time, end_time,shiping_order_num, shiping_order_goid, send_mechanism, end_address, receipt_name, end_mechanism, result, state);

                } else if (user.getFlag().equals("1")) {// 司机

                } else if (user.getFlag().equals("2")) {// 供应商
                    List<String> user_address = rms_dao.getAddressSupperlies(user
                            .getSuppliers_id());
                    count = subsidyInfoDao.supplierGetSubsidyInfoCount(start_time, end_time, shiping_order_num, shiping_order_goid, send_mechanism, end_address, receipt_name, end_mechanism, result, state, user_address, user.getSuppliers_id());

                    subsidyInfoModels = subsidyInfoDao.supplierGetSubsidyInfoList(0, count, start_time, end_time, shiping_order_num, shiping_order_goid, send_mechanism, end_address, receipt_name, end_mechanism, result, state, user_address, user.getSuppliers_id());

                } else if (user.getFlag().equals("3") || user.getFlag().equals("4")) {// 信息员
                    count =subsidyInfoDao.otherGetSubsidyInfoCount( start_time, end_time, shiping_order_num, shiping_order_goid, send_mechanism, end_address, receipt_name, end_mechanism, result, state, user.getCustomer_id());
                    subsidyInfoModels = subsidyInfoDao.otherGetSubsidyInfoList(0, count, start_time, end_time, shiping_order_num, shiping_order_goid, send_mechanism, end_address, receipt_name, end_mechanism, result, state, user.getCustomer_id());
                }
            }
        }
        return subsidyInfoModels;
    }

    @Override
    public int batchInsertSubsidyInfo(List<SubsidyInfoModel> list) {
        int i = 0;
        if (list!=null&&list.size()>0)
           i= subsidyInfoDao.batchInsertSubsidyInfo(list);
        return i;
    }

    @Override
    public SubsidyInfoModel getSubsidyInfoModelById(String id) {

        return subsidyInfoDao.getSubsidyInfoModelById(id);
    }


    @Override
    public Map<String, Object> getAddSubsidyInfo(Integer rows, Integer page,User user,String startTime, String endTime, String shiping_order_num, String shipperorder_id, String send_mechanism, String end_address,String order_type) {
        List<ShippingOrder> list = new ArrayList<ShippingOrder>();
        int count = 0;
        Map<String,Object> map = new HashMap<String,Object>();
        if (user != null) {
            if (user.getFlag().equals("0")) {// 管理员
                list = subsidyInfoDao.getAddOrderInfo(rows,page,startTime,endTime,shiping_order_num,shipperorder_id,send_mechanism,end_address);
                count = subsidyInfoDao.getAddOrderCount(startTime, endTime,shiping_order_num,shipperorder_id,send_mechanism,end_address);
            } else if (user.getFlag().equals("1")) {// 司机
                list = subsidyInfoDao.driverGetAddOrderInfo(rows, page, startTime, endTime, shiping_order_num, shipperorder_id, send_mechanism, end_address, user.getDriver_id());
                count = subsidyInfoDao.driverGetAddOrderCount( startTime, endTime, shiping_order_num, shipperorder_id, send_mechanism, end_address, user.getDriver_id());

            } else if (user.getFlag().equals("2")) {// 供应商
                List<String> user_address = rms_dao.getAddressSupperlies(user
                        .getSuppliers_id());
                list = subsidyInfoDao.supplierAddOrderInfo(rows, page, startTime, endTime, shiping_order_num, shipperorder_id, send_mechanism, end_address, user.getSuppliers_id(), user_address);
                count = subsidyInfoDao.supplierGetAddOrderCount(startTime, endTime,shiping_order_num,shipperorder_id,send_mechanism,end_address,user.getSuppliers_id(), user_address);
            } else if (user.getFlag().equals("3") || user.getFlag().equals("4")) {// 信息员
                list = subsidyInfoDao.otherGetAddOrderInfo(rows, page, startTime, endTime, shiping_order_num, shipperorder_id, send_mechanism, end_address, user.getCustomer_id());
                count = subsidyInfoDao.otherGetAddOrderInfoCount( startTime, endTime, shiping_order_num, shipperorder_id, send_mechanism, end_address, user.getCustomer_id());
            }
        }
        map.put("total", count);
        map.put("rows", list);
        return map;
    }

    @Override
    public ShippingOrder getShippingOrderMessage(String id) {
        return subsidyInfoDao.getShppingOrderMessage(id);
    }

    @Override
    public int cancelAudit(List<String> ids, User user) {

        if (ids==null||ids.size()==0) return 0;
        if (user==null) return 0;
        int i=0;
        if (user.getFlag().equals("0")) {// 管理员
            i=subsidyInfoDao.adminCancelAudit(ids);
        } else if (user.getFlag().equals("1")) {// 司机


        } else if (user.getFlag().equals("2")) {// 供应商

        } else if (user.getFlag().equals("3") || user.getFlag().equals("4")) {// 信息员
            i=subsidyInfoDao.customerCancelAudit(ids);
        }
        if(i>0){
            List<SubsidyHistoryModel> list = new ArrayList<SubsidyHistoryModel>();
            SubsidyHistoryModel subsidyHistoryModel;
            for (String id:ids){
                subsidyHistoryModel =  new SubsidyHistoryModel();
                subsidyHistoryModel.setId(UUIDUtils.getUUID());
                subsidyHistoryModel.setMessage("撤销补助审核信息");
                subsidyHistoryModel.setSubsidy_id(id);
                subsidyHistoryModel.setUser_real_name(user.getRealName());
                subsidyHistoryModel.setType(Integer.parseInt(user.getFlag()));
                subsidyHistoryModel.setUser_name(user.getUsername());
                //subsidyHistoryModel.setShiping_order_num(subsidyInfoModel.getShiping_order_num());
                list.add(subsidyHistoryModel);
                if (list.size()>0) subsidyInfoDao.saveSubsidyHistory(list);

            }
        }
        return i;
    }
}
