package com.jy.model;


public class Delivery {
	private String shiping_order_id;// 订单ID
	private String shiping_order_num;// 订单号
	private String start_address;// 发站
	private String end_address;// 到站
	private String check_time;// 发货日期
	private String checkp;// 托运方
	private String check_tel;// 托运方电话
	private String check_phone;// 托运方手机
	private String receipt;// 收货方
	private String receipt_tel;// 收货方电话
	private String receipt_phone;// 收货方手机
	private String receipt_address;// 收货地址
	private String goods_name;// 货物
	private String goods_packing;// 包装
	private Integer goods_num;// 总件数
	private String goods_weight;// 总重量
	private String goods_vol;// 总体积
	private String car_id;// 接货车号
	private String spe;// 规格
	private String take_fee;// 应收送货费(运单导入)
	private String send_fee;//应付配送费（协议）
	private String adorn_fee;//应收其他费(运单导入)
	private String unloading_fee1;//实付装卸费
	private String send_other;//实付其他费
	private String paid;//提货实付费

	private String trunk_fee;// 应收中转费(运单导入)
	private String insurance;// 保险费
	private Integer pay_type;// 付费方式：1现付 2到付 3代付
	private String transport_pay;// 应收运费总额
	private Integer check_type;// 发货方式：0门点（网点发货） 1货运站发货
	private Integer send_type;// 送货方式：0送货 1自提
	private String remarks;// 备注
	private String updatetime;// 订单录入时间
	private Integer shipping_order_state;//订单状态：0未出库、1出库、2到达、4签收
	private String change_address;//中转地
	private String trade_agency;//代收货款
	private Integer creat_type;//创建方式：0手录  1导入
	private String shipping_order;//制单人
	private String order_state;//订单状态
	private Integer is_update;//是否录入1录入 0未录入	
	private Integer is_receive;// 客户回执：是否接收：0没有 1接收
	private Integer is_send;// 客户回执寄出：：0没有 1寄出
	private String is_send_time;// 客户寄出时间
	private String is_recept_time;// 客户收到时间
	private Integer is_order_arrive;//是否到达：0未到达 1到达
	private String order_arrive_time;//实际到达时间
	private String ys_unloading_fee;// 应收装卸费
	private String is_recept;//是否回单
	private String affirm_value;//声明价值
	private String write_off_t;//实收核销总运费（首页显示用的字段）
	private String sum_pay;//实收总费
	private String write_off5;//实收总运费核销
	private String custom_id;//客户车辆Id
	private String car_number;//客户车牌号
	private String custom_name;//客户名称
	private String customer_tel;//客户电话
	private String car_number1;//接货车牌号
	private String car_name;//接货司机姓名
	
	private String fee_remarks;//应付备注	
	private String send_station;//发站
	
	private String send_fee1; // 实付送货费
	private String send_remarks; // 送货备注
	private String send_remarks1;  // 送货费核销备注	
	private String write_off1;   //  送货费核销
	private String car_time;//发车车次
	private String back_fee;//回扣
	private String change_remarks;//中转备注
	private String send_time;//发货日期
	private String receipt_time;//车到日期
	private String adorn_fee2;//应付装卸费
	private String born_fee;//应收落地费
	private String car_length;//车长
	private String car_type;//大车车型
	private String loading_remarks; //应付装卸费核销备注
	private String adorn_fee1; // 实收装卸费
	
	private String total_cost; // 应收费用总和
	private String collection_fee; // 代收到付运费
	private String  paid_fee ; // 实收到付运费
	private String xf_pay;//现付
	private String hf_pay;//回付
	private String hdqf_pay;//货到前付
	private String xujiaString;//虚假数据
	private  String company_id;//转运公司id
	private String  orderState;//订单状态
	private  String delivery_fee1;//实付接货费
	private String yewuyuan;//业务员
	private String changeb;//修改备注
	private String delivery_fee;//应付实际接货费
	private String local_fee;//本地其他费说明

	private String maoliString;//本票毛利
	private String daikuanString;//贷款扣
	private String hexiaoString;//核销余额
	private String send_agreement_date;//协议配送时间
	/**
	 * 签收
	 */
	private String change_money;//中转回扣
	private String  change_local;//中转成本
	private String change_bz;//中转包装成本
	private String change_cc;//中转叉车成本
	private String change_zx;//中转装卸成本
	private String change_zt;//中转在途信息
	
