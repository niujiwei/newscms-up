package com.jy.action;

import com.jy.common.*;
import com.jy.csaction.BaseAction;
import com.jy.excel.bean.ReverseOrderImport;
import com.jy.excel.bean.SubsidyInfoImport;
import com.jy.model.*;
import com.jy.service.OrderInfoService;
import com.jy.service.ReverseOrderInfoService;
import com.jy.service.ShippingOrderInfoService;
import com.jy.service.impl.ReverseOrderInfoServiceImpl;
import net.sf.json.JSONObject;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.util.*;

/**
 * 逆向物流运单管理
 *
 * @author 90
 */
@Controller
@RequestMapping(value = "/reverse.do")
public class ReverseOrderInfoController extends BaseAction {
    @Resource
    private ReverseOrderInfoService csi;
    @Resource
    private OrderInfoService ordersercice;
    @Resource
    private ShippingOrderInfoService shippingOrderInfoService;
    String filePath="WebRoot/reverserInfoFiles/";

    /**
     * 跳转逆向物流
     *
     * @return
     */
    @RequestMapping(params = {"method=toReverseOrderInfo"})
    public String toReverseOrderInfo() {
        return "reverseOrder/reverseOrderInfo";
    }

    /**
     * 获取逆向物流列表查询信息
     *
     * @param session
     * @param rows
     * @param page
     * @return
     */
    @RequestMapping(params = {"method=getReverseOrderInfo"})
    public
    @ResponseBody
    Map<String, Object> getReverseOrderInfo(HttpSession session, String rows, String page, String send_time, String end_time, String shiping_order_num, String end_address,
                                            String end_mechanism, String custom_contact, String order_type, String receipt_contact, String send_mechanism
    ) {
        Integer rows1 = 0;// 当前有几行
        Integer page1 = 1;// 当前有几页
        if (rows != null && !"".equals(rows)) {
            rows1 = Integer.parseInt(rows);
        }
        if (page != null && !"".equals(page)) {
            page1 = Integer.parseInt(page);
        }
        User user = (User) session.getAttribute(SessionInfo.SessionName);

        return csi.getReverseOrderInfo(user, (page1 - 1)
                * rows1, rows1, send_time, end_time, shiping_order_num, end_address, end_mechanism, custom_contact, order_type, receipt_contact, send_mechanism);
    }

    /**
     * 跳转逆向添加信息
     *
     * @return
     */
    @RequestMapping(params = {"method=toAddReverseOrderInfo"})
    public String toAddReverseOrderInfo() {
        return "reverseOrder/addReverseOrder";
    }

    /**
     * 保存逆向物流信息
     *
     * @param session
     * @param create_type
     * @param reverserOrderModel
     * @return
     */
    @RequestMapping(params = {"method=addReverseOrderInfo"})
    public
    @ResponseBody
    JSONObject addReverseOrderInfo(HttpSession session, String create_type, ReverserOrderModel reverserOrderModel) {
        JSONObject jsonObject = new JSONObject();
        User user = (User) session.getAttribute(SessionInfo.SessionName);

        boolean b = csi.addReverseOrderInfo(user, reverserOrderModel);//保存信息
        if (b) {
            String message = ReverseOrderInfoServiceImpl.insertMessage(user, reverserOrderModel);
            csi.saveReverseOrderHistory(reverserOrderModel,message,user);//保存历史信息
            csi.saveReverserOrderCityInfo(reverserOrderModel);//保存城市信息
        }
        jsonObject.put("success", b);
        return jsonObject;
    }



    /**
     * 跳转退货分配信息页面
     *
     * @return
     */

    @RequestMapping(params = {"method=toDistributionDriver"})
    public String toDistributionDriver() {

        return "reverseOrder/driverInfoNews";
    }

    /**
     * 跳转分配逆向物流订单页面
     *
     * @param request
     * @param driverId
     * @return
     */
    @RequestMapping(params = {"method=toDistributionReverseOrder"})
    public String toDistributionReverseOrder(HttpServletRequest request, String driverId) {
        request.setAttribute("driverId", driverId);
        return "reverseOrder/distributionReverseOrder";
    }

