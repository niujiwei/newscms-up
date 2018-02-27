package com.jy.model;

/**
 * 异常反馈图片信息
 * 
 * @author 90
 * 
 */
public class AbnormalImages {
	private String image_id;// 异常图片id
	private String order_id;// 订单id
	private String imageUrl;// 异常图片url
	private String abnormalt_id;// 异常上报id

	public String getImage_id() {
		return image_id;
	}

	public void setImage_id(String image_id) {
		this.image_id = image_id;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getAbnormalt_id() {
		return abnormalt_id;
	}

	public void setAbnormalt_id(String abnormalt_id) {
		this.abnormalt_id = abnormalt_id;
	}

}
