/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.conversion.batch.audit.entity;

import java.sql.Blob;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public class ConversionBatchAuditRunnerEntity {
    private String id;
    private String taskId;
    private String dataType;
    private List<Map<String, String>> formKeyListMap;
    private String orgType;
    private List<String> orgCodeList;
    private Map<String, List<String>> reportZbData;
    private String exportType;
    private String acctYear;
    private String acctPeriod;
    private String fileName;
    private Timestamp createTime;
    private String createUser;
    private String schemeId;
    private Blob FileData;
    private Integer pageSize;
    private Integer currentPageNum;

    public Integer getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getCurrentPageNum() {
        return this.currentPageNum;
    }

    public void setCurrentPageNum(Integer currentPageNum) {
        this.currentPageNum = currentPageNum;
    }

    public Blob getFileData() {
        return this.FileData;
    }

    public void setFileData(Blob fileData) {
        this.FileData = fileData;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreateUser() {
        return this.createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getSchemeId() {
        return this.schemeId;
    }

    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }

    public String getAcctPeriod() {
        return this.acctPeriod;
    }

    public void setAcctPeriod(String acctPeriod) {
        this.acctPeriod = acctPeriod;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Timestamp getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getAcctYear() {
        return this.acctYear;
    }

    public void setAcctYear(String acctYear) {
        this.acctYear = acctYear;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getDataType() {
        return this.dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public List<Map<String, String>> getFormKeyListMap() {
        return this.formKeyListMap;
    }

    public void setFormKeyListMap(List<Map<String, String>> formKeyListMap) {
        this.formKeyListMap = formKeyListMap;
    }

    public List<String> getOrgCodeList() {
        return this.orgCodeList;
    }

    public void setOrgCodeList(List<String> orgCodeList) {
        this.orgCodeList = orgCodeList;
    }

    public Map<String, List<String>> getReportZbData() {
        return this.reportZbData;
    }

    public void setReportZbData(Map<String, List<String>> reportZbData) {
        this.reportZbData = reportZbData;
    }

    public String getExportType() {
        return this.exportType;
    }

    public void setExportType(String exportType) {
        this.exportType = exportType;
    }

    public String getOrgType() {
        return this.orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }
}

