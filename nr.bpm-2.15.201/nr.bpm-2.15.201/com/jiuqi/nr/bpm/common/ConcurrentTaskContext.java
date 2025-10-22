/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.common;

import com.jiuqi.nr.bpm.common.TaskContext;
import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ConcurrentTaskContext
implements TaskContext,
Serializable {
    private ConcurrentMap<String, Object> values = new ConcurrentHashMap<String, Object>();

    @Override
    public void put(String key, Object value) {
        this.values.put(key, value);
    }

    @Override
    public Object get(String key) {
        return this.values.get(key);
    }
}

