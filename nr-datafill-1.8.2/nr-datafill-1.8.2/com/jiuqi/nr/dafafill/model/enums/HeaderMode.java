/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dafafill.model.enums;

public enum HeaderMode {
    MERGE(1, "\u5408\u5e76\u8868\u5934"),
    LIST(2, "\u7f57\u5217\u663e\u793a");

    private int value;
    private String title;

    private HeaderMode(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int value() {
        return this.value;
    }

    public String title() {
        return this.title;
    }

    public static HeaderMode valueOf(int value) {
        for (HeaderMode type : HeaderMode.values()) {
            if (value != type.value()) continue;
            return type;
        }
        return null;
    }
}

