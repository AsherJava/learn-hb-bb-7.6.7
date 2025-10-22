/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.nr.batch.gather.gzw.web.app.context;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.batch.gather.gzw.web.app.context.BatchGatherGZWContextData;

public class BatchGatherGZWContextDataImpl
implements BatchGatherGZWContextData {
    private String taskId;
    private String entityId;
    private String period;
    private String formSchemeId;
    private String productName = "@nr";
    private String batchGatherSchemeKey;

    @Override
    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    @Override
    public String getEntityId() {
        return this.entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    @Override
    public String getFormSchemeId() {
        return this.formSchemeId;
    }

    public void setFormSchemeId(String formSchemeId) {
        this.formSchemeId = formSchemeId;
    }

    @Override
    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    @Override
    public String getProductName() {
        return this.productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Override
    @JsonIgnore
    public boolean isValidContext() {
        return StringUtils.isNotEmpty((String)this.taskId);
    }

    @Override
    public String getBatchGatherSchemeKey() {
        return this.batchGatherSchemeKey;
    }

    public void setBatchGatherSchemeKey(String batchGatherSchemeKey) {
        this.batchGatherSchemeKey = batchGatherSchemeKey;
    }
}

