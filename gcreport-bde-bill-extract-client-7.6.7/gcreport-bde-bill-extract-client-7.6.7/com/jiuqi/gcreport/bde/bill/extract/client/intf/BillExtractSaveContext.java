/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.bde.bill.extract.client.intf;

public class BillExtractSaveContext {
    private String requestRunnerId;
    private String requestInstcId;
    private String requestTaskId;
    private String username;
    private String billListDefine;
    private String billDefine;
    private String billModel;
    private String masterTableName;
    private String rpUnitType;
    private String unitCode;
    private String startDateStr;
    private String endDateStr;
    private String fetchSchemeId;
    private String billCode;

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

    public String getFetchSchemeId() {
        return this.fetchSchemeId;
    }

    public void setFetchSchemeId(String fetchSchemeId) {
        this.fetchSchemeId = fetchSchemeId;
    }

    public String getBillCode() {
        return this.billCode;
    }

    public void setBillCode(String billCode) {
        this.billCode = billCode;
    }

    public String toString() {
        return "BillExtractSaveContext [requestRunnerId=" + this.requestRunnerId + ", requestInstcId=" + this.requestInstcId + ", requestTaskId=" + this.requestTaskId + ", username=" + this.username + ", billListDefine=" + this.billListDefine + ", billDefine=" + this.billDefine + ", billModel=" + this.billModel + ", masterTableName=" + this.masterTableName + ", rpUnitType=" + this.rpUnitType + ", unitCode=" + this.unitCode + ", startDateStr=" + this.startDateStr + ", endDateStr=" + this.endDateStr + ", fetchSchemeId=" + this.fetchSchemeId + ", billCode=" + this.billCode + "]";
    }
}

