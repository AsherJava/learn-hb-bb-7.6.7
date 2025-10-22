/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dafafill.model.enums;

public enum ColWidthType {
    DEFAULT(1, "\u9ed8\u8ba4"),
    AUTOMATIC(2, "\u81ea\u9002\u5e94"),
    CUSTOM(3, "\u81ea\u5b9a\u4e49");

    private int value;
    private String title;

    private ColWidthType(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int value() {
        return this.value;
    }

    public String title() {
        return this.title;
    }

    public static ColWidthType valueOf(int value) {
        for (ColWidthType type : ColWidthType.values()) {
            if (value != type.value()) continue;
            return type;
        }
        return null;
    }
}

