/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.clbr.dto;

public class ClbrBillPushItemResultDTO {
    private String clbrBillCode;
    private String srcId;
    private String clbrCode;
    private String message;
    private Boolean status = Boolean.FALSE;

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

    public String getClbrCode() {
        return this.clbrCode;
    }

    public void setClbrCode(String clbrCode) {
        this.clbrCode = clbrCode;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getStatus() {
        return this.status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public static ClbrBillPushItemResultDTO builder() {
        return new ClbrBillPushItemResultDTO();
    }

    public ClbrBillPushItemResultDTO clbrBillCode(String clbrBillCode) {
        this.clbrBillCode = clbrBillCode;
        return this;
    }

    public ClbrBillPushItemResultDTO status(Boolean status) {
        this.status = status;
        return this;
    }

    public ClbrBillPushItemResultDTO clbrCode(String clbrCode) {
        this.clbrCode = clbrCode;
        return this;
    }

    public ClbrBillPushItemResultDTO message(String message) {
        this.message = message;
        return this;
    }

    public ClbrBillPushItemResultDTO srcId(String srcId) {
        this.srcId = srcId;
        return this;
    }
}

