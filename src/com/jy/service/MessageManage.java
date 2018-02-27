package com.jy.service;

import java.util.List;

import com.jy.model.MsgModel;
import com.jy.model.User;

public interface MessageManage {
	public void updateOrderMsgState(MsgModel msg);

	public MsgModel getShowMsg(String id);

	public int deleteMsg(String id);

	int getMsgCount(String pid, String sendUserName, String updatetime,
			String orderMsgState, String orderMsg, User user,
			String receiveUserName, String startDate, String endDate,
			String orderMsgTitle);

	public List<MsgModel> getMessage(Integer page, Integer rows, String id,
			String sendUserName, String updatetime, String orderMsgState,
			String orderMsg, User user, String receiveUserName,
			String startDate, String endDate, String orderMsgTitle);

	public int sendMsg(MsgModel Msg);

	List<User> getMessagePeople(Integer page, Integer rows1, String realname,
			String pid, User user, String user_address);

	public int getAgingCount(String realname, String pid, String user_address,
			User user);
	
	/**
	 * 消息已读
	 * 
	 * @param userId
	 * @param state
	 * @return
	 */
	int updateMsgState(String messageId, String state,String userId);
	
}
