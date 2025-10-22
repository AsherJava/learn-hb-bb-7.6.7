/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.definitions.TableModelRunInfo
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.nr.bql.datasource;

import com.jiuqi.np.dataengine.definitions.TableModelRunInfo;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;

public class DataTableColumn {
    private TableModelRunInfo tableRunInfo;
    private ColumnModelDefine dataColumnModel;

    public DataTableColumn(TableModelRunInfo tableRunInfo, ColumnModelDefine dataColumnModel) {
        this.tableRunInfo = tableRunInfo;
        this.dataColumnModel = dataColumnModel;
    }

    public TableModelRunInfo getTableRunInfo() {
        return this.tableRunInfo;
    }

    public ColumnModelDefine getDataColumnModel() {
        return this.dataColumnModel;
    }

    public String getDimensionName() {
        if (this.tableRunInfo.isKeyField(this.dataColumnModel.getCode())) {
            return this.tableRunInfo.getDimensionName(this.dataColumnModel.getCode());
        }
        return null;
    }

    public String toString() {
        return this.tableRunInfo.getTableModelDefine().getCode() + "[" + this.dataColumnModel.getCode() + "]";
    }
}

