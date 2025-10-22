/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.np.dataengine.fml.account;

import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.reader.QueryFieldInfo;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;

public class AccountQueryFieldInfo
extends QueryFieldInfo {
    private int queryIndex;
    private int columnIndex;

    public AccountQueryFieldInfo(QueryField queryField, ColumnModelDefine fieldDefine, int index) {
        super(queryField, fieldDefine, index);
    }

    public int getQueryIndex() {
        return this.queryIndex;
    }

    public void setQueryIndex(int queryIndex) {
        this.queryIndex = queryIndex;
    }

    public int getColumnIndex() {
        return this.columnIndex;
    }

    public void setColumnIndex(int columnIndex) {
        this.columnIndex = columnIndex;
    }
}

