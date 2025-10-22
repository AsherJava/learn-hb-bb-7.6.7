/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 */
package com.jiuqi.nr.datacrud.impl.service.impl;

import com.jiuqi.nr.entity.engine.intf.IEntityTable;

public class EntityTableDTO {
    protected IEntityTable entityTable;
    protected String cacheKey;

    public EntityTableDTO(String cacheKey, IEntityTable entityTable) {
        this.entityTable = entityTable;
        this.cacheKey = cacheKey;
    }

    public IEntityTable getEntityTable() {
        return this.entityTable;
    }

    public void setEntityTable(IEntityTable entityTable) {
        this.entityTable = entityTable;
    }

    public String getCacheKey() {
        return this.cacheKey;
    }

    public void setCacheKey(String cacheKey) {
        this.cacheKey = cacheKey;
    }
}

