/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.provider.DimensionColumn
 *  com.jiuqi.np.definition.provider.DimensionMetaData
 *  com.jiuqi.np.definition.provider.DimensionRow
 */
package com.jiuqi.nr.fmdm.internal.provider.fmdm;

import com.jiuqi.np.definition.provider.DimensionColumn;
import com.jiuqi.np.definition.provider.DimensionMetaData;
import com.jiuqi.np.definition.provider.DimensionRow;
import com.jiuqi.nr.fmdm.IFMDMData;

public class FMDMRow
extends DimensionRow {
    private IFMDMData fmdmData;
    private DimensionMetaData metaData;

    public FMDMRow(String code, String title, IFMDMData fmdmData, DimensionMetaData metaData) {
        super(code, title, metaData);
        this.fmdmData = fmdmData;
        this.metaData = metaData;
    }

    public void setTitle(String title) {
        super.setTitle(title);
    }

    public void setParentKey(String parentKey) {
        super.setParentKey(parentKey);
    }

    public Object getValue(int index) {
        DimensionColumn column = this.metaData.getColumn(index);
        if (column == null) {
            return null;
        }
        return this.fmdmData.getAsObject(column.getName());
    }

    public Object getValue(String name) {
        return this.fmdmData.getAsObject(name);
    }
}

