/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.nr.datacopy.param;

import com.jiuqi.nr.datacopy.param.CopyDataTableDefine;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import java.util.List;

public class CopyDataTable {
    private CopyDataTableDefine copyDataTableDefine;
    private TableModelDefine tableModelDefine;
    private List<ColumnModelDefine> copyDataColModel;

    public CopyDataTableDefine getCopyDataTableDefine() {
        return this.copyDataTableDefine;
    }

    public void setCopyDataTableDefine(CopyDataTableDefine copyDataTableDefine) {
        this.copyDataTableDefine = copyDataTableDefine;
    }

    public List<ColumnModelDefine> getCopyDataColModel() {
        return this.copyDataColModel;
    }

    public void setCopyDataColModel(List<ColumnModelDefine> copyDataColModel) {
        this.copyDataColModel = copyDataColModel;
    }

    public TableModelDefine getTableModelDefine() {
        return this.tableModelDefine;
    }

    public void setTableModelDefine(TableModelDefine tableModelDefine) {
        this.tableModelDefine = tableModelDefine;
    }
}

