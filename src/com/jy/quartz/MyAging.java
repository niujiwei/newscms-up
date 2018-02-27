package com.jy.quartz;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.Id;

import net.sf.json.JSONObject;

import com.baidu.push.msg.AndroidPushBatchUniMsgNew;
import com.baidu.yun.push.exception.PushClientException;
import com.baidu.yun.push.exception.PushServerException;
import com.jy.action.AppController;
import com.jy.model.ShippingOrder;
import com.jy.model.User;
import com.jy.service.RemarkMapService;
import common.Logger;
import org.apache.poi.hwpf.model.SavedByTable;

public class MyAging {
    private Logger log = Logger.getLogger(MyAging.class);
    @Resource
    private RemarkMapService rms;

    /**
     * 时效提醒功能
     *
     * @throws PushServerException
     * @throws PushClientException
     */
    public void oAging() throws PushClientException, PushServerException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Calendar calendar = Calendar.getInstance();
        long time = 30 * 60 * 1000;
        Date date = new Date();
        String dateStr = sdf.format(date);

        if (calendar.get(calendar.MINUTE) == 0 || calendar.get(calendar.MINUTE) == 30) {
            List<String> channerIds = new ArrayList<String>();
            List<ShippingOrder> agingShip = rms.getShippingAging();
            if (agingShip==null) agingShip = new ArrayList<ShippingOrder>();
            for (int i = 0; i <agingShip.size(); i++) {
                try {
                    ShippingOrder order = agingShip.get(i);
                    String Aging_alltime = agingShip.get(i).getAging_alltime();
                    if (Aging_alltime != null) {
                        Date agingAllTime = sdf.parse(Aging_alltime);
                        long nowTime = date.getTime();
                        long agingLongTime = agingAllTime.getTime();
                        long jiangeTime = agingLongTime - nowTime;
                        if (jiangeTime <= time) {
                            User channer = rms.getChannelId(agingShip.get(i).getShiping_order_id());
                            if (channer != null) {
                                 String channelId = channer.getChannelId();
                                if (channelId!=null&&!channelId.equals("")){
                                    channerIds.add(channelId);
                                }
                            }
                        }
                    }

                } catch (Exception e) {
                    log.error("MyAging" + e.getMessage());
                }
            }
            String[] chaIds = new String[channerIds.size()];
            channerIds.toArray(chaIds);
            String title = "时效提醒";
            String message = "您有运单再不配送马上,时效就超时了";
            JSONObject jsonCustormCont = new JSONObject();
            JSONObject json = new JSONObject();
            json.put("type", "0");
            jsonCustormCont.put("successMsg", json);
            AndroidPushBatchUniMsgNew.androidPushBatchMsg(title, message, chaIds, jsonCustormCont);
        }

    }
}
