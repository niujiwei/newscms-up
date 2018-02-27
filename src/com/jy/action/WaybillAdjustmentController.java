package com.jy.action;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jy.common.*;
import com.jy.model.*;
import com.jy.service.OrderInfoService;
import com.jy.service.ShippingOrderInfoService;
import com.jy.service.WaybillAdjustmentService;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.plugin.util.UIUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/2/21/021.
 */
@Controller
@RequestMapping(value = "/waybillAdjustment.do")
public class WaybillAdjustmentController {
    @Resource
    private WaybillAdjustmentService waybillAdjustmentService;

    /**
     * 跳转运单调整页面
     *
     * @return
     */
    @RequestMapping(params = "method=toWaybillAdjustmentInfo")
    public String toWaybillAdjustmentInfo() {
        return "waybillAdjustment/waybillAdjustmentInfo";
    }

    /**
     * 运单调整列表获取信息
     * @param rows
     * @param page
     * @param start_time
     * @param end_time
     * @param shiping_order_num
     * @param shiping_order_goid
     * @param applicant_state
     * @param applicant_type
     * @param send_mechanism
     * @param end_address
     * @param receipt_name
     * @param session
     * @return
     */
    @RequestMapping(params = "method=getWaybillAdjustmentListInfo")
    public @ResponseBody Map getWaybillAdjustmentListInfo(String rows, String page, String start_time, String end_time,
                                     String shiping_order_num, String shiping_order_goid, String applicant_state,
                                     String applicant_type, String send_mechanism, String end_address, String receipt_name,HttpSession session) {
        Map<String, Object> map = new HashMap<String, Object>();
        User user = (User) session.getAttribute(SessionInfo.SessionName);
        Integer rows1 = 0;// 当前有几行
        Integer page1 = 1;// 当前有几页
        if (rows != null && !"".equals(rows)) {
            rows1 = Integer.parseInt(rows);
        }
        if (page != null && !"".equals(page)) {
            page1 = Integer.parseInt(page);
        }
        List<WaybillAdjustment> list = waybillAdjustmentService.getWaybillAdjustmentListInfo(user,(page1 - 1)
                * rows1, rows1,start_time,end_time,shiping_order_num,shiping_order_goid,applicant_state,applicant_type,send_mechanism,end_address,receipt_name);
        int count = waybillAdjustmentService.getCountWaybillAdjustment(user,start_time,end_time,shiping_order_num,shiping_order_goid,applicant_state,applicant_type,send_mechanism,end_address,receipt_name);
        map.put("total", count);
        map.put("rows", list);
        return map;
    }

    /**
     * 运单调整查询运单页面
     * @return
     */
    @RequestMapping(params = "method=waybillAdjustmentSearchOrder")
    public String waybillAdjustmentSearchOrder() {
        return "waybillAdjustment/waybillAdjustmentSearchOrder";
    }

    /**
     * 跳转运单调整页面
     * @param shiping_order_id
     * @param request
     * @return
     */
    @RequestMapping(params = "method=toAddWaybillAdjustmentInfo")
    public String toAddWaybillAdjustmentInfo(String shiping_order_id,
                                    HttpServletRequest request) {
        request.setAttribute("shiping_order_id", shiping_order_id);
        return "waybillAdjustment/addWaybillAdjustmentInfo";
    }

    /**
     * 通过运单id查询运单信息
     * @param orderId
     * @return
     */
    @RequestMapping(params = "method=getShippingOrderMessage")
    public @ResponseBody JSONObject getShippingOrderMessage(String orderId){
        JSONObject jsonObject = new JSONObject();
        ShippingOrder order = waybillAdjustmentService.getShppingOrderMessage(orderId);
        jsonObject.put("order",order);
        return jsonObject;
    }



    /**
     * 添加运单调整
     * @param orderData
     * @param changeMessage
     * @param applicant_message
     * @param applicant_type
     * @param session
     * @return
     */
    @RequestMapping(params = {"method=addWaybillAdjustmentInfo"})
    public @ResponseBody JSONObject addWaybillAdjustmentInfo(String orderData,String changeMessage,String applicant_message,int applicant_type,HttpSession session,String shiping_order_num){
        Gson gson = new Gson();
        JSONObject jsonObject = new JSONObject();
        User user = (User) session.getAttribute(SessionInfo.SessionName);
        WaybillAdjustmentOrder waybillAdjustmentOrder = gson.fromJson(orderData,WaybillAdjustmentOrder.class);
        String orderId = waybillAdjustmentOrder.getShiping_order_id();
        WaybillAdjustment waybillAdjustment = WaybillAdjustmentUtils.getWaybillAdjustment(user,applicant_message,applicant_type,orderId);
        int saveWaybillAdjustmentInfo  = waybillAdjustmentService.saveWaybillAdjustmentInfo(waybillAdjustment);
        if (saveWaybillAdjustmentInfo>0){
            String waybillAdjustmentId = waybillAdjustment.getId();
            waybillAdjustmentOrder.setId(UUIDUtils.getUUID());
            waybillAdjustmentOrder.setWaybill_adjustment_id(waybillAdjustmentId);
            int booleanSaveWaybillAdjustment = waybillAdjustmentService.saveWaybillAdjustment(waybillAdjustmentOrder);
            List<WaybillAdjustmentHistory> waybillAdjustmentHistoryList = gson.fromJson(changeMessage,new TypeToken<List<WaybillAdjustmentHistory>>(){}.getType());
            int waybillAdjustmentHistoryList1 =  waybillAdjustmentService.saveWaybillAdjustmentHistoryList(waybillAdjustmentId,waybillAdjustmentHistoryList);
            boolean b = booleanSaveWaybillAdjustment>0&&waybillAdjustmentHistoryList1>0;
            waybillAdjustmentService.updateOrderState(orderId,"1");
            HistoryUtils.saveHistory(orderId,shiping_order_num,"申请运单调整");

            jsonObject.put("success",b);
        }
        return jsonObject;
    }

