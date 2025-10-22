/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.io.sb.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ImportInfo {
    private List<Map<String, String>> dimValues;

    public List<Map<String, String>> getDimValues() {
        return this.dimValues;
    }

    public void setDimValues(List<Map<String, String>> dimValues) {
        this.dimValues = dimValues;
    }

    public void addDimValues(Map<String, String> dimValue) {
        if (this.dimValues == null) {
            this.dimValues = new ArrayList<Map<String, String>>();
        }
        this.dimValues.add(dimValue);
    }
}

