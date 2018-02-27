package com.jy.model;

import java.util.List;

/**
 * tree的模版
 * 
 * @author 90
 * 
 */
public class ComboTreeModel {
	private String id;// 树节点id
	private String text;// 树节点信息
	private String iconCls;// 树形图片
	private String state;// 树节点的状态
	private String fatherId;// 父节点id
	private String fatherName;// 父节点名字
	private String tel;// 电话
	private String address;// 地址
	private List<ComboTreeModel> children;// 子节点

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public String getFatherId() {
		return fatherId;
	}

	public void setFatherId(String fatherId) {
		this.fatherId = fatherId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<ComboTreeModel> getChildren() {
		return children;
	}

	public void setChildren(List<ComboTreeModel> children) {
		this.children = children;
	}
}
