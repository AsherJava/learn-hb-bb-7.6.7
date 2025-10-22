/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 */
package com.jiuqi.gcreport.archive.api.scheme.vo;

import com.jiuqi.nr.jtable.params.base.JtableContext;
import java.util.List;

public class ArchiveContext
extends JtableContext {
    private String logId;
    private String taskId;
    private String schemeId;
    private String orgType;
    private String fileType;
    private String orgCode;
    private String status;
    private String userName;
    private String defaultPeriod;
    private List<String> orgCodeList;
    private String startPeriodString;
    private String startAdjustCode;
    private String endPeriodString;
    private String endAdjustCode;
    private String excelFormInfos;

    public String getOrgType() {
        return this.orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getFileType() {
        return this.fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
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

    public List<String> getOrgCodeList() {
        return this.orgCodeList;
    }

    public void setOrgCodeList(List<String> orgCodeList) {
        this.orgCodeList = orgCodeList;
    }

    public String getStartPeriodString() {
        return this.startPeriodString;
    }

    public void setStartPeriodString(String startPeriodString) {
        this.startPeriodString = startPeriodString;
    }

    public String getEndPeriodString() {
        return this.endPeriodString;
    }

    public void setEndPeriodString(String endPeriodString) {
        this.endPeriodString = endPeriodString;
    }

    public String getOrgCode() {
        return this.orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDefaultPeriod() {
        return this.defaultPeriod;
    }

    public void setDefaultPeriod(String defaultPeriod) {
        this.defaultPeriod = defaultPeriod;
    }

    public String getLogId() {
        return this.logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public String getExcelFormInfos() {
        return this.excelFormInfos;
    }

    public void setExcelFormInfos(String excelFormInfos) {
        this.excelFormInfos = excelFormInfos;
    }

    public String getStartAdjustCode() {
        return this.startAdjustCode;
    }

    public void setStartAdjustCode(String startAdjustCode) {
        this.startAdjustCode = startAdjustCode;
    }

    public String getEndAdjustCode() {
        return this.endAdjustCode;
    }

    public void setEndAdjustCode(String endAdjustCode) {
        this.endAdjustCode = endAdjustCode;
    }
}

