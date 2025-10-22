/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.table.io;

import com.jiuqi.nr.table.data.ColumnType;
import com.jiuqi.nr.table.io.ReadOptions;
import java.util.Optional;
import java.util.function.Function;

class PartialFunctionColumnTypeReadOptions
implements ReadOptions.ColumnTypeReadOptions {
    final Function<String, Optional<ColumnType>> function;

    public PartialFunctionColumnTypeReadOptions(Function<String, Optional<ColumnType>> function) {
        this.function = function;
    }

    @Override
    public Optional<ColumnType> columnType(int columnNumber, String columnName) {
        return this.function.apply(columnName);
    }
}

