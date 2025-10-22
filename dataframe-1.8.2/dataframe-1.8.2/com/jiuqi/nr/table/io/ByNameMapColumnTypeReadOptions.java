/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.table.io;

import com.jiuqi.nr.table.data.ColumnType;
import com.jiuqi.nr.table.io.ReadOptions;
import java.util.Map;
import java.util.Optional;

class ByNameMapColumnTypeReadOptions
implements ReadOptions.ColumnTypeReadOptions {
    private final Map<String, ColumnType> columnTypesByNameMap;

    public ByNameMapColumnTypeReadOptions(Map<String, ColumnType> columnTypesByNameMap) {
        this.columnTypesByNameMap = columnTypesByNameMap;
    }

    @Override
    public Optional<ColumnType> columnType(int columnNumber, String columnName) {
        return Optional.ofNullable(this.columnTypesByNameMap.get(columnName));
    }
}

