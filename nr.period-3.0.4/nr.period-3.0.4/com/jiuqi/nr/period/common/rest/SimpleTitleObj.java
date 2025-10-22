/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.period.common.rest;

import com.jiuqi.nr.period.common.rest.TitleSettingData;
import java.util.List;

public class SimpleTitleObj {
    private String periodKey;
    private List<TitleSettingData> data;

    public String getPeriodKey() {
        return this.periodKey;
    }

    public void setPeriodKey(String periodKey) {
        this.periodKey = periodKey;
    }

    public List<TitleSettingData> getData() {
        return this.data;
    }

    public void setData(List<TitleSettingData> data) {
        this.data = data;
    }
}

