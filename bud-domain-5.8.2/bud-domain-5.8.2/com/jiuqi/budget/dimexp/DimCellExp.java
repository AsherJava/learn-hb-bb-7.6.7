/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.budget.dimexp;

import java.util.Map;

public class DimCellExp {
    private String tableName;
    private String measCode;
    private Map<String, String> dimValMap;

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getMeasCode() {
        return this.measCode;
    }

    public void setMeasCode(String measCode) {
        this.measCode = measCode;
    }

    public Map<String, String> getDimValMap() {
        return this.dimValMap;
    }

    public void setDimValMap(Map<String, String> dimValMap) {
        this.dimValMap = dimValMap;
    }

    public String toString() {
        return "DimCellExp{tableName='" + this.tableName + '\'' + ", measCode='" + this.measCode + '\'' + ", dimValMap=" + this.dimValMap + '}';
    }
}