    /**
     * 获取分配列表信息
     *
     * @param session
     * @param rows
     * @param page
     * @return
     */
    @RequestMapping(params = {"method=getDistributionReverseOrderInfo"})
    public
    @ResponseBody
    Map<String, Object> getDistributionReverseOrderInfo(HttpSession session, String rows, String page, String send_time, String end_time,
                                                        String end_address, String send_mechanism, String custom_contact,String order_type) {
        Map<String, Object> map = new HashMap<String, Object>();
        Integer rows1 = 0;// 当前有几行
        Integer page1 = 1;// 当前有几页
        if (rows != null && !"".equals(rows)) {
            rows1 = Integer.parseInt(rows);
        }
        if (page != null && !"".equals(page)) {
            page1 = Integer.parseInt(page);
        }
        User user = (User) session.getAttribute(SessionInfo.SessionName);
        map = csi.getDistributionReverseOrderInfo(user, (page1 - 1)
                * rows1, rows1,send_time, end_time, end_address, send_mechanism, custom_contact,order_type);
        return map;
    }

    /**
     * 保存分配信息
     *
     * @param driverId
     * @param orders
     * @return
     */
    @RequestMapping(params = {"method=saveDistributionReverseOrderInfo"})
    public
    @ResponseBody
    JSONObject saveDistributionReverseOrderInfo(String driverId, String[] orders) {
        JSONObject jsonObject = new JSONObject();
        boolean b = csi.saveDistributionReverseOrderInfo(driverId, orders);
        jsonObject.put("flag", b);
        return jsonObject;
    }

    /**
     * 去逆向物流接单页面
     *
     * @param driverId
     * @param request
     * @return
     */
    @RequestMapping(params = {"method=toTakingReverseOrderInfo"})
    public String toTakingReverseOrderInfo(String driverId, HttpServletRequest request) {
        request.setAttribute("driverId", driverId);
        return "reverseOrder/takingReverserOrder";
    }


    /**
     * 获取逆向物流接单列表
     *
     * @param session
     * @param rows
     * @param page
     * @param driverId
     * @return
     */
    @RequestMapping(params = {"method=getTakingReverseOrderInfo"})
    public
    @ResponseBody
    Map<String, Object> getTakingReverseOrderInfo(HttpSession session, String rows, String page, String driverId,String send_time, String end_time,
                                                  String end_address, String send_mechanism, String custom_contact,String order_type) {
        Map<String, Object> map = new HashMap<String, Object>();
        Integer rows1 = 0;// 当前有几行
        Integer page1 = 1;// 当前有几页
        if (rows != null && !"".equals(rows)) {
            rows1 = Integer.parseInt(rows);
        }
        if (page != null && !"".equals(page)) {
            page1 = Integer.parseInt(page);
        }
        User user = (User) session.getAttribute(SessionInfo.SessionName);
        map = csi.getTakingReverseOrderInfo(user, (page1 - 1)
                * rows1, rows1,driverId,send_time,end_time,end_address,send_mechanism,custom_contact,order_type);
        return map;
    }

    /**
     * 取消分配信息
     *
     * @param driverId
     * @param orderIds
     * @return
     */
    @RequestMapping(params = {"method=cancelDistributionReverseOrder"})
    public
    @ResponseBody
    JSONObject cancelDistributionReverseOrder(String driverId, String[] orderIds) {
        JSONObject jsonObject = new JSONObject();
        if (orderIds == null || orderIds.length == 0) return jsonObject;
        List<String> ids = Arrays.asList(orderIds);
        boolean b = csi.cancelDistributionReverseOrder(driverId, ids);
        int i = 0;
        if (b) {

            i = csi.updateReverserOrderState(ids, "", 0);
            ReverserOrderModel reverserOrderModel;
            for (String id:ids){
                reverserOrderModel= csi.getReverseOrderInfoByIds(id);
                csi.saveReverseOrderHistory(reverserOrderModel,"运单删除逆向分配",null);
            }

        }
        jsonObject.put("flag", i > 0);
        return jsonObject;
    }

    /**
     * 跳转订单接单
     *
     * @param id
     * @param request
     * @return
     */
    @RequestMapping(params = {"method=toOrderShipment"})
    public String toOrderShipment(String id, HttpServletRequest request) {
        request.setAttribute("id", id);
        return "reverseOrder/orderShipment";
    }


