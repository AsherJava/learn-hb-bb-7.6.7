/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.estimation.web.ext.dataentry;

import com.jiuqi.nr.data.estimation.web.response.EstimationFormulaSchemeInfo;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataEntryOpenFuncParam {
    private String taskId;
    private String period;
    private boolean periodAllowChange;
    private List<String> unitViewEntityRange;
    private List<String> formulaSchemeIds;
    private Map<String, Object> variableMap = new HashMap<String, Object>();
    private List<EstimationFormulaSchemeInfo> accessFormulaSchemes;

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public Map<String, Object> getVariableMap() {
        return this.variableMap;
    }

    public void setVariableMap(Map<String, Object> variableMap) {
        this.variableMap = variableMap;
    }

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public boolean isPeriodAllowChange() {
        return this.periodAllowChange;
    }

    public void setPeriodAllowChange(boolean periodAllowChange) {
        this.periodAllowChange = periodAllowChange;
    }

    public List<String> getUnitViewEntityRange() {
        return this.unitViewEntityRange;
    }

    public void setUnitViewEntityRange(List<String> unitViewEntityRange) {
        this.unitViewEntityRange = unitViewEntityRange;
    }

    public List<String> getFormulaSchemeIds() {
        return this.formulaSchemeIds;
    }

    public void setFormulaSchemeIds(List<String> formulaSchemeIds) {
        this.formulaSchemeIds = formulaSchemeIds;
    }

    public List<EstimationFormulaSchemeInfo> getAccessFormulaSchemes() {
        return this.accessFormulaSchemes;
    }

    public void setAccessFormulaSchemes(List<EstimationFormulaSchemeInfo> accessFormulaSchemes) {
        this.accessFormulaSchemes = accessFormulaSchemes;
    }
}

