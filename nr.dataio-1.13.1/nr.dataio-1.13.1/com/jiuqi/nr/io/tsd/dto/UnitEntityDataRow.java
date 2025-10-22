/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataentity_ext.dto.IEntityDataRow
 */
package com.jiuqi.nr.io.tsd.dto;

import com.jiuqi.nr.dataentity_ext.dto.IEntityDataRow;
import com.jiuqi.nr.io.tsd.dto.Unit;

public class UnitEntityDataRow
extends Unit
implements IEntityDataRow {
    public UnitEntityDataRow() {
    }

    public UnitEntityDataRow(Unit unit) {
        this.setEntityDataKey(unit.getEntityDataKey());
        this.setParentKey(unit.getParentKey());
        this.setCode(unit.getCode());
        this.setTitle(unit.getTitle());
        this.setOrder(unit.getOrder());
    }

    public String getKey() {
        return this.getEntityDataKey();
    }

    public String getParent() {
        return this.getParentKey();
    }
}

