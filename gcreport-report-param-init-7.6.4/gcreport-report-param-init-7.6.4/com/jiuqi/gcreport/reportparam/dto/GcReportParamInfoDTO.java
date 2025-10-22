/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.alibaba.excel.annotation.ExcelProperty
 */
package com.jiuqi.gcreport.reportparam.dto;

import com.alibaba.excel.annotation.ExcelProperty;

public class GcReportParamInfoDTO {
    @ExcelProperty(value={"\u53c2\u6570\u540d\u79f0"})
    private String paramName;
    @ExcelProperty(value={"\u53c2\u6570\u6587\u4ef6"})
    private String fileName;
    @ExcelProperty(value={"\u53c2\u6570\u7c7b\u578b"})
    private String paramType;
    @ExcelProperty(value={"\u5305\u542b\u4efb\u52a1"})
    private String taskName;
    @ExcelProperty(value={"\u6570\u636e\u6587\u4ef6"})
    private String taskDataFileName;
    @ExcelProperty(value={"\u53c2\u6570\u63cf\u8ff0"})
    private String description;
    @ExcelProperty(value={"\u4e3b\u4f53\u8bbe\u7f6e"})
    private String relatedOrgTypes;
    @ExcelProperty(value={"\u5173\u8054\u5408\u5e76\u4f53\u7cfb"})
    private String relatedMergeSystem;

    public String getParamName() {
        return this.paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getParamType() {
        return this.paramType;
    }

    public void setParamType(String paramType) {
        this.paramType = paramType;
    }

    public String getTaskName() {
        return this.taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDataFileName() {
        return this.taskDataFileName;
    }

    public void setTaskDataFileName(String taskDataFileName) {
        this.taskDataFileName = taskDataFileName;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRelatedMergeSystem() {
        return this.relatedMergeSystem;
    }

    public void setRelatedMergeSystem(String relatedMergeSystem) {
        this.relatedMergeSystem = relatedMergeSystem;
    }

    public String getRelatedOrgTypes() {
        return this.relatedOrgTypes;
    }

    public void setRelatedOrgTypes(String relatedOrgTypes) {
        this.relatedOrgTypes = relatedOrgTypes;
    }
}

