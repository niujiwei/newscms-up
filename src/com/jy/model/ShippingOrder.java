package com.jy.model;

/**
 * 运单bean
 * 
 * @author zzp
 * 
 */
public class ShippingOrder {
//	private String aging_alltime;//时效提醒时间
	private String shiping_order_id;// 订单ID
	private String shiping_order_num;// 货运编号
	private String send_mechanism;// 受理机构
	private String send_station;// 起运地
	private String send_time;// 起运时间
	private String end_mechanism;// 终到机构
	private String end_address;// 终到位置-----用来分配司机配送地址，把运单分配给司机
	private String custom_code;// 发货客户编号
	private String custom_name;// 发货客户名称
	private String custom_contact;// 发货客户联系人
	private String custom_tel;// 发货联系电话
	private String receipt;// 收货客户名称
	private String receipt_tel;// 到货联系人电话
	private String receipt_name;// 到货联系人
	private String receipt_address;//到货联系人地址
	private String goods_name;// 货物名称
	private String goods_packing;// 包装
	private Integer goods_num;// 总件数
	private String goods_weight;// 总重量
	private String goods_vol;// 总体积
	private String spe;// 规格
	private String transport_pay;// 运费总额
	private String remarks;// 备注
	private String updatetime;// 修改时间
	private Integer shipping_order_state;// 订单状态：0发运、1分配、2接单、3点子 、4签收、5返回、6接受
	private String shipping_order_statestr;//订单状态0发运、1分配、2接单、3点子 、4签收、5返回、6接受
	private String trade_agency;// 代收货款
	private Integer creat_type;// 创建方式：0手录 1导入
	private String shipping_order;// 制单人
	private String order_state;// 订单状态
	private Integer is_send;// 客户回执寄出：：0没有 1寄出
	private String is_send_time;// 客户寄出时间
	private String edit_order;// 订单修改人
	private String edit_date;// 订单修改时间
	private String ehistory_id;// 订单修改表id
	private String order_time;// 订单制作时间
	private String write_id;
	private String is_recept;// 回单份数
	private String other_recept_time;// 回单接受时间
	private String deliver_fee;// 配送费
	private String upstairs_fee;// 上楼费
	private String added_fee;// 附加费
	private String other_fee;// 其他费用
	private Integer practiacl_num;
	private String sign_id;// 签收表id
	private String order_id;// 签收关联id
	private String order_number;// 订单号
	private String auto_sign_time;//围栏签收时间
	private String sign_user;// 签收人(默认订单收货联系人)
	private String sign_usernumber;// 证件号码(提货人)
	private String sign_userphone;// 手机号
	private String sign_result;// 签收结果（0、异常签收；1、正常签收）
	private String actual_number;// 实际件数（默认订单总件数）
	private String defect_number;// 缺失数量(不可大于实际单数)
	private String copies_number;// 回单份数（不可大于实际单数）
	private String sign_remarks;// 签收备注
	private String sign_time;// 签收时间
	private String sign_username;// 提货经办
	private String sign_number;// 提货代理人证件
	private String sign_name;// 提货代理人
	private String heji;
	private String clearing_state;// 结算日期( 0未结算 , 1已结算)
	private String is_receive;// 是否回单
	private String aging_time;// 标准时效
	private String aging_day;// 标准时效日期（0,当日；1,次日，2，隔日）
	private String max_time;// 最晚起运时间
	private String min_time;// 最早起运时间
	private String num;// 总件数
	private String customer_settlement_state;// 客户结算(0未结算 , 1已结算)
	private String topordernumber;// 上游订单
	private String downordernumber;// 下游订单
	private String imageUrl;// 订单签收图片
	private String shiping_yueid;// 月结编号
	private Integer driver_province;// 省
	private Integer driver_city;// 市
	private Integer driver_county;// 县
	private Integer isreceive;// 司机是否节点（0，未接单，1，已接单）
	private String receivetime;// 接单时间
	private String shiping_order_goid;// 出货订单号
	private String is_recept_time;// 回单返回时间日期
	private String result;// 结果
	private String appraisalAward;//考核奖励
	private String checkDebit;//考核扣款
	private String unqualifiedDeduction;//不合格品扣款
	private String is_abnormal;//是否异常上报（0,未异常上报，1货物异常，2客户异常，3已处理）
	private String aging_alltime;//理论时效时间
	private String is_allot;//是否已经分配（0，未分配，1分配）
	private String abnormal_name;//异常上报内容
	private int waybill_state;//是否调整上报（0,未调整，1调整运单，2取消签收，3已处理）
	
	
	//hcm逆向物流新增字段
	private String order_mechanism; //制单人所属机构
	private String reverse_orderid;//制单人司机id
	//新增评论状态
	private String comment_state;//评论状态
	
