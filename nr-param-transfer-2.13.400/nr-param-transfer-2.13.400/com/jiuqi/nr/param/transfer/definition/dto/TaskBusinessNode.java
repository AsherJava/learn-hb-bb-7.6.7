/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.transfer.engine.BusinessNode
 */
package com.jiuqi.nr.param.transfer.definition.dto;

import com.jiuqi.bi.transfer.engine.BusinessNode;
import java.util.HashMap;
import java.util.Map;

public class TaskBusinessNode
extends BusinessNode {
    private Map<String, Object> values = new HashMap<String, Object>();

    public Map<String, Object> getValues() {
        return this.values;
    }

    public void setValues(Map<String, Object> values) {
        this.values = values;
    }

    public Object getValue(String key) {
        return this.values.get(key);
    }

    public void setValue(String key, Object value) {
        this.values.put(key, value);
    }
}

