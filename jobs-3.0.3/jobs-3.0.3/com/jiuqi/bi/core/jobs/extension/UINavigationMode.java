/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.core.jobs.extension;

import java.util.HashMap;
import java.util.Map;

public enum UINavigationMode {
    NAVIGATION_TREE(1, "\u5bfc\u822a\u6811\uff0c\u62e5\u6709\u76ee\u5f55"),
    NAVIGATION(2, "\u65e0\u76ee\u5f55\u5bfc\u822a"),
    SINGLE_PAGE(3, "\u5355\u9875\u9762"),
    SYS(4, "\u7cfb\u7edf\u4efb\u52a1\u5206\u7c7b");

    private int value;
    private String title;
    private static Map<Integer, UINavigationMode> finder;

    private UINavigationMode(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int getValue() {
        return this.value;
    }

    public String getTitle() {
        return this.title;
    }

    public static UINavigationMode valueOf(Integer value) {
        return finder.get(value);
    }

    static {
        finder = new HashMap<Integer, UINavigationMode>();
        for (UINavigationMode item : UINavigationMode.values()) {
            finder.put(item.getValue(), item);
        }
    }
}

