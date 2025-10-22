/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.efdc.bean;

import java.io.Serializable;
import java.util.List;

public class PlanTaskEntityParam
implements Serializable {
    private String entityType;
    private List<String> selected;

    public String getEntityType() {
        return this.entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public List<String> getSelected() {
        return this.selected;
    }

    public void setSelected(List<String> selected) {
        this.selected = selected;
    }
}

