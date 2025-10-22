/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 */
package com.jiuqi.nr.calibre2.common;

import com.jiuqi.nvwa.definition.common.ColumnModelType;

public enum CalibreTableColumn {
    KEY("\u6761\u76ee\u4e3b\u952e", "CDT_KEY", 40, 0, false, ColumnModelType.STRING),
    CODE("\u6761\u76ee\u4ee3\u7801", "CDT_CODE", 40, 1, false, ColumnModelType.STRING),
    NAME("\u6761\u76ee\u540d\u79f0", "CDT_NAME", 50, 2, false, ColumnModelType.STRING),
    PARENT("\u6761\u76ee\u7236\u8282\u70b9", "CDT_PARENT_CODE", 40, 3, true, ColumnModelType.STRING),
    ORDER("\u6761\u76ee\u6392\u5e8f\u5b57\u6bb5", "CDT_ORDINAL", 40, 7, false, ColumnModelType.BIGDECIMAL),
    VALUE("\u53e3\u5f84\u5185\u5bb9", "CDT_VALUE", 200, 5, false, ColumnModelType.CLOB),
    CALIBRE_CODE("\u6240\u5c5e\u53e3\u5f84\u5b9a\u4e49\u4ee3\u7801", "CDT_CALIBRE_CODE", 1, 6, true, ColumnModelType.STRING);

    private String title;
    private String code;
    private int lenght;
    private int order;
    private boolean nullable;
    private ColumnModelType type;

    private CalibreTableColumn() {
    }

    private CalibreTableColumn(String title, String code, int lenght, int order, boolean nullable, ColumnModelType type) {
        this.title = title;
        this.code = code;
        this.lenght = lenght;
        this.order = order;
        this.nullable = nullable;
        this.type = type;
    }

    public String getTitle() {
        return this.title;
    }

    public String getCode() {
        return this.code;
    }

    public int getLenght() {
        return this.lenght;
    }

    public int getOrder() {
        return this.order;
    }

    public boolean getNullable() {
        return this.nullable;
    }

    public ColumnModelType getType() {
        return this.type;
    }
}

