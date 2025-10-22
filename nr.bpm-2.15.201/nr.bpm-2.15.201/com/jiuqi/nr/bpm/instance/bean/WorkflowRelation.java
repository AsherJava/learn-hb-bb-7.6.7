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
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.util.CollectionUtils;

public class WorkflowRelation
extends NRContext {
    private String taskKey;
    private String period;
    public String workflowKey;
    private Map<String, Object> variableMap = new HashMap<String, Object>();
    private String adjust;

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

    public String getWorkflowKey() {
        return this.workflowKey;
    }

    public void setWorkflowKey(String workflowKey) {
        this.workflowKey = workflowKey;
    }

    public Map<String, Object> getVariableMap() {
        return this.variableMap;
    }

    public void setVariableMap(Map<String, Object> variableMap) {
        this.variableMap = variableMap;
    }

    public String getAdjust() {
        return this.adjust;
    }

    public void setAdjust(String adjust) {
        this.adjust = adjust;
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

