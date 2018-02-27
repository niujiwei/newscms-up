package com.jy.excel.bean;

import com.jy.common.DateUtils;
import com.jy.common.UUIDUtils;
import com.jy.model.ReverserOrderModel;
import com.sun.org.apache.bcel.internal.generic.I2F;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/22/022.
 */
public class SupplierReverseOrderInfo extends AbstractReverseOrderInfo {
    @Override
     Map<String,Integer> getReverseMapInfo() {
        Map<String,Integer> map =new HashMap<String, Integer>();
        map.put(ORDER_TYPE,0);
        map.put(SHIPING_ORDER_NUM,1);
        map.put(SEND_MECHANISM,2);
        map.put(SEND_TIME,3);
        map.put(CUSTOM_NAME,4);
        map.put(CUSTOM_CONTACT,5);
        map.put(CUSTOM_TEL, 6);
        map.put(GOODS_NAME,7);
        map.put(GOODS_VOL,8);
        map.put(GOODS_WEIGHT,9);
        map.put(GOODS_NUM,10);
        map.put(REAL_NUM, 11);
        map.put(END_MECHANISM,12);
        map.put(RECEIPT_NAME,13);
        map.put(RECEIPT_CONTACT, 14);
        map.put(RECEIPT_TEL,15);
        map.put(RECEIPT_ADDRESS,16);
        map.put(REMARKS,17);
        return map;
    }

    @Override
    public void getReverserOrderModel(String[] data,ReverserOrderModel reverserOrderModel) {
        Map<String,Integer> map = getReverseMapInfo();
        reverserOrderModel.setShiping_order_id(UUIDUtils.getUUID());
        if (data[map.get(ORDER_TYPE)].equals("调拨")){
            reverserOrderModel.setOrder_type(1);
        }else if (data[map.get(ORDER_TYPE)].equals("退货")){
            reverserOrderModel.setOrder_type(0);
        }

        reverserOrderModel.setShiping_order_num(data[map.get(SHIPING_ORDER_NUM)]);
        reverserOrderModel.setSend_mechanism(data[map.get(SEND_MECHANISM)]);
        reverserOrderModel.setSend_time(data[map.get(SEND_TIME)]==null||data[map.get(SEND_TIME)].equals("")? DateUtils.format(new Date(), "yyyy-MM-dd"):data[map.get(SEND_TIME)]);
        reverserOrderModel.setCustom_name(data[map.get(CUSTOM_NAME)]);
        reverserOrderModel.setCustom_contact(data[map.get(CUSTOM_CONTACT)]);
        reverserOrderModel.setCustom_tel(data[map.get(CUSTOM_TEL)]);
        reverserOrderModel.setGoods_name(data[map.get(GOODS_NAME)]);
        reverserOrderModel.setGoods_vol(data[map.get(GOODS_VOL)]);
        reverserOrderModel.setGoods_weight(data[map.get(GOODS_WEIGHT)]);
        reverserOrderModel.setGoods_num(data[map.get(GOODS_NUM)]);
        reverserOrderModel.setReal_num(data[map.get(REAL_NUM)]);
        reverserOrderModel.setEnd_mechanism(data[map.get(END_MECHANISM)]);
        reverserOrderModel.setReceipt_name(data[map.get(RECEIPT_NAME)]);
        reverserOrderModel.setReceipt_tel(data[map.get(RECEIPT_TEL)]);
        reverserOrderModel.setReceipt_contact(data[map.get(RECEIPT_CONTACT)]);
        reverserOrderModel.setRemarks(data[map.get(REMARKS)]);
        reverserOrderModel.setReceipt_address(data[map.get(RECEIPT_ADDRESS)]);
        reverserOrderModel.setDriver_id(getUser().getDriver_id());
        reverserOrderModel.setSuppliers_id(getUser().getSuppliers_id());
        reverserOrderModel.setShipping_order(getUser().getUsername());
        reverserOrderModel.setCreate_type(getUser().getFlag());
        reverserOrderModel.setShipping_order_state(0);
        reverserOrderModel.setEnd_address(getUser().getUser_address());
        reverserOrderModel.setSend_station(getUser().getUser_address());
    }


}
