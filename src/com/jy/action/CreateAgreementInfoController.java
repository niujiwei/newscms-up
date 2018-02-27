/*package com.jy.action;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jy.common.ExportUtils;
import com.jy.common.UUIDUtils;
import com.jy.dao.Json;
import com.jy.model.Company;
import com.jy.model.CreateAgreement;
import com.jy.model.GuanlianCA;
import com.jy.model.OrderHistory;
import com.jy.model.ShippingOrder;
import com.jy.service.CompanyInfoService;
import com.jy.service.CreateAgreementInfoService;
import com.jy.service.DepartmentService;
import com.jy.service.ShippingOrderInfoService;


 * 货运订单管理
 * 
@Controller
@RequestMapping(value = "/createA.do")
public class CreateAgreementInfoController {
	@Resource
	private CreateAgreementInfoService csi;
	@Resource
	private ShippingOrderInfoService soi;
	@Resource
	private CompanyInfoService cif; 
	@Resource
	private DepartmentService depnService;

	@RequestMapping(params = "method=getCreateAgreeInfo")
	public String getCreateAgreementInfoMan() {
		return "create_agreement/createAgreemenInfo";
	}

	// 跳转编辑页面并且获取合同的id 返回到页面
	@RequestMapping(params = "method=getEditCreateAgreePage")
	public String getEditCreateAgreementPage(String[] pid,
			HttpServletRequest request) {
		request.setAttribute("flg", pid);
		return "create_agreement/EditCreateAgreement";
	}

	// 制作跳转页面
	@RequestMapping(params = "method=getCreateAgreePage")
	public String getCreateAgreementPage() {
		return "create_agreement/searchCreateAgreement";
	}

	// 跳转到详细页面
	@RequestMapping(params = "method=xiangxiEditCreateA")
	public String XxCreateAgreement(String pid, HttpServletRequest request) {
		request.setAttribute("pid", pid);
		return "create_agreement/xiangxiECAgreement";
	}

	*//**
	 * 删除单个文件
	 * 
	 * @param sPath
	 *            被删除文件的文件名
	 * @return 单个文件删除成功返回true，否则返回false
	 *//*
	public boolean deleteFile(String sPath) {
		boolean flag;
		flag = false;
		File file = new File(sPath);
		// 路径为文件且不为空则进行删除
		if (file.isFile() && file.exists()) {
			file.delete();
			flag = true;
		}
		return flag;
	}
	@RequestMapping(params = "method=getCreateAgreement")
	public @ResponseBody
	Map getCreateAgreementInfo(String rows, String page, String id,
			String start_date, String end_date,String car_name,String car_number1,String agreement_type,String end_address,String company_id,String phone_number,String order_state,String departid) {
		Integer rows1 = 0;// 当前有几行
		Integer page1 = 1;// 当前有几页
		if (rows != null && !"".equals(rows)) {
			rows1 = Integer.parseInt(rows);
		}
		if (page != null && !"".equals(page)) {
			page1 = Integer.parseInt(page);
		}
		List<CreateAgreement> list = csi.getCreateAgreementInfo((page1 - 1)
				* rows1, rows1, id, start_date, end_date, car_name,car_number1,agreement_type,end_address,company_id,phone_number,order_state,departid);
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getIsagain() == 1) {
				list.get(i).setOrder_state(4);
			}
		}
		int count = csi.getCreateAgreement(id, start_date, end_date,car_name,car_number1,agreement_type,end_address,company_id,phone_number,order_state,departid);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", count);
		map.put("rows", list);
		return map;
	}

	
	 * 批量删除
	 
	@RequestMapping(params = "method=deleCreateA")
	public @ResponseBody
	Json delCreateAgreement(String[] del, String[] dela) {
		Json json = new Json();
		String message="所在协议已作废";
		if (dela.length > 0) {
			int truck = csi.deleteCreateAgreement(dela);
			if (truck > 0) {
				List<GuanlianCA> list = csi.orderNumber(dela);
				String [] pid=new String[list.size()];
				int i=0;
				for(GuanlianCA ca : list){
					pid[i++]=ca.getOrder_id();
				}
				List<ShippingOrder> ships=soi.getShipOrderCAM((1 - 1) * pid.length,pid.length,pid.length==0?null:pid);
				for(ShippingOrder sh:ships){
					saveHistory(sh.getShiping_order_id(),sh.getShiping_order_num(),message);
				}
				json.setFlag(true);
			} else {
				json.setFlag(false);
			}
		}
		if (del.length > 0) {
			List<GuanlianCA> list = csi.orderNumber(del);
			int number = csi.deleToOder(del);
			int order = csi.deleState(list);
			int truck = csi.deleteCreateAgreement(del);
			if (truck > 0 && number > 0 && order > 0) {
				String [] pid=new String[list.size()];
				int i=0;
				for(GuanlianCA ca : list){
					pid[i++]=ca.getOrder_id();
				}
				List<ShippingOrder> ships=soi.getShipOrderCAM((1 - 1) * pid.length,pid.length,pid.length==0?null:pid);
				for(ShippingOrder sh:ships){
					saveHistory(sh.getShiping_order_id(),sh.getShiping_order_num(),message);
				}
				json.setFlag(true);
			} else {
				json.setFlag(false);

			}
		}
		
		 * List<GuanlianCA> list=csi.orderNumber(del); int
		 * number=csi.deleToOder(del); int order=csi.deleState(list);
		 * 
		 * int truck = csi.deleteCreateAgreement(del); if (truck >
		 * 0&&number>0&&order>0) { json.setFlag(true); return json; }
		 * json.setFlag(false);
		 
		return json;
	}

	// 保存
	@RequestMapping(params = "method=saveCreateAgreement")
	public @ResponseBody
	Json saveCreateAgreement(CreateAgreement d, String[] pid) {

		List<GuanlianCA> list = new ArrayList<GuanlianCA>();
		Json json = new Json();
		d.setAgreement_id(UUIDUtils.getUUID());
		for (int i = 0; i < pid.length; i++) {
			GuanlianCA ca = new GuanlianCA();
			ca.setOrder_id(pid[i]);
			ca.setAgreement_id(d.getAgreement_id());
			ca.setId(UUIDUtils.getUUID());
			list.add(ca);
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy/MM/dd HH:mm:ss");
		String number = dateFormat.format(new Date());
		d.setAgreement_number(number.substring(0, 4) + number.substring(5, 7)
				+ number.substring(8, 10) + number.substring(11, 13)
				+ number.substring(14, 16) + number.substring(17, 19));
		d.setCreate_time(number);
		d.setNumber(pid.length);
		int truck = csi.saveCreateAgreement(d);
		int create = csi.saveAgreementToorder(list);
		int state = 0;
		String message=null;
		if (d.getAgreement_type() == 0) {
			message="正在发往\t"+depnService.getDepartment(d.getLdp_id()).getDepartment_name()+"\t"+d.getCar_number1()+d.getCar_name()+"\t联系电话:"+d.getPhone_number();;
			state = csi.updateState(pid, 2);
		} else if (d.getAgreement_type() == 1) {
			message="正在配送\t司机:"+d.getCar_number1()+d.getCar_name()+"\t联系电话为:"+d.getPhone_number();
			state = csi.updateState(pid, 1);
		} else if (d.getAgreement_type() == 2) {
			message="转运中\t"+cif.getUpdateCompany(d.getCompany_id()).getCompany_name()+"\t司机:"+d.getCar_number1()+"\t"+d.getCar_name()+"\t联系电话:"+d.getPhone_number();
			state = csi.updateState(pid, 2);
		}
		List<ShippingOrder> ships=soi.getShipOrderCAM((1 - 1) * pid.length,pid.length,pid.length==0?null:pid);
		
		for(ShippingOrder sh:ships){
			saveHistory(sh.getShiping_order_id(),sh.getShiping_order_num(),message);
		}
		if (truck > 0 && create > 0 && state > 0) {
			json.setFlag(true);
			return json;
		} else {
			json.setFlag(false);
			return json;
		}

	}

	public void saveHistory(String ids,String num, String message){
		List<OrderHistory> order=new ArrayList<OrderHistory>();
		for(int i=0;i<num.length;i++){
			
		}
		OrderHistory h=new OrderHistory();
		h.setOrder_history_id(UUIDUtils.getUUID());
		h.setOrders_id(ids);
		h.setOrder_arrive_time(DateFormat.getDateTimeInstance().format(new Date()));
		h.setGoods_arrive_remakes(h.getOrder_arrive_time()+"---订单号为"+num+"\t"+message);
		order.add(h);
		int count=soi.saveOrderHistory(order);
	}
	// 修改查询
	@RequestMapping(params = "method=getUpdateCreateAgreement")
	public @ResponseBody
	CreateAgreement getUpdateCreateAgreement(String id) {
		CreateAgreement der = csi.getUpdateCreateAgreement(id);
		return der;
	}

	// 修改查询
	@RequestMapping(params = "method=getXXCreateAgreement")
	public @ResponseBody
	List<GuanlianCA> getXXCreateAgreement(String id) {
		List<GuanlianCA> der = csi.getXXCreateAgreement(id);
		return der;
	}

	// 导入页面
	@RequestMapping(params = "method=inputCreateAgreement")
	public String inputCreateAgreement() {
		return "driver/InputDriver";
	}

	// 修改
	@RequestMapping(params = "method=updateCreateAgreement")
	public @ResponseBody
	Json updateCreateAgreement(CreateAgreement d) {
		d.setCreate_time(DateFormat.getDateTimeInstance().format(new Date()));
		Json json = new Json();
		int i = csi.updateCreateAgreement(d);
		if (i > 0) {
			json.setFlag(true);
			return json;
		}
		json.setFlag(false);
		return json;
	}

	
	 * 重复查询
	 
	@RequestMapping(params = "method=getNumber")
	public @ResponseBody
	CreateAgreement getCreateAgreementLength(String number) {
		CreateAgreement der = csi.getNumber(number);
		return der;
	}

	// 车牌检索
	@RequestMapping(params = "method=getPlateNumberLength")
	public @ResponseBody
	String getPlateNumberLength(String number) {
		List<CreateAgreement> list = csi.getPlateNumberLength(number);
		JSONObject jb = null;
		JSONArray jsons = new JSONArray();
		for (CreateAgreement d : list) {
			jb = new JSONObject();
			jb.put("id", d.getCar_id());
			jb.put("name", d.getCar_number1());
			jsons.add(jb);
		}
		return jsons.toString();
	}

	// 查询中转 getCompany
	@RequestMapping(params = "method=getCompany")
	public @ResponseBody
	String getCompany(String name) {
		List<Company> list = csi.getCompany(name);
		JSONObject jb = null;
		JSONArray jsons = new JSONArray();
		for (Company c : list) {
			jb = new JSONObject();
			jb.put("id", c.getCompany_id());
			jb.put("name", c.getCompany_name());
			jb.put("ren", c.getEnd_people());
			jb.put("phones", c.getEnd_phone());
			jsons.add(jb);
		}
		return jsons.toString();
	}

	// 导出信息
	@RequestMapping(params = "method=outAgreement")
	public @ResponseBody
	String outAgreement(String[] agreementIds, HttpServletResponse response)
			throws FileNotFoundException {
		String filename = "协议信息导出";
		Properties prop = new Properties();
		InputStream in = CreateAgreementInfoController.class
				.getResourceAsStream("/../agreementData.properties");
		// InputStream in = new BufferedInputStream (new
		// FileInputStream("../webapps\\surmax\\agreementData.properties"));
		List<String> headtitle = new ArrayList<String>();
		List<String> fieldName = new ArrayList<String>();
		try {
			prop.load(in);
			Iterator<String> it = prop.stringPropertyNames().iterator();
			while (it.hasNext()) {
				String key = it.next();
				headtitle.add(key);
				fieldName.add(prop.getProperty(key));
			}
			in.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		} // /加载属性列表
		List<ShippingOrder> list = csi.getCreateAgreementAll(agreementIds);
		for(ShippingOrder ship:list){
			if("0".equals(ship.getAgreement_type())){
				ship.setChange_pay("");
				ship.setSend_fee("");
				ship.setChange_remarks("");
				ship.setSend_remarks("");
				ship.setChange_other("");
				ship.setPractiacl_num(0);
			}else if("1".equals(ship.getAgreement_type())){
				ship.setChange_pay("");
				ship.setChange_remarks("");
				ship.setChange_other("");
			}else if("2".equals(ship.getAgreement_type())){
				ship.setSend_fee("");
				ship.setSend_remarks("");
				ship.setPractiacl_num(0);
			}
		}
		try {
			// 声明一个工作薄
			HSSFWorkbook workbook = new HSSFWorkbook();
			// 生成一个表格
			response.setContentType("application/vnd.ms-excel;");
			response.setHeader("Content-Disposition", "attachment;filename=\""
					+ new String(filename.getBytes("gbk"), "iso-8859-1")
					+ ".xls\"");
			OutputStream ouputStream = response.getOutputStream();
			// 调用工具类创建表头
			ExportUtils.outputHeaders(
					(String[]) headtitle.toArray(new String[headtitle.size()]),
					workbook, "协议信息导出");
			// 调用工具类生成内容
			ExportUtils.outputColums(
					(String[]) fieldName.toArray(new String[fieldName.size()]),
					list, workbook, 1, "协议信息导出");
			workbook.write(ouputStream);
			ouputStream.flush();
			ouputStream.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(params = "method=selectAgreement")
	public String selectCompany() {
		return "create_agreement/selectCompany";
	}

	@RequestMapping(params = "method=selectCar")
	public String selectCar() {
		return "create_agreement/selectCar";
	}

	
	 * 制作协议修改运单价格
	 

	@RequestMapping(params = "method=updataMoney")
	public @ResponseBody
	String updataMoney(String id, String practiacl_num, String send_fee,
			String send_remarks, String change_pay, String change_remarks,String chanage_pay_type,String change_other) throws UnsupportedEncodingException {
		if(send_remarks!=null){
			send_remarks=new String(send_remarks.getBytes("ISO-8859-1"),"utf-8");
		}
		if(change_remarks!=null){
			change_remarks=new String(change_remarks.getBytes("ISO-8859-1"),"utf-8");
		}
		csi.updataMoney(id, practiacl_num, send_fee, send_remarks, change_pay,
				change_remarks,chanage_pay_type,change_other);
		return null;
	}
	*//**
	 * 修改总金额
	 * *//*
	@RequestMapping(params = "method=updataZong")
	public @ResponseBody
	Json updataZong(String cid,String money){
		Json json=new Json();
		int i=csi.updataZong(cid, money,null);
		if (i > 0) {
			json.setFlag(true);
			return json;
		}
		json.setFlag(false);
		return json;
	} 
	*//**
	 * 移除协议中的订单
	 * *//*
	@RequestMapping(params = "method=removeShip")
	public @ResponseBody
	Json removeShip(String cid,String[]pid){
		Json json=new Json();
 		//删除中间表
		int i=csi.removeShip(cid, pid);
		csi.updataZong(cid, null,pid.length);
		
		//重制未签收的订单
		List<GuanlianCA> list =new ArrayList<GuanlianCA>();
		for(int j=0;j<pid.length;j++){
			GuanlianCA lian=new GuanlianCA();
			lian.setOrder_id(pid[j]);
			list.add(lian);
		}
		int order = csi.deleState(list);
		
		//添加移除信息
		String message="已从协议中移除";
		List<ShippingOrder> ships=soi.getShipOrderCAM((1 - 1) * pid.length,pid.length,pid.length==0?null:pid);
		for(ShippingOrder sh:ships){
			saveHistory(sh.getShiping_order_id(),sh.getShiping_order_num(),message);
		}
		if (i > 0) {
			json.setFlag(true);
			return json;
		}
		json.setFlag(false);
		return json;
	} 
}
*/