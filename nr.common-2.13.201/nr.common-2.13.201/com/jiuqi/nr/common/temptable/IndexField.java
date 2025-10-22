/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.common.temptable;

public class IndexField {
    private String fieldName;
    private int sortType = 1;
    public static final int DESC = -1;
    public static final int ASC = 1;

    public String getFieldName() {
        return this.fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public int getSortType() {
        return this.sortType;
    }

    public void setSortType(int sortType) {
        this.sortType = sortType;
    }
}

