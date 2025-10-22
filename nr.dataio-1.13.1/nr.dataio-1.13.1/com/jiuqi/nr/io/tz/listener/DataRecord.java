/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.io.tz.listener;

import com.jiuqi.nr.io.tz.listener.ColumnData;
import java.util.Map;

public class DataRecord {
    private Map<String, ColumnData> columnData;

    public Map<String, ColumnData> getColumnData() {
        return this.columnData;
    }

    public void setColumnData(Map<String, ColumnData> columnData) {
        this.columnData = columnData;
    }
}

