/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.var.Variable
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.context.infc.INRContext
 *  com.jiuqi.nr.jtable.annotation.JtableLog
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.params.base.LogParam
 *  com.jiuqi.nr.jtable.uniformity.service.JUniformityService
 */
package com.jiuqi.nr.dataentry.paramInfo;

import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.context.infc.INRContext;
import com.jiuqi.nr.jtable.annotation.JtableLog;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.base.LogParam;
import com.jiuqi.nr.jtable.uniformity.service.JUniformityService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BatchCalculateInfo
extends JtableLog
implements JUniformityService,
INRContext {
    private String periodRegionInfo;
    private static final long serialVersionUID = 1L;
    private JtableContext context;
    private String taskKey;
    private String formSchemeKey;
    private String formulaSchemeKey;
    private Map<String, List<String>> formulas = new HashMap<String, List<String>>();
    private Map<String, DimensionValue> dimensionSet = new HashMap<String, DimensionValue>();
    private Map<String, Object> variableMap = new HashMap<String, Object>();
    private boolean ignoreWorkFlow = false;
    private Map<String, String> periodAndFormSchemeMap = new HashMap<String, String>();
    private boolean changeMonitorState = true;
    private String dwScope;
    private String contextTaskKey;
    private String contextEntityId;
    private String contextFormSchemeKey;
    private String contextFilterExpression;

    public List<Variable> getVariables() {
        if (this.context != null) {
            return this.context.getVariables();
        }
        return new ArrayList<Variable>();
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

    public String getFormulaSchemeKey() {
        return this.formulaSchemeKey;
    }

    public void setFormulaSchemeKey(String formulaSchemeKey) {
        this.formulaSchemeKey = formulaSchemeKey;
    }

    public Map<String, List<String>> getFormulas() {
        return this.formulas;
    }

    public void setFormulas(Map<String, List<String>> formulas) {
        this.formulas = formulas;
    }

    public Map<String, DimensionValue> getDimensionSet() {
        return this.dimensionSet;
    }

    public void setDimensionSet(Map<String, DimensionValue> dimensionSet) {
        this.dimensionSet = dimensionSet;
    }

    public Map<String, Object> getVariableMap() {
        return this.variableMap;
    }

    public void setVariableMap(Map<String, Object> variableMap) {
        this.variableMap = variableMap;
    }

    public JtableContext getContext() {
        return this.context;
    }

    public LogParam getLogParam() {
        LogParam logParam = new LogParam();
        logParam.setModule("\u6570\u636e\u5f55\u5165");
        if (!this.formulas.isEmpty()) {
            Map other = logParam.getOrherMsg();
            Set<String> formKeys = this.formulas.keySet();
            StringBuffer form = new StringBuffer();
            formKeys.forEach(e -> form.append((String)e).append(";"));
            other.put("formKeys", form.substring(0, form.length() - 1));
        }
        return logParam;
    }

    public void setContext(JtableContext context) {
        this.context = context;
    }

    public boolean isIgnoreWorkFlow() {
        return this.ignoreWorkFlow;
    }

    public void setIgnoreWorkFlow(boolean ignoreWorkFlow) {
        this.ignoreWorkFlow = ignoreWorkFlow;
    }

    public String getDwScope() {
        return this.dwScope;
    }

    public void setDwScope(String dwScope) {
        this.dwScope = dwScope;
    }

    public Map<String, String> getPeriodAndFormSchemeMap() {
        return this.periodAndFormSchemeMap;
    }

    public void setPeriodAndFormSchemeMap(Map<String, String> periodAndFormSchemeMap) {
        this.periodAndFormSchemeMap = periodAndFormSchemeMap;
    }

    public String getPeriodRegionInfo() {
        return this.periodRegionInfo;
    }

    public void setPeriodRegionInfo(String periodRegionInfo) {
        this.periodRegionInfo = periodRegionInfo;
    }

    public boolean isChangeMonitorState() {
        return this.changeMonitorState;
    }

    public void setChangeMonitorState(boolean changeMonitorState) {
        this.changeMonitorState = changeMonitorState;
    }

    public String getContextEntityId() {
        return this.contextEntityId;
    }

    public String getContextFilterExpression() {
        return this.contextFilterExpression;
    }
}

