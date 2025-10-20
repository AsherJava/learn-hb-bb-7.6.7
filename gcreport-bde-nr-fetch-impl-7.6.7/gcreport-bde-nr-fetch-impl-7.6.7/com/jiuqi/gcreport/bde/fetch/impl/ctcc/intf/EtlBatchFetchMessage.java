/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.bde.fetch.impl.ctcc.intf;

import java.util.List;

public class EtlBatchFetchMessage {
    private String taskKey;
    private String taskCode;
    private String taskTitle;
    private String schemeKey;
    private String dataTime;
    private String currency;
    private String orgType;
    private String formKey;
    private String formCode;
    private String formTitle;
    private String etlTaskName;
    private String etlTaskId;
    private List<String> unitCodes;

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getTaskCode() {
        return this.taskCode;
    }

    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }

    public String getTaskTitle() {
        return this.taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getSchemeKey() {
        return this.schemeKey;
    }

    public void setSchemeKey(String schemeKey) {
        this.schemeKey = schemeKey;
    }

    public String getDataTime() {
        return this.dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }

    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getOrgType() {
        return this.orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getFormCode() {
        return this.formCode;
    }

    public void setFormCode(String formCode) {
        this.formCode = formCode;
    }

    public String getFormTitle() {
        return this.formTitle;
    }

    public void setFormTitle(String formTitle) {
        this.formTitle = formTitle;
    }

    public String getEtlTaskName() {
        return this.etlTaskName;
    }

    public void setEtlTaskName(String etlTaskName) {
        this.etlTaskName = etlTaskName;
    }

    public String getEtlTaskId() {
        return this.etlTaskId;
    }

    public void setEtlTaskId(String etlTaskId) {
        this.etlTaskId = etlTaskId;
    }

    public List<String> getUnitCodes() {
        return this.unitCodes;
    }

    public void setUnitCodes(List<String> unitCodes) {
        this.unitCodes = unitCodes;
    }

    public String toString() {
        return "EtlBatchFetchMessage [taskKey=" + this.taskKey + ", taskCode=" + this.taskCode + ", taskTitle=" + this.taskTitle + ", schemeKey=" + this.schemeKey + ", dataTime=" + this.dataTime + ", currency=" + this.currency + ", orgType=" + this.orgType + ", formKey=" + this.formKey + ", formCode=" + this.formCode + ", formTitle=" + this.formTitle + ", etlTaskName=" + this.etlTaskName + ", etlTaskId=" + this.etlTaskId + ", unitCodes=" + this.unitCodes + "]";
    }
}

