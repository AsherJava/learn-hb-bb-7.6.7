/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.todo.entityimpl;

import com.jiuqi.nr.workflow2.todo.entity.TodoTableData;
import com.jiuqi.nr.workflow2.todo.entity.TodoTableDataInfo;
import java.util.List;

public class TodoTableDataInfoImpl
implements TodoTableDataInfo {
    private int totalNum;
    private List<TodoTableData> tableData;

    public TodoTableDataInfoImpl(int totalNum, List<TodoTableData> tableData) {
        this.totalNum = totalNum;
        this.tableData = tableData;
    }

    @Override
    public int getTotalNum() {
        return this.totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    @Override
    public List<TodoTableData> getTableData() {
        return this.tableData;
    }

    public void setTableData(List<TodoTableData> tableData) {
        this.tableData = tableData;
    }
}

