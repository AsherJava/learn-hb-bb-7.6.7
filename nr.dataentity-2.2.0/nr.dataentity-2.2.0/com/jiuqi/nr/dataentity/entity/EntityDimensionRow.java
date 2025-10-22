/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 */
package com.jiuqi.nr.dataentity.entity;

import com.jiuqi.nr.dataentity.entity.IDimensionRow;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;

public class EntityDimensionRow
implements IDimensionRow {
    private IEntityRow entityRow;

    public EntityDimensionRow(IEntityRow entityRow) {
        this.entityRow = entityRow;
    }

    @Override
    public String getCode() {
        return this.entityRow.getCode();
    }

    @Override
    public String getTitle() {
        return this.entityRow.getTitle();
    }

    @Override
    public Object getExtAttribute(String attributeName) {
        return null;
    }
}

