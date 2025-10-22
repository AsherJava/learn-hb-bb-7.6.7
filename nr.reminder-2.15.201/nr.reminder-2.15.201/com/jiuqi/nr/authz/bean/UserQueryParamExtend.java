/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.authz.bean;

import com.jiuqi.nr.authz.bean.UserQueryParam;

public class UserQueryParamExtend
extends UserQueryParam {
    private String taskKey;
    private String formSchemeKey;
    private String period;
    private int unitType;

    public int getUnitType() {
        return this.unitType;
    }

    public void setUnitType(int unitType) {
        this.unitType = unitType;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }
}

