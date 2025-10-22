/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonCreator
 *  com.fasterxml.jackson.annotation.JsonFormat
 *  com.fasterxml.jackson.annotation.JsonFormat$Shape
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package com.jiuqi.nr.dataresource;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jiuqi.nr.dataresource.NodeType;
import java.util.ArrayList;
import java.util.HashMap;

@JsonFormat(shape=JsonFormat.Shape.OBJECT)
public enum DimType {
    UNIT(1, "\u5355\u4f4d"),
    PERIOD(4, "\u65f6\u671f"),
    DIMENSION(8, "\u7ef4\u5ea6");

    private final String title;
    private final int value;
    private static final HashMap<Integer, DimType> MAP;
    private static final HashMap<String, DimType> TITLE_MAP;

    private DimType(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int getValue() {
        return this.value;
    }

    public String getTitle() {
        return this.title;
    }

    public static DimType valueOf(int value) {
        return MAP.get(value);
    }

    public static DimType titleOf(String title) {
        return TITLE_MAP.get(title);
    }

    public static DimType[] interestType(int type) {
        if (type < 0) {
            return new DimType[0];
        }
        ArrayList<DimType> values = new ArrayList<DimType>(NodeType.values().length);
        for (DimType value : DimType.values()) {
            if ((value.getValue() & type) == 0) continue;
            values.add(value);
        }
        return values.toArray(new DimType[0]);
    }

    @JsonCreator
    public static DimType forValues(@JsonProperty(value="value") Integer value, @JsonProperty(value="title") String title) {
        if (value == null && title == null) {
            return null;
        }
        if (value != null && title != null) {
            DimType byValue = MAP.get(value);
            DimType byTitle = TITLE_MAP.get(title);
            if (byTitle == null) {
                return null;
            }
            return byTitle.equals((Object)byValue) ? byValue : null;
        }
        if (value != null) {
            return MAP.get(value);
        }
        return TITLE_MAP.get(title);
    }

    static {
        DimType[] values = DimType.values();
        MAP = new HashMap(values.length);
        TITLE_MAP = new HashMap(values.length);
        for (DimType value : values) {
            MAP.put(value.value, value);
            TITLE_MAP.put(value.title, value);
        }
    }
}

