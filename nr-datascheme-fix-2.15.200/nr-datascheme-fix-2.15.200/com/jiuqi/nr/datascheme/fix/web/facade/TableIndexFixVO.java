/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 */
package com.jiuqi.nr.datascheme.fix.web.facade;

import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.type.DataTableType;

public class TableIndexFixVO {
    private final String key;
    private final String code;
    private final String title;
    private final DataTableType type;
    private final int errorType;

    public TableIndexFixVO(DataTable dataTable, int errorType) {
        this.key = dataTable.getKey();
        this.code = dataTable.getCode();
        this.title = dataTable.getTitle();
        this.type = dataTable.getDataTableType();
        this.errorType = errorType;
    }

    public String getKey() {
        return this.key;
    }

    public String getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }

    public DataTableType getType() {
        return this.type;
    }

    public int getErrorType() {
        return this.errorType;
    }
}

