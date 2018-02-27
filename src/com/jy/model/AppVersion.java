package com.jy.model;

import java.util.List;

public class AppVersion {
	private int app_id;
	private String version;// 版本号
	private List<String> message;//更新信息
	private int locationTime;// 定位时间

	public List<String> getMessage() {
		return message;
	}

	public void setMessage(List<String> message) {
		this.message = message;
	}

	public int getLocationTime() {
		return locationTime;
	}

	public void setLocationTime(int locationTime) {
		this.locationTime = locationTime;
	}

	public int getApp_id() {
		return app_id;
	}

	public void setApp_id(int app_id) {
		this.app_id = app_id;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
}
