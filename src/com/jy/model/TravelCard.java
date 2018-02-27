package com.jy.model;

import java.util.Date;
/**
 * 固定车辆信息
 * @author lx
 */
public class TravelCard {
	private TransportCard tran;//道路运输证
	
	private Registration reg;//注册登记证
	
	private CarInvoice invoice;//统一发票
	
	private CarMaintenance  main;//二级维护卡
	private Insurance insu;//保险信息
    private String travelCardId;//行驶证ID
    
//    private String tId;//道路运输ID
//    
//    private String rId;//注册登记ID
//    
//    private String inId;//发票ID
//    
//    private String mId;//二级维护ID
    private int state;//状态
    private String plateNumber;//车牌号码

    private String carOwner;//所有人
    private String user_idcard;//身份证
    private String tels;//联系方式
    private String boxType;//厢型
    private String length_name;//箱型名字
    private String departmentId;//所属部门ID
	private Integer front;//是否车头
	
    private Double tractionWeight;//牵引总质量

    private Double equipmentWeight;//整备质量

    private Double carWeight;//总重量

    private Double inlineHeight;//内廓高

    private Double inlineWidth;//内廓宽

    private Double inlineLength;//内廓长

    private Double outlineHeight;//外廓高

    private Double outlineWidth;//外廓宽

    private Double outlineLength;//外廓长

    private Double approveWeight;//核载重量

    private Integer approveNumber;//核定人数

    private String fileNumber;//档案编号

    private String travelCertificateDate;//发证日期

    private String registrationDate;//注册日期

    private String engineNumber;//发动机号

    private String identificationCode;//识别代码

    private String brand;//品牌型号

    private String usingProperties;//使用性质

    private String address;//地址

    private String carType;//车辆类型

    private String carModel;//车型
    
    private String notes;//备注
    
    private String inspectionRecords;//检查记录
    
    private String user;//所属用户
    private String tonnage;
    private String carLength;
    private String d;//日期
    
    
//    public String gettId() {
//		return tId;
//	}
//
//	public void settId(String tId) {
//		this.tId = tId;
//	}
//
//	public String getrId() {
//		return rId;
//	}
//
//	public void setrId(String rId) {
//		this.rId = rId;
//	}
//
//	public String getInId() {
//		return inId;
//	}
//
//	public void setInId(String inId) {
//		this.inId = inId;
//	}
//
//	public String getmId() {
//		return mId;
//	}
//
//	public void setmId(String mId) {
//		this.mId = mId;
//	}
 
	
	public String getD() {
		return d;
	}

	public void setD(String d) {
		this.d = d;
	}

	public String getCarLength() {
		return carLength;
	}

	public void setCarLength(String carLength) {
		this.carLength = carLength;
	}

	public String getTonnage() {
		return tonnage;
	}

	public Insurance getInsu() {
		return insu;
	}

	public void setInsu(Insurance insu) {
		this.insu = insu;
	}

	public void setTonnage(String tonnage) {
		this.tonnage = tonnage;
	}

