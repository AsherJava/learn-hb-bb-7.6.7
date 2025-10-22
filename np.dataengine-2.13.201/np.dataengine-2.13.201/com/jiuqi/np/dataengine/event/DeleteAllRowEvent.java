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

public class DeleteAllRowEvent {
    private String tableKey;
    private String rowFilter;
    private boolean isBreak;
    private String message;
    private List<ColumnModelDefine> allFields = new ArrayList<ColumnModelDefine>();
    private DimensionValueSet masterKeys;

    public String getTableKey() {
        return this.tableKey;
    }

    public String getRowFilter() {
        return this.rowFilter;
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

    public void setRowFilter(String rowFilter) {
        this.rowFilter = rowFilter;
    }

    public List<ColumnModelDefine> getAllFields() {
        return this.allFields;
    }

    public void setAllFields(List<ColumnModelDefine> allFields) {
        this.allFields = allFields;
    }

    public DimensionValueSet getMasterKeys() {
        return this.masterKeys;
    }

    public void setMasterKeys(DimensionValueSet masterKeys) {
        this.masterKeys = masterKeys;
    }
}

