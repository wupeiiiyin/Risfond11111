package com.risfond.rnss.entry;

import java.util.List;

/**
 * Created by Abbott on 2017/5/18.
 */

public class WorkOrderList {

    /**
     * Id : 1007
     * ClientId : null
     * InCallTime : 2017-10-09T15:50:00
     * Location : 03
     * LocationDefinition : 河北
     * ConsultationType : 1
     * ConsultationTypeDefinition : 电话咨询
     * Industry : 21
     * IndustryDefinition : 耐用消费品(服饰/纺织/皮革/家具)
     * CompanyName : 中启计量体系认证中心
     * LinkMan : 666
     * LinkPhone : 18066666123
     * JobDescription : 备注：企业通过APP注册提交的招聘需求，请尽快受理.职位：999
     * IsOffer : 0
     * AddTime : 2017-10-17T17:49:34.517
     * CreatorStaffId : 2
     * CreatorName : 黄小平
     * CreatorCompany : 1
     * Status : 2
     * StatusDefinition : 已接收
     * WorkOrderStatusRemark : null
     * WorkOrderStatusRemarkDefinition : null
     * OtherReason : null
     * CounselorId : 26
     * CounselorName : 何鑫
     * CounselorCompany : 2
     * SignInTime : 2017-10-18T17:54:41.43
     * HandleTime : null
     * IsVerify : 1
     * BilledState : 1
     */

    private int Id;
    private Object ClientId;
    private String InCallTime;
    private String InCallTimeStr;
    private String Location;
    private String LocationDefinition;
    private int ConsultationType;
    private String ConsultationTypeDefinition;
    private String Industry;
    private String IndustryDefinition;
    private String CompanyName;
    private String LinkMan;
    private String LinkPhone;
    private String JobDescription;
    private int IsOffer;
    private String AddTime;
    private String AddTimeStr;
    private int CreatorStaffId;
    private String CreatorName;
    private int CreatorCompany;
    private int Status;
    private String StatusDefinition;
    private Object WorkOrderStatusRemark;
    private Object WorkOrderStatusRemarkDefinition;
    private Object OtherReason;
    private int CounselorId;
    private String CounselorName;
    private int CounselorCompany;
    private String SignInTime;
    private String SignInTimeStr;
    private Object HandleTime;
    private Object HandleTimeStr;
    private int IsVerify;
    private int BilledState;

    public String getInCallTimeStr() {
        return InCallTimeStr;
    }

    public void setInCallTimeStr(String inCallTimeStr) {
        InCallTimeStr = inCallTimeStr;
    }

    public String getAddTimeStr() {
        return AddTimeStr;
    }

    public void setAddTimeStr(String addTimeStr) {
        AddTimeStr = addTimeStr;
    }

    public String getSignInTimeStr() {
        return SignInTimeStr;
    }

    public void setSignInTimeStr(String signInTimeStr) {
        SignInTimeStr = signInTimeStr;
    }

    public Object getHandleTimeStr() {
        return HandleTimeStr;
    }