	private String daidian_fee;//代垫货款
	private String fee_change;//货款变更
	private String  fee_kou;//扣运费
	
	private String place_cc;//	异地叉车费
	private String place_zx;//	异地装卸费
	private String place_qt;//异地其他费
	private String place_feeString;//	少收或赔款
	private String place_bg;//	运费异动-增加
	private String place_sm;//差异说明
	
	private String sign_send;//回单寄出情况
	private String sign_recept;//回单接收情况
	private String writeoff1;//运费核销状态
	private String writeoff;//中转费核销状态（付款状态）
	private String others_date;//应收其他费核销时间（代收款核销时间）
	private String seller_phone;//发货方电话（手动录入）
	private String paid_chayi;//付款方式实收
	private String transport_pay1;//基本运费
	
	
	public String getTransport_pay1() {
		return transport_pay1;
	}


	public void setTransport_pay1(String transport_pay1) {
		this.transport_pay1 = transport_pay1;
	}


	public String getPaid_chayi() {
		return paid_chayi;
	}


	public void setPaid_chayi(String paid_chayi) {
		this.paid_chayi = paid_chayi;
	}


	public String getSend_agreement_date() {
		return send_agreement_date;
	}


	public void setSend_agreement_date(String send_agreement_date) {
		this.send_agreement_date = send_agreement_date;
	}


	public String getSeller_phone() {
		return seller_phone;
	}


	public void setSeller_phone(String seller_phone) {
		this.seller_phone = seller_phone;
	}


	public String getOthers_date() {
		return others_date;
	}


	public void setOthers_date(String others_date) {
		this.others_date = others_date;
	}


	public String getWrite_off5() {
		return write_off5;
	}


	public void setWrite_off5(String write_off5) {
		this.write_off5 = write_off5;
	}


	public String getSum_pay() {
		return sum_pay;
	}


	public void setSum_pay(String sum_pay) {
		this.sum_pay = sum_pay;
	}


	public String getWrite_off_t() {
		return write_off_t;
	}


	public void setWrite_off_t(String write_off_t) {
		this.write_off_t = write_off_t;
	}


	public String getWriteoff() {
		return writeoff;
	}


	public void setWriteoff(String writeoff) {
		this.writeoff = writeoff;
	}


	public String getWriteoff1() {
		return writeoff1;
	}


	public void setWriteoff1(String writeoff1) {
		this.writeoff1 = writeoff1;
	}


	public String getSign_send() {
		return sign_send;
	}


	public void setSign_send(String sign_send) {
		this.sign_send = sign_send;
	}


	public String getSign_recept() {
		return sign_recept;
	}


	public void setSign_recept(String sign_recept) {
		this.sign_recept = sign_recept;
	}


	public String getPlace_cc() {
		return place_cc;
	}


	public void setPlace_cc(String place_cc) {
		this.place_cc = place_cc;
	}


	public String getPlace_zx() {
		return place_zx;
	}


	public void setPlace_zx(String place_zx) {
		this.place_zx = place_zx;
	}


	public String getPlace_qt() {
		return place_qt;
	}


	public void setPlace_qt(String place_qt) {
		this.place_qt = place_qt;
	}


	public String getPlace_feeString() {
		return place_feeString;
	}


	public void setPlace_feeString(String place_feeString) {
		this.place_feeString = place_feeString;
	}


	public String getPlace_bg() {
		return place_bg;
	}


	public void setPlace_bg(String place_bg) {
		this.place_bg = place_bg;
	}


	public String getPlace_sm() {
		return place_sm;
	}


	public void setPlace_sm(String place_sm) {
		this.place_sm = place_sm;
	}


	public String getDaidian_fee() {
		return daidian_fee;
	}


	public void setDaidian_fee(String daidian_fee) {
		this.daidian_fee = daidian_fee;
	}


	public String getFee_change() {
		return fee_change;
	}


	public void setFee_change(String fee_change) {
		this.fee_change = fee_change;
	}


	public String getFee_kou() {
		return fee_kou;
	}


	public void setFee_kou(String fee_kou) {
		this.fee_kou = fee_kou;
	}


	public String getChange_money() {
		return change_money;
	}


	public void setChange_money(String change_money) {
		this.change_money = change_money;
	}


	public String getChange_local() {
		return change_local;
	}


	public void setChange_local(String change_local) {
		this.change_local = change_local;
	}


