/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.api.core;

import java.util.HashMap;
import java.util.Map;

public enum AmountUnit {
    YUAN("YUAN", "\u5143", 1),
    BAIYUAN("BAIYUAN", "\u767e\u5143", 2),
    QIANYUAN("QIANYUAN", "\u5343\u5143", 4),
    WANYUAN("WANYUAN", "\u4e07\u5143", 8),
    BAIWAN("BAIWAN", "\u767e\u4e07", 16),
    QIANWAN("QIANWAN", "\u5343\u4e07", 32),
    YIYUAN("YIYUAN", "\u4ebf\u5143", 64);

    private final String code;
    private final String title;
    private final int value;
    public static final Map<Integer, AmountUnit> mappings;
    public static final Map<String, AmountUnit> codeMappings;

    private AmountUnit(String code, String title, int value) {
        this.code = code;
        this.title = title;
        this.value = value;
    }

    public String getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }

    public int getValue() {
        return this.value;
    }

    public static AmountUnit getByValue(int value) {
        return mappings.get(value);
    }

    public static AmountUnit getByCode(String code) {
        return codeMappings.get(code);
    }

    static {
        mappings = new HashMap<Integer, AmountUnit>();
        codeMappings = new HashMap<String, AmountUnit>();
        for (AmountUnit unit : AmountUnit.values()) {
            mappings.put(unit.getValue(), unit);
            codeMappings.put(unit.getCode(), unit);
        }
    }
}

