/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.bean;

import java.io.Serializable;
import java.util.List;

public class RegionFilterListInfo
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String areaKey;
    private List<String> filterConditions;

    public String getAreaKey() {
        return this.areaKey;
    }

    public void setAreaKey(String areaKey) {
        this.areaKey = areaKey;
    }

    public List<String> getFilterConditions() {
        return this.filterConditions;
    }

    public void setFilterConditions(List<String> filterConditions) {
        this.filterConditions = filterConditions;
    }
}

