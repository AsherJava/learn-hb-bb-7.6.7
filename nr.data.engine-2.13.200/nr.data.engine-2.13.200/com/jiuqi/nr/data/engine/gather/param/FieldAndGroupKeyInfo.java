/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.engine.gather.param;

import java.util.LinkedHashMap;

public class FieldAndGroupKeyInfo {
    private String bizKey;
    private LinkedHashMap<String, String> fieldGroupMap;

    public FieldAndGroupKeyInfo() {
    }

    public FieldAndGroupKeyInfo(String bizKey, LinkedHashMap<String, String> fieldGroupMap) {
        this.bizKey = bizKey;
        this.fieldGroupMap = fieldGroupMap;
    }

    public String getBizKey() {
        return this.bizKey;
    }

    public void setBizKey(String bizKey) {
        this.bizKey = bizKey;
    }

    public LinkedHashMap<String, String> getFieldGroupMap() {
        if (this.fieldGroupMap == null) {
            this.fieldGroupMap = new LinkedHashMap<String, String>();
            return this.fieldGroupMap;
        }
        return this.fieldGroupMap;
    }

    public void setFieldGroupMap(LinkedHashMap<String, String> fieldGroupMap) {
        this.fieldGroupMap = fieldGroupMap;
    }
}

