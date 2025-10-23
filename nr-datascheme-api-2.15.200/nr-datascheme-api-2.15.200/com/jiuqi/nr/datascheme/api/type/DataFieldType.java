/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonCreator
 *  com.fasterxml.jackson.annotation.JsonFormat
 *  com.fasterxml.jackson.annotation.JsonFormat$Shape
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 */
package com.jiuqi.nr.datascheme.api.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import java.io.Serializable;
import java.util.HashMap;

@JsonFormat(shape=JsonFormat.Shape.OBJECT)
public enum DataFieldType implements Serializable
{
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
    private static final HashMap<Integer, DataFieldType> MAP;
    private static final HashMap<String, DataFieldType> TITLE_MAP;

    private DataFieldType(ColumnModelType columnModelType, String title, int value) {
        this.columnModelType = columnModelType;
        this.title = title;
        this.value = value == -1 ? columnModelType.getValue() : value;
    }

    public int getValue() {
        return this.value;
    }

    public String getTitle() {
        return this.title;
    }

    public static DataFieldType valueOf(int value) {
        return MAP.get(value);
    }

    public static DataFieldType titleOf(String title) {
        return TITLE_MAP.get(title);
    }

    @JsonCreator
    public static DataFieldType forValues(@JsonProperty(value="value") Integer value, @JsonProperty(value="title") String title) {
        if (value == null && title == null) {
            return null;
        }
        if (value != null && title != null) {
            DataFieldType dataFieldType = MAP.get(value);
            DataFieldType byTitle = TITLE_MAP.get(title);
            if (byTitle == null) {
                return null;
            }
            return byTitle.equals(dataFieldType) ? dataFieldType : null;
        }
        if (value != null) {
            return MAP.get(value);
        }
        return TITLE_MAP.get(title);
    }

    @JsonIgnore
    public ColumnModelType toColumnModelType() {
        return this.columnModelType;
    }

    static {
        DataFieldType[] values = DataFieldType.values();
        MAP = new HashMap(values.length);
        TITLE_MAP = new HashMap(values.length);
        for (DataFieldType value : values) {
            MAP.put(value.value, value);
            TITLE_MAP.put(value.title, value);
        }
    }
}

