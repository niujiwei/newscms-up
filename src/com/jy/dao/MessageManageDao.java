package com.jy.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jy.model.Customer;
import com.jy.model.Department;
import com.jy.model.JySuppliers;
import com.jy.model.MsgModel;
import com.jy.model.User;

/**
 * 发送消息
 * 
 * @author zhb
 * 
 */

public interface MessageManageDao {
	// 管理员获取全部的信息数量
	int getAllMsgCount(String sendUserName, String updatetime,
			String orderMsgState, String orderMsg, String receiveUserName,
			String startDate, String endDate, String orderMsgTitle);

	// 管理员获取全部的信息
	List<MsgModel> getAllMsg(Integer page, Integer rows, String sendUserName,
			String updatetime, String orderMsgState, String orderMsg,
			String receiveUserName, String startDate, String endDate,
			String orderMsgTitle);

	// 管理员
	int getAllUserCount(String id, String realname,String address);

	List<User> getAllUser(Integer page, Integer rows, String id, String realName,String address);

	// 项目部
	List<User> getMessageSuppliersUser(Integer page, Integer rows, String id,
			String realname,String address);

	int getMessageSuppliersCount(String realname, String pid,
			String user_address);

	// 供应商
	List<User> getSupplersUser(Integer page, Integer rows, String id,
			String cid, String realname,String address);

	String getsuppliers_customer(String id);

	int getSuppliersUserCount(String id, String cid, String realname,String address);

	// 司机
	List<User> getdriverUser(Integer page, Integer rows, String id,
			String realname);

	int getdriverUserCount(String id, String realname);

	void updateOrderMsgState(MsgModel stste);

	MsgModel getShowMsg(String id);

	int deleteMsg(String id);

	int getMsgCount(String id, String sendUserName, String updatetime,
			String orderMsgState, String orderMsg, String receiveUserName,
			String startDate, String endDate, String orderMsgTitle);

	// 接收信息
	List<MsgModel> getMessage(Integer page, Integer rows, String id,
			String sendUserName, String updatetime, String orderMsgState,
			String orderMsg, String receiveUserName, String startDate,
			String endDate, String orderMsgTitle);

	// 发送信息
	int sendMsg(MsgModel mm);

	int getMessageCount(String username, String pid, String user_address);

	// 获取相关的联系人ID
	User getMessagePeopleID(String id);

	// 获取Id的身份
	int getUser_Roler(String id);

	// 获取供应商name
	List<JySuppliers> getMessageSuppliersID(Integer page, Integer rows,
			String id);

	// 获取Suppliers_customer
	User getMessagerSuppliers_customer(String id);

	Customer getMessagerCustomer(String id);

	Department getDepartmentTel(String id);
	
	/**
	 * 消息已读
	 * 
	 * @param userId
	 * @param state
	 * @return
	 */
	int updateMsgState(@Param("messageId") String messageId,
			@Param("state") String state,@Param("userId")String userId);
}
