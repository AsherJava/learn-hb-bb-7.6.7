/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.provider.DimensionColumn
 *  com.jiuqi.np.definition.provider.DimensionMetaData
 *  com.jiuqi.np.definition.provider.DimensionRow
 *  com.jiuqi.nr.entity.engine.data.AbstractData
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 */
package com.jiuqi.nr.fmdm.internal.provider.fmdm;

import com.jiuqi.np.definition.provider.DimensionColumn;
import com.jiuqi.np.definition.provider.DimensionMetaData;
import com.jiuqi.np.definition.provider.DimensionRow;
import com.jiuqi.nr.entity.engine.data.AbstractData;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;

public class EntityRow
extends DimensionRow {
    private IEntityRow entityData;
    private DimensionMetaData metaData;

    public EntityRow(String code, String title, IEntityRow entityRow, DimensionMetaData metaData) {
        super(code, title, metaData);
        this.entityData = entityRow;
        this.metaData = metaData;
    }

    public String getTitle() {
        return this.entityData.getTitle();
    }

    public String getParentKey() {
        return this.entityData.getParentEntityKey();
    }

    public Object getValue(int index) {
        DimensionColumn column = this.metaData.getColumn(index);
        if (column == null) {
            return null;
        }
        AbstractData value = this.entityData.getValue(index);
        return value.getAsObject();
    }

    public Object getValue(String name) {
        return this.entityData.getValue(name).getAsObject();
    }
}

