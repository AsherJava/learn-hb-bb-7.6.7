/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.collections4.MapUtils
 */
package com.jiuqi.bde.common.util;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.collections4.MapUtils;

public class Variable {
    private Map<String, String> variableMap = new HashMap<String, String>(10);
    private static final String VARIABLE_TOKEN_TMPL = "${%s}";

    public void put(String key, String value) {
        value = value == null ? "" : value;
        this.variableMap.put(String.format(VARIABLE_TOKEN_TMPL, key), value);
    }

    public void putAll(Variable variable) {
        if (MapUtils.isNotEmpty(variable.variableMap)) {
            this.variableMap.putAll(variable.variableMap);
        }
    }

    public Map<String, String> getVariableMap() {
        return this.variableMap;
    }
}

