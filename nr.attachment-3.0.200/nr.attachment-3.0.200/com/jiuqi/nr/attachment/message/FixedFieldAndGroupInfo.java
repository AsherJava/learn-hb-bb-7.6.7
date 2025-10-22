/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.attachment.message;

import java.util.LinkedHashMap;

public class FixedFieldAndGroupInfo {
    private Object[] dims;
    private LinkedHashMap<String, String> fieldGroupMap;

    public FixedFieldAndGroupInfo() {
    }

    public FixedFieldAndGroupInfo(Object[] dims, LinkedHashMap<String, String> fieldGroupMap) {
        this.dims = dims;
        this.fieldGroupMap = fieldGroupMap;
    }

    public Object[] getDims() {
        return this.dims;
    }

    public void setDims(Object[] dims) {
        this.dims = dims;
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

