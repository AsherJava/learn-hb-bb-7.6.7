/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.bde.bill.extract.client.vo;

public class BillExtractItemLogVO {
    private String requestTaskId;
    private String log;
    private String unitCode;
    private String unitName;
    private Boolean success;

    public String getRequestTaskId() {
        return this.requestTaskId;
    }

    public void setRequestTaskId(String requestTaskId) {
        this.requestTaskId = requestTaskId;
    }

    public String getLog() {
        return this.log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public String getUnitCode() {
        return this.unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getUnitName() {
        return this.unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public Boolean getSuccess() {
        return this.success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String toString() {
        return "BillExtractItemLogVO [requestTaskId=" + this.requestTaskId + ", log=" + this.log + ", unitCode=" + this.unitCode + ", unitName=" + this.unitName + ", success=" + this.success + "]";
    }
}

