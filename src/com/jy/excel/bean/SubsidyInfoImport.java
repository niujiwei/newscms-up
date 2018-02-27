package com.jy.excel.bean;


import com.jy.csaction.ExeclRead;
import com.jy.model.SubsidyHistoryModel;
import com.jy.model.SubsidyInfoModel;
import com.jy.model.User;
import com.jy.service.SubsidyInfoService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubsidyInfoImport {


    private JSONArray jsonMessages = new JSONArray();

    private JSONObject jsonReturn = new JSONObject();

    private AbstractSubsidyInfoData subsidyInfoData;

    private SubsidyInfoService subsidyInfoService = AbstractSubsidyInfoData.subsidyInfoService;

    /**
     * 保存导入信息返回json
     * @param filePath
     * @return
     * @throws Exception
     */
    private JSONObject saveSubsidyInfoImport(String filePath)throws Exception{
        String[][] data = ExeclRead.getImportFileData(filePath,1);

        Map<String,Object> map = getSubsidyInfoByData(data);

        List<SubsidyInfoModel> subsidyInfoModels = (List<SubsidyInfoModel>) map.get("subsidyInfoModels");
        List<SubsidyHistoryModel> historyModels= (List<SubsidyHistoryModel>) map.get("historyModels");

        int i = subsidyInfoData.saveOrUpdateSubsidyInfo(subsidyInfoModels);

        if (i>0)saveSubsidyHistory(historyModels);

        jsonReturn.put("numberOfEntries",subsidyInfoModels.size());//插入成功条数
        jsonReturn.put("messages",jsonMessages);//插入失败的数据
        jsonReturn.put("totalNumber",data.length);//总条数
        return jsonReturn;
    }

    /**
     * 获取历史信息和导入信息
     * @param data
     * @return
     */
    private Map<String,Object> getSubsidyInfoByData(String[][] data){
        List<SubsidyInfoModel> subsidyInfoModels = new ArrayList<SubsidyInfoModel>();
        List<SubsidyHistoryModel> historyModels = new ArrayList<SubsidyHistoryModel>();
        Map<String,Object> map = new HashMap<String,Object>();
        if (data==null) return map;
        SubsidyInfoModel model;
        JSONObject jsonObject;//用来记录验证信息
        SubsidyHistoryModel subsidyHistoryModel;
        for (int i=0;i<data.length;i++){

            jsonObject = new JSONObject();
            subsidyHistoryModel = new SubsidyHistoryModel();
            String[] message = new String[30];
            System.arraycopy(data[i], 0, message, 0, data[i].length);
            model = subsidyInfoData.getSubsidyInfoModel(message);//进行赋值

            boolean b = subsidyInfoData.validateSubsidyInfo(model,jsonObject);//判断一些验证
            if (b) {
                subsidyInfoModels.add(model);
                //保存历史信息
                subsidyInfoData.getSubsidyHistoryModel(model,subsidyHistoryModel);
                historyModels.add(subsidyHistoryModel);
            }else {
                jsonMessages.add(jsonObject);
            }

        }
        map.put("subsidyInfoModels",subsidyInfoModels);
        map.put("historyModels",historyModels);
        return map;
    }

    /**
     *  保存历史信息
     */
    private boolean saveSubsidyHistory(List<SubsidyHistoryModel> historyModels){
        //List<SubsidyHistoryModel> historyModels= (List<SubsidyHistoryModel>) map.get("historyModels");
        return subsidyInfoService.saveSubsidyHistory(historyModels);
    }

    /**
     * 对外信息
     * @param user
     * @param filePath
     * @return
     * @throws Exception
     */
    public JSONObject getSubsidyInfoData(User user,String filePath) throws Exception {
        if (user.getFlag().equals("1")||user.getFlag().equals("2")){
            subsidyInfoData=new SupplierSubsidyInfoData();
        }
        if (user.getFlag().equals("3")||user.getFlag().equals("4")){
            subsidyInfoData=new CustomerSubsidyInfoData();
        }
        if (user.getFlag().equals("0")){
            subsidyInfoData=new AdminSubsidyInfoData();
        }
        subsidyInfoData.setUser(user);
        return saveSubsidyInfoImport(filePath);
    }
}
