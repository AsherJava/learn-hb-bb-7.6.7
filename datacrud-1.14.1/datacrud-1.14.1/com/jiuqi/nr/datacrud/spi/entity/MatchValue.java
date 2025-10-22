/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datacrud.spi.entity;

import java.util.List;

public class MatchValue {
    private String value;
    private List<String> innerValues;

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<String> getInnerValues() {
        return this.innerValues;
    }

    public void setInnerValues(List<String> innerValues) {
        this.innerValues = innerValues;
    }
}

