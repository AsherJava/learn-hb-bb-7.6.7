/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.budget.masterdata.basedata;

import com.jiuqi.budget.dataperiod.DataPeriod;

public class VerDateQueryParam {
    private DataPeriod dataPeriod;
    private String mainTaskId;
    private String tableName;

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public DataPeriod getDataPeriod() {
        return this.dataPeriod;
    }

    public void setDataPeriod(DataPeriod dataPeriod) {
        this.dataPeriod = dataPeriod;
    }

    public String getMainTaskId() {
        return this.mainTaskId;
    }

    public void setMainTaskId(String mainTaskId) {
        this.mainTaskId = mainTaskId;
    }
}

