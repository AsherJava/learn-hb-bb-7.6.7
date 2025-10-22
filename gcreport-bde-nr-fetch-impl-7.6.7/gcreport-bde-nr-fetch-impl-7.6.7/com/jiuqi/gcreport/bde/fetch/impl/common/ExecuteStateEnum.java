/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.bde.fetch.impl.common;

public enum ExecuteStateEnum {
    CREATED("CREATED", "\u521b\u5efa"),
    PROCESSING("PROCESSING", "\u8fdb\u884c\u4e2d"),
    FINISHED("FINISHED", "\u5b8c\u6210"),
    FAILURE("FAILURE", "\u5931\u8d25"),
    ERROR("ERROR", "\u5f02\u5e38\u7ed3\u675f"),
    OVERTIME("OVERTIME", "\u8d85\u65f6");

    private String code;
    private String title;

    private ExecuteStateEnum(String code, String title) {
        this.code = code;
        this.title = title;
    }

    public String getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }
}

