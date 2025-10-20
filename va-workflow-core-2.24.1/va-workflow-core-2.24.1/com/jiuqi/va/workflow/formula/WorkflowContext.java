/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.impl.model.FormulaContext
 */
package com.jiuqi.va.workflow.formula;

import com.jiuqi.va.biz.impl.model.FormulaContext;
import java.util.HashMap;
import java.util.Map;

public class WorkflowContext
extends FormulaContext {
    private Map<String, Object> variables;

    public WorkflowContext(Map<String, Object> variables) {
        if (variables == null) {
            variables = new HashMap<String, Object>();
        }
        this.variables = variables;
    }

    public void put(String key, Object value) {
        this.variables.put(key, value);
    }

    public Object get(String key) {
        return this.variables.get(key);
    }

    public Map<String, Object> getVariables() {
        return this.variables;
    }
}

