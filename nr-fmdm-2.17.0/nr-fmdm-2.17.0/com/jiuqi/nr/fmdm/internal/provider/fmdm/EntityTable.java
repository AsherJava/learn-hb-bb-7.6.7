/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.provider.DimensionMetaData
 *  com.jiuqi.np.definition.provider.DimensionRow
 *  com.jiuqi.np.definition.provider.DimensionTable
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 */
package com.jiuqi.nr.fmdm.internal.provider.fmdm;

import com.jiuqi.np.definition.provider.DimensionMetaData;
import com.jiuqi.np.definition.provider.DimensionRow;
import com.jiuqi.np.definition.provider.DimensionTable;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.fmdm.internal.provider.fmdm.EntityRow;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntityTable
extends DimensionTable {
    List<EntityRow> entityDataList = new ArrayList<EntityRow>();
    Map<String, EntityRow> keySearch = new HashMap<String, EntityRow>();
    Map<String, EntityRow> codeSearch = new HashMap<String, EntityRow>();

    public EntityTable(String dimensionName, DimensionMetaData metaData) {
        super(dimensionName, metaData);
    }

    public EntityRow addRow(String key, String code, IEntityRow entityRow) {
        EntityRow row = new EntityRow(key, code, entityRow, super.getMetaData());
        this.entityDataList.add(row);
        this.keySearch.put(key, row);
        this.codeSearch.put(code, row);
        return row;
    }

    public DimensionRow getRow(int index) {
        return this.entityDataList.get(index);
    }

    public DimensionRow findRowByKey(String key) {
        return this.keySearch.get(key);
    }

    public DimensionRow findRowByCode(String code) {
        return this.codeSearch.get(code);
    }

    public int rowCount() {
        return this.entityDataList.size();
    }
}

