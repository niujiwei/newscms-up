package com.jy.action;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jy.common.MD5;
import com.jy.common.QRCodeEncoderHandler;
import com.jy.common.SessionInfo;
import com.jy.dao.Json;
import com.jy.model.AbnormalReport;
import com.jy.model.Menu;
import com.jy.model.ShippingTimes;
import com.jy.model.User;
import com.jy.service.AbnormalReportService;
import com.jy.service.LoginUserService;
import com.jy.service.UserInfoService;

@Controller
@RequestMapping("/login.do")
public class LoginUserController {

	@Resource
	private LoginUserService loginUserService;

	@Resource
	private UserInfoService userService;
	
	@Resource
	private AbnormalReportService ar_service;

	// 登录
	@RequestMapping(params = "method=login")
	public String userLogin(User user, HttpSession session, Model model) {
		boolean flag = loginUserService.checkUser(user);
		if (flag == true) {
			User userinfo = loginUserService.getUserInfo(user);
			// user信息存入session
			session.setAttribute(SessionInfo.SessionName, userinfo);
			Integer[] roteId = loginUserService.getUserRote(userinfo.getId());
			// user权限存入session
			if (roteId.length > 0) {
				session.setAttribute(SessionInfo.SessionRote, roteId);
				loginUserService.updateUserTime(userinfo.getId());
			} else {
				model.addAttribute("errornote", "1");
				return "redirect:/login.jsp";
			}
			return "layout/layout";
		}
		model.addAttribute("erroruser", "1");
		return "redirect:/login.jsp";

	}

	// 退出
	@RequestMapping(params = "method=loginout")
	public String loginOut(User user, HttpSession session) {
		if (session.getAttribute(SessionInfo.SessionName) != null) {
			session.removeAttribute(SessionInfo.SessionName);
			session.invalidate();
		}
		return "redirect:/login.jsp";
	}

	// 判断密码
	@RequestMapping(params = "method=checkPas")
	public @ResponseBody
	Json checkPas(String password, HttpSession session) {
		Json json = new Json();
		User user = (User) session.getAttribute(SessionInfo.SessionName);
		MD5 md5 = new MD5();
		int flag = loginUserService.checkPas(user.getId(),
				md5.getMD5ofStr(password));
		if (flag > 0) {
			json.setFlag(true);
		} else {
			json.setFlag(false);
		}
		return json;
	}

	/*
	 * @RequestMapping(params = "method=updatePas") public @ResponseBody Json
	 * updatePas(String password,HttpSession session) { Json json=new Json();
	 * User user=(User)session.getAttribute(SessionInfo.SessionName); MD5
	 * md5=new MD5(); int flag =
	 * loginUserService.updatePas(user.getId(),md5.getMD5ofStr(password));
	 * if(flag>0){ json.setFlag(true); }else{ json.setFlag(false); } return
	 * json; }
	 */

	/*
	 * // 获取部门的数据转换json数据
	 * 
	 * @RequestMapping(params = "method=getDepartment") public @ResponseBody
	 * List<Map<String, Object>> getDepartment() { List<Department> list =
	 * departmentService.getDepartmentInfo(); List<Map<String, Object>>
	 * returnlist = new ArrayList<Map<String, Object>>(); Map<String, Object>
	 * maps = null; for (Department department : list) { maps = new
	 * HashMap<String, Object>(); maps.put("id", department.getDepartment_id());
	 * maps.put("text", department.getDepartment_name()); returnlist.add(maps);
	 * } return returnlist; }
	 */

	// 注册方法
	/*
	 * @RequestMapping(params = "method=addUser") public String addUser(User
	 * user) { int i = loginUserService.addUser(user); return
	 * "redirect:/login.jsp"; }
	 */
	// 获取权限
	@RequestMapping(params = "method=getRoles")
	public @ResponseBody
	String getRoles(HttpSession session) {
		User user = (User) session.getAttribute(SessionInfo.SessionName);
		List<String> role = loginUserService.getRoles(user.getId());
		StringBuffer sb = new StringBuffer();
		for (String rs : role) {
			sb.append(rs);
			sb.append(",");

		}
		if(sb.length()>0){
			sb.deleteCharAt(sb.length() - 1);
		}
		
		return sb.toString();
	}

	// 获取部门名称
	@RequestMapping(params = "method=getDepartmentName")
	public @ResponseBody
	String getDepartmentName(HttpSession session) {
		User user = (User) session.getAttribute(SessionInfo.SessionName);
		String dName = userService.getDepartment(user.getDid());
		return dName;
	}

	// 二维码下载
	@RequestMapping(params = "method=getQrcode")
	public void getQrcode(HttpServletResponse response, String content) {
		QRCodeEncoderHandler handler = new QRCodeEncoderHandler();
		BufferedImage bi = handler.encoderQRCode(content, 4);
		try {
			ImageIO.write(bi, "png", response.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("encoder QRcode success");
	}

	// 欢迎页面
	@RequestMapping(params = "method=welcome")
	public String loginOut() {

		return "layout/center";
	}
	
	@RequestMapping(params = "method=getOrderCountMessage")
	public @ResponseBody
	List<Map<String, Integer>> getOrderCountMessage(HttpSession session,String result,String abnormal_result,String startDate) {
		User user = (User) session.getAttribute(SessionInfo.SessionName);
		List<Map<String, Integer>> list  = loginUserService.getOrderCountMessage(result,abnormal_result,user,startDate);
		
		return list;
	}
	
	@RequestMapping(params = "method=getAbnormalReportInfo")
	public @ResponseBody
	Map getAbnormalReportInfo(HttpSession session,String rows ,String pages) {
		int row =0;
		int page = 0;
		if(rows!=null&&!rows.equals("")){ 
			row = Integer.parseInt(rows);
		}
		if(pages!=null&&!pages.equals("")){
			page = Integer.parseInt(pages);
		}
		User user = (User) session.getAttribute(SessionInfo.SessionName);
		Map<String, Object> map = new HashMap<String, Object>();
		List<AbnormalReport> list  = loginUserService.getAbnormalReportInfo(row*page,page,user);
		
		map.put("list", list);
		//map.put("total", count);
		return map;
	}
	
	/**
	 * 获取时效列表
	 * @param result
	 * @param session
	 * @param rows
	 * @param pages
	 * @return
	 */
	@RequestMapping(params = "method=getShippingTimes")
	public @ResponseBody
	List<ShippingTimes> getShippingTimes(String result,HttpSession session,String rows ,String pages) {
		int row =0;
		int page = 0;
		if(rows!=null&&!rows.equals("")){ 
			row = Integer.parseInt(rows);
		}
		if(pages!=null&&!pages.equals("")){
			page = Integer.parseInt(pages);
		}
		List<ShippingTimes> list  = loginUserService.getShippingTimes(result,row,page);
		return list;
	}
	
	@RequestMapping(params = "method=centerAddTabInfo")
	public @ResponseBody
	JSONObject centerAddTabInfo(HttpSession session,String tittle) {
		JSONObject json = new JSONObject();
		User user = (User) session.getAttribute(SessionInfo.SessionName);
		List<Menu> menu =  loginUserService.centerAddTabInfo(user, tittle);
		json.put("menu", menu);
		return json;
	}
	

	
}
