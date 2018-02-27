package com.jy.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jy.common.SessionInfo;
import com.jy.common.UUIDUtils;
import com.jy.model.MsgModel;
import com.jy.model.User;
import com.jy.service.MessageManage;

@Controller
@RequestMapping(value = "messageManage.do")
public class messageManageController {
	@Resource
	private MessageManage mm;

	//
	// @RequestMapping(params="method=getShowMessage")
	// public @ResponseBody MsgModel getShowMessage(String pid){
	// MsgModel data=mm.getShowMsg(pid);
	// return data;

	// }
	// @RequestMapping(params ="method=msgGet")
	// public String msgGet(String msgid,HttpServletRequest request){
	// MsgModel msg=new MsgModel();
	// msg.setOrderMsgId(msgid);
	// msg.setOrderMsgState("已读");
	// mm.updateOrderMsgState(msg);
	// request.setAttribute("flg", msgid);
	// return "pushmessage/showMsg";
	// }
	@RequestMapping(params = "method=deleteMsg")
	public @ResponseBody
	int deleteMsg(String id) {
		String[] allId = id.split(",");
		int a = 0;
		for (int i = 0; i < allId.length; i++) {
			a = mm.deleteMsg(allId[i]);
			System.out.println(a);
		}
		return a;
	}

	@RequestMapping(params = "method=receiveMessage")
	public String receiveMessage() {
		return "pushmessage/receiveMessage";
	}

	@RequestMapping(params = "method=getMessage")
	public @ResponseBody
	Map<String, Object> getMessage(String page, String rows, String pid,
			String sendUserName, String updatetime, String orderMsgState,
			String orderMsg, String orderMsgId, String receiveUserName,
			String startDate, String endDate, HttpServletRequest requelst,
			String orderMsgTitle) {
		// 当前第几页
		Integer page2 = 1;
		// 当前页一共几条
		Integer rows1 = 0;
		if (page != null && !"".equals(page)) {
			page2 = Integer.parseInt(page);
		}
		if (rows != null && !"".equals(rows)) {
			rows1 = Integer.parseInt(rows);
		}
		User user = (User) requelst.getSession().getAttribute(
				SessionInfo.SessionName);
		int total = mm.getMsgCount(pid, sendUserName, updatetime,
				orderMsgState, orderMsg, user, receiveUserName, startDate,
				endDate, orderMsgTitle);
		List<MsgModel> msg = mm.getMessage((page2 - 1) * rows1, rows1, pid,
				sendUserName, updatetime, orderMsgState, orderMsg, user,
				receiveUserName, startDate, endDate, orderMsgTitle);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", total);
		map.put("rows", msg);
		return map;

	}

	/**
	 * 
	 * 消息管理页面
	 */
	@RequestMapping(params = "method=messageManageInfo")
	public String messageManage() {
		return "pushmessage/messageManageInfo";
	};

	/**
	 * 消息发送
	 */
	@RequestMapping(params = "method=sendOrderMsg")
	public @ResponseBody
	int sendOrderMsg(String id, String person, String ordeMsg, String pid,
			String orderMsgTitle, HttpServletRequest requelst) {
		int a = 0;
		// System.out.println(id);
		if (id == null) {
			return a = 0;
		}
		String[] allId = id.split(",");
		String[] uName = person.split(",");
		for (int i = 0; i < allId.length; i++) {
			MsgModel msg = new MsgModel();
			msg.setOrderMsgState("1");
			msg.setOrderMsgId(UUIDUtils.getUUID());
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					"yyyy:MM:dd hh:mm:ss");
			msg.setUpdatetime(dateFormat.format(new Date()));
			msg.setReceiveUserId(allId[i]);
			msg.setReceiveUserName(uName[i]);
			msg.setOrderMsg(ordeMsg);
			msg.setSendUserId(pid);
			User user = (User) requelst.getSession().getAttribute(
					SessionInfo.SessionName);
			msg.setSendUserName(user.getRealName());
			msg.setOrderMsgTitle(orderMsgTitle);
			a = mm.sendMsg(msg);

		}
		;
		return a;
	}

	/**
	 * 
	 * 消息发送界面
	 * 
	 */
	@RequestMapping(params = "method=sendMessageManage")
	public String sendmessageManage() {
		return "pushmessage/messageManage";
	};

	/**
	 * 获取联系人
	 */
	@RequestMapping(params = "method=getMessagePeople")
	public @ResponseBody
	Map<String, Object> getMessagePeople(String page, String rows,
			String realname, String user_address, String pid,
			HttpServletRequest requelst) {
		// 当前第几页
		Integer page2 = 1;
		// 当前页一共几条
		Integer rows1 = 0;
		if (page != null && !"".equals(page)) {
			page2 = Integer.parseInt(page);
		}
		if (rows != null && !"".equals(rows)) {
			rows1 = Integer.parseInt(rows);
		}
		User user = (User) requelst.getSession().getAttribute(
				SessionInfo.SessionName);
		int total = mm.getAgingCount(realname, pid, user_address, user);
		List<User> list = mm.getMessagePeople((page2 - 1) * rows1, total,
				realname, pid, user, user_address);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", total);
		map.put("rows", list);
		return map;
	}

	/**
	 * 跳转到查看信息页面
	 */
	@RequestMapping(params = "method=toLookMessageInfo")
	public String toLookMessageInfo(String pid, HttpServletRequest requelst) {
		requelst.setAttribute("pid", pid);
		return "pushmessage/lookMessageInfo";
	}

	/**
	 * 跳转到查看信息页面
	 */
	@RequestMapping(params = "method=getLookMessageInfo")
	public @ResponseBody
	JSONObject getLookMessageInfo(String pid, HttpServletRequest requelst) {
		JSONObject json = new JSONObject();
		MsgModel msgModel = mm.getShowMsg(pid);
		json.put("success", msgModel);
		return json;
	}
	
	/**
	 * 跳转到查看信息页面
	 */
	@RequestMapping(params = "method=updateMessageState")
	public @ResponseBody
	JSONObject updateMessageState(String pid,String state,HttpServletRequest requelst) {
		JSONObject json = new JSONObject();
		User user = (User) requelst.getSession().getAttribute(
				SessionInfo.SessionName);
	    int i = mm.updateMsgState(pid, state, user.getId());
		json.put("success", i);
		return json;
	}
	

}
