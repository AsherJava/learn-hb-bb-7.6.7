/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.clbr.dto;

import java.util.Date;
import java.util.Map;

public class ClbrBillDTO {
    private String id;
    private String srcId;
    private String sysCode;
    private Integer clbrBillType;
    private Integer confirmType;
    private String relation;
    private String thisRelation;
    private String orgCode;
    private String oppRelation;
    private String thatRelation;
    private String oppOrgCode;
    private String clbrType;
    private String oppClbrType;
    private String clbrBillCode;
    private String oppClbrBillCode;
    private String oppSrcId;
    private String currency = "CNY";
    private String userName;
    private String userTitle;
    private String nextUserName;
    private String oppUserName;
    private Double amount;
    private Double verifyedAmount;
    private Double noverifyAmount;
    private String clbrSchemeId;
    private String flowControlType;
    private String vchrControlType;
    private String clbrCode;
    private Date clbrTime;
    private Date createTime;
    private Date modifyTime;
    private String rejectUserName;
    private Date rejectTime;
    private Integer billState = 0;
    private String sn;
    private Map<String, Object> extendInfo;
    private String groupId;
    private Boolean penetrationControl;
    private String arbitrationUserName;
    private String rejectionMessage;

    public String getRejectionMessage() {
        return this.rejectionMessage;
    }

    public void setRejectionMessage(String rejectionMessage) {
        this.rejectionMessage = rejectionMessage;
    }

    public String getArbitrationUserName() {
        return this.arbitrationUserName;
    }

    public void setArbitrationUserName(String arbitrationUserName) {
        this.arbitrationUserName = arbitrationUserName;
    }

    public String getGroupId() {
        return this.groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSrcId() {
        return this.srcId;
    }

    public void setSrcId(String srcId) {
        this.srcId = srcId;
    }

    public String getSysCode() {
        return this.sysCode;
    }

    public void setSysCode(String sysCode) {
        this.sysCode = sysCode;
    }

    public Integer getClbrBillType() {
        return this.clbrBillType;
    }

    public void setClbrBillType(Integer clbrBillType) {
        this.clbrBillType = clbrBillType;
    }

    public String getRelation() {
        return this.relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getOrgCode() {
        return this.orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getOppRelation() {
        return this.oppRelation;
    }

    public void setOppRelation(String oppRelation) {
        this.oppRelation = oppRelation;
    }

    public String getOppOrgCode() {
        return this.oppOrgCode;
    }

    public void setOppOrgCode(String oppOrgCode) {
        this.oppOrgCode = oppOrgCode;
    }

    public String getClbrType() {
        return this.clbrType;
    }

    public void setClbrType(String clbrType) {
        this.clbrType = clbrType;
    }

    public String getOppClbrType() {
        return this.oppClbrType;
    }

    public void setOppClbrType(String oppClbrType) {
        this.oppClbrType = oppClbrType;
    }

    public String getClbrBillCode() {
        return this.clbrBillCode;
    }

    public void setClbrBillCode(String clbrBillCode) {
        this.clbrBillCode = clbrBillCode;
    }

    public String getOppClbrBillCode() {
        return this.oppClbrBillCode;
    }

    public void setOppClbrBillCode(String oppClbrBillCode) {
        this.oppClbrBillCode = oppClbrBillCode;
    }

    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserTitle() {
        return this.userTitle;
    }

    public void setUserTitle(String userTitle) {
        this.userTitle = userTitle;
    }

    public String getNextUserName() {
        return this.nextUserName;
    }

    public void setNextUserName(String nextUserName) {
        this.nextUserName = nextUserName;
    }

    public Double getAmount() {
        return this.amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getVerifyedAmount() {
        return this.verifyedAmount;
    }

    public void setVerifyedAmount(Double verifyedAmount) {
        this.verifyedAmount = verifyedAmount;
    }

    public Double getNoverifyAmount() {
        return this.noverifyAmount;
    }

    public void setNoverifyAmount(Double noverifyAmount) {
        this.noverifyAmount = noverifyAmount;
    }

    public String getClbrSchemeId() {
        return this.clbrSchemeId;
    }

    public void setClbrSchemeId(String clbrSchemeId) {
        this.clbrSchemeId = clbrSchemeId;
    }

    public String getClbrCode() {
        return this.clbrCode;
    }

    public void setClbrCode(String clbrCode) {
        this.clbrCode = clbrCode;
    }

    public Date getClbrTime() {
        return this.clbrTime;
    }

    public void setClbrTime(Date clbrTime) {
        this.clbrTime = clbrTime;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return this.modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getRejectUserName() {
        return this.rejectUserName;
    }

    public void setRejectUserName(String rejectUserName) {
        this.rejectUserName = rejectUserName;
    }

    public Date getRejectTime() {
        return this.rejectTime;
    }

    public void setRejectTime(Date rejectTime) {
        this.rejectTime = rejectTime;
    }

    public Integer getBillState() {
        return this.billState;
    }

    public void setBillState(Integer billState) {
        this.billState = billState;
    }

    public String getSn() {
        return this.sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getOppUserName() {
        return this.oppUserName;
    }

    public void setOppUserName(String oppUserName) {
        this.oppUserName = oppUserName;
    }

    public String getOppSrcId() {
        return this.oppSrcId;
    }

    public void setOppSrcId(String oppSrcId) {
        this.oppSrcId = oppSrcId;
    }

    public String getFlowControlType() {
        return this.flowControlType;
    }

    public void setFlowControlType(String flowControlType) {
        this.flowControlType = flowControlType;
    }

    public String getVchrControlType() {
        return this.vchrControlType;
    }

    public void setVchrControlType(String vchrControlType) {
        this.vchrControlType = vchrControlType;
    }

    public Integer getConfirmType() {
        return this.confirmType;
    }

    public void setConfirmType(Integer confirmType) {
        this.confirmType = confirmType;
    }

    public Map<String, Object> getExtendInfo() {
        return this.extendInfo;
    }

    public void setExtendInfo(Map<String, Object> extendInfo) {
        this.extendInfo = extendInfo;
    }

    public String getThatRelation() {
        return this.thatRelation;
    }

    public void setThatRelation(String thatRelation) {
        this.thatRelation = thatRelation;
    }

    public String getThisRelation() {
        return this.thisRelation;
    }

    public void setThisRelation(String thisRelation) {
        this.thisRelation = thisRelation;
    }

    public Boolean getPenetrationControl() {
        return this.penetrationControl;
    }

    public void setPenetrationControl(Boolean penetrationControl) {
        this.penetrationControl = penetrationControl;
    }
}

