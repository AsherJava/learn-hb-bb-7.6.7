/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.clbr.vo;

import java.util.Map;

public class ClbrBillCheckVO {
    private String id;
    private String billId;
    private String srcId;
    private String clbrBillCode;
    private String sysCode;
    private Integer confirmType;
    private String currency = "CNY";
    private Double clbrAmount;
    private String clbrCode;
    private String clbrTime;
    private String relation;
    private String thisRelation;
    private String oppRelation;
    private String thatRelation;
    private String clbrType;
    private Double amount;
    private String createTime;
    private Double noverifyAmount;
    private String clbrBillType;
    private String groupId;
    private Integer confirmStatus;
    private Map<String, Object> extendInfo;
    private Integer rowspan;
    private Integer index;

    public String getThisRelation() {
        return this.thisRelation;
    }

    public void setThisRelation(String thisRelation) {
        this.thisRelation = thisRelation;
    }

    public String getThatRelation() {
        return this.thatRelation;
    }

    public void setThatRelation(String thatRelation) {
        this.thatRelation = thatRelation;
    }

    public String getRelation() {
        return this.relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getOppRelation() {
        return this.oppRelation;
    }

    public void setOppRelation(String oppRelation) {
        this.oppRelation = oppRelation;
    }

    public String getClbrType() {
        return this.clbrType;
    }

    public void setClbrType(String clbrType) {
        this.clbrType = clbrType;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getBillId() {
        return this.billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public Integer getConfirmType() {
        return this.confirmType;
    }

    public void setConfirmType(Integer confirmType) {
        this.confirmType = confirmType;
    }

    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getClbrCode() {
        return this.clbrCode;
    }

    public void setClbrCode(String clbrCode) {
        this.clbrCode = clbrCode;
    }

    public String getClbrTime() {
        return this.clbrTime;
    }

    public void setClbrTime(String clbrTime) {
        this.clbrTime = clbrTime;
    }

    public String getClbrBillCode() {
        return this.clbrBillCode;
    }

    public void setClbrBillCode(String clbrBillCode) {
        this.clbrBillCode = clbrBillCode;
    }

    public String getSysCode() {
        return this.sysCode;
    }

    public void setSysCode(String sysCode) {
        this.sysCode = sysCode;
    }

    public String getSrcId() {
        return this.srcId;
    }

    public void setSrcId(String srcId) {
        this.srcId = srcId;
    }

    public Integer getRowspan() {
        return this.rowspan;
    }

    public void setRowspan(Integer rowspan) {
        this.rowspan = rowspan;
    }

    public Integer getIndex() {
        return this.index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getNoverifyAmount() {
        return this.noverifyAmount;
    }

    public void setNoverifyAmount(Double noverifyAmount) {
        this.noverifyAmount = noverifyAmount;
    }

    public String getClbrBillType() {
        return this.clbrBillType;
    }

    public void setClbrBillType(String clbrBillType) {
        this.clbrBillType = clbrBillType;
    }

    public Map<String, Object> getExtendInfo() {
        return this.extendInfo;
    }

    public void setExtendInfo(Map<String, Object> extendInfo) {
        this.extendInfo = extendInfo;
    }

    public Double getAmount() {
        return this.amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getClbrAmount() {
        return this.clbrAmount;
    }

    public void setClbrAmount(Double clbrAmount) {
        this.clbrAmount = clbrAmount;
    }

    public String getGroupId() {
        return this.groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public Integer getConfirmStatus() {
        return this.confirmStatus;
    }

    public void setConfirmStatus(Integer confirmStatus) {
        this.confirmStatus = confirmStatus;
    }
}

