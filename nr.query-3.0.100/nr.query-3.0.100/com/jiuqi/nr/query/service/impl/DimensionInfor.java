/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 */
package com.jiuqi.nr.query.service.impl;

import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import java.util.LinkedHashMap;
import java.util.Map;

public class DimensionInfor {
    private String name;
    private String title;
    private String tableName;
    private String tableTitle;
    Map<String, IEntityRow> dimensionValueMap;

    public void setName(String val) {
        this.name = val;
    }

    public String getName() {
        return this.name;
    }

    public void setTitle(String val) {
        this.title = val;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTableName(String val) {
        this.tableName = val;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableTitle(String val) {
        this.tableTitle = val;
    }

    public String getTableTitle() {
        return this.tableTitle;
    }

    public void setDimensionValue(Map<String, IEntityRow> value) {
        this.dimensionValueMap = value;
    }

    public Map<String, IEntityRow> getDimensionValue() {
        if (this.dimensionValueMap == null) {
            return new LinkedHashMap<String, IEntityRow>();
        }
        return this.dimensionValueMap;
    }
}

