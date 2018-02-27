package com.jy.model;

/**
 * Created by Administrator on 2017/2/21/021.
 */
public class WaybillAdjustmentHistory {
    private String id;
    private String waybill_adjustment_id;//调整id
    private String waybill_adjustment_tittle;//调整的字段
    private String waybill_adjustment_message;//调整运单信息
    private String waybill_adjustment_after;//修改之后的值
    private String waybill_adjustment_before;//修改之前的值

    public String getWaybill_adjustment_tittle() {
        return waybill_adjustment_tittle;
    }

    public void setWaybill_adjustment_tittle(String waybill_adjustment_tittle) {
        this.waybill_adjustment_tittle = waybill_adjustment_tittle;
    }

    public String getWaybill_adjustment_after() {
        return waybill_adjustment_after;
    }

    public void setWaybill_adjustment_after(String waybill_adjustment_after) {
        this.waybill_adjustment_after = waybill_adjustment_after;
    }

    public String getWaybill_adjustment_before() {
        return waybill_adjustment_before;
    }

    public void setWaybill_adjustment_before(String waybill_adjustment_before) {
        this.waybill_adjustment_before = waybill_adjustment_before;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWaybill_adjustment_id() {
        return waybill_adjustment_id;
    }

    public void setWaybill_adjustment_id(String waybill_adjustment_id) {
        this.waybill_adjustment_id = waybill_adjustment_id;
    }

    public String getWaybill_adjustment_message() {
        return waybill_adjustment_message;
    }

    public void setWaybill_adjustment_message(String waybill_adjustment_message) {
        this.waybill_adjustment_message = waybill_adjustment_message;
    }
}