	public String getLength_name() {
		return length_name;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getTels() {
		return tels;
	}

	public void setTels(String tels) {
		this.tels = tels;
	}


	public void setLength_name(String length_name) {
		this.length_name = length_name;
	}

	public String getUser() {
		return user;
	}

	public String getUser_idcard() {
		return user_idcard;
	}

	public void setUser_idcard(String user_idcard) {
		this.user_idcard = user_idcard;
	}

	public String getBoxType() {
		return boxType;
	}

	public void setBoxType(String boxType) {
		this.boxType = boxType;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public Registration getReg() {
		return reg;
	}

	public void setReg(Registration reg) {
		this.reg = reg;
	}

	public CarInvoice getInvoice() {
		return invoice;
	}

	public void setInvoice(CarInvoice invoice) {
		this.invoice = invoice;
	}

	public CarMaintenance getMain() {
		return main;
	}

	public void setMain(CarMaintenance main) {
		this.main = main;
	}

	public TransportCard getTran() {
		return tran;
	}

	public void setTran(TransportCard tran) {
		this.tran = tran;
	}

	public String getTravelCardId() {
        return travelCardId;
    }

    public void setTravelCardId(String travelCardId) {
        this.travelCardId = travelCardId == null ? null : travelCardId.trim();
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber == null ? null : plateNumber.trim();
    }

    public String getCarOwner() {
        return carOwner;
    }

    public void setCarOwner(String carOwner) {
        this.carOwner = carOwner == null ? null : carOwner.trim();
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getFront() {
        return front;
    }

    public void setFront(Integer front) {
        this.front = front;
    }


    public Double getInlineHeight() {
        return inlineHeight;
    }

    public void setInlineHeight(Double inlineHeight) {
        this.inlineHeight = inlineHeight;
    }

    public Double getInlineWidth() {
        return inlineWidth;
    }

    public void setInlineWidth(Double inlineWidth) {
        this.inlineWidth = inlineWidth;
    }

    public Double getInlineLength() {
        return inlineLength;
    }

    public void setInlineLength(Double inlineLength) {
        this.inlineLength = inlineLength;
    }

    public Double getOutlineHeight() {
        return outlineHeight;
    }

    public void setOutlineHeight(Double outlineHeight) {
        this.outlineHeight = outlineHeight;
    }

    public Double getOutlineWidth() {
        return outlineWidth;
    }

    public void setOutlineWidth(Double outlineWidth) {
        this.outlineWidth = outlineWidth;
    }

    public Double getOutlineLength() {
        return outlineLength;
    }

    public void setOutlineLength(Double outlineLength) {
        this.outlineLength = outlineLength;
    }

    public Double getTractionWeight() {
		return tractionWeight;
	}

	public void setTractionWeight(Double tractionWeight) {
		this.tractionWeight = tractionWeight;
	}

	public Double getEquipmentWeight() {
		return equipmentWeight;
	}

	public void setEquipmentWeight(Double equipmentWeight) {
		this.equipmentWeight = equipmentWeight;
	}

	public Double getCarWeight() {
		return carWeight;
	}

	public void setCarWeight(Double carWeight) {
		this.carWeight = carWeight;
	}

	public Double getApproveWeight() {
		return approveWeight;
	}

	public void setApproveWeight(Double approveWeight) {
		this.approveWeight = approveWeight;
	}

	public Integer getApproveNumber() {
        return approveNumber;
    }

    public void setApproveNumber(Integer approveNumber) {
        this.approveNumber = approveNumber;
    }

    public String getFileNumber() {
        return fileNumber;
    }

    public void setFileNumber(String fileNumber) {
        this.fileNumber = fileNumber == null ? null : fileNumber.trim();
    }

   


	public String getTravelCertificateDate() {
		return travelCertificateDate;
	}

	public void setTravelCertificateDate(String travelCertificateDate) {
		this.travelCertificateDate = travelCertificateDate;
	}

	public String getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(String registrationDate) {
		this.registrationDate = registrationDate;
	}

	public String getEngineNumber() {
        return engineNumber;
    }

    public void setEngineNumber(String engineNumber) {
        this.engineNumber = engineNumber == null ? null : engineNumber.trim();
    }

    public String getIdentificationCode() {
        return identificationCode;
    }

    public void setIdentificationCode(String identificationCode) {
        this.identificationCode = identificationCode == null ? null : identificationCode.trim();
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand == null ? null : brand.trim();
    }

    public String getUsingProperties() {
        return usingProperties;
    }

    public void setUsingProperties(String usingProperties) {
        this.usingProperties = usingProperties == null ? null : usingProperties.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType == null ? null : carType.trim();
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel == null ? null : carModel.trim();
    }

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getInspectionRecords() {
		return inspectionRecords;
	}

	public void setInspectionRecords(String inspectionRecords) {
		this.inspectionRecords = inspectionRecords;
	}
    
}