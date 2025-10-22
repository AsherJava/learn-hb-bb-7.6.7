/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datastatus.facade.obj;

public class DimensionInfo {
    private String dimensionName;
    private String entityId;
    private String colName;

    public String getDimensionName() {
        return this.dimensionName;
    }

    public void setDimensionName(String dimensionName) {
        this.dimensionName = dimensionName;
    }

    public String getEntityId() {
        return this.entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getColName() {
        return this.colName;
    }

    public void setColName(String colName) {
        this.colName = colName;
    }

    public String getStatusTableCol() {
        if ("DATATIME".equals(this.colName)) {
            return "PERIOD";
        }
        return this.colName;
    }

    public String getTZTableCol() {
        if ("DATATIME".equals(this.colName)) {
            return "VALIDDATATIME";
        }
        return this.colName;
    }
}

