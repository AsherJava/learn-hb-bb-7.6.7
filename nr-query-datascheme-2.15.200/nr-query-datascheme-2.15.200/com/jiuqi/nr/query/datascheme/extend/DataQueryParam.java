/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.query.datascheme.extend;

import java.util.HashMap;
import java.util.Map;

public class DataQueryParam {
    private Map<String, Object> paramValues = new HashMap<String, Object>();

    public Map<String, Object> getParamValues() {
        return this.paramValues;
    }

    public void setParamValues(Map<String, Object> paramValues) {
        this.paramValues = paramValues;
    }
}

