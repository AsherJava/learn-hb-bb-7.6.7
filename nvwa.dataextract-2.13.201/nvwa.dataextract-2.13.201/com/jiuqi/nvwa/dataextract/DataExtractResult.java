/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.dataextract;

import com.jiuqi.nvwa.dataextract.ExtractDataRow;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataExtractResult {
    private final int columnCount;
    private final int[] columnDataTypes;
    private final String[] columnNames;
    private Map<String, Map<String, ExtractDataRow>> searchMaps = new HashMap<String, Map<String, ExtractDataRow>>();
    private List<ExtractDataRow> dataRows = new ArrayList<ExtractDataRow>();

    public DataExtractResult(int columnCount) {
        this.columnCount = columnCount;
        this.columnDataTypes = new int[columnCount];
        this.columnNames = new String[columnCount];
    }

    public void setDataType(int index, int dataType) {
        this.columnDataTypes[index] = dataType;
    }

    public int getDataType(int index) {
        return this.columnDataTypes[index];
    }

    public void setColumnName(int index, String columnName) {
        this.columnNames[index] = columnName;
    }

    public String getColumnName(int index) {
        return this.columnNames[index];
    }

    public ExtractDataRow getRow(int rowIndex) {
        return this.dataRows.get(rowIndex);
    }

    public int getColumnCount() {
        return this.columnCount;
    }

    public int size() {
        return this.dataRows.size();
    }

    public ExtractDataRow addRow() {
        ExtractDataRow row = new ExtractDataRow(this.columnCount);
        this.dataRows.add(row);
        return row;
    }

    public Map<String, ExtractDataRow> findSearchMap(String keyColumnIndexes) {
        Map<String, ExtractDataRow> searchMap = this.searchMaps.get(keyColumnIndexes);
        if (searchMap == null) {
            int i;
            searchMap = new HashMap<String, ExtractDataRow>();
            String[] strs = keyColumnIndexes.split(";");
            int[] indexes = new int[strs.length];
            for (i = 0; i < strs.length; ++i) {
                indexes[i] = Integer.parseInt(strs[i]) - 1;
            }
            for (i = 0; i < this.size(); ++i) {
                ExtractDataRow row = this.getRow(i);
                StringBuilder rowKey = new StringBuilder();
                for (int index : indexes) {
                    rowKey.append(row.getValue(index)).append(";");
                }
                if (rowKey.length() > 0) {
                    rowKey.setLength(rowKey.length() - 1);
                }
                searchMap.put(rowKey.toString(), row);
            }
            this.searchMaps.put(keyColumnIndexes, searchMap);
        }
        return searchMap;
    }
}