	private String driver_id;//司机id
	private String driver_name;//司机姓名
	private String driver_phone;//司机电话
	private String driver_suppliers;//司机供应商
	private String order_type;//正向还是逆向（z 正向,n 逆向）
	private Integer business_type;//业务类型(0(同城，1城际，2激活))

	public Integer getBusiness_type() {
		return business_type;
	}

	public void setBusiness_type(Integer business_type) {
		this.business_type = business_type;
	}

	public String getOrder_type() {
		return order_type;
	}

	public void setOrder_type(String order_type) {
		this.order_type = order_type;
	}

//	public String getAging_alltime() {
//		return aging_alltime;
//	}
//
//	public void setAging_alltime(String aging_alltime) {
//		this.aging_alltime = aging_alltime;
//	}


	public int getWaybill_state() {
		return waybill_state;
	}

	public void setWaybill_state(int waybill_state) {
		this.waybill_state = waybill_state;
	}

	public String getDriver_id() {
		return driver_id;
	}

	public String getAuto_sign_time() {
		return auto_sign_time;
	}

	public void setAuto_sign_time(String auto_sign_time) {
		this.auto_sign_time = auto_sign_time;
	}

	public void setDriver_id(String driver_id) {
		this.driver_id = driver_id;
	}

	public String getDriver_name() {
		return driver_name;
	}

	public void setDriver_name(String driver_name) {
		this.driver_name = driver_name;
	}

	public String getDriver_phone() {
		return driver_phone;
	}

	public void setDriver_phone(String driver_phone) {
		this.driver_phone = driver_phone;
	}

	public String getDriver_suppliers() {
		return driver_suppliers;
	}

	public void setDriver_suppliers(String driver_suppliers) {
		this.driver_suppliers = driver_suppliers;
	}

	public String getIs_abnormal() {
		return is_abnormal;
	}

	public String getAbnormal_name() {
		return abnormal_name;
	}

	public void setAbnormal_name(String abnormal_name) {
		this.abnormal_name = abnormal_name;
	}

	public void setIs_abnormal(String is_abnormal) {
		this.is_abnormal = is_abnormal;
	}

	public String getAging_alltime() {
		return aging_alltime;
	}

	public void setAging_alltime(String aging_alltime) {
		this.aging_alltime = aging_alltime;
	}

	public String getIs_allot() {
		return is_allot;
	}

	public void setIs_allot(String is_allot) {
		this.is_allot = is_allot;
	}

	public String getShipping_order_statestr() {
		return shipping_order_statestr;
	}

	public void setShipping_order_statestr(String shipping_order_statestr) {
		this.shipping_order_statestr = shipping_order_statestr;
	}

	public String getAppraisalAward() {
		return appraisalAward;
	}

	public void setAppraisalAward(String appraisalAward) {
		this.appraisalAward = appraisalAward;
	}

	public String getCheckDebit() {
		return checkDebit;
	}

	public void setCheckDebit(String checkDebit) {
		this.checkDebit = checkDebit;
	}

	public String getUnqualifiedDeduction() {
		return unqualifiedDeduction;
	}

