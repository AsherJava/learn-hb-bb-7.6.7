/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.summary.model.report;

public enum PublishState {
    DRAFT(0, "\u672a\u53d1\u5e03"),
    PUBLISH(1, "\u5df2\u53d1\u5e03");

    private int value;
    private String title;

    private PublishState(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int value() {
        return this.value;
    }

    public String title() {
        return this.title;
    }

    public static PublishState valueOf(int value) {
        for (PublishState type : PublishState.values()) {
            if (value != type.value()) continue;
            return type;
        }
        return null;
    }
}