	public String getChange_bz() {
		return change_bz;
	}


	public void setChange_bz(String change_bz) {
		this.change_bz = change_bz;
	}


	public String getChange_cc() {
		return change_cc;
	}


	public void setChange_cc(String change_cc) {
		this.change_cc = change_cc;
	}


	public String getChange_zx() {
		return change_zx;
	}


	public void setChange_zx(String change_zx) {
		this.change_zx = change_zx;
	}


	public String getChange_zt() {
		return change_zt;
	}


	public void setChange_zt(String change_zt) {
		this.change_zt = change_zt;
	}


	public String getDelivery_fee1() {
		return delivery_fee1;
	}


	public String getDaikuanString() {
		return daikuanString;
	}


	public void setDaikuanString(String daikuanString) {
		this.daikuanString = daikuanString;
	}


	public String getHexiaoString() {
		return hexiaoString;
	}


	public void setHexiaoString(String hexiaoString) {
		this.hexiaoString = hexiaoString;
	}



	public String getMaoliString() {
		return maoliString;
	}


	public void setMaoliString(String maoliString) {
		this.maoliString = maoliString;
	}



	public String getDelivery_fee() {
		return delivery_fee;
	}

	public void setDelivery_fee(String delivery_fee) {
		this.delivery_fee = delivery_fee;
	}

	public String getLocal_fee() {
		return local_fee;
	}

	public void setLocal_fee(String local_fee) {
		this.local_fee = local_fee;
	}

	public String getChangeb() {
		return changeb;
	}

	public void setChangeb(String changeb) {
		this.changeb = changeb;
	}

	public String getYewuyuan() {
		return yewuyuan;
	}

	public void setYewuyuan(String yewuyuan) {
		this.yewuyuan = yewuyuan;
	}

	public String getOrderState() {
		return orderState;
	}

	public void setOrderState(String orderState) {
		this.orderState = orderState;
	}

	public void setDelivery_fee1(String delivery_fee1) {
		this.delivery_fee1 = delivery_fee1;
	}

	
	public String getChange_remarks() {
		return change_remarks;
	}

	public String getCompany_id() {
		return company_id;
	}

	public void setCompany_id(String company_id) {
		this.company_id = company_id;
	}

	public String getXf_pay() {
		return xf_pay;
	}

	public void setXf_pay(String xf_pay) {
		this.xf_pay = xf_pay;
	}

	public String getHf_pay() {
		return hf_pay;
	}

	public void setHf_pay(String hf_pay) {
		this.hf_pay = hf_pay;
	}

	public String getHdqf_pay() {
		return hdqf_pay;
	}

	public void setHdqf_pay(String hdqf_pay) {
		this.hdqf_pay = hdqf_pay;
	}

	public String getPaid() {
		return paid;
	}

	public void setPaid(String paid) {
		this.paid = paid;
	}

	public String getAdorn_fee2() {
		return adorn_fee2;
	}

	public void setAdorn_fee2(String adorn_fee2) {
		this.adorn_fee2 = adorn_fee2;
	}

	public String getBorn_fee() {
		return born_fee;
	}

	public void setBorn_fee(String born_fee) {
		this.born_fee = born_fee;
	}

	public String getCar_length() {
		return car_length;
	}

	public void setCar_length(String car_length) {
		this.car_length = car_length;
	}

	public String getCar_type() {
		return car_type;
	}

	public void setCar_type(String car_type) {
		this.car_type = car_type;
	}

	public String getLoading_remarks() {
		return loading_remarks;
	}

	public void setLoading_remarks(String loading_remarks) {
		this.loading_remarks = loading_remarks;
	}

	public String getAdorn_fee1() {
		return adorn_fee1;
	}

	public void setAdorn_fee1(String adorn_fee1) {
		this.adorn_fee1 = adorn_fee1;
	}

	public String getTotal_cost() {
		return total_cost;
	}

	public void setTotal_cost(String total_cost) {
		this.total_cost = total_cost;
	}

	public String getCollection_fee() {
		return collection_fee;
	}

	public void setCollection_fee(String collection_fee) {
		this.collection_fee = collection_fee;
	}

	public String getPaid_fee() {
		return paid_fee;
	}

