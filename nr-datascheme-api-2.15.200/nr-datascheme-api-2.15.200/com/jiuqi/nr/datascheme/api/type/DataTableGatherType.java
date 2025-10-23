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
import java.util.HashMap;

@JsonFormat(shape=JsonFormat.Shape.OBJECT)
public enum DataTableGatherType implements Serializable
{
    NONE(1, "\u4e0d\u6c47\u603b"),
    CLASSIFY(4, "\u6309\u4e1a\u52a1\u4e3b\u952e\u6c47\u603b"),
    LIST(8, "\u6309\u4e1a\u52a1\u4e3b\u952e\u7f57\u5217");

    private final int value;
    private final String title;
    private static final HashMap<Integer, DataTableGatherType> MAP;
    private static final HashMap<String, DataTableGatherType> TITLE_MAP;

    private DataTableGatherType(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int getValue() {
        return this.value;
    }

    public String getTitle() {
        return this.title;
    }

    public static DataTableGatherType valueOf(int value) {
        return MAP.get(value);
    }

    public static DataTableGatherType titleOf(String title) {
        return TITLE_MAP.get(title);
    }

    @JsonCreator
    public static DataTableGatherType forValues(@JsonProperty(value="value") Integer value, @JsonProperty(value="title") String title) {
        if (value == null && title == null) {
            return null;
        }
        if (value != null && title != null) {
            DataTableGatherType dataTableGatherType = MAP.get(value);
            DataTableGatherType byTitle = TITLE_MAP.get(title);
            if (byTitle == null) {
                return null;
            }
            return byTitle.equals(dataTableGatherType) ? dataTableGatherType : null;
        }
        if (value != null) {
            return MAP.get(value);
        }
        return TITLE_MAP.get(title);
    }

    static {
        DataTableGatherType[] values = DataTableGatherType.values();
        MAP = new HashMap(values.length);
        TITLE_MAP = new HashMap(values.length);
        for (DataTableGatherType value : values) {
            MAP.put(value.value, value);
            TITLE_MAP.put(value.title, value);
        }
    }
}

