/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentity_ext.dto;

import com.jiuqi.nr.dataentity_ext.dto.EntityDataType;
import java.util.List;

public class SaveParam {
    private String period;
    private String formSchemeKey;
    private List<EntityDataType> countType;

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public List<EntityDataType> getCountType() {
        return this.countType;
    }

    public void setCountType(List<EntityDataType> countType) {
        this.countType = countType;
    }
}

