package com.jy.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jy.common.SessionInfo;
import com.jy.dao.Json;
import com.jy.model.Equipment_info;
import com.jy.model.User;
import com.jy.service.EquipmentManService;
import com.jy.service.UserInfoService;

/**
 * 2015-5-4 15:35:24
 * 
 * @author zkj
 */
@Controller
@RequestMapping(value = "/equipment.do")
public class EquipmentController {

	@Resource
	private EquipmentManService equipmentManService;

	@Resource
	private UserInfoService uis;

	/**
	 * 去设备管理页面
	 */
	@RequestMapping(params = "method=getequipmentMan")
	public String PersonInfo(String menu_id, HttpSession session) {
		Integer[] rid = (Integer[]) session
				.getAttribute(SessionInfo.SessionRote);
		Integer id = Integer.parseInt(menu_id);
		List<String> list = uis.getFunctions(id, rid);
		if (list.size() > 0) {
			StringBuffer bf = new StringBuffer();
			for (String ss : list) {
				bf.append(ss);
				bf.append(",");

			}
			bf.deleteCharAt(bf.length() - 1);
			session.setAttribute("function", bf.toString());
		} else {
			session.setAttribute("function", "");

		}
		return "equipment/equipmentMan";
	}

	/**
	 * 添加设备页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "method=addEquipment")
	public String addEquipment() {
		return "equipment/addEquipment";
	}

	/**
	 * 修改设备页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "method=editEquipment")
	public String editEquipment(String pid, HttpServletRequest reuqest) {
		reuqest.setAttribute("flg", pid);
		return "equipment/editEquipment";
	}

	/**
	 * 分页查询
	 */
	@RequestMapping(params = "method=getEquipmentManlist")
	public @ResponseBody
	Map<String, Object> EquipmentManList(String page, String rows,
			String equipment_number, String carid, String department_id,String car_number,HttpSession session) {
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
		User user = (User) session.getAttribute(SessionInfo.SessionName);
		List<Equipment_info> list = equipmentManService.getEquipmentManInfo(
				(page2 - 1) * rows1, rows1, equipment_number, carid,
				department_id,car_number,user);
		int total = equipmentManService.getCount(equipment_number, carid,
				department_id,car_number,user);
		// for(Equipment_info lids: list){
		// System.out.println(lids.getDepartment_Name());
		// }
		// json返回串
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", total);
		map.put("rows", list);
		return map;
	}

	/**
	 * 添加设备
	 * 
	 * @param equipment_info
	 * @return
	 */
	@RequestMapping(params = "method=saveEquipment")
	public @ResponseBody
	Json saveEquipment(Equipment_info equipment_info) {
		Json json = new Json();
		int flag = equipmentManService.checkEquipment(equipment_info
				.getEquipment_number());
		if (flag > 0) {
			json.setAns("设备号已存在");
		} else {
			int truck = equipmentManService.InfoManAdd(equipment_info);
			if (truck > 0)
				json.setFlag(true);
			else
				json.setFlag(false);
		}
		return json;
	}

	/**
	 * 查询要修改的信息
	 */

	@RequestMapping(params = "method=getUpdateEquipment")
	public @ResponseBody
	Equipment_info getUpdateEquipment(String pid) {
		Json json = new Json();
		Equipment_info equipment_info = equipmentManService
				.getUpdateEquipment(pid);
		return equipment_info;
	}

	/**
	 * 修改设备信息
	 * 
	 * @param equipment_info
	 * @return
	 */
	@RequestMapping(params = "method=updateEquipment")
	public @ResponseBody
	Json updateEquipment(Equipment_info equipment_info) {
		Json json = new Json();
		boolean b = isNumeric0(equipment_info.getInstitutions_id());
		if (!b) {
			equipment_info.setInstitutions_id(uis
					.namegetDepartment(equipment_info.getInstitutions_id()));
		}
		int singerflag = equipmentManService.checkEquipmentSinger(
				equipment_info.getEquipment_number(),
				equipment_info.getEquipment_id());
		if (singerflag == 0) {
			int flag = equipmentManService.checkEquipment(equipment_info
					.getEquipment_number());
			if (flag > 0) {
				json.setAns("设备号已存在");
			} else {
				int truck = equipmentManService.updateEquipment(equipment_info);
				if (truck > 0)
					json.setFlag(true);
				else
					json.setFlag(false);
			}
		} else {
			int truck = equipmentManService.updateEquipment(equipment_info);
			if (truck > 0)
				json.setFlag(true);
			else
				json.setFlag(false);
		}

		return json;

	}

