/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.summary.model.soulution;

public enum CommitState {
    ALL(0, "\u6240\u6709"),
    COMMIT(1, "\u5df2\u4e0a\u62a5");

    private int value;
    private String title;

    private CommitState(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int value() {
        return this.value;
    }

    public String title() {
        return this.title;
    }

    public static CommitState valueOf(int value) {
        for (CommitState type : CommitState.values()) {
            if (value != type.value()) continue;
            return type;
        }
        return null;
    }
}

