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
public enum DimensionType implements Serializable
{
    UNIT(1, "\u5355\u4f4d"),
    UNIT_SCOPE(2, "\u5355\u4f4d\u8303\u56f4"),
    PERIOD(4, "\u65f6\u671f"),
    DIMENSION(8, "\u7ef4\u5ea6"),
    CALIBRE(16, "\u53e3\u5f84");

    private final int value;
    private static final HashMap<Integer, DimensionType> MAP;
    private final String title;
    private static final HashMap<String, DimensionType> TITLE_MAP;

    private DimensionType(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int getValue() {
        return this.value;
    }

    public String getTitle() {
        return this.title;
    }

    public static DimensionType valueOf(int value) {
        return MAP.get(value);
    }

    public static DimensionType titleOf(String title) {
        return TITLE_MAP.get(title);
    }

    @JsonCreator
    public static DimensionType forValues(@JsonProperty(value="value") Integer value, @JsonProperty(value="title") String title) {
        if (value == null && title == null) {
            return null;
        }
        if (value != null && title != null) {
            DimensionType byValue = MAP.get(value);
            DimensionType byTitle = TITLE_MAP.get(title);
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

    static {
        DimensionType[] values = DimensionType.values();
        MAP = new HashMap(values.length);
        TITLE_MAP = new HashMap(values.length);
        for (DimensionType value : values) {
            MAP.put(value.value, value);
            TITLE_MAP.put(value.title, value);
        }
    }
}

