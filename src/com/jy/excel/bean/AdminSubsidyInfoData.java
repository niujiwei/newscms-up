package com.jy.excel.bean;

import com.jy.common.UUIDUtils;
import com.jy.model.SubsidyHistoryModel;
import com.jy.model.SubsidyInfoModel;
import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/29/029.
 */
public class AdminSubsidyInfoData extends AbstractSubsidyInfoData {

    @Override
    public SubsidyInfoModel getSubsidyInfoModel(String[] data) {
        SubsidyInfoModel subsidyInfoModel = new SubsidyInfoModel();
        subsidyInfoModel.setShiping_order_num(data[map.get(SHIPING_ORDER_NUM)]);
        subsidyInfoModel.setShiping_order_goid(data[map.get(SHIPING_ORDER_GOID)]);
        subsidyInfoModel.setAdmin_subsidy(data[map.get(ADMIN_SUBSIDY)]);
        subsidyInfoModel.setAdmin_remarks(data[map.get(ADMIN_REMARKS)]);
        subsidyInfoModel.setWhether_through(data[map.get(WHETHER_THROUGH)]);
        return subsidyInfoModel;
    }

    @Override
    public boolean validateSubsidyInfo(SubsidyInfoModel subsidyInfoModel, JSONObject jsonObject) {
        List<SubsidyInfoModel> list = subsidyInfoService.getSubsidyInfoModel(subsidyInfoModel.getShiping_order_num(), subsidyInfoModel.getShiping_order_goid());
        if (list!=null&&list.size()==1){
            SubsidyInfoModel model = list.get(0);
            if (subsidyInfoModel.getState()>=2){
                putJsonObjectMessage(subsidyInfoModel.getShiping_order_num(),subsidyInfoModel.getShiping_order_goid(),"该信息已经审核了",jsonObject);

            }else{
                subsidyInfoModel.setId(model.getId());//获取对应的id
                //3通过2
                int state = subsidyInfoModel.getWhether_through()==null||subsidyInfoModel.getWhether_through().equals("")||subsidyInfoModel.getWhether_through().equals("通过")?2:3;
                subsidyInfoModel.setState(state);
            }

        }else {
            putJsonObjectMessage(subsidyInfoModel.getShiping_order_num(),subsidyInfoModel.getShiping_order_goid(),"未到对应信息或者存在多条信息",jsonObject);
        }

        return jsonObject.size()==0;
    }

    @Override
    public int saveOrUpdateSubsidyInfo(List<SubsidyInfoModel> list) {

        return subsidyInfoService.batchUpdateSubsidyInfo(list);
    }
}
