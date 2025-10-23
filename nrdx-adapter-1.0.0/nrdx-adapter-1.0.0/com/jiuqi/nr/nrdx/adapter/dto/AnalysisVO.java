/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.jiuqi.nr.context.infc.INRContext
 */
package com.jiuqi.nr.nrdx.adapter.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jiuqi.nr.context.infc.INRContext;

@JsonIgnoreProperties(ignoreUnknown=true)
public class AnalysisVO
implements INRContext {
    private String key;
    private String taskKey;
    private String formSchemeKey;
    private String mappingKey;
    private String contextEntityId;
    private String contextFilterExpression;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getMappingKey() {
        return this.mappingKey;
    }

    public void setMappingKey(String mappingKey) {
        this.mappingKey = mappingKey;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getContextEntityId() {
        return this.contextEntityId;
    }

    public void setContextEntityId(String contextEntityId) {
        this.contextEntityId = contextEntityId;
    }

    public String getContextFilterExpression() {
        return this.contextFilterExpression;
    }

    public void setContextFilterExpression(String contextFilterExpression) {
        this.contextFilterExpression = contextFilterExpression;
    }
}

