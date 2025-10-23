/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.summary.model.caliber;

public enum CaliberApplyType {
    FLOAT(0, "\u6d6e\u52a8"),
    FILTER(1, "\u6761\u4ef6");

    private int value;
    private String title;

    private CaliberApplyType(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int value() {
        return this.value;
    }

    public String title() {
        return this.title;
    }

    public static CaliberApplyType valueOf(int value) {
        for (CaliberApplyType type : CaliberApplyType.values()) {
            if (value != type.value()) continue;
            return type;
        }
        return null;
    }
}

