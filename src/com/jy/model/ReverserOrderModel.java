package com.jy.model;


public class ReverserOrderModel {
    private String shiping_order_id;//订单id
    private String shiping_order_num;//订单号
    private String shiping_order_goid;//出货订单号
    private String send_mechanism;//受理机构
    private String send_station;//起运地
    private String send_time;//起运时间
    private String end_address;//终到位置
    private String end_mechanism;//终到机构
    private String custom_name;//发货客户名称
    private String custom_tel;//发货客户电话
    private String custom_contact;//发货客户联系人
    private String receipt_name;//收货客户姓名
    private String receipt_contact;//收货客户联系人
    private String receipt_tel;//到货联系电话
    private String receipt_address;//调拨客户详细目的地
    private String goods_num;//货物件数
    private String real_num;//实际件数
    private String goods_name;//货物名称
    private String goods_weight;//货物重量
    private String goods_vol;//总体积（立方米）
    private String remarks;//备注信息
    private int shipping_order_state;//订单状态
    private int order_type;//订单状态(0退货,1调拨)
    private String order_type_name;//
    private String time_limitation;//提货时效
    private String create_type;//创建方式 用户类型()
    private String updatetime;//创建时间
    private String remarks_order;//项目部客户备注
    private String shipping_order;//制单人
    private String suppliers_id;//制单人供应商id
    private String driver_id;//司机id
    private String shipping_order_statestr;//订单状态名称

    /**
     * @author H
     * @return
     */
    private String sign_id;// 签收id
    private String order_id;// 订单id
    private String order_number;// 订单号
    private String sign_user;// 签收人(默认订单收货联系人)
    private String sign_usernumber;// 证件号码
    private String sign_userphone;// 手机号
    private int sign_result;// 签收结果（0、异常签收；1、正常签收）
    private int actual_number;// 实际件数（默认订单总件数）
    private int defect_number;// 缺失数量(不可大于实际单数)
    private String copies_number;// 回单份数（不可大于实际单数）
    private String sign_remarks;// 签收备注
    private String sign_time;// 签收时间
    private String sign_username; // 提货经办
    private String sign_number;//提货代理人证件
    private String sign_name;//提货代理人
    private String driver_province;//终到位置省
    private String driver_city;//终到位置市
    private String driver_county;//终到位置区
    private String end_province;//终到机构的省
    private String end_city;//终到机构市
    private String end_county;//终到位置区

    public String getReceipt_address() {
        return receipt_address;
    }

    public void setReceipt_address(String receipt_address) {
        this.receipt_address = receipt_address;
    }

    public String getDriver_province() {
        return driver_province;
    }

    public void setDriver_province(String driver_province) {
        this.driver_province = driver_province;
    }

    public String getDriver_city() {
        return driver_city;
    }

    public void setDriver_city(String driver_city) {
        this.driver_city = driver_city;
    }

    public String getDriver_county() {
        return driver_county;
    }

    public void setDriver_county(String driver_county) {
        this.driver_county = driver_county;
    }

    public String getEnd_province() {
        return end_province;
    }

    public void setEnd_province(String end_province) {
        this.end_province = end_province;
    }

    public String getEnd_city() {
        return end_city;
    }

    public void setEnd_city(String end_city) {
        this.end_city = end_city;
    }

    public String getEnd_county() {
        return end_county;
    }

    public void setEnd_county(String end_county) {
        this.end_county = end_county;
    }

    public String getSign_id() {
        return sign_id;
    }

