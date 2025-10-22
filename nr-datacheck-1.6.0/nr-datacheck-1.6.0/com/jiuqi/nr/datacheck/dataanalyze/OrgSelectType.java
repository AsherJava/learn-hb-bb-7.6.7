/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datacheck.dataanalyze;

public enum OrgSelectType {
    UCURRENT(0, "\u672c\u7ea7\u8282\u70b9"),
    UCURRENTDIRECTSUB(1, "\u672c\u7ea7+\u76f4\u63a5\u4e0b\u7ea7"),
    UCURRENTALLSUB(2, "\u672c\u7ea7+\u6240\u6709\u4e0b\u7ea7");

    private int value;
    private String title;

    private OrgSelectType(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int value() {
        return this.value;
    }

    public String title() {
        return this.title;
    }

    public static OrgSelectType valueOf(int value) {
        for (OrgSelectType type : OrgSelectType.values()) {
            if (value != type.value()) continue;
            return type;
        }
        return null;
    }
}

