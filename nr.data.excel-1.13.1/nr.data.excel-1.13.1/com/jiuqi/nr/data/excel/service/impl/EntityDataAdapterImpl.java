/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 */
package com.jiuqi.nr.data.excel.service.impl;

import com.jiuqi.nr.data.excel.obj.DimensionData;
import com.jiuqi.nr.data.excel.service.internal.IDimensionDataAdapter;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntityDataAdapterImpl
implements IDimensionDataAdapter {
    private final Map<String, DimensionData> cache = new HashMap<String, DimensionData>();
    private final Map<String, List<DimensionData>> titleCache = new HashMap<String, List<DimensionData>>();
    private final IEntityTable entityTable;

    public EntityDataAdapterImpl(IEntityTable entityTable) {
        this.entityTable = entityTable;
        this.init();
    }

    private void init() {
        List allRows = this.entityTable.getAllRows();
        for (IEntityRow row : allRows) {
            DimensionData dimensionData = new DimensionData(row.getEntityKeyData(), row.getCode(), row.getTitle());
            this.cache.put(row.getEntityKeyData(), dimensionData);
            if (this.titleCache.containsKey(dimensionData.getTitle())) {
                this.titleCache.get(dimensionData.getTitle()).add(dimensionData);
                continue;
            }
            ArrayList<DimensionData> list = new ArrayList<DimensionData>();
            list.add(dimensionData);
            this.titleCache.put(dimensionData.getTitle(), list);
        }
    }

    @Override
    public DimensionData getDimensionData(String dimValue) {
        if (this.cache.containsKey(dimValue)) {
            return this.cache.get(dimValue);
        }
        IEntityRow row = this.entityTable.quickFindByEntityKey(dimValue);
        if (row == null) {
            row = this.entityTable.findByCode(dimValue);
        }
        DimensionData dimensionData = row == null ? new DimensionData() : new DimensionData(row.getEntityKeyData(), row.getCode(), row.getTitle());
        this.cache.put(dimValue, dimensionData);
        return dimensionData;
    }

    @Override
    public List<DimensionData> getByTitle(String dimValueTitle) {
        return this.titleCache.get(dimValueTitle);
    }

    @Override
    public String getCode(String dimValue) {
        DimensionData dimensionData = this.getDimensionData(dimValue);
        if (dimensionData != null) {
            return dimensionData.getCode();
        }
        return null;
    }

    @Override
    public String getTitle(String dimValue) {
        DimensionData dimensionData = this.getDimensionData(dimValue);
        if (dimensionData != null) {
            return dimensionData.getTitle();
        }
        return null;
    }
}

