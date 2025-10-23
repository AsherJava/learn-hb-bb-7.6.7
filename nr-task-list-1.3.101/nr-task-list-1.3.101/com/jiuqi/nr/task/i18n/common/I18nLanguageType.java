/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.i18n.common;

import java.util.HashMap;
import java.util.Map;

public enum I18nLanguageType {
    CHINESE(1, "\u4e2d\u6587"),
    ENGLISH(2, "\u82f1\u6587");

    private final int value;
    private final String title;
    private static final Map<Integer, I18nLanguageType> map;

    private I18nLanguageType(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public static I18nLanguageType valueOf(int value) {
        return map.get(value);
    }

    public int getValue() {
        return this.value;
    }

    public String getTitle() {
        return this.title;
    }

    static {
        map = new HashMap<Integer, I18nLanguageType>();
        for (I18nLanguageType type : I18nLanguageType.values()) {
            map.put(type.value, type);
        }
    }
}

