/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.estimation.service.dataio;

import com.jiuqi.nr.data.estimation.service.dataio.IColumnType;

public interface ITableColumn {
    public String getColumnName();

    public IColumnType getColumnType();
}

