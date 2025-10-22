/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.datacrud;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.datacrud.SaveRowData;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.util.CollectionUtils;

public class SaveData {
    private final List<String> links = new ArrayList<String>();
    private final Map<Integer, List<SaveRowData>> typeRowsMap = new HashMap<Integer, List<SaveRowData>>();
    private Map<DimensionValueSet, List<SaveRowData>> rowSet2RowMap;

    public List<String> getLinks() {
        return this.links;
    }

    public Map<Integer, List<SaveRowData>> getTypeRowsMap() {
        return this.typeRowsMap;
    }

    public List<SaveRowData> getTypeRows(int type) {
        return this.typeRowsMap.computeIfAbsent(type, k -> new ArrayList());
    }

    public boolean isNotEmpty(int ... types) {
        for (int type : types) {
            if (CollectionUtils.isEmpty(this.getTypeRows(type))) continue;
            return true;
        }
        return false;
    }

    public int getRowCount() {
        int count = 0;
        for (List<SaveRowData> value : this.typeRowsMap.values()) {
            count = value.size() + count;
        }
        return count;
    }

    public List<SaveRowData> getAllRows() {
        ArrayList<SaveRowData> list = new ArrayList<SaveRowData>();
        for (List<SaveRowData> value : this.typeRowsMap.values()) {
            list.addAll(value);
        }
        return list;
    }

    public List<SaveRowData> getRowsByDimensionValueSet(DimensionValueSet dimensionValueSet) {
        if (this.rowSet2RowMap != null) {
            return this.rowSet2RowMap.getOrDefault(dimensionValueSet, Collections.emptyList());
        }
        return Collections.emptyList();
    }

    public void addRowsByDimensionValueSet(DimensionValueSet dimensionValueSet, SaveRowData rowData) {
        if (this.rowSet2RowMap == null) {
            this.rowSet2RowMap = new HashMap<DimensionValueSet, List<SaveRowData>>();
        }
        List rows = this.rowSet2RowMap.computeIfAbsent(dimensionValueSet, dim -> new ArrayList());
        rows.add(rowData);
    }
}

