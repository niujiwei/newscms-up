package com.jy.action;

import Decoder.BASE64Decoder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jy.common.*;
import com.jy.dao.RemarkMapDao;
import com.jy.model.*;
import com.jy.service.*;
import com.jy.service.impl.ReverseOrderInfoServiceImpl;
import com.jy.thread.FenPeiSaveHistoryThread;
import common.Logger;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 2015年5月13日 11:30:48 Controller
 *
 * @author zkj
 */
@Controller
@RequestMapping(value = "/app.do")
public class AppController {
    private Logger log = Logger.getLogger(AppController.class);
    @Resource
    private MapService mapService;
    /*
     * @Resource private Demand_releaseService demand_releaseService;
	 */
    @Resource
    private LoginUserService loginUserService;
    @Resource
    private RemarkMapService rms;
    @Resource
    private UserInfoService uis;
    @Resource
    private SuppliersService suppliersService;
    @Resource
    private ShippingOrderInfoService csi;
    @Resource
    private DriverInfoService dsi;
    @Resource
    private OrderInfoService ordersercice;
    @Resource
    private AppService app_sercice;
    @Resource
    private AbnormalReportService ar_service;

    @Resource
    private ReverseOrderInfoService ros;
    @Resource
    private MessageManage manage;

    @Resource
    private RemarkMapDao rmdao;
    @Resource
    private EquipmentManService equipmentManService;
    @Resource
    private WaybillAdjustmentService waybillAdjustmentService;

    // **************************************************************************************************************//
    // 56lineked推送签收信息
    void testPost(String urlStr, String ordernum, String bz, String singtime,
                  String singname, String agtime) {
        try {
            URL url = new URL(urlStr);
            URLConnection con = url.openConnection();
            con.setDoOutput(true);
            con.setRequestProperty("Pragma:", "no-cache");
            con.setRequestProperty("Cache-Control", "no-cache");
            con.setRequestProperty("Content-Type", "text/xml");
            con.setDoOutput(true);
            con.setDoInput(true);
            OutputStreamWriter out = new OutputStreamWriter(
                    con.getOutputStream(), "utf-8");
            String xmlInfo = getXmlInfo(ordernum, bz, singtime, singname,
                    agtime);
            System.out.println("urlStr=" + urlStr);
            System.out.println("xmlInfo=" + xmlInfo);
            out.write(xmlInfo);
            out.flush();
            out.close();
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String line = "";
            for (line = br.readLine(); line != null; line = br.readLine()) {
                System.out.println(new String(line.getBytes("gbk"), "UTF-8"));
            }
        } catch (MalformedURLException e) {
            log.error("AppController:" + e.getCause().getClass() + ","
                    + e.getCause().getMessage());
        } catch (IOException e) {
            log.error("AppController:" + e.getCause().getClass() + ","
                    + e.getCause().getMessage());
        }
    }

    private String getXmlInfo(String ordernum, String bz, String singtime,
                              String singname, String agtime) {
        String ddate = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar
                .getInstance().getTime());
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version='1.0' encoding='UTF-8'?>");
        sb.append("<WLLINKED>");
        sb.append("	<HEAD>");
        sb.append("    <TYPE>ORDER_SIGN_SERVICE</TYPE>");
        sb.append("    <SENDER>JY</SENDER>");
        sb.append("    <CODE>61457</CODE>");
        sb.append("    <DATE>" + ddate + "</DATE>");
        sb.append("    <BUSICODE>" + UUIDUtils.getUUID() + "</BUSICODE>");
        sb.append("	</HEAD>");
        sb.append("<XML_DATA>");
        sb.append("		<CECNO>承运商单号（56系统单号）</CECNO>");
        sb.append(" 	<ECNO>" + ddate + "</ECNO>");
        sb.append(" 	<LEGNO>" + ordernum + "</LEGNO>");
        sb.append(" 	<VEHICLENO>1</VEHICLENO>");
        sb.append("  	<STATUS>签收</STATUS>"); // 状态填写签收
        sb.append("  	<DAMAGE_QUANTITY>0</DAMAGE_QUANTITY>");
        sb.append(" 	<POOR_QUANTITY>0</POOR_QUANTITY>");
        sb.append(" 	<REMARK>" + bz + "</REMARK>");
        sb.append("		<PLANARRIVETIME>" + agtime + "</PLANARRIVETIME>"); // 接单时间
        sb.append("		<ARRIVERDTIME>" + singtime + "</ARRIVERDTIME>");
        sb.append("		<SIGNPERSON>" + singname + "</SIGNPERSON>");
        sb.append("		<IMG_URL> </IMG_URL>");
        sb.append("		<SIGNTIME>" + singtime + "</SIGNTIME>");
        sb.append("	</XML_DATA>");
        sb.append("</WLLINKED>");

