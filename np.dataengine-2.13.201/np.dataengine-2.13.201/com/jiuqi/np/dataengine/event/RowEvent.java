/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.event;

import java.util.Date;

public class RowEvent {
    private String tableKey;
    private Date queryStartVersionDate;
    private Date queryVersionDate;
    private String periodCode;
    private boolean onlySaveData;

    public String getTableKey() {
        return this.tableKey;
    }

    public void setTableKey(String tableKey) {
        this.tableKey = tableKey;
    }

    public Date getQueryStartVersionDate() {
        return this.queryStartVersionDate;
    }

    public void setQueryStartVersionDate(Date queryStartVersionDate) {
        this.queryStartVersionDate = queryStartVersionDate;
    }

    public Date getQueryVersionDate() {
        return this.queryVersionDate;
    }

    public void setQueryVersionDate(Date queryVersionDate) {
        this.queryVersionDate = queryVersionDate;
    }

    public String getPeriodCode() {
        return this.periodCode;
    }

    public void setPeriodCode(String periodCode) {
        this.periodCode = periodCode;
    }

    public boolean isOnlySaveData() {
        return this.onlySaveData;
    }

    public void setOnlySaveData(boolean onlySaveData) {
        this.onlySaveData = onlySaveData;
    }
}

