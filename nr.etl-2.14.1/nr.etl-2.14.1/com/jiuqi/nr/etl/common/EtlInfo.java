/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.asynctask.entity.AysncTaskArgsInfo
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.context.infc.INRContext
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.params.base.LogParam
 *  com.jiuqi.nr.jtable.service.IJtableBase
 */
package com.jiuqi.nr.etl.common;

import com.jiuqi.nr.common.asynctask.entity.AysncTaskArgsInfo;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.context.infc.INRContext;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.base.LogParam;
import com.jiuqi.nr.jtable.service.IJtableBase;
import java.util.HashMap;
import java.util.Map;

public class EtlInfo
extends AysncTaskArgsInfo
implements IJtableBase,
INRContext {
    private static final long serialVersionUID = 1L;
    private String etlTaskKey;
    private String taskKey;
    private String formSchemeKey;
    private String formKey;
    private String unitIds;
    private String period;
    private Map<String, DimensionValue> dimensionSet = new HashMap<String, DimensionValue>();
    private Map<String, Object> variableMap;
    private String formulaSchemeKey;
    public String contextEntityId;
    public String contextFilterExpression;

    public Map<String, Object> getVariableMap() {
        return this.variableMap;
    }

    public void setVariableMap(Map<String, Object> variableMap) {
        this.variableMap = variableMap;
    }

    public String getFormulaSchemeKey() {
        return this.formulaSchemeKey;
    }

    public void setFormulaSchemeKey(String formulaSchemeKey) {
        this.formulaSchemeKey = formulaSchemeKey;
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

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public JtableContext getContext() {
        JtableContext jtableContext = new JtableContext();
        jtableContext.setDimensionSet(this.dimensionSet);
        jtableContext.setFormKey(this.formKey);
        jtableContext.setFormSchemeKey(this.formSchemeKey);
        jtableContext.setTaskKey(this.taskKey);
        return jtableContext;
    }

    public LogParam getLogParam() {
        return null;
    }

    public String getEtlTaskKey() {
        return this.etlTaskKey;
    }

    public void setEtlTaskKey(String etlTaskKey) {
        this.etlTaskKey = etlTaskKey;
    }

    public String getUnitIds() {
        return this.unitIds;
    }

    public void setUnitIds(String unitIds) {
        this.unitIds = unitIds;
    }

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getContextEntityId() {
        return this.contextEntityId;
    }

    public String getContextFilterExpression() {
        return this.contextFilterExpression;
    }
}

