/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.analysisreport.dto;

public class AnalysisReportRefOrgDTO {
    private String id;
    private String mrecid;
    private String templateId;
    private String templateTitle;
    private String orgId;
    private String orgTitle;
    private Long sortOrder;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMrecid() {
        return this.mrecid;
    }

    public void setMrecid(String mrecid) {
        this.mrecid = mrecid;
    }

    public String getTemplateId() {
        return this.templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getTemplateTitle() {
        return this.templateTitle;
    }

    public void setTemplateTitle(String templateTitle) {
        this.templateTitle = templateTitle;
    }

    public String getOrgId() {
        return this.orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOrgTitle() {
        return this.orgTitle;
    }

    public void setOrgTitle(String orgTitle) {
        this.orgTitle = orgTitle;
    }

    public Long getSortOrder() {
        return this.sortOrder;
    }

    public void setSortOrder(Long sortOrder) {
        this.sortOrder = sortOrder;
    }
}

