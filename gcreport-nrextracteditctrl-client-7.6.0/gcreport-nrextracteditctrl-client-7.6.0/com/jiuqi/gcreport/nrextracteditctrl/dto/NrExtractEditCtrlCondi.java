/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.nrextracteditctrl.dto;

public class NrExtractEditCtrlCondi {
    private String taskId;
    private String schemeId;
    private String orgId;
    private String formId;

    public NrExtractEditCtrlCondi() {
    }

    public NrExtractEditCtrlCondi(String taskId, String schemeId, String orgId, String formId) {
        this.taskId = taskId;
        this.schemeId = schemeId;
        this.orgId = orgId;
        this.formId = formId;
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

    public String getOrgId() {
        return this.orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getFormId() {
        return this.formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }
}

