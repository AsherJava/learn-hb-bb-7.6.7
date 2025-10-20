/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.clbr.dto;

import java.util.Date;

public class ClbrBillCancelMsgDTO {
    private String sysCode;
    private String srcId;
    private String oppSrcId;
    private String clbrBillCode;
    private String oppClbrBillCode;
    private String clbrCode;
    private Date clbrTime;
    private Integer confirmType;
    private Double currentAmount = 0.0;
    private Integer cancelType;

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

    public String getClbrBillCode() {
        return this.clbrBillCode;
    }

    public void setClbrBillCode(String clbrBillCode) {
        this.clbrBillCode = clbrBillCode;
    }

    public void setClbrCode(String clbrCode) {
        this.clbrCode = clbrCode;
    }

    public String getClbrCode() {
        return this.clbrCode;
    }

    public Double getCurrentAmount() {
        return this.currentAmount;
    }

    public void setCurrentAmount(Double currentAmount) {
        this.currentAmount = currentAmount;
    }

    public String getOppSrcId() {
        return this.oppSrcId;
    }

    public void setOppSrcId(String oppSrcId) {
        this.oppSrcId = oppSrcId;
    }

    public String getOppClbrBillCode() {
        return this.oppClbrBillCode;
    }

    public void setOppClbrBillCode(String oppClbrBillCode) {
        this.oppClbrBillCode = oppClbrBillCode;
    }

    public Date getClbrTime() {
        return this.clbrTime;
    }

    public void setClbrTime(Date clbrTime) {
        this.clbrTime = clbrTime;
    }

    public Integer getConfirmType() {
        return this.confirmType;
    }

    public void setConfirmType(Integer confirmType) {
        this.confirmType = confirmType;
    }

    public Integer getCancelType() {
        return this.cancelType;
    }

    public void setCancelType(Integer cancelType) {
        this.cancelType = cancelType;
    }
}

