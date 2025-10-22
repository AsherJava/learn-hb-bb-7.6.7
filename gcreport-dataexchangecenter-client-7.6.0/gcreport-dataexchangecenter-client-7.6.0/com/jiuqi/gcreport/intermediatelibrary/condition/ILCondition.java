/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 */
package com.jiuqi.gcreport.intermediatelibrary.condition;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.sql.Timestamp;
import java.util.List;

public class ILCondition {
    private String id;
    private String programmeName;
    private String programmeId;
    private String libraryDataSource;
    private String tablePrefix;
    private String sort;
    private Timestamp createTime;
    private List<String> orgIdList;
    private List<String> fieldIdList;
    private String dateType;
    @JsonFormat(pattern="yyyy-MM-dd", timezone="GMT+8")
    private Timestamp startTime;
    @JsonFormat(pattern="yyyy-MM-dd", timezone="GMT+8")
    private Timestamp endTime;
    private String taskId;
    private List<String> formTreeKeyList;
    private String sourceType;
    private String sourceParam;
    private String extractSimplePloy;

    public String getExtractSimplePloy() {
        return this.extractSimplePloy;
    }

    public void setExtractSimplePloy(String extractSimplePloy) {
        this.extractSimplePloy = extractSimplePloy;
    }

    public String getSourceType() {
        return this.sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getSourceParam() {
        return this.sourceParam;
    }

    public void setSourceParam(String sourceParam) {
        this.sourceParam = sourceParam;
    }

    public List<String> getFormTreeKeyList() {
        return this.formTreeKeyList;
    }

    public void setFormTreeKeyList(List<String> formTreeKeyList) {
        this.formTreeKeyList = formTreeKeyList;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public Timestamp getEndTime() {
        return this.endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public Timestamp getStartTime() {
        return this.startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public String getDateType() {
        return this.dateType;
    }

    public void setDateType(String dateType) {
        this.dateType = dateType;
    }

    public String getProgrammeId() {
        return this.programmeId;
    }

    public void setProgrammeId(String programmeId) {
        this.programmeId = programmeId;
    }

    public List<String> getFieldIdList() {
        return this.fieldIdList;
    }

    public void setFieldIdList(List<String> fieldIdList) {
        this.fieldIdList = fieldIdList;
    }

    public List<String> getOrgIdList() {
        return this.orgIdList;
    }

    public void setOrgIdList(List<String> orgIdList) {
        this.orgIdList = orgIdList;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProgrammeName() {
        return this.programmeName;
    }

    public void setProgrammeName(String programmeName) {
        this.programmeName = programmeName;
    }

    public String getLibraryDataSource() {
        return this.libraryDataSource;
    }

    public void setLibraryDataSource(String libraryDataSource) {
        this.libraryDataSource = libraryDataSource;
    }

    public String getTablePrefix() {
        return this.tablePrefix;
    }

    public void setTablePrefix(String tablePrefix) {
        this.tablePrefix = tablePrefix;
    }

    public String getSort() {
        return this.sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public Timestamp getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }
}

