/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.mappingscheme.impl.common;

public enum SaveTypeEnum {
    CREATE("CREATE", "\u65b0\u589e"),
    MODIFY("MODIFY", "\u4fee\u6539");

    private String code;
    private String name;

    private SaveTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getcode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }
}

