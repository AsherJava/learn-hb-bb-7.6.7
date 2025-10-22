/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.np.dataengine.event;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import java.util.ArrayList;
import java.util.List;

public class DeleteRowEvent {
    private String tableKey;
    private List<DimensionValueSet> deleteRows;
    private boolean isBreak;
    private String message;
    private List<ColumnModelDefine> allFields = new ArrayList<ColumnModelDefine>();

    public String getTableKey() {
        return this.tableKey;
    }

    public List<DimensionValueSet> getDeleteRows() {
        return this.deleteRows;
    }

    public void setBreak(String message) {
        this.isBreak = true;
        this.message = message;
    }

    public boolean isBreak() {
        return this.isBreak;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTableKey(String tableKey) {
        this.tableKey = tableKey;
    }

    public void setDeleteRows(List<DimensionValueSet> deleteRows) {
        this.deleteRows = deleteRows;
    }

    public List<ColumnModelDefine> getAllFields() {
        return this.allFields;
    }

    public void setAllFields(List<ColumnModelDefine> allFields) {
        this.allFields = allFields;
    }
}

