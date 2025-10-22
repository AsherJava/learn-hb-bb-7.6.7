/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.common.systemcheck;

import java.util.HashMap;

public enum CheckOptionType {
    EVENT(1, "\u89e6\u53d1\u4e8b\u4ef6"),
    MODAL(4, "\u5f39\u7a97"),
    PAGE(8, "\u9875\u7b7e");

    private final int value;
    private final String title;
    private static final HashMap<Integer, CheckOptionType> MAP;
    private static final HashMap<String, CheckOptionType> TITLE_MAP;

    private CheckOptionType(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int getValue() {
        return this.value;
    }

    public String getTitle() {
        return this.title;
    }

    public static CheckOptionType valueOf(int value) {
        return MAP.get(value);
    }

    static {
        CheckOptionType[] values = CheckOptionType.values();
        MAP = new HashMap(values.length);
        TITLE_MAP = new HashMap(values.length);
        for (CheckOptionType value : values) {
            MAP.put(value.value, value);
            TITLE_MAP.put(value.title, value);
        }
    }
}

