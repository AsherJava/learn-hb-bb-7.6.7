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
import java.util.Map;

public enum PropDataType {
    BOOLEAN(ColumnModelType.BOOLEAN, "\u5e03\u5c14\u7c7b\u578b"),
    DOUBLE(ColumnModelType.DOUBLE, "\u6d6e\u70b9\u578b"),
    STRING(ColumnModelType.STRING, "\u5b57\u7b26\u578b"),
    INTEGER(ColumnModelType.INTEGER, "\u6574\u6570\u578b"),
    BIG_DECIMAL(ColumnModelType.BIGDECIMAL, "\u9ad8\u7cbe\u5ea6"),
    DATETIME(ColumnModelType.DATETIME, "\u65e5\u671f\u65f6\u95f4"),
    CLOB(ColumnModelType.CLOB, "\u6587\u672c\u7c7b\u578b"),
    BLOB(ColumnModelType.BLOB, "\u4e8c\u8fdb\u5236"),
    ATTACHMENT(ColumnModelType.ATTACHMENT, "\u9644\u4ef6"),
    UUID(ColumnModelType.UUID, "UUID");

    private final int value;
    private final String title;
    private static final Map<Integer, PropDataType> mappings;
    private static final Map<String, PropDataType> title_mappings;

    private PropDataType(ColumnModelType value, String title) {
        this.value = value.getValue();
        this.title = title;
    }

    @JsonValue
    public int getValue() {
        return this.value;
    }

    @JsonIgnore
    public String getTitle() {
        return this.title;
    }

    @JsonCreator
    public static PropDataType forValue(int value) {
        return mappings.get(value);
    }

    public static PropDataType forTitle(String title) {
        return title_mappings.get(title);
    }

    static {
        mappings = new HashMap<Integer, PropDataType>();
        title_mappings = new HashMap<String, PropDataType>();
        for (PropDataType dataType : PropDataType.values()) {
            mappings.put(dataType.getValue(), dataType);
            title_mappings.put(dataType.getTitle(), dataType);
        }
    }
}

