/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.nr.single.core.util.datatable;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.single.core.util.datatable.DataColumn;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class DataColumnCollection
extends ArrayList<DataColumn> {
    private static final long serialVersionUID = 1L;
    private transient Map<String, DataColumn> columnList = new LinkedHashMap<String, DataColumn>();
    private transient Map<String, DataColumn> mapColumnList = new HashMap<String, DataColumn>();
    private boolean hasMapColumName = false;

    public DataColumn get(String columnName) {
        DataColumn item = null;
        if (this.mapColumnList.containsKey(columnName)) {
            item = this.mapColumnList.get(columnName);
        } else if (this.columnList.containsKey(columnName)) {
            item = this.columnList.get(columnName);
        }
        return item;
    }

    public DataColumn addColumn(String columnName, int dataType) {
        DataColumn column = new DataColumn();
        String srcColumnName = columnName;
        if (StringUtils.isNotEmpty((String)srcColumnName) && srcColumnName.length() > 10) {
            srcColumnName = "F_" + this.columnList.size();
            this.hasMapColumName = true;
        }
        column.setColumnName(srcColumnName);
        column.setMapColumnName(columnName);
        column.setDataType(dataType);
        this.addColumn(column);
        return column;
    }

    public void addColumn(DataColumn col) {
        this.add(col);
        this.columnList.put(col.getColumnName(), col);
        this.mapColumnList.put(col.getMapColumnName(), col);
    }

    public boolean isHasMapColumName() {
        return this.hasMapColumName;
    }

    public void setHasMapColumName(boolean hasMapColumName) {
        this.hasMapColumName = hasMapColumName;
    }

    public boolean containColumName(String columnName) {
        boolean result = false;
        if (this.mapColumnList.containsKey(columnName) || this.columnList.containsKey(columnName)) {
            result = true;
        }
        return result;
    }
}

