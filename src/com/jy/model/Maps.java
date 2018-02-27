package com.jy.model;

/**
 * 
 * @author hqh
 * 
 * @createtime 2015-6-5
 * @注释：全图监控
 */
public class Maps {
	private String monitorid;// id
	private String lng;
	private String lat;
	private String carid;// 汽车id
	private String beidouid;// 设备id
	private String nowspace;// 里程数
	private String nowdate;// 定位时间
	private String state;// 运行，静止，离线
	private String departmentid;// 部门id
	private String gpsstate;// 运行速度/或显示设备关闭
	private String angle;// 角度
	private String inssj;// 创建时间
	private int isspeed;// 是否超速：0未超速，1已超速
	private String oldLng;// 原始经度
	private String oldLat;// 原始纬度

	private String driverId;// 司机id
	private String signtime;// 分配时间
	private String receivetime;// 接单时间
	
	private String address;//司机地址
	private String flag;//用户类型

	// 后加入--做定时器 修改车辆状态使用的属性
	private int timesmin;
	// 临时属性
	private String drivename;// 司机名字
	private String drivenumber;// 司机电话
	private String carno;// 车牌号
	private String deptname;// 部门名字
	private String beidouno;// 设备号
	
	

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getSigntime() {
		return signtime;
	}

	public void setSigntime(String signtime) {
		this.signtime = signtime;
	}

	public String getReceivetime() {
		return receivetime;
	}

	public void setReceivetime(String receivetime) {
		this.receivetime = receivetime;
	}

	public String getDriverId() {
		return driverId;
	}

	public void setDriverId(String driverId) {
		this.driverId = driverId;
	}

	public String getInssj() {
		return inssj;
	}

	public void setInssj(String inssj) {
		this.inssj = inssj;
	}

	public int getIsspeed() {
		return isspeed;
	}

	public void setIsspeed(int isspeed) {
		this.isspeed = isspeed;
	}

	public String getAngle() {
		return angle;
	}

	public void setAngle(String angle) {
		this.angle = angle;
	}

	public int getTimesmin() {
		return timesmin;
	}

	public void setTimesmin(int timesmin) {
		this.timesmin = timesmin;
	}

	// 后加入--回放停留点信息
	/*
	 * private String sitestarttime; private String siteendtime; private String
	 * sitetimelong; private String sitelng; private String sitelat;
	 * 
	 * 
	 * public String getSitestarttime() { return sitestarttime; }
	 * 
	 * public void setSitestarttime(String sitestarttime) { this.sitestarttime =
	 * sitestarttime; }
	 * 
	 * public String getSiteendtime() { return siteendtime; }
	 * 
	 * public void setSiteendtime(String siteendtime) { this.siteendtime =
	 * siteendtime; }
	 * 
	 * public String getSitetimelong() { return sitetimelong; }
	 * 
	 * public void setSitetimelong(String sitetimelong) { this.sitetimelong =
	 * sitetimelong; }
	 * 
	 * public String getSitelng() { return sitelng; }
	 * 
	 * public void setSitelng(String sitelng) { this.sitelng = sitelng; }
	 * 
	 * public String getSitelat() { return sitelat; }
	 * 
	 * public void setSitelat(String sitelat) { this.sitelat = sitelat; }
	 */

	public String getBeidouno() {
		return beidouno;
	}

	public void setBeidouno(String beidouno) {
		this.beidouno = beidouno;
	}

	public String getDrivename() {
		return drivename;
	}

	public void setDrivename(String drivename) {
		this.drivename = drivename;
	}

	public String getDrivenumber() {
		return drivenumber;
	}

	public void setDrivenumber(String drivenumber) {
		this.drivenumber = drivenumber;
	}

	public String getCarno() {
		return carno;
	}

	public void setCarno(String carno) {
		this.carno = carno;
	}

	public String getDeptname() {
		return deptname;
	}

	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}

	public String getMonitorid() {
		return monitorid;
	}

	public void setMonitorid(String monitorid) {
		this.monitorid = monitorid;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getCarid() {
		return carid;
	}

	public void setCarid(String carid) {
		this.carid = carid;
	}

	public String getBeidouid() {
		return beidouid;
	}

	public void setBeidouid(String beidouid) {
		this.beidouid = beidouid;
	}

	public String getNowspace() {
		return nowspace;
	}

	public void setNowspace(String nowspace) {
		this.nowspace = nowspace;
	}

	public String getNowdate() {
		return nowdate;
	}

	public void setNowdate(String nowdate) {
		this.nowdate = nowdate;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getDepartmentid() {
		return departmentid;
	}

	public void setDepartmentid(String departmentid) {
		this.departmentid = departmentid;
	}

	public String getGpsstate() {
		return gpsstate;
	}

	public void setGpsstate(String gpsstate) {
		this.gpsstate = gpsstate;
	}

	public String getOldLng() {
		return oldLng;
	}

	public void setOldLng(String oldLng) {
		this.oldLng = oldLng;
	}

	public String getOldLat() {
		return oldLat;
	}

	public void setOldLat(String oldLat) {
		this.oldLat = oldLat;
	}
}
