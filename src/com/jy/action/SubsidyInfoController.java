package com.jy.action;

import com.jy.common.ExportUtils;
import com.jy.common.OrderExcelForPOI;
import com.jy.common.SessionInfo;
import com.jy.common.UUIDUtils;
import com.jy.excel.bean.SubsidyInfoImport;
import com.jy.model.*;
import com.jy.service.SubsidyInfoService;
import net.sf.json.JSONObject;
import org.apache.http.protocol.HTTP;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 资源补助信息
 */
@Controller
@RequestMapping(value = {"/subsidyInfo.do"})
public class SubsidyInfoController {
    String filePath="WebRoot/subsidyInfoFiles/";
    @Resource
    private SubsidyInfoService subsidyInfoService;

    /**
     * 跳转页面信息
     *
     * @return
     */
    @RequestMapping(params = {"method=toSubsidyInfo"})
    public String toSubsidyInfo() {

        return "subsidy/subsidyInfo";
    }

    /**
     * 查询对
     *
     * @param session
     * @param rows
     * @param page
     * @param shiping_order_num
     * @param shiping_order_goid
     * @param send_mechanism
     * @param end_address
     * @param receipt_name
     * @param end_mechanism
     * @param result
     * @param state
     * @return
     */
    @RequestMapping(params = {"method=getSubsidyMapInfo"})
    public
    @ResponseBody
    Map<String, Object> getSubsidyMapInfo(HttpSession session, String rows, String page, String start_time, String end_time, String shiping_order_num, String shiping_order_goid, String send_mechanism, String end_address, String receipt_name, String end_mechanism, String result, String state) {
        Integer rows1 = 0;// 当前有几行
        Integer page1 = 1;// 当前有几页
        if (rows != null && !"".equals(rows)) {
            rows1 = Integer.parseInt(rows);
        }
        if (page != null && !"".equals(page)) {
            page1 = Integer.parseInt(page);
        }
        User user = (User) session.getAttribute(SessionInfo.SessionName);

        return subsidyInfoService.getSubsidyInfoMap(user, (page1 - 1)
                * rows1, rows1, start_time,end_time,shiping_order_num, shiping_order_goid, send_mechanism, end_address, receipt_name, end_mechanism, result, state);
    }

    /**
     * 跳转添加页面
     *
     * @return
     */
    @RequestMapping(params = {"method=toAddSubsidyOrder"})
    public String toAddSubsidyOrder() {
        return "subsidy/addSubsidyOrder";
    }

    /**
     * 添加跳转页面
     *
     * @param request
     * @param shiping_order_id
     * @return
     */
    @RequestMapping(params = {"method=toAddSubsidyInfo"})
    public String toAddSubsidyInfo(HttpServletRequest request, String shiping_order_id) {
        request.setAttribute("shiping_order_id", shiping_order_id);
        return "subsidy/addSubsidyInfo";
    }

    /**
     * 验证信息是否存在
     *
     * @param shiping_order_num
     * @param shiping_order_goid
     * @return
     */
    @RequestMapping(params = {"method=validateSubsidyInfo"})
    public
    @ResponseBody
    JSONObject validateSubsidyInfo(String shiping_order_num, String shiping_order_goid) {

        List<SubsidyInfoModel> list = subsidyInfoService.getSubsidyInfoModel(shiping_order_num, shiping_order_goid);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("success", list.size() == 0);
        return jsonObject;
    }

    /**
     * 添加信息
     *
     * @param subsidyInfoModel
     * @return
     */
    @RequestMapping(params = {"method=saveSubsidyInfo"})
    public
    @ResponseBody
    JSONObject saveSubsidyInfo(SubsidyInfoModel subsidyInfoModel, HttpSession session) {
        User user = (User) session.getAttribute(SessionInfo.SessionName);

        JSONObject jsonObject = new JSONObject();
        subsidyInfoModel.setId(UUIDUtils.getUUID());
        List<SubsidyHistoryModel> list = new ArrayList<SubsidyHistoryModel>();
        boolean b = subsidyInfoService.addSubsidyInfo(subsidyInfoModel);
        if (b) {
            SubsidyHistoryModel subsidyHistoryModel = new SubsidyHistoryModel();
            subsidyHistoryModel.setId(UUIDUtils.getUUID());
            subsidyHistoryModel.setMessage("补助信息添加");
            subsidyHistoryModel.setSubsidy_id(subsidyInfoModel.getId());
            subsidyHistoryModel.setUser_real_name(user.getRealName());
            subsidyHistoryModel.setType(Integer.parseInt(user.getFlag()));
            subsidyHistoryModel.setUser_name(user.getUsername());
            subsidyHistoryModel.setShiping_order_num(subsidyInfoModel.getShiping_order_num());
            list.add(subsidyHistoryModel);
            subsidyInfoService.saveSubsidyHistory(list);
        }
        jsonObject.put("success", b);
        return jsonObject;
    }


