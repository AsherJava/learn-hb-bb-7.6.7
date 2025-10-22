/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 */
package com.jiuqi.nr.calibre2.common;

import com.jiuqi.nvwa.definition.common.ColumnModelType;

public enum CalibreDefineListColumn {
    KEY("\u552f\u4e00\u4e3b\u952e", "CS_KEY", 40, 1, false, ColumnModelType.STRING),
    CD_KEY("\u53e3\u5f84\u5b9a\u4e49Key", "CS_DEFINE", 40, 1, false, ColumnModelType.STRING),
    C_KEY("\u53e3\u5f84Key", "CS_CALIBRE", 40, 1, false, ColumnModelType.STRING),
    LIST_VALUE("\u53e3\u5f84\u5217\u9009\u503c", "CS_VALUE", 40, 1, true, ColumnModelType.STRING);

    private String title;
    private String code;
    private int lenght;
    private int order;
    private boolean nullable;
    private ColumnModelType type;

    private CalibreDefineListColumn() {
    }

    private CalibreDefineListColumn(String title, String code, int lenght, int order, boolean nullable, ColumnModelType type) {
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

