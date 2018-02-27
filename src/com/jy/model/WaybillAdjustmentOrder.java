package com.jy.model;

/**
 * 运单bean
 * 
 * @author zzp
 * 
 */
public class WaybillAdjustmentOrder extends ShippingOrder{
	private String id;
	private String waybill_adjustment_id;

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
}
