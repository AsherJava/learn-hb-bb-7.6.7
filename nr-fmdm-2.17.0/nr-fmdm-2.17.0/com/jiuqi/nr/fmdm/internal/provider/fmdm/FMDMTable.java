/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.provider.DimensionMetaData
 *  com.jiuqi.np.definition.provider.DimensionRow
 *  com.jiuqi.np.definition.provider.DimensionTable
 */
package com.jiuqi.nr.fmdm.internal.provider.fmdm;

import com.jiuqi.np.definition.provider.DimensionMetaData;
import com.jiuqi.np.definition.provider.DimensionRow;
import com.jiuqi.np.definition.provider.DimensionTable;
import com.jiuqi.nr.fmdm.IFMDMData;
import com.jiuqi.nr.fmdm.internal.provider.fmdm.FMDMRow;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FMDMTable
extends DimensionTable {
    List<FMDMRow> fmdmDataList = new ArrayList<FMDMRow>();
    Map<String, FMDMRow> keySearch = new HashMap<String, FMDMRow>();
    Map<String, FMDMRow> codeSearch = new HashMap<String, FMDMRow>();

    public FMDMTable(String dimensionName, DimensionMetaData metaData) {
        super(dimensionName, metaData);
    }

    public FMDMRow addRow(String key, String code, IFMDMData fmdmData) {
        FMDMRow row = new FMDMRow(key, code, fmdmData, super.getMetaData());
        this.fmdmDataList.add(row);
        this.keySearch.put(key, row);
        this.codeSearch.put(code, row);
        return row;
    }

    public DimensionRow getRow(int index) {
        return this.fmdmDataList.get(index);
    }

    public DimensionRow findRowByKey(String key) {
        return this.keySearch.get(key);
    }

    public DimensionRow findRowByCode(String code) {
        return this.codeSearch.get(code);
    }

    public int rowCount() {
        return this.fmdmDataList.size();
    }
}

