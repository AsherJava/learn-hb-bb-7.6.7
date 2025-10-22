/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.entity.engine.var;

import com.jiuqi.nr.entity.adapter.IEntityAdapter;
import com.jiuqi.nr.entity.engine.result.EntityResultSet;
import com.jiuqi.nr.entity.param.impl.EntityQueryParam;

public abstract class RangeQuery {
    public EntityResultSet loadData(EntityQueryParam entityQueryParam, IEntityAdapter entityAdapter) {
        return this.executeQuery(entityQueryParam, entityAdapter);
    }

    protected abstract EntityResultSet executeQuery(EntityQueryParam var1, IEntityAdapter var2);
}

