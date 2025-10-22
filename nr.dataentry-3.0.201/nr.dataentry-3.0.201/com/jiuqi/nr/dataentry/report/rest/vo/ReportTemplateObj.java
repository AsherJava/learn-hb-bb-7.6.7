/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datareport.obj.ReportTemplateObj
 *  com.jiuqi.nr.definition.facade.DesignReportTemplateDefine
 *  com.jiuqi.nr.definition.facade.ReportTemplateDefine
 */
package com.jiuqi.nr.dataentry.report.rest.vo;

import com.jiuqi.nr.definition.facade.DesignReportTemplateDefine;
import com.jiuqi.nr.definition.facade.ReportTemplateDefine;
import java.util.Date;
import java.util.function.Supplier;

public class ReportTemplateObj {
    private String key;
    private String taskKey;
    private String formSchemeKey;
    private String formSchemeTitle;
    private String fileNameExp;
    private String fileName;
    private String fileKey;
    private String order;
    private Date updateTime;

    public ReportTemplateObj() {
    }

    public ReportTemplateObj(com.jiuqi.nr.datareport.obj.ReportTemplateObj obj) {
        this.key = obj.getKey();
        this.taskKey = obj.getTaskKey();
        this.formSchemeKey = obj.getFormSchemeKey();
        this.formSchemeTitle = obj.getFormSchemeTitle();
        this.fileNameExp = obj.getFileNameExp();
        this.fileName = obj.getFileName();
        this.fileKey = obj.getFileKey();
        this.order = obj.getOrder();
        this.updateTime = obj.getUpdateTime();
    }

    public ReportTemplateObj(DesignReportTemplateDefine define, String formSchemeTitle) {
        this.key = define.getKey();
        this.taskKey = define.getTaskKey();
        this.formSchemeKey = define.getFormSchemeKey();
        this.formSchemeTitle = formSchemeTitle;
        this.fileNameExp = define.getFileNameExp();
        this.fileName = define.getFileName();
        this.fileKey = define.getFileKey();
        this.order = define.getOrder();
        this.updateTime = define.getUpdateTime();
    }

    public ReportTemplateObj(ReportTemplateDefine define, String formSchemeTitle) {
        this.key = define.getKey();
        this.taskKey = define.getTaskKey();
        this.formSchemeKey = define.getFormSchemeKey();
        this.formSchemeTitle = formSchemeTitle;
        this.fileNameExp = define.getFileNameExp();
        this.fileName = define.getFileName();
        this.fileKey = define.getFileKey();
        this.order = define.getOrder();
        this.updateTime = define.getUpdateTime();
    }

    public DesignReportTemplateDefine toDesignReportTemplateDefine(Supplier<DesignReportTemplateDefine> defineCreator) {
        DesignReportTemplateDefine define = defineCreator.get();
        define.setKey(this.key);
        define.setTaskKey(this.taskKey);
        define.setFormSchemeKey(this.formSchemeKey);
        define.setFileNameExp(this.fileNameExp);
        define.setFileName(this.fileName);
        define.setFileKey(this.fileKey);
        define.setOrder(this.order);
        define.setUpdateTime(this.updateTime);
        return define;
    }

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

    public String getFormSchemeTitle() {
        return this.formSchemeTitle;
    }

    public void setFormSchemeTitle(String formSchemeTitle) {
        this.formSchemeTitle = formSchemeTitle;
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
}

