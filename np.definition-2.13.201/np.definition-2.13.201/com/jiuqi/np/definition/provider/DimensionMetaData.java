/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.definition.provider;

import com.jiuqi.np.definition.provider.DimensionColumn;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DimensionMetaData {
    private List<DimensionColumn> columns = new ArrayList<DimensionColumn>();
    private Map<String, DimensionColumn> columnMap = new HashMap<String, DimensionColumn>();

    public DimensionColumn addColumn(String name, int dataType) {
        DimensionColumn column = this.columnMap.get(name);
        if (column != null) {
            return column;
        }
        int index = this.columns.size();
        column = new DimensionColumn(name, dataType, index);
        this.columnMap.put(name, column);
        this.columns.add(column);
        return column;
    }

    public DimensionColumn getColumn(int index) {
        if (index < 0 || index >= this.columns.size()) {
            return null;
        }
        return this.columns.get(index);
    }

    public DimensionColumn findColumn(String name) {
        return this.columnMap.get(name);
    }

    public int size() {
        return this.columns.size();
    }

    public String toString() {
        return "DimensionMeta [columns=" + this.columns + "] size:" + this.size();
    }
}

