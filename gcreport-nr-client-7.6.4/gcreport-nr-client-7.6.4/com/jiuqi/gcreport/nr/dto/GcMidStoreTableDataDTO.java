/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.nr.dto;

import java.util.List;

public class GcMidStoreTableDataDTO {
    private List<String> zbNames;
    private List<List<Object>> zbValues;

    public List<String> getZbNames() {
        return this.zbNames;
    }

    public void setZbNames(List<String> zbNames) {
        this.zbNames = zbNames;
    }

    public List<List<Object>> getZbValues() {
        return this.zbValues;
    }

    public void setZbValues(List<List<Object>> zbValues) {
        this.zbValues = zbValues;
    }
}

