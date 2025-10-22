/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.context.cxt.impl;

import com.jiuqi.nr.context.cxt.Extension;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ExtensionImpl
implements Extension {
    private Map<String, Object> variablesMap = new HashMap<String, Object>();

    @Override
    public void put(String key, Serializable value) {
        this.variablesMap.put(key, value);
    }

    @Override
    public Map<String, Object> get() {
        return this.variablesMap;
    }

    @Override
    public Object get(String key) {
        return this.variablesMap.get(key);
    }

    @Override
    public Set<String> getKeys() {
        return this.variablesMap.keySet();
    }
}

