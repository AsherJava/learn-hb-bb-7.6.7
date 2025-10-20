/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.base.util;

import com.jiuqi.common.base.util.ColumnType;

public enum ColumnTypeEnum {
    STRING("\u5b57\u7b26\u578b", new ColumnType<String>(){

        @Override
        public String getColumn(String param) {
            if (param == null) {
                param = "";
            }
            return " '" + param + "' ";
        }
    }),
    INT("\u6574\u578b", new ColumnType<Integer>(){

        @Override
        public String getColumn(Integer param) {
            return "" + param;
        }
    });

    private String description;
    private ColumnType columnType;

    private <T> ColumnTypeEnum(String description, ColumnType<T> columnType) {
        this.description = description;
        this.columnType = columnType;
    }

    public String getDescription() {
        return this.description;
    }

    public <T> ColumnType<T> getColumnType() {
        return this.columnType;
    }
}