        return sb.toString();
    }

    /**
     * APP 用户登录
     *
     * @param json
     * @param session
     * @return
     */
    @RequestMapping(params = "method=appLogin")
    public
    @ResponseBody
    String appLogin(String json, HttpSession session) {
        JSONObject tojson = JSONObject.fromObject(json);
        JSONObject returnjson = new JSONObject();
        User user = new User();
        user.setUsername(tojson.getString("userName"));
        user.setPassword(tojson.getString("pwd"));
        String type = tojson.containsKey("type") ? tojson.getString("type") : "";
        boolean flag = loginUserService.checkUser(user);
        if (flag == true) {
            User userinfo = app_sercice.appGetUserInfo(user);
            List<String> userAppRole;
            if ("".equals(type)) {
                userAppRole = app_sercice.getAppMenuRole(userinfo);
            } else {
                userAppRole = app_sercice.appGetMenuAndRole(userinfo, "0");
            }
            userinfo.setUserAppRole(userAppRole);
            returnjson.put("successMsg", userinfo);
            return returnjson.toString();
        }
        returnjson.put("errorMsg", "用户名密码错误");
        return returnjson.toString();
    }


    /**
     * app获取正逆向物流权限
     *
     * @param json
     * @return
     */
    @RequestMapping(params = "method=appGetMenuAndRole")
    public
    @ResponseBody
    String appGetMenuAndRole(String json) {
        JSONObject tojson = JSONObject.fromObject(json);
        JSONObject returnjson = new JSONObject();
        Gson gson = new Gson();
        String userInfo = tojson.getString("userInfo");// 获取json用户信息
        String type = tojson.getString("type");//1正向 2逆向
        User user = gson.fromJson(userInfo, User.class);
        String fatherId = type.equals("1") ? "11" : "4";//fatherid
        List<String> userAppRole = app_sercice.appGetMenuAndRole(user, fatherId);
        returnjson.put("successMsg", userAppRole);
        return returnjson.toString();
    }

    /**
     * APP 查询部门信息
     *
     * @param json
     * @return
     */
    @RequestMapping(params = "method=appGetDepartmentInfo")
    public
    @ResponseBody
    String appGetDepartmentInfo(String json) {
        Gson gson1 = new Gson();
        JSONObject gson = JSONObject.fromObject(json);
        JSONObject returnjson = new JSONObject();
        String departmentName = gson.getString("departmentName");// 输入的部门名称
        String userInfo = gson.getString("userInfo");// 获取json用户信息
        User user = gson1.fromJson(userInfo, User.class);

        String departmentId = user.getDid();// 部门id

        try {
            List<Department> departments = app_sercice.appGetDepartmentInfo(
                    departmentId, departmentName);
            returnjson.put("successMsg", departments);
        } catch (Exception e) {
            returnjson.put("errorMsg", "出错了");
            log.error("AppController:" + e.getCause().getClass() + ","
                    + e.getCause().getMessage());
        }
        return returnjson.toString();
    }

    /**
     * APP 查询司机信息
     *
     * @param json
     * @return
     */
    @RequestMapping(params = "method=appGetDriverInfo")
    public
    @ResponseBody
    String appGetDriverInfo(String json) {
        Gson gson = new Gson();
        JSONObject getjson = JSONObject.fromObject(json);
        JSONObject returnjson = new JSONObject();
        String userInfo = getjson.getString("userInfo");// 获取json用户信息
        String driverName = getjson.getString("driver_name");// 司机姓名
        String type = getjson.getString("type");// 1添加用户，，其他为查询全部
        User user = gson.fromJson(userInfo, User.class);
        try {
            List<Driver> list = app_sercice.appGetDriverInfo(user, driverName,
                    type);
            returnjson.put("successMsg", list);
        } catch (Exception e) {
            returnjson.put("errorMsg", "出错了");
            log.error("AppController:" + e.getCause().getClass() + ","
                    + e.getCause().getMessage());
        }
        return returnjson.toString();
    }

    /**
     * APP 查询供应商商信息
     *
     * @param json
     * @return
     */
    @RequestMapping(params = "method=appGetSupplersInfo")
    public
    @ResponseBody
    String appGetSupplersInfo(String json) {
        Gson gson = new Gson();
        JSONObject getjson = JSONObject.fromObject(json);
        JSONObject returnjson = new JSONObject();
        String userInfo = getjson.getString("userInfo");// 获取json用户信息
        String driverName = getjson.getString("supplersName");// 供应商姓名
        String type = getjson.getString("type");// 1添加用户，，其他为查询全部
        User user = gson.fromJson(userInfo, User.class);
        try {
            List<JySuppliers> list = app_sercice.appGetSupplersInfo(user,
                    driverName, type);
            returnjson.put("successMsg", list);
        } catch (Exception e) {
            returnjson.put("errorMsg", "出错了");
            log.error("AppController:" + e.getCause().getClass() + ","
                    + e.getCause().getMessage());
        }
        return returnjson.toString();
    }

    /**
     * APP 查询客户信息
     *
     * @param json
     * @return
     */
    @RequestMapping(params = "method=appGetCustomersInfo")
    public
    @ResponseBody
    String appGetCustomersInfo(String json) {
        Gson gson = new Gson();
        JSONObject getjson = JSONObject.fromObject(json);
        JSONObject returnjson = new JSONObject();
        String userInfo = getjson.getString("userInfo");// 获取json用户信息
        String customersName = getjson.getString("customersName");// 供应商姓名
        User user = gson.fromJson(userInfo, User.class);
        try {
            List<Customer> list = app_sercice.appGetCustomersInfo(user,
                    customersName);
            returnjson.put("successMsg", list);
        } catch (Exception e) {
            returnjson.put("errorMsg", "出错了");
            log.error("AppController:" + e.getCause().getClass() + ","
                    + e.getCause().getMessage());
        }
        return returnjson.toString();
    }

    /**
     * APP 添加用户信息
     *
     * @param json
     * @return
     */
    @RequestMapping(params = "method=appSaveUserInfo")
    public
    @ResponseBody
    String appSaveUserInfo(String json) {
        Gson gson = new Gson();
        JSONObject getjson = JSONObject.fromObject(json);
        JSONObject returnjson = new JSONObject();
        String userInfo = getjson.getString("userInfo");// 获取json用户信息
        User user = gson.fromJson(userInfo, User.class);
        if (user != null) {
            try {
                boolean b = app_sercice.appCheckUser(user.getUsername());// 检查用户名是否存在
                if (!b) {
                    user.setPassword(MD5.toMD5(user.getPassword()));
                    String flag = user.getFlag();
                    if ("1".equals(flag)) {
                        user.setDriver_id(user.getDriver_id());
                    } else if ("2".equals(flag)) {
                        user.setSuppliers_id(user.getSuppliers_id());
                        user.setDriver_id(user.getDriver_id());
                    } else if ("3".equals(flag) || "4".equals(flag)) {
                        user.setCustomer_id(user.getCustomer_id());
                    }
                    user.setState("1");
                    user.setId(UUIDUtils.getUUID());
                    boolean saveb = app_sercice.appSaveUserInfo(user);
                    if (saveb) {
                        if (flag != null && !"".equals(flag)) {
                            int i = Integer.parseInt(flag);
                            uis.saveRole(user.getId(), i);// 保存用户角色权限信息
                        }
                        returnjson.put("successMsg", "用户信息添加成功");
                    } else {
                        returnjson.put("errorMsg", "用户信息添加失败");
                    }
                }

            } catch (Exception e) {
                returnjson.put("errorMsg", "出错了");
                log.error("AppController:" + e.getCause().getClass() + ","
                        + e.getCause().getMessage());
            }
        }

        return returnjson.toString();
    }

    /**
     * APP 用信息修改
     *
     * @param json
     * @return
     */
    @RequestMapping(params = "method=appUpdateUserInfo")
    public
    @ResponseBody
    String appUpdateUserInfo(String json) {
        Gson gson = new Gson();
        JSONObject getjson = JSONObject.fromObject(json);
        JSONObject returnjson = new JSONObject();
        String userInfo = getjson.getString("userInfo");// 获取json用户信息
        User user = gson.fromJson(userInfo, User.class);
        if (user != null) {
            try {
                String flag = user.getFlag();
                if ("1".equals(flag)) {
                    user.setDriver_id(user.getDriver_id());
                    user.setSuppliers_id("");
                    user.setCustomer_id("");
                } else if ("2".equals(flag)) {
                    user.setSuppliers_id(user.getSuppliers_id());
                    user.setDriver_id(user.getDriver_id());
                    user.setCustomer_id("");
                } else if ("3".equals(flag) || "4".equals(flag)) {
                    user.setCustomer_id(user.getCustomer_id());
                    user.setDriver_id("");
                    user.setSuppliers_id("");
                }
                user.setState("1");

                boolean updateb = app_sercice.appUpdataUserInfo(user);

                if (updateb) {
                    uis.updateUser_Role_Info(user.getId(), user.getFlag());
                    returnjson.put("successMsg", "用户信息修改成功");
                } else {
                    returnjson.put("errorMsg", "用户信息修改失败");
                }

            } catch (Exception e) {
                returnjson.put("errorMsg", "出错了");
                log.error("AppController:" + e.getCause().getClass() + ","
                        + e.getCause().getMessage());
            }
        }

        return returnjson.toString();
    }

    /**
     * APP 用户信息查询
     *
     * @param json
     * @return
     */
    @RequestMapping(params = "method=appSearchUserInfo")
    public
    @ResponseBody
    String appSearchUserInfo(String json) {
        Gson gson = new Gson();
        JSONObject getjson = JSONObject.fromObject(json);
        JSONObject returnjson = new JSONObject();
        String userInfo = getjson.getString("userInfo");// 获取json用户信息
        String row = getjson.getString("row");// 几行开始
        String page = getjson.getString("page");// 显示多少个
        String user_name = getjson.getString("user_name");// 用户名
        String user_realName = getjson.getString("user_realName");// 真实姓名
        String user_did = getjson.getString("user_did");// 部门
        String falg = getjson.getString("falg");// 类型司机供应商
        User user = gson.fromJson(userInfo, User.class);
        int page2 = 0;
        int rows1 = 0;
        if (user != null) {
            try {
                if (page != null && !"".equals(page)) {
                    page2 = Integer.parseInt(page);
                }
                if (row != null && !"".equals(row)) {
                    rows1 = Integer.parseInt(row);
                }
                List<User> userList = uis.getUserInfo(rows1, page2, user_name,
                        user_realName, user_did, falg, user);

                returnjson.put("successMsg", userList);

                // returnjson.put("errorMsg", "用户信息添加失败");

            } catch (Exception e) {
                returnjson.put("errorMsg", "出错了");
                log.error("AppController:" + e.getCause().getClass() + ","
                        + e.getCause().getMessage());
            }
        }
        return returnjson.toString();
    }

    /**
     * APP 异常上报查询异常信息
     *
     * @param json
     * @return
     */
    @RequestMapping(params = "method=appSearchAbnromalReportInfo")
    public
    @ResponseBody
    String appSearchAbnromalReportInfo(String json) {
        Gson gson = new Gson();
        JSONObject getjson = JSONObject.fromObject(json);
        JSONObject returnjson = new JSONObject();
        String userInfo = getjson.getString("userInfo");// 获取json用户信息
        String row = getjson.getString("row");// 几行开始
        String page = getjson.getString("page");// 显示多少个
        String send_time = getjson.getString("send_time");// 上报开始时间
        String end_time = getjson.getString("end_time");// 上报结束时间
        String shiping_order_num = getjson.getString("shiping_order_num");// 订单号
        String shiping_order_goid = getjson.getString("shiping_order_goid");// 出货订单号
        String abnormal_result = getjson.getString("abnormal_result");// 是否处理 0
        // 未处理 1
        // 已处理
        String abnormal_type = getjson.getString("abnormal_type");// 异常类型（客户，货物）
        String end_address = getjson.getString("end_address");// 终到位置
        String receipt_name = getjson.getString("receipt_name");// 到货联系人
        String send_mechanism = getjson.getString("send_mechanism");// 受理机构
        User user = gson.fromJson(userInfo, User.class);
        int page1 = 0;
        int row1 = 0;
        if (user != null) {
            try {
                if (page != null && !"".equals(page)) {
                    page1 = Integer.parseInt(page);
                }
                if (row != null && !"".equals(row)) {
                    row1 = Integer.parseInt(row);
                }
                List<AbnormalReport> list = ar_service.getAbnormalReport(row1,
                        page1, send_time, end_time, shiping_order_num,
                        shiping_order_goid, abnormal_result, abnormal_type,
                        end_address, receipt_name, send_mechanism, user);
                returnjson.put("successMsg", list);

                // returnjson.put("errorMsg", "用户信息添加失败");

            } catch (Exception e) {
                returnjson.put("errorMsg", "出错了");
                log.error("AppController:" + e.getCause().getClass() + ","
                        + e.getCause().getMessage());
            }
        }
        return returnjson.toString();
    }

    /**
     * APP 异常上报查询异常信息和數量
     *
     * @param json
     * @return
     */
    @RequestMapping(params = "method=appSearchAbnromalReportInfoAndCount")
    public
    @ResponseBody
    String appSearchAbnromalReportInfoAndCount(String json) {
        Gson gson = new Gson();
        JSONObject getjson = JSONObject.fromObject(json);
        JSONObject returnjson = new JSONObject();
        String userInfo = getjson.getString("userInfo");// 获取json用户信息
        String row = getjson.getString("row");// 几行开始
        String page = getjson.getString("page");// 显示多少个
        String send_time = getjson.getString("send_time");// 上报开始时间
        String end_time = getjson.getString("end_time");// 上报结束时间
        String shiping_order_num = getjson.getString("shiping_order_num");// 订单号
        String shiping_order_goid = getjson.getString("shiping_order_goid");// 出货订单号
        String abnormal_result = getjson.getString("abnormal_result");// 是否处理
        // 0未处理
        // 1 已处理
        String abnormal_type = getjson.getString("abnormal_type");// 异常类型（客户，货物）
        String end_address = getjson.getString("end_address");// 终到位置
        String receipt_name = getjson.getString("receipt_name");// 到货联系人
        String send_mechanism = getjson.getString("send_mechanism");// 受理机构
        User user = gson.fromJson(userInfo, User.class);
        int page1 = 0;
        int row1 = 0;
        String isAbnormal_result = "1";
        String noAbnormal_result = "0";
        int isAbnormalResultCount = 0;// 已处理
        int noAbnormalResultCount = 0;// 未处理
        List<AbnormalReport> list = new ArrayList<AbnormalReport>();
        if (user != null) {
            JSONObject retjson = new JSONObject();

            try {
                if (page != null && !"".equals(page)) {
                    page1 = Integer.parseInt(page);
                }
                if (row != null && !"".equals(row)) {
                    row1 = Integer.parseInt(row);
                }
                isAbnormalResultCount = ar_service.getAbnormalReportCount(
                        send_time, end_time, shiping_order_num,
                        shiping_order_goid, isAbnormal_result, abnormal_type,
                        end_address, receipt_name, send_mechanism, user);
                noAbnormalResultCount = ar_service.getAbnormalReportCount(
                        send_time, end_time, shiping_order_num,
                        shiping_order_goid, noAbnormal_result, abnormal_type,
                        end_address, receipt_name, send_mechanism, user);
                list = ar_service.getAbnormalReport(row1, page1, send_time,
                        end_time, shiping_order_num, shiping_order_goid,
                        abnormal_result, abnormal_type, end_address,
                        receipt_name, send_mechanism, user);
                retjson.put("isAbnormalResultCount", isAbnormalResultCount);
                retjson.put("noAbnormalResultCount", noAbnormalResultCount);
                retjson.put("list", list);
                returnjson.put("successMsg", retjson);

                // returnjson.put("errorMsg", "用户信息添加失败");

            } catch (Exception e) {
                returnjson.put("errorMsg", "出错了");
                log.error("AppController:" + e.getCause().getClass() + ","
                        + e.getCause().getMessage());
            }
        }
        return returnjson.toString();
    }

    /**
     * APP 异常上报查询运单信息
     *
     * @param json
     * @return
     */
    @RequestMapping(params = "method=appSearchAbnromalReportOrderInfo")
    public
    @ResponseBody
    String appSearchAbnromalReportOrderInfo(String json) {
        Gson gson = new Gson();
        JSONObject getjson = JSONObject.fromObject(json);
        JSONObject returnjson = new JSONObject();
        String userInfo = getjson.getString("userInfo");// 获取json用户信息
        String row = getjson.getString("row");// 几行开始
        String page = getjson.getString("page");// 显示多少个
        String send_time = getjson.getString("send_time");// 发运时间
        String shiping_order_num = getjson.getString("shiping_order_num");// 订单号
        String send_mechanism = getjson.getString("send_mechanism");// 受理机构
        String end_address = getjson.getString("end_address");// 终到位置
        String custom_name = getjson.getString("custom_name");// 发货客户姓名
        String receipt_name = getjson.getString("receipt_name");// 到货联系人
        String receipt_tel = getjson.getString("receipt_tel");// 到货联系人电话
        String shiping_order_goid = getjson.getString("shiping_order_goid");// 出货订单号
        String end_time = getjson.getString("end_time");// 发运结束时间
        String topordernumber = getjson.getString("topordernumber");//
        String downordernumber = getjson.getString("downordernumber");//
        User user = gson.fromJson(userInfo, User.class);
        int page1 = 0;
        int row1 = 0;
        if (user != null) {
            try {
                if (page != null && !"".equals(page)) {
                    page1 = Integer.parseInt(page);
                }
                if (row != null && !"".equals(row)) {
                    row1 = Integer.parseInt(row);
                }
                List<ShippingOrder> list = ar_service.getShipOrderInfo(row1,
                        page1, send_time, shiping_order_num, send_mechanism,
                        end_address, custom_name, receipt_name, receipt_tel,
                        topordernumber, downordernumber, shiping_order_goid,
                        end_time, user);
                returnjson.put("successMsg", list);

                // returnjson.put("errorMsg", "用户信息添加失败");

            } catch (Exception e) {
                returnjson.put("errorMsg", "出错了");
                log.error("AppController:" + e.getCause().getClass() + ","
                        + e.getCause().getMessage());
            }
        }
        return returnjson.toString();
    }

    /**
     * APP 异常上报添加
     *
     * @param json
     * @return
     */
    @RequestMapping(params = "method=appAddAbnromalReportOrder")
    public
    @ResponseBody
    String appAddAbnromalReportOrder(String json, HttpServletRequest request) {
        String fileRoot = "abnormalreport";
        Gson gson = new Gson();
        JSONObject getjson = JSONObject.fromObject(json);
        JSONObject returnjson = new JSONObject();
        String userInfo = getjson.getString("userInfo");// 获取json用户信息
        User user = gson.fromJson(userInfo, User.class);
        String abReport = getjson.getString("abnormalReport");
        AbnormalReport abnormalReport = gson.fromJson(abReport,
                AbnormalReport.class);
        if (user != null) {
            try {
                if (user.getFlag().equals("1")) {
                    String suppliers = ar_service.getSupplersId(user
                            .getDriver_id());
                    user.setSuppliers_id(suppliers);
                }
                abnormalReport.setAbnormal_supperid(user.getSuppliers_id());
                abnormalReport.setAbnormal_driverid(user.getDriver_id());
                abnormalReport.setId(UUIDUtils.getUUID());
                abnormalReport.setAbnormal_userid(user.getId());
                boolean is = app_sercice.getIsAbnormalReportCount(abnormalReport.getShiping_order_id());
                if (!is) {
                    returnjson.put("successMsg", "该运单信息已经异常上报，请等待处理");
                    return returnjson.toString();
                }

                boolean b = app_sercice.appSaveAbnormalReport(abnormalReport);
                boolean bb = false;
                if (b) {
                    String filenames = getjson.getString("filenames");
                    if (filenames != null && !filenames.equals("")) {
                        String[] file = filenames.split(",");
                        List<AbnormalImages> abnormalImagesList = new ArrayList<AbnormalImages>();
                        for (int j = 0; j < file.length; j++) {
                            if (!file[j].equals("")) {
                                String imageUrl = getPath(request, file[j],
                                        fileRoot);
                                AbnormalImages abnormalImages = new AbnormalImages();
                                abnormalImages.setImage_id(UUIDUtils.getUUID());
                                abnormalImages.setAbnormalt_id(abnormalReport
                                        .getId());
                                abnormalImages.setOrder_id(abnormalReport
                                        .getShiping_order_id());
                                abnormalImages.setImageUrl(imageUrl);
                                abnormalImagesList.add(abnormalImages);
                            }
                        }
                        if (abnormalImagesList.size() > 0) {
                            app_sercice
                                    .appSaveAbnormalImage(abnormalImagesList);
                        }
                        bb = app_sercice.appUpdateIsAbnormal(
                                abnormalReport.getShiping_order_id(),
                                abnormalReport.getAbnormal_type());
                        // 保存图片信息
                    } else {
                        // 更新运单状态
                        bb = app_sercice.appUpdateIsAbnormal(
                                abnormalReport.getShiping_order_id(),
                                abnormalReport.getAbnormal_type());
                    }
                    ;
                    String message = "异常上报，上报原因为"
                            + abnormalReport.getAbnormal_message();
                    saveHistory(abnormalReport.getShiping_order_id(),
                            abnormalReport.getShiping_order_num(), message, "1");
                }
                if (bb) {
                    returnjson.put("successMsg", "异常上报成功");
                } else {
                    returnjson.put("errorMsg", "异常上报失败");
                }

                // returnjson.put("errorMsg", "用户信息添加失败");

            } catch (Exception e) {
                returnjson.put("errorMsg", "出错了");
                log.error("AppController:" + e.getCause().getClass() + ","
                        + e.getCause().getMessage());
            }
        }
        return returnjson.toString();
    }

    /**
     * APP 异常上报处理
     *
     * @param json
     * @return
     */
    @RequestMapping(params = "method=appDoExceptionHandling")
    public
    @ResponseBody
    String appDoExceptionHandling(String json, HttpServletRequest request) {
        Gson gson = new Gson();
        JSONObject getjson = JSONObject.fromObject(json);
        JSONObject returnjson = new JSONObject();
        String userInfo = getjson.getString("userInfo");// 获取json用户信息
        User user = gson.fromJson(userInfo, User.class);
        String abReport = getjson.getString("abnormalReport");
        AbnormalReport abnormalReport = gson.fromJson(abReport,
                AbnormalReport.class);
        if (user != null) {
            try {
                boolean b = app_sercice.appUpdateAbnormalState(
                        abnormalReport.getId(),
                        abnormalReport.getAbnormal_resultremark(),
                        user.getUsername());
                if (b) {
                    boolean bb = app_sercice
                            .appGetAbnormalOrderNum(abnormalReport
                                    .getShiping_order_id());
                    if (bb) {
                        app_sercice.appUpdateOrederAbnormal(abnormalReport
                                .getShiping_order_id());

                    }
                    /*
                     * AndroidPushBatchUniMsg.androidPushBatchMsg("这是测试推送消息",
					 * "推送内容", cha);
					 */
                    returnjson.put("successMsg", "异常处理成功");
                } else {
                    returnjson.put("errorMsg", "异常处理失败");
                }

                // returnjson.put("errorMsg", "用户信息添加失败");
                /*
                 * } catch (PushClientException e) {
				 * log.error("AppController:+推送" + e.getCause().getClass() + ","
				 * + e.getCause().getMessage()); } catch (PushServerException e)
				 * { log.error("AppController:+推送" + e.getCause().getClass() +
				 * "," + e.getCause().getMessage());
				 */
            } catch (Exception e) {
                returnjson.put("errorMsg", "出错了");
                log.error("AppController:" + e.getCause().getClass() + ","
                        + e.getCause().getMessage());
            }
        }
        return returnjson.toString();
    }

    /**
     * APP
     * 登陆查询运单数----------------------------------------------------------------
     * -------------------------------------------
     *
     * @param json
     * @return
     */
    @RequestMapping(params = "method=appGetShipperOrderCountInfo")
    public
    @ResponseBody
    String appGetShipperOrderCountInfo(String json) {
        Gson gson = new Gson();
        JSONObject getjson = JSONObject.fromObject(json);
        JSONObject returnjson = new JSONObject();
        String userInfo = getjson.getString("userInfo");// 获取json用户信息
        User user = gson.fromJson(userInfo, User.class);

        try {

            if (user != null) {
                List<Map<String, Integer>> list = app_sercice
                        .getNumberMessage(user);
                // orderMsgCount消息数
                returnjson.put("successMsg", list);
            }

        } catch (Exception e) {
            returnjson.put("errorMsg", "出错了");
            log.error("AppController:" + e.getCause().getClass() + ","
                    + e.getCause().getMessage());
        }

        return returnjson.toString();
    }

    /**
     * 获取逆向物流信息运单数
     *
     * @param json
     * @return
     */
    @RequestMapping(params = "method=appGetReverseShipperOrderCountInfo")
    public
    @ResponseBody
    String appGetReverseShipperOrderCountInfo(String json) {
        Gson gson = new Gson();
        JSONObject getjson = JSONObject.fromObject(json);
        JSONObject returnjson = new JSONObject();
        String userInfo = getjson.getString("userInfo");// 获取json用户信息
        User user = gson.fromJson(userInfo, User.class);
        try {

            if (user != null) {
                List<Map<String, Integer>> list = app_sercice
                        .getReverseNumberMessage(user);
                returnjson.put("successMsg", list);
            }
        } catch (Exception e) {
            returnjson.put("errorMsg", "出错了");
            log.error("AppController:" + e.getCause().getClass() + ","
                    + e.getCause().getMessage());
        }

        return returnjson.toString();
    }

    /**
     * 获取首页消息数量
     *
     * @param json
     * @return
     */
    @RequestMapping(params = "method=appGetMessageCount")
    public
    @ResponseBody
    String appGetMessageCount(String json) {
        Gson gson = new Gson();
        JSONObject getjson = JSONObject.fromObject(json);
        JSONObject returnjson = new JSONObject();
        String userInfo = getjson.getString("userInfo");// 获取json用户信息
        User user = gson.fromJson(userInfo, User.class);
        try {

            if (user != null) {
                List<Map<String, Integer>> list = app_sercice
                        .appGetMessageCount(user);
                // orderMsgCount消息数
                returnjson.put("successMsg", list);
            }

        } catch (Exception e) {
            returnjson.put("errorMsg", "出错了");
            log.error("AppController:" + e.getCause().getClass() + ","
                    + e.getCause().getMessage());
        }

        return returnjson.toString();
    }

    /**
     * APP 运单分配页面
     *
     * @param json
     * @return
     */
    @RequestMapping(params = "method=appFeiPeiOrderInfo")
    public
    @ResponseBody
    String appFeiPeiOrderInfo(String json) {
        Gson gson = new Gson();
        JSONObject getjson = JSONObject.fromObject(json);
        JSONObject returnjson = new JSONObject();
        String userInfo = getjson.getString("userInfo");// 获取json用户信息
        String row = getjson.getString("row");// 几行开始
        String page = getjson.getString("page");// 显示多少个
        String send_time = getjson.getString("send_time");// 发运时间
        String shiping_order_num = getjson.getString("shiping_order_num");// 订单号
        String send_mechanism = getjson.getString("send_mechanism");// 受理机构
        String end_address = getjson.getString("end_address");// 终到位置
        String custom_name = getjson.getString("custom_name");// 发货客户姓名
        String receipt_name = getjson.getString("receipt_name");// 到货联系人
        String receipt_tel = getjson.getString("receipt_tel");// 到货联系人电话
        String shiping_order_goid = getjson.getString("shiping_order_goid");// 出货订单号
        String end_mechanism = getjson.containsKey("end_mechanism") ? getjson
                .getString("end_mechanism") : "";
        /*
         * String end_time = getjson.getString("end_time");// 发运结束时间 String
		 * topordernumber = getjson.getString("topordernumber");// String
		 * downordernumber = getjson.getString("downordernumber");// 发运结束时间
		 */
        User user = gson.fromJson(userInfo, User.class);
        int page1 = 0;
        int row1 = 0;
        if (user != null) {
            try {
                if (page != null && !"".equals(page)) {
                    page1 = Integer.parseInt(page);
                }
                if (row != null && !"".equals(row)) {
                    row1 = Integer.parseInt(row);
                }
                List<ShippingOrder> list = csi.getAllShipOrderInfoFenPei(row1,
                        page1, send_time, shiping_order_num, send_mechanism,
                        end_address, custom_name, receipt_name, receipt_tel,
                        shiping_order_goid, end_mechanism, user);
                returnjson.put("successMsg", list);

                // returnjson.put("errorMsg", "用户信息添加失败");

            } catch (Exception e) {
                returnjson.put("errorMsg", "出错了");
                log.error("AppController:" + e.getCause().getClass() + ","
                        + e.getCause().getMessage());
            }
        }
        return returnjson.toString();
    }

    /**
     * APP 查看司机已分配运单信息
     *
     * @param json
     * @return
     */
    @RequestMapping(params = "method=appGetFeiPeiOrderInfo")
    public
    @ResponseBody
    String appGetFeiPeiOrderInfo(String json) {
        Gson gson = new Gson();
        JSONObject getjson = JSONObject.fromObject(json);
        JSONObject returnjson = new JSONObject();
        String userInfo = getjson.getString("userInfo");// 获取json用户信息
        String driverId = getjson.getString("driverId");// 司机id
        String row = getjson.getString("row");// 几行开始
        String page = getjson.getString("page");// 显示多少个
        String send_time = getjson.getString("send_time");// 发运时间
        String shiping_order_num = getjson.getString("shiping_order_num");// 订单号
        String send_mechanism = getjson.getString("send_mechanism");// 受理机构
        String end_address = getjson.getString("end_address");// 终到位置
        String custom_name = getjson.getString("custom_name");// 发货客户姓名
        String receipt_name = getjson.getString("receipt_name");// 到货联系人
        String receipt_tel = getjson.getString("receipt_tel");// 到货联系人电话
        String shiping_order_goid = getjson.getString("shiping_order_goid");// 出货订单号
        String type = getjson.containsKey("type") ? getjson.getString("type")
                : "0";// 是否显示签收(0 不显示,1显示)
        String end_mechanism = getjson.containsKey("end_mechanism") ? getjson
                .getString("end_mechanism") : "";// 客户详细目的地
		/*
		 * String end_time = getjson.getString("end_time");// 发运结束时间 String
		 * topordernumber = getjson.getString("topordernumber");// String
		 * downordernumber = getjson.getString("downordernumber");// 发运结束时间
		 */
        User user = gson.fromJson(userInfo, User.class);
        int page1 = 0;
        int row1 = 0;
        if (user != null) {
            try {
                if (page != null && !"".equals(page)) {
                    page1 = Integer.parseInt(page);
                }
                if (row != null && !"".equals(row)) {
                    row1 = Integer.parseInt(row);
                }
				/*
				 * List<ShippingOrder> list =
				 * csi.getAllShipOrderInfoFenPei(row1, page1, send_time,
				 * shiping_order_num, send_mechanism, end_address, custom_name,
				 * receipt_name, receipt_tel, shiping_order_goid, user);
				 */
                if (user.getFlag().equals("3") || user.getFlag().equals("4")) {
                    Customer customer = suppliersService.getCustomer(user
                            .getCustomer_id());
                    send_mechanism = customer == null ? "没有绑定发货客户" : customer
                            .getCustomer_name();
                }

                List<ShippingOrder> list = csi.getShipOrderInfoOnePage(row1,
                        page1, send_time, shiping_order_num, send_mechanism,
                        end_address, custom_name, receipt_name, receipt_tel,
                        driverId, shiping_order_goid, type, end_mechanism, "");
                returnjson.put("successMsg", list);

                // returnjson.put("errorMsg", "用户信息添加失败");

            } catch (Exception e) {
                returnjson.put("errorMsg", "出错了");
                log.error("AppController:" + e.getCause().getClass() + ","
                        + e.getCause().getMessage());
            }
        }
        return returnjson.toString();
    }

    /**
     * APP 删除已分配的运单信息
     *
     * @param json
     * @return
     */
    @RequestMapping(params = "method=appDeleteShipOrderPage")
    public
    @ResponseBody
    String appDeleteShipOrderPage(String json) {
        JSONObject getjson = JSONObject.fromObject(json);
        JSONObject returnjson = new JSONObject();
        String driverId = getjson.getString("driverId");// 司机id
        String jsonorder = getjson.getString("orders");// 运单信息用逗号隔开
        String userInfo = getjson.getString("userInfo");// 获取json用户信息
        String[] orders = null;
        if (!"".equals(jsonorder) && jsonorder != null) {
            orders = jsonorder.split(",");
        }
        try {
            int truck = csi.deleteShipOrderPage(driverId, orders);
            if (truck == 0)
                returnjson.put("errorMsg", "未找到删除信息");
            csi.updateShipperOrderDelete(orders);
            new Thread(new FenPeiSaveHistoryThread(orders, csi, "运单删除分配", "7")).start();
            returnjson.put("successMsg", "删除成功");

            // returnjson.put("errorMsg", "用户信息添加失败");

        } catch (Exception e) {
            returnjson.put("errorMsg", "出错了");
            log.error("AppController:" + e.getCause().getClass() + ","
                    + e.getCause().getMessage());
        }
        return returnjson.toString();
    }

    /**
     * app 判断司机是否有gps
     *
     * @param json
     * @return
     */
    @RequestMapping(params = "method=appDriverISHaveGps")
    public
    @ResponseBody
    String appDriverISHaveGps(String json) {
        JSONObject getjson = JSONObject.fromObject(json);
        JSONObject returnjson = new JSONObject();
        String driverId = getjson.getString("driverId");// 司机id
        try {
            int i = app_sercice.appDriverISHaveGps(driverId);
            returnjson.put("successMsg", i);
        } catch (Exception e) {
            returnjson.put("errorMsg", "出错了");
            log.error("AppController:" + e.getCause().getClass() + ","
                    + e.getCause().getMessage());
        }
        return returnjson.toString();

    }

    /**
     * APP 获取gps设备信息列表
     *
     * @param json
     * @return
     */
    @RequestMapping(params = "method=appGetEquipmentManList")
    public
    @ResponseBody
    String appGetEquipmentManList(String json) {
        JSONObject getjson = JSONObject.fromObject(json);
        JSONObject returnjson = new JSONObject();
        // String driverId = getjson.getString("driverId");// 司机id
        String car_number = getjson.getString("car_number");// 车牌号
        String equipment_number = getjson.getString("equipment_number");// 设备号目前传空
        String userName = getjson.getString("userName");// 用户名目前传空
        String row = getjson.getString("row");// 几行开始
        String page = getjson.getString("page");// 显示多少个
        String userInfo = getjson.getString("userInfo");// 获取json用户信息
        Gson gson = new Gson();
        User user = gson.fromJson(userInfo, User.class);
        int page1 = 0;
        int row1 = 0;
        try {
            if (page != null && !"".equals(page)) {
                page1 = Integer.parseInt(page);
            }
            if (row != null && !"".equals(row)) {
                row1 = Integer.parseInt(row);
            }
            List<Equipment_info> list = equipmentManService
                    .getEquipmentManInfo(row1, page1, equipment_number,
                            userName, "", car_number, user);
            returnjson.put("successMsg", list);

            // returnjson.put("errorMsg", "用户信息添加失败");

        } catch (Exception e) {
            returnjson.put("errorMsg", "出错了");
            log.error("AppController:" + e.getCause().getClass() + ","
                    + e.getCause().getMessage());
        }
        return returnjson.toString();
    }

    /**
     * APP 供应商分配订单
     *
     * @param json
     * @return
     */
    @RequestMapping(params = "method=appFeiPeiShipperOrder")
    public
    @ResponseBody
    String appFeiPeiShipperOrder(String json) {
        JSONObject getjson = JSONObject.fromObject(json);
        JSONObject returnjson = new JSONObject();
        String jsonorder = getjson.getString("orders");// 订单
        String equipmentNum = getjson.containsKey("equipmentNum") ? getjson
                .getString("equipmentNum") : "";
        String userName = getjson.getString("userName");// 设备号绑定的用户名
        String[] orders = null;
        if (!"".equals(jsonorder) && jsonorder != null) {
            orders = jsonorder.split(",");
        }
        try {
            if (orders != null) {
                List<DriverToOrder> orderlist = new ArrayList<DriverToOrder>();
                for (int i = 0; i < orders.length; i++) {
                    DriverToOrder order = new DriverToOrder();
                    order.setId(UUIDUtils.getUUID());
                    order.setDriver_id(getjson.getString("driver_id"));
                    order.setOrder_id(orders[i]);
                    order.setEquipmentNum(equipmentNum);
                    order.setUserName(userName);
                    boolean b = app_sercice.appShiFouFenPei(orders[i]);
                    if (b) {
                        orderlist.add(order);
                    }
                }
                int i = app_sercice.appSaveDrivertoOrder(orderlist);
                if (i > 0) {
                    int j = app_sercice.appUpdateShipperorderState(orders);
                    if (j > 0) {
                        new Thread(new FenPeiSaveHistoryThread(orders, csi))
                                .start();
                    }
                    returnjson.put("successMsg", "添加成功");
                } else {
                    returnjson.put("errorMsg", "添加失败");
                }
            } else {
                returnjson.put("errorMsg", "没有要分配的订单");
            }
            return returnjson.toString();
        } catch (Exception e) {
            log.error("AppController:" + e.getCause().getClass() + ","
                    + e.getCause().getMessage());
            returnjson.put("errorMsg", "出错了");
            return returnjson.toString();
        }
    }

    /**
     * APP 查询供应商对应的司机列表
     *
     * @param json
     * @return
     */
    @RequestMapping(params = "method=appGetShipperToCar")
    public
    @ResponseBody
    String appGetShipperToCar(String json) {
        Gson gson = new Gson();
        JSONObject getjson = JSONObject.fromObject(json);
        String userInfo = getjson.getString("userInfo");// 获取json用户信息
        JSONObject returnjson = new JSONObject();
        // String suppliers_id = getjson.getString("suppliers_id");
        String row = getjson.getString("row");// 几行开始
        String page = getjson.getString("page");// 显示几个
        String driver_name = getjson.getString("driver_name");// 司机姓名
        // getjson.getString("page");//
        // 显示多少个 String
        // driver_name =
        String driver_suppliers = getjson.getString("driver_suppliers");// 供应商姓名
        // String
        // driver_phone
        String driver_phone = getjson.getString("driver_phone");// 司机电话 String
        // driver_address
        // =
        String driver_address = getjson.getString("driver_address");// 终到位置
        // String driver_cardnumber = getjson.getString("driver_cardnumber");//
        String shiping_order_goid = getjson.containsKey("shiping_order_goid") ? getjson
                .getString("shiping_order_goid") : "";// 出货订单号
        String shiping_order_num = getjson.containsKey("shiping_order_num") ? getjson
                .getString("shiping_order_num") : "";// 订单号
        // 身份证号
        User user = gson.fromJson(userInfo, User.class);
        int page1 = 0;
        int row1 = 0;
        try {

            if (page != null && !"".equals(page)) {
                page1 = Integer.parseInt(page);
            }
            if (row != null && !"".equals(row)) {
                row1 = Integer.parseInt(row);
            }

            List<Driver> list = dsi.getDriverInfo(row1, page1, driver_name,
                    driver_suppliers, driver_phone, "", driver_address,
                    shiping_order_num, shiping_order_goid, user.getId(), user);

            returnjson.put("successMsg", list);
            return returnjson.toString();
        } catch (Exception e) {
            log.error("AppController:" + e.getCause().getClass() + ","
                    + e.getCause().getMessage());
            returnjson.put("errorMsg", "出错了");
            return returnjson.toString();
        }
    }

    /**
     * APP 运单接单显示运单信息列表
     *
     * @param json
     * @return
     */
    @RequestMapping(params = "method=appGetReceiveOrderInfo")
    public
    @ResponseBody
    String appGetReceiveOrderInfo(String json) {
        Gson gson = new Gson();
        JSONObject getjson = JSONObject.fromObject(json);
        JSONObject returnjson = new JSONObject();
        String userInfo = getjson.getString("userInfo");// 获取json用户信息
        String row = getjson.getString("row");// 几行开始
        String page = getjson.getString("page");// 显示多少个
        String send_time = getjson.getString("send_time");// 发运时间
        String end_time = getjson.getString("end_time");// 发运结束时间 String
        String shiping_order_num = getjson.getString("shiping_order_num");// 订单号
        String send_mechanism = getjson.getString("send_mechanism");// 受理机构
        String end_address = getjson.getString("end_address");// 终到位置
        // String custom_name = getjson.getString("custom_name");// 发货客户姓名
        String receipt_name = getjson.getString("receipt_name");// 到货联系人
        // String receipt_tel = getjson.getString("receipt_tel");// 到货联系人电话
        String shiping_order_goid = getjson.getString("shiping_order_goid");// 出货订单号
		/*
		 *
		 * topordernumber = getjson.getString("topordernumber");// String
		 * downordernumber = getjson.getString("downordernumber");// 发运结束时间
		 */
        User user = gson.fromJson(userInfo, User.class);
        int page1 = 0;
        int row1 = 0;
        List<ShippingOrder> list = new ArrayList<ShippingOrder>();
        try {
            if (page != null && !"".equals(page)) {
                page1 = Integer.parseInt(page);
            }
            if (row != null && !"".equals(row)) {
                row1 = Integer.parseInt(row);
            }
            list = app_sercice.appGetReceiveOrderInfo(row1, page1, send_time,
                    end_time, shiping_order_num, send_mechanism, receipt_name,
                    end_address, shiping_order_goid, user);
			/*
			 * List<ShippingOrder> list = csi.getSignShipOrder(row1, page1,
			 * shiping_order_num, send_time, end_time, "2", send_mechanism,
			 * receipt_name, end_address, shiping_order_goid, user);
			 */
            returnjson.put("successMsg", list);

            // returnjson.put("errorMsg", "用户信息添加失败");

        } catch (Exception e) {
            returnjson.put("errorMsg", "出错了");
            log.error("AppController:" + e.getCause().getClass() + ","
                    + e.getCause().getMessage());
        }

        return returnjson.toString();
    }

    /**
     * APP 司机接单
     *
     * @param json
     * @return
     */
    @RequestMapping(params = "method=appReceiveOrder")
    public
    @ResponseBody
    String appReceiveOrder(String json) {
        JSONArray jsonArray = JSONArray.fromObject(json);
        JSONObject returnjson = new JSONObject();

        try {
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject gson = jsonArray.getJSONObject(i);
                String order_id = gson.getString("shiping_order_id");
                String driver_id = gson.getString("driver_id");
                String order_num = gson.getString("order_num");
                String driver_name = gson.getString("driver_name");

                ShippingOrder d = ordersercice
                        .getShipOrderInfos(order_id);
                if (1 == d.getWaybill_state()  ||2  == d.getWaybill_state()) continue;
                //returnjson.put("successMsg", order_num+"订单进行了运单调整，请等待处理");
                if (1== (d.getBusiness_type()==null?0:d.getBusiness_type())) {
                    continue;
                }

                int x = app_sercice.appReceiveOrder(order_id, driver_id);
                if (x > 0) {
                    String allTime = app_sercice.appGetAgingAllTime(order_id);
                    app_sercice.appUpdateOrderState(2, order_id, allTime);
                    String i3 = app_sercice.appGetDriverName(driver_id);// 能不能传递过来
                    if (i3 != null) {
                        driver_name = i3;
                    }
                    HistoryUtils.saveHistory(order_id, order_num, "司机:" + i3 + "已接单", "2");

                /*    saveHistory(order_id, order_num, "司机:" + driver_name
                            + "已接单", "1");*/
                }
            }
        } catch (Exception e) {
            returnjson.put("errorMsg", "出错了");
            log.error("AppController:" + e.getCause().getClass() + ","
                    + e.getCause().getMessage());
        }

        returnjson.put("successMsg", "接单成功,未成功接单的TMS未操作或者进行了运单调整");

        return returnjson.toString();
    }

    /**
     * APP 运单签收显示运单信息列表
     *
     * @param json
     * @return
     */
    @RequestMapping(params = "method=appGetSignOrderInfo")
    public
    @ResponseBody
    String appGetSignOrderInfo(String json) {
        Gson gson = new Gson();
        JSONObject getjson = JSONObject.fromObject(json);
        JSONObject returnjson = new JSONObject();
        String userInfo = getjson.getString("userInfo");// 获取json用户信息
        String row = getjson.getString("row");// 几行开始
        String page = getjson.getString("page");// 显示多少个
        String send_time = getjson.getString("send_time");// 发运时间
        String end_time = getjson.getString("end_time");// 发运结束时间 String
        String shiping_order_num = getjson.getString("shiping_order_num");// 订单号
        String send_mechanism = getjson.getString("send_mechanism");// 受理机构
        String end_address = getjson.getString("end_address");// 终到位置
        // String custom_name = getjson.getString("custom_name");// 发货客户姓名
        String receipt_name = getjson.getString("receipt_name");// 到货联系人
        // String receipt_tel = getjson.getString("receipt_tel");// 到货联系人电话
        String shiping_order_goid = getjson.getString("shiping_order_goid");// 出货订单号
		/*
		 *
		 * topordernumber = getjson.getString("topordernumber");// String
		 * downordernumber = getjson.getString("downordernumber");// 发运结束时间
		 */
        User user = gson.fromJson(userInfo, User.class);
        int page1 = 0;
        int row1 = 0;

        try {
            if (page != null && !"".equals(page)) {
                page1 = Integer.parseInt(page);
            }
            if (row != null && !"".equals(row)) {
                row1 = Integer.parseInt(row);
            }
            List<ShippingOrder> list = app_sercice.appGetSignOrderInfo(row1,
                    page1, send_time, end_time, shiping_order_num,
                    send_mechanism, receipt_name, end_address,
                    shiping_order_goid, user);
			/*
			 * List<ShippingOrder> list = csi.getSignShipOrder(row1, page1,
			 * shiping_order_num, send_time, end_time, "2", send_mechanism,
			 * receipt_name, end_address, shiping_order_goid, user);
			 */
            returnjson.put("successMsg", list);

            // returnjson.put("errorMsg", "用户信息添加失败");

        } catch (Exception e) {
            returnjson.put("errorMsg", "出错了");
            log.error("AppController:" + e.getCause().getClass() + ","
                    + e.getCause().getMessage());
        }

        return returnjson.toString();
    }

    /**
     * APP 订单签收
     *
     * @param json
     * @param request
     * @return
     */
    @RequestMapping(params = "method=appSignShipOrder")
    public
    @ResponseBody
    String appSignShipOrder(String json, HttpServletRequest request) {
        JSONObject getjson = JSONObject.fromObject(json);
        JSONObject returnjson = new JSONObject();
        Gson gson = new Gson();
        Sign_order sign_order = gson.fromJson(getjson.getString("singnInfo"),
                Sign_order.class);
        String userInfo = getjson.getString("userInfo");// 获取json用户信息
        User user = gson.fromJson(userInfo, User.class);

        // sign_order.setSign_username(user)
        int i = 0;
        String order_id = sign_order.getOrder_id();
        String fileRoot = "signorderImages";
        String newFileName = getjson.getString("newFileName");
        try {
            if (user == null) {
                returnjson.put("errorMsg", "未获取到用户信息,请重新登录");
                return returnjson.toString();

            }
            sign_order.setSign_id(UUIDUtils.getUUID());
            sign_order.setSign_username(user.getRealName());
            ShippingOrder d = ordersercice.getShipOrderInfos(sign_order
                    .getOrder_id());
            if (d.getWaybill_state() == 1 || d.getWaybill_state() == 2) {
                returnjson.put("successMsg", "该订单进行了运单调整，请等待处理");
                return returnjson.toString();
            }
            String str_num = sign_order.getOrder_number();

            if (str_num != null) {
                if (str_num.indexOf(".53") != -1) {
                    interface_56LinkProject(sign_order, d);// 56link接口
                    // _56linkproject(sign_order, d);//56link 对接
                }
            }

            boolean bSign = app_sercice.appIsnotSignOrder(order_id);
            if (bSign) {
                i = app_sercice.appUpdateSignShipOrder(sign_order);
            } else {
                i = app_sercice.appSaveSignShipOrder(sign_order);
            }
            if (i > 0) {
                if (newFileName != null && !newFileName.equals("")) {
                    String[] images = newFileName.split(",");
                    List<ShipperOrderImg> shiOrdImg = new ArrayList<ShipperOrderImg>();
                    for (int j = 0; j < images.length; j++) {
                        if (images[j].equals(""))
                            continue;
                        String imageUrl = getPath(request, images[j], fileRoot);
                        ShipperOrderImg sOrdImg = new ShipperOrderImg();
                        sOrdImg.setImage_id(UUIDUtils.getUUID());
                        sOrdImg.setOrder_id(sign_order.getOrder_id());
                        sOrdImg.setImageUrl(imageUrl);
                        shiOrdImg.add(sOrdImg);
                    }
                    app_sercice.appSaveSignImages(shiOrdImg);
                }
                int ui = app_sercice.appUpdatestate(sign_order.getOrder_id());

                app_sercice.appInsertShippingTime(sign_order.getOrder_id());// 保存时效信息

                // updateDeliveryCustomerAddress(sign_order);// 保存收货客户地理位置
                HistoryUtils.saveHistory(sign_order.getOrder_id(),
                        sign_order.getOrder_number(), "app实际签收，签收人"
                                + sign_order.getSign_user(), "4");

               /* saveHistory(sign_order.getOrder_id(),
                        sign_order.getOrder_number(), "app实际签收，签收人"
                                + sign_order.getSign_user(), "1");*/
                if (ui > 0) {
                    returnjson.put("successMsg", "订单签收成功");
                } else {
                    returnjson.put("errorMsg", "运单签收,状态更新失败");
                }
                return returnjson.toString();
            } else {
                returnjson.put("errorMsg", "订单签收失败");
                return returnjson.toString();
            }
        } catch (Exception e) {
            log.error("AppController:" + e.getCause().getClass() + ","
                    + e.getCause().getMessage());
            returnjson.put("errorMsg", "出错了");
            return returnjson.toString();
        }

    }

    // 保存收货客户地理位置信息
    public void updateDeliveryCustomerAddress(Sign_order sign_order) {
        String order_id = sign_order.getOrder_id();
        ShippingOrder der = csi.getUpdateShipOrder(order_id);
        String receipt = der.getReceipt_name();
        String sendMechanism = der.getSend_mechanism();
        if (receipt != null && !"".equals(receipt)) {
            List<DeliveryCustomer> delivery = rms.getDeliveryCustomerbyname(
                    receipt, sendMechanism);
            if (delivery != null) {
                if (delivery.size() > 0) {
                    if (delivery.get(0).getDelivery_point() == null
                            || "".equals(delivery.get(0).getDelivery_point())) {
                        String point = sign_order.getLng() + ","
                                + sign_order.getLat();
                        delivery.get(0).setDelivery_point(point);
                        delivery.get(0).setDelivery_areaType("0");
                        delivery.get(0).setDelivery_radius("100");
                        rms.updateDeliveryCustomer(delivery.get(0));

                    }
                }

            }
        }
    }

    // 56link对接
    public void interface_56LinkProject(Sign_order sign_order, ShippingOrder d) {
        // zzp签收哦
        // zzp添加
        String str = "";
        Properties prop = new Properties();
        InputStream in = TemporaryCarController.class
                .getResourceAsStream("/../56linkedInterface.properties");
        try {
            prop.load(in);
            Iterator<String> it = prop.stringPropertyNames().iterator();
            while (it.hasNext()) {
                String key = it.next();
                if (key.equals("56linkedURL")) {
                    str = prop.getProperty(key);
                }

            }
            in.close();

        } catch (Exception e) {
            log.error("AppController:" + e.getCause().getClass() + ","
                    + e.getCause().getMessage());
            // returnjson.put("errorMsg", "出错了");
        } // /加载属性列表
        // 运单号、车牌号、
		/*
		 * ShippingOrder d = ordersercice.getShipOrderInfos(sign_order
		 * .getOrder_id());
		 */
        String ddate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                .format(Calendar.getInstance().getTime());

        String agtime = ddate;
        if (!("").equals(d.getReceivetime()) && d.getReceivetime() != null) {
            agtime = d.getReceivetime().substring(0, 10) + " "
                    + d.getAging_time();
            System.out.println(agtime);
        }
        String goid = d.getShiping_order_goid();
        String mechanism = d.getSend_mechanism();
        int i56 = csi.get56LinkProject(goid, mechanism);
        if (i56 == 0) {
            testPost(str, goid, sign_order.getSign_remarks(), ddate,
                    sign_order.getSign_user(), agtime);
        }

    }

    /**
     * APP 保存坐标信息
     *
     * @param json
     * @return
     */
    @RequestMapping(params = "method=saveMapInfo")
    public
    @ResponseBody
    String saveMapInfo(String json) {
        JSONObject getjson = JSONObject.fromObject(json);
        JSONObject returnjson = new JSONObject();
        Maps maps = new Maps();
        String lat = getjson.containsKey("lat") ? getjson.getString("lat") : "";
        String lng = getjson.containsKey("lng") ? getjson.getString("lng") : "";
        try {

            if (lat.equals("4.9E-324") || lat.equals("")
                    || lng.equals("4.9E-324") || lng.equals("")) {
                returnjson.put("successMsg", "地理坐标非法");
                return returnjson.toString();
            }

            int gpsCount = app_sercice.getGpsInfoCount(getjson
                    .getString("carid"));

            if (gpsCount > 0) {
                returnjson.put("successMsg", "用户已绑定gps");

                return returnjson.toString();
            }
			/*
			 * String url = "http://api.map.baidu.com/geoconv/v1/?coords=" +
			 * getjson.getString("lng") + "," + getjson.getString("lat") +
			 * "&from=3&to=5&ak=iz6uMstBVDO8oDt1GrAv9ynV"; String js =
			 * loadJSON(url);
			 */
            maps.setState("1");
            maps.setGpsstate("APP");
            maps.setBeidouid("");
            maps.setBeidouno("");
            maps.setOldLat(lat);
            maps.setOldLng(lng);
            maps.setLng(lng);
            maps.setLat(lat);
			/*
			 * //JSONObject obj = JSONObject.fromObject(js); if
			 * (obj.get("status").toString().equals("0")) { String lng =
			 * obj.getJSONArray("result").getJSONObject(0) .getString("x");
			 * String lat = obj.getJSONArray("result").getJSONObject(0)
			 * .getString("y"); maps.setLng(lng); maps.setLat(lat); } else {
			 * maps.setLng(getjson.getString("lng"));
			 * maps.setLat(getjson.getString("lat")); }
			 */
            maps.setCarno(getjson.getString("carid"));
            maps.setCarid(getjson.getString("carid"));

            // 计算距离
            List<Maps> map3 = mapService
                    .loadmapinfo(getjson.getString("carid"));
            double licheng = 0.0D;
            if ((map3.size() != 0)) {
                licheng = BaiDuMap.GetShortDistance(
                        Double.parseDouble(maps.getLng()),
                        Double.parseDouble(maps.getLat()),
                        Double.parseDouble(((Maps) map3.get(0)).getLng()),
                        Double.parseDouble(((Maps) map3.get(0)).getLat()));
            }
            maps.setNowspace(licheng + "");
            // 获取表明
            String db = BaiduWeather.getTableName(maps.getNowdate());

            try {
                mapService.deletecarbyid(getjson.getString("carid"));
            } catch (Exception e) {
                log.error("AppController:" + e.getCause().getClass() + ","
                        + e.getCause().getMessage());
                returnjson.put("errorMsg", "删除异常");
            }
            int i = mapService.saveMaps(maps);
            if (i > 0) {
                returnjson.put("successMsg", "临时表添加成功");
            } else {
                returnjson.put("errorMsg", "临时表添加失败");
            }
            int k = 0;
            if (!maps.getNowspace().equals("0.0")) {
                k = mapService.saveMapsweek(maps, db);
            }


            if (k > 0) {
                returnjson.put("successMsg", "添加成功");
            } else {
                returnjson.put("errorMsg", "添加失败");
            }
        } catch (Exception e) {
            log.error("AppController:" + e.getCause().getClass() + ","
                    + e.getCause().getMessage());
            returnjson.put("errorMsg", "出错了");
        }
        return returnjson.toString();
    }

    /**
     * APP 修改密码
     *
     * @param json
     * @return
     */
    @RequestMapping(params = "method=appModifyPassword")
    public
    @ResponseBody
    String appModifyPassword(String json) {
        JSONObject gson = JSONObject.fromObject(json);
        JSONObject returnjson = new JSONObject();
        String password1 = gson.getString("password1");
        String password2 = gson.getString("password2");
        try {
            User u = uis.getUser1(gson.getString("id"));
            boolean pass = u.getPassword().equals(MD5.toMD5(password1));
            if (pass) {
                boolean flag = uis.modifyPassword(gson.getString("id"),
                        MD5.toMD5(password2));
                if (flag) {
                    returnjson.put("successMsg", "修改密码成功");
                } else {
                    returnjson.put("errorMsg", "修改密码失败");
                }
            } else {
                returnjson.put("errorMsg", "原密码输入错误");
            }
            return returnjson.toString();
        } catch (Exception e) {
            returnjson.put("errorMsg", "出错了");
            log.error("AppController:" + e.getCause().getClass() + ","
                    + e.getCause().getMessage());
            return returnjson.toString();
        }
    }

    /**
     * APP 逆向物流信息列表  ----- 过时了
     *
     * @param json
     * @return
     */
    @RequestMapping(params = "method=appGetReverseOrderInfo")
    public
    @ResponseBody
    String appGetReverseOrderInfo(String json) {
        Gson gson = new Gson();
        JSONObject getjson = JSONObject.fromObject(json);
        JSONObject returnjson = new JSONObject();
        String userInfo = getjson.getString("userInfo");// 获取json用户信息
        String row = getjson.getString("row");// 几行开始
        String page = getjson.getString("page");// 显示多少个
        String send_time = getjson.getString("send_time");// 发运时间
        String end_time = getjson.getString("end_time");// 结束时间
        String shiping_order_num = getjson.getString("shiping_order_num");// 订单号
        String end_mechanism = getjson.getString("end_mechanism");// 终到位置
        String custom_name = getjson.getString("custom_name");// 发货客户姓名
        String shiping_order_state = getjson.getString("shiping_order_state");// 运单状态
        // String send_mechanism = getjson.getString("send_mechanism");// 受理机构
        // String receipt_name = getjson.getString("receipt_name");// 到货联系人
        // String receipt_tel = getjson.getString("receipt_tel");// 到货联系人电话
        // String shiping_order_goid =
        // getjson.getString("shiping_order_goid");// 出货订单号
		/*
		 *
		 * topordernumber = getjson.getString("topordernumber");// String
		 * downordernumber = getjson.getString("downordernumber");// 发运结束时间
		 */
        User user = gson.fromJson(userInfo, User.class);
        int page1 = 0;
        int row1 = 0;

        try {
            if (page != null && !"".equals(page)) {
                page1 = Integer.parseInt(page);
            }
            if (row != null && !"".equals(row)) {
                row1 = Integer.parseInt(row);
            }
            List<ShippingOrder> list = app_sercice.appGetReverseOrderInfo(row1,
                    page1, send_time, end_time, shiping_order_num,
                    end_mechanism, custom_name, shiping_order_state, user);

            returnjson.put("successMsg", list);

        } catch (Exception e) {
            returnjson.put("errorMsg", "出错了");
            log.error("AppController:" + e.getCause().getClass() + ","
                    + e.getCause().getMessage());
        }

        return returnjson.toString();
    }


    /**
     * APP 逆向物流发货客户查询
     *
     * @param json
     * @return
     */
    @RequestMapping(params = "method=appReverseGetCustomersInfo")
    public
    @ResponseBody
    String appReverseGetCustomersInfo(String json) {
        // Gson gson = new Gson();
        JSONObject getjson = JSONObject.fromObject(json);
        JSONObject returnjson = new JSONObject();
        // String userInfo = getjson.getString("userInfo");// 获取json用户信息
        String customersName = getjson.getString("customersName");// 供应商姓名
        // User user = gson.fromJson(userInfo, User.class);
        try {
            List<Customer> list = app_sercice
                    .appReverseGetCustomersInfo(customersName);
            returnjson.put("successMsg", list);
        } catch (Exception e) {
            returnjson.put("errorMsg", "出错了");
            log.error("AppController:" + e.getCause().getClass() + ","
                    + e.getCause().getMessage());
        }
        return returnjson.toString();
    }

    /**
     * 签收查询逆向物流信息
     *
     * @return
     */
    @RequestMapping(params = {"method=appGetSignReturnGoodsInfo"})
    public
    @ResponseBody
    String appGetSignReturnGoodsInfo(String json) {
        JSONObject getjson = JSONObject.fromObject(json);
        Gson gson = new Gson();
        JSONObject returnjson = new JSONObject();
        String userInfo = getjson.getString("userInfo");// 获取json用户信息
        String row = getjson.getString("row");// 几行开始
        String page = getjson.getString("page");// 显示多少个
        String shiping_order_num = getjson.getString("shiping_order_num");//订单号
        String send_station = getjson.getString("send_station");//起运地
        String send_time = getjson.getString("send_time");// 发运时间
        String end_time = getjson.getString("end_time");// 结束时间
        String end_address = getjson.getString("end_address");// 终到位置
        String custom_name = getjson.getString("custom_name");// 发货客户名称
        String custom_tel = getjson.getString("custom_tel");//发货客户电话
        String send_mechanism = getjson.getString("send_mechanism");//受理机构
        String end_mechanism = getjson.getString("end_mechanism");//终到机构
        String shipping_order_state = getjson.getString("shipping_order_state");//订单状态（0未签收,4已签收）
        String order_type = getjson.getString("order_type");//订单类型
        User user = gson.fromJson(userInfo, User.class);
        int page1 = 0;
        int row1 = 0;

        try {
            if (page != null && !"".equals(page)) {
                page1 = Integer.parseInt(page);
            }
            if (row != null && !"".equals(row)) {
                row1 = Integer.parseInt(row);
            }
            Map<String, Object> map = ros.getSignReverseOrderInfo(user, row1, page1, send_time, end_time, shiping_order_num, shipping_order_state, order_type, send_mechanism, end_address, end_mechanism);
            returnjson.put("successMsg", map.get("rows"));
        } catch (Exception e) {
            returnjson.put("errorMsg", "出错了");
            log.error("AppController:" + e.getCause().getClass() + ","
                    + e.getCause().getMessage());
        }
        return returnjson.toString();
    }


    /**
     * 签收查询逆向物流信息最终
     *
     * @return
     */
    @RequestMapping(params = {"method=appGetNewReverseOrderInfo"})
    public
    @ResponseBody
    String appGetNewReverseOrderInfo(String json) {
        JSONObject getjson = JSONObject.fromObject(json);
        Gson gson = new Gson();
        JSONObject returnjson = new JSONObject();
        String userInfo = getjson.getString("userInfo");// 获取json用户信息
        String row = getjson.getString("row");// 几行开始
        String page = getjson.getString("page");// 显示多少个
        String shiping_order_num = getjson.getString("shiping_order_num");//订单号
        String send_station = getjson.getString("send_station");//起运地
        String send_time = getjson.getString("send_time");// 发运时间
        String end_time = getjson.getString("end_time");// 结束时间
        String end_address = getjson.getString("end_address");// 终到位置
        String custom_name = getjson.getString("custom_name");// 发货客户名称
        String custom_tel = getjson.getString("custom_tel");//发货客户电话
        String send_mechanism = getjson.getString("send_mechanism");//受理机构
        String end_mechanism = getjson.getString("end_mechanism");//终到机构
        String shipping_order_state = getjson.getString("shipping_order_state");//订单状态（0未签收,4已签收）
        String order_type = getjson.getString("order_type");//订单类型
        User user = gson.fromJson(userInfo, User.class);
        int page1 = 0;
        int row1 = 0;
        try {
            if (page != null && !"".equals(page)) {
                page1 = Integer.parseInt(page);
            }
            if (row != null && !"".equals(row)) {
                row1 = Integer.parseInt(row);
            }
            Map<String, Object> map = app_sercice.getSignReverseOrderInfo(user, row1, page1, send_time, end_time, shiping_order_num, shipping_order_state, order_type, send_mechanism, end_address, end_mechanism);
            returnjson.put("successMsg", map);
        } catch (Exception e) {
            returnjson.put("errorMsg", "出错了");
            log.error("AppController:" + e.getCause().getClass() + ","
                    + e.getCause().getMessage(), e);
        }
        return returnjson.toString();
    }

    /**
     * 逆向物流退货添加
     *
     * @param json
     * @return
     */
    @RequestMapping(params = "method=appSaveReturnGoods")
    public
    @ResponseBody
    String appSaveReturnGoods(String json) {
        Gson gson = new Gson();
        JSONObject getjson = JSONObject.fromObject(json);
        JSONObject returnjson = new JSONObject();
        String reverseOrder = getjson.getString("reverseOrderInfo");// 获取添加信息
        String userInfo = getjson.getString("userInfo");// 获取json用户信息
        ReverserOrderModel reverserOrderModel = gson.fromJson(reverseOrder,
                ReverserOrderModel.class);
        User user = gson.fromJson(userInfo, User.class);

        if (user != null) {
            try {
                if (reverserOrderModel == null) {
                    returnjson.put("successMsg", "逆向物流,请填写信息");
                    return returnjson.toString();
                }
                if (!reverserOrderModel.getShiping_order_num().equals("")) {
                    boolean b = app_sercice.appCheckReverseOrderNum(reverserOrderModel
                            .getShiping_order_num());//检查单号是否存在
                    if (b) {
                        boolean bb = ros.addReverseOrderInfo(user, reverserOrderModel);
                        OrderHistory orderHistory = new OrderHistory();
                        if (bb) {
                            String message = ReverseOrderInfoServiceImpl.insertMessage(user, reverserOrderModel);
                            ros.saveReverseOrderHistory(reverserOrderModel, message, user);//保存历史信息
                            returnjson.put("successMsg", "逆向物流,添加成功");
                        }
                    } else {
                        returnjson.put("errorMsg", "运单号已存在");
                    }
                }
            } catch (Exception e) {
                returnjson.put("errorMsg", "出错了");
                log.error("AppController:" + e.getCause().getClass() + ","
                        + e.getCause().getMessage());
            }
        }
        return returnjson.toString();
    }

    /**
     * 获取退货逆向物流分配列表
     *
     * @return
     */
    @RequestMapping(params = {"method=appGetDistributionReturnGoods"})
    public
    @ResponseBody
    String appGetDistributionReturnGoods(String json) {
        JSONObject getjson = JSONObject.fromObject(json);
        Gson gson = new Gson();
        JSONObject returnjson = new JSONObject();
        String userInfo = getjson.getString("userInfo");// 获取json用户信息
        String row = getjson.getString("row");// 几行开始
        String page = getjson.getString("page");// 显示多少个
        String shiping_order_num = getjson.getString("shiping_order_num");//订单号
        String send_station = getjson.getString("send_station");//起运地
        String send_time = getjson.getString("send_time");// 发运时间
        String end_time = getjson.getString("end_time");// 结束时间
        String end_address = getjson.getString("end_address");// 终到位置
        String custom_name = getjson.getString("custom_name");// 发货客户名称
        String custom_tel = getjson.getString("custom_tel");//发货客户电话
        String send_mechanism = getjson.getString("send_mechanism");//受理机构
        String end_mechanism = getjson.getString("end_mechanism");//终到机构
        String shipping_order_state = getjson.getString("shipping_order_state");//订单状态
        String order_type = getjson.getString("order_type");//订单类型
        User user = gson.fromJson(userInfo, User.class);
        int page1 = 0;
        int row1 = 0;

        try {
            if (page != null && !"".equals(page)) {
                page1 = Integer.parseInt(page);
            }
            if (row != null && !"".equals(row)) {
                row1 = Integer.parseInt(row);
            }
            Map<String, Object> map = ros.getDistributionReverseOrderInfo(user, row1, page1, send_time, end_time, end_address, send_mechanism, custom_name, order_type);
            returnjson.put("successMsg", map.get("rows"));
        } catch (Exception e) {
            returnjson.put("errorMsg", "出错了");
            log.error("AppController:" + e.getCause().getClass() + ","
                    + e.getCause().getMessage());
        }
        return returnjson.toString();
    }

    /**
     * 获取退货逆向物流接单列表---要改
     *
     * @return
     */
    @RequestMapping(params = {"method=appGetTakingReturnGoods"})
    public
    @ResponseBody
    String appGetTakingReturnGoods(String json) {
        JSONObject getjson = JSONObject.fromObject(json);
        Gson gson = new Gson();
        JSONObject returnjson = new JSONObject();
        String userInfo = getjson.getString("userInfo");// 获取json用户信息
        String row = getjson.getString("row");// 几行开始
        String page = getjson.getString("page");// 显示多少个
        String shiping_order_num = getjson.getString("shiping_order_num");//订单号
        String send_station = getjson.getString("send_station");//起运地
        String send_time = getjson.getString("send_time");// 发运时间
        String end_time = getjson.getString("end_time");// 结束时间
        String end_address = getjson.getString("end_address");// 终到位置
        String custom_name = getjson.getString("custom_name");// 发货客户名称
        String custom_tel = getjson.getString("custom_tel");//发货客户电话
        String send_mechanism = getjson.getString("send_mechanism");//受理机构
        String end_mechanism = getjson.getString("end_mechanism");//终到机构
        String shipping_order_state = getjson.getString("shipping_order_state");//订单状态
        User user = gson.fromJson(userInfo, User.class);
        String order_type = getjson.getString("order_type");//订单类型
        int page1 = 0;
        int row1 = 0;
        try {
            if (page != null && !"".equals(page)) {
                page1 = Integer.parseInt(page);
            }
            if (row != null && !"".equals(row)) {
                row1 = Integer.parseInt(row);
            }
            List<ReverserOrderModel> list = app_sercice.appGetTakingReverseOrderInfo(user, row1, page1, send_time, end_time, end_address, send_mechanism, custom_name, order_type);
            returnjson.put("successMsg", list);
        } catch (Exception e) {
            returnjson.put("errorMsg", "出错了");
            log.error("AppController:" + e.getCause().getClass() + ","
                    + e.getCause().getMessage());
        }
        return returnjson.toString();
    }


    /**
     * 逆向物流退货分配
     *
     * @param json
     * @return
     */
    @RequestMapping(params = "method=appSaveDistributionReturnGoods")
    public
    @ResponseBody
    String appSaveDistributionReturnGoods(String json) {
        Gson gson = new Gson();
        JSONObject getjson = JSONObject.fromObject(json);
        JSONObject returnjson = new JSONObject();
        String driverId = getjson.getString("driverId");// 获取json用户信息
        String driverName = getjson.getString("driverName");// 获取司机姓名
        String jsonOrder = getjson.getString("orders");
        String[] orders = null;
        // System.out.println("这是json串" + jsonorder);
        if (!"".equals(jsonOrder) && jsonOrder != null) {
            orders = jsonOrder.split(",");
        }
        boolean b;
        try {
            b = ros.saveDistributionReverseOrderInfo(driverId, orders);
            returnjson.put("successMsg", b ? "逆向物流,分配成功" : "逆向物流,分配失败");
        } catch (Exception e) {
            returnjson.put("errorMsg", "出错了");
            log.error("AppController:" + e.getCause().getClass() + ","
                    + e.getCause().getMessage(), e);
        }

        return returnjson.toString();
    }

    /**
     * 逆向物流退货接单---要改
     *
     * @param json
     * @return
     */
    @RequestMapping(params = "method=appSaveTakingReturnGoods")
    public
    @ResponseBody
    String appSaveTakingReturnGoods(String json, HttpServletRequest request) {
        Gson gson = new Gson();
        JSONObject getjson = JSONObject.fromObject(json);
        JSONObject returnjson = new JSONObject();
        String reverseOrderInfo = getjson.getString("reverseOrderInfo");// 获取json用户信息
        String newFileName = getjson.getString("newFileName");//文件名称
        ReverserOrderModel reverserOrderModel = gson.fromJson(reverseOrderInfo,
                ReverserOrderModel.class);
        String fileRoot = "takingReverseOrder";
        if (reverserOrderModel == null) reverserOrderModel = new ReverserOrderModel();
        boolean b = ros.takingReverserOrder(reverserOrderModel.getShiping_order_id(), reverserOrderModel.getDriver_id(), reverserOrderModel.getRemarks(), 1);
        if (b) {
            reverserOrderModel.setShipping_order_state(2);
            boolean update = ros.updateReverserOrder(reverserOrderModel);
            int type = reverserOrderModel.getOrder_type();

            if (update && type == 1) {
                ReverseOrderInfoController reverseOrderInfoController = SpringContextHolder.getBean("reverseOrderInfoController");
                reverseOrderInfoController.saveShippingOrder(reverserOrderModel);
            }
            if (newFileName != null) {
                String[] file = newFileName.split(",");
                ShipperOrderImg shipperOrderImg;
                List<ShipperOrderImg> shipperOrderImgs = new ArrayList<ShipperOrderImg>();
                for (int j = 0; j < file.length; j++) {
                    if ("".equals(file[j]))
                        continue;
                    String imageUrl = getPath(request, file[j],
                            fileRoot);
                    shipperOrderImg = new ShipperOrderImg();
                    shipperOrderImg.setOrder_id(reverserOrderModel.getShiping_order_id());
                    shipperOrderImg.setImage_id(UUIDUtils.getUUID());
                    shipperOrderImg.setImageUrl(imageUrl);
                    shipperOrderImgs.add(shipperOrderImg);
                }
                ros.batchSaveTakingReverseImage(shipperOrderImgs);
            }
            OrderHistory orderHistory = new OrderHistory();
            orderHistory.setOrder_history_id(UUIDUtils.getUUID());
            orderHistory.setOrders_id(reverserOrderModel.getShiping_order_id());
            orderHistory.setOrder_arrive_time(DateFormat.getDateTimeInstance().format(new Date()));
            orderHistory.setGoods_arrive_remakes(orderHistory.getOrder_arrive_time() + "---" + "货运编号:" + reverserOrderModel.getShiping_order_num() + " 司机已经提货发运");
            boolean b1 = ros.saveReverseOrderHistory(orderHistory);
            if (b1) {
                returnjson.put("successMsg", "逆向物流,接单成功");
            } else {
                returnjson.put("errorMsg", "逆向物流,接单失败");
            }
        }
        return returnjson.toString();
    }


    /**
     * APP 逆向物流录入-----------过时了
     *
     * @param json
     * @return
     */

    @RequestMapping(params = "method=appSaveReverseOrder")
    public
    @ResponseBody
    String appSaveReverseOrder(String json) {
        Gson gson = new Gson();
        JSONObject getjson = JSONObject.fromObject(json);
        JSONObject returnjson = new JSONObject();
        String reverseOrder = getjson.getString("reverseOrderInfo");// 获取json用户信息
        String userInfo = getjson.getString("userInfo");// 获取json用户信息
        ShippingOrder shippingOrder = gson.fromJson(reverseOrder,
                ShippingOrder.class);
        User user = gson.fromJson(userInfo, User.class);
        if (user != null) {
            try {
                boolean b = app_sercice.appCheckReverseOrderNum(shippingOrder
                        .getShiping_order_num());// 检查运单否存在
                if (b) {
                    shippingOrder.setShiping_order_id(UUIDUtils.getUUID());
                    if (user.getFlag().equals("1")) {
                        String suppliers = ar_service.getSupplersId(user
                                .getDriver_id());
                        user.setSuppliers_id(suppliers);
                    }
                    shippingOrder.setOrder_mechanism(user.getSuppliers_id());
                    shippingOrder.setReverse_orderid(user.getDriver_id());
                    shippingOrder.setShipping_order(user.getUsername());
                    int i = app_sercice.appSaveReverseOrder(shippingOrder);
                    if (i > 0) {
                        saveHistory(shippingOrder.getShiping_order_id(),
                                shippingOrder.getShiping_order_num(), "该运单已经录入,操作人"
                                        + shippingOrder.getShipping_order(),
                                "2");
                        returnjson.put("successMsg", "逆向物流,添加成功");
                    } else {
                        returnjson.put("errorMsg", "逆向物流,添加失败");
                    }

                } else {
                    returnjson.put("errorMsg", "运单号已存在");
                }

            } catch (Exception e) {
                returnjson.put("errorMsg", "出错了");
                log.error("AppController:" + e.getCause().getClass() + ","
                        + e.getCause().getMessage());
            }
        }

        return returnjson.toString();
    }

    /**
     * 获取退货逆向物流签收列表---要改
     *
     * @return
     */
    @RequestMapping(params = {"method=appGetSignReturnGoods"})
    public
    @ResponseBody
    String appGetSignReturnGoods(String json) {
        JSONObject getjson = JSONObject.fromObject(json);
        Gson gson = new Gson();
        JSONObject returnjson = new JSONObject();
        String userInfo = getjson.getString("userInfo");// 获取json用户信息
        String row = getjson.getString("row");// 几行开始
        String page = getjson.getString("page");// 显示多少个
        String shiping_order_num = getjson.getString("shiping_order_num");//订单号
        String send_station = getjson.getString("send_station");//起运地
        String send_time = getjson.getString("send_time");// 发运时间
        String end_time = getjson.getString("end_time");// 结束时间
        String end_address = getjson.getString("end_address");// 终到位置
        String custom_name = getjson.getString("custom_name");// 发货客户名称
        String custom_tel = getjson.getString("custom_tel");//发货客户电话
        String send_mechanism = getjson.getString("send_mechanism");//受理机构
        String end_mechanism = getjson.getString("end_mechanism");//终到机构
        String shipping_order_state = getjson.getString("shipping_order_state");//订单状态
        String order_type = getjson.getString("order_type");//订单类型
        User user = gson.fromJson(userInfo, User.class);
        int page1 = 0;
        int row1 = 0;

        try {
            if (page != null && !"".equals(page)) {
                page1 = Integer.parseInt(page);
            }
            if (row != null && !"".equals(row)) {
                row1 = Integer.parseInt(row);
            }
            List<ReverserOrderModel> list = app_sercice.appGetSignReverseOrderInfo(user, row1, page1, send_time, end_time, shiping_order_num, shipping_order_state, order_type, send_mechanism, end_address, end_mechanism);
            returnjson.put("successMsg", list);
        } catch (Exception e) {
            returnjson.put("errorMsg", "出错了");
            log.error("AppController:" + e.getCause().getClass() + ","
                    + e.getCause().getMessage(), e);
        }
        return returnjson.toString();
    }


    /**
     * APP 逆向物流签收
     *
     * @param json
     * @return
     */
    @RequestMapping(params = "method=appSignReverseOrder")
    public
    @ResponseBody
    String appSignReverseOrder(String json, HttpServletRequest request) {
        Gson gson = new Gson();
        JSONObject getjson = JSONObject.fromObject(json);
        JSONObject returnjson = new JSONObject();
        String sign_order = getjson.getString("sign_order");// 获取json签收信息
        String userInfo = getjson.getString("userInfo");// 获取json用户信息
        Sign_order singOrder = gson.fromJson(sign_order, Sign_order.class);
        User user = gson.fromJson(userInfo, User.class);
        String newFileName = getjson.getString("newFileName");
        String fileRoot = "singReverseOrder";
        boolean b = false;
        boolean bb = false;
        if (user != null) {
            try {
                if (singOrder != null) {
                    singOrder.setSign_id(UUIDUtils.getUUID());
                    singOrder.setSign_username(user.getRealName());
                }
                b = app_sercice
                        .appCheckReverseOrderNum(singOrder.getOrder_id());
                if (b) {

                    bb = app_sercice.appSaveSignReverseOrder(singOrder);

                } else {
                    bb = app_sercice.appUpdateSignReverseOrder(singOrder);

                }
                if (bb) {
                    boolean ub = app_sercice
                            .appUpdateReverseOrderState(singOrder.getOrder_id());
                    if (newFileName != null && !newFileName.equals("")) {
                        String[] images = newFileName.split(",");
                        List<ShipperOrderImg> shiOrdImg = new ArrayList<ShipperOrderImg>();
                        for (int i = 0; i < images.length; i++) {
                            if ("".equals(images[i]))
                                continue;
                            String imageUrl = getPath(request, images[i],
                                    fileRoot);
                            ShipperOrderImg sOrdImg = new ShipperOrderImg();
                            sOrdImg.setImage_id(UUIDUtils.getUUID());
                            sOrdImg.setOrder_id(singOrder.getOrder_id());
                            sOrdImg.setImageUrl(imageUrl);
                            shiOrdImg.add(sOrdImg);
                        }
                        app_sercice.appSaveSignReverseOrderImage(shiOrdImg);
                    }

                    if (ub) {
                        saveHistory(singOrder.getOrder_id(),
                                singOrder.getOrder_number(), user.getRealName() + "项目部已签收，签收人:"
                                        + user.getUsername(), "2");
                        returnjson.put("successMsg", "逆向物流,订单签收成功");

                    } else {
                        returnjson.put("errorMsg", "逆向物流，订单状态更新失败");
                    }

                }

            } catch (Exception e) {
                returnjson.put("errorMsg", "出错了");
                log.error("AppController:" + e.getCause().getClass() + ","
                        + e.getCause().getMessage(), e);
            }
        }

        return returnjson.toString();
    }

    /**
     * APP 省市县的获取
     *
     * @param json
     * @return
     */
    @RequestMapping(params = "method=appGetCityInfo")
    public
    @ResponseBody
    String appGetCityInfo(String json) {
        JSONObject getjson = JSONObject.fromObject(json);
        JSONObject returnjson = new JSONObject();
        String citye_parent_id = getjson.getString("citye_parent_id");// 获取json签收信息

        try {
            List<City_info> list = app_sercice.appGetCityInfo(citye_parent_id);
            returnjson.put("successMsg", list);
        } catch (Exception e) {
            returnjson.put("errorMsg", "出错了");
            log.error("AppController:" + e.getCause().getClass() + ","
                    + e.getCause().getMessage());
        }

        return returnjson.toString();
    }

    /**
     * APP 查询订单信息节点信息
     *
     * @param json
     * @return
     */
    @RequestMapping(params = "method=appGetOrderMessage")
    public
    @ResponseBody
    String appGetOrderMessage(String json) {
        JSONObject getjson = JSONObject.fromObject(json);
        JSONObject returnjson = new JSONObject();
        String order_id = getjson.getString("order_id");// 获取json运单id
        String type = getjson.getString("type");// 1 正向，2逆向
        try {
            List<OrderHistory> list = app_sercice.appGetOrderHistory(order_id,
                    type);
            returnjson.put("successMsg", list);
        } catch (Exception e) {
            returnjson.put("errorMsg", "出错了");
            log.error("AppController:" + e.getCause().getClass() + ","
                    + e.getCause().getMessage());
        }

        return returnjson.toString();
    }

    /**
     * APP 查询订单签收图片信息
     *
     * @param json
     * @return
     */
    @RequestMapping(params = "method=appToOrderImagesJsp")
    public String appToOrderImagesJsp(String json, HttpServletRequest request) {
        JSONObject getjson = JSONObject.fromObject(json);
        JSONObject returnjson = new JSONObject();
        String order_id = getjson.getString("order_id");// 获取json运单id
        String type = getjson.getString("type");// 1 正向，2逆向,3异常签收
        returnjson.put("order_id", order_id);
        returnjson.put("type", type);
        request.setAttribute("message", returnjson.toString());
        return "app/appImage";
    }

    /**
     * app 跳转地图页面信息
     *
     * @param json
     * @param request
     * @return
     */
    @RequestMapping(params = "method=appToOrderMapInfo")
    public String appToOrderMapInfo(String json, HttpServletRequest request) {
        JSONObject getjson = JSONObject.fromObject(json);
        JSONObject returnjson = new JSONObject();
        String order_id = getjson.getString("order_id");// 获取json运单id
        //String type = getjson.getString("type");// 1 正向，2逆向,3异常签收
        returnjson.put("order_id", order_id);
        // returnjson.put("type", type);
        request.setAttribute("order_id", order_id);
        return "app/appOrderInfoMap";
    }


    /**
     * APP 查询订单签收图片信息
     *
     * @param json
     * @return
     */
    @RequestMapping(params = "method=appGetOrderImages")
    public
    @ResponseBody
    String appGetOrderImages(String json) {
        JSONObject getjson = JSONObject.fromObject(json);
        JSONObject returnjson = new JSONObject();
        String order_id = getjson.getString("order_id");// 获取json运单id
        String type = getjson.getString("type");// 1 正向，2逆向，3异常
        try {
            List<ShipperOrderImg> list = app_sercice.appGetOrderImages(
                    order_id, type);
            returnjson.put("successMsg", list);
        } catch (Exception e) {
            returnjson.put("errorMsg", "出错了");
            log.error("AppController:" + e.getCause().getClass() + ","
                    + e.getCause().getMessage());
        }

        return returnjson.toString();
    }

    /**
     * APP 添加司机信息
     *
     * @param json
     * @return
     */
    @RequestMapping(params = "method=appAddDriverInfo")
    public
    @ResponseBody
    String appAddDriverInfo(String json) {
        JSONObject getjson = JSONObject.fromObject(json);
        JSONObject returnjson = new JSONObject();
        String driverInfo = getjson.getString("driverInfo");// 获取json供应商信息
        String userName = getjson.getString("userName");
        Gson gson = new Gson();
        Driver driver = gson.fromJson(driverInfo, Driver.class);
        try {
            if (driver != null) {
                driver.setDriver_id(UUIDUtils.getUUID());
                driver.setDriver_updatepeople(userName);
                boolean b = app_sercice.appSaveDriver(driver);
                if (b) {
                    boolean bb = app_sercice.appsaveDriverCityInfo(driver);
                    if (bb) {
                        returnjson.put("successMsg", "司机信息,添加成功");
                    } else {
                        returnjson.put("errorMsg", "司机信息，终到位置添加失败");
                    }

                } else {
                    returnjson.put("errorMsg", "司机信息，添加失败");
                }
            }

        } catch (Exception e) {
            returnjson.put("errorMsg", "出错了");
            log.error("AppController:" + e.getCause().getClass() + ","
                    + e.getCause().getMessage());
        }

        return returnjson.toString();
    }

    /**
     * APP 更新司机信息
     *
     * @param json
     * @return
     */
    @RequestMapping(params = "method=appUpdateDriverInfo")
    public
    @ResponseBody
    String appUpdateDriverInfo(String json) {
        JSONObject getjson = JSONObject.fromObject(json);
        JSONObject returnjson = new JSONObject();
        String driverInfo = getjson.getString("driverInfo");// 获取json供应商信息
        String userName = getjson.getString("userName");
        Gson gson = new Gson();
        Driver driver = gson.fromJson(driverInfo, Driver.class);
        try {
            if (driver != null) {
                driver.setDriver_updatepeople(userName);
                boolean b = app_sercice.appUpdateDriver(driver);
                if (b) {
                    String[] did = new String[1];
                    did[0] = driver.getDriver_id();
                    dsi.deleteDriverCityInfo(did);
                    boolean bb = app_sercice.appsaveDriverCityInfo(driver);
                    if (bb) {
                        returnjson.put("successMsg", "司机信息，更新成功");
                    } else {
                        returnjson.put("errorMsg", "司机信息，终到位置更新失败");
                    }

                } else {
                    returnjson.put("errorMsg", "司机信息，更新失败");
                }
            }

        } catch (Exception e) {
            returnjson.put("errorMsg", "出错了");
            log.error("AppController:" + e.getCause().getClass() + ","
                    + e.getCause().getMessage());
        }

        return returnjson.toString();
    }

    /**
     * APP 添加供应商信息
     *
     * @param json
     * @return
     */
    @RequestMapping(params = "method=appAddSupplersInfo")
    public
    @ResponseBody
    String appAddSupplersInfo(String json) {
        JSONObject getjson = JSONObject.fromObject(json);
        JSONObject returnjson = new JSONObject();
        String suppliers = getjson.getString("suppliers");// 获取json供应商信息
        String userName = getjson.getString("userName");
        Gson gson = new Gson();
        JySuppliers jySuppliers = gson.fromJson(suppliers, JySuppliers.class);

        try {
            if (jySuppliers != null) {
                jySuppliers.setSuppliersId(UUIDUtils.getUUID());
                jySuppliers.setSuppliersOperator(userName);

                boolean b = app_sercice.appSaveSuppliers(jySuppliers);
                if (b) {
                    boolean bb = app_sercice
                            .appSaveSuppliersCityInfo(jySuppliers);
                    if (bb) {
                        returnjson.put("successMsg", "供应商信息,添加成功");
                        Driver d = new Driver();
                        d.setDriver_id(jySuppliers.getSuppliersId());
                        d.setDriver_name(jySuppliers.getSuppliersName());
                        d.setDriver_phone(jySuppliers.getSuppliersPhone());
                        d.setDriver_cardnumber(jySuppliers
                                .getSuppliers_carnumber());
                        d.setDriver_updatepeople(jySuppliers
                                .getSuppliersOperator());
                        d.setDriver_updatedate(jySuppliers
                                .getSuppliersOperatorDate());
                        d.setDriver_suppliers(jySuppliers.getSuppliersId());
                        d.setDriver_address(jySuppliers.getSuppliersAddress());
                        d.setDriver_carnumber(jySuppliers
                                .getSuppliers_carnumber());
                        d.setDriver_cartype(jySuppliers.getSuppliers_cartype());
                        d.setDriver_phonebrand(jySuppliers.getPhone_brand());
                        d.setDriver_phonemodel(jySuppliers.getPhone_model());
                        d.setDriver_province(jySuppliers.getDriver_province());
                        d.setDriver_city(jySuppliers.getDriver_city());
                        d.setDriver_countys(jySuppliers.getSuppers_county());
                        d.setDriver_countyname(jySuppliers
                                .getDriver_countyname());
                        d.setDriver_updatepeople(userName);
                        app_sercice.appSaveDriver(d);
                        app_sercice.appsaveDriverCityInfo(d);

                    } else {
                        returnjson.put("errorMsg", "供应商信息,终到位置添加失败");
                    }
                } else {
                    returnjson.put("errorMsg", "供应商信息,添加失败");
                }
            }

        } catch (Exception e) {
            returnjson.put("errorMsg", "出错了");
            log.error("AppController:" + e.getCause().getClass() + ","
                    + e.getCause().getMessage());
        }

        return returnjson.toString();
    }

    /**
     * APP 更新供应商信息
     *
     * @param json
     * @return
     */
    @RequestMapping(params = "method=appUpdateSupplersInfo")
    public
    @ResponseBody
    String appUpdateSupplersInfo(String json) {
        JSONObject getjson = JSONObject.fromObject(json);
        JSONObject returnjson = new JSONObject();
        String suppliers = getjson.getString("suppliers");// 获取json供应商信息
        String userName = getjson.getString("userName");
        Gson gson = new Gson();
        JySuppliers jySuppliers = gson.fromJson(suppliers, JySuppliers.class);

        try {
            if (jySuppliers != null) {
                jySuppliers.setSuppliersOperator(userName);
                boolean b = app_sercice.appUpdateSuppliers(jySuppliers);
                if (b) {
                    String[] sId = new String[1];
                    sId[0] = jySuppliers.getSuppliersId();
                    suppliersService.deleteSuppliersCityInfo(sId);
                    boolean bb = app_sercice
                            .appSaveSuppliersCityInfo(jySuppliers);
                    if (bb) {
                        returnjson.put("successMsg", "供应商信息,更新成功");
                        Driver d = new Driver();
                        d.setDriver_id(jySuppliers.getSuppliersId());
                        d.setDriver_name(jySuppliers.getSuppliersName());
                        d.setDriver_phone(jySuppliers.getSuppliersPhone());
                        d.setDriver_cardnumber(jySuppliers
                                .getSuppliers_carnumber());
                        d.setDriver_updatepeople(jySuppliers
                                .getSuppliersOperator());
                        d.setDriver_updatedate(jySuppliers
                                .getSuppliersOperatorDate());
                        d.setDriver_suppliers(jySuppliers.getSuppliersId());
                        d.setDriver_address(jySuppliers.getSuppliersAddress());
                        d.setDriver_carnumber(jySuppliers
                                .getSuppliers_carnumber());
                        d.setDriver_cartype(jySuppliers.getSuppliers_cartype());
                        d.setDriver_phonebrand(jySuppliers.getPhone_brand());
                        d.setDriver_phonemodel(jySuppliers.getPhone_model());
                        d.setDriver_countys(jySuppliers.getSuppers_county());
                        d.setDriver_countyname(jySuppliers
                                .getDriver_countyname());
                        d.setDriver_updatepeople(userName);
                        app_sercice.appUpdateDriver(d);
                        String[] did = new String[1];
                        did[0] = d.getDriver_id();
                        dsi.deleteDriverCityInfo(did);
                        app_sercice.appsaveDriverCityInfo(d);
                    } else {
                        returnjson.put("errorMsg", "供应商信息,终到位置添加失败");
                    }
                } else {
                    returnjson.put("errorMsg", "供应商信息,添加失败");
                }
            }

        } catch (Exception e) {
            returnjson.put("errorMsg", "出错了");
            log.error("AppController:" + e.getCause().getClass() + ","
                    + e.getCause().getMessage());
        }

        return returnjson.toString();
    }

    /**
     * APP 查询供应商商信息
     *
     * @param json
     * @return
     */
    @RequestMapping(params = "method=appAddDriverSupplersInfo")
    public
    @ResponseBody
    String appAddDriverSupplersInfo(String json) {
        // Gson gson = new Gson();
        JSONObject getjson = JSONObject.fromObject(json);
        JSONObject returnjson = new JSONObject();
        // String userInfo = getjson.getString("userInfo");// 获取json用户信息
        String supplersName = getjson.getString("supplersName");// 供应商姓名
        // User user = gson.fromJson(userInfo, User.class);
        try {
            List<JySuppliers> list = dsi.getSuppliers(supplersName);
            returnjson.put("successMsg", list);
        } catch (Exception e) {
            returnjson.put("errorMsg", "出错了");
            log.error("AppController:" + e.getCause().getClass() + ","
                    + e.getCause().getMessage());
        }
        return returnjson.toString();
    }

    /**
     * APP 司机信息列表
     *
     * @param json
     * @return
     */
    @RequestMapping(params = "method=appGetDriversInfo")
    public
    @ResponseBody
    String appGetDriversInfo(String json) {
        Gson gson = new Gson();
        JSONObject getjson = JSONObject.fromObject(json);
        JSONObject returnjson = new JSONObject();
        String userInfo = getjson.getString("userInfo");// 获取json用户信息
        String row = getjson.getString("row");// 几行开始
        String page = getjson.getString("page");// 显示多少个
        String driver_name = getjson.getString("driver_name");// 司机姓名
        String driver_suppliers = getjson.getString("driver_suppliers");// 所属供应商
        String driver_phone = getjson.getString("driver_phone");// 司机电话
        String driver_cardnumber = getjson.getString("driver_cardnumber");// 身份证号
        String driver_address = getjson.getString("driver_address");// 终到位置

        // String send_mechanism = getjson.getString("send_mechanism");// 受理机构
        // String receipt_name = getjson.getString("receipt_name");// 到货联系人
        // String receipt_tel = getjson.getString("receipt_tel");// 到货联系人电话
        // String shiping_order_goid =
        // getjson.getString("shiping_order_goid");// 出货订单号
		/*
		 *
		 * topordernumber = getjson.getString("topordernumber");// String
		 * downordernumber = getjson.getString("downordernumber");// 发运结束时间
		 */
        User user = gson.fromJson(userInfo, User.class);
        int page1 = 0;
        int row1 = 0;

        try {
            if (page != null && !"".equals(page)) {
                page1 = Integer.parseInt(page);
            }
            if (row != null && !"".equals(row)) {
                row1 = Integer.parseInt(row);
            }
            List<Driver> list = dsi.getDriverInfo(row1, page1, driver_name,
                    driver_suppliers, driver_phone, driver_cardnumber,
                    driver_address, null, null, user.getId(), user);
            returnjson.put("successMsg", list);

        } catch (Exception e) {
            returnjson.put("errorMsg", "出错了");
            log.error("AppController:" + e.getCause().getClass() + ","
                    + e.getCause().getMessage());
        }

        return returnjson.toString();
    }

    /**
     * APP 供应商信息列表
     *
     * @param json
     * @return
     */
    @RequestMapping(params = "method=appGetSupplersListInfo")
    public
    @ResponseBody
    String appGetSupplersListInfo(String json) {
        Gson gson = new Gson();
        JSONObject getjson = JSONObject.fromObject(json);
        JSONObject returnjson = new JSONObject();
        String userInfo = getjson.getString("userInfo");// 获取json用户信息
        String row = getjson.getString("row");// 几行开始
        String page = getjson.getString("page");// 显示多少个
        String name = getjson.getString("name");// 姓名
        String phone = getjson.getString("phone");// 电话
        String address = getjson.getString("address");// 终到位置
		/*
		 * String driver_cardnumber = getjson.getString("driver_cardnumber");//
		 * 身份证号 String driver_address = getjson.getString("driver_address");//
		 * 终到位置
		 */// String send_mechanism = getjson.getString("send_mechanism");//
        // 受理机构
        // String receipt_name = getjson.getString("receipt_name");// 到货联系人
        // String receipt_tel = getjson.getString("receipt_tel");// 到货联系人电话
        // String shiping_order_goid =
        // getjson.getString("shiping_order_goid");// 出货订单号
		/*
		 *
		 * topordernumber = getjson.getString("topordernumber");// String
		 * downordernumber = getjson.getString("downordernumber");// 发运结束时间
		 */
        User user = gson.fromJson(userInfo, User.class);
        int page1 = 0;
        int row1 = 0;

        try {
            if (page != null && !"".equals(page)) {
                page1 = Integer.parseInt(page);
            }
            if (row != null && !"".equals(row)) {
                row1 = Integer.parseInt(row);
            }
            List<JySuppliers> list = suppliersService.getSuppUser(row1, page1,
                    name, phone, address, user.getDriver_id(), null, user);
            returnjson.put("successMsg", list);

        } catch (Exception e) {
            returnjson.put("errorMsg", "出错了");
            log.error("AppController:" + e.getCause().getClass() + ","
                    + e.getCause().getMessage());
        }

        return returnjson.toString();
    }

    /**
     * APP 订单查询信息
     *
     * @param json
     * @return
     */
    @RequestMapping(params = "method=appSearchShipperOrderInfo")
    public
    @ResponseBody
    String appSearchShipperOrderInfo(String json) {
        Gson gson = new Gson();
        JSONObject getjson = JSONObject.fromObject(json);
        JSONObject returnjson = new JSONObject();
        String userInfo = getjson.getString("userInfo");// 获取json用户信息
        String row = getjson.getString("row");// 几行开始
        String page = getjson.getString("page");// 显示多少个
        String send_time = getjson.getString("send_time");// 发运时间
        String shiping_order_num = getjson.getString("shiping_order_num");// 订单号
        String send_mechanism = getjson.getString("send_mechanism");// 受理机构
        String end_address = getjson.getString("end_address");// 终到位置
        // String custom_name = getjson.getString("custom_name");// 发货客户姓名
        String receipt_name = getjson.getString("receipt_name");// 到货联系人
        // String receipt_tel = getjson.getString("receipt_tel");// 到货联系人电话
        String shiping_order_goid = getjson.getString("shiping_order_goid");// 出货订单号
        String end_time = getjson.getString("end_time");// 发运结束时间
        // String topordernumber = getjson.getString("topordernumber");//
        // String downordernumber = getjson.getString("downordernumber");//
        // 发运结束时间
        String state = getjson.getString("state2");// 运单状态0 未签收，1签收 空全部
        User user = gson.fromJson(userInfo, User.class);
        int page1 = 0;
        int row1 = 0;
        if (user != null) {
            try {
                if (page != null && !"".equals(page)) {
                    page1 = Integer.parseInt(page);
                }
                if (row != null && !"".equals(row)) {
                    row1 = Integer.parseInt(row);
                }
                List<ShippingOrder> list = csi.getSignShipOrder(row1, page1,
                        shiping_order_num, send_time, end_time, state,
                        send_mechanism, receipt_name, end_address,
                        shiping_order_goid, "", "", user);

                returnjson.put("successMsg", list);

                // returnjson.put("errorMsg", "用户信息添加失败");

            } catch (Exception e) {
                returnjson.put("errorMsg", "出错了");
                log.error("AppController:" + e.getCause().getClass() + ","
                        + e.getCause().getMessage());
            }
        }
        return returnjson.toString();
    }

    /**
     * APP 获取信息列表
     *
     * @param json
     * @return
     */
    @RequestMapping(params = "method=appGetMsgInfo")
    public
    @ResponseBody
    String appGetMsgInfo(String json) {
        Gson gson = new Gson();
        JSONObject getjson = JSONObject.fromObject(json);
        JSONObject returnjson = new JSONObject();
        String state = getjson.getString("state");// 获取状态
        String userInfo = getjson.getString("userInfo");// 获取json用户信息
        User user = gson.fromJson(userInfo, User.class);
        if (user != null) {
            try {
                List<MsgModel> list = app_sercice.appGetMsgInfo(user, state);
                returnjson.put("successMsg", list);
            } catch (Exception e) {
                returnjson.put("errorMsg", "出错了");
                log.error("AppController:" + e.getCause().getClass() + ","
                        + e.getCause().getMessage());
            }
        }

        return returnjson.toString();
    }

    /**
     * APP 修改信息状态已读
     *
     * @param json
     * @return
     */
    @RequestMapping(params = "method=appUpdateMsgState")
    public
    @ResponseBody
    String appUpdateMsgState(String json) {
        Gson gson = new Gson();
        JSONObject getjson = JSONObject.fromObject(json);
        JSONObject returnjson = new JSONObject();
        String state = getjson.getString("state");// 获取状态（1，表示未读。2，表示已读）
        String messageId = getjson.getString("messageId");// 获取信息的id
        String userInfo = getjson.containsKey("userInfo") ? getjson
                .getString("userInfo") : "";// 获取json用户信息
        User user = gson.fromJson(userInfo, User.class);
        if (user == null) {
            returnjson.put("successMsg", "请尽快更新APP");
        }

        try {
            int i = app_sercice.appUpdateMsgState(messageId, state,
                    user.getId());
            if (i > 0) {
                log.info("信息阅读成功");
            }

        } catch (Exception e) {
            returnjson.put("errorMsg", "出错了");
            log.error("AppController:" + e.getCause().getClass() + ","
                    + e.getCause().getMessage());
        }

        return returnjson.toString();
    }

    // _______________________________________________

    /**
     * 上传图片
     *
     * @param json
     * @param imgString
     * @param newFileName
     * @param fileRoot
     * @param request
     * @return
     */
    @RequestMapping(params = "method=getUploadImage")
    public
    @ResponseBody
    String getUploadImage(String json, String imgString, String newFileName,
                          String fileRoot, HttpServletRequest request) {
        if (imgString != null) {
            imgString = imgString.replaceAll(" ", "+");
            // System.out.println("新来的！" + imgString.replaceAll(" ", "+"));
        }
        if (fileRoot == null || fileRoot.equals("")) {
            fileRoot = "signorderImages";
        }
        JSONObject returnjson = new JSONObject();
        try {
			/*
			 * String str=getjson.getString("imgString"); String
			 * newFileName=getjson.getString("newFileName");
			 */
            BASE64Decoder decoder = new BASE64Decoder();
            // Base64解码
            byte[] b = decoder.decodeBuffer(imgString);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {// 调整异常数据
                    b[i] += 256;
                }
            }
            // 生成jpeg图片
            String path = request.getSession().getServletContext()
                    .getRealPath("WebRoot/" + fileRoot + "/");// 文件路径
            File filez = new File(path);
            if (!filez.exists()) {
                filez.mkdirs();
            }
            String paths = path + "\\" + newFileName;// 新生成的图片
            OutputStream out = new BufferedOutputStream(new FileOutputStream(
                    paths));
            out.write(b);
            out.flush();
            out.close();
            returnjson.put("successMsg", "成功");
            return returnjson.toString();
        } catch (Exception e) {
            returnjson.put("errorMsg", "出错了");
            return returnjson.toString();
        }
    }

    /**
     * 获取图片路径
     *
     * @param request
     * @param fileName
     * @param fileRoot
     * @return
     */
    private String getPath(HttpServletRequest request, String fileName,
                           String fileRoot) {
        // 生成jpeg图片]
        String filePath = "WebRoot/" + fileRoot + "/";
        String path = request.getSession().getServletContext()
                .getRealPath(filePath);// 文件路径
        File filez = new File(path);
        if (!filez.exists()) {
            filez.mkdirs();
        }

        String newpath = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName()
                + ":" + request.getServerPort() + newpath + "/";
        String paths = basePath + filePath + fileName;
        return paths;
    }

    /**
     * 保存历史记录
     *
     * @param ids
     * @param num
     * @param message
     */
    public void saveHistory(String ids, String num, String message, String type) {
        List<OrderHistory> order = new ArrayList<OrderHistory>();
        OrderHistory h = new OrderHistory();
        h.setOrder_history_id(UUIDUtils.getUUID());
        h.setOrders_id(ids);
        h.setOrder_arrive_time(DateFormat.getDateTimeInstance().format(
                new Date()));
        h.setGoods_arrive_remakes(h.getOrder_arrive_time() + "---货运编号为:" + num
                + "\t" + message);
        order.add(h);
        if (type.equals("1")) {// 1是正向
            csi.saveOrderHistory(order);
        } else if (type.equals("2")) {// 2是逆向
            ros.saveReverserOrderHistoryList(order);
        }

    }

    /**
     * 请求url 获取返回数据
     *
     * @param url
     * @return
     */
    public static String loadJSON(String url) {
        StringBuilder json = new StringBuilder();
        try {
            URL oracle = new URL(url);
            URLConnection yc = oracle.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    yc.getInputStream()));
            String inputLine = null;
            while ((inputLine = in.readLine()) != null) {
                json.append(inputLine);
            }
            in.close();
        } catch (MalformedURLException e) {
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json.toString();
    }

    /**
     * APP 更新个人信息
     *
     * @param json
     * @return
     */
    @RequestMapping(params = "method=appUpdatePersonInfo")
    public
    @ResponseBody
    String appUpdatePersonInfo(String json) {
        Gson gson = new Gson();
        JSONObject getjson = JSONObject.fromObject(json);
        JSONObject returnjson = new JSONObject();
        String userInfo = getjson.getString("userInfo");// 获取json用户信息
        String phone = getjson.getString("phone");
        User user = gson.fromJson(userInfo, User.class);
        try {

            int i = app_sercice.appUpdatePersonInfo(user, phone);
            if (i > 0) {
                returnjson.put("successMsg", "个人信息修改成功");
            } else {
                returnjson.put("errorMsg", "个人信息修改失败");
            }

        } catch (Exception e) {
            returnjson.put("errorMsg", "出错了");
            log.error("AppController:" + e.getCause().getClass() + ","
                    + e.getCause().getMessage());
        }
        return returnjson.toString();
    }

    /**
     * APP 发送消息获取联系人
     *
     * @param json
     * @return
     */
    @RequestMapping(params = "method=appGetMessagePeople")
    public
    @ResponseBody
    String appGetMessagePeople(String json) {
        Gson gson = new Gson();
        JSONObject getjson = JSONObject.fromObject(json);
        JSONObject returnjson = new JSONObject();
        String userInfo = getjson.getString("userInfo");// 获取json用户信息
        String row = getjson.getString("row");// 几行开始
        String page = getjson.getString("page");// 显示多少个
        String realname = getjson.getString("realname");// 真实姓名
        String user_address = getjson.getString("user_address");// 终到大区

        Integer page2 = 0;

        Integer rows1 = 0;
        try {
            if (page != null && !"".equals(page)) {
                page2 = Integer.parseInt(page);
            }
            if (row != null && !"".equals(row)) {
                rows1 = Integer.parseInt(row);
            }
            User user = gson.fromJson(userInfo, User.class);
            List<User> list = manage.getMessagePeople(rows1, page2, realname,
                    "", user, user_address);
            returnjson.put("successMsg", list);
        } catch (Exception e) {
            returnjson.put("errorMsg", "出错了");
            log.error("AppController:" + e.getCause().getClass() + ","
                    + e.getCause().getMessage());
        }

        return returnjson.toString();
    }

    /**
     * APP 发送消息获取联系人
     *
     * @param json
     * @return
     */
    @RequestMapping(params = "method=appSaveMessageInfo")
    public
    @ResponseBody
    String appSaveMessageInfo(String json) {
        Gson gson = new Gson();
        JSONObject getjson = JSONObject.fromObject(json);
        JSONObject returnjson = new JSONObject();
        String userInfo = getjson.getString("userInfo");// 获取json用户信息
        String orderMsgTitle = getjson.getString("orderMsgTitle");// 获取标题
        String orderMsg = getjson.getString("orderMsg");// 获取信息内容
        String receiveUser = getjson.getString("receiveUser");// 获取接受客户信息
        try {

            User user = gson.fromJson(userInfo, User.class);
            if (user != null) {
                List<MsgModel> listModels = gson.fromJson(receiveUser,
                        new TypeToken<List<MsgModel>>() {
                        }.getType());
                for (MsgModel msgModel : listModels) {
                    msgModel.setOrderMsgId(UUIDUtils.getUUID());
                    msgModel.setOrderMsg(orderMsg);
                    msgModel.setOrderMsgTitle(orderMsgTitle);
                    msgModel.setOrderMsgState("1");
                    msgModel.setSendUserName(user.getRealName());
                    msgModel.setSendUserId(user.getId());
                }
                if (listModels.size() != 0) {
                    int i = app_sercice.appSaveMessageInfo(listModels);
                    if (i > 0) {
                        returnjson.put("successMsg", "发送成功");
                    } else {
                        returnjson.put("errorMsg", "对不起发送失败");
                    }
                }

            }

        } catch (Exception e) {
            returnjson.put("errorMsg", "出错了");
            log.error("AppController:" + e.getCause().getClass() + ","
                    + e.getCause().getMessage());
        }

        return returnjson.toString();
    }

    /**
     * 获取app版本号
     */
    @RequestMapping(params = "method=getAppVersion")
    public
    @ResponseBody
    String getAppVersion(String json) {
        // JSONObject getjson = JSONObject.fromObject(json);
        JSONObject returnjson = new JSONObject();
        try {
            AppVersion dep = uis.getAppVersion();
            List<String> message = app_sercice.appGetUpdateMessage();
            dep.setMessage(message);
            returnjson.put("successMsg", dep);
            return returnjson.toString();
        } catch (Exception e) {
            returnjson.put("errorMsg", "出错了");
            log.error("AppController:" + e.getCause().getClass() + ","
                    + e.getCause().getMessage());
            return returnjson.toString();
        }
    }

    /**
     * 获取app获取定位时间
     *
     * @param json
     * @return
     */
    @RequestMapping(params = "method=appGetLocationTime")
    public
    @ResponseBody
    String appGetLocationTime(String json) {
        // JSONObject getjson = JSONObject.fromObject(json);
        JSONObject returnjson = new JSONObject();
        try {
            AppVersion appVersion = app_sercice.appGetLocationTime();
            if (appVersion == null)
                new AppVersion().setLocationTime(5);
            returnjson.put("successMsg", appVersion.getLocationTime());

            return returnjson.toString();
        } catch (Exception e) {
            returnjson.put("errorMsg", "出错了");
            log.error("AppController:" + e.getCause().getClass() + ","
                    + e.getCause().getMessage());
            return returnjson.toString();
        }
    }

    /**
     * 获取app是否有gps 有则不提示开启，，否则开启
     *
     * @param json
     * @return
     */
    @RequestMapping(params = "method=appIsHaveGps")
    public
    @ResponseBody
    String appIsHaveGps(String json) {
        JSONObject getjson = JSONObject.fromObject(json);
        JSONObject returnjson = new JSONObject();
        Gson gson = new Gson();
        try {
            String userInfo = getjson.getString("userInfo");// 获取json用户信息
            User user = gson.fromJson(userInfo, User.class);
            int gpsCount = app_sercice.getGpsInfoCount(user.getUsername());
            if (gpsCount > 0) {
                returnjson.put("successMsg", "YES");

                return returnjson.toString();
            }
            returnjson.put("successMsg", "NO");

            return returnjson.toString();
        } catch (Exception e) {
            returnjson.put("errorMsg", "出错了");
            log.error("AppController:" + e.getCause().getClass() + ","
                    + e.getCause().getMessage());
            return returnjson.toString();
        }
    }

    /**
     * APP 设备id更新
     *
     * @param json
     * @return
     */
    @RequestMapping(params = "method=appUpdateChannelId")
    public
    @ResponseBody
    String appUpdateChannelId(String json) {
        JSONObject returnjson = new JSONObject();

        try {
            JSONObject tojson = JSONObject.fromObject(json);

            boolean b = loginUserService.updatechannelId(
                    tojson.getString("channelId"),
                    tojson.getString("device_type"), tojson.getString("id"));
            if (b) {
                returnjson.put("successMsg", "更新设备状态成功");
                return returnjson.toString();
            } else {
                returnjson.put("successMsg", "更新失败，用户信息不存在");
                return returnjson.toString();
            }
        } catch (Exception e) {
            returnjson.put("successMsg", "出错了");
            return returnjson.toString();
        }
    }

    /**
     * APP 管理更加发货客户显示列表
     *
     * @param json
     * @return
     */
    @RequestMapping(params = "method=appAdminSearchCustomerInfo")
    public
    @ResponseBody
    String appAdminSearchCustomerInfo(String json) {
        Gson gson = new Gson();
        JSONObject getjson = JSONObject.fromObject(json);
        JSONObject returnjson = new JSONObject();
        String userInfo = getjson.getString("userInfo");// 获取json用户信息
        String row = getjson.getString("row");// 几行开始
        String page = getjson.getString("page");// 显示多少个
        String customer_name = getjson.getString("customer_name");// 发货客户姓名
        String customer_tel = getjson.getString("customer_tel");// 发货客户联系方式
        String customer_address = getjson.getString("customer_address");// 发货客户地址
        User user = gson.fromJson(userInfo, User.class);
        int page1 = 0;
        int row1 = 0;
        if (user != null) {
            try {
                if (page != null && !"".equals(page)) {
                    page1 = Integer.parseInt(page);
                }
                if (row != null && !"".equals(row)) {
                    row1 = Integer.parseInt(row);
                }
                List<Customer> list = rms.getRemarks(row1, page1,
                        customer_name, customer_tel, customer_address, user);

                returnjson.put("successMsg", list);

            } catch (Exception e) {
                returnjson.put("errorMsg", "出错了");
                log.error("AppController:" + e.getCause().getClass() + ","
                        + e.getCause().getMessage());
            }
        }
        return returnjson.toString();
    }

    /**
     * APP 查询签收信息
     *
     * @param json
     * @return
     */
    @RequestMapping(params = "method=appSearchShipperOrderInfoGroupByRole")
    public
    @ResponseBody
    String appSearchShipperOrderInfoGroupByRole(String json) {

        Gson gson = new Gson();
        JSONObject getjson = JSONObject.fromObject(json);
        JSONObject returnjson = new JSONObject();
        String userInfo = getjson.getString("userInfo");// 获取json用户信息
        String row = getjson.getString("row");// 几行开始
        String page = getjson.getString("page");// 显示多少个
        String selectId = getjson.getString("selectId");// 选择的某一行的id
        String send_time = getjson.getString("send_time");// 发运时间
        String end_time = getjson.getString("end_time");// 发运结束时间
        String shiping_order_num = getjson.getString("shiping_order_num");// 订单号
        String shiping_order_goid = getjson.getString("shiping_order_goid");// 出货订单号
        String send_mechanism = getjson.getString("send_mechanism");// 受理机构
        String end_address = getjson.getString("end_address");// 终到位置
        // String custom_name = getjson.getString("custom_name");// 发货客户姓名
        String receipt_name = getjson.getString("receipt_name");// 到货联系人
        String receipt_tel = getjson.getString("receipt_tel");// 到货联系人电话
        String end_mechanism = getjson.containsKey("end_mechanism") ? getjson
                .getString("end_mechanism") : "";// 客户详细目的地

        String type = getjson.containsKey("type") ? getjson.getString("type")
                : "0";// 是否显示签收(0 不显示,1显示)
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        // 获取当前月第一天：
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
        String first = format.format(c.getTime());
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        String last = format.format(c.getTime());// 获取当前月最后一天
        User user = gson.fromJson(userInfo, User.class);
        send_time = send_time == null || send_time.equals("") ? first : send_time;
        end_time = end_time == null || end_time.equals("") ? last : end_time;
        int page1 = 0;
        int row1 = 0;
        String sign = "1";
        String nosign = "0";
        int signCount = 0;
        int nosignCount = 0;
        List<ShippingOrder> list = new ArrayList<ShippingOrder>();
        if (user != null) {
            JSONObject retJson = new JSONObject();
            try {

                if (page != null && !"".equals(page)) {
                    page1 = Integer.parseInt(page);
                }
                if (row != null && !"".equals(row)) {
                    row1 = Integer.parseInt(row);
                }
                signCount = csi
                        .getSignShipOrdercount(shiping_order_num,
                                send_time, end_time, sign, send_mechanism,
                                receipt_name, end_address,
                                shiping_order_goid, "", "", user);
                nosignCount = csi
                        .getSignShipOrdercount(shiping_order_num,
                                send_time, end_time, nosign,
                                send_mechanism, receipt_name, end_address,
                                shiping_order_goid, "", "", user);
                list = csi
                        .getSignShipOrder(row1, page1, shiping_order_num,
                                send_time, end_time, type, send_mechanism,
                                receipt_name, end_address,
                                shiping_order_goid, "", "", user);
                retJson.put("signCount", signCount);
                retJson.put("nosignCount", nosignCount);
                retJson.put("list", list);

                returnjson.put("successMsg", retJson);

            } catch (Exception e) {
                returnjson.put("errorMsg", "出错了");
                log.error("AppController:" + e.getCause().getClass() + ","
                        + e.getCause().getMessage());
            }
        }
        return returnjson.toString();
    }


    /**
     * APP 查询运单调整信息列表
     *
     * @param json
     * @return json
     */
    @RequestMapping(params = "method=appGetWaybillAdjustmentListInfo")
    public
    @ResponseBody
    String appGetWaybillAdjustmentListInfo(String json) {
        Gson gson = new Gson();
        JSONObject getjson = JSONObject.fromObject(json);
        JSONObject returnjson = new JSONObject();
        String row = getjson.getString("row");// 几行开始
        String page = getjson.getString("page");// 显示多少个
        String start_time = getjson.getString("start_time");//申请时间
        String end_time = getjson.getString("end_time");//结束时间
        String shiping_order_num = getjson.getString("shiping_order_num");//订单号
        String shiping_order_goid = getjson.getString("shiping_order_goid");//出货订单号
        String applicant_state = getjson.getString("applicant_state");//申请状态是否处理（0否，1是）
        String applicant_type = getjson.getString("applicant_type");//申请类型（0运单调整，1取消签收）
        String send_mechanism = getjson.getString("send_mechanism");//受理机构
        String end_address = getjson.getString("end_address");//终到位置
        String receipt_name = getjson.getString("receipt_name");//收货客户姓名
        String userInfo = getjson.getString("userInfo");//用户信息
        User user = gson.fromJson(userInfo, User.class);
        int page1 = 0;
        int row1 = 0;
        if (user != null) {
            try {
                if (page != null && !"".equals(page)) {
                    page1 = Integer.parseInt(page);
                }
                if (row != null && !"".equals(row)) {
                    row1 = Integer.parseInt(row);
                }
                int noApplicantCount = waybillAdjustmentService.getCountWaybillAdjustment(user, start_time, end_time, shiping_order_num, shiping_order_goid, "0", applicant_type, send_mechanism, end_address, receipt_name);
                int applicantCount = waybillAdjustmentService.getCountWaybillAdjustment(user, start_time, end_time, shiping_order_num, shiping_order_goid, "1", applicant_type, send_mechanism, end_address, receipt_name);


                List<WaybillAdjustment> list = waybillAdjustmentService.
                        getWaybillAdjustmentListInfo(user, row1, page1, start_time, end_time, shiping_order_num, shiping_order_goid, applicant_state, applicant_type, send_mechanism, end_address, receipt_name);
                returnjson.put("successMsg", list);
                returnjson.put("noApplicantCount", noApplicantCount);
                returnjson.put("applicantCount", applicantCount);
            } catch (Exception e) {
                returnjson.put("errorMsg", "出错了");
                log.error("AppController:" + e.getCause().getClass() + ","
                        + e.getCause().getMessage(), e);
            }
        }
        return returnjson.toString();
    }

    /**
     * APP 运单调整信息添加
     *
     * @param json
     * @return
     */
    @RequestMapping(params = "method=appAddWaybillAdjustmentInfo")
    public
    @ResponseBody
    String appAddWaybillAdjustmentInfo(String json) {
        Gson gson = new Gson();
        JSONObject getjson = JSONObject.fromObject(json);
        JSONObject returnjson = new JSONObject();
        String orderData = getjson.getString("orderData");//调整之后的运单信息
        String userInfo = getjson.getString("userInfo");//用户信息
        String applicant_message = getjson.getString("applicant_message");//调整信息原因
        int applicant_type = 0;//运单调整
        String changeMessage = getjson.getString("changeMessage");//json调整的字段信息bean
        String shiping_order_num = getjson.getString("shiping_order_num");
        User user = gson.fromJson(userInfo, User.class);
        try {
            WaybillAdjustmentOrder waybillAdjustmentOrder = gson.fromJson(orderData, WaybillAdjustmentOrder.class);

            String orderId = waybillAdjustmentOrder.getShiping_order_id();

            WaybillAdjustment waybillAdjustment = WaybillAdjustmentUtils.getWaybillAdjustment(user, applicant_message, applicant_type, orderId);
            int saveWaybillAdjustmentInfo = waybillAdjustmentService.saveWaybillAdjustmentInfo(waybillAdjustment);
            boolean b = true;
            if (saveWaybillAdjustmentInfo > 0) {
                String waybillAdjustmentId = waybillAdjustment.getId();
                waybillAdjustmentOrder.setId(UUIDUtils.getUUID());
                waybillAdjustmentOrder.setWaybill_adjustment_id(waybillAdjustmentId);
                int booleanSaveWaybillAdjustment = waybillAdjustmentService.saveWaybillAdjustment(waybillAdjustmentOrder);
                List<WaybillAdjustmentHistory> waybillAdjustmentHistoryList = gson.fromJson(changeMessage, new TypeToken<List<WaybillAdjustmentHistory>>() {
                }.getType());
                int waybillAdjustmentHistoryList1 = waybillAdjustmentService.saveWaybillAdjustmentHistoryList(waybillAdjustmentId, waybillAdjustmentHistoryList);
                b = booleanSaveWaybillAdjustment > 0 && waybillAdjustmentHistoryList1 > 0;
                waybillAdjustmentService.updateOrderState(orderId, "1");
                HistoryUtils.saveHistory(orderId, shiping_order_num, "申请运单调整");
            }


            returnjson.put("successMsg", b ? "运单信息调整成功,请等待审核" : "运单信息调整失败");

        } catch (Exception e) {
            returnjson.put("errorMsg", "出错了");
            log.error("AppController:" + e.getCause().getClass() + ","
                    + e.getCause().getMessage());
        }
        return returnjson.toString();
    }

    /**
     * APP 取消签收信息添加
     *
     * @param json
     * @return
     */
    @RequestMapping(params = "method=appAddCancelSignInfo")
    public
    @ResponseBody
    String appAddCancelSignInfo(String json) {
        Gson gson = new Gson();
        JSONObject getjson = JSONObject.fromObject(json);
        JSONObject returnjson = new JSONObject();
        String applicant_message = getjson.getString("applicant_message");
        String userInfo = getjson.getString("userInfo");//用户信息
        String shiping_order_id = getjson.getString("shiping_order_id");//订单id
        String shiping_order_num = getjson.getString("shiping_order_num");//订单号
        try {
            User user = gson.fromJson(userInfo, User.class);
            WaybillAdjustment waybillAdjustment = WaybillAdjustmentUtils.getWaybillAdjustment(user, applicant_message, 1, shiping_order_id);
            int saveWaybillAdjustmentInfo = waybillAdjustmentService.saveWaybillAdjustmentInfo(waybillAdjustment);
            boolean b = saveWaybillAdjustmentInfo > 0;
            if (b) {
                waybillAdjustmentService.updateOrderState(shiping_order_id, "2");
            }
            returnjson.put("successMsg", b ? "取消签收成功" : "取消签收失败");
            HistoryUtils.saveHistory(shiping_order_id, shiping_order_num, "申请取消签收");
        } catch (Exception e) {
            returnjson.put("errorMsg", "出错了");
            log.error("AppController:" + e.getCause().getClass() + ","
                    + e.getCause().getMessage());
        }


        return returnjson.toString();
    }

    /**
     * 审核信息运单调整
     *
     * @param json
     * @return
     */
    @RequestMapping(params = "method=appDoWaybillAuditInfo")
    public
    @ResponseBody
    String appDoWaybillAuditInfo(String json) {
        Gson gson = new Gson();
        JSONObject getjson = JSONObject.fromObject(json);
        JSONObject returnjson = new JSONObject();
        String userInfo = getjson.getString("userInfo");//用户信息
        User user = gson.fromJson(userInfo, User.class);
        String id = getjson.getString("waybillAdjustmentId");//审核id
        String auditor_message = getjson.getString("auditor_message");//审核信息
        String shiping_order_id = getjson.getString("shiping_order_id");//订单id
        String applicant_type = getjson.getString("applicant_type");//调整类型
        String shiping_order_num = getjson.getString("shiping_order_num");//订单号
        try {
            WaybillAdjustment waybillAdjustment = new WaybillAdjustment();
            waybillAdjustment.setAuditor_message(auditor_message);
            waybillAdjustment.setAuditor_id(user.getId());
            waybillAdjustment.setAuditor_name(user.getRealName());
            waybillAdjustment.setAuditor_username(user.getUsername());
            waybillAdjustment.setShiping_order_id(shiping_order_id);
            waybillAdjustment.setId(id);
            waybillAdjustment.setApplicant_state(1);
            boolean b = false;
            if (applicant_type.equals("1")) {
                b = WaybillAdjustmentUtils.doCancelSign(shiping_order_id);
            } else if (applicant_type.equals("0")) {
                b = WaybillAdjustmentUtils.doUpdateOderMessage(id, shiping_order_id);
            }
            if (b) {
                int i = waybillAdjustmentService.doWaybillAuditInfo(waybillAdjustment);
                HistoryUtils.saveHistory(shiping_order_id, shiping_order_num, "运单调整审核通过");
                returnjson.put("successMsg", i > 0 ? "审核成功" : "审核失败");
            }
        } catch (Exception e) {
            returnjson.put("errorMsg", "出错了");
            log.error("AppController:" + e.getCause().getClass() + ","
                    + e.getCause().getMessage());
        }
        return returnjson.toString();
    }


    /**
     * 查询司机司机地图接口
     *
     * @param json
     * @return
     */
    @ResponseBody
    @RequestMapping(params = "method=getMapDriverListInfo")
    public String getMapDriverListInfo(String json) {
        Gson gson = new Gson();
        JSONObject getjson = JSONObject.fromObject(json);
        JSONObject returnjson = new JSONObject();
        String row = getjson.getString("row");// 几行开始
        String page = getjson.getString("page");// 显示多少个
        String driverName = getjson.getString("driverName");//司机姓名
        String userName = getjson.getString("userName");//用户名
        String driverPhone = getjson.getString("driverPhone");//司机电话
        String address = getjson.getString("address");//终到位置
        String userInfo = getjson.getString("userInfo");//用户信息
        User user = gson.fromJson(userInfo, User.class);
        int page1 = 0;
        int row1 = 0;
        if (user != null) {
            if (page != null && !"".equals(page)) {
                page1 = Integer.parseInt(page);
            }
            if (row != null && !"".equals(row)) {
                row1 = Integer.parseInt(row);
            }
            List<Maps> list = mapService.loadallcarinfo(row1, page1,
                    driverName, "", driverPhone, "",
                    "", user, userName, address);
            returnjson.put("successMsg", list);
        }

        return returnjson.toString();
    }

    /**
     * 获取当前定位信息
     *
     * @param json
     * @return
     */
    @ResponseBody
    @RequestMapping(params = "method=getMapInfoByUserInfo")
    public String getMapInfoByUserInfo(String json) {
        Gson gson = new Gson();
        JSONObject getjson = JSONObject.fromObject(json);
        JSONObject returnjson = new JSONObject();
        String carno = getjson.getString("userName");// 用户名
        String userInfo = getjson.getString("userInfo");//用户信息
        User user = gson.fromJson(userInfo, User.class);
        if (user != null) {
            List<Maps> list = mapService.loadmapinfo(carno, user);
            returnjson.put("successMsg", list);
        }
        return returnjson.toString();
    }

    /**
     * 获取总个数
     *
     * @param json
     * @return
     */
    @ResponseBody
    @RequestMapping(params = {"method=getPlayBackPageCount"})
    public String getPlayBackPageCount(String json) {
        Gson gson = new Gson();
        JSONObject getjson = JSONObject.fromObject(json);
        JSONObject returnjson = new JSONObject();
        String userName = getjson.getString("userName");// 用户名
        String startTime = getjson.getString("startTime");//开始时间
        String endTime = getjson.getString("endTime");//结束时间
        Map<String, String> tablename = BaiduWeather.dateFen(startTime,
                endTime);
        int num = 0;
        for (String table : tablename.keySet()) {
            num += mapService.gethfpage(table, userName, startTime, endTime);
        }
        returnjson.put("successMsg", num);
        return returnjson.toString();
    }

    /**
     * 分页获取回放数据
     *
     * @param json
     * @return
     */
    @ResponseBody
    @RequestMapping(params = {"method=getPlayBackPageMapInfo"})
    public String getPlayBackPageMapInfo(String json) {
        Gson gson = new Gson();
        JSONObject getjson = JSONObject.fromObject(json);
        JSONObject returnjson = new JSONObject();
        String userName = getjson.getString("userName");// 用户名
        String startTime = getjson.getString("startTime");//开始时间
        String endTime = getjson.getString("endTime");//结束时间
        String page = getjson.getString("page");//几页
        int page1 = 0;
        if (page != null && !"".equals(page)) {
            page1 = Integer.parseInt(page);
        }
        Map<String, String> tablename = BaiduWeather.dateFen(startTime,
                endTime);
        String[] db = new String[2];
        int n = 0;
        for (String table : tablename.keySet()) {
            db[n++] = table;
        }
        if ("".equals(db[1]) || db[1] == null) {
            db[1] = db[0];
        }
        List<Maps> list = mapService.gethfmessnozero(db[0], userName, startTime,
                endTime, db[1], page1 + "");
        returnjson.put("successMsg", list);
        return returnjson.toString();
    }

    /**
     * app 地图获取信息通过运单号
     *
     * @param json
     * @return
     */
    @ResponseBody
    @RequestMapping(params = "method=appGetShippingOrderId")
    public String appGetShippingOrderId(String json) {
        Gson gson = new Gson();
        JSONObject getjson = JSONObject.fromObject(json);
        JSONObject returnjson = new JSONObject();
        String userInfo = getjson.getString("userInfo");//用户信息
        String shiping_order_num = getjson.getString("shiping_order_num");// 订单号
        String shiping_order_goid = getjson.getString("shiping_order_goid");// 出货订单号
        User user = gson.fromJson(userInfo, User.class);
        List<ShippingOrder> list = csi.getShippingOrderId(shiping_order_num, shiping_order_goid);
        if (list == null) {
            returnjson.put("errorMsg", "输入的运单信息不存在");
            return returnjson.toString();
        }
        if (list.size() != 1) {
            returnjson.put("errorMsg", "输入的运单信息存在多条");
            return returnjson.toString();
        }
        returnjson.put("successMsg", list.get(0));
        return returnjson.toString();
    }

    @ResponseBody
    @RequestMapping(value = "method=updateBusinessType", method = RequestMethod.POST)
    public JSONObject updateBusinessType(String json) {

        JSONObject returnJson = new JSONObject();
        log.debug("TMS-------------" + json);
        if (json == null) {
            returnJson.put("code", false);
            return returnJson;
        }
        Gson gson = new Gson();
        try {
            List<TmsTransfer> list = gson.fromJson(json, new TypeToken<List<TmsTransfer>>() {
            }.getType());
            if (list == null || list.size() == 0) {
                returnJson.put("code", false);
                return returnJson;
            }
            List<String> ids = new ArrayList<String>();
            for (TmsTransfer tmsTransfer : list) {
                String id = csi.getShippingOrderBusinessType(tmsTransfer.getPlatform_number_SCM(), "", 1);
                ids.add(id);
            }
            int i = csi.updateShippingOrderBusinessType(ids, 2);
            returnJson.put("code", true);
        } catch (Exception e) {
            returnJson.put("code", false);
            return returnJson;
        }

        return returnJson;
    }


    // 以下是dms1期的接口************************************************************************

    // 用户登录
    // 登录
    @RequestMapping(params = "method=applogin")
    public
    @ResponseBody
    String userLogin(String json, HttpSession session) {
        // System.out.println("666666666666");
        JSONObject tojson = JSONObject.fromObject(json);

        JSONObject returnjson = new JSONObject();
		/* try { */
        User user = new User();
        user.setUsername(tojson.getString("userName"));
        user.setPassword(tojson.getString("pwd"));
        boolean flag = false;
        // boolean flag = loginUserService.checkUser(user);
        if (flag == true) {
            User userinfo = loginUserService.getUserInfo(user);
            // 更新app标识
			/*
			 * loginUserService.updatechannelId(tojson.getString("channelId"),user
			 * .getId());
			 */// 是否有固定车辆信息
			/*
			 * String
			 * carflag=loginUserService.getCarCount(userinfo.getDriver_id());
			 *//*
				 * if(carflag==null){ returnjson.put("errorMsg","您不是司机用户");
				 * return returnjson.toString(); }else{
				 * returnjson.put("successMsg",userinfo);
				 * System.out.println(returnjson.toString()); return
				 * returnjson.toString(); }
				 */
            returnjson.put("successMsg", userinfo);
            return returnjson.toString();
        }
        returnjson.put("errorMsg", "用户名密码错误");
        return returnjson.toString();
		/* } */
		/*
		 * catch (Exception e) { // TODO: handle exception
		 * returnjson.put("errorMsg","登录异常"); return returnjson.toString();
		 *
		 * }
		 */

    }

    /**
     * 展示订单
     *
     * @param json
     * @return 0是待配订单1配送订单页面2是电子围栏签收3是签收查询页面 startTime开始时间 endTime结束时间
     * state(0.未签收,1.已签收) 货运编号 order_num 出货订单号 shiping_order_goid 发货客户
     * custom_name 收货客户 receipt_name
     */
    @RequestMapping(params = "method=showOrder")
    public
    @ResponseBody
    String showOrder(String json) {
        JSONObject gson = JSONObject.fromObject(json);
        String driver_id = gson.getString("driver_id");// 司机 id
        String flag = gson.getString("flag");// 页面编号
        String rownum = gson.getString("rownum");// 行号
        String shiping_order_goid = gson.getString("shiping_order_goid");// 出货订单号
        String custom_name = gson.getString("custom_name");// 发货客户
        String receipt_name = gson.getString("receipt_name");// 收货客户
        if (rownum != null && !"".equals(rownum)) {
            if (Integer.parseInt(rownum) > 0) {
                rownum = String.valueOf(Integer.parseInt(rownum) - 1);
            }
        }
        List<ShippingOrder> orders = new ArrayList<ShippingOrder>();
        if (driver_id != null && !"".equals(driver_id)) {
            // 待配订单页面
            if (flag.equals("0")) {

                String[] orderids = loginUserService.getOrderid(driver_id,
                        Integer.parseInt(flag), gson.getString("order_num"),
                        rownum, gson.getString("rows"), shiping_order_goid,
                        custom_name, receipt_name);
                if (orderids.length > 0) {
                    orders = loginUserService.getOrders(orderids);
                }
                // 配送订单页面
            } else if (flag.equals("1")) {
                String[] orderids = loginUserService.getOrderidss(driver_id, 1,
                        1, gson.getString("order_num"), rownum,
                        gson.getString("rows"), "", "", "", shiping_order_goid,
                        custom_name, receipt_name);
                if (orderids.length > 0) {
                    orders = loginUserService.getOrders(orderids);
                }
                // 电子围栏签收页面
            } else if (flag.equals("2")) {
				/*
				 * String[] orderids = loginUserService.getOrderidss(driver_id,
				 * 1, 2, gson.getString("order_num"), rownum,
				 * gson.getString("rows"), "", "", "");
				 *
				 * if (orderids.length > 0) { orders =
				 * loginUserService.getOrders(orderids);
				 *
				 * }
				 */
                // 签收查询页面
            } else if (flag.equals("3")) {
                // 默认显示7天
                SimpleDateFormat dateFormat = new SimpleDateFormat(
                        "yyyy-MM-dd HH:mm:ss");
                String startTime = gson.getString("startTime");
                String endTime = gson.getString("endTime");
                String state2 = gson.getString("state2");
                if ((startTime == null || "".equals(startTime))
                        && (endTime == null || "".equals(endTime))) {
                    startTime = dateFormat.format(new Date().getTime() - 2 * 24
                            * 3600 * 1000);
                    endTime = dateFormat.format(new Date());
                }
                // 查询用户类型1司机，2供应商
                String userType = gson.getString("type");

                // 司机
                if (userType.equals("1")) {
                    orders = csi.psDriverPageInfoShipperOrder(rownum,
                            gson.getString("rows"), driver_id,
                            gson.getString("order_num"), startTime, endTime,
                            state2, shiping_order_goid, custom_name,
                            receipt_name);

                    // 供应商
                } else if (userType.equals("2")) {
                    String supperlis_id = gson.getString("suppliers_id");
                    orders = csi.psSuppPageInfoShipperOrder(rownum,
                            gson.getString("rows"), supperlis_id,
                            gson.getString("order_num"), startTime, endTime,
                            state2, shiping_order_goid, custom_name,
                            receipt_name);
                }
            }
        }
        JSONObject returnjson = new JSONObject();
        returnjson.put("successMsg", orders);
        return returnjson.toString();
    }

    /**
     * 司机接单
     *
     * @param json
     * @return
     */
    @RequestMapping(params = "method=ReceiveOrder")
    public
    @ResponseBody
    String receiveOrder(String json) {
        JSONArray jsonArray = JSONArray.fromObject(json);
        JSONObject returnjson = new JSONObject();
        for (int i = 0; i < jsonArray.size(); i++) {
            // JSONObject gson=JSONObject.fromObject(json);
            JSONObject gson = jsonArray.getJSONObject(i);
            String order_id = gson.getString("shiping_order_id");
            String driver_id = gson.getString("driver_id");
            String order_num = gson.getString("order_num");
            ShippingOrder d = ordersercice
                    .getShipOrderInfos(order_id);
            if (d.getWaybill_state() == 1 || d.getWaybill_state() == 2) continue;
            int x = loginUserService.receiveOrder(order_id, driver_id);
            if (x > 0) {
                String allTime = app_sercice.appGetAgingAllTime(order_id);
                app_sercice.appUpdateOrderState(2, order_id, allTime);
                String i3 = app_sercice.appGetDriverName(driver_id);// 能不能传递过来
                // saveHistory(order_id, order_num, "司机:" + i3 + "已接单", "1");
                HistoryUtils.saveHistory(order_id, order_num, "司机:" + i3 + "已接单", "2");
            }
        }
        returnjson.put("successMsg", "接单成功,未成功接单的运单进行了运单调整");

        return returnjson.toString();
    }

    /**
     * 订单签收
     */
    @RequestMapping(params = "method=signShipOrder")
    public
    @ResponseBody
    String signShipOrder(String json, HttpServletRequest request) {
        JSONObject getjson = JSONObject.fromObject(json);
        JSONObject returnjson = new JSONObject();
		/* try { */
        Gson gson = new Gson();
        int flag = json.indexOf("newFileName");
        // System.out.println(json);
        if (flag > 0) {
            Sign_order sign_order = gson.fromJson(
                    getjson.getString("singnInfo"), Sign_order.class);
            sign_order.setSign_id(UUIDUtils.getUUID());
            String str_num = sign_order.getOrder_number();
            // String order_num = "";
            if (str_num != null) {
                if (str_num.indexOf(".53") != -1) {
                    String str = "";
                    Properties prop = new Properties();
                    InputStream in = TemporaryCarController.class
                            .getResourceAsStream("/../56linkedInterface.properties");
                    try {
                        prop.load(in);
                        Iterator<String> it = prop.stringPropertyNames()
                                .iterator();
                        while (it.hasNext()) {
                            String key = it.next();
                            if (key.equals("56linkedURL")) {
                                str = prop.getProperty(key);
                            }

                        }
                        in.close();
                        // 运单号、车牌号、
                        ShippingOrder d = ordersercice
                                .getShipOrderInfos(sign_order.getOrder_id());

                        if (d.getWaybill_state() == 1 || d.getWaybill_state() == 2) {
                            returnjson.put("successMsg", "该订单进行了运单调整，请等待处理");
                            return returnjson.toString();
                        }

                        String ddate = new SimpleDateFormat(
                                "yyyy-MM-dd HH:mm:ss").format(Calendar
                                .getInstance().getTime());

                        String agtime = ddate;
                        if (!("").equals(d.getReceivetime())
                                && d.getReceivetime() != null) {
                            agtime = d.getReceivetime().substring(0, 10) + " "
                                    + d.getAging_time();
                        }

                        String goid = d.getShiping_order_goid();
                        String mechanism = d.getSend_mechanism();
                        int i56 = csi.get56LinkProject(goid, mechanism);
                        if (i56 == 0) {
                            testPost(str, goid, sign_order.getSign_remarks(),
                                    ddate, sign_order.getSign_user(), agtime);
                        }

                    } catch (Exception e) {

                        System.out.println(e.getMessage());
                    } //

                }
            }
            int i = 0;

            String order_id = sign_order.getOrder_id();
            Sign_order sign = csi.isnotSignOrder(order_id);

            Driver driver = csi.getDriverInfo(order_id);
            if (driver == null) {
                sign_order.setSign_username("未找到签收经办");
            } else {
                sign_order.setSign_username(driver.getDriver_name());
            }

            if (sign != null) {
                i = csi.updateSignShipOrder(sign_order);
            } else {
                i = csi.saveSignShipOrder(sign_order);

            }

            // 生成jpeg图片
            String path = request.getSession().getServletContext()
                    .getRealPath("WebRoot/signorderImages/");// 文件路径
            File filez = new File(path);
            if (!filez.exists()) {
                filez.mkdirs();
            }
			/*
			 * -------------- 获取路径----------
			 */
            String newpath = request.getContextPath();
            String basePath = request.getScheme() + "://"
                    + request.getServerName() + ":" + request.getServerPort()
                    + newpath;
            String paths = basePath + "/WebRoot/signorderImages/"
                    + getjson.getString("newFileName");
			/*
			 * -------------- 获取路径----------
			 */

            // String paths=path+"\\"+getjson.getString("newFileName");// 新生成的图片
            // paths=paths.replaceAll("\\\\","////");

            int flg = csi.saveSignImages(UUIDUtils.getUUID(),
                    sign_order.getOrder_id(), paths);
            if (flg > 0 && i > 0) {
                csi.updatestate(sign_order.getOrder_id());
                // order_id = sign_order.getOrder_id();
				/*
				 * ShippingOrder der = csi.getUpdateShipOrder(order_id); String
				 * receipt = der.getReceipt_name(); String sendMechanism =
				 * der.getSend_mechanism();
				 */
				/*
				 * if (receipt != null && !"".equals(receipt)) {
				 * List<DeliveryCustomer> delivery = rms
				 * .getDeliveryCustomerbyname(receipt, sendMechanism);
				 * System.out.println("这里是签收的收货客户信息" + delivery); if (delivery
				 * != null) { if (delivery.size() > 0) { if
				 * (delivery.get(0).getDelivery_point() == null ||
				 * "".equals(delivery.get(0) .getDelivery_point())) { String
				 * point = sign_order.getLng() + "," + sign_order.getLat();
				 * delivery.get(0).setDelivery_point(point);
				 * delivery.get(0).setDelivery_areaType("0");
				 * delivery.get(0).setDelivery_radius("100"); int iq =
				 * rms.updateDeliveryCustomer(delivery .get(0));
				 *
				 * } }
				 *
				 * } }
				 */
                app_sercice.appInsertShippingTime(sign_order.getOrder_id());// 保存时效信息
                saveHistory(sign_order.getOrder_id(),
                        sign_order.getOrder_number(), "app实际签收，签收人"
                                + sign_order.getSign_user(), "1");
                returnjson.put("successMsg", "订单签收成功");
                // zzp签收哦
                // zzp添加

                // return returnjson.toString();
            } else {
                returnjson.put("errorMsg", "订单签收失败");

            }
        } else {
            returnjson.put("errorMsg", "订单图片上传失败,请重新上传");
        }
		/*
		 * else { Sign_order sign_order = gson.fromJson(json, Sign_order.class);
		 * sign_order.setSign_id(UUIDUtils.getUUID()); int i = 0;
		 *
		 * csi.updatestate(sign_order.getOrder_id()); i =
		 * csi.saveSignShipOrder(sign_order); if (i > 0) { String order_id =
		 * sign_order.getOrder_id(); ShippingOrder der =
		 * csi.getUpdateShipOrder(order_id); String receipt = der.getReceipt();
		 * String sendMechanism = der.getSend_mechanism(); if (receipt != null
		 * && !"".equals(receipt)) { List<DeliveryCustomer> delivery = rms
		 * .getDeliveryCustomerbyname(receipt, sendMechanism); if (delivery !=
		 * null) { if (delivery.size() > 0) { if
		 * (delivery.get(0).getDelivery_point() == null ||
		 * "".equals(delivery.get(0) .getDelivery_point())) { String point =
		 * sign_order.getLng() + "," + sign_order.getLat();
		 * delivery.get(0).setDelivery_point(point);
		 * delivery.get(0).setDelivery_areaType("0");
		 * delivery.get(0).setDelivery_radius("100");
		 * rms.updateDeliveryCustomer(delivery.get(0)); } } } }
		 *
		 * saveHistory(sign_order.getOrder_id(), sign_order.getOrder_number(),
		 * "app实际签收,收货人" + sign_order.getSign_user());
		 * returnjson.put("successMsg", "订单签收成功"); return returnjson.toString();
		 * } else { returnjson.put("errorMsg", "订单签收失败"); return
		 * returnjson.toString(); }
		 *
		 * }
		 */

		/*
		 * } catch (Exception e) { // TODO: handle exception
		 * returnjson.put("errorMsg", "系统异常"); return returnjson.toString(); }
		 */
        return returnjson.toString();
    }

    /**
     * 查询司机分配的订单
     */
    @RequestMapping(params = "method=getCarOder")
    public
    @ResponseBody
    String getCarOder(String json) {

        JSONObject getjson = JSONObject.fromObject(json);
        JSONObject returnjson = new JSONObject();
        try {
            System.out.println(getjson.getString("car_id") + "车牌号");
            List<ShippingOrder> der = csi.getCarShipOrder(getjson
                    .getString("car_id"));
            returnjson.put("successMsg", der);

            return returnjson.toString();
        } catch (Exception e) {
            // TODO: handle exception
            returnjson.put("errorMsg", "系统异常");
            return returnjson.toString();
        }
    }

    /**
     * 查询供应商对应的司机
     */
    @RequestMapping(params = "method=getshippertocar")
    public
    @ResponseBody
    String getShipperTocar(String json) {
        JSONObject getjson = JSONObject.fromObject(json);
        JSONObject returnjson = new JSONObject();
        try {
            List<Driver> list = dsi.getDriverInfoSupp(getjson
                    .getString("suppliers_id"));

            returnjson.put("successMsg", list);

            return returnjson.toString();
        } catch (Exception e) {
            // TODO: handle exception
            returnjson.put("errorMsg", "系统异常");
            return returnjson.toString();
        }
    }

    /**
     * dms供应商查询订单
     */
    @RequestMapping(params = "method=getShipOrder")
    public
    @ResponseBody
    String getShipOrder(String json) {
        JSONObject getjson = JSONObject.fromObject(json);

        JSONObject returnjson = new JSONObject();
        String rownum = getjson.getString("rownum");
        if (rownum != null && !"".equals(rownum)) {
            if (Integer.parseInt(rownum) > 0) {
                rownum = String.valueOf(Integer.parseInt(rownum) - 1);
            }
        }
        String shiping_order_goid = getjson.getString("shiping_order_goid");// 出货订单号
        String custom_name = getjson.getString("custom_name");// 发货客户
        String receipt_name = getjson.getString("receipt_name");// 收货客户
        String shippingOderId = getjson.getString("shippingOderId");// 货运编号
        List<String> userAddress = rmdao.getAddressSupperlies(getjson
                .getString("suppliers_id"));

        try {
            List<ShippingOrder> list = csi.suppliersGetShipperOrderInfo(rownum,
                    getjson.getString("rows"),
                    getjson.getString("suppliers_id"), shippingOderId,
                    shiping_order_goid, custom_name, receipt_name, userAddress);

            returnjson.put("successMsg", list);

            return returnjson.toString();
        } catch (Exception e) {
            // TODO: handle exception
            returnjson.put("errorMsg", "系统异常");
            return returnjson.toString();
        }
    }

    /**
     * 供应商分配订单
     */
    @RequestMapping(params = "method=fpShipperOrder")
    public
    @ResponseBody
    String fpShipperOrder(String json) {
        JSONObject getjson = JSONObject.fromObject(json);
        JSONObject returnjson = new JSONObject();
        String jsonorder = getjson.getString("orders");
        String driverId = getjson.getString("driver_id");

        String[] orders = null;
        // System.out.println("这是json串" + jsonorder);
        if (!"".equals(jsonorder) && jsonorder != null) {
            orders = jsonorder.split(",");
        }
        try {
            if (orders != null) {
                String userName = app_sercice.driverGetUserName(driverId);
                List<DriverToOrder> orderlist = new ArrayList<DriverToOrder>();
                for (int i = 0; i < orders.length; i++) {
                    DriverToOrder order = new DriverToOrder();
                    order.setId(UUIDUtils.getUUID());
                    order.setDriver_id(driverId);
                    order.setOrder_id(orders[i]);
                    order.setEquipmentNum("");
                    order.setUserName(userName);
                    orderlist.add(order);

                }

                int i = dsi.saveDrivertoOrder(orderlist);
                if (i > 0) {
                    System.out.println("订单数量" + orders.length);
                    int j = dsi.updateShipperorderState(orders);
                    if (j > 0) {
                        new Thread(new FenPeiSaveHistoryThread(orders, csi))
                                .start();
                    }
                    returnjson.put("successMsg", "添加成功");
                } else {
                    returnjson.put("errorMsg", "添加失败");
                }
            } else {
                returnjson.put("errorMsg", "没有要分配的订单");
            }
            System.out.println(returnjson.toString());
            return returnjson.toString();
        } catch (Exception e) {
            returnjson.put("errorMsg", "系统异常");
            return returnjson.toString();
        }
    }
}
