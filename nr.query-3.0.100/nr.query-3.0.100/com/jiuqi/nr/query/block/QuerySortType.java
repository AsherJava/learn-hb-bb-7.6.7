/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.query.block;

public enum QuerySortType {
    SORT_ASC,
    SORT_DESC;


    public static QuerySortType parse(String type) {
        return type.equals("Sort_ASC") ? SORT_ASC : SORT_DESC;
    }
}

