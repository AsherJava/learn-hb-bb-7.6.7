/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 */
package com.jiuqi.nr.unit.treestore.enumeration;

import com.jiuqi.nvwa.definition.common.ColumnModelType;

public enum FieldDataType {
    BOOLEAN(ColumnModelType.BOOLEAN, "\u5e03\u5c14"),
    DOUBLE(ColumnModelType.DOUBLE, "\u6d6e\u70b9"),
    STRING(ColumnModelType.STRING, "\u5b57\u7b26"),
    INTEGER(ColumnModelType.INTEGER, "\u6574\u578b"),
    BIGDECIMAL(ColumnModelType.BIGDECIMAL, "\u5927\u6574\u578b"),
    DATETIME(ColumnModelType.DATETIME, "\u65e5\u671f"),
    CLOB(ColumnModelType.CLOB, "\u5b57\u7b26"),
    BLOB(ColumnModelType.BLOB, "\u4e8c\u8fdb\u5236"),
    ATTACHMENT(ColumnModelType.ATTACHMENT, "\u9644\u4ef6"),
    UUID(ColumnModelType.UUID, "UUID");

    public String name;
    public ColumnModelType type;

    private FieldDataType(ColumnModelType type, String name) {
        this.type = type;
        this.name = name;
    }

    public static String getName(ColumnModelType columnModelType) {
        for (FieldDataType type : FieldDataType.values()) {
            if (type.type.getValue() != columnModelType.getValue()) continue;
            return type.name;
        }
        return "";
    }
}

