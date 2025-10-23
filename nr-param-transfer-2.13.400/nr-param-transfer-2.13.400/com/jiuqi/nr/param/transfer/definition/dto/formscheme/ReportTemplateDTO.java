/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.jiuqi.nr.definition.facade.DesignReportTemplateDefine
 */
package com.jiuqi.nr.param.transfer.definition.dto.formscheme;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jiuqi.nr.definition.facade.DesignReportTemplateDefine;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown=true)
public class ReportTemplateDTO {
    private String key;
    private String taskKey;
    private String formSchemeKey;
    private String condition;
    private String fileNameExp;
    private String fileName;
    private String fileKey;
    private String order;
    private Date updateTime;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
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

    public String getCondition() {
        return this.condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getFileNameExp() {
        return this.fileNameExp;
    }

    public void setFileNameExp(String fileNameExp) {
        this.fileNameExp = fileNameExp;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileKey() {
        return this.fileKey;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
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

    public static ReportTemplateDTO valueOf(DesignReportTemplateDefine reportTemplateDefine) {
        if (reportTemplateDefine == null) {
            return null;
        }
        ReportTemplateDTO dto = new ReportTemplateDTO();
        dto.setKey(reportTemplateDefine.getKey());
        dto.setTaskKey(reportTemplateDefine.getTaskKey());
        dto.setFormSchemeKey(reportTemplateDefine.getFormSchemeKey());
        dto.setCondition(reportTemplateDefine.getCondition());
        dto.setFileNameExp(reportTemplateDefine.getFileNameExp());
        dto.setFileName(reportTemplateDefine.getFileName());
        dto.setFileKey(reportTemplateDefine.getFileKey());
        dto.setOrder(reportTemplateDefine.getOrder());
        dto.setUpdateTime(reportTemplateDefine.getUpdateTime());
        return dto;
    }

    public void value2Define(DesignReportTemplateDefine reportTemplateDefine) {
        reportTemplateDefine.setKey(this.getKey());
        reportTemplateDefine.setTaskKey(this.getTaskKey());
        reportTemplateDefine.setFormSchemeKey(this.getFormSchemeKey());
        reportTemplateDefine.setCondition(this.getCondition());
        reportTemplateDefine.setFileNameExp(this.getFileNameExp());
        reportTemplateDefine.setFileName(this.getFileName());
        reportTemplateDefine.setFileKey(this.getFileKey());
        reportTemplateDefine.setOrder(this.getOrder());
        reportTemplateDefine.setUpdateTime(this.getUpdateTime());
    }
}

