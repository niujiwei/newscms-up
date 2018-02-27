package com.jy.model;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/2/21/021.
 */
public class WaybillAdjustment {
    private String id;
    private String shiping_order_id;//订单号id
    private String applicant_id;//申请人id
    private String applicant_name;//申请人姓名
    private String applicant_username;//申请人用户名
    private String applicant_time;//申请时间
    private int applicant_type;//申请类型（0 运单调整，1取消签收）
    private String applicant_message;//申请信息
    private String auditor_id;//审核人id
    private String auditor_name;//审核人姓名
    private String auditor_username;//审核人用户名
    private String auditor_time;//审核时间
    private String auditor_message;//审核信息
    private int applicant_state;//审核状态（0，未审核，1已审核）
    private ShippingOrder shippingOrder;//调整前运单信息
    private List<WaybillAdjustmentHistory> waybillAdjustmentHistories;//调整记录
    private WaybillAdjustmentOrder waybillAdjustmentOrder;//调整后的运单信息

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShiping_order_id() {
        return shiping_order_id;
    }

    public void setShiping_order_id(String shiping_order_id) {
        this.shiping_order_id = shiping_order_id;
    }

    public String getApplicant_name() {
        return applicant_name;
    }

    public void setApplicant_name(String applicant_name) {
        this.applicant_name = applicant_name;
    }

    public String getApplicant_username() {
        return applicant_username;
    }

    public void setApplicant_username(String applicant_username) {
        this.applicant_username = applicant_username;
    }


    public int getApplicant_type() {
        return applicant_type;
    }

    public void setApplicant_type(int applicant_type) {
        this.applicant_type = applicant_type;
    }

    public String getApplicant_message() {
        return applicant_message;
    }

    public void setApplicant_message(String applicant_message) {
        this.applicant_message = applicant_message;
    }

    public String getAuditor_name() {
        return auditor_name;
    }

    public void setAuditor_name(String auditor_name) {
        this.auditor_name = auditor_name;
    }

    public String getAuditor_username() {
        return auditor_username;
    }

    public void setAuditor_username(String auditor_username) {
        this.auditor_username = auditor_username;
    }

    public String getAuditor_message() {
        return auditor_message;
    }

    public void setAuditor_message(String auditor_message) {
        this.auditor_message = auditor_message;
    }

    public String getApplicant_time() {
        return applicant_time;
    }

    public void setApplicant_time(String applicant_time) {
        this.applicant_time = applicant_time;
    }

    public String getAuditor_time() {
        return auditor_time;
    }

    public void setAuditor_time(String auditor_time) {
        this.auditor_time = auditor_time;
    }

    public int getApplicant_state() {
        return applicant_state;
    }

    public void setApplicant_state(int applicant_state) {
        this.applicant_state = applicant_state;
    }

    public ShippingOrder getShippingOrder() {
        return shippingOrder;
    }

    public void setShippingOrder(ShippingOrder shippingOrder) {
        this.shippingOrder = shippingOrder;
    }

    public List<WaybillAdjustmentHistory> getWaybillAdjustmentHistories() {
        return waybillAdjustmentHistories;
    }

    public void setWaybillAdjustmentHistories(List<WaybillAdjustmentHistory> waybillAdjustmentHistories) {
        this.waybillAdjustmentHistories = waybillAdjustmentHistories;
    }

    public WaybillAdjustmentOrder getWaybillAdjustmentOrder() {
        return waybillAdjustmentOrder;
    }

    public void setWaybillAdjustmentOrder(WaybillAdjustmentOrder waybillAdjustmentOrder) {
        this.waybillAdjustmentOrder = waybillAdjustmentOrder;
    }

    public String getApplicant_id() {
        return applicant_id;
    }

    public void setApplicant_id(String applicant_id) {
        this.applicant_id = applicant_id;
    }

    public String getAuditor_id() {
        return auditor_id;
    }

    public void setAuditor_id(String auditor_id) {
        this.auditor_id = auditor_id;
    }
}
