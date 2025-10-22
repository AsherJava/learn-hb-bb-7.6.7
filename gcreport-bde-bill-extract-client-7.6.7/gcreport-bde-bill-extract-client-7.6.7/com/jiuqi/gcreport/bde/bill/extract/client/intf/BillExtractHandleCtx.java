/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.dto.fetch.init.FetchFormDTO
 */
package com.jiuqi.gcreport.bde.bill.extract.client.intf;

import com.jiuqi.bde.common.dto.fetch.init.FetchFormDTO;
import java.util.List;

public class BillExtractHandleCtx {
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
    private String fetchSchemeId;
    private List<String> billCodeList;
    private String taskId;
    private String taskTitle;
    private List<FetchFormDTO> fetchForms;
    private Boolean includeUncharged;

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

    public String getFetchSchemeId() {
        return this.fetchSchemeId;
    }

    public void setFetchSchemeId(String fetchSchemeId) {
        this.fetchSchemeId = fetchSchemeId;
    }

    public List<String> getBillCodeList() {
        return this.billCodeList;
    }

    public void setBillCodeList(List<String> billCodeList) {
        this.billCodeList = billCodeList;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskTitle() {
        return this.taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public List<FetchFormDTO> getFetchForms() {
        return this.fetchForms;
    }

    public void setFetchForms(List<FetchFormDTO> fetchForms) {
        this.fetchForms = fetchForms;
    }

    public Boolean getIncludeUncharged() {
        return this.includeUncharged;
    }

    public void setIncludeUncharged(Boolean includeUncharged) {
        this.includeUncharged = includeUncharged;
    }

    public String toString() {
        return "BillExtractMessage [requestSourceType=" + this.requestSourceType + ", username=" + this.username + ", billListDefine=" + this.billListDefine + ", billDefine=" + this.billDefine + ", billModel=" + this.billModel + ", masterTableName=" + this.masterTableName + ", rpUnitType=" + this.rpUnitType + ", unitCode=" + this.unitCode + ", startDateStr=" + this.startDateStr + ", endDateStr=" + this.endDateStr + ", fetchSchemeId=" + this.fetchSchemeId + ", billCodeList=" + this.billCodeList + ", taskId=" + this.taskId + ", taskTitle=" + this.taskTitle + ", fetchForms=" + this.fetchForms + ", includeUncharged=" + this.includeUncharged + "]";
    }
}

