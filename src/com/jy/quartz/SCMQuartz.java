package com.jy.quartz;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jy.common.HttpRequesterUtils;
import com.jy.common.SpringContextHolder;
import com.jy.model.SCMBusinessRemarkData;
import com.jy.model.SCMResult;
import com.jy.model.SCMSupplierUser;
import com.jy.service.SCMService;
import common.Logger;
import net.sf.json.JSONObject;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by njw on 2017/7/11.
 */
public class SCMQuartz {
    private SCMService scmService = SpringContextHolder.getBean("SCMServiceImp");
    private static final String SCM_Url="http://103.239.152.40:9999/NSCM/";
   // private static final String SCM_Url="http://103.239.152.40:6060/NSCM/";
    //private static final String SCM_Url="http://192.168.7.167:8080/NSCM/";
    private Logger log = Logger.getLogger(this.getClass());
    public void crateShippingOrder() {
        scmService.insertSCMShippingOrder();
        scmService.createSCMBusinessRemarkDataOrder();
    }

    public void transmissionShippingOrder(){
        List<SCMBusinessRemarkData> list = scmService.getSCMBusinessRemarkData("0", "1", 100);

        if (list==null||list.size()==0) return;
        Gson gson = new Gson();
        String url = SCM_Url+"bsre.do?method=saveBusRemark";
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("userJson",gson.toJson(list));
        try {
            String result = HttpRequesterUtils.doPost(url,map,"utf-8");
            System.out.println("--------1");
            JSONObject jsonObject = JSONObject.fromObject(result);
            System.out.println(result);
            List<String> errorList =  gson.fromJson(jsonObject.getString("errorList"),new TypeToken<List<String>>(){}.getType());
            List<String> successList =  gson.fromJson(jsonObject.getString("successList"),new TypeToken<List<String>>(){}.getType());
            System.out.println("--------2");
            SCMResult scmResult = gson.fromJson(result, new TypeToken<SCMResult>() {
            }.getType());
            scmResult.setErrorList(errorList);
            scmResult.setSuccessList(successList);
            System.out.println("--------3");
            int j = scmService.updateSCMBusinessRemarkDataState(1,scmResult.getSuccessList());
            System.out.println("更新了11111111----------"+j );
          //  scmService.updateSCMUpdateState(1,scmResult.getSuccessList(),0 ,1);
            int i = scmService.updateSCMBusinessRemarkDataState(2,scmResult.getErrorList());
            System.out.println("更新了----------"+i);

           // scmService.updateSCMUpdateState(2,scmResult.getErrorList(),1 ,3);
        } catch (Exception e) {
            System.out.println("--------4");
            StringWriter sw=new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            System.out.println(sw.getBuffer().toString());
            log.error("异常信息:" + sw.getBuffer().toString());
        }
    }


    public void createSupplies(){
        scmService.createSCMSupplierUser();
    }

    public void tSuppliers(){
        int size=50;
        int  count = scmService.getSCMSupplierUserCount(size,"0");
        int page = (int) Math.ceil(count/size);
        List<SCMSupplierUser> list;
        Map<String,Object> map;
        String url = SCM_Url+"supu.do?method=saveSuppUser";
        for (int i=0;i<=page;i++){
            list = scmService.getSCMSupplierUser(i*size,size, "0");
            if (list==null||list.size()==0) return;
            Gson gson = new Gson();
            gson.toJson(list);
            map = new HashMap<String, Object>();
            map.put("userJson",gson.toJson(list));
            try {
                String result = HttpRequesterUtils.doPost(url,map,"utf-8");
                System.out.println(result);
                JSONObject jsonObject = JSONObject.fromObject(result);
                List<String> errorList =  gson.fromJson(jsonObject.getString("errorList"),new TypeToken<List<String>>(){}.getType());
                List<String> successList =  gson.fromJson(jsonObject.getString("successList"),new TypeToken<List<String>>(){}.getType());
                SCMResult scmResult = gson.fromJson(result, new TypeToken<SCMResult>() {
                }.getType());
                scmResult.setErrorList(errorList);
                scmResult.setSuccessList(successList);
               int success =  scmService.updateSCMSupplierUser(1,successList);
               int error =  scmService.updateSCMSupplierUser(1,errorList);
               if (success>0){
                   tSuppliersHead(successList);
               }
               if (error>0){
                   tSuppliersHead(errorList);
               }

            } catch (Exception e) {
                System.out.println("出错了");
                e.printStackTrace();
            }
        }

    }

    public void tSuppliersHead(List<String> list){
        if (list==null){ return;}
        int size=1;
        String state="1";
        String url = SCM_Url+"supu.do?method=saveSuppHead";
        int count = list.size();
        if (count==0) {return;}
        int page = (int) Math.ceil(count/size);
        Map<String,List<String>> map;
        for (int i=0;i<=page;i++){
            List<String> userFile = scmService.getSCMSupplierUserHead(i*size,size,state,list);
            if (userFile==null||userFile.size()==0) {continue;}
            map=new HashMap<String, List<String>>();
            map.put("userFile",userFile);
            String result = HttpRequesterUtils.formListUpload(url,null,map);
        }
    }

    public void createSuppliersNew(){
        int i = scmService.createSCMSupplierUserDay();
        if (i==0) {return;}
        tSuppliers();

    }



}
