/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.core.jobs.extension;

import java.util.HashMap;
import java.util.Map;

public enum UIOpenMode {
    DIALOG(1, "\u5f39\u6846"),
    BACK_FRAME(2, "iframe\u5d4c\u5165");

    private int value;
    private String title;
    private static Map<Integer, UIOpenMode> finder;

    private UIOpenMode(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int getValue() {
        return this.value;
    }

    public String getTitle() {
        return this.title;
    }

    public static UIOpenMode valueOf(Integer value) {
        return finder.get(value);
    }

    static {
        finder = new HashMap<Integer, UIOpenMode>();
        for (UIOpenMode item : UIOpenMode.values()) {
            finder.put(item.getValue(), item);
        }
    }
}

