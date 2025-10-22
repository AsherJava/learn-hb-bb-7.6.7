/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.jtable.annotation.JtableLog
 */
package com.jiuqi.nr.finalaccountsaudit.integritycheck.common;

import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.jtable.annotation.JtableLog;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IntegrityCheckInfo
extends JtableLog {
    private static final long serialVersionUID = 1L;
    private String taskKey;
    private String formSchemeKey;
    private String period;
    private Map<String, DimensionValue> dimensionSet = new HashMap<String, DimensionValue>();
    private List<String> entityKeys = new ArrayList<String>();
    private List<String> formKeys = new ArrayList<String>();
    private boolean isZero = false;

    public String getperiod() {
        return this.period;
    }

    public void setperiod(String period) {
        this.period = period;
    }

    public boolean getisZero() {
        return this.isZero;
    }

    public void setisZero(boolean isZero) {
        this.isZero = isZero;
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

    public List<String> getEntityKeys() {
        return this.entityKeys;
    }

    public void setEntityKeys(List<String> entityKeys) {
        this.entityKeys = entityKeys;
    }

    public List<String> getFormKeys() {
        return this.formKeys;
    }

    public void setFormKeys(List<String> formKeys) {
        this.formKeys = formKeys;
    }

    public Map<String, DimensionValue> getDimensionSet() {
        return this.dimensionSet;
    }

    public void setDimensionSet(Map<String, DimensionValue> dimensionSet) {
        this.dimensionSet = dimensionSet;
    }
}

