/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.DesignReportTemplateDefine
 *  com.jiuqi.nr.definition.facade.ReportTemplateDefine
 */
package com.jiuqi.nr.report.web.vo;

import com.jiuqi.nr.definition.facade.DesignReportTemplateDefine;
import com.jiuqi.nr.definition.facade.ReportTemplateDefine;
import com.jiuqi.nr.report.dto.ReportTemplateDTO;
import java.util.function.Supplier;

public class ReportTemplateVO {
    private String key;
    private String taskKey;
    private String formSchemeKey;
    private String condition;
    private String fileNameExp;
    private String fileName;
    private String fileKey;
    private String order;

    public ReportTemplateVO() {
    }

    public ReportTemplateVO(DesignReportTemplateDefine define) {
        if (define != null) {
            this.key = define.getKey();
            this.taskKey = define.getTaskKey();
            this.formSchemeKey = define.getFormSchemeKey();
            this.condition = define.getCondition();
            this.fileNameExp = define.getFileNameExp();
            this.fileName = define.getFileName();
            this.fileKey = define.getFileKey();
            this.order = define.getOrder();
        }
    }

    public ReportTemplateVO(ReportTemplateDefine define) {
        this.key = define.getKey();
        this.taskKey = define.getTaskKey();
        this.formSchemeKey = define.getFormSchemeKey();
        this.condition = define.getCondition();
        this.fileNameExp = define.getFileNameExp();
        this.fileName = define.getFileName();
        this.fileKey = define.getFileKey();
        this.order = define.getOrder();
    }

    public ReportTemplateVO(ReportTemplateDTO defineDto) {
        this.key = defineDto.getKey();
        this.taskKey = defineDto.getTaskKey();
        this.formSchemeKey = defineDto.getFormSchemeKey();
        this.condition = defineDto.getCondition();
        this.fileNameExp = defineDto.getFileNameExp();
        this.fileName = defineDto.getFileName();
        this.fileKey = defineDto.getFileKey();
        this.order = defineDto.getOrder();
    }

    public DesignReportTemplateDefine toDesignReportTemplateDefine(Supplier<DesignReportTemplateDefine> defineCreator) {
        DesignReportTemplateDefine define = defineCreator.get();
        define.setKey(this.key);
        define.setTaskKey(this.taskKey);
        define.setFormSchemeKey(this.formSchemeKey);
        define.setCondition(this.condition);
        define.setFileNameExp(this.fileNameExp);
        define.setFileName(this.fileName);
        define.setFileKey(this.fileKey);
        define.setOrder(this.order);
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
}

