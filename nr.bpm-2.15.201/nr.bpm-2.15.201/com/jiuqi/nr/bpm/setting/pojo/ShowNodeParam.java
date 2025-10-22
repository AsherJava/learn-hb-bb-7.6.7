/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.jiuqi.np.dataengine.var.Variable
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.context.infc.impl.NRContext
 */
package com.jiuqi.nr.bpm.setting.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.context.infc.impl.NRContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.util.CollectionUtils;

public class ShowNodeParam
extends NRContext
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String formSchemeKey;
    private String period;
    private Map<String, DimensionValue> dimensionSetMap;
    private String formKey;
    private String groupKey;
    private transient Map<String, Object> variableMap = new HashMap<String, Object>();

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getGroupKey() {
        return this.groupKey;
    }

    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
    }

    public Map<String, DimensionValue> getDimensionSetMap() {
        return this.dimensionSetMap;
    }

    public void setDimensionSetMap(Map<String, DimensionValue> dimensionSetMap) {
        this.dimensionSetMap = dimensionSetMap;
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

