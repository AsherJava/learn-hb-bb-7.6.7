/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.mappingscheme.impl.common;

public enum ModelTypeEnum {
    BASEDATA("BASEDATA", "\u57fa\u7840\u6570\u636e"),
    BIZDATA("BIZDATA", "\u4e1a\u52a1\u6570\u636e");

    private String code;
    private String name;

    private ModelTypeEnum(String code, String name) {
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

