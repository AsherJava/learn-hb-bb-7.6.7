/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.todo.entityimpl;

import com.jiuqi.nr.workflow2.todo.entity.TodoTableData;
import com.jiuqi.nr.workflow2.todo.entityimpl.TableDataActualParam;
import com.jiuqi.nr.workflow2.todo.entityimpl.TableDataShowText;

public class TodoTableDataImpl
implements TodoTableData {
    private TableDataShowText tableDataShowText;
    private TableDataActualParam tableDataActualParam;

    @Override
    public TableDataShowText getTableDataShowText() {
        return this.tableDataShowText;
    }

    public void setTableDataShowText(TableDataShowText tableDataShowText) {
        this.tableDataShowText = tableDataShowText;
    }

    @Override
    public TableDataActualParam getTableDataActualParam() {
        return this.tableDataActualParam;
    }

    public void setTableDataActualParam(TableDataActualParam tableDataActualParam) {
        this.tableDataActualParam = tableDataActualParam;
    }
}

