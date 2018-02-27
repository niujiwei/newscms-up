package com.jy.model;

public class AppMenu {
	private Integer app_menu_id;//APP菜单id
	private String app_menu_name;//APP菜单名字
	private String app_menu_mark;//APP菜单备注
	private String app_menu_icon;//APP菜单图标
	private String app_role_id;//APP 角色id
	
	
	public String getApp_role_id() {
		return app_role_id;
	}
	public void setApp_role_id(String app_role_id) {
		this.app_role_id = app_role_id;
	}
	public String getApp_menu_icon() {
		return app_menu_icon;
	}
	public void setApp_menu_icon(String app_menu_icon) {
		this.app_menu_icon = app_menu_icon;
	}
	public Integer getApp_menu_id() {
		return app_menu_id;
	}
	public void setApp_menu_id(Integer app_menu_id) {
		this.app_menu_id = app_menu_id;
	}
	
	public String getApp_menu_name() {
		return app_menu_name;
	}
	public void setApp_menu_name(String app_menu_name) {
		this.app_menu_name = app_menu_name;
	}
	public String getApp_menu_mark() {
		return app_menu_mark;
	}
	public void setApp_menu_mark(String app_menu_mark) {
		this.app_menu_mark = app_menu_mark;
	}
	

}
