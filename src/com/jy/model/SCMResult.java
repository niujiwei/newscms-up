package com.jy.model;

import java.util.List;

/**
 * Created by njw on 2017/7/12.
 */
public class SCMResult{
    private boolean flag;	//记录正确与否
    private List<String> ErrorList;//记录返回的信息
    private List<String> SuccessList;//记录返回的信息

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public List<String> getErrorList() {
        return ErrorList;
    }

    public void setErrorList(List<String> errorList) {
        ErrorList = errorList;
    }

    public List<String> getSuccessList() {
        return SuccessList;
    }

    public void setSuccessList(List<String> successList) {
        SuccessList = successList;
    }
}