	public void setPaid_fee(String paid_fee) {
		this.paid_fee = paid_fee;
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

	public String getIs_recept_time() {
		return is_recept_time;
	}

	public void setIs_recept_time(String is_recept_time) {
		this.is_recept_time = is_recept_time;
	}

	public Integer getIs_order_arrive() {
		return is_order_arrive;
	}

	public void setIs_order_arrive(Integer is_order_arrive) {
		this.is_order_arrive = is_order_arrive;
	}

	public String getOrder_arrive_time() {
		return order_arrive_time;
	}

	public void setOrder_arrive_time(String order_arrive_time) {
		this.order_arrive_time = order_arrive_time;
	}

	public String getYs_unloading_fee() {
		return ys_unloading_fee;
	}

	public void setYs_unloading_fee(String ys_unloading_fee) {
		this.ys_unloading_fee = ys_unloading_fee;
	}

	public void setChange_remarks(String change_remarks) {
		this.change_remarks = change_remarks;
	}
	
	public String getCar_number1() {
		return car_number1;
	}

	public void setCar_number1(String car_number1) {
		this.car_number1 = car_number1;
	}

	public String getCar_name() {
		return car_name;
	}

	public void setCar_name(String car_name) {
		this.car_name = car_name;
	}
	public String getXujiaString() {
		return xujiaString;
	}

	public void setXujiaString(String xujiaString) {
		this.xujiaString = xujiaString;
	}

	public String getSend_other() {
		return send_other;
	}

	public void setSend_other(String send_other) {
		this.send_other = send_other;
	}
	public String getUnloading_fee1() {
		return unloading_fee1;
	}

	public void setUnloading_fee1(String unloading_fee1) {
		this.unloading_fee1 = unloading_fee1;
	}
	public String getCustomer_tel() {
		return customer_tel;
	}

	public void setCustomer_tel(String customer_tel) {
		this.customer_tel = customer_tel;
	}
	
	public String getReceipt_time() {
		return receipt_time;
	}

	public void setReceipt_time(String receipt_time) {
		this.receipt_time = receipt_time;
	}

	
	
	public String getBack_fee() {
		return back_fee;
	}

	public void setBack_fee(String back_fee) {
		this.back_fee = back_fee;
	}

	public String getCar_time() {
		return car_time;
	}

	public void setCar_time(String car_time) {
		this.car_time = car_time;
	}

	
	
	public String getSend_fee1() {
		return send_fee1;
	}

	public void setSend_fee1(String send_fee1) {
		this.send_fee1 = send_fee1;
	}

	public String getSend_remarks() {
		return send_remarks;
	}

	public void setSend_remarks(String send_remarks) {
		this.send_remarks = send_remarks;
	}

	public String getSend_remarks1() {
		return send_remarks1;
	}

	public void setSend_remarks1(String send_remarks1) {
		this.send_remarks1 = send_remarks1;
	}

	public String getWrite_off1() {
		return write_off1;
	}

	public void setWrite_off1(String write_off1) {
		this.write_off1 = write_off1;
	}

	public String getSend_time() {
		return send_time;
	}

	public void setSend_time(String send_time) {
		this.send_time = send_time;
	}

	public String getSend_station() {
		return send_station;
	}

	public void setSend_station(String send_station) {
		this.send_station = send_station;
	}

	private String company_name;//中转公司
	private String company_time;//中转时间
	

	private String ndp_id;
	private String ldp_id;
	private String agreement_type;
	private String change_fee1;//实付中转费
	private String company_number;//中转单号
	private String start_phone;//中转起始电话
	private String end_phone;//中转到站电话
	
	public String getStart_phone() {
		return start_phone;
	}

	public void setStart_phone(String start_phone) {
		this.start_phone = start_phone;
	}

	public String getEnd_phone() {
		return end_phone;
	}

	public void setEnd_phone(String end_phone) {
		this.end_phone = end_phone;
	}
	
	public String getCompany_number() {
		return company_number;
	}

	public void setCompany_number(String company_number) {
		this.company_number = company_number;
	}

	public String getCompany_time() {
		return company_time;
	}

	public void setCompany_time(String company_time) {
		this.company_time = company_time;
	}
	public String getChange_fee1() {
		return change_fee1;
	}

	public void setChange_fee1(String change_fee1) {
		this.change_fee1 = change_fee1;
	}

	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

	public String getNdp_id() {
		return ndp_id;
	}

	public void setNdp_id(String ndp_id) {
		this.ndp_id = ndp_id;
	}

	public String getLdp_id() {
		return ldp_id;
	}

	public void setLdp_id(String ldp_id) {
		this.ldp_id = ldp_id;
	}

	public String getAgreement_type() {
		return agreement_type;
	}

	public void setAgreement_type(String agreement_type) {
		this.agreement_type = agreement_type;
	}

	public String getFee_remarks() {
		return fee_remarks;
	}

	public void setFee_remarks(String fee_remarks) {
		this.fee_remarks = fee_remarks;
	}

	public String getCustom_id() {
		return custom_id;
	}

	public void setCustom_id(String custom_id) {
		this.custom_id = custom_id;
	}

	public String getCar_number() {
		return car_number;
	}

	public void setCar_number(String car_number) {
		this.car_number = car_number;
	}

	public String getCustom_name() {
		return custom_name;
	}

	public void setCustom_name(String custom_name) {
		this.custom_name = custom_name;
	}

	public String getIs_recept() {
		return is_recept;
	}

	public void setIs_recept(String is_recept) {
		this.is_recept = is_recept;
	}

	/**
	 * 订单录入，收获客户时候存储经度纬度
	 */
	private  String customer_id;//客户id
	private String customer_name;//客户名称
	private String customer_point;//坐标
	
	
	public String getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(String customer_id) {
		this.customer_id = customer_id;
	}

	public String getCustomer_name() {
		return customer_name;
	}

	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}

