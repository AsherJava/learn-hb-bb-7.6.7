/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.np.dataengine.var.Variable
 *  com.jiuqi.np.definition.common.StringUtils
 */
package com.jiuqi.nr.context.infc.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.np.definition.common.StringUtils;
import com.jiuqi.nr.context.infc.INRContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.springframework.util.CollectionUtils;

public class NRContext
implements INRContext,
Serializable {
    private static final long serialVersionUID = 8981521724668460434L;
    public static final String ENTITY_ID = "contextEntityId";
    public static final String FILTER_EXPRESSION = "contextFilterExpression";
    private String contextEntityId;
    private String contextFilterExpression;
    private String variableStr;

    public NRContext() {
    }

    public NRContext(INRContext inrContext) {
        this.contextEntityId = inrContext.getContextEntityId();
        this.contextFilterExpression = inrContext.getContextFilterExpression();
    }

    @Override
    public String getContextEntityId() {
        return this.contextEntityId;
    }

    public void setContextEntityId(String contextEntityId) {
        this.contextEntityId = contextEntityId;
    }

    @Override
    public String getContextFilterExpression() {
        return this.contextFilterExpression;
    }

    public void setContextFilterExpression(String contextFilterExpression) {
        this.contextFilterExpression = contextFilterExpression;
    }

    @Override
    public List<Variable> getVariables() {
        Map<String, Object> variableMap = this.getVariable();
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

    public Map<String, Object> getVariable() {
        ObjectMapper objectMapper = new ObjectMapper();
        Map map = null;
        if (StringUtils.isEmpty((String)this.variableStr)) {
            return map;
        }
        try {
            map = (Map)objectMapper.readValue(this.variableStr, Map.class);
        }
        catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return map;
    }

    public String getVariableStr() {
        return this.variableStr;
    }

    public void setVariableStr(String variableStr) {
        this.variableStr = variableStr;
    }
}

