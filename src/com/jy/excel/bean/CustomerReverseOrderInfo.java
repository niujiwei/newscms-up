package com.jy.excel.bean;

import com.jy.common.DateUtils;
import com.jy.common.UUIDUtils;
import com.jy.model.ReverserOrderModel;
import com.jy.model.User;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/22/022.
 */
public class CustomerReverseOrderInfo extends AbstractReverseOrderInfo {
    @Override
    Map<String, Integer> getReverseMapInfo() {
        Map<String,Integer> map = new HashMap<String,Integer>();
        map.put(ORDER_TYPE,0);
        map.put(SHIPING_ORDER_NUM,1);
        map.put(END_ADDRESS,2);
        map.put(TIME_LIMITATION,3);
        map.put(CUSTOM_NAME,4);
        map.put(CUSTOM_CONTACT,5);
        map.put(CUSTOM_TEL,6);
        map.put(SHIPING_ORDER_GOID,7);
        map.put(GOODS_NAME,8);
        map.put(GOODS_VOL,9);
        map.put(GOODS_WEIGHT,10);
        map.put(GOODS_NUM,11);
        map.put(END_MECHANISM,12);
        map.put(RECEIPT_NAME,13);
        map.put(RECEIPT_ADDRESS,14);
        map.put(RECEIPT_CONTACT,15);
        map.put(RECEIPT_TEL,16);
        map.put(REMARKS,17);
        return map;
    }

    @Override
    public void getReverserOrderModel(String[] data, ReverserOrderModel reverserOrderModel) {
        Map<String,Integer> map = getReverseMapInfo();
        reverserOrderModel.setShiping_order_id(UUIDUtils.getUUID());
        if (data[map.get(ORDER_TYPE)].equals("调拨")) {
            reverserOrderModel.setOrder_type(1);
        } else if (data[map.get(ORDER_TYPE)].equals("退货")) {
            reverserOrderModel.setOrder_type(0);
        }
        reverserOrderModel.setShiping_order_num(data[map.get(SHIPING_ORDER_NUM)]);
        reverserOrderModel.setEnd_address(data[map.get(END_ADDRESS)]);
        reverserOrderModel.setTime_limitation(data[map.get(TIME_LIMITATION)]);
        reverserOrderModel.setCustom_name(data[map.get(CUSTOM_NAME)]);
        reverserOrderModel.setCustom_contact(data[map.get(CUSTOM_CONTACT)]);
        reverserOrderModel.setCustom_tel(data[map.get(CUSTOM_TEL)]);
        reverserOrderModel.setShiping_order_goid(data[map.get(SHIPING_ORDER_GOID)]);
        reverserOrderModel.setGoods_name(data[map.get(GOODS_NAME)]);
        reverserOrderModel.setGoods_vol(data[map.get(GOODS_VOL)]);
        reverserOrderModel.setGoods_weight(data[map.get(GOODS_WEIGHT)]);
        reverserOrderModel.setGoods_num(data[map.get(GOODS_NUM)]);
        reverserOrderModel.setEnd_mechanism(data[map.get(END_MECHANISM)]);
        reverserOrderModel.setReceipt_name(data[map.get(RECEIPT_NAME)]);
        reverserOrderModel.setReceipt_contact(data[map.get(RECEIPT_CONTACT)]);
        reverserOrderModel.setReceipt_tel(data[map.get(RECEIPT_TEL)]);
        reverserOrderModel.setReceipt_address(data[map.get(RECEIPT_ADDRESS)]);
        reverserOrderModel.setRemarks_order(data[map.get(REMARKS)]);
        reverserOrderModel.setSend_mechanism(getUser().getCustomer_name());
        reverserOrderModel.setCreate_type(getUser().getFlag());
        reverserOrderModel.setShipping_order(getUser().getUsername());
        reverserOrderModel.setShipping_order_state(0);
        reverserOrderModel.setReal_num("0");
        reverserOrderModel.setSend_station(getUser().getUser_address());
        reverserOrderModel.setSend_time(DateUtils.format(new Date(), "yyyy-MM-dd"));


    }
}
