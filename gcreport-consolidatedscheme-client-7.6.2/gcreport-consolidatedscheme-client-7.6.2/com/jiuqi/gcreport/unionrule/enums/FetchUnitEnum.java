/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.unionrule.enums;

public enum FetchUnitEnum {
    DIFFERENCE("DIFFERENCE", "\u5dee\u989d\u5355\u4f4d"),
    UNION("UNION", "\u5408\u5e76\u5355\u4f4d"),
    HEADQUARTERS("HEADQUARTERS", "\u672c\u90e8\u5355\u4f4d");

    private String code;
    private String name;

    private FetchUnitEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }
}

