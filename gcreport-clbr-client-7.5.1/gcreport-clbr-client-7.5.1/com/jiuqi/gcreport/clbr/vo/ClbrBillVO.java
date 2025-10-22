/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 */
package com.jiuqi.gcreport.clbr.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import java.util.Map;
import org.springframework.format.annotation.DateTimeFormat;

public class ClbrBillVO {
    private String id;
    private Integer confirmType;
    private String clbrCode;
    private String clbrBillCode;
    private String currency = "CNY";
    private String clbrType;
    private String clbrTypeTitle;
    private String oppClbrType;
    private String oppClbrTypeTitle;
    private String relation;
    private String thisRelation;
    private String thatRelation;
    private String relationTitle;
    private String oppRelation;
    private String oppRelationTitle;
    private Double amount;
    private Double verifyedAmount;
    private Double noverifyAmount;
    private String clbrUserName;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd", timezone="GMT+8")
    private Date clbrTime;
    private String rejectUserName;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd", timezone="GMT+8")
    private Date rejectTime;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd", timezone="GMT+8")
    private Date createTime;
    private String oppClbrBillCode;
    private Map<String, Object> extendInfo;

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

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClbrCode() {
        return this.clbrCode;
    }

    public void setClbrCode(String clbrCode) {
        this.clbrCode = clbrCode;
    }

    public String getClbrBillCode() {
        return this.clbrBillCode;
    }

    public void setClbrBillCode(String clbrBillCode) {
        this.clbrBillCode = clbrBillCode;
    }

    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getClbrType() {
        return this.clbrType;
    }

    public void setClbrType(String clbrType) {
        this.clbrType = clbrType;
    }

    public String getClbrTypeTitle() {
        return this.clbrTypeTitle;
    }

    public void setClbrTypeTitle(String clbrTypeTitle) {
        this.clbrTypeTitle = clbrTypeTitle;
    }

    public String getOppClbrType() {
        return this.oppClbrType;
    }

    public void setOppClbrType(String oppClbrType) {
        this.oppClbrType = oppClbrType;
    }

    public String getOppClbrTypeTitle() {
        return this.oppClbrTypeTitle;
    }

    public void setOppClbrTypeTitle(String oppClbrTypeTitle) {
        this.oppClbrTypeTitle = oppClbrTypeTitle;
    }

    public String getRelation() {
        return this.relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getRelationTitle() {
        return this.relationTitle;
    }

    public void setRelationTitle(String relationTitle) {
        this.relationTitle = relationTitle;
    }

    public String getOppRelation() {
        return this.oppRelation;
    }

    public void setOppRelation(String oppRelation) {
        this.oppRelation = oppRelation;
    }

    public String getOppRelationTitle() {
        return this.oppRelationTitle;
    }

    public void setOppRelationTitle(String oppRelationTitle) {
        this.oppRelationTitle = oppRelationTitle;
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

    public String getClbrUserName() {
        return this.clbrUserName;
    }

    public void setClbrUserName(String clbrUserName) {
        this.clbrUserName = clbrUserName;
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

    public String getOppClbrBillCode() {
        return this.oppClbrBillCode;
    }

    public void setOppClbrBillCode(String oppClbrBillCode) {
        this.oppClbrBillCode = oppClbrBillCode;
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
}

