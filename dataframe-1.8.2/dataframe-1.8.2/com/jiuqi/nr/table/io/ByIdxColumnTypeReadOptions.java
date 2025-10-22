/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.table.io;

import com.jiuqi.nr.table.data.ColumnType;
import com.jiuqi.nr.table.io.ReadOptions;
import java.util.Optional;

class ByIdxColumnTypeReadOptions
implements ReadOptions.ColumnTypeReadOptions {
    final ColumnType[] columnTypesByIdx;

    public ByIdxColumnTypeReadOptions(ColumnType[] columnTypesByIdx) {
        this.columnTypesByIdx = columnTypesByIdx;
    }

    @Override
    public Optional<ColumnType> columnType(int columnNumber, String columnName) {
        if (this.columnTypesByIdx.length - 1 >= columnNumber) {
            return Optional.of(this.columnTypesByIdx[columnNumber]);
        }
        return Optional.empty();
    }

    @Override
    public ColumnType[] columnTypes() {
        return this.columnTypesByIdx;
    }

    @Override
    public boolean hasColumnTypeForAllColumnsIfHavingColumnNames() {
        return true;
    }

    @Override
    public boolean hasColumnTypeForAllColumns() {
        return true;
    }
}

