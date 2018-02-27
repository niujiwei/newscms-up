package com.jy.model;

/**
 * Created by njw on 2017/7/12.
 */
public class SCMBusinessRemarkData {
    private Integer id;
    private String bus_id;                    //平台订单id
    private String address;                    //省市县镇地址详情
    private String createtime;                //创建时间
    private String dpeoid;                    //配送员id
    private String idccard;                    //身份证号
    private String platform_number;            //客户单号
    private String signpeople;                //签收人
    private String pointtype;                //(节点类型 0,运输节点,1签收节点)
    private String datacreatetime;            //本条数据创建时间
    private String signtime;                //签收时间
    private String datecome;                //0 scm 1tms 2 dms 数据来源
    private String dotype;                    //操作类型
    private Integer manu_Id;                    //客户id
    private String ord_number;                //订单编号
    private String dms_id;//我的订单id
    private Integer state;//状态是否传输
    private Integer shipping_order_state;//历史状态

    public String getDms_id() {
        return dms_id;
    }

    public void setDms_id(String dms_id) {
        this.dms_id = dms_id;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBus_id() {
        return bus_id;
    }

    public void setBus_id(String bus_id) {
        this.bus_id = bus_id;
    }

    public Integer getShipping_order_state() {
        return shipping_order_state;
    }

    public void setShipping_order_state(Integer shipping_order_state) {
        this.shipping_order_state = shipping_order_state;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getDpeoid() {
        return dpeoid;
    }

    public void setDpeoid(String dpeoid) {
        this.dpeoid = dpeoid;
    }

    public String getIdccard() {
        return idccard;
    }

    public void setIdccard(String idccard) {
        this.idccard = idccard;
    }

    public String getPlatform_number() {
        return platform_number;
    }

    public void setPlatform_number(String platform_number) {
        this.platform_number = platform_number;
    }

    public String getSignpeople() {
        return signpeople;
    }

    public void setSignpeople(String signpeople) {
        this.signpeople = signpeople;
    }

    public String getPointtype() {
        return pointtype;
    }

    public void setPointtype(String pointtype) {
        this.pointtype = pointtype;
    }

    public String getDatacreatetime() {
        return datacreatetime;
    }

    public void setDatacreatetime(String datacreatetime) {
        this.datacreatetime = datacreatetime;
    }

    public String getSigntime() {
        return signtime;
    }

    public void setSigntime(String signtime) {
        this.signtime = signtime;
    }

    public String getDatecome() {
        return datecome;
    }

    public void setDatecome(String datecome) {
        this.datecome = datecome;
    }

    public String getDotype() {
        return dotype;
    }

    public void setDotype(String dotype) {
        this.dotype = dotype;
    }

    public Integer getManu_Id() {
        return manu_Id;
    }

    public void setManu_Id(Integer manu_Id) {
        this.manu_Id = manu_Id;
    }

    public String getOrd_number() {
        return ord_number;
    }

    public void setOrd_number(String ord_number) {
        this.ord_number = ord_number;
    }


}


