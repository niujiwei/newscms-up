package com.jy.model;

/**
 * 短信模板保存bean
 * 
 * @author zzp
 * 
 */
public class MsgModel {
	private String model_id;// 短信id
	private String model_title;// 短信标题
	private String model_content;// 短信内容
	private String updatetime;// 更新时间
	
	
	private String receiveUserName;// 接受用户名
	private String receiveUserId;// 接收用户ID
	private String orderMsg;// 信息
	private String orderMsgId;// 消息Id
	private String orderMsgState;// msg状态
	private String sendUserName;// 发送用户名
	private String sendUserId;// 发送用户Id
	private String orderMsgTitle;// Msg标题
	private String receivetime;//信息查看时间
	
	
	public String getReceivetime() {
		return receivetime;
	}

	public void setReceivetime(String receivetime) {
		this.receivetime = receivetime;
	}

	public String getOrderMsgTitle() {
		return orderMsgTitle;
	}

	public void setOrderMsgTitle(String orderMsgTitle) {
		this.orderMsgTitle = orderMsgTitle;
	}

	public String getReceiveUserName() {
		return receiveUserName;
	}

	public void setReceiveUserName(String receiveUserName) {
		this.receiveUserName = receiveUserName;
	}

	public String getReceiveUserId() {
		return receiveUserId;
	}

	public void setReceiveUserId(String receiveUserId) {
		this.receiveUserId = receiveUserId;
	}

	public String getSendUserName() {
		return sendUserName;
	}

	public void setSendUserName(String sendUserName) {
		this.sendUserName = sendUserName;
	}

	public String getSendUserId() {
		return sendUserId;
	}

	public void setSendUserId(String sendUserId) {
		this.sendUserId = sendUserId;
	}

	public String getOrderMsgState() {
		return orderMsgState;
	}

	public void setOrderMsgState(String orderMsgState) {
		this.orderMsgState = orderMsgState;
	}

	public String getOrderMsg() {
		return orderMsg;
	}

	public void setOrderMsg(String orderMsg) {
		this.orderMsg = orderMsg;
	}

	public String getOrderMsgId() {
		return orderMsgId;
	}

	public void setOrderMsgId(String orderMsgId) {
		this.orderMsgId = orderMsgId;
	}

	public String getModel_id() {
		return model_id;
	}

	public void setModel_id(String model_id) {
		this.model_id = model_id;
	}

	public String getModel_title() {
		return model_title;
	}

	public void setModel_title(String model_title) {
		this.model_title = model_title;
	}

	public String getModel_content() {
		return model_content;
	}

	public void setModel_content(String model_content) {
		this.model_content = model_content;
	}

	public String getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}
}
