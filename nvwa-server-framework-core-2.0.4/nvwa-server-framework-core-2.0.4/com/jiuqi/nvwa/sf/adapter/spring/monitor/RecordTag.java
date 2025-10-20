/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.sf.adapter.spring.monitor;

public class RecordTag {
    private String key;
    private String value;

    public RecordTag(String key, String value) {
        this.key = key;
        this.value = value;
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