	public String getCustomer_point() {
		return customer_point;
	}

	public void setCustomer_point(String customer_point) {
		this.customer_point = customer_point;
	}

	/*private String longitude;//经度
	private String latitude;//纬度
	
	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
*/
	/**
	 * 中转费核销
	 */
	private String change_fee_id;
	private String sends_fee;
	private String loading_fee;
	private String unloading_fee;
	private String trans_price;
	private String change_fee;
	private String unloadgoods_fee;
	private String ischange_fee;
	private String change_pay;//应付中转费(协议)
	private String change_other;//中转其他费费
	private String write_a;
	private String write_b;
	private String write_c;
	private String write_d;
	private String write_e;
	private String write_f;
	private String write_g;
	private String write_h;
	
	
	public String getChange_other() {
		return change_other;
	}


	public void setChange_other(String change_other) {
		this.change_other = change_other;
	}


	public String getWrite_h() {
		return write_h;
	}

	public void setWrite_h(String write_h) {
		this.write_h = write_h;
	}

	public String getWrite_a() {
		return write_a;
	}

	public void setWrite_a(String write_a) {
		this.write_a = write_a;
	}

	public String getWrite_b() {
		return write_b;
	}

	public void setWrite_b(String write_b) {
		this.write_b = write_b;
	}

	public String getWrite_c() {
		return write_c;
	}

	public void setWrite_c(String write_c) {
		this.write_c = write_c;
	}

	public String getWrite_d() {
		return write_d;
	}

	public void setWrite_d(String write_d) {
		this.write_d = write_d;
	}

	public String getWrite_e() {
		return write_e;
	}

	public void setWrite_e(String write_e) {
		this.write_e = write_e;
	}

	public String getWrite_f() {
		return write_f;
	}

	public void setWrite_f(String write_f) {
		this.write_f = write_f;
	}

	public String getWrite_g() {
		return write_g;
	}

	public void setWrite_g(String write_g) {
		this.write_g = write_g;
	}

	public String getChange_fee_id() {
		return change_fee_id;
	}

	public void setChange_fee_id(String change_fee_id) {
		this.change_fee_id = change_fee_id;
	}

	public String getSends_fee() {
		return sends_fee;
	}

	public void setSends_fee(String sends_fee) {
		this.sends_fee = sends_fee;
	}

	public String getLoading_fee() {
		return loading_fee;
	}

	public void setLoading_fee(String loading_fee) {
		this.loading_fee = loading_fee;
	}

	public String getUnloading_fee() {
		return unloading_fee;
	}

	public void setUnloading_fee(String unloading_fee) {
		this.unloading_fee = unloading_fee;
	}

	public String getTrans_price() {
		return trans_price;
	}

	public void setTrans_price(String trans_price) {
		this.trans_price = trans_price;
	}

	public String getChange_fee() {
		return change_fee;
	}

	public void setChange_fee(String change_fee) {
		this.change_fee = change_fee;
	}

	public String getUnloadgoods_fee() {
		return unloadgoods_fee;
	}

	public void setUnloadgoods_fee(String unloadgoods_fee) {
		this.unloadgoods_fee = unloadgoods_fee;
	}

