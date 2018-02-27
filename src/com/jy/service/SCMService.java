package com.jy.service;

import com.jy.model.SCMBusinessRemarkData;
import com.jy.model.SCMSupplierUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by njw on 2017/7/11.
 */
public interface SCMService {

    boolean insertSCMShippingOrder();

    int createSCMBusinessRemarkDataOrder();


    List<SCMBusinessRemarkData> getSCMBusinessRemarkData(String state, String count, Integer max_size);


    int updateSCMBusinessRemarkDataState(Integer state,List<String> dmsIds);


    int updateSCMUpdateState(Integer state,List<String> dmsIds,Integer minCount,Integer maxCount);


    int createSCMSupplierUser();


    List<SCMSupplierUser> getSCMSupplierUser(Integer page,Integer size,String state);


    int updateSCMSupplierUser(Integer state,List<String> ids);


    List<String> getSCMSupplierUserHead(Integer page,Integer size,String state);

    List<String> getSCMSupplierUserHead(Integer page,Integer size,String state,List<String> ids);

    int getSCMSupplierUserHeadCount(String state);

    int getSCMSupplierUserCount(Integer size,String state);


    int createSCMSupplierUserDay();

}
