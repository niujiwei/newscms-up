package com.jy.excel.bean;

import com.jy.common.UUIDUtils;
import com.jy.common.ValidateUntil;
import com.jy.model.ShippingOrder;
import com.jy.model.SubsidyInfoModel;
import net.sf.json.JSONObject;
import org.apache.commons.collections.map.HashedMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/29/029.
 */
public class SupplierSubsidyInfoData extends AbstractSubsidyInfoData{

    @Override
    public SubsidyInfoModel getSubsidyInfoModel(String[] data) {
        SubsidyInfoModel subsidyInfoModel = new SubsidyInfoModel();
        subsidyInfoModel.setId(UUIDUtils.getUUID());
        subsidyInfoModel.setDelivery_date(data[map.get(DELIVERY_DATE)]);
        subsidyInfoModel.setShiping_order_num(data[map.get(SHIPING_ORDER_NUM)]);
        subsidyInfoModel.setShiping_order_goid(data[map.get(SHIPING_ORDER_GOID)]);
        subsidyInfoModel.setSuper_far_subsidy(data[map.get(SUPER_FAR_SUBSIDY)]);
        subsidyInfoModel.setUpstairs_subsidy(data[map.get(UPSTAIRS_SUBSIDY)]);
        subsidyInfoModel.setSuper_volume_subsidy(data[map.get(SUPER_VOLUME_SUBSIDY)]);
        subsidyInfoModel.setOther_subsidy(data[map.get(OTHER_SUBSIDY)]);
        subsidyInfoModel.setRemarks(data[map.get(REMARKS)]);
        return subsidyInfoModel;
    }

    @Override
    public boolean validateSubsidyInfo(SubsidyInfoModel subsidyInfoModel, JSONObject jsonObject) {

        if (subsidyInfoModel.getSuper_far_subsidy()!=null
                &&!"".equals(subsidyInfoModel.getSuper_far_subsidy())&&
                !ValidateUntil.isNumber(subsidyInfoModel.getSuper_far_subsidy())){
            putJsonObjectMessage(subsidyInfoModel.getShiping_order_num(),subsidyInfoModel.getShiping_order_goid(),"超远补助申请必须为数字",jsonObject);

        }
        if (subsidyInfoModel.getUpstairs_subsidy()!=null
                &&!"".equals(subsidyInfoModel.getUpstairs_subsidy())&&
                !ValidateUntil.isNumber(subsidyInfoModel.getUpstairs_subsidy())){
            putJsonObjectMessage(subsidyInfoModel.getShiping_order_num(),subsidyInfoModel.getShiping_order_goid(),"上楼补助申请必须为数字",jsonObject);

        }
        if (subsidyInfoModel.getSuper_volume_subsidy()!=null
                &&!"".equals(subsidyInfoModel.getSuper_volume_subsidy())&&
                !ValidateUntil.isNumber(subsidyInfoModel.getSuper_volume_subsidy())){
            putJsonObjectMessage(subsidyInfoModel.getShiping_order_num(),subsidyInfoModel.getShiping_order_goid(),"超远体积申请必须为数字",jsonObject);

        }

        List<ShippingOrder> orders = subsidyInfoService.getShippingOrder(subsidyInfoModel.getShiping_order_num(), subsidyInfoModel.getShiping_order_goid());
        if (orders!=null&&orders.size()==1){
            List<SubsidyInfoModel> subsidyInfoModels = subsidyInfoService.getSubsidyInfoModel(subsidyInfoModel.getShiping_order_num(),subsidyInfoModel.getShiping_order_goid());
            if (subsidyInfoModels.size()!=0){
                putJsonObjectMessage(subsidyInfoModel.getShiping_order_num(),subsidyInfoModel.getShiping_order_goid(),"该信息已经进行了申请",jsonObject);
            }else{
                ShippingOrder shippingOrder = orders.get(0);
                subsidyInfoModel.setShiping_order_id(shippingOrder.getShiping_order_id());
                subsidyInfoModel.setShiping_order_goid(shippingOrder.getShiping_order_goid());
                subsidyInfoModel.setShiping_order_num(shippingOrder.getShiping_order_num());
                subsidyInfoModel.setState(0);
                subsidyInfoModel.setOrder_type(shippingOrder.getOrder_type());
            }
        }else{
            putJsonObjectMessage(subsidyInfoModel.getShiping_order_num(),subsidyInfoModel.getShiping_order_goid(),"未找到对应信息或者存在多条信息",jsonObject);
        }
        return jsonObject.size()==0;
    }



    @Override
    public int saveOrUpdateSubsidyInfo(List<SubsidyInfoModel> list) {
        if (list==null) return 0;
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = list.size() - 1; j > i; j--) {
                if (list.get(j).getShiping_order_id()
                        .equals(list.get(i).getShiping_order_id())) {
                    list.remove(j);
                }
            }}
        return subsidyInfoService.batchInsertSubsidyInfo(list);
    }


}
