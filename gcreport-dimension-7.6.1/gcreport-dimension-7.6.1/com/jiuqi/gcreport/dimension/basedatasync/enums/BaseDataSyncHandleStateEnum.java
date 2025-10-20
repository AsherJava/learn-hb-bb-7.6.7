/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.dimension.basedatasync.enums;

public enum BaseDataSyncHandleStateEnum {
    UNHANDLED(0, "\u672a\u5904\u7406"),
    HANDLED(1, "\u5df2\u5904\u7406"),
    MIDDLE(2, "\u5904\u7406\u4e2d");

    private Integer code;
    private String name;

    private BaseDataSyncHandleStateEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }
}

