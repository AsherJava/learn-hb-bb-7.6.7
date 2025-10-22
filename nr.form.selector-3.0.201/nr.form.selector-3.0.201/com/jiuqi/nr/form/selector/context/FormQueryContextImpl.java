/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.databind.annotation.JsonDeserialize
 *  com.jiuqi.np.dataengine.var.Variable
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.context.infc.impl.NRContext
 *  com.jiuqi.nr.itreebase.context.ITreeVariableDataDeserializer
 *  com.jiuqi.xlib.utils.CollectionUtils
 *  org.json.JSONObject
 */
package com.jiuqi.nr.form.selector.context;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.context.infc.impl.NRContext;
import com.jiuqi.nr.form.selector.context.IFormQueryContext;
import com.jiuqi.nr.itreebase.context.ITreeVariableDataDeserializer;
import com.jiuqi.xlib.utils.CollectionUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

public class FormQueryContextImpl
extends NRContext
implements IFormQueryContext {
    private String taskKey;
    private String formSchemeKey;
    private String period;
    private String formulaSchemeKey;
    private Map<String, DimensionValue> dimValueSet;
    private JSONObject customVariable;
    private Map<String, Object> variableMap = new HashMap<String, Object>();

    @Override
    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    @Override
    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    @Override
    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    @Override
    public Map<String, DimensionValue> getDimValueSet() {
        return this.dimValueSet;
    }

    public void setDimValueSet(Map<String, DimensionValue> dimValueSet) {
        this.dimValueSet = dimValueSet;
    }

    @Override
    public JSONObject getCustomVariable() {
        return this.customVariable;
    }

    @JsonDeserialize(using=ITreeVariableDataDeserializer.class)
    public void setCustomVariable(JSONObject customVariable) {
        this.customVariable = customVariable;
    }

    public String getFormulaSchemeKey() {
        return this.formulaSchemeKey;
    }

    public void setFormulaSchemeKey(String formulaSchemeKey) {
        this.formulaSchemeKey = formulaSchemeKey;
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

