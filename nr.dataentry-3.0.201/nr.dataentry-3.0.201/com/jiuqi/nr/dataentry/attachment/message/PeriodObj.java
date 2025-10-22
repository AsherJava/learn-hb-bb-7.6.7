/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.attachment.message;

import com.jiuqi.nr.dataentry.attachment.message.CustomPeriodData;
import java.util.List;

public class PeriodObj {
    private String key;
    private String viewKey;
    private String title;
    private int periodType;
    private boolean defaultPeriod = true;
    private List<CustomPeriodData> customPeriodDataList;

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

    public List<CustomPeriodData> getCustomPeriodDataList() {
        return this.customPeriodDataList;
    }

    public void setCustomPeriodDataList(List<CustomPeriodData> customPeriodDataList) {
        this.customPeriodDataList = customPeriodDataList;
    }
}

