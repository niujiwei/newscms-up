package com.jy.model;


public class SubsidyInfoModel {
    private String id;//id
    private String shiping_order_num;//订单号
    private String shiping_order_goid;//出货订单号
    private String shiping_order_id;//订单id
    private String delivery_date;//配送时间
    private String super_far_subsidy;//超远
    private String upstairs_subsidy;//上楼
    private String super_volume_subsidy;//超体积
    private String other_subsidy;//其他补助
    private String remarks;//提交备注
    private int state;//状态(0已经提交,1项目审核，2审核通过，3审核不同)
    private String customer_subsidy;//客户补助
    private String customer_remarks;//客户备注
    private String customer_time;//客户审核时间
    private String admin_time;//管理员审核时间
    private String create_time;//创建时间
    private String admin_subsidy;//管理员补助
    private String admin_remarks;//管理员备注
    private String whether_through;//是否通过(0通过，1不通过)

    private String send_mechanism;// 受理机构
    private String send_station;// 起运地
    private String send_time;// 起运时间
    private String end_mechanism;// 终到机构
    private String end_address;// 终到位置-----用来分配司机配送地址，把运单分配给司机
    private Integer goods_num;// 总件数

    private String receivetime;// 接单时间
    private String updatetime;// 导入时间
    private String receipt_name;// 到货联系人
    private String sign_remarks;// 签收备注
    private String sign_time;// 订单签收时间
    private String result;// 结果

    private String order_type;//正向还是逆向（z 正向,n 逆向)

    public String getCustomer_time() {
        return customer_time;
    }

    public void setCustomer_time(String customer_time) {
        this.customer_time = customer_time;
    }

    public String getAdmin_time() {
        return admin_time;
    }

    public void setAdmin_time(String admin_time) {
        this.admin_time = admin_time;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getOrder_type() {
        return order_type;
    }

    public void setOrder_type(String order_type) {
        this.order_type = order_type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShiping_order_num() {
        return shiping_order_num;
    }

    public void setShiping_order_num(String shiping_order_num) {
        this.shiping_order_num = shiping_order_num;
    }

    public String getShiping_order_goid() {
        return shiping_order_goid;
    }

    public void setShiping_order_goid(String shiping_order_goid) {
        this.shiping_order_goid = shiping_order_goid;
    }

    public String getShiping_order_id() {
        return shiping_order_id;
    }

    public void setShiping_order_id(String shiping_order_id) {
        this.shiping_order_id = shiping_order_id;
    }

    public String getDelivery_date() {
        return delivery_date;
    }

    public void setDelivery_date(String delivery_date) {
        this.delivery_date = delivery_date;
    }

    public String getSuper_far_subsidy() {
        return super_far_subsidy;
    }

    public void setSuper_far_subsidy(String super_far_subsidy) {
        this.super_far_subsidy = super_far_subsidy;
    }

    public String getUpstairs_subsidy() {
        return upstairs_subsidy;
    }

    public void setUpstairs_subsidy(String upstairs_subsidy) {
        this.upstairs_subsidy = upstairs_subsidy;
    }

    public String getSuper_volume_subsidy() {
        return super_volume_subsidy;
    }

    public void setSuper_volume_subsidy(String super_volume_subsidy) {
        this.super_volume_subsidy = super_volume_subsidy;
    }

    public String getOther_subsidy() {
        return other_subsidy;
    }

    public void setOther_subsidy(String other_subsidy) {
        this.other_subsidy = other_subsidy;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getCustomer_subsidy() {
        return customer_subsidy;
    }

    public void setCustomer_subsidy(String customer_subsidy) {
        this.customer_subsidy = customer_subsidy;
    }

    public String getCustomer_remarks() {
        return customer_remarks;
    }

    public void setCustomer_remarks(String customer_remarks) {
        this.customer_remarks = customer_remarks;
    }

    public String getAdmin_subsidy() {
        return admin_subsidy;
    }

    public void setAdmin_subsidy(String admin_subsidy) {
        this.admin_subsidy = admin_subsidy;
    }

    public String getAdmin_remarks() {
        return admin_remarks;
    }

    public void setAdmin_remarks(String admin_remarks) {
        this.admin_remarks = admin_remarks;
    }

    public String getSend_mechanism() {
        return send_mechanism;
    }

    public void setSend_mechanism(String send_mechanism) {
        this.send_mechanism = send_mechanism;
    }

    public String getSend_station() {
        return send_station;
    }

    public void setSend_station(String send_station) {
        this.send_station = send_station;
    }

    public String getSend_time() {
        return send_time;
    }

    public void setSend_time(String send_time) {
        this.send_time = send_time;
    }

    public String getEnd_mechanism() {
        return end_mechanism;
    }

    public void setEnd_mechanism(String end_mechanism) {
        this.end_mechanism = end_mechanism;
    }

    public String getEnd_address() {
        return end_address;
    }

    public void setEnd_address(String end_address) {
        this.end_address = end_address;
    }

    public Integer getGoods_num() {
        return goods_num;
    }

    public void setGoods_num(Integer goods_num) {
        this.goods_num = goods_num;
    }

    public String getReceivetime() {
        return receivetime;
    }

    public void setReceivetime(String receivetime) {
        this.receivetime = receivetime;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public String getReceipt_name() {
        return receipt_name;
    }

    public void setReceipt_name(String receipt_name) {
        this.receipt_name = receipt_name;
    }

    public String getSign_remarks() {
        return sign_remarks;
    }

    public void setSign_remarks(String sign_remarks) {
        this.sign_remarks = sign_remarks;
    }

    public String getSign_time() {
        return sign_time;
    }

    public void setSign_time(String sign_time) {
        this.sign_time = sign_time;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getWhether_through() {
        return whether_through;
    }

    public void setWhether_through(String whether_through) {
        this.whether_through = whether_through;
    }
}
