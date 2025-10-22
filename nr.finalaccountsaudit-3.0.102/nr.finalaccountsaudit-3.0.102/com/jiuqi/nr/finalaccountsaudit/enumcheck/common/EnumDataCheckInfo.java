/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.jtable.annotation.JtableLog
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 */
package com.jiuqi.nr.finalaccountsaudit.enumcheck.common;

import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.jtable.annotation.JtableLog;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import java.util.HashMap;
import java.util.Map;

public class EnumDataCheckInfo
extends JtableLog {
    private JtableContext context;
    private static final long serialVersionUID = 1L;
    private String enumNames;
    private Map<String, DimensionValue> selectedDimensionSet = new HashMap<String, DimensionValue>();
    private String formKeys;
    private Map<String, Object> variableMap = new HashMap<String, Object>();
    private boolean ignoreBlank;
    private String filterFormula;

    public JtableContext getContext() {
        return this.context;
    }

    public void setContext(JtableContext context) {
        this.context = context;
    }

    public Map<String, Object> getVariableMap() {
        return this.variableMap;
    }

    public void setVariableMap(Map<String, Object> variableMap) {
        this.variableMap = variableMap;
    }

    public String getEnumNames() {
        return this.enumNames;
    }

    public void setEnumNames(String enumNames) {
        this.enumNames = enumNames;
    }

    public String getFormKeys() {
        return this.formKeys;
    }

    public void setFormKeys(String formKeys) {
        this.formKeys = formKeys;
    }

    public Map<String, DimensionValue> getSelectedDimensionSet() {
        return this.selectedDimensionSet;
    }

    public void setSelectedDimensionSet(Map<String, DimensionValue> selectedDimensionSet) {
        this.selectedDimensionSet = selectedDimensionSet;
    }

    public boolean isIgnoreBlank() {
        return this.ignoreBlank;
    }

    public void setIgnoreBlank(boolean ignoreBlank) {
        this.ignoreBlank = ignoreBlank;
    }

    public String getFilterFormula() {
        return this.filterFormula;
    }

    public void setFilterFormula(String filterFormula) {
        this.filterFormula = filterFormula;
    }
}