    /**
     * 跳转
     *
     * @return
     */
    @RequestMapping(value = {"method=toUpdateSubsidyInfo"})
    public String toUpdateSubsidyInfo(String id, HttpServletRequest request) {
        request.setAttribute("id", id);
        return "subsidy/editSubsidyInfo";
    }

    /**
     * 根据id获取补助信息
     *
     * @param id
     * @return
     */
    @RequestMapping(params = {"method=getSubsidyInfoModelById"})
    public
    @ResponseBody
    JSONObject getSubsidyInfoModelById(String id) {

        SubsidyInfoModel model = subsidyInfoService.getSubsidyInfoModelById(id);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data", model);
        return jsonObject;
    }

    /**
     * 更新补助信息
     *
     * @param subsidyInfoModel
     * @param session
     * @return
     */
    @RequestMapping(params = {"method=updateSubsidyInfoModel"})
    public
    @ResponseBody
    JSONObject updateSubsidyInfoModel(SubsidyInfoModel subsidyInfoModel, HttpSession session) {
        JSONObject json = new JSONObject();
        boolean b = subsidyInfoService.updateSubsidyInfo(subsidyInfoModel);
        json.put("success", b);
        return json;
    }

    /**
     * 删除补助信息
     *
     * @param id
     * @return
     */
    @RequestMapping(params = {"method=deleteSubsidyInfoModel"})
    public
    @ResponseBody
    JSONObject deleteSubsidyInfoModel(String[] id) {
        JSONObject json = new JSONObject();
        List<String> list = Arrays.asList(id);
        boolean b = subsidyInfoService.deleteSubsidyInfo(list);
        json.put("success", b);
        return json;
    }


    /**
     * 调整审核页面
     *
     * @param id
     * @param request
     * @return
     */
    @RequestMapping(params = {"method=toAuditSubsidyInfo"})
    public String toAuditSubsidyInfo(String id, HttpServletRequest request) {
        request.setAttribute("id", id);
        return "subsidy/auditSubsidyInfo";
    }

    /**
     * 审核
     * @param subsidyInfoModel
     * @param session
     * @return
     */
    @RequestMapping(params = {"method=auditSubsidyInfo"})
    public
    @ResponseBody
    JSONObject auditSubsidyInfo(SubsidyInfoModel subsidyInfoModel, HttpSession session) {
        User user = (User) session.getAttribute(SessionInfo.SessionName);
        JSONObject jsonObject = new JSONObject();
        List<SubsidyHistoryModel> list = new ArrayList<SubsidyHistoryModel>();
        String message = "";
        String flag = user.getFlag();
        if (flag.equals("4") || flag.equals("3")) {
            subsidyInfoModel.setState(1);
            message = "项目审核完毕";
        } else if (flag.equals("0")) {
            String whether_through = subsidyInfoModel.getWhether_through();
            subsidyInfoModel.setState(whether_through==null||whether_through.equals("0") ? 2 : 3);
            message = "终端配送审核完毕";
        }
        boolean b = subsidyInfoService.updateSubsidyInfo(subsidyInfoModel);
        if (b) {
            SubsidyHistoryModel historyModel = new SubsidyHistoryModel();
            historyModel.setId(UUIDUtils.getUUID());
            historyModel.setShiping_order_num(subsidyInfoModel.getShiping_order_num());
            historyModel.setSubsidy_id(subsidyInfoModel.getId());
            historyModel.setType(Integer.parseInt(user.getFlag()));
            historyModel.setUser_name(user.getUsername());
            historyModel.setUser_real_name(user.getRealName());
            historyModel.setMessage(message);
            list.add(historyModel);
            subsidyInfoService.saveSubsidyHistory(list);
        }
        jsonObject.put("success", b);
        return jsonObject;
    }

    @RequestMapping(params = {"method=toImportSubsidyInfo"})
    public String toImportSubsidyInfo(){
        return "subsidy/importSubsidyInfo";
    }

    /**
     * 做导入前的上传处理
     *
     * @param request
     * @param files
     * @return
     * @throws IllegalStateException
     * @throws IOException
     */
    @RequestMapping(params = "method=startImportSubsidy")
    public @ResponseBody
    JSONObject startimplinplan(HttpServletRequest request,
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
        File newfile = filez.createTempFile("subsidy", prefix, filez);
        String filesname = newfile.toString();
        filesname = filesname.substring(filesname.lastIndexOf("\\") + 1,
                filesname.length());
        // request.setAttribute("filesname", filesname);
        myfile.transferTo(newfile);
        JSONObject json = new JSONObject();
        json.put("filesName", filesname);
        return json;
    }


