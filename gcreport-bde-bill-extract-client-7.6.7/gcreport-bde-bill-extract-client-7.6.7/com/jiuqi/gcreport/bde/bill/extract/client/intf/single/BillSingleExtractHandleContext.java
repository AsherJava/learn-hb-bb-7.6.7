/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.bde.bill.setting.client.dto.BillFetchCondiDTO
 */
package com.jiuqi.gcreport.bde.bill.extract.client.intf.single;

import com.jiuqi.gcreport.bde.bill.setting.client.dto.BillFetchCondiDTO;
import java.util.Map;

public class BillSingleExtractHandleContext {
    private String requestRunnerId;
    private String requestInstcId;
    private String requestTaskId;
    private String requestSourceType;
    private String username;
    private String billDefine;
    private String billModel;
    private String billCode;
    private String fetchSchemeId;
    private String masterTableName;
    private String bblx;
    private String unitCode;
    private String startDateStr;
    private String endDateStr;
    private String currency;
    private String periodScheme;
    private String gcUnitType;
    private Map<String, String> otherEntity;
    private String dimensionSetStr;
    private Map<String, String> extParam;
    private Map<String, Object> billData;
    private BillFetchCondiDTO billFetchCondiDTO;
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

    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getGcUnitType() {
        return this.gcUnitType;
    }

    public void setGcUnitType(String gcUnitType) {
        this.gcUnitType = gcUnitType;
    }

    public String getRequestTaskId() {
        return this.requestTaskId;
    }

    public void setRequestTaskId(String requestTaskId) {
        this.requestTaskId = requestTaskId;
    }

    public String getPeriodScheme() {
        return this.periodScheme;
    }

    public void setPeriodScheme(String periodScheme) {
        this.periodScheme = periodScheme;
    }

    public Map<String, String> getOtherEntity() {
        return this.otherEntity;
    }

    public void setOtherEntity(Map<String, String> otherEntity) {
        this.otherEntity = otherEntity;
    }

    public String getDimensionSetStr() {
        return this.dimensionSetStr;
    }

    public void setDimensionSetStr(String dimensionSetStr) {
        this.dimensionSetStr = dimensionSetStr;
    }

    public Map<String, String> getExtParam() {
        return this.extParam;
    }

    public void setExtParam(Map<String, String> extParam) {
        this.extParam = extParam;
    }

    public Map<String, Object> getBillData() {
        return this.billData;
    }

    public void setBillData(Map<String, Object> billData) {
        this.billData = billData;
    }

    public BillFetchCondiDTO getBillFetchCondiDTO() {
        return this.billFetchCondiDTO;
    }

    public void setBillFetchCondiDTO(BillFetchCondiDTO billFetchCondiDTO) {
        this.billFetchCondiDTO = billFetchCondiDTO;
    }
}

