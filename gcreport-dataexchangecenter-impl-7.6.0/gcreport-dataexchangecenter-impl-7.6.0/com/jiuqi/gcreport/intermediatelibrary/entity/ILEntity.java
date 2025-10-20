/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 */
package com.jiuqi.gcreport.intermediatelibrary.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.sql.Timestamp;

public class ILEntity {
    private String id;
    private String programmeName;
    private String libraryDataSource;
    private String tablePrefix;
    private String sort;
    private Timestamp createTime;
    @JsonFormat(pattern="yyyy-MM-dd", timezone="GMT+8")
    private Timestamp startTime;
    @JsonFormat(pattern="yyyy-MM-dd", timezone="GMT+8")
    private Timestamp endTime;
    private String taskId;
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

    public Timestamp getStartTime() {
        return this.startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return this.endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
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

