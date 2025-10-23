/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.core.settings.enumeration;

public enum StartTimeStrategy {
    IDENTICAL_TO_START_TIME("\u4e0e\u586b\u62a5\u5f00\u59cb\u65f6\u95f4\u4e00\u81f4"),
    IDENTICAL_TO_TASK("\u4e0e\u4efb\u52a1\u7684\u586b\u62a5\u65f6\u671f\u504f\u79fb\u4e00\u81f4"),
    CUSTOM("\u81ea\u5b9a\u4e49");

    public final String title;

    private StartTimeStrategy(String title) {
        this.title = title;
    }
}

