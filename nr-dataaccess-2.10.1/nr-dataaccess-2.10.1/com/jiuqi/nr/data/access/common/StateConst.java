/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.access.common;

public enum StateConst {
    STARTFILL(0, "\u5f00\u542f\u586b\u62a5"),
    ENDFILL(1, "\u7ec8\u6b62\u586b\u62a5"),
    ENDFILLICON(2, "\u7ec8\u6b62\u586b\u62a5\u7f6e\u7070"),
    STARTFILLICON(3, "\u5f00\u542f\u586b\u62a5\u7f6e\u7070");

    private int value;
    private String title;

    private StateConst(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int getValue() {
        return this.value;
    }

    public String getTitle() {
        return this.title;
    }

    public static StateConst valueOf(int value) {
        for (StateConst v : StateConst.values()) {
            if (v.value != value) continue;
            return v;
        }
        return STARTFILL;
    }
}

