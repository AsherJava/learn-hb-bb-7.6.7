/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.table.io;

import com.jiuqi.nr.table.data.ColumnType;
import com.jiuqi.nr.table.io.ReadOptions;
import java.util.Optional;
import java.util.function.Function;

class CompleteFunctionColumnTypeReadOptions
implements ReadOptions.ColumnTypeReadOptions {
    private final Function<String, ColumnType> function;

    public CompleteFunctionColumnTypeReadOptions(Function<String, ColumnType> function) {
        this.function = function;
    }

    @Override
    public Optional<ColumnType> columnType(int columnNumber, String columnName) {
        return Optional.of(this.function.apply(columnName));
    }

    @Override
    public boolean hasColumnTypeForAllColumnsIfHavingColumnNames() {
        return true;
    }
}

