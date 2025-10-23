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
public enum OperationType {
    ADD(1, "\u65b0\u589e"),
    MODIFY(2, "\u4fee\u6539");

    private final int type;
    private final String title;
    private static final HashMap<Integer, OperationType> MAP;
    private static final HashMap<String, OperationType> TITLE_MAP;

    private OperationType(int type, String title) {
        this.type = type;
        this.title = title;
    }

    public int getType() {
        return this.type;
    }

    public String getTitle() {
        return this.title;
    }

    public static OperationType typeOf(int type) {
        return MAP.get(type);
    }

    public static OperationType titleOf(String title) {
        return TITLE_MAP.get(title);
    }

    @JsonCreator
    public static OperationType forValues(@JsonProperty(value="type") Integer type, @JsonProperty(value="title") String title) {
        if (MAP.containsKey(type) && TITLE_MAP.containsKey(title) && OperationType.typeOf(type).equals((Object)OperationType.titleOf(title))) {
            return OperationType.typeOf(type);
        }
        return null;
    }

    static {
        MAP = new HashMap();
        TITLE_MAP = new HashMap();
        for (OperationType operationType : OperationType.values()) {
            MAP.put(operationType.getType(), operationType);
            TITLE_MAP.put(operationType.getTitle(), operationType);
        }
    }
}

