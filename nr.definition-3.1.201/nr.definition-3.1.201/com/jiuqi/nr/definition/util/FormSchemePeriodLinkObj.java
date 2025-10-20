/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.util;

import com.jiuqi.nr.definition.util.PeriodCodeRegion;
import java.util.List;

public class FormSchemePeriodLinkObj {
    private String scheme;
    private List<PeriodCodeRegion> periodList;
    private boolean isDefault;

    public boolean isDefault() {
        return this.isDefault;
    }

    public void setDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

    public String getScheme() {
        return this.scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public List<PeriodCodeRegion> getPeriodList() {
        return this.periodList;
    }

    public void setPeriodList(List<PeriodCodeRegion> periodList) {
        this.periodList = periodList;
    }
}

