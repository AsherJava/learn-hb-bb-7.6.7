/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.clbr.dto;

import java.util.Date;

public class ClbrBillConfirmMsgDTO {
    private String sn;
    private String sysCode;
    private String oppSysCode;
    private String srcId;
    private String oppSrcId;
    private String clbrBillCode;
    private String oppClbrBillCode;
    private Double amount;
    private String clbrCode;
    private Date clbrTime;
    private Double verifyedAmount;
    private Double noverifyAmount;
    private Double currentAmount = 0.0;

    public static ClbrBillConfirmMsgDTO builder() {
        return new ClbrBillConfirmMsgDTO();
    }

    public String getSysCode() {
        return this.sysCode;
    }

    public void setSysCode(String sysCode) {
        this.sysCode = sysCode;
    }

    public String getOppSysCode() {
        return this.oppSysCode;
    }

    public void setOppSysCode(String oppSysCode) {
        this.oppSysCode = oppSysCode;
    }

    public String getSrcId() {
        return this.srcId;
    }

    public void setSrcId(String srcId) {
        this.srcId = srcId;
    }

    public String getOppSrcId() {
        return this.oppSrcId;
    }

    public void setOppSrcId(String oppSrcId) {
        this.oppSrcId = oppSrcId;
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

    public Double getAmount() {
        return this.amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
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

    public Double getCurrentAmount() {
        return this.currentAmount;
    }

    public void setCurrentAmount(Double currentAmount) {
        this.currentAmount = currentAmount;
    }

    public String getSn() {
        return this.sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public ClbrBillConfirmMsgDTO amount(Double amount) {
        this.amount = amount;
        return this;
    }

    public ClbrBillConfirmMsgDTO noverifyAmount(Double noverifyAmount) {
        this.noverifyAmount = noverifyAmount;
        return this;
    }

    public ClbrBillConfirmMsgDTO verifyedAmount(Double verifyedAmount) {
        this.verifyedAmount = verifyedAmount;
        return this;
    }

    public ClbrBillConfirmMsgDTO currentAmount(Double currentAmount) {
        this.currentAmount = currentAmount;
        return this;
    }

    public ClbrBillConfirmMsgDTO oppClbrBillCode(String oppClbrBillCode) {
        this.oppClbrBillCode = oppClbrBillCode;
        return this;
    }

    public ClbrBillConfirmMsgDTO clbrBillCode(String clbrBillCode) {
        this.clbrBillCode = clbrBillCode;
        return this;
    }

    public ClbrBillConfirmMsgDTO clbrCode(String clbrCode) {
        this.clbrCode = clbrCode;
        return this;
    }

    public ClbrBillConfirmMsgDTO clbrTime(Date clbrTime) {
        this.clbrTime = clbrTime;
        return this;
    }

    public ClbrBillConfirmMsgDTO srcId(String srcId) {
        this.srcId = srcId;
        return this;
    }

    public ClbrBillConfirmMsgDTO oppSrcId(String oppSrcId) {
        this.oppSrcId = oppSrcId;
        return this;
    }

    public ClbrBillConfirmMsgDTO sysCode(String sysCode) {
        this.sysCode = sysCode;
        return this;
    }

    public ClbrBillConfirmMsgDTO oppSysCode(String oppSysCode) {
        this.oppSysCode = oppSysCode;
        return this;
    }

    public ClbrBillConfirmMsgDTO sn(String sn) {
        this.sn = sn;
        return this;
    }
}

