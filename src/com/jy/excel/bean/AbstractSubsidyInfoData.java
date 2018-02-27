package com.jy.excel.bean;

import com.jy.common.SpringContextHolder;
import com.jy.common.UUIDUtils;
import com.jy.model.SubsidyHistoryModel;
import com.jy.model.SubsidyInfoModel;
import com.jy.model.User;
import com.jy.service.SubsidyInfoService;
import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public abstract class AbstractSubsidyInfoData {
    static SubsidyInfoService subsidyInfoService = SpringContextHolder.getBean("SubsidyInfoServiceImp");
    static final String DELIVERY_DATE="delivery_date";//配送时间
    static final String SHIPING_ORDER_NUM="shiping_order_num";//订单号
    static final String SHIPING_ORDER_GOID="shiping_order_goid";//出货订单号
    static final String SUPER_FAR_SUBSIDY = "super_far_subsidy";//超远
    static final String UPSTAIRS_SUBSIDY="upstairs_subsidy";//上楼
    static final String SUPER_VOLUME_SUBSIDY ="super_volume_subsidy";//超体积
    static final String OTHER_SUBSIDY="other_subsidy";//其他补助
    static final String REMARKS ="remarks";//备注
    static final String CUSTOMER_SUBSIDY="customer_subsidy";//客户评价
    static final String CUSTOMER_REMARKS="customer_remarks";//客户备注
    static final String ADMIN_SUBSIDY="admin_subsidy";//管理员备注
    static final String ADMIN_REMARKS="admin_remarks";//管理员备注
    static final String WHETHER_THROUGH="whether_through";//是否通过

    private static final String MESSAGE="message";
    private static final String ORDER_NUM="order_num";
    private static final String ORDER_GOID="order_goid";
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    static Map<String,Integer> map;
    static {
        map = new HashMap<String, Integer>();
        map.put(DELIVERY_DATE,0);
        map.put(SHIPING_ORDER_NUM,1);
        map.put(SHIPING_ORDER_GOID,2);
        map.put(SUPER_FAR_SUBSIDY,3);
        map.put(UPSTAIRS_SUBSIDY,4);
        map.put(SUPER_VOLUME_SUBSIDY,5);
        map.put(OTHER_SUBSIDY,6);
        map.put(REMARKS,7);
        map.put(ADMIN_SUBSIDY,17);
        map.put(ADMIN_REMARKS,18);
        map.put(WHETHER_THROUGH,19);
        map.put(CUSTOMER_SUBSIDY,15);
        map.put(CUSTOMER_REMARKS,16);
    }

    public abstract SubsidyInfoModel getSubsidyInfoModel(String[] data);

    public abstract boolean validateSubsidyInfo(SubsidyInfoModel subsidyInfoModel, JSONObject jsonObject);

    void putJsonObjectMessage(String shipping_order_num, String shiping_order_goid,String message, JSONObject jsonObject) {
        jsonObject.put(ORDER_NUM,shipping_order_num);
        jsonObject.put(ORDER_GOID,shiping_order_goid);
        jsonObject.put(MESSAGE, shipping_order_num+":"+ message);
    }

    SubsidyHistoryModel getSubsidyHistoryModel(SubsidyInfoModel subsidyInfoModel, SubsidyHistoryModel subsidyHistoryModel){
        subsidyHistoryModel.setId(UUIDUtils.getUUID());
        subsidyHistoryModel.setShiping_order_num(subsidyHistoryModel.getShiping_order_num());
        subsidyHistoryModel.setSubsidy_id(subsidyInfoModel.getId());
        subsidyHistoryModel.setUser_name(user.getUsername());
        subsidyHistoryModel.setUser_real_name(user.getRealName());
        subsidyHistoryModel.setType(Integer.parseInt(user.getFlag()));
        String message="";
        if (user!=null){
            if (user.getFlag().equals("1")||user.getFlag().equals("2")){
                message="补助申请信息已经导入";
            }
            if (user.getFlag().equals("0")){
                message="补助申请信息管理员已经审核";
            }
            if (user.getFlag().equals("3")||user.getFlag().equals("4")){
                message="补助申请信息项目已经审核";
            }
        }
        subsidyHistoryModel.setMessage(message);
        return subsidyHistoryModel;
    }

    public abstract int saveOrUpdateSubsidyInfo(List<SubsidyInfoModel> list);


}
