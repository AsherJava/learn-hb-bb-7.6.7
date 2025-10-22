/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.snapshot.message;

import java.io.Serializable;
import java.util.List;

public class DataRegionAndFields
implements Serializable {
    private static final long serialVersionUID = 3999414896862270166L;
    private String dataRegionKey;
    private List<String> fieldKeys;

    public String getDataRegionKey() {
        return this.dataRegionKey;
    }

    public void setDataRegionKey(String dataRegionKey) {
        this.dataRegionKey = dataRegionKey;
    }

    public List<String> getFieldKeys() {
        return this.fieldKeys;
    }

    public void setFieldKeys(List<String> fieldKeys) {
        this.fieldKeys = fieldKeys;
    }
}

