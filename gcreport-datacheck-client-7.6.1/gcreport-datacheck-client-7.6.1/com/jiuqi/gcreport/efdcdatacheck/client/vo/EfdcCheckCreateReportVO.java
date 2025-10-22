/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.efdcdatacheck.client.vo;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class EfdcCheckCreateReportVO {
    private String fileName;
    private String acctYear;
    private String acctPeriod;
    private String periodString;
    private List<String> orgCodeList;
    private String orgType;
    private String periodType;
    private String adjustCode;
    private String groupMode;
    private String form;
    private String taskId;
    private String schemeId;
    private List<String> formKeyData;
    private String[] fileType;
    private Boolean includeUncharged;
    private Map<String, Set<String>> reportZbData;

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getAdjustCode() {
        return this.adjustCode;
    }

    public void setAdjustCode(String adjustCode) {
        this.adjustCode = adjustCode;
    }

    public String getAcctYear() {
        return this.acctYear;
    }

    public void setAcctYear(String acctYear) {
        this.acctYear = acctYear;
    }

    public String getAcctPeriod() {
        return this.acctPeriod;
    }

    public void setAcctPeriod(String acctPeriod) {
        this.acctPeriod = acctPeriod;
    }

    public List<String> getOrgCodeList() {
        return this.orgCodeList;
    }

    public void setOrgCodeList(List<String> orgCodeList) {
        this.orgCodeList = orgCodeList;
    }

    public String getPeriodType() {
        return this.periodType;
    }

    public void setPeriodType(String periodType) {
        this.periodType = periodType;
    }

    public String getGroupMode() {
        return this.groupMode;
    }

    public void setGroupMode(String groupMode) {
        this.groupMode = groupMode;
    }

    public String getForm() {
        return this.form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getSchemeId() {
        return this.schemeId;
    }

    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }

    public List<String> getFormKeyData() {
        return this.formKeyData;
    }

    public void setFormKeyData(List<String> formKeyData) {
        this.formKeyData = formKeyData;
    }

    public Map<String, Set<String>> getReportZbData() {
        return this.reportZbData;
    }

    public void setReportZbData(Map<String, Set<String>> reportZbData) {
        this.reportZbData = reportZbData;
    }

    public String[] getFileType() {
        return this.fileType;
    }

    public void setFileType(String[] fileType) {
        this.fileType = fileType;
    }

    public String getPeriodString() {
        return this.periodString;
    }

    public void setPeriodString(String periodString) {
        this.periodString = periodString;
    }

    public Boolean getIncludeUncharged() {
        return this.includeUncharged;
    }

    public void setIncludeUncharged(Boolean includeUncharged) {
        this.includeUncharged = includeUncharged;
    }

    public String getOrgType() {
        return this.orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }
}

