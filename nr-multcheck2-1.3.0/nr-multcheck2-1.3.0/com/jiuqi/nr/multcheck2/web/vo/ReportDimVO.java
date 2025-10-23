/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.multcheck2.web.vo;

import java.util.Map;

public class ReportDimVO {
    private Map<String, String> schemeDimSet;
    private Map<String, Map<String, String>> itemsDimSet;

    public Map<String, String> getSchemeDimSet() {
        return this.schemeDimSet;
    }

    public void setSchemeDimSet(Map<String, String> schemeDimSet) {
        this.schemeDimSet = schemeDimSet;
    }

    public Map<String, Map<String, String>> getItemsDimSet() {
        return this.itemsDimSet;
    }

    public void setItemsDimSet(Map<String, Map<String, String>> itemsDimSet) {
        this.itemsDimSet = itemsDimSet;
    }
}

