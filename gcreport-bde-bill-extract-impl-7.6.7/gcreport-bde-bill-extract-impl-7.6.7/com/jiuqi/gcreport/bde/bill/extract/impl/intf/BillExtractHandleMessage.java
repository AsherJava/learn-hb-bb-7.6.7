/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.bde.bill.extract.impl.intf;

public class BillExtractHandleMessage {
    private String bblx;
    private String requestRunnerId;
    private String requestInstcId;
    private String requestTaskId;
    private String requestSourceType;
    private String username;
    private String billListDefine;
    private String billDefine;
    private String billModel;
    private String masterTableName;
    private String rpUnitType;
    private String unitCode;
    private String startDateStr;
    private String endDateStr;
    private String billCode;
    private String fetchSchemeId;
    private Boolean includeUncharged;

    public String getBblx() {
        return this.bblx;
    }

    public void setBblx(String bblx) {
        this.bblx = bblx;
    }

    public String getRequestRunnerId() {
        return this.requestRunnerId;
    }

    public void setRequestRunnerId(String requestRunnerId) {
        this.requestRunnerId = requestRunnerId;
    }

    public String getRequestInstcId() {
        return this.requestInstcId;
    }

    public void setRequestInstcId(String requestInstcId) {
        this.requestInstcId = requestInstcId;
    }

    public String getRequestTaskId() {
        return this.requestTaskId;
    }

    public void setRequestTaskId(String requestTaskId) {
        this.requestTaskId = requestTaskId;
    }

    public String getRequestSourceType() {
        return this.requestSourceType;
    }

    public void setRequestSourceType(String requestSourceType) {
        this.requestSourceType = requestSourceType;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBillListDefine() {
        return this.billListDefine;
    }

    public void setBillListDefine(String billListDefine) {
        this.billListDefine = billListDefine;
    }

    public String getBillDefine() {
        return this.billDefine;
    }

    public void setBillDefine(String billDefine) {
        this.billDefine = billDefine;
    }

    public String getBillModel() {
        return this.billModel;
    }

    public void setBillModel(String billModel) {
        this.billModel = billModel;
    }

    public String getMasterTableName() {
        return this.masterTableName;
    }

    public void setMasterTableName(String masterTableName) {
        this.masterTableName = masterTableName;
    }

    public String getRpUnitType() {
        return this.rpUnitType;
    }

    public void setRpUnitType(String rpUnitType) {
        this.rpUnitType = rpUnitType;
    }

    public String getUnitCode() {
        return this.unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getStartDateStr() {
        return this.startDateStr;
    }

    public void setStartDateStr(String startDateStr) {
        this.startDateStr = startDateStr;
    }

    public String getEndDateStr() {
        return this.endDateStr;
    }

    public void setEndDateStr(String endDateStr) {
        this.endDateStr = endDateStr;
    }

    public String getBillCode() {
        return this.billCode;
    }

    public void setBillCode(String billCode) {
        this.billCode = billCode;
    }

    public String getFetchSchemeId() {
        return this.fetchSchemeId;
    }

    public void setFetchSchemeId(String fetchSchemeId) {
        this.fetchSchemeId = fetchSchemeId;
    }

    public Boolean getIncludeUncharged() {
        return this.includeUncharged;
    }

    public void setIncludeUncharged(Boolean includeUncharged) {
        this.includeUncharged = includeUncharged;
    }

    public String toString() {
        return "BillExtractHandleMessage [bblx=" + this.bblx + ", requestRunnerId=" + this.requestRunnerId + ", requestInstcId=" + this.requestInstcId + ", requestTaskId=" + this.requestTaskId + ", requestSourceType=" + this.requestSourceType + ", username=" + this.username + ", billListDefine=" + this.billListDefine + ", billDefine=" + this.billDefine + ", billModel=" + this.billModel + ", masterTableName=" + this.masterTableName + ", rpUnitType=" + this.rpUnitType + ", unitCode=" + this.unitCode + ", startDateStr=" + this.startDateStr + ", endDateStr=" + this.endDateStr + ", billCode=" + this.billCode + ", fetchSchemeId=" + this.fetchSchemeId + ", includeUncharged=" + this.includeUncharged + "]";
    }
}

