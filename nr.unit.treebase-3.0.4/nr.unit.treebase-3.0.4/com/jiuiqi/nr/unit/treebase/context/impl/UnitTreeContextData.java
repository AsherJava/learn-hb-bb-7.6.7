/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.itreebase.context.ITreeContextData
 */
package com.jiuiqi.nr.unit.treebase.context.impl;

import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.itreebase.context.ITreeContextData;
import java.util.Map;

public class UnitTreeContextData
extends ITreeContextData {
    private String taskId;
    private String entityId;
    private String formScheme;
    private String periodEntityId;
    private String period;
    private String versionDateStr;
    private String rowFilterExpression;
    private Map<String, DimensionValue> dimValueSet;
    private boolean mainDimQuery = false;

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

    public String getFormScheme() {
        return this.formScheme;
    }

    public void setFormScheme(String formScheme) {
        this.formScheme = formScheme;
    }

    public String getPeriodEntityId() {
        return this.periodEntityId;
    }

    public void setPeriodEntityId(String periodEntityId) {
        this.periodEntityId = periodEntityId;
    }

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getVersionDateStr() {
        return this.versionDateStr;
    }

    public void setVersionDateStr(String versionDateStr) {
        this.versionDateStr = versionDateStr;
    }

    public String getRowFilterExpression() {
        return this.rowFilterExpression;
    }

    public void setRowFilterExpression(String rowFilterExpression) {
        this.rowFilterExpression = rowFilterExpression;
    }

    public Map<String, DimensionValue> getDimValueSet() {
        return this.dimValueSet;
    }

    public void setDimValueSet(Map<String, DimensionValue> dimValueSet) {
        this.dimValueSet = dimValueSet;
    }

    public boolean isMainDimQuery() {
        return this.mainDimQuery;
    }

    public void setMainDimQuery(boolean mainDimQuery) {
        this.mainDimQuery = mainDimQuery;
    }
}

