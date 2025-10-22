/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.adhoc.model.TimeGranularity
 */
package com.jiuqi.nr.period.common.utils;

import com.jiuqi.bi.adhoc.model.TimeGranularity;

public class TimeDimField {
    private String name;
    private String title;
    private TimeGranularity timeGranularity;
    private int dataType;
    private boolean isTimeKey;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        if (this.title == null && this.timeGranularity != null) {
            return this.timeGranularity.title();
        }
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public TimeGranularity getTimeGranularity() {
        return this.timeGranularity;
    }

    public void setTimeGranularity(TimeGranularity timeGranularity) {
        this.timeGranularity = timeGranularity;
    }

    public int getDataType() {
        return this.dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public boolean isTimeKey() {
        return this.isTimeKey;
    }

    public void setTimeKey(boolean isTimeKey) {
        this.isTimeKey = isTimeKey;
    }
}

