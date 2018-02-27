package com.jy.model;


import java.util.Date;

public class SubsidyHistoryModel {
    private String id;//id
    private String subsidy_id;//
    private String user_name;//用户名
    private String user_real_name;//真实姓名
    private String shiping_order_num;//订单号
    private String message;//信息
    private Date create_time;//创建时间
    private int type;//审核类型

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubsidy_id() {
        return subsidy_id;
    }

    public void setSubsidy_id(String subsidy_id) {
        this.subsidy_id = subsidy_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_real_name() {
        return user_real_name;
    }

    public void setUser_real_name(String user_real_name) {
        this.user_real_name = user_real_name;
    }

    public String getShiping_order_num() {
        return shiping_order_num;
    }

    public void setShiping_order_num(String shiping_order_num) {
        this.shiping_order_num = shiping_order_num;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }
}
