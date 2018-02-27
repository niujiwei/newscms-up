import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jy.common.HttpRequesterUtils;
import com.jy.model.SCMBusinessRemarkData;
import com.jy.model.SCMResult;
import com.jy.model.SCMSupplierUser;
import com.jy.model.TmsTransfer;
import com.jy.quartz.SCMQuartz;
import com.jy.service.SCMService;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.aspectj.weaver.AdviceKind.Before;

/**
 * Created by njw on 2017/7/12.
 */
public class TestMain {
    private Logger logger = Logger.getLogger("stdout");
    private static ApplicationContext applicationContext;
    private SCMService scmService;
    @Before
    public void initMethod() {
        applicationContext = new ClassPathXmlApplicationContext(
                new String[] { "classpath:mybatis-config.xml",
                        "classpath:spring.xml" });

        scmService = (SCMService) applicationContext.getBean("SCMServiceImp");
    }
    @Test
    public void creat(){
       // scmService.insertSCMShippingOrder();
       /* scmService.createSCMBusinessRemarkDataOrder();*/
        SCMQuartz scmQuartz = new SCMQuartz();
       scmQuartz.transmissionShippingOrder();

    }


    @Test
    public void insert(){
        List<SCMBusinessRemarkData> list = scmService.getSCMBusinessRemarkData("0", "3", 100);
        Gson gson = new Gson();
        String url ="http://60.208.27.155:5410/NSCM/bsre.do?method=saveBusRemark";
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("userJson",gson.toJson(list));
        try {
            String result = HttpRequesterUtils.doPost(url,map,"utf-8");
            JSONObject jsonObject = JSONObject.fromObject(result);
           List<String> errorList =  gson.fromJson(jsonObject.getString("errorList"),new TypeToken<List<String>>(){}.getType());
            List<String> successList =  gson.fromJson(jsonObject.getString("successList"),new TypeToken<List<String>>(){}.getType());

            SCMResult scmResult = gson.fromJson(result, new TypeToken<SCMResult>() {
            }.getType());
            scmResult.setErrorList(errorList);
            scmResult.setSuccessList(successList);

            scmService.updateSCMBusinessRemarkDataState(1,scmResult.getSuccessList());
            scmService.updateSCMUpdateState(1,scmResult.getSuccessList(),0,3);
            scmService.updateSCMBusinessRemarkDataState(0,scmResult.getErrorList());
            System.out.println(result);
        } catch (Exception e) {
            System.out.println("出错了----");
            e.printStackTrace();
        }
    }
    @Test
    public void suppliers(){
        String filepath = "E:\\project\\IntelliJ IDEA Project\\newscms1\\out\\scms\\WebRoot\\uploadImages\\cut2069093653555092319.png";
       String oneFilePath="E:\\project\\IntelliJ IDEA Project\\newscms1\\out\\scms\\WebRoot\\uploadImages\\cut2880571359744172758.png";
        String urlStr = "http://192.168.5.48:8080/NSCM/supu.do?method=saveSuppUser";
        Map<String, String> textMap = new HashMap<String, String>();
        textMap.put("userJson", "testname");
        Map<String, List<String>> fileMap = new HashMap<String, List<String>>();
        List<String> list = new ArrayList<String>();
        list.add(filepath);
        list.add(oneFilePath);
        fileMap.put("userFile",list);
        String ret = HttpRequesterUtils.formListUpload(urlStr, textMap, fileMap);
        System.out.println(ret);
    }


    @Test
    public void createSupplies(){
  /*      Gson gson = new Gson();
        List<TmsTransfer> list = gson.fromJson(, new TypeToken<List<TmsTransfer>>() {
        }.getType());
        System.out.println(list);*/
        logger.debug("hello");
        //scmService.createSCMSupplierUser();
      /*  SCMQuartz scmQuartz = new SCMQuartz();
        scmQuartz.transmissionShippingOrder();*/
    }


    @Test
    public void tSuppliers(){
        int size=50;
        int  count = scmService.getSCMSupplierUserCount(size,"0");
        int page = (int) Math.ceil(count/size);
        List<SCMSupplierUser> list;
        Map<String,Object> map;
        String url = "http://103.239.152.40:6060/NSCM/supu.do?method=saveSuppUser";
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
                scmService.updateSCMSupplierUser(1,successList);
                scmService.updateSCMSupplierUser(0,errorList);
            } catch (Exception e) {
                System.out.println("出错了");
                e.printStackTrace();
            }
        }

    }

    @Test
    public void tSuppliersHead(){
        int size=25;
        String state="1";
        String url = "http://103.239.152.40:6060/NSCM/supu.do?method=saveSuppHead";
        int count = scmService.getSCMSupplierUserHeadCount(state);
        if (count==0) return;
        int page = (int) Math.ceil(count/size);
        Map<String,List<String>> map;
        for (int i=0;i<=page;i++){
            List<String> list = scmService.getSCMSupplierUserHead(i*size,size,state);
            map=new HashMap<String, List<String>>();
            map.put("userFile",list);
            String result = HttpRequesterUtils.formListUpload(url,null,map);
            logger.info(result);
        }
    }





}
