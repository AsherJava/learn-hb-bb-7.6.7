/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 */
package com.jiuqi.nr.enumcheck.message;

import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.enumcheck.common.EnumAssTable;

public class EnumAssAndEntityInfo {
    private EnumAssTable enumAssTable;
    private IEntityTable entityTable;

    public EnumAssAndEntityInfo(EnumAssTable enumAssTable, IEntityTable entityTable) {
        this.enumAssTable = enumAssTable;
        this.entityTable = entityTable;
    }

    public EnumAssTable getEnumAssTable() {
        return this.enumAssTable;
    }

    public void setEnumAssTable(EnumAssTable enumAssTable) {
        this.enumAssTable = enumAssTable;
    }

    public IEntityTable getEntityTable() {
        return this.entityTable;
    }

    public void setEntityTable(IEntityTable entityTable) {
        this.entityTable = entityTable;
    }
}