	public void setUnqualifiedDeduction(String unqualifiedDeduction) {
		this.unqualifiedDeduction = unqualifiedDeduction;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getOther_recept_time() {
		return other_recept_time;
	}

	public void setOther_recept_time(String other_recept_time) {
		this.other_recept_time = other_recept_time;
	}

	public String getReceivetime() {
		return receivetime;
	}

	public void setReceivetime(String receivetime) {
		this.receivetime = receivetime;
	}

	public Integer getIsreceive() {
		return isreceive;
	}

	public void setIsreceive(Integer isreceive) {
		this.isreceive = isreceive;
	}

	public Integer getDriver_province() {
		return driver_province;
	}

	public void setDriver_province(Integer driver_province) {
		this.driver_province = driver_province;
	}

	public Integer getDriver_city() {
		return driver_city;
	}

	public void setDriver_city(Integer driver_city) {
		this.driver_city = driver_city;
	}

	public Integer getDriver_county() {
		return driver_county;
	}

	public void setDriver_county(Integer driver_county) {
		this.driver_county = driver_county;
	}

	public String getShiping_yueid() {
		return shiping_yueid;
	}

	public void setShiping_yueid(String shiping_yueid) {
		this.shiping_yueid = shiping_yueid;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getAging_day() {
		return aging_day;
	}

	public void setAging_day(String aging_day) {
		this.aging_day = aging_day;
	}

	public String getIs_recept_time() {
		return is_recept_time;
	}

	public void setIs_recept_time(String is_recept_time) {
		this.is_recept_time = is_recept_time;
	}

	public String getShiping_order_goid() {
		return shiping_order_goid;
	}

	public void setShiping_order_goid(String shiping_order_goid) {
		this.shiping_order_goid = shiping_order_goid;
	}

	public String getTopordernumber() {
		return topordernumber;
	}

	public void setTopordernumber(String topordernumber) {
		this.topordernumber = topordernumber;
	}

	public String getDownordernumber() {
		return downordernumber;
	}

	public void setDownordernumber(String downordernumber) {
		this.downordernumber = downordernumber;
	}

	public String getAging_time() {
		return aging_time;
	}

	public void setAging_time(String aging_time) {
		this.aging_time = aging_time;
	}

	public String getIs_receive() {
		return is_receive;
	}

	public void setIs_receive(String is_receive) {
		this.is_receive = is_receive;
	}

	public String getClearing_state() {
		return clearing_state;
	}

	public void setClearing_state(String clearing_state) {
		this.clearing_state = clearing_state;
	}

	public String getHeji() {
		return heji;
	}

	public void setHeji(String heji) {
		this.heji = heji;
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

	public String getSign_result() {
		return sign_result;
	}

	public void setSign_result(String sign_result) {
		this.sign_result = sign_result;
	}

	public String getActual_number() {
		return actual_number;
	}

	public void setActual_number(String actual_number) {
		this.actual_number = actual_number;
	}

	public String getDefect_number() {
		return defect_number;
	}

	public void setDefect_number(String defect_number) {
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

	public String getDeliver_fee() {
		return deliver_fee;
	}

	public void setDeliver_fee(String deliver_fee) {
		this.deliver_fee = deliver_fee;
	}

	public String getUpstairs_fee() {
		return upstairs_fee;
	}

	public void setUpstairs_fee(String upstairs_fee) {
		this.upstairs_fee = upstairs_fee;
	}

	public String getAdded_fee() {
		return added_fee;
	}

	public void setAdded_fee(String added_fee) {
		this.added_fee = added_fee;
	}

	public String getOther_fee() {
		return other_fee;
	}

	public void setOther_fee(String other_fee) {
		this.other_fee = other_fee;
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

	public String getCustom_code() {
		return custom_code;
	}

	public void setCustom_code(String custom_code) {
		this.custom_code = custom_code;
	}

	public String getCustom_name() {
		return custom_name;
	}

	public void setCustom_name(String custom_name) {
		this.custom_name = custom_name;
	}

	public String getCustom_contact() {
		return custom_contact;
	}

	public void setCustom_contact(String custom_contact) {
		this.custom_contact = custom_contact;
	}

	public String getCustom_tel() {
		return custom_tel;
	}

	public void setCustom_tel(String custom_tel) {
		this.custom_tel = custom_tel;
	}

	public String getReceipt() {
		return receipt;
	}

	public void setReceipt(String receipt) {
		this.receipt = receipt;
	}

	public String getReceipt_tel() {
		return receipt_tel;
	}

	public void setReceipt_tel(String receipt_tel) {
		this.receipt_tel = receipt_tel;
	}

	public String getReceipt_name() {
		return receipt_name;
	}

	public void setReceipt_name(String receipt_name) {
		this.receipt_name = receipt_name;
	}

	public String getReceipt_address() {
		return receipt_address;
	}

	public void setReceipt_address(String receipt_address) {
		this.receipt_address = receipt_address;
	}

	public String getGoods_name() {
		return goods_name;
	}

	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}

	public String getGoods_packing() {
		return goods_packing;
	}

	public void setGoods_packing(String goods_packing) {
		this.goods_packing = goods_packing;
	}

	public Integer getGoods_num() {
		return goods_num;
	}

	public void setGoods_num(Integer goods_num) {
		this.goods_num = goods_num;
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

	public String getSpe() {
		return spe;
	}

	public void setSpe(String spe) {
		this.spe = spe;
	}

	public String getTransport_pay() {
		return transport_pay;
	}

	public void setTransport_pay(String transport_pay) {
		this.transport_pay = transport_pay;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}

	public Integer getShipping_order_state() {
		return shipping_order_state;
	}

	public void setShipping_order_state(Integer shipping_order_state) {
		this.shipping_order_state = shipping_order_state;
	}

	public String getTrade_agency() {
		return trade_agency;
	}

	public void setTrade_agency(String trade_agency) {
		this.trade_agency = trade_agency;
	}

	public Integer getCreat_type() {
		return creat_type;
	}

	public void setCreat_type(Integer creat_type) {
		this.creat_type = creat_type;
	}

	public String getShipping_order() {
		return shipping_order;
	}

	public void setShipping_order(String shipping_order) {
		this.shipping_order = shipping_order;
	}

	public String getOrder_state() {
		return order_state;
	}

	public void setOrder_state(String order_state) {
		this.order_state = order_state;
	}

	public Integer getIs_send() {
		return is_send;
	}

	public void setIs_send(Integer is_send) {
		this.is_send = is_send;
	}

	public String getIs_send_time() {
		return is_send_time;
	}

	public void setIs_send_time(String is_send_time) {
		this.is_send_time = is_send_time;
	}

	public String getEdit_order() {
		return edit_order;
	}

	public void setEdit_order(String edit_order) {
		this.edit_order = edit_order;
	}

	public String getEdit_date() {
		return edit_date;
	}

	public void setEdit_date(String edit_date) {
		this.edit_date = edit_date;
	}

	public String getEhistory_id() {
		return ehistory_id;
	}

	public void setEhistory_id(String ehistory_id) {
		this.ehistory_id = ehistory_id;
	}

	public String getOrder_time() {
		return order_time;
	}

	public void setOrder_time(String order_time) {
		this.order_time = order_time;
	}

	public String getWrite_id() {
		return write_id;
	}

	public void setWrite_id(String write_id) {
		this.write_id = write_id;
	}

	public String getIs_recept() {
		return is_recept;
	}

	public void setIs_recept(String is_recept) {
		this.is_recept = is_recept;
	}

	public Integer getPractiacl_num() {
		return practiacl_num;
	}

	public void setPractiacl_num(Integer practiacl_num) {
		this.practiacl_num = practiacl_num;
	}

	public String getMax_time() {
		return max_time;
	}

	public void setMax_time(String maxTime) {
		max_time = maxTime;
	}

	public String getMin_time() {
		return min_time;
	}

	public void setMin_time(String minTime) {
		min_time = minTime;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getCustomer_settlement_state() {
		return customer_settlement_state;
	}

	public void setCustomer_settlement_state(String customerSettlementState) {
		customer_settlement_state = customerSettlementState;
	}

	public String getOrder_mechanism() {
		return order_mechanism;
	}

	public void setOrder_mechanism(String order_mechanism) {
		this.order_mechanism = order_mechanism;
	}

	public String getReverse_orderid() {
		return reverse_orderid;
	}

	public void setReverse_orderid(String reverse_orderid) {
		this.reverse_orderid = reverse_orderid;
	}

	public String getComment_state() {
		return comment_state;
	}

	public void setComment_state(String comment_state) {
		this.comment_state = comment_state;
	}
	
}
