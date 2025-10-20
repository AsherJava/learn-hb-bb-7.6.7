/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.oss;

import java.util.ArrayList;
import java.util.List;

public class ObjectFilterCondition {
    private String propName;
    private List<Object> value = new ArrayList<Object>();
    private boolean exact = true;

    public ObjectFilterCondition(String propName, Object propValue) {
        this.propName = propName;
        this.value.add(propValue);
    }

    public ObjectFilterCondition(String propName, List<Object> propValues) {
        this(propName, propValues, true);
    }

    public ObjectFilterCondition(String propName, List<Object> propValues, boolean exact) {
        this.propName = propName;
        this.value.addAll(propValues);
        this.exact = exact;
    }

    public void setExact(boolean exact) {
        this.exact = exact;
    }

    public String getPropName() {
        return this.propName;
    }

    public boolean isMultiValue() {
        return this.value.size() > 1;
    }

    public List<Object> getValue() {
        return new ArrayList<Object>(this.value);
    }

    public boolean isExact() {
        return this.exact;
    }
}

