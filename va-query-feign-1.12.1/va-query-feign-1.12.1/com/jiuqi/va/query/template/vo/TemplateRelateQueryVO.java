/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.query.template.vo;

public class TemplateRelateQueryVO {
    private String id;
    private String templateId;
    private String processorName;
    private String processorConfig;
    private String triggerField;
    private String openType;
    private String description;
    private String queryParam;
    private String checkExpression;
    private String relateCheckMessage;
    private Integer sortOrder;

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

    public String getProcessorName() {
        return this.processorName;
    }

    public void setProcessorName(String processorName) {
        this.processorName = processorName;
    }

    public String getProcessorConfig() {
        return this.processorConfig;
    }

    public void setProcessorConfig(String processorConfig) {
        this.processorConfig = processorConfig;
    }

    public String getTriggerField() {
        return this.triggerField;
    }

    public void setTriggerField(String triggerField) {
        this.triggerField = triggerField;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getQueryParam() {
        return this.queryParam;
    }

    public void setQueryParam(String queryParam) {
        this.queryParam = queryParam;
    }

    public Integer getSortOrder() {
        return this.sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getOpenType() {
        return this.openType;
    }

    public void setOpenType(String openType) {
        this.openType = openType;
    }

    public String getCheckExpression() {
        return this.checkExpression;
    }

    public void setCheckExpression(String checkExpression) {
        this.checkExpression = checkExpression;
    }

    public String getRelateCheckMessage() {
        return this.relateCheckMessage;
    }

    public void setRelateCheckMessage(String relateCheckMessage) {
        this.relateCheckMessage = relateCheckMessage;
    }
}