    public void setHandleTimeStr(Object handleTimeStr) {
        HandleTimeStr = handleTimeStr;
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public Object getClientId() {
        return ClientId;
    }

    public void setClientId(Object ClientId) {
        this.ClientId = ClientId;
    }

    public String getInCallTime() {
        return InCallTime;
    }

    public void setInCallTime(String InCallTime) {
        this.InCallTime = InCallTime;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String Location) {
        this.Location = Location;
    }

    public String getLocationDefinition() {
        return LocationDefinition;
    }

    public void setLocationDefinition(String LocationDefinition) {
        this.LocationDefinition = LocationDefinition;
    }

    public int getConsultationType() {
        return ConsultationType;
    }

    public void setConsultationType(int ConsultationType) {
        this.ConsultationType = ConsultationType;
    }

    public String getConsultationTypeDefinition() {
        return ConsultationTypeDefinition;
    }

    public void setConsultationTypeDefinition(String ConsultationTypeDefinition) {
        this.ConsultationTypeDefinition = ConsultationTypeDefinition;
    }

    public String getIndustry() {
        return Industry;
    }

    public void setIndustry(String Industry) {
        this.Industry = Industry;
    }

    public String getIndustryDefinition() {
        return IndustryDefinition;
    }

    public void setIndustryDefinition(String IndustryDefinition) {
        this.IndustryDefinition = IndustryDefinition;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String CompanyName) {
        this.CompanyName = CompanyName;
    }

    public String getLinkMan() {
        return LinkMan;
    }

    public void setLinkMan(String LinkMan) {
        this.LinkMan = LinkMan;
    }

    public String getLinkPhone() {
        return LinkPhone;
    }

    public void setLinkPhone(String LinkPhone) {
        this.LinkPhone = LinkPhone;
    }

    public String getJobDescription() {
        return JobDescription;
    }

    public void setJobDescription(String JobDescription) {
        this.JobDescription = JobDescription;
    }

    public int getIsOffer() {
        return IsOffer;
    }

    public void setIsOffer(int IsOffer) {
        this.IsOffer = IsOffer;
    }

    public String getAddTime() {
        return AddTime;
    }

    public void setAddTime(String AddTime) {
        this.AddTime = AddTime;
    }

    public int getCreatorStaffId() {
        return CreatorStaffId;
    }

    public void setCreatorStaffId(int CreatorStaffId) {
        this.CreatorStaffId = CreatorStaffId;
    }

    public String getCreatorName() {
        return CreatorName;
    }

    public void setCreatorName(String CreatorName) {
        this.CreatorName = CreatorName;
    }

    public int getCreatorCompany() {
        return CreatorCompany;
    }

    public void setCreatorCompany(int CreatorCompany) {
        this.CreatorCompany = CreatorCompany;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int Status) {
        this.Status = Status;
    }

    public String getStatusDefinition() {
        return StatusDefinition;
    }

    public void setStatusDefinition(String StatusDefinition) {
        this.StatusDefinition = StatusDefinition;
    }

    public Object getWorkOrderStatusRemark() {
        return WorkOrderStatusRemark;
    }

    public void setWorkOrderStatusRemark(Object WorkOrderStatusRemark) {
        this.WorkOrderStatusRemark = WorkOrderStatusRemark;
    }

    public Object getWorkOrderStatusRemarkDefinition() {
        return WorkOrderStatusRemarkDefinition;
    }

    public void setWorkOrderStatusRemarkDefinition(Object WorkOrderStatusRemarkDefinition) {
        this.WorkOrderStatusRemarkDefinition = WorkOrderStatusRemarkDefinition;
    }

    public Object getOtherReason() {
        return OtherReason;
    }

    public void setOtherReason(Object OtherReason) {
        this.OtherReason = OtherReason;
    }

    public int getCounselorId() {
        return CounselorId;
    }

    public void setCounselorId(int CounselorId) {
        this.CounselorId = CounselorId;
    }

    public String getCounselorName() {
        return CounselorName;
    }

    public void setCounselorName(String CounselorName) {
        this.CounselorName = CounselorName;
    }

    public int getCounselorCompany() {
        return CounselorCompany;
    }

    public void setCounselorCompany(int CounselorCompany) {
        this.CounselorCompany = CounselorCompany;
    }

    public String getSignInTime() {
        return SignInTime;
    }

    public void setSignInTime(String SignInTime) {
        this.SignInTime = SignInTime;
    }

    public Object getHandleTime() {
        return HandleTime;
    }

    public void setHandleTime(Object HandleTime) {
        this.HandleTime = HandleTime;
    }

    public int getIsVerify() {
        return IsVerify;
    }

    public void setIsVerify(int IsVerify) {
        this.IsVerify = IsVerify;
    }

    public int getBilledState() {
        return BilledState;
    }

    public void setBilledState(int BilledState) {
        this.BilledState = BilledState;
    }
}
