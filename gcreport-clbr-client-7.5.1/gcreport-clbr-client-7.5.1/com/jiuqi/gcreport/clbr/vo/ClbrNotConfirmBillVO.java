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

public class ClbrNotConfirmBillVO {
    private String id;
    private String clbrCode;
    private String clbrBillCode;
    private String clbrTypeTitle;
    private String relationTitle;
    private String oppRelationTitle;
    private String currencyTitle;
    private Double amount;
    private Double verifyedAmount;
    private Double noverifyAmount;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd", timezone="GMT+8")
    private Date createTime;
    private Map<String, Object> extendInfo;
    private Boolean penetrationControl;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClbrBillCode() {
        return this.clbrBillCode;
    }

    public void setClbrBillCode(String clbrBillCode) {
        this.clbrBillCode = clbrBillCode;
    }

    public String getClbrTypeTitle() {
        return this.clbrTypeTitle;
    }

    public void setClbrTypeTitle(String clbrTypeTitle) {
        this.clbrTypeTitle = clbrTypeTitle;
    }

    public String getRelationTitle() {
        return this.relationTitle;
    }

    public void setRelationTitle(String relationTitle) {
        this.relationTitle = relationTitle;
    }

    public String getOppRelationTitle() {
        return this.oppRelationTitle;
    }

    public void setOppRelationTitle(String oppRelationTitle) {
        this.oppRelationTitle = oppRelationTitle;
    }

    public String getCurrencyTitle() {
        return this.currencyTitle;
    }

    public void setCurrencyTitle(String currencyTitle) {
        this.currencyTitle = currencyTitle;
    }

    public Double getNoverifyAmount() {
        return this.noverifyAmount;
    }

    public void setNoverifyAmount(Double noverifyAmount) {
        this.noverifyAmount = noverifyAmount;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public String getClbrCode() {
        return this.clbrCode;
    }

    public void setClbrCode(String clbrCode) {
        this.clbrCode = clbrCode;
    }

    public Map<String, Object> getExtendInfo() {
        return this.extendInfo;
    }

    public void setExtendInfo(Map<String, Object> extendInfo) {
        this.extendInfo = extendInfo;
    }

    public Boolean getPenetrationControl() {
        return this.penetrationControl;
    }

    public void setPenetrationControl(Boolean penetrationControl) {
        this.penetrationControl = penetrationControl;
    }
}

