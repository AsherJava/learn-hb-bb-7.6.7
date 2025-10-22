/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.queryparam;

import com.jiuqi.np.dataengine.common.QueryTable;

public class TableNameFindParam {
    private String regionKey;
    private String tableName;

    public TableNameFindParam() {
    }

    public TableNameFindParam(String regionKey, String tableName) {
        this.regionKey = regionKey;
        this.tableName = tableName;
    }

    public TableNameFindParam(QueryTable queryTable) {
        this.regionKey = queryTable.getRegion();
        this.tableName = queryTable.getTableName();
    }

    public String getRegionKey() {
        return this.regionKey;
    }

    public void setRegionKey(String regionKey) {
        this.regionKey = regionKey;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String toString() {
        return "TableNameFindParam [regionKey=" + this.regionKey + ", tableName=" + this.tableName + "]";
    }
}

