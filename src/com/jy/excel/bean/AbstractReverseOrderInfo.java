package com.jy.excel.bean;

import com.jy.common.DateUtils;
import com.jy.common.SpringContextHolder;
import com.jy.common.UUIDUtils;
import com.jy.common.ValidateUntil;
import com.jy.model.*;
import com.jy.service.ReverseOrderInfoService;
import com.jy.service.SubsidyInfoService;
import net.sf.json.JSONObject;

import java.text.DateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/22/022.
 */
public abstract class AbstractReverseOrderInfo {
    //获取逆向物流Service
    static ReverseOrderInfoService reverseOrderInfoService = SpringContextHolder.getBean("ReverseOrderInfoService");
    static final String ORDER_TYPE="order_type";//订单类型
    static final String SHIPING_ORDER_NUM="shiping_order_num";//订单号
    static final String SHIPING_ORDER_GOID="shiping_order_goid";//出货订单号
    static final String SEND_MECHANISM ="send_mechanism";//受理机构
    static final String SEND_TIME ="send_time";//起运时间
    static final String END_ADDRESS="end_address";//调拨/退货始发位置

    static final String END_MECHANISM="end_mechanism";//调拨/退货终到位置

    static final String CUSTOM_NAME="custom_name";////发货客户名称
    static final String CUSTOM_CONTACT="custom_contact";//客户联系人
    static final String CUSTOM_TEL="custom_tel";//发货客户电话

    static final String GOODS_NAME="goods_name";//货物名称
    static final String GOODS_VOL="goods_vol";//总体积
    static final String GOODS_WEIGHT="goods_weight";//总重量

    static final String GOODS_NUM="goods_num";//总件数
    static final String REAL_NUM="real_num";//实际件数

    static final String RECEIPT_NAME = "receipt_name"; //收货客户名称
    static final String RECEIPT_CONTACT="receipt_contact";//收货客户联系人
    static final String RECEIPT_TEL="receipt_tel";//收货客户联系电话
    static final String REMARKS="remarks";//备注
    static final String TIME_LIMITATION="time_limitation";//提货时效
    static final String RECEIPT_ADDRESS="receipt_address";//收货客户地址

    private static final String MESSAGE="message";
    private static final String ORDER_NUM="order_num";

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    abstract Map<String,Integer>  getReverseMapInfo();

    public abstract void getReverserOrderModel(String[] data,ReverserOrderModel reverserOrderModel);

    public boolean validateReverserInfo(JSONObject jsonObject, ReverserOrderModel reverserOrderModel) {
        boolean b = ValidateUntil.isNumber(reverserOrderModel.getReal_num())&&ValidateUntil.isNumber(reverserOrderModel.getGoods_num());
        if (!b){
            jsonObject.put(MESSAGE,"总件数或者实际件数不为数字");
            jsonObject.put(ORDER_NUM,reverserOrderModel.getShiping_order_num()==null||
                    reverserOrderModel.getShiping_order_num().equals("")?"受理机构为:"+reverserOrderModel.getSend_mechanism():
                    "订单号为:"+reverserOrderModel.getShiping_order_num());
            return false;
        }
        if (!ValidateUntil.isNumber(reverserOrderModel.getGoods_vol())){
            jsonObject.put(MESSAGE,"体积不为数字");
            jsonObject.put(ORDER_NUM,reverserOrderModel.getShiping_order_num()==null||
                    reverserOrderModel.getShiping_order_num().equals("")?"受理机构为:"+reverserOrderModel.getSend_mechanism():
                    "订单号为:"+reverserOrderModel.getShiping_order_num());
            return false;
        }
        if (!ValidateUntil.isNumber(reverserOrderModel.getGoods_weight())){
            jsonObject.put(MESSAGE,"重量不为数字");
            jsonObject.put(ORDER_NUM,reverserOrderModel.getShiping_order_num()==null||
                    reverserOrderModel.getShiping_order_num().equals("")?"受理机构为:"+reverserOrderModel.getSend_mechanism():
                    "订单号为:"+reverserOrderModel.getShiping_order_num());
            return false;
        }
        boolean bb;
        if (getUser().getFlag().equals("1")||getUser().getFlag().equals("2")) {
            bb = reverseOrderInfoService.getReverseOrderCountByNum(reverserOrderModel.getShiping_order_num(), reverserOrderModel.getShiping_order_goid());
            if (!bb) {
                jsonObject.put(MESSAGE, "该运单信息已经存在数据库中");
                jsonObject.put(ORDER_NUM, reverserOrderModel.getShiping_order_num() == null ||
                        reverserOrderModel.getShiping_order_num().equals("") ? "受理机构为:" + reverserOrderModel.getSend_mechanism() :
                        "订单号为:" + reverserOrderModel.getShiping_order_num());
                return false;
            }

        }
        if (!ValidateUntil.isValidDate(reverserOrderModel.getSend_time())){
            jsonObject.put(MESSAGE, "起运时间格式不正确");
            jsonObject.put(ORDER_NUM, reverserOrderModel.getShiping_order_num() == null ||
                    reverserOrderModel.getShiping_order_num().equals("") ? "受理机构为:" + reverserOrderModel.getSend_mechanism() :
                    "订单号为:" + reverserOrderModel.getShiping_order_num());
            return false;
        }

        return true;
    }

    public void getOrderHistoryInfo(OrderHistory orderHistory,ReverserOrderModel reverserOrderModel,String message){
        orderHistory.setOrder_history_id(UUIDUtils.getUUID());
        orderHistory.setOrders_id(reverserOrderModel.getShiping_order_id());
        orderHistory.setOrder_arrive_time(DateFormat.getDateTimeInstance().format(new Date()));
        orderHistory.setGoods_arrive_remakes(orderHistory.getOrder_arrive_time()+"---"+reverserOrderModel.getShiping_order_num()+message);
    }

    public void getShippingOrder(ReverserOrderModel reverserOrderModel,ShippingOrder shippingOrder){
        if (reverserOrderModel==null) return ;
        shippingOrder.setShiping_order_id(reverserOrderModel.getShiping_order_id());
        shippingOrder.setEnd_address(reverserOrderModel.getEnd_mechanism());
        shippingOrder.setShipping_order_state(0);
        shippingOrder.setRemarks("逆向物流调拨");
        shippingOrder.setGoods_num(Integer.parseInt(reverserOrderModel.getReal_num()));
        shippingOrder.setSend_mechanism(reverserOrderModel.getSend_mechanism());
        shippingOrder.setShiping_order_num(reverserOrderModel.getShiping_order_num());
        shippingOrder.setSend_time(DateUtils.format(new Date(), "yyyy-MM-dd"));
        shippingOrder.setReceipt_tel(reverserOrderModel.getReceipt_tel());
        shippingOrder.setReceipt_name(reverserOrderModel.getReceipt_contact());
        shippingOrder.setUpdatetime(DateUtils.format(new Date(), "yyyy-MM-dd hh:mm:ss"));
        shippingOrder.setCustom_contact(reverserOrderModel.getCustom_contact());
        shippingOrder.setCustom_tel(reverserOrderModel.getCustom_tel());
        shippingOrder.setSend_station(reverserOrderModel.getEnd_address());
    }
}
