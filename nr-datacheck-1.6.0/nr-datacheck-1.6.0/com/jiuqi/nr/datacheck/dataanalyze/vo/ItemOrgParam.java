/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 */
package com.jiuqi.nr.datacheck.dataanalyze.vo;

import com.jiuqi.nr.common.params.DimensionValue;
import java.util.List;
import java.util.Map;

public class ItemOrgParam {
    private String recordKey;
    private String itemKey;
    private String task;
    private List<String> orgs;
    private Map<String, DimensionValue> dimSet;
    private String type;

    public String getRecordKey() {
        return this.recordKey;
    }

    public void setRecordKey(String recordKey) {
        this.recordKey = recordKey;
    }

    public String getItemKey() {
        return this.itemKey;
    }

    public void setItemKey(String itemKey) {
        this.itemKey = itemKey;
    }

    public String getTask() {
        return this.task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public List<String> getOrgs() {
        return this.orgs;
    }

    public void setOrgs(List<String> orgs) {
        this.orgs = orgs;
    }

    public Map<String, DimensionValue> getDimSet() {
        return this.dimSet;
    }

    public void setDimSet(Map<String, DimensionValue> dimSet) {
        this.dimSet = dimSet;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

