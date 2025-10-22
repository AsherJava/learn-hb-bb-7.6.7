/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.fielddatacrud;

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
        if (dimValue == null) {
            return;
        }
        if (this.dimValues == null) {
            this.dimValues = new ArrayList<Map<String, String>>();
        }
        this.dimValues.add(dimValue);
    }

    public void addDimValues(List<Map<String, String>> dimValues) {
        if (dimValues == null) {
            return;
        }
        if (this.dimValues == null) {
            this.dimValues = new ArrayList<Map<String, String>>();
        }
        this.dimValues.addAll(dimValues);
    }
}

