/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonCreator
 *  com.fasterxml.jackson.annotation.JsonFormat
 *  com.fasterxml.jackson.annotation.JsonFormat$Shape
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package com.jiuqi.nr.zb.scheme.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashMap;

@JsonFormat(shape=JsonFormat.Shape.OBJECT)
public enum ZbDiffType {
    TITLE_DIFF(1, "\u540d\u79f0\u4e0d\u4e00\u81f4"),
    DATATYPE_DIFF(2, "\u7c7b\u578b\u4e0d\u4e00\u81f4"),
    PRECISION_DIFF(4, "\u957f\u5ea6\u4e0d\u4e00\u81f4"),
    DECIMAL_DIFF(8, "\u5c0f\u6570\u4f4d\u4e0d\u4e00\u81f4"),
    REFER_DIFF(16, "\u5173\u8054\u679a\u4e3e\u4e0d\u4e00\u81f4"),
    CODE_DIFF(32, "\u6807\u8bc6\u4e0d\u4e00\u81f4");

    private final int type;
    private final String title;
    private static final HashMap<Integer, ZbDiffType> MAP;
    private static final HashMap<String, ZbDiffType> TITLE_MAP;

    private ZbDiffType(int type, String title) {
        this.type = type;
        this.title = title;
    }

    public int getType() {
        return this.type;
    }

    public String getTitle() {
        return this.title;
    }

    public static ZbDiffType typeOf(int type) {
        return MAP.get(type);
    }

    public static ZbDiffType titleOf(String title) {
        return TITLE_MAP.get(title);
    }

    @JsonCreator
    public static ZbDiffType forValues(@JsonProperty(value="type") Integer type, @JsonProperty(value="title") String title) {
        if (MAP.containsKey(type) && TITLE_MAP.containsKey(title) && ZbDiffType.typeOf(type).equals((Object)ZbDiffType.titleOf(title))) {
            return ZbDiffType.typeOf(type);
        }
        return null;
    }

    static {
        MAP = new HashMap();
        TITLE_MAP = new HashMap();
        for (ZbDiffType zbRepeatType : ZbDiffType.values()) {
            MAP.put(zbRepeatType.getType(), zbRepeatType);
            TITLE_MAP.put(zbRepeatType.getTitle(), zbRepeatType);
        }
    }
}

