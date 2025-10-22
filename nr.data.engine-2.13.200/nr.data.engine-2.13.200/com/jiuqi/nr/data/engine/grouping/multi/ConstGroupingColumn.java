/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.query.QueryContext
 */
package com.jiuqi.nr.data.engine.grouping.multi;

import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.nr.data.engine.grouping.multi.IMultiGroupingColumn;

public class ConstGroupingColumn
implements IMultiGroupingColumn {
    private final Object value;

    @Override
    public Object readValue(QueryContext context) {
        return this.value;
    }

    public ConstGroupingColumn(Object value) {
        this.value = value;
    }

    @Override
    public void writeValue(QueryContext context, Object value) {
    }

    @Override
    public int getDataType(QueryContext context) {
        return 0;
    }

    @Override
    public void writeValue(Object value) {
    }
}

