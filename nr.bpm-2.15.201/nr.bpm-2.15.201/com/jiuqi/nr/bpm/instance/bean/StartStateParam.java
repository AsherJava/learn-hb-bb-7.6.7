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
import com.jiuqi.nr.bpm.instance.bean.QueryGridDataParam;
import com.jiuqi.nr.context.infc.impl.NRContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.util.CollectionUtils;

public class StartStateParam
extends NRContext
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String taskKey;
    private String period;
    private String adjust;
    private Set<String> formOrGroupKeys;
    private QueryGridDataParam queryGridDataParam;
    private int operateType;
    private transient Map<String, Object> variableMap = new HashMap<String, Object>();

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

    public Set<String> getFormOrGroupKeys() {
        return this.formOrGroupKeys;
    }

    public void setFormOrGroupKeys(Set<String> formOrGroupKeys) {
        this.formOrGroupKeys = formOrGroupKeys;
    }

    public int getOperateType() {
        return this.operateType;
    }

    public void setOperateType(int operateType) {
        this.operateType = operateType;
    }

    public QueryGridDataParam getQueryGridDataParam() {
        return this.queryGridDataParam;
    }

    public void setQueryGridDataParam(QueryGridDataParam queryGridDataParam) {
        this.queryGridDataParam = queryGridDataParam;
    }

    public String getAdjust() {
        return this.adjust;
    }

    public void setAdjust(String adjust) {
        this.adjust = adjust;
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

    public static enum Type {
        START(1),
        CLEAR(0),
        STOP(-1);

        private int type;

        private Type(int type) {
            this.type = type;
        }

        public int getType() {
            return this.type;
        }
    }
}

