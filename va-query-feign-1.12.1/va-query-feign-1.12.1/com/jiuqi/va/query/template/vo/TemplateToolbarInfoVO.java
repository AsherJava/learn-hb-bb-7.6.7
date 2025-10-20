/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.query.template.vo;

public class TemplateToolbarInfoVO {
    private String id;
    private String templateId;
    private String action;
    private String title;
    private String config;
    private String operationFlag;
    private Integer sortOrder;
    private Boolean enableAuth;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTemplateId() {
        return this.templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getAction() {
        return this.action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOperationFlag() {
        return this.operationFlag;
    }

    public void setOperationFlag(String operationFlag) {
        this.operationFlag = operationFlag;
    }

    public Integer getSortOrder() {
        return this.sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getConfig() {
        return this.config;
    }

    public void setConfig(String config) {
        this.config = config;
    }

    public Boolean getEnableAuth() {
        return this.enableAuth;
    }

    public void setEnableAuth(Boolean enableAuth) {
        this.enableAuth = enableAuth;
    }
}

