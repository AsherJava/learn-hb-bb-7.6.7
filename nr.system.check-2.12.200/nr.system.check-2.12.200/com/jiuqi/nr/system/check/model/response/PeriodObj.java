/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.period.modal.IPeriodRow
 */
package com.jiuqi.nr.system.check.model.response;

import com.jiuqi.nr.period.modal.IPeriodRow;
import java.util.List;

public class PeriodObj {
    private String key;
    private String viewKey;
    private String title;
    private int periodType;
    private boolean defaultPeriod = true;
    private List<IPeriodRow> customPeriodDataList;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getViewKey() {
        return this.viewKey;
    }

    public void setViewKey(String viewKey) {
        this.viewKey = viewKey;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPeriodType() {
        return this.periodType;
    }

    public void setPeriodType(int periodType) {
        this.periodType = periodType;
    }

    public boolean getDefaultPeriod() {
        return this.defaultPeriod;
    }

    public void setDefaultPeriod(boolean defaultPeriod) {
        this.defaultPeriod = defaultPeriod;
    }

    public List<IPeriodRow> getCustomPeriodDataList() {
        return this.customPeriodDataList;
    }

    public void setCustomPeriodDataList(List<IPeriodRow> customPeriodDataList) {
        this.customPeriodDataList = customPeriodDataList;
    }
}

