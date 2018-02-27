package com.jy.common;

import com.jy.model.*;
import com.jy.service.OrderInfoService;
import com.jy.service.ShippingOrderInfoService;
import com.jy.service.WaybillAdjustmentService;

/**
 * Created by Administrator on 2017/4/27/027.
 */
public class WaybillAdjustmentUtils {

    private static WaybillAdjustmentService waybillAdjustmentService = SpringContextHolder.getBean("WaybillAdjustmentService");
    private static OrderInfoService orderInfoService = SpringContextHolder.getBean("OrderInfoService");;
    private static ShippingOrderInfoService shippingOrderInfoService = SpringContextHolder.getBean("ShippingOrderInfoService");

    public static boolean doCancelSign(String shiping_order_id){
        waybillAdjustmentService.deleteSignOrderMessage(shiping_order_id);
        String orderState = "2";
        int j = waybillAdjustmentService.updateOrderWaybillAudit(shiping_order_id,orderState,"3");
        return j>0;
    }

    public static boolean doUpdateOderMessage(String waybill_adjustment_id,String shiping_order_id){
        ShippingOrder shippingOrder = waybillAdjustmentService.getShppingOrderMessage(shiping_order_id);
        WaybillAdjustmentOrder waybillAdjustmentOrder = waybillAdjustmentService.getWaybillAdjustmentOrderInfo(waybill_adjustment_id);
        int i =0;
        if (shippingOrder!=null&&waybillAdjustmentOrder!=null&&!shippingOrder.getEnd_address().equals(waybillAdjustmentOrder.getEnd_address())){
            Aging aging = orderInfoService.getNewAging(waybillAdjustmentOrder.getSend_mechanism(),
                    waybillAdjustmentOrder.getEnd_address(), shippingOrder.getUpdatetime());
            if (aging != null) {
                waybillAdjustmentOrder.setAging_day(aging.getAging_day());
                waybillAdjustmentOrder.setAging_time(aging.getAging_time());
            }
            waybillAdjustmentOrder.setShipping_order_state(0);
            waybillAdjustmentOrder.setRemarks(shippingOrder.getRemarks());
            shippingOrderInfoService.deleteShipOrders(waybillAdjustmentOrder.getShiping_order_id());
            i = shippingOrderInfoService.updateShipOrders(waybillAdjustmentOrder);
        }else {
            waybillAdjustmentOrder.setShipping_order_state(shippingOrder.getShipping_order_state());
            waybillAdjustmentOrder.setDriver_city(shippingOrder.getDriver_city());
            waybillAdjustmentOrder.setDriver_county(shippingOrder.getDriver_county());
            waybillAdjustmentOrder.setDriver_province(shippingOrder.getDriver_province());
            waybillAdjustmentOrder.setAging_day(shippingOrder.getAging_day());
            waybillAdjustmentOrder.setAging_time(shippingOrder.getAging_time());
            waybillAdjustmentOrder.setRemarks(shippingOrder.getRemarks());
            i = shippingOrderInfoService.updateShipOrders(waybillAdjustmentOrder);
        }
        int j =0;
        if(i>0){
            j =  waybillAdjustmentService.updateOrderState(shiping_order_id,"3");
        }
        return j>0;
    }

    /**
     * 赋值运单调整信息
     * @param user
     * @param applicant_message
     * @param applicant_type
     * @param orderId
     * @return
     */
    public static WaybillAdjustment getWaybillAdjustment(User user, String applicant_message, int applicant_type, String orderId){
        WaybillAdjustment waybillAdjustment = new WaybillAdjustment();
        waybillAdjustment.setId(UUIDUtils.getUUID());
        waybillAdjustment.setApplicant_name(user.getRealName());
        waybillAdjustment.setApplicant_username(user.getUsername());
        waybillAdjustment.setApplicant_id(user.getId());
        waybillAdjustment.setApplicant_message(applicant_message);
        waybillAdjustment.setApplicant_type(applicant_type);
        waybillAdjustment.setApplicant_state(0);
        waybillAdjustment.setShiping_order_id(orderId);
        return waybillAdjustment;
    }
}
