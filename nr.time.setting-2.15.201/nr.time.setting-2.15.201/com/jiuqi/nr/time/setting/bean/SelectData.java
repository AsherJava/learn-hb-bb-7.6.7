/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.time.setting.bean;

import java.util.List;

public class SelectData {
    private String fromSchemeKey;
    private String period;
    private List<String> unitIds;

    public String getFromSchemeKey() {
        return this.fromSchemeKey;
    }

    public void setFromSchemeKey(String fromSchemeKey) {
        this.fromSchemeKey = fromSchemeKey;
    }

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public List<String> getUnitIds() {
        return this.unitIds;
    }

    public void setUnitIds(List<String> unitIds) {
        this.unitIds = unitIds;
    }
}

