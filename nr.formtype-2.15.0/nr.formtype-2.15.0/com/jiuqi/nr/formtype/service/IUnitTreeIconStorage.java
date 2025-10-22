/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.formtype.service;

import java.util.Map;

public interface IUnitTreeIconStorage {
    default public String getBase64Icon(String key) {
        return this.getAllBase64Icon().get(key);
    }

    public Map<String, String> getAllBase64Icon();

    public Map<String, String> getAllBase64Icon(String var1);

    default public String getBase64Icon(String schemeKey, String key) {
        return this.getAllBase64Icon(schemeKey).get(key);
    }

    default public boolean containsKey(String schemeKey, String key) {
        return this.getAllBase64Icon(schemeKey).containsKey(key);
    }
}

