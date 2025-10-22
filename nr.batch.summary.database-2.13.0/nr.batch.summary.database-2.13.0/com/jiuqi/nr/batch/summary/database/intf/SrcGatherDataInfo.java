/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.nr.batch.summary.database.intf;

import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import java.util.List;

public class SrcGatherDataInfo {
    private TableModelDefine tableModelDefine;
    private List<ColumnModelDefine> columnModelDefines;
    private boolean isFloat;

    public TableModelDefine getTableModelDefine() {
        return this.tableModelDefine;
    }

    public void setTableModelDefine(TableModelDefine tableModelDefine) {
        this.tableModelDefine = tableModelDefine;
    }

    public List<ColumnModelDefine> getColumnModelDefines() {
        return this.columnModelDefines;
    }

    public void setColumnModelDefines(List<ColumnModelDefine> columnModelDefines) {
        this.columnModelDefines = columnModelDefines;
    }

    public boolean isFloat() {
        return this.isFloat;
    }

    public void setFloat(boolean aFloat) {
        this.isFloat = aFloat;
    }
}

