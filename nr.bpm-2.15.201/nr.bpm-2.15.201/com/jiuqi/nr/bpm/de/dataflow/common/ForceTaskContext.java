/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.de.dataflow.common;

import com.jiuqi.nr.bpm.common.TaskContext;
import java.util.HashMap;
import java.util.Map;

public class ForceTaskContext
implements TaskContext {
    private Map<String, Object> values = new HashMap<String, Object>();

    @Override
    public void put(String key, Object value) {
        this.values.put(key, value);
    }

    @Override
    public Object get(String key) {
        return this.values.get(key);
    }
}

