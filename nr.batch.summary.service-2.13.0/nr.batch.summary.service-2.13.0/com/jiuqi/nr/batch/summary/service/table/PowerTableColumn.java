/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 */
package com.jiuqi.nr.batch.summary.service.table;

import com.jiuqi.nr.batch.summary.service.table.IPowerTableColumn;
import com.jiuqi.nr.batch.summary.service.table.aggregator.IAggregateType;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import java.util.Objects;

public class PowerTableColumn
implements IPowerTableColumn {
    private String columnCode;
    private ColumnModelType columnType;
    private IAggregateType aggregateType;
    private Object anyValue;

    public PowerTableColumn(String columnCode) {
        this.columnCode = columnCode;
    }

    public PowerTableColumn(String columnCode, IAggregateType aggregateType, ColumnModelType columnType) {
        this(columnCode);
        this.aggregateType = aggregateType;
        this.columnType = columnType;
    }

    public PowerTableColumn(String columnCode, IAggregateType aggregateType, Object anyValue) {
        this(columnCode);
        this.aggregateType = aggregateType;
        this.anyValue = anyValue;
    }

    @Override
    public String getColumnCode() {
        return this.columnCode;
    }

    public void setColumnCode(String columnCode) {
        this.columnCode = columnCode;
    }

    @Override
    public ColumnModelType getColumnType() {
        return this.columnType;
    }

    public void setColumnType(ColumnModelType columnType) {
        this.columnType = columnType;
    }

    @Override
    public IAggregateType getAggregateType() {
        return this.aggregateType;
    }

    public void setAggregateType(IAggregateType aggregateType) {
        this.aggregateType = aggregateType;
    }

    @Override
    public Object getAnyValue() {
        return this.anyValue;
    }

    public void setAnyValue(Object anyValue) {
        this.anyValue = anyValue;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        PowerTableColumn that = (PowerTableColumn)o;
        return Objects.equals(this.columnCode, that.columnCode);
    }

    public int hashCode() {
        return Objects.hash(this.columnCode);
    }
}

