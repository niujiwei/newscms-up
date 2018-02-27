package com.jy.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.jy.dao.MessageManageDao;
import com.jy.model.MsgModel;
import com.jy.model.User;
import com.jy.service.MessageManage;

@Component
public class MessageManageImpl implements MessageManage {
	@Resource
	private MessageManageDao mm;

	public void updateOrderMsgState(MsgModel msg) {
		mm.updateOrderMsgState(msg);
	};

	public MsgModel getShowMsg(String id) {

		return mm.getShowMsg(id);
	};

	public int deleteMsg(String id) {
		return mm.deleteMsg(id);
	}

	public int getMsgCount(String pid, String sendUserName, String updatetime,
			String orderMsgState, String orderMsg, User user,
			String receiveUserName, String startDate, String endDate,
			String orderMsgTitle) {
		if (user.getFlag().equals("0")) {
			return mm.getAllMsgCount(sendUserName, updatetime, orderMsgState,
					orderMsg, receiveUserName, startDate, endDate,
					orderMsgTitle);
		} else {
			return mm.getMsgCount(user.getId(), sendUserName, updatetime,
					orderMsgState, orderMsg, receiveUserName, startDate,
					endDate, orderMsgTitle);
		}
	}

	// 接收信息
	public List<MsgModel> getMessage(Integer page, Integer rows, String id,
			String sendUserName, String updatetime, String orderMsgState,
			String orderMsg, User user, String receiveUserName,
			String startDate, String endDate, String orderMsgTitle) {
		List<MsgModel> list = new ArrayList<MsgModel>();
		if (user.getFlag().equals("0")) {
			 list= mm.getAllMsg(page, rows, sendUserName,
					updatetime, orderMsgState, orderMsg, receiveUserName,
					startDate, endDate, orderMsgTitle);
			
		} else  {
			list = mm.getMessage(page, rows, user.getId(),
					sendUserName, updatetime, orderMsgState, orderMsg,
					receiveUserName, startDate, endDate, orderMsgTitle);
			
		}
		return list;
	}

	// 发送消息
	public int sendMsg(MsgModel Msg) {
		int a = mm.sendMsg(Msg);
		return a;
	}

	// 消息提醒总人数
	public int getAgingCount(String realname, String pid, String user_address,
			User user) {
		
		int total = 0;
		if (user.getFlag().equals("0")) {// 管理员
			total = mm.getAllUserCount(user.getId(),realname,user_address);
		} else if (user.getFlag().equals("1")) {// 司机
			/*String cid = allId.getDriver_id();
			total = mm.getdriverUserCount(cid, realname);*/
		} else if (user.getFlag().equals("2")) {// 供应商
			
			total = mm.getSuppliersUserCount(user.getSuppliers_id(), user.getId(),
					realname,user_address);
		} else if (user.getFlag().equals("3")||user.getFlag().equals("4")) {// 信息员,项目部
			total = mm.getMessageSuppliersCount(realname,
					user.getCustomer_id(), user_address);

		}
		return total;
	};

	// 联系人
	public List<User> getMessagePeople(Integer page, Integer rows,
			String realname, String id, User user, String user_address) {
		// User allId = mm.getMessagePeopleID(id);
		List<User> userList = null;
		if(user!=null){
			if (user.getFlag().equals("0")) {
				// 管理员
				userList = mm.getAllUser(page, rows,user.getId() ,realname,user_address);

			} else if (user.getFlag().equals("1")) {
				/*// 司机
				userList = mm.getdriverUser(page, rows, user.getDriver_id(), realname);*/

			} else if (user.getFlag().equals("3")||user.getFlag().equals("4")) {
				// 项目部
				userList = mm.getMessageSuppliersUser(page, rows,
						user.getCustomer_id(), realname,user_address);

			} else if (user.getFlag().equals("2")) {
				// 供应商

				userList = mm.getSupplersUser(page, rows, user.getSuppliers_id(),
						user.getId(), realname,user_address);

			}
		}
		
		return userList;
	}

	public int updateMsgState(String messageId, String state, String userId) {
	
		return mm.updateMsgState(messageId, state, userId);
	}
	
	
}
