/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.query.datascheme.web.param;

import com.jiuqi.nr.query.datascheme.web.param.QueryDataDimVO;

public class QueryDataDimFieldVO
extends QueryDataDimVO {
    private String fieldKey;
    private String fieldCode;
    private String columnName;
    private String columnTitle;

    public String getFieldKey() {
        return this.fieldKey;
    }

    public void setFieldKey(String fieldKey) {
        this.fieldKey = fieldKey;
    }

    public String getFieldCode() {
        return this.fieldCode;
    }

    public void setFieldCode(String fieldCode) {
        this.fieldCode = fieldCode;
    }

    public String getColumnName() {
        return this.columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnTitle() {
        return this.columnTitle;
    }

    public void setColumnTitle(String columnTitle) {
        this.columnTitle = columnTitle;
    }
}

