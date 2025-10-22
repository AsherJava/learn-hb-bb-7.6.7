/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.jiuqi.np.dataengine.var.Variable
 *  com.jiuqi.nr.context.infc.impl.NRContext
 */
package com.jiuqi.nr.bpm.instance.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.nr.context.infc.impl.NRContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.util.CollectionUtils;

public class QueryGridDataParam
extends NRContext
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String taskKey;
    private String period;
    private String adjust;
    private String currentUnitKey;
    private String searchKeys;
    private List<String> searchUnitKeys;
    private List<Integer> states;
    private boolean allSubordinate = true;
    private int pageNum;
    private int pageSize;
    private boolean allChecked;
    private List<String> selectKeys;
    private List<String> cancelSelectKeys;
    private transient Map<String, Object> variableMap = new HashMap<String, Object>();

    public String getAdjust() {
        return this.adjust;
    }

    public void setAdjust(String adjust) {
        this.adjust = adjust;
    }

    public String getSearchKeys() {
        return this.searchKeys;
    }

    public void setSearchKeys(String searchKeys) {
        this.searchKeys = searchKeys;
    }

    public boolean isAllSubordinate() {
        return this.allSubordinate;
    }

    public void setAllSubordinate(boolean allSubordinate) {
        this.allSubordinate = allSubordinate;
    }

    public List<String> getSearchUnitKeys() {
        return this.searchUnitKeys;
    }

    public void setSearchUnitKeys(List<String> searchUnitKeys) {
        this.searchUnitKeys = searchUnitKeys;
    }

    public List<Integer> getStates() {
        return this.states;
    }

    public void setStates(List<Integer> states) {
        this.states = states;
    }

    public int getPageNum() {
        return this.pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getCurrentUnitKey() {
        return this.currentUnitKey;
    }

    public void setCurrentUnitKey(String currentUnitKey) {
        this.currentUnitKey = currentUnitKey;
    }

    public boolean isAllChecked() {
        return this.allChecked;
    }

    public void setAllChecked(boolean allChecked) {
        this.allChecked = allChecked;
    }

    public List<String> getSelectKeys() {
        return this.selectKeys;
    }

    public void setSelectKeys(List<String> selectKeys) {
        this.selectKeys = selectKeys;
    }

    public List<String> getCancelSelectKeys() {
        return this.cancelSelectKeys;
    }

    public void setCancelSelectKeys(List<String> cancelSelectKeys) {
        this.cancelSelectKeys = cancelSelectKeys;
    }

    public Map<String, Object> getVariableMap() {
        return this.variableMap;
    }

    public void setVariableMap(Map<String, Object> variableMap) {
        this.variableMap = variableMap;
    }

    @JsonIgnore
    public List<Variable> getVariables() {
        Map<String, Object> variableMap = this.getVariableMap();
        if (!CollectionUtils.isEmpty(variableMap)) {
            ArrayList<Variable> variables = new ArrayList<Variable>();
            for (String variableName : variableMap.keySet()) {
                Object variableValue = variableMap.get(variableName);
                Variable variable = new Variable(variableName, 6);
                variable.setVarValue(variableValue);
                variables.add(variable);
            }
            return variables;
        }
        return Collections.emptyList();
    }
}

