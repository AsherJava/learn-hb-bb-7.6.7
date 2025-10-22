/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.state.beans;

import com.jiuqi.np.dataengine.common.DimensionValueSet;

public class StateInfo {
    private DimensionValueSet dims;
    private String state;
    private String createTime;
    private String period;
    private int type;
    private String tableName;

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public DimensionValueSet getDims() {
        return this.dims;
    }

    public void setDims(DimensionValueSet dims) {
        this.dims = dims;
    }
}

