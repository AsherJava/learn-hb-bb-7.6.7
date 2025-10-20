/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.parameter.model;

import java.util.HashMap;
import java.util.Map;

public enum NoneDsDefValueFilterMode {
    APPOINT(0, "\u6307\u5b9a\u53d6\u503c"),
    EXPRESSION(1, "\u8868\u8fbe\u5f0f");

    private int value;
    private String title;
    private static Map<Integer, NoneDsDefValueFilterMode> finder;

    private NoneDsDefValueFilterMode(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public String title() {
        return this.title;
    }

    public int value() {
        return this.value;
    }

    public static NoneDsDefValueFilterMode valueOf(int value) {
        return finder.get(value);
    }

    public static NoneDsDefValueFilterMode valueOf(Integer value) {
        return finder.get(value);
    }

    static {
        finder = new HashMap<Integer, NoneDsDefValueFilterMode>();
        for (NoneDsDefValueFilterMode item : NoneDsDefValueFilterMode.values()) {
            finder.put(new Integer(item.value()), item);
        }
    }
}

