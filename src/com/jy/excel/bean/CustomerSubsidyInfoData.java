package com.jy.excel.bean;

import com.jy.model.SubsidyInfoModel;
import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/29/029.
 */
public class CustomerSubsidyInfoData extends AbstractSubsidyInfoData {



    @Override
    public SubsidyInfoModel getSubsidyInfoModel(String[] data) {
        SubsidyInfoModel subsidyInfoModel = new SubsidyInfoModel();
        subsidyInfoModel.setShiping_order_num(data[map.get(SHIPING_ORDER_NUM)]);
        subsidyInfoModel.setShiping_order_goid(data[map.get(SHIPING_ORDER_GOID)]);
        subsidyInfoModel.setCustomer_subsidy(data[map.get(CUSTOMER_SUBSIDY)]);
        subsidyInfoModel.setCustomer_remarks(data[map.get(CUSTOMER_REMARKS)]);
        return subsidyInfoModel;
    }

    @Override
    public boolean validateSubsidyInfo(SubsidyInfoModel subsidyInfoModel, JSONObject jsonObject) {
        List<SubsidyInfoModel> list = subsidyInfoService.getSubsidyInfoModel(subsidyInfoModel.getShiping_order_num(), subsidyInfoModel.getShiping_order_goid());
        if (list != null && list.size() == 1) {
            SubsidyInfoModel model = list.get(0);
            if (model.getState()>=1&&model.getState()<3) {
                putJsonObjectMessage(subsidyInfoModel.getShiping_order_num(),subsidyInfoModel.getShiping_order_goid(),"该信息已经审核了",jsonObject);
            }else{
                subsidyInfoModel.setId(model.getId());//获取对应的id
                subsidyInfoModel.setState(1);
            }

        } else {
            putJsonObjectMessage(subsidyInfoModel.getShiping_order_num(),subsidyInfoModel.getShiping_order_goid(),"未到对应信息或者存在多条信息",jsonObject);
        }
        return jsonObject.size()==0;
    }
    @Override
    public int saveOrUpdateSubsidyInfo(List<SubsidyInfoModel> list) {

        return subsidyInfoService.batchUpdateSubsidyInfo(list);
    }
}
