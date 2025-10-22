/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 */
package com.jiuqi.nr.batch.summary.service.table;

import com.jiuqi.nr.batch.summary.service.table.aggregator.IAggregateType;
import com.jiuqi.nvwa.definition.common.ColumnModelType;

public interface IPowerTableColumn {
    public String getColumnCode();

    public ColumnModelType getColumnType();

    public IAggregateType getAggregateType();

    public Object getAnyValue();
}

