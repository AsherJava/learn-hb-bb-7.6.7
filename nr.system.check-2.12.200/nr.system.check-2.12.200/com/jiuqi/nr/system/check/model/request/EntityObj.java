/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.system.check.model.request;

import java.util.List;

public class EntityObj {
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

