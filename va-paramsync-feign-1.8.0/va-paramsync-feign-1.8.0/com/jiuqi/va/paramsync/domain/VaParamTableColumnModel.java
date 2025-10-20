/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.jdialect.Type
 *  com.jiuqi.va.mapper.jdialect.model.ColumnModel
 */
package com.jiuqi.va.paramsync.domain;

import com.jiuqi.va.mapper.jdialect.Type;
import com.jiuqi.va.mapper.jdialect.model.ColumnModel;

public class VaParamTableColumnModel {
    private String columnName;
    private Type columnType;

    public String getColumnName() {
        return this.columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public Type getColumnType() {
        return this.columnType;
    }

    public void setColumnType(Type columnType) {
        this.columnType = columnType;
    }

    public static VaParamTableColumnModel newCopy(ColumnModel columnModel) {
        VaParamTableColumnModel model = new VaParamTableColumnModel();
        model.setColumnName(columnModel.getColumnName());
        model.setColumnType(columnModel.getColumnType());
        return model;
    }
}

