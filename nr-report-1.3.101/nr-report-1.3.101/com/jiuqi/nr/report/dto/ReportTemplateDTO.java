/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.DesignReportTemplateDefine
 */
package com.jiuqi.nr.report.dto;

import com.jiuqi.nr.definition.facade.DesignReportTemplateDefine;
import java.util.Date;

public class ReportTemplateDTO {
    private String key;
    private String fileName;
    private String taskKey;
    private String formSchemeKey;
    private String fileKey;
    private String fileNameExp;
    private String condition;
    private String order;
    private Date updateTime;

    public ReportTemplateDTO() {
    }

    public ReportTemplateDTO(DesignReportTemplateDefine define) {
        if (define != null) {
            this.key = define.getKey();
            this.fileName = define.getFileName();
            this.taskKey = define.getTaskKey();
            this.formSchemeKey = define.getFormSchemeKey();
            this.condition = define.getCondition();
            this.fileNameExp = define.getFileNameExp();
            this.fileKey = define.getFileKey();
            this.order = define.getOrder();
            this.updateTime = define.getUpdateTime();
        }
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getFileKey() {
        return this.fileKey;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
    }

    public String getFileNameExp() {
        return this.fileNameExp;
    }

    public void setFileNameExp(String fileNameExp) {
        this.fileNameExp = fileNameExp;
    }

    public String getCondition() {
        return this.condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}