	public String getIschange_fee() {
		return ischange_fee;
	}

	public void setIschange_fee(String ischange_fee) {
		this.ischange_fee = ischange_fee;
	}

	public String getChange_pay() {
		return change_pay;
	}

	public void setChange_pay(String change_pay) {
		this.change_pay = change_pay;
	}

	/**
	 * 明细表
	 * @return
	 */
	private String change_fee10;
	private String land_total;
	private String send_pay;
	private String detailed_id;
	
	
	
	
	public String getDetailed_id() {
		return detailed_id;
	}

	public void setDetailed_id(String detailed_id) {
		this.detailed_id = detailed_id;
	}

	public String getChange_fee10() {
		return change_fee10;
	}

	public void setChange_fee10(String change_fee10) {
		this.change_fee10 = change_fee10;
	}

	public String getLand_total() {
		return land_total;
	}

	public void setLand_total(String land_total) {
		this.land_total = land_total;
	}

	public String getSend_pay() {
		return send_pay;
	}

	public void setSend_pay(String send_pay) {
		this.send_pay = send_pay;
	}

	public Integer getIs_receive() {
		return is_receive;
	}

	public void setIs_receive(Integer is_receive) {
		this.is_receive = is_receive;
	}

	public Integer getIs_update() {
		return is_update;
	}

	public void setIs_update(Integer is_update) {
		this.is_update = is_update;
	}

	private String imageUrl;//签收的图片显示
	
	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	

	public String getOrder_state() {
		return order_state;
	}

	public void setOrder_state(String order_state) {
		this.order_state = order_state;
	}

	private String sendtype;//送货方式 ，交接方式
	private String sendtype1;
	private String checktype;
	private String checktype1;
	private String paytype;
	private String transportpay;//总运费

	private String travel_card_id;// 车牌Id
	private String plate_number;// 车牌号
	private String driver_name;//司机姓名
	private String phone_number;//
	private String ismake;//是否被制作总
	private String ismakedp;//单票制作
	private String driver_phone;//司机电话
	private String write_off;
	private String car_remarks;//大车备注
	private String create_time;//协议制作时间
	private  Integer overGoodsNum;//剩余的件数
	private Integer practiacl_num;//实际送货件数
	private String create_user;//协议在制作人
	private String paid1;//实际代收货款:总运费
	private String remarks_send;//协议备注
	private String send_date;//配送时间
	private String agreement_number;//协议编号
	private String payfee;//实收金额
	private String paydayString;//付款日期
	private String z_fee;//基本运费
	private String sign_name;//提货代理人
	private String sign_number;//提货代理人证件
	
	private String write_off4;//车辆装卸费核销
	private String write_off_z;//车辆装卸费核销文字
	
	private String write_off_c;//中转费费核销文字 write_off 
	private String write_off3;//落地费核销 write_off3
	private String write_off3_l;//落地费核销 文字
	private String other_fee;//其他费（首页作用：中转其他费）
	private String write_remarks;//实收备注
	private String order_time;//制单时间
	private String remarks_order;//订单修改人
	private String remarks_date;//订单修改时间
	private String paid_chayi1;//提付货款（订单修改录入）
	private  String chayi_daifukuan1;//代收货款（订单修改）
	
	
	public String getPaid_chayi1() {
		return paid_chayi1;
	}


	public void setPaid_chayi1(String paid_chayi1) {
		this.paid_chayi1 = paid_chayi1;
	}


	public String getChayi_daifukuan1() {
		return chayi_daifukuan1;
	}


	public void setChayi_daifukuan1(String chayi_daifukuan1) {
		this.chayi_daifukuan1 = chayi_daifukuan1;
	}


	public String getRemarks_order() {
		return remarks_order;
	}


	public void setRemarks_order(String remarks_order) {
		this.remarks_order = remarks_order;
	}


	public String getRemarks_date() {
		return remarks_date;
	}


	public void setRemarks_date(String remarks_date) {
		this.remarks_date = remarks_date;
	}


	public String getOrder_time() {
		return order_time;
	}


	public void setOrder_time(String order_time) {
		this.order_time = order_time;
	}


	public String getWrite_remarks() {
		return write_remarks;
	}


	public void setWrite_remarks(String write_remarks) {
		this.write_remarks = write_remarks;
	}


	public String getOther_fee() {
		return other_fee;
	}


