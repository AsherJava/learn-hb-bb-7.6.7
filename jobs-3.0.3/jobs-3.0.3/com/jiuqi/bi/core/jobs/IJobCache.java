/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.core.jobs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface IJobCache {
    public void put(String var1, Object var2);

    public void remove(String var1);

    default public void removes(List<String> keys) {
        if (keys != null) {
            for (String key : keys) {
                this.remove(key);
            }
        }
    }

    public Object get(String var1);

    default public Map<String, Object> gets(List<String> keys) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        if (keys != null) {
            for (String key : keys) {
                Object object = this.get(key);
                if (null == object) continue;
                map.put(key, object);
            }
        }
        return map;
    }

    default public boolean exist(String key) {
        return this.get(key) != null;
    }
}

