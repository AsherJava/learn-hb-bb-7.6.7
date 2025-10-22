/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dafafill.model.enums;

public enum ModelType {
    SCHEME(1, "\u6570\u636e\u8868"),
    TASK(2, "\u7ec4\u7ec7\u7ed3\u6784");

    private int value;
    private String title;

    private ModelType(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int value() {
        return this.value;
    }

    public String title() {
        return this.title;
    }

    public static ModelType valueOf(int value) {
        for (ModelType type : ModelType.values()) {
            if (value != type.value()) continue;
            return type;
        }
        return null;
    }
}

