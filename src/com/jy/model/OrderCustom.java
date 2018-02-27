package com.jy.model;

public class OrderCustom {
	private String custom_id;//客户id
	private String custom_name;//客户名称
	private String car_time;//客户发车车次
	private String send_station;//发站
	private String car_number;//车牌号
	private String driver_name;//司机姓名
	private String driver_phone;//电话
	private String send_time;//发货日期
	private String receipt_time;//车到日期
	private String adorn_fee2;//应付装卸费
	private String born_fee;//应收落地费
	private String car_remarks;//大车备注
	private String updatetime;//录入时间
	private String car_length;//车长
	private String car_type;//大车车型
	private String loading_remarks; //应付装卸费核销备注
	private String adorn_fee1; // 实收装卸费
	private String write_off4;  // 核销
	private String ys_unloading_fee; 
	private String transport_pay; // 应收总运费
	private String kuhu_date;//  应收客户核销日期
	private String zhuangxie_date; // 装卸费核销日期
	private String people_kehu; // 应收客户核销人
	private String people_zhuang;  // 装卸费和小人（车）
	private Integer is_arrived;//车辆是否实际到达：未到达0，到达1
	
	
	
	public Integer getIs_arrived() {
		return is_arrived;
	}
	public void setIs_arrived(Integer is_arrived) {
		this.is_arrived = is_arrived;
	}
	public String getPeople_kehu() {
		return people_kehu;
	}
	public void setPeople_kehu(String people_kehu) {
		this.people_kehu = people_kehu;
	}
	public String getPeople_zhuang() {
		return people_zhuang;
	}
	public void setPeople_zhuang(String people_zhuang) {
		this.people_zhuang = people_zhuang;
	}
	public String getKuhu_date() {
		return kuhu_date;
	}
	public void setKuhu_date(String kuhu_date) {
		this.kuhu_date = kuhu_date;
	}
	public String getZhuangxie_date() {
		return zhuangxie_date;
	}
	public void setZhuangxie_date(String zhuangxie_date) {
		this.zhuangxie_date = zhuangxie_date;
	}
	public String getTransport_pay() {
		return transport_pay;
	}
	public void setTransport_pay(String transport_pay) {
		this.transport_pay = transport_pay;
	}
	public String getYs_unloading_fee() {
		return ys_unloading_fee;
	}
	public void setYs_unloading_fee(String ys_unloading_fee) {
		this.ys_unloading_fee = ys_unloading_fee;
	}
	public String getBorn_fee1() {
		return born_fee1;
	}
	public void setBorn_fee1(String born_fee1) {
		this.born_fee1 = born_fee1;
	}
	private String total_cost; // 应收费用总和
	private String trunk_fee; // 应收中转费
	private String take_fee ;  // 应收送货费
	private String adorn_fee; //  应收其他费ring
	private String collection_fee; // 代收到付运费
	private String  paid_fee ; // 实收到付运费
	private String write_off3; // 核销方式  现金 打卡
	private String remarks_fee1; //  应收客户账款备注
	private String write_off5; // 现金打卡 
	
private String remarks_fee2; //  应付客户账款备注
	
	private String born_fee1;//实收落地费
	
	private String  paid;//提付实收
	private String  paid1;//回付实收
	private String  paid3;//到付实收
	private String  trade_agency;//代收款
	
	private String trade_sum;//代收合计
	private String reality_sum;//实收合计
	
	private String  change_pay;//应付中转费
	private String send_fee;//应付送货费
	private String payble_other;//应付其他费
	
	private String pay_sum;//应付款合计
	
	private String change_fee1;//实付中转费
	private String send_fee1;//实付送货费
	private String send_other;//实付其他费
	
	private String reality_pay_sum;//实付款合计
	
	private String trunk_fee_c;//应收中转费
	private String take_fee_c;//应收送货费
	private String adorn_fee_c;//应收其他费
	
	private String fee_sum_c;//应收款合计
	
	private String trunk_fee1_c;//实收中转费
	private String take_fee1_c;//实收送货费
	private String adorn_fee1_c;//实收其他费
	
	private String reality_fee_sum_c;//实收款合计
	
