/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.clbr.dto;

import java.util.Date;

public class ClbrBillRejectMsgDTO {
    private String sn;
    private String sysCode;
    private String srcId;
    private String clbrBillCode;
    private String oppClbrBillCode;
    private Double amount;
    private String clbrCode;
    private Date clbrTime;
    private Double verifyedAmount;
    private Double noverifyAmount;
    private String clbrState = "reject";

    public static ClbrBillRejectMsgDTO builder() {
        return new ClbrBillRejectMsgDTO();
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

    public String getClbrState() {
        return this.clbrState;
    }

    public void setClbrState(String clbrState) {
        this.clbrState = clbrState;
    }

    public ClbrBillRejectMsgDTO amount(Double amount) {
        this.amount = amount;
        return this;
    }

    public String getSn() {
        return this.sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public ClbrBillRejectMsgDTO noverifyAmount(Double noverifyAmount) {
        this.noverifyAmount = noverifyAmount;
        return this;
    }

    public ClbrBillRejectMsgDTO verifyedAmount(Double verifyedAmount) {
        this.verifyedAmount = verifyedAmount;
        return this;
    }

    public ClbrBillRejectMsgDTO oppClbrBillCode(String oppClbrBillCode) {
        this.oppClbrBillCode = oppClbrBillCode;
        return this;
    }

    public ClbrBillRejectMsgDTO clbrBillCode(String clbrBillCode) {
        this.clbrBillCode = clbrBillCode;
        return this;
    }

    public ClbrBillRejectMsgDTO clbrCode(String clbrCode) {
        this.clbrCode = clbrCode;
        return this;
    }

    public ClbrBillRejectMsgDTO clbrTime(Date clbrTime) {
        this.clbrTime = clbrTime;
        return this;
    }

    public ClbrBillRejectMsgDTO srcId(String srcId) {
        this.srcId = srcId;
        return this;
    }

    public ClbrBillRejectMsgDTO sysCode(String sysCode) {
        this.sysCode = sysCode;
        return this;
    }

    public ClbrBillRejectMsgDTO sn(String sn) {
        this.sn = sn;
        return this;
    }
}

