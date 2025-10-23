/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonCreator
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonValue
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 */
package com.jiuqi.nr.zb.scheme.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import java.util.HashMap;

public enum ZbDataType {
    STRING(ColumnModelType.STRING, "\u5b57\u7b26", -1),
    INTEGER(ColumnModelType.INTEGER, "\u6574\u6570", -1),
    BOOLEAN(ColumnModelType.BOOLEAN, "\u5e03\u5c14", -1),
    DATE(ColumnModelType.DATETIME, "\u65e5\u671f", 2),
    DATE_TIME(ColumnModelType.DATETIME, "\u65f6\u95f4\u65e5\u671f", 102),
    UUID(ColumnModelType.UUID, "UUID", -1),
    BIGDECIMAL(ColumnModelType.BIGDECIMAL, "\u6570\u503c", -1),
    CLOB(ColumnModelType.CLOB, "\u6587\u672c", -1),
    PICTURE(ColumnModelType.ATTACHMENT, "\u56fe\u7247", 105),
    FILE(ColumnModelType.ATTACHMENT, "\u9644\u4ef6", -1);

    private final ColumnModelType columnModelType;
    private final String title;
    private final int value;
    private static final HashMap<Integer, ZbDataType> MAP;
    private static final HashMap<String, ZbDataType> TITLE_MAP;

    private ZbDataType(ColumnModelType columnModelType, String title, int value) {
        this.columnModelType = columnModelType;
        this.title = title;
        this.value = value == -1 ? columnModelType.getValue() : value;
    }

    @JsonValue
    public int getValue() {
        return this.value;
    }

    public String getTitle() {
        return this.title;
    }

    public static ZbDataType valueOf(int value) {
        return MAP.get(value);
    }

    public static ZbDataType titleOf(String title) {
        return TITLE_MAP.get(title);
    }

    @JsonCreator
    public static ZbDataType forValues(int value) {
        return MAP.get(value);
    }

    @JsonIgnore
    public ColumnModelType toColumnModelType() {
        return this.columnModelType;
    }

    static {
        ZbDataType[] values = ZbDataType.values();
        MAP = new HashMap(values.length);
        TITLE_MAP = new HashMap(values.length);
        for (ZbDataType value : values) {
            MAP.put(value.value, value);
            TITLE_MAP.put(value.title, value);
        }
    }
}