	private String trunk;//中转费
	private String take;//送货费
	private String loading;//落地费
	private String adorn;//其他费
	private String sum;//合计
	
	
	public String getChange_fee1() {
		return change_fee1;
	}
	public String getPaid() {
		return paid;
	}
	public String getSum() {
		return sum;
	}
	public void setSum(String sum) {
		this.sum = sum;
	}
	public String getTrunk() {
		return trunk;
	}
	public void setTrunk(String trunk) {
		this.trunk = trunk;
	}
	public String getTake() {
		return take;
	}
	public void setTake(String take) {
		this.take = take;
	}
	public String getLoading() {
		return loading;
	}
	public void setLoading(String loading) {
		this.loading = loading;
	}
	public String getAdorn() {
		return adorn;
	}
	public void setAdorn(String adorn) {
		this.adorn = adorn;
	}
	public String getTrade_sum() {
		return trade_sum;
	}
	public void setTrade_sum(String trade_sum) {
		this.trade_sum = trade_sum;
	}
	public String getReality_sum() {
		return reality_sum;
	}
	public void setReality_sum(String reality_sum) {
		this.reality_sum = reality_sum;
	}
	public String getPay_sum() {
		return pay_sum;
	}
	public void setPay_sum(String pay_sum) {
		this.pay_sum = pay_sum;
	}
	public String getReality_pay_sum() {
		return reality_pay_sum;
	}
	public void setReality_pay_sum(String reality_pay_sum) {
		this.reality_pay_sum = reality_pay_sum;
	}
	public String getFee_sum_c() {
		return fee_sum_c;
	}
	public void setFee_sum_c(String fee_sum_c) {
		this.fee_sum_c = fee_sum_c;
	}
	public String getReality_fee_sum_c() {
		return reality_fee_sum_c;
	}
	public void setReality_fee_sum_c(String reality_fee_sum_c) {
		this.reality_fee_sum_c = reality_fee_sum_c;
	}
	public String Paid() {
		return paid;
	}
	public void setPaid(String paid) {
		this.paid = paid;
	}
	public String getPaid1() {
		return paid1;
	}
	public void setPaid1(String paid1) {
		this.paid1 = paid1;
	}
	public String getPaid3() {
		return paid3;
	}
	public void setPaid3(String paid3) {
		this.paid3 = paid3;
	}
	public String getTrade_agency() {
		return trade_agency;
	}
	public void setTrade_agency(String trade_agency) {
		this.trade_agency = trade_agency;
	}
	public String getChange_pay() {
		return change_pay;
	}
	public void setChange_pay(String change_pay) {
		this.change_pay = change_pay;
	}
	public String getSend_fee() {
		return send_fee;
	}
	public void setSend_fee(String send_fee) {
		this.send_fee = send_fee;
	}
	public String getPayble_other() {
		return payble_other;
	}
	public void setPayble_other(String payble_other) {
		this.payble_other = payble_other;
	}
	public String Change_fee1() {
		return change_fee1;
	}
	public void setChange_fee1(String change_fee1) {
		this.change_fee1 = change_fee1;
	}
	public String getSend_fee1() {
		return send_fee1;
	}
	public void setSend_fee1(String send_fee1) {
		this.send_fee1 = send_fee1;
	}
	public String getSend_other() {
		return send_other;
	}
	public void setSend_other(String send_other) {
		this.send_other = send_other;
	}
	public String getTrunk_fee_c() {
		return trunk_fee_c;
	}
	public void setTrunk_fee_c(String trunk_fee_c) {
		this.trunk_fee_c = trunk_fee_c;
	}
	public String getTake_fee_c() {
		return take_fee_c;
	}
	public void setTake_fee_c(String take_fee_c) {
		this.take_fee_c = take_fee_c;
	}
	public String getAdorn_fee_c() {
		return adorn_fee_c;
	}
	public void setAdorn_fee_c(String adorn_fee_c) {
		this.adorn_fee_c = adorn_fee_c;
	}
	public String getTrunk_fee1_c() {
		return trunk_fee1_c;
	}
	public void setTrunk_fee1_c(String trunk_fee1_c) {
		this.trunk_fee1_c = trunk_fee1_c;
	}
	public String getTake_fee1_c() {
		return take_fee1_c;
	}
	public void setTake_fee1_c(String take_fee1_c) {
		this.take_fee1_c = take_fee1_c;
	}
	public String getAdorn_fee1_c() {
		return adorn_fee1_c;
	}
	public void setAdorn_fee1_c(String adorn_fee1_c) {
		this.adorn_fee1_c = adorn_fee1_c;
	}
	public String getRemarks_fee2() {
		return remarks_fee2;
	}
	public void setRemarks_fee2(String remarks_fee2) {
		this.remarks_fee2 = remarks_fee2;
	}
	
	
	public String getWrite_off5() {
		return write_off5;
	}
	public void setWrite_off5(String write_off5) {
		this.write_off5 = write_off5;
	}
	public String getRemarks_fee1() {
		return remarks_fee1;
	}
	public void setRemarks_fee1(String remarks_fee1) {
		this.remarks_fee1 = remarks_fee1;
	}
	public String getWrite_off3() {
		return write_off3;
	}
	public void setWrite_off3(String write_off3) {
		this.write_off3 = write_off3;
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
	public String getTotal_cost() {
		return total_cost;
	}
	public void setTotal_cost(String total_cost) {
		this.total_cost = total_cost;
	}
	public String getTrunk_fee() {
		return trunk_fee;
	}
	public void setTrunk_fee(String trunk_fee) {
		this.trunk_fee = trunk_fee;
	}
	public String getTake_fee() {
		return take_fee;
	}
	public void setTake_fee(String take_fee) {
		this.take_fee = take_fee;
	}
	public String getAdorn_fee2() {
		return adorn_fee2;
	}
	public void setAdorn_fee2(String adorn_fee2) {
		this.adorn_fee2 = adorn_fee2;
	}
	public String getWrite_off4() {
		return write_off4;
	}
	public void setWrite_off4(String write_off4) {
		this.write_off4 = write_off4;
	}
	public String getAdorn_fee1() {
		return adorn_fee1;
	}
	public void setAdorn_fee1(String adorn_fee1) {
		this.adorn_fee1 = adorn_fee1;
	}
	public String getLoading_remarks() {
		return loading_remarks;
	}
	public void setLoading_remarks(String loading_remarks) {
		this.loading_remarks = loading_remarks;
	}
	public String getCar_type() {
		return car_type;
	}
	public void setCar_type(String car_type) {
		this.car_type = car_type;
	}
	private String customer_id;//客户id
	private String customer_name;//客户名称
	
	
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
	public String getCar_length() {
		return car_length;
	}
	public void setCar_length(String car_length) {
		this.car_length = car_length;
	}
	public String getCustom_id() {
		return custom_id;
	}
	public void setCustom_id(String custom_id) {
		this.custom_id = custom_id;
	}
	public String getCustom_name() {
		return custom_name;
	}
	public void setCustom_name(String custom_name) {
		this.custom_name = custom_name;
	}
	public String getCar_time() {
		return car_time;
	}
	public void setCar_time(String car_time) {
		this.car_time = car_time;
	}
	public String getSend_station() {
		return send_station;
	}
	public void setSend_station(String send_station) {
		this.send_station = send_station;
	}
	public String getCar_number() {
		return car_number;
	}
	public void setCar_number(String car_number) {
		this.car_number = car_number;
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
	public String getSend_time() {
		return send_time;
	}
	public void setSend_time(String send_time) {
		this.send_time = send_time;
	}
	public String getReceipt_time() {
		return receipt_time;
	}
	public void setReceipt_time(String receipt_time) {
		this.receipt_time = receipt_time;
	}
	public String getAdorn_fee() {
		return adorn_fee;
	}
	public void setAdorn_fee(String adorn_fee) {
		this.adorn_fee = adorn_fee;
	}
	public String getBorn_fee() {
		return born_fee;
	}
	public void setBorn_fee(String born_fee) {
		this.born_fee = born_fee;
	}
	public String getCar_remarks() {
		return car_remarks;
	}
	public void setCar_remarks(String car_remarks) {
		this.car_remarks = car_remarks;
	}
	public String getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}

	
}