	public void setOther_fee(String other_fee) {
		this.other_fee = other_fee;
	}


	public String getWrite_off4() {
		return write_off4;
	}


	public void setWrite_off4(String write_off4) {
		this.write_off4 = write_off4;
	}


	public String getWrite_off_z() {
		return write_off_z;
	}


	public void setWrite_off_z(String write_off_z) {
		this.write_off_z = write_off_z;
	}


	public String getWrite_off_c() {
		return write_off_c;
	}


	public void setWrite_off_c(String write_off_c) {
		this.write_off_c = write_off_c;
	}


	public String getWrite_off3() {
		return write_off3;
	}


	public void setWrite_off3(String write_off3) {
		this.write_off3 = write_off3;
	}


	public String getWrite_off3_l() {
		return write_off3_l;
	}


	public void setWrite_off3_l(String write_off3_l) {
		this.write_off3_l = write_off3_l;
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


	public String getZ_fee() {
		return z_fee;
	}


	public void setZ_fee(String z_fee) {
		this.z_fee = z_fee;
	}


	public String getPaydayString() {
		return paydayString;
	}


	public void setPaydayString(String paydayString) {
		this.paydayString = paydayString;
	}


	public String getPayfee() {
		return payfee;
	}


	public void setPayfee(String payfee) {
		this.payfee = payfee;
	}


	public String getAffirm_value() {
		return affirm_value;
	}

	public void setAffirm_value(String affirm_value) {
		this.affirm_value = affirm_value;
	}

	
	public String getAgreement_number() {
		return agreement_number;
	}

	public void setAgreement_number(String agreement_number) {
		this.agreement_number = agreement_number;
	}

	public String getSend_date() {
		return send_date;
	}

	public void setSend_date(String send_date) {
		this.send_date = send_date;
	}

	public String getRemarks_send() {
		return remarks_send;
	}

	public void setRemarks_send(String remarks_send) {
		this.remarks_send = remarks_send;
	}

	public String getPaid1() {
		return paid1;
	}

	public void setPaid1(String paid1) {
		this.paid1 = paid1;
	}

	public String getCreate_user() {
		return create_user;
	}

	public void setCreate_user(String create_user) {
		this.create_user = create_user;
	}

	public Integer getPractiacl_num() {
		return practiacl_num;
	}

	public void setPractiacl_num(Integer practiacl_num) {
		this.practiacl_num = practiacl_num;
	}

	public Integer getOverGoodsNum() {
		return overGoodsNum;
	}

	public void setOverGoodsNum(Integer overGoodsNum) {
		this.overGoodsNum = overGoodsNum;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getCar_remarks() {
		return car_remarks;
	}

	public void setCar_remarks(String car_remarks) {
		this.car_remarks = car_remarks;
	}

	public String getDriver_phone() {
		return driver_phone;
	}

	public void setDriver_phone(String driver_phone) {
		this.driver_phone = driver_phone;
	}

	public String getWrite_off() {
		return write_off;
	}

	public void setWrite_off(String write_off) {
		this.write_off = write_off;
	}

	public String getIsmakedp() {
		return ismakedp;
	}

	public void setIsmakedp(String ismakedp) {
		this.ismakedp = ismakedp;
	}

	public String getIsmake() {
		return ismake;
	}

	public void setIsmake(String ismake) {
		this.ismake = ismake;
	}

	public String getDriver_name() {
		return driver_name;
	}

	public void setDriver_name(String driver_name) {
		this.driver_name = driver_name;
	}

	public String getPhone_number() {
		return phone_number;
	}

	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}

	public String getSendtype1() {
		return sendtype1;
	}

	public void setSendtype1(String sendtype1) {
		this.sendtype1 = sendtype1;
	}

	public String getChecktype1() {
		return checktype1;
	}

	public void setChecktype1(String checktype1) {
		this.checktype1 = checktype1;
	}

	public String getPaytype() {
		return paytype;
	}

	public void setPaytype(String paytype) {
		this.paytype = paytype;
	}

	public String getTransportpay() {
		return transportpay;
	}

	public void setTransportpay(String transportpay) {
		this.transportpay = transportpay;
	}

	public String getSendtype() {
		return sendtype;
	}

	public void setSendtype(String sendtype) {
		this.sendtype = sendtype;
	}

	public String getChecktype() {
		return checktype;
	}

	public void setChecktype(String checktype) {
		this.checktype = checktype;
	}

	public String getShipping_order() {
		return shipping_order;
	}

	public void setShipping_order(String shipping_order) {
		this.shipping_order = shipping_order;
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

	public String getChange_address() {
		return change_address;
	}

	public void setChange_address(String change_address) {
		this.change_address = change_address;
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

	public String getGoods_packing() {
		return goods_packing;
	}

	public void setGoods_packing(String goods_packing) {
		this.goods_packing = goods_packing;
	}

	public String getShiping_order_id() {
		return shiping_order_id;
	}

	public Integer getGoods_num() {
		return goods_num;
	}

	public void setGoods_num(Integer goods_num) {
		this.goods_num = goods_num;
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

	public String getStart_address() {
		return start_address;
	}

	public void setStart_address(String start_address) {
		this.start_address = start_address;
	}

	public String getEnd_address() {
		return end_address;
	}

	public void setEnd_address(String end_address) {
		this.end_address = end_address;
	}

	public String getCheck_time() {
		return check_time;
	}

	public void setCheck_time(String check_time) {
		this.check_time = check_time;
	}
	
	public String getCheckp() {
		return checkp;
	}

	public void setCheckp(String checkp) {
		this.checkp = checkp;
	}

	public String getCheck_tel() {
		return check_tel;
	}

	public void setCheck_tel(String check_tel) {
		this.check_tel = check_tel;
	}

	public String getCheck_phone() {
		return check_phone;
	}

	public void setCheck_phone(String check_phone) {
		this.check_phone = check_phone;
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

	public String getReceipt_phone() {
		return receipt_phone;
	}

	public void setReceipt_phone(String receipt_phone) {
		this.receipt_phone = receipt_phone;
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

	public String getCar_id() {
		return car_id;
	}

	public void setCar_id(String car_id) {
		this.car_id = car_id;
	}

	public String getSpe() {
		return spe;
	}

	public void setSpe(String spe) {
		this.spe = spe;
	}

	public String getTake_fee() {
		return take_fee;
	}

	public void setTake_fee(String take_fee) {
		this.take_fee = take_fee;
	}

	public String getSend_fee() {
		return send_fee;
	}

	public void setSend_fee(String send_fee) {
		this.send_fee = send_fee;
	}

	public String getAdorn_fee() {
		return adorn_fee;
	}

	public void setAdorn_fee(String adorn_fee) {
		this.adorn_fee = adorn_fee;
	}

	public String getTrunk_fee() {
		return trunk_fee;
	}

	public void setTrunk_fee(String trunk_fee) {
		this.trunk_fee = trunk_fee;
	}

	public String getInsurance() {
		return insurance;
	}

	public void setInsurance(String insurance) {
		this.insurance = insurance;
	}

	public Integer getPay_type() {
		return pay_type;
	}

	public void setPay_type(Integer pay_type) {
		this.pay_type = pay_type;
	}

	public String getTransport_pay() {
		return transport_pay;
	}

	public void setTransport_pay(String transport_pay) {
		this.transport_pay = transport_pay;
	}

	public Integer getCheck_type() {
		return check_type;
	}

	public void setCheck_type(Integer check_type) {
		this.check_type = check_type;
	}

	public Integer getSend_type() {
		return send_type;
	}

	public void setSend_type(Integer send_type) {
		this.send_type = send_type;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getTravel_card_id() {
		return travel_card_id;
	}

	public void setTravel_card_id(String travel_card_id) {
		this.travel_card_id = travel_card_id;
	}

	public String getPlate_number() {
		return plate_number;
	}

	public void setPlate_number(String plate_number) {
		this.plate_number = plate_number;
	}
	//hqhadd
	//签收
	private String sign_id;// 签收id
	private String order_id;// 订单id
	private String order_number;// 订单号
	private String sign_user;// 签收人(默认订单收货联系人)
	private String sign_usernumber;// 证件号码
	private String sign_userphone;// 手机号
	private int sign_result;// 签收结果（0、异常签收；1、正常签收）
	private int actual_number;// 实际件数（默认订单总件数）
	private int defect_number;// 缺失数量(不可大于实际单数)
	private int copies_number;// 回单份数（不可大于实际单数）
	private String sign_remarks;// 签收备注
	private String sign_time;// 签收时间


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

	public int getCopies_number() {
		return copies_number;
	}

	public void setCopies_number(int copies_number) {
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



	
}
