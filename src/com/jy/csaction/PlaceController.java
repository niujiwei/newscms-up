package com.jy.csaction;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * 位置区域管理
 * @author zzp
 *
 */
@Controller
@RequestMapping(value="/place.do")
public class PlaceController {
	//跳转编辑页面并且获取合同的id 返回到页面
	@RequestMapping(params = "method=toplace")
	public String getEditAgreementPage(String pid, HttpServletRequest request) {
		request.setAttribute("flg", pid);
		return "csms/resources/place";
	}
}
