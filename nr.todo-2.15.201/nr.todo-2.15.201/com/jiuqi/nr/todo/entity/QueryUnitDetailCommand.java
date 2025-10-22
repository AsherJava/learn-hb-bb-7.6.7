/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.todo.entity;

import java.util.List;

public class QueryUnitDetailCommand {
    private List<String> unitIds;
    private String formSchemeId;
    private List<String> formOrGroups;
    private String period;

    public List<String> getUnitIds() {
        return this.unitIds;
    }

    public void setUnitIds(List<String> unitIds) {
        this.unitIds = unitIds;
    }

    public String getFormSchemeId() {
        return this.formSchemeId;
    }

    public void setFormSchemeId(String entityId) {
        this.formSchemeId = entityId;
    }

    public List<String> getFormOrGroups() {
        return this.formOrGroups;
    }

    public void setFormOrGroups(List<String> formOrGroups) {
        this.formOrGroups = formOrGroups;
    }

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }
}

