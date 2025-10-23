/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.core.settings.enumeration;

public enum TimeControlType {
    WEEKDAY("\u5de5\u4f5c\u65e5"),
    NATURAL_DAY("\u81ea\u7136\u65e5");

    public final String title;

    private TimeControlType(String title) {
        this.title = title;
    }
}

