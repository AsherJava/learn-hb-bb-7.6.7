/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.entity.engine.intf.impl;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.entity.adapter.IEntityAdapter;
import com.jiuqi.nr.entity.engine.intf.impl.EntityRowImpl;
import com.jiuqi.nr.entity.engine.intf.impl.ReadonlyTableImpl;
import com.jiuqi.nr.entity.engine.result.EntityResultSet;
import com.jiuqi.nr.entity.param.IEntityQueryParam;

public class DataLoader {
    private final IEntityAdapter entityAdapter;
    private final DimensionValueSet masterKeys;

    public DataLoader(DimensionValueSet masterKeys, IEntityAdapter entityAdapter) {
        this.entityAdapter = entityAdapter;
        this.masterKeys = masterKeys;
    }

    public void loadData(ReadonlyTableImpl readonlyTable, EntityResultSet rs) {
        readonlyTable.reset();
        EntityResultSet resultSet = readonlyTable.getResultSet();
        int oldIndex = 0;
        if (resultSet != null) {
            oldIndex = resultSet.getTotal();
            resultSet.merge(rs);
        } else {
            readonlyTable.setResultSet(rs);
        }
        while (rs.next()) {
            EntityRowImpl dataRow = readonlyTable.addDataRow(this.masterKeys);
            dataRow.setRowIndex(oldIndex + rs.getRowIndex());
        }
    }

    public void loadData(ReadonlyTableImpl readonlyTable, IEntityQueryParam entityQueryParam) {
        EntityResultSet rs = this.entityAdapter.getAllRows(entityQueryParam);
        readonlyTable.reset();
        EntityResultSet resultSet = readonlyTable.getResultSet();
        int oldIndex = 0;
        if (resultSet != null) {
            oldIndex = resultSet.getTotal();
            resultSet.merge(rs);
        } else {
            readonlyTable.setResultSet(rs);
        }
        while (rs.next()) {
            EntityRowImpl dataRow = readonlyTable.addDataRow(this.masterKeys);
            dataRow.setRowIndex(oldIndex + rs.getRowIndex());
        }
    }

    public IEntityAdapter getEntityAdapter() {
        return this.entityAdapter;
    }
}

