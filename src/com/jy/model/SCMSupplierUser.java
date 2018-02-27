package com.jy.model;

/**
 * Created by njw on 2017/7/13.
 */
public class SCMSupplierUser {
    private Integer suid;// 主键-自增
    private String susername;// 用户名
    private String suserpassword;// 密码
    private String srealname;// 真实姓名
    private Integer sdid;// 保存的部门id
    private String slasttime;// 最后登录时间
    private String createtime;// 创建时间
    private Integer state; // 数据状态         1-有效0-无效
    private String salt;// 6位随机字符串，加密用到
    private Integer supid; // 供应商id
    private String stype;//账号类型 1：供应商 ，2：司机
    private String isdefault;//是否为默认账号
    private String supname;//供应商名字
    private Integer belongid;//供应链公司id
    private String belongname,linename,lineid;//供应链公司名称;
    private String head;	//头像保存地址
    private String sex;		//性别 (男,女)
    private Integer age;	//年龄
    private String  servicetimes;	//服务时长
    private String tel;		//电话
    private String supids;	//供应商id  1,2,3,4,5,6 多个保存方式
    private Integer usable;	//配送商：0不可用状态,1可用状态
    private String pname;	//省名字
    private Integer pid;		//省id
    private String cname;		//市名字
    private Integer cid;		//市id
    private String aname;		//县名字
    private Integer aid;		//县id
    private String zname;		//镇名字
    private Integer zid;		//镇id
    private String addmin;		//最小单位
    private Integer addminid;	//最小单位id
    private String supUserId;	//配送员id
    private Integer datacome;	//0.scm 1dms
    private String image_url;//头像地址
   // private String supname;//供应商名字

    public String getServicetimes() {
        return servicetimes;
    }

    public void setServicetimes(String servicetimes) {
        this.servicetimes = servicetimes;
    }

 /*   public String getsupname() {
        return supname;
    }

    public void setsupname(String supname) {
        this.supname = supname;
    }*/

    public Integer getSuid() {
        return suid;
    }

    public void setSuid(Integer suid) {
        this.suid = suid;
    }

    public String getSusername() {
        return susername;
    }

    public void setSusername(String susername) {
        this.susername = susername;
    }

    public String getSuserpassword() {
        return suserpassword;
    }

    public void setSuserpassword(String suserpassword) {
        this.suserpassword = suserpassword;
    }

    public String getSrealname() {
        return srealname;
    }

    public void setSrealname(String srealname) {
        this.srealname = srealname;
    }

    public Integer getSdid() {
        return sdid;
    }

    public void setSdid(Integer sdid) {
        this.sdid = sdid;
    }

    public String getSlasttime() {
        return slasttime;
    }

    public void setSlasttime(String slasttime) {
        this.slasttime = slasttime;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Integer getSupid() {
        return supid;
    }

    public void setSupid(Integer supid) {
        this.supid = supid;
    }

    public String getStype() {
        return stype;
    }

    public void setStype(String stype) {
        this.stype = stype;
    }

    public String getIsdefault() {
        return isdefault;
    }

    public void setIsdefault(String isdefault) {
        this.isdefault = isdefault;
    }

    public String getsupname() {
        return supname;
    }

    public void setsupname(String supname) {
        this.supname = supname;
    }

    public Integer getBelongid() {
        return belongid;
    }

    public void setBelongid(Integer belongid) {
        this.belongid = belongid;
    }

    public String getBelongname() {
        return belongname;
    }

    public void setBelongname(String belongname) {
        this.belongname = belongname;
    }

    public String getLinename() {
        return linename;
    }

    public void setLinename(String linename) {
        this.linename = linename;
    }

    public String getLineid() {
        return lineid;
    }

    public void setLineid(String lineid) {
        this.lineid = lineid;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }


    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getSupids() {
        return supids;
    }

    public void setSupids(String supids) {
        this.supids = supids;
    }

    public Integer getUsable() {
        return usable;
    }

    public void setUsable(Integer usable) {
        this.usable = usable;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getAname() {
        return aname;
    }

    public void setAname(String aname) {
        this.aname = aname;
    }

    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }

    public String getZname() {
        return zname;
    }

    public void setZname(String zname) {
        this.zname = zname;
    }

    public Integer getZid() {
        return zid;
    }

    public void setZid(Integer zid) {
        this.zid = zid;
    }

    public String getAddmin() {
        return addmin;
    }

    public void setAddmin(String addmin) {
        this.addmin = addmin;
    }

    public Integer getAddminid() {
        return addminid;
    }

    public void setAddminid(Integer addminid) {
        this.addminid = addminid;
    }

    public String getSupUserId() {
        return supUserId;
    }

    public void setSupUserId(String supUserId) {
        this.supUserId = supUserId;
    }

    public Integer getDatacome() {
        return datacome;
    }

    public void setDatacome(Integer datacome) {
        this.datacome = datacome;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