    /**
     * 获取逆向物流模板信息根据id
     *
     * @param id
     * @return
     */
    @RequestMapping(params = {"method=getReverseOrderInfoById"})
    public
    @ResponseBody
    JSONObject getReverseOrderInfoById(String id) {
        JSONObject jsonObject = new JSONObject();
        ReverserOrderModel model = csi.getReverseOrderInfoByIds(id);
        jsonObject.put("success", model);
        return jsonObject;
    }

    /**
     * 上传文件信息
     *
     * @param file
     * @param request
     * @param response
     */
    @RequestMapping(params = "method=uploadImage")
    public void uploadImage(@RequestParam("file") MultipartFile file,
                            HttpServletRequest request, HttpServletResponse response) {
        BaseAction base = new BaseAction();
        try {
            base.upload(file, "reverse_files", request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新接单信息
     *
     * @param session
     * @param reverserOrderModel
     * @return
     */
    @RequestMapping(params = {"method=toSaveTakingReverserOrder"})
    public
    @ResponseBody
    JSONObject toSaveTakingReverserOrder(HttpSession session, ReverserOrderModel reverserOrderModel) {
        JSONObject jsonObject = new JSONObject();
        if (reverserOrderModel == null) reverserOrderModel = new ReverserOrderModel();
        boolean b = csi.takingReverserOrder(reverserOrderModel.getShiping_order_id(), reverserOrderModel.getDriver_id(), reverserOrderModel.getRemarks(), 1);
        if (b) {
            reverserOrderModel.setShipping_order_state(2);
            boolean update =csi.updateReverserOrder(reverserOrderModel);
            int type = reverserOrderModel.getOrder_type();
            if(update&&type==1){
                saveShippingOrder(reverserOrderModel);
            }
            String filenames = (String) session.getAttribute("filename");
            if (filenames != null) {
                String[] file = filenames.split(",");
                ShipperOrderImg shipperOrderImg;
                List<ShipperOrderImg> shipperOrderImgs = new ArrayList<ShipperOrderImg>();
                for (int j = 0; j < file.length; j++) {
                    shipperOrderImg = new ShipperOrderImg();
                    shipperOrderImg.setOrder_id(reverserOrderModel.getShiping_order_id());
                    shipperOrderImg.setImage_id(UUIDUtils.getUUID());
                    shipperOrderImg.setImageUrl(file[j]);
                    shipperOrderImgs.add(shipperOrderImg);
                }
                csi.batchSaveTakingReverseImage(shipperOrderImgs);
            }
            boolean bb =saveReverseHistory(reverserOrderModel.getShiping_order_id(),reverserOrderModel.getShiping_order_num(),"司机已经接货发运");
            jsonObject.put("success", bb);
        }
        return jsonObject;
    }

    public int saveShippingOrder(ReverserOrderModel reverserOrderModel){
        if (reverserOrderModel==null) return 0;
        ShippingOrder shippingOrder = new ShippingOrder();
        shippingOrder.setShiping_order_id(reverserOrderModel.getShiping_order_id());
        shippingOrder.setEnd_address(reverserOrderModel.getEnd_mechanism());
        shippingOrder.setShipping_order_state(0);
        shippingOrder.setRemarks("逆向物流调拨");
        shippingOrder.setGoods_num(Integer.parseInt(reverserOrderModel.getReal_num()));
        shippingOrder.setSend_mechanism(reverserOrderModel.getSend_mechanism());
        shippingOrder.setShiping_order_num(reverserOrderModel.getShiping_order_num());
        shippingOrder.setSend_time(DateUtils.format(new Date(), "yyyy-MM-dd"));
        shippingOrder.setReceipt(reverserOrderModel.getReceipt_name());
        shippingOrder.setReceipt_tel(reverserOrderModel.getReceipt_tel());
        shippingOrder.setReceipt_name(reverserOrderModel.getReceipt_contact());
        shippingOrder.setUpdatetime(DateUtils.format(new Date(), "yyyy-MM-dd hh:mm:ss"));
        shippingOrder.setCustom_contact(reverserOrderModel.getCustom_contact());
        shippingOrder.setCustom_tel(reverserOrderModel.getCustom_tel());
        shippingOrder.setSend_station(reverserOrderModel.getEnd_address());
        shippingOrder.setEnd_mechanism(reverserOrderModel.getReceipt_address());
        shippingOrder.setGoods_vol(reverserOrderModel.getGoods_vol());
        shippingOrder.setGoods_weight(reverserOrderModel.getGoods_weight());
        shippingOrder.setEnd_mechanism(reverserOrderModel.getReceipt_address());
        shippingOrder.setRemarks(reverserOrderModel.getRemarks());
        shippingOrder.setCustom_name(reverserOrderModel.getCustom_name());
        shippingOrder.setGoods_name(reverserOrderModel.getGoods_name());
        shippingOrder.setShiping_order_goid(reverserOrderModel.getShiping_order_goid());
        shippingOrder.setIs_recept("1");
        Aging aging = ordersercice.getNewAging(
                shippingOrder.getSend_mechanism(), shippingOrder.getEnd_address(), shippingOrder.getUpdatetime());
        if (aging != null) {
            shippingOrder.setAging_time(aging.getAging_time());
            shippingOrder.setAging_day(aging.getAging_day());
        }
        int i = shippingOrderInfoService.saveShipOrder(shippingOrder);
        if(i>0) {
          saveHistory(shippingOrder.getShiping_order_id(), shippingOrder.getShiping_order_num(),
                  "逆向物流调拨录入");
        }
        return 0;
    }

    /**
     * 保存逆向物流运单信息
     * @param id
     * @param orderNum
     * @param message
     * @return
     */
    public static boolean saveReverseHistory(String id,String orderNum,String message){
        ReverseOrderInfoService csi = SpringContextHolder.getBean("ReverseOrderInfoService");
        OrderHistory orderHistory = new OrderHistory();
        orderHistory.setOrder_history_id(UUIDUtils.getUUID());
        orderHistory.setOrders_id(id);
        orderHistory.setOrder_arrive_time(DateFormat.getDateTimeInstance().format(new Date()));
        orderHistory.setGoods_arrive_remakes(orderHistory.getOrder_arrive_time() +"---货运编号:"+orderNum+ " "+message);
        return csi.saveReverseOrderHistory(orderHistory);
    }

    public void  saveHistory(String ids, String num, String message) {
        List<OrderHistory> order = new ArrayList<OrderHistory>();
        OrderHistory h = new OrderHistory();
        h.setOrder_history_id(UUIDUtils.getUUID());
        h.setOrders_id(ids);
        h.setOrder_arrive_time(DateFormat.getDateTimeInstance().format(
                new Date()));
        h.setGoods_arrive_remakes(h.getOrder_arrive_time() + "---货运编号为" + num
                + "\t" + message);
        order.add(h);
        shippingOrderInfoService.saveOrderHistory(order);
    }
    /**
     * 跳转逆向物流签收页面
     *
     * @return
     */
    @RequestMapping(params = {"method=toSignReverseOrderInfo"})
    public String toSignReverseOrderInfo() {
        return "reverseOrder/signReverseOrder";
    }

    /**
     * 获取逆向物流签收列表
     *
     * @param session
     * @param rows
     * @param page
     * @return
     */
    @RequestMapping(params = {"method=getSignReverseOrderInfo"})
    public
    @ResponseBody
    Map<String, Object> getSignReverseOrderInfo(HttpSession session, String rows, String page,String send_time,String end_time,String shiping_order_num,String shipping_order_state,String order_type,String send_mechanism,String end_address,String end_mechanism) {
        Map<String, Object> map;
        Integer rows1 = 0;// 当前有几行
        Integer page1 = 1;// 当前有几页
        if (rows != null && !"".equals(rows)) {
            rows1 = Integer.parseInt(rows);
        }
        if (page != null && !"".equals(page)) {
            page1 = Integer.parseInt(page);
        }
        User user = (User) session.getAttribute(SessionInfo.SessionName);
        map = csi.getSignReverseOrderInfo(user, (page1 - 1)
                * rows1, rows1,send_time, end_time, shiping_order_num, shipping_order_state, order_type, send_mechanism, end_address, end_mechanism);
        return map;
    }

    /**
     * 跳转逆向运单修改页面
     *
     * @param request
     * @param id
     * @return
     */
    @RequestMapping(params = "method=toEditReverseOrder")
    public String toEditReverseOrder(HttpServletRequest request, String id) {
        request.setAttribute("id", id);
        return "reverseOrder/editReverseOrder";
    }

    /**
     * 更新运单信息
     *
     * @param reverserOrderModel
     * @return
     */
    @RequestMapping(params = {"method=updateReverseOrder"})
    public
    @ResponseBody
    JSONObject updateReverseOrder(ReverserOrderModel reverserOrderModel) {
        JSONObject jsonObject = new JSONObject();
        boolean b = csi.updateReverserOrder(reverserOrderModel);
        if (b) csi.updateReverseOrderCityInfo(reverserOrderModel);
        jsonObject.put("success", b);
        return jsonObject;
    }

    /**
     * 删除逆向物流运单信息
     *
     * @param ids
     * @return
     */
    @RequestMapping(params = {"method=deleteReverseOrderInfo"})
    public
    @ResponseBody
    JSONObject deleteReverseOrderInfo(String[] ids) {
        JSONObject jsonObject = new JSONObject();
        List<String> list = new ArrayList<String>();
        if (ids.length > 0) {
            list = Arrays.asList(ids);
        }
        boolean delete = csi.deleteReverserOrderInfo(list);
        jsonObject.put("success", delete);
        return jsonObject;
    }

    /**
     * 去签收运单信息页面
     * @param id
     * @param request
     * @return
     */
    @RequestMapping(params = {"method=toEditSignReverseOrderInfo"})
    public String toEditSignReverseOrderInfo(String id,HttpServletRequest request){
        request.setAttribute("id",id);
        return "reverseOrder/editSignReverseOrder";
    }


    /**
     * 签收逆向运单信息
     * @param sign_order
     * @param session
     * @return
     */
    @RequestMapping(params = {"method=saveSignReverseOrderInfo"})
    public
    @ResponseBody
    JSONObject saveSignReverseOrderInfo(Sign_order sign_order, HttpSession session) {

        JSONObject jsonObject = new JSONObject();
        sign_order.setSign_id(UUIDUtils.getUUID());
        User user = (User) session.getAttribute(SessionInfo.SessionName);
        boolean b = csi.checkReverseOrderInfoById(sign_order.getOrder_id());
        boolean bb;
        if (b) {
            bb= csi.saveSignReverserOrderInfo(sign_order);
        }else {
            bb = csi.updateSignReverseOrderInfo(sign_order);
        }
        if (bb){
            boolean state = csi.updateReverseOrderState(sign_order.getOrder_id(), "4");
            String filenames = (String) session.getAttribute("filename");
            if (filenames != null) {
                String[] file = filenames.split(",");
                ShipperOrderImg shipperOrderImg;
                List<ShipperOrderImg> shipperOrderImgs = new ArrayList<ShipperOrderImg>();
                for (int j = 0; j < file.length; j++) {
                    shipperOrderImg = new ShipperOrderImg();
                    shipperOrderImg.setOrder_id(sign_order.getOrder_id());
                    shipperOrderImg.setImage_id(UUIDUtils.getUUID());
                    shipperOrderImg.setImageUrl(file[j]);
                    shipperOrderImgs.add(shipperOrderImg);
                }
                csi.saveSignReverseOrderImage(shipperOrderImgs);
            }
            saveReverseHistory(sign_order.getOrder_id(),sign_order.getOrder_number(),user.getRealName()+"项目部已签收,签收人:"+user.getUsername());
            jsonObject.put("success", state);
        }
        return jsonObject;
    }

    /**
     * 通过id获取签收信息和逆向运单信息
     * @param id
     * @return
     */
    @RequestMapping(params = {"method=getSignReverseOrderById"})
    public @ResponseBody JSONObject getSignReverseOrderById(String id){
        JSONObject jsonObject = new JSONObject();
        ReverserOrderModel reverserOrderModel = csi.getSignReverseOrderById(id);
        jsonObject.put("success",reverserOrderModel);
        return jsonObject;
    }

    /**
     * 运单签收查看图片信息
     *
     * @param id
     * @param request
     * @return
     */
    @RequestMapping(params = "method=getSingReverseOrderImg")
    public String getSingReverseOrderImg(String id, HttpServletRequest request) {
        List<ShipperOrderImg> listOrderImg = csi.getSingReverseOrderImg(id);
        if (listOrderImg.size() == 0) {
            request.setAttribute("iurl", "");
        }
        request.setAttribute("iurl", listOrderImg);

        return "reverseOrder/orderImg";
    }

    /**
     * 查询逆向物流信息
     * @param pid
     * @param shiping_order_num
     * @param request
     * @return
     */
    @RequestMapping(params = "method=getReverseOrderPage")
    public String getReverseOrderPage(String pid, String shiping_order_num,
                                   HttpServletRequest request) {
        ReverserOrderModel d = csi.getSignReverseOrderById(pid);
        List<ShipperOrderImg> listOrderImg = csi.getSingReverseOrderImg(pid);
        if (listOrderImg.size() == 0) {
            request.setAttribute("iurl", "");
        }
        List<ShipperOrderImg> takingOrderImg = csi.getTakingReverseOrderImg(pid);
        request.setAttribute("pid", listOrderImg);
        request.setAttribute("sp", d);
        request.setAttribute("takingOrderImg",takingOrderImg);
        request.setAttribute("numb", shiping_order_num);
        return "reverseOrder/shipOrderMsg";
    }

    /**
     * 获取运单历史信息
     * @param id
     * @return
     */
    @RequestMapping(params = {"method=getReverseOrderHistoryInfo"})
    public @ResponseBody List<OrderHistory> getReverseOrderHistoryInfo(String id) {
        return csi.getReverseOrderHistory(id);
    }


    /**
     * 逆向物流信息导出
     * @param dataIds
     * @param headtitle
     * @param fieldName
     * @param response
     * @param session
     * @param total
     * @param send_time
     * @param end_time
     * @param shiping_order_num
     * @param end_address
     * @param end_mechanism
     * @param custom_contact
     * @param order_type
     * @param receipt_contact
     * @param send_mechanism
     * @return
     */
    @RequestMapping(params = "method=exportReverseOrder")
    public @ResponseBody
    String exportReverseOrder(String[] dataIds, String[] headtitle,
                            String[] fieldName, HttpServletResponse response,
                            HttpSession session,String total, String send_time, String end_time, String shiping_order_num, String end_address,
                               String end_mechanism, String custom_contact, String order_type, String receipt_contact, String send_mechanism) {
        String filename="逆向物流导出运单信息";
        List<ReverserOrderModel> list;
        User user = (User) session.getAttribute(SessionInfo.SessionName);
        try {
            if(dataIds!=null&&dataIds.length>0){
                list= csi.getReverseOrderByIds(Arrays.asList(dataIds));
            }else{
                Map<String, Object> map = csi.getReverseOrderInfo(user, 0,
                        Integer.parseInt(total), send_time, end_time, shiping_order_num, end_address, end_mechanism, custom_contact, order_type, receipt_contact, send_mechanism);
                list = (List<ReverserOrderModel>) map.get("rows");
            }

            // 声明一个工作薄
            HSSFWorkbook workbook = new HSSFWorkbook();
            // 生成一个表格
            response.setContentType("application/vnd.ms-excel;");
            response.setHeader("Content-Disposition", "attachment;filename=\""
                    + new String(filename.getBytes("gbk"), "iso-8859-1")
                    + ".xls\"");
            OutputStream ouputStream = response.getOutputStream();
            // 调用工具类创建表头
            ExportUtils.outputHeaders(headtitle, workbook, "逆向物流运单信息导出");
            // 调用工具类生成内容
            ExportUtils.outputColums(fieldName, list, workbook, 1, "逆向物流运单信息导出");
            workbook.write(ouputStream);
            ouputStream.flush();
            ouputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 逆向物流签收信息
     * @param dataIds
     * @param headtitle
     * @param fieldName
     * @param response
     * @param session
     * @param total
     * @param rows
     * @param page
     * @param send_time
     * @param end_time
     * @param shiping_order_num
     * @param shipping_order_state
     * @param order_type
     * @param send_mechanism
     * @param end_address
     * @param end_mechanism
     * @return
     */
    @RequestMapping(params = "method=exportSignReverseOrder")
    public @ResponseBody
    String exportSignReverseOrder(String[] dataIds, String[] headtitle,
                              String[] fieldName, HttpServletResponse response,
                              HttpSession session,String total,  String rows, String page,String send_time,String end_time,String shiping_order_num,String shipping_order_state,String order_type,String send_mechanism,String end_address,String end_mechanism) {
        String filename="逆向物流签收运单信息";
        List<ReverserOrderModel> list;
        User user = (User) session.getAttribute(SessionInfo.SessionName);
        try {
            if(dataIds!=null&&dataIds.length>0){
                list= csi.getSignReverseOrderByIds(Arrays.asList(dataIds));
            }else{
                Map<String, Object> map = csi.getSignReverseOrderInfo(user,   0,Integer.parseInt(total),
                               send_time, end_time, shiping_order_num, shipping_order_state, order_type, send_mechanism, end_address, end_mechanism);
                list = (List<ReverserOrderModel>) map.get("rows");
            }

            // 声明一个工作薄
            HSSFWorkbook workbook = new HSSFWorkbook();
            // 生成一个表格
            response.setContentType("application/vnd.ms-excel;");
            response.setHeader("Content-Disposition", "attachment;filename=\""
                    + new String(filename.getBytes("gbk"), "iso-8859-1")
                    + ".xls\"");
            OutputStream ouputStream = response.getOutputStream();
            // 调用工具类创建表头
            ExportUtils.outputHeaders(headtitle, workbook, "逆向物流签收运单信息");
            // 调用工具类生成内容
            ExportUtils.outputColums(fieldName, list, workbook, 1, "逆向物流签收运单信息");
            workbook.write(ouputStream);
            ouputStream.flush();
            ouputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 跳转逆向物流运单导入信息
     * @return
     */
    @RequestMapping(params = "method=toImportReverserOrderInfo")
    public String toImportReverserOrderInfo(){
        return "reverseOrder/maplinimp";
    }

    /**
     * 逆向物流导入开始之前
     * @param request
     * @param files
     * @return
     * @throws IllegalStateException
     * @throws IOException
     */
    @RequestMapping(params = "method=startImportReverser")
    public @ResponseBody
    JSONObject startImportReverser(HttpServletRequest request,
                               @RequestParam(value = "files") MultipartFile files)
            throws IllegalStateException, IOException {
        String path = request.getSession().getServletContext().getRealPath(filePath);// 文件路径
        File filez = new File(path);
        if (!filez.exists()) {
            filez.mkdirs();
        }
        MultipartFile myfile = files;
        String filename = myfile.getOriginalFilename();
        String prefix = filename.substring(filename.lastIndexOf("."));
        @SuppressWarnings("static-access")
        File newfile = filez.createTempFile("reverser", prefix, filez);
        String filesname = newfile.toString();
        filesname = filesname.substring(filesname.lastIndexOf("\\") + 1,
                filesname.length());
        // request.setAttribute("filesname", filesname);
        myfile.transferTo(newfile);
        JSONObject json = new JSONObject();
        json.put("filesName", filesname);
        return json;
    }

    @RequestMapping(params = "method=saveImportReverserInfo")
    public @ResponseBody
    String saveImportReverserInfo(HttpServletRequest request, String filename,String pid) {
        String realPath = request.getSession().getServletContext()
                .getRealPath(filePath + filename);
        ReverseOrderImport reverseOrderImport= new ReverseOrderImport();
        JSONObject jsonObject = new JSONObject();
        User user = (User) request.getSession().getAttribute(
                SessionInfo.SessionName);
        try {
            jsonObject = reverseOrderImport.importReverseOrderInfo(realPath,user);
            //deleteFile(filepath); // 执行上传文件删除操作

        } catch (Exception e) {
            e.printStackTrace();
            return jsonObject.toString();


        }
        return jsonObject.toString();
    }
}
