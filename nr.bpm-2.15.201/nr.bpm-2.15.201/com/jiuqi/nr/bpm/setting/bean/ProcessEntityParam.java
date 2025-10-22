/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.setting.bean;

import java.io.Serializable;
import java.util.List;

public class ProcessEntityParam
implements Serializable {
    private static final long serialVersionUID = 8891557273842652268L;
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

