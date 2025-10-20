/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.datamapping.client.enums;

public enum DataRefFilterType {
    ALL("ALL", "\u6240\u6709\u6570\u636e"),
    UNREF("UNREF", "\u672a\u6620\u5c04\u6570\u636e"),
    HASREF("HASREF", "\u5df2\u6620\u5c04\u6570\u636e"),
    AUTOMATCH("AUTOMATCH", "\u81ea\u52a8\u5339\u914d\u6570\u636e");

    private String code;
    private String name;

    private DataRefFilterType(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public static DataRefFilterType fromCode(String code) {
        if (code == null) {
            return ALL;
        }
        for (DataRefFilterType type : DataRefFilterType.values()) {
            if (!type.code.equals(code)) continue;
            return type;
        }
        return ALL;
    }
}

