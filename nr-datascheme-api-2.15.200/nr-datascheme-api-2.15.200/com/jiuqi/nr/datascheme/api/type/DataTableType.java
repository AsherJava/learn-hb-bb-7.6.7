/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonCreator
 *  com.fasterxml.jackson.annotation.JsonFormat
 *  com.fasterxml.jackson.annotation.JsonFormat$Shape
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package com.jiuqi.nr.datascheme.api.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

@JsonFormat(shape=JsonFormat.Shape.OBJECT)
public enum DataTableType implements Serializable
{
    TABLE(1, "\u6307\u6807\u8868"),
    MD_INFO(2, "\u5355\u4f4d\u4fe1\u606f\u8868"),
    DETAIL(4, "\u660e\u7ec6\u8868"),
    MULTI_DIM(8, "\u591a\u7ef4\u8868"),
    ACCOUNT(32, "\u53f0\u8d26\u8868"),
    SUB_TABLE(64, "\u5b50\u8868");

    private final int value;
    private final String title;
    private static final HashMap<Integer, DataTableType> MAP;
    private static final HashMap<String, DataTableType> TITLE_MAP;

    private DataTableType(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int getValue() {
        return this.value;
    }

    public String getTitle() {
        return this.title;
    }

    public static DataTableType valueOf(int value) {
        return MAP.get(value);
    }

    public static DataTableType titleOf(String value) {
        return TITLE_MAP.get(value);
    }

    @JsonCreator
    public static DataTableType forValues(@JsonProperty(value="value") Integer value, @JsonProperty(value="title") String title) {
        if (value == null && title == null) {
            return null;
        }
        if (value != null && title != null) {
            DataTableType byValue = MAP.get(value);
            DataTableType byTitle = TITLE_MAP.get(title);
            if (byTitle == null) {
                return null;
            }
            return byTitle.equals(byValue) ? byValue : null;
        }
        if (value != null) {
            return MAP.get(value);
        }
        return TITLE_MAP.get(title);
    }

    public static DataTableType[] interestType(int type) {
        if (type < 0) {
            return new DataTableType[0];
        }
        ArrayList<DataTableType> values = new ArrayList<DataTableType>(DataTableType.values().length);
        for (DataTableType value : DataTableType.values()) {
            if ((value.getValue() & type) == 0) continue;
            values.add(value);
        }
        return values.toArray(new DataTableType[0]);
    }

    static {
        DataTableType[] values = DataTableType.values();
        MAP = new HashMap(values.length);
        TITLE_MAP = new HashMap(values.length);
        for (DataTableType value : values) {
            MAP.put(value.value, value);
            TITLE_MAP.put(value.title, value);
        }
    }
}

