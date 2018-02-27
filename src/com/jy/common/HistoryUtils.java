package com.jy.common;

import com.jy.model.OrderHistory;
import com.jy.service.ShippingOrderInfoService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/2/27/027.
 */
public class HistoryUtils {
    private static ShippingOrderInfoService csi = SpringContextHolder.getBean("ShippingOrderInfoService");
    //private static ApplicationContext ac=new ClassPathXmlApplicationContext(new String[] {"classpath:mybatis-config.xml","classpath:spring.xml"});
    public static void  saveHistory(String ids, String num, String message) {
        List<OrderHistory> order = new ArrayList<OrderHistory>();
        OrderHistory h = new OrderHistory();
        h.setOrder_history_id(UUIDUtils.getUUID());
        h.setOrders_id(ids);
        h.setOrder_arrive_time(DateFormat.getDateTimeInstance().format(
                new Date()));
        h.setGoods_arrive_remakes(h.getOrder_arrive_time() + "---货运编号为:" + num
                + "\t" + message);
        order.add(h);
        csi.saveOrderHistory(order);
    }

    public static void  saveHistory(String ids, String num, String message,String state) {
        List<OrderHistory> order = new ArrayList<OrderHistory>();
        OrderHistory h = new OrderHistory();
        h.setOrder_history_id(UUIDUtils.getUUID());
        h.setOrders_id(ids);
        h.setOrder_arrive_time(DateFormat.getDateTimeInstance().format(
                new Date()));
        h.setGoods_arrive_remakes(h.getOrder_arrive_time() + "---货运编号为:" + num
                + "\t" + message);
        h.setState(state);
        order.add(h);
        csi.saveOrderHistory(order);
    }
    public static void saveHistory(List<OrderHistory> orderHistories){
        csi.saveOrderHistory(orderHistories);
    }


}
