/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 */
package com.jiuqi.nr.designer.web.rest.param;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class DimensionFilterPM {
    private String taskId;
    private String entityId;
    private String values;

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getEntityId() {
        return this.entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getValues() {
        return this.values;
    }

    public void setValues(String values) {
        this.values = values;
    }
}