    @RequestMapping(params = "method=saveImportSubsidyInfo")
    public @ResponseBody
    String saveImportSubsidyInfo(HttpServletRequest request, String filename,String type) {
        String realPath = request.getSession().getServletContext()
                .getRealPath(filePath + filename);
        SubsidyInfoImport subsidyInfoImport = new SubsidyInfoImport();
        JSONObject jsonObject = new JSONObject();
        User user = (User) request.getSession().getAttribute(
                SessionInfo.SessionName);
        User newUser = new User();
        newUser.setFlag(user.getFlag());
        newUser.setId(user.getId());
        newUser.setUsername(user.getUsername());
        newUser.setDid(user.getDid());
        newUser.setRealName(user.getRealName());
        newUser.setCustomer_id(user.getCustomer_id());
        newUser.setDriver_id(user.getDriver_id());
        newUser.setSuppliers_id(user.getSuppliers_id());
        newUser.setUser_address(user.getUser_address());
        if (type.equals("1")) newUser.setFlag("1");
        try {
            jsonObject = subsidyInfoImport.getSubsidyInfoData(newUser,realPath);
            //deleteFile(filepath); // 执行上传文件删除操作
        } catch (Exception e) {
            e.printStackTrace();


        }
        return jsonObject.toString();
    }

    /**
     * 导出
     * @param session
     * @param response
     * @param dataIds
     * @param headtitle
     * @param fieldName
     * @param start_time
     * @param end_time
     * @param shiping_order_num
     * @param shiping_order_goid
     * @param send_mechanism
     * @param end_address
     * @param receipt_name
     * @param end_mechanism
     * @param result
     * @param state
     * @return
     */
    @RequestMapping(params = {"method=outPutSubsidyInfo"})
    public String outPutSubsidyInfo(HttpSession session, HttpServletResponse response, String[] dataIds, String[] headtitle, String[] fieldName, String start_time, String end_time, String shiping_order_num, String shiping_order_goid, String send_mechanism, String end_address, String receipt_name, String end_mechanism, String result, String state) {
        User user = (User) session.getAttribute(
                SessionInfo.SessionName);
        List<SubsidyInfoModel> list = subsidyInfoService.outPutSubsidyInfoModel(user,dataIds,start_time, end_time, shiping_order_num, shiping_order_goid, send_mechanism, end_address, receipt_name, end_mechanism, result, state);
        // ExportExcel<Agreement> esx= new ExportExcel<Agreement>();
        String filename = "补助信息导出";
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
            ExportUtils.outputHeaders(headtitle, workbook, filename);
            // 调用工具类生成内容
            ExportUtils.outputColums(fieldName, list, workbook, 1, "运单信息导出");
            workbook.write(ouputStream);
            ouputStream.flush();
            ouputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取补助申请异常信息
     * @param session
     * @param rows
     * @param page
     * @param startTime
     * @param endTime
     * @param shiping_order_num
     * @param shipperorder_id
     * @param send_mechanism
     * @param end_address
     * @return
     */
    @RequestMapping(params = {"method=getAddSubsidyInfo"})
    public @ResponseBody Map<String, Object> getAddSubsidyInfo(HttpSession session,String rows, String page, String send_time, String end_time, String shiping_order_num, String shipperorder_id, String send_mechanism, String end_address,String order_type) {
        User user = (User) session.getAttribute(
                SessionInfo.SessionName);
        Integer rows1 = 0;// 当前有几行
        Integer page1 = 1;// 当前有几页
        if (rows != null && !"".equals(rows)) {
            rows1 = Integer.parseInt(rows);
        }
        if (page != null && !"".equals(page)) {
            page1 = Integer.parseInt(page);
        }
        Map<String,Object> map=subsidyInfoService.getAddSubsidyInfo((page1 - 1)
                * rows1, rows1, user, send_time, end_time, shiping_order_num, shipperorder_id, send_mechanism, end_address,order_type);

        return map;
    }
    /**
     * 通过运单id查询运单信息
     * @param orderId
     * @return
     */
    @RequestMapping(params = "method=getShippingOrderMessage")
    public @ResponseBody JSONObject getShippingOrderMessage(String orderId){
        JSONObject jsonObject = new JSONObject();
        ShippingOrder order = subsidyInfoService.getShippingOrderMessage(orderId);
        jsonObject.put("order",order);
        return jsonObject;
    }

    @RequestMapping(params = "method=cancelAudit")
    public @ResponseBody JSONObject cancelAudit(String[] id,HttpSession session){
        JSONObject json = new JSONObject();
        List<String> list = Arrays.asList(id);
        User user = (User) session.getAttribute(
                SessionInfo.SessionName);
        int b = subsidyInfoService.cancelAudit(list,user);
        json.put("success", b>0);
        return json;
    }


}
