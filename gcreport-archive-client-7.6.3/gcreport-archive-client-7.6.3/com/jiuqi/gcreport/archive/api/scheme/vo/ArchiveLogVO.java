/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.archive.api.scheme.vo;

import java.util.Date;
import java.util.List;

public class ArchiveLogVO {
    private String id;
    private String taskName;
    private String taskId;
    private String schemeName;
    private String schemeId;
    private List<String> orgCodeList;
    private String startPeriod;
    private String endPeriod;
    private Integer status;
    private Date createDate;
    private Date endDate;
    private String logInfo;
    private String createUser;
    private String orgTypeTitle;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrgTypeTitle() {
        return this.orgTypeTitle;
    }

    public void setOrgTypeTitle(String orgTypeTitle) {
        this.orgTypeTitle = orgTypeTitle;
    }

    public String getTaskName() {
        return this.taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getSchemeName() {
        return this.schemeName;
    }

    public void setSchemeName(String schemeName) {
        this.schemeName = schemeName;
    }

    public String getStartPeriod() {
        return this.startPeriod;
    }

    public void setStartPeriod(String startPeriod) {
        this.startPeriod = startPeriod;
    }

    public String getEndPeriod() {
        return this.endPeriod;
    }

    public void setEndPeriod(String endPeriod) {
        this.endPeriod = endPeriod;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getEndDate() {
        return this.endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getLogInfo() {
        return this.logInfo;
    }

    public void setLogInfo(String logInfo) {
        this.logInfo = logInfo;
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

    public String getCreateUser() {
        return this.createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }
}

