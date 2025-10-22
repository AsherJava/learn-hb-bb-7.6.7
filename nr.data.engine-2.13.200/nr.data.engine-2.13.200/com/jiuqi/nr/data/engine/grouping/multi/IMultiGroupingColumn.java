/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.query.QueryContext
 */
package com.jiuqi.nr.data.engine.grouping.multi;

import com.jiuqi.np.dataengine.query.QueryContext;

public interface IMultiGroupingColumn {
    public Object readValue(QueryContext var1);

    public void writeValue(QueryContext var1, Object var2);

    public int getDataType(QueryContext var1);

    public void writeValue(Object var1);
}