    public void setSign_id(String sign_id) {
        this.sign_id = sign_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getOrder_number() {
        return order_number;
    }

    public void setOrder_number(String order_number) {
        this.order_number = order_number;
    }

    public String getSign_user() {
        return sign_user;
    }

    public void setSign_user(String sign_user) {
        this.sign_user = sign_user;
    }

    public String getSign_usernumber() {
        return sign_usernumber;
    }

    public void setSign_usernumber(String sign_usernumber) {
        this.sign_usernumber = sign_usernumber;
    }

    public String getSign_userphone() {
        return sign_userphone;
    }

    public void setSign_userphone(String sign_userphone) {
        this.sign_userphone = sign_userphone;
    }

    public int getSign_result() {
        return sign_result;
    }

    public void setSign_result(int sign_result) {
        this.sign_result = sign_result;
    }

    public int getActual_number() {
        return actual_number;
    }

    public void setActual_number(int actual_number) {
        this.actual_number = actual_number;
    }

    public int getDefect_number() {
        return defect_number;
    }

    public void setDefect_number(int defect_number) {
        this.defect_number = defect_number;
    }

    public String getCopies_number() {
        return copies_number;
    }

    public void setCopies_number(String copies_number) {
        this.copies_number = copies_number;
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

    public String getSign_username() {
        return sign_username;
    }

    public void setSign_username(String sign_username) {
        this.sign_username = sign_username;
    }

    public String getSign_number() {
        return sign_number;
    }

    public void setSign_number(String sign_number) {
        this.sign_number = sign_number;
    }

    public String getSign_name() {
        return sign_name;
    }

    public void setSign_name(String sign_name) {
        this.sign_name = sign_name;
    }

    public String getShiping_order_id() {
        return shiping_order_id;
    }

    public void setShiping_order_id(String shiping_order_id) {
        this.shiping_order_id = shiping_order_id;
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

    public String getEnd_address() {
        return end_address;
    }

    public void setEnd_address(String end_address) {
        this.end_address = end_address;
    }

    public String getEnd_mechanism() {
        return end_mechanism;
    }

    public void setEnd_mechanism(String end_mechanism) {
        this.end_mechanism = end_mechanism;
    }

    public String getCustom_name() {
        return custom_name;
    }

    public void setCustom_name(String custom_name) {
        this.custom_name = custom_name;
    }

    public String getCustom_tel() {
        return custom_tel;
    }

    public void setCustom_tel(String custom_tel) {
        this.custom_tel = custom_tel;
    }

    public String getCustom_contact() {
        return custom_contact;
    }

    public void setCustom_contact(String custom_contact) {
        this.custom_contact = custom_contact;
    }

    public String getReceipt_name() {
        return receipt_name;
    }

    public void setReceipt_name(String receipt_name) {
        this.receipt_name = receipt_name;
    }

    public String getReceipt_contact() {
        return receipt_contact;
    }

    public void setReceipt_contact(String receipt_contact) {
        this.receipt_contact = receipt_contact;
    }

    public String getReceipt_tel() {
        return receipt_tel;
    }

    public void setReceipt_tel(String receipt_tel) {
        this.receipt_tel = receipt_tel;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getGoods_weight() {
        return goods_weight;
    }

    public void setGoods_weight(String goods_weight) {
        this.goods_weight = goods_weight;
    }

    public String getGoods_vol() {
        return goods_vol;
    }

    public void setGoods_vol(String goods_vol) {
        this.goods_vol = goods_vol;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public int getShipping_order_state() {
        return shipping_order_state;
    }

    public void setShipping_order_state(int shipping_order_state) {
        this.shipping_order_state = shipping_order_state;
    }

    public int getOrder_type() {
        return order_type;
    }

    public void setOrder_type(int order_type) {
        this.order_type = order_type;
    }

    public String getTime_limitation() {
        return time_limitation;
    }

    public void setTime_limitation(String time_limitation) {
        this.time_limitation = time_limitation;
    }

    public String getCreate_type() {
        return create_type;
    }

    public void setCreate_type(String create_type) {
        this.create_type = create_type;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public String getRemarks_order() {
        return remarks_order;
    }

    public void setRemarks_order(String remarks_order) {
        this.remarks_order = remarks_order;
    }

    public String getShipping_order() {
        return shipping_order;
    }

    public void setShipping_order(String shipping_order) {
        this.shipping_order = shipping_order;
    }

    public String getGoods_num() {
        return goods_num;
    }

    public void setGoods_num(String goods_num) {
        this.goods_num = goods_num;
    }

    public String getReal_num() {
        return real_num;
    }

    public void setReal_num(String real_num) {
        this.real_num = real_num;
    }

    public String getSuppliers_id() {
        return suppliers_id;
    }

    public void setSuppliers_id(String suppliers_id) {
        this.suppliers_id = suppliers_id;
    }

    public String getDriver_id() {
        return driver_id;
    }

    public void setDriver_id(String driver_id) {
        this.driver_id = driver_id;
    }

    public String getOrder_type_name() {
        return order_type_name;
    }

    public void setOrder_type_name(String order_type_name) {

        this.order_type_name = order_type_name;
    }

    public String getShipping_order_statestr() {
        return shipping_order_statestr;
    }

    public void setShipping_order_statestr(String shipping_order_statestr) {
        this.shipping_order_statestr = shipping_order_statestr;
    }
}
