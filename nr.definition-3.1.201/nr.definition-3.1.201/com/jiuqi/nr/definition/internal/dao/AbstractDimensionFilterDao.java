/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.IDimensionFilter
 *  com.jiuqi.nr.datascheme.internal.dao.impl.BaseDao
 */
package com.jiuqi.nr.definition.internal.dao;

import com.jiuqi.np.definition.facade.IDimensionFilter;
import com.jiuqi.nr.datascheme.internal.dao.impl.BaseDao;
import com.jiuqi.nr.definition.internal.db.TransUtil;
import org.springframework.util.Assert;

public abstract class AbstractDimensionFilterDao<T extends IDimensionFilter>
extends BaseDao {
    public abstract Class<T> getClz();

    public Class<TransUtil> getExternalTransCls() {
        return TransUtil.class;
    }

    public T getByKey(String key) {
        Assert.hasLength(key, "DimensionFilter key must not be empty");
        return (T)((IDimensionFilter)super.getByKey((Object)key, this.getClz()));
    }

    public T getByEntityId(String entityId) {
        Assert.hasLength(entityId, "DimensionFilter entity id must not be empty");
        return (T)((IDimensionFilter)super.list(new String[]{"DF_ENTITY_ID"}, (Object[])new String[]{entityId}, this.getClz()).stream().findFirst().orElse(null));
    }
}