    /**
     * 跳转取消签收
     * @return
     */
    @RequestMapping(params = "method=toCancelSignOrderInfo")
    public String toCancelSignOrderInfo() {
        return "waybillAdjustment/cancelSignOrderInfo";
    }

    /**
     * 添加取消签收
     * @param shiping_order_id
     * @param applicant_message
     * @param applicant_type
     * @param session
     * @return
     */
    @RequestMapping(params = {"method=addCancelSignInfo"})
    public @ResponseBody JSONObject addCancelSignInfo(String shiping_order_id,String applicant_message,int applicant_type,HttpSession session,String shiping_order_num){
        User user = (User) session.getAttribute(SessionInfo.SessionName);
        WaybillAdjustment waybillAdjustment = WaybillAdjustmentUtils.getWaybillAdjustment(user,applicant_message,applicant_type,shiping_order_id);
        int saveWaybillAdjustmentInfo  = waybillAdjustmentService.saveWaybillAdjustmentInfo(waybillAdjustment);
        boolean b = saveWaybillAdjustmentInfo>0;
        if(b) waybillAdjustmentService.updateOrderState(shiping_order_id,"2");
        HistoryUtils.saveHistory(shiping_order_id,shiping_order_num,"申请取消签收");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("success",b);
        return jsonObject;
    }

    /**
     * 跳珠调整运单审核页面
     * @param id
     * @param request
     * @return
     */
    @RequestMapping(params = "method=toWaybillAuditInfo")
    public String toWaybillAuditInfo(String id, HttpServletRequest request) {
        request.setAttribute("id",id);
        return "waybillAdjustment/waybillAuditInfo";
    }

    /**
     * 获取运单审核bean信息
     * @param id
     * @return
     */
    @RequestMapping(params = "method=getWaybillAuditInfo")
    public @ResponseBody JSONObject getWaybillAuditInfo(String id){
        WaybillAdjustment waybillAdjustment = waybillAdjustmentService.getWaybillAuditInfo(id);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("success",waybillAdjustment);
        return jsonObject;
    }

    /**
     * 根据运单调整id获取运单调整记录
     * @param id
     * @return
     */
    @RequestMapping(params = {"method=getWaybillAuditHistoryListInfo"})
    public @ResponseBody JSONObject getWaybillAuditHistoryListInfo(String id){
        List<WaybillAdjustmentHistory> list =waybillAdjustmentService.getWaybillAuditHistoryListInfo(id);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("success",list);
        return jsonObject;
    }

    /**
     *运单调整审核处理
     * @param id
     * @param shiping_order_id
     * @return
     */
    @RequestMapping(params = {"method=doWaybillAuditInfo"})
    public @ResponseBody JSONObject doWaybillAuditInfo(String id,String shiping_order_id,String auditor_message,HttpSession session,String applicant_type,String shiping_order_num){
        User user = (User) session.getAttribute(SessionInfo.SessionName);
        JSONObject jsonObject = new JSONObject();
        WaybillAdjustment waybillAdjustment = new WaybillAdjustment();
        waybillAdjustment.setAuditor_message(auditor_message);
        waybillAdjustment.setAuditor_id(user.getId());
        waybillAdjustment.setAuditor_name(user.getRealName());
        waybillAdjustment.setAuditor_username(user.getUsername());
        waybillAdjustment.setShiping_order_id(shiping_order_id);
        waybillAdjustment.setId(id);
        waybillAdjustment.setApplicant_state(1);
        boolean b=false;
        if(applicant_type.equals("1")){
            b = WaybillAdjustmentUtils.doCancelSign(shiping_order_id);
        }else if (applicant_type.equals("0")){
            b =  WaybillAdjustmentUtils.doUpdateOderMessage(id,shiping_order_id);
        }
        if (b){
            int  i  = waybillAdjustmentService.doWaybillAuditInfo(waybillAdjustment);
            String message = "运单调整审核通过";
            HistoryUtils.saveHistory(shiping_order_id,shiping_order_num,message);
            jsonObject.put("success",i>0);
        }
        return jsonObject;
    }

    /**
     *
     * @param id
     * @param request
     * @return
     */
    @RequestMapping(params = "method=toLookWaybillAuditInfo")
    public String toLookWaybillAuditInfo(String id, HttpServletRequest request) {
        request.setAttribute("id",id);
        return "waybillAdjustment/lookWaybillAuditInfo";
    }

}
