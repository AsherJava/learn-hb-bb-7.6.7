/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.context.infc.INRContext
 *  com.jiuqi.nr.jtable.annotation.JtableLog
 */
package com.jiuqi.nr.integritycheck.common;

import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.context.infc.INRContext;
import com.jiuqi.nr.jtable.annotation.JtableLog;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IntegrityCheckInfo
extends JtableLog
implements INRContext {
    private static final long serialVersionUID = 1L;
    private String batchId;
    private String taskKey;
    private String formSchemeKey;
    private Map<String, DimensionValue> dimensionSet = new HashMap<String, DimensionValue>();
    private List<String> entityKeys = new ArrayList<String>();
    private String period;
    private List<String> formKeys = new ArrayList<String>();
    private boolean isZero = false;
    private String contextEntityId;

    public String getBatchId() {
        return this.batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
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

    public Map<String, DimensionValue> getDimensionSet() {
        return this.dimensionSet;
    }

    public void setDimensionSet(Map<String, DimensionValue> dimensionSet) {
        this.dimensionSet = dimensionSet;
    }

    public List<String> getEntityKeys() {
        return this.entityKeys;
    }

    public void setEntityKeys(List<String> entityKeys) {
        this.entityKeys = entityKeys;
    }

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public List<String> getFormKeys() {
        return this.formKeys;
    }

    public void setFormKeys(List<String> formKeys) {
        this.formKeys = formKeys;
    }

    public boolean isZero() {
        return this.isZero;
    }

    public void setZero(boolean zero) {
        this.isZero = zero;
    }

    public String getContextEntityId() {
        return this.contextEntityId;
    }

    public void setContextEntityId(String contextEntityId) {
        this.contextEntityId = contextEntityId;
    }

    public String getContextFilterExpression() {
        return null;
    }
}

