/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.base.common.enums;

public enum SourceDataTypeEnum {
    DIRECT_TYPE("DIRECT", "\u6e90\u8d22\u52a1\u7cfb\u7edf"),
    ODS_TYPE("ODS", "\u4e00\u672c\u8d26ODS\u6e90\u6570\u636e");

    private String code;
    private String name;

    private SourceDataTypeEnum(String code, String name) {
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

