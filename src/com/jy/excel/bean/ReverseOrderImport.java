package com.jy.excel.bean;

import com.jy.common.SpringContextHolder;
import com.jy.csaction.ExeclRead;
import com.jy.dao.RemarkMapDao;
import com.jy.dao.ReverseOrderDao;
import com.jy.model.*;
import com.jy.service.OrderInfoService;
import com.jy.service.ReverseOrderInfoService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 导入逆向物流信息
 */
public class ReverseOrderImport {

    //
    private JSONArray jsonMessages = new JSONArray();

    private JSONObject jsonReturn = new JSONObject();

    private AbstractReverseOrderInfo abstractReverseOrderInfo;

    private ReverseOrderDao so_dao = SpringContextHolder.getBean("reverseOrderDao");

    private OrderInfoService orderInfoService =SpringContextHolder.getBean("OrderInfoService");

    private ReverseOrderInfoService reverseOrderInfoService = SpringContextHolder.getBean("ReverseOrderInfoService");

    public JSONObject importReverseOrderInfo(String fileName, User user) throws Exception{
        if (user.getFlag().equals("0")) return jsonReturn;

        if (user.getFlag().equals("1")||user.getFlag().equals("2")){
            abstractReverseOrderInfo = new SupplierReverseOrderInfo();
            Driver driver = so_dao.getDriverSupplierId(user.getDriver_id());
            user.setSuppliers_id(driver==null?"":driver.getDriver_suppliers());
            user.setUser_address(driver==null?"":(driver.getDriver_address()==null)?"":driver.getDriver_address().split(",")[0]);

        }else if(user.getFlag().equals("3")||user.getFlag().equals("4")){
            abstractReverseOrderInfo = new CustomerReverseOrderInfo();
            Customer customer = so_dao.getCustomer(user.getCustomer_id());
            user.setCustomer_id(customer==null?"":customer.getCustomer_id());
            user.setCustomer_name(customer == null ? "" : customer.getCustomer_name());
            user.setUser_address(customer==null?"":customer.getCustomer_address());
        }
        abstractReverseOrderInfo.setUser(user);
        return getJsonObjectMessage(fileName);
    }

    private JSONObject getJsonObjectMessage(String fileName) throws IOException {
        String[][] data =  ExeclRead.getImportFileData(fileName,1);
        Map<String,Object> map = getReverserOrderListInfo(data);
        List<OrderHistory> historyList = (List<OrderHistory>) map.get("history");
        List<ReverserOrderModel> reverserOrderModels = (List<ReverserOrderModel>) map.get("order");
        List<ShippingOrder> shippingOrderList = (List<ShippingOrder>) map.get("shippingOrder");
        List<OrderHistory> orderHistories = (List<OrderHistory>) map.get("shippingHistoryList");
        int i = reverseOrderInfoService.batchInsertReverseOrderInfo(reverserOrderModels);

        if (i > 0) {
            reverseOrderInfoService.saveReverserOrderHistoryList(historyList);
        }
        if (shippingOrderList!=null&&shippingOrderList.size()>0){
            int j = orderInfoService.saveimp(shippingOrderList);
            if (j>0){
                if (orderHistories!=null&&orderHistories.size()>0){
                    orderInfoService.saveOrderHistory(orderHistories);
                }
            }
        }


        jsonReturn.put("numberOfEntries",i);//插入成功条数
        jsonReturn.put("messages",jsonMessages);//插入失败的数据
        jsonReturn.put("totalNumber",data.length);//总条数
        return jsonReturn;
    }


    private Map<String,Object> getReverserOrderListInfo(String[][] data){
        Map<String,Object> map = new HashMap<String,Object>();
        if (data==null) return map;
        List<ReverserOrderModel> reverserOrderModels = new ArrayList<ReverserOrderModel>();
        List<OrderHistory> historyList = new ArrayList<OrderHistory>();
        List<OrderHistory> shippingHistoryList = new ArrayList<OrderHistory>();
        List<ShippingOrder> shippingOrderList = new ArrayList<ShippingOrder>();
        ReverserOrderModel reverserOrderModel;
        OrderHistory orderHistory;
        OrderHistory shippingOrderHistory;
        JSONObject jsonObject;
        ShippingOrder shippingOrder;
        for (int i=0;i<data.length;i++){
            reverserOrderModel=new ReverserOrderModel();
            jsonObject = new JSONObject();
            orderHistory = new OrderHistory();
            shippingOrder=new ShippingOrder();
            shippingOrderHistory=new OrderHistory();
            String[] message = new String[data[i].length+1];
            System.arraycopy(data[i], 0, message, 0, data[i].length);
            abstractReverseOrderInfo.getReverserOrderModel(message,reverserOrderModel);
            boolean b = abstractReverseOrderInfo.validateReverserInfo(jsonObject, reverserOrderModel);
            if(b){
                reverserOrderModels.add(reverserOrderModel);
                if ((reverserOrderModel.getCreate_type().equals("2")||reverserOrderModel.getCreate_type().equals("1"))&&reverserOrderModel.getOrder_type()==1){
                    int bi = orderInfoService.bGetOrderCount(reverserOrderModel.getShiping_order_num(), reverserOrderModel.getShiping_order_goid());
                    if (bi==0){
                        abstractReverseOrderInfo.getShippingOrder(reverserOrderModel, shippingOrder);
                        abstractReverseOrderInfo.getOrderHistoryInfo(shippingOrderHistory, reverserOrderModel,"逆向物流运单导入");
                        shippingOrderList.add(shippingOrder);
                        shippingHistoryList.add(shippingOrderHistory);
                    }

                }
                abstractReverseOrderInfo.getOrderHistoryInfo(orderHistory, reverserOrderModel,"运单已导入");
                historyList.add(orderHistory);
            }else{
                jsonObject.put("line",i+2);
                jsonMessages.add(jsonObject);
            }
        }
        map.put("order",reverserOrderModels);
        map.put("history",historyList);
        map.put("shippingOrder",shippingOrderList);
        map.put("shippingHistoryList",shippingHistoryList);
        return map;
    }



}
