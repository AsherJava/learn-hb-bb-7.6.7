/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.query.sql.dto;

public enum QueryExportGroupStyleEnum {
    EXCEL_GROUP("excel\u5206\u7ec4"),
    MERGE_CELL_BY_ROW("\u5408\u5e76\u5355\u5143\u683c"),
    NO_MERGE("\u4e0d\u5408\u5e76");

    private String title;

    private QueryExportGroupStyleEnum(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }
}

