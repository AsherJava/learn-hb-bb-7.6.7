/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.impl.data;

import java.util.Map;

public class Encrypt {
    private Map<String, Object> map;
    private String key;
    private String value;

    public Encrypt(Map<String, Object> map, String key, String value) {
        this.map = map;
        this.key = key;
        this.value = value;
    }

    public void apply() {
        this.map.put(this.key, this.value);
    }

    public Map<String, Object> getMap() {
        return this.map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

