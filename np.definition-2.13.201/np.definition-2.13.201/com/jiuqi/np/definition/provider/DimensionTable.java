/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.definition.provider;

import com.jiuqi.np.definition.provider.DimensionMetaData;
import com.jiuqi.np.definition.provider.DimensionRow;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DimensionTable {
    private String dimensionName;
    private String tableCode;
    private String tableKey;
    private DimensionMetaData metaData;
    private List<DimensionRow> rows = new ArrayList<DimensionRow>();
    private Map<String, DimensionRow> rowKeySearch = new HashMap<String, DimensionRow>();
    private Map<String, DimensionRow> rowCodeSearch = new HashMap<String, DimensionRow>();

    public String getDimensionName() {
        return this.dimensionName;
    }

    public String getTableCode() {
        return this.tableCode;
    }

    public void setTableCode(String tableCode) {
        this.tableCode = tableCode;
    }

    public String getTableKey() {
        return this.tableKey;
    }

    public void setTableKey(String tableKey) {
        this.tableKey = tableKey;
    }

    public DimensionTable(String dimensionName, DimensionMetaData metaData) {
        this.dimensionName = dimensionName;
        this.metaData = metaData;
    }

    public DimensionMetaData getMetaData() {
        return this.metaData;
    }

    public DimensionRow addRow(String key, String code) {
        DimensionRow row = new DimensionRow(key, code, this.metaData);
        this.rows.add(row);
        this.rowCodeSearch.put(code, row);
        this.rowKeySearch.put(key, row);
        return row;
    }

    public DimensionRow getRow(int index) {
        if (index < 0 || index >= this.rows.size()) {
            return null;
        }
        return this.rows.get(index);
    }

    public DimensionRow findRowByKey(String key) {
        return this.rowKeySearch.get(key);
    }

    public DimensionRow findRowByCode(String code) {
        return this.rowCodeSearch.get(code);
    }

    public int rowCount() {
        return this.rows.size();
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.dimensionName == null ? 0 : this.dimensionName.hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        DimensionTable other = (DimensionTable)obj;
        return !(this.dimensionName == null ? other.dimensionName != null : !this.dimensionName.equals(other.dimensionName));
    }

    public String toString() {
        return "DimensionTable [dimensionName=" + this.dimensionName + ", metaData=" + this.metaData + ", rows=" + this.rows + "] rowCount+" + this.rowCount();
    }
}