	/**
	 * 删除设备
	 * 
	 * @param del
	 * @return
	 */
	@RequestMapping(params = "method=deleteEquipment")
	public @ResponseBody
	Json delTruck(String[] del) {
		Json json = new Json();
		int truck = equipmentManService.deleteEquipment(del);
		if (truck > 0) {
			json.setFlag(true);
			return json;
		}
		json.setFlag(false);
		return json;
	}

	// 获取设备号检索列表
	@RequestMapping(params = "method=getInfoManlength")
	public @ResponseBody
	String getCar_length(String search) {
		List<Equipment_info> list = equipmentManService
				.getEquipment_number(search);
		JSONObject jb = null;
		JSONArray jsons = new JSONArray();
		for (Equipment_info t : list) {
			jb = new JSONObject();
			jb.put("id", t.getEquipment_id());
			jb.put("name", t.getEquipment_number());
			jsons.add(jb);
		}
		return jsons.toString();
	}

	/**
	 * 绑定操作
	 * 
	 * @param search
	 * @return
	 */
	@RequestMapping(params = "method=bundlingUserInfo")
	public @ResponseBody
	JSONObject bundlingUserInfo(String[] id) {

		JSONObject jb = new JSONObject();
		int i = equipmentManService.bundlingUserInfo(id);
		if (i > 0) {
			jb.put("message", true);
		} else {
			jb.put("message", false);
		}

		return jb;
	}

	/**
	 * 解除绑定操作
	 * 
	 * @param search
	 * @return
	 */
	@RequestMapping(params = "method=unbundlingUserInfo")
	public @ResponseBody
	JSONObject unbundlingUserInfo(String[] id) {

		JSONObject jb = new JSONObject();
		int i = equipmentManService.unbundlingUserInfo(id);
		if (i > 0) {
			jb.put("message", true);
		} else {
			jb.put("message", false);
		}

		return jb;
	}

	/**
	 * 查询用户信息
	 * 
	 * @param username
	 * @return
	 */
	@RequestMapping(params = "method=getUserName")
	public @ResponseBody
	String getUserName(String username) {
		List<User> list = equipmentManService.getUserName(username);
		JSONObject jb = null;
		JSONArray jsons = new JSONArray();
		for (User t : list) {
			jb = new JSONObject();
			jb.put("id", t.getId());
			jb.put("name", t.getUsername());
			jb.put("dn", t.getDrivername());
			jsons.add(jb);
		}
		return jsons.toString();
	}

	// 获取车牌号
	@RequestMapping(params = "method=getCarIdlength")
	public @ResponseBody
	String getCarid_length(String carid) {
		List<Equipment_info> list = equipmentManService.getCar_id(carid);
		System.out.println(list.size());
		JSONObject jb = null;
		JSONArray jsons = new JSONArray();
		for (Equipment_info t : list) {
			jb = new JSONObject();
			jb.put("id", t.getTravel_card_id());
			jb.put("name", t.getPlate_number1());
			jsons.add(jb);
		}
		return jsons.toString();
	}

	// 获取车牌号
	@RequestMapping(params = "method=getCarIdlengthAdd")
	public @ResponseBody
	String getCarid_lengthAdd(String carid) {
		List<Equipment_info> list = equipmentManService.getCar_idAdd(carid);
		System.out.println(list.size() + "====");
		JSONObject jb = null;
		JSONArray jsons = new JSONArray();
		for (Equipment_info t : list) {
			jb = new JSONObject();
			jb.put("id", t.getTravel_card_id());
			jb.put("name", t.getPlate_number1());
			jsons.add(jb);
		}
		return jsons.toString();
	}

	private static boolean isNumeric0(String str) {
		for (int i = str.length(); --i >= 0;) {
			int chr = str.charAt(i);
			if (chr < 48 || chr > 57)
				return false;
		}
		return true;
	}
	
	// 获取车牌号
		@RequestMapping(params = "method=getCarNumberCount")
		public @ResponseBody
		JSONObject getCarNumberCount(String carNumber) {
			JSONObject jb = new JSONObject();
			int count = equipmentManService.getCarNumberCount(carNumber);
			if(count>0) jb.put("flag", true);
			else jb.put("flag", false);
			return jb;
		}
}
